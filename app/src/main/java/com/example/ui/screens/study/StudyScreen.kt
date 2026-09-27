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


    val categories = listOf("Todos", "Articulado", "Administrativas", "Enlace", "Interiores")

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
                    text = "Temario oficial subrayado con puntos críticos, trampas de examen y esquemas técnicos interactivos.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
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

        // Category Filter Chips
        item {
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
        }

        // Syllabus Cards
        items(filteredItems, key = { it.id }) { item ->
            StudyItemCard(
                item = item,
                isDark = viewModel.isDarkTheme,
                isVisualExpanded = expandedVisualId == item.id,
                onToggleVisual = {
                    FeedbackManager.playClick(context)
                    expandedVisualId = if (expandedVisualId == item.id) null else item.id
                },
                onStartPractice = {
                    FeedbackManager.playClick(context)
                    // Map syllabus id to a question pool
                    val mappedKey = when {
                        item.id.startsWith("art") -> "articulado"
                        item.id in listOf("itc-03", "itc-04", "itc-05") -> "empresas"
                        item.id in listOf("itc-10", "itc-14", "itc-15", "itc-17") -> "enlace"
                        item.id in listOf("itc-18", "itc-21") -> "tierra"
                        item.id in listOf("itc-25", "itc-27") -> "interiores"
                        else -> "especiales"
                    }
                    viewModel.startTopicPractice(mappedKey)
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
    onToggleVisual: () -> Unit,
    onStartPractice: () -> Unit,
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
        border = BorderStroke(1.dp, if (isDark) Color(0xFF30363D) else Color(0xFFE1E4E8))
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
                        color = Color(0xFF58A6FF).copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = item.code,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isDark) Color(0xFF58A6FF) else Color(0xFF0969DA),
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

            // Interactive Visual Aid (if expanded)
            AnimatedVisibility(visible = isVisualExpanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    SyllabusVisualAid(item.id)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onStartPractice,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isDark) Color(0xFF238636) else Color(0xFF1F883D)
                    )
                ) {
                    Icon(Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Practicar", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = onToggleVisual,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = if (isVisualExpanded) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (isVisualExpanded) "Ocultar" else "Esquema", fontSize = 12.sp)
                }

                IconButton(
                    onClick = onAddReminder,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.NotificationAdd,
                        contentDescription = "Crear Recordatorio para esta ITC",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }

            }
        }
    }
}
