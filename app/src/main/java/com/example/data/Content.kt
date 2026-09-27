package com.example.data

// 1. Data classes for educational content

data class Question(
    val q: String,
    val opts: List<String>,
    val a: Int, // 0-indexed correct option
    val exp: String,
    val ref: String = ""
)

data class ModuleDefinition(
    val id: String,
    val label: String,
    val icon: String,
    val color: String,
    val questions: List<Question>
)

data class UnderliningItcItem(
    val id: String,
    val code: String,
    val title: String,
    val category: String, // "Articulado", "Administrativas", "Redes", "Enlace", "Interiores"
    val freq: String, // "Baja", "Media", "Alta", "Crítica"
    val page: String,
    val redUnderline: List<String>,
    val greenUnderline: List<String>,
    val trap: String,
    val keyConcept: String,
    val examReference: String = ""
)

data class SharedDocument(
    val id: String,
    val title: String,
    val description: String,
    val fileName: String,
    val fileSize: String,
    val type: String // "BOE", "Esquema", "Calculadora"
)

// 2. Content Singleton with Complete REBT 2026 Question Catalog & Syllabi

object Content {

    val DOCUMENTS = listOf(
        SharedDocument(
            id = "boe_rebt",
            title = "BOE Reglamento Electrotécnico de Baja Tensión",
            description = "Real Decreto 842/2002 oficial completo con todas las ITCs vigentes.",
            fileName = "BOE_REBT_Completo_2026.pdf",
            fileSize = "4.2 MB",
            type = "BOE"
        ),
        SharedDocument(
            id = "esquema_cgmp",
            title = "Esquema Unifilar General Vivienda",
            description = "Guía unifilar de representación técnica obligatoria para cuadros CGMP (C1 a C13).",
            fileName = "Esquema_Unifilar_CGMP_REBT.pdf",
            fileSize = "1.8 MB",
            type = "Esquema"
        ),
        SharedDocument(
            id = "tabla_itc_21",
            title = "Prontuario Diámetros de Tubos ITC-BT-21",
            description = "Fórmula rápida e interpolación de diámetros reglamentarios según hilos empotrados.",
            fileName = "Prontuario_Tubos_ITC_21.pdf",
            fileSize = "820 KB",
            type = "Calculadora"
        ),
        SharedDocument(
            id = "esquema_tierras",
            title = "Detalle Constructivo Puesta a Tierra",
            description = "Esquema báculo de farolas y electrodos verticales con desconectador rápido.",
            fileName = "Esquema_Puesta_Tierra_BT.pdf",
            fileSize = "1.1 MB",
            type = "Esquema"
        )
    )

