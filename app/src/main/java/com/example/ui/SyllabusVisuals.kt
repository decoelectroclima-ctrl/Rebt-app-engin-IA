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
fun SyllabusVisualAid(itcId: String, isDark: Boolean = true) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDark) Color(0xFF0F141C) else Color(0xFFF1F5F9)
        ),
        border = BorderStroke(
            1.dp,
            if (isDark) Color(0xFF21262D) else Color(0xFFCBD5E1)
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "Infografía Interactiva",
                    tint = if (isDark) Color(0xFFFFD2D2) else Color(0xFFD97706),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "INFOGRAFÍA ACTIVA Y ESQUEMA TÉCNICO (REBT)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (isDark) Color(0xFF8B949E) else Color(0xFF475569),
                    letterSpacing = 1.sp
                )
            }

            when (itcId) {
                "art-1-5" -> Art1To5MindMap()
                "art-6-13", "art-6-15" -> Art6To15PowerCalculator()
                "art-14-22", "art-16-29", "art-23-29" -> Art16To29SilencioFlow()
                "itc-01" -> Itc01SafetyVoltages()
                "itc-02" -> Itc02NormasInfographic()
                "itc-03" -> Itc03InstallerComparison()
                "itc-04" -> Itc04GarageDecisionTree()
                "itc-05" -> Itc05OcaInspections()
                "itc-06" -> Itc06AereasInfographic()
                "itc-07" -> Itc07TrenchCanvas()
                "itc-08" -> Itc08EsquemasNeutro()
                "itc-09" -> Itc09AlumbradoInfographic()
                "itc-10" -> Itc10ElectrificationPlanner()
                "itc-11", "itc-12", "itc-14" -> Itc14EnlaceSchematic()
                "itc-13" -> Itc13CgpInfographic()
                "itc-15" -> Itc15DerivacionInfographic()
                "itc-16" -> Itc16ContadoresInfographic()
                "itc-17" -> Itc17DinRailPanel()
                "itc-18" -> Itc18EarthPitInfographic()
                "itc-19", "itc-20" -> Itc19ColoresSeccionesInfographic()
                "itc-21" -> Itc21TubosMatrix()
                "itc-22" -> Itc22CurvasDisparo()
                "itc-23" -> Itc23Sobretensiones()
                "itc-24" -> Itc24ContactosIndirectos()
                "itc-25" -> Itc25CircuitMatrix()
                "itc-26" -> Itc26MontajeVivienda()
                "itc-27" -> Itc27BanosVolumenes()
                "itc-28" -> Itc28PublicaConcurrencia()
                "itc-29" -> Itc29AtexZonas()
                "itc-30" -> Itc30LocalesEspeciales()
                "itc-31" -> Itc31PiscinasVolumenes()
                "itc-32", "itc-33", "itc-34", "itc-35" -> Itc33ObrasSeguridad()
                "itc-36", "itc-37", "itc-39" -> Itc36MbtsInfographic()
                "itc-38" -> Itc38QuirofanosItMedico()
                "itc-40" -> Itc40AutoconsumoGeneradores()
                "itc-41", "itc-42" -> Itc41CampingsMarinas()
                "itc-43", "itc-44", "itc-45", "itc-46", "itc-47", "itc-48", "itc-49", "itc-50" -> Itc46MotoresCondensadores()
                "itc-51" -> Itc51DomoticaInfographic()
                "itc-52" -> Itc52CargaVeInfographic()
                else -> GenericItcTechnicalInfographic(itcId)
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

// 14. ITC-02: Reference Norms Infographic
@Composable
fun Itc02NormasInfographic() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Jerarquía Normativa y Homologaciones (ITC-02)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            NormaBadge("UNE / EN", "Obligadas por REBT", Color(0xFF58A6FF), Modifier.weight(1f))
            NormaBadge("Marcado CE", "Libre Circulación UE", Color(0xFF2ECC71), Modifier.weight(1f))
            NormaBadge("Resoluciones", "Actualizaciones BOE", Color(0xFFF39C12), Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text("• Las normas UNE citadas en el REBT tienen carácter OBLIGATORIO.", fontSize = 11.sp, color = Color.LightGray)
        Text("• El Ministerio de Industria actualiza el listado periódicamente mediante Resoluciones oficiales.", fontSize = 11.sp, color = Color.LightGray)
    }
}

