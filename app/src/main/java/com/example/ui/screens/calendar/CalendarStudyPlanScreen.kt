package com.example.ui.screens.calendar

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.MainViewModel
import com.example.data.StudentCalendarEventEntity
import com.example.ui.FeedbackManager
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarStudyPlanScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val allEvents by viewModel.studentEventsFlow.collectAsState()
    val studyPlan by viewModel.studyPlanFlow.collectAsState()

    BackHandler {
        viewModel.activeTab = "dashboard"
    }

    // Formatting helpers
    val sdfIso = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    val sdfDisplay = remember { SimpleDateFormat("EEEE, d 'de' MMMM 'de' yyyy", Locale("es", "ES")) }
    val sdfMonthHeader = remember { SimpleDateFormat("MMMM yyyy", Locale("es", "ES")) }

    // Today calculation
    val todayCal = remember { Calendar.getInstance() }
    val todayIso = remember { sdfIso.format(todayCal.time) }

    // Filter events by selected category
    val filteredEvents = remember(allEvents, viewModel.calendarFilterType) {
        when (viewModel.calendarFilterType) {
            "EXAMENES" -> allEvents.filter { it.eventType == "EXAM_THEORY" || it.eventType == "EXAM_PRACTICE" }
            "CLASES" -> allEvents.filter { it.eventType == "CLASS_THEORY" || it.eventType == "CLASS_PRACTICE" }
            "PLAN_ESTUDIO" -> allEvents.filter { it.eventType == "STUDY_SESSION" || it.eventType == "SIMULATION" }
            else -> allEvents
        }
    }

    // Events on selected day
    val eventsOnSelectedDate = remember(filteredEvents, viewModel.selectedCalendarDate) {
        filteredEvents.filter { it.date == viewModel.selectedCalendarDate }
            .sortedBy { it.time }
    }

    // Key upcoming exam events for countdown cards
    val theoryExamEvent = remember(allEvents) {
        allEvents.firstOrNull { it.eventType == "EXAM_THEORY" }
    }
    val practiceExamEvent = remember(allEvents) {
        allEvents.firstOrNull { it.eventType == "EXAM_PRACTICE" }
    }

    // Compute countdowns
    fun computeDaysRemaining(dateIso: String?): Int? {
        if (dateIso.isNullOrBlank()) return null
        return try {
            val target = sdfIso.parse(dateIso)?.time ?: return null
            val now = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis
            val diff = target - now
            (diff / (1000 * 60 * 60 * 24)).toInt()
        } catch (_: Exception) {
            null
        }
    }

    val daysToTheory = computeDaysRemaining(theoryExamEvent?.date ?: studyPlan?.targetExamDate)
    val daysToPractice = computeDaysRemaining(practiceExamEvent?.date ?: studyPlan?.targetPracticeExamDate)

    // Compute study plan progress
    val totalMilestones = allEvents.size
    val completedMilestones = allEvents.count { it.isCompleted }
    val planProgressPct = if (totalMilestones > 0) (completedMilestones * 100 / totalMilestones) else 0

    // Calendar grid calculations
    val calendarMonthName = remember(viewModel.calendarViewingYear, viewModel.calendarViewingMonth) {
        val cal = Calendar.getInstance().apply {
            set(Calendar.YEAR, viewModel.calendarViewingYear)
            set(Calendar.MONTH, viewModel.calendarViewingMonth)
            set(Calendar.DAY_OF_MONTH, 1)
        }
        sdfMonthHeader.format(cal.time).replaceFirstChar { it.uppercase() }
    }

    val daysInMonthGrid = remember(viewModel.calendarViewingYear, viewModel.calendarViewingMonth) {
        val cal = Calendar.getInstance().apply {
            set(Calendar.YEAR, viewModel.calendarViewingYear)
            set(Calendar.MONTH, viewModel.calendarViewingMonth)
            set(Calendar.DAY_OF_MONTH, 1)
        }
        val maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        // Day of week: Sunday = 1, Monday = 2... Convert to Monday = 0..Sunday = 6
        val firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
        val emptyLeadingSlots = if (firstDayOfWeek == Calendar.SUNDAY) 6 else firstDayOfWeek - 2

        val list = mutableListOf<String?>()
        repeat(emptyLeadingSlots) { list.add(null) }
        for (day in 1..maxDays) {
            val dStr = String.format(Locale.ROOT, "%04d-%02d-%02d", viewModel.calendarViewingYear, viewModel.calendarViewingMonth + 1, day)
            list.add(dStr)
        }
        list
    }

    // Map of dates to event types for dot indicators
    val eventsByDateMap = remember(allEvents) {
        allEvents.groupBy { it.date }
    }

    // Upcoming events list computed inside Composable scope
    val upcomingEvents = remember(allEvents, todayIso) {
        allEvents.filter { it.date >= todayIso && !it.isCompleted }
            .sortedWith(compareBy({ it.date }, { it.time }))
            .take(6)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = {
                                FeedbackManager.playClick(context)
                                viewModel.activeTab = "dashboard"
                            },
                            modifier = Modifier.testTag("calendar_back_button")
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
                                text = "Calendario y Plan de Estudios",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Clases, Examen Teórico, Práctico y Agenda REBT",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            FeedbackManager.playClick(context)
                            viewModel.openStudyPlanWizard()
                        },
                        modifier = Modifier.testTag("app_bar_study_plan_wizard")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoFixHigh,
                            contentDescription = "Diseñar Plan de Estudios",
                            tint = Color(0xFFF5B041)
                        )
                    }
                    IconButton(
                        onClick = {
                            FeedbackManager.playClick(context)
                            viewModel.openAddEventDialog()
                        },
                        modifier = Modifier.testTag("app_bar_add_event")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Añadir Evento",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 48.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Official Exam Countdown Cards (Teórico + Práctico)
            item {
                Text(
                    text = "Fechas Oficiales de Examen",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ExamCountdownCard(
                        modifier = Modifier.weight(1f),
                        title = "Examen Teórico",
                        subtitle = "Test REBT 40 Preguntas",
                        dateStr = theoryExamEvent?.date ?: studyPlan?.targetExamDate ?: "Sin fijar",
                        daysRemaining = daysToTheory,
                        icon = Icons.Default.Assignment,
                        accentColor = Color(0xFFF85149),
                        isDark = viewModel.isDarkTheme,
                        onClick = {
                            FeedbackManager.playClick(context)
                            if (theoryExamEvent != null) {
                                viewModel.openEditEventDialog(theoryExamEvent)
                            } else {
                                viewModel.openAddEventDialog(prefilledType = "EXAM_THEORY")
                            }
                        },
                        tag = "exam_theory_countdown_card"
                    )

                    ExamCountdownCard(
                        modifier = Modifier.weight(1f),
                        title = "Examen Práctico",
                        subtitle = "Montaje y Medidas Taller",
                        dateStr = practiceExamEvent?.date ?: studyPlan?.targetPracticeExamDate ?: "Sin fijar",
                        daysRemaining = daysToPractice,
                        icon = Icons.Default.Handyman,
                        accentColor = Color(0xFF3FB950),
                        isDark = viewModel.isDarkTheme,
                        onClick = {
                            FeedbackManager.playClick(context)
                            if (practiceExamEvent != null) {
                                viewModel.openEditEventDialog(practiceExamEvent)
                            } else {
                                viewModel.openAddEventDialog(prefilledType = "EXAM_PRACTICE")
                            }
                        },
                        tag = "exam_practice_countdown_card"
                    )
                }
            }

            // 2. Study Plan Progress & Wizard Quick Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color(0xFFFFFFFF)
                    ),
                    border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
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
                                        .background(Color(0xFFF5B041).copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Flag,
                                        contentDescription = null,
                                        tint = Color(0xFFF5B041),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = studyPlan?.examCallName ?: "Plan de Preparación REBT 2026",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "Ritmo: ${studyPlan?.studyPlanMode ?: "ESTÁNDAR"} • ${studyPlan?.hoursPerWeek ?: 8} h/semana",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            Text(
                                text = "$planProgressPct%",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (planProgressPct >= 75) Color(0xFF3FB950) else Color(0xFF58A6FF)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Linear Progress
                        LinearProgressIndicator(
                            progress = { (planProgressPct / 100f).coerceIn(0f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = if (planProgressPct >= 75) Color(0xFF3FB950) else Color(0xFF58A6FF),
                            trackColor = if (viewModel.isDarkTheme) Color(0xFF21262D) else Color(0xFFEAEFF2)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "$completedMilestones de $totalMilestones sesiones y clases completadas",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                TextButton(
                                    onClick = {
                                        FeedbackManager.playClick(context)
                                        viewModel.openStudyPlanWizard()
                                    },
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                    modifier = Modifier.testTag("btn_configure_study_plan")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.DesignServices,
                                        contentDescription = null,
                                        modifier = Modifier.size(14.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Diseñar Plan", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }

            // 3. Category Filter Chips
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val filters = listOf(
                        Triple("TODOS", "Todos", Icons.Default.AllInclusive),
                        Triple("EXAMENES", "Exámenes Oficiales", Icons.Default.EmojiEvents),
                        Triple("CLASES", "Clases Teóricas y Prácticas", Icons.Default.School),
                        Triple("PLAN_ESTUDIO", "Estudio y Simulacros", Icons.Default.MenuBook)
                    )

                    items(filters) { (key, label, icon) ->
                        val isSelected = viewModel.calendarFilterType == key
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                FeedbackManager.playClick(context)
                                viewModel.calendarFilterType = key
                            },
                            label = { Text(label, fontSize = 12.sp) },
                            leadingIcon = {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                            modifier = Modifier.testTag("filter_chip_$key")
                        )
                    }
                }
            }

            // 4. Interactive Month Calendar Card
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color(0xFFFFFFFF)
                    ),
                    border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("calendar_month_view_card")
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Month navigation row
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = calendarMonthName,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                TextButton(
                                    onClick = {
                                        FeedbackManager.playClick(context)
                                        viewModel.setCalendarToToday()
                                    },
                                    modifier = Modifier.testTag("calendar_today_button")
                                ) {
                                    Text("Hoy", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                                IconButton(
                                    onClick = {
                                        FeedbackManager.playClick(context)
                                        viewModel.changeCalendarMonth(-1)
                                    },
                                    modifier = Modifier.testTag("calendar_prev_month")
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Mes anterior",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        FeedbackManager.playClick(context)
                                        viewModel.changeCalendarMonth(1)
                                    },
                                    modifier = Modifier.testTag("calendar_next_month")
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = "Mes siguiente",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Days of week header (Lun, Mar, Mié, Jue, Vie, Sáb, Dom)
                        val daysOfWeek = listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            daysOfWeek.forEach { dayName ->
                                Text(
                                    text = dayName,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider(color = if (viewModel.isDarkTheme) Color(0xFF21262D) else Color(0xFFEAEFF2))
                        Spacer(modifier = Modifier.height(8.dp))

                        // Grid of days
                        val rows = daysInMonthGrid.chunked(7)
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            rows.forEach { week ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    week.forEach { dateIso ->
                                        if (dateIso == null) {
                                            Box(modifier = Modifier.weight(1f).aspectRatio(1f))
                                        } else {
                                            val dayNumber = dateIso.substringAfterLast("-").toIntOrNull() ?: 1
                                            val isSelected = dateIso == viewModel.selectedCalendarDate
                                            val isToday = dateIso == todayIso
                                            val dayEvents = eventsByDateMap[dateIso] ?: emptyList()

                                            Box(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .aspectRatio(1f)
                                                    .clip(RoundedCornerShape(10.dp))
                                                    .background(
                                                        when {
                                                            isSelected -> MaterialTheme.colorScheme.primaryContainer
                                                            isToday -> if (viewModel.isDarkTheme) Color(0xFF21262D) else Color(0xFFE8F1FC)
                                                            else -> Color.Transparent
                                                        }
                                                    )
                                                    .border(
                                                        width = if (isSelected) 1.5.dp else if (isToday) 1.dp else 0.dp,
                                                        color = if (isSelected) MaterialTheme.colorScheme.primary else if (isToday) Color(0xFF58A6FF) else Color.Transparent,
                                                        shape = RoundedCornerShape(10.dp)
                                                    )
                                                    .clickable {
                                                        FeedbackManager.playClick(context)
                                                        viewModel.selectedCalendarDate = dateIso
                                                    }
                                                    .testTag("calendar_day_$dateIso"),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Column(
                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                    verticalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        text = "$dayNumber",
                                                        fontSize = 13.sp,
                                                        fontWeight = if (isSelected || isToday) FontWeight.ExtraBold else FontWeight.Medium,
                                                        color = when {
                                                            isSelected -> MaterialTheme.colorScheme.onPrimaryContainer
                                                            isToday -> MaterialTheme.colorScheme.primary
                                                            else -> MaterialTheme.colorScheme.onSurface
                                                        }
                                                    )

                                                    // Dots for events
                                                    if (dayEvents.isNotEmpty()) {
                                                        Row(
                                                            horizontalArrangement = Arrangement.Center,
                                                            modifier = Modifier.padding(top = 2.dp)
                                                        ) {
                                                            dayEvents.take(3).forEach { ev ->
                                                                val dotColor = when (ev.eventType) {
                                                                    "EXAM_THEORY" -> Color(0xFFF85149)
                                                                    "EXAM_PRACTICE" -> Color(0xFF3FB950)
                                                                    "CLASS_THEORY" -> Color(0xFF58A6FF)
                                                                    "CLASS_PRACTICE" -> Color(0xFFBC8CFF)
                                                                    "SIMULATION" -> Color(0xFFE67E22)
                                                                    else -> Color(0xFFF5B041)
                                                                }
                                                                Box(
                                                                    modifier = Modifier
                                                                        .padding(horizontal = 1.dp)
                                                                        .size(4.dp)
                                                                        .clip(CircleShape)
                                                                        .background(dotColor)
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    val missing = 7 - week.size
                                    repeat(missing) {
                                        Box(modifier = Modifier.weight(1f).aspectRatio(1f))
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Legend of colors
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            LegendItem(color = Color(0xFFF85149), label = "Ex. Teórico")
                            LegendItem(color = Color(0xFF3FB950), label = "Ex. Práctico")
                            LegendItem(color = Color(0xFF58A6FF), label = "Clase Teórica")
                            LegendItem(color = Color(0xFFBC8CFF), label = "Clase Taller")
                            LegendItem(color = Color(0xFFF5B041), label = "Estudio")
                        }
                    }
                }
            }

            // 5. Selected Day's Agenda
            item {
                val formattedSelectedDate = try {
                    val d = sdfIso.parse(viewModel.selectedCalendarDate)
                    if (d != null) sdfDisplay.format(d).replaceFirstChar { it.uppercase() } else viewModel.selectedCalendarDate
                } catch (_: Exception) {
                    viewModel.selectedCalendarDate
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Agenda del Día",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = formattedSelectedDate,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    FilledTonalButton(
                        onClick = {
                            FeedbackManager.playClick(context)
                            viewModel.openAddEventDialog(prefilledDate = viewModel.selectedCalendarDate)
                        },
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("btn_add_event_for_selected_day")
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Añadir", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Events for this day
            if (eventsOnSelectedDate.isEmpty()) {
                item {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color(0xFFF6F8FA)
                        ),
                        border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.EventAvailable,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Sin actividades para este día",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Puedes incluir clases presenciales, talleres de laboratorio o reservar tiempo para el examen.",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedButton(
                                onClick = {
                                    FeedbackManager.playClick(context)
                                    viewModel.openAddEventDialog(prefilledDate = viewModel.selectedCalendarDate)
                                }
                            ) {
                                Text("Añadir Clase o Examen")
                            }
                        }
                    }
                }
            } else {
                items(eventsOnSelectedDate, key = { it.id }) { event ->
                    StudentEventCard(
                        event = event,
                        isDark = viewModel.isDarkTheme,
                        onToggleCompletion = {
                            FeedbackManager.playClick(context)
                            viewModel.toggleEventCompletion(event)
                        },
                        onEdit = {
                            FeedbackManager.playClick(context)
                            viewModel.openEditEventDialog(event)
                        },
                        onDelete = {
                            FeedbackManager.playClick(context)
                            viewModel.showDeleteEventConfirmDialog = event
                        }
                    )
                }
            }

            // 6. Chronological Upcoming Key Milestones
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Próximos Hitos del Alumno",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Resumen de las próximas clases y pruebas programadas",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (upcomingEvents.isEmpty()) {
                item {
                    Text(
                        text = "¡Todo al día! No tienes hitos pendientes próximos.",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            } else {
                items(upcomingEvents, key = { "upcoming_${it.id}" }) { event ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color(0xFFFFFFFF)
                        ),
                        border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                FeedbackManager.playClick(context)
                                viewModel.selectedCalendarDate = event.date
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(getEventColor(event.eventType).copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = getEventIcon(event.eventType),
                                    contentDescription = null,
                                    tint = getEventColor(event.eventType),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = event.date,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = getEventColor(event.eventType)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "• ${event.time} (${event.durationMinutes} min)",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Text(
                                    text = event.title,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            IconButton(
                                onClick = {
                                    FeedbackManager.playClick(context)
                                    viewModel.toggleEventCompletion(event)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.CheckCircle,
                                    contentDescription = "Marcar completado",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            // 7. Reset / Restore Default Calendar
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = {
                            FeedbackManager.playClick(context)
                            viewModel.resetCalendarToDefaults()
                        },
                        modifier = Modifier.testTag("btn_reset_calendar_defaults")
                    ) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Restaurar Plan y Clases de Ejemplo Oficiales", fontSize = 12.sp)
                    }
                }
            }
        }
    }

    // Modal Dialog: Add / Edit Event
    if (viewModel.showAddEditEventDialog) {
        AddEditEventDialog(viewModel = viewModel)
    }

    // Modal Dialog: Study Plan Wizard
    if (viewModel.showStudyPlanWizard) {
        StudyPlanWizardDialog(viewModel = viewModel)
    }

    // Confirmation Dialog: Delete Event
    viewModel.showDeleteEventConfirmDialog?.let { eventToDelete ->
        AlertDialog(
            onDismissRequest = { viewModel.showDeleteEventConfirmDialog = null },
            icon = {
                Icon(
                    imageVector = Icons.Default.DeleteOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(30.dp)
                )
            },
            title = { Text("Eliminar Evento", fontWeight = FontWeight.Bold) },
            text = { Text("¿Deseas eliminar '${eventToDelete.title}' del calendario?") },
            confirmButton = {
                Button(
                    onClick = { viewModel.deleteEvent(eventToDelete) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.showDeleteEventConfirmDialog = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

// -------------------------------------------------------------
// Component: Exam Countdown Card
// -------------------------------------------------------------
@Composable
fun ExamCountdownCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    dateStr: String,
    daysRemaining: Int?,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    isDark: Boolean,
    onClick: () -> Unit,
    tag: String
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .testTag(tag),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDark) Color(0xFF161B22) else Color(0xFFFFFFFF)
        ),
        border = BorderStroke(1.2.dp, accentColor.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            accentColor.copy(alpha = if (isDark) 0.12f else 0.08f),
                            Color.Transparent
                        )
                    )
                )
                .padding(14.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(accentColor.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(18.dp))
                    }
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = accentColor.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = if (daysRemaining != null) {
                                when {
                                    daysRemaining < 0 -> "Realizado"
                                    daysRemaining == 0 -> "¡HOY!"
                                    daysRemaining == 1 -> "Mañana"
                                    else -> "$daysRemaining días"
                                }
                            } else "Fijar",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = accentColor,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = dateStr,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// Component: Student Event Card
// -------------------------------------------------------------
@Composable
fun StudentEventCard(
    event: StudentCalendarEventEntity,
    isDark: Boolean,
    onToggleCompletion: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val eventColor = getEventColor(event.eventType)
    val eventIcon = getEventIcon(event.eventType)
    val eventTypeLabel = getEventTypeLabel(event.eventType)

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDark) Color(0xFF161B22) else Color(0xFFFFFFFF)
        ),
        border = BorderStroke(
            1.dp,
            if (event.isCompleted) {
                if (isDark) Color(0xFF30363D) else Color(0xFFE1E4E8)
            } else {
                eventColor.copy(alpha = 0.5f)
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("event_item_${event.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Checkbox for completion
            IconButton(
                onClick = onToggleCompletion,
                modifier = Modifier
                    .size(32.dp)
                    .testTag("toggle_event_${event.id}")
            ) {
                Icon(
                    imageVector = if (event.isCompleted) Icons.Default.CheckCircle else Icons.Outlined.Circle,
                    contentDescription = if (event.isCompleted) "Completado" else "Pendiente",
                    tint = if (event.isCompleted) Color(0xFF3FB950) else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                // Header with chip & time
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = eventColor.copy(alpha = 0.15f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Icon(imageVector = eventIcon, contentDescription = null, tint = eventColor, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = eventTypeLabel, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = eventColor)
                        }
                    }

                    Text(
                        text = "${event.time} • ${event.durationMinutes} min",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Title
                Text(
                    text = event.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = if (event.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (event.isCompleted) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
                )

                // Description
                if (event.description.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = event.description,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 16.sp
                    )
                }

                // Tags row (ITC / Location)
                if (event.relatedItc.isNotBlank() || event.locationOrNotes.isNotBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (event.relatedItc.isNotBlank()) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = if (isDark) Color(0xFF21262D) else Color(0xFFEAEFF2)
                            ) {
                                Text(
                                    text = "📑 ${event.relatedItc}",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        if (event.locationOrNotes.isNotBlank()) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = if (isDark) Color(0xFF21262D) else Color(0xFFEAEFF2)
                            ) {
                                Text(
                                    text = "📍 ${event.locationOrNotes}",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }

            // Action menu
            Column(horizontalAlignment = Alignment.End) {
                IconButton(
                    onClick = onEdit,
                    modifier = Modifier.size(28.dp).testTag("edit_event_${event.id}")
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
                    modifier = Modifier.size(28.dp).testTag("delete_event_${event.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// Component: Legend Item
// -------------------------------------------------------------
@Composable
fun LegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = label, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

// -------------------------------------------------------------
// Dialog: Add / Edit Event Modal
// -------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditEventDialog(viewModel: MainViewModel) {
    val context = LocalContext.current
    val isEditing = viewModel.editingEvent != null

    val eventTypes = listOf(
        Pair("CLASS_THEORY", "Clase Teórica"),
        Pair("CLASS_PRACTICE", "Clase Práctica / Taller"),
        Pair("EXAM_THEORY", "Examen Teórico Oficial"),
        Pair("EXAM_PRACTICE", "Examen Práctico Oficial"),
        Pair("STUDY_SESSION", "Sesión de Estudio"),
        Pair("SIMULATION", "Simulacro Oficial")
    )

    // Android DatePicker dialog launcher
    fun showDatePicker() {
        val cal = Calendar.getInstance()
        try {
            val parts = viewModel.eventInputDate.split("-")
            if (parts.size == 3) {
                cal.set(Calendar.YEAR, parts[0].toInt())
                cal.set(Calendar.MONTH, parts[1].toInt() - 1)
                cal.set(Calendar.DAY_OF_MONTH, parts[2].toInt())
            }
        } catch (_: Exception) {}

        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                viewModel.eventInputDate = String.format(Locale.ROOT, "%04d-%02d-%02d", year, month + 1, dayOfMonth)
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    // Android TimePicker dialog launcher
    fun showTimePicker() {
        val cal = Calendar.getInstance()
        try {
            val parts = viewModel.eventInputTime.split(":")
            if (parts.size == 2) {
                cal.set(Calendar.HOUR_OF_DAY, parts[0].toInt())
                cal.set(Calendar.MINUTE, parts[1].toInt())
            }
        } catch (_: Exception) {}

        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                viewModel.eventInputTime = String.format(Locale.ROOT, "%02d:%02d", hourOfDay, minute)
            },
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }

    AlertDialog(
        onDismissRequest = { viewModel.showAddEditEventDialog = false },
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (isEditing) Icons.Default.EditCalendar else Icons.Default.AddCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isEditing) "Editar Actividad" else "Nueva Clase / Examen",
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
                // Event Type Chips
                Text("Tipo de Actividad:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(eventTypes) { (typeKey, typeLabel) ->
                        FilterChip(
                            selected = viewModel.eventInputType == typeKey,
                            onClick = { viewModel.eventInputType = typeKey },
                            label = { Text(typeLabel, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = getEventColor(typeKey).copy(alpha = 0.2f),
                                selectedLabelColor = getEventColor(typeKey)
                            )
                        )
                    }
                }

                // Title
                OutlinedTextField(
                    value = viewModel.eventInputTitle,
                    onValueChange = { viewModel.eventInputTitle = it },
                    label = { Text("Título de la actividad *") },
                    placeholder = { Text("ej. Clase: ITC-BT-18 y Puesta a Tierra") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("input_event_title")
                )

                // Date and Time Pickers
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { showDatePicker() },
                        modifier = Modifier.weight(1f).testTag("btn_pick_event_date")
                    ) {
                        Icon(imageVector = Icons.Default.CalendarToday, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = viewModel.eventInputDate, fontSize = 12.sp)
                    }

                    OutlinedButton(
                        onClick = { showTimePicker() },
                        modifier = Modifier.weight(1f).testTag("btn_pick_event_time")
                    ) {
                        Icon(imageVector = Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = viewModel.eventInputTime, fontSize = 12.sp)
                    }
                }

                // Duration Selector Chips
                Text("Duración (minutos):", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf(45, 60, 90, 120, 180).forEach { mins ->
                        FilterChip(
                            selected = viewModel.eventInputDurationMinutes == mins,
                            onClick = { viewModel.eventInputDurationMinutes = mins },
                            label = { Text("${mins}m", fontSize = 11.sp) }
                        )
                    }
                }

                // Related ITC / Topic
                OutlinedTextField(
                    value = viewModel.eventInputRelatedItc,
                    onValueChange = { viewModel.eventInputRelatedItc = it },
                    label = { Text("ITC / Temario") },
                    placeholder = { Text("ej. ITC-BT-18, ITC-28, Cuadros...") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Location / Classroom / Sede
                OutlinedTextField(
                    value = viewModel.eventInputLocation,
                    onValueChange = { viewModel.eventInputLocation = it },
                    label = { Text("Ubicación / Sede / Notas") },
                    placeholder = { Text("ej. Aula 3 / Taller / Convocatoria Industria") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Description
                OutlinedTextField(
                    value = viewModel.eventInputDescription,
                    onValueChange = { viewModel.eventInputDescription = it },
                    label = { Text("Descripción / Material necesario") },
                    placeholder = { Text("ej. Llevar reglamento REBT, calculadora homologada y bolígrafo...") },
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    FeedbackManager.playClick(context)
                    viewModel.saveEventFromDialog()
                },
                enabled = viewModel.eventInputTitle.isNotBlank(),
                modifier = Modifier.testTag("btn_save_event")
            ) {
                Text(if (isEditing) "Actualizar" else "Guardar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { viewModel.showAddEditEventDialog = false },
                modifier = Modifier.testTag("btn_cancel_save_event")
            ) {
                Text("Cancelar")
            }
        }
    )
}

// -------------------------------------------------------------
// Dialog: Study Plan Wizard Modal
// -------------------------------------------------------------
@Composable
fun StudyPlanWizardDialog(viewModel: MainViewModel) {
    val context = LocalContext.current

    // DatePicker for Theoretical Exam
    fun pickTheoryDate() {
        val cal = Calendar.getInstance()
        try {
            val parts = viewModel.planWizardTheoryDate.split("-")
            if (parts.size == 3) {
                cal.set(Calendar.YEAR, parts[0].toInt())
                cal.set(Calendar.MONTH, parts[1].toInt() - 1)
                cal.set(Calendar.DAY_OF_MONTH, parts[2].toInt())
            }
        } catch (_: Exception) {}

        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                viewModel.planWizardTheoryDate = String.format(Locale.ROOT, "%04d-%02d-%02d", year, month + 1, dayOfMonth)
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    // DatePicker for Practical Exam
    fun pickPracticeDate() {
        val cal = Calendar.getInstance()
        try {
            val parts = viewModel.planWizardPracticeDate.split("-")
            if (parts.size == 3) {
                cal.set(Calendar.YEAR, parts[0].toInt())
                cal.set(Calendar.MONTH, parts[1].toInt() - 1)
                cal.set(Calendar.DAY_OF_MONTH, parts[2].toInt())
            }
        } catch (_: Exception) {}

        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                viewModel.planWizardPracticeDate = String.format(Locale.ROOT, "%04d-%02d-%02d", year, month + 1, dayOfMonth)
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    AlertDialog(
        onDismissRequest = { viewModel.showStudyPlanWizard = false },
        icon = {
            Icon(
                imageVector = Icons.Default.AutoFixHigh,
                contentDescription = null,
                tint = Color(0xFFF5B041),
                modifier = Modifier.size(32.dp)
            )
        },
        title = {
            Text(
                text = "Diseñador de Plan de Estudios",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Configura tus fechas oficiales para generar automáticamente un plan de estudio con clases teóricas, talleres de verificación y simulacros cronometrados.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Call Name
                OutlinedTextField(
                    value = viewModel.planWizardCallName,
                    onValueChange = { viewModel.planWizardCallName = it },
                    label = { Text("Nombre de la Convocatoria") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Theoretical Exam Target Date
                Text("Día del Examen Teórico Oficial:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                OutlinedButton(
                    onClick = { pickTheoryDate() },
                    modifier = Modifier.fillMaxWidth().testTag("btn_wizard_pick_theory_date")
                ) {
                    Icon(imageVector = Icons.Default.Assignment, contentDescription = null, tint = Color(0xFFF85149))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Examen Teórico: ${viewModel.planWizardTheoryDate}")
                }

                // Practical Exam Target Date
                Text("Día del Examen Práctico Oficial:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                OutlinedButton(
                    onClick = { pickPracticeDate() },
                    modifier = Modifier.fillMaxWidth().testTag("btn_wizard_pick_practice_date")
                ) {
                    Icon(imageVector = Icons.Default.Handyman, contentDescription = null, tint = Color(0xFF3FB950))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Examen Práctico: ${viewModel.planWizardPracticeDate}")
                }

                // Plan Mode
                Text("Ritmo de Preparación:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    PlanModeOption(
                        title = "🚀 Intensivo (15h/semana)",
                        description = "Para alumnos con examen inminente en 2-4 semanas.",
                        selected = viewModel.planWizardMode == "INTENSIVO",
                        onSelect = { viewModel.planWizardMode = "INTENSIVO" }
                    )
                    PlanModeOption(
                        title = "⚖️ Estándar (8h/semana)",
                        description = "Ritmo equilibrado compaginado con trabajo (4-8 semanas).",
                        selected = viewModel.planWizardMode == "ESTANDAR",
                        onSelect = { viewModel.planWizardMode = "ESTANDAR" }
                    )
                    PlanModeOption(
                        title = "⏳ Extendido (4h/semana)",
                        description = "Preparación a medio plazo con estudio pausado (8-12 semanas).",
                        selected = viewModel.planWizardMode == "EXTENDIDO",
                        onSelect = { viewModel.planWizardMode = "EXTENDIDO" }
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    FeedbackManager.playClick(context)
                    viewModel.generateStudyPlanFromWizard()
                },
                enabled = viewModel.planWizardTheoryDate.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF5B041),
                    contentColor = Color.Black
                ),
                modifier = Modifier.testTag("btn_confirm_generate_study_plan")
            ) {
                Icon(imageVector = Icons.Default.Bolt, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Generar Plan en Calendario", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = { viewModel.showStudyPlanWizard = false }) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun PlanModeOption(
    title: String,
    description: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable { onSelect() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            1.dp,
            if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(selected = selected, onClick = onSelect)
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Text(text = description, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

// -------------------------------------------------------------
// Helpers: Type to Color and Label
// -------------------------------------------------------------
fun getEventColor(type: String): Color {
    return when (type) {
        "EXAM_THEORY" -> Color(0xFFF85149)
        "EXAM_PRACTICE" -> Color(0xFF3FB950)
        "CLASS_THEORY" -> Color(0xFF58A6FF)
        "CLASS_PRACTICE" -> Color(0xFFBC8CFF)
        "SIMULATION" -> Color(0xFFE67E22)
        else -> Color(0xFFF5B041)
    }
}

fun getEventIcon(type: String): androidx.compose.ui.graphics.vector.ImageVector {
    return when (type) {
        "EXAM_THEORY" -> Icons.Default.Assignment
        "EXAM_PRACTICE" -> Icons.Default.Handyman
        "CLASS_THEORY" -> Icons.Default.School
        "CLASS_PRACTICE" -> Icons.Default.Construction
        "SIMULATION" -> Icons.Default.Timer
        else -> Icons.Default.MenuBook
    }
}

fun getEventTypeLabel(type: String): String {
    return when (type) {
        "EXAM_THEORY" -> "Examen Teórico"
        "EXAM_PRACTICE" -> "Examen Práctico"
        "CLASS_THEORY" -> "Clase Teórica"
        "CLASS_PRACTICE" -> "Clase Práctica / Taller"
        "SIMULATION" -> "Simulacro Oficial"
        else -> "Plan de Estudio"
    }
}
