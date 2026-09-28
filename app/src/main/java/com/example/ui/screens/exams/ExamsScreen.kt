package com.example.ui.screens.exams

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ExamMode
import com.example.MainViewModel
import com.example.data.*
import com.example.ui.FeedbackManager
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ExamsScreen(viewModel: MainViewModel) {
    val activeModule = viewModel.activeExamModule

    if (activeModule != null) {
        if (viewModel.examCompleted) {
            ExamResultsView(viewModel)
        } else {
            ActiveExamView(viewModel)
        }
    } else {
        ExamSelectionHub(viewModel)
    }
}

// -------------------------------------------------------------
// 1. EXAM HUB / SELECTION SCREEN
// -------------------------------------------------------------
@Composable
fun ExamSelectionHub(viewModel: MainViewModel) {
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf(0) } // 0: Simulacros, 1: Por Tema, 2: Mis Errores, 3: Historial
    val mistakeReviews by viewModel.questionReviewsFlow.collectAsState()
    val examHistory by viewModel.examHistoryFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Centro de Exámenes REBT",
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Simulacros reales de convocatorias oficiales de Industria y autoevaluaciones por módulo.",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Tab Selector
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clip(RoundedCornerShape(12.dp))
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Simulacros", fontSize = 12.sp, maxLines = 1) },
                icon = { Icon(Icons.Default.Timer, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Por Tema", fontSize = 12.sp, maxLines = 1) },
                icon = { Icon(Icons.Default.Category, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                text = {
                    Text(
                        text = if (mistakeReviews.isNotEmpty()) "Fallos (${mistakeReviews.size})" else "Fallos",
                        fontSize = 12.sp,
                        maxLines = 1,
                        color = if (mistakeReviews.isNotEmpty()) Color(0xFFF85149) else MaterialTheme.colorScheme.onSurface
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.RestartAlt,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = if (mistakeReviews.isNotEmpty()) Color(0xFFF85149) else MaterialTheme.colorScheme.onSurface
                    )
                }
            )
            Tab(
                selected = selectedTab == 3,
                onClick = { selectedTab = 3 },
                text = { Text("Historial", fontSize = 12.sp, maxLines = 1) },
                icon = { Icon(Icons.Default.History, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTab) {
            0 -> OfficialSimulationsTab(viewModel)
            1 -> TopicPracticeTab(viewModel)
            2 -> MistakesReviewTab(viewModel, mistakeReviews)
            3 -> ExamHistoryTab(viewModel, examHistory)
        }
    }
}

@Composable
fun OfficialSimulationsTab(viewModel: MainViewModel) {
    val context = LocalContext.current
    val examHistory by viewModel.examHistoryFlow.collectAsState()
    val isPremium = viewModel.isPremium
    val officialExamsCount = remember(examHistory) {
        examHistory.count { it.totalCount == ExamConfig.OFFICIAL_QUESTIONS }
    }
    var showGatingDialog by remember { mutableStateOf(false) }
    var gatingDialogMessage by remember { mutableStateOf("") }

    if (showGatingDialog) {
        AlertDialog(
            onDismissRequest = { showGatingDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.WorkspacePremium,
                    contentDescription = null,
                    tint = Color(0xFFF59E0B),
                    modifier = Modifier.size(32.dp)
                )
            },
            title = {
                Text(
                    text = "Función del Plan Pro",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = gatingDialogMessage,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showGatingDialog = false
                        viewModel.activeTab = "subscription"
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE67E22))
                ) {
                    Text("Ver Planes de Suscripción")
                }
            },
            dismissButton = {
                TextButton(onClick = { showGatingDialog = false }) {
                    Text("Cerrar")
                }
            }
        )
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        // Express simulation
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("express_sim_1_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                ),
                border = BorderStroke(1.dp, Color(0xFFD97706))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Pregunta Express (60s)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text("60 seg", fontSize = 12.sp, color = Color(0xFFD97706))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Pon a prueba tu agilidad mental con una pregunta aleatoria.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedButton(
                        onClick = {
                            FeedbackManager.playClick(context)
                            val allQ = Content.QUESTIONS.values.flatMap { it.questions }.shuffled().take(1)
                            val module = com.example.data.ModuleDefinition(
                                id = "express_${System.currentTimeMillis()}",
                                label = "Express",
                                icon = "⚡",
                                color = "#D97706",
                                questions = allQ
                            )
                            viewModel.activeExamMode = ExamMode.TOPIC_PRACTICE
                            viewModel.activeExamModule = module
                            viewModel.examRemainingSeconds = 60
                            viewModel.examTotalSeconds = 60
                            viewModel.activeTab = "exams"
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Iniciar Desafío Express")
                    }
                }
            }
        }
        
        // 20 Questions
        item {
            SimulationCard(
                viewModel = viewModel,
                title = "Simulacro Corto (${ExamConfig.SHORT_QUESTIONS} Preguntas)",
                questionCount = ExamConfig.SHORT_QUESTIONS,
                timeMinutes = ExamConfig.SHORT_MINUTES,
                color = Color(0xFF3498DB),
                testTag = "start_sim_20"
            )
        }

        // 40 Questions
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                ),
                border = BorderStroke(1.dp, Color(0xFF238636))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Simulacro Completo (${ExamConfig.OFFICIAL_QUESTIONS} Preguntas)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (!isPremium) {
                                    if (officialExamsCount >= 1) Color(0xFFF85149).copy(alpha = 0.15f) else Color(0xFFF59E0B).copy(alpha = 0.15f)
                                } else {
                                    Color(0xFF238636).copy(alpha = 0.15f)
                                }
                            ) {
                                Text(
                                    text = if (!isPremium) {
                                        if (officialExamsCount >= 1) "Demo Agotada (1/1)" else "1 Prueba Demo (0/1)"
                                    } else {
                                        "Ilimitado PRO"
                                    },
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (!isPremium) {
                                        if (officialExamsCount >= 1) Color(0xFFF85149) else Color(0xFFF59E0B)
                                    } else {
                                        Color(0xFF3FB950)
                                    },
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFF238636).copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "Básica (IBTB)",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF3FB950),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Examen oficial de 40 preguntas estratificadas: 10 de Articulado y 30 de ITCs reglamentarias.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            FeedbackManager.playClick(context)
                            if (!isPremium && officialExamsCount >= 1) {
                                gatingDialogMessage = "Has completado tu simulacro oficial de prueba (40 preguntas). Para realizar simulacros oficiales ilimitados con selección estratificada y temporizador de 90 min, activa el Plan Pro."
                                showGatingDialog = true
                            } else {
                                viewModel.startOfficialSimulation()
                            }
                        },
                        modifier = Modifier.fillMaxWidth().testTag("start_sim_40"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!isPremium && officialExamsCount >= 1) Color(0xFFE67E22) else Color(0xFF238636)
                        )
                    ) {
                        if (!isPremium && officialExamsCount >= 1) {
                            Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Desbloquear Ilimitados (Plan Pro)")
                        } else {
                            Text("Iniciar (${ExamConfig.OFFICIAL_MINUTES} min)")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SimulationCard(
    viewModel: MainViewModel,
    title: String,
    questionCount: Int,
    timeMinutes: Int,
    color: Color,
    testTag: String
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
        ),
        border = BorderStroke(1.dp, color)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    FeedbackManager.playClick(context)
                    val allQuestions = Content.QUESTIONS.values.flatMap { it.questions }.distinctBy { it.q }.shuffled()
                    val questions = allQuestions.take(questionCount)
                    
                    val module = com.example.data.ModuleDefinition(
                        id = "sim_${questionCount}_${System.currentTimeMillis()}",
                        label = title,
                        icon = "📝",
                        color = "#${Integer.toHexString(color.toArgb()).substring(2)}",
                        questions = questions
                    )
                    viewModel.activeExamMode = ExamMode.OFFICIAL_SIMULATION
                    viewModel.activeExamModule = module
                    viewModel.examRemainingSeconds = timeMinutes * 60
                    viewModel.examTotalSeconds = timeMinutes * 60
                    viewModel.activeTab = "exams"
                },
                modifier = Modifier.fillMaxWidth().testTag(testTag),
                colors = ButtonDefaults.buttonColors(containerColor = color)
            ) {
                Text("Iniciar ($timeMinutes min)")
            }
        }
    }
}

