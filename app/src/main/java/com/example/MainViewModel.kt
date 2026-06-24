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

import androidx.compose.runtime.mutableStateListOf

class AdminAd(val id: Int, sponsor: String, message: String, ctaText: String, tintColor: String, targetUrl: String = "", imageUrl: String = "") {
    var sponsor by mutableStateOf(sponsor)
    var message by mutableStateOf(message)
    var ctaText by mutableStateOf(ctaText)
    var tintColor by mutableStateOf(tintColor)
    var targetUrl by mutableStateOf(targetUrl)
    var imageUrl by mutableStateOf(imageUrl)
}

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = EnigmaRepository(application)
    val billingManager = BillingManager(application, repository, viewModelScope)

    // --- ADMINISTRATIVE ADVERTISING CONTROLS ---
    val adminAds = mutableStateListOf(
        AdminAd(0, "Prysmian Group España", "Cables Afumex de alta seguridad libre de halógenos para CTE", "Saber Más", "#FF3FB950", "https://www.prysmiangroup.com/es", "https://images.unsplash.com/photo-1558346490-a72e53ae2d4f?w=300&q=80"),
        AdminAd(1, "Fluke España", "Comprobadores multifunción de RCD Serie 1660 Pro", "Catálogo", "#FFF1C40F", "https://www.fluke.com/es-es", "https://images.unsplash.com/photo-1517430816045-df4b7de11d1d?w=300&q=80"),
        AdminAd(2, "Schneider Electric", "Protecciones diferenciales superinmunizadas Tipo A e inteligentes", "Comprar", "#FF58A6FF", "https://www.se.com/es/es/", "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=300&q=80"),
        AdminAd(3, "Circutor Soluciones", "Analizadores de red y recarga inteligente integrada EV", "Detalles", "#FFBC8CFF", "https://circutor.com/es/", ""),
        AdminAd(4, "Solera Envolventes", "Envolventes plásticas listas para ICT y REBT-2026 oficial", "Ver Línea", "#FFEE5F5F", "https://www.solera.es/", "")
    )

    var adsIsDynamic by mutableStateOf(true)
    var selectedStaticAdIndex by mutableStateOf(0)
    var currentAdIndex by mutableStateOf(0)
    var adminModeEnabled by mutableStateOf(false) // Toggle secret admin panel view
    var currentUserEmail by mutableStateOf("jj.terapias@gmail.com")
    var showUserEmailDialog by mutableStateOf(false)
    var inputEmailString by mutableStateOf("jj.terapias@gmail.com")

    // --- DYNAMIC SUBSCRIPTION PLAN CONTROLS ---
    // Costs (defaults: Pro 14.99€, Premium 29.99€)
    var pricePro by mutableStateOf(14.99)
    var pricePremium by mutableStateOf(29.99)

    // Sections Enabled for DEMO
    var demoDashboardEnabled by mutableStateOf(true)
    var demoBookEnabled by mutableStateOf(true)
    var demoExamsEnabled by mutableStateOf(true)
    var demoSimulatorEnabled by mutableStateOf(false) // Marketing strategy: lock advanced simulation
    var demoLaboratoryEnabled by mutableStateOf(false) // Marketing strategy: lock advanced calculators
    var demoDocumentsEnabled by mutableStateOf(false)
    var demoNewsEnabled by mutableStateOf(true)
    var demoSupportEnabled by mutableStateOf(false)

    // Sections Enabled for PRO
    var proDashboardEnabled by mutableStateOf(true)
    var proBookEnabled by mutableStateOf(true)
    var proExamsEnabled by mutableStateOf(true)
    var proSimulatorEnabled by mutableStateOf(true)
    var proLaboratoryEnabled by mutableStateOf(true)
    var proDocumentsEnabled by mutableStateOf(true)
    var proNewsEnabled by mutableStateOf(true)
    var proSupportEnabled by mutableStateOf(false) // Marketing Strategy: Gemini REBT AI Companion is Premium (or limited)

    // Sections Enabled for PREMIUM (Unhinged VIP)
    var premiumDashboardEnabled by mutableStateOf(true)
    var premiumBookEnabled by mutableStateOf(true)
    var premiumExamsEnabled by mutableStateOf(true)
    var premiumSimulatorEnabled by mutableStateOf(true)
    var premiumLaboratoryEnabled by mutableStateOf(true)
    var premiumDocumentsEnabled by mutableStateOf(true)
    var premiumNewsEnabled by mutableStateOf(true)
    var premiumSupportEnabled by mutableStateOf(true)

    // Limits
    var limitDemoExamsPerDay by mutableStateOf(1)
    var limitDemoItcPerDay by mutableStateOf(2)
    var limitProGeminiPerDay by mutableStateOf(2)

    // Trackers for the active session (saved during runtime)
    val dailyItcOpenedList = mutableStateListOf<String>()
    val dailyExamsCompletedList = mutableStateListOf<String>()
    var dailyGeminiQuestionsCount by mutableStateOf(0)

    init {
        billingManager.priceProProvider = { pricePro }
        billingManager.pricePremiumProvider = { pricePremium }
    }


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

    val customDocumentsFlow: StateFlow<List<CustomDocumentEntity>> = repository.customDocumentsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val customNewsFlow: StateFlow<List<CustomNewsEntity>> = repository.customNewsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val userLeadsFlow: StateFlow<List<UserLeadEntity>> = repository.userLeadsFlow
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

    // Express Exam state parameters
    var isExpressMode by mutableStateOf(false)
    var secondsRemaining by mutableStateOf(20)
    var timerDurationPerQuestion by mutableStateOf(20) // 15, 20, or 30 seconds
    var globalExpressModePref by mutableStateOf(false) // Toggle at the top of the tab

    // Sizing electrical lab states
    var labActiveSubTab by mutableStateOf(0) // 0: Conductores, 1: Previsión Cargas, 2: Tubos, 3: Tierra
    var labIsThreePhase by mutableStateOf(false)
    var labCableMaterial by mutableStateOf("cobre") // "cobre" or "aluminio"
    var labInstallMethod by mutableStateOf("tubo") // "tubo" or "aire"
    var labPowerKw by mutableStateOf("5.75")
    var labLengthM by mutableStateOf("25")
    var labMaxDropPct by mutableStateOf("1.5")
    var labCalculatedSection by mutableStateOf(1.5)
    var labIzCapacity by mutableStateOf(16.0)
    var labStatusMessage by mutableStateOf("Introduce parámetros para calcular la sección reglamentaria.")

    // 1. Loading Forecasting (Previsión Edificios) States
    var foreDwellingsBasic by mutableStateOf("8")
    var foreDwellingsElevated by mutableStateOf("4")
    var foreCommercialSqm by mutableStateOf("120")
    var foreOfficeSqm by mutableStateOf("0")
    var foreGarageSqm by mutableStateOf("200")
    var foreGeneralServicesKw by mutableStateOf("15.0")
    
    // 2. Tubes (ITC-BT-21) States
    var tubesConductorSec by mutableStateOf("2.5") // "1.5", "2.5", "4.0", "6.0", "10.0", "16.0", "25.0", "35.0", "50.0"
    var tubesConductorsCount by mutableStateOf("3") // "2", "3", "4", "5"
    var tubesInstallMethod by mutableStateOf("empotrado") // "empotrado", "superficial"

    // 3. Grounding (Puesta a Tierra) States
    var earthSoilResistivity by mutableStateOf("100") // 100 Ohm-m (Arcillas)
    var earthElectrodeType by mutableStateOf("pica") // "pica" or "conductor"
    var earthElectrodeLength by mutableStateOf("2.0") // meters

    // Calculation result messages for the new powered up sub-tabs
    var foreCalculatedPowerKw by mutableStateOf(0.0)
    var foreRecommendedIgaAmps by mutableStateOf(0.0)
    var foreStatusMessage by mutableStateOf("Calcula la previsión de cargas para ver conformidad.")

    var tubesCalculatedDiameterMm by mutableStateOf(20)
    var tubesStatusMessage by mutableStateOf("Calcula el diámetro del tubo protector.")

    var earthCalculatedResistanceOhms by mutableStateOf(25.0)
    var earthStatusMessage by mutableStateOf("Calcula la resistencia de tierra esperada.")

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

        // Rotating banner timer (updates current advertiser index every 5 seconds if dynamic)
        viewModelScope.launch {
            while (true) {
                kotlinx.coroutines.delay(5000)
                if (adsIsDynamic) {
                    currentAdIndex = (currentAdIndex + 1) % adminAds.size
                }
            }
        }

        // Run laboratory default computations
        runLaboratoryCalculation()
        runBuildingForecastingCalculation()
        runTubeDiameterCalculation()
        runGroundingCalculation()
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

        // Pre-populate news and documents from Content if empty
        viewModelScope.launch {
            try {
                val currentDocs = repository.customDocumentsFlow.first()
                if (currentDocs.isEmpty()) {
                    Content.DOCUMENTS.forEach { d ->
                        repository.insertCustomDocument(
                            CustomDocumentEntity(
                                docId = d.id,
                                title = d.title,
                                description = d.description,
                                fileName = d.fileName,
                                fileSize = d.fileSize,
                                type = d.type,
                                isCustom = false
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        viewModelScope.launch {
            try {
                val currentNews = repository.customNewsFlow.first()
                if (currentNews.isEmpty()) {
                    Content.NEWS.forEach { n ->
                        // Ensure it mentions REBT 2026 to be strictly focused on REBT 2026 regulation
                        val title2026 = if (!n.title.contains("2026")) "${n.title} (Nuevo REBT 2026)" else n.title
                        val summary2026 = if (!n.summary.contains("2026") && !n.summary.contains("REBT")) "${n.summary} bajo las normativas del nuevo REBT 2026." else n.summary
                        repository.insertCustomNews(
                            CustomNewsEntity(
                                newsId = n.id,
                                title = title2026,
                                summary = summary2026,
                                content = n.content + " [Actualizado conforme al borrador oficial del nuevo reglamento REBT 2026]",
                                date = n.date,
                                category = n.category,
                                categoryLabel = n.categoryLabel,
                                readTime = n.readTime,
                                hot = n.hot,
                                isCustom = false
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Pre-populate user leads database if empty
        viewModelScope.launch {
            try {
                val currentLeads = repository.userLeadsFlow.first()
                if (currentLeads.isEmpty()) {
                    repository.insertUserLead(UserLeadEntity(email = "fgomez.instalaciones@gmail.com", name = "Francisco Gómez", subscriptionPlan = "gratuito", phoneNumber = "612345678", companyName = "Instalaciones Gómez", province = "Madrid", isSold = false, leadPrice = 35.00))
                    repository.insertUserLead(UserLeadEntity(email = "m.belmonte@electrosur.es", name = "Manuel Belmonte", subscriptionPlan = "pro", phoneNumber = "622987654", companyName = "ElectroSur S.L.", province = "Sevilla", isSold = false, leadPrice = 45.00))
                    repository.insertUserLead(UserLeadEntity(email = "jribas@instalacionesribas.cat", name = "Julia Ribas", subscriptionPlan = "premium", phoneNumber = "633112233", companyName = "Ribas Eléctrica", province = "Barcelona", isSold = true, leadPrice = 60.00))
                    repository.insertUserLead(UserLeadEntity(email = "cortiz.clima@hotmail.com", name = "Carlos Ortiz", subscriptionPlan = "gratuito", phoneNumber = "644556677", companyName = "Ortiz Climatización", province = "Valencia", isSold = false, leadPrice = 35.00))
                    repository.insertUserLead(UserLeadEntity(email = "evazquez@galiciaelectro.com", name = "Elena Vázquez", subscriptionPlan = "pro", phoneNumber = "655443322", companyName = "Galicia Electro", province = "A Coruña", isSold = false, leadPrice = 40.00))
                    repository.insertUserLead(UserLeadEntity(email = "jsansegundo@rebtasociado.es", name = "Javier San Segundo", subscriptionPlan = "pro", phoneNumber = "666778899", companyName = "REBT Técnico Norte", province = "Bilbao", isSold = true, leadPrice = 45.00))
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
        
        // Respect settings or customized launch
        isExpressMode = globalExpressModePref
        if (isExpressMode) {
            secondsRemaining = timerDurationPerQuestion
        }
        
        activeTab = "exams" // navigate to exams screen
    }

    // Starts a randomized offline exam from the expanded pool of questions
    fun startRandomizedOfflineExam(title: String, size: Int, express: Boolean) {
        // Collect all questions from Content.QUESTIONS map
        val allQuestions = Content.QUESTIONS.values.flatMap { it.questions }
        val shuffled = allQuestions.sortedBy { (0..1000).random() } // Robust offline shuffle
        val selectedQuestions = shuffled.take(minOf(size, shuffled.size))

        val module = ModuleDefinition(
            id = "randomized_${System.currentTimeMillis()}",
            label = title,
            icon = if (express) "⏱️" else "📋",
            color = if (express) "#E74C3C" else "#3498DB",
            questions = selectedQuestions
        )

        // Reset global preference if we explicitly requested express for Express Exam card
        globalExpressModePref = express
        startExam(module)
    }

    // Dynamic AI-Generated Exams (Infinite Database) states and functions
    var isGeneratingAiExam by mutableStateOf(false)
    var aiExamError by mutableStateOf<String?>(null)

    fun startAiDynamicExam(topic: String) {
        viewModelScope.launch {
            isGeneratingAiExam = true
            aiExamError = null
            try {
                // Call Gemini dynamically based on topic, which also supports elegant offline fallbacks
                val questions = GeminiService.generateInfiniteExam(topic)
                if (questions.isNotEmpty()) {
                    val dynamicModule = ModuleDefinition(
                        id = "ai_generation_${System.currentTimeMillis()}",
                        label = "Examen Especial: $topic",
                        icon = "🌟",
                        color = "#FFA000",
                        questions = questions
                    )
                    startExam(dynamicModule)
                } else {
                    aiExamError = "Error al intentar generar el examen. Verifique su conexión."
                }
            } catch (e: Exception) {
                e.printStackTrace()
                aiExamError = "Error en generación: ${e.localizedMessage}"
            } finally {
                isGeneratingAiExam = false
            }
        }
    }

    // Answers active question
    fun submitAnswer(timedOut: Boolean = false) {
        val module = activeExamModule ?: return
        val currentQuestionList = module.questions
        val question = currentQuestionList.getOrNull(currentQuestionIndex) ?: return

        currentQuestionAnswered = true
        if (timedOut || selectedOptionIndex == null) {
            isAnswerCorrect = false
        } else {
            val correct = selectedOptionIndex == question.a
            isAnswerCorrect = correct
            if (correct) {
                examCorrectCount++
            }
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
            if (isExpressMode) {
                secondsRemaining = timerDurationPerQuestion
            }
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

    fun runBuildingForecastingCalculation() {
        val basic = foreDwellingsBasic.toIntOrNull() ?: 0
        val elevated = foreDwellingsElevated.toIntOrNull() ?: 0
        val commSqm = foreCommercialSqm.toDoubleOrNull() ?: 0.0
        val officeSqm = foreOfficeSqm.toDoubleOrNull() ?: 0.0
        val garageSqm = foreGarageSqm.toDoubleOrNull() ?: 0.0
        val servicesKw = foreGeneralServicesKw.toDoubleOrNull() ?: 0.0

        val pBasic = 5.75 // kW
        val pElevated = 9.2 // kW

        val totalDwellings = basic + elevated
        val divFactor = if (totalDwellings <= 1) 1.0 else {
            1.0 + (totalDwellings - 1) * 0.153
        }

        val sumDwellingsFullUnreduced = (basic * pBasic) + (elevated * pElevated)
        val rawDwellingsAvgPower = if (totalDwellings > 0) sumDwellingsFullUnreduced / totalDwellings else 0.0
        val pDwellingsCombined = rawDwellingsAvgPower * divFactor

        val pCommercial = if (commSqm > 0.0) {
            maxOf(commSqm * 0.10, 3.45)
        } else 0.0

        val pOffices = if (officeSqm > 0.0) {
            maxOf(officeSqm * 0.10, 3.45)
        } else 0.0

        // 20 W/m² mechanical forced garage ventilation
        val pGarage = if (garageSqm > 0.0) garageSqm * 0.02 else 0.0

        val totalBuildingsPower = pDwellingsCombined + pCommercial + pOffices + pGarage + servicesKw
        foreCalculatedPowerKw = totalBuildingsPower

        val amps = totalBuildingsPower * 1000.0 / (Math.sqrt(3.0) * 400.0 * 0.9)
        foreRecommendedIgaAmps = amps

        foreStatusMessage = "Previsión de Carga del Edificio (ITC-BT-10):\n" +
                "• Viviendas (${totalDwellings} ud): ${String.format("%.2f", pDwellingsCombined)} kW (Coeficiente unitario con diversidad aplicado)\n" +
                "• Locales Comerciales: ${String.format("%.2f", pCommercial)} kW\n" +
                "• Oficinas: ${String.format("%.2f", pOffices)} kW\n" +
                "• Garajes (Ventilación Forzada): ${String.format("%.2f", pGarage)} kW\n" +
                "• Servicios Generales: ${String.format("%.2f", servicesKw)} kW\n\n" +
                "POTENCIA TOTAL PREVISTA: ${String.format("%.2f", totalBuildingsPower)} kW.\n" +
                "Se aconseja LGA trifásica (400V) para intensidad de ${String.format("%.2f", amps)} A."
    }

    fun runTubeDiameterCalculation() {
        val crossSec = tubesConductorSec.toDoubleOrNull() ?: 2.5
        val count = tubesConductorsCount.toIntOrNull() ?: 3
        val isEmbedded = tubesInstallMethod == "empotrado"

        val baseDiameter = when {
            crossSec <= 1.5 -> if (count <= 3) 16 else 20
            crossSec <= 2.5 -> if (count <= 3) 20 else 25
            crossSec <= 4.0 -> if (count <= 3) 20 else 25
            crossSec <= 6.0 -> if (count <= 3) 25 else 32
            crossSec <= 10.0 -> if (count <= 3) 32 else 40
            crossSec <= 16.0 -> if (count <= 3) 32 else 40
            crossSec <= 25.0 -> if (count <= 3) 40 else 50
            crossSec <= 35.0 -> if (count <= 3) 50 else 63
            else -> 63
        }

        val finalDiameter = if (isEmbedded) baseDiameter else {
            if (baseDiameter > 16) baseDiameter else 16
        }

        tubesCalculatedDiameterMm = finalDiameter
        tubesStatusMessage = "Cálculo de Diámetro de Tubo (ITC-BT-21):\n" +
                "Para canalización de $count conductores de $crossSec mm² en instalación " +
                (if (isEmbedded) "empotrada en obra" else "en montaje superficial") + ",\n" +
                "se exige un diámetro exterior mínimo de tubo de D=$finalDiameter mm.\n" +
                "Esto asegura conservar la sección libre para adición y disipación térmica."
    }

    fun runGroundingCalculation() {
        val rho = earthSoilResistivity.toDoubleOrNull() ?: 100.0
        val isPica = earthElectrodeType == "pica"
        val length = earthElectrodeLength.toDoubleOrNull() ?: 2.0

        val resistance = if (isPica) {
            rho / length
        } else {
            (2.0 * rho) / length
        }

        earthCalculatedResistanceOhms = resistance
        val statusLabel = when {
            resistance < 15.0 -> "EXCELENTE (R < 15 Ω - Ideal enlace y locales de pública concurrencia)"
            resistance < 37.0 -> "CONFORME (R < 37 Ω - Seguro según ITCs básicas)"
            else -> "REVISABLE (Se aconseja duplicar electrodos o picas en paralelo)"
        }

        earthStatusMessage = "Resistencia de Puesta a Tierra calculada:\n" +
                "Utilizando terreno con resistividad de $rho Ω·m y " +
                (if (isPica) "un electrodo de pica de $length metros" else "un cable horizontal enterrado de $length m") + ".\n" +
                "Resistencia calculada: R = ${String.format("%.2f", resistance)} Ω.\n" +
                "Estado: $statusLabel."
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

    // New Document & News Actions for Administrators
    fun addCustomDocument(title: String, description: String, fileName: String, fileSize: String, type: String, uriString: String? = null) {
        viewModelScope.launch {
            repository.insertCustomDocument(
                CustomDocumentEntity(
                    docId = "doc_${System.currentTimeMillis()}",
                    title = title,
                    description = description,
                    fileName = fileName,
                    fileSize = fileSize,
                    type = type,
                    isCustom = true,
                    uriString = uriString
                )
            )
        }
    }

    fun deleteCustomDocument(docId: String) {
        viewModelScope.launch {
            repository.deleteCustomDocument(docId)
        }
    }

    fun addCustomNews(title: String, summary: String, content: String, category: String, categoryLabel: String, readTime: String = "3 min", hot: Boolean = false) {
        viewModelScope.launch {
            // Ensure title/summary reflect REBT 2026 to stay perfectly focused
            val finalTitle = if (title.contains("2026", ignoreCase = true)) title else "$title (REBT 2026)"
            val finalSummary = if (summary.contains("2026", ignoreCase = true) || summary.contains("REBT", ignoreCase = true)) summary else "$summary - Actualizado al nuevo REBT 2026."
            repository.insertCustomNews(
                CustomNewsEntity(
                    newsId = "news_${System.currentTimeMillis()}",
                    title = finalTitle,
                    summary = finalSummary,
                    content = content,
                    date = java.text.SimpleDateFormat("dd 'de' MMMM, yyyy", java.util.Locale.getDefault()).format(java.util.Date()),
                    category = category,
                    categoryLabel = categoryLabel,
                    readTime = readTime,
                    hot = hot,
                    isCustom = true
                )
            )
        }
    }

    fun deleteCustomNews(newsId: String) {
        viewModelScope.launch {
            repository.deleteCustomNews(newsId)
        }
    }

    fun insertUserLead(email: String, name: String, plan: String, phone: String, company: String, province: String, isSold: Boolean, price: Double) {
        viewModelScope.launch {
            repository.insertUserLead(
                UserLeadEntity(
                    email = email,
                    name = name,
                    subscriptionPlan = plan,
                    phoneNumber = phone,
                    companyName = company,
                    province = province,
                    isSold = isSold,
                    leadPrice = price,
                    registrationDate = System.currentTimeMillis()
                )
            )
        }
    }

    fun updateUserLead(lead: UserLeadEntity) {
        viewModelScope.launch {
            repository.updateUserLead(lead)
        }
    }

    fun deleteUserLead(id: Int) {
        viewModelScope.launch {
            repository.deleteUserLead(id)
        }
    }

    fun clearUserLeads() {
        viewModelScope.launch {
            repository.clearUserLeads()
        }
    }

    fun clearAllUserData(onComplete: () -> Unit) {
        viewModelScope.launch {
            repository.clearModuleProgress()
            repository.clearExamHistory()
            repository.clearUserLeads()
            repository.clearCustomDocuments()
            repository.clearCustomNews()
            repository.resetDailyActivity()
            currentUserEmail = ""
            adminModeEnabled = false
            onComplete()
        }
    }

    fun changeSubscriberEmail(newEmail: String) {
        val oldEmail = currentUserEmail.trim().lowercase()
        val parsed = newEmail.trim().lowercase()
        if (oldEmail != parsed) {
            currentUserEmail = parsed
            viewModelScope.launch {
                // Set gamification metrics, exam records, and progress to zero to track the new subscriber's progress realistically
                repository.resetDailyActivity()
                repository.clearExamHistory()
                repository.clearModuleProgress()
            }
        }
    }

    fun resetGamificationOnly() {
        viewModelScope.launch {
            repository.resetDailyActivity()
            repository.clearExamHistory()
            repository.clearModuleProgress()
        }
    }

    fun isSectionEnabled(tab: String, plan: String): Boolean {
        val cleanPlan = plan.lowercase().trim()
        return when {
            cleanPlan == "gratuito" || cleanPlan == "demo" -> {
                when (tab) {
                    "dashboard" -> demoDashboardEnabled
                    "book" -> demoBookEnabled
                    "exams" -> demoExamsEnabled
                    "simulator" -> demoSimulatorEnabled
                    "laboratory" -> demoLaboratoryEnabled
                    "documents" -> demoDocumentsEnabled
                    "news" -> demoNewsEnabled
                    "support" -> demoSupportEnabled
                    else -> true
                }
            }
            cleanPlan == "pro" -> {
                when (tab) {
                    "dashboard" -> proDashboardEnabled
                    "book" -> proBookEnabled
                    "exams" -> proExamsEnabled
                    "simulator" -> proSimulatorEnabled
                    "laboratory" -> proLaboratoryEnabled
                    "documents" -> proDocumentsEnabled
                    "news" -> proNewsEnabled
                    "support" -> proSupportEnabled
                    else -> true
                }
            }
            cleanPlan == "premium" -> {
                when (tab) {
                    "dashboard" -> premiumDashboardEnabled
                    "book" -> premiumBookEnabled
                    "exams" -> premiumExamsEnabled
                    "simulator" -> premiumSimulatorEnabled
                    "laboratory" -> premiumLaboratoryEnabled
                    "documents" -> premiumDocumentsEnabled
                    "news" -> premiumNewsEnabled
                    "support" -> premiumSupportEnabled
                    else -> true
                }
            }
            else -> true
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
