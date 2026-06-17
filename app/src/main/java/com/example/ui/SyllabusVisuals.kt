package com.example.ui

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun SyllabusVisualAid(itcId: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F141C)),
        border = BorderStroke(1.dp, Color(0xFF21262D))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "Infografía Interactiva",
                    tint = Color(0xFFFFD2D2),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "INFOGRAFÍA ACTIVA Y MATERIAL GRÁFICO (REBT)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF8B949E),
                    letterSpacing = 1.sp
                )
            }

            when (itcId) {
                "art-1-5" -> Art1To5MindMap()
                "art-6-15" -> Art6To15PowerCalculator()
                "art-16-29" -> Art16To29SilencioFlow()
                "itc-01" -> Itc01SafetyVoltages()
                "itc-03" -> Itc03InstallerComparison()
                "itc-04" -> Itc04GarageDecisionTree()
                "itc-05" -> Itc05OcaInspections()
                "itc-07" -> Itc07TrenchCanvas()
                "itc-10" -> Itc10ElectrificationPlanner()
                "itc-14" -> Itc14EnlaceSchematic()
                "itc-17" -> Itc17DinRailPanel()
                "itc-18" -> Itc18EarthPitInfographic()
                "itc-25" -> Itc25CircuitMatrix()
                else -> {
                    Text(
                        "Gráfico resumen de apoyo disponible para este capítulo reglamentario.",
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}

// 1. Art-1-5: Interactive Mind Map of Scope and Exclusions
@Composable
fun Art1To5MindMap() {
    var selectedNode by remember { mutableStateOf<String?>("voltages") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Mapa Conceptual: Límites de Tención y Exclusiones",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Interactive Graph representation
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // General Scope Node
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedNode == "voltages") Color(0x3358A6FF) else Color(0xFF161B22)
                ),
                border = BorderStroke(1.dp, if (selectedNode == "voltages") Color(0xFF58A6FF) else Color(0xFF30363D)),
                modifier = Modifier
                    .width(150.dp)
                    .clickable { selectedNode = "voltages" }
            ) {
                Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.FlashOn, contentDescription = null, tint = Color(0xFFF1C40F))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Tensiones REBT", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("CA ≤ 1000V\nCC ≤ 1500V", fontSize = 10.sp, color = Color.LightGray, textAlign = TextAlign.Center)
                }
            }

            // Exclusions Node
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedNode == "excl") Color(0x33F85149) else Color(0xFF161B22)
                ),
                border = BorderStroke(1.dp, if (selectedNode == "excl") Color(0xFFF85149) else Color(0xFF30363D)),
                modifier = Modifier
                    .width(150.dp)
                    .clickable { selectedNode = "excl" }
            ) {
                Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.NotInterested, contentDescription = null, tint = Color(0xFFE74C3C))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Exclusiones", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("Fuera del ámbito", fontSize = 10.sp, color = Color.LightGray, textAlign = TextAlign.Center)
                }
            }

            // Modif Node
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedNode == "modif") Color(0x332ECC71) else Color(0xFF161B22)
                ),
                border = BorderStroke(1.dp, if (selectedNode == "modif") Color(0xFF2ECC71) else Color(0xFF30363D)),
                modifier = Modifier
                    .width(150.dp)
                    .clickable { selectedNode = "modif" }
            ) {
                Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Autorenew, contentDescription = null, tint = Color(0xFF2ECC71))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Modificaciones", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("> 50% de Potencia", fontSize = 10.sp, color = Color.LightGray, textAlign = TextAlign.Center)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Dynamic detail content
        AnimatedContent(targetState = selectedNode, label = "mindmap") { target ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)),
                border = BorderStroke(1.dp, Color(0xFF30363D))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    when (target) {
                        "voltages" -> {
                            Text("Rangos Ofiales de Baja Tensión (Art. 2):", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.White)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("• Corriente Alterna (CA): Igual o inferior a 1.000 V eficaces a frecuencia estándar de 50 Hz.", fontSize = 11.sp, color = Color.LightGray)
                            Text("• Corriente Continua (CC): Igual o inferior a 1.500 V de valor medio.", fontSize = 11.sp, color = Color.LightGray)
                            Text("• Todo voltaje por encima se tipifica regulatoriamente como Alta Tensión (RAT / RLAT).", fontSize = 11.sp, color = Color.LightGray)
                        }
                        "excl" -> {
                            Text("Exclusiones Rigurosas del Reglamento (Art. 2.1):", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFFF85149))
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("• Minas subterráneas, tracción ferroviaria, navíos, aeronaves, sistemas de defensa.", fontSize = 11.sp, color = Color.LightGray)
                            Text("⚠️ ¡Cuidado en examen!: Las estaciones de recarga de vehículos eléctricos (VE) SÍ están dentro (ITC-BT-52).", fontSize = 11.sp, color = Color(0xFFFFD2D2), style = androidx.compose.ui.text.TextStyle(fontStyle = FontStyle.Italic))
                        }
                        "modif" -> {
                            Text("Regla del 50% (Modificaciones de Importancia):", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF2ECC71))
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("• Exclusivamente se exige tramitar como nueva instalación reglamentaria si la ampliación o alteración supera el 50% de la potencia total contratada de origen.", fontSize = 11.sp, color = Color.LightGray)
                        }
                    }
                }
            }
        }
    }
}

