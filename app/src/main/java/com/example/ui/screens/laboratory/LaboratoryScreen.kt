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
        if (isPremium) "Tierras ITC-18" else "Tierras ITC-18 🔒"
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

                    // Installation method
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Bajo Tubo Empotrado (vs Aire Libre)", fontSize = 13.sp)
                        Switch(
                            checked = viewModel.labInstallMethod == "tubo",
                            onCheckedChange = {
                                viewModel.labInstallMethod = if (it) "tubo" else "aire"
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
                        text = viewModel.labStatusMessage,
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