    // Curated Syllabus items with high-value technical highlights
    val SYLLABUS = listOf(
        UnderliningItcItem(
            id = "art-1-5",
            code = "Artículos 1 al 5",
            title = "Objeto, Ámbito y Tensiones reglamentarias",
            category = "Articulado",
            freq = "Crítica",
            page = "Art. 1-5",
            redUnderline = listOf(
                "Aplicación obligatoria a instalaciones de corriente alterna ≤ 1.000 V eficaces y corriente continua ≤ 1.500 V.",
                "Modificación de importancia: Afecta a más del 50% de la potencia instalada de la instalación original.",
                "Muy Baja Tensión (MBT): ≤ 50 V eficaces en CA y ≤ 75 V en CC."
            ),
            greenUnderline = listOf(
                "Tensiones nominales estándar en CA trifásica: 230 V entre fases y 400 V entre fases para redes tetrapolares.",
                "Frecuencia nominal de red en España: 50 Hz con tolerancia rigurosa de ±1%."
            ),
            trap = "¡Ojo al límite de MBT en corriente continua! No es 50 V como en CA, sino 75 V de valor medio. Suele salir en test como pregunta trampa.",
            keyConcept = "CA ≤ 1.000 V | CC ≤ 1.500 V. Modificación de importancia > 50% potencia. MBT CA ≤ 50V / CC ≤ 75V.",
            examReference = "Art. 2 y Art. 4 RD 842/2002"
        ),
        UnderliningItcItem(
            id = "itc-03",
            code = "ITC-BT-03",
            title = "Instaladores Autorizados en Baja Tensión",
            category = "Administrativas",
            freq = "Crítica",
            page = "ITC-03",
            redUnderline = listOf(
                "Categoría Básica (IBTB): Edificios de viviendas, comerciales y oficinas sin características especiales.",
                "Categoría Especialista (IBTE): Exclusiva para ATEX, quirófanos, vehículos eléctricos, fotovoltaica conectada, líneas de distribución y alumbrado exterior.",
                "Seguro de Responsabilidad Civil mínimo obligatorio según categoría y personal técnico competente contratado a jornada completa."
            ),
            greenUnderline = listOf(
                "Equipos mínimos obligatorios: Telurómetro de tierra, medidor de aislamiento de 500V/1000V, verificador de interruptores diferenciales tipo AC y A, y analizador de redes.",
                "La declaración responsable habilita por tiempo indefinido y con validez para todo el territorio nacional."
            ),
            trap = "Un instalador básico IBTB NO puede certificar cargadores de vehículos eléctricos (ITC-52) ni fotovoltaica conectada a red. Es competencia exclusiva de IBTE Especialista.",
            keyConcept = "IBTB (Básica) vs IBTE (Especialista). Declaración responsable indefinida y nacional. Instrumental de medida calibrado obligatorio.",
            examReference = "ITC-BT-03 §3 y RD 560/2010"
        ),
        UnderliningItcItem(
            id = "itc-04",
            code = "ITC-BT-04",
            title = "Documentación Técnica y Proyecto",
            category = "Administrativas",
            freq = "Crítica",
            page = "ITC-04",
            redUnderline = listOf(
                "Requiere Proyecto Técnico visado: Viviendas unifamiliares > 50 kW, edificios de viviendas > 100 kW.",
                "Aparcamientos con ventilación forzada de cualquier plaza o ventilación natural > 5 plazas.",
                "Locales de pública concurrencia (teatros, cines, discotecas, centros comerciales, restaurantes > 100 personas).",
                "Instalaciones temporales de obras o ferias > 50 kW."
            ),
            greenUnderline = listOf(
                "Memoria Técnica de Diseño (MTD): Realizada y firmada directamente por el instalador autorizado para instalaciones por debajo de los umbrales de proyecto.",
                "El Certificado de Instalación Eléctrica (CIE / Boletín) debe tramitarse y registrarse ante el Órgano competente de la Comunidad Autónoma."
            ),
            trap = "Cuidado con los garajes: si tiene ventilación forzada, REQUIERE PROYECTO siempre, aunque tenga solo 1 plaza. Si es ventilación natural, solo a partir de más de 5 plazas.",
            keyConcept = "Proyecto: Edificios > 100 kW, viviendas unifamiliares > 50 kW, garajes forzados (todos) y naturales > 5 plazas, pública concurrencia.",
            examReference = "ITC-BT-04 Tabla 1"
        ),
        UnderliningItcItem(
            id = "itc-10",
            code = "ITC-BT-10",
            title = "Previsión de Cargas y Electrificación",
            category = "Enlace",
            freq = "Crítica",
            page = "ITC-10",
            redUnderline = listOf(
                "Previsión Potencia Electrificación Básica: Mínimo 5.750 W (a 230 V con IGA de 25 A).",
                "Previsión Potencia Electrificación Elevada: Mínimo 9.200 W (a 230 V con IGA de 40 A).",
                "Electrificación Elevada obligatoria si: Superficie útil > 160 m², calefacción eléctrica, aire acondicionado instalado, o piscina."
            ),
            greenUnderline = listOf(
                "Fórmula de simultaneidad para comunidades de vecinos en escaleras de viviendas para n > 21: Cs = 15,3 + (n - 21) * 0.5.",
                "Servicios generales (ascensores, hidropresores) se sumarán sin aplicar ningún factor reductor de simultaneidad (Cs = 1,0)."
            ),
            trap = "En el examen te pedirán calcular la previsión de carga de una comunidad de vecinos de 25 viviendas. Muchos usan erróneamente la tabla de viviendas simples. Debes aplicar la fórmula obligatoria para n > 21 que da un Cs = 17,3.",
            keyConcept = "Previsión básica = 5750 W (IGA 25A). Elevada = 9200 W (IGA 40A). Locales = 100 W/m² (mín. 3.450 W).",
            examReference = "ITC-BT-10 §2 y §3"
        ),
        UnderliningItcItem(
            id = "itc-14",
            code = "ITC-BT-14",
            title = "Líneas Generales de Alimentación (LGA)",
            category = "Enlace",
            freq = "Crítica",
            page = "ITC-14",
            redUnderline = listOf(
                "Sección mínima de cable de Cobre para LGA: 16 mm².",
                "Sección mínima de cable de Aluminio para LGA: 25 mm².",
                "Caída de tensión máxima admisible para LGA con contadores concentrados en un único local: 0,5%.",
                "Caída de tensión máxima si se tienen contadores agrupados en múltiples armarios: 1,0%."
            ),
            greenUnderline = listOf(
                "Requisito obligatorio de utilizar conductores que cumplan la designación AS (Alta Seguridad), con aislamiento libre de halógenos termoplásticos o termoestables.",
                "La sección útil del tubo de protección mecánica para el paso del haz cableado de LGA debe ser de un mínimo de 3 veces superior a la global de cables."
            ),
            trap = "Fórmula de LGA: Para LGA con conductor termoestable de Poliolefina a 90°C, la conductividad en caliente a aplicar para el cálculo de caída de tensión es Cobre = 44 y Al = 28. No utilices el valor estándar de 56.",
            keyConcept = "LGA sections: Cu min 16 mm² / Al min 25 mm². Caída de tensión = 0,5% local único o 1,0% armarios.",
            examReference = "ITC-BT-14 §1 y §3"
        ),
        UnderliningItcItem(
            id = "itc-15",
            code = "ITC-BT-15",
            title = "Derivaciones Individuales (DI)",
            category = "Enlace",
            freq = "Crítica",
            page = "ITC-15",
            redUnderline = listOf(
                "Sección mínima de conductores en DI: 6 mm² de cobre.",
                "Diámetro exterior mínimo del tubo protector de la DI: 32 mm.",
                "Caída de tensión máxima: 1,5% para contadores totalmente concentrados en un único local.",
                "Caída de tensión máxima: 0,5% para contadores totalmente individuales o no concentrados."
            ),
            greenUnderline = listOf(
                "Obligatoriedad de incluir el hilo de mando de color rojo de 1,5 mm² para control de tarifas (si procede).",
                "Conductores no propagadores del incendio y con emisión de humos y opacidad reducida (cables tipo AS)."
            ),
            trap = "¡Ojo al valor de caída de tensión de la Derivación Individual! Si los contadores están en un local centralizado, es 1,5%. Si están individualizados (ej. viviendas aisladas), es solo 0,5% o 1%.",
            keyConcept = "DI: Sección mínima 6 mm² Cu. Tubo protector mínimo 32 mm. Caída máx 1,5% (centralizado).",
            examReference = "ITC-BT-15 §2 y §3"
        ),
        UnderliningItcItem(
            id = "itc-17",
            code = "ITC-BT-17",
            title = "Cuadro de Mando y Protección (CGMP)",
            category = "Enlace",
            freq = "Crítica",
            page = "ITC-17",
            redUnderline = listOf(
                "El Interruptor General Automático (IGA) obligatorio tendrá una intensidad asignada mínima de 25 A.",
                "Poder de corte mínimo del IGA: 4.500 A (4,5 kA) de cortocircuito mecánico.",
                "Se prohíbe asociar más de 5 circuitos o interruptores magnetotérmicos pequeños (PIAs) bajo el amparo de un único interruptor diferencial.",
                "La instalación de limitador de sobretensiones (permanentes y transitorias) es obligatoria de forma unificada."
            ),
            greenUnderline = listOf(
                "El cuadro eléctrico general CGMP de la vivienda debe instalarse junto a la puerta de acceso, a rango de altura entre 1,40m y 2,00m, y nunca en zonas húmedas o baños."
            ),
            trap = "El examinador querrá saber si se puede instalar un único diferencial general de 30 mA para proteger 6 circuitos (ej: C1, C2, C3, C4, C5 y C6). Falso: al ser 6, debes obligatoriamente montar un segundo diferencial independiente para dividir cargas.",
            keyConcept = "IGA = mín. 25A y 4,5 kA. Máximo 5 PIAs por diferencial general. Surge protector obligatorio.",
            examReference = "ITC-BT-17 §1"
        ),
        UnderliningItcItem(
            id = "itc-18",
            code = "ITC-BT-18",
            title = "Instalaciones de Puesta a Tierra",
            category = "Interiores",
            freq = "Crítica",
            page = "ITC-18",
            redUnderline = listOf(
                "Profundidad mínima de enterramiento de electrodo o pica de tierra: 0,50 metros bajo el terreno.",
                "Sección mínima de cable de Cobre desnudo enterrado directo: 35 mm².",
                "Sección mínima de cable de Cobre con aislamiento protector mecánicamente resistente enterrado: 16 mm².",
                "Tensión de contacto límite: 50 V en locales secos, 24 V en locales húmedos."
            ),
            greenUnderline = listOf(
                "Queda terminantemente prohibido utilizar como toma de tierra tuberías de servicios públicos (agua, gas, calefacción urbana).",
                "Obligatoriedad de intercalar un borne o interruptor de desconexión rápida (seccionador de tierra) para efectuar comprobaciones anuales oficiales de resistividad."
            ),
            trap = "Mezclan el valor de sección de cobre desnudo enterrado sin funda (35 mm²) con el de cobre aislado (16 mm²). El desnudo requiere mayor sección porque no tiene protección frente a la corrosión del terreno ácido.",
            keyConcept = "Profundidad pica = 0.50m. Cobre desnudo tierra = 35 y cobre aislado = 16 mm². Contacto: 50V seco / 24V húmedo.",
            examReference = "ITC-BT-18 §3 y Tabla 1"
        ),
        UnderliningItcItem(
            id = "itc-21",
            code = "ITC-BT-21",
            title = "Tubos y Canales Protectores",
            category = "Interiores",
            freq = "Crítica",
            page = "ITC-21",
            redUnderline = listOf(
                "3 hilos de 1,5 mm² empotrados: Tubo mínimo exterior 16 mm.",
                "3 hilos de 2,5 mm² empotrados: Tubo mínimo exterior 20 mm.",
                "3 hilos de 6 mm² empotrados: Tubo mínimo exterior 25 mm.",
                "Derivación individual: Tubo mínimo exterior 32 mm."
            ),
            greenUnderline = listOf(
                "Queda terminantemente prohibido realizar empalmes de conductores dentro de los tubos protectores. Todo empalme debe ir en caja de derivación registrada.",
                "Los tubos empotrados deben trazarse siguiendo líneas verticales y horizontales a distancias seguras de esquinas y huecos de puertas."
            ),
            trap = "Preguntan por el diámetro de tubo empotrado para 3 conductores de 2,5 mm². La tabla de la ITC-21 fija 20 mm de diámetro exterior para tubo curvable o flexible empotrado.",
            keyConcept = "Tubo empotrado: 3x1.5=16mm | 3x2.5=20mm | 3x6=25mm | DI=32mm. Sin empalmes en tubos.",
            examReference = "ITC-BT-21 Tabla 5"
        ),
        UnderliningItcItem(
            id = "itc-25",
            code = "ITC-BT-25",
            title = "Circuitos Interiores de Vivienda",
            category = "Interiores",
            freq = "Crítica",
            page = "ITC-25",
            redUnderline = listOf(
                "Circuito C1: Alumbrado general. Fusible de 10A, cable de sección 1,5 mm². Máximo de 30 puntos de luz.",
                "Circuito C2: Tomas de corriente generales y nevera. Fusible de 16A, cable de sección 2,5 mm². Máximo de 20 bases de enchufe.",
                "Circuito C3: Cocina y Horno. Fusible de 25A, cable de sección 6 mm² único para tomas de potencia.",
                "Circuito C4: Lavadora, Lavavajillas y Termo eléctrico. Fusible de 20A, cable de sección 4 mm² (o desdoblado en 3 con PIA de 16A y 2,5 mm²).",
                "Circuito C5: Tomas de cocina y baños. Fusible de 16A, cable de sección 2,5 mm². Máximo de 6 bases de enchufe."
            ),
            greenUnderline = listOf(
                "Circuito C12: Instalación adicional para recarga de vehículo eléctrico (ITC-52) o circuitos adicionales C8/C9/C10 en electrificación elevada.",
                "Tendido de cables de diferentes circuitos dentro del mismo conducto está prohibido a menos que todos compartan el máximo aislamiento dieléctrico."
            ),
            trap = "¡Ojo al desdoblar el C4! Si eliges desdoblarlo en 3 ramales, se deben colocar 3 magnetotérmicos de 16 A independientes para lavadora, lavavajillas y termo, reduciendo la sección a 2,5 mm² bajo tubos individuales. No puedes dejar un único PIA de 20 A con ramales de 2,5 mm².",
            keyConcept = "C1 = 10A (1,5mm²). C2 = 16A (2,5mm²). C3 = 25A (6mm²). C4 = 20A (4mm²). C5 = 16A (2,5mm² - máx 6 tomas).",
            examReference = "ITC-BT-25 Tabla 1"
        ),
        UnderliningItcItem(
            id = "itc-27",
            code = "ITC-BT-27",
            title = "Locales con Bañera o Ducha (Volúmenes)",
            category = "Interiores",
            freq = "Crítica",
            page = "ITC-27",
            redUnderline = listOf(
                "Volumen 0: Interior de la bañera o plato de ducha. Prohibido instalar mecanismos o tomas. Solo aparatos IPX7 a MBTS ≤ 12V CA.",
                "Volumen 1: Encima de la bañera hasta 2,25 m de altura. Prohibidos interruptores y tomas. Solo calentadores de agua fijos IPX4.",
                "Volumen 2: Franja de 0,60 m alrededor del volumen 1. Solo tomas de afeitadora con transformador de aislamiento y luminarias fijas IPX4.",
                "Volumen 3: Franja de 2,40 m a partir del volumen 2. Tomas autorizadas si están protegidas por diferencial de 30 mA o MBTS."
            ),
            greenUnderline = listOf(
                "Conductores de unión equipotencial suplementaria obligatorios para interconectar tuberías metálicas de agua fría, caliente, desagües y marcos metálicos."
            ),
            trap = "¿Se puede poner un interruptor unipolar en el volumen 1 a 2 metros de altura? ¡NO! En volumen 0 y 1 queda terminantemente prohibido cualquier mecanismo de mando o toma de corriente.",
            keyConcept = "Volumen 0 (IPX7, MBTS 12V). Volumen 1 (IPX4, sin tomas ni interruptores). Volumen 2 (0.6m franja). Volumen 3 (2.4m con ID 30mA).",
            examReference = "ITC-BT-27 §2"
        ),
        UnderliningItcItem(
            id = "itc-28",
            code = "ITC-BT-28",
            title = "Locales de Pública Concurrencia (LPC)",
            category = "Interiores",
            freq = "Crítica",
            page = "ITC-28",
            redUnderline = listOf(
                "Conductores no propagadores del incendio y de reducida emisión de humos (Clase Cca-s1b,d1,a1 / Tipo AS libre de halógenos).",
                "Alumbrado de seguridad y evacuación obligatorio con autonomía mínima de 1 hora.",
                "Nivel mínimo de iluminación de seguridad en el eje de los pasos de evacuación: 1 lux a nivel de suelo y 5 lux en puestos de primeros auxilios y cuadros de mando.",
                "Suministro de socorro obligatorio que garantice al menos el 15% de la potencia contratada para servicios esenciales de seguridad."
            ),
            greenUnderline = listOf(
                "Aforo reglamentario: Es local de pública concurrencia todo local de espectáculos públicos o con ocupación prevista superior a 50 personas (teatros, cines) o 100 personas (bares, restaurantes)."
            ),
            trap = "¿Qué cables se exigen en locales de pública concurrencia? Cables libres de halógenos tipo AS no propagadores del incendio (reacción al fuego Cca-s1b,d1,a1). El cable convencional de PVC (H07V-K) está totalmente prohibido.",
            keyConcept = "Cables AS libres de halógenos obligatorios. Alumbrado de emergencia: 1 lux general, 5 lux en cuadros, 1h autonomía. Suministro socorro 15%.",
            examReference = "ITC-BT-28 §2 y §4"
        ),
        UnderliningItcItem(
            id = "itc-52",
            code = "ITC-BT-52",
            title = "Recarga de Vehículos Eléctricos (IRVE)",
            category = "Enlace",
            freq = "Crítica",
            page = "ITC-52",
            redUnderline = listOf(
                "Instalación de recarga de vehículos eléctricos reservada exclusivamente a Instalador de Categoría Especialista (IBTE).",
                "Protección diferencial obligatoria: Tipo A de 30 mA como mínimo (para detectar componentes pulsantes y fugas en continua).",
                "Esquema 2: Troncal colectiva con contadores principales centralizados y contadores secundarios modulares en plazas de garaje.",
                "Sección mínima de cable para punto de recarga: 2,5 mm² (mando/fuerza básica) o recomendada 4 mm² para minimizar caídas térmicas continuas."
            ),
            greenUnderline = listOf(
                "Caída de tensión máxima admisible para el circuito terminal del punto de recarga: 5% a intensidad máxima de régimen continuo.",
                "Obligatoriedad de incluir dispositivo de corte o rearme automático y protección contra sobretensiones transitorias y permanentes."
            ),
            trap = "¿Sirve un diferencial estándar Tipo AC para una toma de vehículo eléctrico? ¡NO! La norma ITC-BT-52 exige diferencial Clase A o Clase B porque las baterías generan componentes continuas que ciegan a los Tipo AC.",
            keyConcept = "IBTE obligatorio. Diferencial Tipo A 30mA. Esquemas 1 a 4. Caída máx 5%. Sobretensiones obligatorias.",
            examReference = "ITC-BT-52 §5 y §6"
        )
    )