// 2. Art-6-15: Slider-Based Supply Power Planner Calculator
@Composable
fun Art6To15PowerCalculator() {
    var powerInput by remember { mutableStateOf(50.0f) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Planificador Interactivo de Suministros Especiales",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Text(
            text = "Ajusta la potencia contratada total de tu instalación para visualizar el porcentaje legal mínimo obligatorio de los suministros complementarios.",
            fontSize = 11.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Potencia Total Contratada:", fontSize = 12.sp, color = Color.LightGray)
            Text(
                text = "${powerInput.roundToInt()} kW",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF58A6FF)
            )
        }

        Slider(
            value = powerInput,
            onValueChange = { powerInput = it },
            valueRange = 10f..250f,
            steps = 24,
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF58A6FF),
                activeTrackColor = Color(0xFF58A6FF)
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Results Grid
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            // Socorro
            val socorroKw = String.format("%.1f", powerInput * 0.15)
            PowerProgressRow(label = "Socorro (Mín. 15%)", kw = "$socorroKw kW", fraction = 0.15f, color = Color(0xFFF39C12))

            // Reserva
            val reservaKw = String.format("%.1f", powerInput * 0.25)
            PowerProgressRow(label = "Reserva (Mín. 25%)", kw = "$reservaKw kW", fraction = 0.25f, color = Color(0xFF58A6FF))

            // Duplicado
            val duplicadoKw = String.format("%.1f", powerInput * 0.50)
            PowerProgressRow(label = "Duplicado (Mín. 50%)", kw = "$duplicadoKw kW", fraction = 0.50f, color = Color(0xFF2ECC71))
        }
    }
}

@Composable
fun PowerProgressRow(label: String, kw: String, fraction: Float, color: Color) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text(kw, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = color)
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { fraction },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = color,
            trackColor = Color(0xFF21262D)
        )
    }
}

// 3. Art-16-29: Administrative flow & CIE registration with distributor positive silence vs industry negative silence
@Composable
fun Art16To29SilencioFlow() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Ciclo de CIE y Desglose de Silencios Administrativos",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)),
            border = BorderStroke(1.dp, Color(0xFF21262D))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(Color(0xFF2ECC71), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("1", fontSize = 10.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("CIE / Boletín de Instalación", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
                Text(
                    "Suscrito por Instalador Habilitado e inscrito obligatoriamente en Industria para activar el contrato de suministro.",
                    fontSize = 11.sp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(start = 24.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(Color(0xFF58A6FF), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("2", fontSize = 10.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Buzón de Plazos de Silencio (3 Meses)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
                
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = Color(0x222ECC71)),
                        border = BorderStroke(1.dp, Color(0xFF2ECC71))
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("Normas Empresas", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2ECC71))
                            Text("Silencio = POSITIVO", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                            Text("Especificaciones particulares de la distribuidora.", fontSize = 9.sp, color = Color.LightGray)
                        }
                    }

                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = Color(0x22E74C3C)),
                        border = BorderStroke(1.dp, Color(0xFFE74C3C))
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("Excepciones Industria", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE74C3C))
                            Text("Silencio = NEGATIVO", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                            Text("Imposibilidades físicas de obra presentadas.", fontSize = 9.sp, color = Color.LightGray)
                        }
                    }
                }
            }
        }
    }
}

