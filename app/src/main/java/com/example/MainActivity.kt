package com.example

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.FeedbackManager
import com.example.ui.screens.analytics.AnalyticsScreen
import com.example.ui.screens.dashboard.DashboardScreen
import com.example.ui.screens.exams.ExamsScreen
import com.example.ui.screens.laboratory.LaboratoryScreen
import com.example.ui.screens.reminders.RemindersScreen
import com.example.ui.screens.settings.SettingsScreen
import com.example.ui.screens.study.StudyScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        com.example.data.QuestionValidator.validateOnDebugStartup()
        enableEdgeToEdge()
        val navigateTarget = intent?.getStringExtra("navigate_to")
        setContent {
            val viewModel: MainViewModel = viewModel()
            LaunchedEffect(navigateTarget) {
                if (!navigateTarget.isNullOrBlank()) {
                    viewModel.activeTab = navigateTarget
                }
            }
            EnginIaAppTheme(isDark = viewModel.isDarkTheme) {
                MainAppLayout(viewModel)
            }
        }
    }
}

// Custom Technical Material 3 Color Schemes
@Composable
fun EnginIaAppTheme(isDark: Boolean = true, content: @Composable () -> Unit) {
    val darkColorScheme = darkColorScheme(
        primary = Color(0xFF58A6FF), // Electric Tech Blue
        secondary = Color(0xFFBC8CFF), // Regal Violet
        tertiary = Color(0xFF3FB950), // Ground Green
        background = Color(0xFF0D1117), // Deep Navy Slate
        surface = Color(0xFF161B22), // Technical Dark Surface
        onPrimary = Color.Black,
        onSecondary = Color.Black,
        onBackground = Color(0xFFC9D1D9),
        onSurface = Color(0xFFC9D1D9),
        surfaceVariant = Color(0xFF21262D),
        onSurfaceVariant = Color(0xFF8B949E)
    )
    val lightColorScheme = lightColorScheme(
        primary = Color(0xFF0969DA), // Deep Tech Blue
        secondary = Color(0xFF8250DF), // Purple Accent
        tertiary = Color(0xFF1A7F37), // Ground Green
        background = Color(0xFFF6F8FA), // Technical Clean White
        surface = Color(0xFFFFFFFF),
        onPrimary = Color.White,
        onSecondary = Color.White,
        onBackground = Color(0xFF24292F),
        onSurface = Color(0xFF24292F),
        surfaceVariant = Color(0xFFEAEFF2),
        onSurfaceVariant = Color(0xFF57606A)
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
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(context, "Notificaciones activadas correctamente", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        if (android.os.Build.VERSION.SDK_INT >= 33) {
            if (context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    val subscription by viewModel.subscriptionFlow.collectAsState()
    val isPremium = subscription?.isActive == true

    val remindersList by viewModel.remindersFlow.collectAsState()
    val pendingReminders = remindersList.count { it.status == "Pendiente" || (it.status != "Completado" && it.dueDate < System.currentTimeMillis()) }

    var lastBackPressTime by remember { mutableLongStateOf(0L) }
    var showExitConfirmationDialog by remember { mutableStateOf(false) }

    // Handle back button:
    // 1. If inside active exam, prompt before exiting exam
    // 2. If inside sub-screens, return to dashboard
    // 3. If on dashboard: double tap within 2s to exit directly, or show confirmation dialog
    BackHandler {
        if (viewModel.activeExamModule != null) {
            viewModel.exitActiveExam()
        } else if (viewModel.activeTab != "dashboard") {
            viewModel.activeTab = "dashboard"
        } else {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastBackPressTime < 2000L) {
                (context as? Activity)?.finish()
            } else {
                lastBackPressTime = currentTime
                Toast.makeText(context, "Presiona de nuevo para salir", Toast.LENGTH_SHORT).show()
                showExitConfirmationDialog = true
            }
        }
    }

    // Exit application confirmation dialog
    if (showExitConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showExitConfirmationDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
            },
            title = {
                Text(
                    text = "¿Seguro que quieres salir?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    text = "¿Estás seguro de que deseas salir de EnginIA REBT? Tu progreso y estadísticas se guardan automáticamente.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showExitConfirmationDialog = false
                        (context as? Activity)?.finish()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    ),
                    modifier = Modifier.testTag("confirm_exit_app_button")
                ) {
                    Text("Salir")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showExitConfirmationDialog = false },
                    modifier = Modifier.testTag("cancel_exit_app_button")
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    // User authentication simulation dialog
    if (viewModel.showUserEmailDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.showUserEmailDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cuenta de Usuario", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Introduce tu correo para sincronizar progreso y suscripciones de Google Play.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    OutlinedTextField(
                        value = viewModel.inputEmailString,
                        onValueChange = { viewModel.inputEmailString = it },
                        label = { Text("Correo Electrónico") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.updateUserEmail(viewModel.inputEmailString.trim())
                        viewModel.showUserEmailDialog = false
                    }
                ) {
                    Text("Guardar y Activar")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.showUserEmailDialog = false }) {
                    Text("Cancelar")
                }
            }
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
                            modifier = Modifier.size(26.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "EnginIA REBT",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 19.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (isPremium) Color(0xFFD4AC0D) else MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .clickable {
                                    FeedbackManager.playClick(context)
                                    viewModel.activeTab = "subscription"
                                }
                                .testTag("top_bar_subscription_badge")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (isPremium) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = Color.Black,
                                        modifier = Modifier.size(11.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                }
                                Text(
                                    text = if (isPremium) "PRO" else "FREE",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    color = if (isPremium) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            FeedbackManager.playClick(context)
                            viewModel.activeTab = "calendar"
                        },
                        modifier = Modifier.testTag("app_bar_calendar_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "Calendario y Plan de Estudios",
                            tint = if (viewModel.activeTab == "calendar") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(
                        onClick = {
                            FeedbackManager.playClick(context)
                            viewModel.activeTab = "reminders"
                        },
                        modifier = Modifier.testTag("app_bar_reminders_button")
                    ) {
                        BadgedBox(
                            badge = {
                                if (pendingReminders > 0) {
                                    Badge(
                                        containerColor = Color(0xFFF85149),
                                        contentColor = Color.White
                                    ) {
                                        Text("$pendingReminders")
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Recordatorios",
                                tint = if (viewModel.activeTab == "reminders") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                    IconButton(
                        onClick = {
                            viewModel.isDarkTheme = !viewModel.isDarkTheme
                            FeedbackManager.playClick(context)
                        },
                        modifier = Modifier.testTag("app_bar_theme_toggle")
                    ) {
                        Icon(
                            imageVector = if (viewModel.isDarkTheme) Icons.Default.WbSunny else Icons.Default.NightsStay,
                            contentDescription = "Cambiar tema",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        bottomBar = {
            // Only show bottom navigation when not in an active exam
            if (viewModel.activeExamModule == null) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ) {
                    NavigationBarItem(
                        selected = viewModel.activeTab == "dashboard",
                        onClick = {
                            FeedbackManager.playClick(context)
                            viewModel.activeTab = "dashboard"
                        },
                        icon = { Icon(Icons.Default.Dashboard, contentDescription = "Inicio") },
                        label = { Text("Inicio", fontSize = 11.sp, maxLines = 1) },
                        modifier = Modifier.testTag("nav_tab_dashboard")
                    )
                    NavigationBarItem(
                        selected = viewModel.activeTab == "study",
                        onClick = {
                            FeedbackManager.playClick(context)
                            viewModel.activeTab = "study"
                        },
                        icon = { Icon(Icons.Default.MenuBook, contentDescription = "Estudio") },
                        label = { Text("Estudio", fontSize = 11.sp, maxLines = 1) },
                        modifier = Modifier.testTag("nav_tab_study")
                    )
                    NavigationBarItem(
                        selected = viewModel.activeTab == "exams",
                        onClick = {
                            FeedbackManager.playClick(context)
                            viewModel.activeTab = "exams"
                        },
                        icon = { Icon(Icons.Default.Assignment, contentDescription = "Exámenes") },
                        label = { Text("Exámenes", fontSize = 11.sp, maxLines = 1) },
                        modifier = Modifier.testTag("nav_tab_exams")
                    )
                    NavigationBarItem(
                        selected = viewModel.activeTab == "laboratory",
                        onClick = {
                            FeedbackManager.playClick(context)
                            viewModel.activeTab = "laboratory"
                        },
                        icon = { Icon(Icons.Default.Calculate, contentDescription = "Cálculo") },
                        label = { Text("Cálculo", fontSize = 11.sp, maxLines = 1) },
                        modifier = Modifier.testTag("nav_tab_laboratory")
                    )
                    NavigationBarItem(
                        selected = viewModel.activeTab == "analytics",
                        onClick = {
                            FeedbackManager.playClick(context)
                            viewModel.activeTab = "analytics"
                        },
                        icon = { Icon(Icons.Default.BarChart, contentDescription = "Analítica") },
                        label = { Text("Analítica", fontSize = 11.sp, maxLines = 1) },
                        modifier = Modifier.testTag("nav_tab_analytics")
                    )
                    NavigationBarItem(
                        selected = viewModel.activeTab == "settings",
                        onClick = {
                            FeedbackManager.playClick(context)
                            viewModel.activeTab = "settings"
                        },
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Ajustes") },
                        label = { Text("Ajustes", fontSize = 11.sp, maxLines = 1) },
                        modifier = Modifier.testTag("nav_tab_settings")
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (viewModel.activeTab) {
                "dashboard" -> DashboardScreen(viewModel)
                "study" -> StudyScreen(viewModel)
                "exams" -> ExamsScreen(viewModel)
                "laboratory" -> LaboratoryScreen(viewModel)
                "analytics" -> AnalyticsScreen(viewModel)
                "settings" -> SettingsScreen(viewModel)
                "reminders" -> RemindersScreen(viewModel)
                "calendar" -> com.example.ui.screens.calendar.CalendarStudyPlanScreen(viewModel)
                "posits" -> com.example.ui.screens.posits.PositsScreen(viewModel)
                "correlacion" -> com.example.ui.screens.study.CorrelacionScreen(viewModel)
                "boe" -> com.example.ui.screens.settings.BoeScreen(viewModel)
                "subscription" -> com.example.ui.screens.subscription.SubscriptionScreen(viewModel)
                else -> DashboardScreen(viewModel)
            }
        }
    }
}
