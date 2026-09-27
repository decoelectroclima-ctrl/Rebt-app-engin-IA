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
    OFFICIAL_SIMULATION, // 80 questions, 180 min timer, official 75% threshold
    TOPIC_PRACTICE,      // 10-20 questions from a chosen module, no timer pressure
    MISTAKES_REVIEW      // Questions the user previously failed
}

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
    var examTotalSeconds by mutableStateOf(180 * 60) // 180 min default for official simulation
    var examRemainingSeconds by mutableStateOf(180 * 60)
    var isTimerRunning by mutableStateOf(false)
    private var timerJob: Job? = null

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
     * Start Official Simulation Exam (80 questions, 180 minutes)
     */
    fun startOfficialSimulation() {
        activeExamMode = ExamMode.OFFICIAL_SIMULATION
        val allQuestions = Content.QUESTIONS.values.flatMap { it.questions }.shuffled()
        val questions80 = allQuestions.take(80)

        val module = ModuleDefinition(
            id = "simulacro_oficial_${System.currentTimeMillis()}",
            label = "Simulacro Oficial REBT 2026 (80 Preguntas)",
            icon = "🏛️",
            color = "#58a6ff",
            questions = questions80
        )

        setupExamSession(module, durationSeconds = 180 * 60)
    }

    /**
     * Start Topic Practice (e.g. 10-20 questions from chosen module)
     */
    fun startTopicPractice(moduleKey: String) {
        val moduleDef = Content.QUESTIONS[moduleKey] ?: return
        activeExamMode = ExamMode.TOPIC_PRACTICE

        val practiceModule = ModuleDefinition(
            id = "practica_${moduleDef.id}_${System.currentTimeMillis()}",
            label = "Repaso: ${moduleDef.label}",
            icon = moduleDef.icon,
            color = moduleDef.color,
            questions = moduleDef.questions.shuffled()
        )

        // No strict countdown for practice, e.g. 45 min relaxed
        setupExamSession(practiceModule, durationSeconds = 45 * 60)
    }

    /**
     * Start Review of Failed Questions (Spaced Repetition)
     */
    fun startMistakesReview(reviews: List<QuestionReviewEntity>) {
        if (reviews.isEmpty()) return
        activeExamMode = ExamMode.MISTAKES_REVIEW

        val allAvailableQuestions = Content.QUESTIONS.values.flatMap { it.questions }
        val mistakeQuestions = reviews.mapNotNull { rev ->
            allAvailableQuestions.find { it.q == rev.questionText } ?: Question(
                q = rev.questionText,
                opts = listOf("Opción revisada A", "Opción revisada B", "Opción revisada C", "Opción revisada D"),
                a = rev.correctOption,
                exp = rev.explanation,
                ref = rev.reference
            )
        }

        val reviewModule = ModuleDefinition(
            id = "revision_fallos_${System.currentTimeMillis()}",
            label = "Repaso de Preguntas Falladas (${mistakeQuestions.size})",
            icon = "🎯",
            color = "#f85149",
            questions = mistakeQuestions
        )

        setupExamSession(reviewModule, durationSeconds = 60 * 60)
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
}