// 4. ITC-01: Safety Voltages (Seco, Húmedo, Mojado)
@Composable
fun Itc01SafetyVoltages() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Tensiones de Contacto Límites Convencionales",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            VoltageIndicatorMeter(envName = "Locales Secos Ordinarios", voltageLimit = "50 V", fraction = 0.8f, color = Color(0xFFF39C12))
            VoltageIndicatorMeter(envName = "Locales Húmedos/Mojados", voltageLimit = "24 V", fraction = 0.45f, color = Color(0xFF58A6FF))
            VoltageIndicatorMeter(envName = "Piscinas / Áreas Sumergidas", voltageLimit = "12 V", fraction = 0.25f, color = Color(0xFFE74C3C))
        }
    }
}

@Composable
fun VoltageIndicatorMeter(envName: String, voltageLimit: String, fraction: Float, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)),
        border = BorderStroke(1.dp, Color(0xFF21262D))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(color.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(voltageLimit, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = color)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(envName, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = Color(0xFF21262D))
                LinearProgressIndicator(
                    progress = { fraction },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp),
                    color = color,
                    trackColor = Color(0xFF21262D)
                )
            }
        }
    }
}

// 5. ITC-03: Installer comparison Basics vs Specialists
@Composable
fun Itc03InstallerComparison() {
    var expandedTopic by remember { mutableStateOf<String?>("basics") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Habilitación Profesional de Empresas Instaladoras (ITC-BT-03)",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { expandedTopic = "basics" },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (expandedTopic == "basics") Color(0xFF58A6FF) else Color(0xFF161B22),
                    contentColor = if (expandedTopic == "basics") Color.Black else Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, if (expandedTopic == "basics") Color(0xFF58A6FF) else Color(0xFF30363D))
            ) {
                Text("Categoría Básica (IBTB)", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = { expandedTopic = "specialist" },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (expandedTopic == "specialist") Color(0xFFbc8cff) else Color(0xFF161B22),
                    contentColor = if (expandedTopic == "specialist") Color.Black else Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, if (expandedTopic == "specialist") Color(0xFFbc8cff) else Color(0xFF30363D))
            ) {
                Text("Especialista (IBTE)", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)),
            border = BorderStroke(1.dp, Color(0xFF21262D))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                if (expandedTopic == "basics") {
                    Text("Competencias del Instalador Básico IBTB:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF58A6FF))
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("✅ Montaje, mantenimiento y reparación de instalaciones domésticas en general.", fontSize = 11.sp, color = Color.LightGray)
                    Text("✅ Oficinas y locales comerciales comunes sin catalogación de riesgo.", fontSize = 11.sp, color = Color.LightGray)
                    Text("✅ Pequeños talleres artesanales y alumbrado interior monofásico.", fontSize = 11.sp, color = Color.LightGray)
                } else {
                    Text("Competencias del Instalador Especialista IBTE:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFFbc8cff))
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("🔥 Locales con riesgo de incendio o explosión (Clasificación ATEX).", fontSize = 11.sp, color = Color.LightGray)
                    Text("🏥 Quirófanos fijos y salas de intervención crítica médica en general.", fontSize = 11.sp, color = Color.LightGray)
                    Text("☀️ Sistemas de energía solar fotovoltaica y generadores de baja tensión.", fontSize = 11.sp, color = Color.LightGray)
                    Text("🔋 Infraestructuras de recarga de vehículos eléctricos (ITC-BT-52).", fontSize = 11.sp, color = Color.LightGray)
                    Text("💡 Redes de distribución subterránea y Alumbrado Público > 5 kW.", fontSize = 11.sp, color = Color.LightGray)
                }
            }
        }
    }
}

