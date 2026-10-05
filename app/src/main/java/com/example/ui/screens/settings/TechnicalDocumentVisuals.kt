package com.example.ui.screens.settings

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.OfficialTechnicalNotesCatalog
import com.example.data.TechnicalNoteSection
import kotlin.math.roundToInt

/**
 * TechnicalDocumentVisuals.kt
 * Proporciona infografías técnicas, esquemas interactivos, unifilares y tablas reglamentarias
 * para los documentos oficiales del REBT (Nivel Dios).
 */

@Composable
fun TechnicalDocumentVisualDispatcher(
    docId: String,
    isDark: Boolean
) {
    when (docId) {
        "esquema_cgmp" -> EsquemaUnifilarGodTierView(isDark = isDark)
        "esquema_tierras" -> PuestaATierraGodTierView(isDark = isDark)
        "tabla_itc_21" -> ProntuarioTubosItc21GodTierView(isDark = isDark)
        "apuntes_cgmp_circuitos" -> ApuntesCgmpCircuitosGodTierView(isDark = isDark)
        "apuntes_enlace_lga_di" -> ApuntesEnlaceLgaDiGodTierView(isDark = isDark)
        "apuntes_tierras_protecciones" -> ApuntesTierrasProteccionesGodTierView(isDark = isDark)
        "apuntes_publica_concurrencia" -> PublicaConcurrenciaGodTierView(isDark = isDark)
        "apuntes_recarga_ve" -> RecargaVeGodTierView(isDark = isDark)
        "apuntes_tramitaciones_cie" -> TramitacionesCieGodTierView(isDark = isDark)
        "boe_rebt" -> BoeRebtCompletoGodTierView(isDark = isDark)
        else -> EsquemaUnifilarGodTierView(isDark = isDark)
    }
}

/* ==========================================================================================
 * 1. ESQUEMA UNIFILAR GENERAL DE VIVIENDA (NIVEL DIOS)
 * ========================================================================================== */

data class CircuitSpec(
    val code: String,
    val name: String,
    val piaAmps: Int,
    val sectionMm2: Double,
    val tubeMm: Int,
    val maxPoints: String,
    val maxDropPercent: Double,
    val application: String,
    val examTrap: String,
    val isElevatedOnly: Boolean = false,
    val color: Color
)

