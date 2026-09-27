package com.example.ui.screens.reminders

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.MainViewModel
import com.example.data.ReminderEntity
import com.example.data.ReminderNotificationManager
import com.example.ui.FeedbackManager
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val allReminders by viewModel.remindersFlow.collectAsState()
    val dailyActivity by viewModel.dailyActivityFlow.collectAsState()

    val todayDate = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
    val isToday = dailyActivity?.lastActiveDate == todayDate
    val hasStudiedToday = isToday && ((dailyActivity?.studiedItcsToday?.isNotBlank() == true) || (dailyActivity?.questionsAnswered ?: 0) > 0)
    val studyReminderEnabled = dailyActivity?.dailyStudyReminderEnabled ?: true
    val studyReminderHour = dailyActivity?.dailyStudyReminderHour ?: 20
    val studyReminderMinute = dailyActivity?.dailyStudyReminderMinute ?: 0
    val formattedStudyTime = String.format("%02d:%02d", studyReminderHour, studyReminderMinute)

    // Notification permission launcher for Android 13+
    var hasNotificationPermission by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasNotificationPermission = isGranted
        if (isGranted) {
            viewModel.sendTestNotification(
                title = "Notificaciones Activadas",
                description = "Recibirás avisos para inspecciones y revisiones del REBT.",
                article = "REBT 2026"
            )
        }
    }

    // Dynamic filtering
    val now = System.currentTimeMillis()
    val processedReminders = allReminders.map { reminder ->
        if (reminder.status != "Completado" && reminder.dueDate < now) {
            reminder.copy(status = "Vencido")
        } else {
            reminder
        }
    }

    val filteredList = processedReminders.filter { reminder ->
        val matchesStatus = when (viewModel.reminderStatusFilter) {
            "Pendientes" -> reminder.status == "Pendiente"
            "Completados" -> reminder.status == "Completado"
            "Vencidos" -> reminder.status == "Vencido"
            else -> true
        }

        val matchesPriority = when (viewModel.reminderPriorityFilter) {
            "Alta" -> reminder.priority == "Alta"
            "Media" -> reminder.priority == "Media"
            "Baja" -> reminder.priority == "Baja"
            else -> true
        }

        val matchesSearch = viewModel.reminderSearchQuery.isBlank() ||
                reminder.title.contains(viewModel.reminderSearchQuery, ignoreCase = true) ||
                reminder.description.contains(viewModel.reminderSearchQuery, ignoreCase = true) ||
                reminder.rebtArticle.contains(viewModel.reminderSearchQuery, ignoreCase = true)

        matchesStatus && matchesPriority && matchesSearch
    }

    val countPendientes = processedReminders.count { it.status == "Pendiente" }
    val countCompletados = processedReminders.count { it.status == "Completado" }
    val countVencidos = processedReminders.count { it.status == "Vencido" }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    FeedbackManager.playClick(context)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !hasNotificationPermission) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                    viewModel.openCreateReminderDialog()
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.testTag("fab_add_reminder")
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nuevo Recordatorio")
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header with Back button if navigated from other screen
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = {
                            FeedbackManager.playClick(context)
                            viewModel.activeTab = "dashboard"
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Column {
                        Text(
                            text = "Recordatorios y Revisiones",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Control de plazos, mantenimiento e ITCs del REBT",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Notification Permission Banner (if not granted on Android 13+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !hasNotificationPermission) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF39C12).copy(alpha = 0.15f)),
                        border = BorderStroke(1.dp, Color(0xFFF39C12).copy(alpha = 0.5f))
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.NotificationsActive,
                                contentDescription = null,
                                tint = Color(0xFFF39C12),
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Permiso de Notificaciones",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Activa los avisos para no perder plazos de revisiones reglamentarias.",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Button(
                                onClick = { permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS) },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF39C12)),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text("Activar", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                            }
                        }
                    }
                }
            }

            // Summary Stats Row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ReminderStatBadge(
                        modifier = Modifier.weight(1f),
                        label = "Pendientes",
                        count = countPendientes,
                        color = Color(0xFF58A6FF),
                        isDark = viewModel.isDarkTheme
                    )
                    ReminderStatBadge(
                        modifier = Modifier.weight(1f),
                        label = "Vencidos",
                        count = countVencidos,
                        color = Color(0xFFF85149),
                        isDark = viewModel.isDarkTheme
                    )
                    ReminderStatBadge(
                        modifier = Modifier.weight(1f),
                        label = "Completados",
                        count = countCompletados,
                        color = Color(0xFF3FB950),
                        isDark = viewModel.isDarkTheme
                    )
                }
            }

            // Automated Daily Study Reminder Banner
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("reminder_daily_study_banner"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color(0xFFF0F6FC)
                    ),
                    border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFD0D7DE))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(
                                    if (hasStudiedToday) Color(0xFF3FB950).copy(alpha = 0.2f) else Color(0xFF58A6FF).copy(alpha = 0.2f)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (hasStudiedToday) Icons.Default.CheckCircle else Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = if (hasStudiedToday) Color(0xFF3FB950) else Color(0xFF58A6FF),
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Recordatorio Diario de Estudio",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = if (studyReminderEnabled) Color(0xFF3FB950).copy(alpha = 0.2f) else Color.Gray.copy(alpha = 0.2f)
                                ) {
                                    Text(
                                        text = if (studyReminderEnabled) "Activo ($formattedStudyTime)" else "Desactivado",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (studyReminderEnabled) (if (viewModel.isDarkTheme) Color(0xFF7EE787) else Color(0xFF1A7F37)) else Color.Gray,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = if (hasStudiedToday) {
                                    "✓ Meta diaria de hoy cumplida. ¡Excelente trabajo!"
                                } else {
                                    "Avisa a las $formattedStudyTime si no has repasado ninguna ITC hoy."
                                },
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        IconButton(
                            onClick = {
                                FeedbackManager.playClick(context)
                                viewModel.sendTestDailyStudyNotification()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.NotificationsActive,
                                contentDescription = "Probar Recordatorio Diario",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            // Quick Tool Bar: Search & Notification test
            item {
                OutlinedTextField(
                    value = viewModel.reminderSearchQuery,
                    onValueChange = { viewModel.reminderSearchQuery = it },
                    placeholder = { Text("Buscar por título, norma (ej. ITC-BT-18)...", fontSize = 13.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(20.dp)) },
                    trailingIcon = {
                        if (viewModel.reminderSearchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.reminderSearchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Limpiar")
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("reminder_search_input")
                )
            }

            // Status Filter Chips
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val filters = listOf("Todos", "Pendientes", "Vencidos", "Completados")
                    items(filters) { filter ->
                        val isSelected = viewModel.reminderStatusFilter == filter
                        val chipColor = when (filter) {
                            "Pendientes" -> Color(0xFF58A6FF)
                            "Vencidos" -> Color(0xFFF85149)
                            "Completados" -> Color(0xFF3FB950)
                            else -> MaterialTheme.colorScheme.primary
                        }

                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                FeedbackManager.playClick(context)
                                viewModel.reminderStatusFilter = filter
                            },
                            label = {
                                Text(
                                    text = filter,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            leadingIcon = if (isSelected) {
                                {
                                    Icon(
                                        imageVector = when (filter) {
                                            "Pendientes" -> Icons.Default.HourglassEmpty
                                            "Vencidos" -> Icons.Default.WarningAmber
                                            "Completados" -> Icons.Default.CheckCircle
                                            else -> Icons.Default.FilterList
                                        },
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            } else null,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = chipColor.copy(alpha = 0.2f),
                                selectedLabelColor = chipColor
                            )
                        )
                    }
                }
            }

            // Priority Filter Chips
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Prioridad:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    val priorities = listOf("Todos", "Alta", "Media", "Baja")
                    priorities.forEach { prio ->
                        val isSelected = viewModel.reminderPriorityFilter == prio
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            border = BorderStroke(
                                1.dp,
                                if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
                            ),
                            modifier = Modifier
                                .clickable {
                                    FeedbackManager.playClick(context)
                                    viewModel.reminderPriorityFilter = prio
                                }
                        ) {
                            Text(
                                text = prio,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Test notification button
                    IconButton(
                        onClick = {
                            FeedbackManager.playClick(context)
                            viewModel.sendTestNotification(
                                title = "Alarma REBT: Inspección Periódica",
                                description = "Vencimiento próximo de comprobación de diferenciales ITC-BT-24.",
                                article = "ITC-BT-24"
                            )
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.NotificationAdd,
                            contentDescription = "Probar Notificación",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Reminder Items List or Empty State
            if (filteredList.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color(0xFFFFFFFF)
                        ),
                        border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.EventNote,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "No hay recordatorios en esta vista",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Crea un nuevo recordatorio o pulsa abajo para cargar los recordatorios normativos oficiales.",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    FeedbackManager.playClick(context)
                                    viewModel.seedDefaultReminders()
                                },
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Cargar Recordatorios Tipo REBT")
                            }
                        }
                    }
                }
            } else {
                items(filteredList, key = { it.id }) { reminder ->
                    ReminderItemCard(
                        reminder = reminder,
                        isDark = viewModel.isDarkTheme,
                        onToggle = {
                            FeedbackManager.playClick(context)
                            viewModel.toggleReminderStatus(reminder)
                        },
                        onEdit = {
                            FeedbackManager.playClick(context)
                            viewModel.openEditReminderDialog(reminder)
                        },
                        onDelete = {
                            FeedbackManager.playClick(context)
                            viewModel.showDeleteConfirmDialog = reminder
                        },
                        onNotifyTest = {
                            FeedbackManager.playClick(context)
                            viewModel.sendTestNotification(
                                title = reminder.title,
                                description = reminder.description,
                                article = reminder.rebtArticle
                            )
                        }
                    )
                }
            }
        }
    }

    // CREATE / EDIT DIALOG
    if (viewModel.showReminderDialog) {
        ReminderFormDialog(viewModel = viewModel)
    }

    // DELETE CONFIRMATION DIALOG
    viewModel.showDeleteConfirmDialog?.let { reminderToDelete ->
        AlertDialog(
            onDismissRequest = { viewModel.showDeleteConfirmDialog = null },
            icon = { Icon(Icons.Default.Delete, contentDescription = null, tint = Color(0xFFF85149)) },
            title = { Text("Eliminar Recordatorio") },
            text = {
                Text("¿Estás seguro de que deseas eliminar el recordatorio \"${reminderToDelete.title}\"?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteReminder(reminderToDelete.id)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF85149))
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.showDeleteConfirmDialog = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun ReminderStatBadge(
    modifier: Modifier = Modifier,
    label: String,
    count: Int,
    color: Color,
    isDark: Boolean
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDark) Color(0xFF161B22) else Color.White
        ),
        border = BorderStroke(1.dp, color.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$count",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = color
            )
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ReminderItemCard(
    reminder: ReminderEntity,
    isDark: Boolean,
    onToggle: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onNotifyTest: () -> Unit
) {
    val isCompleted = reminder.status == "Completado"
    val isVencido = reminder.status == "Vencido"

    val statusColor = when {
        isCompleted -> Color(0xFF3FB950)
        isVencido -> Color(0xFFF85149)
        else -> Color(0xFF58A6FF)
    }

    val priorityColor = when (reminder.priority) {
        "Alta" -> Color(0xFFF85149)
        "Media" -> Color(0xFFF39C12)
        else -> Color(0xFF2EA043)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("reminder_card_${reminder.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDark) Color(0xFF161B22) else Color.White
        ),
        border = BorderStroke(
            1.dp,
            if (isVencido) Color(0xFFF85149).copy(alpha = 0.5f)
            else if (isDark) Color(0xFF30363D)
            else Color(0xFFE1E4E8)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            // Top Row: Checkbox, Title, Badges
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Completed Toggle Button
                IconButton(
                    onClick = onToggle,
                    modifier = Modifier
                        .size(28.dp)
                        .testTag("toggle_reminder_${reminder.id}")
                ) {
                    Icon(
                        imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Outlined.Circle,
                        contentDescription = "Marcar completado",
                        tint = statusColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = reminder.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isCompleted) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface,
                        textDecoration = if (isCompleted) TextDecoration.LineThrough else TextDecoration.None
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = reminder.description,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 16.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Chips Row: REBT Article, Priority, Periodicity, Due Date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Article Tag
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = reminder.rebtArticle,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // Priority Badge
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = priorityColor.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = reminder.priority,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = priorityColor,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                // Periodicity Badge
                if (reminder.periodicity != "Puntual") {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFFBC8CFF).copy(alpha = 0.12f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Repeat,
                                contentDescription = null,
                                tint = Color(0xFFBC8CFF),
                                modifier = Modifier.size(11.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = reminder.periodicity,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFFBC8CFF)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Due date countdown
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = statusColor.copy(alpha = 0.12f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (isVencido) Icons.Default.Warning else Icons.Default.Schedule,
                            contentDescription = null,
                            tint = statusColor,
                            modifier = Modifier.size(11.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = if (isCompleted) "Completado" else ReminderNotificationManager.getRelativeTimeSpanString(reminder.dueDate),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = statusColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = if (isDark) Color(0xFF21262D) else Color(0xFFF0F2F5))
            Spacer(modifier = Modifier.height(6.dp))

            // Footer Row: Exact Date string & Actions (Edit / Delete / Alarm)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Vencimiento: ${ReminderNotificationManager.formatDateTime(reminder.dueDate)}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row {
                    IconButton(
                        onClick = onNotifyTest,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notificar",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    IconButton(
                        onClick = onEdit,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            contentDescription = "Eliminar",
                            tint = Color(0xFFF85149),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ReminderFormDialog(viewModel: MainViewModel) {
    val context = LocalContext.current
    val isEditing = viewModel.editingReminder != null

    // Calendar for date/time picking
    val calendar = remember {
        Calendar.getInstance().apply {
            timeInMillis = viewModel.reminderInputDueDate
        }
    }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            viewModel.reminderInputDueDate = calendar.timeInMillis
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            viewModel.reminderInputDueDate = calendar.timeInMillis
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    AlertDialog(
        onDismissRequest = { viewModel.showReminderDialog = false },
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (isEditing) Icons.Default.EditCalendar else Icons.Default.AddAlert,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isEditing) "Editar Recordatorio" else "Nuevo Recordatorio REBT",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Title
                OutlinedTextField(
                    value = viewModel.reminderInputTitle,
                    onValueChange = { viewModel.reminderInputTitle = it },
                    label = { Text("Título *") },
                    placeholder = { Text("Ej: Revisión Anual Puesta a Tierra") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_reminder_title")
                )

                // Description
                OutlinedTextField(
                    value = viewModel.reminderInputDescription,
                    onValueChange = { viewModel.reminderInputDescription = it },
                    label = { Text("Descripción") },
                    placeholder = { Text("Detalle de la medición o inspección reglamentaria...") },
                    maxLines = 3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_reminder_description")
                )

                // REBT Article selector & quick chips
                Text(
                    text = "Artículo o ITC del REBT:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                OutlinedTextField(
                    value = viewModel.reminderInputArticle,
                    onValueChange = { viewModel.reminderInputArticle = it },
                    label = { Text("Norma REBT") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_reminder_article")
                )

                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    val quickArticles = listOf("ITC-BT-05", "ITC-BT-18", "ITC-BT-28", "ITC-BT-24", "Art. 14", "Art. 22", "ITC-BT-04")
                    items(quickArticles) { art ->
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.clickable { viewModel.reminderInputArticle = art }
                        ) {
                            Text(
                                text = art,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // Due Date and Time Picker Buttons
                Text(
                    text = "Fecha y Hora de Vencimiento:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { datePickerDialog.show() },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.CalendarToday, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(ReminderNotificationManager.formatDateOnly(viewModel.reminderInputDueDate), fontSize = 12.sp)
                    }

                    OutlinedButton(
                        onClick = { timePickerDialog.show() },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.AccessTime, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(ReminderNotificationManager.formatTimeOnly(viewModel.reminderInputDueDate), fontSize = 12.sp)
                    }
                }

                // Quick Offset Chips
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    val offsets = listOf(
                        "Mañana" to (1 * 86400000L),
                        "En 3 días" to (3 * 86400000L),
                        "En 1 semana" to (7 * 86400000L),
                        "En 1 mes" to (30 * 86400000L),
                        "En 1 año" to (365 * 86400000L)
                    )
                    items(offsets) { (label, millis) ->
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            modifier = Modifier.clickable {
                                viewModel.reminderInputDueDate = System.currentTimeMillis() + millis
                            }
                        ) {
                            Text(
                                text = "+ $label",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                    }
                }

                // Periodicity Selector
                Text(
                    text = "Periodicidad:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val periodicities = listOf("Puntual", "Diaria", "Semanal", "Mensual", "Anual")
                    periodicities.forEach { period ->
                        val isSelected = viewModel.reminderInputPeriodicity == period
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .clickable { viewModel.reminderInputPeriodicity = period }
                        ) {
                            Text(
                                text = period,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                // Priority Selector
                Text(
                    text = "Prioridad:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val priorities = listOf("Baja", "Media", "Alta")
                    priorities.forEach { prio ->
                        val isSelected = viewModel.reminderInputPriority == prio
                        val color = when (prio) {
                            "Alta" -> Color(0xFFF85149)
                            "Media" -> Color(0xFFF39C12)
                            else -> Color(0xFF2EA043)
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) color else color.copy(alpha = 0.12f),
                            border = BorderStroke(1.dp, color),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { viewModel.reminderInputPriority = prio }
                        ) {
                            Text(
                                text = prio,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else color,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                modifier = Modifier.padding(vertical = 6.dp)
                            )
                        }
                    }
                }

                // Notification Toggle
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Notificación al vencer",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Switch(
                        checked = viewModel.reminderInputNotify,
                        onCheckedChange = { viewModel.reminderInputNotify = it }
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    FeedbackManager.playClick(context)
                    viewModel.saveReminder()
                },
                enabled = viewModel.reminderInputTitle.isNotBlank(),
                modifier = Modifier.testTag("dialog_save_reminder_button")
            ) {
                Text(if (isEditing) "Actualizar" else "Crear")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { viewModel.showReminderDialog = false }
            ) {
                Text("Cancelar")
            }
        }
    )
}