// 6. ITC-04: Decision Tree Calculator for Garages (Project vs MTD)
@Composable
fun Itc04GarageDecisionTree() {
    var hasForcedVentilation by remember { mutableStateOf(false) }
    var garageSpaces by remember { mutableStateOf(5) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Asistente Regulador de Trámites sobre Aparcamientos",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Text(
            text = "Efectúa la simulación legal según las variables del garaje para concluir la tramitación exigida.",
            fontSize = 11.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Switch ventilación
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("¿Ventilación Forzada por Motor (Mecánica)?:", fontSize = 11.sp, color = Color.LightGray)
            Switch(
                checked = hasForcedVentilation,
                onCheckedChange = { hasForcedVentilation = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFF58A6FF),
                    checkedTrackColor = Color(0xFF161B22)
                )
            )
        }

        // Spaces Slider
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Número de Plazas de Aparcamiento:", fontSize = 11.sp, color = Color.LightGray)
            Text("$garageSpaces plazas", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
        Slider(
            value = garageSpaces.toFloat(),
            onValueChange = { garageSpaces = it.roundToInt() },
            valueRange = 1f..15f,
            steps = 14,
            colors = SliderDefaults.colors(thumbColor = Color.White, activeTrackColor = Color.Gray)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Diagnostic output
        val requiresProject = hasForcedVentilation || garageSpaces > 5
        val diagnosticText = if (requiresProject) {
            "PROYECTO TÉCNICO FIRMADO POR INGENIERO"
        } else {
            "MEMORIA TÉCNICA DE DISEÑO (MTD) POR INSTALADOR"
        }
        val cardColor = if (requiresProject) Color(0x33F85149) else Color(0x332ECC71)
        val borderColor = if (requiresProject) Color(0xFFF85149) else Color(0xFF2ECC71)

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardColors(containerColor = cardColor, contentColor = Color.White, disabledContainerColor = cardColor, disabledContentColor = Color.White),
            border = BorderStroke(1.dp, borderColor)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "Resultado Legal del Análisis:",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = borderColor
                )
                Text(
                    text = diagnosticText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = if (hasForcedVentilation) {
                        "• Motivo: Con ventilación forzada mecánica, TODO garaje exige proyecto de forma absoluta sin importar el número de plazas."
                    } else if (garageSpaces > 5) {
                        "• Motivo: Al superar el límite legal estricto de 5 plazas tradicionales bajo ventilación natural, se requiere proyecto."
                    } else {
                        "• Motivo: Garaje de hasta 5 plazas exactas con ventilación natural, exento de proyecto facultativo."
                    },
                    fontSize = 10.sp,
                    color = Color.LightGray
                )
            }
        }
    }
}

// 7. ITC-05: OCA Inspection intervals selector
@Composable
fun Itc05OcaInspections() {
    var activeCategory by remember { mutableStateOf("quir") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Intervalos de Inspección Reglamentaria (OCA)",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                Triple("Clinicos / Quirófanos", "quir", Color(0xFFF39C12)),
                Triple("Generales / ATEX / Comercios", "gen", Color(0xFF58A6FF)),
                Triple("Comunidades Vecinos > 100kW", "comun", Color(0xFF2ECC71))
            ).forEach { (label, id, col) ->
                ElevatedFilterChip(
                    selected = activeCategory == id,
                    onClick = { activeCategory = id },
                    label = { Text(label, fontSize = 11.sp, fontWeight = FontWeight.Bold) }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)),
            border = BorderStroke(1.dp, Color(0xFF21262D))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                when (activeCategory) {
                    "quir" -> {
                        Text("🔬 Quirófanos y Salas Médicas Críticas", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFFF39C12))
                        Text(
                            text = "Inspección Obligatoria: CADA 1 AÑO (Anual)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        Text("Se audita anualmente para controlar asislamientos y transformadores de aislamiento especiales.", fontSize = 10.sp, color = Color.LightGray)
                    }
                    "gen" -> {
                        Text("🏭 Locales Industriales, ATEX, Comercios Grandes", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF58A6FF))
                        Text(
                            text = "Inspección Obligatoria: CADA 5 AÑOS (Quinquenal)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        Text("Aplica a industrias >100 kW, garajes >25 plazas con ventilación mecánica, locales ATEX y escuelas públicas.", fontSize = 10.sp, color = Color.LightGray)
                    }
                    "comun" -> {
                        Text("🏢 Bloque de Viviendas Residencial (Portal/Comunes)", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF2ECC71))
                        Text(
                            text = "Inspección Obligatoria: CADA 10 AÑOS (Decenal)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        Text("Viviendas de más de 100 kW totales en acometida. No confundir con la ITE general del edificio físico.", fontSize = 10.sp, color = Color.LightGray)
                    }
                }
            }
        }
    }
}