@Composable
private fun NormaBadge(title: String, subtitle: String, color: Color, modifier: Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, color)) {
        Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = color)
            Text(subtitle, fontSize = 9.sp, color = Color.LightGray, textAlign = TextAlign.Center)
        }
    }
}

// 15. ITC-06: Aerial Lines Infographic
@Composable
fun Itc06AereasInfographic() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Redes Aéreas de Distribución en BT (ITC-06)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, Color(0xFF30363D))) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("📐 Alturas Mínimas Reglamentarias sobre el Terreno:", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFF58A6FF))
                Text("• Pasos de calles, avenidas y carreteras: 6,00 metros.", fontSize = 11.sp, color = Color.White)
                Text("• Zonas peatonales y aceras: 4,00 m (tensado) / 2,50 m (posado sobre fachada).", fontSize = 11.sp, color = Color.LightGray)
                Text("• Distancia a ventanas y balcones practicables: Mínimo 1,00 metro.", fontSize = 11.sp, color = Color(0xFFF39C12))
                Text("• Haz trenzado tipo RZ (Aluminio mín. 16 mm² con fiador Almelec).", fontSize = 11.sp, color = Color(0xFF2ECC71))
            }
        }
    }
}

// 16. ITC-08: Neutral & Earthing Systems (TT, TN, IT)
@Composable
fun Itc08EsquemasNeutro() {
    var selectedSchema by remember { mutableStateOf("TT") }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Esquemas de Conexión de Neutro y Masas (ITC-08)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { selectedSchema = "TT" },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = if (selectedSchema == "TT") Color(0xFF58A6FF) else Color(0xFF161B22))
            ) { Text("Esquema TT", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
            Button(
                onClick = { selectedSchema = "TN" },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = if (selectedSchema == "TN") Color(0xFF2ECC71) else Color(0xFF161B22))
            ) { Text("Esquema TN", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
            Button(
                onClick = { selectedSchema = "IT" },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = if (selectedSchema == "IT") Color(0xFFF39C12) else Color(0xFF161B22))
            ) { Text("Esquema IT", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, Color(0xFF30363D))) {
            Column(modifier = Modifier.padding(10.dp)) {
                when (selectedSchema) {
                    "TT" -> {
                        Text("⚡ Esquema TT (Obligatorio en Redes Públicas Españolas):", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFF58A6FF))
                        Text("• Neutro de la fuente a tierra (T) y masas de la instalación a tierra independiente (T).", fontSize = 11.sp, color = Color.LightGray)
                        Text("• Protección OBLIGATORIA mediante Interruptores Diferenciales (RCD ≤ 30mA).", fontSize = 11.sp, color = Color(0xFF2ECC71))
                    }
                    "TN" -> {
                        Text("⚡ Esquema TN (Neutro a tierra, Masas al neutro):", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFF2ECC71))
                        Text("• TN-S: Conductor neutro (N) y de protección (PE) separados en toda la instalación.", fontSize = 11.sp, color = Color.LightGray)
                        Text("• TN-C: Conductor PEN unificado (prohibido cortar el PEN).", fontSize = 11.sp, color = Color.LightGray)
                    }
                    "IT" -> {
                        Text("⚡ Esquema IT (Neutro aislado, Masas a tierra):", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFFF39C12))
                        Text("• Máxima continuidad de servicio: El primer defecto a masa NO provoca disparo.", fontSize = 11.sp, color = Color.LightGray)
                        Text("• Obligatorio en Quirófanos y procesos industriales críticos con vigilador VMA.", fontSize = 11.sp, color = Color(0xFFFFD2D2))
                    }
                }
            }
        }
    }
}

