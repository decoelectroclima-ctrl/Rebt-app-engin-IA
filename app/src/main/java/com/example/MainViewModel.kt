package com.example

import android.app.Application
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
    var labInstallMethod by mutableStateOf("tubo") // "tubo" or "aire"
    var labPowerKw by mutableStateOf("5.75")
    var labLengthM by mutableStateOf("25")
    var labMaxDropPct by mutableStateOf("1.5")
    var labCalculatedSection by mutableStateOf(1.5)
    var labIzCapacity by mutableStateOf(16.0)
    var labStatusMessage by mutableStateOf("")

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
        val allQuestions = Content.QUESTIONS.values.flatMap { it.questions }.distinctBy { it.q }

        // Pool de Articulado
        val articuladoPool = allQuestions.filter { it.isArticulado() && it.itcNumber() == null }.shuffled().toMutableList()
        if (articuladoPool.size < ExamConfig.OFFICIAL_ARTICULADO_COUNT) {
            val extraArt = allQuestions.filter { it.isArticulado() && it !in articuladoPool }.shuffled()
            articuladoPool.addAll(extraArt)
        }

        // Pool de ITCs del ámbito Básica (excluyendo especialista: 6, 7, 38, 51)
        val itcPool = allQuestions.filter { q ->
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
            val fallbackPool = allQuestions.filter { q ->
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

        return selected.shuffled()
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
        val moduleDef = Content.QUESTIONS[moduleKey] ?: return
        activeExamMode = ExamMode.TOPIC_PRACTICE

        val practiceModule = ModuleDefinition(
            id = "practica_${moduleDef.id}_${System.currentTimeMillis()}",
            label = "Repaso: ${moduleDef.label}",
            icon = moduleDef.icon,
            color = moduleDef.color,
            questions = moduleDef.questions.shuffled().take(ExamConfig.ITC_BLOCK_QUESTIONS)
        )

        setupExamSession(practiceModule, durationSeconds = ExamConfig.ITC_BLOCK_MINUTES * 60)
    }

    /**
     * Inicia un test específico por ITC (bloque de 20 preguntas, 60 minutos)
     */
    fun startItcPractice(itcNumber: Int, itcTitle: String) {
        activeExamMode = ExamMode.TOPIC_PRACTICE
        val allQuestions = Content.QUESTIONS.values.flatMap { it.questions }.distinctBy { it.q }
        val itcQuestions = allQuestions.filter { it.itcNumber() == itcNumber }.shuffled().take(ExamConfig.ITC_BLOCK_QUESTIONS)
        if (itcQuestions.isEmpty()) return

        val itcCodeFormatted = String.format("ITC-BT-%02d", itcNumber)
        val module = ModuleDefinition(
            id = "itc_${String.format("%02d", itcNumber)}_${System.currentTimeMillis()}",
            label = "Test $itcCodeFormatted: $itcTitle",
            icon = "📋",
            color = if (itcNumber in ExamConfig.SPECIALIST_ONLY_ITC) "#d29922" else "#238636",
            questions = itcQuestions
        )

        setupExamSession(module, durationSeconds = ExamConfig.ITC_BLOCK_MINUTES * 60)
    }

    /**
     * Inicia un test exclusivo del Articulado del REBT (bloque de 20 preguntas, 60 minutos)
     */
    fun startArticuladoPractice() {
        activeExamMode = ExamMode.TOPIC_PRACTICE
        val allQuestions = Content.QUESTIONS.values.flatMap { it.questions }.distinctBy { it.q }
        val artQuestions = allQuestions.filter { it.isArticulado() }.shuffled().take(ExamConfig.ITC_BLOCK_QUESTIONS)
        if (artQuestions.isEmpty()) return

        val module = ModuleDefinition(
            id = "articulado_${System.currentTimeMillis()}",
            label = "Test Articulado REBT (Art. 1-29)",
            icon = "⚡",
            color = "#bc8cff",
            questions = artQuestions
        )

        setupExamSession(module, durationSeconds = ExamConfig.ITC_BLOCK_MINUTES * 60)
    }

    /**
     * Start Review of Failed Questions (Spaced Repetition)
     * Si la pregunta ya no existe en el banco, se omite limpiamente (sin opciones ficticias).
     */
    fun startMistakesReview(reviews: List<QuestionReviewEntity>) {
        if (reviews.isEmpty()) return
        activeExamMode = ExamMode.MISTAKES_REVIEW

        val allAvailableQuestions = Content.QUESTIONS.values.flatMap { it.questions }
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

    fun submitAnswer() {
        val module = activeExamModule ?: return
        val question = module.questions.getOrNull(currentQuestionIndex) ?: return
        if (selectedOptionIndex == null) return

        currentQuestionAnswered = true
        val correct = selectedOptionIndex == question.a
        isAnswerCorrect = correct

        examAnswerRecords.add(
            ExamAnswerRecord(
                question = question,
                selectedOption = selectedOptionIndex ?: 0,
                isCorrect = correct
            )
        )

        if (correct) {
            examCorrectCount++
            if (activeExamMode == ExamMode.MISTAKES_REVIEW) {
                viewModelScope.launch {
                    val qId = "${module.id}_${question.q.hashCode()}"
                    repository.markQuestionMastered(qId)
                }
            }
        } else {
            // Record mistake for Weaknesses review / spaced repetition
            viewModelScope.launch {
                repository.recordQuestionMistake(
                    questionText = question.q,
                    moduleKey = module.id,
                    selectedOption = selectedOptionIndex ?: 0,
                    correctOption = question.a,
                    explanation = question.exp,
                    reference = question.ref
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

        val ampCapacityTable = mapOf(
            1.5 to (if (labInstallMethod == "tubo") 14.0 else 18.0),
            2.5 to (if (labInstallMethod == "tubo") 18.0 else 24.0),
            4.0 to (if (labInstallMethod == "tubo") 24.0 else 32.0),
            6.0 to (if (labInstallMethod == "tubo") 31.0 else 41.0),
            10.0 to (if (labInstallMethod == "tubo") 42.0 else 57.0),
            16.0 to (if (labInstallMethod == "tubo") 56.0 else 76.0),
            25.0 to (if (labInstallMethod == "tubo") 73.0 else 101.0),
            35.0 to (if (labInstallMethod == "tubo") 89.0 else 125.0),
            50.0 to (if (labInstallMethod == "tubo") 108.0 else 151.0)
        )

        val currentAmps = if (labIsThreePhase) {
            pWatts / (Math.sqrt(3.0) * u * 0.9)
        } else {
            pWatts / u
        }

        var finalSec = selectedSec
        var finalIz = ampCapacityTable[finalSec] ?: (finalSec * 3.0)
        while (finalIz < currentAmps && finalSec < 50.0) {
            val idx = standardSections.indexOf(finalSec)
            if (idx == -1 || idx == standardSections.lastIndex) break
            finalSec = standardSections[idx + 1]
            finalIz = ampCapacityTable[finalSec] ?: (finalSec * 3.0)
        }

        labCalculatedSection = finalSec
        labIzCapacity = finalIz

        val formattedAmps = String.format("%.2f", currentAmps)
        val formattedDrop = String.format("%.2f", sCalc)
        val phaseLabel = if (labIsThreePhase) "Trifásica 400V" else "Monofásica 230V"
        val matLabel = if (labCableMaterial == "cobre") "Cobre" else "Aluminio"

        labStatusMessage = "Sección Reglamentaria ($matLabel - $phaseLabel):\n" +
                "• Caída de tensión teórica ($maxDrop%): $formattedDrop mm²\n" +
                "• Intensidad calculada: $formattedAmps A\n" +
                "• Sección comercial elegida: $finalSec mm² (Iz = $finalIz A)"
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

    fun purchaseSubscription(activity: android.app.Activity, planType: String) {
        val (productId, productType) = when (planType) {
            "pro" -> Pair(billingManager.PRO_MONTHLY_PRODUCT_ID, com.android.billingclient.api.BillingClient.ProductType.SUBS)
            "premium" -> Pair(billingManager.PREMIUM_LIFETIME_PRODUCT_ID, com.android.billingclient.api.BillingClient.ProductType.INAPP)
            else -> Pair(billingManager.PRO_MONTHLY_PRODUCT_ID, com.android.billingclient.api.BillingClient.ProductType.SUBS)
        }
        billingManager.launchBillingFlow(activity, productId, productType)
    }

    fun activatePlanDirect(plan: String, price: Double) {
        viewModelScope.launch {
            repository.activatePremiumSubscription(plan, price)
        }
    }

    fun restoreSubscription() {
        billingManager.queryActivePurchases()
    }

    fun cancelSubscriptionDev() {
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

    fun seedDefaultPostItsIfEmpty(forceAddMissing: Boolean = false) {
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

            if (list.isEmpty()) {
                for ((content, category, color) in defaults) {
                    repository.insertPostIt(content, category, color)
                }
            } else if (forceAddMissing) {
                val existingContents = list.map { it.content.trim().take(30) }.toSet()
                for ((content, category, color) in defaults) {
                    if (existingContents.none { content.startsWith(it) }) {
                        repository.insertPostIt(content, category, color)
                    }
                }
            }
        }
    }
}