// 8. ITC-07: Soil Underground Trench Blueprint Drawing
@Composable
fun Itc07TrenchCanvas() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Corte Técnico de Zanja Subterránea (ITC-BT-07)",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)),
            border = BorderStroke(1.dp, Color(0xFF21262D))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                // blueprint layout representation
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .background(Color(0xFF07090C))
                ) {
                    val w = size.width
                    val h = size.height

                    // Ground Level line
                    drawLine(Color.Gray, start = androidx.compose.ui.geometry.Offset(0f, 15f), end = androidx.compose.ui.geometry.Offset(w, 15f), strokeWidth = 2.dp.toPx())

                    // Sidewalk side and Roadway side indicators
                    drawRect(Color(0xFF2C3E50), size = androidx.compose.ui.geometry.Size(w * 0.45f, 10f), topLeft = androidx.compose.ui.geometry.Offset(5f, 5f))
                    drawRect(Color(0xFF34495E), size = androidx.compose.ui.geometry.Size(w * 0.45f, 10f), topLeft = androidx.compose.ui.geometry.Offset(w * 0.52f, 5f))

                    // Trench zanja cavity rectangle outline
                    drawRect(
                        Color(0xFF58A6FF).copy(alpha = 0.2f),
                        topLeft = androidx.compose.ui.geometry.Offset(w * 0.3f, 15f),
                        size = androidx.compose.ui.geometry.Size(w * 0.4f, h - 35f)
                    )

                    // Sand bed area (Lecho arena)
                    drawRect(
                        Color(0xFFFFD2D2).copy(alpha = 0.5f),
                        topLeft = androidx.compose.ui.geometry.Offset(w * 0.35f, h * 0.65f),
                        size = androidx.compose.ui.geometry.Size(w * 0.3f, h * 0.25f)
                    )

                    // Cable Dot inside sand bed
                    drawCircle(
                        Color(0xFFE74C3C),
                        radius = 8f,
                        center = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.75f)
                    )

                    // Brick layer above sand bed
                    drawRect(
                        Color(0xFFE67E22),
                        topLeft = androidx.compose.ui.geometry.Offset(w * 0.36f, h * 0.55f),
                        size = androidx.compose.ui.geometry.Size(w * 0.28f, 12f)
                    )

                    // Warning Text labels coordinates
                    // 0.60m walkway depth / 0.80m roadway depth
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text("Profundidades Legales Exigidas:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("• Bajo Acera Común para peatones: Mínimo 0.60 metros.", fontSize = 10.sp, color = Color.LightGray)
                Text("• Cruces de Calzadas de Tráfico de Vehículos: Mínimo 0.80 metros.", fontSize = 10.sp, color = Color.LightGray)
                Text("• Lecho de Arena Fina: Espesor mínimo perimetral de 10 cm con tejas protectoras y cinta de aviso en PVC.", fontSize = 10.sp, color = Color.LightGray)
            }
        }
    }
}

