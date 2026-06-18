package com.example.data

// 1. Data classes for static educational content

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

data class NewsItem(
    val id: String,
    val title: String,
    val summary: String,
    val content: String,
    val date: String,
    val category: String, // "borrador", "ev", "autoconsumo", "inspecciones"
    val categoryLabel: String,
    val readTime: String = "3 min",
    val hot: Boolean = false
)

data class SharedDocument(
    val id: String,
    val title: String,
    val description: String,
    val fileName: String,
    val fileSize: String,
    val type: String // "BOE", "Esquema", "Calculadora"
)

// 2. Singleton Object with datasets

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

    val NEWS = listOf(
        NewsItem(
            id = "news_01",
            title = "Borrador de Actualización de las ITCs Básicas",
            summary = "El Ministerio prepara la revisión de las tablas de conductividad en caliente bajo las normas EN.",
            content = "La nueva directiva reforzará los coeficientes de reducción térmica debido al cambio climático y obligará el uso de cables sin halógenos (tipo AS) en conductos enterrados compartidos.",
            date = "15 de Junio, 2026",
            category = "borrador",
            categoryLabel = "Borrador",
            readTime = "5 min",
            hot = true
        ),
        NewsItem(
            id = "news_02",
            title = "Vehículo Eléctrico ITC-BT-52: Novedades 2026",
            summary = "Se unifican los esquemas de instalación obligando a colocar protecciones diferenciales dedicadas tipo A.",
            content = "Para cargadores en plazas de parking comunitarias con suministro compartido, se exigirá contador secundario inteligente modulable para prevención de sobrecargas de red sin cortes accidentales en viviendas.",
            date = "08 de Mayo, 2026",
            category = "ev",
            categoryLabel = "Vehículo Eléctrico",
            readTime = "4 min",
            hot = true
        ),
        NewsItem(
            id = "news_03",
            title = "Autoconsumo y Generadores: Simplificación de Trámites",
            summary = "Instalaciones solares conectadas de menos de 15 kW quedan exentas de autorización previa.",
            content = "Únicamente requerirán de la habilitación por declaración responsable mediante la firma CIE de un instalador especialista IBTE. Las compañías distribuidoras tendrán un plazo de resolución táctica de 15 días.",
            date = "22 de Abril, 2026",
            category = "autoconsumo",
            categoryLabel = "Autoconsumo",
            readTime = "3 min"
        ),
        NewsItem(
            id = "news_04",
            title = "Inspecciones de Oficio por OCAs",
            summary = "Aviso a comunidades que superen 100 kW de potencia de acometida general para revisión periódica.",
            content = "El incumplimiento de la inspección de mantenimiento decenal (cada 10 años) incurrirá en multas de graves a muy graves según el Reglamento de Seguridad Industrial, con posible precinto de acometidas.",
            date = "04 de Abril, 2026",
            category = "inspecciones",
            categoryLabel = "Inspecciones",
            readTime = "3 min"
        )
    )

    // Complete static dataset for Study Book syllabus with highlighting, traps and exam references
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
                "Tensiones de seguridad para Muy Baja Tensión (MBT): ≤ 50 V eficaces en CA y ≤ 75 V continuas en CC.",
                "Modificaciones de importancia exigen nuevo trámite cuando alteren > 50% de la potencia instalada contratada."
            ),
            greenUnderline = listOf(
                "Exclusión explícita del REBT: Minas subterráneas, tracción ferroviaria, vehículos de motor, navíos, aeronaves, sistemas militares de defensa.",
                "Las distribuidoras deben entregar suministro con la frecuencia de 50 Hz y niveles de voltaje normalizados."
            ),
            trap = "El tribunal te intentará decir que un ascensor o un vehículo híbrido que se enchufa a la red está excluido por ser 'vehículo mecánico'. Falso: la recarga del Vehículo Eléctrico (ITC-BT-52) sí está rigurosamente dentro del REBT.",
            keyConcept = "Tensiones límites de BT: 1000V en CA y 1500V en CC. Exclusiones absolutas: Minas, trenes, barcos, aviones, militares.",
            examReference = "Examen Castilla y León 2024 / Trampas Administrativas"
        ),
        UnderliningItcItem(
            id = "art-6-15",
            code = "Artículos 6 al 15",
            title = "Inscripción, Suministros y Distribución",
            category = "Articulado",
            freq = "Alta",
            page = "Art. 6-15",
            redUnderline = listOf(
                "Suministro de Socorro: Debe garantizar como mínimo el 15% de la potencia total contratada para necesidades de seguridad.",
                "Suministro Duplicado: Asegura una potencia no menor al 50% de la convencional básica.",
                "Suministro de Reserva: Garantiza un mínimo del 25% de la potencia total contratada del abonado."
            ),
            greenUnderline = listOf(
                "Definición de servicios de seguridad obligatorios que deben salvaguardar la vida humana (evacuación, quirófanos, ventilación de parkings).",
                "Las especificaciones particulares de distribuidoras deben aprobarse técnicamente por delegaciones de industria."
            ),
            trap = "Prestar extremada atención al porcentaje exigible en Socorro (15% mínimo) vs Reserva (25% mínimo) vs Duplicado (50% mínimo). Las barajan cruzándote las cifras en los exámenes habituales de la certificadora.",
            keyConcept = "Suministro socorro = min 15%. Suministro de Reserva = min 25%. Suministro Duplicado = min 50%.",
            examReference = "Examen Madrid Comunidad - Pregunta Teórica de Suministros"
        ),
        UnderliningItcItem(
            id = "art-16-29",
            code = "Artículos 16 al 29",
            title = "Ejecución, Tramitación, Inspección y Sanciones",
            category = "Articulado",
            freq = "Crítica",
            page = "Art. 16-29",
            redUnderline = listOf(
                "Las instalaciones solo podrán ejecutarse por empresas instaladoras habilitadas (categorías básica o especialista).",
                "La solicitud de autorizaciones excepcionales (por imposibilidad técnica física) se resolverá de forma expresa en 3 meses.",
                "Silencio Administrativo: El silencio del órgano de la administración competente en autorizaciones especiales equivale a denegación (silencio desestimatorio)."
            ),
            greenUnderline = listOf(
                "El Certificado de Instalación Eléctrica (CIE o Boletín) tiene validez oficial una vez registrado formalmente en Industria.",
                "Clasificación de infracciones graves y muy graves con multas coercitivas por falta de OCA."
            ),
            trap = "El silencio administrativo de las especificaciones particulares de las Compañías Distribuidoras es POSITIVO en 3 meses, pero el silencio para Excepciones Técnicas autorizadas por Industria es NEGATIVO (desestimatorio). Memorizar esta diferencia de impacto jurídico.",
            keyConcept = "Mantenimiento preventivo recae en propietario de la instalación. Las OCA tienen libre acceso de inspección.",
            examReference = "Comunidad Valenciana - Derecho Eléctrico (Certificadora)"
        ),
        UnderliningItcItem(
            id = "itc-01",
            code = "ITC-BT-01",
            title = "Terminología y Vocabulario Eléctrico",
            category = "Administrativas",
            freq = "Media",
            page = "ITC-01",
            redUnderline = listOf(
                "Tensión de defecto: Tensión que aparece entre una masa metálica y tierra por un fallo de aislamiento.",
                "Tensión de contacto límite: 50 V en locales secos ordinarios y 24 V en locales mojados o húmedos especiales.",
                "Masa: Parte metálica accesible de un equipo eléctrico que normalmente no está en tensión, pero puede estarlo si falla el aislamiento."
            ),
            greenUnderline = listOf(
                "Diferencia legal entre Conductor de Protección (PE) y Conductor de Neutro (N).",
                "Definición técnica rigurosa de sobrecorriente, sobrecarga, cortocircuito, corriente residual e interruptor automático combi."
            ),
            trap = "El examen suele meter la definición de 'Masa' sustituyendo la palabra 'no está normalmente en tensión' por 'siempre está en tensión'. Mantén la distinción clara de que masa no lleva corriente en servicio normal.",
            keyConcept = "Masa = Conductor pasivo normalmente frío. Tensión de contacto límite seca = 50V. Tensión mojada = 24V. Piscina = 12V.",
            examReference = "Banco General Certificadora - Conceptos Básicos"
        ),
        UnderliningItcItem(
            id = "itc-03",
            code = "ITC-BT-03",
            title = "Empresas Instaladoras y Habilitación",
            category = "Administrativas",
            freq = "Crítica",
            page = "ITC-03",
            redUnderline = listOf(
                "Categoría Básica (IBTB): Permite realizar instalaciones domésticas comunes, locales comerciales sencillos y alumbrado general en baja tensión.",
                "Categoría Especialista (IBTE): Exclusiva para dar de alta: Locales ATEX, Hospitales/Quirófanos, Fotovoltaica, Domótica SCADA, Alumbrado Exterior > 5 kW y recarga de vehículos eléctricos (VE)."
            ),
            greenUnderline = listOf(
                "El carnet o certificado de cualificación individual del instalador no tiene caducidad física si se mantiene el seguro de responsabilidad civil suscrito."
            ),
            trap = "Preguntan si un instalador IBT Básica puede dar de alta un punto de recarga de vehículos eléctricos en una vivienda unifamiliar. La respuesta es NO: todo cargador de vehículo eléctrico (ITC-BT-52) precisa instalador Especialista (IBTE).",
            keyConcept = "Categorías: Básica (General) vs Especialista (ATEX, Quirófanos, Solar, Domótica, Alumbrado Exterior, Vehículo Eléctrico).",
            examReference = "Examen de Acceso Profesional"
        ),
        UnderliningItcItem(
            id = "itc-04",
            code = "ITC-BT-04",
            title = "Documentación y Puesta en Servicio",
            category = "Administrativas",
            freq = "Crítica",
            page = "ITC-04",
            redUnderline = listOf(
                "Proyecto de Ingeniería obligatorio: Viviendas con previsión > 100 kW, locales ATEX (excepto garajes < 25 plazas), piscinas con potencia > 10 kW, industrias con potencia > 20 kW.",
                "Garajes con más de 5 plazas exigen Proyecto obligatoriamente si disponen de ventilación natural.",
                "Cualquier garaje con ventilación forzada (mecánica) requiere Proyecto sin importar el número de plazas."
            ),
            greenUnderline = listOf(
                "Memoria Técnica de Diseño (MTD): Aplica a todas las instalaciones que queden por debajo de las potencias o límites de Proyecto.",
                "Registro del boletín (CIE) ante el órgano competente es obligatorio para suministrar energía comercial."
            ),
            trap = "El examinador te preguntará por un garaje residencial cerrado de 5 plazas exactas con ventilación natural. No requiere Proyecto (el límite es > 5 plazas). Pero si tuviese ventilación forzada o 6 plazas, requeriría Proyecto.",
            keyConcept = "Garajes de 5 plazas natural = MTD. Garajes > 5 plazas o ventilación forzada = Proyecto. Pública Concurrencia = Siempre Proyecto.",
            examReference = "Examen Valencia - MTD vs Proyecto de Locales"
        ),
        UnderliningItcItem(
            id = "itc-05",
            code = "ITC-BT-05",
            title = "Verificaciones e Inspecciones (OCA)",
            category = "Administrativas",
            freq = "Crítica",
            page = "ITC-05",
            redUnderline = listOf(
                "Inspección Inicial de OCA obligatoria antes de conectar: Locales de Pública Concurrencia, Quirófanos fijos, Alumbrado público > 5 kW, Piscinas > 10 kW.",
                "Inspecciones Periódicas: Obligatorias cada 5 años para oficinas, comercios, industrias grandes, locales ATEX, etc.",
                "Zonas comunes de comunidades de vecinos de potencia > 100 kW pasan inspección periódica de la OCA cada 10 años."
            ),
            greenUnderline = listOf(
                "Quirófanos y salas críticas de intervención médica deben superar la auditoría de inspección de forma ANUAL (cada 1 año)."
            ),
            trap = "Mucho cuidado en no responder '5 años' para la OCA de un bloque de viviendas comunes residenciales. Si tiene potencia CGP > 100 kW, se pasa de forma extraordinaria cada 10 años.",
            keyConcept = "OCA Inicial: Pública Concurrencia, Quirófanos, Piscinas >10kW, Alumbrado >5kW. Periódica: Generales cada 5 años, Edificios cada 10 años, Quirófanos anual.",
            examReference = "Histórico Certificadora OCA"
        ),
        UnderliningItcItem(
            id = "itc-07",
            code = "ITC-BT-07",
            title = "Redes de Distribución Subterráneas",
            category = "Redes",
            freq = "Alta",
            page = "ITC-07",
            redUnderline = listOf(
                "Profundidad mínima de enterramiento de zanja de distribución: 0,60 metros bajo aceras peatonales.",
                "Profundidad mínima bajo calzadas de tráfico rodado (carreteras, calles): 0,80 metros.",
                "Sección mínima de conductores subterráneos de distribución: Cobre = 6 mm², Aluminio = 16 mm²."
            ),
            greenUnderline = listOf(
                "Cama o lecho de arena limpia de río alrededor del cable enterrado: espesor mínimo de 10 cm para absorción mecánica.",
                "Colocación de teja, ladrillo o bloque protector mecánico junto con cinta roja avisadora de plástico PVC a 20 cm por encima del cable."
            ),
            trap = "El examinador formulará una pregunta capciosa sobre si un cable de distribución enterrado a 0.50m es apto bajo acera. El mínimo absoluto legal bajo acera es de 0.60 metros.",
            keyConcept = "Enterramiento: 0,60 m bajo acera y 0,80 m bajo calzada. Lecho de arena = 10 cm.",
            examReference = "Examen Aragón Distribución Subterránea"
        ),
        UnderliningItcItem(
            id = "itc-10",
            code = "ITC-BT-10",
            title = "Previsión de Cargas y Electrificación",
            category = "Enlace",
            freq = "Crítica",
            page = "ITC-10",
            redUnderline = listOf(
                "Previsión Potencia Electrificación Básica: Mínimo obligatorio de 5.750 W (a 230 V eficaces con IGA de 25 A).",
                "Previsión Potencia Electrificación Elevada: Mínimo obligatorio de 9.200 W (a 230 V con IGA de 40 A).",
                "Electrificación Elevada obligatoria si: Superficie útil > 160 m², calefacción eléctrica, aire acondicionado instalado, o piscina."
            ),
            greenUnderline = listOf(
                "Fórmula de simultaneidad para comunidades de vecinos en escaleras de viviendas para n > 21: Cs = 15,3 + (n - 21) * 0.5.",
                "Los locales dedicados a servicios generales (ascensores, hidropresores) se sumarán sin aplicar ningún factor reductor de simultaneidad (Cs = 1,0)."
            ),
            trap = "En el examen te pedirán calcular la previsión de carga de una comunidad de vecinos de 25 viviendas. Muchos usan erróneamente la tabla de viviendas simples. Debes aplicar la fórmula obligatoria para n > 21 que da un Cs = 17,3.",
            keyConcept = "Previsión básica = 5750 W (IGA 25A). Elevada = 9200 W (IGA 40A). Locales = 100 W/m² (mín. 3.450 W).",
            examReference = "Examen Asturias Previsión de Cargas"
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
            examReference = "Examen de Diseños Técnicos LGA"
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
            examReference = "Examen Navarra CGMP e IGA"
        ),
        UnderliningItcItem(
            id = "itc-18",
            code = "ITC-BT-18",
            title = "Instalaciones de Puesta a Tierra",
            category = "Interiores",
            freq = "Crítica",
            page = "ITC-18",
            redUnderline = listOf(
                "Profundidad mínima de hundimiento de electrodo o pica de tierra: 0,50 metros de profundidad vertical.",
                "Sección mínima de cable de Cobre desnudo enterrado directo: 35 mm².",
                "Sección mínima de cable de Cobre con aislamiento protector mecánicamente resistente enterrado: 16 mm²."
            ),
            greenUnderline = listOf(
                "Queda terminantemente prohibido utilizar como toma de tierra tuberías de servicios públicos (agua, gas, calefacción urbana).",
                "Obligatoriedad de intercalar un borne o interruptor de desconexión rápida (seccionador de tierra) para efectuar comprobaciones anuales oficiales de resistividad."
            ),
            trap = "Mezclan el valor de sección de cobre desnudo enterrado sin funda (35 mm²) con el de cobre aislado (16 mm²). El desnudo requiere mayor sección porque no tiene protección frente a la corrosión del terreno ácido.",
            keyConcept = "Profundidad pica = 0.50m. Cobre desnudo tierra = 35 y cobre aislado = 16 mm².",
            examReference = "Examen de Puesta a Tierra"
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
                "Obligatoriedad de aislar con termomagnéticos individuales cada ramal interior independiente de vivienda.",
                "El tendido de cables de diferentes circuitos dentro del mismo conducto está estrictamente prohibido a menos que todos compartan el máximo aislamiento dieléctrico."
            ),
            trap = "¡Ojo al desdoblar el C4! Si eliges desdoblarlo en 3 ramales, se deben colocar 3 magnetotérmicos de 16 A independientes para lavadora, lavavajillas y termo, reduciendo la sección a 2,5 mm² bajo tubos individuales. No puedes dejar un único PIA de 20 A con ramales de 2,5 mm².",
            keyConcept = "C1 = 10A (1,5mm²). C2 = 16A (2,5mm²). C3 = 25A (6mm²). C4 = 20A (4mm²). C5 = 16A (2,5mm² - máx 6 tomas).",
            examReference = "Examen General Certificadora Circuitos"
        )
    )

    // Filterable REBT mock test questions categories
    val QUESTIONS = mapOf(
        "articulado" to ModuleDefinition(
            id = "articulado",
            label = "Articulado REBT (Art. 1-29)",
            icon = "⚡",
            color = "#bc8cff",
            questions = listOf(
                Question(
                    q = "¿Cuál NO es un objetivo del REBT según el Art. 1?",
                    opts = listOf(
                        "Preservar la seguridad de personas y bienes",
                        "Garantizar el máximo beneficio a las distribuidoras",
                        "Contribuir a la eficiencia económica",
                        "Asegurar el normal funcionamiento"
                    ),
                    a = 1,
                    exp = "El Art. 1 define 3 objetivos del reglamento: preservar la seguridad de personas y bienes, asegurar el normal funcionamiento y prevenir perturbaciones, y contribuir a la fiabilidad técnica y a la eficiencia económica. El beneficio de las distribuidoras NO es un objetivo.",
                    ref = "Art. 1 REBT"
                ),
                Question(
                    q = "El REBT aplica a instalaciones CA con tensión nominal máxima de:",
                    opts = listOf(
                        "500 V",
                        "750 V",
                        "1.000 V",
                        "1.500 V"
                    ),
                    a = 2,
                    exp = "El Art. 2.1.a dicta que el reglamento se aplicará a las instalaciones cuya tensión nominal sea igual o inferior a 1.000 V en corriente alterna.",
                    ref = "Art. 2.1.a REBT"
                ),
                Question(
                    q = "Una modificación es 'de importancia' cuando afecta a más del:",
                    opts = listOf(
                        "25% de la potencia",
                        "33% de la potencia",
                        "50% de la potencia",
                        "75% de la potencia"
                    ),
                    a = 2,
                    exp = "El Art. 2.2 indica que se considerará modificación de importancia la que afecte a más del 50% de la potencia instalada de la instalación original.",
                    ref = "Art. 2.2 REBT"
                ),
                Question(
                    q = "El límite de MBT en corriente ALTERNA es:",
                    opts = listOf(
                        "25 V",
                        "50 V",
                        "75 V",
                        "100 V"
                    ),
                    a = 1,
                    exp = "El Art. 4.1 tipifica que la MBT (Muy Baja Tensión) en corriente alterna comprende las tensiones inferiores o iguales a 50 V eficaces.",
                    ref = "Art. 4.1 REBT"
                ),
                Question(
                    q = "El límite de MBT en corriente CONTINUA es:",
                    opts = listOf(
                        "50 V",
                        "65 V",
                        "75 V",
                        "100 V"
                    ),
                    a = 2,
                    exp = "El Art. 4.1 tipifica que la MBT (Muy Baja Tensión) en corriente continua es la que no supere los 75 V de valor medio. ¡El límite CC es mayor que en CA!",
                    ref = "Art. 4.1 REBT"
                ),
                Question(
                    q = "Un suministro de SOCORRO garantiza como mínimo el:",
                    opts = listOf(
                        "10%",
                        "15%",
                        "25%",
                        "50%"
                    ),
                    a = 1,
                    exp = "El Art. 10.1.B.a dictamina que para suministros de socorro, la potencia complementaria no será inferior al 15% de la potencia total contratada.",
                    ref = "Art. 10 REBT"
                ),
                Question(
                    q = "Un suministro de RESERVA garantiza como mínimo el:",
                    opts = listOf(
                        "15%",
                        "20%",
                        "25%",
                        "50%"
                    ),
                    a = 2,
                    exp = "El Art. 10.1.B.b dictamina que el suministro de reserva debe garantizar al menos el 25% de la potencia total contratada del abonado.",
                    ref = "Art. 10 REBT"
                ),
                Question(
                    q = "La declaración responsable de empresa instaladora habilita por:",
                    opts = listOf(
                        "1 año renovable",
                        "5 años renovables",
                        "Tiempo indefinido",
                        "El tiempo que fije la CA"
                    ),
                    a = 2,
                    exp = "El Art. 22.2 indica que la presentación de la declaración responsable habilita por tiempo indefinido, de manera inmediata y con validez para todo el territorio nacional.",
                    ref = "Art. 22.2 REBT"
                ),
                Question(
                    q = "El silencio administrativo en el Art. 24 (excepciones al REBT) es:",
                    opts = listOf(
                        "Aprobatorio a los 30 días",
                        "Aprobatorio a los 3 meses",
                        "Desestimatorio",
                        "Requiere resolución expresa siempre"
                    ),
                    a = 2,
                    exp = "En el Art. 24, las solicitudes de excepción que no tengan respuesta en el plazo reglamentario se consideran desestimadas (silencio administrativo negativo).",
                    ref = "Art. 24 REBT"
                )
            )
        ),
        "empresas" to ModuleDefinition(
            id = "empresas",
            label = "Habilitación e Inspecciones (ITC-03/04/05)",
            icon = "🏢",
            color = "#f85149",
            questions = listOf(
                Question(
                    q = "¿Puede un IBTB (Básica) instalar fotovoltaica conectada a red?",
                    opts = listOf(
                        "Sí, sin restricciones",
                        "Sí, hasta 10 kW",
                        "No, es competencia de IBTE (Especialista)",
                        "Sí, con supervisión de IBTE"
                    ),
                    a = 2,
                    exp = "Las instalaciones generadoras de baja tensión de potencia superior o igual a 10 kW pertenecen exclusivamente al IBTE (Especialista), mientras que la ITC-03 reserva las generadoras en general en la lista del IBTE.",
                    ref = "ITC-BT-03 par. 3.2"
                ),
                Question(
                    q = "¿Puede un IBTE instalar en locales con riesgo ATEX?",
                    opts = listOf(
                        "No, requiere empresa especializada",
                        "Sí, es competencia del IBTE",
                        "Solo Clase II (polvo)",
                        "Solo con supervisión de OCA"
                    ),
                    a = 1,
                    exp = "La categoría Especialista (IBTE) habilita explícitamente para intervenir en instalaciones situadas en locales con riesgo de incendio o explosión (ATEX).",
                    ref = "ITC-BT-03 par. 3.2"
                ),
                Question(
                    q = "Un aparcamiento de 8 plazas con ventilación NATURAL, ¿necesita proyecto?",
                    opts = listOf(
                        "No, menos de 10 plazas",
                        "Sí, más de 5 plazas lo requiere",
                        "Solo si supera 10 kW",
                        "No, los aparcamientos naturales nunca"
                    ),
                    a = 1,
                    exp = "La ITC-BT-04 grupo h indica que los aparcamientos de ventilación natural precisan proyecto cuando disponen de más de 5 plazas de estacionamiento.",
                    ref = "ITC-BT-04 par. 3"
                ),
                Question(
                    q = "Un aparcamiento de 5 plazas EXACTAS con ventilación natural, ¿proyecto?",
                    opts = listOf(
                        "Sí, cualquier aparcamiento",
                        "No, el límite es MÁS DE 5 plazas",
                        "Sí, pero solo MTD",
                        "Depende de la potencia"
                    ),
                    a = 1,
                    exp = "El límite es estricto: MÁS DE 5 plazas. Por tanto, 5 plazas exactas se tramitan únicamente con Memoria Técnica de Diseño (MTD).",
                    ref = "ITC-BT-04 grupo h"
                ),
                Question(
                    q = "Un aparcamiento con ventilación FORZADA de 3 plazas, ¿proyecto?",
                    opts = listOf(
                        "No, menos de 5 plazas",
                        "Sí, siempre con ventilación forzada",
                        "Solo si supera 10 kW",
                        "Solo con más de 25 plazas"
                    ),
                    a = 1,
                    exp = "La ITC-BT-04 grupo g indica que los aparcamientos que requieren ventilación forzada precisan proyecto SIEMPRE, cualquiera que sea su ocupación o plazas.",
                    ref = "ITC-BT-04 grupo g"
                ),
                Question(
                    q = "Un local de pública concurrencia, ¿cuándo necesita proyecto?",
                    opts = listOf(
                        "Solo si supera 20 kW",
                        "Solo si supera 100 kW",
                        "Siempre, sin límite de potencia",
                        "Solo si tiene alumbrado de emergencia"
                    ),
                    a = 2,
                    exp = "Los locales de pública concurrencia (grupo i) exigen la formulación de proyecto técnico de forma absoluta ('Sin límite de potencia').",
                    ref = "ITC-BT-04 grupo i"
                ),
                Question(
                    q = "Un industrial de 120 kW, ¿requiere inspección inicial OCA?",
                    opts = listOf(
                        "No, el límite es 150 kW",
                        "Sí, supera los 100 kW",
                        "Solo si tiene maquinaria peligrosa",
                        "Solo inspección periódica"
                    ),
                    a = 1,
                    exp = "La ITC-BT-05 §4.1.a dicta inspección inicial obligatoria por un Organismo de Control (OCA) para instalaciones industriales que precisen proyecto con una potencia instalada superior a 100 kW.",
                    ref = "ITC-BT-05 §4.1.a"
                ),
                Question(
                    q = "¿Con qué frecuencia se inspeccionan las instalaciones comunes de viviendas > 100 kW?",
                    opts = listOf(
                        "Cada 5 años",
                        "Cada 8 años",
                        "Cada 10 años",
                        "Cada 15 años"
                    ),
                    a = 2,
                    exp = "Las instalaciones comunes de edificios de viviendas cuya potencia total instalada sea superior a 100 kW deben someterse a inspección periódica cada 10 años.",
                    ref = "ITC-BT-05 §4.2"
                )
            )
        ),
        "suministro" to ModuleDefinition(
            id = "suministro",
            label = "Cuadros y Potencias (ITC-10/17/25)",
            icon = "⚡",
            color = "#f0c040",
            questions = listOf(
                Question(
                    q = "La potencia mínima para electrificación BÁSICA es:",
                    opts = listOf(
                        "3.450 W",
                        "5.000 W",
                        "5.750 W",
                        "9.200 W"
                    ),
                    a = 2,
                    exp = "La potencia a prever para nuevas construcciones de grado básico no será inferior a 5.750 W a 230 V.",
                    ref = "ITC-BT-10 §2.2"
                ),
                Question(
                    q = "La potencia mínima para electrificación ELEVADA es:",
                    opts = listOf(
                        "5.750 W",
                        "7.500 W",
                        "9.200 W",
                        "12.000 W"
                    ),
                    a = 2,
                    exp = "En las viviendas con grado de electrificación elevada, la potencia mínima a prever no será inferior a 9.200 W.",
                    ref = "ITC-BT-10 §2.2"
                ),
                Question(
                    q = "Carga mínima de previsión para locales comerciales:",
                    opts = listOf(
                        "50 W/m², mín. 2.000W",
                        "100 W/m², mín. 3.450W",
                        "75 W/m², mín. 5.000W",
                        "125 W/m², mín. 3.450W"
                    ),
                    a = 1,
                    exp = "En locales comerciales u oficinas, se calcula con 100 W por m² y planta, definiendo un mínimo de 3.450 W por local (Cs=1).",
                    ref = "ITC-BT-10 §3.3"
                ),
                Question(
                    q = "IGA mínimo en electrificación BÁSICA:",
                    opts = listOf(
                        "16 A",
                        "20 A",
                        "25 A",
                        "32 A"
                    ),
                    a = 2,
                    exp = "El interruptor general automático (IGA) en grado de electrificación básica tendrá una intensidad nominal mínima de 25 A.",
                    ref = "ITC-BT-25 §2.1"
                ),
                Question(
                    q = "IGA mínimo en electrificación ELEVADA:",
                    opts = listOf(
                        "25 A",
                        "32 A",
                        "40 A",
                        "50 A"
                    ),
                    a = 2,
                    exp = "El IGA de la vivienda con electrificación elevada se asocia con un interruptor general automático mínimo de 40 A de corriente nominal.",
                    ref = "ITC-BT-25"
                ),
                Question(
                    q = "¿Cuántos circuitos puede proteger un mismo IID (diferencial) como máximo?",
                    opts = listOf(
                        "3",
                        "5",
                        "7",
                        "10"
                    ),
                    a = 1,
                    exp = "Se instalará por prescripción un interruptor diferencial como mínimo por cada cinco circuitos o fracción instalados para evitar fugas acumuladas.",
                    ref = "ITC-BT-25 §2.3.2"
                )
            )
        ),
        "tierra" to ModuleDefinition(
            id = "tierra",
            label = "Puesta a Tierra (ITC-18)",
            icon = "🌍",
            color = "#3fb950",
            questions = listOf(
                Question(
                    q = "Profundidad mínima de enterramiento del electrodo de tierra:",
                    opts = listOf(
                        "0,30 m",
                        "0,50 m",
                        "0,70 m",
                        "1,00 m"
                    ),
                    a = 1,
                    exp = "Por el riesgo de heladas o desecación del terreno, la profundidad reglamentaria del electrodo enterrado nunca será inferior a 0,50 m.",
                    ref = "ITC-BT-18 §3.1"
                ),
                Question(
                    q = "Tensión de contacto máxima en local SECO:",
                    opts = listOf(
                        "24 V",
                        "50 V",
                        "75 V",
                        "110 V"
                    ),
                    a = 1,
                    exp = "La tensión límite convencional de contacto en locales de carácter seco o condiciones habituales es de 50 V eficaces en CA.",
                    ref = "ITC-BT-18 §9"
                ),
                Question(
                    q = "Tensión de contacto máxima en local HÚMEDO:",
                    opts = listOf(
                        "12 V",
                        "24 V",
                        "50 V",
                        "75 V"
                    ),
                    a = 1,
                    exp = "En locales húmedos, mojados o emplazamientos conductores de mayor riesgo, la tensión de seguridad máxima admisible es de 24 V eficaces.",
                    ref = "ITC-BT-18 §9"
                ),
                Question(
                    q = "Para fase de 25 mm², la sección mínima del conductor de protección (PE) es:",
                    opts = listOf(
                        "10 mm²",
                        "16 mm²",
                        "25 mm²",
                        "12,5 mm²"
                    ),
                    a = 1,
                    exp = "De acuerdo con la Tabla 2 de la ITC-18, para fases entre 16 y 35 mm² de sección, el conductor de protección (PE) tendrá un valor fijo de 16 mm².",
                    ref = "ITC-BT-18 Tabla 2"
                ),
                Question(
                    q = "Para fase de 50 mm², la sección mínima del PE es:",
                    opts = listOf(
                        "16 mm²",
                        "25 mm²",
                        "35 mm²",
                        "50 mm²"
                    ),
                    a = 1,
                    exp = "De acuerdo con la Tabla 2 de la ITC-18, para fases de S > 35 mm², la sección del PE será la mitad del valor de la fase (S/2). 50 mm² / 2 = 25 mm².",
                    ref = "ITC-BT-18 Tabla 2"
                )
            )
        ),
        "tubos" to ModuleDefinition(
            id = "tubos",
            label = "Conducciones y Tubos (ITC-21)",
            icon = "🔧",
            color = "#58a6ff",
            questions = listOf(
                Question(
                    q = "3 hilos de 2,5 mm² bajo tubo empotrado: diámetro exterior mínimo del tubo:",
                    opts = listOf(
                        "12 mm",
                        "16 mm",
                        "20 mm",
                        "25 mm"
                    ),
                    a = 1,
                    exp = "Según la Tabla 5 de ITC-BT-21 para conducciones empotradas, 3 hilos de sección 2,5 mm² requieren un diámetro exterior de tubo mínimo de 16 mm.",
                    ref = "ITC-BT-21 Tabla 5"
                ),
                Question(
                    q = "3 hilos de 6 mm² bajo tubo empotrado: diámetro mínimo del tubo:",
                    opts = listOf(
                        "16 mm",
                        "20 mm",
                        "25 mm",
                        "32 mm"
                    ),
                    a = 1,
                    exp = "Según la Tabla 5 de la norma para empotrados, 3 conductores de sección 6 mm² exigen un diámetro exterior de tubo mínimo de 20 mm.",
                    ref = "ITC-BT-21 Tabla 5"
                ),
                Question(
                    q = "Diámetro exterior de tubo mínimo para la Derivación Individual:",
                    opts = listOf(
                        "20 mm",
                        "25 mm",
                        "32 mm",
                        "40 mm"
                    ),
                    a = 2,
                    exp = "La ITC-BT-15 par.2 establece un límite estricto de seguridad: el diámetro mínimo de los tubos para derivaciones individuales será siempre de 32 mm.",
                    ref = "ITC-BT-15 §2"
                )
            )
        )
    )
}
