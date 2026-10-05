package com.example.ui.screens.exams

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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.MainViewModel
import com.example.ui.FeedbackManager
import kotlin.math.*

data class FormulaItem(
    val id: String,
    val title: String,
    val itcReference: String,
    val category: String,
    val formulaDisplay: String,
    val description: String,
    val variables: List<FormulaVariable>,
    val examRule: String,
    val exampleProblem: String,
    val calculatorType: String // "drop_single", "drop_three", "power_single", "power_three", "ground_rod", "reactive_power"
)

data class FormulaVariable(
    val symbol: String,
    val name: String,
    val unit: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamFormulasView(viewModel: MainViewModel) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todas") }

    val categories = listOf(
        "Todas",
        "Caída de Tensión",
        "Potencias & Corriente",
        "Puesta a Tierra",
        "Previsión Cargas",
        "Batería Condensadores",
        "Canalizaciones & Tubos"
    )

    val formulas = remember {
        listOf(
            FormulaItem(
                id = "drop_single",
                title = "Caída de Tensión Monofásica (230 V)",
                itcReference = "ITC-BT-19 / UNE 20460",
                category = "Caída de Tensión",
                formulaDisplay = "e = (2 · L · P) / (γ · S · V)  =  (2 · L · I · cos φ) / (γ · S)",
                description = "Calcula la pérdida de tensión en voltios producida en una línea monofásica de 230 V debido a la resistencia de los conductores de ida y vuelta.",
                variables = listOf(
                    FormulaVariable("e", "Caída de tensión", "Voltios (V)"),
                    FormulaVariable("L", "Longitud de la línea", "metros (m)"),
                    FormulaVariable("P", "Potencia activa", "Vatios (W)"),
                    FormulaVariable("γ", "Conductividad (Cobre: 56 a 20ºC / 48 a 70ºC; Aluminio: 35)", "m/(Ω·mm²)"),
                    FormulaVariable("S", "Sección del conductor", "mm²"),
                    FormulaVariable("V", "Tensión nominal monofásica (230 V)", "V"),
                    FormulaVariable("I", "Intensidad de corriente", "Amperios (A)"),
                    FormulaVariable("cos φ", "Factor de potencia (típico 0,8 - 1,0)", "adimensional")
                ),
                examRule = "¡REGLA DE ORO DE EXAMEN!: En monofásica SIEMPRE se multiplica por 2 en el numerador (ida y vuelta del neutro). Límites oficiales: Alumbrado 3%, Otros usos (fuerza) 5%, Derivación Individual 1,5%, Línea General de Alimentación 0,5%.",
                exampleProblem = "Ejemplo oficial: Línea monofásica de 40 m en cobre a 70ºC (γ = 48) que alimenta un termo de 3.000 W (cos φ = 1) con cable de 4 mm². e = (2 · 40 · 3000) / (48 · 4 · 230) = 240.000 / 44.160 = 5,43 V -> e(%) = (5,43 / 230) · 100 = 2,36 % (Cumple por ser < 5%).",
                calculatorType = "drop_single"
            ),
            FormulaItem(
                id = "drop_three",
                title = "Caída de Tensión Trifásica (400 V)",
                itcReference = "ITC-BT-19 / UNE-HD 60364",
                category = "Caída de Tensión",
                formulaDisplay = "e = (L · P) / (γ · S · V_L)  =  (√3 · L · I · cos φ) / (γ · S)",
                description = "Calcula la caída de tensión entre fases en una línea trifásica simétrica y equilibrada a 400 V.",
                variables = listOf(
                    FormulaVariable("e", "Caída de tensión compuesta", "Voltios (V)"),
                    FormulaVariable("L", "Longitud de la línea", "metros (m)"),
                    FormulaVariable("P", "Potencia trifásica total", "Vatios (W)"),
                    FormulaVariable("γ", "Conductividad del metal", "m/(Ω·mm²)"),
                    FormulaVariable("S", "Sección de fase", "mm²"),
                    FormulaVariable("V_L", "Tensión de línea / compuesta (400 V)", "V"),
                    FormulaVariable("I", "Intensidad de línea", "A")
                ),
                examRule = "¡CUIDADO CON LA TRAMPA!: Al usar la potencia trifásica total P y la tensión de línea V_L = 400 V, NO se multiplica por 2 ni por √3 en el numerador con P. La fórmula es e = (L · P) / (γ · S · V).",
                exampleProblem = "Ejemplo oficial: Motor trifásico de 15 kW a 400 V alimentado a 60 m con cobre (γ = 44 en caliente a 90ºC XLPE) y sección de 10 mm². e = (60 · 15.000) / (44 · 10 · 400) = 900.000 / 176.000 = 5,11 V -> e(%) = (5,11 / 400) · 100 = 1,28 %.",
                calculatorType = "drop_three"
            ),
            FormulaItem(
                id = "power_single",
                title = "Potencia e Intensidad Monofásica",
                itcReference = "ITC-BT-10 / Electrotecnia Básica",
                category = "Potencias & Corriente",
                formulaDisplay = "P = V · I · cos φ    =>    I = P / (V · cos φ)",
                description = "Relación entre potencia activa (W), tensión simple (230 V), corriente (A) y factor de potencia.",
                variables = listOf(
                    FormulaVariable("P", "Potencia activa", "Vatios (W) o kW"),
                    FormulaVariable("V", "Tensión monofásica (230 V)", "V"),
                    FormulaVariable("I", "Intensidad de corriente", "Amperios (A)"),
                    FormulaVariable("cos φ", "Factor de potencia", "adimensional (0 a 1)"),
                    FormulaVariable("S", "Potencia aparente: S = V · I", "Voltio-Amperios (VA)"),
                    FormulaVariable("Q", "Potencia reactiva: Q = V · I · sen φ", "Voltio-Amperios reactivos (VAr)")
                ),
                examRule = "En receptores resistivos puros (estufas, hornos, termos), cos φ = 1. Para motores y fluorescentes sin compensar usar siempre el valor de cos φ indicado en el enunciado (típico 0,8).",
                exampleProblem = "Ejemplo: Vivienda básica con potencia contratada 5.750 W a 230 V (cos φ = 1). I = 5.750 / 230 = 25 A (corresponde a IGA de 25 A).",
                calculatorType = "power_single"
            ),
            FormulaItem(
                id = "power_three",
                title = "Potencia e Intensidad Trifásica",
                itcReference = "ITC-BT-10 / ITC-BT-43",
                category = "Potencias & Corriente",
                formulaDisplay = "P = √3 · V_L · I_L · cos φ    =>    I = P / (√3 · V_L · cos φ)",
                description = "Intensidad de fase y línea en redes trifásicas con tensión compuesta de 400 V.",
                variables = listOf(
                    FormulaVariable("P", "Potencia activa trifásica", "W o kW"),
                    FormulaVariable("V_L", "Tensión entre fases (400 V)", "V"),
                    FormulaVariable("I_L", "Intensidad de línea", "A"),
                    FormulaVariable("√3", "Constante trifásica (~1,732)", "adimensional"),
                    FormulaVariable("cos φ", "Factor de potencia", "adimensional")
                ),
                examRule = "Regla mnemotécnica para 400 V: √3 · 400 ≈ 692,82. Por tanto, para cos φ = 1, I (A) ≈ P(W) / 693. Por cada kilovatio trifásico a 400 V corresponden aproximadamente 1,44 Amperios.",
                exampleProblem = "Ejemplo: Bomba de agua trifásica de 7,5 kW, cos φ = 0,85. I = 7.500 / (1,732 · 400 · 0,85) = 7.500 / 588,9 = 12,74 A.",
                calculatorType = "power_three"
            ),
            FormulaItem(
                id = "ground_rod",
                title = "Resistencia de Puesta a Tierra y Contacto Indirecto",
                itcReference = "ITC-BT-18 / ITC-BT-24",
                category = "Puesta a Tierra",
                formulaDisplay = "Pica Vertical: R = ρ / L    |    Seguridad Contacto: R_A ≤ U_L / I_Δn",
                description = "Cálculo de la resistencia de difusión de un electrodo vertical y condición de disparo de la protección diferencial en esquema TT.",
                variables = listOf(
                    FormulaVariable("R", "Resistencia de tierra de la pica", "Ohmios (Ω)"),
                    FormulaVariable("ρ", "Resistividad del terreno", "Ω·m (arena: 1000, arcilla: 50)"),
                    FormulaVariable("L", "Longitud de la pica (mínimo oficial: 2 m)", "metros (m)"),
                    FormulaVariable("U_L", "Tensión límite de seguridad (50V secos / 24V húmedos)", "V"),
                    FormulaVariable("I_Δn", "Sensibilidad del diferencial (ej. 30 mA = 0,03 A)", "A"),
                    FormulaVariable("R_A", "Resistencia máxima admisible de las masas", "Ohmios (Ω)")
                ),
                examRule = "¡PREGUNTA SEGURA EN EXAMEN!: En esquema TT con diferencial de 30 mA: en local seco (U_L = 50 V), R_máx = 50 / 0,03 = 1.666 Ω. En local húmedo (U_L = 24 V), R_máx = 24 / 0,03 = 800 Ω.",
                exampleProblem = "Ejemplo: Terreno de arcilla húmeda (ρ = 100 Ω·m) con 2 picas de 2 m en paralelo separadas más de 2L. R_pica = 100 / 2 = 50 Ω. En paralelo: R_total ≈ 25 Ω (muy inferior a 800 Ω, cumple perfectamente).",
                calculatorType = "ground_rod"
            ),
            FormulaItem(
                id = "reactive_power",
                title = "Compensación de Energía Reactiva (Condensadores)",
                itcReference = "ITC-BT-43 / Eficiencia Energética",
                category = "Batería Condensadores",
                formulaDisplay = "Q_c = P · (tan φ_1 - tan φ_2)",
                description = "Determina la potencia capacitiva en kVAr necesaria para elevar el factor de potencia desde un valor inicial cos φ_1 hasta un objetivo cos φ_2.",
                variables = listOf(
                    FormulaVariable("Q_c", "Potencia de la batería de condensadores", "kVAr"),
                    FormulaVariable("P", "Potencia activa de la instalación", "kW"),
                    FormulaVariable("tan φ_1", "Tangente del ángulo inicial (arccos cos φ_1)", "adimensional"),
                    FormulaVariable("tan φ_2", "Tangente del ángulo objetivo (arccos cos φ_2)", "adimensional")
                ),
                examRule = "El objetivo habitual de compensación es alcanzar cos φ = 0,95 (tan φ = 0,33) para evitar recargos por energía reactiva según la normativa de facturación eléctrica.",
                exampleProblem = "Ejemplo: Taller con P = 100 kW y cos φ_1 = 0,70 (tan φ_1 = 1,02). Se desea cos φ_2 = 0,95 (tan φ_2 = 0,33). Q_c = 100 · (1,02 - 0,33) = 69 kVAr.",
                calculatorType = "reactive_power"
            ),
            FormulaItem(
                id = "conduit_tubes",
                title = "Dimensionado de Tubos Protectores",
                itcReference = "ITC-BT-21 (Tablas 1 a 5)",
                category = "Canalizaciones & Tubos",
                formulaDisplay = "S_interior_tubo ≥ 4 · Σ S_exterior_cables  (Ocupación máx: 25% a 35%)",
                description = "El área interior del tubo debe garantizar que los conductores puedan alojarse y extraerse fácilmente sin sufrir esfuerzos mecánicos.",
                variables = listOf(
                    FormulaVariable("D_ext", "Diámetro exterior nominal del tubo", "mm (16, 20, 25, 32, 40...)"),
                    FormulaVariable("S_ext", "Sección exterior total de cada conductor con aislamiento", "mm²"),
                    FormulaVariable("DI", "Derivación individual: tubo exterior mínimo = 32 mm", "mm")
                ),
                examRule = "Diámetros mínimos normativos obligatorios: En Derivaciones Individuales (DI) el diámetro exterior mínimo del tubo es de 32 mm. En viviendas empotrado en tabiques: mínimo 16 mm o 20 mm según circuito.",
                exampleProblem = "Pregunta tipo test: ¿Cuál es el diámetro exterior mínimo reglamentario para el tubo de una derivación individual monofásica según ITC-BT-15? Respuesta correcta: 32 mm.",
                calculatorType = "conduit_tubes"
            ),
            FormulaItem(
                id = "loads_forecast",
                title = "Previsión de Cargas en Edificios de Viviendas",
                itcReference = "ITC-BT-10",
                category = "Previsión Cargas",
                formulaDisplay = "P_total_viviendas = P_media · [ 1 + (n - 1) · 0,5 ]",
                description = "Fórmula oficial de simultaneidad para n viviendas idénticas en un edificio residencial.",
                variables = listOf(
                    FormulaVariable("n", "Número de viviendas del edificio", "unidades"),
                    FormulaVariable("P_media", "Potencia prevista por vivienda (5.750 W básica / 9.200 W elevada)", "W"),
                    FormulaVariable("P_total", "Carga conjunta simultánea del conjunto de viviendas", "W o kW")
                ),
                examRule = "¡TABLA Y FÓRMULA OFICIAL ITC-BT-10!: Para 1 sola vivienda el coeficiente es 1. Para n viviendas: Coeficiente = 1 + (n - 1) · 0,5. Ejemplo: 10 viviendas básicas (5.750 W): Coeficiente = 1 + (9 · 0,5) = 5,5 -> P = 5,5 · 5.750 W = 31.625 W.",
                exampleProblem = "Edificio de 20 viviendas con electrificación básica (5.750 W): Coeficiente = 1 + (19 · 0,5) = 10,5. Potencia simultánea = 10,5 · 5.750 W = 60.375 W = 60,38 kW.",
                calculatorType = "loads_forecast"
            )
        )
    }

    val filteredFormulas = remember(searchQuery, selectedCategory) {
        formulas.filter { f ->
            val matchCat = selectedCategory == "Todas" || f.category == selectedCategory
            val matchQuery = searchQuery.isBlank() ||
                    f.title.contains(searchQuery, ignoreCase = true) ||
                    f.itcReference.contains(searchQuery, ignoreCase = true) ||
                    f.description.contains(searchQuery, ignoreCase = true) ||
                    f.formulaDisplay.contains(searchQuery, ignoreCase = true) ||
                    f.examRule.contains(searchQuery, ignoreCase = true)
            matchCat && matchQuery
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 48.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Header Banner
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color(0xFFFFFFFF)
                ),
                border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF58A6FF).copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Functions,
                                contentDescription = null,
                                tint = Color(0xFF58A6FF),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Formulario Oficial REBT para Exámenes",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Fórmulas autorizadas, reglas de oro y comprobador de cálculo para convocatorias oficiales",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // 2. Search Bar
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Buscar fórmula (ej: caída, tierra, trifásica, ITC-19)...", fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Limpiar")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                singleLine = true
            )
        }

        // 3. Category Filter Chips
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(categories) { cat ->
                    val isSelected = selectedCategory == cat
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            FeedbackManager.playClick(context)
                            selectedCategory = cat
                        },
                        label = { Text(cat, fontSize = 12.sp) },
                        leadingIcon = if (isSelected) {
                            { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                        } else null
                    )
                }
            }
        }

        // 4. Formula Cards
        items(filteredFormulas, key = { it.id }) { item ->
            FormulaCardItem(item = item, isDarkTheme = viewModel.isDarkTheme)
        }
    }
}

