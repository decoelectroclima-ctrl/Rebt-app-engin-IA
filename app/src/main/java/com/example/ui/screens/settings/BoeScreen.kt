package com.example.ui.screens.settings

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
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

data class BoeArticleSummary(
    val number: String,
    val title: String,
    val summary: String,
    val tag: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoeScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Documentos Oficiales, 1: Articulado BOE RD 842/2002
    var readingDoc by remember { mutableStateOf<com.example.data.SharedDocument?>(null) }
    var selectedDocCategory by remember { mutableStateOf("Todos") }

    val docCategories = listOf("Todos", "Apuntes", "Esquema", "BOE", "Calculadora")

    val boeArticles = remember {
        listOf(
            BoeArticleSummary(
                number = "Art. 1",
                title = "Objeto",
                summary = "Establecer las condiciones técnicas y garantías que deben reunir las instalaciones eléctricas conectadas a una fuente de suministro de baja tensión.",
                tag = "Ámbito"
            ),
            BoeArticleSummary(
                number = "Art. 2",
                title = "Campo de aplicación",
                summary = "Instalaciones que distribuyan corriente alterna hasta 1.000 V eficaces o corriente continua hasta 1.500 V. Modificación de importancia: >50% de la potencia instalada.",
                tag = "Tensiones"
            ),
            BoeArticleSummary(
                number = "Art. 3",
                title = "Términos y definiciones",
                summary = "Remite a la ITC-BT-01 para la terminología oficial (masas, partes activas, contactos directos e indirectos, corte omnipolar).",
                tag = "Conceptos"
            ),
            BoeArticleSummary(
                number = "Art. 6",
                title = "Equipos y materiales",
                summary = "Los equipos y materiales deben ostentar el Marcado CE conforme a las Directivas Europeas de compatibilidad electromagnética y baja tensión.",
                tag = "Marcado CE"
            ),
            BoeArticleSummary(
                number = "Art. 12",
                title = "Redes de distribución y acometidas",
                summary = "Las empresas distribuidoras son responsables del mantenimiento de las redes de distribución y acometidas hasta las Cajas Generales de Protección.",
                tag = "Distribución"
            ),
            BoeArticleSummary(
                number = "Art. 14",
                title = "Especificaciones particulares de empresas distribuidoras",
                summary = "Aprobadas por los órganos competentes de las CCAA. Tienen silencio administrativo POSITIVO transcurridos 3 meses sin resolución.",
                tag = "Procedimiento"
            ),
            BoeArticleSummary(
                number = "Art. 22",
                title = "Empresas instaladoras habilitadas",
                summary = "Declaración responsable con validez indefinida y eficacia nacional. Póliza de responsabilidad civil obligatoria (600.000€ básica / 900.000€ especialista).",
                tag = "Habilitación"
            ),
            BoeArticleSummary(
                number = "Art. 23",
                title = "Verificaciones previas e inspecciones periódicas",
                summary = "Verificaciones reglamentarias por instalador antes de puesta en marcha. Inspecciones por Organismos de Control Autorizados (OCA) cada 5 o 10 años.",
                tag = "Inspección"
            ),
            BoeArticleSummary(
                number = "Art. 24",
                title = "Excepciones técnicas",
                summary = "En casos excepcionales debidamente justificados ante Industria. Rige el silencio administrativo NEGATIVO si la Administración no responde en 3 meses.",
                tag = "Excepciones"
            ),
            BoeArticleSummary(
                number = "Art. 29",
                title = "Infracciones y sanciones",
                summary = "Tipificación de faltas leves, graves y muy graves según la Ley 21/1992 de Industria, con suspensión de suministro inmediata ante riesgo grave inminente.",
                tag = "Régimen Legal"
            )
        )
    }

    val filteredArticles = remember(searchQuery) {
        if (searchQuery.isBlank()) boeArticles
        else boeArticles.filter {
            it.number.contains(searchQuery, ignoreCase = true) ||
                    it.title.contains(searchQuery, ignoreCase = true) ||
                    it.summary.contains(searchQuery, ignoreCase = true) ||
                    it.tag.contains(searchQuery, ignoreCase = true)
        }
    }

    val filteredDocs = remember(searchQuery, selectedDocCategory) {
        Content.DOCUMENTS.filter { doc ->
            val matchCategory = selectedDocCategory == "Todos" || doc.type.equals(selectedDocCategory, ignoreCase = true)
            val matchQuery = searchQuery.isBlank() ||
                    doc.title.contains(searchQuery, ignoreCase = true) ||
                    doc.description.contains(searchQuery, ignoreCase = true) ||
                    doc.type.contains(searchQuery, ignoreCase = true) ||
                    doc.content.contains(searchQuery, ignoreCase = true)
            matchCategory && matchQuery
        }
    }

    if (readingDoc != null) {
        val currentDoc = readingDoc!!
        AlertDialog(
            onDismissRequest = { readingDoc = null },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = currentDoc.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        maxLines = 2
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 500.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF58A6FF).copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = "${currentDoc.type} • Documentación Técnica Oficial REBT",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF58A6FF),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = currentDoc.description,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (currentDoc.content.isNotBlank()) currentDoc.content else "Documento técnico reglamentario oficial para preparación del examen de instalador autorizado en baja tensión.",
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            confirmButton = {
                Button(onClick = { readingDoc = null }) {
                    Text("Cerrar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as? android.content.ClipboardManager
                        val clip = android.content.ClipData.newPlainText("Apuntes REBT", "${currentDoc.title}\n\n${currentDoc.content}")
                        clipboard?.setPrimaryClip(clip)
                        Toast.makeText(context, "Apuntes copiados al portapapeles", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Text("Copiar Texto")
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
                            imageVector = Icons.Default.Description,
                            contentDescription = null,
                            tint = Color(0xFF58A6FF),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Documentación Oficial REBT",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "BOE RD 842/2002 y Guías Técnicas 2026",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            FeedbackManager.playClick(context)
                            viewModel.activeTab = "settings"
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver a Ajustes"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Tabs
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = {
                        FeedbackManager.playClick(context)
                        selectedTab = 0
                    },
                    text = { Text("Apuntes & Documentos", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = {
                        FeedbackManager.playClick(context)
                        selectedTab = 1
                    },
                    text = { Text("Articulado BOE", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
            }

            // Search Bar
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Buscar en apuntes, guías o articulado...") },
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
                        .testTag("boe_search_field"),
                    shape = RoundedCornerShape(14.dp),
                    singleLine = true
                )
            }

            if (selectedTab == 0) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(top = 4.dp, bottom = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = if (viewModel.isDarkTheme) Color(0xFF162332) else Color(0xFFE8F1FC)
                            ),
                            border = BorderStroke(1.dp, Color(0xFF58A6FF).copy(alpha = 0.5f)),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Verified,
                                        contentDescription = null,
                                        tint = Color(0xFF58A6FF),
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Apuntes Técnicos y Documentación Oficial",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = if (viewModel.isDarkTheme) Color(0xFF58A6FF) else Color(0xFF0969DA)
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Apuntes limpios de estudio (sin marcas de agua ni nombres), esquemas unifilares y textos reglamentarios autorizados para el examen oficial de Instalador.",
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    // Category Filter Chips for Documents
                    item {
                        androidx.compose.foundation.lazy.LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(docCategories) { cat ->
                                val isSelected = selectedDocCategory == cat
                                FilterChip(
                                    selected = isSelected,
                                    onClick = {
                                        FeedbackManager.playClick(context)
                                        selectedDocCategory = cat
                                    },
                                    label = { Text(cat, fontSize = 12.sp) },
                                    leadingIcon = if (isSelected) {
                                        { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                                    } else null
                                )
                            }
                        }
                    }

                    items(filteredDocs, key = { it.id }) { doc ->
                        Card(
                            onClick = {
                                FeedbackManager.playClick(context)
                                readingDoc = doc
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                            ),
                            border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(CircleShape)
                                        .background(
                                            when (doc.type) {
                                                "BOE" -> Color(0xFF58A6FF).copy(alpha = 0.15f)
                                                "Apuntes" -> Color(0xFF7EE787).copy(alpha = 0.15f)
                                                "Esquema" -> Color(0xFF3FB950).copy(alpha = 0.15f)
                                                else -> Color(0xFFF39C12).copy(alpha = 0.15f)
                                            }
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = when (doc.type) {
                                            "BOE" -> Icons.Default.Description
                                            "Apuntes" -> Icons.Default.MenuBook
                                            "Esquema" -> Icons.Default.AccountTree
                                            else -> Icons.Default.Calculate
                                        },
                                        contentDescription = null,
                                        tint = when (doc.type) {
                                            "BOE" -> Color(0xFF58A6FF)
                                            "Apuntes" -> if (viewModel.isDarkTheme) Color(0xFF7EE787) else Color(0xFF1A7F37)
                                            "Esquema" -> Color(0xFF3FB950)
                                            else -> Color(0xFFF39C12)
                                        },
                                        modifier = Modifier.size(24.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(14.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = doc.title,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = doc.description,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        lineHeight = 16.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${doc.type} • ${doc.fileName} • ${doc.fileSize}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                FilledTonalIconButton(
                                    onClick = {
                                        FeedbackManager.playClick(context)
                                        readingDoc = doc
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Visibility,
                                        contentDescription = "Leer",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            if (selectedTab == 1) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(top = 4.dp, bottom = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = if (viewModel.isDarkTheme) Color(0xFF1E1715) else Color(0xFFFFF8F2)
                            ),
                            border = BorderStroke(1.dp, Color(0xFFE67E22).copy(alpha = 0.5f)),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "Articulado Oficial del RD 842/2002",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFE67E22)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "El articulado fija las bases legales generales del reglamento. Las prescripciones técnicas específicas se desarrollan en las 52 ITCs.",
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    items(filteredArticles, key = { it.number }) { art ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                            ),
                            border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                        ) {
                                            Text(
                                                text = art.number,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = art.title,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = MaterialTheme.colorScheme.surfaceVariant
                                    ) {
                                        Text(
                                            text = art.tag,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = art.summary,
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