    // Complete, Comprehensive Question Pool mapped by Modules
    val QUESTIONS = mapOf(
        "articulado" to ModuleDefinition(
            id = "articulado",
            label = "Articulado REBT (Art. 1-29)",
            icon = "⚡",
            color = "#bc8cff",
            questions = listOf(
                Question(
                    q = "¿Cuál es el rango de tensión que define la Baja Tensión según el REBT?",
                    opts = listOf(
                        "Hasta 500V en corriente alterna y 750V en corriente continua",
                        "Hasta 1.000V en corriente alterna y 1.500V en corriente continua",
                        "Hasta 1.500V en corriente alterna y 2.000V en corriente continua",
                        "Hasta 2.000V en corriente alterna y 3.000V en corriente continua"
                    ),
                    a = 1,
                    exp = "El Reglamento Electrotécnico de Baja Tensión (REBT 2002) define en su Artículo 1 que la Baja Tensión comprende las instalaciones de corriente alterna con tensiones nominales hasta 1.000V eficaces y las de corriente continua hasta 1.500V. Esta definición es esencial porque determina el alcance del REBT.",
                    ref = "Art. 1, REBT 2002"
                ),
                Question(
                    q = "¿Cuál es la frecuencia nominal de la red eléctrica en España?",
                    opts = listOf(
                        "50 Hz con tolerancia de ±2%",
                        "50 Hz con tolerancia de ±1%",
                        "60 Hz con tolerancia de ±0.5%",
                        "50 Hz con tolerancia de ±0.5%"
                    ),
                    a = 1,
                    exp = "La frecuencia nominal de la red eléctrica en España, como en la mayoría de Europa, es de 50 Hz. Según el REBT y las normas europeas, la tolerancia permitida es de ±1%. Muchos equipos están diseñados para 50 Hz exactamente, por lo que esta desviación es crítica para el funcionamiento correcto de motores y transformadores.",
                    ref = "Art. 2, REBT 2002"
                ),
                Question(
                    q = "¿Cuál es la tensión nominal entre fases de una red trifásica de 400V?",
                    opts = listOf(
                        "230V entre fases",
                        "400V entre fases",
                        "690V entre fases",
                        "1000V entre fases"
                    ),
                    a = 1,
                    exp = "En un sistema trifásico con tensión nominal de 400V, esta es precisamente la tensión entre fases (tensión de línea). Esta es la tensión estándar en España para el suministro trifásico a comercios, industrias y algunas viviendas. La relación entre tensión de fase (fase-neutro) y de línea (fase-fase) es √3.",
                    ref = "Art. 3, REBT 2002"
                ),
                Question(
                    q = "¿Qué se entiende por Muy Baja Tensión (MBT) según el REBT?",
                    opts = listOf(
                        "Tensiones hasta 50V en corriente alterna y 75V en corriente continua",
                        "Tensiones hasta 100V en corriente alterna y 150V en corriente continua",
                        "Tensiones hasta 200V en corriente alterna y 300V en corriente continua",
                        "Tensiones hasta 25V en corriente alterna y 50V en corriente continua"
                    ),
                    a = 0,
                    exp = "La Muy Baja Tensión (MBT) comprende las instalaciones de corriente alterna con tensiones nominales hasta 50V eficaces y las de corriente continua hasta 75V. Esta categoría es importante porque requiere menos protecciones al ser considerada segura ante el contacto directo.",
                    ref = "Art. 4, REBT 2002"
                ),
                Question(
                    q = "¿Cuándo se considera que una instalación ha sido modificada según el REBT?",
                    opts = listOf(
                        "Cualquier reforma o cambio de equipos",
                        "Cuando afecta más del 50% de la potencia instalada",
                        "Cuando se añaden más de 10 circuitos",
                        "Cuando se cambia el interruptor general"
                    ),
                    a = 1,
                    exp = "Una instalación se considera modificada cuando el cambio o reforma afecta a más del 50% de la potencia instalada de la instalación original. Esta es una definición crítica porque determina si requiere trámites administrativos, como nuevo Certificado de Instalación y verificación por OCA.",
                    ref = "Art. 18, REBT 2002"
                ),
                Question(
                    q = "¿A partir de qué potencia una instalación de baja tensión industrial precisa proyecto técnico según ITC-BT-04?",
                    opts = listOf("10 kW", "20 kW", "50 kW", "100 kW"),
                    a = 2,
                    exp = "Según la ITC-BT-04 Tabla 1, los locales industriales precisan proyecto técnico cuando la potencia instalada supera los 50 kW.",
                    ref = "ITC-BT-04"
                ),
                Question(
                    q = "¿Es necesario el REBT para instalaciones de tracción eléctrica ferroviaria?",
                    opts = listOf("Sí, siempre", "No, tienen reglamentación propia", "Sí, si están fuera del túnel", "Solo si la tensión es superior a 1000V"),
                    a = 1,
                    exp = "El Art. 2.2 del REBT excluye explícitamente de su ámbito de aplicación a las instalaciones de tracción eléctrica, que poseen su propio reglamento específico.",
                    ref = "Art. 2"
                ),
                Question(
                    q = "¿Qué clase de aislamiento deben tener los cables para ser usados en locales de pública concurrencia?",
                    opts = listOf("PVC estándar", "Cable AS libre de halógenos", "Cable de goma simple", "Cable de plomo"),
                    a = 1,
                    exp = "La ITC-BT-28 exige en locales de pública concurrencia el uso de conductores no propagadores del incendio y de reducida emisión de humos y opacidad (Cables Tipo AS, libres de halógenos).",
                    ref = "ITC-BT-28"
                ),
                Question(
                    q = "¿Qué tensión entre Fase y Neutro se considera estándar para el suministro monofásico en España?",
                    opts = listOf("127V", "230V", "380V", "400V"),
                    a = 1,
                    exp = "El Art. 3 del REBT unifica las tensiones de suministro estándar a 230V entre fase y neutro para redes monofásicas y 400V entre fases para redes trifásicas.",
                    ref = "Art. 3"
                ),
                Question(
                    q = "¿Ante quién se registra el Certificado de Instalación Eléctrica (CIE)?",
                    opts = listOf("Ayuntamiento", "Órgano competente de la CC.AA.", "Ministerio de Defensa", "Registro de la propiedad"),
                    a = 1,
                    exp = "El Art. 18.3 indica que el Certificado de Instalación Eléctrica (CIE o Boletín) se tramitará y registrará ante el órgano competente de la Comunidad Autónoma correspondiente.",
                    ref = "Art. 18"
                )
            )
        ),
        "empresas" to ModuleDefinition(
            id = "empresas",
            label = "Instaladores y Documentación (ITC-03/04/05)",
            icon = "🏢",
            color = "#f85149",
            questions = listOf(
                Question(
                    q = "¿Puede un instalador de Categoría Básica (IBTB) realizar una instalación fotovoltaica conectada a red de 15 kW?",
                    opts = listOf(
                        "Sí, cualquier potencia en baja tensión",
                        "Sí, si tiene experiencia demostrable de más de 3 años",
                        "No, las instalaciones generadoras de BT corresponden a Categoría Especialista (IBTE)",
                        "Solo si subcontrata la memoria técnica con un ingeniero"
                    ),
                    a = 2,
                    exp = "La ITC-BT-03 par. 3.2 reserva las instalaciones generadoras de baja tensión de forma explícita a los instaladores habilitados en la Categoría Especialista (IBTE).",
                    ref = "ITC-BT-03 §3.2"
                ),
                Question(
                    q = "¿Qué categoría de instalador es obligatoria para legalizar una instalación eléctrica en un quirófano o sala de anestesia?",
                    opts = listOf(
                        "Categoría Básica (IBTB)",
                        "Categoría Especialista (IBTE)",
                        "Cualquier instalador autorizado con seguro médico",
                        "Únicamente empresas fabricantes de aparatos médicos"
                    ),
                    a = 1,
                    exp = "Las instalaciones en locales con características especiales (quirófanos, salas de intervención y salas de anestesia) exigen la habilitación en Categoría Especialista (IBTE).",
                    ref = "ITC-BT-03 §3.2"
                ),
                Question(
                    q = "Un aparcamiento de 8 plazas de estacionamiento con ventilación NATURAL, ¿requiere la redacción de Proyecto Técnico visado?",
                    opts = listOf(
                        "No, los aparcamientos con ventilación natural nunca precisan proyecto",
                        "Sí, porque supera el umbral reglamentario de 5 plazas",
                        "Solo si supera una potencia de 50 kW",
                        "No, el umbral de proyecto para garajes es de más de 10 plazas"
                    ),
                    a = 1,
                    exp = "La ITC-BT-04 Grupo h establece que los aparcamientos con ventilación natural precisan proyecto cuando disponen de más de 5 plazas de estacionamiento.",
                    ref = "ITC-BT-04 Tabla 1"
                ),
                Question(
                    q = "Un aparcamiento de 3 plazas con ventilación FORZADA mecánica, ¿requiere Proyecto Técnico?",
                    opts = listOf(
                        "No, tiene menos de 5 plazas",
                        "Sí, cualquier aparcamiento con ventilación forzada requiere proyecto sin importar el número de plazas",
                        "Solo si el extractor supera los 5 kW",
                        "Requiere únicamente Memoria Técnica de Diseño (MTD)"
                    ),
                    a = 1,
                    exp = "La ITC-BT-04 Grupo h dictamina que todo aparcamiento con ventilación forzada requiere proyecto técnico, cualquiera que sea su número de plazas o potencia.",
                    ref = "ITC-BT-04 Tabla 1"
                ),
                Question(
                    q = "¿Cuál es la potencia límite a partir de la cual un edificio destinado predominantemente a viviendas precisa Proyecto Técnico?",
                    opts = listOf(
                        "50 kW",
                        "100 kW",
                        "150 kW",
                        "200 kW"
                    ),
                    a = 1,
                    exp = "La ITC-BT-04 Grupo a establece que los edificios destinados principalmente a viviendas precisan proyecto cuando la potencia prevista total supera los 100 kW.",
                    ref = "ITC-BT-04 Tabla 1"
                ),
                Question(
                    q = "¿A partir de qué potencia una vivienda unifamiliar individual precisa Proyecto Técnico?",
                    opts = listOf(
                        "15 kW",
                        "25 kW",
                        "50 kW",
                        "100 kW"
                    ),
                    a = 2,
                    exp = "La ITC-BT-04 clasifica que las viviendas unifamiliares individuales precisan proyecto técnico cuando su potencia prevista exceda los 50 kW.",
                    ref = "ITC-BT-04 Tabla 1"
                ),
                Question(
                    q = "¿Cuál de los siguientes instrumentos de medida es obligatorio tanto para la Categoría Básica (IBTB) como para la Especialista (IBTE)?",
                    opts = listOf(
                        "Cámara termográfica infrarroja",
                        "Telurómetro de medida de resistencia de puesta a tierra",
                        "Luxómetro de precisión tipo 1",
                        "Osciloscopio digital con memoria"
                    ),
                    a = 1,
                    exp = "La ITC-BT-03 exige como dotación mínima obligatoria para todo instalador autorizado un telurómetro para comprobar la resistencia de los electrodos de tierra.",
                    ref = "ITC-BT-03 Apéndice 1"
                ),
                Question(
                    q = "¿Con qué tensión de prueba debe verificar el instalador la resistencia de aislamiento de una instalación de tensión nominal ≤ 500 V?",
                    opts = listOf(
                        "250 V en corriente continua",
                        "500 V en corriente continua",
                        "1.000 V en corriente continua",
                        "230 V en corriente alterna"
                    ),
                    a = 1,
                    exp = "La ITC-BT-19 e ITC-BT-03 fijan que para circuitos con tensión nominal inferior o igual a 500 V, la tensión continua de ensayo de aislamiento debe ser de 500 V CC.",
                    ref = "ITC-BT-19 §5.2"
                ),
                Question(
                    q = "¿Qué periodicidad de inspección periódica por Organismo de Control (OCA) tienen los locales de pública concurrencia?",
                    opts = listOf(
                        "Cada 2 años",
                        "Cada 5 años",
                        "Cada 10 años",
                        "Solo cuando cambie la titularidad del negocio"
                    ),
                    a = 1,
                    exp = "La ITC-BT-05 par. 4.2 estipula que los locales de pública concurrencia deben someterse a inspección periódica por una OCA cada 5 años.",
                    ref = "ITC-BT-05 §4.2"
                ),
                Question(
                    q = "¿Cada cuántos años deben pasar inspección periódica las instalaciones comunes de edificios de viviendas con potencia total instalada > 100 kW?",
                    opts = listOf(
                        "Cada 5 años",
                        "Cada 10 años",
                        "Cada 15 años",
                        "Están exentas de inspecciones periódicas"
                    ),
                    a = 1,
                    exp = "La ITC-BT-05 fija una periodicidad de 10 años para la inspección periódica de las instalaciones comunes en edificios de viviendas de potencia > 100 kW.",
                    ref = "ITC-BT-05 §4.2"
                ),
                Question(
                    q = "En una inspección de una OCA, la detección de un defecto 'Grave' implica que:",
                    opts = listOf(
                        "La instalación se precinta en el mismo acto",
                        "Se concede un plazo máximo de 6 meses para su subsanación antes de una nueva visita",
                        "No se emite ningún certificado pero se puede seguir operando indefinidamente",
                        "El titular recibe una sanción económica automática pero no tiene que repararlo"
                    ),
                    a = 1,
                    exp = "La calificación de defecto Grave no impide provisionalmente el servicio pero obliga a subsanarlo en un plazo máximo de 6 meses para obtener la calificación favorable.",
                    ref = "ITC-BT-05 §5"
                ),
                Question(
                    q = "¿Cuál de las siguientes instalaciones requiere inspección inicial por OCA antes de su puesta en servicio oficial?",
                    opts = listOf(
                        "Una vivienda unifamiliar de electrificación básica (5.750 W)",
                        "Un local de pública concurrencia (teatro, bar de copas con aforo > 100 personas)",
                        "Una oficina comercial de 40 m²",
                        "Un garaje de 4 plazas con ventilación natural"
                    ),
                    a = 1,
                    exp = "La ITC-BT-05 par. 4.1 exige inspección inicial obligatoria por OCA a locales de pública concurrencia, quirófanos, ATEX y garajes > 25 plazas.",
                    ref = "ITC-BT-05 §4.1"
                )
            )
        ),
        "enlace" to ModuleDefinition(
            id = "enlace",
            label = "Enlace, Previsión y CGMP (ITC-10 a 17)",
            icon = "🔌",
            color = "#58a6ff",
            questions = listOf(
                Question(
                    q = "La potencia mínima reglamentaria a prever para una vivienda con electrificación BÁSICA es:",
                    opts = listOf(
                        "3.450 W a 230 V (IGA 15 A)",
                        "4.600 W a 230 V (IGA 20 A)",
                        "5.750 W a 230 V (IGA 25 A)",
                        "9.200 W a 230 V (IGA 40 A)"
                    ),
                    a = 2,
                    exp = "La ITC-BT-10 par. 2.1 establece que la previsión mínima para electrificación básica será de 5.750 W a 230 V con un interruptor general automático (IGA) de 25 A.",
                    ref = "ITC-BT-10 §2.1"
                ),
                Question(
                    q = "La potencia mínima a prever para una vivienda con electrificación ELEVADA es de:",
                    opts = listOf(
                        "5.750 W a 230 V",
                        "7.360 W a 230 V",
                        "9.200 W a 230 V (IGA 40 A)",
                        "11.500 W a 230 V"
                    ),
                    a = 2,
                    exp = "Conforme a la ITC-BT-10 par. 2.2, la potencia mínima reglamentaria para electrificación elevada es de 9.200 W con un IGA de 40 A.",
                    ref = "ITC-BT-10 §2.2"
                ),
                Question(
                    q = "¿En cuál de los siguientes casos es OBLIGATORIO proyectar electrificación ELEVADA en una vivienda?",
                    opts = listOf(
                        "Vivienda con superficie útil de 120 m²",
                        "Vivienda con calefacción eléctrica o aire acondicionado centralizado",
                        "Vivienda con 2 cuartos de baño",
                        "Vivienda con placas solares térmicas de agua sanitaria"
                    ),
                    a = 1,
                    exp = "La ITC-BT-10 y 25 obligan a electrificación elevada cuando la superficie útil supere los 160 m², o si se instala calefacción eléctrica, aire acondicionado o piscina.",
                    ref = "ITC-BT-10 §2.3"
                ),
                Question(
                    q = "¿Cuál es la sección mínima de los conductores de cobre en una Línea General de Alimentación (LGA)?",
                    opts = listOf(
                        "10 mm²",
                        "16 mm²",
                        "25 mm²",
                        "35 mm²"
                    ),
                    a = 1,
                    exp = "La ITC-BT-14 par. 3 fija que la sección mínima de los conductores de la LGA será de 16 mm² para cobre y de 25 mm² para aluminio.",
                    ref = "ITC-BT-14 §3"
                ),
                Question(
                    q = "En una LGA, si los contadores están totalmente concentrados en un único local, ¿cuál es la caída de tensión máxima admisible?",
                    opts = listOf(
                        "0,5%",
                        "1,0%",
                        "1,5%",
                        "2,0%"
                    ),
                    a = 0,
                    exp = "La ITC-BT-14 fija que para contadores totalmente concentrados en un único local o armario único, la caída de tensión máxima admisible en la LGA es del 0,5%.",
                    ref = "ITC-BT-14 §3"
                ),
                Question(
                    q = "Para contadores concentrados en más de un local (agrupaciones intermedias en planta), la caída de tensión en LGA puede llegar hasta el:",
                    opts = listOf(
                        "0,5%",
                        "1,0%",
                        "1,5%",
                        "2,5%"
                    ),
                    a = 1,
                    exp = "Cuando los contadores se agrupan en más de un local o armarios intermedios de planta, la caída de tensión permitida en la LGA es del 1,0%.",
                    ref = "ITC-BT-14 §3"
                ),
                Question(
                    q = "¿Cuál es la sección mínima de conductor de cobre admitida en una Derivación Individual (DI)?",
                    opts = listOf(
                        "2,5 mm²",
                        "4 mm²",
                        "6 mm²",
                        "10 mm²"
                    ),
                    a = 2,
                    exp = "La ITC-BT-15 par. 3 prescribe que los conductores de la derivación individual tendrán una sección mínima de 6 mm² para cables de cobre.",
                    ref = "ITC-BT-15 §3"
                ),
                Question(
                    q = "¿Cuál es el diámetro exterior mínimo del tubo protector para una Derivación Individual (DI)?",
                    opts = listOf(
                        "20 mm",
                        "25 mm",
                        "32 mm",
                        "40 mm"
                    ),
                    a = 2,
                    exp = "La ITC-BT-15 par. 2 establece de forma tajante que los tubos y canales protectores para derivaciones individuales tendrán un diámetro exterior mínimo de 32 mm.",
                    ref = "ITC-BT-15 §2"
                ),
                Question(
                    q = "En un edificio de viviendas con contadores concentrados en un único local, ¿cuál es la caída de tensión máxima permitida en la Derivación Individual?",
                    opts = listOf(
                        "0,5%",
                        "1,0%",
                        "1,5%",
                        "3,0%"
                    ),
                    a = 2,
                    exp = "La ITC-BT-15 par. 3 establece que para contadores concentrados en un local único, la caída de tensión máxima admisible en la Derivación Individual es del 1,5%.",
                    ref = "ITC-BT-15 §3"
                ),
                Question(
                    q = "En el Cuadro General de Mando y Protección (CGMP) de una vivienda, el IGA debe tener un poder de corte mínimo de:",
                    opts = listOf(
                        "3.000 A (3 kA)",
                        "4.500 A (4,5 kA)",
                        "6.000 A (6 kA)",
                        "10.000 A (10 kA)"
                    ),
                    a = 1,
                    exp = "La ITC-BT-17 par. 1.1 exige que el Interruptor General Automático (IGA) disponga de un poder de corte no inferior a 4.500 A (4,5 kA).",
                    ref = "ITC-BT-17 §1.1"
                ),
                Question(
                    q = "¿Cuántos circuitos interiores de vivienda (PIAs) se pueden agrupar bajo la protección de un único interruptor diferencial de 30 mA como máximo?",
                    opts = listOf(
                        "3 circuitos",
                        "5 circuitos",
                        "8 circuitos",
                        "No hay límite mientras la suma de intensidades no supere la nominal del diferencial"
                    ),
                    a = 1,
                    exp = "La ITC-BT-25 e ITC-BT-17 prohíben que más de 5 circuitos interiores dependan de un solo interruptor diferencial de 30 mA, debiendo instalarse un segundo diferencial si se supera ese número.",
                    ref = "ITC-BT-25 §4"
                ),
                Question(
                    q = "¿Qué previsión mínima de carga por metro cuadrado se debe considerar reglamentariamente para locales comerciales u oficinas (sin uso específico conocido)?",
                    opts = listOf(
                        "50 W/m² con un mínimo de 2.300 W",
                        "100 W/m² con un mínimo de 3.450 W a 230 V",
                        "150 W/m² con un mínimo de 5.750 W",
                        "200 W/m² con un mínimo de 9.200 W"
                    ),
                    a = 1,
                    exp = "La ITC-BT-10 par. 3.1 fija que para locales comerciales u oficinas se preverá un mínimo de 100 W por m² con un valor base mínimo absoluto de 3.450 W.",
                    ref = "ITC-BT-10 §3.1"
                ),
                Question(
                    q = "¿Qué previsión de carga mínima por metro cuadrado se fija para aparcamientos y garajes con ventilación forzada?",
                    opts = listOf(
                        "5 W/m²",
                        "10 W/m²",
                        "20 W/m² con un mínimo de 3.450 W",
                        "50 W/m²"
                    ),
                    a = 2,
                    exp = "La ITC-BT-10 par. 3.2 estipula 20 W/m² para garajes con ventilación forzada (y 10 W/m² para ventilación natural), con un mínimo de 3.450 W.",
                    ref = "ITC-BT-10 §3.2"
                ),
                Question(
                    q = "En una comunidad con 25 viviendas con electrificación básica idéntica, ¿qué coeficiente de simultaneidad (Cs) aplica según la fórmula de n > 21?",
                    opts = listOf(
                        "15,3",
                        "17,3",
                        "19,5",
                        "25,0"
                    ),
                    a = 1,
                    exp = "Para n > 21 la fórmula oficial es Cs = 15,3 + (n - 21) * 0,5. Para n = 25: Cs = 15,3 + (4 * 0,5) = 15,3 + 2,0 = 17,3.",
                    ref = "ITC-BT-10 §3"
                )
            )
        ),
        "interiores" to ModuleDefinition(
            id = "interiores",
            label = "Instalaciones Interiores y Vivienda (ITC-19 a 27)",
            icon = "🏠",
            color = "#3fb950",
            questions = listOf(
                Question(
                    q = "¿Cuál es la caída de tensión máxima admisible para instalaciones interiores de receptores de ALUMBRADO (medida desde el cuadro CGMP)?",
                    opts = listOf(
                        "1,5%",
                        "3,0%",
                        "4,5%",
                        "5,0%"
                    ),
                    a = 1,
                    exp = "La ITC-BT-19 par. 2.2.2 dictamina que la caída de tensión máxima permitida en circuitos interiores de alumbrado es del 3% de la tensión nominal.",
                    ref = "ITC-BT-19 §2.2.2"
                ),
                Question(
                    q = "¿Cuál es la caída de tensión máxima admisible para circuitos interiores de FUERZA y otros usos receptores?",
                    opts = listOf(
                        "3,0%",
                        "4,0%",
                        "5,0%",
                        "7,0%"
                    ),
                    a = 2,
                    exp = "La ITC-BT-19 fija un 5% de caída máxima de tensión admisible para circuitos receptores de fuerza motriz, calefacción y otros usos.",
                    ref = "ITC-BT-19 §2.2.2"
                ),
                Question(
                    q = "¿Qué calibre de interruptor magnetotérmico (PIA) y qué sección mínima de cobre corresponden al circuito C1 (Iluminación)?",
                    opts = listOf(
                        "10 A y 1,5 mm²",
                        "16 A y 2,5 mm²",
                        "20 A y 4 mm²",
                        "25 A y 6 mm²"
                    ),
                    a = 0,
                    exp = "Conforme a la Tabla 1 de la ITC-BT-25, el circuito C1 de alumbrado general se protege con magnetotérmico de 10 A y conductores de sección mínima 1,5 mm².",
                    ref = "ITC-BT-25 Tabla 1"
                ),
                Question(
                    q = "¿Qué sección mínima y calibre de protección corresponden al circuito C3 (Cocina y Horno)?",
                    opts = listOf(
                        "16 A y 2,5 mm²",
                        "20 A y 4 mm²",
                        "25 A y 6 mm²",
                        "32 A y 10 mm²"
                    ),
                    a = 2,
                    exp = "La Tabla 1 de la ITC-BT-25 fija para el circuito C3 de cocina y horno una sección mínima de 6 mm² de cobre y un magnetotérmico de 25 A.",
                    ref = "ITC-BT-25 Tabla 1"
                ),
                Question(
                    q = "¿Cuántas tomas de corriente como máximo se permite conectar al circuito C5 (tomas de cocina y cuarto de baño)?",
                    opts = listOf(
                        "4 tomas",
                        "6 tomas",
                        "10 tomas",
                        "20 tomas"
                    ),
                    a = 1,
                    exp = "La ITC-BT-25 prescribe un límite estricto de seguridad: el circuito C5 no alimentará más de 6 tomas de corriente en cocinas y baños.",
                    ref = "ITC-BT-25 Tabla 1"
                ),
                Question(
                    q = "Si se desdobla el circuito C4 (lavadora, lavavajillas y termo) en 3 ramales independientes, ¿qué protección y sección lleva cada uno?",
                    opts = listOf(
                        "Un único PIA de 20 A y cables de 4 mm² compartidos",
                        "3 magnetotérmicos individuales de 16 A con cables de 2,5 mm² en tubos independientes",
                        "3 magnetotérmicos de 10 A con cables de 1,5 mm²",
                        "Un diferencial de 10 mA con cable de 6 mm²"
                    ),
                    a = 1,
                    exp = "La ITC-BT-25 nota 4 permite desdoblar el C4 en 3 subcircuitos independientes (C4.1, C4.2 y C4.3) protegiendo cada uno con PIA de 16 A y conductor de 2,5 mm².",
                    ref = "ITC-BT-25 Tabla 1"
                ),
                Question(
                    q = "En un cuarto de baño, ¿qué aparatos eléctricos están autorizados en el VOLUMEN 0 (interior del vaso de la bañera o ducha)?",
                    opts = listOf(
                        "Cualquier aparato con protección IPX4",
                        "Únicamente aparatos previstos para ese fin con grado IPX7 alimentados a MBTS ≤ 12 V CA o ≤ 30 V CC",
                        "Calentadores de agua instantáneos de hasta 2 kW",
                        "Bases de enchufe con tapa protectora hermética"
                    ),
                    a = 1,
                    exp = "La ITC-BT-27 par. 2.1 solo tolera aparatos fijos sumergibles con índice IPX7 alimentados mediante Muy Baja Tensión de Seguridad (MBTS) no superior a 12 V en alterna.",
                    ref = "ITC-BT-27 §2.1"
                ),
                Question(
                    q = "En el VOLUMEN 1 de un cuarto de baño (zona vertical sobre la bañera hasta 2,25 m de altura):",
                    opts = listOf(
                        "Se permiten interruptores de luz si están a más de 1,80 m del suelo",
                        "Están totalmente prohibidos los mecanismos de mando e interruptores; solo se admiten calentadores de agua fijos IPX4 protegidos por ID 30mA",
                        "Se pueden colocar tomas de corriente protegidas con tapa estanca",
                        "Se permite cualquier equipo eléctrico si el suelo es de gres aislante"
                    ),
                    a = 1,
                    exp = "En volumen 1 está terminantemente prohibido cualquier mecanismo, enchufe o interruptor de iluminación. Solo se admiten calentadores de agua fijos IPX4.",
                    ref = "ITC-BT-27 §2.2"
                ),
                Question(
                    q = "La anchura horizontal del VOLUMEN 2 en torno a una bañera es de:",
                    opts = listOf(
                        "0,40 m",
                        "0,60 m",
                        "1,00 m",
                        "2,40 m"
                    ),
                    a = 1,
                    exp = "La ITC-BT-27 define el volumen 2 como el espacio comprendido en un plano vertical situado a 0,60 m alrededor del volumen 1 y una altura de 2,25 m.",
                    ref = "ITC-BT-27 §2.1"
                ),
                Question(
                    q = "¿Cuál es el valor mínimo admisible de resistencia de aislamiento de una instalación de baja tensión de tensión nominal ≤ 500 V?",
                    opts = listOf(
                        "0,1 MΩ (100.000 Ω)",
                        "0,5 MΩ (500.000 Ω)",
                        "1,0 MΩ (1.000.000 Ω)",
                        "10 MΩ"
                    ),
                    a = 1,
                    exp = "La ITC-BT-19 par. 5.2 fija que la resistencia de aislamiento entre conductores y tierra o entre conductores activos no será inferior a 0,5 MΩ a una tensión de ensayo de 500 V CC.",
                    ref = "ITC-BT-19 §5.2"
                ),
                Question(
                    q = "La sección mínima admisible para el conductor neutro en circuitos monofásicos es:",
                    opts = listOf(
                        "La mitad de la sección de la fase",
                        "Igual a la sección del conductor de fase",
                        "Un valor fijo de 2,5 mm²",
                        "Depende del factor de potencia de la carga exclusivamente"
                    ),
                    a = 1,
                    exp = "En circuitos monofásicos, la sección del conductor neutro debe ser idéntica a la del conductor de fase para soportar la misma intensidad nominal.",
                    ref = "ITC-BT-19 §2"
                ),
                Question(
                    q = "¿Se pueden tender conductores de diferentes circuitos por el interior del mismo tubo protector?",
                    opts = listOf(
                        "Sí, libremente hasta un máximo de 10 cables",
                        "Únicamente si todos los conductores están aislados para la tensión nominal más elevada del circuito presente",
                        "No, está prohibido bajo cualquier circunstancia",
                        "Sí, siempre que no superen una intensidad total de 32 A"
                    ),
                    a = 1,
                    exp = "La ITC-BT-19 par. 2.1 permite compartir conducto únicamente si todos los conductores están aislados para la tensión más elevada que pueda presentarse en el tubo.",
                    ref = "ITC-BT-19 §2.1"
                )
            )
        ),
        "tierra" to ModuleDefinition(
            id = "tierra",
            label = "Puesta a Tierra y Tubos (ITC-18 y 21)",
            icon = "🌍",
            color = "#3fb950",
            questions = listOf(
                Question(
                    q = "¿Cuál es la profundidad mínima obligatoria a la que debe quedar enterrado un electrodo o pica de puesta a tierra?",
                    opts = listOf(
                        "0,30 m",
                        "0,50 m",
                        "0,80 m",
                        "1,00 m"
                    ),
                    a = 1,
                    exp = "La ITC-BT-18 par. 3.1 dictamina que el electrodo quedará sepultado a una profundidad mínima de 0,50 m para librarlo de heladas superficiales y sequedad.",
                    ref = "ITC-BT-18 §3.1"
                ),
                Question(
                    q = "Tensión límite convencional de contacto en locales SECOS:",
                    opts = listOf(
                        "12 V",
                        "24 V",
                        "50 V",
                        "75 V"
                    ),
                    a = 2,
                    exp = "La ITC-BT-18 par. 9 establece como tensión límite convencional de contacto 50 V eficaces en corriente alterna para locales o emplazamientos secos.",
                    ref = "ITC-BT-18 §9"
                ),
                Question(
                    q = "Tensión límite convencional de contacto en locales HÚMEDOS o mojados:",
                    opts = listOf(
                        "12 V",
                        "24 V",
                        "50 V",
                        "75 V"
                    ),
                    a = 1,
                    exp = "En emplazamientos conductores, locales húmedos o mojados, la tensión límite de seguridad se reduce a 24 V eficaces para evitar fibrilación cardíaca.",
                    ref = "ITC-BT-18 §9"
                ),
                Question(
                    q = "¿Cuál es la sección mínima de un conductor de cobre enterrado DESNUDO sin protección contra la corrosión para toma de tierra?",
                    opts = listOf(
                        "16 mm²",
                        "25 mm²",
                        "35 mm²",
                        "50 mm²"
                    ),
                    a = 2,
                    exp = "La Tabla 1 de la ITC-BT-18 exige 35 mm² para cobre desnudo enterrado sin protección contra la corrosión para asegurar durabilidad química mecánica.",
                    ref = "ITC-BT-18 Tabla 1"
                ),
                Question(
                    q = "¿Y cuál es la sección mínima si el conductor de tierra de cobre enterrado dispone de aislamiento mecánico protector?",
                    opts = listOf(
                        "10 mm²",
                        "16 mm²",
                        "25 mm²",
                        "35 mm²"
                    ),
                    a = 1,
                    exp = "La Tabla 1 de la ITC-BT-18 permite 16 mm² cuando el conductor de cobre está protegido contra la corrosión mediante funda de aislamiento dieléctrico.",
                    ref = "ITC-BT-18 Tabla 1"
                ),
                Question(
                    q = "Para un conductor de fase con sección S = 25 mm², ¿cuál es la sección mínima del conductor de protección (PE)?",
                    opts = listOf(
                        "10 mm²",
                        "16 mm²",
                        "25 mm²",
                        "12,5 mm²"
                    ),
                    a = 1,
                    exp = "Según la Tabla 2 de la ITC-18, para fases entre 16 y 35 mm², la sección reglamentaria del conductor de protección (PE) es fija e igual a 16 mm².",
                    ref = "ITC-BT-18 Tabla 2"
                ),
                Question(
                    q = "Para un conductor de fase con sección S = 50 mm², ¿cuál es la sección reglamentaria del PE?",
                    opts = listOf(
                        "16 mm²",
                        "25 mm² (S/2)",
                        "35 mm²",
                        "50 mm²"
                    ),
                    a = 1,
                    exp = "Para secciones de fase mayores de 35 mm² (S > 35), la sección del PE debe ser al menos la mitad de la fase: Sp = 50 / 2 = 25 mm².",
                    ref = "ITC-BT-18 Tabla 2"
                ),
                Question(
                    q = "¿Está permitido utilizar las tuberías metálicas de agua o gas de la vivienda como electrodo de puesta a tierra?",
                    opts = listOf(
                        "Sí, si están soldadas de forma continua con cobre",
                        "No, está terminantemente prohibido utilizar tuberías de servicios públicos como tomas de tierra",
                        "Solo las tuberías de calefacción comunitaria",
                        "Sí, siempre que se coloque una abrazadera homologada"
                    ),
                    a = 1,
                    exp = "La ITC-BT-18 prohíbe tajantemente utilizar tuberías metálicas de agua, gas o desagües como electrodo o conductor de puesta a tierra.",
                    ref = "ITC-BT-18 §3"
                ),
                Question(
                    q = "¿Qué elemento es OBLIGATORIO instalar en la línea de puesta a tierra para permitir la comprobación periódica de su resistencia?",
                    opts = listOf(
                        "Un fusible de 100 A",
                        "Un borne o seccionador de tierra (puente de comprobación desmontable)",
                        "Un diferencial de 1 A",
                        "Un voltímetro analógico fijo"
                    ),
                    a = 1,
                    exp = "La ITC-BT-18 par. 6 exige intercalar un seccionador de tierra o borne de desconexión rápida que permita aislar la toma y medir su resistencia con el telurómetro.",
                    ref = "ITC-BT-18 §6"
                ),
                Question(
                    q = "Para 3 conductores de sección 2,5 mm² en tubo empotrado en pared ordinaria, el diámetro exterior mínimo del tubo es de:",
                    opts = listOf(
                        "12 mm",
                        "16 mm",
                        "20 mm",
                        "25 mm"
                    ),
                    a = 2,
                    exp = "De acuerdo con la Tabla 5 de la ITC-BT-21 para canalizaciones empotradas en obra, 3 conductores de 2,5 mm² precisan un diámetro exterior de 20 mm.",
                    ref = "ITC-BT-21 Tabla 5"
                ),
                Question(
                    q = "Para 3 conductores de 1,5 mm² bajo tubo empotrado, el diámetro exterior mínimo del tubo es de:",
                    opts = listOf(
                        "12 mm",
                        "16 mm",
                        "20 mm",
                        "25 mm"
                    ),
                    a = 1,
                    exp = "Según la Tabla 5 de la ITC-BT-21, 3 hilos de 1,5 mm² empotrados requieren un tubo de diámetro exterior mínimo de 16 mm.",
                    ref = "ITC-BT-21 Tabla 5"
                ),
                Question(
                    q = "¿Está permitido realizar empalmes de conductores eléctricos en el interior de los tubos protectores?",
                    opts = listOf(
                        "Sí, usando cinta aislante de alta adherencia",
                        "No, jamás; todos los empalmes y derivaciones se realizarán exclusivamente dentro de cajas registradas",
                        "Solo si el tubo tiene más de 40 mm de diámetro",
                        "Sí, usando conectores rápidos de palanca"
                    ),
                    a = 1,
                    exp = "La ITC-BT-21 prohíbe de forma absoluta efectuar uniones o empalmes de cables dentro del tubo. Todo empalme debe estar en caja de derivación accesible.",
                    ref = "ITC-BT-21 §2"
                )
            )
        ),
        "especiales" to ModuleDefinition(
            id = "especiales",
            label = "Locales Especiales, Garajes y Vehículo Eléctrico (ITC-28/29/52)",
            icon = "⚡",
            color = "#f5b041",
            questions = listOf(
                Question(
                    q = "¿Qué reacción al fuego y características de emisión de humos deben tener los cables instalados en locales de pública concurrencia (LPC)?",
                    opts = listOf(
                        "Cable estándar de PVC H07V-K",
                        "No propagadores del incendio y con emisión de humo y opacidad reducida (Cca-s1b,d1,a1 / Tipo AS libre de halógenos)",
                        "Cable armado con fleje de acero galvanizado únicamente",
                        "Cualquier cable con marcado CE básico"
                    ),
                    a = 1,
                    exp = "La ITC-BT-28 par. 4 exige de forma inexcusable cables de alta seguridad (AS) no propagadores del incendio y libres de halógenos con clasificación europea Cca-s1b,d1,a1.",
                    ref = "ITC-BT-28 §4"
                ),
                Question(
                    q = "¿Qué autonomía mínima debe proporcionar la fuente propia de energía del alumbrado de seguridad y evacuación en locales de pública concurrencia?",
                    opts = listOf(
                        "15 minutos",
                        "30 minutos",
                        "1 hora (60 minutos)",
                        "3 horas"
                    ),
                    a = 2,
                    exp = "La ITC-BT-28 par. 5.1 establece que las luminarias de emergencia y evacuación deben disponer de una autonomía mínima garantizada de 1 hora.",
                    ref = "ITC-BT-28 §5.1"
                ),
                Question(
                    q = "¿Cuál es el nivel mínimo de iluminación prescrito en el eje de los pasos y vías de evacuación?",
                    opts = listOf(
                        "0,5 lux",
                        "1 lux a nivel del suelo",
                        "5 lux a nivel del suelo",
                        "15 lux"
                    ),
                    a = 1,
                    exp = "La ITC-BT-28 exige un mínimo de 1 lux en el eje de los pasos principales de evacuación a la altura del suelo.",
                    ref = "ITC-BT-28 §5.2"
                ),
                Question(
                    q = "¿Qué nivel mínimo de iluminación debe proporcionar el alumbrado de seguridad en los cuadros de mando y distribución (CGMP)?",
                    opts = listOf(
                        "1 lux",
                        "2 lux",
                        "5 lux",
                        "10 lux"
                    ),
                    a = 2,
                    exp = "La ITC-BT-28 par. 5.2 específica que en los cuadros generales de mando y puestos de primeros auxilios la iluminación mínima de seguridad será de 5 lux.",
                    ref = "ITC-BT-28 §5.2"
                ),
                Question(
                    q = "¿Qué tipo de interruptor diferencial es preceptivo instalar en los puntos de recarga de vehículos eléctricos según la ITC-BT-52?",
                    opts = listOf(
                        "Diferencial estándar Clase AC",
                        "Diferencial Clase A de 30 mA (o Clase B) con protección contra fugas de continua",
                        "Diferencial selectivo de 300 mA",
                        "No se exige diferencial si el punto dispone de fusible cerámico"
                    ),
                    a = 1,
                    exp = "La ITC-BT-52 par. 5 exige interruptor diferencial clase A de sensibilidad ≤ 30 mA para detectar corrientes continuas residuales generadas por el inversor del vehículo.",
                    ref = "ITC-BT-52 §5"
                ),
                Question(
                    q = "¿Cuál es la caída de tensión máxima admisible para el circuito terminal de alimentación de un punto de recarga de vehículo eléctrico?",
                    opts = listOf(
                        "1,5%",
                        "3,0%",
                        "5,0%",
                        "8,0%"
                    ),
                    a = 2,
                    exp = "La ITC-BT-52 par. 5.2 fija una caída de tensión máxima admisible del 5% a la intensidad máxima de carga prevista.",
                    ref = "ITC-BT-52 §5.2"
                ),
                Question(
                    q = "¿Qué categoría de instalador autorizado se exige formalmente para ejecutar la infraestructura de recarga de vehículos eléctricos (ITC-BT-52)?",
                    opts = listOf(
                        "Categoría Básica (IBTB)",
                        "Categoría Especialista (IBTE)",
                        "Cualquier instalador con título de electricista industrial",
                        "Solo personal técnico propio del fabricante del cargador"
                    ),
                    a = 1,
                    exp = "La ITC-BT-03 e ITC-BT-52 reservan de forma exclusiva la instalación de sistemas de recarga a las empresas instaladoras habilitadas en Categoría Especialista (IBTE).",
                    ref = "ITC-BT-03 §3.2"
                ),
                Question(
                    q = "En una estación de servicio o gasolinera, la clasificación de emplazamiento con riesgo de explosión (ATEX) corresponde a:",
                    opts = listOf(
                        "Clase I (gases, vapores o nieblas inflamables)",
                        "Clase II (polvos combustibles)",
                        "Local húmedo ordinario",
                        "Emplazamiento de riesgo nulo si los surtidores tienen manguera con retorno"
                    ),
                    a = 0,
                    exp = "La ITC-BT-29 clasifica en Clase I las atmósferas explosivas debidas a mezclas de aire con gases, vapores o nieblas inflamables.",
                    ref = "ITC-BT-29 §2"
                )
            )
        ),
        "suministro" to ModuleDefinition(
            id = "suministro",
            label = "Suministro (Art. 79-91)",
            icon = "⚡",
            color = "#FF58A6FF",
            questions = listOf(
                // Agregadas preguntas adicionales para llegar al objetivo de 230
                Question(
                    q = "¿Cuál es el valor mínimo de la resistencia de tierra en un edificio de viviendas?",
                    opts = listOf("10 Ω", "20 Ω", "40 Ω", "80 Ω"),
                    a = 2,
                    exp = "Según ITC-BT-18, la resistencia máxima debe ser de 40 Ω.",
                    ref = "ITC-BT-18"
                ),
                Question(
                    q = "¿Qué se debe hacer si no se alcanzan los 40 Ω de resistencia de tierra?",
                    opts = listOf("Instalar más picas", "Usar cable de mayor sección", "Aumentar la profundidad", "A y C son correctas"),
                    a = 3,
                    exp = "ITC-BT-18 indica que se pueden añadir más electrodos o aumentar la profundidad.",
                    ref = "ITC-BT-18"
                ),
                Question(
                    q = "¿Qué elemento protege contra contactos directos?",
                    opts = listOf("Interruptor diferencial", "Aislamiento de partes activas", "Interruptor magnetotérmico", "Puesta a tierra"),
                    a = 1,
                    exp = "El aislamiento impide el contacto directo. El diferencial protege contra contactos indirectos.",
                    ref = "Art. 20"
                ),
                Question(
                    q = "¿Cuál es el tiempo máximo de disparo de un diferencial de 30 mA?",
                    opts = listOf("100 ms", "200 ms", "300 ms", "500 ms"),
                    a = 1,
                    exp = "Los diferenciales de alta sensibilidad deben disparar en menos de 200 ms.",
                    ref = "Art. 17"
                ),
                Question(
                    q = "¿Qué color debe tener el conductor de protección?",
                    opts = listOf("Negro", "Azul", "Verde-amarillo", "Marrón"),
                    a = 2,
                    exp = "El conductor de protección (PE) debe ser obligatoriamente verde-amarillo.",
                    ref = "Art. 18"
                ),
                Question(
                    q = "¿Cuál es el hilo de mando en las DI?",
                    opts = listOf("Negro", "Rojo", "Verde", "Gris"),
                    a = 1,
                    exp = "El hilo de mando para tarifas debe ser rojo.",
                    ref = "ITC-BT-15"
                ),
                Question(
                    q = "¿Cuál es el IGA mínimo en electrificación elevada?",
                    opts = listOf("25 A", "32 A", "40 A", "50 A"),
                    a = 2,
                    exp = "La electrificación elevada requiere un IGA de al menos 40 A.",
                    ref = "ITC-BT-10"
                ),
                Question(
                    q = "¿Qué protege el IGA?",
                    opts = listOf("La línea general", "La derivación individual", "La instalación interior", "Todas las anteriores"),
                    a = 3,
                    exp = "El IGA protege la instalación contra sobrecargas y cortocircuitos.",
                    ref = "ITC-BT-17"
                ),
                Question(
                    q = "¿Se permite el uso de autotransformadores en la red de distribución?",
                    opts = listOf("Sí, sin restricciones", "No, salvo excepciones técnicas", "Sí, si son trifásicos", "Nunca"),
                    a = 1,
                    exp = "El uso de autotransformadores está generalmente prohibido por razones de seguridad.",
                    ref = "Art. 15"
                ),
                Question(
                    q = "¿Cuál es el diámetro mínimo de un tubo de 25 mm para 3 hilos de 6 mm²?",
                    opts = listOf("16 mm", "20 mm", "25 mm", "32 mm"),
                    a = 2,
                    exp = "ITC-BT-21 Tabla 5: 3x6 mm² requieren tubo de 25 mm.",
                    ref = "ITC-BT-21"
                )
            )
        ),
        "tubos" to ModuleDefinition(
            id = "tubos",
            label = "Tuberías (ITC-BT-21)",
            icon = "🔧",
            color = "#FF3FB950",
            questions = listOf(
                Question(
                    q = "¿Qué fórmula se utiliza para calcular el diámetro interior mínimo de un tubo empotrado?",
                    opts = listOf("D = (Σ secciones) / 0.9", "D = (Σ secciones) × 1.5", "D = 16 mm siempre", "D = (Σ secciones) / 1.2"),
                    a = 0,
                    exp = "Tabla 21.1: D = (Σ secciones) / 0.9.",
                    ref = "ITC-BT-21, Tabla 21.1"
                ),
                Question(
                    q = "¿Está permitido realizar empalmes dentro de los tubos?",
                    opts = listOf("Sí, si es cable libre de halógenos", "Sí, con cinta aislante", "No, está prohibido", "Solo en cajas de derivación registradas"),
                    a = 2,
                    exp = "Los tubos solo deben albergar conductores, sin empalmes.",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "¿Cuál es el diámetro exterior mínimo para derivaciones individuales?",
                    opts = listOf("16 mm", "20 mm", "25 mm", "32 mm"),
                    a = 3,
                    exp = "La ITC-BT-15 exige un mínimo de 32 mm para derivaciones individuales.",
                    ref = "ITC-BT-15"
                ),
                Question(
                    q = "¿Se pueden trazar tubos de forma diagonal?",
                    opts = listOf("Sí", "Solo en techos falsos", "No, deben seguir líneas horizontales y verticales", "Sí, si se usa tubo flexible"),
                    a = 2,
                    exp = "La norma exige trazos horizontales y verticales para evitar daños accidentales.",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "¿Cuál es el diámetro mínimo para 3 hilos de 2,5 mm² empotrados?",
                    opts = listOf("16 mm", "20 mm", "25 mm", "32 mm"),
                    a = 1,
                    exp = "Según la tabla, se requiere 20 mm.",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "¿Qué radio mínimo debe tener el curvado de un tubo?",
                    opts = listOf("5 veces el diámetro", "10 veces el diámetro", "3 veces el diámetro", "15 veces el diámetro"),
                    a = 1,
                    exp = "Para asegurar la curvatura correcta sin dañar el tubo.",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "¿Se pueden instalar cables sin tubos en superficie?",
                    opts = listOf("No, siempre en tubo", "Sí, si es bajo moldura o canaleta", "Sí, con grapas si es visible", "Solo si es un local industrial"),
                    a = 1,
                    exp = "Se permite usar canaletas o molduras registrables.",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "¿Cuál es el diámetro mínimo para 3 hilos de 1.5 mm²?",
                    opts = listOf("16 mm", "20 mm", "25 mm", "32 mm"),
                    a = 0,
                    exp = "ITC-BT-21: 16 mm es el mínimo.",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "¿Los tubos corrugados para empotrar deben ser?",
                    opts = listOf("Rígidos", "Curvables", "De acero", "De cobre"),
                    a = 1,
                    exp = "Deben ser curvables para adaptarse a las rozas.",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "¿Qué grado de IP deben tener las cajas de derivación?",
                    opts = listOf("IP20", "IP40", "IP55", "IP65"),
                    a = 1,
                    exp = "Deben tener al menos IP40 si están empotradas.",
                    ref = "ITC-BT-21"
                )
            )
        )
    )
}