@Composable
fun FormulaCardItem(item: FormulaItem, isDarkTheme: Boolean) {
    val context = LocalContext.current
    var isExpanded by remember { mutableStateOf(false) }
    var showQuickCalc by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkTheme) Color(0xFF161B22) else Color.White
        ),
        border = BorderStroke(1.dp, if (isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Category badge + ITC Reference
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFF58A6FF).copy(alpha = 0.15f)
                ) {
                    Text(
                        text = item.itcReference,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF58A6FF),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = item.category,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title
            Text(
                text = item.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Formula Display Box (Monospace / Mathematical Card)
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = if (isDarkTheme) Color(0xFF0D1117) else Color(0xFFF6F8FA),
                border = BorderStroke(1.dp, if (isDarkTheme) Color(0xFF21262D) else Color(0xFFD0D7DE)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = item.formulaDisplay,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDarkTheme) Color(0xFF7EE787) else Color(0xFF1A7F37),
                        lineHeight = 19.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Short Description
            Text(
                text = item.description,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Exam Rule Highlight Card (Golden Tip)
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFFF59E0B).copy(alpha = 0.12f),
                border = BorderStroke(1.dp, Color(0xFFF59E0B).copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = null,
                        tint = Color(0xFFF59E0B),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = item.examRule,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (isDarkTheme) Color(0xFFFFD166) else Color(0xFF9A6700),
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons Row: Ver Detalle / Probar Cálculo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        FeedbackManager.playClick(context)
                        isExpanded = !isExpanded
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (isExpanded) "Ocultar" else "Variables & Ejemplo", fontSize = 11.sp)
                }

                FilledTonalButton(
                    onClick = {
                        FeedbackManager.playClick(context)
                        showQuickCalc = !showQuickCalc
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Calculate,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (showQuickCalc) "Cerrar Calc" else "Probar Cálculo", fontSize = 11.sp)
                }
            }

            // Expanded Section: Variables & Real Exam Problem
            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp)
                ) {
                    HorizontalDivider(color = if (isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Significado de Variables y Unidades:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    item.variables.forEach { v ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${v.symbol}:",
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.width(36.dp)
                            )
                            Text(
                                text = "${v.name} (${v.unit})",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Problema Resuelto de Convocatoria Oficial:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isDarkTheme) Color(0xFF0E1726) else Color(0xFFF0F7FF),
                        border = BorderStroke(1.dp, Color(0xFF58A6FF).copy(alpha = 0.3f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = item.exampleProblem,
                            fontSize = 11.sp,
                            lineHeight = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }

            // Interactive Quick Calculation Widget
            AnimatedVisibility(visible = showQuickCalc) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp)
                ) {
                    HorizontalDivider(color = if (isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))
                    Spacer(modifier = Modifier.height(10.dp))
                    InteractiveFormulaWidget(calculatorType = item.calculatorType, isDarkTheme = isDarkTheme)
                }
            }
        }
    }
}

