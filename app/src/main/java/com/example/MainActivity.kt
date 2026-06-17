package com.example

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.testTag
import com.example.data.*
import com.example.ui.SyllabusVisualAid
import com.example.ui.FeedbackManager
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
            EnginIaAppTheme(isDark = viewModel.isDarkTheme) {
                MainAppLayout(viewModel)
            }
        }
    }
}

// Custom Premium Dark & Light Material Theme Colors
@Composable
fun EnginIaAppTheme(isDark: Boolean = true, content: @Composable () -> Unit) {
    val darkColorScheme = darkColorScheme(
        primary = Color(0xFF58a6ff), // Electric Tech Blue
        secondary = Color(0xFFbc8cff), // Regal Violet
        tertiary = Color(0xFF3fb950), // Ground Green
        background = Color(0xFF0d1117), // Deep Navy Slate
        surface = Color(0xFF161b22), // Card dark grey
        onPrimary = Color.Black,
        onSecondary = Color.Black,
        onBackground = Color(0xFFc9d1d9), // Silver typography
        onSurface = Color(0xFFc9d1d9)
    )
    val lightColorScheme = lightColorScheme(
        primary = Color(0xFF0969da), // Deep Tech Blue
        secondary = Color(0xFF8250df), // Purple Accent
        tertiary = Color(0xFF1a7f37), // Ground Green
        background = Color(0xFFf6f8fa), // Clean technical light background
        surface = Color(0xFFffffff), // Crisp white surface
        onPrimary = Color.White,
        onSecondary = Color.White,
        onBackground = Color(0xFF24292f), // Dark charcoal typography
        onSurface = Color(0xFF24292f)
    )
    MaterialTheme(
        colorScheme = if (isDark) darkColorScheme else lightColorScheme,
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppLayout(viewModel: MainViewModel) {
    if (viewModel.showFlashIntro) {
        com.example.ui.FlashIntroScreen(viewModel)
        return
    }

    val context = LocalContext.current
    val subscription by viewModel.subscriptionFlow.collectAsState()
    val isPremium = subscription?.isActive == true
    val currentPlan = subscription?.plan ?: "gratuito"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = "Logo",
                            tint = Color(0xFFF5B041),
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "EnginIA REBT",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp,
                            color = if (viewModel.isDarkTheme) Color.White else Color(0xFF24292f)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        if (isPremium) {
                            Badge(
                                containerColor = Color(0xFFD4AC0D),
                                contentColor = Color.Black,
                                modifier = Modifier.padding(start = 4.dp)
                            ) {
                                Text(
                                    text = "PREMIUM",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                            }
                        } else {
                            Badge(
                                containerColor = Color.Gray,
                                contentColor = Color.White,
                                modifier = Modifier.padding(start = 4.dp)
                            ) {
                                Text(
                                    text = "FREE",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                            }
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.isDarkTheme = !viewModel.isDarkTheme
                            FeedbackManager.playClick(context)
                        }
                    ) {
                        Icon(
                            imageVector = if (viewModel.isDarkTheme) Icons.Default.WbSunny else Icons.Default.NightsStay,
                            contentDescription = "Cambiar tema",
                            tint = if (viewModel.isDarkTheme) Color.White else Color(0xFF24292f)
                        )
                    }
                    IconButton(
                        onClick = { viewModel.activeTab = "billing" },
                        modifier = Modifier.testTag("app_bar_subscription_button")
                    ) {
                        Icon(
                            imageVector = if (isPremium) Icons.Default.WorkspacePremium else Icons.Default.AddCard,
                            contentDescription = "Suscripciones",
                            tint = if (isPremium) Color(0xFFF5B041) else (if (viewModel.isDarkTheme) Color.White else Color(0xFF24292f))
                        )
                    }
                    IconButton(
                        onClick = { viewModel.activeTab = "support" },
                        modifier = Modifier.testTag("app_bar_support_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.SupportAgent,
                            contentDescription = "Soporte",
                            tint = if (viewModel.isDarkTheme) Color.White else Color(0xFF24292f)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = if (viewModel.isDarkTheme) Color.White else Color(0xFF24292f)
            ) {
                NavigationBarItem(
                    selected = viewModel.activeTab == "dashboard",
                    onClick = { viewModel.activeTab = "dashboard" },
                    icon = { Icon(Icons.Default.Dashboard, contentDescription = "Inicio") },
                    label = { Text("Inicio", fontSize = 11.sp, maxLines = 1) },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.primary)
                )
                NavigationBarItem(
                    selected = viewModel.activeTab == "book",
                    onClick = { viewModel.activeTab = "book" },
                    icon = { Icon(Icons.Default.MenuBook, contentDescription = "Estudio") },
                    label = { Text("Syllabus", fontSize = 11.sp, maxLines = 1) },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.primary)
                )
                NavigationBarItem(
                    selected = viewModel.activeTab == "exams",
                    onClick = { viewModel.activeTab = "exams" },
                    icon = { Icon(Icons.Default.Assignment, contentDescription = "Exámenes") },
                    label = { Text("Exámenes", fontSize = 11.sp, maxLines = 1) },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.primary)
                )
                NavigationBarItem(
                    selected = viewModel.activeTab == "simulator",
                    onClick = { viewModel.activeTab = "simulator" },
                    icon = { Icon(Icons.Default.Engineering, contentDescription = "Simulador") },
                    label = { Text("Pruebas", fontSize = 11.sp, maxLines = 1) },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.primary)
                )
                NavigationBarItem(
                    selected = viewModel.activeTab == "laboratory",
                    onClick = { viewModel.activeTab = "laboratory" },
                    icon = { Icon(Icons.Default.Calculate, contentDescription = "Calculadoras") },
                    label = { Text("Cálculo", fontSize = 11.sp, maxLines = 1) },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.primary)
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Floating Banner describing active status & active user email
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (viewModel.isDarkTheme) Color(0xFF21262d) else Color(0xFFeaeef2))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Usuario: jj.terapias@gmail.com",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (viewModel.isDarkTheme) Color(0xFF8b949e) else Color(0xFF57606a)
                )
                Text(
                    text = if (isPremium) "Suscripción: Google Play $currentPlan" else "Suscripción Gratuita (Básica)",
                    fontSize = 11.sp,
                    color = if (isPremium) (if (viewModel.isDarkTheme) Color(0xFF58a6ff) else Color(0xFF0969da)) else (if (viewModel.isDarkTheme) Color(0xFFf0a6ca) else Color(0xFF8250df))
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                when (viewModel.activeTab) {
                    "dashboard" -> DashboardTabContent(viewModel, isPremium)
                    "book" -> StudyBookTabContent(viewModel)
                    "exams" -> ExamCenterTabContent(viewModel, isPremium)
                    "simulator" -> SimulatorTabContent(viewModel)
                    "laboratory" -> LaboratorioCalculosTabContent(viewModel)
                    "documents" -> DocumentsCenterTabContent(viewModel, isPremium)
                    "news" -> NewsCenterTabContent(viewModel)
                    "support" -> SupportCenterTabContent(viewModel)
                    "billing" -> SubscriptionPlansPortalContent(viewModel, isPremium, currentPlan)
                }
            }

            // Interactive rotating Advertisement frame matching the exact web functionality
            if (!isPremium) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .testTag("advertisement_banner_frame"),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0x33F8D7DA)),
                    border = BorderStroke(1.dp, Color(0x66F5B7B1))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Info Ad",
                            tint = Color(0xFFf85149),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "ANUNCIO REBT patrocinado por ASELaR",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = Color(0xFFf85149)
                            )
                            Text(
                                text = "Consiga la habilitación oficial de instalador. Compre la Licencia Google Play Billing para remover esta publicidad.",
                                fontSize = 10.sp,
                                color = Color.White,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { viewModel.activeTab = "billing" },
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                            modifier = Modifier.height(30.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFf85149))
                        ) {
                            Text("Quitar", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "✓ Licencia comercial activa. Publicidad bloqueada.",
                        fontSize = 11.sp,
                        color = Color(0xFF3fb950),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }

    // Modal Sheet representation representing the Google Play billing checkout flow
    GooglePlayCheckoutSheet(viewModel)
}

