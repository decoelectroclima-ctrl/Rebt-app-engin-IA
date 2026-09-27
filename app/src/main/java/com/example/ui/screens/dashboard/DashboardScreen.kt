package com.example.ui.screens.dashboard

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.MainViewModel
import com.example.data.Content
import com.example.ui.FeedbackManager

@Composable
fun DashboardScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val progressList by viewModel.progressFlow.collectAsState()
    val examHistory by viewModel.examHistoryFlow.collectAsState()
    val dailyActivity by viewModel.dailyActivityFlow.collectAsState()
    val mistakeReviews by viewModel.questionReviewsFlow.collectAsState()
    val remindersList by viewModel.remindersFlow.collectAsState()
    val subscription by viewModel.subscriptionFlow.collectAsState()
    val isPremium = subscription?.isActive == true

    // Computed Reminders
    val pendingRemindersCount = remindersList.count { it.status == "Pendiente" }
    val expiredRemindersCount = remindersList.count { it.status == "Vencido" || (it.status != "Completado" && it.dueDate < System.currentTimeMillis()) }

    // Computed Analytics
    val totalAnswered = examHistory.sumOf { it.totalCount }
    val totalCorrect = examHistory.sumOf { it.correctCount }
    val avgScore = if (totalAnswered > 0) (totalCorrect * 100 / totalAnswered) else 0

    // Identify weakest module
    val weakestProgress = progressList.minByOrNull { it.pct }
    val weakestModuleName = if (weakestProgress != null && weakestProgress.answeredCount > 0) {
        Content.QUESTIONS[weakestProgress.moduleId]?.label ?: weakestProgress.moduleId
    } else {
        "ITC-BT-18 (Puesta a Tierra)"
    }

    val streak = dailyActivity?.streakDays ?: 1
    val todayDate = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
    val isToday = dailyActivity?.lastActiveDate == todayDate
    val hasStudiedToday = isToday && ((dailyActivity?.studiedItcsToday?.isNotBlank() == true) || (dailyActivity?.questionsAnswered ?: 0) > 0)
    val studyReminderHour = dailyActivity?.dailyStudyReminderHour ?: 20
    val studyReminderMinute = dailyActivity?.dailyStudyReminderMinute ?: 0
    val formattedStudyTime = String.format("%02d:%02d", studyReminderHour, studyReminderMinute)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Hero Header
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("dashboard_hero_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color(0xFFFFFFFF)
                ),
                border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFD0D7DE)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = if (viewModel.isDarkTheme) {
                                    listOf(Color(0xFF1A2332), Color(0xFF111720))
                                } else {
                                    listOf(Color(0xFFE8F1FC), Color(0xFFF6F8FA))
                                }
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text(
                                    text = "EnginIA REBT 2026",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (viewModel.isDarkTheme) Color(0xFF58A6FF) else Color(0xFF0969DA),
                                    letterSpacing = 1.sp
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Panel de Rendimiento",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                                // Daily Goal Chip
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = if (hasStudiedToday) Color(0xFF3FB950).copy(alpha = 0.15f) else Color(0xFF58A6FF).copy(alpha = 0.15f),
                                    border = BorderStroke(1.dp, if (hasStudiedToday) Color(0xFF3FB950).copy(alpha = 0.4f) else Color(0xFF58A6FF).copy(alpha = 0.4f)),
                                    modifier = Modifier.testTag("dashboard_daily_goal_badge")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = if (hasStudiedToday) Icons.Default.CheckCircle else Icons.Default.MenuBook,
                                            contentDescription = null,
                                            tint = if (hasStudiedToday) Color(0xFF3FB950) else Color(0xFF58A6FF),
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = if (hasStudiedToday) "Meta Hoy ✓" else "Aviso $formattedStudyTime",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (hasStudiedToday) {
                                                if (viewModel.isDarkTheme) Color(0xFF7EE787) else Color(0xFF1A7F37)
                                            } else {
                                                if (viewModel.isDarkTheme) Color(0xFF58A6FF) else Color(0xFF0969DA)
                                            }
                                        )
                                    }
                                }

                                // Streak Badge
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = Color(0xFFF39C12).copy(alpha = 0.15f),
                                    border = BorderStroke(1.dp, Color(0xFFF39C12).copy(alpha = 0.4f)),
                                    modifier = Modifier.testTag("dashboard_streak_badge")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.LocalFireDepartment,
                                            contentDescription = "Racha",
                                            tint = Color(0xFFE67E22),
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "$streak d",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFFE67E22)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = if (totalAnswered == 0) {
                                "Bienvenido. Empieza hoy tu preparación oficial para el examen de instalador autorizado en baja tensión."
                            } else {
                                "Progreso activo: has completado $totalAnswered preguntas con una precisión global del $avgScore%."
                            },
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Primary Action Button
                        Button(
                            onClick = {
                                FeedbackManager.playClick(context)
                                viewModel.startOfficialSimulation()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("dashboard_continue_study_button"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (viewModel.isDarkTheme) Color(0xFF238636) else Color(0xFF1F883D)
                            )
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Iniciar Simulacro Oficial (80 Preguntas)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }

        // 2. Three Metric Cards
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MetricCard(
                    modifier = Modifier.weight(1f),
                    title = "Respondidas",
                    value = "$totalAnswered",
                    subtitle = "preguntas",
                    icon = Icons.Default.Quiz,
                    iconTint = Color(0xFF58A6FF),
                    isDark = viewModel.isDarkTheme,
                    tag = "metric_answered"
                )

                MetricCard(
                    modifier = Modifier.weight(1f),
                    title = "Precisión",
                    value = "$avgScore%",
                    subtitle = if (avgScore >= 75) "Apto" else "En refuerzo",
                    icon = Icons.Default.Score,
                    iconTint = if (avgScore >= 75) Color(0xFF3FB950) else Color(0xFFF39C12),
                    isDark = viewModel.isDarkTheme,
                    tag = "metric_precision"
                )

                MetricCard(
                    modifier = Modifier.weight(1f),
                    title = "Fallos Activos",
                    value = "${mistakeReviews.size}",
                    subtitle = "por dominar",
                    icon = Icons.Default.WarningAmber,
                    iconTint = if (mistakeReviews.isNotEmpty()) Color(0xFFF85149) else Color(0xFF3FB950),
                    isDark = viewModel.isDarkTheme,
                    tag = "metric_mistakes"
                )
            }
        }

        // 3. Recommended Actions
        item {
            Text(
                text = "Acciones Rápidas",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
        }

        item {
            // Action 1: Weaknesses / Mistakes
            ActionRowCard(
                title = "Repasar Preguntas Falladas (Debilidades)",
                description = if (mistakeReviews.isNotEmpty()) {
                    "Tienes ${mistakeReviews.size} preguntas pendientes de reforzar con explicaciones normativas y trampas."
                } else {
                    "¡Excelente! No tienes fallos pendientes. Mantén tu racha practicando nuevos módulos."
                },
                badgeText = if (mistakeReviews.isNotEmpty()) "${mistakeReviews.size} fallos" else "Al día",
                badgeColor = if (mistakeReviews.isNotEmpty()) Color(0xFFF85149) else Color(0xFF3FB950),
                icon = Icons.Default.RestartAlt,
                isDark = viewModel.isDarkTheme,
                tag = "dashboard_action_mistakes",
                onClick = {
                    FeedbackManager.playClick(context)
                    if (mistakeReviews.isNotEmpty()) {
                        viewModel.startMistakesReview(mistakeReviews)
                    } else {
                        viewModel.activeTab = "exams"
                    }
                }
            )
        }

        item {
            // Action 2: Topic Review
            ActionRowCard(
                title = "Estudiar Temario REBT y Esquemas",
                description = "Consulta artículos clave, resúmenes con subrayado técnico y esquemas unifilares interactivos.",
                badgeText = "Syllabus",
                badgeColor = Color(0xFFBC8CFF),
                icon = Icons.AutoMirrored.Filled.MenuBook,
                isDark = viewModel.isDarkTheme,
                tag = "dashboard_action_study",
                onClick = {
                    FeedbackManager.playClick(context)
                    viewModel.activeTab = "study"
                }
            )
        }

        item {
            // Action 3: Electrical Lab
            ActionRowCard(
                title = "Laboratorio de Cálculo Eléctrico",
                description = "Calcula secciones de conductores por caída de tensión e Iz, previsión de cargas y tubos ITC-21.",
                badgeText = "Herramienta",
                badgeColor = Color(0xFF58A6FF),
                icon = Icons.Default.Calculate,
                isDark = viewModel.isDarkTheme,
                tag = "dashboard_action_lab",
                onClick = {
                    FeedbackManager.playClick(context)
                    viewModel.activeTab = "laboratory"
                }
            )
        }

        item {
            // Action 4: Recordatorios y Revisiones REBT
            ActionRowCard(
                title = "Recordatorios y Revisiones REBT",
                description = if (expiredRemindersCount > 0) {
                    "Tienes $expiredRemindersCount revisiones vencidas y $pendingRemindersCount pendientes asociadas a ITCs del REBT."
                } else if (pendingRemindersCount > 0) {
                    "Tienes $pendingRemindersCount tareas programadas (inspecciones OCA, puesta a tierra, diferenciales)."
                } else {
                    "Gestiona avisos periódicos de mantenimiento, seguros y revisiones reglamentarias."
                },
                badgeText = if (expiredRemindersCount > 0) "$expiredRemindersCount vencidos" else "$pendingRemindersCount activos",
                badgeColor = if (expiredRemindersCount > 0) Color(0xFFF85149) else Color(0xFFE67E22),
                icon = Icons.Default.NotificationsActive,
                isDark = viewModel.isDarkTheme,
                tag = "dashboard_action_reminders",
                onClick = {
                    FeedbackManager.playClick(context)
                    viewModel.activeTab = "reminders"
                }
            )
        }

        // 4. Critical Weakness Advisory
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("dashboard_weakness_alert"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF1E1715) else Color(0xFFFFF8F2)
                ),
                border = BorderStroke(1.dp, Color(0xFFE67E22).copy(alpha = 0.4f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE67E22).copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = null,
                            tint = Color(0xFFE67E22),
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Punto Crítico a Reforzar",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE67E22)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = weakestModuleName,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Dedica 10 minutos a repasar las trampas de este bloque para asegurar el aprobado oficial.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    IconButton(
                        onClick = {
                            FeedbackManager.playClick(context)
                            viewModel.startTopicPractice(weakestProgress?.moduleId ?: "tierra")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Ir al tema",
                            tint = Color(0xFFE67E22)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MetricCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    isDark: Boolean,
    tag: String
) {
    Card(
        modifier = modifier.testTag(tag),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDark) Color(0xFF161B22) else Color.White
        ),
        border = BorderStroke(1.dp, if (isDark) Color(0xFF30363D) else Color(0xFFE1E4E8))
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )
        }
    }
}

@Composable
fun ActionRowCard(
    title: String,
    description: String,
    badgeText: String,
    badgeColor: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isDark: Boolean,
    tag: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag(tag),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDark) Color(0xFF161B22) else Color.White
        ),
        border = BorderStroke(1.dp, if (isDark) Color(0xFF30363D) else Color(0xFFE1E4E8))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(badgeColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = badgeColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = badgeColor.copy(alpha = 0.12f)
                    ) {
                        Text(
                            text = badgeText,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = badgeColor,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 16.sp
                )
            }
        }
    }
}