@Composable
fun InteractiveFormulaWidget(calculatorType: String, isDarkTheme: Boolean) {
    when (calculatorType) {
        "drop_single" -> DropSingleCalculator(isDarkTheme)
        "drop_three" -> DropThreeCalculator(isDarkTheme)
        "power_single" -> PowerSingleCalculator(isDarkTheme)
        "power_three" -> PowerThreeCalculator(isDarkTheme)
        "ground_rod" -> GroundRodCalculator(isDarkTheme)
        "reactive_power" -> ReactivePowerCalculator(isDarkTheme)
        else -> GenericExamCalculator(calculatorType, isDarkTheme)
    }
}

@Composable
fun DropSingleCalculator(isDarkTheme: Boolean) {
    var lengthStr by remember { mutableStateOf("40") }
    var powerStr by remember { mutableStateOf("3000") }
    var sectionStr by remember { mutableStateOf("4") }
    var gammaStr by remember { mutableStateOf("48") } // Cu a 70ºC

    val l = lengthStr.toDoubleOrNull() ?: 0.0
    val p = powerStr.toDoubleOrNull() ?: 0.0
    val s = sectionStr.toDoubleOrNull() ?: 1.0
    val g = gammaStr.toDoubleOrNull() ?: 48.0

    val dropV = if (s > 0 && g > 0) (2.0 * l * p) / (g * s * 230.0) else 0.0
    val dropPct = (dropV / 230.0) * 100.0
    val complies3 = dropPct <= 3.0
    val complies5 = dropPct <= 5.0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isDarkTheme) Color(0xFF0D1117) else Color(0xFFF8FAFC), RoundedCornerShape(10.dp))
            .padding(12.dp)
    ) {
        Text("Comprobador Caída Monofásica (230 V):", fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = lengthStr,
                onValueChange = { lengthStr = it },
                label = { Text("L (m)", fontSize = 10.sp) },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            OutlinedTextField(
                value = powerStr,
                onValueChange = { powerStr = it },
                label = { Text("P (W)", fontSize = 10.sp) },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            OutlinedTextField(
                value = sectionStr,
                onValueChange = { sectionStr = it },
                label = { Text("S (mm²)", fontSize = 10.sp) },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (complies5) Color(0xFF238636).copy(alpha = 0.15f) else Color(0xFFDA3633).copy(alpha = 0.15f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = "Resultado: ΔU = ${String.format(java.util.Locale.US, "%.2f", dropV)} V  (${String.format(java.util.Locale.US, "%.2f", dropPct)} %)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = if (complies5) Color(0xFF3FB950) else Color(0xFFF85149)
                )
                Text(
                    text = "• Alumbrado (<3%): ${if (complies3) "CUMPLE" else "NO CUMPLE"}\n• Fuerza (<5%): ${if (complies5) "CUMPLE" else "NO CUMPLE"}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun DropThreeCalculator(isDarkTheme: Boolean) {
    var lengthStr by remember { mutableStateOf("60") }
    var powerStr by remember { mutableStateOf("15000") }
    var sectionStr by remember { mutableStateOf("10") }
    var gammaStr by remember { mutableStateOf("44") } // Cu a 90ºC

    val l = lengthStr.toDoubleOrNull() ?: 0.0
    val p = powerStr.toDoubleOrNull() ?: 0.0
    val s = sectionStr.toDoubleOrNull() ?: 1.0
    val g = gammaStr.toDoubleOrNull() ?: 44.0

    val dropV = if (s > 0 && g > 0) (l * p) / (g * s * 400.0) else 0.0
    val dropPct = (dropV / 400.0) * 100.0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isDarkTheme) Color(0xFF0D1117) else Color(0xFFF8FAFC), RoundedCornerShape(10.dp))
            .padding(12.dp)
    ) {
        Text("Comprobador Caída Trifásica (400 V):", fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = lengthStr,
                onValueChange = { lengthStr = it },
                label = { Text("L (m)", fontSize = 10.sp) },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            OutlinedTextField(
                value = powerStr,
                onValueChange = { powerStr = it },
                label = { Text("P (W)", fontSize = 10.sp) },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            OutlinedTextField(
                value = sectionStr,
                onValueChange = { sectionStr = it },
                label = { Text("S (mm²)", fontSize = 10.sp) },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (dropPct <= 5.0) Color(0xFF238636).copy(alpha = 0.15f) else Color(0xFFDA3633).copy(alpha = 0.15f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = "Resultado: ΔU = ${String.format(java.util.Locale.US, "%.2f", dropV)} V  (${String.format(java.util.Locale.US, "%.2f", dropPct)} %)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = if (dropPct <= 5.0) Color(0xFF3FB950) else Color(0xFFF85149)
                )
                Text(
                    text = "Comprobación reglamentaria: ${if (dropPct <= 5.0) "CUMPLE (<5% límite general)" else "SUPERA EL LÍMITE (requiere mayor sección)"}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun PowerSingleCalculator(isDarkTheme: Boolean) {
    var powerStr by remember { mutableStateOf("5750") }
    var cosPhiStr by remember { mutableStateOf("1.0") }

    val p = powerStr.toDoubleOrNull() ?: 0.0
    val cosPhi = cosPhiStr.toDoubleOrNull() ?: 1.0
    val current = if (cosPhi > 0) p / (230.0 * cosPhi) else 0.0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isDarkTheme) Color(0xFF0D1117) else Color(0xFFF8FAFC), RoundedCornerShape(10.dp))
            .padding(12.dp)
    ) {
        Text("Cálculo Intensidad Monofásica (230 V):", fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = powerStr,
                onValueChange = { powerStr = it },
                label = { Text("P (W)", fontSize = 10.sp) },
                modifier = Modifier.weight(1.2f),
                singleLine = true
            )
            OutlinedTextField(
                value = cosPhiStr,
                onValueChange = { cosPhiStr = it },
                label = { Text("cos φ", fontSize = 10.sp) },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Intensidad resultante: I = ${String.format(java.util.Locale.US, "%.2f", current)} A",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Composable
fun PowerThreeCalculator(isDarkTheme: Boolean) {
    var powerStr by remember { mutableStateOf("15000") }
    var cosPhiStr by remember { mutableStateOf("0.85") }

    val p = powerStr.toDoubleOrNull() ?: 0.0
    val cosPhi = cosPhiStr.toDoubleOrNull() ?: 0.85
    val current = if (cosPhi > 0) p / (sqrt(3.0) * 400.0 * cosPhi) else 0.0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isDarkTheme) Color(0xFF0D1117) else Color(0xFFF8FAFC), RoundedCornerShape(10.dp))
            .padding(12.dp)
    ) {
        Text("Cálculo Intensidad Trifásica (400 V):", fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = powerStr,
                onValueChange = { powerStr = it },
                label = { Text("P (W)", fontSize = 10.sp) },
                modifier = Modifier.weight(1.2f),
                singleLine = true
            )
            OutlinedTextField(
                value = cosPhiStr,
                onValueChange = { cosPhiStr = it },
                label = { Text("cos φ", fontSize = 10.sp) },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Intensidad de línea: I_L = ${String.format(java.util.Locale.US, "%.2f", current)} A",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Composable
fun GroundRodCalculator(isDarkTheme: Boolean) {
    var rhoStr by remember { mutableStateOf("100") }
    var lengthStr by remember { mutableStateOf("2") }

    val rho = rhoStr.toDoubleOrNull() ?: 100.0
    val l = lengthStr.toDoubleOrNull() ?: 2.0
    val rPica = if (l > 0) rho / l else 0.0
    val compliesSeco = rPica <= 1666.0
    val compliesHum = rPica <= 800.0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isDarkTheme) Color(0xFF0D1117) else Color(0xFFF8FAFC), RoundedCornerShape(10.dp))
            .padding(12.dp)
    ) {
        Text("Estimación Pica Vertical & Contacto Indirecto:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = rhoStr,
                onValueChange = { rhoStr = it },
                label = { Text("ρ (Ω·m)", fontSize = 10.sp) },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            OutlinedTextField(
                value = lengthStr,
                onValueChange = { lengthStr = it },
                label = { Text("Longitud L (m)", fontSize = 10.sp) },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (compliesHum) Color(0xFF238636).copy(alpha = 0.15f) else Color(0xFFF59E0B).copy(alpha = 0.15f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = "Resistencia estimada: R = ${String.format(java.util.Locale.US, "%.1f", rPica)} Ω",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = if (compliesHum) Color(0xFF3FB950) else Color(0xFFF59E0B)
                )
                Text(
                    text = "• Local Seco (límite 50V / 30mA = 1.666 Ω): ${if (compliesSeco) "CUMPLE" else "NO CUMPLE"}\n" +
                            "• Local Húmedo (límite 24V / 30mA = 800 Ω): ${if (compliesHum) "CUMPLE" else "NO CUMPLE"}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ReactivePowerCalculator(isDarkTheme: Boolean) {
    var powerKwStr by remember { mutableStateOf("100") }
    var cos1Str by remember { mutableStateOf("0.70") }
    var cos2Str by remember { mutableStateOf("0.95") }

    val p = powerKwStr.toDoubleOrNull() ?: 100.0
    val c1 = cos1Str.toDoubleOrNull() ?: 0.70
    val c2 = cos2Str.toDoubleOrNull() ?: 0.95

    val tan1 = if (c1 in 0.01..0.999) tan(acos(c1)) else 1.02
    val tan2 = if (c2 in 0.01..0.999) tan(acos(c2)) else 0.33
    val qc = (p * (tan1 - tan2)).coerceAtLeast(0.0)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isDarkTheme) Color(0xFF0D1117) else Color(0xFFF8FAFC), RoundedCornerShape(10.dp))
            .padding(12.dp)
    ) {
        Text("Batería de Condensadores Necesaria:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = powerKwStr,
                onValueChange = { powerKwStr = it },
                label = { Text("P (kW)", fontSize = 10.sp) },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            OutlinedTextField(
                value = cos1Str,
                onValueChange = { cos1Str = it },
                label = { Text("cos φ actual", fontSize = 10.sp) },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            OutlinedTextField(
                value = cos2Str,
                onValueChange = { cos2Str = it },
                label = { Text("cos φ objetivo", fontSize = 10.sp) },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Potencia reactiva requerida: Q_c = ${String.format(java.util.Locale.US, "%.1f", qc)} kVAr",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Composable
fun GenericExamCalculator(calculatorType: String, isDarkTheme: Boolean) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Consulta el criterio reglamentario y las tablas oficiales en el apartado de Documentación y Guías Técnicas.",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(10.dp)
        )
    }
}