// 17. ITC-09: Outdoor Lighting
@Composable
fun Itc09AlumbradoInfographic() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Alumbrado Exterior y Viales (ITC-09)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, Color(0xFF30363D))) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("• Caída de tensión máxima admisible: 3,0% desde el cuadro de mando.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF39C12))
                Text("• Toma de tierra en báculos: Resistencia ≤ 30 Ω interconectada con Cu desnudo 35 mm².", fontSize = 11.sp, color = Color.White)
                Text("• Aislamiento de cables subterráneos: Mínimo 0,6/1 kV (RV-K o XZ1).", fontSize = 11.sp, color = Color.LightGray)
                Text("• Control horario con reloj astronómico para ahorro energético.", fontSize = 11.sp, color = Color(0xFF2ECC71))
            }
        }
    }
}

// 18. ITC-13: CGP & CPM
@Composable
fun Itc13CgpInfographic() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Cajas Generales de Protección CGP / CPM (ITC-13)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, Color(0xFF30363D))) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("• Ubicación: Fachada exterior o límite de la finca en zona de libre acceso público.", fontSize = 11.sp, color = Color.White)
                Text("• Altura reglamentaria: Entre 0,50 m y 2,00 m desde la rasante del suelo.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF58A6FF))
                Text("• Resistencia a impactos: Grado IK08 mínimo (IK09 en zonas de tráfico rodado).", fontSize = 11.sp, color = Color(0xFFF39C12))
                Text("• Fusibles de cuchilla NH de alto poder de corte (≥ 100 kA).", fontSize = 11.sp, color = Color.LightGray)
            }
        }
    }
}

// 19. ITC-15: Derivación Individual
@Composable
fun Itc15DerivacionInfographic() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Derivaciones Individuales DI (ITC-15)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, Color(0xFF30363D))) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("• Sección mínima de cobre: 6 mm² (con cable de mando rojo 1,5 mm²).", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2ECC71))
                Text("• Diámetro exterior mínimo del tubo protector: 32 mm.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF58A6FF))
                Text("• Caída de tensión máxima: 1,5% (contadores centralizados) / 0,5% (individual).", fontSize = 11.sp, color = Color(0xFFF39C12))
                Text("• Cables de Alta Seguridad (AS) no propagadores de llama ni emisión de halógenos.", fontSize = 11.sp, color = Color.LightGray)
            }
        }
    }
}

// 20. ITC-16: Meter Rooms
@Composable
fun Itc16ContadoresInfographic() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Centralización de Contadores (ITC-16)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, Color(0xFF30363D))) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("• Local técnico exclusivo obligatorio: A partir de > 16 contadores.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF58A6FF))
                Text("• Altura libre del local: 2,30 m con pasillo frontal de mínimo 1,10 m.", fontSize = 11.sp, color = Color.White)
                Text("• Puerta corta-fuegos EI2 60-C5 con apertura hacia afuera.", fontSize = 11.sp, color = Color(0xFFF39C12))
                Text("• Extintor de CO2 de 5 kg y alumbrado de emergencia de 5 lux en cuadros.", fontSize = 11.sp, color = Color.LightGray)
            }
        }
    }
}

// 21. ITC-19: Colors & Voltage Drops
@Composable
fun Itc19ColoresSeccionesInfographic() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Código de Colores y Caídas de Tensión (ITC-19)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            ColorChipWidget("Fases", "Marrón/Negro/Gris", Color(0xFF8D6E63), Modifier.weight(1f))
            ColorChipWidget("Neutro", "Azul Claro", Color(0xFF42A5F5), Modifier.weight(1f))
            ColorChipWidget("Tierra", "Verde-Amarillo", Color(0xFF66BB6A), Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, Color(0xFF30363D))) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text("• Caída máxima en interiores: 3,0% (Alumbrado) y 5,0% (Fuerza/Otros usos).", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF39C12))
                Text("• Sección mínima obligatoria: 1,5 mm² en iluminación / 2,5 mm² en tomas.", fontSize = 11.sp, color = Color.White)
            }
        }
    }
}

