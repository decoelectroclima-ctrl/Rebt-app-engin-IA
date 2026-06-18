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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.testTag
import com.example.data.*
import com.example.ui.SyllabusVisualAid
import com.example.ui.FeedbackManager
import kotlinx.coroutines.launch
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import android.provider.OpenableColumns

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

    if (viewModel.showUserEmailDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.showUserEmailDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Autenticación",
                        tint = if (viewModel.isDarkTheme) Color(0xFF58a6ff) else Color(0xFF0969da),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Cuenta de Google",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (viewModel.isDarkTheme) Color.White else Color.Black
                    )
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Inicie sesión o simule su cuenta certificada de Google para sincronizar su progreso, comprobar suscripciones o validar accesos de control de la app.",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )

                    OutlinedTextField(
                        value = viewModel.inputEmailString,
                        onValueChange = { viewModel.inputEmailString = it },
                        label = { Text("Correo de Google (Gmail)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val parsedEmail = viewModel.inputEmailString.trim().lowercase()
                        viewModel.currentUserEmail = parsedEmail
                        if (parsedEmail == "decoelectroclima@gmail.com") {
                            viewModel.adminModeEnabled = true
                            viewModel.activeTab = "admin"
                        } else {
                            viewModel.adminModeEnabled = false
                            if (viewModel.activeTab == "admin") {
                                viewModel.activeTab = "dashboard"
                            }
                        }
                        viewModel.showUserEmailDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (viewModel.isDarkTheme) Color(0xFF58a6ff) else Color(0xFF0969da)
                    )
                ) {
                    Text("Autenticar", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.showUserEmailDialog = false }) {
                    Text("Cancelar", color = Color.Gray)
                }
            },
            containerColor = if (viewModel.isDarkTheme) Color(0xFF161b22) else Color.White,
            titleContentColor = if (viewModel.isDarkTheme) Color.White else Color.Black,
            textContentColor = if (viewModel.isDarkTheme) Color.White else Color.Black
        )
    }

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
                if (viewModel.currentUserEmail == "decoelectroclima@gmail.com") {
                    NavigationBarItem(
                        selected = viewModel.activeTab == "admin",
                        onClick = { viewModel.activeTab = "admin" },
                        icon = { Icon(Icons.Default.AdminPanelSettings, contentDescription = "Admin", tint = Color(0xFFF39C12)) },
                        label = { Text("Admin", fontSize = 11.sp, maxLines = 1, color = Color(0xFFF39C12)) },
                        colors = NavigationBarItemDefaults.colors(selectedIconColor = Color(0xFFF39C12))
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Floating Banner describing active status & active user email, clickable to simulate Google login
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (viewModel.isDarkTheme) Color(0xFF21262d) else Color(0xFFeaeef2))
                    .clickable {
                        viewModel.showUserEmailDialog = true
                        viewModel.inputEmailString = viewModel.currentUserEmail
                    }
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Usuario: ${viewModel.currentUserEmail}",
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
                    "admin" -> AdminPanelTabContent(viewModel)
                    "support" -> SupportCenterTabContent(viewModel)
                    "billing" -> SubscriptionPlansPortalContent(viewModel, isPremium, currentPlan)
                }
            }

            // Interactive rotating Advertisement frame matching the exact web functionality
            if (!isPremium) {
                val adIndex = viewModel.currentAdIndex.coerceIn(0, viewModel.adminAds.size.coerceAtLeast(1) - 1)
                val activeAd = if (viewModel.adminAds.isNotEmpty()) viewModel.adminAds[adIndex] else null
                val adTintColor = if (activeAd != null) {
                    try {
                        Color(android.graphics.Color.parseColor(activeAd.tintColor))
                    } catch (e: Exception) {
                        Color(0xFFF39C12)
                    }
                } else {
                    Color(0xFFf85149)
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .testTag("advertisement_banner_frame"),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = adTintColor.copy(alpha = 0.15f)),
                    border = BorderStroke(1.dp, adTintColor.copy(alpha = 0.4f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Campaign,
                            contentDescription = "Patrocinado",
                            tint = adTintColor,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "ANUNCIO REBT: " + (activeAd?.sponsor ?: "Certificadora Oficial"),
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = adTintColor,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = activeAd?.message ?: "Consiga la habilitación de instalador oficial. Remueva la publicidad en soporte.",
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
                            colors = ButtonDefaults.buttonColors(containerColor = adTintColor)
                        ) {
                            Text(activeAd?.ctaText ?: "Quitar", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Black)
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
    val context = LocalContext.current
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
                        // Avatar profile icon, clickable to verify Google Identity
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(if (viewModel.isDarkTheme) Color(0xFF58a6ff) else Color(0xFF0969da))
                                .clickable {
                                    viewModel.showUserEmailDialog = true
                                    viewModel.inputEmailString = viewModel.currentUserEmail
                                    FeedbackManager.playClick(context)
                                },
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
                        text = "Su progreso de preparación reglamentaria para el examen oficial de la certificadora / Industria 2026:",
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
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF161b22)),
                    border = BorderStroke(1.dp, Color(0xFF38444d))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "🌟", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Base de Exámenes Infinita (Gemini AI)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(0xFFffa000)
                            )
                        }
                        Text(
                            text = "Accede a exámenes dinámicos de la certificadora recopilados de foros de industria y simulados por Inteligencia Artificial en tiempo real sin límites.",
                            fontSize = 11.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        var aiKeyword by remember { mutableStateOf("Cálculo y secciones reglamentarias") }

                        OutlinedTextField(
                            value = aiKeyword,
                            onValueChange = { aiKeyword = it },
                            label = { Text("Tema o ITC de búsqueda", color = Color.Gray) },
                            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 13.sp),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().testTag("ai_keyword_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFFffa000),
                                unfocusedBorderColor = Color(0xFF30363d)
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Suggestion chips
                        Text("Temas sugeridos por foros de certificadora:", fontSize = 11.sp, color = Color.Gray)
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            val topics = listOf("ITC-BT-52 VE", "Puesta a Tierra", "ITC-BT-28 Locales")
                            topics.forEach { topic ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color(0xFF21262d))
                                        .clickable { aiKeyword = topic }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(topic, fontSize = 10.sp, color = Color.White)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        if (viewModel.isGeneratingAiExam) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(color = Color(0xFFffa000), modifier = Modifier.size(24.dp))
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("Extrayendo preguntas del foro de la certificadora en red...", fontSize = 12.sp, color = Color(0xFFffa000))
                            }
                        } else {
                            Button(
                                onClick = { viewModel.startAiDynamicExam(aiKeyword) },
                                modifier = Modifier.fillMaxWidth().testTag("ai_generate_button"),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFffa000), contentColor = Color.Black)
                            ) {
                                Text("GENERAR EXAMEN PERSONALIZADO", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }

                        viewModel.aiExamError?.let { err ->
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = err, color = Color.Red, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
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
                            text = "Sugerencia: Se exige obtener mínimo 70% para pasar los exámenes de la certificadora.",
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
            text = "Laboratorio Técnico de Cálculos REBT",
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )
        Text(
            text = "Herramientas de dimensionamiento homologado según el Reglamento Electrotécnico de Baja Tensión.",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Navigation sub-tabs
        ScrollableTabRow(
            selectedTabIndex = viewModel.labActiveSubTab,
            containerColor = Color(0xFF161b22),
            contentColor = Color.White,
            edgePadding = 0.dp,
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp))
        ) {
            Tab(
                selected = viewModel.labActiveSubTab == 0,
                onClick = { viewModel.labActiveSubTab = 0 },
                text = { Text("⚡ Conductores", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = viewModel.labActiveSubTab == 1,
                onClick = { viewModel.labActiveSubTab = 1 },
                text = { Text("🏢 Edificios", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = viewModel.labActiveSubTab == 2,
                onClick = { viewModel.labActiveSubTab = 2 },
                text = { Text("📺 Tubos", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = viewModel.labActiveSubTab == 3,
                onClick = { viewModel.labActiveSubTab = 3 },
                text = { Text("🌱 P. Tierra", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        when (viewModel.labActiveSubTab) {
            0 -> {
                // Sizing Conductors sub-tab
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF161b22)),
                    border = BorderStroke(1.dp, Color(0xFF30363d))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Ajustes de la Carga y Acometida (Sección):",
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

            1 -> {
                // Building load forecasting
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF161b22)),
                    border = BorderStroke(1.dp, Color(0xFF30363d))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Previsión de Cargas de Edificios (ITC-BT-10):",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = viewModel.foreDwellingsBasic,
                            onValueChange = {
                                viewModel.foreDwellingsBasic = it
                                viewModel.runBuildingForecastingCalculation()
                            },
                            label = { Text("Viviendas Electrificación Básica (5.75kW)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = viewModel.foreDwellingsElevated,
                            onValueChange = {
                                viewModel.foreDwellingsElevated = it
                                viewModel.runBuildingForecastingCalculation()
                            },
                            label = { Text("Viviendas Electrificación Elevada (9.2kW)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = viewModel.foreCommercialSqm,
                            onValueChange = {
                                viewModel.foreCommercialSqm = it
                                viewModel.runBuildingForecastingCalculation()
                            },
                            label = { Text("Locales Comerciales (m²)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = viewModel.foreGarageSqm,
                            onValueChange = {
                                viewModel.foreGarageSqm = it
                                viewModel.runBuildingForecastingCalculation()
                            },
                            label = { Text("Aparcamiento con ventilación mecánica (m²)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = viewModel.foreGeneralServicesKw,
                            onValueChange = {
                                viewModel.foreGeneralServicesKw = it
                                viewModel.runBuildingForecastingCalculation()
                            },
                            label = { Text("Servicios Generales (Ascensor, Escaleras...) (kW)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            singleLine = true
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Power forecasting results
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1b222c)),
                    border = BorderStroke(1.dp, Color(0xFF38444d))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "PREVISIÓN CARGA GLOBAL EDIFICIO:",
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
                                    text = "${String.format("%.2f", viewModel.foreCalculatedPowerKw)} kW",
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                                Text(
                                    text = "Potencia simultánea del enlace",
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFF58a6ff).copy(alpha = 0.2f))
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "${String.format("%.1f", viewModel.foreRecommendedIgaAmps)} A",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF58a6ff),
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        HorizontalDivider(color = Color(0xFF30363d))
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = viewModel.foreStatusMessage,
                            fontSize = 12.sp,
                            color = Color.White,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            2 -> {
                // Sizing tubes sub-tab
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF161b22)),
                    border = BorderStroke(1.dp, Color(0xFF30363d))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Diámetro de Tubos Protectores (ITC-BT-21):",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Sección de Conductores (mm²):", fontSize = 12.sp)
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            val sections = listOf("1.5", "2.5", "4.0", "6.0", "10.0", "16.0")
                            sections.forEach { sec ->
                                FilterChip(
                                    selected = viewModel.tubesConductorSec == sec,
                                    onClick = {
                                        viewModel.tubesConductorSec = sec
                                        viewModel.runTubeDiameterCalculation()
                                    },
                                    label = { Text(sec, fontSize = 11.sp) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text("Número de Conductores Activos:", fontSize = 12.sp)
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            val counts = listOf("2", "3", "4", "5")
                            counts.forEach { count ->
                                FilterChip(
                                    selected = viewModel.tubesConductorsCount == count,
                                    onClick = {
                                        viewModel.tubesConductorsCount = count
                                        viewModel.runTubeDiameterCalculation()
                                    },
                                    label = { Text(count, fontSize = 11.sp) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text("Tipo de Montaje Físico:", fontSize = 12.sp)
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            FilterChip(
                                selected = viewModel.tubesInstallMethod == "empotrado",
                                onClick = {
                                    viewModel.tubesInstallMethod = "empotrado"
                                    viewModel.runTubeDiameterCalculation()
                                },
                                label = { Text("Empotrado bajo obra", fontSize = 11.sp) }
                            )
                            FilterChip(
                                selected = viewModel.tubesInstallMethod == "superficial",
                                onClick = {
                                    viewModel.tubesInstallMethod = "superficial"
                                    viewModel.runTubeDiameterCalculation()
                                },
                                label = { Text("Superficial", fontSize = 11.sp) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Tube diameter calculation feedback
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1b222c)),
                    border = BorderStroke(1.dp, Color(0xFF38444d))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "DIÁMETRO DE TUBO EXIGIDO:",
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
                                    text = "Ø ${viewModel.tubesCalculatedDiameterMm} mm",
                                    fontSize = 34.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                                Text(
                                    text = "Diámetro nominal exterior mínimo",
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFF58a6ff).copy(alpha = 0.2f))
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "ITC-BT-21",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF58a6ff),
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        HorizontalDivider(color = Color(0xFF30363d))
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = viewModel.tubesStatusMessage,
                            fontSize = 12.sp,
                            color = Color.White,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            3 -> {
                // Grounding sub-tab
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF161b22)),
                    border = BorderStroke(1.dp, Color(0xFF30363d))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Resistencia de Toma a Tierra:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Resistividad estimada del terreno (Ω·m):", fontSize = 12.sp)
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            val items = listOf(
                                "100" to "Arcillas",
                                "200" to "T. Humoso",
                                "500" to "Arenas",
                                "1500" to "Terreno Rocoso"
                            )
                            items.forEach { (valStr, label) ->
                                FilterChip(
                                    selected = viewModel.earthSoilResistivity == valStr,
                                    onClick = {
                                        viewModel.earthSoilResistivity = valStr
                                        viewModel.runGroundingCalculation()
                                    },
                                    label = { Text("$valStr ($label)", fontSize = 10.sp) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text("Tipo de electrodo disipador:", fontSize = 12.sp)
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            FilterChip(
                                selected = viewModel.earthElectrodeType == "pica",
                                onClick = {
                                    viewModel.earthElectrodeType = "pica"
                                    viewModel.runGroundingCalculation()
                                },
                                label = { Text("Pica de cobre vertical", fontSize = 11.sp) }
                            )
                            FilterChip(
                                selected = viewModel.earthElectrodeType == "conductor",
                                onClick = {
                                    viewModel.earthElectrodeType = "conductor"
                                    viewModel.runGroundingCalculation()
                                },
                                label = { Text("Cable horizontal desnudo", fontSize = 11.sp) }
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = viewModel.earthElectrodeLength,
                            onValueChange = {
                                viewModel.earthElectrodeLength = it
                                viewModel.runGroundingCalculation()
                            },
                            label = { Text("Longitud active del electrodo (m)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Grounding resistance calculated result card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1b222c)),
                    border = BorderStroke(1.dp, Color(0xFF38444d))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "RESISTENCIA RESIDUAL CALCULADA:",
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
                                    text = "${String.format("%.2f", viewModel.earthCalculatedResistanceOhms)} Ω",
                                    fontSize = 34.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                                Text(
                                    text = "Valor ohmico calculado",
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(
                                        if (viewModel.earthCalculatedResistanceOhms < 37.0) Color(0xFF3fb950).copy(alpha = 0.2f)
                                        else Color(0xFFd29922).copy(alpha = 0.2f)
                                    )
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = if (viewModel.earthCalculatedResistanceOhms < 37.0) "APTO" else "REVISABLE",
                                    fontWeight = FontWeight.Bold,
                                    color = if (viewModel.earthCalculatedResistanceOhms < 37.0) Color(0xFF3fb950) else Color(0xFFd29922),
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        HorizontalDivider(color = Color(0xFF30363d))
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = viewModel.earthStatusMessage,
                            fontSize = 12.sp,
                            color = Color.White,
                            lineHeight = 18.sp
                        )
                    }
                }
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
    var openPreViewItem by remember { mutableStateOf<CustomDocumentEntity?>(null) }

    val scope = rememberCoroutineScope()
    val customDocs by viewModel.customDocumentsFlow.collectAsState()

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
                            if (openPreViewItem!!.uriString != null) {
                                Text(
                                    text = "Origen: PDF Cargado por Administrador",
                                    fontSize = 10.sp,
                                    color = Color(0xFFE2FCD4)
                                )
                            } else {
                                Text(
                                    text = "Completado conforme al Real Decreto 842/2002",
                                    fontSize = 10.sp,
                                    color = Color(0xFF3fb950)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Descripción técnica: ${openPreViewItem!!.description}",
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
            items(customDocs) { doc ->
                val isDownloading = activeProgressDownloadItem == doc.docId
                val isDownloaded = downloadedItemIds.contains(doc.docId)

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
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = doc.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = Color.White
                                    )
                                    if (doc.isCustom) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Badge(containerColor = Color(0xFFE67E22)) {
                                            Text("AÑADIDO", fontSize = 8.sp, color = Color.White)
                                        }
                                    }
                                }
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
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    if (isDownloaded) {
                                        IconButton(
                                            onClick = { openPreViewItem = doc },
                                            modifier = Modifier.testTag("doc_view_button_${doc.docId}")
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
                                                    activeProgressDownloadItem = doc.docId
                                                    itemDownloadPercentage = 0f
                                                    // Simulating download over time
                                                    for (i in 1..10) {
                                                        kotlinx.coroutines.delay(120)
                                                        itemDownloadPercentage = i.toFloat() / 10f
                                                    }
                                                    downloadedItemIds = downloadedItemIds + doc.docId
                                                    activeProgressDownloadItem = null
                                                }
                                            },
                                            modifier = Modifier.testTag("doc_download_button_${doc.docId}")
                                        ) {
                                            Icon(Icons.Default.Download, contentDescription = "Download", tint = Color.White)
                                        }
                                    }

                                    // Allow deleting custom admin uploads
                                    if (doc.isCustom) {
                                        IconButton(onClick = { viewModel.deleteCustomDocument(doc.docId) }) {
                                            Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                                        }
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
    val customNews by viewModel.customNewsFlow.collectAsState()

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
                    text = "Avisos de industria, legislación y normativas complementarias en TIEMPO REAL.",
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
            items(customNews) { item ->
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
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Badge(containerColor = Color(0xFF21262d)) {
                                    Text(
                                        item.categoryLabel,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                if (item.isCustom) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Badge(containerColor = Color(0x33F39C12)) {
                                        Text(
                                            "ALERTA ADMIN",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = Color(0xFFF39C12),
                                            modifier = Modifier.padding(horizontal = 4.dp)
                                        )
                                    }
                                }
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
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
                                if (item.isCustom) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    IconButton(
                                        onClick = { viewModel.deleteCustomNews(item.newsId) },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red, modifier = Modifier.size(16.dp))
                                    }
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
    var email by remember(viewModel.currentUserEmail) { mutableStateOf(viewModel.currentUserEmail) }
    var name by remember { mutableStateOf("Técnico REBT") }
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
                        "Su solicitud se ha grabado de forma segura y un asesor técnico responderá a $email en menos de 24 horas laborables.",
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
                        text = viewModel.currentUserEmail,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPanelTabContent(viewModel: MainViewModel) {
    val context = LocalContext.current
    val contentResolver = context.contentResolver
    val customDocs by viewModel.customDocumentsFlow.collectAsState()
    val customNews by viewModel.customNewsFlow.collectAsState()
    val leads by viewModel.userLeadsFlow.collectAsState()

    var activeAdminSection by remember { mutableStateOf("pdf") } // "pdf", "news", "ads", "leads"

    // Create lead form states
    var showAddLeadDialog by remember { mutableStateOf(false) }
    var newLeadName by remember { mutableStateOf("") }
    var newLeadEmail by remember { mutableStateOf("") }
    var newLeadPhone by remember { mutableStateOf("") }
    var newLeadCompany by remember { mutableStateOf("") }
    var newLeadProvince by remember { mutableStateOf("") }
    var newLeadPlan by remember { mutableStateOf("gratuito") }
    var newLeadPrice by remember { mutableStateOf("35.00") }

    // Search and filters
    var leadSearchQuery by remember { mutableStateOf("") }
    var leadFilterPlan by remember { mutableStateOf("todos") } // "todos", "gratuito", "pro", "premium"
    var leadFilterStatus by remember { mutableStateOf("todos") } // "todos", "disponible", "vendido"

    // Edit lead state
    var editingLead by remember { mutableStateOf<UserLeadEntity?>(null) }
    var showEditLeadDialog by remember { mutableStateOf(false) }
    var editLeadName by remember { mutableStateOf("") }
    var editLeadEmail by remember { mutableStateOf("") }
    var editLeadPhone by remember { mutableStateOf("") }
    var editLeadCompany by remember { mutableStateOf("") }
    var editLeadProvince by remember { mutableStateOf("") }
    var editLeadPlan by remember { mutableStateOf("gratuito") }
    var editLeadPrice by remember { mutableStateOf("35.00") }
    var editLeadIsSold by remember { mutableStateOf(false) }

    // PDF variables
    var pdfTitle by remember { mutableStateOf("") }
    var pdfDesc by remember { mutableStateOf("") }
    var pdfFileName by remember { mutableStateOf("") }
    var pdfFileSize by remember { mutableStateOf("1.2 MB") }
    var pdfType by remember { mutableStateOf("BOE") } // "BOE", "Esquema", "Calculadora"
    var selectedUriString by remember { mutableStateOf<String?>(null) }
    var docUploadSuccess by remember { mutableStateOf(false) }

    // News variables
    var newsTitle by remember { mutableStateOf("") }
    var newsSummary by remember { mutableStateOf("") }
    var newsContent by remember { mutableStateOf("") }
    var newsCategory by remember { mutableStateOf("borrador") } // "borrador", "ev", "autoconsumo", "inspecciones"
    var newsCategoryLabel by remember { mutableStateOf("Borrador REBT 2026") }
    var newsIsHot by remember { mutableStateOf(false) }
    var newsPublishSuccess by remember { mutableStateOf(false) }

    val docPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedUriString = uri.toString()
            var name = "convenio_tecnico.pdf"
            var sizeBytes = 0L
            try {
                contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                    val nameIdx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    val sizeIdx = cursor.getColumnIndex(OpenableColumns.SIZE)
                    if (cursor.moveToFirst()) {
                        if (nameIdx != -1) name = cursor.getString(nameIdx)
                        if (sizeIdx != -1) sizeBytes = cursor.getLong(sizeIdx)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            pdfFileName = name
            pdfTitle = name.substringBeforeLast(".").replace("_", " ").replace("-", " ")
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(java.util.Locale.getDefault()) else it.toString() }
            pdfFileSize = if (sizeBytes > 0) {
                val kb = sizeBytes / 1024
                if (kb > 1024) String.format("%.2f MB", kb.toFloat() / 1024f) else "$kb KB"
            } else "1.4 MB"
            docUploadSuccess = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Tab Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AdminPanelSettings,
                    contentDescription = "Admin icon",
                    tint = Color(0xFFF39C12),
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Panel de Control Administrativo",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Text(
                        text = "Base de datos infinita de reglamentos y noticias",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            IconButton(onClick = { viewModel.activeTab = "support" }) {
                Icon(Icons.Default.Close, contentDescription = "Back", tint = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Sector choice Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Button(
                onClick = { activeAdminSection = "pdf" },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (activeAdminSection == "pdf") Color(0xFFF39C12) else Color(0xFF161b22)
                )
            ) {
                Icon(Icons.Default.InsertDriveFile, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("PDFs", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            Button(
                onClick = { activeAdminSection = "news" },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (activeAdminSection == "news") Color(0xFFF39C12) else Color(0xFF161b22)
                )
            ) {
                Icon(Icons.Default.Announcement, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Noticias", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            Button(
                onClick = { activeAdminSection = "ads" },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (activeAdminSection == "ads") Color(0xFFF39C12) else Color(0xFF161b22)
                )
            ) {
                Icon(Icons.Default.Campaign, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Anuncios", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            Button(
                onClick = { activeAdminSection = "leads" },
                modifier = Modifier.weight(1.1f),
                contentPadding = PaddingValues(horizontal = 2.dp, vertical = 2.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (activeAdminSection == "leads") Color(0xFFF39C12) else Color(0xFF161b22)
                )
            ) {
                Icon(Icons.Default.People, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(3.dp))
                Text("Leads REBT", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (activeAdminSection == "pdf") {
            // PDF Management Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF161b22)),
                border = BorderStroke(1.dp, Color(0xFF30363d))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Cargar Nuevo Contenido PDF",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                    Text(
                        text = "Seleccione un archivo de su celular o rellene el formulario para ofrecer guías técnicas a los abonados.",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 14.dp)
                    )

                    Button(
                        onClick = { docPickerLauncher.launch("application/pdf") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Default.Upload, contentDescription = "Choose PDF")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("SELECCIONAR PDF DISPOSITIVO", fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    if (selectedUriString != null) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0x333fb950)),
                            border = BorderStroke(1.dp, Color(0x553fb950))
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = "Uri Match", tint = Color(0xFF3fb950))
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("¡Archivo PDF Enlazado Exitosamente!", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                    Text("Ruta: $selectedUriString", fontSize = 10.sp, color = Color.LightGray)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                    }

                    OutlinedTextField(
                        value = pdfTitle,
                        onValueChange = { pdfTitle = it },
                        label = { Text("Título del Documento") },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp).testTag("admin_pdf_title"),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = pdfDesc,
                        onValueChange = { pdfDesc = it },
                        label = { Text("Descripción Corta / Instrucciones") },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp).testTag("admin_pdf_desc")
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = pdfFileName,
                            onValueChange = { pdfFileName = it },
                            label = { Text("Nombre del Archivo") },
                            modifier = Modifier.weight(1f).padding(vertical = 5.dp).testTag("admin_pdf_file_name"),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = pdfFileSize,
                            onValueChange = { pdfFileSize = it },
                            label = { Text("Tamaño") },
                            modifier = Modifier.weight(0.5f).padding(vertical = 5.dp),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Tipo de Recurso:", fontSize = 12.sp, color = Color.Gray)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        listOf("BOE","Esquema","Calculadora").forEach { type ->
                            val isSelected = pdfType == type
                            FilterChip(
                                selected = isSelected,
                                onClick = { pdfType = type },
                                label = { Text(type, fontSize = 11.sp, color = if (isSelected) Color.Black else Color.White) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFFF39C12)
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    if (docUploadSuccess) {
                        Text(
                            text = "✓ ¡Documento PDF subido al servidor correctamente!",
                            color = Color(0xFF3fb950),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.addCustomDocument(
                                title = pdfTitle,
                                description = pdfDesc,
                                fileName = pdfFileName,
                                fileSize = pdfFileSize,
                                type = pdfType,
                                uriString = selectedUriString
                            )
                            docUploadSuccess = true
                            pdfTitle = ""
                            pdfDesc = ""
                            pdfFileName = ""
                            pdfFileSize = "1.2 MB"
                            selectedUriString = null
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp).testTag("admin_pdf_submit_btn"),
                        enabled = pdfTitle.isNotEmpty() && pdfDesc.isNotEmpty() && pdfFileName.isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF39C12))
                    ) {
                        Icon(Icons.Default.Upload, contentDescription = "Publish doc")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("COMPARTIR PDF CON SUSCRIPTORES", fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Uploaded list
            val customDocList = customDocs.filter { it.isCustom }
            if (customDocList.isNotEmpty()) {
                Text("Documentos Añadidos por el Admin (${customDocList.size}):", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                customDocList.forEach { doc ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF21262d))
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(doc.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text("${doc.type} • ${doc.fileName} (${doc.fileSize})", color = Color.Gray, fontSize = 11.sp)
                            }
                            IconButton(onClick = { viewModel.deleteCustomDocument(doc.docId) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                            }
                        }
                    }
                }
            }
        } else if (activeAdminSection == "news") {
            // News Management Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF161b22)),
                border = BorderStroke(1.dp, Color(0xFF30363d))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Crear Alerta / Noticia REBT 2026",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                    Text(
                        text = "Toda noticia debe estar estrictamente centrada y redactada conforme a los borradores o especificaciones del reglamento REBT 2026.",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    OutlinedTextField(
                        value = newsTitle,
                        onValueChange = { newsTitle = it },
                        label = { Text("Título de Noticia REBT 2026") },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp).testTag("admin_news_title"),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = newsSummary,
                        onValueChange = { newsSummary = it },
                        label = { Text("Resumen Rápido (Línea de Enlace / ITC)") },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp).testTag("admin_news_summary"),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = newsContent,
                        onValueChange = { newsContent = it },
                        label = { Text("Desarrollo Técnico Oficial y Previsión") },
                        modifier = Modifier.fillMaxWidth().height(120.dp).padding(vertical = 5.dp).testTag("admin_news_content")
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Categoría REBT 2026:", fontSize = 12.sp, color = Color.Gray)
                    Row(
                        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val categories = listOf(
                            Triple("borrador", "Borrador REBT 2026", "Borrador"),
                            Triple("ev", "Vehículo Eléctrico (ITC-52)", "Coche Eléctrico"),
                            Triple("autoconsumo", "Autoconsumo ITC-BT-40", "Autoconsumo"),
                            Triple("inspecciones", "Inspección OCA 2026", "Inspecciones")
                        )
                        categories.forEach { (cat, label, chipName) ->
                            val isSelected = newsCategory == cat
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    newsCategory = cat
                                    newsCategoryLabel = label
                                },
                                label = { Text(chipName, fontSize = 11.sp, color = if (isSelected) Color.Black else Color.White) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFFF39C12)
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = newsIsHot,
                            onCheckedChange = { newsIsHot = it },
                            colors = CheckboxDefaults.colors(checkedColor = Color(0xFFFF5722))
                        )
                        Text("Noticia Destacada de Impacto (Sello NUEVO / HOT)", fontSize = 12.sp, color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (newsPublishSuccess) {
                        Text(
                            text = "✓ ¡Noticia REBT 2026 compartida en tiempo real!",
                            color = Color(0xFF3fb950),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.addCustomNews(
                                title = newsTitle,
                                summary = newsSummary,
                                content = newsContent,
                                category = newsCategory,
                                categoryLabel = newsCategoryLabel,
                                hot = newsIsHot
                            )
                            newsPublishSuccess = true
                            newsTitle = ""
                            newsSummary = ""
                            newsContent = ""
                            newsIsHot = false
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp).testTag("admin_news_submit_btn"),
                        enabled = newsTitle.isNotEmpty() && newsSummary.isNotEmpty() && newsContent.isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF39C12))
                    ) {
                        Icon(Icons.Default.Announcement, contentDescription = "Publish news")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("PUBLICAR EN TIEMPO REAL REBT 2026", fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Custom news list
            val customNewsList = customNews.filter { it.isCustom }
            if (customNewsList.isNotEmpty()) {
                Text("Noticias Añadidas por el Admin (${customNewsList.size}):", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                customNewsList.forEach { news ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF21262d))
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(news.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text("${news.categoryLabel} • ${news.date}", color = Color.Gray, fontSize = 11.sp)
                            }
                            IconButton(onClick = { viewModel.deleteCustomNews(news.newsId) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                            }
                        }
                    }
                }
            }
        } else if (activeAdminSection == "ads") {
            // Advertising Administration section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF161b22)),
                border = BorderStroke(1.dp, Color(0xFF30363d))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Controlador de Anuncios y Patrocinadores",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                    Text(
                        text = "Monetice la aplicación de manera inteligente. Altere los anunciantes del REBT que se despliegan para usuarios de la versión gratuita.",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 14.dp)
                    )

                    // Switch row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Rotación Dinámica Activa", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text("Si está activo, el banner rotará automáticamente cada 5 segundos de patrocinador.", color = Color.Gray, fontSize = 11.sp)
                        }
                        Switch(
                            checked = viewModel.adsIsDynamic,
                            onCheckedChange = { viewModel.adsIsDynamic = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(0xFFF39C12),
                                checkedTrackColor = Color(0x66F39C12)
                            )
                        )
                    }

                    if (!viewModel.adsIsDynamic) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Anuncio Estático Fijado:", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            viewModel.adminAds.forEachIndexed { idx, ad ->
                                val isSelected = viewModel.currentAdIndex == idx
                                FilterChip(
                                    selected = isSelected,
                                    onClick = {
                                        viewModel.selectedStaticAdIndex = idx
                                        viewModel.currentAdIndex = idx
                                    },
                                    label = { Text(ad.sponsor, fontSize = 10.sp, color = if (isSelected) Color.Black else Color.White) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = Color(0xFFF39C12)
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = Color(0xFF30363d))
                    Spacer(modifier = Modifier.height(14.dp))

                    Text("Editar Patrocinadores Activos (Real-Time):", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(10.dp))

                    viewModel.adminAds.forEach { ad ->
                        var showDetails by remember { mutableStateOf(false) }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF21262d)),
                            border = BorderStroke(1.dp, Color(0xFF30363d))
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        val adColor = try { Color(android.graphics.Color.parseColor(ad.tintColor)) } catch(e: Exception) { Color(0xFFF39C12) }
                                        Box(modifier = Modifier.size(10.dp).background(adColor, CircleShape))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(ad.sponsor, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    }
                                    IconButton(
                                        onClick = { showDetails = !showDetails },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (showDetails) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                            contentDescription = "Expand",
                                            tint = Color.Gray
                                        )
                                    }
                                }

                                if (showDetails) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    OutlinedTextField(
                                        value = ad.sponsor,
                                        onValueChange = { ad.sponsor = it },
                                        label = { Text("Nombre Patrocinador", fontSize = 11.sp) },
                                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                        singleLine = true
                                    )
                                    OutlinedTextField(
                                        value = ad.message,
                                        onValueChange = { ad.message = it },
                                        label = { Text("Mensaje Publicitario", fontSize = 11.sp) },
                                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                        maxLines = 3
                                    )
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        OutlinedTextField(
                                            value = ad.ctaText,
                                            onValueChange = { ad.ctaText = it },
                                            label = { Text("Botón (CTA)", fontSize = 11.sp) },
                                            modifier = Modifier.weight(1f).padding(vertical = 4.dp),
                                            singleLine = true
                                        )
                                        OutlinedTextField(
                                            value = ad.tintColor,
                                            onValueChange = { ad.tintColor = it },
                                            label = { Text("Color Hex", fontSize = 11.sp) },
                                            modifier = Modifier.weight(1f).padding(vertical = 4.dp),
                                            singleLine = true
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (activeAdminSection == "leads") {
            // Leads Management Block
            val filteredLeads = leads.filter { lead ->
                val matchesSearch = lead.name.contains(leadSearchQuery, ignoreCase = true) ||
                        lead.email.contains(leadSearchQuery, ignoreCase = true) ||
                        lead.companyName.contains(leadSearchQuery, ignoreCase = true) ||
                        lead.province.contains(leadSearchQuery, ignoreCase = true) ||
                        lead.phoneNumber.contains(leadSearchQuery, ignoreCase = true)
                
                val matchesPlan = leadFilterPlan == "todos" || lead.subscriptionPlan == leadFilterPlan
                val matchesStatus = leadFilterStatus == "todos" || 
                        (leadFilterStatus == "disponible" && !lead.isSold) || 
                        (leadFilterStatus == "vendido" && lead.isSold)

                matchesSearch && matchesPlan && matchesStatus
            }

            // Calculations
            val totalCount = leads.size
            val totalRevenue = leads.filter { it.isSold }.sumOf { it.leadPrice }
            val potentialRevenue = leads.filter { !it.isSold }.sumOf { it.leadPrice }

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                
                // Indicators Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF21262d)),
                        border = BorderStroke(1.dp, Color(0xFF30363d))
                    ) {
                        Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Total Base", fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                            Text("$totalCount Leads", fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }

                    Card(
                        modifier = Modifier.weight(1.2f),
                        colors = CardDefaults.cardColors(containerColor = Color(0x333fb950)),
                        border = BorderStroke(1.dp, Color(0xFF3fb950).copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Vendido (Leads)", fontSize = 10.sp, color = Color(0xFF3fb950), fontWeight = FontWeight.Bold)
                            Text(String.format("%.2f€", totalRevenue), fontSize = 14.sp, color = Color(0xFF3fb950), fontWeight = FontWeight.Bold)
                        }
                    }

                    Card(
                        modifier = Modifier.weight(1.2f),
                        colors = CardDefaults.cardColors(containerColor = Color(0x22f39c12)),
                        border = BorderStroke(1.dp, Color(0xFFf39c12).copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Disponibles", fontSize = 10.sp, color = Color(0xFFf39c12), fontWeight = FontWeight.Bold)
                            Text(String.format("%.2f€", potentialRevenue), fontSize = 14.sp, color = Color(0xFFf39c12), fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // Control panel
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF161b22)),
                    border = BorderStroke(1.dp, Color(0xFF30363d))
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Base de Datos Comercial", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
                            Button(
                                onClick = {
                                    newLeadName = ""
                                    newLeadEmail = ""
                                    newLeadPhone = ""
                                    newLeadCompany = ""
                                    newLeadProvince = ""
                                    newLeadPlan = "gratuito"
                                    newLeadPrice = "35.00"
                                    showAddLeadDialog = true
                                },
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF39C12))
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("AÑADIR LEAD", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }

                        Text(
                            text = "Gestione las suscripciones, correos certificados de Google, y vende la información de instaladores como leads profesionales para empresas de suministros REBT 2026.",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )

                        // Search Field
                        OutlinedTextField(
                            value = leadSearchQuery,
                            onValueChange = { leadSearchQuery = it },
                            placeholder = { Text("Buscar por email, nombre, provincia...", fontSize = 12.sp) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) }
                        )

                        // Plan filter
                        Column {
                            Text("Filtrar por Suscripción:", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                            Row(
                                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                listOf("todos", "gratuito", "pro", "premium").forEach { plan ->
                                    val isSelected = leadFilterPlan == plan
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = { leadFilterPlan = plan },
                                        label = { Text(plan.uppercase(), fontSize = 10.sp) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = Color(0xFF58a6ff),
                                            selectedLabelColor = Color.Black
                                        )
                                    )
                                }
                            }
                        }

                        // Status filter
                        Column {
                            Text("Filtrar por Disponibilidad Comercial:", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                            Row(
                                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                listOf("todos", "disponible", "vendido").forEach { status ->
                                    val isSelected = leadFilterStatus == status
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = { leadFilterStatus = status },
                                        label = { Text(status.uppercase(), fontSize = 10.sp) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = if (status == "vendido") Color(0xFFF39C12) else Color(0xFF3fb950),
                                            selectedLabelColor = Color.Black
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                // Leads list view
                if (filteredLeads.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No se encontraron leads con los filtros activos.", fontSize = 12.sp, color = Color.Gray)
                    }
                } else {
                    filteredLeads.forEach { lead ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF21262d)),
                            border = BorderStroke(1.dp, if (lead.isSold) Color(0xFFF39C12).copy(alpha = 0.4f) else Color(0xFF30363d))
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                // Title row: Name & Province
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(lead.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        Text(
                                            text = if (lead.companyName.isNotEmpty()) "${lead.companyName} (${lead.province})" else "Instalador Autónomo (${lead.province})",
                                            fontSize = 11.sp,
                                            color = Color.LightGray
                                        )
                                    }

                                    // Price / Value tag
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                color = if (lead.isSold) Color(0xFFF39C12).copy(alpha = 0.15f) else Color(0xFF3fb950).copy(alpha = 0.15f),
                                                shape = RoundedCornerShape(4.dp)
                                            )
                                            .padding(horizontal = 6.dp, vertical = 3.dp)
                                    ) {
                                        Text(
                                            text = String.format("%.2f€", lead.leadPrice),
                                            color = if (lead.isSold) Color(0xFFF39C12) else Color(0xFF3fb950),
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    }
                                }

                                HorizontalDivider(color = Color(0xFF30363d))

                                // Contact info details (Email & Phone)
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.Email, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(12.dp))
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(lead.email, fontSize = 11.sp, color = Color.LightGray)
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.Phone, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(12.dp))
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(lead.phoneNumber, fontSize = 11.sp, color = Color(0xFF58a6ff), fontWeight = FontWeight.Bold)
                                        }
                                    }

                                    // Badges
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        // Plan badge
                                        val planColor = when (lead.subscriptionPlan.lowercase()) {
                                            "premium" -> Color(0xFFF1C40F)
                                            "pro" -> Color(0xFF3498DB)
                                            else -> Color.Gray
                                        }
                                        Box(
                                            modifier = Modifier
                                                .background(planColor.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                                .border(1.dp, planColor.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text(lead.subscriptionPlan.uppercase(), fontSize = 8.sp, color = planColor, fontWeight = FontWeight.Bold)
                                        }

                                        // Status badge
                                        Box(
                                            modifier = Modifier
                                                .background(
                                                    color = if (lead.isSold) Color(0x33F39C12) else Color(0x333fb950),
                                                    shape = RoundedCornerShape(4.dp)
                                                )
                                                .border(
                                                    width = 1.dp,
                                                    color = if (lead.isSold) Color(0x66F39C12) else Color(0x663fb950),
                                                    shape = RoundedCornerShape(4.dp)
                                                )
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = if (lead.isSold) "VENDIDO" else "DISPONIBLE",
                                                fontSize = 8.sp,
                                                color = if (lead.isSold) Color(0xFFF39C12) else Color(0xFF3fb950),
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }

                                // Actions Row
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Button(
                                            onClick = {
                                                editingLead = lead
                                                editLeadName = lead.name
                                                editLeadEmail = lead.email
                                                editLeadPhone = lead.phoneNumber
                                                editLeadCompany = lead.companyName
                                                editLeadProvince = lead.province
                                                editLeadPlan = lead.subscriptionPlan
                                                editLeadPrice = lead.leadPrice.toString()
                                                editLeadIsSold = lead.isSold
                                                showEditLeadDialog = true
                                            },
                                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 1.dp),
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF30363d)),
                                            modifier = Modifier.height(28.dp)
                                        ) {
                                            Icon(Icons.Default.Edit, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(12.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("EDITAR", fontSize = 10.sp, color = Color.White)
                                        }

                                        IconButton(
                                            onClick = {
                                                viewModel.deleteUserLead(lead.id)
                                                FeedbackManager.playClick(context)
                                            },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red.copy(alpha = 0.8f), modifier = Modifier.size(16.dp))
                                        }
                                    }

                                    if (!lead.isSold) {
                                        Button(
                                            onClick = {
                                                viewModel.updateUserLead(lead.copy(isSold = true))
                                                FeedbackManager.playClick(context)
                                            },
                                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 2.dp),
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3fb950)),
                                            modifier = Modifier.height(28.dp)
                                        ) {
                                            Icon(Icons.Default.Paid, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("VENDER LEAD", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                        }
                                    } else {
                                        Text(
                                            text = "Ingreso cobrado",
                                            fontSize = 11.sp,
                                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                            color = Color.Gray
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Dialog definitions
            if (showAddLeadDialog) {
                AlertDialog(
                    onDismissRequest = { showAddLeadDialog = false },
                    title = { Text("Añadir Nuevo Lead de Usuario", color = Color.White, fontWeight = FontWeight.Bold) },
                    text = {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.verticalScroll(rememberScrollState())
                        ) {
                            OutlinedTextField(
                                value = newLeadName,
                                onValueChange = { newLeadName = it },
                                label = { Text("Nombre Completo") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = newLeadEmail,
                                onValueChange = { newLeadEmail = it },
                                label = { Text("Email (Certificado Google)") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = newLeadPhone,
                                onValueChange = { newLeadPhone = it },
                                label = { Text("Teléfono de Contacto") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = newLeadCompany,
                                onValueChange = { newLeadCompany = it },
                                label = { Text("Empresa Suministros") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = newLeadProvince,
                                onValueChange = { newLeadProvince = it },
                                label = { Text("Provincia / Zona") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = newLeadPrice,
                                onValueChange = { newLeadPrice = it },
                                label = { Text("Precio de Venta (€)") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Column {
                                Text("Nivel de Suscripción:", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    listOf("gratuito", "pro", "premium").forEach { plan ->
                                        val isSel = newLeadPlan == plan
                                        FilterChip(
                                            selected = isSel,
                                            onClick = { newLeadPlan = plan },
                                            label = { Text(plan.uppercase(), fontSize = 10.sp) },
                                            colors = FilterChipDefaults.filterChipColors(
                                                selectedContainerColor = Color(0xFFF39C12)
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                if (newLeadName.isNotEmpty() && newLeadEmail.isNotEmpty()) {
                                    val price = newLeadPrice.toDoubleOrNull() ?: 35.00
                                    viewModel.insertUserLead(
                                        email = newLeadEmail.trim().lowercase(),
                                        name = newLeadName.trim(),
                                        plan = newLeadPlan,
                                        phone = newLeadPhone.trim(),
                                        company = newLeadCompany.trim(),
                                        province = newLeadProvince.trim().ifEmpty { "Madrid" },
                                        isSold = false,
                                        price = price
                                    )
                                    showAddLeadDialog = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF39C12))
                        ) {
                            Text("Guardar Lead Upgrade", color = Color.White)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showAddLeadDialog = false }) {
                            Text("Cancelar", color = Color.Gray)
                        }
                    },
                    containerColor = Color(0xFF161b22),
                    titleContentColor = Color.White,
                    textContentColor = Color.White
                )
            }

            if (showEditLeadDialog && editingLead != null) {
                AlertDialog(
                    onDismissRequest = { showEditLeadDialog = false },
                    title = { Text("Modificar Lead / Suscripción", color = Color.White, fontWeight = FontWeight.Bold) },
                    text = {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.verticalScroll(rememberScrollState())
                        ) {
                            OutlinedTextField(
                                value = editLeadName,
                                onValueChange = { editLeadName = it },
                                label = { Text("Nombre Completo") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = editLeadEmail,
                                onValueChange = { editLeadEmail = it },
                                label = { Text("Email google") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = editLeadPhone,
                                onValueChange = { editLeadPhone = it },
                                label = { Text("Teléfono") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = editLeadCompany,
                                onValueChange = { editLeadCompany = it },
                                label = { Text("Empresa") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = editLeadProvince,
                                onValueChange = { editLeadProvince = it },
                                label = { Text("Provincia") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = editLeadPrice,
                                onValueChange = { editLeadPrice = it },
                                label = { Text("Valor Comercial (€)") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Lead Vendido (Lead Cerrado)", color = Color.White, fontSize = 12.sp)
                                Switch(
                                    checked = editLeadIsSold,
                                    onCheckedChange = { editLeadIsSold = it },
                                    colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFFF39C12))
                                )
                            }
                            Column {
                                Text("Plan de Suscripción:", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    listOf("gratuito", "pro", "premium").forEach { plan ->
                                        val isSel = editLeadPlan == plan
                                        FilterChip(
                                            selected = isSel,
                                            onClick = { editLeadPlan = plan },
                                            label = { Text(plan.uppercase(), fontSize = 10.sp) },
                                            colors = FilterChipDefaults.filterChipColors(
                                                selectedContainerColor = Color(0xFFF39C12)
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                val currentEditing = editingLead
                                if (currentEditing != null && editLeadName.isNotEmpty()) {
                                    val price = editLeadPrice.toDoubleOrNull() ?: currentEditing.leadPrice
                                    viewModel.updateUserLead(
                                        currentEditing.copy(
                                            name = editLeadName.trim(),
                                            email = editLeadEmail.trim().lowercase(),
                                            phoneNumber = editLeadPhone.trim(),
                                            companyName = editLeadCompany.trim(),
                                            province = editLeadProvince.trim(),
                                            subscriptionPlan = editLeadPlan,
                                            leadPrice = price,
                                            isSold = editLeadIsSold
                                        )
                                    )
                                    showEditLeadDialog = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF39C12))
                        ) {
                            Text("Guardar Cambios", color = Color.White)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showEditLeadDialog = false }) {
                            Text("Cancelar", color = Color.Gray)
                        }
                    },
                    containerColor = Color(0xFF161b22),
                    titleContentColor = Color.White,
                    textContentColor = Color.White
                )
            }
        }
    }
}