// 9. ITC-10: Electric layout planner Basic vs Elevated
@Composable
fun Itc10ElectrificationPlanner() {
    var hasHeavyLoad1 by remember { mutableStateOf(false) } // AC
    var hasHeavyLoad2 by remember { mutableStateOf(false) } // Heating
    var hasHeavyLoad3 by remember { mutableStateOf(false) } // Pool
    var largeSurface by remember { mutableStateOf(false) }  // >160m

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Calificador de Grado de Electrificación de Vivienda",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Text(
            text = "Selecciona qué equipamiento o parámetros se prevén en los esquemas unifilares:",
            fontSize = 11.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Checkbox Grid styled beautifully
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFF161B22),
            border = BorderStroke(1.dp, Color(0xFF21262D))
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = largeSurface, onCheckedChange = { largeSurface = it })
                    Text("Superficie útil de vivienda > 160 m²", fontSize = 11.sp, color = Color.White)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = hasHeavyLoad1, onCheckedChange = { hasHeavyLoad1 = it })
                    Text("Previsión de Aire Acondicionado central", fontSize = 11.sp, color = Color.White)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = hasHeavyLoad2, onCheckedChange = { hasHeavyLoad2 = it })
                    Text("Calefacción Eléctrica o acumuladores", fontSize = 11.sp, color = Color.White)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = hasHeavyLoad3, onCheckedChange = { hasHeavyLoad3 = it })
                    Text("Instalación de Piscina con bomba depuradora", fontSize = 11.sp, color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        val isElevated = largeSurface || hasHeavyLoad1 || hasHeavyLoad2 || hasHeavyLoad3

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = if (isElevated) Color(0x33F0C040) else Color(0x3358A6FF)
            ),
            border = BorderStroke(1.dp, if (isElevated) Color(0xFFF0C040) else Color(0xFF58A6FF))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = if (isElevated) "GRADO EXIGIDO: ELEVADO" else "GRADO REQUERIDO: BÁSICO",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (isElevated) Color(0xFFF0C040) else Color(0xFF58A6FF)
                )
                Text(
                    text = if (isElevated) {
                        "Previsión mínima de 9.200 W (a 230 V con IGA de 40 A). Circuitos obligatorios C1 a C5, más C6 (adicional unificado cocina/baño) y circuitos dedicados C7 a C12 según corresponda."
                    } else {
                        "Previsión estándar de 5.750 W (a 230 V con IGA de 25 A). Circuitos básicos obligatorios esenciales C1, C2, C3, C4 y C5."
                    },
                    fontSize = 11.sp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

// 10. ITC-14: Enlace unifilar diagram
@Composable
fun Itc14EnlaceSchematic() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Esquema Unifilar Completo: Instalaciones de Enlace",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)),
            border = BorderStroke(1.dp, Color(0xFF21262D))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                EnlaceBoxNode("Red Distribución Pública", "Acometida de Compañía", Color.Gray)
                Icon(Icons.Default.ArrowDownward, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(16.dp))
                EnlaceBoxNode("CGP - Caja General de Protección", "Fusibles Generales obligatorios", Color(0xFFF39C12))
                Icon(Icons.Default.ArrowDownward, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(16.dp))
                EnlaceBoxNode("LGA - Línea General de Alimentación", "Cu min 16mm² / Al min 25mm² • Caída: 0,5% general", Color(0xFFE74C3C))
                Icon(Icons.Default.ArrowDownward, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(16.dp))
                EnlaceBoxNode("Contador General de Centralización", "Ubicados en local único o armarios", Color(0xFF2ECC71))
                Icon(Icons.Default.ArrowDownward, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(16.dp))
                EnlaceBoxNode("DI - Derivación Individual", "Diámetro mín de Tubo: 32 mm • Caída en vivienda: 1.5%", Color(0xFF58A6FF))
            }
        }
    }
}

@Composable
fun EnlaceBoxNode(title: String, subtitle: String, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(0.9f),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F141C)),
        border = BorderStroke(1.dp, color)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = color)
            Text(subtitle, fontSize = 9.sp, color = Color.LightGray)
        }
    }
}