@Composable
fun EsquemaUnifilarGodTierView(isDark: Boolean) {
    var isElevated by remember { mutableStateOf(false) }
    var isDesglosadoC4 by remember { mutableStateOf(true) }
    var selectedCircuitCode by remember { mutableStateOf<String?>("C1") }

    val circuitsList = remember(isElevated, isDesglosadoC4) {
        val base = mutableListOf(
            CircuitSpec(
                code = "C1",
                name = "Iluminación",
                piaAmps = 10,
                sectionMm2 = 1.5,
                tubeMm = 16,
                maxPoints = "Hasta 30 puntos de luz",
                maxDropPercent = 3.0,
                application = "Luminarias fijas, interruptores y conmutadores",
                examTrap = "La caída de tensión máxima es del 3% (no el 5%). Recuerda que conductores son Cu 1,5 mm².",
                color = Color(0xFFF59E0B)
            ),
            CircuitSpec(
                code = "C2",
                name = "Tomas Uso General y Frigo",
                piaAmps = 16,
                sectionMm2 = 2.5,
                tubeMm = 20,
                maxPoints = "Hasta 20 tomas 16A 2P+T",
                maxDropPercent = 5.0,
                application = "Enchufes salón, dormitorios, pasillos y frigorífico",
                examTrap = "Máximo 20 tomas por circuito. A partir de 21 tomas se exige un circuito C7 adicional.",
                color = Color(0xFF3B82F6)
            ),
            CircuitSpec(
                code = "C3",
                name = "Cocina y Horno",
                piaAmps = 25,
                sectionMm2 = 6.0,
                tubeMm = 25,
                maxPoints = "2 tomas de 25A (o caja de bornes)",
                maxDropPercent = 5.0,
                application = "Placa vitrocerámica/inducción y horno eléctrico",
                examTrap = "El cable es de 6 mm² y el PIA de 25 A. ¡Nunca 4 mm² ni 16 A!",
                color = Color(0xFFEF4444)
            )
        )

        if (isDesglosadoC4) {
            base.add(
                CircuitSpec(
                    code = "C4.1",
                    name = "Lavadora",
                    piaAmps = 16,
                    sectionMm2 = 2.5,
                    tubeMm = 20,
                    maxPoints = "1 toma dedicada de 16A",
                    maxDropPercent = 5.0,
                    application = "Toma individual exclusiva de lavadora",
                    examTrap = "La opción desglosada requiere PIA 16A y cable 2,5 mm² para cada aparato.",
                    color = Color(0xFF10B981)
                )
            )
            base.add(
                CircuitSpec(
                    code = "C4.2",
                    name = "Lavavajillas",
                    piaAmps = 16,
                    sectionMm2 = 2.5,
                    tubeMm = 20,
                    maxPoints = "1 toma dedicada de 16A",
                    maxDropPercent = 5.0,
                    application = "Toma individual exclusiva de lavavajillas",
                    examTrap = "Al desglosar C4 en tres, se computan como 3 circuitos independientes en el diferencial.",
                    color = Color(0xFF14B8A6)
                )
            )
            base.add(
                CircuitSpec(
                    code = "C4.3",
                    name = "Termo Eléctrico",
                    piaAmps = 16,
                    sectionMm2 = 2.5,
                    tubeMm = 20,
                    maxPoints = "1 toma dedicada de 16A",
                    maxDropPercent = 5.0,
                    application = "Toma individual exclusiva de termo o calentador",
                    examTrap = "Si la vivienda usa caldera de gas, C4.3 puede no instalarse o reservarse.",
                    color = Color(0xFF06B6D4)
                )
            )
        } else {
            base.add(
                CircuitSpec(
                    code = "C4",
                    name = "Lavadora, Lavavajillas y Termo",
                    piaAmps = 20,
                    sectionMm2 = 4.0,
                    tubeMm = 20,
                    maxPoints = "3 tomas de 16A agrupadas",
                    maxDropPercent = 5.0,
                    application = "Lavadora + Lavavajillas + Termo en línea común",
                    examTrap = "Aunque las tomas son de 16A, la línea es de 4 mm² y el PIA de 20A.",
                    color = Color(0xFF10B981)
                )
            )
        }

        base.add(
            CircuitSpec(
                code = "C5",
                name = "Baños y Cocina Aux.",
                piaAmps = 16,
                sectionMm2 = 2.5,
                tubeMm = 20,
                maxPoints = "Hasta 6 tomas 16A",
                maxDropPercent = 5.0,
                application = "Tomas de encimera cocina y tomas en zonas seguras de baño",
                examTrap = "Límite estricto de 6 tomas. En baños deben estar fuera de los volúmenes 0, 1 y 2.",
                color = Color(0xFF8B5CF6)
            )
        )

        if (isElevated) {
            base.addAll(
                listOf(
                    CircuitSpec(
                        code = "C6",
                        name = "Ilum. Adicional",
                        piaAmps = 10,
                        sectionMm2 = 1.5,
                        tubeMm = 16,
                        maxPoints = "Hasta 30 puntos adicionales",
                        maxDropPercent = 3.0,
                        application = "Por cada 30 puntos extra de alumbrado",
                        examTrap = "Obligatorio a partir de 31 puntos de luz en la vivienda.",
                        isElevatedOnly = true,
                        color = Color(0xFFF59E0B)
                    ),
                    CircuitSpec(
                        code = "C7",
                        name = "Tomas Adicionales",
                        piaAmps = 16,
                        sectionMm2 = 2.5,
                        tubeMm = 20,
                        maxPoints = "Hasta 20 tomas adicionales",
                        maxDropPercent = 5.0,
                        application = "Por cada 20 tomas extra de uso general",
                        examTrap = "Obligatorio a partir de 21 tomas de uso general.",
                        isElevatedOnly = true,
                        color = Color(0xFF3B82F6)
                    ),
                    CircuitSpec(
                        code = "C8",
                        name = "Calefacción Eléctrica",
                        piaAmps = 25,
                        sectionMm2 = 6.0,
                        tubeMm = 25,
                        maxPoints = "Emisores térmicos",
                        maxDropPercent = 5.0,
                        application = "Radiadores de acumulación o convectores",
                        examTrap = "Su presencia obliga automáticamente al paso a electrificación elevada.",
                        isElevatedOnly = true,
                        color = Color(0xFFEC4899)
                    ),
                    CircuitSpec(
                        code = "C9",
                        name = "Aire Acondicionado",
                        piaAmps = 25,
                        sectionMm2 = 6.0,
                        tubeMm = 25,
                        maxPoints = "Bomba de calor / Clima",
                        maxDropPercent = 5.0,
                        application = "Unidad interior y exterior de climatización",
                        examTrap = "Copia sección de C3 y C8: 6 mm² y PIA de 25 A.",
                        isElevatedOnly = true,
                        color = Color(0xFF0284C7)
                    ),
                    CircuitSpec(
                        code = "C10",
                        name = "Secadora",
                        piaAmps = 16,
                        sectionMm2 = 2.5,
                        tubeMm = 20,
                        maxPoints = "1 toma dedicada",
                        maxDropPercent = 5.0,
                        application = "Toma individual para secadora independiente",
                        examTrap = "Si la vivienda tiene secadora independiente, se desglosa en C10 con 16A y 2,5 mm².",
                        isElevatedOnly = true,
                        color = Color(0xFF6366F1)
                    ),
                    CircuitSpec(
                        code = "C11",
                        name = "Domótica / Control",
                        piaAmps = 10,
                        sectionMm2 = 1.5,
                        tubeMm = 16,
                        maxPoints = "Nodos de automatización",
                        maxDropPercent = 3.0,
                        application = "Autómatas, pasarelas KNX, videoportero y seguridad",
                        examTrap = "Alimentación de sistemas de seguridad y telegestión con PIA de 10 A.",
                        isElevatedOnly = true,
                        color = Color(0xFF64748B)
                    ),
                    CircuitSpec(
                        code = "C13",
                        name = "Recarga Vehículo Eléctrico",
                        piaAmps = 32,
                        sectionMm2 = 6.0,
                        tubeMm = 25,
                        maxPoints = "1 Wallbox / Estación VE",
                        maxDropPercent = 5.0,
                        application = "Punto de recarga Modo 3 (ITC-BT-52)",
                        examTrap = "Exige interruptor diferencial propio Tipo A con detección 6 mA DC (o Tipo B).",
                        isElevatedOnly = true,
                        color = Color(0xFF10B981)
                    )
                )
            )
        }
        base
    }

    val selectedCircuit = circuitsList.firstOrNull { it.code == selectedCircuitCode } ?: circuitsList.first()

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Mode Controls Header
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isDark) Color(0xFF161B22) else Color(0xFFF1F5F9)
            ),
            border = BorderStroke(1.dp, if (isDark) Color(0xFF30363D) else Color(0xFFCBD5E1))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (isElevated) "Electrificación Elevada (9.200 W - 14.490 W)" else "Electrificación Básica (5.750 W)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isElevated) Color(0xFFE67E22) else Color(0xFF3FB950)
                        )
                        Text(
                            text = if (isElevated) "IGA 40A/50A/63A • ≥ 2 Diferenciales • Sup > 160 m² o Clima/Calef/VE" else "IGA 25A • 1 Diferencial • Máx 5 circuitos por ID • Sup ≤ 160 m²",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Switch(
                        checked = isElevated,
                        onCheckedChange = { isElevated = it }
                    )
                }

                HorizontalDivider(color = if (isDark) Color(0xFF30363D) else Color(0xFFE2E8F0))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Desglose oficial de C4 (Lavadora, Lavavajillas, Termo):",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    FilterChip(
                        selected = isDesglosadoC4,
                        onClick = { isDesglosadoC4 = !isDesglosadoC4 },
                        label = { Text(if (isDesglosadoC4) "3 x 16A (Desglosado)" else "1 x 20A (Clásico)", fontSize = 11.sp) }
                    )
                }
            }
        }

        // ESQUEMA UNIFILAR INTERACTIVO (CANVAS DE COMPONENTES)
        Text(
            text = "⚡ DIAGRAMA UNIFILAR COMPLETO EN CASCADA (REBT)",
            fontSize = 13.sp,
            fontWeight = FontWeight.ExtraBold,
            color = if (isDark) Color(0xFF58A6FF) else Color(0xFF0969DA),
            letterSpacing = 0.5.sp
        )

        // 1. DI / Cabecera Card
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF1B2332) else Color(0xFFEBF3FF)),
            border = BorderStroke(1.dp, Color(0xFF3B82F6).copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Bolt, contentDescription = null, tint = Color(0xFF3B82F6), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("1. DERIVACIÓN INDIVIDUAL (DI)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3B82F6))
                    Spacer(modifier = Modifier.weight(1f))
                    Surface(shape = RoundedCornerShape(6.dp), color = Color(0xFF3B82F6).copy(alpha = 0.2f)) {
                        Text("ΔV ≤ 1,5% (3,45 V)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3B82F6), modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (isElevated) "2x16 mm² Cu + PE 16 mm² + Mando Rojo 1,5 mm² en Tubo Ø 32 mm (Libre de halógenos RZ1-K 0,6/1 kV)" else "2x10 mm² Cu + PE 10 mm² + Mando Rojo 1,5 mm² en Tubo Ø 32 mm (Libre de halógenos RZ1-K 0,6/1 kV)",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Línea vertical de unión
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Box(modifier = Modifier.width(2.dp).height(12.dp).background(Color(0xFF58A6FF)))
        }

        // 2. IGA + Sobretensiones
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF231B15) else Color(0xFFFFF7ED)),
                border = BorderStroke(1.dp, Color(0xFFF97316).copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text("2. IGA OMNIPOLAR", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF97316))
                    Text(
                        text = if (isElevated) "40 A (o 50A/63A)" else "25 A",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text("Curva C • PdC ≥ 4.500 A • 2P (F+N)", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Card(
                modifier = Modifier.weight(1.2f),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF1E1724) else Color(0xFFFAF5FF)),
                border = BorderStroke(1.dp, Color(0xFFA855F7).copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text("3. SOBRETENSIONES", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFA855F7))
                    Text("POP + Transitorias", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Text("POP: Disparo > 255 V | Tipo 2 In=15kA a PE", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        // Línea vertical de unión
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Box(modifier = Modifier.width(2.dp).height(12.dp).background(Color(0xFF58A6FF)))
        }

        // 3. Diferencial 1 (ID1)
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF16231B) else Color(0xFFF0FDF4)),
            border = BorderStroke(1.dp, Color(0xFF22C55E).copy(alpha = 0.6f))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(28.dp).clip(CircleShape).background(Color(0xFF22C55E).copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("ID1", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF22C55E))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text("INTERRUPTOR DIFERENCIAL 1 (ID 1)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text("40 A / 30 mA • Tipo A/AC • t < 200 ms • Protege C1 a C5", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                Surface(shape = RoundedCornerShape(6.dp), color = Color(0xFF22C55E).copy(alpha = 0.2f)) {
                    Text("IΔn = 30 mA", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF22C55E), modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                }
            }
        }

        // Circuitos de ID1 en Horizontal Scroll / Chips
        Text("Circuitos derivados ID1 (Toca para inspeccionar):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            circuitsList.filter { !it.isElevatedOnly }.forEach { circuit ->
                val isSelected = selectedCircuitCode == circuit.code
                OutlinedCard(
                    onClick = { selectedCircuitCode = circuit.code },
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = if (isSelected) circuit.color.copy(alpha = 0.15f) else Color.Transparent
                    ),
                    border = BorderStroke(if (isSelected) 2.dp else 1.dp, if (isSelected) circuit.color else Color.Gray.copy(alpha = 0.3f)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(circuit.code, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = circuit.color)
                        Text("${circuit.piaAmps}A", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text("${circuit.sectionMm2} mm²", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }

        // Si es Electrificación Elevada, mostramos ID2 y sus circuitos
        if (isElevated) {
            Spacer(modifier = Modifier.height(4.dp))
            // Línea de unión
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Box(modifier = Modifier.width(2.dp).height(12.dp).background(Color(0xFFE67E22)))
            }

            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF2A1C16) else Color(0xFFFFF7ED)),
                border = BorderStroke(1.dp, Color(0xFFE67E22).copy(alpha = 0.6f))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(28.dp).clip(CircleShape).background(Color(0xFFE67E22).copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("ID2", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE67E22))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("INTERRUPTOR DIFERENCIAL 2 (ID 2)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text("40 A / 30 mA • Tipo A • Protege C6 a C13 (Regla máx 5 circuitos)", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                    Surface(shape = RoundedCornerShape(6.dp), color = Color(0xFFE67E22).copy(alpha = 0.2f)) {
                        Text("IΔn = 30 mA", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE67E22), modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                    }
                }
            }

            Text("Circuitos derivados ID2 (Electrificación Elevada):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                circuitsList.filter { it.isElevatedOnly }.forEach { circuit ->
                    val isSelected = selectedCircuitCode == circuit.code
                    OutlinedCard(
                        onClick = { selectedCircuitCode = circuit.code },
                        colors = CardDefaults.outlinedCardColors(
                            containerColor = if (isSelected) circuit.color.copy(alpha = 0.15f) else Color.Transparent
                        ),
                        border = BorderStroke(if (isSelected) 2.dp else 1.dp, if (isSelected) circuit.color else Color.Gray.copy(alpha = 0.3f)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(circuit.code, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = circuit.color)
                            Text("${circuit.piaAmps}A", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text("${circuit.sectionMm2} mm²", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }

        // Barra PE de Tierra
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF152217) else Color(0xFFE8F5E9)),
            border = BorderStroke(1.dp, Color(0xFF2E7D32).copy(alpha = 0.5f))
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(Color(0xFF4CAF50)))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "BARRA COLECTORA PE (TIERRA): Cobre verde-amarillo conectado a bornes de protección de todos los circuitos y a la arqueta exterior.",
                    fontSize = 10.sp,
                    color = if (isDark) Color(0xFF81C784) else Color(0xFF2E7D32)
                )
            }
        }

        // INSPECTOR TÉCNICO DETALLADO DEL CIRCUITO SELECCIONADO
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isDark) Color(0xFF131922) else Color(0xFFF8FAFC)
            ),
            border = BorderStroke(1.5.dp, selectedCircuit.color)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(shape = RoundedCornerShape(8.dp), color = selectedCircuit.color.copy(alpha = 0.2f)) {
                            Text(
                                text = selectedCircuit.code,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = selectedCircuit.color,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(selectedCircuit.name, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            Text(selectedCircuit.application, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }

                HorizontalDivider(color = if (isDark) Color(0xFF21262D) else Color(0xFFE2E8F0))

                // Fila de parámetros reglamentarios
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    ParameterBadge("PIA (Calibre)", "${selectedCircuit.piaAmps} A (Curva C)")
                    ParameterBadge("Sección Cu", "${selectedCircuit.sectionMm2} mm²")
                    ParameterBadge("Tubo Mínimo", "Ø ${selectedCircuit.tubeMm} mm")
                    ParameterBadge("Caída Máx", "${selectedCircuit.maxDropPercent}%")
                }

                // Potencia máxima admisible
                val maxWatts = (selectedCircuit.piaAmps * 230).toDouble()
                Text(
                    text = "Potencia nominal de corte: ${maxWatts.toInt()} W a 230 V • Límite reglamentario: ${selectedCircuit.maxPoints}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )

                // Trampa de examen
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isDark) Color(0xFF2D1E16) else Color(0xFFFFF3E0),
                    border = BorderStroke(1.dp, Color(0xFFF97316).copy(alpha = 0.5f))
                ) {
                    Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.Top) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFF97316), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("Clave de Examen Oficial REBT:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF97316))
                            Text(selectedCircuit.examTrap, fontSize = 11.sp, lineHeight = 15.sp, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
        }

        // TABLA MAESTRA COMPARATIVA C1 A C13
        Text(
            text = "📋 TABLA TÉCNICA MAESTRA: CIRCUITOS C1 A C13 (ITC-BT-25)",
            fontSize = 13.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF161B22) else Color.White),
            border = BorderStroke(1.dp, if (isDark) Color(0xFF30363D) else Color(0xFFE2E8F0))
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                // Table Header
                Row(
                    modifier = Modifier.fillMaxWidth().background(if (isDark) Color(0xFF21262D) else Color(0xFFF1F5F9), RoundedCornerShape(6.dp)).padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Cto.", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.8f))
                    Text("Uso", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(2f))
                    Text("PIA", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.9f))
                    Text("Cable", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text("Tubo", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.9f))
                    Text("ΔV", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.7f))
                }

                circuitsList.forEach { c ->
                    HorizontalDivider(color = if (isDark) Color(0xFF21262D) else Color(0xFFF1F5F9))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedCircuitCode = c.code }
                            .padding(vertical = 6.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(c.code, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = c.color, modifier = Modifier.weight(0.8f))
                        Text(c.name, fontSize = 11.sp, maxLines = 1, modifier = Modifier.weight(2f))
                        Text("${c.piaAmps}A", fontSize = 11.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(0.9f))
                        Text("${c.sectionMm2}mm²", fontSize = 11.sp, modifier = Modifier.weight(1f))
                        Text("Ø${c.tubeMm}", fontSize = 11.sp, modifier = Modifier.weight(0.9f))
                        Text("${c.maxDropPercent}%", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary, modifier = Modifier.weight(0.7f))
                    }
                }
            }
        }
    }
}

