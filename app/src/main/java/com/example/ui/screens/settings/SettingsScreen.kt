package com.example.ui.screens.settings

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.MainViewModel
import com.example.data.Content
import com.example.ui.FeedbackManager

@Composable
fun SettingsScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val subscription by viewModel.subscriptionFlow.collectAsState()
    val isPremium = subscription?.isActive == true
    val currentPlan = subscription?.plan ?: "gratuito"

    var showClearHistoryDialog by remember { mutableStateOf(false) }
    val dailyActivity by viewModel.dailyActivityFlow.collectAsState()
    val reminderEnabled = dailyActivity?.dailyStudyReminderEnabled ?: true
    val reminderHour = dailyActivity?.dailyStudyReminderHour ?: 20
    val reminderMinute = dailyActivity?.dailyStudyReminderMinute ?: 0
    val formattedTime = String.format("%02d:%02d", reminderHour, reminderMinute)

    var showTimePickerDialog by remember { mutableStateOf(false) }
    if (showTimePickerDialog) {
        android.app.TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                viewModel.updateDailyStudyReminderSettings(reminderEnabled, hourOfDay, minute)
                showTimePickerDialog = false
            },
            reminderHour,
            reminderMinute,
            true
        ).show()
        showTimePickerDialog = false
    }

    if (showClearHistoryDialog) {
        AlertDialog(
            onDismissRequest = { showClearHistoryDialog = false },
            title = { Text("¿Restablecer progreso?") },
            text = { Text("Se borrará el historial de exámenes y los registros de fallos de la base de datos local.") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.clearExamHistory()
                        showClearHistoryDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF85149))
                ) {
                    Text("Borrar datos")
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearHistoryDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Ajustes y Asistente",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Configuración del perfil, gestión de suscripción y tutor técnico con Inteligencia Artificial.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // 1. User Profile & Subscription Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                ),
                border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF58A6FF).copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = Color(0xFF58A6FF),
                                modifier = Modifier.size(26.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = viewModel.currentUserEmail,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = if (isPremium) {
                                    val planName = when (subscription?.plan) {
                                        "premium" -> "Plan Instalador Pro Vitalicio (49,99 €)"
                                        "pro_quarterly" -> "Plan Convocatoria Trimestral (29,99 €)"
                                        "pro_monthly" -> "Plan Aspirante Pro Mensual (14,99 €)"
                                        else -> "Plan Pro"
                                    }
                                    "✨ $planName Activo"
                                } else {
                                    "Plan Gratuito de Demostración"
                                },
                                fontSize = 12.sp,
                                fontWeight = if (isPremium) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (isPremium) Color(0xFF3FB950) else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        IconButton(
                            onClick = {
                                viewModel.inputEmailString = viewModel.currentUserEmail
                                viewModel.showUserEmailDialog = true
                            }
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar cuenta", tint = Color.Gray)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (isPremium) "Gestión de Licencia" else "Pase Oficial de Examen",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = if (isPremium) "Consulta tu plan o gestiona en Play Store" else "Desbloquea las 52 ITCs y simulacros ilimitados",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Button(
                            onClick = {
                                FeedbackManager.playClick(context)
                                viewModel.activeTab = "subscription"
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isPremium) MaterialTheme.colorScheme.primaryContainer else Color(0xFFE67E22),
                                contentColor = if (isPremium) MaterialTheme.colorScheme.onPrimaryContainer else Color.White
                            ),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                            modifier = Modifier.testTag("settings_view_plans_button")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (isPremium) Icons.Default.Settings else Icons.Default.WorkspacePremium,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isPremium) "Ver Planes" else "Ver Planes",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }


        // 3. Theme & Preferences
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                ),
                border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Text(
                        text = "Preferencias de la Aplicación",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (viewModel.isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Modo Oscuro Técnico", fontSize = 13.sp)
                        }

                        Switch(
                            checked = viewModel.isDarkTheme,
                            onCheckedChange = {
                                viewModel.isDarkTheme = it
                                FeedbackManager.playClick(context)
                            },
                            modifier = Modifier.testTag("settings_theme_switch")
                        )
                    }

                    HorizontalDivider(color = if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))

                    // Shortcut to Reminders
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                FeedbackManager.playClick(context)
                                viewModel.activeTab = "reminders"
                            }
                            .padding(vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.NotificationsActive,
                                contentDescription = null,
                                tint = Color(0xFFE67E22),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text("Recordatorios y Revisiones REBT", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                                Text("Gestionar plazos de OCA, tierras y diferenciales", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        // 3.5. Daily Study Reminder Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                ),
                border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF58A6FF).copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Recordatorio Diario de Estudio",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Aviso si no has estudiado ninguna ITC hoy",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Switch(
                            checked = reminderEnabled,
                            onCheckedChange = { isChecked ->
                                FeedbackManager.playClick(context)
                                viewModel.updateDailyStudyReminderSettings(
                                    enabled = isChecked,
                                    hour = reminderHour,
                                    minute = reminderMinute
                                )
                            },
                            modifier = Modifier.testTag("settings_daily_study_switch")
                        )
                    }

                    if (reminderEnabled) {
                        HorizontalDivider(color = if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text(
                                    text = "Hora de la Notificación",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "Se enviará a las $formattedTime si aún no has entrado a estudiar",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            FilledTonalButton(
                                onClick = {
                                    FeedbackManager.playClick(context)
                                    showTimePickerDialog = true
                                },
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.AccessTime, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(formattedTime, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                        }

                        OutlinedButton(
                            onClick = {
                                FeedbackManager.playClick(context)
                                viewModel.sendTestDailyStudyNotification()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.NotificationsActive, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Probar Notificación de Estudio Diario", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // 4. Documentación Oficial y Recursos
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                ),
                border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Text(
                        text = "Documentación Oficial REBT",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    // BOE 2026
                    ActionRow(
                        title = "BOE 2026 (Actualizado)",
                        icon = Icons.Default.Description,
                        isDark = viewModel.isDarkTheme,
                        onClick = { viewModel.activeTab = "boe" }
                    )

                    // Índice y Correlación
                    ActionRow(
                        title = "Índice y Correlación ITC",
                        icon = Icons.Default.List,
                        isDark = viewModel.isDarkTheme,
                        onClick = { viewModel.activeTab = "correlacion" }
                    )

                    HorizontalDivider(color = if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))

                    // Documentos descargables existentes
                    Content.DOCUMENTS.forEach { doc ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(doc.title, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                                Text("${doc.type} • ${doc.fileSize}", fontSize = 11.sp, color = Color.Gray)
                            }
                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = "Descargar",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }

        // 5. Data Reset
        item {
            OutlinedButton(
                onClick = { showClearHistoryDialog = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFF85149))
            ) {
                Icon(Icons.Default.DeleteOutline, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Restablecer Historial de Estudio")
            }
        }
    }
}

@Composable
fun ActionRow(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isDark: Boolean,
    onClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(title, fontSize = 13.sp, fontWeight = FontWeight.Medium)
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(18.dp)
        )
    }
}