// 11. ITC-17: Breaker Panel Mockup with click "TEST" RCD
@Composable
fun Itc17DinRailPanel() {
    val context = androidx.compose.ui.platform.LocalContext.current
    var isRcdTripped by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Esquema Cuadro Principal (CGMP)",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            TextButton(onClick = {
                if (isRcdTripped) {
                    isRcdTripped = false
                    FeedbackManager.playClick(context)
                }
            }) {
                Text("RESETEAR CUADRO", fontSize = 10.sp, color = Color(0xFF58A6FF))
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)),
            border = BorderStroke(1.dp, Color(0xFF21262D))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                // DIN Rail Frame Representation
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF21262D), RoundedCornerShape(4.dp))
                        .padding(vertical = 12.dp, horizontal = 6.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Breaker IGA
                    BreakerWidget(label = "IGA\n25A", color = Color(0xFFE74C3C), isTripped = isRcdTripped)
                    // Breaker Sobretensiones
                    BreakerWidget(label = "PCS\nSurge", color = Color.Gray, isTripped = isRcdTripped)
                    
                    // Breaker Diferencial RCD with click action
                    Card(
                        modifier = Modifier
                            .width(80.dp)
                            .height(65.dp)
                            .clickable {
                                if (!isRcdTripped) {
                                    isRcdTripped = true
                                    FeedbackManager.playTripPower(context)
                                }
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = if (isRcdTripped) Color(0xFF4A1A1A) else Color(0xFF163C1E)
                        ),
                        border = BorderStroke(1.dp, if (isRcdTripped) Color.Red else Color.Green)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(3.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceAround
                        ) {
                            Text("ID 30mA", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Box(
                                modifier = Modifier
                                    .size(24.dp, 14.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(Color.Yellow)
                                    .clickable {
                                        if (!isRcdTripped) {
                                            isRcdTripped = true
                                            FeedbackManager.playTripPower(context)
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text("TEST", fontSize = 8.sp, color = Color.Black, fontWeight = FontWeight.ExtraBold)
                            }
                            Text(if (isRcdTripped) "[OFF]" else "[ON]", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = if (isRcdTripped) Color.Red else Color.Green)
                        }
                    }

                    // Breaker PIAs
                    BreakerWidget(label = "PIA C1\n10A", color = Color(0xFF58A6FF), isTripped = isRcdTripped)
                    BreakerWidget(label = "PIA C2\n16A", color = Color(0xFF58A6FF), isTripped = isRcdTripped)
                    BreakerWidget(label = "PIA C3\n25A", color = Color(0xFF58A6FF), isTripped = isRcdTripped)
                }

                Spacer(modifier = Modifier.height(10.dp))

                AnimatedVisibility(visible = isRcdTripped) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0x33E74C3C)),
                        border = BorderStroke(1.dp, Color(0xFFE74C3C))
                    ) {
                        Text(
                            text = "⚠️ ¡DISPARADO POR SIMULACIÓN! Un diferencial de 30mA desconectará el carril DIN de aguas abajo en <40ms ante una fuga residual.",
                            fontSize = 11.sp,
                            color = Color(0xFFFFC0C0),
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BreakerWidget(label: String, color: Color, isTripped: Boolean) {
    Card(
        modifier = Modifier
            .width(55.dp)
            .height(65.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F141C)),
        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(color)
            )
            Text(label, fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.White, textAlign = TextAlign.Center)
            Text(
                text = if (isTripped) "OFF" else "ON",
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                color = if (isTripped) Color.Red else Color.LightGray
            )
        }
    }
}

// 12. ITC-18: Ground Well Pit & Rod Illustration
@Composable
fun Itc18EarthPitInfographic() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Detalle Constructivo de Puesta a Tierra (ITC-18)",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)),
            border = BorderStroke(1.dp, Color(0xFF21262D))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .background(Color(0xFF07090C))
                ) {
                    val w = size.width
                    val h = size.height

                    // Earth ground surface
                    drawLine(Color(0xFF8E44AD), start = androidx.compose.ui.geometry.Offset(0f, 30f), end = androidx.compose.ui.geometry.Offset(w, 30f), strokeWidth = 3.dp.toPx())

                    // Ground well box (Arqueta Registrable) representation
                    drawRect(
                        Color(0xFF95A5A6),
                        topLeft = androidx.compose.ui.geometry.Offset(w * 0.4f, 15f),
                        size = androidx.compose.ui.geometry.Size(w * 0.2f, 35f)
                    )

                    // Rod (Pica) extending downwards
                    drawLine(
                        Color(0xFFF39C12),
                        start = androidx.compose.ui.geometry.Offset(w * 0.5f, 50f),
                        end = androidx.compose.ui.geometry.Offset(w * 0.5f, h - 10f),
                        strokeWidth = 5.dp.toPx()
                    )

                    // Ground Wire feed bare copper
                    drawLine(
                        Color(0xFFD35400),
                        start = androidx.compose.ui.geometry.Offset(w * 0.1f, 25f),
                        end = androidx.compose.ui.geometry.Offset(w * 0.45f, 40f),
                        strokeWidth = 2.dp.toPx()
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text("Especificaciones de Ensayo y Obra:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("• Profundidad mínima de la pica: 0.50 metros.", fontSize = 10.sp, color = Color.LightGray)
                Text("• Conductor Desnudo Enterrado: Mínimo 35 mm² de sección.", fontSize = 10.sp, color = Color.LightGray)
                Text("• Conductor Aislado Enterrado: Mínimo 16 mm² con aislante termoplástico.", fontSize = 10.sp, color = Color.LightGray)
                Text("⚠️ Prohibición Absoluta: Queda prohibido conectarse a cañerías públicas de gas o calefacción.", fontSize = 10.sp, color = Color(0xFFFFC0C0))
            }
        }
    }
}