@Composable
private fun ColorChipWidget(name: String, label: String, color: Color, modifier: Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, color)) {
        Column(modifier = Modifier.padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier.size(14.dp).background(color, CircleShape))
            Text(name, fontWeight = FontWeight.Bold, fontSize = 10.sp, color = Color.White)
            Text(label, fontSize = 8.sp, color = Color.LightGray, textAlign = TextAlign.Center)
        }
    }
}

// 22. ITC-21: Conduit Tubes Matrix
@Composable
fun Itc21TubosMatrix() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Diámetros Exteriores de Tubos Empotrados (ITC-21)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, Color(0xFF30363D))) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("• 3 conductores de 1,5 mm²: Tubo mínimo Ø 16 mm.", fontSize = 11.sp, color = Color.White)
                Text("• 3 conductores de 2,5 mm²: Tubo mínimo Ø 20 mm.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF58A6FF))
                Text("• 3 conductores de 6,0 mm²: Tubo mínimo Ø 25 mm.", fontSize = 11.sp, color = Color.White)
                Text("• Derivación Individual (DI): Tubo mínimo Ø 32 mm.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF39C12))
                Text("⚠️ Prohibido realizar empalmes dentro de los tubos protectores.", fontSize = 10.sp, color = Color(0xFFFFD2D2))
            }
        }
    }
}

// 23. ITC-22: Breaker Curves
@Composable
fun Itc22CurvasDisparo() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Curvas de Disparo Magnetotérmico (ITC-22)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            CurvaCard("Curva B", "3 a 5 In", "Líneas largas y generadores", Color(0xFF58A6FF), Modifier.weight(1f))
            CurvaCard("Curva C", "5 a 10 In", "Estándar doméstico / terciario", Color(0xFF2ECC71), Modifier.weight(1f))
            CurvaCard("Curva D", "10 a 20 In", "Motores y picos arranque", Color(0xFFF39C12), Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text("Regla de oro de sobrecarga: IB ≤ In ≤ Iz  y  I2 ≤ 1,45 · Iz", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF58A6FF))
    }
}

@Composable
private fun CurvaCard(curva: String, rango: String, uso: String, color: Color, modifier: Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, color)) {
        Column(modifier = Modifier.padding(6.dp)) {
            Text(curva, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = color)
            Text(rango, fontWeight = FontWeight.ExtraBold, fontSize = 10.sp, color = Color.White)
            Text(uso, fontSize = 8.sp, color = Color.LightGray)
        }
    }
}

// 24. ITC-23: Surge Protections
@Composable
fun Itc23Sobretensiones() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Protección contra Sobretensiones (ITC-23)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, Color(0xFF30363D))) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("⚡ Transitorias (DPS / Descargadores):", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFF58A6FF))
                Text("• Derivan las ondas de rayo a tierra mediante varistores sin cortar el suministro.", fontSize = 10.sp, color = Color.LightGray)
                Text("⚡ Permanentes (Rotura de Neutro a 400V):", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFFF39C12))
                Text("• Disparan mecánicamente el IGA general para proteger los electrodomésticos.", fontSize = 10.sp, color = Color.LightGray)
            }
        }
    }
}

// 25. ITC-24: Direct / Indirect Contacts
@Composable
fun Itc24ContactosIndirectos() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Protección contra Choques Eléctricos (ITC-24)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, Color(0xFF30363D))) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("• Contacto Directo: Partes activas. Barreras IP2X y diferencial ≤ 30 mA.", fontSize = 11.sp, color = Color.White)
                Text("• Contacto Indirecto: Masas con defecto. Coordinación RA · IΔn ≤ 50 V (seco) / 24 V (húmedo).", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF58A6FF))
                Text("• MBTS: Muy Baja Tensión de Seguridad aislada sin tierra a ≤ 12 V o ≤ 50 V.", fontSize = 11.sp, color = Color(0xFF2ECC71))
            }
        }
    }
}

