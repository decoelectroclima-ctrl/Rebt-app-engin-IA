package com.example.ui.screens.study

import androidx.compose.animation.*
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

data class ItcCorrelation(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color,
    val primaryItc: String,
    val pageGuide: String,
    val relatedItcs: List<String>,
    val searchKeywords: List<String>,
    val tips: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CorrelacionScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Correlaciones Clave, 1: Índice Oficial, 2: Estrategia Búsqueda
    var searchQuery by remember { mutableStateOf("") }

    val correlations = remember {
        listOf(
            ItcCorrelation(
                title = "Cálculo de Secciones e Intensidades (Caída de Tensión)",
                icon = Icons.Default.Calculate,
                color = Color(0xFF58A6FF),
                primaryItc = "ITC-BT-19 / ITC-BT-14 / ITC-BT-15",
                pageGuide = "Sección e intensidades admisibles Iz en Tabla 1 ITC-19. Caídas de tensión: LGA (0,5% local único) en ITC-14, DI (1,5% centralizado) en ITC-15, Interiores (3% luz / 5% fuerza) en ITC-19.",
                relatedItcs = listOf("ITC-BT-10 (Previsión Cargas)", "ITC-BT-14 (LGA)", "ITC-BT-15 (DI)", "ITC-BT-19 (Cálculo e Iz)", "ITC-BT-21 (Tubos)"),
                searchKeywords = listOf("caida", "seccion", "conductor", "cobre", "aluminio", "iz", "intensidad", "lga", "di", "cable", "grosor", "milimetros", "mm2", "voltaje", "tension", "amperios", "amperaje", "calentamiento", "distancia", "metros", "formula", "resistencia"),
                tips = "Si la pregunta pide sección comercial por caída de tensión e Iz, busca primero la intensidad en la fórmula, verifica Iz en la tabla ITC-19 y comprueba el tubo mínimo en ITC-21."
            ),
            ItcCorrelation(
                title = "Puesta a Tierra y Protecciones Diferenciales",
                icon = Icons.Default.Shield,
                color = Color(0xFF3FB950),
                primaryItc = "ITC-BT-18 / ITC-BT-24",
                pageGuide = "ITC-18: Electrodos, picas (mín 2m enterrada a ≥0.5m), conductor Cu desnudo (35 mm²), Cu aislado (16 mm²). ITC-24: Regla Ra · IΔn ≤ Ul (50V seco, 24V húmedo).",
                relatedItcs = listOf("ITC-BT-08 (Regímenes de Neutro TT/TN/IT)", "ITC-BT-18 (Puesta a Tierra)", "ITC-BT-24 (Contactos Directos e Indirectos)", "ITC-BT-26 (Mecanismos)"),
                searchKeywords = listOf("tierra", "pica", "picas", "jabalina", "electrodo", "resistencia", "diferencial", "id", "salto", "salta", "dispara", "disparo", "sensibilidad", "30ma", "300ma", "contacto", "indirecto", "directo", "calambre", "descarga", "seguridad", "tn", "tt", "it", "neutro", "masas", "ohm", "ohmios"),
                tips = "En esquema TT estándar de España, la protección obligatoria contra contactos indirectos SIEMPRE se realiza con interruptor diferencial de sensibilidad adecuada al valor de tierra."
            ),
            ItcCorrelation(
                title = "Cuadro Eléctrico de Vivienda (CGMP) y Circuitos",
                icon = Icons.Default.ElectricMeter,
                color = Color(0xFFBC8CFF),
                primaryItc = "ITC-BT-17 / ITC-BT-25",
                pageGuide = "ITC-17: IGA general mín 25A y 4,5 kA poder de corte; máx 5 automáticos (PIAs) por interruptor diferencial. ITC-25: Circuitos C1 al C5 (básica) y C6 al C13 (elevada).",
                relatedItcs = listOf("ITC-BT-10 (Electrificación)", "ITC-BT-17 (CGMP e IGA)", "ITC-BT-22 (Sobrecargas IB≤In≤Iz)", "ITC-BT-23 (Sobretensiones)", "ITC-BT-25 (Circuitos)"),
                searchKeywords = listOf("cgmp", "iga", "diferencial", "c1", "c2", "c3", "c4", "c5", "vivienda", "pia", "sobretensiones", "enchufe", "enchufes", "toma", "tomas", "bases", "cuadro", "automatico", "automaticos", "magnetotermico", "fusible", "fusibles", "luz", "iluminacion", "cocina", "horno", "lavadora", "termo", "aire acondicionado", "calefaccion", "potencia", "grado"),
                tips = "Pregunta clásica: ¿Puedo poner 6 circuitos bajo un diferencial? ¡NO! La ITC-17 limita tajantemente a un máximo de 5 interruptores automáticos por cada diferencial."
            ),
            ItcCorrelation(
                title = "Garajes y Aparcamientos (Proyectos e Inspecciones)",
                icon = Icons.Default.DirectionsCar,
                color = Color(0xFFF39C12),
                primaryItc = "ITC-BT-04 / ITC-BT-05 / ITC-BT-29",
                pageGuide = "ITC-04 Tabla 1: Con ventilación forzada mecánica = PROYECTO siempre (incluso 1 plaza). Con ventilación natural = PROYECTO a partir de > 5 plazas. ITC-05: Inspección periódica OCA cada 5 años si > 25 plazas.",
                relatedItcs = listOf("ITC-BT-04 (Documentación y Proyecto)", "ITC-BT-05 (Inspecciones OCA)", "ITC-BT-10 (Previsión Garajes 20 W/m²)", "ITC-BT-29 (ATEX)", "ITC-BT-52 (Cargadores VE)"),
                searchKeywords = listOf("garaje", "garajes", "aparcamiento", "parking", "plazas", "plaza", "ventilacion", "extractor", "extractores", "forzada", "natural", "proyecto", "oca", "inspeccion", "revision", "atex", "fuego", "incendios", "humos", "detectores"),
                tips = "Si el enunciado menciona 'ventilación forzada', la respuesta inmediata es PROYECTO TÉCNICO sin importar el número de plazas."
            ),
            ItcCorrelation(
                title = "Locales de Pública Concurrencia y Alumbrado Emergencia",
                icon = Icons.Default.Groups,
                color = Color(0xFFE74C3C),
                primaryItc = "ITC-BT-28 / ITC-BT-04 / ITC-BT-05",
                pageGuide = "ITC-28: Aforo > 100 personas (o cualquier teatro, cine, discoteca, centro comercial). Alumbrado de evacuación mín. 1 lux en ejes de pasillos y 5 lux en cuadros y equipos contra incendios durante 1 hora. Inspección inicial y periódica cada 5 años por OCA.",
                relatedItcs = listOf("ITC-BT-04 (Proyecto Obligatorio)", "ITC-BT-05 (Inspección Quinquenal)", "ITC-BT-28 (Pública Concurrencia)", "Cables de Alta Seguridad AS (Libres de halógenos)"),
                searchKeywords = listOf("publica", "concurrencia", "emergencia", "evacuacion", "aforo", "100", "lux", "autonomia", "oca", "local", "locales", "bar", "restaurante", "cine", "teatro", "discoteca", "comercio", "tienda", "salida", "as", "libre de halogenos", "incendios"),
                tips = "En locales de pública concurrencia es OBLIGATORIO cable no propagador del incendio y con emisión de humos y opacidad reducida (cables tipo AS)."
            ),
            ItcCorrelation(
                title = "Recarga de Vehículos Eléctricos (IRVE)",
                icon = Icons.Default.EvStation,
                color = Color(0xFF00B4D8),
                primaryItc = "ITC-BT-52 / ITC-BT-03 / ITC-BT-10",
                pageGuide = "ITC-52: Esquemas 1 (colectivo contador principal), 2 (individual contador principal), 3a/3b (vivienda unifamiliar/garaje comunitario con contador secundario), 4a/4b (sin contador exclusivo). Instalador Especialista IBTE obligatorio.",
                relatedItcs = listOf("ITC-BT-03 (Categoría Especialista IBTE)", "ITC-BT-04 (Proyecto Técnico)", "ITC-BT-10 (Previsión de Cargas)", "ITC-BT-52 (Infraestructura VE)"),
                searchKeywords = listOf("vehiculo", "electrico", "recarga", "cargador", "itc-52", "esquema 1", "esquema 2", "esquema 3", "esquema 4", "irve", "coche", "coches", "wallbox", "postes", "garaje comunitario", "especialista", "ibte"),
                tips = "La instalación de puntos de recarga de vehículos eléctricos es competencia EXCLUSIVA de empresas instaladoras de Categoría Especialista (IBTE)."
            ),
            ItcCorrelation(
                title = "Baños, Duchas y Volúmenes de Seguridad",
                icon = Icons.Default.Bathtub,
                color = Color(0xFF0077B6),
                primaryItc = "ITC-BT-27",
                pageGuide = "Volumen 0: Interior de bañera/ducha (solo MBTS ≤ 12V e IPX7). Volumen 1: Sobre bañera hasta 2,25m (solo calentadores IPX4). Volumen 2: Franja de 0,60m alrededor del vol. 1 (toma afeitadora con transformador de aislamiento). Volumen 3: Franja de 2,40m (bases protegidas por ID 30 mA).",
                relatedItcs = listOf("ITC-BT-24 (Protección Contactos)", "ITC-BT-27 (Baños y Duchas)", "ITC-BT-36 (Muy Baja Tensión MBTS)"),
                searchKeywords = listOf("bano", "banos", "ducha", "duchas", "plato", "banera", "volumen 0", "volumen 1", "volumen 2", "volumen 3", "ipx7", "ipx4", "afeitadora", "toma de afeitar", "humedad", "agua", "seguridad", "mbts", "muy baja tension", "lavabo", "lavabos", "enchufe", "toma"),
                tips = "¡Atención a las alturas!: El Volumen 1 llega hasta 2,25 m desde el fondo de la bañera. Si la ducha no tiene plato, el radio es de 1,20 m desde el punto de desagüe."
            ),
            ItcCorrelation(
                title = "Instalaciones de Enlace (CGP, LGA, Contadores y DI)",
                icon = Icons.Default.AccountTree,
                color = Color(0xFFE67E22),
                primaryItc = "ITC-BT-11 a ITC-BT-16",
                pageGuide = "Acometida -> CGP (ITC-13, fachada 0,5-2m) -> LGA (ITC-14, mín 16 mm² Cu / 25 mm² Al, caída 0,5%) -> Contadores (ITC-16, local si > 16 contadores) -> DI (ITC-15, mín 6 mm² Cu, tubo mín 32 mm, caída 1,5%).",
                relatedItcs = listOf("ITC-BT-11 (Acometidas)", "ITC-BT-12 (Esquemas)", "ITC-BT-13 (CGP)", "ITC-BT-14 (LGA)", "ITC-BT-15 (DI)", "ITC-BT-16 (Contadores)"),
                searchKeywords = listOf("enlace", "cgp", "lga", "di", "contador", "centralizacion", "derivacion", "acometida", "caja general de proteccion", "linea general de alimentacion", "contadores", "fachada", "tubo", "tubos", "seccion"),
                tips = "Para el cálculo de LGA y DI con conductor termoestable a 90°C (Poliolefina libre de halógenos), la conductividad en caliente a utilizar es γ = 44 para Cobre y γ = 28 para Aluminio."
            ),
            ItcCorrelation(
                title = "Locales Húmedos, Mojados, Cocinas y Zonas con Agua",
                icon = Icons.Default.WaterDrop,
                color = Color(0xFF00B4D8),
                primaryItc = "ITC-BT-30 / ITC-BT-25 / ITC-BT-27",
                pageGuide = "ITC-30: Prescripciones para locales húmedos, mojados, lavanderías, cocinas industriales y domésticas, lavabos. Grados de protección IP mínimos requeridos y canalizaciones protegidas frente a corrosión e humedad.",
                relatedItcs = listOf("ITC-BT-25 (Circuitos de Vivienda C3 Cocina y Horno)", "ITC-BT-27 (Baños y Duchas)", "ITC-BT-30 (Locales Húmedos y Mojados)"),
                searchKeywords = listOf("cocina", "cocinas", "lavabo", "lavabos", "zonas humedas", "humedo", "mojado", "agua", "lavanderia", "ip", "corrosion", "bano", "ducha", "c3", "horno", "fregadero", "industrial", "humedad", "enchufe", "toma"),
                tips = "En cocinas, lavabos y locales húmedos, los mecanismos e interruptores deben tener grado de protección IP adecuado y los circuitos de fuerza deben estar protegidos por interruptor diferencial de alta sensibilidad (30 mA)."
            ),
            ItcCorrelation(
                title = "Piscinas, Pediluvios y Fuentes Ornamentales",
                icon = Icons.Default.Pool,
                color = Color(0xFF023E8A),
                primaryItc = "ITC-BT-31",
                pageGuide = "Volúmenes de protección en piscinas. Uso obligatorio de MBTS (Muy Baja Tensión de Seguridad) a 12V CA o 30V CC para iluminación interior de vasos de piscina. Grado de protección mín IPX8.",
                relatedItcs = listOf("ITC-BT-24 (Protección Contactos)", "ITC-BT-31 (Piscinas y Fuentes)", "ITC-BT-36 (MBTS)"),
                searchKeywords = listOf("piscina", "piscinas", "fuente", "fuentes", "pediluvio", "vaso", "mbts", "12v", "ipx8", "sumergida", "iluminacion sumergida", "agua"),
                tips = "Pregunta de examen frecuente: La iluminación sumergida en el interior de piscinas exige obligatoriamente MBTS a 12V con transformador de seguridad situado fuera de los volúmenes 0, 1 y 2."
            )
        )
    }

    val bookBlocks = remember {
        listOf(
            "BLOQUE 1: ARTICULADO (Art. 1 al 29)" to listOf(
                "Art. 1-5" to "Objeto, Ámbito de Aplicación y Tensiones Reglamentarias",
                "Art. 6-13" to "Materiales, Conformidad CE y Redes de Distribución",
                "Art. 14-22" to "Instaladores Autorizados y Declaración Responsable",
                "Art. 23-29" to "Inspecciones Oficiales, OCAs y Régimen de Sanciones"
            ),
            "BLOQUE 2: ITCs ADMINISTRATIVAS (ITC-01 a 05)" to listOf(
                "ITC-BT-01" to "Terminología y Definiciones Oficiales",
                "ITC-BT-02" to "Normas UNE de Obligado Cumplimiento",
                "ITC-BT-03" to "Categorías Básica (IBTB) y Especialista (IBTE)",
                "ITC-BT-04" to "Documentación Técnica: Proyecto vs Memoria (MTD)",
                "ITC-BT-05" to "Verificaciones e Inspecciones Periódicas por OCA"
            ),
            "BLOQUE 3: REDES DE DISTRIBUCIÓN Y ALUMBRADO (ITC-06 a 09)" to listOf(
                "ITC-BT-06" to "Redes Aéreas de Distribución en Baja Tensión",
                "ITC-BT-07" to "Redes Subterráneas (Zanjas, Profundidades y Cruces)",
                "ITC-BT-08" to "Sistemas de Neutro y Masas (Esquemas TT, TN e IT)",
                "ITC-BT-09" to "Instalaciones de Alumbrado Exterior y Eficiencia"
            ),
            "BLOQUE 4: INSTALACIONES DE ENLACE (ITC-10 a 17)" to listOf(
                "ITC-BT-10" to "Previsión de Cargas en Edificios y Viviendas",
                "ITC-BT-11" to "Redes de Distribución y Acometidas",
                "ITC-BT-12" to "Esquemas Oficiales para Instalaciones de Enlace",
                "ITC-BT-13" to "Cajas Generales de Protección (CGP y CPM)",
                "ITC-BT-14" to "Líneas Generales de Alimentación (LGA)",
                "ITC-BT-15" to "Derivaciones Individuales (DI)",
                "ITC-BT-16" to "Contadores: Armarios y Locales Técnicos",
                "ITC-BT-17" to "Cuadros Generales de Mando y Protección (CGMP)"
            ),
            "BLOQUE 5: INSTALACIONES INTERIORES Y SEGURIDAD (ITC-18 a 27)" to listOf(
                "ITC-BT-18" to "Instalaciones de Puesta a Tierra y Electrodos",
                "ITC-BT-19" to "Prescripciones Generales y Secciones de Conductores",
                "ITC-BT-20" to "Sistemas de Instalación (Superficie, Canales, Bandejas)",
                "ITC-BT-21" to "Tubos y Canales Protectores (Diámetros Oficiales)",
                "ITC-BT-22" to "Protección contra Sobrecargas y Cortocircuitos",
                "ITC-BT-23" to "Protección contra Sobretensiones Transitorias y Permanentes",
                "ITC-BT-24" to "Protección contra Contactos Directos e Indirectos",
                "ITC-BT-25" to "Instalaciones en Viviendas (Circuitos C1 a C13)",
                "ITC-BT-26" to "Prescripciones de Montaje y Mecanismos",
                "ITC-BT-27" to "Locales con Bañera o Ducha (Volúmenes 0 a 3)"
            ),
            "BLOQUE 6: LOCALES ESPECIALES, RECEPTORES E IRVE (ITC-28 a 52)" to listOf(
                "ITC-BT-28" to "Locales de Pública Concurrencia y Emergencia",
                "ITC-BT-29" to "Locales con Riesgo de Incendio o Explosión (ATEX)",
                "ITC-BT-30" to "Locales Húmedos, Mojados y a Temperatura Extrema",
                "ITC-BT-31" to "Piscinas, Pediluvios y Fuentes Ornamentales",
                "ITC-BT-33" to "Instalaciones Provisionales y Temporales de Obras",
                "ITC-BT-38" to "Quirófanos y Salas de Intervención Médica (IT-Médico)",
                "ITC-BT-40" to "Instalaciones Generadoras de Baja Tensión (Fotovoltaica)",
                "ITC-BT-51" to "Sistemas de Domótica e Inmótica",
                "ITC-BT-52" to "Infraestructura para Recarga de Vehículos Eléctricos (IRVE)"
            )
        )
    }

    val filteredCorrelations = remember(searchQuery) {
        if (searchQuery.isBlank()) correlations
        else {
            val queryWords = searchQuery.lowercase().trim().split("\\s+".toRegex())
            correlations.filter { corr ->
                queryWords.any { word ->
                    corr.title.contains(word, ignoreCase = true) ||
                            corr.primaryItc.contains(word, ignoreCase = true) ||
                            corr.pageGuide.contains(word, ignoreCase = true) ||
                            corr.tips.contains(word, ignoreCase = true) ||
                            corr.relatedItcs.any { it.contains(word, ignoreCase = true) } ||
                            corr.searchKeywords.any { it.contains(word, ignoreCase = true) }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = null,
                            tint = Color(0xFF58A6FF),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Índice y Correlación REBT",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Guía rápida para encontrar respuestas en el libro",
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
                            viewModel.activeTab = "study"
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
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
            // Tab Selector
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
                    text = { Text("Correlaciones", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = {
                        FeedbackManager.playClick(context)
                        selectedTab = 1
                    },
                    text = { Text("Índice del Libro", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = {
                        FeedbackManager.playClick(context)
                        selectedTab = 2
                    },
                    text = { Text("Guía Examen", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
            }

            // Search Bar (Active for tabs 0 and 1)
            if (selectedTab != 2) {
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Buscar tema, artículo o ITC (ej: caída, garaje, tierra)...") },
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
                            .testTag("correlacion_search_field"),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true
                    )
                }
            }

            // Tab 0: Correlaciones Clave
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
                                containerColor = if (viewModel.isDarkTheme) Color(0xFF13231B) else Color(0xFFE6F4EA)
                            ),
                            border = BorderStroke(1.dp, Color(0xFF3FB950).copy(alpha = 0.5f)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lightbulb,
                                    contentDescription = null,
                                    tint = Color(0xFF3FB950),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Usa esta correlación para saber exactamente qué páginas e ITCs abrir en el libro durante el examen cuando leas el enunciado.",
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp,
                                    color = if (viewModel.isDarkTheme) Color(0xFF7EE787) else Color(0xFF1A7F37)
                                )
                            }
                        }
                    }

                    items(filteredCorrelations, key = { it.title }) { corr ->
                        CorrelationCard(corr = corr, isDark = viewModel.isDarkTheme)
                    }
                }
            }

            // Tab 1: Índice Estructurado del Libro
            if (selectedTab == 1) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(top = 4.dp, bottom = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    bookBlocks.forEach { (blockTitle, itemsList) ->
                        val matchingItems = if (searchQuery.isBlank()) itemsList else {
                            itemsList.filter { (code, title) ->
                                code.contains(searchQuery, ignoreCase = true) || title.contains(searchQuery, ignoreCase = true)
                            }
                        }

                        if (matchingItems.isNotEmpty()) {
                            item {
                                Text(
                                    text = blockTitle,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                                )
                            }

                            items(matchingItems, key = { it.first }) { (code, title) ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                                    ),
                                    border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))
                                ) {
                                    Row(
                                        modifier = Modifier.padding(14.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                        ) {
                                            Text(
                                                text = code,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Text(
                                            text = title,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Tab 2: Guía de Examen
            if (selectedTab == 2) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                            ),
                            border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Timer,
                                        contentDescription = null,
                                        tint = Color(0xFFF39C12),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "Regla de los 30 Segundos",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "1. Identifica la palabra clave del enunciado (ej: 'local con aforo', 'caída de tensión', 'pica vertical').\n" +
                                            "2. Dirígete inmediatamente a la ITC principal asociada sin hojear el articulado general.\n" +
                                            "3. Las tablas normativas (intensidades, tubos, factores de simultaneidad) se encuentran siempre en los anexos de cada ITC.",
                                    fontSize = 13.sp,
                                    lineHeight = 19.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
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
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.WarningAmber,
                                        contentDescription = null,
                                        tint = Color(0xFFE74C3C),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "Trampas Frecuentes en Enunciados",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "• Conductividad del Cobre: Muchos usan 56 en caliente, cuando el REBT exige 44 m/(Ω·mm²) a 90°C.\n" +
                                            "• Tierra en locales secos vs húmedos: 50 V seco, 24 V húmedo, 12 V piscina.\n" +
                                            "• Máximo de 5 automáticos por diferencial en viviendas (ITC-17).\n" +
                                            "• Garaje forzado siempre requiere PROYECTO, aunque tenga 1 sola plaza (ITC-04).",
                                    fontSize = 13.sp,
                                    lineHeight = 19.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    item {
                        Button(
                            onClick = {
                                FeedbackManager.playClick(context)
                                viewModel.activeTab = "posits"
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE67E22))
                        ) {
                            Icon(Icons.Default.StickyNote2, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Ver Mis Posits con estas Fórmulas y Trampas", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CorrelationCard(
    corr: ItcCorrelation,
    isDark: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDark) Color(0xFF161B22) else Color.White
        ),
        border = BorderStroke(1.dp, if (isDark) Color(0xFF30363D) else Color(0xFFE1E4E8))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(corr.color.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = corr.icon,
                        contentDescription = null,
                        tint = corr.color,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = corr.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = corr.primaryItc,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = corr.color
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = if (isDark) Color(0xFF0F141C) else Color(0xFFF6F8FA),
                border = BorderStroke(1.dp, if (isDark) Color(0xFF21262D) else Color(0xFFD0D7DE)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "Dónde mirar en el Libro:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = corr.pageGuide,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Related ITCs chain
            Text(
                text = "Cadena de correlación: ${corr.relatedItcs.joinToString(" ➔ ")}",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 15.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Tip
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = corr.color.copy(alpha = 0.10f)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = corr.color,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = corr.tips,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 15.sp
                    )
                }
            }
        }
    }
}