// 13. ITC-25: Clickable Circuit Grid Matrix comparison
@Composable
fun Itc25CircuitMatrix() {
    var selectedCircuitRow by remember { mutableStateOf("C2") }

    val circuits = listOf(
        CircuitRowData("C1", "Alumbrado", "10 A", "1,5 mm²", "Max 30 puntos luz"),
        CircuitRowData("C2", "Bases / Enchufes", "16 A", "2,5 mm²", "Max 20 enchufes"),
        CircuitRowData("C3", "Cocina y Horno", "25 A", "6,0 mm²", "Max 2 tomas de fuerza"),
        CircuitRowData("C4", "Lavadora/Termo", "20 A", "4,0 mm²", "O desdoblado en 3"),
        CircuitRowData("C5", "Enchufes Cocina", "16 A", "2,5 mm²", "Max 6 enchu húmedos")
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Matriz Comparativa de Circuitos de Vivienda (ITC-25)",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        // Table headers
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF161B22))
                .padding(6.dp)
        ) {
            Text("Cód", modifier = Modifier.weight(0.12f), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.LightGray)
            Text("Uso Principal", modifier = Modifier.weight(0.35f), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.LightGray)
            Text("PIA", modifier = Modifier.weight(0.18f), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.LightGray)
            Text("Sección", modifier = Modifier.weight(0.2f), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.LightGray)
        }

        circuits.forEach { row ->
            val isSelected = selectedCircuitRow == row.id
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (isSelected) Color(0xFF21262D) else Color.Transparent)
                    .clickable { selectedCircuitRow = row.id }
                    .padding(vertical = 8.dp, horizontal = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(row.id, modifier = Modifier.weight(0.12f), fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = if (isSelected) Color(0xFF58A6FF) else Color.White)
                Text(row.name, modifier = Modifier.weight(0.35f), fontSize = 11.sp, color = Color.White)
                Text(row.pia, modifier = Modifier.weight(0.18f), fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Yellow)
                Text(row.section, modifier = Modifier.weight(0.2f), fontSize = 11.sp, color = Color.LightGray)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Row detail Card
        val selectedObj = circuits.find { it.id == selectedCircuitRow } ?: circuits[0]
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)),
            border = BorderStroke(1.dp, Color(0xFF58A6FF))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "Reglas de Instalación para ${selectedObj.id} (${selectedObj.name}):",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF58A6FF)
                )
                Text(
                    text = "• Interruptor Automático (PIA) requerido: ${selectedObj.pia}.",
                    fontSize = 11.sp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = "• Sección mínima obligatoria de cobre rígido: ${selectedObj.section}.",
                    fontSize = 11.sp,
                    color = Color.LightGray
                )
                Text(
                    text = "• Restricciones de carga e hilos: ${selectedObj.limit}.",
                    fontSize = 11.sp,
                    color = Color.LightGray
                )
            }
        }
    }
}

data class CircuitRowData(
    val id: String,
    val name: String,
    val pia: String,
    val section: String,
    val limit: String
)
