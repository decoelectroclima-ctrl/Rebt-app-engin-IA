package com.example.ui.screens.study

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.MainViewModel
import com.example.data.Content
import com.example.data.UnderliningItcItem
import com.example.ui.FeedbackManager
import com.example.ui.SyllabusVisualAid
import kotlinx.coroutines.launch

@Composable
fun StudyScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var selectedCategory by remember { mutableStateOf("Todos") }
    var searchQuery by remember { mutableStateOf("") }
    var expandedVisualId by remember { mutableStateOf<String?>(null) }
    var expandAllVisuals by remember { mutableStateOf(false) }
    val dailyActivity by viewModel.dailyActivityFlow.collectAsState()

    val todayDate = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
    val isToday = dailyActivity?.lastActiveDate == todayDate
    val studiedItcsList = if (isToday) {
        dailyActivity?.studiedItcsToday?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() } ?: emptyList()
    } else {
        emptyList()
    }
    val hasStudiedToday = isToday && (studiedItcsList.isNotEmpty() || (dailyActivity?.questionsAnswered ?: 0) > 0)
    val reminderHour = dailyActivity?.dailyStudyReminderHour ?: 20
    val reminderMinute = dailyActivity?.dailyStudyReminderMinute ?: 0
    val formattedReminderTime = String.format("%02d:%02d", reminderHour, reminderMinute)

    val categories = listOf("Todos", "Articulado", "Administrativas", "Distribución", "Enlace", "Interiores", "Especiales", "Receptores")

    val filteredItems = Content.SYLLABUS.filter { item ->
        val matchesCategory = selectedCategory == "Todos" || item.category.equals(selectedCategory, ignoreCase = true)
        val matchesQuery = searchQuery.isBlank() ||
                item.title.contains(searchQuery, ignoreCase = true) ||
                item.code.contains(searchQuery, ignoreCase = true) ||
                item.keyConcept.contains(searchQuery, ignoreCase = true)
        matchesCategory && matchesQuery
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header
        item {
            Column {
                Text(
                    text = "Manual de Estudio REBT 2026",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Catálogo oficial completo (${Content.SYLLABUS.size} Artículos e ITCs) con puntos críticos subrayados, trampas de examen y esquemas.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Daily Study Goal & Reminder Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("study_daily_goal_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (hasStudiedToday) {
                        if (viewModel.isDarkTheme) Color(0xFF13231B) else Color(0xFFE6F4EA)
                    } else {
                        if (viewModel.isDarkTheme) Color(0xFF261D13) else Color(0xFFFEF3E2)
                    }
                ),
                border = BorderStroke(
                    1.dp,
                    if (hasStudiedToday) Color(0xFF3FB950).copy(alpha = 0.5f) else Color(0xFFF59E0B).copy(alpha = 0.5f)
                )
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
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
                                    .background(
                                        if (hasStudiedToday) Color(0xFF3FB950).copy(alpha = 0.2f) else Color(0xFFF59E0B).copy(alpha = 0.2f)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (hasStudiedToday) Icons.Default.CheckCircle else Icons.Default.Alarm,
                                    contentDescription = null,
                                    tint = if (hasStudiedToday) Color(0xFF3FB950) else Color(0xFFD97706),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = if (hasStudiedToday) "¡Meta de Estudio Cumplida Hoy! 🎉" else "Meta de Estudio Diaria Pendiente",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = if (hasStudiedToday) {
                                        if (viewModel.isDarkTheme) Color(0xFF7EE787) else Color(0xFF1A7F37)
                                    } else {
                                        if (viewModel.isDarkTheme) Color(0xFFFBBF24) else Color(0xFFB45309)
                                    }
                                )
                                Text(
                                    text = if (hasStudiedToday) {
                                        "Has repasado ${studiedItcsList.size} ${if (studiedItcsList.size == 1) "ITC" else "ITCs"} hoy."
                                    } else {
                                        "Aviso automático programado a las $formattedReminderTime si no estudias."
                                    },
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
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

                    if (studiedItcsList.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "ITCs estudiadas hoy:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(studiedItcsList) { itc ->
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color(0xFF3FB950).copy(alpha = 0.2f),
                                    border = BorderStroke(1.dp, Color(0xFF3FB950).copy(alpha = 0.4f))
                                ) {
                                    Text(
                                        text = "✓ $itc",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (viewModel.isDarkTheme) Color(0xFF7EE787) else Color(0xFF1A7F37),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Search Bar
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Buscar por ITC, artículo o palabra clave...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Limpiar")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("study_search_field"),
                shape = RoundedCornerShape(14.dp),
                singleLine = true
            )
        }

        // Category Filter Chips & Infographics Global Toggle
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(categories) { cat ->
                        FilterChip(
                            selected = selectedCategory == cat,
                            onClick = {
                                FeedbackManager.playClick(context)
                                selectedCategory = cat
                            },
                            label = { Text(cat, fontSize = 12.sp) },
                            modifier = Modifier.testTag("study_filter_$cat")
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${filteredItems.size} ${if (filteredItems.size == 1) "norma disponible" else "normas disponibles"}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    FilledTonalButton(
                        onClick = {
                            FeedbackManager.playClick(context)
                            expandAllVisuals = !expandAllVisuals
                        },
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = if (expandAllVisuals) {
                                if (viewModel.isDarkTheme) Color(0xFF1F6FEB).copy(alpha = 0.3f) else Color(0xFFDDF4FF)
                            } else {
                                MaterialTheme.colorScheme.surfaceVariant
                            }
                        )
                    ) {
                        Icon(
                            imageVector = if (expandAllVisuals) Icons.Default.VisibilityOff else Icons.Default.AutoAwesome,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = if (viewModel.isDarkTheme) Color(0xFF58A6FF) else Color(0xFF0969DA)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (expandAllVisuals) "Contraer Infografías" else "Ver Todas las Infografías",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (viewModel.isDarkTheme) Color(0xFF58A6FF) else Color(0xFF0969DA)
                        )
                    }
                }
            }
        }

        // Syllabus Cards
        items(filteredItems, key = { it.id }) { item ->
            val isItemStudiedToday = studiedItcsList.contains(item.code)
            val isVisExpanded = expandAllVisuals || expandedVisualId == item.id
            StudyItemCard(
                item = item,
                isDark = viewModel.isDarkTheme,
                isVisualExpanded = isVisExpanded,
                isStudiedToday = isItemStudiedToday,
                onToggleVisual = {
                    FeedbackManager.playClick(context)
                    viewModel.recordItcStudy(item.code)
                    if (expandAllVisuals) {
                        expandAllVisuals = false
                        expandedVisualId = null
                    } else {
                        expandedVisualId = if (expandedVisualId == item.id) null else item.id
                    }
                },
                onStartPractice = {
                    FeedbackManager.playClick(context)
                    viewModel.recordItcStudy(item.code)
                    // Map syllabus id to a question pool
                    val mappedKey = when {
                        item.id.startsWith("art") -> "articulado"
                        item.id in listOf("itc-01", "itc-02", "itc-03", "itc-04", "itc-05") -> "empresas"
                        item.id in listOf("itc-06", "itc-07", "itc-08", "itc-09") -> "redes"
                        item.id in listOf("itc-10", "itc-11", "itc-12", "itc-13", "itc-14", "itc-15", "itc-16", "itc-17", "itc-52") -> "enlace"
                        item.id in listOf("itc-18", "itc-22", "itc-23", "itc-24") -> "tierra"
                        item.id in listOf("itc-19", "itc-20", "itc-21", "itc-25", "itc-26", "itc-27") -> "interiores"
                        item.id in listOf("itc-43", "itc-44", "itc-45", "itc-46", "itc-47", "itc-48", "itc-49") -> "calculos"
                        else -> "especiales"
                    }
                    viewModel.startTopicPractice(mappedKey)
                },
                onMarkStudied = {
                    FeedbackManager.playClick(context)
                    viewModel.recordItcStudy(item.code)
                },
                onAddReminder = {
                    FeedbackManager.playClick(context)
                    viewModel.openCreateReminderDialog(item.code)
                    viewModel.activeTab = "reminders"
                }
            )
        }
    }
}

