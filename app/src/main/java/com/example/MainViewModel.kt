package com.example

import android.app.Application
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class ExamMode {
    OFFICIAL_SIMULATION, // 40 questions, 90 min timer, official 75% threshold (ExamConfig)
    TOPIC_PRACTICE,      // 10-20 questions from a chosen module or ITC block (ExamConfig.ITC_BLOCK_MINUTES)
    MISTAKES_REVIEW      // Questions the user previously failed
}

data class ExamAnswerRecord(
    val question: Question,
    val selectedOption: Int,
    val isCorrect: Boolean
)

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = EnigmaRepository(application)
    val billingManager = BillingManager(application, repository, viewModelScope)

    // Load and merge questions
    val allQuestions = Content.QUESTIONS.toMutableMap().apply {
        val loadedQuestions = QuestionLoader.loadQuestions(application)
        loadedQuestions.forEach { q ->
            val moduleKey = inferModuleKeyFromRef(q.ref)
            this[moduleKey] = this[moduleKey]?.let {
                it.copy(questions = it.questions + q)
            } ?: ModuleDefinition(moduleKey, moduleKey, "⚡", "#CCCCCC", listOf(q))
        }
    }

    private fun inferModuleKeyFromRef(ref: String): String {
        val itcMatch = """\bITC(?:-BT|\s+BT)?\s*[-–]?\s*(\d+)""".toRegex(RegexOption.IGNORE_CASE).find(ref)
        val itc = itcMatch?.groupValues?.get(1)?.toIntOrNull()
        return when {
            itc == 1 -> "itc_01"
            itc in 3..5 -> "empresas"
            itc in 6..9 -> "suministro"
            itc == 10 || itc in 12..17 -> if (itc == 12) "itc_12" else "enlace"
            itc == 11 -> "itc_11"
            itc == 18 -> "tierra"
            itc in 19..27 -> "interiores"
            itc != null -> "especiales"
            ref.contains("ART", ignoreCase = true) || ref.contains("RD 842", ignoreCase = true) -> "articulado"
            else -> "especiales"
        }
    }

    // User profile state
    var currentUserEmail by mutableStateOf("jj.terapias@gmail.com")
    var currentUserName by mutableStateOf("Instalador Autorizado")
    var showUserEmailDialog by mutableStateOf(false)
    var inputEmailString by mutableStateOf("jj.terapias@gmail.com")

    // Theme & UI state
    var isDarkTheme by mutableStateOf(true)
    var showFlashIntro by mutableStateOf(true)
    var activeTab by mutableStateOf("dashboard") // "dashboard", "study", "exams", "laboratory", "analytics", "settings"

    // Repository Flows
    val progressFlow: StateFlow<List<ModuleProgressEntity>> = repository.progressFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val examHistoryFlow: StateFlow<List<ExamRecordEntity>> = repository.examsHistoryFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val dailyActivityFlow: StateFlow<DailyActivityEntity?> = repository.dailyActivityFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val subscriptionFlow: StateFlow<SubscriptionRecordEntity?> = repository.subscriptionFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val questionReviewsFlow: StateFlow<List<QuestionReviewEntity>> = repository.questionReviewsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val remindersFlow: StateFlow<List<ReminderEntity>> = repository.remindersFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val postItsFlow: StateFlow<List<PostItEntity>> = repository.postItsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- REMINDERS SYSTEM STATES ---
    var reminderStatusFilter by mutableStateOf("Todos") // "Todos", "Pendiente", "Completado", "Vencido"
    var reminderPriorityFilter by mutableStateOf("Todos") // "Todos", "Alta", "Media", "Baja"
    var reminderSearchQuery by mutableStateOf("")
    var showReminderDialog by mutableStateOf(false)
    var editingReminder by mutableStateOf<ReminderEntity?>(null)
    var showDeleteConfirmDialog by mutableStateOf<ReminderEntity?>(null)

    // Reminder form fields
    var reminderInputTitle by mutableStateOf("")
    var reminderInputDescription by mutableStateOf("")
    var reminderInputArticle by mutableStateOf("ITC-BT-05")
    var reminderInputDueDate by mutableStateOf(System.currentTimeMillis() + 86400000L)
    var reminderInputPeriodicity by mutableStateOf("Puntual")
    var reminderInputPriority by mutableStateOf("Media")
    var reminderInputNotify by mutableStateOf(true)

    // --- EXAM ENGINE STATES ---
    var activeExamMode by mutableStateOf(ExamMode.OFFICIAL_SIMULATION)
    var activeExamModule by mutableStateOf<ModuleDefinition?>(null)
    
    val demoUsedCount by derivedStateOf {
        dailyActivityFlow.value?.demoUsedCount ?: 0
    }
    
    
    fun incrementDemoUsedCount() {
        viewModelScope.launch {
            val activity = repository.dailyActivityFlow.first() ?: DailyActivityEntity(
                streakDays = 0,
                lastActiveDate = ""
            )
            repository.updateDailyActivity(activity.copy(demoUsedCount = activity.demoUsedCount + 1))
        }
    }
    var currentQuestionIndex by mutableStateOf(0)
    var selectedOptionIndex by mutableStateOf<Int?>(null)
    var currentQuestionAnswered by mutableStateOf(false)
    var isAnswerCorrect by mutableStateOf<Boolean?>(null)
    var examCorrectCount by mutableStateOf(0)
    var examCompleted by mutableStateOf(false)
    var examTimeSpentSeconds by mutableStateOf(0)

    // Exam countdown timer
    var examTotalSeconds by mutableStateOf(ExamConfig.OFFICIAL_MINUTES * 60)
    var examRemainingSeconds by mutableStateOf(ExamConfig.OFFICIAL_MINUTES * 60)
    var isTimerRunning by mutableStateOf(false)
    private var timerJob: Job? = null

    // Track answers for breakdown report and avoid repeating in next simulation
    val examAnswerRecords = mutableStateListOf<ExamAnswerRecord>()
    private var lastOfficialSimulationQuestionHashes = setOf<Int>()

    // Study filter
    var studySearchQuery by mutableStateOf("")
    var selectedStudyCategory by mutableStateOf("Todos")

    // --- LABORATORY / ELECTRICAL SIZING STATES ---
    var labActiveSubTab by mutableStateOf(0) // 0: Conductor, 1: Previsión Edificio, 2: Tubos, 3: Tierra
    var labIsThreePhase by mutableStateOf(false)
    var labCableMaterial by mutableStateOf("cobre") // "cobre" or "aluminio"
    var labInstallMethod by mutableStateOf("A2") // A2, B1, B2, C, E, D
    var labInsulationType by mutableStateOf("XLPE (90ºC)") // XLPE (90ºC) or PVC (70ºC)
    var labAmbientTemp by mutableStateOf("30") // ºC
    var labGroupingFactor by mutableStateOf("1.0") // f_a
    var labPowerKw by mutableStateOf("5.75")
    var labLengthM by mutableStateOf("25")
    var labMaxDropPct by mutableStateOf("1.5")
    var labCalculatedSection by mutableStateOf(1.5)
    var labIzCapacity by mutableStateOf(16.0)
    var labCorrectedIz by mutableStateOf(16.0)
    var labCalculatedPeSection by mutableStateOf(1.5)
    var labStatusMessage by mutableStateOf("")
    var showTechnicalReportDialog by mutableStateOf(false)

    // Building load forecasting
    var foreDwellingsBasic by mutableStateOf("8")
    var foreDwellingsElevated by mutableStateOf("4")
    var foreCommercialSqm by mutableStateOf("120")
    var foreGarageSqm by mutableStateOf("200")
    var foreGeneralServicesKw by mutableStateOf("15.0")
    var foreCalculatedPowerKw by mutableStateOf(0.0)
    var foreRecommendedIgaAmps by mutableStateOf(0.0)
    var foreStatusMessage by mutableStateOf("")

    // Tubes (ITC-BT-21)
    var tubesConductorSec by mutableStateOf("2.5")
    var tubesConductorsCount by mutableStateOf("3")
    var tubesInstallMethod by mutableStateOf("empotrado")
    var tubesCalculatedDiameterMm by mutableStateOf(20)
    var tubesStatusMessage by mutableStateOf("")

    // Grounding (ITC-BT-18)
    var earthSoilResistivity by mutableStateOf("100") // Ohm-m
    var earthPicasCount by mutableStateOf("2")
    var earthPicaLengthM by mutableStateOf("2.0")
    var earthCalculatedResistanceOhms by mutableStateOf(25.0)
    var earthStatusMessage by mutableStateOf("")

    // Billing purchase sheet
    var showCheckoutPlaySheet by mutableStateOf(false)
    var checkoutPlanType by mutableStateOf("pro")

    init {
        billingManager.queryActivePurchases()
        ReminderNotificationManager.ensureNotificationChannel(application)
        DailyStudyNotificationManager.ensureDailyStudyChannel(application)
        viewModelScope.launch {
            repository.seedDefaultRemindersIfEmpty()
            repository.initDailyStudyReminder()
            seedDefaultPostItsIfEmpty()
        }
        runLaboratoryCalculation()
        runBuildingForecastingCalculation()
        runTubeDiameterCalculation()
        runGroundingCalculation()
    }

    // -------------------------------------------------------------
    // EXAM ENGINE LOGIC
    // -------------------------------------------------------------

    /**
     * Genera un conjunto de 40 preguntas estratificadas y reproducibles:
     * - ~25% Articulado (10 preguntas)
     * - ~75% ITCs (30 preguntas) repartidas entre las ITC del ámbito Categoría Básica (excluyendo SPECIALIST_ONLY_ITC)
     * - Sin preguntas repetidas en el mismo simulacro
     * - Prioriza preguntas no vistas en el último simulacro de la sesión
     */
    fun generateOfficialSimulationQuestions(): List<Question> {
        val allQuestionsList = allQuestions.values.flatMap { it.questions }.distinctBy { it.q }

        // Pool de Articulado
        val articuladoPool = allQuestionsList.filter { it.isArticulado() && it.itcNumber() == null }.shuffled().toMutableList()
        if (articuladoPool.size < ExamConfig.OFFICIAL_ARTICULADO_COUNT) {
            val extraArt = allQuestionsList.filter { it.isArticulado() && it !in articuladoPool }.shuffled()
            articuladoPool.addAll(extraArt)
        }

        // Pool de ITCs del ámbito Básica (excluyendo especialista: 6, 7, 38, 51)
        val itcPool = allQuestionsList.filter { q ->
            val itc = q.itcNumber()
            itc != null && itc !in ExamConfig.SPECIALIST_ONLY_ITC
        }.shuffled().toMutableList()

        // Priorizar preguntas no vistas en el último simulacro de la sesión
        val unseenArt = articuladoPool.filter { it.q.hashCode() !in lastOfficialSimulationQuestionHashes }
        val seenArt = articuladoPool.filter { it.q.hashCode() in lastOfficialSimulationQuestionHashes }
        val prioritizedArt = unseenArt + seenArt

        val unseenItc = itcPool.filter { it.q.hashCode() !in lastOfficialSimulationQuestionHashes }
        val seenItc = itcPool.filter { it.q.hashCode() in lastOfficialSimulationQuestionHashes }
        val prioritizedItc = unseenItc + seenItc

        val selected = mutableListOf<Question>()
        val selectedEnunciados = mutableSetOf<String>()

        // 1. Seleccionar preguntas de Articulado (target: ExamConfig.OFFICIAL_ARTICULADO_COUNT)
        for (q in prioritizedArt) {
            if (selected.size >= ExamConfig.OFFICIAL_ARTICULADO_COUNT) break
            if (selectedEnunciados.add(q.q)) {
                selected.add(q)
            }
        }

        // 2. Seleccionar preguntas de ITCs Básicas distribuidas equitativamente
        val itcGroups = prioritizedItc.groupBy { it.itcNumber() ?: 0 }.toMutableMap()
        val itcKeys = itcGroups.keys.toList().shuffled()
        var itcCursor = 0

        while (selected.size < ExamConfig.OFFICIAL_QUESTIONS && itcGroups.values.any { it.isNotEmpty() }) {
            val key = itcKeys[itcCursor % itcKeys.size]
            val group = itcGroups[key]
            if (!group.isNullOrEmpty()) {
                val q = group.first()
                itcGroups[key] = group.drop(1)
                if (selectedEnunciados.add(q.q)) {
                    selected.add(q)
                }
            }
            itcCursor++
            if (selected.size >= ExamConfig.OFFICIAL_QUESTIONS) break
        }

        // 3. Relleno de seguridad si faltasen preguntas para completar exactamente 40
        if (selected.size < ExamConfig.OFFICIAL_QUESTIONS) {
            val fallbackPool = allQuestionsList.filter { q ->
                val itc = q.itcNumber()
                itc == null || itc !in ExamConfig.SPECIALIST_ONLY_ITC
            }.shuffled()
            for (q in fallbackPool) {
                if (selected.size >= ExamConfig.OFFICIAL_QUESTIONS) break
                if (selectedEnunciados.add(q.q)) {
                    selected.add(q)
                }
            }
        }

        // Registrar hashes en memoria de las preguntas del simulacro para no repetirlas de inmediato
        lastOfficialSimulationQuestionHashes = selected.map { it.q.hashCode() }.toSet()

        return selected.shuffled().map { it.withShuffledOptions() }
    }

    /**
     * Start Official Simulation Exam (40 questions, 90 minutes)
     */
    fun startOfficialSimulation() {
        activeExamMode = ExamMode.OFFICIAL_SIMULATION
        val questions40 = generateOfficialSimulationQuestions()

        val module = ModuleDefinition(
            id = "simulacro_oficial_${System.currentTimeMillis()}",
            label = "Simulacro Oficial REBT 2026 (${ExamConfig.OFFICIAL_QUESTIONS} Preguntas)",
            icon = "🏛️",
            color = "#58a6ff",
            questions = questions40
        )

        setupExamSession(module, durationSeconds = ExamConfig.OFFICIAL_MINUTES * 60)
    }

    /**
     * Inicia un test de bloque temático o módulo específico (20 preguntas, 60 minutos)
     */
    fun startTopicPractice(moduleKey: String) {
        val moduleDef = allQuestions[moduleKey] ?: return
        activeExamMode = ExamMode.TOPIC_PRACTICE

        val practiceModule = ModuleDefinition(
            id = "practica_${moduleDef.id}_${System.currentTimeMillis()}",
            label = "Repaso: ${moduleDef.label}",
            icon = moduleDef.icon,
            color = moduleDef.color,
            questions = moduleDef.questions.shuffled().take(ExamConfig.ITC_BLOCK_QUESTIONS).map { it.withShuffledOptions() }
        )

        setupExamSession(practiceModule, durationSeconds = ExamConfig.ITC_BLOCK_MINUTES * 60)
    }

    private val itcPrefs by lazy {
        getApplication<Application>().getSharedPreferences("itc_practice_history", android.content.Context.MODE_PRIVATE)
    }

    private fun getSeenQuestionsForItcKey(key: String): Set<String> {
        return itcPrefs.getStringSet("seen_$key", emptySet()) ?: emptySet()
    }

    private fun saveSeenQuestionsForItcKey(key: String, seen: Set<String>) {
        itcPrefs.edit().putStringSet("seen_$key", seen).apply()
    }

    fun getItcSeenCount(itcNumber: Int): Int {
        val itcKey = String.format("itc_%02d", itcNumber)
        val allQuestionsList = allQuestions.values.flatMap { it.questions }.distinctBy { it.q }
        val pool = allQuestionsList.filter { it.itcNumber() == itcNumber }
        val seen = getSeenQuestionsForItcKey(itcKey)
        return pool.count { it.q in seen }
    }

    fun isItcBankExhausted(itcNumber: Int): Boolean {
        val allQuestionsList = allQuestions.values.flatMap { it.questions }.distinctBy { it.q }
        val pool = allQuestionsList.filter { it.itcNumber() == itcNumber }
        if (pool.isEmpty()) return false
        val itcKey = String.format("itc_%02d", itcNumber)
        val seen = getSeenQuestionsForItcKey(itcKey)
        return pool.isNotEmpty() && pool.all { it.q in seen }
    }

    /**
     * Inicia un test específico por ITC (bloque de 20 preguntas, 60 minutos).
     * Si el usuario ya ha realizado tests y agotado el banco de preguntas de esa ITC,
     * permite seguir realizando tests indefinidamente en bucle continuo de asimilación,
     * variando de forma aleatoria tanto el orden de las preguntas como el orden de las
     * opciones de respuesta para afianzar el dominio real del reglamento.
     */
    fun startItcPractice(itcNumber: Int, itcTitle: String) {
        activeExamMode = ExamMode.TOPIC_PRACTICE
        val allQuestionsList = allQuestions.values.flatMap { it.questions }.distinctBy { it.q }
        val pool = allQuestionsList.filter { it.itcNumber() == itcNumber }
        if (pool.isEmpty()) return

        val itcKey = String.format("itc_%02d", itcNumber)
        val seenQuestions = getSeenQuestionsForItcKey(itcKey).toMutableSet()
        val targetCount = minOf(pool.size, ExamConfig.ITC_BLOCK_QUESTIONS)

        val unseen = pool.filter { it.q !in seenQuestions }
        val selectedPool: List<Question>
        val isExhaustedMode: Boolean

        if (unseen.size >= targetCount) {
            // Hay suficientes preguntas no vistas en este ciclo
            val chosen = unseen.shuffled().take(targetCount)
            selectedPool = chosen
            seenQuestions.addAll(chosen.map { it.q })
            saveSeenQuestionsForItcKey(itcKey, seenQuestions)
            isExhaustedMode = false
        } else {
            // El banco se ha agotado o no quedan suficientes no vistas para completar el bloque
            // Priorizamos las no vistas restantes y rellenamos con recicladas barajadas
            isExhaustedMode = true
            val remainingUnseen = unseen.shuffled()
            val needed = targetCount - remainingUnseen.size
            val recycled = pool.filter { it !in remainingUnseen }.shuffled().take(needed)
            selectedPool = (remainingUnseen + recycled).shuffled()

            // Guardamos el conjunto de preguntas para el nuevo ciclo de asimilación
            saveSeenQuestionsForItcKey(itcKey, selectedPool.map { it.q }.toSet())
        }

        // Variar obligatoriamente tanto el orden de las preguntas como el orden de las 4 opciones
        val randomizedQuestions = selectedPool.shuffled().map { it.withShuffledOptions() }

        val itcCodeFormatted = String.format("ITC-BT-%02d", itcNumber)
        val modeLabel = if (isExhaustedMode) " • Refuerzo y Asimilación" else ""
        val module = ModuleDefinition(
            id = "itc_${String.format("%02d", itcNumber)}_${System.currentTimeMillis()}",
            label = "Test $itcCodeFormatted: $itcTitle$modeLabel",
            icon = if (isExhaustedMode) "🔄" else "📋",
            color = if (itcNumber in ExamConfig.SPECIALIST_ONLY_ITC) "#d29922" else "#238636",
            questions = randomizedQuestions
        )

        setupExamSession(module, durationSeconds = ExamConfig.ITC_BLOCK_MINUTES * 60)
    }

    /**
     * Inicia un test exclusivo del Articulado del REBT (bloque de 20 preguntas, 60 minutos).
     * Soporta rotación continua y barajado de preguntas y respuestas al agotar el banco.
     */
    fun startArticuladoPractice() {
        activeExamMode = ExamMode.TOPIC_PRACTICE
        val allQuestionsList = allQuestions.values.flatMap { it.questions }.distinctBy { it.q }
        val pool = allQuestionsList.filter { it.isArticulado() }
        if (pool.isEmpty()) return

        val artKey = "articulado"
        val seenQuestions = getSeenQuestionsForItcKey(artKey).toMutableSet()
        val targetCount = minOf(pool.size, ExamConfig.ITC_BLOCK_QUESTIONS)

        val unseen = pool.filter { it.q !in seenQuestions }
        val selectedPool: List<Question>
        val isExhaustedMode: Boolean

        if (unseen.size >= targetCount) {
            val chosen = unseen.shuffled().take(targetCount)
            selectedPool = chosen
            seenQuestions.addAll(chosen.map { it.q })
            saveSeenQuestionsForItcKey(artKey, seenQuestions)
            isExhaustedMode = false
        } else {
            isExhaustedMode = true
            val remainingUnseen = unseen.shuffled()
            val needed = targetCount - remainingUnseen.size
            val recycled = pool.filter { it !in remainingUnseen }.shuffled().take(needed)
            selectedPool = (remainingUnseen + recycled).shuffled()
            saveSeenQuestionsForItcKey(artKey, selectedPool.map { it.q }.toSet())
        }

        val randomizedQuestions = selectedPool.shuffled().map { it.withShuffledOptions() }

        val module = ModuleDefinition(
            id = "articulado_${System.currentTimeMillis()}",
            label = if (isExhaustedMode) "Test Articulado REBT • Refuerzo y Asimilación" else "Test Articulado REBT (Art. 1-29)",
            icon = if (isExhaustedMode) "🔄" else "⚡",
            color = "#bc8cff",
            questions = randomizedQuestions
        )

        setupExamSession(module, durationSeconds = ExamConfig.ITC_BLOCK_MINUTES * 60)
    }

    /**
     * Permite al usuario repetir o iniciar un nuevo intento del examen o test actual,
     * barajando nuevamente tanto el orden de las preguntas como el orden de las opciones.
     */
    fun repeatCurrentExamShuffled() {
        val currentModule = activeExamModule ?: return
        val currentQuestions = currentModule.questions
        if (currentQuestions.isEmpty()) return

        val itcNumber = currentQuestions.firstOrNull()?.itcNumber()
        val isArt = currentQuestions.all { it.isArticulado() && it.itcNumber() == null }

        if (itcNumber != null) {
            val cleanTitle = currentModule.label.substringAfter(": ").substringBefore(" •").trim()
            startItcPractice(itcNumber, cleanTitle.ifBlank { "ITC-BT-${String.format("%02d", itcNumber)}" })
        } else if (isArt) {
            startArticuladoPractice()
        } else {
            val newShuffled = currentQuestions.shuffled().map { it.withShuffledOptions() }
            val newModule = currentModule.copy(
                id = "${currentModule.id.substringBeforeLast("_")}_${System.currentTimeMillis()}",
                questions = newShuffled
            )
            setupExamSession(newModule, durationSeconds = examTotalSeconds.coerceAtLeast(60))
        }
    }

    /**
     * Start Review of Failed Questions (Spaced Repetition)
     * Si la pregunta ya no existe en el banco, se omite limpiamente (sin opciones ficticias).
     */
    fun startMistakesReview(reviews: List<QuestionReviewEntity>) {
        if (reviews.isEmpty()) return
        activeExamMode = ExamMode.MISTAKES_REVIEW

        val allAvailableQuestions = allQuestions.values.flatMap { it.questions }
        val mistakeQuestions = reviews.mapNotNull { rev ->
            allAvailableQuestions.find { it.q == rev.questionText }
        }
        if (mistakeQuestions.isEmpty()) return

        val reviewModule = ModuleDefinition(
            id = "revision_fallos_${System.currentTimeMillis()}",
            label = "Repaso de Preguntas Falladas (${mistakeQuestions.size})",
            icon = "🎯",
            color = "#f85149",
            questions = mistakeQuestions
        )

        setupExamSession(reviewModule, durationSeconds = ExamConfig.ITC_BLOCK_MINUTES * 60)
    }

    private fun setupExamSession(module: ModuleDefinition, durationSeconds: Int) {
        activeExamModule = module
        currentQuestionIndex = 0
        selectedOptionIndex = null
        currentQuestionAnswered = false
        isAnswerCorrect = null
        examCorrectCount = 0
        examCompleted = false
        examTimeSpentSeconds = 0
        examTotalSeconds = durationSeconds
        examRemainingSeconds = durationSeconds
        examAnswerRecords.clear()

        startTimer()
        activeTab = "exams"
    }

    private fun startTimer() {
        timerJob?.cancel()
        isTimerRunning = true
        timerJob = viewModelScope.launch {
            while (examRemainingSeconds > 0 && !examCompleted) {
                delay(1000)
                if (isTimerRunning) {
                    examRemainingSeconds--
                    examTimeSpentSeconds++
                    if (examRemainingSeconds <= 0) {
                        finishExamDueToTimeOut()
                    }
                }
            }
        }
    }

    fun pauseTimer() {
        isTimerRunning = false
    }

    fun resumeTimer() {
        isTimerRunning = true
    }

    private fun stopTimer() {
        isTimerRunning = false
        timerJob?.cancel()
        timerJob = null
    }

    fun submitAnswer(displayedQuestion: Question? = null) {
        val module = activeExamModule ?: return
        val originalQuestion = module.questions.getOrNull(currentQuestionIndex) ?: return
        if (selectedOptionIndex == null) return

        val activeQuestion = displayedQuestion ?: originalQuestion
        currentQuestionAnswered = true
        val correct = selectedOptionIndex == activeQuestion.a
        isAnswerCorrect = correct

        val selectedOptionText = activeQuestion.opts.getOrNull(selectedOptionIndex ?: 0) ?: ""
        val originalSelectedOptionIndex = originalQuestion.opts.indexOf(selectedOptionText).let {
            if (it >= 0) it else (selectedOptionIndex ?: 0)
        }

        examAnswerRecords.add(
            ExamAnswerRecord(
                question = originalQuestion,
                selectedOption = originalSelectedOptionIndex,
                isCorrect = correct
            )
        )

        if (correct) {
            examCorrectCount++
            if (activeExamMode == ExamMode.MISTAKES_REVIEW) {
                viewModelScope.launch {
                    val qId = "${module.id}_${originalQuestion.q.hashCode()}"
                    repository.markQuestionMastered(qId)
                }
            }
        } else {
            // Record mistake for Weaknesses review / spaced repetition
            viewModelScope.launch {
                repository.recordQuestionMistake(
                    questionText = originalQuestion.q,
                    moduleKey = module.id,
                    selectedOption = originalSelectedOptionIndex,
                    correctOption = originalQuestion.a,
                    explanation = originalQuestion.exp,
                    reference = originalQuestion.ref
                )
            }
        }
    }

    fun getExamArticuladoBreakdown(): Pair<Int, Int> { // correct, total
        val artRecords = examAnswerRecords.filter { it.question.isArticulado() && it.question.itcNumber() == null }
        return Pair(artRecords.count { it.isCorrect }, artRecords.size)
    }

    fun getExamItcBreakdown(): Pair<Int, Int> { // correct, total
        val itcRecords = examAnswerRecords.filter { it.question.itcNumber() != null }
        return Pair(itcRecords.count { it.isCorrect }, itcRecords.size)
    }

    fun getItcSuccessPct(itcNumber: Int, progressList: List<ModuleProgressEntity>): String {
        val key1 = "itc_${String.format("%02d", itcNumber)}"
        val key2 = "itc_${itcNumber}"
        val record = progressList.find { it.moduleId.startsWith(key1) || it.moduleId.startsWith(key2) }
        return if (record != null) "${record.pct}%" else "—"
    }

    fun getArticuladoSuccessPct(progressList: List<ModuleProgressEntity>): String {
        val record = progressList.find { it.moduleId.startsWith("articulado") || it.moduleId == "articulado" }
        return if (record != null) "${record.pct}%" else "—"
    }

    fun nextQuestion() {
        val module = activeExamModule ?: return
        if (currentQuestionIndex + 1 < module.questions.size) {
            currentQuestionIndex++
            selectedOptionIndex = null
            currentQuestionAnswered = false
            isAnswerCorrect = null
        } else {
            completeExam()
        }
    }

    private fun completeExam() {
        val module = activeExamModule ?: return
        stopTimer()
        examCompleted = true

        viewModelScope.launch {
            repository.recordExam(
                moduleId = module.id,
                correct = examCorrectCount,
                total = module.questions.size
            )
        }
    }

    private fun finishExamDueToTimeOut() {
        completeExam()
    }

    fun exitActiveExam() {
        stopTimer()
        activeExamModule = null
        examCompleted = false
    }

    // -------------------------------------------------------------
    // TECHNICAL LABORATORY CALCULATIONS
    // -------------------------------------------------------------

    fun runLaboratoryCalculation() {
        viewModelScope.launch { repository.useCalculator() }

        val power = labPowerKw.toDoubleOrNull() ?: 5.75
        val length = labLengthM.toDoubleOrNull() ?: 25.0
        val maxDrop = labMaxDropPct.toDoubleOrNull() ?: 1.5
        val ambientT = labAmbientTemp.toDoubleOrNull() ?: 30.0
        val fa = labGroupingFactor.toDoubleOrNull() ?: 1.0

        // Temperature correction factor ft (approximate standard UNE-HD 60364-5-52)
        val isXlpe = labInsulationType.contains("XLPE", ignoreCase = true)
        val ft = when {
            isXlpe -> when {
                ambientT <= 10 -> 1.15
                ambientT <= 20 -> 1.08
                ambientT <= 30 -> 1.00
                ambientT <= 40 -> 0.91
                ambientT <= 50 -> 0.82
                else -> 0.71
            }
            else -> when { // PVC 70ºC
                ambientT <= 10 -> 1.22
                ambientT <= 20 -> 1.11
                ambientT <= 30 -> 1.00
                ambientT <= 40 -> 0.87
                ambientT <= 50 -> 0.71
                else -> 0.50
            }
        }

        // Installation method multiplier relative to A2 base
        val methodMultiplier = when (labInstallMethod) {
            "A2" -> 1.0
            "B1", "B2" -> 1.15
            "C" -> 1.28
            "E" -> 1.45
            "D" -> 1.35
            else -> 1.0
        }

        val u = if (labIsThreePhase) 400.0 else 230.0
        val deltaU = (maxDrop / 100.0) * u
        val c = if (labCableMaterial == "cobre") 44.0 else 28.0 // Conductivity in hot condition (90ºC)
        val pWatts = power * 1000.0

        val sCalc = if (labIsThreePhase) {
            (pWatts * length) / (c * deltaU * u)
        } else {
            (2.0 * pWatts * length) / (c * deltaU * u)
        }

        val standardSections = listOf(1.5, 2.5, 4.0, 6.0, 10.0, 16.0, 25.0, 35.0, 50.0, 70.0, 95.0)
        var selectedSec = standardSections.first()
        for (sec in standardSections) {
            if (sec >= sCalc) {
                selectedSec = sec
                break
            }
        }

        val baseAmpCapacityTable = mapOf(
            1.5 to 14.0,
            2.5 to 18.0,
            4.0 to 24.0,
            6.0 to 31.0,
            10.0 to 42.0,
            16.0 to 56.0,
            25.0 to 73.0,
            35.0 to 89.0,
            50.0 to 108.0
        )

        val currentAmps = if (labIsThreePhase) {
            pWatts / (Math.sqrt(3.0) * u * 0.9)
        } else {
            pWatts / u
        }

        var finalSec = selectedSec
        var baseIz = baseAmpCapacityTable[finalSec] ?: (finalSec * 3.0)
        var finalIz = baseIz * methodMultiplier
        var correctedIz = finalIz * ft * fa

        while (correctedIz < currentAmps && finalSec < 50.0) {
            val idx = standardSections.indexOf(finalSec)
            if (idx == -1 || idx == standardSections.lastIndex) break
            finalSec = standardSections[idx + 1]
            baseIz = baseAmpCapacityTable[finalSec] ?: (finalSec * 3.0)
            finalIz = baseIz * methodMultiplier
            correctedIz = finalIz * ft * fa
        }

        labCalculatedSection = finalSec
        labIzCapacity = finalIz
        labCorrectedIz = correctedIz

        // PE Conductor minimum section calculation according to REBT
        val peSec = when {
            finalSec <= 16.0 -> finalSec
            finalSec <= 35.0 -> 16.0
            else -> finalSec / 2.0
        }
        labCalculatedPeSection = peSec

        val formattedAmps = String.format("%.2f", currentAmps)
        val formattedDrop = String.format("%.2f", sCalc)
        val phaseLabel = if (labIsThreePhase) "Trifásica 400V" else "Monofásica 230V"
        val matLabel = if (labCableMaterial == "cobre") "Cobre" else "Aluminio"

        labStatusMessage = "Informe Técnico de Obra ($matLabel - $phaseLabel):\n" +
                "• Caída de tensión teórica ($maxDrop%): $formattedDrop mm²\n" +
                "• Intensidad de diseño ($currentAmps A) vs Iz Corr ($correctedIz A)\n" +
                "• Método ($labInstallMethod) | T. Amb (${ambientT}ºC, ft=$ft) | Agrup. (fa=$fa)\n" +
                "• Sección Fase Comercial: $finalSec mm²\n" +
                "• Conductor de Protección (PE) Mínimo: $peSec mm²"
    }

    fun runBuildingForecastingCalculation() {
        val basic = foreDwellingsBasic.toIntOrNull() ?: 0
        val elevated = foreDwellingsElevated.toIntOrNull() ?: 0
        val commSqm = foreCommercialSqm.toDoubleOrNull() ?: 0.0
        val garageSqm = foreGarageSqm.toDoubleOrNull() ?: 0.0
        val servicesKw = foreGeneralServicesKw.toDoubleOrNull() ?: 0.0

        val totalDwellings = basic + elevated
        val divFactor = if (totalDwellings <= 1) 1.0 else if (totalDwellings <= 21) {
            1.0 + (totalDwellings - 1) * 0.153
        } else {
            15.3 + (totalDwellings - 21) * 0.5
        }

        val sumDwellings = (basic * 5.75) + (elevated * 9.20)
        val avgDwelling = if (totalDwellings > 0) sumDwellings / totalDwellings else 0.0
        val pDwellingsCombined = avgDwelling * divFactor

        val pCommercial = if (commSqm > 0.0) maxOf(commSqm * 0.10, 3.45) else 0.0
        val pGarage = if (garageSqm > 0.0) maxOf(garageSqm * 0.02, 3.45) else 0.0

        val totalBuildingKw = pDwellingsCombined + pCommercial + pGarage + servicesKw
        foreCalculatedPowerKw = totalBuildingKw

        val amps = (totalBuildingKw * 1000.0) / (Math.sqrt(3.0) * 400.0 * 0.9)
        foreRecommendedIgaAmps = amps

        foreStatusMessage = "Previsión Edificio (ITC-BT-10):\n" +
                "• Viviendas (${totalDwellings} ud, Cs = ${String.format("%.1f", divFactor)}): ${String.format("%.2f", pDwellingsCombined)} kW\n" +
                "• Locales Comerciales: ${String.format("%.2f", pCommercial)} kW\n" +
                "• Garaje: ${String.format("%.2f", pGarage)} kW\n" +
                "• Servicios Comunes: ${String.format("%.2f", servicesKw)} kW\n" +
                "• Potencia Total: ${String.format("%.2f", totalBuildingKw)} kW (${String.format("%.1f", amps)} A)"
    }

    fun runTubeDiameterCalculation() {
        val s = tubesConductorSec.toDoubleOrNull() ?: 2.5
        val n = tubesConductorsCount.toIntOrNull() ?: 3

        val diameter = when {
            s <= 1.5 -> if (n <= 3) 16 else if (n <= 5) 20 else 25
            s <= 2.5 -> if (n <= 3) 20 else if (n <= 5) 20 else 25
            s <= 4.0 -> if (n <= 3) 20 else if (n <= 5) 25 else 32
            s <= 6.0 -> if (n <= 3) 25 else if (n <= 5) 32 else 40
            s <= 10.0 -> if (n <= 3) 32 else 40
            s <= 16.0 -> if (n <= 3) 32 else 40
            else -> 50
        }

        tubesCalculatedDiameterMm = diameter
        tubesStatusMessage = "ITC-BT-21: Para $n conductores de $s mm² empotrados, el diámetro exterior mínimo reglamentario es de $diameter mm."
    }

    fun runGroundingCalculation() {
        val rho = earthSoilResistivity.toDoubleOrNull() ?: 100.0
        val picas = earthPicasCount.toIntOrNull() ?: 2
        val length = earthPicaLengthM.toDoubleOrNull() ?: 2.0

        // Single vertical rod resistance formula: R = rho / L
        val rSingle = rho / length
        // Parallel rods with mutual resistance factor ~ 1.2
        val rTotal = if (picas > 1) (rSingle / picas) * 1.15 else rSingle

        earthCalculatedResistanceOhms = rTotal
        val isSafe50V = (rTotal * 0.030) <= 50.0
        val isSafe24V = (rTotal * 0.030) <= 24.0

        earthStatusMessage = "ITC-BT-18: Resistencia de Tierra Estimada = ${String.format("%.2f", rTotal)} Ω.\n" +
                "• Locales Secos (Límite 50V con ID 30mA): ${if (isSafe50V) "CUMPLE (≤ 1.666 Ω)" else "NO CUMPLE"}\n" +
                "• Locales Húmedos (Límite 24V con ID 30mA): ${if (isSafe24V) "CUMPLE (≤ 800 Ω)" else "NO CUMPLE"}"
    }

    fun changeSubscriberEmail(email: String) {
        currentUserEmail = email
        inputEmailString = email
    }

    // -------------------------------------------------------------
    // SUBSCRIPTION & GOOGLE PLAY BILLING ACTIONS
    // -------------------------------------------------------------

    val formattedPrices: StateFlow<Map<String, String>> = billingManager.formattedPrices

    val isPremium: Boolean
        get() = subscriptionFlow.value?.isActive == true

    fun purchaseSubscription(activity: android.app.Activity, planId: String, planKey: String) {
        val (productId, productType, basePlanId) = when (planId) {
            "plan_trimestral" -> Triple(
                billingManager.PRO_QUARTERLY_PRODUCT_ID,
                com.android.billingclient.api.BillingClient.ProductType.SUBS,
                billingManager.BASE_PLAN_QUARTERLY
            )
            "plan_mensual" -> Triple(
                billingManager.PRO_MONTHLY_PRODUCT_ID,
                com.android.billingclient.api.BillingClient.ProductType.SUBS,
                billingManager.BASE_PLAN_MONTHLY
            )
            "plan_vitalicio" -> Triple(
                billingManager.PREMIUM_LIFETIME_PRODUCT_ID,
                com.android.billingclient.api.BillingClient.ProductType.INAPP,
                null
            )
            else -> Triple(
                billingManager.PRO_QUARTERLY_PRODUCT_ID,
                com.android.billingclient.api.BillingClient.ProductType.SUBS,
                billingManager.BASE_PLAN_QUARTERLY
            )
        }
        billingManager.launchBillingFlow(activity, productId, productType, basePlanId)
    }

    fun activatePlanDirect(plan: String, price: Double) {
        // P0-4 FIX: activatePlanDirect is strictly restricted to DEBUG sandbox builds
        if (!BuildConfig.DEBUG) return
        viewModelScope.launch {
            repository.activatePremiumSubscription(plan, price)
        }
    }

    fun restoreSubscription(onResult: ((Boolean) -> Unit)? = null) {
        billingManager.queryActivePurchases(onResult)
    }

    fun cancelSubscriptionDev() {
        if (!BuildConfig.DEBUG) return
        viewModelScope.launch {
            repository.restoreOrCancelSubscription()
        }
    }

    fun clearExamHistory() {
        viewModelScope.launch {
            repository.clearExamHistory()
        }
    }

    // -------------------------------------------------------------
    // REMINDER SYSTEM ACTIONS
    // -------------------------------------------------------------

    fun openCreateReminderDialog(defaultArticle: String = "ITC-BT-05") {
        editingReminder = null
        reminderInputTitle = ""
        reminderInputDescription = ""
        reminderInputArticle = defaultArticle
        reminderInputDueDate = System.currentTimeMillis() + (24 * 3600 * 1000L) // Default tomorrow
        reminderInputPeriodicity = "Puntual"
        reminderInputPriority = "Media"
        reminderInputNotify = true
        showReminderDialog = true
    }

    fun openEditReminderDialog(reminder: ReminderEntity) {
        editingReminder = reminder
        reminderInputTitle = reminder.title
        reminderInputDescription = reminder.description
        reminderInputArticle = reminder.rebtArticle
        reminderInputDueDate = reminder.dueDate
        reminderInputPeriodicity = reminder.periodicity
        reminderInputPriority = reminder.priority
        reminderInputNotify = reminder.notifyEnabled
        showReminderDialog = true
    }

    fun saveReminder() {
        if (reminderInputTitle.isBlank()) return

        val currentEditing = editingReminder
        val initialStatus = if (reminderInputDueDate < System.currentTimeMillis()) "Vencido" else "Pendiente"

        viewModelScope.launch {
            if (currentEditing != null) {
                val updated = currentEditing.copy(
                    title = reminderInputTitle.trim(),
                    description = reminderInputDescription.trim(),
                    rebtArticle = reminderInputArticle.trim(),
                    dueDate = reminderInputDueDate,
                    periodicity = reminderInputPeriodicity,
                    priority = reminderInputPriority,
                    status = if (currentEditing.status == "Completado") "Completado" else initialStatus,
                    notifyEnabled = reminderInputNotify
                )
                repository.updateReminder(updated)
            } else {
                val newReminder = ReminderEntity(
                    title = reminderInputTitle.trim(),
                    description = reminderInputDescription.trim(),
                    rebtArticle = reminderInputArticle.trim(),
                    dueDate = reminderInputDueDate,
                    periodicity = reminderInputPeriodicity,
                    priority = reminderInputPriority,
                    status = initialStatus,
                    notifyEnabled = reminderInputNotify
                )
                repository.insertReminder(newReminder)
            }
            showReminderDialog = false
            editingReminder = null
        }
    }

    fun toggleReminderStatus(reminder: ReminderEntity) {
        viewModelScope.launch {
            repository.toggleReminderStatus(reminder)
        }
    }

    fun deleteReminder(id: Int) {
        viewModelScope.launch {
            repository.deleteReminder(id)
            showDeleteConfirmDialog = null
        }
    }

    fun seedDefaultReminders() {
        viewModelScope.launch {
            repository.seedDefaultRemindersIfEmpty()
        }
    }

    fun sendTestNotification(title: String, description: String, article: String) {
        ReminderNotificationManager.sendTestNotification(
            getApplication(),
            title = title,
            description = description,
            article = article
        )
    }

    fun recordItcStudy(itcCode: String) {
        viewModelScope.launch {
            repository.recordItcStudy(itcCode)
        }
    }

    fun updateDailyStudyReminderSettings(enabled: Boolean, hour: Int, minute: Int) {
        viewModelScope.launch {
            repository.updateDailyStudyReminderSettings(enabled, hour, minute)
        }
    }

    fun sendTestDailyStudyNotification() {
        DailyStudyNotificationManager.sendTestDailyStudyNotification(getApplication())
    }

    // -------------------------------------------------------------
    // POST-ITS & NOTAS TÉCNICAS REBT
    // -------------------------------------------------------------

    fun addPostIt(content: String, category: String = "General", color: String = "#FFEAA7") {
        if (content.isBlank()) return
        viewModelScope.launch {
            repository.insertPostIt(content.trim(), category, color)
        }
    }

    fun deletePostIt(id: Int) {
        viewModelScope.launch {
            repository.deletePostIt(id)
        }
    }

    fun seedDefaultPostItsIfEmpty(forceAddMissing: Boolean = false, onlyBasic: Boolean = false) {
        viewModelScope.launch {
            val list = repository.postItsFlow.first()
            val defaults = listOf(
                Triple(
                    "Fórmula Caída Tensión Monofásica:\ne = 2 · L · P / (γ · S · V)\n• Cobre en caliente: γ = 44 m/(Ω·mm²)\n• Aluminio en caliente: γ = 28 m/(Ω·mm²)\n• LGA contadores centralizados: máx 0,5%\n• DI contadores centralizados: máx 1,5%",
                    "Fórmulas",
                    "#FFEAA7"
                ),
                Triple(
                    "Fórmula Potencia y Caída Trifásica:\n• P = √3 · V · I · cos φ\n• I = P / (√3 · V · cos φ) (a 400 V con cos φ=0,85 -> I ≈ 1,7 · P[kW])\n• Caída de tensión trifásica: e = (L · P) / (γ · S · V)\n• %e = (100 · L · P) / (γ · S · V²)\n¡Ojo examen!: Para igual potencia transmitida y sección, la caída porcentual en trifásica 400 V es la mitad que en monofásica 230 V.",
                    "Fórmulas",
                    "#FFEAA7"
                ),
                Triple(
                    "Límites de Tensión de Contacto:\n• Locales secos ordinarios: 50 V\n• Locales húmedos o mojados: 24 V\n• Piscinas e inmersión: 12 V\nFórmula de seguridad: Ra · IΔn ≤ Ul",
                    "Seguridad",
                    "#D4EDDA"
                ),
                Triple(
                    "Puesta a Tierra (ITC-BT-18):\n• Conductor Cu desnudo enterrado: mín. 35 mm²\n• Conductor Cu aislado enterrado: mín. 16 mm²\n• Pica vertical: longitud estándar 2,0 m enterrada a ≥ 0,50 m.",
                    "Artículos",
                    "#CCE5FF"
                ),
                Triple(
                    "Circuitos Interiores Vivienda (ITC-25):\n• C1: Alumbrado (10 A - 1,5 mm²)\n• C2: Enchufes generales (16 A - 2,5 mm²)\n• C3: Cocina y Horno (25 A - 6 mm²)\n• C4: Lavadora, lavavajillas, termo (20 A - 4 mm²)\n• C5: Tomas baño y auxiliares cocina (16 A - 2,5 mm²)",
                    "Examen",
                    "#FFF3CD"
                ),
                Triple(
                    "Regla de Oro en Sobrecargas (ITC-22):\nIB ≤ In ≤ Iz  y  I2 ≤ 1,45 · Iz\n¡Trampa habitual!: El calibre nominal In del magnetotérmico NUNCA puede ser superior a la intensidad admisible Iz del cable que protege.",
                    "Trucos",
                    "#F8D7DA"
                ),
                Triple(
                    "Inspecciones Periódicas OCA (ITC-05):\n• Cada 5 años: Locales de pública concurrencia, garajes >25 plazas, locales ATEX.\n• Cada 10 años: Zonas comunes edificios de viviendas con potencia total > 100 kW.",
                    "Artículos",
                    "#E2E3E5"
                ),
                Triple(
                    "Previsión de Cargas en Edificios (ITC-10):\n• Grado Básico: 5.750 W (IGA 32 A a 230 V)\n• Grado Elevado: 9.200 W (IGA 40 A a 230 V)\n• Locales comerciales: mín. 100 W/m² (mín. 3.450 W)\n• Garajes ventilación forzada: 20 W/m² (mín. 3.450 W)\n• Coeficiente simultaneidad viviendas: según tabla par. 3",
                    "Examen",
                    "#FFEAA7"
                ),
                Triple(
                    "Simultaneidad Edificios Residenciales (ITC-10):\n• 1 vivienda: Coeficiente = 1 (P = P1)\n• 2 a 4 viviendas: P = Σ P1 (sin simultaneidad, factor = 1)\n• n > 4 viviendas: P_total = P_media · [1 + (n - 1) · 0,153]\n(¡Fórmula imprescindible para cálculo de LGA en examen oficial!)",
                    "Trucos",
                    "#FFF3CD"
                ),
                Triple(
                    "Caídas de Tensión Máximas (ITC-14, 15, 19):\n• LGA (Línea General de Alimentación): máx 0,5% (1,0% para contadores en varias plantas)\n• DI (Derivación Individual): máx 1,5% (1,0% si contadores en plantas)\n• Alumbrado interior vivienda: máx 3%\n• Fuerza y otros usos interior: máx 5%",
                    "Fórmulas",
                    "#D4EDDA"
                ),
                Triple(
                    "Volúmenes en Baños y Duchas (ITC-27):\n• Vol 0 (interior bañera): IPX7, solo MBTS 12 V\n• Vol 1 (hasta 2,25 m vertical): IPX4, calentador fijo o MBTS 12 V\n• Vol 2 (0,60 m alrededor de vol 1): IPX4, luminarias Clase II\n• Vol 3 (2,40 m desde vol 2): IPX1, bases con diferencial 30 mA",
                    "Seguridad",
                    "#CCE5FF"
                ),
                Triple(
                    "Tubos Empotrados en Tabiques (ITC-21):\n• 3 x 1,5 mm²: Tubo exterior Ø 16 mm\n• 3 x 2,5 mm²: Tubo exterior Ø 20 mm\n• 3 x 4 mm²: Tubo exterior Ø 20 mm\n• 3 x 6 mm²: Tubo exterior Ø 25 mm\n• 3 x 10 mm²: Tubo exterior Ø 32 mm\n• Radio curvatura mín: 6 veces diámetro exterior",
                    "Trucos",
                    "#FFF3CD"
                ),
                Triple(
                    "Receptores Motores y Alumbrado (ITC-44 y 46):\n• Conductor motor único: I_calculo = 1,25 · I_nominal\n• Conductor varios motores: 1,25 · In(mayor) + Σ In(restantes)\n• Lámparas de descarga/fluorescencia: Potencia cálculo = 1,8 · P_nominal (balastros y armónicos)",
                    "Fórmulas",
                    "#F8D7DA"
                ),
                Triple(
                    "Cuadro de Mando y Protecciones (ITC-17):\n• Altura de mandos: entre 1,40 m y 2,00 m del suelo (1,00 - 1,40 m accesibilidad).\n• IGA: Omnipolar, mín. 25 A (habitual 32 A en básico / 40 A en elevado), poder de corte mín. 4,5 kA.\n• ID (Diferencial): Mínimo 1 por cada 5 circuitos instalados.",
                    "Artículos",
                    "#CCE5FF"
                ),
                Triple(
                    "Alumbrado de Emergencia (ITC-28):\n• Autonomía mínima: 1 hora.\n• Evacuación: mín. 1 lux en eje central de pasillos; 5 lux en cuadros y botiquín.\n• Antipánico: mín. 0,5 lux en todo el recinto.\n• Tiempo de encendido: 50% de lux en 5 s; 100% en 60 s.",
                    "Seguridad",
                    "#D4EDDA"
                ),
                Triple(
                    "Redes Subterráneas de Distribución (ITC-07):\n• Profundidad zanja: mín. 0,60 m (acera) y mín. 0,80 m (calzada).\n• Cinta señalizadora: a 20 cm por encima del tubo o conductor.\n• Cruzamientos con gas o agua: distancia mínima 0,20 m.\n• Resistencia compresión tubos enterrados: 450 N (código 450).",
                    "Trucos",
                    "#FFF3CD"
                ),
                Triple(
                    "Códigos de Protección IP e IK:\n• IP 1ª cifra (sólidos 0-6): IP2X (dedos ≥12,5 mm), IP4X (alambres ≥1 mm), IP5X (polvo), IP6X (estanco al polvo).\n• IP 2ª cifra (líquidos 0-8): IPX4 (salpicaduras), IPX5 (chorro), IPX7 (inmersión 1 m), IPX8 (inmersión continua).\n• IK (impacto 00-10): IK08 = resistencia 5 julios (habitual envolventes exteriores).",
                    "Seguridad",
                    "#F8D7DA"
                ),
                Triple(
                    "Locales de Pública Concurrencia (ITC-28):\n• Conductores obligatorios: No propagadores del incendio y de emisión reducida de humos y opacidad (libres de halógenos, tipo H07Z1-K o RZ1-K).\n• Suministro complementario: Socorro (mín. 15% potencia contratada) o Reserva (mín. 25%).\n• Obligatorio proyecto y dirección de obra por técnico titulado.",
                    "Examen",
                    "#E2E3E5"
                ),
                Triple(
                    "Infraestructura Vehículo Eléctrico (ITC-52):\n• Modo 1: Enchufe doméstico sin control (prohibido en vía pública).\n• Modo 2: Cable con caja de control piloto integrada (ICCB).\n• Modo 3: Wallbox con control piloto dedicado (estándar preferente).\n• Modo 4: Corriente continua de alta potencia.\n• Protección: Diferencial exclusivo por punto (Tipo A con detección CC 6 mA o Tipo B) y protección contra sobretensiones transitorias y permanentes.",
                    "Examen",
                    "#FFEAA7"
                )
            )

            val targetDefaults = if (onlyBasic) defaults.take(10) else defaults

            if (list.isEmpty()) {
                for ((content, category, color) in targetDefaults) {
                    repository.insertPostIt(content, category, color)
                }
            } else if (forceAddMissing) {
                val existingContents = list.map { it.content.trim().take(30) }.toSet()
                for ((content, category, color) in targetDefaults) {
                    if (existingContents.none { content.startsWith(it) }) {
                        repository.insertPostIt(content, category, color)
                    }
                }
            }
        }
    }
}