// 26. ITC-26: Domestic Mounting
@Composable
fun Itc26MontajeVivienda() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Prescripciones de Montaje en Viviendas (ITC-26)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, Color(0xFF30363D))) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("• Altura de tomas generales: Mínimo 30 cm sobre suelo terminado.", fontSize = 11.sp, color = Color.White)
                Text("• Altura sobre encimera en cocinas: Mínimo 1,10 m.", fontSize = 11.sp, color = Color.White)
                Text("• Tomas con obturadores de seguridad infantil tipo Schuko 16A.", fontSize = 11.sp, color = Color(0xFF2ECC71))
                Text("• Obligatorio llevar conductor de tierra (PE) a todos los puntos de luz.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF39C12))
            }
        }
    }
}

// 27. ITC-27: Bathrooms Volumes
@Composable
fun Itc27BanosVolumenes() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Volúmenes de Seguridad en Baños (ITC-27)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            VolumenCard("Vol 0", "Interior Bañera", "IPX7\nMBTS 12V", Color(0xFFE74C3C), Modifier.weight(1f))
            VolumenCard("Vol 1", "Hasta 2,25m", "IPX4\nSin tomas", Color(0xFFF39C12), Modifier.weight(1f))
            VolumenCard("Vol 2", "Franja 0,6m", "IPX4\nAfeitadora", Color(0xFFF1C40F), Modifier.weight(1f))
            VolumenCard("Vol 3", "Franja 2,4m", "ID 30mA\nTomas OK", Color(0xFF2ECC71), Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text("• Unión equipotencial suplementaria obligatoria en tuberías metálicas.", fontSize = 10.sp, color = Color.LightGray)
    }
}

@Composable
private fun VolumenCard(vol: String, desc: String, req: String, color: Color, modifier: Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, color)) {
        Column(modifier = Modifier.padding(4.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(vol, fontWeight = FontWeight.Bold, fontSize = 10.sp, color = color)
            Text(desc, fontSize = 8.sp, color = Color.White, textAlign = TextAlign.Center)
            Text(req, fontSize = 8.sp, color = Color.LightGray, textAlign = TextAlign.Center)
        }
    }
}

// 28. ITC-28: Public Premises
@Composable
fun Itc28PublicaConcurrencia() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Locales de Pública Concurrencia LPC (ITC-28)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, Color(0xFF30363D))) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("• Cables de Alta Seguridad (AS): Libres de halógenos Cca-s1b,d1,a1 obligatorios.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE74C3C))
                Text("• Alumbrado de emergencia: 1 lux en suelo de evacuación / 5 lux en cuadros y botiquín.", fontSize = 11.sp, color = Color.White)
                Text("• Autonomía mínima de luces de emergencia: 1 hora.", fontSize = 11.sp, color = Color(0xFF58A6FF))
                Text("• Suministro de socorro: Mínimo 15% de la potencia total contratada.", fontSize = 11.sp, color = Color(0xFFF39C12))
            }
        }
    }
}

// 29. ITC-29: ATEX Hazardous Areas
@Composable
fun Itc29AtexZonas() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Atmósferas Explosivas ATEX (ITC-29)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, Color(0xFF30363D))) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("• Gases/Vapores: Zona 0 (continua) | Zona 1 (probable) | Zona 2 (improbable).", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF39C12))
                Text("• Polvos Combustibles: Zona 20 | Zona 21 | Zona 22.", fontSize = 11.sp, color = Color.White)
                Text("• Instalación reservada exclusivamente a Instalador Especialista (IBTE).", fontSize = 11.sp, color = Color(0xFF2ECC71))
                Text("• Sellado de cortafuegos obligatorio en pasos de canalizaciones.", fontSize = 11.sp, color = Color.LightGray)
            }
        }
    }
}