@Composable
fun TopicPracticeTab(viewModel: MainViewModel) {
    var subTab by remember { mutableStateOf(0) } // 0: Por ITC (REBT Completo), 1: Bloques Temáticos (8)
    val progressList by viewModel.progressFlow.collectAsState()
    val allQuestions = remember { Content.QUESTIONS.values.flatMap { it.questions }.distinctBy { it.q } }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = subTab == 0,
                onClick = { subTab = 0 },
                label = { Text("Por ITC (REBT Completo)", fontSize = 12.sp) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.FormatListNumbered,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                },
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                selected = subTab == 1,
                onClick = { subTab = 1 },
                label = { Text("Bloques Temáticos (8)", fontSize = 12.sp) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.GridView,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                },
                modifier = Modifier.weight(1f)
            )
        }

        if (subTab == 0) {
            ItcTestsList(viewModel, progressList, allQuestions)
        } else {
            ThematicModulesList(viewModel)
        }
    }
}

@Composable
fun ItcTestsList(
    viewModel: MainViewModel,
    progressList: List<com.example.data.ModuleProgressEntity>,
    allQuestions: List<Question>
) {
    val context = LocalContext.current
    val isPremium = viewModel.isPremium
    var itcSearchQuery by remember { mutableStateOf("") }
    val syllabusMap = remember { Content.SYLLABUS.associateBy { it.id } }
    var showGatingDialog by remember { mutableStateOf(false) }
    var gatingMessage by remember { mutableStateOf("") }

    if (showGatingDialog) {
        AlertDialog(
            onDismissRequest = { showGatingDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.WorkspacePremium,
                    contentDescription = null,
                    tint = Color(0xFFF59E0B),
                    modifier = Modifier.size(32.dp)
                )
            },
            title = {
                Text(
                    text = "Instrucción Técnica Pro",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = gatingMessage,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showGatingDialog = false
                        viewModel.activeTab = "subscription"
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE67E22))
                ) {
                    Text("Ver Planes de Suscripción")
                }
            },
            dismissButton = {
                TextButton(onClick = { showGatingDialog = false }) {
                    Text("Cerrar")
                }
            }
        )
    }

    val filteredItcs = remember(itcSearchQuery) {
        (1..52).filter { itcNum ->
            val itcCode = String.format("ITC-BT-%02d", itcNum)
            val title = syllabusMap["itc-${String.format("%02d", itcNum)}"]?.title ?: "Instrucción BT-$itcNum"
            if (itcSearchQuery.isBlank()) true
            else itcCode.contains(itcSearchQuery, ignoreCase = true) || title.contains(itcSearchQuery, ignoreCase = true)
        }
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            OutlinedTextField(
                value = itcSearchQuery,
                onValueChange = { itcSearchQuery = it },
                placeholder = { Text("Buscar ITC por número o temática...", fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(18.dp)) },
                trailingIcon = {
                    if (itcSearchQuery.isNotEmpty()) {
                        IconButton(onClick = { itcSearchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Limpiar", modifier = Modifier.size(16.dp))
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
        }

        // Fila de Articulado REBT
        if (itcSearchQuery.isBlank() || "articulado".contains(itcSearchQuery, ignoreCase = true) || "art".contains(itcSearchQuery, ignoreCase = true)) {
            item {
                val artQuestions = remember(allQuestions) { allQuestions.filter { it.isArticulado() } }
                val artScore = viewModel.getArticuladoSuccessPct(progressList)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            FeedbackManager.playClick(context)
                            viewModel.startArticuladoPractice()
                        }
                        .testTag("itc_card_articulado"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                    ),
                    border = BorderStroke(1.dp, Color(0xFFBC8CFF).copy(alpha = 0.6f))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFBC8CFF).copy(alpha = 0.15f),
                            modifier = Modifier.size(42.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("⚡", fontSize = 20.sp)
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Articulado REBT (Art. 1-29)",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = if (artQuestions.size < ExamConfig.ITC_BLOCK_QUESTIONS)
                                    "${artQuestions.size} preguntas disponibles • ${ExamConfig.ITC_BLOCK_MINUTES} min"
                                else
                                    "${artQuestions.size} preguntas • ${ExamConfig.ITC_BLOCK_MINUTES} min",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                            ) {
                                Text(
                                    text = artScore,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text("Acierto", fontSize = 10.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }

        // Cada una de las 52 ITCs reglamentarias
        items(filteredItcs, key = { it }) { itcNum ->
            val itcKey = "itc-${String.format("%02d", itcNum)}"
            val syllabus = syllabusMap[itcKey]
            val title = syllabus?.title ?: "Instrucción BT-${String.format("%02d", itcNum)}"
            val isSpecialist = itcNum in ExamConfig.SPECIALIST_ONLY_ITC
            val itcQuestions = remember(allQuestions, itcNum) { allQuestions.filter { it.itcNumber() == itcNum } }
            val qCount = itcQuestions.size
            val successPct = viewModel.getItcSuccessPct(itcNum, progressList)
            val isLockedForFree = !isPremium && itcNum > 5

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = qCount > 0 || isLockedForFree) {
                        FeedbackManager.playClick(context)
                        if (isLockedForFree) {
                            gatingMessage = "La preparación específica para la ITC-BT-${String.format("%02d", itcNum)} ($title) está reservada para el Plan Pro (ITCs 01 a 05 gratuitas). Desbloquea las 52 ITCs del REBT con el Plan Pro o Vitalicio."
                            showGatingDialog = true
                        } else if (qCount > 0) {
                            viewModel.startItcPractice(itcNum, title)
                        }
                    }
                    .testTag("itc_card_${itcNum}"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                ),
                border = BorderStroke(
                    1.dp,
                    if (isLockedForFree) {
                        Color(0xFFE67E22).copy(alpha = 0.4f)
                    } else if (qCount > 0) {
                        if (isSpecialist) Color(0xFFD29922).copy(alpha = 0.4f)
                        else if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8)
                    } else {
                        Color.Gray.copy(alpha = 0.15f)
                    }
                )
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isLockedForFree) Color(0xFFE67E22).copy(alpha = 0.12f)
                        else if (isSpecialist) Color(0xFFD29922).copy(alpha = 0.15f)
                        else MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                        modifier = Modifier.size(42.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = String.format("%02d", itcNum),
                                fontWeight = FontWeight.Black,
                                fontSize = 15.sp,
                                color = if (isLockedForFree) Color(0xFFE67E22)
                                else if (isSpecialist) Color(0xFFD29922)
                                else MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "ITC-BT-${String.format("%02d", itcNum)}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            if (isSpecialist) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = Color(0xFFD29922).copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        text = "Solo especialista",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFD29922),
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = when {
                                isLockedForFree -> "ITC Pro (01-05 gratis en Plan Free)"
                                qCount == 0 -> "0 preguntas disponibles"
                                qCount < ExamConfig.ITC_BLOCK_QUESTIONS -> "$qCount preguntas disponibles • ${ExamConfig.ITC_BLOCK_MINUTES} min"
                                else -> "$qCount preguntas • ${ExamConfig.ITC_BLOCK_MINUTES} min"
                            },
                            fontSize = 11.sp,
                            color = if (isLockedForFree) Color(0xFFE67E22) else if (qCount > 0) Color(0xFF3FB950) else Color.Gray
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        if (isLockedForFree) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFFE67E22).copy(alpha = 0.15f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Requiere Plan Pro",
                                        tint = Color(0xFFE67E22),
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = "PRO",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFE67E22)
                                    )
                                }
                            }
                        } else {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (successPct != "—") Color(0xFF3FB950).copy(alpha = 0.12f) else Color.Gray.copy(alpha = 0.1f)
                            ) {
                                Text(
                                    text = successPct,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (successPct != "—") Color(0xFF3FB950) else Color.Gray,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text("Acierto", fontSize = 10.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ThematicModulesList(viewModel: MainViewModel) {
    val context = LocalContext.current
    val classicKeys = remember { setOf("articulado", "empresas", "enlace", "interiores", "tierra", "especiales", "suministro", "tubos") }
    val modules = remember { Content.QUESTIONS.filterKeys { it in classicKeys }.values.toList() }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        items(modules, key = { it.id }) { mod ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        FeedbackManager.playClick(context)
                        viewModel.startTopicPractice(mod.id)
                    }
                    .testTag("topic_card_${mod.id}"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                ),
                border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = mod.icon, fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = mod.label,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "${mod.questions.size} preguntas con explicaciones normativas",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun MistakesReviewTab(viewModel: MainViewModel, reviews: List<com.example.data.QuestionReviewEntity>) {
    val context = LocalContext.current
    val isPremium = viewModel.isPremium
    var showGatingDialog by remember { mutableStateOf(false) }

    if (showGatingDialog) {
        AlertDialog(
            onDismissRequest = { showGatingDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.WorkspacePremium,
                    contentDescription = null,
                    tint = Color(0xFFF59E0B),
                    modifier = Modifier.size(32.dp)
                )
            },
            title = {
                Text(
                    text = "Repaso Interactivo Pro",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "El test interactivo con repetición espaciada es una función exclusiva del Plan Pro. Puedes consultar todas tus preguntas falladas en esta lista, o desbloquear el test interactivo para eliminarlas de tu lista al acertarlas.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showGatingDialog = false
                        viewModel.activeTab = "subscription"
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE67E22))
                ) {
                    Text("Ver Planes de Suscripción")
                }
            },
            dismissButton = {
                TextButton(onClick = { showGatingDialog = false }) {
                    Text("Cerrar")
                }
            }
        )
    }

    if (reviews.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF3FB950),
                    modifier = Modifier.size(56.dp)
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "¡Sin Preguntas Falladas!",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "No tienes preguntas pendientes de dominar en este momento. Las preguntas que falles en los exámenes se guardarán automáticamente aquí para repasarlas.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (viewModel.isDarkTheme) Color(0xFF1E1715) else Color(0xFFFFF5F5)
                    ),
                    border = BorderStroke(1.dp, Color(0xFFF85149).copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Modo de Repetición Espaciada (${reviews.size} pendientes)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFFF85149)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Repasa exclusivamente los conceptos donde has fallado hasta asimilarlos con seguridad.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = {
                                FeedbackManager.playClick(context)
                                if (!isPremium) {
                                    showGatingDialog = true
                                } else {
                                    viewModel.startMistakesReview(reviews)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("start_mistakes_review_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (!isPremium) Color(0xFFE67E22) else Color(0xFFF85149)
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            if (!isPremium) {
                                Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Desbloquear Modo Test Interactivo (Plan Pro)")
                            } else {
                                Icon(Icons.Default.PlayArrow, contentDescription = null)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Repasar Todos los Fallos (${reviews.size})")
                            }
                        }
                    }
                }
            }

            items(reviews, key = { it.questionId }) { rev ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                    ),
                    border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFFF85149).copy(alpha = 0.12f)
                            ) {
                                Text(
                                    text = rev.reference.ifBlank { "REBT" },
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFF85149),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Text(
                                text = "Fallada ${rev.failCount} vez",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = rev.questionText,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Explicación: ${rev.explanation}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExamHistoryTab(viewModel: MainViewModel, history: List<com.example.data.ExamRecordEntity>) {
    val context = LocalContext.current
    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()) }

    if (history.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.AssignmentLate,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(56.dp)
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Sin Historial de Exámenes",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Aún no has completado ningún examen. Los resultados de tus simulacros y repasos se guardarán aquí.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Últimas ${history.size} sesiones realizadas",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    TextButton(onClick = { viewModel.clearExamHistory() }) {
                        Text("Limpiar Historial", fontSize = 12.sp, color = Color(0xFFF85149))
                    }
                }
            }

            items(history, key = { it.id }) { rec ->
                val isPassed = rec.pct >= (ExamConfig.PASS_THRESHOLD * 100).toInt()
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                    ),
                    border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = rec.moduleId.replace("_", " ").replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${rec.correctCount} de ${rec.totalCount} aciertos • ${dateFormat.format(Date(rec.createdAt))}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isPassed) Color(0xFF3FB950).copy(alpha = 0.15f) else Color(0xFFF85149).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "${rec.pct}% • ${if (isPassed) "APTO" else "NO APTO"}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isPassed) Color(0xFF3FB950) else Color(0xFFF85149),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 2. ACTIVE EXAM VIEW (RUNNING EXAM)