// 1. Dashboard Tab implementation
@Composable
fun DashboardTabContent(viewModel: MainViewModel, isPremium: Boolean) {
    val dailyActivity by viewModel.dailyActivityFlow.collectAsState()
    val progressList by viewModel.progressFlow.collectAsState()
    val examHistoryList by viewModel.examHistoryFlow.collectAsState()

    val totalAnswered = dailyActivity?.questionsAnswered ?: 0
    val totalCalculators = dailyActivity?.calculatorsUsed ?: 0
    val totalSchemas = dailyActivity?.schemasExplored ?: 0
    val activeStreak = dailyActivity?.streakDays ?: 0

    val averagePct = if (examHistoryList.isNotEmpty()) {
        examHistoryList.map { it.pct }.average().toInt()
    } else 0

    val totalQuestions = Content.QUESTIONS.values.sumOf { it.questions.size }
    val totalCovered = progressList.sumOf { it.answeredCount }
    val globalProgress = if (totalQuestions > 0) totalCovered.toFloat() / totalQuestions.toFloat() else 0f

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero visual card block
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = if (viewModel.isDarkTheme) Color(0xFF1f262d) else Color(0xFFebeff3))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "¡Hola, Instalador!",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (viewModel.isDarkTheme) Color.White else Color(0xFF1c2128)
                            )
                            Text(
                                text = "Plan: ${if (isPremium) "Acceso Ilimitado Pro" else "Acceso Gratuito Básico"}",
                                fontSize = 13.sp,
                                color = if (viewModel.isDarkTheme) Color(0xFF8b949e) else Color(0xFF57606a)
                            )
                        }
                        // Avatar profile icon
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(if (viewModel.isDarkTheme) Color(0xFF58a6ff) else Color(0xFF0969da)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isPremium) Icons.Default.WorkspacePremium else Icons.Default.Person,
                                contentDescription = "Profile",
                                tint = if (viewModel.isDarkTheme) Color.Black else Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Su progreso de preparación reglamentaria para el examen oficial de ASELaR / Industria 2026:",
                        fontSize = 13.sp,
                        color = if (viewModel.isDarkTheme) Color(0xFFc9d1d9) else Color(0xFF24292f)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        DashboardMetricBox(
                            value = "$totalAnswered",
                            label = "Preguntas",
                            icon = Icons.Default.QuestionAnswer,
                            color = Color(0xFF58a6ff)
                        )
                        DashboardMetricBox(
                            value = "$averagePct%",
                            label = "Aciertos",
                            icon = Icons.Default.DoneAll,
                            color = Color(0xFF3fb950)
                        )
                        DashboardMetricBox(
                            value = "$activeStreak",
                            label = "Racha días",
                            icon = Icons.Default.LocalFireDepartment,
                            color = Color(0xFFF39C12)
                        )
                    }

                    // --- GLOBAL PROGRESS BAR ---
                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Progreso Global del Temario REBT",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (viewModel.isDarkTheme) Color(0xFF8b949e) else Color(0xFF24292f)
                        )
                        Text(
                            text = "$totalCovered / $totalQuestions preg. (${(globalProgress * 100).toInt()}%)",
                            fontSize = 11.sp,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = if (viewModel.isDarkTheme) Color(0xFF58a6ff) else Color(0xFF0969da)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    LinearProgressIndicator(
                        progress = { globalProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = if (viewModel.isDarkTheme) Color(0xFF58a6ff) else Color(0xFF0969da),
                        trackColor = if (viewModel.isDarkTheme) Color(0xFF21262d) else Color(0xFFeaeef2)
                    )
                }
            }
        }

        // Secondary interactive navigation block (Doc and News center shortcuts)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { viewModel.activeTab = "documents" },
                    colors = CardDefaults.cardColors(containerColor = if (viewModel.isDarkTheme) Color(0xFF161b22) else Color(0xFFffffff)),
                    border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363d) else Color(0xFFd0d7de))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CloudDownload, contentDescription = "Docs", tint = Color(0xFFbc8cff))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                "Documental",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = if (viewModel.isDarkTheme) Color.White else Color(0xFF1c2128)
                            )
                            Text("Descargas BOE", fontSize = 10.sp, color = Color.Gray)
                        }
                    }
                }

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { viewModel.activeTab = "news" },
                    colors = CardDefaults.cardColors(containerColor = if (viewModel.isDarkTheme) Color(0xFF161b22) else Color(0xFFffffff)),
                    border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363d) else Color(0xFFd0d7de))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.RssFeed, contentDescription = "Noticias", tint = Color(0xFF58a6ff))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                "Noticias 2026",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = if (viewModel.isDarkTheme) Color.White else Color(0xFF1c2128)
                            )
                            Text("Borradores REBT", fontSize = 10.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }

        // --- GAMIFICATION CARD ---
        item {
            val currentXp = (totalAnswered * 15) + (totalCalculators * 25) + (totalSchemas * 25) + (activeStreak * 50)
            val xpPerLevel = 250
            val currentLevel = 1 + (currentXp / xpPerLevel)
            val xpInCurrentLevel = currentXp % xpPerLevel
            val levelProgress = xpInCurrentLevel.toFloat() / xpPerLevel.toFloat()

            val rankTitle = when {
                currentLevel >= 5 -> "Ingeniero Supremo REBT"
                currentLevel >= 4 -> "Maestro Eléctrico REBT"
                currentLevel >= 3 -> "Director de Cuadros Técnicos"
                currentLevel >= 2 -> "Técnico Especialista REBT"
                else -> "Instalador Básico Autorizado"
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363d) else Color(0xFFd0d7de)),
                colors = CardDefaults.cardColors(containerColor = if (viewModel.isDarkTheme) Color(0xFF161b22) else Color(0xFFffffff))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFF1C40F).copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.EmojiEvents,
                                    contentDescription = null,
                                    tint = Color(0xFFF1C40F),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Gimnasio Eléctrico: Nivel y Logros",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = if (viewModel.isDarkTheme) Color.White else Color(0xFF1c2128)
                            )
                        }
                        
                        Badge(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                            contentColor = MaterialTheme.colorScheme.primary
                        ) {
                            Text(
                                text = "Nivel $currentLevel",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Rango REBT: $rankTitle",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFbc8cff)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // XP Progress bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Progreso del Nivel ($currentXp XP totales)",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "$xpInCurrentLevel / $xpPerLevel XP",
                            fontSize = 11.sp,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = if (viewModel.isDarkTheme) Color.White else Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    LinearProgressIndicator(
                        progress = { levelProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = Color(0xFFbc8cff),
                        trackColor = if (viewModel.isDarkTheme) Color(0xFF21262d) else Color(0xFFeaeef2)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "Logros Obtenidos (Medallas)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (viewModel.isDarkTheme) Color.White else Color(0xFF1c2128),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Achievement items
                    val achievements = listOf(
                        Triple("Novato Técnico", "Responde tus primeros test oficiales del REBT", totalAnswered >= 1),
                        Triple("Cálculo Seguro", "Calcula la sección reglamentaria de un cable en el laboratorio", totalCalculators >= 1),
                        Triple("Bucle Inspector", "Simula una prueba de RCD o resistencia de bucle", totalSchemas >= 1),
                        Triple("Arco de Racha", "Consigue una racha activa de estudio diario (3+ días)", activeStreak >= 3),
                        Triple("Ingeniero Maestro", "Contesta más de 20 preguntas del temario oficial", totalAnswered >= 20),
                        Triple("Socio de Servicio", "Hazte socio con el Plan Pro / Premium Completo", isPremium)
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        achievements.forEach { (title, desc, isUnlocked) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (isUnlocked) {
                                            if (viewModel.isDarkTheme) Color(0xFF1F241F) else Color(0xFFEAF5EA)
                                        } else {
                                            if (viewModel.isDarkTheme) Color(0xFF161B22) else Color(0xFFF6F8FA)
                                        }
                                    )
                                    .border(
                                        1.dp,
                                        if (isUnlocked) Color(0xFF3FB950).copy(alpha = 0.3f) else Color.Transparent,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (isUnlocked) Color(0xFF3FB950).copy(alpha = 0.2f) else Color.Gray.copy(alpha = 0.15f)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = if (isUnlocked) Icons.Default.Verified else Icons.Default.Lock,
                                        contentDescription = null,
                                        tint = if (isUnlocked) Color(0xFF3FB950) else Color.Gray,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = title,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isUnlocked) {
                                            if (viewModel.isDarkTheme) Color.White else Color(0xFF1a7f37)
                                        } else Color.Gray
                                    )
                                    Text(
                                        text = desc,
                                        fontSize = 9.sp,
                                        color = if (viewModel.isDarkTheme) Color.LightGray else Color.DarkGray
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            Text(
                text = "Módulos de Preparación",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = if (viewModel.isDarkTheme) Color.White else Color(0xFF1c2128)
            )
        }

        // Rendering state cards matching original question generator
        items(Content.QUESTIONS.values.toList()) { module ->
            val progress = progressList.find { it.moduleId == module.id }
            val completedCount = progress?.answeredCount ?: 0
            val successRate = progress?.pct ?: 0

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.startExam(module) }
                    .testTag("dashboard_module_card_${module.id}"),
                colors = CardDefaults.cardColors(containerColor = if (viewModel.isDarkTheme) Color(0xFF161b22) else Color(0xFFffffff)),
                border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363d) else Color(0xFFd0d7de))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(android.graphics.Color.parseColor(module.color)).copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = module.icon, fontSize = 20.sp)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = module.label,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = if (viewModel.isDarkTheme) Color.White else Color(0xFF1c2128)
                                )
                                Text(
                                    text = "${module.questions.size} preguntas oficiales",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "Entrar",
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Respondidas: $completedCount",
                            fontSize = 12.sp,
                            color = Color(0xFF8b949e)
                        )
                        if (completedCount > 0) {
                            Text(
                                text = "Acierto: $successRate%",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (successRate >= 70) Color(0xFF3fb950) else Color(0xFFf85149)
                            )
                        } else {
                            Text(
                                text = "No iniciado",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    // Simple progress visual meter
                    val progressValue = if (completedCount > 0) completedCount.toFloat() / module.questions.size.toFloat() else 0f
                    LinearProgressIndicator(
                        progress = { progressValue },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = Color(android.graphics.Color.parseColor(module.color)),
                        trackColor = if (viewModel.isDarkTheme) Color(0xFF21262d) else Color(0xFFeaeef2)
                    )
                }
            }
        }

        // --- SECTION: Interactive sticky notes (Conceptos / Pósits de Estudio) ---
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Note,
                        contentDescription = "Pósit",
                        tint = Color(0xFFF1C40F),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Mis Pósits (Notas Rápidas)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = if (viewModel.isDarkTheme) Color.White else Color(0xFF1c2128)
                    )
                }
                var showNewPostItDialog by remember { mutableStateOf(false) }
                
                TextButton(
                    onClick = { showNewPostItDialog = true }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Añadir", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Crear Pósit", fontSize = 12.sp, color = Color(0xFF58a6ff))
                }

                if (showNewPostItDialog) {
                    NewPostItDialog(
                        onDismiss = { showNewPostItDialog = false },
                        onSave = { content, category, color ->
                            viewModel.addPostIt(content, category, color)
                            showNewPostItDialog = false
                        }
                    )
                }
            }
        }

        item {
            val postItsList by viewModel.postItsFlow.collectAsState()
            
            if (postItsList.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF161b22)),
                    border = BorderStroke(1.dp, Color(0xFF30363d))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No tienes pósits guardados. Crea ideas de estudio pulsando '+ Crear Pósit'.",
                            fontSize = 13.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 8.dp)
                ) {
                    items(postItsList) { postIt ->
                        PostItStickyCard(
                            postIt = postIt,
                            onDelete = { viewModel.deletePostIt(postIt.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardMetricBox(value: String, label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color) {
    Card(
        modifier = Modifier.width(100.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF21262d))
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = icon, contentDescription = label, tint = color, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
            Text(text = label, fontSize = 10.sp, color = Color.Gray)
        }
    }
}

@Composable
fun PostItStickyCard(postIt: PostItEntity, onDelete: () -> Unit) {
    val parsedColor = try {
        Color(android.graphics.Color.parseColor(postIt.color))
    } catch (e: Exception) {
        Color(0xFFFFEAA7)
    }

    Card(
        modifier = Modifier
            .width(180.dp)
            .height(170.dp),
        colors = CardDefaults.cardColors(containerColor = parsedColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(Color.DarkGray)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = postIt.category.uppercase(),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.DarkGray
                    )
                }
                
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Borrar",
                        tint = Color.DarkGray.copy(alpha = 0.6f),
                        modifier = Modifier.size(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = postIt.content,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1E272C),
                lineHeight = 14.sp,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            val dateStr = java.text.SimpleDateFormat("dd/MM", java.util.Locale.getDefault())
                .format(java.util.Date(postIt.createdAt))
            Text(
                text = dateStr,
                fontSize = 8.sp,
                color = Color.DarkGray.copy(alpha = 0.5f),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPostItDialog(onDismiss: () -> Unit, onSave: (String, String, String) -> Unit) {
    var content by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Fórmula") }
    val categories = listOf("Fórmula", "Artículo", "Consejo", "Examen")
    
    val stickyColors = listOf(
        Pair("Amarillo", "#FFEAA7"),
        Pair("Rojo", "#FFD2D2"),
        Pair("Azul", "#D2F4FF"),
        Pair("Verde", "#E2FCD4"),
        Pair("Morado", "#F0E4FF")
    )
    var selectedColorPair by remember { mutableStateOf(stickyColors[0]) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Note, contentDescription = null, tint = Color(0xFFF1C40F))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Nuevo Pósit de Estudio", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        },
        containerColor = Color(0xFF1e2530),
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Añade notas breves, fórmulas o dudas de preparación al REBT.",
                    fontSize = 12.sp,
                    color = Color.LightGray
                )

                OutlinedTextField(
                    value = content,
                    onValueChange = { if (it.length <= 150) content = it },
                    placeholder = { Text("Escribe tu regla mnemotécnica o fórmula aquí...", fontSize = 12.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    maxLines = 5,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFF58a6ff),
                        unfocusedBorderColor = Color(0xFF30363d)
                    )
                )
                
                Text(
                    text = "${content.length}/150",
                    fontSize = 10.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.End)
                )

                Text("Categoría:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { cat ->
                        val isSelected = selectedCategory == cat
                        Surface(
                            modifier = Modifier
                                .clickable { selectedCategory = cat }
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) Color(0xFF58a6ff) else Color(0xFF161b22),
                            border = BorderStroke(1.dp, if (isSelected) Color(0xFF58a6ff) else Color(0xFF30363d))
                        ) {
                            Text(
                                text = cat,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.Black else Color.White,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Text("Color de papel Pósit:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    stickyColors.forEach { colPair ->
                        val isSelected = selectedColorPair.second == colPair.second
                        val colHex = try {
                            Color(android.graphics.Color.parseColor(colPair.second))
                        } catch (e: Exception) {
                            Color(0xFFFFEAA7)
                        }
                        
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(colHex)
                                .border(
                                    border = BorderStroke(
                                        width = if (isSelected) 3.dp else 1.dp,
                                        color = if (isSelected) Color.White else Color.Transparent
                                    ),
                                    shape = CircleShape
                                )
                                .clickable { selectedColorPair = colPair }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (content.isNotBlank()) {
                        onSave(content, selectedCategory, selectedColorPair.second)
                    }
                },
                enabled = content.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF58a6ff), contentColor = Color.Black)
            ) {
                Text("Guardar", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = Color.Gray)
            }
        }
    )
}

