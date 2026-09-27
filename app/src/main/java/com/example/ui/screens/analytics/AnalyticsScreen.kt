package com.example.ui.screens.analytics

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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

@Composable
fun AnalyticsScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val examHistory by viewModel.examHistoryFlow.collectAsState()
    val progressList by viewModel.progressFlow.collectAsState()
    val mistakeReviews by viewModel.questionReviewsFlow.collectAsState()

    val totalAnswered = examHistory.sumOf { it.totalCount }
    val totalCorrect = examHistory.sumOf { it.correctCount }
    val globalPct = if (totalAnswered > 0) (totalCorrect * 100 / totalAnswered) else 0

    val readinessStatus = when {
        globalPct >= 80 && totalAnswered >= 100 -> "Nivel Dios: ¡Listo para Examen Oficial!"
        globalPct >= 75 -> "Preparado para Aprobar (75%+)"
        globalPct >= 50 -> "Nivel Intermedio en Proceso"
        else -> "Fase Inicial de Preparación"
    }

    val readinessColor = when {
        globalPct >= 75 -> Color(0xFF3FB950)
        globalPct >= 50 -> Color(0xFFF5B041)
        else -> Color(0xFF58A6FF)
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
                    text = "Analíticas de Aprendizaje",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Diagnóstico detallado de retención, tasa de aciertos por módulo y puntos críticos.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // 1. Overall Readiness Hero
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("analytics_readiness_card"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                ),
                border = BorderStroke(1.5.dp, readinessColor)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Índice de Preparación",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = readinessColor.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = readinessStatus,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = readinessColor,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "$globalPct%",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Black,
                            color = readinessColor
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "precisión global",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = { globalPct / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = readinessColor,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Exámenes: ${examHistory.size}", fontSize = 12.sp, color = Color.Gray)
                        Text("Preguntas: $totalAnswered", fontSize = 12.sp, color = Color.Gray)
                        Text("Aciertos: $totalCorrect", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }

        // 2. Mistakes review shortcut banner
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF1E1715) else Color(0xFFFFF5F5)
                ),
                border = BorderStroke(1.dp, Color(0xFFF85149).copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.WarningAmber,
                        contentDescription = null,
                        tint = Color(0xFFF85149),
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${mistakeReviews.size} Preguntas con Fallos Registrados",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Practica la repetición espaciada para no cometer estos errores en el examen real.",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    if (mistakeReviews.isNotEmpty()) {
                        TextButton(
                            onClick = {
                                FeedbackManager.playClick(context)
                                viewModel.startMistakesReview(mistakeReviews)
                            }
                        ) {
                            Text("Repasar", fontWeight = FontWeight.Bold, color = Color(0xFFF85149))
                        }
                    }
                }
            }
        }

        // 3. Performance Breakdown per REBT Module
        item {
            Text(
                text = "Rendimiento por Módulos Temáticos",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        val allModules = Content.QUESTIONS.values.toList()
        items(allModules, key = { it.id }) { mod ->
            val prog = progressList.find { it.moduleId == mod.id }
            val pct = prog?.pct ?: 0
            val answered = prog?.answeredCount ?: 0

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("analytics_mod_${mod.id}"),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                ),
                border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = mod.icon, fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = mod.label,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Text(
                            text = if (answered > 0) "$pct%" else "Sin datos",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (pct >= 75) Color(0xFF3FB950) else if (pct >= 50) Color(0xFFF5B041) else Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = { if (answered > 0) pct / 100f else 0f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = if (pct >= 75) Color(0xFF3FB950) else if (pct >= 50) Color(0xFFF5B041) else Color(0xFFF85149),
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (answered > 0) "$answered preguntas completadas" else "Pendiente de estudiar",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = if (pct >= 75) "Apto" else if (answered > 0) "Reforzar" else "No iniciado",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (pct >= 75) Color(0xFF3FB950) else Color(0xFFE67E22)
                        )
                    }
                }
            }
        }
    }
}
