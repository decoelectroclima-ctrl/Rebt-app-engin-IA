package com.example.ui.screens.laboratory

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.MainViewModel
import com.example.ui.FeedbackManager

@Composable
fun LaboratoryScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val isPremium = viewModel.isPremium
    val subTabs = listOf(
        "Conductores",
        if (isPremium) "Previsión Cargas" else "Previsión Cargas 🔒",
        "Tubos ITC-21",
        if (isPremium) "Tierras ITC-18" else "Tierras ITC-18 🔒",
        if (isPremium) "Protecciones ITC-22/24" else "Protecciones ITC-22/24 🔒"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Laboratorio de Cálculo REBT",
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Dimensionamiento técnico riguroso según las fórmulas oficiales de las ITCs vigentes.",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Sub Tabs
        ScrollableTabRow(
            selectedTabIndex = viewModel.labActiveSubTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            edgePadding = 0.dp,
            modifier = Modifier.clip(RoundedCornerShape(12.dp))
        ) {
            subTabs.forEachIndexed { index, title ->
                Tab(
                    selected = viewModel.labActiveSubTab == index,
                    onClick = {
                        FeedbackManager.playClick(context)
                        viewModel.labActiveSubTab = index
                    },
                    text = { Text(title, fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (viewModel.labActiveSubTab) {
            0 -> CableSizingTab(viewModel)
            1 -> if (isPremium) {
                BuildingLoadTab(viewModel)
            } else {
                LockedLabCalculatorCard(
                    title = "Previsión de Cargas en Edificios",
                    itcReference = "ITC-BT-10 Oficial",
                    description = "Dimensionamiento riguroso de potencia simultánea para edificios de viviendas, servicios generales, garajes con recarga de vehículos eléctricos (ITC-52) y locales comerciales.",
                    keyFeatures = listOf(
                        "Grados de electrificación Básica (5.750 W) y Elevada (9.200 W)",
                        "Coeficientes de simultaneidad oficiales según número de viviendas",
                        "Integración automática con recarga de vehículo eléctrico (ITC-BT-52)",
                        "Cálculo de acometida general y línea general de alimentación (LGA)"
                    ),
                    onUnlock = {
                        FeedbackManager.playClick(context)
                        viewModel.activeTab = "subscription"
                    },
                    isDarkTheme = viewModel.isDarkTheme
                )
            }
            2 -> ConduitTubesTab(viewModel)
            3 -> if (isPremium) {
                GroundingTab(viewModel)
            } else {
                LockedLabCalculatorCard(
                    title = "Red de Puesta a Tierra y Protección",
                    itcReference = "ITC-BT-18 Oficial",
                    description = "Cálculo de resistencia de tierra admisible, dimensionamiento de picas, placas, conductores de cobre desnudo y verificación de tensiones de paso y contacto.",
                    keyFeatures = listOf(
                        "Fórmulas oficiales para picas verticales, conductores enterrados y placas",
                        "Resistividad del terreno según tipo de suelo (roca, arcilla, caliza)",
                        "Sensibilidad diferencial requerida (30 mA, 300 mA)",
                        "Comprobación automática de seguridad frente a contactos indirectos"
                    ),
                    onUnlock = {
                        FeedbackManager.playClick(context)
                        viewModel.activeTab = "subscription"
                    },
                    isDarkTheme = viewModel.isDarkTheme
                )
            }
            4 -> if (isPremium) {
                ProtectionsTab(viewModel)
            } else {
                LockedLabCalculatorCard(
                    title = "Coordinación y Protecciones Eléctricas",
                    itcReference = "ITC-BT-22 y ITC-BT-24 Oficial",
                    description = "Dimensionamiento y comprobación reglamentaria de interruptores automáticos (PIA / IGA), poder de corte, curvas de disparo y sensibilidad de interruptores diferenciales.",
                    keyFeatures = listOf(
                        "Reglas de coordinación conductor-protección (Ib ≤ In ≤ Iz' y I2 ≤ 1.45 · Iz')",
                        "Calibres comerciales estándar recomendados (10A, 16A, 20A, 25A, 32A, 40A, 50A, 63A)",
                        "Sensibilidad de interruptores diferenciales (30 mA y 300 mA)",
                        "Verificación de cumplimiento frente a sobreintensidades y contactos indirectos"
                    ),
                    onUnlock = {
                        FeedbackManager.playClick(context)
                        viewModel.activeTab = "subscription"
                    },
                    isDarkTheme = viewModel.isDarkTheme
                )
            }
        }
    }
}

@Composable
private fun LockedLabCalculatorCard(
    title: String,
    itcReference: String,
    description: String,
    keyFeatures: List<String>,
    onUnlock: () -> Unit,
    isDarkTheme: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .testTag("locked_lab_calculator_card"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkTheme) Color(0xFF1E211A) else Color(0xFFFBF8EE)
        ),
        border = BorderStroke(1.5.dp, Color(0xFFE67E22))
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = androidx.compose.foundation.shape.CircleShape,
                color = Color(0xFFE67E22).copy(alpha = 0.15f),
                modifier = Modifier.size(54.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Calculadora Pro",
                        tint = Color(0xFFE67E22),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = Color(0xFFE67E22).copy(alpha = 0.15f)
            ) {
                Text(
                    text = "FUNCIÓN EXCLUSIVA PLAN PRO",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFE67E22),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = itcReference,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                keyFeatures.forEach { feat ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color(0xFF10B981),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = feat,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onUnlock,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE67E22))
            ) {
                Icon(Icons.Default.WorkspacePremium, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Desbloquear con Plan Pro", fontWeight = FontWeight.Bold)
            }
        }
    }
}

// -------------------------------------------------------------
// SUB-TAB 1: CABLE SIZING
// -------------------------------------------------------------
@Composable
fun CableSizingTab(viewModel: MainViewModel) {
    val context = LocalContext.current

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color(0xFFF8FAFC)
                ),
                border = BorderStroke(1.5.dp, Color(0xFF2563EB).copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Build,
                            contentDescription = null,
                            tint = Color(0xFF2563EB),
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Método de Instalación y Guía de Obra",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Text(
                        text = "Se toman en obra ante una línea existente, una ampliación, una nueva carga, una modificación de cuadro o una instalación que necesitamos comprobar en ese mismo momento.",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                    Text(
                        text = "Método A2 (ITC-BT-19):",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    BulletPoint("Cable multipolar en pared aislante")
                    BulletPoint("Cable multiconductor dentro de tubo en pared térmicamente aislante")

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Selección de Conductor:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    BulletPoint("Cobre: Tabla 1 ITC-BT-19")
                    BulletPoint("Aluminio: Excepción UNE desde 10 mm²")

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Con Sección BT podemos trabajar directamente sobre el terreno:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    BulletPoint("Comprobar rápidamente si una instalación existente es correcta.")
                    BulletPoint("Predimensionar conductores y protecciones antes de ejecutar una nueva línea o una modificación.")
                    BulletPoint("Calcular de forma precisa secciones, intensidades, protecciones y caídas de tensión.")
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                ),
                border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Parámetros de Entrada (ITC-BT-14/19)",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    OutlinedTextField(
                        value = viewModel.labPowerKw,
                        onValueChange = {
                            viewModel.labPowerKw = it
                            viewModel.runLaboratoryCalculation()
                        },
                        label = { Text("Potencia de la Carga (kW)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth().testTag("lab_power_input")
                    )

                    OutlinedTextField(
                        value = viewModel.labLengthM,
                        onValueChange = {
                            viewModel.labLengthM = it
                            viewModel.runLaboratoryCalculation()
                        },
                        label = { Text("Longitud de la Línea (metros)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth().testTag("lab_length_input")
                    )

                    OutlinedTextField(
                        value = viewModel.labMaxDropPct,
                        onValueChange = {
                            viewModel.labMaxDropPct = it
                            viewModel.runLaboratoryCalculation()
                        },
                        label = { Text("Caída de Tensión Máxima Admisible (%)") },
                        supportingText = { Text("LGA: 0,5% | DI: 1,5% | Alumbrado: 3% | Fuerza: 5%") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth().testTag("lab_drop_input")
                    )

                    // Installation method selector A2, B1, B2, C, E, D
                    Text(
                        text = "Método de Instalación (UNE-HD 60364-5-52)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        listOf("A2", "B1", "B2", "C", "E", "D").forEach { method ->
                            FilterChip(
                                selected = viewModel.labInstallMethod == method,
                                onClick = {
                                    viewModel.labInstallMethod = method
                                    viewModel.runLaboratoryCalculation()
                                },
                                label = { Text(method, fontSize = 11.sp) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // Insulation Type Switch
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Aislamiento XLPE (90ºC) vs PVC (70ºC)", fontSize = 13.sp)
                        Switch(
                            checked = viewModel.labInsulationType.contains("XLPE"),
                            onCheckedChange = {
                                viewModel.labInsulationType = if (it) "XLPE (90ºC)" else "PVC (70ºC)"
                                viewModel.runLaboratoryCalculation()
                            }
                        )
                    }

                    OutlinedTextField(
                        value = viewModel.labAmbientTemp,
                        onValueChange = {
                            viewModel.labAmbientTemp = it
                            viewModel.runLaboratoryCalculation()
                        },
                        label = { Text("Temperatura Ambiente (ºC)") },
                        supportingText = { Text("Base estándar: 30ºC") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = viewModel.labGroupingFactor,
                        onValueChange = {
                            viewModel.labGroupingFactor = it
                            viewModel.runLaboratoryCalculation()
                        },
                        label = { Text("Factor de Agrupamiento (fa)") },
                        supportingText = { Text("Circuitos agrupados en tubo/bandeja (ej: 0.80)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Switch 1: Monofásica / Trifásica
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Sistema Trifásico (400V)", fontSize = 13.sp)
                        Switch(
                            checked = viewModel.labIsThreePhase,
                            onCheckedChange = {
                                viewModel.labIsThreePhase = it
                                viewModel.runLaboratoryCalculation()
                            }
                        )
                    }

                    // Material
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Conductor de Cobre (vs Aluminio)", fontSize = 13.sp)
                        Switch(
                            checked = viewModel.labCableMaterial == "cobre",
                            onCheckedChange = {
                                viewModel.labCableMaterial = if (it) "cobre" else "aluminio"
                                viewModel.runLaboratoryCalculation()
                            }
                        )
                    }
                }
            }
        }

        // Result Card
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF1C222D) else Color(0xFFF1F6FE)
                ),
                border = BorderStroke(1.5.dp, Color(0xFF58A6FF))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Resultado Reglamentario",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color(0xFF58A6FF)
                        )
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF58A6FF).copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "${viewModel.labCalculatedSection} mm²",
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp,
                                color = Color(0xFF58A6FF),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "• Intensidad Corregida (Iz'): ${String.format("%.1f", viewModel.labCorrectedIz)} A\n" +
                                "• Sección Mínima PE (Tierra): ${viewModel.labCalculatedPeSection} mm²",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = viewModel.labStatusMessage,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// SUB-TAB 2: BUILDING LOAD FORECASTING
// -------------------------------------------------------------
@Composable
fun BuildingLoadTab(viewModel: MainViewModel) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                ),
                border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Edificio de Viviendas (ITC-BT-10)",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    OutlinedTextField(
                        value = viewModel.foreDwellingsBasic,
                        onValueChange = {
                            viewModel.foreDwellingsBasic = it
                            viewModel.runBuildingForecastingCalculation()
                        },
                        label = { Text("Nº Viviendas Electrificación Básica (5.750 W)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = viewModel.foreDwellingsElevated,
                        onValueChange = {
                            viewModel.foreDwellingsElevated = it
                            viewModel.runBuildingForecastingCalculation()
                        },
                        label = { Text("Nº Viviendas Electrificación Elevada (9.200 W)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = viewModel.foreCommercialSqm,
                        onValueChange = {
                            viewModel.foreCommercialSqm = it
                            viewModel.runBuildingForecastingCalculation()
                        },
                        label = { Text("Superficie Locales Comerciales (m²)") },
                        supportingText = { Text("100 W/m² con mínimo de 3.450 W") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = viewModel.foreGarageSqm,
                        onValueChange = {
                            viewModel.foreGarageSqm = it
                            viewModel.runBuildingForecastingCalculation()
                        },
                        label = { Text("Superficie Garaje Forzado (m²)") },
                        supportingText = { Text("20 W/m² con mínimo de 3.450 W") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = viewModel.foreGeneralServicesKw,
                        onValueChange = {
                            viewModel.foreGeneralServicesKw = it
                            viewModel.runBuildingForecastingCalculation()
                        },
                        label = { Text("Servicios Comunes (kW)") },
                        supportingText = { Text("Ascensores, hidropresores (Cs = 1,0)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Result Card
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF1C222D) else Color(0xFFF1F6FE)
                ),
                border = BorderStroke(1.5.dp, Color(0xFF3FB950))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Previsión Total: ${String.format("%.2f", viewModel.foreCalculatedPowerKw)} kW",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 17.sp,
                        color = Color(0xFF3FB950)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = viewModel.foreStatusMessage,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// SUB-TAB 3: CONDUIT TUBES (ITC-BT-21)
// -------------------------------------------------------------
@Composable
fun ConduitTubesTab(viewModel: MainViewModel) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                ),
                border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Diámetro de Tubos Protectores (ITC-BT-21)",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    OutlinedTextField(
                        value = viewModel.tubesConductorSec,
                        onValueChange = {
                            viewModel.tubesConductorSec = it
                            viewModel.runTubeDiameterCalculation()
                        },
                        label = { Text("Sección de los Conductores (mm²)") },
                        supportingText = { Text("1.5, 2.5, 4, 6, 10, 16, 25, 35") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = viewModel.tubesConductorsCount,
                        onValueChange = {
                            viewModel.tubesConductorsCount = it
                            viewModel.runTubeDiameterCalculation()
                        },
                        label = { Text("Número de Conductores en el Tubo") },
                        supportingText = { Text("Generalmente 3 (Fase+N+PE) o 5 (3F+N+PE)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF1C222D) else Color(0xFFF1F6FE)
                ),
                border = BorderStroke(1.5.dp, Color(0xFF58A6FF))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Diámetro Exterior Mínimo: Ø ${viewModel.tubesCalculatedDiameterMm} mm",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 17.sp,
                        color = Color(0xFF58A6FF)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = viewModel.tubesStatusMessage,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// SUB-TAB 4: GROUNDING (ITC-BT-18)
// -------------------------------------------------------------
@Composable
fun GroundingTab(viewModel: MainViewModel) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                ),
                border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Puesta a Tierra (ITC-BT-18)",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    OutlinedTextField(
                        value = viewModel.earthSoilResistivity,
                        onValueChange = {
                            viewModel.earthSoilResistivity = it
                            viewModel.runGroundingCalculation()
                        },
                        label = { Text("Resistividad del Terreno (Ω·m)") },
                        supportingText = { Text("Tierra de huerta: 50 | Arcilla: 100 | Arena/Grava: 500-1000") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = viewModel.earthPicasCount,
                        onValueChange = {
                            viewModel.earthPicasCount = it
                            viewModel.runGroundingCalculation()
                        },
                        label = { Text("Número de Picas Verticales") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = viewModel.earthPicaLengthM,
                        onValueChange = {
                            viewModel.earthPicaLengthM = it
                            viewModel.runGroundingCalculation()
                        },
                        label = { Text("Longitud de cada Pica (metros)") },
                        supportingText = { Text("Estándar: 2,0 metros enterrada a ≥ 0,5 m") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF1C222D) else Color(0xFFF1F6FE)
                ),
                border = BorderStroke(1.5.dp, Color(0xFF3FB950))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Resistencia Estimada: ${String.format("%.2f", viewModel.earthCalculatedResistanceOhms)} Ω",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 17.sp,
                        color = Color(0xFF3FB950)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = viewModel.earthStatusMessage,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun BulletPoint(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(androidx.compose.foundation.shape.CircleShape)
                .background(Color(0xFF2563EB))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ProtectionsTab(viewModel: MainViewModel) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                ),
                border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Coordinación y Protecciones (ITC-BT-22 / ITC-BT-24)",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "Selección de interruptores automáticos (PIA / IGA) y diferenciales reglamentarios.",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                    Text(
                        text = "Regla de Coordinación Conductor-Protección (ITC-BT-22):",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    BulletPoint("Ib ≤ In ≤ Iz' (La corriente de diseño debe ser menor o igual al calibre del protector, y este menor o igual a la intensidad admisible corregida del cable).")
                    BulletPoint("I2 ≤ 1.45 · Iz' (La corriente convencional de operación del dispositivo asegura la protección térmica contra sobrecargas).")

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Calibres comerciales estándar recomendados (In):",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    BulletPoint("Alumbrado / Tomas generales: 10 A, 16 A, 20 A")
                    BulletPoint("Cocinas / Hornos / Lavadoras: 20 A, 25 A")
                    BulletPoint("Acometidas y Cuadro General (IGA): 25 A, 32 A, 40 A, 50 A, 63 A")

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Sensibilidad de Interruptores Diferenciales (ITC-BT-24):",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    BulletPoint("Alta Sensibilidad (30 mA): Obligatorio para protección de personas, tomas de corriente y cuartos de baño.")
                    BulletPoint("Sensibilidad General (300 mA): Uso en cabecera para protección contra incendios por corrientes de fuga.")
                }
            }
        }
    }
}
