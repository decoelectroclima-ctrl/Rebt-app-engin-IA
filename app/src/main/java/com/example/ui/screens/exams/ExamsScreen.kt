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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ExamMode
import com.example.MainViewModel
import com.example.data.Content
import com.example.data.Question
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

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            // Hero Official Simulation 80 questions
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("official_sim_80_card"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                ),
                border = BorderStroke(1.5.dp, Color(0xFF58A6FF))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF58A6FF).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "CONVOCATORIA OFICIAL",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF58A6FF),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Timer, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("180 min", fontSize = 12.sp, color = Color.Gray)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Simulacro Oficial General (80 Preguntas)",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Examen con distribución oficial idéntica a las pruebas de acreditación de Industria. Umbral de Aprobado: 75% de aciertos.",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            FeedbackManager.playClick(context)
                            viewModel.startOfficialSimulation()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("start_official_exam_80"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF238636))
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Comenzar Simulacro Oficial", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item {
            // Half simulation 40 questions
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("practice_sim_40_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                ),
                border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Simulacro Intermedio (40 Preguntas)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text("90 min", fontSize = 12.sp, color = Color.Gray)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Sesión representativa para días con menor disponibilidad horaria.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedButton(
                        onClick = {
                            FeedbackManager.playClick(context)
                            val allQ = Content.QUESTIONS.values.flatMap { it.questions }.shuffled().take(40)
                            val module = com.example.data.ModuleDefinition(
                                id = "sim_intermedio_${System.currentTimeMillis()}",
                                label = "Simulacro Intermedio (40 Q)",
                                icon = "📝",
                                color = "#3498DB",
                                questions = allQ
                            )
                            viewModel.activeExamMode = ExamMode.OFFICIAL_SIMULATION
                            viewModel.startTopicPractice("articulado") // Fallback loader
                            viewModel.activeExamModule = module
                            viewModel.examRemainingSeconds = 90 * 60
                            viewModel.examTotalSeconds = 90 * 60
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Iniciar Sesión de 40 Preguntas")
                    }
                }
            }
        }
    }
}

@Composable
fun TopicPracticeTab(viewModel: MainViewModel) {
    val context = LocalContext.current
    val modules = Content.QUESTIONS.values.toList()

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
                                viewModel.startMistakesReview(reviews)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("start_mistakes_review_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF85149)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Repasar Todos los Fallos (${reviews.size})")
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
                val isPassed = rec.pct >= 75
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
    val module = viewModel.activeExamModule ?: return
    val total = module.questions.size
    val correct = viewModel.examCorrectCount
    val pct = if (total > 0) (correct * 100 / total) else 0
    val isPassed = pct >= 75 // Spanish official industry standard threshold

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
                        Text("Tiempo", fontSize = 11.sp, color = Color.Gray)
                        Text("${timeMinutes}m ${timeSeconds}s", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
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
                            // Collect mistake reviews and start
                            val reviews = viewModel.questionReviewsFlow.value
                            viewModel.startMistakesReview(reviews)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.RestartAlt, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Repasar Fallos de esta Sesión")
                    }
                }
            }
        }
    }
}
