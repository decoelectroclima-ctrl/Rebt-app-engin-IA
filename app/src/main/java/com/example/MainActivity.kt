package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
    val subscription by viewModel.subscriptionFlow.collectAsState()
    val isPremium = subscription?.isActive == true

    val remindersList by viewModel.remindersFlow.collectAsState()
    val pendingReminders = remindersList.count { it.status == "Pendiente" || (it.status != "Completado" && it.dueDate < System.currentTimeMillis()) }

    // Handle back button: if inside active exam, prompt before exiting; otherwise return to dashboard
    BackHandler {
        if (viewModel.activeExamModule != null) {
            viewModel.exitActiveExam()
        } else if (viewModel.activeTab != "dashboard") {
            viewModel.activeTab = "dashboard"
        }
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
                        viewModel.changeSubscriberEmail(viewModel.inputEmailString.trim())
                        viewModel.showUserEmailDialog = false
                    }
                ) {
                    Text("Guardar")
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
                            color = if (isPremium) Color(0xFFD4AC0D) else MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = if (isPremium) "PRO" else "FREE",
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                color = if (isPremium) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                },
                actions = {
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
                else -> DashboardScreen(viewModel)
            }
        }
    }
}