@Composable
fun StudyItemCard(
    item: UnderliningItcItem,
    isDark: Boolean,
    isVisualExpanded: Boolean,
    isStudiedToday: Boolean = false,
    onToggleVisual: () -> Unit,
    onStartPractice: () -> Unit,
    onMarkStudied: () -> Unit,
    onAddReminder: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("study_card_${item.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDark) Color(0xFF161B22) else Color.White
        ),
        border = BorderStroke(
            1.dp,
            if (isStudiedToday) Color(0xFF3FB950).copy(alpha = 0.6f) else if (isDark) Color(0xFF30363D) else Color(0xFFE1E4E8)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isStudiedToday) Color(0xFF3FB950).copy(alpha = 0.2f) else Color(0xFF58A6FF).copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = if (isStudiedToday) "✓ ${item.code}" else item.code,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isStudiedToday) {
                                if (isDark) Color(0xFF7EE787) else Color(0xFF1A7F37)
                            } else {
                                if (isDark) Color(0xFF58A6FF) else Color(0xFF0969DA)
                            },
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = item.category,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    if (isStudiedToday) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF3FB950).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "Estudiada hoy",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isDark) Color(0xFF7EE787) else Color(0xFF1A7F37),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = when (item.freq) {
                            "Crítica" -> Color(0xFFF85149).copy(alpha = 0.15f)
                            "Alta" -> Color(0xFFE67E22).copy(alpha = 0.15f)
                            else -> Color(0xFF3FB950).copy(alpha = 0.15f)
                        }
                    ) {
                        Text(
                            text = "Frecuencia: ${item.freq}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = when (item.freq) {
                                "Crítica" -> Color(0xFFF85149)
                                "Alta" -> Color(0xFFE67E22)
                                else -> Color(0xFF3FB950)
                            },
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = item.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Prescripciones Críticas (Red Underline)
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = if (isDark) Color(0xFF1E1715) else Color(0xFFFFF5F5),
                border = BorderStroke(1.dp, Color(0xFFF85149).copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Gavel,
                            contentDescription = null,
                            tint = Color(0xFFF85149),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Prescripciones Obligatorias",
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = Color(0xFFF85149)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    item.redUnderline.forEach { rule ->
                        Text(
                            text = "• $rule",
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Trampa de Examen
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = if (isDark) Color(0xFF1F1C12) else Color(0xFFFFFBEB),
                border = BorderStroke(1.dp, Color(0xFFF5B041).copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.WarningAmber,
                            contentDescription = null,
                            tint = Color(0xFFD97706),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Trampa de Examen Oficial",
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = Color(0xFFD97706)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.trap,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Prominent Infografía & Technical Diagram Section
            Spacer(modifier = Modifier.height(10.dp))
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (isDark) Color(0xFF0F172A) else Color(0xFFF1F5F9),
                border = BorderStroke(
                    1.dp,
                    if (isVisualExpanded) {
                        if (isDark) Color(0xFF38BDF8) else Color(0xFF0284C7)
                    } else {
                        if (isDark) Color(0xFF334155) else Color(0xFFCBD5E1)
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleVisual() }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = if (isDark) Color(0xFF38BDF8) else Color(0xFF0284C7),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Infografía y Esquema Técnico REBT",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = if (isDark) Color(0xFFE2E8F0) else Color(0xFF0F172A)
                            )
                            Text(
                                text = if (isVisualExpanded) "Toca para ocultar esquema gráfico" else "Toca para ver diagramas, fórmulas y esquemas",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Icon(
                        imageVector = if (isVisualExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (isVisualExpanded) "Ocultar" else "Mostrar",
                        tint = if (isDark) Color(0xFF38BDF8) else Color(0xFF0284C7)
                    )
                }
            }

            // Interactive Visual Aid (Infografía completa)
            AnimatedVisibility(visible = isVisualExpanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    SyllabusVisualAid(item.id, isDark = isDark)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Buttons: Symmetrical and well-proportioned
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Primary Action Button (Prominent & Elegantly Aligned)
                Button(
                    onClick = onStartPractice,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("study_practice_${item.id}"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isDark) Color(0xFF238636) else Color(0xFF1F883D),
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Quiz,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Practicar Test de ${item.code}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Secondary Action Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Toggle Infographic Button
                    OutlinedButton(
                        onClick = onToggleVisual,
                        modifier = Modifier
                            .weight(1f)
                            .height(38.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isVisualExpanded) {
                                if (isDark) Color(0xFF38BDF8).copy(alpha = 0.12f) else Color(0xFFE0F2FE)
                            } else Color.Transparent,
                            contentColor = if (isVisualExpanded) {
                                if (isDark) Color(0xFF38BDF8) else Color(0xFF0284C7)
                            } else MaterialTheme.colorScheme.onSurface
                        ),
                        border = BorderStroke(
                            1.dp,
                            if (isVisualExpanded) {
                                if (isDark) Color(0xFF38BDF8) else Color(0xFF0284C7)
                            } else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                        ),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        Icon(
                            imageVector = if (isVisualExpanded) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isVisualExpanded) "Ocultar" else "Infografía",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // Mark as Studied Button
                    OutlinedButton(
                        onClick = onMarkStudied,
                        modifier = Modifier
                            .weight(1f)
                            .height(38.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isStudiedToday) Color(0xFF3FB950).copy(alpha = 0.15f) else Color.Transparent,
                            contentColor = if (isStudiedToday) {
                                if (isDark) Color(0xFF7EE787) else Color(0xFF1A7F37)
                            } else MaterialTheme.colorScheme.primary
                        ),
                        border = BorderStroke(
                            1.dp,
                            if (isStudiedToday) Color(0xFF3FB950) else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                        ),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        Icon(
                            imageVector = if (isStudiedToday) Icons.Default.CheckCircle else Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(15.dp),
                            tint = if (isStudiedToday) (if (isDark) Color(0xFF7EE787) else Color(0xFF1A7F37)) else MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isStudiedToday) "Leída ✓" else "Marcar leída",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // Notification Reminder Button
                    OutlinedIconButton(
                        onClick = onAddReminder,
                        modifier = Modifier.size(38.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.NotificationAdd,
                            contentDescription = "Crear Recordatorio para esta ITC",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}