// 30. ITC-30: Special Characteristic Rooms
@Composable
fun Itc30LocalesEspeciales() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Locales Especiales (ITC-30)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, Color(0xFF30363D))) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("• Locales Húmedos: Grado IPX1 mínimo.", fontSize = 11.sp, color = Color.White)
                Text("• Locales Mojados: Grado IPX4 mínimo y tensión límite 24 V.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF58A6FF))
                Text("• Locales Polvorientos: Grado IP5X (o IP6X si el polvo es conductor).", fontSize = 11.sp, color = Color(0xFFF39C12))
                Text("• Altas temperaturas (> 40°C): Cables de silicona o termoestables.", fontSize = 11.sp, color = Color.LightGray)
            }
        }
    }
}

// 31. ITC-31: Swimming Pools
@Composable
fun Itc31PiscinasVolumenes() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Piscinas y Fuentes (ITC-31)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, Color(0xFF30363D))) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("• Volumen 0 (Interior del vaso): Grado IPX8 y alimentación exclusiva MBTS ≤ 12 V CA.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE74C3C))
                Text("• Volumen 1 (Franja de 2 m y hasta 2,5 m altura): Grado IPX5 sin tomas de enchufe.", fontSize = 11.sp, color = Color(0xFFF39C12))
                Text("• Volumen 2 (Franja de 1,5 m adicional): Tomas con diferencial 30 mA o MBTS.", fontSize = 11.sp, color = Color(0xFF2ECC71))
                Text("• Unión equipotencial de escaleras, barandillas y armaduras metálicas.", fontSize = 10.sp, color = Color.LightGray)
            }
        }
    }
}

// 32. ITC-33: Works & Temporary
@Composable
fun Itc33ObrasSeguridad() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Instalaciones de Obra y Provisionales (ITC-33)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, Color(0xFF30363D))) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("• Cuadros de obra estancos: Grado IP44 e impacto IK08.", fontSize = 11.sp, color = Color.White)
                Text("• Mangueras pesadas de goma resistentes al agua tipo H07RN-F.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF58A6FF))
                Text("• Diferenciales individuales de ≤ 30 mA en todas las bases de enchufe.", fontSize = 11.sp, color = Color(0xFF2ECC71))
                Text("• Requiere proyecto técnico si la potencia instalada supera los 50 kW.", fontSize = 11.sp, color = Color(0xFFF39C12))
            }
        }
    }
}

// 33. ITC-36: MBTS / MBTP
@Composable
fun Itc36MbtsInfographic() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Muy Baja Tensión MBTS y MBTP (ITC-36)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, Color(0xFF30363D))) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("• MBTS: Aislada de tierra mediante transformador de seguridad (EN 61558-2-6).", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2ECC71))
                Text("• MBTP: Con puesta a tierra de protección.", fontSize = 11.sp, color = Color.LightGray)
                Text("• Límites: CA ≤ 50 V eficaces | CC ≤ 75 V sin ondulación.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF58A6FF))
            }
        }
    }
}

// 34. ITC-38: Operating Theatres
@Composable
fun Itc38QuirofanosItMedico() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Quirófanos y Esquema IT Médico (ITC-38)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, Color(0xFF30363D))) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("• Esquema IT Médico: Transformador de aislamiento 0,5 a 10 kVA con VMA.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF58A6FF))
                Text("• Suelo antielectrostático disipativo: Resistencia entre 50 kΩ y 1 MΩ.", fontSize = 11.sp, color = Color.White)
                Text("• Suministro especial complementario: Entrada en < 0,5 segundos (autonomía 2h).", fontSize = 11.sp, color = Color(0xFF2ECC71))
                Text("• Embarrado de equipotencialidad (EE) exclusivo en cada quirófano.", fontSize = 10.sp, color = Color.LightGray)
            }
        }
    }
}

