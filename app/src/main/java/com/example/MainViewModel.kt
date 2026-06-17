package com.example

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = EnigmaRepository(application)
    val billingManager = BillingManager(application, repository, viewModelScope)

    // Data streams from model
    val progressFlow: StateFlow<List<ModuleProgressEntity>> = repository.progressFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val examHistoryFlow: StateFlow<List<ExamRecordEntity>> = repository.examsHistoryFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val dailyActivityFlow: StateFlow<DailyActivityEntity?> = repository.dailyActivityFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val subscriptionFlow: StateFlow<SubscriptionRecordEntity?> = repository.subscriptionFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val supportRequestsFlow: StateFlow<List<SupportRequestEntity>> = repository.supportRequestsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val postItsFlow: StateFlow<List<PostItEntity>> = repository.postItsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // UI interactive states
    var activeTab by mutableStateOf("dashboard") // "dashboard", "book", "exams", "simulator", "laboratory", "documents", "news", "support", "billing"
    var showFlashIntro by mutableStateOf(true)
    var isDarkTheme by mutableStateOf(true)
    
    // Syllabus Search Filters
    var activeSearchQuery by mutableStateOf("")
    var activeCategoryFilter by mutableStateOf("Todos")

    // Dynamic Exam Flow states
    var activeExamModule by mutableStateOf<ModuleDefinition?>(null)
    var currentQuestionIndex by mutableStateOf(0)
    var selectedOptionIndex by mutableStateOf<Int?>(null)
    var currentQuestionAnswered by mutableStateOf(false)
    var isAnswerCorrect by mutableStateOf<Boolean?>(null)
    var examCorrectCount by mutableStateOf(0)
    var examCompleted by mutableStateOf(false)

    // Sizing electrical lab states
    var labIsThreePhase by mutableStateOf(false)
    var labCableMaterial by mutableStateOf("cobre") // "cobre" or "aluminio"
    var labInstallMethod by mutableStateOf("tubo") // "tubo" or "aire"
    var labPowerKw by mutableStateOf("5.75")
    var labLengthM by mutableStateOf("25")
    var labMaxDropPct by mutableStateOf("1.5")
    var labCalculatedSection by mutableStateOf(1.5)
    var labIzCapacity by mutableStateOf(16.0)
    var labStatusMessage by mutableStateOf("Introduce parámetros para calcular la sección reglamentaria.")

    // Multifunction Simulator States
    var selectedTesterType by mutableStateOf("RCD") // "RCD", "EarthLoop", "Insulation"
    var simLimitAmp by mutableStateOf("30") // "30" mA, "300" mA
    var simPhaseAngle by mutableStateOf("0") // "0" deg, "180" deg
    var simRcdTripTimeMs by mutableStateOf(0)
    var simRcdStatus by mutableStateOf("Listo para simular")
    
    var simEarthSoilType by mutableStateOf("Tierra vegetal") // "Tierra vegetal", "Arcilla", "Grava"
    var simEarthPicaCount by mutableStateOf(1)
    var simEarthCalculatedResistance by mutableStateOf(0.0)
    var simEarthStatus by mutableStateOf("Calcula la resistencia de bucle")

    var simInsulationVoltage by mutableStateOf("500") // "500" V, "1000" V
    var simInsulationReadMegaohms by mutableStateOf(0.0)
    var simInsulationStatus by mutableStateOf("Listo para prueba de rigidez dieléctrica")

    // Checkout Billing Dialog states
    var showCheckoutPlaySheet by mutableStateOf(false)
    var checkoutPlanType by mutableStateOf("pro") // "pro", "premium"
    var checkoutPrice by mutableStateOf(4.99)

    init {
        // Query active purchases from Google Play on startup
        billingManager.queryActivePurchases()
        // Run laboratory default computation
        runLaboratoryCalculation()
        // Pre-populate post-it notes if empty
        viewModelScope.launch {
            try {
                val currentPostIts = repository.postItsFlow.first()
                if (currentPostIts.isEmpty()) {
                    repository.insertPostIt(
                        content = "Tensiones límites de Baja Tensión (REBT):\n• Corriente Alterna: ≤ 1.000 V eficaces\n• Corriente Continua: ≤ 1.500 V",
                        category = "Artículo",
                        color = "#FFEAA7" // classic yellow
                    )
                    repository.insertPostIt(
                        content = "Suministros especiales obligatorios (mínimos):\n• Socorro: ≥ 15% potencia total contratada\n• Reserva: ≥ 25% potencia total\n• Duplicado: ≥ 50% potencia total",
                        category = "Fórmula",
                        color = "#FFD2D2" // light red/coral
                    )
                    repository.insertPostIt(
                        content = "Masa metálica:\nEs aquella parte accesible que normalmente no está en tensión, pero sí puede estarlo si falla el primer aislamiento.",
                        category = "Consejo",
                        color = "#D2F4FF" // light blue
                    )
                    repository.insertPostIt(
                        content = "Bucle de tierra (ITC-18):\n• Locales húmedos/mojados: R ≤ 24 V / I_d\n• Locales secos comunes: R ≤ 50 V / I_d",
                        category = "Fórmula",
                        color = "#E2FCD4" // light green
                    )
                    repository.insertPostIt(
                        content = "Recarga de Vehículos Eléctricos (ITC-BT-52):\nToda infraestructura de recarga exige que el instalador habilitado de la obra sea de categoría Especialista (IBTE).",
                        category = "Examen",
                        color = "#F0E4FF" // light violet/purple
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addPostIt(content: String, category: String, color: String) {
        viewModelScope.launch {
            repository.insertPostIt(content, category, color)
        }
    }

    fun deletePostIt(id: Int) {
        viewModelScope.launch {
            repository.deletePostIt(id)
        }
    }

    // Starts an exam
    fun startExam(module: ModuleDefinition) {
        activeExamModule = module
        currentQuestionIndex = 0
        selectedOptionIndex = null
        currentQuestionAnswered = false
        isAnswerCorrect = null
        examCorrectCount = 0
        examCompleted = false
        activeTab = "exams" // navigate to exams screen
    }

    // Answers active question
    fun submitAnswer() {
        val module = activeExamModule ?: return
        val currentQuestionList = module.questions
        val question = currentQuestionList.getOrNull(currentQuestionIndex) ?: return
        val selected = selectedOptionIndex ?: return

        currentQuestionAnswered = true
        val correct = selected == question.a
        isAnswerCorrect = correct
        if (correct) {
            examCorrectCount++
        }
    }

    // Next question or completes exam
    fun nextQuestion() {
        val module = activeExamModule ?: return
        val currentQuestionList = module.questions
        if (currentQuestionIndex + 1 < currentQuestionList.size) {
            currentQuestionIndex++
            selectedOptionIndex = null
            currentQuestionAnswered = false
            isAnswerCorrect = null
        } else {
            // Complete exam
            examCompleted = true
            viewModelScope.launch {
                repository.recordExam(
                    moduleId = module.id,
                    correct = examCorrectCount,
                    total = currentQuestionList.size
                )
            }
        }
    }

    // Sizing calculations inside the technical laboratory
    fun runLaboratoryCalculation() {
        viewModelScope.launch {
            repository.useCalculator() // track count
        }

        val power = labPowerKw.toDoubleOrNull() ?: 5.75
        val length = labLengthM.toDoubleOrNull() ?: 25.0
        val maxDrop = labMaxDropPct.toDoubleOrNull() ?: 1.5

        val u = if (labIsThreePhase) 400.0 else 230.0
        val deltaU = (maxDrop / 100.0) * u
        val c = if (labCableMaterial == "cobre") 44.0 else 28.0
        val pWatts = power * 1000.0

        val sCalculatedValue = if (labIsThreePhase) {
            (pWatts * length) / (c * deltaU * u)
        } else {
            (2.0 * pWatts * length) / (c * deltaU * u)
        }

        val standardSectionsList = listOf(1.5, 2.5, 4.0, 6.0, 10.0, 16.0, 25.0, 35.0, 50.0, 70.0, 95.0)
        var selectedSec = standardSectionsList.first()
        for (sec in standardSectionsList) {
            if (sec >= sCalculatedValue) {
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
            val idx = standardSectionsList.indexOf(finalSec)
            if (idx == -1 || idx == standardSectionsList.lastIndex) break
            finalSec = standardSectionsList[idx + 1]
            finalIz = ampCapacityTable[finalSec] ?: (finalSec * 3.0)
        }

        labCalculatedSection = finalSec
        labIzCapacity = finalIz

        val formattedAmps = String.format("%.2f", currentAmps)
        val formattedDrop = String.format("%.2f", sCalculatedValue)
        val phaseLabel = if (labIsThreePhase) "Trifásica" else "Monofásica"
        val materialLabel = if (labCableMaterial == "cobre") "Cobre" else "Aluminio"
        val installLabel = if (labInstallMethod == "tubo") "Empotrado en tubo" else "Al aire libre"
        
        labStatusMessage = "Cálculo Realizado ($materialLabel - $installLabel):\n" +
                "Para acometida $phaseLabel de $power kW ($formattedAmps A de servicio), se regula una sección comercial de $finalSec mm².\n" +
                "La sección teórica exacta calculada por caída de tensión de $maxDrop% es de $formattedDrop mm². " +
                "La corriente máxima admisible térmica nominal Iz de la sección es de $finalIz A."
    }

    fun triggerExploreSchema() {
        viewModelScope.launch {
            repository.exploreSchema()
        }
    }

    // Runs simulation tests
    fun triggerSimulatorTest() {
        viewModelScope.launch {
            repository.exploreSchema()
        }

        when (selectedTesterType) {
            "RCD" -> {
                val currentMa = simLimitAmp.toIntOrNull() ?: 30
                val tripTime = (14..45).random()
                simRcdTripTimeMs = tripTime
                simRcdStatus = "PRUEBA RCD COMPLETADA:\nMagnetotérmico Diferencial saltó en $tripTime ms al inyectar corriente de fuga de ${currentMa}mA en ángulo de fase de $simPhaseAngle°.\n" +
                        "La bobina electromecánica del diferencial operó correctamente dentro del tiempo límite exigido (<200 ms según ITC-BT-17/25)."
            }
            "EarthLoop" -> {
                val resistivityMultiplier = when (simEarthSoilType) {
                    "Tierra vegetal" -> 50
                    "Arcilla" -> 30
                    "Grava" -> 300
                    else -> 100
                }
                val resistance = (resistivityMultiplier.toDouble() / simEarthPicaCount) + (1..4).random()
                simEarthCalculatedResistance = resistance
                val pass = resistance <= 30.0
                simEarthStatus = "RESISTENCIA DE BUCLE DE TIERRA: ${String.format("%.2f", resistance)} Ω.\n" +
                        (if (pass) "Apto. El electrodo cumple la norma (menor de 30 Ω según ITC-BT-18 para alumbrado exterior e interiores)." else "Fuera de reglamento. Resistencia superior a 30 Ω. Añada más picas en paralelo o aplique mejorante electrolítico al terreno.")
            }
            "Insulation" -> {
                val testV = simInsulationVoltage.toDoubleOrNull() ?: 500.0
                val minR = if (testV > 500.0) 1.0 else 0.5
                val measured = minR + (0.2..18.0).random()
                simInsulationReadMegaohms = measured
                simInsulationStatus = "ENSAYO DE RIGIDEZ DIELÉCTRICA COMPLETO:\nTensión continua inyectada: $testV VCC.\nResistencia óhmica obtenida: ${String.format("%.2f", measured)} MΩ.\n" +
                        "Aprobado (El mínimo exigido para redes de 450/750V es de $minR MΩ)."
            }
        }
    }

    // Play Billing functions
    fun loadCheckoutSheet(plan: String, price: Double) {
        checkoutPlanType = plan
        checkoutPrice = price
        showCheckoutPlaySheet = true
    }

    fun completeGooglePlayPurchase(activity: android.app.Activity) {
        val productId = if (checkoutPlanType == "pro") billingManager.PRO_MONTHLY_PRODUCT_ID else billingManager.PREMIUM_LIFETIME_PRODUCT_ID
        val productType = if (checkoutPlanType == "pro") com.android.billingclient.api.BillingClient.ProductType.SUBS else com.android.billingclient.api.BillingClient.ProductType.INAPP
        billingManager.launchBillingFlow(activity, productId, productType)
        showCheckoutPlaySheet = false
    }

    fun cancelOrDowngradeSubscription() {
        viewModelScope.launch {
            repository.restoreOrCancelSubscription()
        }
    }

    // Support trigger
    fun sendSupportTicket(name: String, email: String, subject: String, msg: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            repository.createSupportRequest(name, email, subject, msg)
            onSuccess()
        }
    }
}

// Random helper extension for Double ranges
private fun ClosedRange<Double>.random(): Double {
    return java.util.concurrent.ThreadLocalRandom.current().nextDouble(start, endInclusive)
}

// Random helper extension for Int ranges
private fun ClosedRange<Int>.random(): Int {
    return java.util.concurrent.ThreadLocalRandom.current().nextInt(start, endInclusive + 1)
}