@Composable
private fun ParameterBadge(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
    }
}

/* ==========================================================================================
 * 2. DETALLE CONSTRUCTIVO PUESTA A TIERRA (NIVEL DIOS)
 * ========================================================================================== */

@Composable
fun PuestaATierraGodTierView(isDark: Boolean) {
    var selectedScheme by remember { mutableStateOf("TT") }
    var soilResistivity by remember { mutableFloatStateOf(100f) } // Ohm · m

    val rPica = soilResistivity / 2.0 // R = rho / L (pica 2m)
    val rZanja = (2.0 * soilResistivity) / 15.0 // R = 2 rho / L (anillo 15m)

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Esquema gráfico de la Arqueta
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isDark) Color(0xFF131A15) else Color(0xFFF0FDF4)
            ),
            border = BorderStroke(1.dp, Color(0xFF22C55E).copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Engineering, contentDescription = null, tint = Color(0xFF22C55E))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("CORTE TÉCNICO: ARQUETA DE REGISTRO SECCIONABLE", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF22C55E))
                }

                // Diagrama estilizado
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF1C271E) else Color.White),
                    border = BorderStroke(1.dp, Color(0xFF22C55E).copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("1. Tapa de fundición / composite:", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Text("Rótulo oficial «PUESTA A TIERRA»", fontSize = 11.sp, color = Color(0xFF22C55E), fontWeight = FontWeight.Bold)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("2. Puente de comprobación:", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Text("Pletina de cobre seccionable con tuercas", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("3. Línea de enlace con tierra:", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Text("Cu desnudo ≥ 35 mm² o aislado ≥ 16 mm²", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("4. Electrodo vertical (Pica):", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Text("Acero-cobre ≥ 2,00 m (recubrimiento 250 µm)", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("5. Anillo en cimentación:", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Text("Cu desnudo 35 mm² a prof. ≥ 0,80 m", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }

        // Selector de Esquemas de Distribución (TT, TN-S, TN-C, IT)
        Text("⚡ COMPARATIVA OFICIAL DE ESQUEMAS DE DISTRIBUCIÓN (ITC-BT-08)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("TT", "TN-S", "TN-C", "IT").forEach { scheme ->
                val isSelected = selectedScheme == scheme
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedScheme = scheme },
                    label = { Text("Esquema $scheme", fontSize = 12.sp) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Ficha del esquema seleccionado
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF161B22) else Color.White),
            border = BorderStroke(1.dp, if (isDark) Color(0xFF30363D) else Color(0xFFE2E8F0))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                when (selectedScheme) {
                    "TT" -> {
                        Text("Esquema TT: Obligatorio en Redes Públicas Españolas", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3FB950))
                        Text("• Neutro de la compañía puesto a tierra directamente en el Centro de Transformación.", fontSize = 11.sp)
                        Text("• Masas de la instalación conectadas a tierra INDEPENDIENTE de la del neutro.", fontSize = 11.sp)
                        Text("• Condición de corte obligatoria: Ra · IΔn ≤ UL (50 V en seco, 24 V en húmedo).", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text("• Con diferencial de 30 mA: Ra ≤ 50 / 0,030 = 1.666 Ω. Recomendación técnica: Ra < 15 Ω.", fontSize = 11.sp)
                    }
                    "TN-S" -> {
                        Text("Esquema TN-S: Neutro y Protección Separados", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF58A6FF))
                        Text("• Masas conectadas directamente al neutro de la fuente puesto a tierra.", fontSize = 11.sp)
                        Text("• El conductor Neutro (N) y el de Protección (PE) discurren SEPARADOS en toda la red.", fontSize = 11.sp)
                        Text("• Un defecto a masa es un cortocircuito fase-neutro franco. Dispara el magnetotérmico.", fontSize = 11.sp)
                    }
                    "TN-C" -> {
                        Text("Esquema TN-C: Neutro y Protección Comunes (PEN)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE67E22))
                        Text("• Las funciones de neutro y conductor de protección se combinan en un solo conductor (PEN).", fontSize = 11.sp)
                        Text("• Sección mínima exigida: ≥ 10 mm² Cu o ≥ 16 mm² Al. Prohibido en instalaciones interiores de viviendas.", fontSize = 11.sp)
                        Text("• ¡PROHIBIDO pasar de TN-S a TN-C aguas abajo! Una vez separados, jamás deben volver a unirse.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF85149))
                    }
                    "IT" -> {
                        Text("Esquema IT: Neutro Aislado (Quirófanos e Industria Crítica)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFFA371F7))
                        Text("• Neutro totalmente aislado de tierra o puesto a través de impedancia elevada (1.000 Ω).", fontSize = 11.sp)
                        Text("• Masas conectadas a tierra. Un primer defecto fase-masa NO corta el suministro.", fontSize = 11.sp)
                        Text("• Exige obligatoriamente Vigilante de Aislamiento permanente con alarma acústica/visual.", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Calculadora Interactiva de Resistencia según Terreno
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF1E1715) else Color(0xFFFFF8F2)),
            border = BorderStroke(1.dp, Color(0xFFE67E22).copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Calculadora Interactiva de Resistencia de Toma de Tierra (ITC-BT-18)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE67E22))
                Text("Resistividad del terreno (ρ): ${soilResistivity.toInt()} Ω·m", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                Slider(
                    value = soilResistivity,
                    onValueChange = { soilResistivity = it },
                    valueRange = 10f..500f
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("Pica 2m (R = ρ/L):", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("${"%.1f".format(rPica)} Ω", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF3FB950))
                    }
                    Column {
                        Text("Anillo 15m (R = 2ρ/L):", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("${"%.1f".format(rZanja)} Ω", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF58A6FF))
                    }
                    Column {
                        Text("Límite Seco (30mA):", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("1.666 Ω", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                    }
                }
            }
        }
    }
}

/* ==========================================================================================
 * 3. PRONTUARIO DIÁMETROS DE TUBOS ITC-BT-21 (NIVEL DIOS)
 * ========================================================================================== */

@Composable
fun ProntuarioTubosItc21GodTierView(isDark: Boolean) {
    var installationType by remember { mutableStateOf("Empotrado") }
    var selectedSection by remember { mutableDoubleStateOf(2.5) }
    var numConductors by remember { mutableIntStateOf(3) }

    val sections = listOf(1.5, 2.5, 4.0, 6.0, 10.0, 16.0, 25.0, 35.0, 50.0)

    // Lookup en tablas oficiales ITC-BT-21
    val tubeDiameter = remember(installationType, selectedSection, numConductors) {
        when (installationType) {
            "Empotrado" -> {
                when (selectedSection) {
                    1.5 -> 16
                    2.5 -> if (numConductors <= 3) 16 else 20
                    4.0 -> if (numConductors <= 2) 16 else 20
                    6.0 -> if (numConductors <= 1) 16 else if (numConductors <= 3) 20 else 25
                    10.0 -> if (numConductors <= 1) 20 else if (numConductors <= 3) 25 else 32
                    16.0 -> if (numConductors <= 1) 20 else if (numConductors <= 2) 25 else if (numConductors <= 4) 32 else 40
                    25.0 -> if (numConductors <= 1) 25 else if (numConductors <= 3) 32 else if (numConductors == 4) 40 else 50
                    35.0 -> if (numConductors <= 1) 25 else if (numConductors <= 2) 32 else if (numConductors == 3) 40 else 50
                    else -> if (numConductors <= 1) 32 else if (numConductors <= 2) 40 else if (numConductors <= 4) 50 else 63
                }
            }
            "Superficial" -> {
                when (selectedSection) {
                    1.5 -> 16
                    2.5 -> if (numConductors <= 3) 16 else 20
                    4.0 -> if (numConductors <= 2) 16 else 20
                    6.0 -> if (numConductors <= 1) 16 else if (numConductors <= 3) 20 else 25
                    10.0 -> if (numConductors <= 2) 20 else if (numConductors == 3) 25 else 32
                    16.0 -> if (numConductors <= 1) 20 else if (numConductors <= 2) 25 else 32
                    25.0 -> if (numConductors <= 1) 25 else if (numConductors <= 3) 32 else 40
                    35.0 -> if (numConductors <= 1) 25 else if (numConductors <= 2) 32 else if (numConductors <= 4) 40 else 50
                    else -> if (numConductors <= 1) 32 else if (numConductors <= 2) 40 else if (numConductors <= 4) 50 else 63
                }
            }
            else -> { // Enterrado
                if (selectedSection <= 16.0) 40 else if (selectedSection <= 50.0) 50 else 63
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Controls
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF161B22) else Color(0xFFF8FAFC)),
            border = BorderStroke(1.dp, if (isDark) Color(0xFF30363D) else Color(0xFFCBD5E1))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("1. Tipo de canalización reglamentaria:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Empotrado", "Superficial", "Enterrado").forEach { type ->
                        FilterChip(
                            selected = installationType == type,
                            onClick = { installationType = type },
                            label = { Text(type, fontSize = 12.sp) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                HorizontalDivider(color = if (isDark) Color(0xFF30363D) else Color(0xFFE2E8F0))

                Text("2. Número de conductores en el tubo:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    (1..5).forEach { n ->
                        FilterChip(
                            selected = numConductors == n,
                            onClick = { numConductors = n },
                            label = { Text("$n hilos", fontSize = 11.sp) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                HorizontalDivider(color = if (isDark) Color(0xFF30363D) else Color(0xFFE2E8F0))

                Text("3. Sección de los conductores (mm²):", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    sections.forEach { s ->
                        FilterChip(
                            selected = selectedSection == s,
                            onClick = { selectedSection = s },
                            label = { Text("${if (s % 1 == 0.0) s.toInt() else s} mm²", fontSize = 11.sp) }
                        )
                    }
                }
            }
        }

        // Visual Tube Gauge Result
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF13202E) else Color(0xFFEBF5FF)),
            border = BorderStroke(2.dp, Color(0xFF58A6FF))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("DIÁMETRO EXTERIOR MÍNIMO:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF58A6FF))
                    Text("Ø $tubeDiameter mm", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onSurface)
                    Text(
                        text = "Tubo corrugado normalizado para $numConductors conductores de ${if (selectedSection % 1 == 0.0) selectedSection.toInt() else selectedSection} mm²",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Box(
                    modifier = Modifier.size(60.dp).clip(CircleShape).background(Color(0xFF58A6FF).copy(alpha = 0.2f)).border(3.dp, Color(0xFF58A6FF), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Ø $tubeDiameter", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF58A6FF))
                }
            }
        }

        // Regla reglamentaria 3x / 4x
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = if (isDark) Color(0xFF161B22) else Color(0xFFF1F5F9),
            border = BorderStroke(1.dp, if (isDark) Color(0xFF30363D) else Color(0xFFCBD5E1))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Regla de Ocupación Interior REBT (ITC-BT-21):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text(
                    text = "La sección interior del tubo protector debe ser como mínimo igual a 3 VECES la suma de las secciones totales de los conductores (o 4 VECES en canalizaciones enterradas) para garantizar ventilación y paso sin rozamientos.",
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/* ==========================================================================================
 * 4. APUNTES CUADRO CGMP Y CIRCUITOS C1 A C13
 * ========================================================================================== */

@Composable
fun ApuntesCgmpCircuitosGodTierView(isDark: Boolean) {
    EsquemaUnifilarGodTierView(isDark = isDark)
}

/* ==========================================================================================
 * 5. APUNTES ENLACE, LGA Y DERIVACIONES
 * ========================================================================================== */

@Composable
fun ApuntesEnlaceLgaDiGodTierView(isDark: Boolean) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Enlace Pipeline visual
        Text("⚡ CADENA COMPLETA DE LAS INSTALACIONES DE ENLACE (ITC-BT-11 A 16)", fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF58A6FF))

        val stages = listOf(
            Triple("1. RED DE DISTRIBUCIÓN", "Acometida aérea o subterránea propiedad de la compañía distribuidora.", Color(0xFF6B7280)),
            Triple("2. CAJA GENERAL (CGP / CPM)", "Límite de propiedad. Aloja fusibles BUC NH00 gG de corte general.", Color(0xFFEF4444)),
            Triple("3. LÍNEA GENERAL (LGA)", "Une CGP con contadores. ΔV ≤ 0,5% (concentrados) o 1,0% (parciales). RZ1-K Cu ≥ 10mm².", Color(0xFFF59E0B)),
            Triple("4. CENTRALIZACIÓN CONTADORES", "Armario (≤ 16) o Local exclusivo (> 16). IGM general de corte ≥ 160 A.", Color(0xFF10B981)),
            Triple("5. DERIVACIÓN INDIVIDUAL (DI)", "Enlaza contador con vivienda. Tubo Ø 32mm. Cu ≥ 6mm². ΔV ≤ 1,5%.", Color(0xFF3B82F6)),
            Triple("6. CUADRO CGMP VIVIENDA", "IGA omnipolar + Sobretensiones + Diferencial 30mA + PIAs C1 a C13.", Color(0xFF8B5CF6))
        )

        stages.forEach { (title, desc, color) ->
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF161B22) else Color.White),
                border = BorderStroke(1.dp, color.copy(alpha = 0.5f))
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(color))
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = color)
                        Text(desc, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }

        // Tabla de caídas de tensión
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF1C2128) else Color(0xFFF1F5F9))
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Límites Reglamentarios de Caída de Tensión (ITC-BT-14, 15, 19):", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text("• LGA con contadores concentrados en un solo punto: 0,5 %", fontSize = 11.sp)
                Text("• LGA con contadores centralizados por plantas / parciales: 1,0 %", fontSize = 11.sp)
                Text("• DI con contadores concentrados: 1,5 %", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3FB950))
                Text("• DI con centralizaciones parciales: 0,5 %", fontSize = 11.sp)
                Text("• DI para suministro único (sin LGA previa): 1,5 %", fontSize = 11.sp)
                Text("• Circuitos interiores de alumbrado: 3,0 %", fontSize = 11.sp)
                Text("• Circuitos interiores de fuerza / otros usos: 5,0 %", fontSize = 11.sp)
            }
        }
    }
}

/* ==========================================================================================
 * 6. APUNTES PUESTA A TIERRA Y PROTECCIONES
 * ========================================================================================== */

@Composable
fun ApuntesTierrasProteccionesGodTierView(isDark: Boolean) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        PuestaATierraGodTierView(isDark = isDark)

        Text("⚡ CURVAS DE DISPARO MAGNETOTÉRMICO (UNE-EN 60898)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CurveCard("Curva B", "3 a 5 In", "Líneas muy largas, generadores y semiconductores.", Color(0xFF3B82F6), isDark)
            CurveCard("Curva C", "5 a 10 In", "Uso general en viviendas, alumbrado y tomas comunes.", Color(0xFF10B981), isDark)
            CurveCard("Curva D", "10 a 20 In", "Motores de gran inercia, transformadores y soldaduras.", Color(0xFFF59E0B), isDark)
        }
    }
}

@Composable
private fun RowScope.CurveCard(name: String, range: String, desc: String, color: Color, isDark: Boolean) {
    Card(
        modifier = Modifier.weight(1f),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF161B22) else Color.White),
        border = BorderStroke(1.dp, color.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(name, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = color)
            Text(range, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Text(desc, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

/* ==========================================================================================
 * 7. PÚBLICA CONCURRENCIA Y ALUMBRADO DE EMERGENCIA
 * ========================================================================================== */

@Composable
fun PublicaConcurrenciaGodTierView(isDark: Boolean) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text("🏛️ LOCALES DE PÚBLICA CONCURRENCIA (ITC-BT-28)", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFFE67E22))

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF161B22) else Color.White),
            border = BorderStroke(1.dp, if (isDark) Color(0xFF30363D) else Color(0xFFCBD5E1))
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Umbrales de Clasificación Obligatoria:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text("• Espectáculos y recreo: Cines, teatros, estadios, discotecas (CUALQUIER AFORO).", fontSize = 11.sp)
                Text("• Bares, restaurantes, hoteles, templos, escuelas: Aforo > 50 personas.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE67E22))
                Text("• Centros comerciales, estaciones de viajeros: Aforo > 50 personas.", fontSize = 11.sp)
                Text("• Cualquier otro local no clasificado: Aforo > 100 personas.", fontSize = 11.sp)
                Text("• Sanitarios: Hospitales, ambulatorios, clínicas (CUALQUIER CAPACIDAD).", fontSize = 11.sp)
            }
        }

        Text("💡 EXIGENCIAS DE ALUMBRADO DE EMERGENCIA", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Card(modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp), colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF1C271E) else Color(0xFFF0FDF4))) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text("Evacuación", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF10B981))
                    Text("≥ 1 lux", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                    Text("En eje central a nivel del suelo • Relación máx/mín ≤ 40:1 • Autonomía ≥ 1 h", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Card(modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp), colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF261D15) else Color(0xFFFFF7ED))) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text("Puntos Socorro", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF59E0B))
                    Text("≥ 5 lux", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                    Text("En botiquines y extintores • Plano de trabajo", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Card(modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp), colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF2B1717) else Color(0xFFFEF2F2))) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text("Alto Riesgo", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFEF4444))
                    Text("≥ 15 lux", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                    Text("O el 10% del alumbrado normal • Salas de máquinas", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

/* ==========================================================================================
 * 8. RECARGA VEHÍCULO ELÉCTRICO (ITC-BT-52)
 * ========================================================================================== */

@Composable
fun RecargaVeGodTierView(isDark: Boolean) {
    var selectedScheme by remember { mutableStateOf("Esquema 2") }

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text("🚗 INFRAESTRUCTURA DE RECARGA DE VEHÍCULOS ELÉCTRICOS (ITC-BT-52)", fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF10B981))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("Esquema 1", "Esquema 2", "Esquema 3", "Esquema 4").forEach { sch ->
                FilterChip(
                    selected = selectedScheme == sch,
                    onClick = { selectedScheme = sch },
                    label = { Text(sch, fontSize = 11.sp) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF161B22) else Color.White),
            border = BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                when (selectedScheme) {
                    "Esquema 1" -> {
                        Text("Esquema 1: Colectivo con Contador Principal Común", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF10B981))
                        Text("• Un único contador principal para todos los puntos de recarga.", fontSize = 11.sp)
                        Text("• 1a: Con contadores secundarios de medida por cada plaza.", fontSize = 11.sp)
                        Text("• 1b: Sin contadores secundarios (tarifa plana comunitaria).", fontSize = 11.sp)
                        Text("• 1c: Con contadores individuales integrados en las estaciones inteligentes.", fontSize = 11.sp)
                    }
                    "Esquema 2" -> {
                        Text("Esquema 2: Individual con Contador Exclusivo Centralizado", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF10B981))
                        Text("• Cada usuario contrata su propio suministro exclusivo para el coche.", fontSize = 11.sp)
                        Text("• El contador se ubica en el local centralizado de contadores del edificio.", fontSize = 11.sp)
                        Text("• Se alimenta directamente de la LGA mediante una derivación propia.", fontSize = 11.sp)
                    }
                    "Esquema 3" -> {
                        Text("Esquema 3: Vinculado al Contador de la Vivienda (El más común)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF10B981))
                        Text("• La recarga se factura conjuntamente con el recibo de la vivienda.", fontSize = 11.sp)
                        Text("• 3a: La línea arranca desde el propio cuadro CGMP interior de la vivienda.", fontSize = 11.sp)
                        Text("• 3b: La línea arranca en los bornes de salida del contador de la vivienda en la concentración.", fontSize = 11.sp)
                    }
                    "Esquema 4" -> {
                        Text("Esquema 4: Vivienda Unifamiliar", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF10B981))
                        Text("• Circuito adicional exclusivo C13 que arranca del cuadro CGMP de la vivienda.", fontSize = 11.sp)
                        Text("• PIA de 32 A • Cable 6 mm² • Tubo Ø 25 mm • Diferencial propio Tipo A con RDC-DD.", fontSize = 11.sp)
                    }
                }
            }
        }

        // Modos de carga
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF1F242C) else Color(0xFFF1F5F9))
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Modos de Carga Reglamentarios:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text("• Modo 1: Enchufe doméstico directo sin comunicación (PROHIBIDO recarga habitual).", fontSize = 11.sp, color = Color(0xFFF85149))
                Text("• Modo 2: Cable con control ICCB en base Schuko (carga lenta ocasional máx 10A/13A).", fontSize = 11.sp)
                Text("• Modo 3: Wallbox fijo en corriente alterna con toma Mennekes Tipo 2 y piloto PWM.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3FB950))
                Text("• Modo 4: Carga rápida en corriente continua (50 kW a 350 kW) con conector CCS Combo.", fontSize = 11.sp)
            }
        }
    }
}