// 2. Study Book (Syllabus/Libro) Tab
@Composable
fun StudyBookTabContent(viewModel: MainViewModel) {
    var expandedItemIndex by remember { mutableStateOf<String?>(null) }
    
    val categoryList = listOf("Todos", "Articulado", "Administrativas", "Redes", "Enlace", "Interiores")

    val filteredItems = Content.SYLLABUS.filter { item ->
        val matchesCategory = viewModel.activeCategoryFilter == "Todos" || item.category == viewModel.activeCategoryFilter
        val matchesSearch = viewModel.activeSearchQuery.isEmpty() ||
                item.code.contains(viewModel.activeSearchQuery, ignoreCase = true) ||
                item.title.contains(viewModel.activeSearchQuery, ignoreCase = true) ||
                item.keyConcept.contains(viewModel.activeSearchQuery, ignoreCase = true)
        matchesCategory && matchesSearch
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF161b22))
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                // Interactive study search bar
                OutlinedTextField(
                    value = viewModel.activeSearchQuery,
                    onValueChange = { viewModel.activeSearchQuery = it },
                    placeholder = { Text("Buscar norma, ITC-BT o concepto...", fontSize = 14.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("book_search_bar"),
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    trailingIcon = {
                        if (viewModel.activeSearchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.activeSearchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color(0xFF30363d)
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Scrollable category filter chips
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categoryList.forEach { category ->
                        ElevatedFilterChip(
                            selected = viewModel.activeCategoryFilter == category,
                            onClick = { viewModel.activeCategoryFilter = category },
                            label = { Text(category, fontSize = 12.sp) },
                            modifier = Modifier.testTag("book_chip_$category")
                        )
                    }
                }
            }
        }

        if (filteredItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No se encontraron capítulos o ITCs para la búsqueda actual.",
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredItems) { item ->
                    val isExpanded = expandedItemIndex == item.id
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expandedItemIndex = if (isExpanded) null else item.id }
                            .testTag("syllabus_card_${item.id}"),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF161b22)),
                        border = BorderStroke(1.dp, Color(0xFF30363d))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = item.code,
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 13.sp,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        val freqColor = when (item.freq) {
                                            "Crítica" -> Color(0xFFf85149)
                                            "Alta" -> Color(0xFFF39C12)
                                            "Media" -> Color(0xFF58a6ff)
                                            else -> Color.Gray
                                        }
                                        Badge(containerColor = freqColor) {
                                            Text(
                                                item.freq,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White,
                                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                            )
                                        }
                                    }
                                    Text(
                                        text = item.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = Color.White
                                    )
                                }
                                Icon(
                                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = "Expand",
                                    tint = Color.Gray
                                )
                            }

                            // Summarized brief concept
                            Text(
                                text = "Idea Clave: ${item.keyConcept}",
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                fontSize = 12.sp,
                                color = Color(0xFF8b949e),
                                modifier = Modifier.padding(top = 8.dp)
                            )

                            // Complete highlighted regulations viewable if expanded
                            AnimatedVisibility(
                                visible = isExpanded,
                                enter = expandVertically() + fadeIn(),
                                exit = shrinkVertically() + fadeOut()
                            ) {
                                Column(modifier = Modifier.padding(top = 14.dp)) {
                                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                                    // RED Underline / Subrayado Crítico
                                    Text(
                                        text = "Subrayado Rojo (Límites Legales Críticos):",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = Color(0xFFE74C3C)
                                    )
                                    item.redUnderline.forEach { rule ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp)
                                        ) {
                                            Text("• ", color = Color(0xFFE74C3C), fontWeight = FontWeight.Bold)
                                            Text(rule, fontSize = 12.sp, color = Color(0xFFc9d1d9))
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))

                                    // GREEN Underline / Subrayado Técnico
                                    Text(
                                        text = "Subrayado Verde (Pautas Técnicas y Directrices):",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = Color(0xFF2ECC71)
                                    )
                                    item.greenUnderline.forEach { rule ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp)
                                        ) {
                                            Text("• ", color = Color(0xFF2ECC71), fontWeight = FontWeight.Bold)
                                            Text(rule, fontSize = 12.sp, color = Color(0xFFc9d1d9))
                                        }
                                    }

                                    // Interactive Infographics/Dynamic Mind Map/Calculator Visual Aid
                                    SyllabusVisualAid(itcId = item.id)

                                    Spacer(modifier = Modifier.height(12.dp))

                                    // Custom warnings box "Trampa del Tribunal" matching exact web visual styles
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = CardDefaults.cardColors(containerColor = Color(0x22F39C12)),
                                        border = BorderStroke(1.dp, Color(0x66F39C12))
                                    ) {
                                        Column(modifier = Modifier.padding(12.dp)) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    imageVector = Icons.Default.Warning,
                                                    contentDescription = "Warning Trap",
                                                    tint = Color(0xFFF39C12),
                                                    modifier = Modifier.size(18.dp)
                                                )
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(
                                                    text = "¡Cuidado con la Trampa del Tribunal!",
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 12.sp,
                                                    color = Color(0xFFF39C12)
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = item.trap,
                                                fontSize = 11.sp,
                                                color = Color(0xFFc9d1d9)
                                            )
                                        }
                                    }

                                    if (item.examReference.isNotEmpty()) {
                                        Text(
                                            text = "Historial Examen: ${item.examReference}",
                                            fontSize = 10.sp,
                                            color = Color.Gray,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(top = 8.dp),
                                            textAlign = TextAlign.End
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// 3. Exams Tab content and ongoing dynamic test wizard
@Composable
fun ExamCenterTabContent(viewModel: MainViewModel, isPremium: Boolean) {
    val context = LocalContext.current
    val activeExam = viewModel.activeExamModule

    if (activeExam == null) {
        // Module List Panel
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF3b1d1d)) // maroon warning card
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Simulador Oficial de Exámenes",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "En la versión gratuita puede iniciar cualquier test, pero únicamente completará hasta 3 exámenes totales para registrar historial. Adquiera Licencia Pro/Premium para exámenes infinitos sin límite reglamentario.",
                            fontSize = 11.sp,
                            color = Color(0xFFfbcfe8)
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Seleccione un Cuestionario para Iniciar:",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 15.sp
                )
            }

            items(Content.QUESTIONS.values.toList()) { module ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.startExam(module) }
                        .testTag("exam_module_card_${module.id}"),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF161b22)),
                    border = BorderStroke(1.dp, Color(0xFF30363d))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = module.icon, fontSize = 24.sp)
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = module.label,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = Color.White
                                )
                                Text(
                                    text = "${module.questions.size} Preguntas • Explicación y BOE",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Comenzar",
                            tint = Color.Gray
                        )
                    }
                }
            }
        }
    } else {
        // EXAM WIZARD ACTIVE STATE
        val currentQuestionList = activeExam.questions
        val currentQuestion = currentQuestionList.getOrNull(viewModel.currentQuestionIndex)

        if (viewModel.examCompleted || currentQuestion == null) {
            // Exam Report screen
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = "Success Trophy",
                    tint = Color(0xFFF1C40F),
                    modifier = Modifier.size(72.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Examen Completado",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 22.sp,
                    color = Color.White
                )

                Text(
                    text = activeExam.label,
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier.width(220.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF161b22))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Resultado",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "${viewModel.examCorrectCount} / ${currentQuestionList.size}",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                        val pass = viewModel.examCorrectCount.toFloat() / currentQuestionList.size.toFloat() >= 0.7f
                        Badge(containerColor = if (pass) Color(0xFF3fb950) else Color(0xFFf85149)) {
                            Text(
                                text = if (pass) "APROBADO" else "SUSPENSO",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Sugerencia: Se exige obtener mínimo 70% para pasar los exámenes de ASELaR.",
                            fontSize = 10.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { viewModel.activeExamModule = null },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("exam_finish_back_button")
                ) {
                    Text("Regresar al Centro de Exámenes", fontWeight = FontWeight.Bold)
                }
            }
        } else {
            // Test Ongoing
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
                    .testTag("exam_question_form")
            ) {
                // Header progress meter
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Examen: ${activeExam.label}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Color.Gray,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Pregunta ${viewModel.currentQuestionIndex + 1} de ${currentQuestionList.size}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                val examProgressRatio = (viewModel.currentQuestionIndex).toFloat() / currentQuestionList.size.toFloat()
                LinearProgressIndicator(
                    progress = { examProgressRatio },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = Color(0xFF21262d)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Question Statement card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF161b22)),
                    border = BorderStroke(1.dp, Color(0xFF30363d))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = currentQuestion.q,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Options Buttons Selection
                currentQuestion.opts.forEachIndexed { idx, option ->
                    val isSelected = viewModel.selectedOptionIndex == idx
                    val isCorrectIdx = currentQuestion.a == idx
                    val answered = viewModel.currentQuestionAnswered

                    // Color indicators according to status
                    val borderC = when {
                        answered && isSelected && isCorrectIdx -> Color(0xFF3fb950) // correct response highlighted green
                        answered && isSelected && !isCorrectIdx -> Color(0xFFf85149) // bad response highlighted red
                        answered && isCorrectIdx -> Color(0xFF3fb950) // indicate what was perfect choice
                        isSelected -> MaterialTheme.colorScheme.primary
                        else -> Color(0xFF30363d)
                    }

                    val containerC = when {
                        answered && isSelected && isCorrectIdx -> Color(0x1a3fb950)
                        answered && isSelected && !isCorrectIdx -> Color(0x1af85149)
                        answered && isCorrectIdx -> Color(0x1a3fb950)
                        isSelected -> Color(0x1558a6ff)
                        else -> Color(0xFF161b22)
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable(enabled = !answered) { viewModel.selectedOptionIndex = idx }
                            .testTag("exam_option_card_$idx"),
                        colors = CardDefaults.cardColors(containerColor = containerC),
                        border = BorderStroke(if (isSelected || answered) 2.dp else 1.dp, borderC)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { if (!answered) viewModel.selectedOptionIndex = idx },
                                enabled = !answered,
                                colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = option, fontSize = 13.sp, color = Color.White)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Detailed Explanations and BOE citations appearing in real-time on answer checkout
                if (viewModel.currentQuestionAnswered) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                            .testTag("exam_answer_feedback_panel"),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1f262d)),
                        border = BorderStroke(1.dp, Color(0xFF30363d))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (viewModel.isAnswerCorrect == true) Icons.Default.CheckCircle else Icons.Default.Cancel,
                                    contentDescription = "Status Icon",
                                    tint = if (viewModel.isAnswerCorrect == true) Color(0xFF3fb950) else Color(0xFFf85149),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (viewModel.isAnswerCorrect == true) "¡Respuesta Correcta!" else "Respuesta Incorrecta",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = if (viewModel.isAnswerCorrect == true) Color(0xFF3fb950) else Color(0xFFf85149)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Explicación Técnica: ${currentQuestion.exp}",
                                fontSize = 12.sp,
                                color = Color(0xFFc9d1d9)
                            )
                            if (currentQuestion.ref.isNotEmpty()) {
                                Text(
                                    text = "Referencia Oficial: ${currentQuestion.ref}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Answer check button OR next logic
                if (!viewModel.currentQuestionAnswered) {
                    Button(
                        onClick = {
                            viewModel.submitAnswer()
                            if (viewModel.isAnswerCorrect == true) {
                                FeedbackManager.playCorrect(context)
                            } else {
                                FeedbackManager.playIncorrect(context)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("exam_submit_answer_button"),
                        enabled = viewModel.selectedOptionIndex != null
                    ) {
                        Text("Verificar Respuesta", fontWeight = FontWeight.Bold)
                    }
                } else {
                    Button(
                        onClick = { viewModel.nextQuestion() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("exam_next_question_button")
                    ) {
                        val isLast = viewModel.currentQuestionIndex + 1 == currentQuestionList.size
                        Text(
                            text = if (isLast) "Finalizar y Ver Informe" else "Siguiente Pregunta",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

// 4. Simulator tab content (Differential testing, ground loops, dialectic leakage)
@Composable
fun SimulatorTabContent(viewModel: MainViewModel) {
    val tabs = listOf("RCD", "EarthLoop", "Insulation")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Simulador Multifunción REBT",
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )
        Text(
            text = "Pruebas de idoneidad y puesta en marcha sobre esquemas ITCs simulados.",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Device Mode Tabs
        TabRow(
            selectedTabIndex = tabs.indexOf(viewModel.selectedTesterType),
            containerColor = Color(0xFF161b22),
            contentColor = Color.White
        ) {
            tabs.forEach { tab ->
                val label = when (tab) {
                    "RCD" -> "IID Diferencial"
                    "EarthLoop" -> "Bucle Tierra"
                    "Insulation" -> "Rigidez MΩ"
                    else -> tab
                }
                Tab(
                    selected = viewModel.selectedTesterType == tab,
                    onClick = { viewModel.selectedTesterType = tab },
                    text = { Text(label, fontSize = 11.sp, fontWeight = FontWeight.Bold) }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Testing Parameters panel
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF161b22)),
            border = BorderStroke(1.dp, Color(0xFF30363d))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Ajustes del Comprobador Técnico:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                when (viewModel.selectedTesterType) {
                    "RCD" -> {
                        // Slider limits or chips
                        Text("Corriente de fuga elegida (IΔn):", fontSize = 12.sp)
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            listOf("30", "300").forEach { limit ->
                                FilterChip(
                                    selected = viewModel.simLimitAmp == limit,
                                    onClick = { viewModel.simLimitAmp = limit },
                                    label = { Text("$limit mA", fontSize = 12.sp) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text("Ángulo de fase inyectado:", fontSize = 12.sp)
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            listOf("0", "180").forEach { angle ->
                                FilterChip(
                                    selected = viewModel.simPhaseAngle == angle,
                                    onClick = { viewModel.simPhaseAngle = angle },
                                    label = { Text("$angle°", fontSize = 12.sp) }
                                )
                            }
                        }
                    }
                    "EarthLoop" -> {
                        Text("Nivel de resistividad geológica (Tipo de Terreno):", fontSize = 12.sp)
                        Row(
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            listOf("Tierra vegetal", "Arcilla", "Grava").forEach { soil ->
                                FilterChip(
                                    selected = viewModel.simEarthSoilType == soil,
                                    onClick = { viewModel.simEarthSoilType = soil },
                                    label = { Text(soil, fontSize = 11.sp) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text("Número de picas metálicas en paralelo: ${viewModel.simEarthPicaCount}", fontSize = 12.sp)
                        Slider(
                            value = viewModel.simEarthPicaCount.toFloat(),
                            onValueChange = { viewModel.simEarthPicaCount = it.toInt() },
                            valueRange = 1f..10f,
                            steps = 8
                        )
                    }
                    "Insulation" -> {
                        Text("Tensión de comprobación inyectada (VCC):", fontSize = 12.sp)
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            listOf("500", "1000").forEach { testV ->
                                FilterChip(
                                    selected = viewModel.simInsulationVoltage == testV,
                                    onClick = { viewModel.simInsulationVoltage = testV },
                                    label = { Text("$testV VCC", fontSize = 12.sp) }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { viewModel.triggerSimulatorTest() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("simulator_run_test_button")
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Simulate")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("REALIZAR ENSAYO DEL CIRCUITO", fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Virtual Screen Display displaying live diagnostics
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF07090e)), // terminal black slate
            border = BorderStroke(2.dp, Color(0xFF161b22))
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF3fb950))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "DISPLAY LCD COMPROBADOR S-920",
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = Color(0xFF8b949e)
                        )
                    }
                    Badge(containerColor = Color(0xFF30363d)) {
                        Text("CALIBRADO", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Interactive measurements readout
                when (viewModel.selectedTesterType) {
                    "RCD" -> {
                        Text(
                            text = if (viewModel.simRcdTripTimeMs > 0) "${viewModel.simRcdTripTimeMs} ms" else "--- ms",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF3fb950),
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                        )
                        Text(
                            text = viewModel.simRcdStatus,
                            fontSize = 12.sp,
                            color = Color.White
                        )
                    }
                    "EarthLoop" -> {
                        Text(
                            text = if (viewModel.simEarthCalculatedResistance > 0.0) "${String.format("%.2f", viewModel.simEarthCalculatedResistance)} Ω" else "--- Ω",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF3fb950),
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                        )
                        Text(
                            text = viewModel.simEarthStatus,
                            fontSize = 12.sp,
                            color = Color.White
                        )
                    }
                    "Insulation" -> {
                        Text(
                            text = if (viewModel.simInsulationReadMegaohms > 0.0) "${String.format("%.2f", viewModel.simInsulationReadMegaohms)} MΩ" else "--- MΩ",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF3fb950),
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                        )
                        Text(
                            text = viewModel.simInsulationStatus,
                            fontSize = 12.sp,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = Color(0xFF21262d))
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Pruebas técnicas conforme al manual simplificado del Reglamento de Seguridad Industrial.",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

// 5. Electrical wire laboratory sizing and math routines
@Composable
fun LaboratorioCalculosTabContent(viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Calculadora de Cables y Dimensionamiento",
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )
        Text(
            text = "Calcula la sección obligatoria según ITC-BT-14/15/19 por caída de tensión y calentamiento Iz.",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Left controls panel
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF161b22)),
            border = BorderStroke(1.dp, Color(0xFF30363d))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Ajustes de la Carga y Acometida:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Phase switch
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Sistema de Alimentación:", fontSize = 13.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Monofásico (230V)", fontSize = 11.sp, color = if (!viewModel.labIsThreePhase) Color.White else Color.Gray)
                        Switch(
                            checked = viewModel.labIsThreePhase,
                            onCheckedChange = {
                                viewModel.labIsThreePhase = it
                                viewModel.runLaboratoryCalculation()
                            },
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .testTag("lab_phase_switch")
                        )
                        Text("Trifásico (400V)", fontSize = 11.sp, color = if (viewModel.labIsThreePhase) Color.White else Color.Gray)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Material selector
                Text("Metal del Conductor del Cable:", fontSize = 13.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    FilterChip(
                        selected = viewModel.labCableMaterial == "cobre",
                        onClick = {
                            viewModel.labCableMaterial = "cobre"
                            viewModel.runLaboratoryCalculation()
                        },
                        label = { Text("Cobre (Cu)", fontSize = 12.sp) },
                        modifier = Modifier.testTag("lab_chip_cobre")
                    )
                    FilterChip(
                        selected = viewModel.labCableMaterial == "aluminio",
                        onClick = {
                            viewModel.labCableMaterial = "aluminio"
                            viewModel.runLaboratoryCalculation()
                        },
                        label = { Text("Aluminio (Al)", fontSize = 12.sp) },
                        modifier = Modifier.testTag("lab_chip_aluminio")
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Installation mode selector
                Text("Método de canalización física:", fontSize = 13.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    FilterChip(
                        selected = viewModel.labInstallMethod == "tubo",
                        onClick = {
                            viewModel.labInstallMethod = "tubo"
                            viewModel.runLaboratoryCalculation()
                        },
                        label = { Text("Bajo tubo empotrado", fontSize = 12.sp) }
                    )
                    FilterChip(
                        selected = viewModel.labInstallMethod == "aire",
                        onClick = {
                            viewModel.labInstallMethod = "aire"
                            viewModel.runLaboratoryCalculation()
                        },
                        label = { Text("Al aire o bandeja", fontSize = 12.sp) }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = Color(0xFF21262d))
                Spacer(modifier = Modifier.height(16.dp))

                // Numerical inputs (Power, Length, Max tension Drop)
                OutlinedTextField(
                    value = viewModel.labPowerKw,
                    onValueChange = {
                        viewModel.labPowerKw = it
                        viewModel.runLaboratoryCalculation()
                    },
                    label = { Text("Potencia a suministrar (kW)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .testTag("lab_input_power"),
                    singleLine = true
                )

                OutlinedTextField(
                    value = viewModel.labLengthM,
                    onValueChange = {
                        viewModel.labLengthM = it
                        viewModel.runLaboratoryCalculation()
                    },
                    label = { Text("Longitud total de acometida (m)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .testTag("lab_input_length"),
                    singleLine = true
                )

                OutlinedTextField(
                    value = viewModel.labMaxDropPct,
                    onValueChange = {
                        viewModel.labMaxDropPct = it
                        viewModel.runLaboratoryCalculation()
                    },
                    label = { Text("Caída de tensión máxima admitida (%)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.runLaboratoryCalculation() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("lab_calculate_button")
                ) {
                    Text("FORZAR RECALCULO REBT", fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Calculated results panel
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1b222c)), // darker cyan/slate card
            border = BorderStroke(1.dp, Color(0xFF38444d))
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "CONFORMIDAD TÉCNICA OBTENIDA:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = Color(0xFF58a6ff)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "${viewModel.labCalculatedSection} mm²",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                        Text(
                            text = "Sección comercial reglamentaria",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFF3fb950).copy(alpha = 0.2f))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "APTO Iz=${viewModel.labIzCapacity} A",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF3fb950),
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = Color(0xFF30363d))
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = viewModel.labStatusMessage,
                    fontSize = 12.sp,
                    color = Color.White,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

// 6. Documents Center (Simulating PDF reference downloads and previews)
@Composable
fun DocumentsCenterTabContent(viewModel: MainViewModel, isPremium: Boolean) {
    var activeProgressDownloadItem by remember { mutableStateOf<String?>(null) }
    var itemDownloadPercentage by remember { mutableStateOf(0f) }
    var downloadedItemIds by remember { mutableStateOf(setOf<String>()) }
    var openPreViewItem by remember { mutableStateOf<SharedDocument?>(null) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Biblioteca de Documentos",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                Text(
                    text = "Planos resueltos e ITCs del Boletín Oficial.",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            IconButton(onClick = { viewModel.activeTab = "dashboard" }) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (openPreViewItem != null) {
            // Simulated local preview of downloaded item
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF161b22)),
                border = BorderStroke(1.dp, Color(0xFF30363d))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = openPreViewItem!!.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        IconButton(onClick = { openPreViewItem = null }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Gray)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.Black)
                            .border(1.dp, Color(0xFF30363d)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.InsertDriveFile,
                                contentDescription = "Doc reading Icon",
                                tint = Color.White,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Lectura Simétrica Activa: ${openPreViewItem!!.fileName}",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "Completado conforme al Real Decreto 842/2002",
                                fontSize = 10.sp,
                                color = Color(0xFF3fb950)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Descripción técnica: El archivo detalla las limitaciones constructivas y resistividades reglamentarias de este elemento bajo inspección OCA decenal.",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(Content.DOCUMENTS) { doc ->
                val isDownloading = activeProgressDownloadItem == doc.id
                val isDownloaded = downloadedItemIds.contains(doc.id)

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF161b22)),
                    border = BorderStroke(1.dp, Color(0xFF30363d))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = doc.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                                Text(
                                    text = "${doc.type} • ${doc.fileSize}",
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }

                            if (!isPremium) {
                                // Locked state
                                IconButton(onClick = { viewModel.activeTab = "billing" }) {
                                    Icon(Icons.Default.Lock, contentDescription = "Locked doc", tint = Color(0xFFF39C12))
                                }
                            } else {
                                // Premium actions
                                if (isDownloaded) {
                                    IconButton(
                                        onClick = { openPreViewItem = doc },
                                        modifier = Modifier.testTag("doc_view_button_${doc.id}")
                                    ) {
                                        Icon(Icons.Default.Visibility, contentDescription = "Preview", tint = Color(0xFF3fb950))
                                    }
                                } else if (isDownloading) {
                                    CircularProgressIndicator(
                                        progress = { itemDownloadPercentage },
                                        modifier = Modifier.size(24.dp),
                                        color = MaterialTheme.colorScheme.primary,
                                        strokeWidth = 3.dp
                                    )
                                } else {
                                    IconButton(
                                        onClick = {
                                            scope.launch {
                                                activeProgressDownloadItem = doc.id
                                                itemDownloadPercentage = 0f
                                                // Simulating download over time
                                                for (i in 1..10) {
                                                    kotlinx.coroutines.delay(120)
                                                    itemDownloadPercentage = i.toFloat() / 10f
                                                }
                                                downloadedItemIds = downloadedItemIds + doc.id
                                                activeProgressDownloadItem = null
                                            }
                                        },
                                        modifier = Modifier.testTag("doc_download_button_${doc.id}")
                                    ) {
                                        Icon(Icons.Default.Download, contentDescription = "Download", tint = Color.White)
                                    }
                                }
                            }
                        }

                        Text(
                            text = doc.description,
                            fontSize = 12.sp,
                            color = Color(0xFF8b949e),
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                }
            }
        }
    }
}

// 7. News Tab Content
@Composable
fun NewsCenterTabContent(viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Noticias Técnicas REBT 2026",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                Text(
                    text = "Avisos de industria, legislación y normativas complementarias.",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            IconButton(onClick = { viewModel.activeTab = "dashboard" }) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(Content.NEWS) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF161b22)),
                    border = BorderStroke(1.dp, Color(0xFF30363d))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Badge(containerColor = Color(0xFF21262d)) {
                                Text(
                                    item.categoryLabel,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            if (item.hot) {
                                Badge(containerColor = Color(0xFFFF5722)) {
                                    Text(
                                        text = "NUEVO",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 9.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 4.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = item.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color.White
                        )

                        Text(
                            text = item.summary,
                            fontSize = 12.sp,
                            color = Color(0xFF8b949e),
                            modifier = Modifier.padding(vertical = 6.dp)
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        HorizontalDivider(color = Color(0xFF21262d))
                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = item.content,
                            fontSize = 12.sp,
                            color = Color(0xFFc9d1d9),
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Lectura: ${item.readTime}",
                                fontSize = 10.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = item.date,
                                fontSize = 10.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

// 8. Support tickets center
@Composable
fun SupportCenterTabContent(viewModel: MainViewModel) {
    var subject by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("jj.terapias@gmail.com") }
    var name by remember { mutableStateOf("JJ Terapias") }
    var body by remember { mutableStateOf("") }
    
    var ticketSentState by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Centro de Soporte Técnico",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                Text(
                    text = "Pregunte dudas sobre cálculo de líneas o simulaciones.",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            IconButton(onClick = { viewModel.activeTab = "dashboard" }) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (ticketSentState) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0x223fb950)),
                border = BorderStroke(1.dp, Color(0x663fb950))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = "Sent", tint = Color(0xFF3fb950), modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("¡Ticket Enviado Exitosamente!", fontWeight = FontWeight.Bold, color = Color.White)
                    Text(
                        "Su solicitud se ha grabado de forma segura y un asesor técnico responderá a jj.terapias@gmail.com en menos de 24 horas laborables.",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(onClick = { ticketSentState = false }) {
                        Text("Crear otra consulta")
                    }
                }
            }
        } else {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF161b22)),
                border = BorderStroke(1.dp, Color(0xFF30363d))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nombre Completo") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .testTag("support_name_input"),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Correo Electrónico de Contacto") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .testTag("support_email_input"),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = subject,
                        onValueChange = { subject = it },
                        label = { Text("Tema o Consulta Técnica") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .testTag("support_subject_input"),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = body,
                        onValueChange = { body = it },
                        label = { Text("Explique su duda referente al REBT") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(vertical = 6.dp)
                            .testTag("support_body_input")
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            viewModel.sendSupportTicket(name, email, subject, body) {
                                ticketSentState = true
                                subject = ""
                                body = ""
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("support_send_button"),
                        enabled = name.isNotEmpty() && email.isNotEmpty() && subject.isNotEmpty() && body.isNotEmpty()
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "Send Icon")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("ENVIAR CONSULTA AL EQUIPO", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// 9. Subscriptions Portal content matching Google Play formulas
@Composable
fun SubscriptionPlansPortalContent(viewModel: MainViewModel, isPremium: Boolean, currentPlan: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Planes y Suscripción",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                Text(
                    text = "Conéctate mediante la pasarela de Google Play Billing.",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            IconButton(onClick = { viewModel.activeTab = "dashboard" }) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Plan status banner
        if (isPremium) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0x223fb950)),
                border = BorderStroke(1.dp, Color(0xFF3fb950))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "✓ Licencia Google Play Activa",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = Color(0xFF3fb950)
                    )
                    Text(
                        text = "Plan cargado: Google Play $currentPlan License. Dispone de acceso ilimitado sin cortes, calculadores térmicos absolutos y material de descargas BOE completas.",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = { viewModel.cancelOrDowngradeSubscription() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFf85149))
                    ) {
                        Text("Cancelar Licencia en Google Play Store", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        } else {
            // Free limits banner
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2d2218)),
                border = BorderStroke(1.dp, Color(0xFFFF9800))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Modo Gratuito Activo (Limitado)",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF9800),
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Dispone únicamente de simulaciones básicas y de un número restringido de exámenes almacenables en registro local. Adquiera una licencia para habilitar todos los servicios.",
                        fontSize = 11.sp,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Licencias Disponibles en Google Play:",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 15.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // License Plan Cards
        LicensePlanCard(
            title = "Licencia Pro Mensual",
            price = "€4.99 / mensual",
            features = listOf(
                "Remoción total de anuncios y banners",
                "Simulador de exámenes sin límites",
                "Acceso completo a calculadoras eléctricas",
                "Soporte directo prioritario en menos de 12 horas"
            ),
            buttonLabel = if (currentPlan == "pro") "Plan Actual Activo" else "Comprar Licencia Pro",
            buttonEnabled = !isPremium,
            badgeLabel = "Suscripción",
            badgeColor = Color(0xFF58a6ff),
            onClick = { viewModel.loadCheckoutSheet("pro", 4.99) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        LicensePlanCard(
            title = "Licencia Premium Completa",
            price = "€14.99 pago único",
            features = listOf(
                "Todo lo incluido en el Plan Pro",
                "Descargas ilimitadas de planos unifilares y BOE",
                "Habilitación de simulador de resistividad por capas",
                "Actualizaciones legislativas del boletín de por vida"
            ),
            buttonLabel = if (currentPlan == "premium") "Plan Premium Activo" else "Comprar Licencia Completa",
            buttonEnabled = !isPremium,
            badgeLabel = "Mejor Valor",
            badgeColor = Color(0xFFbc8cff),
            onClick = { viewModel.loadCheckoutSheet("premium", 14.99) }
        )
    }
}

@Composable
fun LicensePlanCard(
    title: String,
    price: String,
    features: List<String>,
    buttonLabel: String,
    buttonEnabled: Boolean,
    badgeLabel: String,
    badgeColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF161b22)),
        border = BorderStroke(1.dp, Color(0xFF30363d))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
                Badge(containerColor = badgeColor) {
                    Text(
                        text = badgeLabel,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Text(text = price, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(vertical = 4.dp))

            Spacer(modifier = Modifier.height(10.dp))

            features.forEach { feat ->
                Row(modifier = Modifier.padding(vertical = 3.dp)) {
                    Text(text = "✓ ", color = Color(0xFF3fb950), fontWeight = FontWeight.Bold)
                    Text(text = feat, fontSize = 12.sp, color = Color(0xFFc9d1d9))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = buttonEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = badgeColor,
                    disabledContainerColor = Color(0xFF21262d)
                )
            ) {
                Text(
                    text = buttonLabel,
                    fontWeight = FontWeight.Bold,
                    color = if (buttonEnabled) Color.White else Color.Gray
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GooglePlayCheckoutSheet(viewModel: MainViewModel) {
    val activity = LocalContext.current as? android.app.Activity
    if (viewModel.showCheckoutPlaySheet) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.showCheckoutPlaySheet = false },
            containerColor = Color(0xFFF3F4F6) // Google Play gray background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .testTag("google_play_bottom_sheet")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Play Store",
                            tint = Color(0xFF137333),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Google Play",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                    }
                    Text(
                        text = "jj.terapias@gmail.com",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = Color.LightGray)
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        val planTitle = if (viewModel.checkoutPlanType == "pro") {
                            "Licencia Pro Mensual"
                        } else {
                            "Licencia Premium Completa"
                        }
                        Text(
                            text = "EnginIA REBT - $planTitle",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                        Text(
                            text = "Acceso ilimitado a simuladores, exámenes y calculadoras.",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                    Text(
                        text = "€${viewModel.checkoutPrice}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CreditCard,
                                contentDescription = "Visa",
                                tint = Color(0xFF1976D2),
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Google Pay (Visa **** 8392)",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Black
                                )
                                Text(
                                    text = "Pago seguro mediante pasarela oficial de Google",
                                    fontSize = 10.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Cambiar",
                            tint = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        activity?.let {
                            viewModel.completeGooglePlayPurchase(it)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("play_confirm_purchase_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF137333)) // Play Green
                ) {
                    Text(
                        text = "Comprar de forma segura",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

