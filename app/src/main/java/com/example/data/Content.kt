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

    // Curated Syllabus items with high-value technical highlights - Complete REBT Catalog
    val SYLLABUS = listOf(
        UnderliningItcItem(
            id = "art-1-5",
            code = "Art. 1-5",
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
            id = "art-6-13",
            code = "Art. 6-13",
            title = "Equipos, Materiales y Redes de Distribución",
            category = "Articulado",
            freq = "Alta",
            page = "Art. 6-13",
            redUnderline = listOf(
                "Todos los materiales deben ostentar el Marcado CE y cumplir las directivas de seguridad aplicables.",
                "Las empresas distribuidoras están obligadas a mantener la calidad de suministro y la tensión nominal dentro de ±7%.",
                "Las acometidas forman parte de la red de distribución aunque sean sufragadas por el promotor."
            ),
            greenUnderline = listOf(
                "Prescripciones de compatibilidad electromagnética (CEM) para evitar perturbaciones en la red general.",
                "Homologación y especificaciones particulares de compañías distribuidoras aprobadas por la Comunidad Autónoma."
            ),
            trap = "¿Quién es propietario de la acometida? La empresa distribuidora es responsable de su mantenimiento, aun cuando haya sido financiada por los usuarios en la solicitud de enganche.",
            keyConcept = "Marcado CE obligatorio. Tolerancia tensión ±7%. Distribuidora responsable de red y acometida.",
            examReference = "Art. 6 al 13 RD 842/2002"
        ),
        UnderliningItcItem(
            id = "art-14-22",
            code = "Art. 14-22",
            title = "Empresas Instaladoras y Habilitación Profesional",
            category = "Articulado",
            freq = "Crítica",
            page = "Art. 14-22",
            redUnderline = listOf(
                "La habilitación como instalador autorizado se obtiene mediante Declaración Responsable ante la Administración.",
                "Validez indefinida y con eficacia en todo el territorio español sin necesidad de trámites autonómicos adicionales.",
                "Póliza de seguro de Responsabilidad Civil obligatoria: Cobertura mínima reglamentaria actualizada (600.000€ básica / 900.000€ especialista)."
            ),
            greenUnderline = listOf(
                "Obligación de mantener los equipos de medida con calibración vigente y registrar las actuaciones.",
                "El instalador debe conservar copias de los certificados de instalación emitidos durante al menos 5 años."
            ),
            trap = "¿Caduca la acreditación de instalador autorizado? No, la declaración responsable no tiene caducidad temporal salvo cese de actividad o sanción.",
            keyConcept = "Declaración responsable con validez nacional indefinida. Seguro RC y personal contratado.",
            examReference = "Art. 22 RD 842/2002 y Ley Ómnibus"
        ),
        UnderliningItcItem(
            id = "art-23-29",
            code = "Art. 23-29",
            title = "Inspecciones, OCAs y Régimen de Infracciones",
            category = "Articulado",
            freq = "Alta",
            page = "Art. 23-29",
            redUnderline = listOf(
                "Las inspecciones periódicas son realizadas por Organismos de Control Autorizados (OCA).",
                "Calificaciones del acta de inspección: Favorable, Condicionada o Negativa.",
                "Defecto muy grave: Aquel que entraña un peligro inmediato para la seguridad de personas o cosas (corte de suministro cautelar)."
            ),
            greenUnderline = listOf(
                "Infracciones leves, graves y muy graves con sanciones económicas según la Ley de Industria 21/1992.",
                "Plazo de subsanación para defectos calificados como 'Condicionada': Máximo 6 meses."
            ),
            trap = "Un defecto muy grave suspende el suministro de inmediato, mientras que uno grave permite 6 meses de plazo si no existe riesgo inminente.",
            keyConcept = "Actas OCA: Favorable, Condicionada (6 meses), Negativa. Defecto muy grave = corte inmediato.",
            examReference = "Art. 23 y Ley 21/1992 de Industria"
        ),
        UnderliningItcItem(
            id = "itc-01",
            code = "ITC-BT-01",
            title = "Terminología y Definiciones Oficiales",
            category = "Administrativas",
            freq = "Alta",
            page = "ITC-01",
            redUnderline = listOf(
                "Masa: Parte conductora de un equipo eléctrico que puede ser tocada y que no está normalmente en tensión pero puede estarlo en caso de defecto.",
                "Conductor de protección (PE): Conductor requerido para medidas de protección contra choques eléctricos.",
                "Corte omnipolar: Corte simultáneo de todos los conductores activos (fases y neutro)."
            ),
            greenUnderline = listOf(
                "Contacto directo: Contacto de personas o animales con partes activas en tensión.",
                "Contacto indirecto: Contacto de personas con masas puestas accidentalmente en tensión debido a un fallo de aislamiento."
            ),
            trap = "Diferencia crítica en test: Contacto Directo = parte activa. Contacto Indirecto = masa que normalmente no tiene tensión.",
            keyConcept = "Masa (potencialmente peligrosa). Contacto Directo (partes activas) vs Indirecto (masas con defecto).",
            examReference = "ITC-BT-01 Definiciones"
        ),
        UnderliningItcItem(
            id = "itc-02",
            code = "ITC-BT-02",
            title = "Normas de Referencia en el REBT",
            category = "Administrativas",
            freq = "Media",
            page = "ITC-02",
            redUnderline = listOf(
                "Listado oficial de normas UNE, EN e IEC de obligado cumplimiento citadas en el reglamento.",
                "Resoluciones de actualización del Ministerio de Industria adaptan las versiones de normas UNE sin necesidad de reformar el RD."
            ),
            greenUnderline = listOf(
                "Principio de equivalencia técnica: Se admiten productos conformes a normas de otros Estados miembros de la UE si garantizan igual nivel de seguridad."
            ),
            trap = "¿Las normas UNE son siempre voluntarias? No; en el ámbito del REBT, las normas UNE citadas en la ITC-BT-02 son de OBLIGADO cumplimiento.",
            keyConcept = "Normas UNE citadas en ITC-02 son obligatorias. Actualizaciones periódicas por el Ministerio.",
            examReference = "ITC-BT-02 §1"
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
            id = "itc-05",
            code = "ITC-BT-05",
            title = "Verificaciones e Inspecciones Oficiales (OCA)",
            category = "Administrativas",
            freq = "Crítica",
            page = "ITC-05",
            redUnderline = listOf(
                "Inspección Inicial por OCA obligatoria: Locales de pública concurrencia, locales mojados > 25 kW, piscinas > 10 kW, garajes > 25 plazas, alumbrado exterior > 5 kW.",
                "Inspección Periódica cada 5 años: Locales de pública concurrencia, garajes > 25 plazas, locales con riesgo de incendio o explosión (ATEX), piscinas > 10 kW.",
                "Inspección Periódica cada 10 años: Zonas comunes de edificios de viviendas con potencia total instalada > 100 kW."
            ),
            greenUnderline = listOf(
                "El instalador debe realizar la verificación previa con instrumental oficial antes de conectar la instalación a la red.",
                "Acta de inspección con plazo de 6 meses improrrogables para solventar defectos condicionados."
            ),
            trap = "Comunidades de vecinos: Las zonas comunes de un edificio de viviendas de más de 100 kW se inspeccionan cada 10 AÑOS, no cada 5 años.",
            keyConcept = "Inspección inicial OCA: Pública concurrencia, garajes > 25 plazas. Periódicas: 5 años (LPC, ATEX) / 10 años (Edificios > 100 kW).",
            examReference = "ITC-BT-05 §4 y §5"
        ),
        UnderliningItcItem(
            id = "itc-06",
            code = "ITC-BT-06",
            title = "Redes Aéreas para Distribución en BT",
            category = "Distribución",
            freq = "Alta",
            page = "ITC-06",
            redUnderline = listOf(
                "Conductores trenzados en haz (RZ) posados sobre fachada o tensados sobre apoyos con neutro fiador.",
                "Sección mínima en redes aéreas: 16 mm² en aluminio (haz Al/XLPE) o 10 mm² en cobre.",
                "Altura mínima sobre el suelo en pasos de calles o carreteras: 6 metros.",
                "Altura mínima en aceras o zonas no accesibles a vehículos: 2,5 metros (posado) y 4 metros (tensado)."
            ),
            greenUnderline = listOf(
                "Resistencia mecánica mínima a la tracción del haz cableado trenzado de 1.000 daN en el neutro fiador Almelec.",
                "Distancia de seguridad a ventanas, balcones y huecos practicables: Mínimo 1 metro."
            ),
            trap = "Altura de paso sobre carreteras para cables aéreos: La altura libre mínima obligatoria es 6 metros en el punto de máxima flecha a 50°C.",
            keyConcept = "Haz trenzado RZ. Min 16 mm² Al. Alturas: 6m sobre calzada, 2,5m sobre fachada. Distancia a ventanas 1m.",
            examReference = "ITC-BT-06 §2 y §3"
        ),
        UnderliningItcItem(
            id = "itc-07",
            code = "ITC-BT-07",
            title = "Redes Subterráneas para Distribución en BT",
            category = "Distribución",
            freq = "Alta",
            page = "ITC-07",
            redUnderline = listOf(
                "Profundidad mínima de enterramiento en zanja: 0,60 m en aceras y 0,80 m en calzadas transitables.",
                "Sección mínima de conductores subterráneos de distribución: 25 mm² de aluminio o 16 mm² de cobre.",
                "Tendido bajo arena de río, cinta de señalización de advertencia a 0,20 m sobre el tubo y rasilla o placa de protección mecánica."
            ),
            greenUnderline = listOf(
                "Distancias de cruzamiento y paralelismo: 0,20 m con cables de telecomunicación y agua, 0,50 m con tuberías de gas.",
                "Cables tipo RV-K o XZ1 (aislamiento XLPE) aptos para inmersión temporal."
            ),
            trap = "Profundidad de zanja: En calzada transitable por vehículos pesados la profundidad mínima reglamentaria es 0,80 metros (no 0,60 m).",
            keyConcept = "Profundidad: 0.60m en acera / 0.80m en calzada. Min 25 mm² Al. Cinta señalizadora y cruzamiento gas 0.50m.",
            examReference = "ITC-BT-07 §2 y §3"
        ),
        UnderliningItcItem(
            id = "itc-08",
            code = "ITC-BT-08",
            title = "Sistemas de Conexión del Neutro y de las Masas",
            category = "Distribución",
            freq = "Crítica",
            page = "ITC-08",
            redUnderline = listOf(
                "Esquema TT: Neutro de la fuente a tierra y masas de la instalación a toma de tierra independiente (obligatorio en redes públicas de distribución BT en España).",
                "Esquema TN: Neutro puesto a tierra y masas conectadas al neutro (TN-S separado, TN-C común PEN).",
                "Esquema IT: Neutro aislado de tierra o impedante y masas a tierra; garantiza continuidad de servicio ante el primer defecto a masa."
            ),
            greenUnderline = listOf(
                "En esquema TT, la protección contra contactos indirectos se realiza obligatoriamente mediante interruptores diferenciales.",
                "En esquema TN-C, queda prohibido colocar elementos de corte en el conductor neutro/PEN."
            ),
            trap = "¿Cuál es el esquema de conexión estándar y preceptivo para el suministro público en España? El esquema TT. Los esquemas TN e IT se reservan a industrias con transformador propio.",
            keyConcept = "Esquema TT = estándar en España (diferenciales obligatorios). TN-S (PE y N separados). IT (aislado, primer defecto no desconecta).",
            examReference = "ITC-BT-08 §1 y §2"
        ),
        UnderliningItcItem(
            id = "itc-09",
            code = "ITC-BT-09",
            title = "Instalaciones de Alumbrado Exterior",
            category = "Distribución",
            freq = "Alta",
            page = "ITC-09",
            redUnderline = listOf(
                "Caída de tensión máxima admisible en líneas de alumbrado exterior: 3% desde el cuadro de mando.",
                "Toma de tierra independiente con electrodo en la base de cada soporte o báculo, interconectados mediante conductor de cobre desnudo de 35 mm².",
                "Resistencia de puesta a tierra: ≤ 30 Ω en báculos metálicos accesibles al público.",
                "Cables subterráneos con aislamiento mínimo de 0,6/1 kV (tipo RV-K o XZ1)."
            ),
            greenUnderline = listOf(
                "Protección magnetotérmica y diferencial (máximo 300 mA con selectividad o 30 mA en cuadros accesibles).",
                "Interruptores horarios astronómicos o células fotoeléctricas para eficiencia energética obligatoria."
            ),
            trap = "¿Cuál es la caída de tensión máxima en alumbrado público? Es del 3% (más exigente que en interiores debido a las grandes longitudes de tirada).",
            keyConcept = "Caída máx 3%. Tierra báculos ≤ 30 Ω con Cu 35 mm². Tensión aislamiento 0,6/1 kV. Reloj astronómico.",
            examReference = "ITC-BT-09 §3 y §5"
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
            id = "itc-11",
            code = "ITC-BT-11",
            title = "Redes de Distribución y Acometidas",
            category = "Enlace",
            freq = "Media",
            page = "ITC-11",
            redUnderline = listOf(
                "La acometida conecta la red de distribución con la Caja General de Protección (CGP).",
                "Trazado más corto posible, sin curvas pronunciadas y por espacios de dominio público.",
                "Secciones y canalizaciones normalizadas según las normas particulares de la empresa distribuidora."
            ),
            greenUnderline = listOf(
                "Protección mecánica rígida en los puntos de entrada al edificio hasta una altura mínima de 2,5 m."
            ),
            trap = "La acometida finaliza exactamente en los bornes de entrada de la Caja General de Protección (CGP). A partir de la CGP comienza la LGA.",
            keyConcept = "Conexión Red-CGP. Dominio público. Protección mecánica en fachadas.",
            examReference = "ITC-BT-11 §1"
        ),
        UnderliningItcItem(
            id = "itc-12",
            code = "ITC-BT-12",
            title = "Esquemas para Instalaciones de Enlace",
            category = "Enlace",
            freq = "Alta",
            page = "ITC-12",
            redUnderline = listOf(
                "Esquema 1: Para uno o dos usuarios con contadores individuales (CPM en fachada).",
                "Esquema 2: Contadores concentrados en un único lugar (armario o local técnico).",
                "Esquema 3: Contadores concentrados en varios lugares (módulos o armarios por plantas en edificios en altura)."
            ),
            greenUnderline = listOf(
                "Elementos de la instalación de enlace: CGP, LGA, Contadores, DI, ICP/IGA y CGMP."
            ),
            trap = "¿Cuándo NO existe Línea General de Alimentación (LGA)? En suministros a uno o dos usuarios con Caja de Protección y Medida (CPM) unificada.",
            keyConcept = "Esquema 1 (CPM individual sin LGA). Esquema 2 (Local único). Esquema 3 (Varios armarios por planta).",
            examReference = "ITC-BT-12 §1 y §2"
        ),
        UnderliningItcItem(
            id = "itc-13",
            code = "ITC-BT-13",
            title = "Cajas Generales de Protección (CGP y CPM)",
            category = "Enlace",
            freq = "Alta",
            page = "ITC-13",
            redUnderline = listOf(
                "Ubicación obligatoria en el límite de la propiedad o fachada exterior del edificio, en zona de dominio público.",
                "Grado de protección mínimo contra impactos: IK08 (e IK09 en zonas de acceso público rodado).",
                "Altura de montaje: Entre 0,50 m y 2,00 m desde la rasante del suelo.",
                "Equipadas con cortacircuitos fusibles tipo cuchilla (NH o cilindricos) de alto poder de corte (≥ 100 kA)."
            ),
            greenUnderline = listOf(
                "Caja de Protección y Medida (CPM): Agrupa CGP y contador para suministros individuales de hasta 50 kW o 100 kW monofásico/trifásico."
            ),
            trap = "¿Dónde se coloca la CGP? Siempre en la fachada exterior o límite de la finca, accesible permanentemente a la distribuidora sin necesidad de llaves comunitarias.",
            keyConcept = "Fachada pública. Altura 0.5-2.0m. Fusibles alto poder de corte. IK08/IK09.",
            examReference = "ITC-BT-13 §1"
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
            id = "itc-16",
            code = "ITC-BT-16",
            title = "Contadores: Ubicación y Sistemas de Instalación",
            category = "Enlace",
            freq = "Alta",
            page = "ITC-16",
            redUnderline = listOf(
                "Local de contadores obligatorio si el número de contadores es superior a 16.",
                "Armario de contadores permitido para hasta 16 contadores (o módulos por planta).",
                "Dimensiones mínimas del local: Altura libre 2,30 m, pasillo de servicio mínimo 1,10 m de anchura frente a los módulos.",
                "Ventilación directa al exterior o mediante conducto independiente, con extintor de CO2 de 5 kg junto a la puerta."
            ),
            greenUnderline = listOf(
                "Puerta con apertura hacia el exterior, resistencia al fuego EI2 60-C5 y cerradura normalizada de compañía distribuidora."
            ),
            trap = "¿Cuándo es obligatorio local de contadores en vez de armario? A partir de MÁS DE 16 CONTADORES es obligatorio destinar un local técnico exclusivo.",
            keyConcept = "Local exclusivo > 16 contadores. Altura 2,30m, pasillo 1,10m, puerta EI 60 hacia afuera, extintor CO2.",
            examReference = "ITC-BT-16 §2"
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
            id = "itc-19",
            code = "ITC-BT-19",
            title = "Instalaciones Interiores: Prescripciones Generales",
            category = "Interiores",
            freq = "Crítica",
            page = "ITC-19",
            redUnderline = listOf(
                "Sección mínima para alumbrado: 1,5 mm² de cobre.",
                "Sección mínima para tomas de corriente generales: 2,5 mm² de cobre.",
                "Caída de tensión máxima admisible desde el origen de la instalación interior (CGMP) hasta los puntos de utilización: 3% para alumbrado y 5% para fuerza/otros usos."
            ),
            greenUnderline = listOf(
                "Colores de identificación de conductores: Fase = Marrón, Negro o Gris; Neutro = Azul claro; Conductor de protección (tierra) = Verde-Amarillo.",
                "El conductor neutro NO podrá ser común a varios circuitos diferentes."
            ),
            trap = "Caídas de tensión en interiores: 3% para alumbrado y 5% para fuerza. En viviendas con contadores centralizados, sumando la DI (1,5%) y el interior (3%), la caída total acumulada máxima no puede superar el 4,5% en alumbrado.",
            keyConcept = "Secciones mínimas: 1,5 mm² luz / 2,5 mm² fuerza. Caída máx: 3% alumbrado / 5% fuerza. Colores normalizados.",
            examReference = "ITC-BT-19 §2.2"
        ),
        UnderliningItcItem(
            id = "itc-20",
            code = "ITC-BT-20",
            title = "Sistemas de Instalación de Tubos, Canales y Bandejas",
            category = "Interiores",
            freq = "Media",
            page = "ITC-20",
            redUnderline = listOf(
                "Canalizaciones fijas en superficie, empotradas en obra, aéreas o enterradas.",
                "Bandejas perforadas o ciegas: Los conductores deben ser aislados con tensión asignada mínima de 0,6/1 kV si son accesibles.",
                "Canales protectores con tapa desmontable solo mediante el uso de herramientas si alojan conductores unipolares sin cubierta (H07V-K)."
            ),
            greenUnderline = listOf(
                "Prohibición de tender cables directamente sobre falsos techos sin tubo protector o bandeja homologada."
            ),
            trap = "¿Se puede meter cable H07V-K suelto en una bandeja de rejilla abierta? No, en bandejas perforadas o rejillas abiertas se exigen cables con cubierta tipo manguera (0,6/1 kV).",
            keyConcept = "Bandejas exigen cables con cubierta (0,6/1 kV). Canales con tapa fija con útiles.",
            examReference = "ITC-BT-20 §2"
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
            id = "itc-22",
            code = "ITC-BT-22",
            title = "Protección contra Sobreintensidades",
            category = "Interiores",
            freq = "Crítica",
            page = "ITC-22",
            redUnderline = listOf(
                "Protección obligatoria frente a dos fenómenos: Sobrecargas (lentas) y Cortocircuitos (instantáneos).",
                "Condición de sobrecarga: IB ≤ In ≤ Iz (Corriente de empleo ≤ Calibre nominal del PIA ≤ Corriente máxima admisible del cable).",
                "Segunda condición de disparo en sobrecarga: I2 ≤ 1,45 * Iz (Corriente convencional de disparo ≤ 1,45 veces Iz).",
                "Poder de corte del interruptor automático: Debe ser superior a la corriente de cortocircuito máxima prevista en el punto de instalación."
            ),
            greenUnderline = listOf(
                "Curvas de disparo magnetotérmico: Curva B (3-5 In), Curva C (5-10 In, estándar doméstico), Curva D (10-20 In, motores con alto pico de arranque)."
            ),
            trap = "Fórmula mágica de sobrecarga: IB ≤ In ≤ Iz y I2 ≤ 1.45*Iz. El calibre In del magnetotérmico jamás puede superar la intensidad máxima admisible Iz del cable que protege.",
            keyConcept = "Regla de oro: IB ≤ In ≤ Iz. Curvas B, C y D. Poder de corte superior a Icc máxima.",
            examReference = "ITC-BT-22 §1"
        ),
        UnderliningItcItem(
            id = "itc-23",
            code = "ITC-BT-23",
            title = "Protección contra Sobretensiones",
            category = "Interiores",
            freq = "Alta",
            page = "ITC-23",
            redUnderline = listOf(
                "Sobretensiones transitorias: Debidas a descargas atmosféricas (rayos) o conmutaciones de red. Protegidas con descargadores DPS Tipo 1, Tipo 2 o Tipo 3.",
                "Sobretensiones permanentes: Debidas a rotura o desconexión del neutro en redes trifásicas (hace subir la tensión hasta 400 V). Protegidas con bobina de disparo asociada al IGA.",
                "Obligatorio en edificios alimentados por red aérea o en zonas con nivel ceráunico elevado (Nk > 20 días de tormenta al año)."
            ),
            greenUnderline = listOf(
                "Nivel de tensión soportada a impulsos según categoría de sobretensión: Cat IV (6 kV origen), Cat III (4 kV cuadros), Cat II (2,5 kV electrodomésticos), Cat I (1,5 kV electrónica)."
            ),
            trap = "Diferencia entre transitorias y permanentes: El protector transitorio deriva la onda a tierra mediante varistores/descargadores de gas sin abrir el IGA; el permanente provoca la apertura mecánica del IGA para aislar los receptores.",
            keyConcept = "Transitorias (varistores a tierra). Permanentes (disparo de IGA por rotura de neutro). Categorías I a IV.",
            examReference = "ITC-BT-23 §1 y §3"
        ),
        UnderliningItcItem(
            id = "itc-24",
            code = "ITC-BT-24",
            title = "Protección contra Contactos Directos e Indirectos",
            category = "Interiores",
            freq = "Crítica",
            page = "ITC-24",
            redUnderline = listOf(
                "Protección contra contactos directos: Aislamiento de partes activas, barreras o envolventes (IP2X mínimo), y protección complementaria mediante interruptor diferencial de alta sensibilidad (≤ 30 mA).",
                "Protección contra contactos indirectos: Desconexión automática de la alimentación coordinando la toma de tierra (Ra) con el diferencial: Ra * IΔn ≤ Ul (donde Ul = 50 V o 24 V).",
                "Uso de Muy Baja Tensión de Seguridad (MBTS) sin puesta a tierra a ≤ 12V en inmersión o ≤ 50V."
            ),
            greenUnderline = listOf(
                "Doble aislamiento (Clase II) o separación eléctrica por transformador de aislamiento galvánico de relación 1:1."
            ),
            trap = "Un diferencial de 30 mA NO evita el contacto directo, solo actúa como protección COMPLEMENTARIA reduciendo el tiempo de paso de corriente por el cuerpo humano a milisegundos.",
            keyConcept = "Contacto directo (IP2X, aislamiento). Contacto indirecto (Ra * IΔn ≤ 50V con ID ≤ 30mA). MBTS.",
            examReference = "ITC-BT-24 §1 y §2"
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
            id = "itc-26",
            code = "ITC-BT-26",
            title = "Prescripciones de Montaje en Viviendas",
            category = "Interiores",
            freq = "Media",
            page = "ITC-26",
            redUnderline = listOf(
                "Tomas de corriente con toma de tierra incorporada tipo Schuko de 16 A con obturadores de seguridad infantil.",
                "Puntos de luz: Todo punto de luz en techo debe incorporar conductor de protección PE (amarillo-verde) aunque la luminaria sea provisional.",
                "Altura de tomas generales: Mínimo 30 cm sobre el pavimento acabado (excepto en cocinas sobre encimera a 1,10 m)."
            ),
            greenUnderline = listOf(
                "Interruptores de alumbrado situados a una altura comprendida entre 0,90 m y 1,20 m junto al marco de acceso."
            ),
            trap = "¿Es obligatorio llevar el hilo de tierra a un punto de luz de techo de bombilla simple? SÍ, la ITC-26 obliga a llevar conductor de protección a todos los puntos de utilización sin excepción.",
            keyConcept = "Obturadores infantiles en tomas. Tierra en todos los puntos de luz. Alturas: enchufes 30cm, luz 0.9-1.2m.",
            examReference = "ITC-BT-26 §2"
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
            id = "itc-29",
            code = "ITC-BT-29",
            title = "Locales con Riesgo de Incendio o Explosión (ATEX)",
            category = "Especiales",
            freq = "Crítica",
            page = "ITC-29",
            redUnderline = listOf(
                "Clasificación de zonas con gases/vapores: Zona 0 (presencia continua), Zona 1 (probable en servicio normal), Zona 2 (improbable y corta duración).",
                "Clasificación de zonas con polvos inflamables: Zona 20, Zona 21 y Zona 22.",
                "Material eléctrico con marcado Ex antideflagrante, seguridad aumentada (Exe) o seguridad intrínseca (Exi).",
                "Instalación exclusiva por Instalador de Categoría Especialista (IBTE)."
            ),
            greenUnderline = listOf(
                "Sellado de tubos con cortafuegos en los pasos de frontera entre zonas clasificadas y no clasificadas."
            ),
            trap = "Los garajes de más de 5 plazas son considerados locales con riesgo de desprendimiento de gases inflamables (clasificados Zona 2 hasta una altura de 0,60 m del suelo).",
            keyConcept = "Zonas 0/1/2 (gases) y 20/21/22 (polvos). Marcado Ex. IBTE Especialista. Sellado de canalizaciones.",
            examReference = "ITC-BT-29 §3 y §5"
        ),
        UnderliningItcItem(
            id = "itc-30",
            code = "ITC-BT-30",
            title = "Locales de Características Especiales",
            category = "Especiales",
            freq = "Alta",
            page = "ITC-30",
            redUnderline = listOf(
                "Locales húmedos: Grado de protección IPX1 mínimo; canalizaciones estancas.",
                "Locales mojados: Grado de protección IPX4 mínimo; mecanismos estancos; tensión de contacto límite 24 V.",
                "Locales polvorientos: Grado de protección IP5X mínimo (o IP6X si el polvo es conductor).",
                "Locales a temperatura muy elevada (> 40°C): Cables con aislamiento especial de silicona o termoestable a 90°C."
            ),
            greenUnderline = listOf(
                "Distancia de separación de luminarias a materiales fácilmente combustibles: Mínimo 0,5 metros."
            ),
            trap = "Diferencia entre local húmedo y mojado: En local húmedo el vapor no condensa en gotas en paredes (IPX1); en mojado el agua forma regueros y gotas continuas (IPX4, límite 24 V).",
            keyConcept = "Húmedo (IPX1). Mojado (IPX4, 24V). Polvoriento (IP5X/IP6X). Aislamientos térmicos especiales.",
            examReference = "ITC-BT-30 §1 a §4"
        ),
        UnderliningItcItem(
            id = "itc-31",
            code = "ITC-BT-31",
            title = "Instalaciones en Piscinas y Fuentes",
            category = "Especiales",
            freq = "Alta",
            page = "ITC-31",
            redUnderline = listOf(
                "Volumen 0: Interior del vaso de la piscina. Grado de protección IPX8. Alimentación exclusiva a MBTS ≤ 12 V CA.",
                "Volumen 1: Franja de 2 metros alrededor del vaso y hasta 2,5 m de altura. Grado IPX5 (o IPX4 en interiores). Prohibidas tomas de corriente.",
                "Volumen 2: Franja de 1,5 m a continuación del volumen 1. Tomas permitidas protegidas por diferencial de 30 mA o MBTS a 12V.",
                "Unión equipotencial suplementaria obligatoria conectando escaleras metálicas, barandillas y armaduras de hormigón."
            ),
            greenUnderline = listOf(
                "Transformadores de seguridad para focos sumergidos situados fuera de los volúmenes 0 y 1."
            ),
            trap = "Focos sumergidos en el vaso de la piscina: Tensión máxima permitida 12 V en CA (MBTS) con transformador de seguridad fuera de los volúmenes 0 y 1.",
            keyConcept = "Volumen 0 (IPX8, MBTS ≤ 12V). Volumen 1 (2m, IPX5). Volumen 2 (1.5m, ID 30mA). Equipotencialidad obligatoria.",
            examReference = "ITC-BT-31 §2"
        ),
        UnderliningItcItem(
            id = "itc-32",
            code = "ITC-BT-32",
            title = "Máquinas de Elevación y Transporte",
            category = "Especiales",
            freq = "Media",
            page = "ITC-32",
            redUnderline = listOf(
                "Acometida independiente con interruptor de corte omnipolar con bloqueo por candado en el cuarto de máquinas.",
                "Circuito de alumbrado de cabina y hueco de ascensor independiente del circuito de fuerza motriz del motor.",
                "Cables manguera flexibles colgantes con armadura textil resistente a la tracción y torsión continuada."
            ),
            greenUnderline = listOf(
                "Dispositivo de socorro de parada de emergencia en foso y techo de cabina."
            ),
            trap = "El alumbrado de cabina del ascensor NO puede desconectarse cuando se corta el interruptor principal del motor de tracción para mantenimiento.",
            keyConcept = "Línea independiente motor/luz. Interruptor bloqueable. Mangueras flexibles suspendidas.",
            examReference = "ITC-BT-32 §2 y §3"
        ),
        UnderliningItcItem(
            id = "itc-33",
            code = "ITC-BT-33",
            title = "Instalaciones Provisionales y de Obra",
            category = "Especiales",
            freq = "Alta",
            page = "ITC-33",
            redUnderline = listOf(
                "Cuadros de obra con grado de protección IP44 mínimo e IK08 contra impactos mecánicos.",
                "Protección diferencial obligatoria de alta sensibilidad (≤ 30 mA) en todas las tomas de corriente.",
                "Cables con cubierta de policloropreno resistente al agua y a la abrasión (tipo H07RN-F).",
                "Requiere proyecto si la potencia instalada supera los 50 kW."
            ),
            greenUnderline = listOf(
                "Puesta a tierra de obra con electrodo independiente y revisión mensual registrada."
            ),
            trap = "¿Qué tipo de cable se exige para tender por el suelo en una obra? Manguera de goma pesada tipo H07RN-F. Queda prohibido el cable de PVC doméstico.",
            keyConcept = "Cuadros IP44/IK08. Diferenciales 30mA en todas las tomas. Cable H07RN-F. Proyecto si > 50 kW.",
            examReference = "ITC-BT-33 §2 y §4"
        ),
        UnderliningItcItem(
            id = "itc-34",
            code = "ITC-BT-34",
            title = "Instalaciones en Ferias y Stands",
            category = "Especiales",
            freq = "Media",
            page = "ITC-34",
            redUnderline = listOf(
                "Instalaciones temporales en recintos feriales con cuadros generales equipados con parada de emergencia visible.",
                "Cables no propagadores de la llama libres de halógenos en zonas de concurrencia pública.",
                "Todas las masas metálicas de casetas y atracciones unidas a la red de tierra general con conductor continuo."
            ),
            greenUnderline = listOf(
                "Inspección previa por OCA antes de la inauguración oficial si la potencia total es > 50 kW."
            ),
            trap = "En ferias y atracciones, la protección diferencial de 30 mA es obligatoria para cada atracción o caseta individualizada.",
            keyConcept = "Cuadros de feria con seta de emergencia. Diferenciales 30mA. Equipotencialidad de atracciones.",
            examReference = "ITC-BT-34 §3"
        ),
        UnderliningItcItem(
            id = "itc-35",
            code = "ITC-BT-35",
            title = "Establecimientos Agrícolas y Hortícolas",
            category = "Especiales",
            freq = "Media",
            page = "ITC-35",
            redUnderline = listOf(
                "Locales con presencia de animales de granja: Tensión de contacto límite reducida a 24 V (o 12 V en zonas muy mojadas).",
                "Red de equipotencialidad en el suelo para evitar tensiones de paso peligrosas para el ganado.",
                "Grado de protección IP54 mínimo para polvo y salpicaduras de purines/amoníaco."
            ),
            greenUnderline = listOf(
                "Diferenciales con IΔn ≤ 30 mA para circuitos de tomas y ≤ 300 mA selectivos contra riesgo de incendio."
            ),
            trap = "Los animales son extremadamente sensibles a la tensión eléctrica; por ello la tensión límite de seguridad es 24 V y se exige mallazo equipotencial en el suelo.",
            keyConcept = "Tensión límite 24V. IP54. Mallazo en suelo para evitar tensión de paso en ganado.",
            examReference = "ITC-BT-35 §2 y §3"
        ),
        UnderliningItcItem(
            id = "itc-36",
            code = "ITC-BT-36",
            title = "Instalaciones a Muy Baja Tensión (MBT)",
            category = "Especiales",
            freq = "Media",
            page = "ITC-36",
            redUnderline = listOf(
                "MBTS (Muy Baja Tensión de Seguridad): Circuito aislado de tierra con fuente de seguridad (transformador de aislamiento EN 61558-2-6).",
                "MBTP (Muy Baja Tensión de Protección): Circuito con puesta a tierra deliberada.",
                "Límites de tensión: ≤ 50 V en CA eficaz y ≤ 75 V en CC sin ondulación."
            ),
            greenUnderline = listOf(
                "Las clavijas y bases de enchufe de MBT no deben poder penetrar en tomas de 230V convencionales."
            ),
            trap = "¿Pueden conectarse las masas de un circuito MBTS a la toma de tierra del edificio? NO, en MBTS las masas están terminantemente aisladas de tierra.",
            keyConcept = "MBTS (sin tierra, aislada). MBTP (con tierra). Límites: CA ≤ 50V / CC ≤ 75V.",
            examReference = "ITC-BT-36 §1"
        ),
        UnderliningItcItem(
            id = "itc-37",
            code = "ITC-BT-37",
            title = "Instalaciones a Tensiones Especiales y Rótulos",
            category = "Especiales",
            freq = "Baja",
            page = "ITC-37",
            redUnderline = listOf(
                "Rótulos luminosos de descarga de alta tensión (tubos de neón > 1.000 V).",
                "Interruptor de corte para bomberos exterior visible y accesible con indicación clara de desconexión.",
                "Cables de alta tensión resistentes al ozono con pantallas protectoras puestas a tierra."
            ),
            greenUnderline = listOf(
                "Transformadores de neón con protección contra circuito abierto y corriente de fuga."
            ),
            trap = "Todo rótulo luminoso de alta tensión en fachada debe disponer de un interruptor de corte general de bomberos operable con pértiga desde la calle.",
            keyConcept = "Rótulos de neón. Interruptor de corte de bomberos en fachada. Cables anti-ozono.",
            examReference = "ITC-BT-37 §2"
        ),
        UnderliningItcItem(
            id = "itc-38",
            code = "ITC-BT-38",
            title = "Quirófanos y Salas de Intervención",
            category = "Especiales",
            freq = "Crítica",
            page = "ITC-38",
            redUnderline = listOf(
                "Esquema IT Médico obligatorio para equipos electromédicos de soporte vital dentro de la zona del paciente.",
                "Transformador de aislamiento galvánico monofásico de potencia entre 0,5 kVA y 10 kVA con vigilancia continua de aislamiento (VMA).",
                "Suministro especial complementario: Entrada en servicio en menos de 0,5 segundos (corte breve Clase 0,5) con autonomía mínima de 2 horas.",
                "Suelo antielectrostático con resistencia entre 50 kΩ y 1 MΩ para evitar chispas inflamables de anestésicos."
            ),
            greenUnderline = listOf(
                "Embarrado de equipotencialidad (EE) exclusivo en cada quirófano uniendo masas de equipos y tomas de tierra con cables de 16 mm².",
                "Diferenciales Clase A o B con sensibilidad de 30 mA para circuitos fuera de la zona de soporte vital."
            ),
            trap = "¿Por qué se usa el esquema IT en quirófanos? Porque ante un primer defecto a masa, el sistema NO corta la corriente, manteniendo en marcha los respiradores y monitores de quirófano.",
            keyConcept = "Esquema IT Médico obligatorio. Transformador 0.5-10 kVA con VMA. Suministro socorro < 0.5s (2h). Suelo conductor 50kΩ-1MΩ.",
            examReference = "ITC-BT-38 §2 y §3"
        ),
        UnderliningItcItem(
            id = "itc-39",
            code = "ITC-BT-39",
            title = "Instalaciones de Cercas Eléctricas para Ganado",
            category = "Especiales",
            freq = "Baja",
            page = "ITC-39",
            redUnderline = listOf(
                "Alimentadas mediante electrificadores homologados que emiten impulsos de corta duración (≤ 0,1 s a intervalos ≥ 1 s).",
                "Toma de tierra del electrificador separada al menos 10 metros de cualquier otra toma de tierra de edificios o líneas eléctricas.",
                "Carteles de aviso de peligro triangulares amarillos cada 50 metros en caminos públicos."
            ),
            greenUnderline = listOf(
                "Prohibición de conectar cercas a más de un electrificador simultáneamente."
            ),
            trap = "Distancia de la toma de tierra de la cerca eléctrica a la tierra de una vivienda: Mínimo 10 metros de separación para evitar inducir pulsos en la red doméstica.",
            keyConcept = "Impulsos homologados. Tierra de cerca separada ≥ 10m. Carteles cada 50m.",
            examReference = "ITC-BT-39 §2"
        ),
        UnderliningItcItem(
            id = "itc-40",
            code = "ITC-BT-40",
            title = "Instalaciones Generadoras de Baja Tensión",
            category = "Especiales",
            freq = "Crítica",
            page = "ITC-40",
            redUnderline = listOf(
                "Grupos electrógenos y sistemas fotovoltaicos en autoconsumo con o sin excedentes.",
                "Enclavamiento mecánico y eléctrico obligatorio para impedir la interconexión involuntaria o retorno de tensión hacia la red pública durante cortes.",
                "Protección de desacoplamiento con vigilancia de tensión (±10%) y frecuencia (±1 Hz) con desconexión en menos de 0,5 s.",
                "Sección del conductor neutro de generadores dimensionada al 100% de la fase por presencia de armónicos."
            ),
            greenUnderline = listOf(
                "Instalación de generadores interconectados reservada a Instalador Autorizado Especialista (IBTE)."
            ),
            trap = "¿Qué exige la norma para evitar alimentar la red pública cuando hay una avería en el transformador? Relé de protección de desacoplamiento automático y conmutador con enclavamiento mecánico.",
            keyConcept = "Autoconsumo y grupos. Enclavamiento mecánico obligatorio. Relé de desacoplamiento V/F. IBTE Especialista.",
            examReference = "ITC-BT-40 §3 y §5"
        ),
        UnderliningItcItem(
            id = "itc-41",
            code = "ITC-BT-41",
            title = "Caravanas y Parques de Caravanas",
            category = "Especiales",
            freq = "Media",
            page = "ITC-41",
            redUnderline = listOf(
                "Cada parcela de camping o parque debe contar con toma de corriente individual con su propio interruptor diferencial de 30 mA y PIA de 16 A.",
                "Bases de enchufe industriales tipo CETAC azules (2P+T 16A 230V) con grado de estanqueidad IP44 mínimo.",
                "Altura de las tomas: Entre 0,50 m y 1,50 m sobre el terreno para evitar inundaciones."
            ),
            greenUnderline = listOf(
                "Máximo de 4 bases de enchufe agrupadas por pedestal de distribución."
            ),
            trap = "En campings, cada toma de corriente para caravana debe tener su propio diferencial individual de 30 mA; no se permite compartir un diferencial para varias tomas de parcelas.",
            keyConcept = "1 diferencial 30mA y 1 PIA por cada toma. Base CETAC azul IP44. Altura 0.5-1.5m.",
            examReference = "ITC-BT-41 §3"
        ),
        UnderliningItcItem(
            id = "itc-42",
            code = "ITC-BT-42",
            title = "Puertos y Marinas para Barcos de Recreo",
            category = "Especiales",
            freq = "Media",
            page = "ITC-42",
            redUnderline = listOf(
                "Torretas de pantalán con grado de protección IP56 mínimo contra chorros potentes de agua salada y corrosión marina.",
                "Tomas de corriente industriales (2P+T o 3P+N+T) con enclavamiento mecánico que impida conectar o desconectar bajo carga.",
                "Cada toma protegida individualmente por diferencial de 30 mA y magnetotérmico.",
                "Separación galvánica o aislamiento para prevenir corrosión galvánica de los cascos de embarcaciones."
            ),
            greenUnderline = listOf(
                "Cables submarinos o sobre pasarelas flotantes con resistencia química y mecánica al hidrocarburo."
            ),
            trap = "Grado de protección en torretas de pantalán en puertos deportivos: Mínimo IP56 (muy superior al IP44 ordinario debido al oleaje y salitre).",
            keyConcept = "Torretas IP56 en pantalán. Diferencial 30mA por toma con enclavamiento. Protección anticorrosión.",
            examReference = "ITC-BT-42 §3 y §4"
        ),
        UnderliningItcItem(
            id = "itc-43",
            code = "ITC-BT-43",
            title = "Receptores: Prescripciones Generales",
            category = "Receptores",
            freq = "Media",
            page = "ITC-43",
            redUnderline = listOf(
                "Clasificación de receptores por protección contra choques: Clase 0 (prohibidos en BT), Clase I (toma de tierra), Clase II (doble aislamiento), Clase III (MBTS).",
                "Todo receptor debe llevar placa de características con tensión, potencia, corriente asignada y Marcado CE.",
                "Poder de conexión y desconexión adecuado a la naturaleza inductiva o resistiva de la carga."
            ),
            greenUnderline = listOf(
                "Separación y refrigeración de receptores que disipen calor respecto a superficies inflamables."
            ),
            trap = "¿Están permitidos los aparatos eléctricos de Clase 0 (sin tierra ni doble aislamiento)? NO, en España los aparatos de Clase 0 están expresamente prohibidos.",
            keyConcept = "Clases de aislamiento I (tierra), II (doble capa), III (MBTS). Clase 0 prohibida.",
            examReference = "ITC-BT-43 §1 y §2"
        ),
        UnderliningItcItem(
            id = "itc-44",
            code = "ITC-BT-44",
            title = "Receptores de Alumbrado e Iluminación",
            category = "Receptores",
            freq = "Alta",
            page = "ITC-44",
            redUnderline = listOf(
                "Luminarias fluorescentes o de descarga: La potencia de cálculo debe multiplicarse por un factor mínimo de 1,8 para tener en cuenta balastros y armónicos.",
                "Compensación individual o en bloque del factor de potencia para alcanzar cos φ ≥ 0,90.",
                "Conexión a tierra de partes metálicas de luminarias Clase I mediante borna señalizada."
            ),
            greenUnderline = listOf(
                "Portalámparas: El casquillo roscado exterior debe conectarse obligatoriamente al conductor neutro, y el borne central de fondo a la fase."
            ),
            trap = "Cálculo de potencia para tubos fluorescentes y lámparas de descarga: Se multiplica la potencia en vatios por 1,8 (ej. 4x18W = 72W * 1,8 = 129,6 W a prever).",
            keyConcept = "Factor 1,8 para lámparas de descarga. Casquillo neutro / contacto central fase. Cos φ ≥ 0,90.",
            examReference = "ITC-BT-44 §2 y §3"
        ),
        UnderliningItcItem(
            id = "itc-45",
            code = "ITC-BT-45",
            title = "Receptores de Calefacción y Termos",
            category = "Receptores",
            freq = "Media",
            page = "ITC-45",
            redUnderline = listOf(
                "Aparatos de calefacción de potencia > 3 kW deben conectarse mediante circuito independiente y conexión fija directa sin clavija de enchufe ordinaria.",
                "Termostato de control acompañado obligatoriamente de un limitador térmico de seguridad no rearmable automáticamente (corte térmico).",
                "Calefacción por suelo radiante: Malla metálica conectada a tierra sobre los cables calefactores."
            ),
            greenUnderline = listOf(
                "Cables resistentes al calor tipo silicona en las proximidades inmediatas de las resistencias calefactoras."
            ),
            trap = "Los termos y calderas eléctricas deben incorporar doble protección térmica: termostato de regulación y limitador de seguridad independiente de rearme manual.",
            keyConcept = "Potencia > 3 kW conexión fija. Limitador térmico de seguridad obligatorio. Suelo radiante con tierra.",
            examReference = "ITC-BT-45 §2"
        ),
        UnderliningItcItem(
            id = "itc-46",
            code = "ITC-BT-46",
            title = "Receptores: Motores Eléctricos",
            category = "Receptores",
            freq = "Crítica",
            page = "ITC-46",
            redUnderline = listOf(
                "Limitación de corriente de arranque para motores de más de 0,75 kW: La relación Iarranque/Inominal no puede superar los valores de la tabla reglamentaria.",
                "Arranque estrella-triángulo (Y-Δ), arrancadores suaves o variadores de frecuencia obligatorios para potencias elevadas.",
                "Los conductores de alimentación de un solo motor deben dimensionarse para una intensidad mínima del 125% de la corriente nominal a plena carga (In * 1,25).",
                "Para varios motores, el cable se calcula para el 125% del motor de mayor potencia más el 100% de la suma de los restantes."
            ),
            greenUnderline = listOf(
                "Protección térmica contra sobrecargas mediante relé térmico o guardamotor calibrado a la In del motor.",
                "Protección contra falta de fase para evitar el quemado de devanados en motores trifásicos."
            ),
            trap = "¿Cómo se calcula la sección del cable para alimentar un motor de 10 A? Se calcula para 12,5 A (10 A * 1,25) para absorber el calentamiento durante los arranques repetidos.",
            keyConcept = "Cable motor único = 125% In. Varios motores = 125% mayor + suma restantes. Guardamotor. Arranque estrella-triángulo.",
            examReference = "ITC-BT-46 §3 y §4"
        ),
        UnderliningItcItem(
            id = "itc-47",
            code = "ITC-BT-47",
            title = "Transformadores, Reactancias y Autotransformadores",
            category = "Receptores",
            freq = "Media",
            page = "ITC-47",
            redUnderline = listOf(
                "Transformadores de aislamiento galvánico de seguridad con aislamiento doble o reforzado entre primario y secundario.",
                "Autotransformadores prohibidos para alimentar circuitos MBTS de seguridad porque no existe separación galvánica.",
                "Protección en primario y secundario contra cortocircuitos y sobrecargas mediante fusibles o disyuntores coordinados."
            ),
            greenUnderline = listOf(
                "Refrigeración adecuada y envolventes con ventilación para disipar pérdidas en el hierro y cobre."
            ),
            trap = "¿Se puede usar un autotransformador para una instalación a 24V de seguridad? ¡NO! El autotransformador tiene bobinado común y no proporciona aislamiento galvánico.",
            keyConcept = "Transformador de aislamiento (primario y secundario separados). Autotransformador prohibido en MBTS.",
            examReference = "ITC-BT-47 §2"
        ),
        UnderliningItcItem(
            id = "itc-48",
            code = "ITC-BT-48",
            title = "Condensadores y Corrección del Factor de Potencia",
            category = "Receptores",
            freq = "Media",
            page = "ITC-48",
            redUnderline = listOf(
                "Baterías de condensadores para elevar el factor de potencia (cos φ ≥ 0,95) y evitar penalizaciones por energía reactiva.",
                "Descargadores de resistencia automáticos en bornes para reducir la tensión remanente a menos de 50 V en menos de 1 minuto tras desconexión.",
                "Conductores dimensionados para un mínimo del 150% (1,5 veces) de la corriente nominal asignada del condensador debido a corrientes de cierre y armónicos."
            ),
            greenUnderline = listOf(
                "Interruptores automáticos con contactores específicos para cargas capacitivas provistos de resistencias de pre-inserción."
            ),
            trap = "Sección de cable para baterías de condensadores: Debe dimensionarse para el 150% de la corriente nominal (In * 1,5), no para el 100%.",
            keyConcept = "Compensación cos φ. Conductores = 150% In. Resistencia de descarga rápida a < 50V en 1 min.",
            examReference = "ITC-BT-48 §2 y §3"
        ),
        UnderliningItcItem(
            id = "itc-49",
            code = "ITC-BT-49",
            title = "Instalaciones Eléctricas en Muebles",
            category = "Interiores",
            freq = "Baja",
            page = "ITC-49",
            redUnderline = listOf(
                "Muebles de cocina, baño o expositores con cableado incorporado.",
                "Cables con cubierta protectora tipo manguera (H05VV-F) guiados por canales o grapados sin riesgo de pellizco en cajones o bisagras.",
                "Luminarias montadas en muebles con marcado de inflamabilidad (símbolo F) o LED de baja emisión térmica."
            ),
            greenUnderline = listOf(
                "Cajas de empalme estancas y mecanismos con fijación mecánica sólida al cuerpo del mueble."
            ),
            trap = "Queda prohibido utilizar cable unipolar simple suelto sin tubo en el interior de muebles de madera; siempre manguera con doble cubierta.",
            keyConcept = "Cables manguera H05VV-F. Símbolo F en luminarias. Protección contra pellizcos en partes móviles.",
            examReference = "ITC-BT-49 §1"
        ),
        UnderliningItcItem(
            id = "itc-50",
            code = "ITC-BT-50",
            title = "Instalaciones de Saunas",
            category = "Especiales",
            freq = "Media",
            page = "ITC-50",
            redUnderline = listOf(
                "Volumen 1: Zona del calentador (solo el propio calefactor).",
                "Volumen 2: Sin prescripción especial de resistencia térmica pero grado IP24.",
                "Volumen 3: Zona superior a 1 m del suelo; cables con aislamiento de silicona resistente a 170°C.",
                "Volumen 4: Zona del techo (a menos de 0,5 m del techo); solo aparatos de mando del calentador o sensores térmicos.",
                "Prohibido instalar tomas de corriente en todo el recinto de la sauna."
            ),
            greenUnderline = listOf(
                "Protección por interruptor diferencial de 30 mA para todos los circuitos de la sauna."
            ),
            trap = "¿Se pueden colocar enchufes dentro de una cabina de sauna? ¡NO, en ningún volumen de la sauna se admiten tomas de corriente!",
            keyConcept = "Volúmenes 1 a 4. Cables de silicona resistentes a 170°C en zonas altas. Prohibidas tomas de corriente.",
            examReference = "ITC-BT-50 §2 y §3"
        ),
        UnderliningItcItem(
            id = "itc-51",
            code = "ITC-BT-51",
            title = "Domótica y Gestión Técnica de la Energía",
            category = "Interiores",
            freq = "Alta",
            page = "ITC-51",
            redUnderline = listOf(
                "Sistemas de automatización, monitorización energética, confort y seguridad en edificios inteligentes.",
                "Separación física y dieléctrica entre cables de bus de datos (KNX, LonWorks, etc.) y cables de potencia de 230V/400V (separación mínima o aislamiento 4 kV).",
                "Topologías en estrella, bus lineal o árbol con protección contra sobretensiones en líneas de datos exteriores."
            ),
            greenUnderline = listOf(
                "En caso de caída de suministro auxiliar, los actuadores deben adoptar una posición de seguridad predeterminada (fail-safe)."
            ),
            trap = "¿Pueden compartir el mismo tubo un cable de bus domótico y los cables de fuerza de 230V? Solo si el cable de bus cuenta con aislamiento dieléctrico equivalente para la máxima tensión presente (aislamiento para 400V/1000V).",
            keyConcept = "Domótica KNX/Bus. Aislamiento dieléctrico y separación de fuerza. Estado seguro por fallo de bus.",
            examReference = "ITC-BT-51 §2"
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
                "Protección diferencial obligatoria: Tipo A de 30 mA con detección de fugas en continua de 6 mA (o Tipo B).",
                "Esquema 2: Troncal colectiva con contadores principales centralizados y contadores secundarios modulares en plazas de garaje.",
                "Esquema 3a/3b: Derivación individual desde el contador principal de la vivienda hasta la plaza de garaje.",
                "Sección mínima de cable para punto de recarga: 2,5 mm² (mando/fuerza básica) o recomendada 4/6 mm² para régimen continuo de 32 A."
            ),
            greenUnderline = listOf(
                "Caída de tensión máxima admisible para el circuito terminal del punto de recarga: 5% a intensidad máxima de régimen continuo.",
                "Obligatoriedad de incluir dispositivo de corte o rearme automático y protección contra sobretensiones transitorias y permanentes."
            ),
            trap = "¿Sirve un diferencial estándar Tipo AC para una toma de vehículo eléctrico? ¡NO! La norma ITC-BT-52 exige diferencial Clase A con RDC-DD o Clase B porque las baterías generan componentes continuas que ciegan a los Tipo AC.",
            keyConcept = "IBTE obligatorio. Diferencial Tipo A 30mA + 6mA DC. Esquemas 1 a 4. Caída máx 5%. Sobretensiones obligatorias.",
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
                ),
                Question(
                    q = "¿Cuál de los siguientes NO es un objetivo primordial del REBT según el Art. 1?",
                    opts = listOf(
                        "Preservar la seguridad de personas y bienes",
                        "Asegurar el normal funcionamiento de las instalaciones y prevenir perturbaciones",
                        "Maximizar la rentabilidad económica de las empresas comercializadoras de energía",
                        "Contribuir a la fiabilidad técnica y a la eficiencia económica de las instalaciones"
                    ),
                    a = 2,
                    exp = "El Art. 1 define tres fines esenciales del reglamento: seguridad de personas y bienes, normal funcionamiento evitando perturbaciones a otras instalaciones, y contribuir a la fiabilidad técnica y eficiencia económica. La rentabilidad de comercializadoras no forma parte de los objetivos regulatorios.",
                    ref = "Art. 1 RD 842/2002"
                ),
                Question(
                    q = "Un suministro complementario de SOCORRO debe garantizar un suministro mínimo de potencia equivalente al:",
                    opts = listOf(
                        "10% de la potencia total contratada",
                        "15% de la potencia total contratada",
                        "25% de la potencia total contratada",
                        "50% de la potencia total contratada"
                    ),
                    a = 1,
                    exp = "El Art. 10.1.B.a dictamina que para suministros de socorro, la potencia complementaria no será inferior al 15% de la potencia total contratada del abonado para servicios de seguridad.",
                    ref = "Art. 10.1 RD 842/2002"
                ),
                Question(
                    q = "Un suministro complementario de RESERVA debe garantizar como mínimo:",
                    opts = listOf(
                        "15% de la potencia total contratada",
                        "20% de la potencia total contratada",
                        "25% de la potencia total contratada",
                        "50% de la potencia total contratada"
                    ),
                    a = 2,
                    exp = "El Art. 10.1.B.b dictamina que el suministro de reserva debe garantizar al menos el 25% de la potencia total contratada para permitir continuar con las actividades mínimas del usuario.",
                    ref = "Art. 10.1 RD 842/2002"
                ),
                Question(
                    q = "Un suministro complementario DUPLICADO debe ser capaz de alimentar como mínimo:",
                    opts = listOf(
                        "50% de la potencia total contratada",
                        "75% de la potencia total contratada",
                        "100% de la potencia total contratada",
                        "120% de la potencia total contratada para prever picos"
                    ),
                    a = 2,
                    exp = "El Art. 10.1.B.c define el suministro duplicado como aquel capaz de mantener el 100% de la potencia total contratada de la instalación receptora en caso de fallo del suministro principal.",
                    ref = "Art. 10.1 RD 842/2002"
                ),
                Question(
                    q = "¿Qué documento formal deben presentar las empresas instaladoras ante la Administración para iniciar su actividad?",
                    opts = listOf(
                        "Una solicitud de licencia municipal con informe de bomberos",
                        "Una Declaración Responsable ante el órgano competente de la Comunidad Autónoma",
                        "Una autorización expresa previa del Ministerio de Industria",
                        "Un certificado de antecedentes comerciales emitido por la distribuidora"
                    ),
                    a = 1,
                    exp = "Según el Art. 22 del REBT (modificado por la Ley Ómnibus y RD 560/2010), las empresas instaladoras deben presentar una Declaración Responsable ante el órgano autonómico competente, habilitándolas de forma inmediata y por tiempo indefinido para actuar en todo el territorio nacional.",
                    ref = "Art. 22 RD 842/2002"
                ),
                Question(
                    q = "La presentación de la Declaración Responsable de empresa instaladora en una Comunidad Autónoma habilita para trabajar en:",
                    opts = listOf(
                        "Únicamente en el municipio donde radica la sede social",
                        "Únicamente en la Comunidad Autónoma donde se presentó",
                        "En todo el territorio del Estado español de forma inmediata e indefinida",
                        "Durante un período máximo improrrogable de 2 años"
                    ),
                    a = 2,
                    exp = "El principio de unidad de mercado y el Art. 22.2 establecen que la declaración responsable capacita a la empresa instaladora para actuar en todo el territorio español sin necesidad de trámites adicionales en otras autonomías.",
                    ref = "Art. 22.2 RD 842/2002"
                ),
                Question(
                    q = "¿Quién es el responsable legal de mantener la instalación eléctrica en perfecto estado de conservación y seguridad?",
                    opts = listOf(
                        "El instalador que emitió el boletín inicial durante toda la vida del inmueble",
                        "El titular o propietario de la instalación eléctrica",
                        "La empresa distribuidora de la zona",
                        "El fabricante de los magnetotérmicos del cuadro general"
                    ),
                    a = 1,
                    exp = "El Art. 19 del REBT establece con claridad que corresponde al titular o propietario de la instalación eléctrica mantenerla en debido estado de funcionamiento y seguridad, contratando las revisiones periódicas que procedan.",
                    ref = "Art. 19 RD 842/2002"
                ),
                Question(
                    q = "Las especificaciones particulares de las empresas distribuidoras de energía eléctrica deben ser aprobadas por:",
                    opts = listOf(
                        "La propia junta directiva de la empresa distribuidora sin control externo",
                        "El Ministerio de Industria y las Comunidades Autónomas competentes",
                        "El Colegio Oficial de Ingenieros Industriales de la provincia",
                        "Los ayuntamientos donde operen las redes"
                    ),
                    a = 1,
                    exp = "El Art. 14 del REBT establece que las normas y especificaciones técnicas particulares de las distribuidoras deben ser aprobadas por el Ministerio competente o los órganos competentes de las CC.AA., debiendo basarse en criterios de normalización no discriminatorios.",
                    ref = "Art. 14 RD 842/2002"
                ),
                Question(
                    q = "¿Qué principio rige la aplicación del concepto de 'Seguridad Equivalente' regulado en el Art. 12?",
                    opts = listOf(
                        "Permite incumplir normas si se abarata el presupuesto en más de un 30%",
                        "Permite soluciones técnicas alternativas siempre que justifiquen documentalmente un nivel de seguridad al menos igual al reglamentario",
                        "Solo es aplicable a instalaciones militares o de defensa",
                        "Requiere un aval bancario solidario a favor de la Administración"
                    ),
                    a = 1,
                    exp = "El Art. 12 autoriza técnicas o diseños distintos a los prescritos en las ITCs siempre que el proyectista o instalador justifique técnicamente que la solución aporta una seguridad al menos equivalente a la reglamentaria.",
                    ref = "Art. 12 RD 842/2002"
                ),
                Question(
                    q = "En caso de discrepancia técnica entre un Organismo de Control Autorizado (OCA) y el instalador, ¿quién resuelve el conflicto?",
                    opts = listOf(
                        "La empresa distribuidora eléctrica de la zona",
                        "El órgano territorial competente en materia de energía de la Comunidad Autónoma",
                        "La asociación provincial de empresarios instaladores",
                        "El Instituto Nacional de Consumo"
                    ),
                    a = 1,
                    exp = "El Art. 24 del REBT señala que cualquier discrepancia técnica o de interpretación entre instalador, titular u OCA será resuelta de forma vinculante por el órgano competente de la Comunidad Autónoma.",
                    ref = "Art. 24 RD 842/2002"
                ),
                Question(
                    q = "Si un titular solicita una excepción formal al REBT al amparo del Art. 24 y la Administración no responde en plazo, el silencio administrativo es:",
                    opts = listOf(
                        "Positivo (la excepción se considera concedida)",
                        "Negativo / Desestimatorio (la excepción se considera denegada)",
                        "Nulo de pleno derecho",
                        "Prorrogado automáticamente por un año adicional"
                    ),
                    a = 1,
                    exp = "En materia de seguridad industrial y excepciones a reglamentos técnicos (Art. 24), el silencio administrativo tiene carácter desestimatorio para preservar la seguridad pública.",
                    ref = "Art. 24 RD 842/2002"
                ),
                Question(
                    q = "¿A qué régimen legal se remite el Art. 28 del REBT para la tipificación de infracciones y cuantía de sanciones?",
                    opts = listOf(
                        "Al Código Penal español exclusivamente",
                        "Al Título V de la Ley 21/1992, de Industria",
                        "Al Reglamento de Disciplina Urbanística de cada municipio",
                        "A los estatutos de la Federación Nacional de Instaladores (FENIE)"
                    ),
                    a = 1,
                    exp = "El Art. 28 del REBT se remite al Título V de la Ley 21/1992 de Industria y a la Ley 24/2013 del Sector Eléctrico para la calificación y sanción de infracciones leves, graves y muy graves.",
                    ref = "Art. 28 RD 842/2002"
                ),
                Question(
                    q = "¿Qué marcado de conformidad europeo deben ostentar obligatoriamente los aparatos y materiales eléctricos según el Art. 6?",
                    opts = listOf(
                        "Marcado CE de conformidad con las Directivas Comunitarias aplicables",
                        "Sello de calidad AENOR de forma indispensable y exclusiva",
                        "Número de registro ante el Ministerio de Industria",
                        "Holograma de seguridad del instalador"
                    ),
                    a = 0,
                    exp = "El Art. 6 del REBT establece que los materiales y aparatos eléctricos deben cumplir las directivas europeas de aplicación y ostentar el marcado CE que garantiza su libre circulación y seguridad.",
                    ref = "Art. 6 RD 842/2002"
                ),
                Question(
                    q = "¿Qué documento técnico elabora la Dirección General de Industria para facilitar la interpretación práctica de las ITCs?",
                    opts = listOf(
                        "Un manual de estilo publicitario",
                        "La Guía Técnica de aplicación del REBT, de carácter no vinculante",
                        "Una ordenanza municipal marco",
                        "Un catálogo de tarifas recomendadas para boletines"
                    ),
                    a = 1,
                    exp = "El Art. 29 contempla la elaboración por el Centro Directivo competente del Ministerio de una Guía Técnica de aplicación del REBT, con aclaraciones y criterios prácticos no vinculantes pero de gran valor técnico.",
                    ref = "Art. 29 RD 842/2002"
                ),
                Question(
                    q = "¿Cuál es el valor eficaz de la tensión de ensayo para verificar el aislamiento de conductores en circuitos de BT con tensión nominal ≤ 500 V?",
                    opts = listOf(
                        "250 V en corriente alterna",
                        "500 V en corriente continua",
                        "1.000 V en corriente alterna",
                        "2.500 V en corriente continua"
                    ),
                    a = 1,
                    exp = "Según la ITC-BT-19 e ITC-BT-03, la tensión de ensayo de aislamiento para instalaciones de tensión nominal hasta 500 V (las habituales de 230/400 V) es de 500 V de tensión continua.",
                    ref = "ITC-BT-19 §5.2"
                ),
                Question(
                    q = "¿Qué valor mínimo de resistencia de aislamiento se exige en una instalación nueva de BT con tensión ≤ 500 V?",
                    opts = listOf(
                        "0,25 MΩ (250.000 Ω)",
                        "0,5 MΩ (500.000 Ω)",
                        "1,0 MΩ (1.000.000 Ω)",
                        "10 MΩ"
                    ),
                    a = 1,
                    exp = "La ITC-BT-19 par. 5.2 fija que la resistencia de aislamiento entre conductores y tierra, o entre conductores activos, debe ser como mínimo de 0,5 MΩ para tensiones nominales hasta 500 V.",
                    ref = "ITC-BT-19 §5.2"
                ),
                Question(
                    q = "¿Qué es una red de distribución privada según el Art. 8 del REBT?",
                    opts = listOf(
                        "Una red propiedad de la compañía distribuidora principal en suelo rústico",
                        "La red de distribución destinada al suministro a varios usuarios que no pertenezca a una empresa distribuidora pública",
                        "Cualquier instalación interior de una vivienda con más de 20 circuitos",
                        "Una línea de alta tensión subterránea compartida"
                    ),
                    a = 1,
                    exp = "El Art. 8 define como redes de distribución privadas aquellas que, partiendo de una central generadora o red pública, suministran a varios usuarios o instalaciones industriales independientes sin pertenecer a la empresa distribuidora concesionaria.",
                    ref = "Art. 8 RD 842/2002"
                ),
                Question(
                    q = "¿Puede la empresa distribuidora suministrar energía de forma definitiva antes de registrar el Certificado de Instalación (CIE)?",
                    opts = listOf(
                        "Sí, si el cliente paga la fianza de contratación",
                        "No; solo se permite la conexión provisional para pruebas y ensayos antes de la tramitación definitiva",
                        "Sí, siempre que la potencia contratada no supere los 15 kW",
                        "Sí, si el alcalde del municipio firma una autorización de urgencia"
                    ),
                    a = 1,
                    exp = "El Art. 18 prohíbe el enganche y suministro definitivo hasta que el instalador haya tramitado y obtenido el registro del Certificado de Instalación ante la CCAA. Solo se autoriza conexión provisional para pruebas técnicas.",
                    ref = "Art. 18 RD 842/2002"
                ),
                Question(
                    q = "En una instalación existente anterior al REBT 2002, ¿cuándo es obligatorio adaptarla plenamente al nuevo reglamento?",
                    opts = listOf(
                        "Cada 5 años con independencia de su uso",
                        "Cuando sufra una modificación de importancia (> 50% de la potencia instalada) o una ampliación sustancial",
                        "Solo cuando cambie el inquilino o titular del contrato de suministro",
                        "Nunca, rige el principio de irretroactividad absoluta para todas las instalaciones"
                    ),
                    a = 1,
                    exp = "El Art. 2.2 determina que las instalaciones existentes deben adaptarse a las prescripciones del REBT 2002 cuando sean objeto de modificaciones de importancia (más del 50% de potencia) o cuando su estado represente peligro manifiesto para personas o bienes.",
                    ref = "Art. 2.2 RD 842/2002"
                ),
                Question(
                    q = "¿Qué obligación documental tiene el instalador respecto a la entrega de documentación al titular de la instalación?",
                    opts = listOf(
                        "Entregar únicamente la factura comercial del servicio prestado",
                        "Entregar copia del Certificado de Instalación (CIE), la Memoria o Proyecto y el manual de uso y mantenimiento",
                        "Guardar toda la documentación en secreto profesional sin entregar copias",
                        "Entregar solo un recibo manuscrito de pago de tasas autonómicas"
                    ),
                    a = 1,
                    exp = "El Art. 18 y la ITC-BT-03 obligan a la empresa instaladora a entregar al titular copia registrada del CIE, la documentación técnica de diseño (MTD o Proyecto) y las instrucciones de uso y mantenimiento seguro de la instalación.",
                    ref = "Art. 18.4 RD 842/2002"
                ),
                Question(
                    q = "¿Cuál es el plazo de garantía mínimo que la empresa instaladora debe otorgar legalmente sobre sus trabajos ejecutados?",
                    opts = listOf(
                        "3 meses",
                        "6 meses",
                        "1 año",
                        "5 años"
                    ),
                    a = 1,
                    exp = "El Art. 23 del REBT (y la normativa general de garantías de instalaciones) fija un plazo de garantía legal mínimo de 6 meses para las instalaciones eléctricas ejecutadas.",
                    ref = "Art. 23 RD 842/2002"
                ),
                Question(
                    q = "Las normas UNE referenciadas en las Instrucciones Técnicas Complementarias (ITCs):",
                    opts = listOf(
                        "Tienen carácter vinculante en la versión y año específicamente citados en el listado oficial de la ITC-BT-02",
                        "Son de aplicación puramente voluntaria y sin valor reglamentario",
                        "Deben comprarse obligatoriamente en formato físico por todo usuario doméstico",
                        "Quedan derogadas automáticamente cuando se publica una norma internacional ISO"
                    ),
                    a = 0,
                    exp = "La ITC-BT-02 establece que las normas UNE recogidas en su listado son de obligado cumplimiento en las versiones indicadas, publicándose periódicamente resoluciones de actualización por el Ministerio.",
                    ref = "ITC-BT-02 RD 842/2002"
                ),
                Question(
                    q = "¿Qué se considera una instalación temporal según el REBT?",
                    opts = listOf(
                        "Cualquier instalación con tubos vistos",
                        "Instalaciones destinadas a funcionar durante un tiempo determinado (obras, ferias, festejos, rodajes)",
                        "Instalaciones en locales comerciales de alquiler por menos de 5 años",
                        "Las instalaciones de alumbrado público durante los meses de invierno"
                    ),
                    a = 1,
                    exp = "El Art. 2 y la ITC-BT-34 definen las instalaciones temporales como aquellas proyectadas para un período de funcionamiento efímero y determinado, como obras, ferias, exposiciones o festejos.",
                    ref = "Art. 2 y ITC-BT-34"
                ),
                Question(
                    q = "En caso de accidente grave por electrocución o incendio de origen eléctrico, ¿qué obligación tiene la empresa instaladora o mantenedora?",
                    opts = listOf(
                        "Ocultar el suceso para evitar inspecciones",
                        "Ponerlo en conocimiento del órgano competente de la Comunidad Autónoma en el plazo más breve posible",
                        "Notificarlo únicamente al fabricante del cable",
                        "Esperar a la renovación de la póliza de seguro de responsabilidad civil"
                    ),
                    a = 1,
                    exp = "El Art. 23 y la Ley de Industria obligan a comunicar de inmediato cualquier accidente o incidente grave en instalaciones industriales y de energía a la autoridad autonómica competente para su investigación técnica.",
                    ref = "Art. 23 RD 842/2002"
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
                ),
                Question(
                    q = "¿A partir de qué potencia prevista requiere PROYECTO TÉCNICO visado una instalación de alumbrado exterior según la ITC-BT-04?",
                    opts = listOf(
                        "Potencia superior a 1 kW",
                        "Potencia superior a 5 kW",
                        "Potencia superior a 15 kW",
                        "Potencia superior a 50 kW"
                    ),
                    a = 1,
                    exp = "La Tabla 1 de la ITC-BT-04 Grupo d fija que las instalaciones de alumbrado exterior precisan proyecto redactado y firmado por técnico titulado competente cuando la potencia instalada sea superior a 5 kW.",
                    ref = "ITC-BT-04 Tabla 1"
                ),
                Question(
                    q = "Una piscina pública con potencia de bombas e iluminación de 8 kW, ¿requiere Proyecto Técnico visado?",
                    opts = listOf(
                        "No, las piscinas nunca requieren proyecto si no superan 50 kW",
                        "Sí, las instalaciones de piscinas y fuentes precisan proyecto a partir de más de 5 kW de potencia",
                        "Solo si dispone de agua climatizada o sauna",
                        "Requiere únicamente Memoria Técnica de Diseño (MTD)"
                    ),
                    a = 1,
                    exp = "Conforme a la ITC-BT-04 Grupo f, las instalaciones correspondientes a piscinas y fuentes requieren proyecto técnico cuando su potencia instalada exceda de 5 kW.",
                    ref = "ITC-BT-04 Tabla 1"
                ),
                Question(
                    q = "¿Cuál es la potencia límite para que un local clasificado como HÚMEDO o MOJADO requiera Proyecto Técnico?",
                    opts = listOf(
                        "Superior a 5 kW",
                        "Superior a 10 kW",
                        "Superior a 25 kW",
                        "Superior a 50 kW"
                    ),
                    a = 1,
                    exp = "La ITC-BT-04 Grupo e clasifica los locales húmedos, mojados, con riesgo de corrosión o polvorientos con requerimiento de proyecto técnico a partir de potencias superiores a 10 kW.",
                    ref = "ITC-BT-04 Tabla 1"
                ),
                Question(
                    q = "¿Qué titulación mínima o vía de acceso capacita al personal técnico competente de una empresa instaladora en Categoría Básica (IBTB)?",
                    opts = listOf(
                        "Título universitario o de Formación Profesional de Grado Medio/Superior en electricidad, o Certificado de Profesionalidad equivalente",
                        "Cualquier titulación universitaria de humanidades con curso de 20 horas",
                        "Haber trabajado 6 meses como peón en cualquier sector",
                        "Disponer de carné de conducir tipo B"
                    ),
                    a = 0,
                    exp = "La ITC-BT-03 e instrucciones de desarrollo exigen titulación universitaria técnica competente, Ciclo Formativo de Grado Medio o Superior en la rama eléctrica, o Certificado de Profesionalidad del Catálogo Nacional de Cualificaciones Profesionales.",
                    ref = "ITC-BT-03 §4"
                ),
                Question(
                    q = "¿Puede un instalador de Categoría Básica (IBTB) realizar la instalación eléctrica de un local con riesgo de incendio o explosión (ATEX)?",
                    opts = listOf(
                        "Sí, siempre que la potencia sea menor de 20 kW",
                        "No; las instalaciones ATEX están reservadas exclusivamente a la Categoría Especialista (IBTE)",
                        "Sí, si utiliza tubos de acero galvanizado roscado",
                        "Solo con autorización expresa del ayuntamiento"
                    ),
                    a = 1,
                    exp = "La ITC-BT-03 par. 3.2 enumera las instalaciones en locales con riesgo de incendio o explosión como ámbito privativo de las empresas instaladoras en Categoría Especialista (IBTE).",
                    ref = "ITC-BT-03 §3.2"
                ),
                Question(
                    q = "¿Qué periodicidad de inspección periódica por OCA tienen los garajes con más de 25 plazas de estacionamiento?",
                    opts = listOf(
                        "Cada año",
                        "Cada 2 años",
                        "Cada 5 años",
                        "Cada 10 años"
                    ),
                    a = 2,
                    exp = "La ITC-BT-05 par. 4.2 establece que los garajes con capacidad para más de 25 vehículos deben someterse a inspección periódica por un Organismo de Control cada 5 años.",
                    ref = "ITC-BT-05 §4.2"
                ),
                Question(
                    q = "En una inspección de una OCA, la detección de un defecto 'Muy Grave' conlleva:",
                    opts = listOf(
                        "Un plazo de subsanación de 1 año",
                        "La propuesta inmediata de desconexión / corte de suministro por constituir peligro inminente para personas o bienes",
                        "Una simple anotación informativa en el libro de mantenimiento",
                        "La sustitución automática del instalador de la obra"
                    ),
                    a = 1,
                    exp = "La ITC-BT-05 par. 5 califica como defecto Muy Grave aquel que supone un peligro inmediato e inminente para la seguridad de las personas o cosas, conllevando la no emisión de certificado favorable y la propuesta de corte de suministro.",
                    ref = "ITC-BT-05 §5"
                ),
                Question(
                    q = "Si en una inspección de OCA se detectan únicamente defectos 'Leves', la calificación final del acta será:",
                    opts = listOf(
                        "Favorable con observaciones",
                        "Condicionada con paralización de obra",
                        "Negativa automática",
                        "Anulada hasta nueva visita"
                    ),
                    a = 0,
                    exp = "La ITC-BT-05 dictamina que la existencia exclusiva de defectos leves permite otorgar una calificación Favorable, debiendo subsanarse antes de la siguiente inspección reglamentaria.",
                    ref = "ITC-BT-05 §5"
                ),
                Question(
                    q = "¿Qué cobertura mínima obligatoria debe tener la póliza de Seguro de Responsabilidad Civil de una empresa instaladora de Categoría Básica (IBTB)?",
                    opts = listOf(
                        "60.000 €",
                        "300.000 €",
                        "Importe fijado y actualizado periódicamente por la orden ministerial (superior a 600.000 €)",
                        "10.000.000 €"
                    ),
                    a = 2,
                    exp = "La ITC-BT-03 par. 4 exige disponer de un seguro de responsabilidad civil u otra garantía financiera para cubrir los riesgos por la cuantía mínima establecida y actualizada oficialmente por el Ministerio.",
                    ref = "ITC-BT-03 §4"
                ),
                Question(
                    q = "¿Cuál de los siguientes equipos de medida NO es obligatorio para la Categoría Básica (IBTB) pero SÍ para la Categoría Especialista (IBTE)?",
                    opts = listOf(
                        "Medidor de aislamiento (Megóhmetro)",
                        "Telurómetro de tierra",
                        "Analizador de redes y armónicos / perturbaciones",
                        "Voltímetro digital multifunción"
                    ),
                    a = 2,
                    exp = "La ITC-BT-03 Apéndice 1 exige el analizador de redes y calidad de suministro con registro de armónicos de forma obligatoria para los instaladores de Categoría Especialista (IBTE).",
                    ref = "ITC-BT-03 Apéndice 1"
                ),
                Question(
                    q = "Una instalación generadora fotovoltaica aislada (sin conexión a red) de 8 kW de potencia, ¿requiere Proyecto Técnico?",
                    opts = listOf(
                        "No, las instalaciones aisladas < 10 kW requieren únicamente Memoria Técnica de Diseño (MTD)",
                        "Sí, toda instalación generadora requiere siempre proyecto",
                        "Solo si incluye acumuladores de plomo-ácido",
                        "Requiere exclusivamente declaración jurada del titular"
                    ),
                    a = 0,
                    exp = "La ITC-BT-04 y la ITC-BT-40 establecen que las instalaciones generadoras de BT de potencia menor o igual a 10 kW requieren MTD en lugar de proyecto técnico.",
                    ref = "ITC-BT-04 Tabla 1"
                ),
                Question(
                    q = "¿A quién corresponde la responsabilidad civil y técnica por los cálculos contenidos en una Memoria Técnica de Diseño (MTD)?",
                    opts = listOf(
                        "A la empresa distribuidora de energía",
                        "Al instalador autorizado firmante de la MTD",
                        "Al fabricante de los cables utilizados",
                        "Al operario que tiró las líneas en obra"
                    ),
                    a = 1,
                    exp = "El instalador autorizado asume la plena responsabilidad técnica y de seguridad de los esquemas, cálculos de sección y protecciones especificados en la Memoria Técnica de Diseño que suscribe.",
                    ref = "ITC-BT-03 e ITC-BT-04"
                ),
                Question(
                    q = "¿Qué documento debe confeccionar obligatoriamente el instalador autorizado antes de solicitar el registro del Certificado de Instalación en la CCAA?",
                    opts = listOf(
                        "Un certificado de antecedentes tributarios",
                        "El Manual de Instrucciones y Registro de Verificaciones Previas realizadas a la instalación",
                        "Un informe de impacto medioambiental municipal",
                        "Una póliza de caución bancaria individual"
                    ),
                    a = 1,
                    exp = "La ITC-BT-03 par. 5 obliga al instalador a realizar las pruebas y verificaciones reglamentarias previas a la puesta en servicio y a reflejar sus resultados en el registro de verificaciones entregado al titular.",
                    ref = "ITC-BT-03 §5"
                ),
                Question(
                    q = "En caso de cese de actividad de una empresa instaladora autorizada, ¿qué plazo tiene para comunicarlo al órgano competente de la CCAA?",
                    opts = listOf(
                        "En el plazo de 1 mes natural",
                        "En el plazo de 6 meses",
                        "No es necesario comunicarlo",
                        "Al finalizar el año fiscal"
                    ),
                    a = 0,
                    exp = "La legislación de seguridad industrial y la ITC-BT-03 dictaminan la obligación de comunicar cualquier modificación o cese de actividad en el plazo máximo de 1 mes ante la autoridad autonómica.",
                    ref = "ITC-BT-03 §4"
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
                ),
                Question(
                    q = "¿Cuál es la altura reglamentaria de montaje del Cuadro General de Mando y Protección (CGMP) en una vivienda?",
                    opts = listOf(
                        "Entre 0,50 m y 1,20 m sobre el nivel del suelo",
                        "Entre 1,40 m y 2,00 m desde el nivel del suelo hasta la base de los dispositivos",
                        "A una altura mínima de 2,50 m para evitar que lo alcancen los niños",
                        "No existe regulación de altura si está dentro de un armario de madera"
                    ),
                    a = 1,
                    exp = "La ITC-BT-17 par. 1.2 especifica que los dispositivos de mando y protección del CGMP se situarán a una altura comprendida entre 1,40 m y 2,00 m sobre el pavimento acabado.",
                    ref = "ITC-BT-17 §1.2"
                ),
                Question(
                    q = "¿Dónde se debe ubicar preferentemente la Caja General de Protección (CGP) de un edificio?",
                    opts = listOf(
                        "En el interior del rellano de la primera planta",
                        "En la fachada exterior del edificio, en el límite de la propiedad o en un nicho accesible desde la vía pública",
                        "En el cuarto de contadores exclusivamente",
                        "En la cubierta o azotea del edificio"
                    ),
                    a = 1,
                    exp = "La ITC-BT-13 par. 1.1 establece que la CGP se ubicará sobre la fachada exterior del edificio en el límite de la propiedad, en lugar de libre y permanente acceso para el personal de la empresa distribuidora.",
                    ref = "ITC-BT-13 §1.1"
                ),
                Question(
                    q = "¿Qué tipo de fusibles deben instalarse preceptivamente en el interior de la Caja General de Protección (CGP)?",
                    opts = listOf(
                        "Fusibles cilíndricos domésticos de 10x38 mm",
                        "Fusibles de cuchillas tipo NH de alto poder de corte (gG)",
                        "Magnetotérmicos unipolares con rearme manual",
                        "Pletinas de cobre fijas sin fusible"
                    ),
                    a = 1,
                    exp = "La ITC-BT-13 e ITC-BT-14 prescriben el uso de cartuchos fusibles calibrados de cuchilla de alto poder de corte tipo gG con poder de corte de al menos 50 kA a 100 kA.",
                    ref = "ITC-BT-13 §1.2"
                ),
                Question(
                    q = "¿Cuál es la designación y reacción al fuego obligatoria para los cables instalados en la Línea General de Alimentación (LGA)?",
                    opts = listOf(
                        "Cables estándar de PVC H07V-K",
                        "Cables tipo AS (Alta Seguridad) no propagadores del incendio, con emisión de humos reducida y libres de halógenos (Cca-s1b,d1,a1)",
                        "Cables armados con plomo H07RN-F",
                        "Conductores desnudos sobre aisladores cerámicos"
                    ),
                    a = 1,
                    exp = "La ITC-BT-14 par. 2 exige taxativamente conductores de alta seguridad no propagadores del incendio con baja opacidad y libres de halógenos conforme a la norma europea CPR (Clase Cca-s1b,d1,a1).",
                    ref = "ITC-BT-14 §2"
                ),
                Question(
                    q = "¿Cuál es la relación mínima exigida entre la sección interior del tubo protector de una LGA y la sección ocupada por el haz de cables?",
                    opts = listOf(
                        "El diámetro del tubo debe ser igual a la suma de cables",
                        "La sección interior útil del tubo debe ser como mínimo 3 veces la sección global ocupada por los conductores",
                        "La sección interior debe ser 1,5 veces la de los cables",
                        "No se requiere tubo si van en conducto de albañilería"
                    ),
                    a = 1,
                    exp = "La ITC-BT-14 par. 3 fija que la sección interior del tubo protector será como mínimo 3 veces superior a la sección total que ocupen los conductores que aloje.",
                    ref = "ITC-BT-14 §3"
                ),
                Question(
                    q = "¿Cuál es la resistencia al fuego exigida a la puerta de acceso al local o recinto de contadores de un edificio?",
                    opts = listOf(
                        "Sin resistencia especial (puerta de madera ordinaria)",
                        "Resistencia al fuego de al menos EI2 30-C5 (antigua RF-30)",
                        "Debe ser de cristal blindado transparente",
                        "Puerta metálica con orificios abiertos de 10x10 cm"
                    ),
                    a = 1,
                    exp = "La ITC-BT-16 par. 2.1 dictamina que las puertas de acceso a locales de contadores deben abrir hacia el exterior y poseer una resistencia al fuego no inferior a EI2 30 (RF-30).",
                    ref = "ITC-BT-16 §2.1"
                ),
                Question(
                    q = "¿Qué elementos de servicio e iluminación son obligatorios dentro del local de concentración de contadores?",
                    opts = listOf(
                        "Una toma de corriente de 16 A con toma de tierra y un punto de luz accionado por interruptor situado junto a la puerta de acceso",
                        "Un extintor de agua a presión y una toma de antena colectiva",
                        "Un transformador elevador de 10 kVA",
                        "Un cuadro de telecomunicaciones integrado en el mismo bastidor"
                    ),
                    a = 0,
                    exp = "La ITC-BT-16 par. 2.1 exige en el interior del local de contadores un nivel de iluminación mínimo de 200 lux, un interruptor junto a la entrada, alumbrado de emergencia autónomo y una base de enchufe con tierra de 16 A.",
                    ref = "ITC-BT-16 §2.1"
                ),
                Question(
                    q = "En una Derivación Individual (DI) monofásica, ¿qué colores deben tener los conductores de Fase, Neutro y Protección (PE)?",
                    opts = listOf(
                        "Fase: Marrón/Negro/Gris | Neutro: Azul claro | Protección: Verde-amarillo",
                        "Fase: Rojo | Neutro: Negro | Protección: Blanco",
                        "Fase: Azul | Neutro: Marrón | Protección: Verde",
                        "Todos los conductores deben ser del mismo color para evitar confusiones estéticas"
                    ),
                    a = 0,
                    exp = "La ITC-BT-19 par. 2.2.4 normaliza los colores de identificación: Fase (marrón, negro o gris), Neutro (azul claro) y Conductor de protección (bicolor verde-amarillo).",
                    ref = "ITC-BT-19 §2.2.4"
                ),
                Question(
                    q = "¿Para qué sirve el hilo de mando de color rojo de 1,5 mm² en una Derivación Individual?",
                    opts = listOf(
                        "Para la conexión del timbre de la vivienda",
                        "Para el control de cambio de tarifa / discriminación horaria desde el equipo de medida de la distribuidora",
                        "Para la parada de emergencia del ascensor",
                        "Para alimentar el portero automático"
                    ),
                    a = 1,
                    exp = "La ITC-BT-15 par. 3 contempla la instalación del conductor rojo de 1,5 mm² para el hilo de mando y cambio de tarifas entre el contador y el cuadro interior del usuario.",
                    ref = "ITC-BT-15 §3"
                ),
                Question(
                    q = "¿Qué tipo de protección contra sobretensiones transitorias es preceptiva en instalaciones de enlace según la ITC-BT-23?",
                    opts = listOf(
                        "Protectores contra sobretensiones transitorias (DPS / descargadores) coordinados con la toma de tierra",
                        "Fusibles rápidos de cristal",
                        "Condensadores en paralelo de 100 uF",
                        "No se requieren protectores si el transformador está a más de 500 metros"
                    ),
                    a = 0,
                    exp = "La ITC-BT-23 e ITC-BT-17 establecen la obligatoriedad de instalar dispositivos de protección contra sobretensiones transitorias y permanentes según el nivel ceráunico y el tipo de alimentación de la red.",
                    ref = "ITC-BT-23 e ITC-BT-17"
                ),
                Question(
                    q = "En un suministro monofásico con IGA de 25 A y diferencial de 40 A / 30 mA, ¿por qué el calibre del diferencial es superior al del IGA?",
                    opts = listOf(
                        "Por error de cálculo",
                        "Para garantizar que el diferencial soporte la corriente máxima sin dañarse térmicamente antes de que dispare el magnetotérmico",
                        "Porque el diferencial debe disparar antes que el magnetotérmico por sobrecarga",
                        "Para compensar la caída de tensión en los bornes"
                    ),
                    a = 1,
                    exp = "El interruptor diferencial no protege contra sobrecargas térmicas; su intensidad nominal (ej. 40 A) indica la corriente máxima que sus contactos pueden soportar en régimen permanente. Por ello, debe ser igual o superior al calibre del IGA que lo protege aguas arriba (25 A).",
                    ref = "ITC-BT-17 §1"
                ),
                Question(
                    q = "¿Cuál es la caída de tensión máxima permitida en una instalación con suministro a un único usuario donde no existe LGA (contador y CGP integrados en CPM)?",
                    opts = listOf(
                        "0,5%",
                        "1,5%",
                        "3,0%",
                        "5,0%"
                    ),
                    a = 1,
                    exp = "La ITC-BT-15 par. 3 fija que para suministros a un solo usuario (ej. vivienda unifamiliar con CPM), la caída de tensión en la derivación individual no superará el 1,5%.",
                    ref = "ITC-BT-15 §3"
                ),
                Question(
                    q = "¿Qué grado de protección mecánica contra impactos (código IK) debe tener la envolvente de una CPM o CGP instalada en fachada accesible?",
                    opts = listOf(
                        "IK02",
                        "IK05",
                        "IK08 o superior",
                        "No requiere protección contra impactos"
                    ),
                    a = 2,
                    exp = "La ITC-BT-13 exige que las envolventes de las CGP y CPM instaladas en intemperie o accesibles al público ofrezcan una resistencia mecánica mínima contra impactos equivalente a IK08 o IK09.",
                    ref = "ITC-BT-13 §1.1"
                ),
                Question(
                    q = "¿Cuál es la potencia base mínima que se debe prever para los Servicios Generales de un edificio de viviendas (ascensor, alumbrado escalera, grupo de presión)?",
                    opts = listOf(
                        "1.000 W",
                        "La suma de las potencias de los receptores sin aplicar factor reductor de simultaneidad (Cs = 1,0)",
                        "Un 10% de la potencia total de las viviendas",
                        "3.450 W en cualquier caso"
                    ),
                    a = 1,
                    exp = "La ITC-BT-10 par. 3.3 dicta que la carga de servicios generales se calculará sumando la potencia nominal de todos los motores, bombas, ascensores y alumbrado con coeficiente de simultaneidad Cs = 1,0.",
                    ref = "ITC-BT-10 §3.3"
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
                ),
                Question(
                    q = "En el circuito C4 de una vivienda (Lavadora, Lavavajillas y Termo eléctrico), si se opta por desdoblarlo en tres circuitos independientes, ¿qué protección y sección corresponden a cada ramal?",
                    opts = listOf(
                        "Un magnetotérmico de 20 A y cable de 4 mm² para cada uno",
                        "Un magnetotérmico individual de 16 A y conductores de 2,5 mm² en tubos independientes",
                        "Un único magnetotérmico de 20 A con cables de 1,5 mm²",
                        "Fusibles de 10 A y cables de 1,5 mm²"
                    ),
                    a = 1,
                    exp = "La ITC-BT-25 par. 3 autoriza desdoblar el circuito C4 en 3 circuitos independientes protegidos cada uno con PIA de 16 A y conductores de sección mínima 2,5 mm² bajo tubo protector individual.",
                    ref = "ITC-BT-25 §3"
                ),
                Question(
                    q = "¿Cuántos puntos de utilización (puntos de luz) como máximo pueden conectarse a un único circuito C1 de alumbrado en una vivienda?",
                    opts = listOf(
                        "15 puntos de luz",
                        "20 puntos de luz",
                        "30 puntos de luz",
                        "50 puntos de luz"
                    ),
                    a = 2,
                    exp = "La Tabla 1 de la ITC-BT-25 establece que el circuito C1 de alumbrado general admite hasta un máximo de 30 puntos de luz. Si se supera esa cifra, debe crearse un circuito adicional C6.",
                    ref = "ITC-BT-25 Tabla 1"
                ),
                Question(
                    q = "¿Cuántas bases de toma de corriente generales como máximo pueden conectarse al circuito C2 de una vivienda?",
                    opts = listOf(
                        "10 tomas",
                        "15 tomas",
                        "20 tomas",
                        "30 tomas"
                    ),
                    a = 2,
                    exp = "La Tabla 1 de la ITC-BT-25 fija un máximo de 20 bases de enchufe para el circuito C2 de tomas generales. Al superar las 20 tomas, es obligatorio añadir el circuito C7.",
                    ref = "ITC-BT-25 Tabla 1"
                ),
                Question(
                    q = "En un cuarto de baño, ¿qué aparatos eléctricos están permitidos instalar en el interior del VOLUMEN 0 (seno de la bañera o plato de ducha)?",
                    opts = listOf(
                        "Cualquier aparato con protección IPX4",
                        "Únicamente aparatos previstos específicamente para ese volumen, protegidos a MBTS ≤ 12 V CA (o 30 V CC) y con grado de estanqueidad mínimo IPX7",
                        "Calentadores de agua instantáneos de 230 V con toma de tierra",
                        "Secadores de pelo de mano conectados con diferencial de 10 mA"
                    ),
                    a = 1,
                    exp = "La ITC-BT-27 par. 2.1 prohíbe todo mecanismo en volumen 0 y solo admite receptores sumergibles diseñados para ello, con protección IPX7 y alimentados a MBTS no superior a 12 V eficaces en CA.",
                    ref = "ITC-BT-27 §2.1"
                ),
                Question(
                    q = "¿Qué distancia comprende el VOLUMEN 2 alrededor de una bañera o plato de ducha?",
                    opts = listOf(
                        "0,30 m en horizontal alrededor del volumen 1",
                        "0,60 m en el plano horizontal a partir del volumen 1 (y hasta 2,25 m de altura)",
                        "1,20 m desde el borde de la bañera",
                        "2,40 m desde el suelo"
                    ),
                    a = 1,
                    exp = "La ITC-BT-27 define el volumen 2 como la zona comprendida entre la superficie vertical exterior del volumen 1 y una superficie vertical paralela situada a 0,60 m de distancia, hasta una altura de 2,25 m.",
                    ref = "ITC-BT-27 §2.1"
                ),
                Question(
                    q = "¿Se permite instalar interruptores de luz o bases de enchufe convencionales de 230 V en el VOLUMEN 1 de un cuarto de baño?",
                    opts = listOf(
                        "Sí, si tienen tapa abatible estanca",
                        "No; en el volumen 1 está terminantemente prohibido instalar cualquier interruptor o base de toma de corriente",
                        "Sí, a una altura superior a 1,80 m del suelo",
                        "Solo tomas protegidas por fusible térmico"
                    ),
                    a = 1,
                    exp = "La ITC-BT-27 par. 2.2 prohíbe taxativamente mecanismos de mando, interruptores y tomas de corriente en los volúmenes 0 y 1 de cuartos de baño.",
                    ref = "ITC-BT-27 §2.2"
                ),
                Question(
                    q = "¿En qué volumen de un baño se permite instalar tomas de corriente alimentadas a 230 V protegidas por diferencial de 30 mA?",
                    opts = listOf(
                        "En el Volumen 0",
                        "En el Volumen 1",
                        "En el Volumen 2",
                        "En el Volumen 3 (o fuera de los volúmenes de protección)"
                    ),
                    a = 3,
                    exp = "La ITC-BT-27 autoriza las bases de toma de corriente convencionales en el Volumen 3 (franja de 2,40 m a partir del volumen 2) siempre que estén protegidas por diferencial de 30 mA o alimentadas por transformador de aislamiento.",
                    ref = "ITC-BT-27 §2.2"
                ),
                Question(
                    q = "¿Qué elemento es OBLIGATORIO en cuartos de baño para prevenir diferencias de potencial peligrosas entre masas metálicas?",
                    opts = listOf(
                        "Un pararrayos radiactivo",
                        "La Unión Equipotencial Suplementaria que interconecte tuberías metálicas de agua fría, caliente, desagües y marcos metálicos con el conductor de protección",
                        "Un suelo de goma aislante de 10 mm",
                        "Lámparas incandescentes sin tierra"
                    ),
                    a = 1,
                    exp = "La ITC-BT-27 par. 2.1 exige una conexión equipotencial suplementaria que una las partes conductoras accesibles (tuberías de agua, calefacción, marcos metálicos) al conductor de protección.",
                    ref = "ITC-BT-27 §2.1"
                ),
                Question(
                    q = "¿Cuál es la dotación mínima de tomas de corriente en el SALÓN o cuarto de estar de una vivienda según la ITC-BT-26?",
                    opts = listOf(
                        "1 toma cada 10 m²",
                        "1 toma por cada 6 m² de superficie con un mínimo de 3 tomas",
                        "Únicamente 2 tomas en esquinas opuestas",
                        "5 tomas fijas con independencia del tamaño"
                    ),
                    a = 1,
                    exp = "La ITC-BT-26 par. 2 fija para la sala de estar o salón 1 base de enchufe por cada 6 m² de superficie útil, con un mínimo reglamentario de 3 bases.",
                    ref = "ITC-BT-26 §2"
                ),
                Question(
                    q = "¿Qué curva de disparo magnetotérmico es la estándar recomendada para la protección de circuitos de alumbrado y usos generales domésticos?",
                    opts = listOf(
                        "Curva B (disparo magnético entre 3 y 5 In)",
                        "Curva C (disparo magnético entre 5 y 10 In)",
                        "Curva D (disparo magnético entre 10 y 20 In)",
                        "Curva Z (ultrarrápida)"
                    ),
                    a = 1,
                    exp = "La curva C es la estándar universal para instalaciones interiores de viviendas y usos generales comerciales, ya que soporta las pequeñas corrientes de inserción típicas de electrodomésticos sin disparos intempestivos.",
                    ref = "ITC-BT-22 e ITC-BT-25"
                ),
                Question(
                    q = "¿Cuál es la caída de tensión máxima admisible si se alimenta un motor eléctrico trifásico desde el cuadro interior CGMP?",
                    opts = listOf(
                        "1,5%",
                        "3,0%",
                        "5,0%",
                        "6,5%"
                    ),
                    a = 2,
                    exp = "La ITC-BT-19 par. 2.2.2 fija una caída de tensión máxima del 5% para circuitos interiores de fuerza motriz (motores y receptores industriales).",
                    ref = "ITC-BT-19 §2.2.2"
                ),
                Question(
                    q = "En una vivienda con aire acondicionado instalado y calefacción eléctrica, ¿qué circuitos adicionales mínimos deben proyectarse obligatoriamente?",
                    opts = listOf(
                        "C1 y C2 únicamente",
                        "C8 (calefacción eléctrica) y C9 (aire acondicionado) con protecciones de 25 A y cables de 6 mm²",
                        "Un circuito de 10 A compartido",
                        "No requieren circuitos adicionales si la potencia contratada es de 5,75 kW"
                    ),
                    a = 1,
                    exp = "La ITC-BT-25 par. 2.3 establece que en electrificación elevada por calefacción o climatización deben incorporarse los circuitos C8 (calefacción) y C9 (aire acondicionado) con conductores de 6 mm² y magnetotérmico de 25 A.",
                    ref = "ITC-BT-25 §2.3"
                ),
                Question(
                    q = "¿Cuál es el valor máximo de sensibilidad que debe tener un interruptor diferencial para considerarse protección complementaria de ALTA SENSIBILIDAD?",
                    opts = listOf(
                        "10 mA",
                        "30 mA (0,03 A)",
                        "100 mA",
                        "300 mA"
                    ),
                    a = 1,
                    exp = "La ITC-BT-24 define como protección complementaria contra contactos directos e indirectos los dispositivos diferenciales residuales de alta sensibilidad con corriente diferencial asignada no superior a 30 mA.",
                    ref = "ITC-BT-24 §4"
                ),
                Question(
                    q = "En locales que contengan saunas (ITC-BT-27), ¿qué tipo de aislamiento térmico y resistencia a la temperatura deben soportar los cables eléctricos?",
                    opts = listOf(
                        "Cables estándar de PVC H07V-K hasta 70 °C",
                        "Conductores con aislamiento especial de silicona aptos para temperaturas de al menos 170 °C (tipo H05S-K o similar)",
                        "Cables coaxiales de antena",
                        "Cables desnudos suspendidos"
                    ),
                    a = 1,
                    exp = "La ITC-BT-27 Sección 3 para saunas exige que los conductores instalados en las zonas de alta temperatura dispongan de aislamiento termoestable especial resistente a 170 °C como mínimo.",
                    ref = "ITC-BT-27 §3"
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
                ),
                Question(
                    q = "Para una instalación de puesta a tierra con electrodo horizontal enterrado directo en zanja, ¿cuál es la sección mínima de cable de COBRE DESNUDO?",
                    opts = listOf(
                        "16 mm²",
                        "25 mm²",
                        "35 mm²",
                        "50 mm²"
                    ),
                    a = 2,
                    exp = "La Tabla 1 de la ITC-BT-18 exige una sección mínima de 35 mm² para el conductor de cobre desnudo enterrado sin protección contra la corrosión.",
                    ref = "ITC-BT-18 Tabla 1"
                ),
                Question(
                    q = "¿Cuál es la longitud mínima reglamentaria recomendada para una pica vertical de puesta a tierra hincada en el terreno?",
                    opts = listOf(
                        "0,50 metros",
                        "1,00 metro",
                        "2,00 metros",
                        "5,00 metros"
                    ),
                    a = 2,
                    exp = "La ITC-BT-18 par. 3.2.1 normaliza las picas de acero cobreado o cobre con una longitud estándar mínima de 2 metros para asegurar el contacto con capas de terreno húmedas.",
                    ref = "ITC-BT-18 §3.2.1"
                ),
                Question(
                    q = "En un edificio protegido con instalación de PARARRAYOS, ¿cuál es el valor máximo reglamentario admisible de resistencia de la toma de tierra?",
                    opts = listOf(
                        "10 Ω (ohmios)",
                        "25 Ω (ohmios)",
                        "40 Ω (ohmios)",
                        "100 Ω (ohmios)"
                    ),
                    a = 0,
                    exp = "La ITC-BT-18 e ITC-BT-45 dictaminan que la toma de tierra de una instalación con pararrayos no debe superar una resistencia de 10 Ω para facilitar la rápida disipación de la descarga atmosférica sin generar sobretensiones peligrosas.",
                    ref = "ITC-BT-18 e ITC-BT-45"
                ),
                Question(
                    q = "¿Cuál es la sección mínima de los conductores de la UNIÓN EQUIPOTENCIAL PRINCIPAL de un edificio (que une el borne principal de tierra con las estructuras metálicas y tuberías de acometida)?",
                    opts = listOf(
                        "2,5 mm² de cobre",
                        "Al menos la mitad de la sección del conductor de protección principal del edificio, con un mínimo de 6 mm² y un máximo de 25 mm² de cobre",
                        "35 mm² siempre",
                        "1,5 mm²"
                    ),
                    a = 1,
                    exp = "La ITC-BT-18 par. 3.4 establece que la sección del conductor equipotencial principal no será inferior a la mitad del conductor de protección mayor, con un límite mínimo de 6 mm² y máximo de 25 mm² de cobre.",
                    ref = "ITC-BT-18 §3.4"
                ),
                Question(
                    q = "Si el conductor de fase de una línea es de cobre con sección S = 10 mm², ¿qué sección mínima debe tener su conductor de protección (PE)?",
                    opts = listOf(
                        "4 mm²",
                        "6 mm²",
                        "10 mm² (misma sección que la fase)",
                        "16 mm²"
                    ),
                    a = 2,
                    exp = "Según la Tabla 2 de la ITC-BT-18, cuando la sección de los conductores de fase S ≤ 16 mm², la sección del conductor de protección Sp debe ser idéntica a la de la fase (Sp = S = 10 mm²).",
                    ref = "ITC-BT-18 Tabla 2"
                ),
                Question(
                    q = "Si el conductor de fase de una línea es de cobre con sección S = 70 mm², ¿cuál es la sección reglamentaria del conductor de protección (PE)?",
                    opts = listOf(
                        "16 mm²",
                        "25 mm²",
                        "35 mm² (S / 2)",
                        "70 mm²"
                    ),
                    a = 2,
                    exp = "Para secciones de fase S > 35 mm², la sección del conductor de protección se calcula como Sp = S / 2. Para S = 70 mm²: Sp = 70 / 2 = 35 mm².",
                    ref = "ITC-BT-18 Tabla 2"
                ),
                Question(
                    q = "¿Por qué motivo técnico se exige un seccionador de puesta a tierra en la arqueta principal de toma de tierra?",
                    opts = listOf(
                        "Para cortar la electricidad de la vivienda en caso de impago",
                        "Para poder desacoplar el electrodo de tierra y medir su resistencia aislada con el telurómetro sin retorno por otras masas",
                        "Para conectar generadores de emergencia trifásicos",
                        "Para evitar la entrada de rayos por la red de agua"
                    ),
                    a = 1,
                    exp = "El borne o puente de desconexión rápida permite independizar la toma de tierra del resto de la instalación para verificar con precisión el valor óhmico del terreno mediante el telurómetro.",
                    ref = "ITC-BT-18 §6"
                ),
                Question(
                    q = "En caso de utilizar placas metálicas enterradas como electrodo de tierra, ¿cuál es el espesor mínimo para una placa de cobre?",
                    opts = listOf(
                        "0,5 mm",
                        "2 mm",
                        "5 mm",
                        "10 mm"
                    ),
                    a = 1,
                    exp = "La ITC-BT-18 par. 3.2.3 fija que las placas de cobre enterradas como electrodos deben tener un espesor mínimo de 2 mm (o 5 mm si son de hierro galvanizado) para resistir la corrosión.",
                    ref = "ITC-BT-18 §3.2.3"
                ),
                Question(
                    q = "¿Qué instrumento oficial y método de medida debe utilizarse preceptivamente para comprobar la resistencia de puesta a tierra?",
                    opts = listOf(
                        "Un polímetro digital en escala de continuidad sonora",
                        "Un telurómetro (medidor de resistencia de tierra) mediante el método de las dos picas auxiliares de potencial y corriente",
                        "Un osciloscopio de doble canal",
                        "Un luxómetro calibrado"
                    ),
                    a = 1,
                    exp = "La ITC-BT-18 e ITC-BT-03 exigen el uso del telurómetro aplicando el método de caída de potencial mediante dos electrodos auxiliares (sondas de tensión y corriente) clavados a distancias adecuadas.",
                    ref = "ITC-BT-18 e ITC-BT-03"
                ),
                Question(
                    q = "¿Cuál es el color normalizado para los bornes y conductores de protección de puesta a tierra funcional o de medida independiente?",
                    opts = listOf(
                        "Azul marino",
                        "Verde-amarillo",
                        "Rosa palo",
                        "Gris marengo"
                    ),
                    a = 1,
                    exp = "Todo conductor de protección o equipotencialidad se identificará sin excepción mediante la combinación bicolor verde-amarillo, quedando prohibido su uso para cualquier otro fin activo.",
                    ref = "ITC-BT-19 e ITC-BT-18"
                ),
                Question(
                    q = "En un esquema de distribución de tipo TT (el más común en España en baja tensión), ¿cómo se conectan el neutro y las masas?",
                    opts = listOf(
                        "El neutro del transformador se conecta a tierra y las masas de la instalación receptora a una toma de tierra independiente de la del neutro",
                        "El neutro y las masas se unen en un único cable común (PEN)",
                        "El neutro está totalmente aislado de tierra y las masas unidas a fase",
                        "No se utiliza conductor neutro"
                    ),
                    a = 0,
                    exp = "En el esquema TT (Primera T: neutro a tierra en el transformador; Segunda T: masas de los receptores conectadas a su propia toma de tierra local), la protección contra contactos indirectos se basa en interruptores diferenciales.",
                    ref = "ITC-BT-08 e ITC-BT-18"
                ),
                Question(
                    q = "En un esquema de distribución de tipo TN-S, ¿cómo se distribuyen los conductores de Neutro y Protección?",
                    opts = listOf(
                        "En un único conductor combinado PEN en toda la instalación",
                        "Separados en dos conductores distintos (Neutro 'N' y Protección 'PE') a lo largo de todo el esquema",
                        "El neutro va enterrado sin funda y el PE suspendido",
                        "El esquema TN-S no lleva conductor de protección"
                    ),
                    a = 1,
                    exp = "En el esquema TN-S (S = Separated), el conductor neutro (N) y el conductor de protección (PE) son totalmente independientes e independientes en toda la red de distribución.",
                    ref = "ITC-BT-08"
                ),
                Question(
                    q = "En el esquema TN-C, ¿qué conductor combina simultáneamente las funciones de neutro y conductor de protección?",
                    opts = listOf(
                        "El conductor de fase L1",
                        "El conductor PEN",
                        "El cable de antena",
                        "La tubería de gas"
                    ),
                    a = 1,
                    exp = "En el esquema TN-C (C = Combined), las funciones de neutro y conductor de protección están unificadas en un único conductor denominado PEN, prohibido en secciones inferiores a 10 mm² Cu o 16 mm² Al.",
                    ref = "ITC-BT-08"
                ),
                Question(
                    q = "¿En qué tipo de instalaciones es característico el uso del esquema IT (neutro aislado o impedante)?",
                    opts = listOf(
                        "En viviendas unifamiliares con piscina",
                        "En quirófanos de hospitales e industrias de proceso continuo donde un primer fallo a tierra no deba interrumpir el suministro",
                        "En alumbrado navideño municipal",
                        "En quioscos de prensa"
                    ),
                    a = 1,
                    exp = "El esquema IT (I = Isolé, T = Terre) se emplea en quirófanos (ITC-BT-38) e industrias críticas porque un primer defecto a masa genera una corriente de fuga muy pequeña que no provoca el disparo de protecciones, manteniendo el servicio.",
                    ref = "ITC-BT-08 e ITC-BT-38"
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
                ),
                Question(
                    q = "En un quirófano o sala de intervención quirúrgica (ITC-BT-38), ¿qué esquema de neutro es OBLIGATORIO para alimentar los equipos electromédicos que puedan entrar en contacto con el paciente?",
                    opts = listOf(
                        "Esquema TT estándar con diferencial de 30 mA",
                        "Esquema IT médico con transformador de aislamiento y monitor permanente de aislamiento",
                        "Esquema TN-C con neutro puesto a tierra común",
                        "Alimentación directa a 400 V sin protección"
                    ),
                    a = 1,
                    exp = "La ITC-BT-38 par. 2.1 exige esquema IT con transformador de separación de circuitos de uso médico (potencia 0,5 a 10 kVA) para que un primer fallo a masa no interrumpa el suministro ni cause microchoques cardíacos.",
                    ref = "ITC-BT-38 §2.1"
                ),
                Question(
                    q = "En el monitor de detección continua de aislamiento de un quirófano (ITC-BT-38), ¿a qué umbral de resistencia debe dispararse la alarma acústica y visual?",
                    opts = listOf(
                        "Al descender el aislamiento por debajo de 50 kΩ (50.000 Ω)",
                        "Al descender de 1 MΩ",
                        "Al superar 100 Ω",
                        "Solo cuando la corriente supere 16 A"
                    ),
                    a = 0,
                    exp = "La ITC-BT-38 par. 2.1 establece que el dispositivo vigilante del nivel de aislamiento debe emitir señal acústica y luminosa cuando la resistencia de aislamiento baje de 50 kΩ.",
                    ref = "ITC-BT-38 §2.1"
                ),
                Question(
                    q = "En un quirófano, ¿qué diferencia de potencial máxima admisible se tolera entre dos masas conductoras accesibles simultáneamente?",
                    opts = listOf(
                        "10 mV (0,010 V)",
                        "50 V",
                        "24 V",
                        "230 V"
                    ),
                    a = 0,
                    exp = "Para prevenir el microchoque ventricular en pacientes intervenidos, la ITC-BT-38 par. 2.2 exige que la diferencia de potencial entre partes metálicas conductoras en el quirófano no supere 10 mV eficaces.",
                    ref = "ITC-BT-38 §2.2"
                ),
                Question(
                    q = "En la clasificación de zonas con riesgo de explosión por gases o vapores inflamables (Clase I), ¿qué define a la ZONA 0?",
                    opts = listOf(
                        "Emplazamiento en el que la atmósfera explosiva está presente de modo permanente, o por largos períodos de tiempo, o con frecuencia",
                        "Lugar donde es probable que se forme atmósfera explosiva en funcionamiento normal",
                        "Lugar donde no es probable que se forme y si se forma dura muy poco tiempo",
                        "Cualquier zona al aire libre a más de 10 metros del suelo"
                    ),
                    a = 0,
                    exp = "La ITC-BT-29 par. 2.1 define Zona 0 como el área en la que existe una atmósfera explosiva gaseosa de forma continua o durante largos períodos (por ejemplo, el interior de un depósito de gasolina).",
                    ref = "ITC-BT-29 §2.1"
                ),
                Question(
                    q = "¿Qué modo de protección ATEX se basa en limitar la energía de las chispas o arcos eléctricos por debajo del umbral de ignición del gas circundante?",
                    opts = listOf(
                        "Envolvente antideflagrante (Ex d)",
                        "Seguridad intrínseca (Ex i)",
                        "Inmersión en aceite (Ex o)",
                        "Sobrepresión interna (Ex p)"
                    ),
                    a = 1,
                    exp = "La Seguridad Intrínseca (Ex i) se fundamenta en diseñar los circuitos de modo que ninguna chispa o efecto térmico contenga suficiente energía eléctrica para inflamar la mezcla de gas explosiva.",
                    ref = "ITC-BT-29 §4"
                ),
                Question(
                    q = "¿Cuál es el aforo previsto a partir del cual un bar, cafetería o restaurante se considera legalmente Local de Pública Concurrencia (LPC)?",
                    opts = listOf(
                        "Ocupación prevista superior a 20 personas",
                        "Ocupación prevista superior a 50 personas",
                        "Ocupación prevista superior a 100 personas",
                        "Todos los locales comerciales sin importar el aforo"
                    ),
                    a = 2,
                    exp = "La ITC-BT-28 par. 1 clasifica como locales de pública concurrencia a bares, restaurantes y cafeterías cuya ocupación calculada exceda de 100 personas (o teatros y cines con más de 50 personas).",
                    ref = "ITC-BT-28 §1"
                ),
                Question(
                    q = "¿Qué características debe tener el suministro de SOCORRO exigido en locales de pública concurrencia de gran aforo?",
                    opts = listOf(
                        "Una batería de 12 V portátil",
                        "Una fuente propia de energía (grupo electrógeno o baterías fijas) capaz de mantener al menos el 15% de la potencia contratada para servicios esenciales durante 2 horas",
                        "Una segunda acometida del mismo transformador de la distribuidora",
                        "Una dinamo manual de emergencia"
                    ),
                    a = 1,
                    exp = "La ITC-BT-28 par. 3 exige suministro de socorro que cubra como mínimo el 15% de la potencia total contratada para garantizar el funcionamiento del alumbrado de seguridad, bombas de achique y sistemas contra incendios.",
                    ref = "ITC-BT-28 §3"
                ),
                Question(
                    q = "En una instalación generadora fotovoltaica conectada a red de baja tensión (ITC-BT-40), ¿qué dispositivo de seguridad es OBLIGATORIO para evitar la inyección de tensión a la red pública durante un corte de suministro (modo isla involuntario)?",
                    opts = listOf(
                        "Un interruptor horario analógico",
                        "Un relé de protección de interconexión con detección de tensión y frecuencia (protección anti-isla)",
                        "Un transformador trifásico elevador",
                        "Un limitador de sobretensión permanente exclusivamente"
                    ),
                    a = 1,
                    exp = "La ITC-BT-40 par. 4.1 exige protecciones de interconexión con desconexión automática por sobre/subtensión y sobre/subfrecuencia (sistema anti-isla) para proteger a los operarios de la distribuidora.",
                    ref = "ITC-BT-40 §4.1"
                ),
                Question(
                    q = "En el Esquema 2 de la ITC-BT-52 (Recarga colectiva de vehículos en garajes comunitarios), ¿cómo se estructura la medida de energía?",
                    opts = listOf(
                        "Un único contador sin posibilidad de medir consumos individuales",
                        "Un contador principal colectivo en la centralización y contadores secundarios modulares para cada punto de recarga",
                        "Una batería de condensadores central",
                        "Contadores alquilados al ayuntamiento"
                    ),
                    a = 1,
                    exp = "La ITC-BT-52 par. 3 describe el Esquema 2 como una línea troncal colectiva con contador principal común y contadores secundarios de medida individual para facturar el consumo exacto de cada plaza.",
                    ref = "ITC-BT-52 §3"
                ),
                Question(
                    q = "¿Qué grado de protección IP mínimo deben tener los mecanismos, tomas y envolventes instalados en un LOCAL MOJADO (ITC-BT-30)?",
                    opts = listOf(
                        "IP20",
                        "IPX4 (protegido contra salpicaduras de agua en todas las direcciones)",
                        "IPX1",
                        "IP68 sumergible"
                    ),
                    a = 1,
                    exp = "La ITC-BT-30 par. 1 fija que en los locales mojados todo el material eléctrico, canalizaciones y cajas dispondrán de un grado de protección estanca no inferior a IPX4.",
                    ref = "ITC-BT-30 §1"
                ),
                Question(
                    q = "¿Cuál es la tensión máxima de seguridad admitida en alumbrado portátil de mano utilizado en el interior de calderas o recintos metálicos muy conductores (ITC-BT-36)?",
                    opts = listOf(
                        "12 V o 24 V en corriente alterna suministrada mediante transformador de seguridad (MBTS)",
                        "110 V con toma de tierra",
                        "230 V con doble aislamiento",
                        "400 V trifásica"
                    ),
                    a = 0,
                    exp = "La ITC-BT-36 par. 2 exige para lámparas portátiles en recintos metálicos estrechos tensiones de seguridad MBTS no superiores a 24 V (o 12 V), con el transformador situado fuera del recinto.",
                    ref = "ITC-BT-36 §2"
                ),
                Question(
                    q = "En instalaciones de cercas eléctricas para ganado (ITC-BT-39), ¿qué requisito de homologación deben cumplir los generadores de impulsos?",
                    opts = listOf(
                        "Alimentarse directamente de la red a 230 V sin transformador",
                        "Cumplir la norma UNE-EN 60335-2-76 y disponer de limitación de energía de impulso máxima segura",
                        "Tener una potencia continua superior a 5 kW",
                        "Conectarse a la toma de tierra del edificio de viviendas"
                    ),
                    a = 1,
                    exp = "La ITC-BT-39 exige que los electrificadores de cercas estén certificados bajo norma UNE-EN 60335-2-76 para limitar la energía y duración de los impulsos a valores no letales.",
                    ref = "ITC-BT-39"
                ),
                Question(
                    q = "¿Qué protección mecánica mínima deben tener las canalizaciones de alumbrado exterior enterradas en zanja bajo acera según la ITC-BT-09?",
                    opts = listOf(
                        "Estar enterradas a 0,20 m sin tubo",
                        "Estar alojadas en tubos de diámetro adecuado enterrados a una profundidad mínima de 0,40 m bajo acera (o 0,60 m bajo calzada)",
                        "Ir suspendidas de cables de acero aéreos",
                        "Colocarse en canaletas de PVC en superficie"
                    ),
                    a = 1,
                    exp = "La ITC-BT-09 par. 2.1 fija que los cables subterráneos de alumbrado exterior se tenderán bajo tubo protector enterrado a profundidad mínima de 0,40 m en aceras y 0,60 m en calzadas transitables.",
                    ref = "ITC-BT-09 §2.1"
                ),
                Question(
                    q = "En una instalación temporal de ferias o atracciones (ITC-BT-34), ¿qué sensibilidad deben tener los interruptores diferenciales que protejan los circuitos de tomas de corriente accesibles al público?",
                    opts = listOf(
                        "Diferenciales de 30 mA de alta sensibilidad",
                        "Diferenciales de 300 mA",
                        "Diferenciales regulables a 1 A",
                        "No se exigen diferenciales si la atracción tiene estructura metálica"
                    ),
                    a = 0,
                    exp = "La ITC-BT-34 par. 4 exige protección diferencial de alta sensibilidad ≤ 30 mA para todos los circuitos terminales y tomas de corriente en atracciones, puestos de feria e instalaciones temporales.",
                    ref = "ITC-BT-34 §4"
                )
            )
        ),
        "suministro" to ModuleDefinition(
            id = "suministro",
            label = "Suministro y Puesta en Servicio (Art. 79-91)",
            icon = "⚡",
            color = "#FF58A6FF",
            questions = listOf(
                Question(
                    q = "¿Cuál es el documento preceptivo e indispensable que debe estar registrado ante la Administración antes de que la empresa distribuidora pueda conectar de forma definitiva una instalación a la red?",
                    opts = listOf(
                        "El Certificado de Instalación Eléctrica (CIE o Boletín) debidamente diligenciado por la Comunidad Autónoma",
                        "El albarán de compra de los magnetotérmicos",
                        "La licencia de obras municipal exclusivamente",
                        "Un justificante bancario de pago de tasas"
                    ),
                    a = 0,
                    exp = "Conforme a los Artículos 79 y 81 del REBT (y Art. 18), el Certificado de Instalación Eléctrica es el documento legal oficial suscrito por el instalador autorizado que acredita la conformidad técnica y reglamentaria de la instalación antes del enganche definitivo por la distribuidora.",
                    ref = "Art. 79 y 81 REBT"
                ),
                Question(
                    q = "¿Qué periodicidad de inspección periódica oficial por Organismo de Control (OCA) se exige a las instalaciones de más de 100 kW de potencia total instalada en locales o edificios?",
                    opts = listOf(
                        "Cada 2 años",
                        "Cada 5 años",
                        "Cada 10 años",
                        "Cada 20 años"
                    ),
                    a = 2,
                    exp = "La ITC-BT-05 par. 4.2 e ITC complementarias establecen que las instalaciones comunes en edificios de viviendas o industriales de potencia superior a 100 kW deben pasar inspección periódica por una OCA cada 10 años.",
                    ref = "ITC-BT-05 §4.2"
                ),
                Question(
                    q = "¿Qué profesional o entidad está legalmente facultada para emitir el acta oficial de inspección periódica reglamentaria?",
                    opts = listOf(
                        "El electricista habitual de mantenimiento",
                        "Un Organismo de Control Autorizado (OCA) acreditado por ENAC e inscrito en el registro de la Comunidad Autónoma",
                        "El presidente de la comunidad de vecinos",
                        "El técnico de la empresa comercializadora de energía"
                    ),
                    a = 1,
                    exp = "Las inspecciones iniciales y periódicas reglamentarias son potestad exclusiva de los Organismos de Control Autorizados (OCAs) legalmente acreditados e independientes de las partes.",
                    ref = "ITC-BT-05 §3"
                ),
                Question(
                    q = "En una instalación de pública concurrencia de nueva ejecución, ¿qué trámite previo ante OCA es obligatorio antes de solicitar el alta de suministro a la distribuidora?",
                    opts = listOf(
                        "Una declaración jurada del arquitecto",
                        "La Inspección Inicial con calificación Favorable emitida por una OCA",
                        "Una auditoría energética de consumo",
                        "No se requiere inspección inicial si la potencia es inferior a 50 kW"
                    ),
                    a = 1,
                    exp = "La ITC-BT-05 par. 4.1 exige que los locales de pública concurrencia, quirófanos, instalaciones ATEX y garajes de más de 25 plazas pasen obligatoriamente una Inspección Inicial Favorable por OCA previa a su puesta en servicio.",
                    ref = "ITC-BT-05 §4.1"
                ),
                Question(
                    q = "¿Qué obligación de archivo documental tiene la empresa instaladora respecto a los Certificados de Instalación que ha suscrito?",
                    opts = listOf(
                        "Destruirlos transcurrido 1 mes",
                        "Conservar copias de los certificados, memorias o proyectos y actas de verificación durante un plazo mínimo de 5 años a disposición de la autoridad competente",
                        "Enviarlos físicamente al Ministerio de Industria cada trimestre",
                        "No está obligada a guardar copias una vez entregado el original al cliente"
                    ),
                    a = 1,
                    exp = "La ITC-BT-03 y la legislación de seguridad industrial obligan a las empresas instaladoras a mantener un registro y archivo documental de todas las instalaciones ejecutadas durante al menos 5 años.",
                    ref = "ITC-BT-03 §5"
                ),
                Question(
                    q = "En un contrato de mantenimiento de instalaciones eléctricas de baja tensión en locales de pública concurrencia, ¿quién debe suscribir el contrato?",
                    opts = listOf(
                        "El titular de la instalación con una empresa instaladora autorizada en la categoría correspondiente",
                        "El ayuntamiento de oficio",
                        "La empresa distribuidora con la compañía de seguros",
                        "Cualquier técnico de grado medio aunque no esté de alta en empresa instaladora"
                    ),
                    a = 0,
                    exp = "La reglamentación autonómica y el REBT disponen que el titular debe mantener en vigor un contrato de mantenimiento con empresa instaladora autorizada para instalaciones con inspección periódica obligatoria.",
                    ref = "Art. 19 e ITC-BT-05"
                ),
                Question(
                    q = "Si una instalación eléctrica cambia de titular o usuario, ¿es necesario emitir un nuevo Certificado de Instalación (CIE)?",
                    opts = listOf(
                        "Sí, siempre en todo cambio de nombre",
                        "Solo si la instalación tiene una antigüedad superior a 20 años y se solicita una modificación de potencia o condiciones de suministro a la distribuidora",
                        "No, el boletín original nunca caduca ni requiere renovación ante la distribuidora",
                        "Solo si el nuevo titular es extranjero"
                    ),
                    a = 1,
                    exp = "La normativa del sector eléctrico establece que para instalaciones con más de 20 años de antigüedad, la distribuidora puede exigir la revisión y emisión de un nuevo CIE ante aumentos de potencia o reactivación de suministros.",
                    ref = "Art. 83 REBT y RD 1955/2000"
                ),
                Question(
                    q = "¿Qué plazo máximo concede la Administración para la subsanación de defectos Graves señalados en un acta de inspección de OCA?",
                    opts = listOf(
                        "15 días naturales",
                        "6 meses a contar desde la fecha de notificación del acta",
                        "2 años",
                        "Indefinido si se presenta un recurso de alzada"
                    ),
                    a = 1,
                    exp = "La ITC-BT-05 fija un plazo máximo improrrogable de 6 meses para que el titular subsane los defectos calificados como Graves ante una nueva visita de comprobación de la OCA.",
                    ref = "ITC-BT-05 §5"
                ),
                Question(
                    q = "¿Cuál es la función principal del libro o manual de mantenimiento entregado al titular de una instalación de baja tensión?",
                    opts = listOf(
                        "Detallar el calendario de revisiones preventivas, periodicidad de comprobación de diferenciales y registro de incidencias",
                        "Servir como factura comercial justificativa de IVA",
                        "Describir la biografía del instalador",
                        "Autorizar al propietario a modificar cuadros eléctricos sin técnico"
                    ),
                    a = 0,
                    exp = "El manual de instrucciones y mantenimiento recoge las pautas de uso seguro, la comprobación periódica obligatoria del pulsador de prueba de los diferenciales y el registro de revisiones anuales.",
                    ref = "Art. 18.4 REBT"
                ),
                Question(
                    q = "¿Qué consecuencia legal inmediata tiene la emisión de un acta de inspección con calificación NEGATIVA por defectos Muy Graves?",
                    opts = listOf(
                        "Una rebaja en la tarifa eléctrica de la factura",
                        "La no emisión del certificado favorable y la notificación urgente al órgano competente para la suspensión cautelar del suministro eléctrico",
                        "Una advertencia verbal sin consecuencias administrativas",
                        "La obligación de pagar una tasa doble el año siguiente"
                    ),
                    a = 1,
                    exp = "La calificación Negativa por defectos Muy Graves impide la puesta en marcha de la instalación y obliga a la desconexión o precinto de la misma por riesgo grave e inminente de electrocución o incendio.",
                    ref = "ITC-BT-05 §5"
                ),
                Question(
                    q = "En las instalaciones temporales para ferias y festejos (ITC-BT-34), ¿cuándo debe efectuarse la verificación y emisión del boletín?",
                    opts = listOf(
                        "Al finalizar la feria después de desmontar los puestos",
                        "Previamente a la puesta en tensión y funcionamiento público en cada nuevo emplazamiento donde se monte",
                        "Una sola vez cada 5 años independientemente de los traslados",
                        "No se precisa boletín para ferias de menos de 3 días"
                    ),
                    a = 1,
                    exp = "Las instalaciones provisionales o desmontables precisan revisión, verificación y certificado de instalación para cada montaje individual antes de autorizarse su conexión y apertura al público.",
                    ref = "ITC-BT-34"
                ),
                Question(
                    q = "¿Quién debe firmar el Certificado de Instalación Eléctrica (CIE)?",
                    opts = listOf(
                        "Cualquier albañil o fontanero de la obra",
                        "El instalador autorizado cualificado integrado en la plantilla de la empresa instaladora habilitada",
                        "El presidente de la asociación de vecinos",
                        "El inspector de Hacienda municipal"
                    ),
                    a = 1,
                    exp = "El CIE debe estar formalmente suscrito por el instalador cualificado o técnico competente adscrito a la empresa instaladora autorizada que asume la responsabilidad de la ejecución.",
                    ref = "ITC-BT-03 §5"
                ),
                Question(
                    q = "¿Qué comprobación de seguridad debe realizar mensualmente el usuario final en su cuadro general (CGMP)?",
                    opts = listOf(
                        "Apretar los tornillos con un destornillador metálico sin guantes",
                        "Pulsar el botón de test ('T') del interruptor diferencial para verificar el correcto disparo mecánico del dispositivo",
                        "Medir la tensión con un multímetro en bornes del IGA",
                        "Desconectar el contador de la distribuidora"
                    ),
                    a = 1,
                    exp = "Las instrucciones oficiales de mantenimiento recomiendan accionar periódicamente el pulsador de test del diferencial para asegurar que el mecanismo interno de disparo no se encuentre trabado o agarrotado por falta de uso.",
                    ref = "ITC-BT-17 e ITC-BT-24"
                ),
                Question(
                    q = "¿Cuál es el trámite administrativo para legalizar una modificación que afecte a menos del 50% de la potencia instalada sin alterar elementos estructurales de seguridad?",
                    opts = listOf(
                        "Proyecto visado por 3 colegios profesionales",
                        "Memoria Técnica de Diseño (MTD) y Certificado de Instalación tramitados ante la Comunidad Autónoma",
                        "Comunicación verbal al repartidor de la compañía",
                        "No requiere ningún trámite ni registro"
                    ),
                    a = 1,
                    exp = "Las modificaciones que no alcanzan el umbral de modificación de importancia (> 50%) se tramitan mediante MTD y emisión del correspondiente certificado de instalación sin necesidad de proyecto técnico.",
                    ref = "Art. 2.2 e ITC-BT-04"
                ),
                Question(
                    q = "¿Qué documento es preceptivo registrar ante la Administración en instalaciones generadoras fotovoltaicas de autoconsumo en baja tensión para permitir la compensación de excedentes?",
                    opts = listOf(
                        "El Certificado de Instalación Eléctrica en la modalidad de generadora / autoconsumo diligenciado por la Comunidad Autónoma y el Certificado de Fin de Obra",
                        "Una factura proforma del panel solar",
                        "El folleto comercial del inversor en inglés",
                        "Un permiso de vertido de la confederación hidrográfica"
                    ),
                    a = 0,
                    exp = "Para activar el contrato de acceso y la compensación simplificada de excedentes, la distribuidora y comercializadora exigen el CIE registrado de la instalación generadora conforme a la ITC-BT-40 y RD 244/2019.",
                    ref = "ITC-BT-40 y RD 244/2019"
                )
            )
        ),
        "tubos" to ModuleDefinition(
            id = "tubos",
            label = "Tuberías y Canalizaciones (ITC-BT-21)",
            icon = "🔧",
            color = "#FF3FB950",
            questions = listOf(
                Question(
                    q = "¿Qué fórmula oficial se utiliza para calcular el diámetro interior mínimo de un tubo protector que aloje conductores de distintas secciones según la ITC-BT-21?",
                    opts = listOf(
                        "D = (Σ secciones totales exteriores de los conductores) / 0,9",
                        "D = (Σ secciones de cobre) * 2,5",
                        "D = 16 mm de forma universal",
                        "D = (Σ diámetros exteriores) / 1,5"
                    ),
                    a = 0,
                    exp = "La ITC-BT-21 par. 1.2 establece que cuando se alojen conductores de diferentes secciones en un mismo tubo, el diámetro interior mínimo se calculará dividiendo la suma de las secciones totales de los conductores (incluido aislamiento) entre 0,9 para asegurar un coeficiente de relleno inferior al 50%.",
                    ref = "ITC-BT-21, Tabla 21.1"
                ),
                Question(
                    q = "¿Está permitido realizar empalmes o uniones de conductores eléctricos dentro de los tubos protectores?",
                    opts = listOf(
                        "Sí, si se utiliza cinta aislante vulcanizada de alta calidad",
                        "No, queda terminantemente prohibido; todo empalme o derivación debe realizarse obligatoriamente dentro de cajas de derivación registrables",
                        "Solo en tubos de diámetro superior a 40 mm",
                        "Sí, si se utilizan fichas de empalme cerámicas"
                    ),
                    a = 1,
                    exp = "La ITC-BT-21 par. 2 prohíbe de forma categórica cualquier tipo de empalme o derivación dentro de los tubos protectores para evitar puntos calientes inaccesibles e imposibilidad de sustitución del cableado.",
                    ref = "ITC-BT-21 §2"
                ),
                Question(
                    q = "¿Cuál es el diámetro exterior mínimo del tubo protector exigido para una Derivación Individual (DI)?",
                    opts = listOf(
                        "16 mm",
                        "20 mm",
                        "25 mm",
                        "32 mm"
                    ),
                    a = 3,
                    exp = "La ITC-BT-15 par. 2 fija un diámetro exterior mínimo de 32 mm para el tubo de las derivaciones individuales, garantizando espacio para ampliaciones futuras y fácil tracción.",
                    ref = "ITC-BT-15 §2"
                ),
                Question(
                    q = "¿Cómo deben trazarse reglamentariamente los tubos protectores empotrados en paredes y tabiques?",
                    opts = listOf(
                        "En diagonal desde el interruptor hasta la caja de derivación para ahorrar tubo",
                        "Siguiendo exclusivamente líneas verticales y horizontales, a distancias seguras de techos, suelos y esquinas",
                        "En zigzag para disipar mejor el calor",
                        "En círculos concéntricos alrededor de las cajas"
                    ),
                    a = 1,
                    exp = "La ITC-BT-21 par. 2.2 exige que los tubos empotrados sigan trayectorias perfectamente verticales u horizontales a distancias estandarizadas para prevenir taladros y perforaciones accidentales en el tabique.",
                    ref = "ITC-BT-21 §2.2"
                ),
                Question(
                    q = "Para 3 conductores unipolares de 2,5 mm² en canalización empotrada ordinaria, ¿cuál es el diámetro exterior mínimo del tubo según la ITC-BT-21?",
                    opts = listOf(
                        "16 mm",
                        "20 mm",
                        "25 mm",
                        "32 mm"
                    ),
                    a = 1,
                    exp = "La Tabla 5 de la ITC-BT-21 fija un diámetro exterior mínimo de 20 mm para 3 conductores de sección 2,5 mm² alojados en tubo curvable o flexible empotrado en obra.",
                    ref = "ITC-BT-21 Tabla 5"
                ),
                Question(
                    q = "Para 3 conductores de sección 1,5 mm² bajo tubo empotrado en pared, ¿cuál es el diámetro exterior mínimo del tubo?",
                    opts = listOf(
                        "16 mm",
                        "20 mm",
                        "25 mm",
                        "32 mm"
                    ),
                    a = 0,
                    exp = "La Tabla 5 de la ITC-BT-21 indica que para 3 conductores de 1,5 mm² empotrados el diámetro exterior mínimo reglamentario es de 16 mm.",
                    ref = "ITC-BT-21 Tabla 5"
                ),
                Question(
                    q = "Para 3 conductores de 6 mm² empotrados en pared ordinaria, ¿qué diámetro exterior mínimo de tubo se exige?",
                    opts = listOf(
                        "16 mm",
                        "20 mm",
                        "25 mm",
                        "32 mm"
                    ),
                    a = 2,
                    exp = "Conforme a la Tabla 5 de la ITC-BT-21, 3 conductores unipolares de 6 mm² empotrados precisan un tubo con diámetro exterior mínimo de 25 mm.",
                    ref = "ITC-BT-21 Tabla 5"
                ),
                Question(
                    q = "¿Cuál es el radio mínimo de curvatura admisible al doblar un tubo protector rígido o curvable?",
                    opts = listOf(
                        "El radio de curvatura no será inferior a 6 veces el diámetro exterior del tubo",
                        "El doble del grosor del cable",
                        "50 centímetros fijos",
                        "Se puede doblar a 90 grados en ángulo vivo si se calienta con soplete"
                    ),
                    a = 0,
                    exp = "La ITC-BT-21 par. 1.2 establece que las curvas en los tubos deben ser continuas, sin estrangulamientos ni aplastamientos, con un radio de curvatura no inferior a 6 veces el diámetro exterior del tubo para permitir el paso de cables sin fricción excesiva.",
                    ref = "ITC-BT-21 §1.2"
                ),
                Question(
                    q = "¿Cuál es la distancia máxima recomendada entre cajas de registro intermedias en tramos rectos de canalización por tubo?",
                    opts = listOf(
                        "Máximo 5 metros",
                        "Máximo 15 metros en tramos rectos (o tras un máximo de 3 curvas en ángulo recto)",
                        "Máximo 50 metros",
                        "No se exigen cajas intermedias si se utiliza guía de nylon"
                    ),
                    a = 1,
                    exp = "La ITC-BT-21 par. 2.2 recomienda intercalar cajas de registro como máximo cada 15 metros en tramos rectos o tras dos/tres cambios de dirección para asegurar el enhebrado y la sustitución de cables.",
                    ref = "ITC-BT-21 §2.2"
                ),
                Question(
                    q = "¿Qué grado de estanqueidad y protección IP mínimo deben tener las cajas de derivación empotradas en tabiques secos?",
                    opts = listOf(
                        "IP20",
                        "IP40",
                        "IP55",
                        "IP68"
                    ),
                    a = 1,
                    exp = "Las cajas de derivación y registro empotradas en paredes ordinarias dispondrán de una protección mínima IP40 frente a la penetración de cuerpos sólidos y polvo.",
                    ref = "ITC-BT-21 §2"
                ),
                Question(
                    q = "En canalizaciones con tubos superficiales en locales industriales, ¿cómo deben fijarse los tubos a paredes y techos?",
                    opts = listOf(
                        "Pegados con silicona caliente",
                        "Mediante abrazaderas metálicas o de plástico homologadas atornilladas, separadas a distancias máximas según el diámetro del tubo (máx. 0,5 a 1 m)",
                        "Colgados con alambre galvanizado suelto",
                        "Apoyados sobre las vigas sin fijación"
                    ),
                    a = 1,
                    exp = "La ITC-BT-21 par. 2.1 exige fijación mediante bridas o abrazaderas resistentes a la corrosión firmemente atornilladas a distancias regulares (máximo 0,80 m a 1 m) para evitar pandeo o desprendimientos.",
                    ref = "ITC-BT-21 §2.1"
                ),
                Question(
                    q = "¿Qué código de clasificación de 4 dígitos según norma UNE-EN 50086 / UNE-EN 61386 define las características mecánicas y térmicas de un tubo?",
                    opts = listOf(
                        "Código postal del fabricante",
                        "Dígito 1 (Resistencia a la compresión), Dígito 2 (Resistencia al impacto), Dígito 3 (Temperatura mínima), Dígito 4 (Temperatura máxima)",
                        "Voltaje máximo, amperaje, frecuencia y potencia",
                        "Largo, ancho, alto y peso"
                    ),
                    a = 1,
                    exp = "La ITC-BT-21 clasifica los tubos mediante 4 dígitos normalizados: 1º Compresión (Ligera/Media/Fuerte), 2º Impacto (Ligero/Medio/Fuerte), 3º Temperatura mínima (-5°C/-15°C/-25°C) y 4º Temperatura máxima (+60°C/+90°C/+105°C).",
                    ref = "ITC-BT-21 Tabla 1"
                ),
                Question(
                    q = "En una canaleta o canal protectora con tapa registrable para instalaciones de superficie, ¿cuál es el coeficiente máximo de llenado de la sección útil?",
                    opts = listOf(
                        "Se puede llenar al 100% a presión",
                        "El área total ocupada por los cables no superará el 40% al 50% de la sección interior útil de la canaleta",
                        "Máximo 1 cable por canaleta",
                        "75% si los cables son libres de halógenos"
                    ),
                    a = 1,
                    exp = "La ITC-BT-21 par. 3 establece que los conductores no ocuparán más del 40-50% de la sección transversal útil de la canal para evitar sobrecalentamientos y permitir la disipación térmica del haz cableado.",
                    ref = "ITC-BT-21 §3"
                ),
                Question(
                    q = "¿Se pueden mezclar cables de telecomunicaciones (fibra, datos, TV) y cables de energía eléctrica de 230/400 V dentro del mismo compartimento de una canaleta?",
                    opts = listOf(
                        "Sí, mientras quepan físicamente",
                        "No; deben ir en compartimentos o canales físicamente separados mediante tabique divisor continuo para evitar interferencias electromagnéticas y riesgos de aislamiento",
                        "Solo si el cable de datos tiene conector blindado",
                        "Sí, si la canaleta es metálica"
                    ),
                    a = 1,
                    exp = "La ITC-BT-21 e ITC-BT-19 prohíben la compartición de compartimentos entre circuitos de energía y telecomunicaciones/datos sin separación física dieléctrica o metálica puesta a tierra.",
                    ref = "ITC-BT-21 e ITC-BT-19"
                ),
                Question(
                    q = "Para tubos enterrados en zanja para acometidas o canalizaciones subterráneas de BT, ¿qué resistencia mínima a la compresión e impacto debe tener el tubo según ITC-BT-07 e ITC-BT-21?",
                    opts = listOf(
                        "Tubo ligero de compresión 125 N",
                        "Tubo curvable o rígido con resistencia a la compresión no inferior a 450 N (Tipo Normal o Fuerte) y resistencia al impacto de al menos 28 Julios (IK10)",
                        "Tubos de cartón alquitranado",
                        "No se exige resistencia mecánica si va enterrado a más de 10 cm"
                    ),
                    a = 1,
                    exp = "La ITC-BT-07 e ITC-BT-21 para canalizaciones enterradas prescriben tubos con código de compresión mínima de 450 N (Código 4) para resistir el peso de las tierras y el paso de tráfico rodado.",
                    ref = "ITC-BT-07 e ITC-BT-21"
                )
            )
        ),
        "itc_01" to ModuleDefinition(
            id = "itc_01",
            label = "ITC-BT-01 Terminología Reglamentaria",
            icon = "📖",
            color = "#40c463",
            questions = listOf(
                Question(
                    q = "¿Qué se entiende reglamentariamente por 'Masa' según la ITC-BT-01?",
                    opts = listOf(
                        "Cualquier estructura metálica del edificio conectada voluntariamente a tierra",
                        "Parte conductora de un equipo eléctrico susceptible de ser tocada y que normalmente no está bajo tensión, pero que puede ponerse bajo tensión en caso de fallo",
                        "El borne de puesta a tierra principal situado en el cuadro general de distribución",
                        "Todo conductor activo perteneciente al circuito de potencia que transporte corriente de retorno"
                    ),
                    a = 1,
                    exp = "La ITC-BT-01 define 'Masa' como la parte conductora de un material eléctrico susceptible de ser tocada por una persona, que normalmente no está bajo tensión pero que puede ponerse bajo tensión cuando falla el aislamiento principal. No deben confundirse las masas con los elementos conductores ajenos a la instalación.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "Según las definiciones de la ITC-BT-01, ¿qué es un 'Elemento conductor'?",
                    opts = listOf(
                        "Un cable aislado destinado al transporte exclusivo de energía eléctrica",
                        "El electrodo enterrado de cobre desnudo destinado a disipar corrientes de defecto",
                        "Estructura o parte metálica susceptible de propagar un potencial que no forma parte de la instalación eléctrica",
                        "El embarrado de cobre situado en el interior de la caja general de protección"
                    ),
                    a = 2,
                    exp = "Conforme a la ITC-BT-01, un elemento conductor es toda parte conductora que no forma parte de la instalación eléctrica y que es susceptible de introducir un potencial, generalmente el de tierra. Ejemplos típicos son las tuberías metálicas de agua o gas, armaduras de hormigón y vigas de acero estructurales.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Cómo define la ITC-BT-01 la 'Tensión de defecto' en una instalación eléctrica?",
                    opts = listOf(
                        "Tensión que aparece a causa de un defecto de aislamiento entre dos masas, o entre una masa y un punto de tierra de referencia", // VERIFICAR-BOE
                        "La caída de tensión porcentual producida en bornes del receptor más alejado del cuadro",
                        "La sobretensión transitoria provocada por descargas atmosféricas en la red de distribución",
                        "La diferencia de potencial nominal existente entre la fase activa y el conductor neutro"
                    ),
                    a = 0,
                    exp = "La ITC-BT-01 establece que la tensión de defecto es la diferencia de potencial que aparece a causa de un fallo o defecto de aislamiento entre dos masas, entre una masa y un elemento conductor, o entre una masa y una tierra de referencia. Es el valor básico para coordinar la desconexión automática.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Qué caracteriza a un 'Corte omnipolar' según las definiciones de la ITC-BT-01?",
                    opts = listOf(
                        "El disparo exclusivo del polo del conductor neutro ante corrientes armónicas de tercer orden",
                        "La desconexión secuencial en la que el neutro se desconecta siempre antes que las fases activas",
                        "El seccionamiento del conductor de protección de tierra para realizar mediciones de aislamiento",
                        "La apertura simultánea o casi simultánea de todos los conductores activos que alimentan el circuito"
                    ),
                    a = 3,
                    exp = "Según la ITC-BT-01, el corte omnipolar es aquel corte en el que se interrumpe la corriente en todos los conductores activos (todas las fases y el conductor neutro si existe). En los dispositivos tetrapolares o bipolares con corte omnipolar, el neutro no debe abrirse antes que las fases ni cerrarse después.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Cuál es la misión principal del 'Conductor de protección (CP o PE)' definido en la ITC-BT-01?",
                    opts = listOf(
                        "Transportar permanentemente la corriente desequilibrada producida por los receptores monofásicos",
                        "Garantizar la protección contra choques eléctricos uniendo las masas metálicas a la toma de tierra",
                        "Servir de soporte mecánico para el tendido de los cables activos en bandejas perforadas",
                        "Disipar las pérdidas por efecto Joule producidas por las sobrecargas en las líneas generales"
                    ),
                    a = 1,
                    exp = "La ITC-BT-01 define el conductor de protección como aquel conductor prescrito para ciertas medidas de protección contra choques eléctricos y destinado a conectar eléctricamente masas de los equipos entre sí, con otros elementos conductores o con el borne principal de tierra.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "En el contexto del REBT y la ITC-BT-01, ¿qué constituye un 'Contacto directo'?",
                    opts = listOf(
                        "El contacto de personas o animales domésticos con partes habitualmente bajo tensión de la instalación",
                        "El contacto con la carcasa metálica de un electrodoméstico que ha perdido su aislamiento interno",
                        "La unión equipotencial principal establecida entre las tuberías metálicas de agua y la tierra",
                        "El cebado de un arco eléctrico entre dos barras del cuadro general de distribución"
                    ),
                    a = 0,
                    exp = "La ITC-BT-01 define contacto directo como el contacto de personas o animales con partes activas de los materiales y circuitos eléctricos que se encuentran habitualmente bajo tensión en servicio normal. La protección se logra por aislamiento, barreras, envolventes o alejamiento.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Cómo se define reglamentariamente el 'Contacto indirecto' en la ITC-BT-01?",
                    opts = listOf(
                        "El contacto accidental con conductores aéreos desnudos de baja tensión durante trabajos de poda",
                        "La inducción electromagnética en cables de telecomunicaciones tendidos junto a líneas de fuerza",
                        "El contacto de personas o animales con masas que han quedado bajo tensión debido a un fallo de aislamiento",
                        "La aproximación a una distancia menor de 30 centímetros de un embarrado de baja tensión"
                    ),
                    a = 2,
                    exp = "El contacto indirecto es definido en la ITC-BT-01 como el contacto de personas o animales domésticos con masas que se han puesto accidentalmente bajo tensión a consecuencia de un defecto en el aislamiento de las partes activas. Su protección básica es la desconexión por interruptor diferencial coordinado con la tierra.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "Según la ITC-BT-01, ¿qué es la 'Corriente de fuga' en una instalación eléctrica?",
                    opts = listOf(
                        "La intensidad máxima de cortocircuito en bornes secundarios del transformador de distribución",
                        "La corriente que circula por el neutro debida exclusivamente a la distorsión armónica de las cargas",
                        "La corriente transitoria absorbida por los motores de inducción durante el proceso de arranque",
                        "La corriente que fluye a tierra o a elementos conductores en un circuito eléctricamente sano en ausencia de defectos" // VERIFICAR-BOE
                    ),
                    a = 3,
                    exp = "La ITC-BT-01 define corriente de fuga como la corriente que, en ausencia de defectos de aislamiento, se transmite desde las partes activas de la instalación hacia la tierra o hacia elementos conductores a través del dieléctrico o capacidades parásitas. No debe confundirse con la corriente de defecto franco.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Qué se entiende por 'Defecto franco' según la terminología de la ITC-BT-01?",
                    opts = listOf(
                        "Una fuga lenta y progresiva a través del polvo acumulado en la superficie de un aislador",
                        "Un fallo de aislamiento cuya impedancia entre dos puntos con potencial diferente es prácticamente despreciable",
                        "La desconexión indebida de un interruptor diferencial producida por perturbaciones de alta frecuencia",
                        "El corte fortuito del suministro eléctrico por apertura del disyuntor en cabecera de la red"
                    ),
                    a = 1,
                    exp = "La ITC-BT-01 define defecto franco como la unión accidental de impedancia prácticamente nula producida entre dos puntos de diferente potencial. En caso de defecto franco entre fase y neutro o entre dos fases, se originan las máximas corrientes de cortocircuito admisibles por la instalación.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Cómo define la ITC-BT-01 una 'Canalización eléctrica'?",
                    opts = listOf(
                        "El conjunto constituido por uno o varios conductores y los elementos que aseguran su fijación y su protección mecánica",
                        "Únicamente el tubo rígido de PVC empotrado en las rozas practicadas en tabiquería de ladrillo",
                        "La zanja excavada en el terreno natural donde se alojan directamente los cables subterráneos",
                        "El pozo vertical de registro utilizado en edificios residenciales para las derivaciones individuales"
                    ),
                    a = 0,
                    exp = "En la ITC-BT-01, canalización eléctrica es el conjunto formado por uno o varios conductores eléctricos y por los elementos que aseguran su fijación y, en su caso, su protección mecánica (tubos, canales protectores, bandejas, molduras o conductos). Es un concepto amplio que abarca tanto el cable como su envolvente protectora.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "En el marco de la ITC-BT-01, ¿qué distingue a una 'Sobrecarga' de un cortocircuito?",
                    opts = listOf(
                        "La sobrecarga se produce siempre por un defecto franco de aislamiento directo entre fase y tierra",
                        "La sobrecarga implica corrientes miles de veces superiores a la corriente asignada del cable",
                        "La sobrecarga es un exceso de corriente en un circuito eléctricamente sano sin presencia de defecto franco",
                        "La sobrecarga solo tiene lugar en circuitos alimentados por corriente continua de muy baja tensión"
                    ),
                    a = 2,
                    exp = "La ITC-BT-01 define sobrecarga como el régimen de funcionamiento de un circuito eléctricamente sano en el que la corriente supera a la nominal asignada debido a la conexión de excesiva carga o potencia. A diferencia del cortocircuito, no hay defecto franco ni avería dieléctrica entre conductores.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Cuál es la definición reglamentaria de 'Tensión nominal' de una instalación según la ITC-BT-01?",
                    opts = listOf(
                        "El valor de cresta máximo medido durante perturbaciones transitorias de maniobra",
                        "El valor convencional de la tensión con el que se designa una red y para el cual ha sido proyectada", // VERIFICAR-BOE
                        "La tensión mínima por debajo de la cual los receptores térmicos deben interrumpir su servicio",
                        "La diferencia de potencial registrada entre el electrodo de tierra y el neutro en régimen de carga"
                    ),
                    a = 1,
                    exp = "La ITC-BT-01 define la tensión nominal como el valor convencional de la tensión eficaz con el que se designa un sistema o instalación y al que se refieren determinadas características de funcionamiento. En redes de BT en España, los valores normalizados son 230 V monofásica y 400 V trifásica.",
                    ref = "ITC-BT-01"
                )
            )
        ),
        "itc_11" to ModuleDefinition(
            id = "itc_11",
            label = "ITC-BT-11 Cajas Generales de Protección",
            icon = "📦",
            color = "#3498db",
            questions = listOf(
                Question(
                    q = "¿Dónde debe ubicarse reglamentariamente la Caja General de Protección (CGP) según la ITC-BT-11?",
                    opts = listOf(
                        "En el interior de la vivienda del presidente de la comunidad de propietarios",
                        "En el límite de la propiedad o fachada exterior del edificio, en lugar accesible directamente desde la vía pública",
                        "En el cuarto técnico de contadores junto a las baterías de medida individuales",
                        "En la cubierta superior del edificio para facilitar la acometida por línea aérea"
                    ),
                    a = 1,
                    exp = "La ITC-BT-11 par. 1 establece con claridad que las Cajas Generales de Protección se instalarán preferentemente en la fachada exterior del edificio, sobre la línea de fachada o en una zona común accesible permanentemente desde la vía pública por el personal de la empresa distribuidora sin necesidad de llaves de portal.",
                    ref = "ITC-BT-11 §1"
                ),
                Question(
                    q = "En montaje sobre fachada, ¿a qué altura debe situarse la parte inferior de la CGP según la ITC-BT-11?",
                    opts = listOf(
                        "Entre 0,50 m y 2,50 m sobre la rasante de la acera o del suelo de la vía pública", // VERIFICAR-BOE
                        "A un mínimo estricto de 3,50 metros para evitar actos de vandalismo callejero",
                        "A ras de suelo para facilitar la entrada directa de la acometida subterránea",
                        "Entre 1,80 m y 3,00 m sin excepción alguna en todo el territorio nacional"
                    ),
                    a = 0,
                    exp = "La ITC-BT-11 par. 2.1 prescribe que la parte inferior de la caja general de protección quedará instalada a una cota comprendida entre 0,50 m y 2,50 m del suelo de la acera. Esta distancia asegura la protección frente a humedad e impactos a la vez que permite un acceso cómodo para maniobras.",
                    ref = "ITC-BT-11 §2.1"
                ),
                Question(
                    q = "¿Qué tipo de dispositivos de protección se alojan en el interior de una CGP según la ITC-BT-11?",
                    opts = listOf(
                        "Interruptores automáticos magnetotérmicos de curva D con rearme manual",
                        "Interruptores diferenciales de media sensibilidad de 300 mA con retardo selectivo",
                        "Cortacircuitos fusibles de alto poder de ruptura (APR) de tipo cuchilla",
                        "Descargadores de sobretensiones permanentes combinados con bobina de emisión"
                    ),
                    a = 2,
                    exp = "La ITC-BT-11 par. 1.2 especifica que las CGP alojan en su interior cortacircuitos fusibles de alto poder de ruptura (fusibles tipo cuchilla NH) para proteger la línea general de alimentación frente a cortocircuitos. No se admiten magnetotérmicos en la CGP salvo en cuadros de distribución especiales.",
                    ref = "ITC-BT-11 §1.2"
                ),
                Question(
                    q = "¿Qué grados de protección mínimos (IP e IK) debe tener la envolvente de una CGP instalada en exterior según ITC-BT-11?",
                    opts = listOf(
                        "IP20 contra polvo e IK05 contra impactos ordinarios de personas",
                        "IP43 contra penetración de agua e IK09 contra impactos mecánicos severos", // VERIFICAR-BOE
                        "IP68 sumergible continua e IK02 sin exigencia de resistencia mecánica",
                        "IP30 para ambientes secos e IK07 para envolventes plásticas ordinarias"
                    ),
                    a = 1,
                    exp = "Las cajas generales de protección instaladas a la intemperie en fachadas deben poseer como mínimo un grado de protección IP43 frente a la penetración de cuerpos extraños y lluvia, y un grado de protección contra impactos mecánicos externos IK09 según las normas UNE-EN 60529 y UNE-EN 50102.",
                    ref = "ITC-BT-11 §1.1"
                ),
                Question(
                    q = "En el interior de la CGP, ¿cómo debe efectuarse la conexión del conductor neutro según la ITC-BT-11?",
                    opts = listOf(
                        "A través de un fusible calibrado a la mitad de la intensidad nominal de las fases",
                        "Mediante un relé térmico diferencial provisto de rearme manual precintable",
                        "Directamente trenzado y soldado a la estructura metálica de la fachada",
                        "Mediante una pletina o borne seccionable sin fusible de protección interpuesto"
                    ),
                    a = 3,
                    exp = "En la CGP, el conductor neutro se conecta a una pletina de conexión desmontable o borne seccionable. Está terminantemente prohibido intercalar fusibles en el neutro, dado que la fusión del neutro en una red trifásica provocaría sobretensiones destructivas en los receptores monofásicos conectados entre fase y neutro.",
                    ref = "ITC-BT-11 §1.2"
                ),
                Question(
                    q = "¿En qué caso reglamentario se autoriza instalar una Caja de Protección y Medida (CPM) unificada según ITC-BT-11 e ITC-BT-13?",
                    opts = listOf(
                        "Para uno o dos suministros individuales alimentados desde el mismo punto cuando la medida va en fachada",
                        "En edificios de viviendas con más de 20 contadores centralizados en planta sótano",
                        "Exclusivamente en industrias con potencia instalada superior a 150 kW en baja tensión",
                        "En locales de pública concurrencia que cuenten con suministro de socorro independiente"
                    ),
                    a = 0,
                    exp = "La ITC-BT-11 e ITC-BT-13 permiten la unificación de la caja de protección y la caja de medida en un solo elemento denominado CPM (Caja de Protección y Medida) cuando se trata de suministros para uno o dos usuarios alimentados desde un mismo punto (por ejemplo, viviendas unifamiliares o pequeños locales).",
                    ref = "ITC-BT-11 e ITC-BT-13"
                ),
                Question(
                    q = "Según las especificaciones de la ITC-BT-11 y normas UNE, ¿qué determinan los Esquemas normalizados de CGP (del 1 al 14)?",
                    opts = listOf(
                        "El color reglamentario de los cables interiores utilizados en las viviendas",
                        "La tensión de ensayo dieléctrico de los contadores electrónicos inteligentes",
                        "El tipo de acometida (aérea o subterránea), número de líneas de alimentación y disposición de fusibles",
                        "El calibre del interruptor de control de potencia (ICP) instalado por el usuario"
                    ),
                    a = 2,
                    exp = "La ITC-BT-11 par. 1.2 adopta los esquemas normalizados UNE para CGP (Esquemas 1 al 14). Cada esquema define si la acometida es aérea posada, aérea tensada o subterránea, si la caja alimenta a una o varias líneas generales de alimentación (LGA), y la disposición de fusibles seccionables y neutro.",
                    ref = "ITC-BT-11 §1.2"
                ),
                Question(
                    q = "¿Cómo debe realizarse el cierre y aseguramiento de la CGP según la ITC-BT-11?",
                    opts = listOf(
                        "Mediante cerradura con llave ordinaria de serreta disponible en ferreterías",
                        "Mediante cierre normalizado con dispositivo homologado por la empresa distribuidora de energía eléctrica",
                        "Mediante soldadura autógena de la tapa para impedir permanentemente su apertura",
                        "Con candado personal aportado por el administrador de la finca del inmueble"
                    ),
                    a = 1,
                    exp = "La ITC-BT-11 par. 1.1 exige que las envolventes de las CGP cuenten con un sistema de cierre normalizado y precintable según las especificaciones técnicas aprobadas de la empresa distribuidora de la zona, permitiendo la apertura exclusiva por personal autorizado para mantenimiento y sustitución de fusibles.",
                    ref = "ITC-BT-11 §1.1"
                ),
                Question(
                    q = "¿Qué características deben reunir los materiales de las envolventes de las CGP según ITC-BT-11?",
                    opts = listOf(
                        "Madera tratada con barniz ignífugo para exteriores de viviendas rústicas",
                        "Chapa de hierro dulce galvanizada en caliente de espesor inferior a 0,5 mm",
                        "Aluminio sin aislamiento interior ni conexión a tierra de protección",
                        "Material aislante autoextinguible o metálico con aislamiento de Clase II equivalente"
                    ),
                    a = 3,
                    exp = "La ITC-BT-11 exige que las envolventes de las CGP sean de material aislante autoextinguible (generalmente poliéster reforzado con fibra de vidrio), no higroscópicas y resistentes a los rayos UV, o metálicas garantizando una protección equivalente a la Clase II (doble aislamiento) para evitar contactos indirectos en fachada.",
                    ref = "ITC-BT-11 §1.1"
                ),
                Question(
                    q = "¿Quién asume la propiedad y responsabilidad de conservación de la CGP tras su puesta en servicio?",
                    opts = listOf(
                        "La propiedad o la comunidad de propietarios del edificio tras la conexión y recepción por la distribuidora",
                        "El instalador autorizado de forma vitalicia durante toda la vida útil del edificio",
                        "El ayuntamiento del municipio a través de los servicios técnicos de alumbrado",
                        "El fabricante de la envolvente plástica en régimen de garantía perpetua"
                    ),
                    a = 0,
                    exp = "Conforme a la reglamentación del REBT (Art. 18 y concordantes con ITC-BT-11), la Caja General de Protección es un elemento de la instalación de enlace que forma parte de la instalación receptora común, siendo su propiedad y mantenimiento responsabilidad del titular o comunidad del inmueble tras la cesión y conexión de acometida.",
                    ref = "ITC-BT-11"
                ),
                Question(
                    q = "¿En qué posición relativa debe disponerse el borne o cuchilla del neutro en una CGP trifásica según ITC-BT-11?",
                    opts = listOf(
                        "Siempre en el centro, intercalado obligatoriamente entre la fase L1 y la fase L2",
                        "En la parte posterior de la envolvente oculto tras las pletinas de fase",
                        "En el lateral izquierdo de las bases de fusibles de fase mirando frontalmente la caja", // VERIFICAR-BOE
                        "En el extremo superior derecho sobre los bornes de salida de la LGA"
                    ),
                    a = 2,
                    exp = "En las Cajas Generales de Protección normalizadas bajo norma UNE e ITC-BT-11, el borne seccionable de neutro se ubica de forma estándar en el lado izquierdo de las tres bases portafusibles de fase mirando de frente la caja, garantizando una disposición uniforme y segura en todas las instalaciones.",
                    ref = "ITC-BT-11 §1.2"
                ),
                Question(
                    q = "Para acometidas subterráneas conectadas a la CGP, ¿qué sección mínima de conductores suele fijar la normativa y distribuidoras?",
                    opts = listOf(
                        "Conductores de cobre de 2,5 mm² con aislamiento de silicona para altas temperaturas",
                        "Conductores de aluminio de 6 mm² bajo tubo corrugado ordinario de 20 mm",
                        "Cables trenzados de cobre sin cubierta protectora de sección 10 mm²",
                        "Cables unipolares de cobre de al menos 16 mm² o de aluminio de 25 mm² de tensión 0,6/1 kV" // VERIFICAR-BOE
                    ),
                    a = 3,
                    exp = "Las acometidas que acometen a las Cajas Generales de Protección se ejecutan con conductores aislados para 0,6/1 kV, con secciones mínimas normalizadas no inferiores a 16 mm² en cobre o 25 mm² en aluminio por motivos de resistencia mecánica y capacidad frente a corrientes de cortocircuito de la red pública.",
                    ref = "ITC-BT-11 e ITC-BT-07"
                )
            )
        ),
        "itc_12" to ModuleDefinition(
            id = "itc_12",
            label = "ITC-BT-12 Esquemas de Instalaciones de Enlace",
            icon = "📊",
            color = "#9b59b6",
            questions = listOf(
                Question(
                    q = "¿Qué define el 'Esquema 1' para instalaciones de enlace en edificios residenciales según la ITC-BT-12?",
                    opts = listOf(
                        "Colocación de contadores totalmente concentrados en un único local o armario",
                        "Instalación de un contador individual en el rellano de cada vivienda alimentado por una LGA independiente",
                        "Suministro mediante contadores descentralizados situados en el exterior del tejado del edificio",
                        "Distribución sin contadores mediante facturación a tanto alzado por superficie construida"
                    ),
                    a = 0,
                    exp = "La ITC-BT-12 par. 2.2 define el Esquema 1 para edificios de viviendas y oficinas como aquel en el que los contadores se agrupan en una única centralización (armario o local técnico) situada en la planta baja o sótano, de donde parten las derivaciones individuales hacia cada usuario.",
                    ref = "ITC-BT-12 §2.2"
                ),
                Question(
                    q = "¿A partir de cuántos contadores concentrados exige la ITC-BT-12 ubicar la centralización en un local técnico en vez de un armario?",
                    opts = listOf(
                        "A partir de 4 contadores en edificios de más de dos plantas",
                        "A partir de 8 contadores concentrados en cualquier tipo de edificio",
                        "A partir de 16 contadores concentrados en un mismo punto", // VERIFICAR-BOE
                        "Solo es obligatorio local técnico cuando se superan los 50 contadores"
                    ),
                    a = 2,
                    exp = "Conforme a la ITC-BT-12 e ITC-BT-16, cuando el número de contadores concentrados en un mismo punto supera los 16 contadores, es preceptivo ubicarlos en un local técnico independiente exclusivo para este fin. Hasta 16 contadores se permite su instalación en armario técnico empotrado o adosado.",
                    ref = "ITC-BT-12 e ITC-BT-16"
                ),
                Question(
                    q = "¿En qué consiste el 'Esquema 2' de centralización de contadores contemplado en la ITC-BT-12?",
                    opts = listOf(
                        "Un único contador trifásico general para todo el edificio que factura a la comunidad",
                        "Contadores concentrados en varios puntos (centralizaciones parciales por plantas en edificios altos)",
                        "Contadores integrados en el interior de cada cuadro general de mando y protección particular",
                        "Instalación de contadores exclusivamente para suministros de alumbrado de emergencia y escaleras"
                    ),
                    a = 1,
                    exp = "El Esquema 2 de la ITC-BT-12 se destina a edificios de gran altura o geometría compleja donde la centralización se distribuye en varios puntos (armarios o locales en plantas intermedias), alimentados por una o varias Líneas Generales de Alimentación para reducir la longitud de las derivaciones individuales.",
                    ref = "ITC-BT-12 §2.2"
                ),
                Question(
                    q = "¿Entre qué alturas deben quedar situados los cuadrantes o pantallas de lectura de los contadores según la ITC-BT-12 e ITC-BT-16?",
                    opts = listOf(
                        "A cualquier altura siempre que el operario pueda acceder con una escalera de tijera",
                        "Entre 0,20 m y 1,00 m del pavimento acabado del local o pasillo",
                        "Exactamente a 2,50 m para evitar que personas no autorizadas manipulen los aparatos",
                        "Entre 0,50 m y 1,80 m sobre el nivel del suelo para permitir lectura visual cómoda" // VERIFICAR-BOE
                    ),
                    a = 3,
                    exp = "La ITC-BT-12 e ITC-BT-16 par. 2.2 estipulan que los aparatos de medida deben montarse de manera que los cuadrantes o visores de lectura se ubiquen a una altura comprendida entre 0,50 m y 1,80 m del suelo, permitiendo la lectura directa y tareas de mantenimiento sin posturas peligrosas.",
                    ref = "ITC-BT-12 e ITC-BT-16"
                ),
                Question(
                    q = "¿Cuál es la función reglamentaria del Interruptor General de Maniobra (IGM) en una centralización de contadores (ITC-BT-12)?",
                    opts = listOf(
                        "Permitir el corte y seccionamiento general de la batería de contadores sin desenergizar la LGA",
                        "Limitar automáticamente la potencia contratada por el conjunto de las viviendas del edificio",
                        "Proteger exclusivamente contra descargas atmosféricas mediante varistores de óxido de zinc",
                        "Interrumpir la alimentación cuando la temperatura del armario técnico supere los 40 °C"
                    ),
                    a = 0,
                    exp = "La ITC-BT-12 par. 2.1 y la ITC-BT-16 exigen instalar un Interruptor General de Maniobra (IGM) en cabeza de cada concentración de contadores para permitir el corte en carga y aislamiento de toda la batería por motivos de seguridad o mantenimiento sin necesidad de retirar los fusibles de la CGP.",
                    ref = "ITC-BT-12 e ITC-BT-16"
                ),
                Question(
                    q = "¿Qué anchura mínima de pasillo libre de paso exige la ITC-BT-12 e ITC-BT-16 frente a los cuadros en locales de contadores?",
                    opts = listOf(
                        "0,70 metros para locales de superficie inferior a 10 metros cuadrados",
                        "1,10 metros libres frente a los paneles de medida para permitir paso y evacuación segura", // VERIFICAR-BOE
                        "2,00 metros en cualquier caso según la directiva de seguridad contra incendios",
                        "No se exige pasillo mínimo si los cuadros disponen de puertas transparentes"
                    ),
                    a = 1,
                    exp = "La ITC-BT-16 complementaria a los esquemas de la ITC-BT-12 prescribe que el local de contadores dispondrá de un pasillo libre de paso de al menos 1,10 metros de anchura frente a los cuadros de medida para permitir la maniobra de operarios y la evacuación rápida en caso de emergencia.",
                    ref = "ITC-BT-12 e ITC-BT-16"
                ),
                Question(
                    q = "¿Qué tipo de ventilación se requiere en un local técnico destinado a la centralización de contadores?",
                    opts = listOf(
                        "No precisa ventilación al tratarse de un recinto cerrado herméticamente contra el polvo",
                        "Ventilación forzada mediante turbina eólica instalada exclusivamente en la fachada norte",
                        "Ventilación natural directa o forzada que asegure la renovación constante de aire y disipación térmica",
                        "Climatización continua a 21 °C con deshumidificador industrial permanente"
                    ),
                    a = 2,
                    exp = "La ITC-BT-12 e ITC-BT-16 exigen que los locales de contadores cuenten con ventilación suficiente (natural o forzada con conductos independientes hacia el exterior) para asegurar la renovación de aire, evitando la acumulación de humedad y el sobrecalentamiento producido por el efecto Joule de los contadores.",
                    ref = "ITC-BT-12 e ITC-BT-16"
                ),
                Question(
                    q = "¿Qué resistencia al fuego mínima debe tener la puerta de acceso al local técnico de contadores según ITC-BT-12 e ITC-BT-16?",
                    opts = listOf(
                        "Puerta de madera estándar sin clasificación de resistencia al fuego",
                        "Puerta con grado de estanqueidad IP68 sin consideración de protección térmica",
                        "Chapa simple de acero perforada para favorecer la corriente de aire",
                        "Puerta resistente al fuego con clasificación mínima EI2 30-C5 (o RF-30) con apertura hacia el exterior" // VERIFICAR-BOE
                    ),
                    a = 3,
                    exp = "El local de contadores debe estar delimitado por paramentos y puertas resistentes al fuego. La puerta de acceso debe poseer una resistencia mínima EI2 30-C5 (antiguamente RF-30), abrir hacia el exterior del local y disponer de cerradura normalizada por la empresa distribuidora.",
                    ref = "ITC-BT-12 e ITC-BT-16"
                ),
                Question(
                    q = "¿Qué circuito auxiliar de servicio debe disponer obligatoriamente el local de contadores según ITC-BT-12 e ITC-BT-16?",
                    opts = listOf(
                        "Circuito independiente para alumbrado (mínimo 100 lux) y toma de corriente con su propio cuadro CGMP",
                        "Circuito trifásico de fuerza a 400 V para maquinaria de carga de baterías de vehículos",
                        "Circuito de calefacción por radiadores para evitar la condensación invernal",
                        "Toma de agua corriente con desagüe directo para lavado periódico del pavimento"
                    ),
                    a = 0,
                    exp = "La reglamentación exige que los locales técnicos de contadores dispongan de su propia instalación auxiliar de alumbrado (garantizando un nivel mínimo de iluminación de 100 lux a nivel de suelo), alumbrado de emergencia y una base de enchufe protegida con interruptor diferencial de 30 mA y magnetotérmico.",
                    ref = "ITC-BT-12 e ITC-BT-16"
                ),
                Question(
                    q = "Para un suministro individual aislado (vivienda unifamiliar), ¿cómo se dispone el equipo de medida según ITC-BT-12?",
                    opts = listOf(
                        "Obligatoriamente en el salón principal de la vivienda cerca del televisor",
                        "En una arqueta enterrada estanca bajo la calzada frente a la parcela",
                        "En un módulo o armario empotrado en la valla exterior accesible directamente desde la vía pública",
                        "Suspendido directamente del poste de hormigón de la acometida aérea"
                    ),
                    a = 2,
                    exp = "En suministros monofásicos o trifásicos individuales (Esquemas para 1 o 2 usuarios de la ITC-BT-12), el contador se ubica en un armario o nicho estanco en la valla o muro exterior de la propiedad, accesible permanentemente desde la vía pública para facilitar la lectura y corte de suministro por la distribuidora.",
                    ref = "ITC-BT-12 §2.1"
                ),
                Question(
                    q = "¿Qué instalaciones ajenas al servicio eléctrico tienen expresamente prohibido su paso por el local de contadores (ITC-BT-12)?",
                    opts = listOf(
                        "Líneas de fibra óptica de telecomunicaciones siempre que vayan bajo tubo",
                        "Tuberías de agua, gas, calefacción, climatización o desagües residuales ajenas al local",
                        "Conductores de puesta a tierra del propio edificio que acometen a la pica principal",
                        "Canalizaciones de derivaciones individuales que parten de los contadores del propio local"
                    ),
                    a = 1,
                    exp = "La ITC-BT-16 e ITC-BT-12 prohíben taxativamente que a través del local de contadores discurran tuberías de conducción de agua, gas, saneamiento, desagües o canalizaciones ajenas a la instalación eléctrica para prevenir inundaciones, fugas de gas o atmósferas explosivas en contacto con los cuadros de medida.",
                    ref = "ITC-BT-12 e ITC-BT-16"
                ),
                Question(
                    q = "En edificios de gran altura, ¿a partir de cuántas plantas recomienda la ITC-BT-12 la centralización por plantas (Esquema 2)?",
                    opts = listOf(
                        "A partir de 3 plantas en cualquier bloque de pisos",
                        "A partir de 6 plantas en zonas rurales y 8 plantas en zonas urbanas",
                        "No se recomienda nunca la centralización por plantas por encarecer la obra",
                        "A partir de 12 plantas o cuando las caídas de tensión de las derivaciones individuales resulten excesivas" // VERIFICAR-BOE
                    ),
                    a = 3,
                    exp = "La ITC-BT-12 par. 2.2 señala que en edificios de más de 12 plantas de altura es aconsejable distribuir los contadores en concentraciones parciales por plantas (Esquema 2), evitando así derivaciones individuales de excesiva longitud que obligarían a sobredimensionar notablemente los conductores por caída de tensión.",
                    ref = "ITC-BT-12 §2.2"
                )
            )
        )
    )
}