/* ==========================================================================================
 * 9. TRAMITACIONES, CIE Y MTD VS PROYECTO
 * ========================================================================================== */

@Composable
fun TramitacionesCieGodTierView(isDark: Boolean) {
    var hasProjectQuestion1 by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text("📑 TRAMITACIONES REBT: PROYECTO vs MEMORIA TÉCNICA (MTD)", fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF58A6FF))

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF161B22) else Color.White),
            border = BorderStroke(1.dp, if (isDark) Color(0xFF30363D) else Color(0xFFCBD5E1))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Instalaciones que exigen OBLIGATORIAMENTE Proyecto Visado por Facultativo:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF85149))
                Text("1. Industrias en general con potencia > 20 kW.", fontSize = 11.sp)
                Text("2. Locales de pública concurrencia (CUALQUIER POTENCIA).", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text("3. Locales con riesgo de incendio o explosión ATEX (CUALQUIER POTENCIA).", fontSize = 11.sp)
                Text("4. Locales mojados o bombas de agua con potencia > 10 kW.", fontSize = 11.sp)
                Text("5. Garajes con ventilación forzada (TODOS) o natural > 5 plazas.", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text("6. Viviendas unifamiliares > 50 kW o Edificios de viviendas > 100 kW.", fontSize = 11.sp)
                Text("7. Instalaciones generadoras (autoconsumo) > 10 kW.", fontSize = 11.sp)

                HorizontalDivider(color = if (isDark) Color(0xFF30363D) else Color(0xFFE2E8F0))
                Text(
                    text = "En todos los demás casos, basta con MEMORIA TÉCNICA DE DISEÑO (MTD) redactada y firmada directamente por el instalador autorizado.",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF3FB950)
                )
            }
        }

        Text("🧰 LOS 6 INSTRUMENTOS OBLIGATORIOS DEL INSTALADOR", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF1C2128) else Color(0xFFF1F5F9))
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("1. Telurómetro: Medición de resistencia de tierra con picas auxiliares.", fontSize = 11.sp)
                Text("2. Megóhmetro a 500 V CC: Aislamiento entre conductores ≥ 0,50 MΩ.", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text("3. Comprobador de diferenciales: Medición de rampa de mA y tiempo de disparo en ms.", fontSize = 11.sp)
                Text("4. Medidor de bucle Zs: Comprobación de corte de magnetotérmicos en defecto a tierra.", fontSize = 11.sp)
                Text("5. Comprobador de continuidad a 200 mA: Resistencia de conductores PE ≤ 0,20 Ω.", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text("6. Multímetro True RMS y Pinza voltiamperimétrica.", fontSize = 11.sp)
            }
        }
    }
}