// -------------------------------------------------------------
@Composable
fun ActiveExamView(viewModel: MainViewModel) {
    val context = LocalContext.current
    val module = viewModel.activeExamModule ?: return
    val questions = module.questions
    val currentIndex = viewModel.currentQuestionIndex
    val currentQuestion = questions.getOrNull(currentIndex) ?: return

    var showExitConfirmDialog by remember { mutableStateOf(false) }

    if (showExitConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showExitConfirmDialog = false },
            title = { Text("¿Salir del examen?") },
            text = { Text("Si sales ahora, la sesión actual se cancelará y no se registrará como completada.") },
            confirmButton = {
                Button(
                    onClick = {
                        showExitConfirmDialog = false
                        viewModel.exitActiveExam()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF85149))
                ) {
                    Text("Salir")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitConfirmDialog = false }) {
                    Text("Continuar examen")
                }
            }
        )
    }

    // Format timer
    val minutes = viewModel.examRemainingSeconds / 60
    val seconds = viewModel.examRemainingSeconds % 60
    val timerText = String.format("%02d:%02d", minutes, seconds)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Top Header: Module, Progress & Timer
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { showExitConfirmDialog = true }) {
                Icon(Icons.Default.Close, contentDescription = "Salir")
            }

            Text(
                text = "Pregunta ${currentIndex + 1} de ${questions.size}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Timer display
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = if (viewModel.examRemainingSeconds < 300) Color(0xFFF85149).copy(alpha = 0.15f) else Color(0xFF58A6FF).copy(alpha = 0.15f),
                border = BorderStroke(1.dp, if (viewModel.examRemainingSeconds < 300) Color(0xFFF85149) else Color(0xFF58A6FF).copy(alpha = 0.3f))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = if (viewModel.examRemainingSeconds < 300) Color(0xFFF85149) else Color(0xFF58A6FF)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = timerText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (viewModel.examRemainingSeconds < 300) Color(0xFFF85149) else Color(0xFF58A6FF)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Progress bar
        LinearProgressIndicator(
            progress = { (currentIndex + 1).toFloat() / questions.size.toFloat() },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Scrollable Question & Options Body
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Reference Badge
            if (currentQuestion.ref.isNotBlank()) {
                item {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFF58A6FF).copy(alpha = 0.12f)
                    ) {
                        Text(
                            text = "Referencia: ${currentQuestion.ref}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (viewModel.isDarkTheme) Color(0xFF58A6FF) else Color(0xFF0969DA),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            // Question Text
            item {
                Text(
                    text = currentQuestion.q,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 24.sp
                )
            }

            // Options List
            items(currentQuestion.opts.indices.toList()) { optIndex ->
                val optText = currentQuestion.opts[optIndex]
                val isSelected = viewModel.selectedOptionIndex == optIndex
                val isAnswered = viewModel.currentQuestionAnswered
                val isCorrectOption = optIndex == currentQuestion.a

                val (containerColor, borderColor, contentColor) = when {
                    isAnswered && isCorrectOption -> Triple(Color(0xFF3FB950).copy(alpha = 0.15f), Color(0xFF3FB950), Color(0xFF3FB950))
                    isAnswered && isSelected && !isCorrectOption -> Triple(Color(0xFFF85149).copy(alpha = 0.15f), Color(0xFFF85149), Color(0xFFF85149))
                    isSelected -> Triple(Color(0xFF58A6FF).copy(alpha = 0.15f), Color(0xFF58A6FF), MaterialTheme.colorScheme.onSurface)
                    else -> Triple(
                        if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White,
                        if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8),
                        MaterialTheme.colorScheme.onSurface
                    )
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = !isAnswered) {
                            FeedbackManager.playClick(context)
                            viewModel.selectedOptionIndex = optIndex
                        }
                        .testTag("option_${optIndex}"),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = containerColor),
                    border = BorderStroke(1.5.dp, borderColor)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(borderColor.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            val letter = ('A' + optIndex).toString()
                            Text(
                                text = letter,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = if (isAnswered && (isCorrectOption || isSelected)) borderColor else MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = optText,
                            fontSize = 14.sp,
                            color = contentColor,
                            lineHeight = 20.sp,
                            modifier = Modifier.weight(1f)
                        )

                        if (isAnswered) {
                            if (isCorrectOption) {
                                Icon(Icons.Default.CheckCircle, contentDescription = "Correcto", tint = Color(0xFF3FB950))
                            } else if (isSelected) {
                                Icon(Icons.Default.Cancel, contentDescription = "Incorrecto", tint = Color(0xFFF85149))
                            }
                        }
                    }
                }
            }

            // Explanation Card (revealed after answering)
            if (viewModel.currentQuestionAnswered) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("explanation_card"),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (viewModel.isDarkTheme) Color(0xFF1E232B) else Color(0xFFF6F8FA)
                        ),
                        border = BorderStroke(1.dp, Color(0xFF58A6FF).copy(alpha = 0.3f))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = if (viewModel.isAnswerCorrect == true) "✓ Respuesta Correcta" else "✗ Justificación Normativa",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = if (viewModel.isAnswerCorrect == true) Color(0xFF3FB950) else Color(0xFFF85149)
                                )

                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = currentQuestion.exp,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }
        }

        // Bottom Action Bar
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                if (!viewModel.currentQuestionAnswered) {
                    Button(
                        onClick = {
                            FeedbackManager.playClick(context)
                            viewModel.submitAnswer()
                        },
                        enabled = viewModel.selectedOptionIndex != null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("submit_answer_button"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Comprobar Respuesta", fontWeight = FontWeight.Bold)
                    }
                } else {
                    Button(
                        onClick = {
                            FeedbackManager.playClick(context)
                            viewModel.nextQuestion()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("next_question_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (currentIndex + 1 >= questions.size) Color(0xFF238636) else MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = if (currentIndex + 1 >= questions.size) "Finalizar y Ver Calificación" else "Siguiente Pregunta →",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 3. EXAM RESULTS VIEW (SCORE REPORT)
// -------------------------------------------------------------
@Composable
fun ExamResultsView(viewModel: MainViewModel) {
    val context = LocalContext.current
    val isPremium = viewModel.isPremium
    val module = viewModel.activeExamModule ?: return
    val total = module.questions.size
    val correct = viewModel.examCorrectCount
    val pct = if (total > 0) (correct * 100 / total) else 0
    val isPassed = if (total > 0) (correct.toFloat() / total) >= ExamConfig.PASS_THRESHOLD else false
    val scoreTen = String.format(Locale.US, "%.1f", if (total > 0) (correct.toFloat() / total) * 10f else 0f)

    val (artCorrect, artTotal) = viewModel.getExamArticuladoBreakdown()
    val (itcCorrect, itcTotal) = viewModel.getExamItcBreakdown()

    val timeMinutes = viewModel.examTimeSpentSeconds / 60
    val timeSeconds = viewModel.examTimeSpentSeconds % 60

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("exam_results_card"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
            ),
            border = BorderStroke(1.5.dp, if (isPassed) Color(0xFF3FB950) else Color(0xFFF85149))
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = if (isPassed) Icons.Default.Verified else Icons.Default.SentimentDissatisfied,
                    contentDescription = null,
                    tint = if (isPassed) Color(0xFF3FB950) else Color(0xFFF85149),
                    modifier = Modifier.size(64.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = if (isPassed) "¡APTO (Superado)!" else "NO APTO (Refuerzo)",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (isPassed) Color(0xFF3FB950) else Color(0xFFF85149)
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = module.label,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Score Circle
                Surface(
                    shape = CircleShape,
                    color = if (isPassed) Color(0xFF3FB950).copy(alpha = 0.12f) else Color(0xFFF85149).copy(alpha = 0.12f),
                    border = BorderStroke(2.dp, if (isPassed) Color(0xFF3FB950) else Color(0xFFF85149)),
                    modifier = Modifier.size(110.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "$pct%",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Black,
                                color = if (isPassed) Color(0xFF3FB950) else Color(0xFFF85149)
                            )
                            Text(
                                text = "$correct de $total",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Stats breakdown
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Aciertos", fontSize = 11.sp, color = Color.Gray)
                        Text("$correct", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3FB950))
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Fallos", fontSize = 11.sp, color = Color.Gray)
                        Text("${total - correct}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF85149))
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Nota", fontSize = 11.sp, color = Color.Gray)
                        Text("$scoreTen / 10", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = if (isPassed) Color(0xFF3FB950) else Color(0xFFF85149))
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Tiempo", fontSize = 11.sp, color = Color.Gray)
                        Text("${timeMinutes}m ${timeSeconds}s", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    }
                }

                // Desglose por bloque normativo (Articulado / ITCs) (Tarea 2)
                if (artTotal > 0 || itcTotal > 0) {
                    Spacer(modifier = Modifier.height(18.dp))
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (viewModel.isDarkTheme) Color(0xFF0D1117) else Color(0xFFF6F8FA)
                        ),
                        border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "Desglose Normativo por Bloque",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            if (artTotal > 0) {
                                val artPct = (artCorrect * 100) / artTotal
                                val artPassed = artPct >= (ExamConfig.PASS_THRESHOLD * 100).toInt()
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "• Articulado REBT (Art. 1-29):",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "$artCorrect de $artTotal ($artPct%)",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (artPassed) Color(0xFF3FB950) else Color(0xFFF85149)
                                    )
                                }
                            }
                            if (itcTotal > 0) {
                                if (artTotal > 0) Spacer(modifier = Modifier.height(6.dp))
                                val itcPct = (itcCorrect * 100) / itcTotal
                                val itcPassed = itcPct >= (ExamConfig.PASS_THRESHOLD * 100).toInt()
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "• Instrucciones ITC:",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "$itcCorrect de $itcTotal ($itcPct%)",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (itcPassed) Color(0xFF3FB950) else Color(0xFFF85149)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Actions
                Button(
                    onClick = {
                        FeedbackManager.playClick(context)
                        viewModel.exitActiveExam()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("results_return_hub_button"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Volver a Catálogo de Exámenes", fontWeight = FontWeight.Bold)
                }

                if (!isPassed) {
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedButton(
                        onClick = {
                            FeedbackManager.playClick(context)
                            if (!isPremium) {
                                viewModel.activeTab = "subscription"
                            } else {
                                val reviews = viewModel.questionReviewsFlow.value
                                viewModel.startMistakesReview(reviews)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (!isPremium) {
                            Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color(0xFFE67E22))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Repasar Fallos en Test (Plan Pro)", color = Color(0xFFE67E22))
                        } else {
                            Icon(Icons.Default.RestartAlt, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Repasar Fallos de esta Sesión")
                        }
                    }
                }
            }
        }
    }
}