// 35. ITC-40: Generators & PV
@Composable
fun Itc40AutoconsumoGeneradores() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Instalaciones Generadoras y Autoconsumo (ITC-40)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, Color(0xFF30363D))) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("• Enclavamiento mecánico y eléctrico para evitar retorno a red pública.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE74C3C))
                Text("• Relé de protección de desacoplamiento automático por tensión y frecuencia.", fontSize = 11.sp, color = Color(0xFF58A6FF))
                Text("• Conductor neutro dimensionado al 100% por presencia de armónicos.", fontSize = 11.sp, color = Color.LightGray)
            }
        }
    }
}

// 36. ITC-41 / 42: Campings & Marinas
@Composable
fun Itc41CampingsMarinas() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Caravanas, Campings y Puertos (ITC-41/42)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, Color(0xFF30363D))) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("• 1 interruptor diferencial de 30 mA y 1 PIA individual por cada toma.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2ECC71))
                Text("• Bases industriales tipo CETAC azules IP44 (campings) / IP56 (marinas).", fontSize = 11.sp, color = Color(0xFF58A6FF))
                Text("• Altura de tomas: Entre 0,50 m y 1,50 m sobre rasante.", fontSize = 10.sp, color = Color.LightGray)
            }
        }
    }
}

// 37. ITC-46 / 48: Motors & Capacitors
@Composable
fun Itc46MotoresCondensadores() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Receptores: Motores y Baterías de Condensadores (ITC-46/48)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, Color(0xFF30363D))) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("• Cable para motor único: Dimensionar al 125% de In (In · 1,25).", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF58A6FF))
                Text("• Cable para batería de condensadores: Dimensionar al 150% de In (In · 1,50).", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF39C12))
                Text("• Resistencia de descarga rápida en condensadores: Reducir a < 50V en < 1 min.", fontSize = 11.sp, color = Color.LightGray)
            }
        }
    }
}

// 38. ITC-51: Home Automation KNX
@Composable
fun Itc51DomoticaInfographic() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Domótica y Gestión de Energía (ITC-51)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, Color(0xFF30363D))) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("• Separación física entre bus de datos y cables de fuerza de 230V/400V.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF58A6FF))
                Text("• En caso de fallo de alimentación auxiliar, los actuadores adoptan posición segura (fail-safe).", fontSize = 11.sp, color = Color.White)
            }
        }
    }
}

// 39. ITC-52: Electric Vehicle Charging
@Composable
fun Itc52CargaVeInfographic() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Recarga de Vehículos Eléctricos IRVE (ITC-52)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, Color(0xFF30363D))) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("• Diferencial obligatorio: Tipo A 30 mA con detección continua 6 mA (RDC-DD) o Tipo B.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE74C3C))
                Text("• Caída de tensión máxima admisible: 5,0% en régimen continuo.", fontSize = 11.sp, color = Color(0xFF58A6FF))
                Text("• Exclusivo para Instalador Autorizado Especialista (IBTE).", fontSize = 11.sp, color = Color(0xFF2ECC71))
                Text("• Esquemas 1 a 4 según sea contador principal o secundario en garaje.", fontSize = 10.sp, color = Color.LightGray)
            }
        }
    }
}

// 40. Generic Technical Infographic Fallback for any other ITC
@Composable
fun GenericItcTechnicalInfographic(itcId: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Esquema y Parámetros Técnicos REBT: $itcId", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)), border = BorderStroke(1.dp, Color(0xFF30363D))) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("• Prescripciones de montaje, verificación y cálculo conforme al Real Decreto 842/2002.", fontSize = 11.sp, color = Color.White)
                Text("• Conductor de protección (PE) y aislamiento reglamentario.", fontSize = 11.sp, color = Color(0xFF58A6FF))
                Text("• Protección coordinada contra sobreintensidades y contactos indirectos.", fontSize = 11.sp, color = Color(0xFF2ECC71))
            }
        }
    }
}