/* ==========================================================================================
 * 10. BOE REBT COMPLETO
 * ========================================================================================== */

@Composable
fun BoeRebtCompletoGodTierView(isDark: Boolean) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF161B22) else Color.White),
            border = BorderStroke(1.dp, Color(0xFF58A6FF).copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("ESTRUCTURA GENERAL DEL RD 842/2002 (BOE núm. 224)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF58A6FF))
                Text("• 29 Artículos generales: Delimitan el marco jurídico, competencias, OCA y régimen de sanciones.", fontSize = 11.sp)
                Text("• 52 Instrucciones Técnicas Complementarias (ITCs): Especificaciones técnicas obligatorias de diseño y montaje.", fontSize = 11.sp)
                Text("• Guía Técnica de Aplicación: Interpretación oficial del Ministerio (no vinculante pero recomendada).", fontSize = 11.sp)
            }
        }

        Text("⚖️ SILENCIO ADMINISTRATIVO EN EL REBT (PREGUNTAS TÍPICAS)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF13231B) else Color(0xFFE6F4EA)),
                border = BorderStroke(1.dp, Color(0xFF3FB950).copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text("Art. 14 Distribuidoras", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3FB950))
                    Text("Silencio POSITIVO", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                    Text("3 meses sin respuesta de la Comunidad Autónoma = Especificaciones APROBADAS.", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF261D15) else Color(0xFFFEF3E2)),
                border = BorderStroke(1.dp, Color(0xFFF59E0B).copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text("Art. 24 Excepciones", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD97706))
                    Text("Silencio NEGATIVO", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                    Text("3 meses sin respuesta del Ministerio de Industria = Excepción DESESTIMADA.", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}
