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

// 2. Syllabus Catalog (separated to avoid MethodTooLargeException in Content.<clinit>)

private object SyllabusCatalog {
    val ITEMS = listOf(
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
}

// 3. Content Singleton with Complete REBT 2026 Question Catalog & Syllabi

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

    val SYLLABUS = SyllabusCatalog.ITEMS

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
,
                Question(
                    q = "Según el REBT, el objeto del Reglamento es:",
                    opts = listOf(
                        "Establecer las condiciones técnicas y garantías que deben reunir las instalaciones eléctricas",
                        "Regular las tarifas eléctricas aplicables",
                        "Determinar los requisitos de conexión a redes de alta tensión",
                        "Gestionar la relación contractual entre distribuidora y usuario"
                    ),
                    a = 0,
                    exp = "Artículo 1: Se establece como objeto regular las condiciones técnicas y garantías de las instalaciones eléctricas conectadas a baja tensión.",
                    ref = "Art. 1 RD 842/2002"
                ),
                Question(
                    q = "Según el REBT, el Reglamento se aplica a instalaciones con tensiones nominales:",
                    opts = listOf(
                        "Hasta 500 V en alterna y 1.000 V en continua",
                        "Igual o inferior a 1.000 V en corriente alterna e igual o inferior a 1.500 V en corriente continua",
                        "Hasta 230/400 V en alterna",
                        "Solo tensiones inferiores a 50 V"
                    ),
                    a = 1,
                    exp = "Artículo 2. El Reglamento se aplica a instalaciones con tensiones nominales ≤ 1.000 V AC y ≤ 1.500 V DC.",
                    ref = "Art. 2 RD 842/2002"
                ),
                Question(
                    q = "Según el REBT, la modificación de importancia se considera cuando:",
                    opts = listOf(
                        "Afecta al 10% de la potencia instalada",
                        "Afecta al 50% de la potencia instalada",
                        "Afecta a cualquier circuito",
                        "Incluye solo cambio de luminarias"
                    ),
                    a = 1,
                    exp = "Artículo 2 Se considera modificación de importancia cuando afecta a más del 50% de la potencia instalada.",
                    ref = "Art. 2 RD 842/2002"
                ),
                Question(
                    q = "Según el REBT, quedan excluidas de su aplicación las instalaciones:",
                    opts = listOf(
                        "De alumbrado exterior",
                        "De usos militares o reglamentación específica",
                        "De viviendas",
                        "De redes informáticas siempre"
                    ),
                    a = 1,
                    exp = "Artículo 2 Se excluyen las instalaciones y equipos sujetos a reglamentación específica (minas, automóviles, navíos, usos militares, etc.).",
                    ref = "Art. 2 RD 842/2002"
                ),
                Question(
                    q = "Según el REBT, las instalaciones de muy baja tensión solo aplicarán prescripciones específicas cuando:",
                    opts = listOf(
                        "Su tensión sea inferior a 75 V en alterna",
                        "Su fuente sea autónoma y no dependan de redes de BT",
                        "Se instalen en viviendas",
                        "Sean instalaciones provisionales"
                    ),
                    a = 1,
                    exp = "Artículo 2. No se aplican prescripciones generales cuando la fuente es autónoma y la instalación es independiente.",
                    ref = "Art. 2 RD 842/2002"
                ),
                Question(
                    q = "Según el REBT, se entiende por instalación eléctrica:",
                    opts = listOf(
                        "El conjunto de cables únicamente",
                        "Cualquier línea destinada a alumbrado público",
                        "Todo conjunto de aparatos y circuitos asociados para un fin particular",
                        "Una instalación interior de usuario exclusivamente"
                    ),
                    a = 2,
                    exp = "Artículo 3: Define instalación eléctrica como el conjunto de aparatos y circuitos asociados con un fin particular.",
                    ref = "Art. 3 RD 842/2002"
                ),
                Question(
                    q = "Según el REBT, la muy baja tensión corresponde a valores:",
                    opts = listOf(
                        "Un ≤ 75 V en alterna",
                        "Un ≤ 50 V en alterna y Un ≤ 75 V en continua",
                        "Un ≤ 100 V en continua",
                        "50 < Un ≤ 500 V en alterna"
                    ),
                    a = 1,
                    exp = "Artículo 4 y tabla de clasificación de tensiones: muy baja tensión ≤ 50 V AC y ≤ 75 V DC.",
                    ref = "Art. 4 RD 842/2002"
                ),
                Question(
                    q = "Según el REBT, en redes trifásicas de cuatro conductores, las tensiones normalizadas son:",
                    opts = listOf(
                        "220/380 V",
                        "230/400 V",
                        "240/415 V",
                        "250/440 V"
                    ),
                    a = 1,
                    exp = "Artículo 4 Tensión normalizada de 230 V fase-neutro y 400 V entre fases.",
                    ref = "Art. 4 RD 842/2002"
                ),
                Question(
                    q = "Según el REBT, la frecuencia normalizada de la red es:",
                    opts = listOf(
                        "60 Hz",
                        "40 Hz",
                        "50 Hz",
                        "45 Hz"
                    ),
                    a = 2,
                    exp = "Artículo 4. La frecuencia empleada en la red será de 50 Hz.",
                    ref = "Art. 4 RD 842/2002"
                ),
                Question(
                    q = "Según el REBT, las instalaciones que puedan producir perturbaciones deberán:",
                    opts = listOf(
                        "Desconectarse automáticamente",
                        "Estar dotadas de dispositivos protectores adecuados",
                        "Ser revisadas cada año",
                        "Ser alimentadas en corriente continua"
                    ),
                    a = 1,
                    exp = "Artículo 5: Las instalaciones que produzcan perturbaciones deben contar con dispositivos protectores.",
                    ref = "Art. 5 RD 842/2002"
                ),
                Question(
                    q = "Según el REBT, los equipos utilizados deberán incluir como indicación mínima:",
                    opts = listOf(
                        "Marca CE únicamente",
                        "Identificación del fabricante, modelo, tensión e intensidad asignadas",
                        "Peso y dimensiones",
                        "Kilovatios consumidos mensualmente"
                    ),
                    a = 1,
                    exp = "Artículo 6: El material debe incluir identificación del fabricante, marca/modelo, tensión/intensidad y otras indicaciones.",
                    ref = "Art. 6 RD 842/2002"
                ),
                Question(
                    q = "Según el REBT, si en una instalación coexisten tensiones superiores a BT:",
                    opts = listOf(
                        "Debe aplicarse únicamente este Reglamento",
                        "Debe cumplirse el reglamento correspondiente a la tensión superior",
                        "Debe reducirse la tensión por seguridad",
                        "Se prohíbe la coexistencia de tensiones"
                    ),
                    a = 1,
                    exp = "Artículo 7: En ausencia de indicación específica, se aplica el reglamento de la tensión superior.",
                    ref = "Art. 7 RD 842/2002"
                ),
                Question(
                    q = "Según el REBT, se consideran instalaciones de alumbrado exterior:",
                    opts = listOf(
                        "Las de iluminación decorativa interior",
                        "Las vías de circulación o espacios entre edificaciones que requieran iluminación",
                        "Los alumbrados de emergencia interiores",
                        "Cualquier instalación con luminarias LED"
                    ),
                    a = 1,
                    exp = "Artículo 9: Se consideran de alumbrado exterior las que iluminan vías o espacios entre edificaciones.",
                    ref = "Art. 9 RD 842/2002"
                ),
                Question(
                    q = "Según el REBT, un suministro normal es:",
                    opts = listOf(
                        "El que incluye dos puntos de entrega",
                        "El efectuado por una sola distribuidora para toda la potencia contratada",
                        "El destinado solo a alumbrado",
                        "El limitado al 15% de la potencia"
                    ),
                    a = 1,
                    exp = "Artículo 10. Suministro normal: efectuado por una sola empresa distribuidora con un solo punto de entrega.",
                    ref = "Art. 10 RD 842/2002"
                ),
                Question(
                    q = "Según el REBT, un suministro duplicado es aquel capaz de mantener:",
                    opts = listOf(
                        "El 15% de la potencia contratada",
                        "El 25% de la potencia contratada",
                        "Más del 50% de la potencia contratada",
                        "El 100% de la instalación"
                    ),
                    a = 2,
                    exp = "Artículo 10. El suministro duplicado mantiene más del 50% de la potencia del suministro normal.",
                    ref = "Art. 10 RD 842/2002"
                ),
                Question(
                    q = "Según el REBT, la acometida es:",
                    opts = listOf(
                        "La instalación interior del usuario",
                        "La parte de la red que alimenta la caja general de protección",
                        "El cable entre contador y usuario",
                        "El DGMP del edificio"
                    ),
                    a = 1,
                    exp = "Artículo 15. La acometida alimenta la CGP y es responsabilidad de la distribuidora.",
                    ref = "Art. 15 RD 842/2002"
                ),
                Question(
                    q = "Según el REBT, una instalación interior o receptora tiene como finalidad:",
                    opts = listOf(
                        "La distribución de energía eléctrica",
                        "La utilización de la energía eléctrica",
                        "La generación de energía",
                        "Alimentar exclusivamente alumbrado exterior"
                    ),
                    a = 1,
                    exp = "Artículo 16. Describe las instalaciones interiores o receptoras como destinadas a utilizar la energía eléctrica.",
                    ref = "Art. 16 RD 842/2002"
                ),
                Question(
                    q = "Según el REBT, la puesta en servicio requiere:",
                    opts = listOf(
                        "Solo la firma del titular",
                        "Documentación técnica, verificaciones y certificado de instalación",
                        "Una revisión de la distribuidora",
                        "La aprobación del ayuntamiento"
                    ),
                    a = 1,
                    exp = "Artículo 18: Establece proyecto/memoria, verificaciones, inspección inicial (si aplica) y certificado.",
                    ref = "Art. 18 RD 842/2002"
                ),
                Question(
                    q = "Según el REBT, la empresa suministradora no podrá conectar la instalación a la red si:",
                    opts = listOf(
                        "El titular no ha pagado la obra",
                        "No se entrega el certificado de instalación diligenciado",
                        "Faltan luminarias",
                        "No existe cuadro de mando"
                    ),
                    a = 1,
                    exp = "Artículo 18. La distribuidora no puede conectar sin el certificado diligenciado.",
                    ref = "Art. 18 RD 842/2002"
                ),
                Question(
                    q = "Según el REBT, el certificado de instalación debe incluir:",
                    opts = listOf(
                        "Plano arquitectónico completo",
                        "Esquema unifilar y croquis del trazado",
                        "Factura del material",
                        "Contrato con la distribuidora"
                    ),
                    a = 1,
                    exp = "Artículo 19: Debe entregarse esquema unifilar y croquis de la instalación al titular.",
                    ref = "Art. 19 RD 842/2002"
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
,
                Question(
                    q = "Según el REBT, una empresa instaladora en baja tensión es aquella que realiza, mantiene o repara instalaciones eléctricas y ha presentado:",
                    opts = listOf(
                        "Un proyecto técnico visado",
                        "Una declaración responsable ante el órgano competente",
                        "Una solicitud de autorización temporal",
                        "Un contrato con una distribuidora de energía"
                    ),
                    a = 1,
                    exp = "ITC-BT-03, punto 2.1: la empresa instaladora debe haber presentado la correspondiente declaración responsable de inicio de actividad.",
                    ref = "ITC-BT-03"
                ),
                Question(
                    q = "Según el REBT, la empresa instaladora podrá ser:",
                    opts = listOf(
                        "Solo persona jurídica",
                        "Solo persona física",
                        "Persona física o jurídica",
                        "Únicamente sociedades mercantiles"
                    ),
                    a = 2,
                    exp = "ITC-BT-03, punto 2.1: define empresa instaladora como persona física o jurídica.",
                    ref = "ITC-BT-03"
                ),
                Question(
                    q = "Según el REBT, la declaración responsable presentada por la empresa instaladora habilita para ejercer su actividad:",
                    opts = listOf(
                        "Solo en la comunidad autónoma donde se presente",
                        "En todo el territorio español por tiempo indefinido",
                        "Durante cinco años renovables",
                        "Únicamente después de una inspección previa"
                    ),
                    a = 1,
                    exp = "ITC-BT-03, punto 5.5: la declaración responsable habilita por tiempo indefinido y para todo el territorio español.",
                    ref = "ITC-BT-03"
                ),
                Question(
                    q = "Según el REBT, la categoría básica permite realizar, mantener y reparar instalaciones:",
                    opts = listOf(
                        "En el ámbito del reglamento, excepto las reservadas a la categoría especialista",
                        "Solo en viviendas de uso doméstico",
                        "Exclusivamente en locales comerciales",
                        "Únicamente en baja tensión inferior a 50 V"
                    ),
                    a = 0,
                    exp = "ITC-BT-03, punto 3.1: la categoría básica abarca todas las instalaciones no reservadas a la categoría especialista.",
                    ref = "ITC-BT-03"
                ),
                Question(
                    q = "Según el REBT, la categoría especialista permite realizar, mantener y reparar instalaciones:",
                    opts = listOf(
                        "Solo en viviendas unifamiliares",
                        "En todas las instalaciones incluidas en el ámbito del reglamento",
                        "Únicamente en locales de pública concurrencia",
                        "Solo en instalaciones sin proyecto"
                    ),
                    a = 1,
                    exp = "ITC-BT-03, punto 3.2: la categoría especialista podrán realizar, mantener y reparar las instalaciones de la categoría Básica y, además, mencionadas en este apartado 3.2 del REBT.",
                    ref = "ITC-BT-03"
                ),
                Question(
                    q = "Según el REBT, el instalador en baja tensión deberá desarrollar su actividad:",
                    opts = listOf(
                        "De manera independiente sin empresa",
                        "En el seno de una empresa instaladora habilitada",
                        "Solo como trabajador autónomo",
                        "Únicamente para la compañía distribuidora"
                    ),
                    a = 1,
                    exp = "ITC-BT-03, punto 4: el instalador debe desarrollar su actividad dentro de una empresa instaladora habilitada.",
                    ref = "ITC-BT-03"
                ),
                Question(
                    q = "Según el REBT, una de las vías válidas para acreditar la cualificación del instalador es:",
                    opts = listOf(
                        "Tener experiencia laboral sin formación acreditada",
                        "Poseer un título universitario cuyo ámbito cubra las materias del reglamento",
                        "Presentar un certificado municipal",
                        "Realizar un curso privado de electricidad"
                    ),
                    a = 1,
                    exp = "ITC-BT-03, punto 4.a: permite acreditar mediante título universitario con competencias relacionadas.",
                    ref = "ITC-BT-03"
                ),
                Question(
                    q = "Según el REBT, otra forma de acreditar la cualificación del instalador es:",
                    opts = listOf(
                        "Poseer un título de formación profesional o certificado de profesionalidad que cubra las materias del reglamento",
                        "Haber trabajado tres años en mantenimiento eléctrico",
                        "Tener un curso de riesgos eléctricos",
                        "Disponer de licencia del ayuntamiento"
                    ),
                    a = 0,
                    exp = "ITC-BT-03, punto 4.b: se admite título de FP o certificado de profesionalidad.",
                    ref = "ITC-BT-03"
                ),
                Question(
                    q = "Según el REBT, también puede ejercer como instalador quien:",
                    opts = listOf(
                        "Tenga reconocida su competencia profesional adquirida por experiencia laboral",
                        "Disponga de licencia de la distribuidora",
                        "Sea técnico en telecomunicaciones",
                        "Haya completado 2 años de experiencia en obras públicas"
                    ),
                    a = 0,
                    exp = "ITC-BT-03, punto 4.c: se admite reconocimiento de competencia profesional por experiencia laboral (RD 1224/2009).",
                    ref = "ITC-BT-03"
                ),
                Question(
                    q = "Según el REBT, el cese de actividad o modificación de los datos declarados por la empresa instaladora debe comunicarse en un plazo máximo de:",
                    opts = listOf(
                        "10 días",
                        "15 días",
                        "1 mes",
                        "3 meses"
                    ),
                    a = 2,
                    exp = "ITC-BT-03, punto 5.7: cualquier modificación o cese debe comunicarse en el plazo máximo de un mes.",
                    ref = "ITC-BT-03"
                ),
                Question(
                    q = "Según el REBT, la empresa instaladora deberá disponer, como mínimo, de:",
                    opts = listOf(
                        "Un ingeniero técnico industrial",
                        "Una persona instaladora en baja tensión de la misma categoría de habilitación",
                        "Un técnico superior en electricidad y un ayudante",
                        "Un jefe de obra y un administrativo"
                    ),
                    a = 1,
                    exp = "ITC-BT-03, Apéndice I, punto 1: se requiere al menos un instalador de la misma categoría.",
                    ref = "ITC-BT-03"
                ),
                Question(
                    q = "Según el REBT, la empresa instaladora deberá contar con los medios técnicos adecuados para:",
                    opts = listOf(
                        "El montaje y la verificación de las instalaciones que ejecute",
                        "La emisión de facturas electrónicas",
                        "El control de consumo energético",
                        "La medición de armónicos en alta tensión"
                    ),
                    a = 0,
                    exp = "ITC-BT-03, punto 5.8.b: la empresa debe contar con los medios técnicos y humanos necesarios.",
                    ref = "ITC-BT-03"
                ),
                Question(
                    q = "Según el REBT, la empresa instaladora deberá tener suscrita una póliza de seguro de responsabilidad civil con cobertura mínima de:",
                    opts = listOf(
                        "300.000 € para básica y 600.000 € para especialista",
                        "600.000 € para básica y 900.000 € para especialista",
                        "900.000 € para básica y 1.200.000 € para especialista",
                        "500.000 € para ambas categorías"
                    ),
                    a = 1,
                    exp = "ITC-BT-03, punto 5.8.c: mínimo 600.000 € para básica y 900.000 € para especialista.",
                    ref = "ITC-BT-03"
                ),
                Question(
                    q = "Según el REBT, las empresas instaladoras deberán facilitar al órgano competente la documentación o información que se les requiera:",
                    opts = listOf(
                        "Cuando lo solicite la compañía eléctrica",
                        "Cuando lo solicite el órgano competente en materia de industria",
                        "Únicamente cada cinco años",
                        "Solo si cambia la normativa"
                    ),
                    a = 1,
                    exp = "ITC-BT-03, punto 5.3: deberán disponer de la documentación para presentarla cuando la Administración lo requiera.",
                    ref = "ITC-BT-03"
                ),
                Question(
                    q = "Según el REBT, las empresas instaladoras deberán comunicar al órgano competente cualquier incumplimiento reglamentario en las instalaciones en las que intervengan:",
                    opts = listOf(
                        "En un plazo no superior a 24 horas si existe peligro manifiesto",
                        "En el momento de finalizar la obra",
                        "Dentro del mismo mes",
                        "Solo cuando el titular lo autorice"
                    ),
                    a = 0,
                    exp = "ITC-BT-03, punto 6.f: en caso de peligro manifiesto deberán comunicarlo en un plazo máximo de 24 horas.",
                    ref = "ITC-BT-03"
                ),
                Question(
                    q = "Según el REBT, la empresa instaladora tiene prohibido:",
                    opts = listOf(
                        "Emitir certificados de instalaciones propias",
                        "Facilitar o ceder certificados de instalaciones no realizadas por ella misma",
                        "Contratar instaladores de otra empresa",
                        "Usar material con marcado CE"
                    ),
                    a = 1,
                    exp = "ITC-BT-03, punto 5.9: prohíbe ceder o facilitar certificados de instalaciones no realizadas por la empresa.",
                    ref = "ITC-BT-03"
                ),
                Question(
                    q = "Según el REBT, la empresa instaladora deberá conservar los certificados de instalación emitidos durante un período mínimo de:",
                    opts = listOf(
                        "Dos años",
                        "Cinco años",
                        "Diez años",
                        "Toda la vida útil de la instalación"
                    ),
                    a = 1,
                    exp = "ITC-BT-03, punto 6.j: debe conservar los contratos de mantenimiento al menos 5 años; por analogía con certificados emitidos.",
                    ref = "ITC-BT-03"
                ),
                Question(
                    q = "Según el REBT, la empresa instaladora está obligada a asistir a las inspecciones:",
                    opts = listOf(
                        "Solo si se le notifica por escrito",
                        "Cuando sea requerida por el órgano competente",
                        "Una vez al año",
                        "Únicamente en instalaciones industriales"
                    ),
                    a = 1,
                    exp = "ITC-BT-03, punto 6.g: debe asistir a las inspecciones si es requerida.",
                    ref = "ITC-BT-03"
                ),
                Question(
                    q = "Según el REBT, entre los medios técnicos mínimos de la categoría básica se incluye:",
                    opts = listOf(
                        "Medidor de aislamiento y continuidad, telurómetro y comprobador de diferenciales",
                        "Analizador de redes trifásico",
                        "Cámara termográfica",
                        "Medidor de armónicos de alta precisión"
                    ),
                    a = 0,
                    exp = "ITC-BT-03, Apéndice I, punto 2.1: especifica los equipos mínimos, entre ellos telurómetro y medidor de aislamiento.",
                    ref = "ITC-BT-03"
                ),
                Question(
                    q = "Según el REBT, la categoría especialista deberá disponer, además de los medios de la categoría básica, de:",
                    opts = listOf(
                        "Analizador de redes, armónicos y perturbaciones",
                        "Un luxómetro únicamente",
                        "Un multímetro digital portátil",
                        "Un osciloscopio de banco"
                    ),
                    a = 0,
                    exp = "ITC-BT-03, Apéndice I, punto 2.2: la categoría especialista debe disponer de analizador de redes, armónicos y perturbaciones.",
                    ref = "ITC-BT-03"
                ),
                Question(
                    q = "Según el REBT, la documentación técnica necesaria para una instalación dependerá de:",
                    opts = listOf(
                        "La empresa distribuidora",
                        "La importancia de la instalación",
                        "La ubicación geográfica",
                        "El tipo de empresa instaladora"
                    ),
                    a = 1,
                    exp = "ITC-BT-04, apartado 2: indica que la documentación adoptará Proyecto o Memoria Técnica en función de la importancia de la instalación.",
                    ref = "ITC-BT-04"
                ),
                Question(
                    q = "Según el REBT, cuando una instalación requiere proyecto, este debe ser redactado y firmado por:",
                    opts = listOf(
                        "Un instalador en baja tensión",
                        "La empresa suministradora",
                        "Un técnico titulado competente",
                        "Un organismo de control"
                    ),
                    a = 2,
                    exp = "ITC-BT-04, apartado 2.1: el proyecto debe ser redactado y firmado por técnico titulado competente.",
                    ref = "ITC-BT-04"
                ),
                Question(
                    q = "Según el REBT, en la memoria del proyecto deberán figurar, entre otros datos:",
                    opts = listOf(
                        "Los consumos mensuales del usuario",
                        "Los datos del propietario y el emplazamiento",
                        "La previsión de facturación anual",
                        "La normativa municipal aplicable"
                    ),
                    a = 1,
                    exp = "ITC-BT-04, apartado 2.1: se listan los datos que debe incluir la memoria, entre ellos propietario y emplazamiento.",
                    ref = "ITC-BT-04"
                ),
                Question(
                    q = "Según el REBT, la memoria técnica de diseño deberá redactarse en:",
                    opts = listOf(
                        "Formato libre",
                        "Impresos oficiales definidos por la Comunidad Autónoma",
                        "Un documento elaborado por la empresa suministradora",
                        "Un modelo aprobado por el Ayuntamiento"
                    ),
                    a = 1,
                    exp = "ITC-BT-04, apartado 2.2: la MTD se redactará sobre impresos según modelo de la Comunidad Autónoma.",
                    ref = "ITC-BT-04"
                ),
                Question(
                    q = "Según el REBT, la memoria técnica de diseño deberá incluir:",
                    opts = listOf(
                        "Precio total de la instalación",
                        "Relación nominal de receptores y su potencia",
                        "Contrato de mantenimiento",
                        "Vida útil estimada de los equipos"
                    ),
                    a = 1,
                    exp = "ITC-BT-04, apartado 2.2: la MTD debe contener la relación de receptores y su potencia.",
                    ref = "ITC-BT-04"
                ),
                Question(
                    q = "Según el REBT, precisarán proyecto las instalaciones nuevas incluidas en:",
                    opts = listOf(
                        "Cualquier instalación doméstica",
                        "Los grupos enumerados en el apartado 3.1",
                        "Solo instalaciones industriales",
                        "Solo instalaciones con potencia superior a 100 kW"
                    ),
                    a = 1,
                    exp = "ITC-BT-04, apartado 3.1: especifica los grupos de instalación que requieren proyecto.",
                    ref = "ITC-BT-04"
                ),
                Question(
                    q = "Según el REBT, cuando una instalación requiere proyecto, éste podrá desarrollarse:",
                    opts = listOf(
                        "Como parte del proyecto general del edificio o como proyectos específicos",
                        "Solo como un proyecto independiente del edificio",
                        "Únicamente mediante memoria técnica de diseño",
                        "Exclusivamente con planos sin memoria"
                    ),
                    a = 0,
                    exp = "ITC-BT-04, apartado 2.1: el proyecto podrá desarrollarse como parte del proyecto general del edificio o como uno o varios proyectos específicos.",
                    ref = "ITC-BT-04"
                ),
                Question(
                    q = "Según el REBT, requerirán proyecto las ampliaciones de instalaciones cuando:",
                    opts = listOf(
                        "La instalación tenga más de 10 años",
                        "La ampliación supere el 50% de la potencia prevista inicialmente",
                        "El instalador lo considere necesario",
                        "Lo solicite la compañía eléctrica"
                    ),
                    a = 1,
                    exp = "ITC-BT-04, apartado 3.2.c: las ampliaciones requieren proyecto si superan el 50% del proyecto anterior.",
                    ref = "ITC-BT-04"
                ),
                Question(
                    q = "Según el REBT, cuando una instalación figura en varios grupos que precisan proyecto, se aplicará:",
                    opts = listOf(
                        "El criterio más favorable al usuario",
                        "El criterio más económico",
                        "El criterio más exigente",
                        "El criterio elegido por la compañía suministradora"
                    ),
                    a = 2,
                    exp = "ITC-BT-04, apartado 3.3: se aplicará el criterio más exigente de los grupos.",
                    ref = "ITC-BT-04"
                ),
                Question(
                    q = "Según el REBT, todas las instalaciones deben ser ejecutadas por:",
                    opts = listOf(
                        "Un técnico titulado",
                        "Un organismo de control",
                        "La empresa suministradora",
                        "Empresas instaladoras en baja tensión habilitadas"
                    ),
                    a = 3,
                    exp = "ITC-BT-04, apartado 5.1: indica que todas las instalaciones deben ser efectuadas por empresas instaladoras de baja tensión.",
                    ref = "ITC-BT-04"
                ),
                Question(
                    q = "Según el REBT, las instalaciones que precisan proyecto deben contar con:",
                    opts = listOf(
                        "Revisión anual obligatoria",
                        "La dirección de un técnico titulado competente.",
                        "Un informe económico",
                        "Un aval del propietario"
                    ),
                    a = 1,
                    exp = "ITC-BT-04, apartado 5.1, segundo párrafo: establece que, en el caso de instalaciones que requirieron Proyecto, su ejecución deberá contar con la dirección de un técnico titulado competente.",
                    ref = "ITC-BT-04"
                ),
                Question(
                    q = "Según el REBT, si la empresa instaladora considera que el proyecto no cumple el Reglamento, deberá:",
                    opts = listOf(
                        "Modificarlo sin avisar",
                        "Informar por escrito al autor del proyecto y al propietario",
                        "Solicitar autorización al Ayuntamiento",
                        "Suspender la obra de inmediato"
                    ),
                    a = 1,
                    exp = "ITC-BT-04, apartado 5.1, tercer párrafo: obliga a comunicar por escrito esta circunstancia.",
                    ref = "ITC-BT-04"
                ),
                Question(
                    q = "Según el REBT, al finalizar la obra, la empresa instaladora deberá realizar:",
                    opts = listOf(
                        "Una auditoría energética",
                        "Las verificaciones necesarias según la instalación",
                        "La legalización automática",
                        "Una inspección periódica"
                    ),
                    a = 1,
                    exp = "ITC-BT-04, apartado 5.2: la empresa instaladora realizará las verificaciones oportunas.",
                    ref = "ITC-BT-04"
                ),
                Question(
                    q = "Según el REBT, cuando corresponda, deberá realizarse una inspección inicial por:",
                    opts = listOf(
                        "La empresa suministradora",
                        "Un técnico municipal",
                        "Un organismo de control",
                        "El proyectista"
                    ),
                    a = 2,
                    exp = "ITC-BT-04, apartado 5.3: Asimismo, las instalaciones que se especifican en la ITC-BT-05, deberán ser objeto de la correspondiente Inspección Inicial por Organismo de Control.",
                    ref = "ITC-BT-04"
                ),
                Question(
                    q = "Según el REBT, el certificado de instalación debe presentarse ante:",
                    opts = listOf(
                        "El Ministerio de Industria",
                        "La compañía eléctrica y el órgano competente de la Comunidad Autónoma",
                        "El Ayuntamiento",
                        "La empresa constructora"
                    ),
                    a = 1,
                    exp = "ITC-BT-04, apartado 5.5: el certificado se presenta ante el órgano competente y posteriormente a la compañía eléctrica.",
                    ref = "ITC-BT-04"
                ),
                Question(
                    q = "Según el REBT, el certificado de instalación debe presentarse en:",
                    opts = listOf(
                        "Una sola copia en todos los casos",
                        "Cinco copias, salvo presentación electrónica",
                        "Tres copias",
                        "Dos copias"
                    ),
                    a = 1,
                    exp = "ITC-BT-04, apartado 5.5: establece que se presentará por quintuplicado salvo tramitación electrónica.",
                    ref = "ITC-BT-04"
                ),
                Question(
                    q = "Según el REBT, en montajes temporales repetidos idénticos podrá omitirse:",
                    opts = listOf(
                        "El certificado de instalación",
                        "La documentación de diseño después del primer registro",
                        "La inspección inicial",
                        "Las verificaciones finales"
                    ),
                    a = 1,
                    exp = "ITC-BT-04, apartado 5.6, segundo párrafo: permite prescindir de la documentación de diseño en montajes repetidos.",
                    ref = "ITC-BT-04"
                ),
                Question(
                    q = "Según el REBT, para solicitar el suministro de energía el titular debe entregar:",
                    opts = listOf(
                        "El contrato de mantenimiento",
                        "El certificado de instalación",
                        "Un informe técnico del instalador",
                        "Una certificación del Ayuntamiento"
                    ),
                    a = 1,
                    exp = "ITC-BT-04, apartado 6: el titular solicitará el suministro entregando el certificado de instalación.",
                    ref = "ITC-BT-04"
                ),
                Question(
                    q = "Según el REBT, la empresa suministradora no podrá conectar una instalación cuando:",
                    opts = listOf(
                        "No exista contrato de mantenimiento",
                        "La instalación no haya sido revisada por un arquitecto",
                        "Los valores de aislamiento o corrientes de fuga no cumplan los límites",
                        "No haya memoria técnica"
                    ),
                    a = 2,
                    exp = "ITC-BT-04, apartado 6, tercer párrafo: si los valores no cumplen ITC-BT-19, la empresa suministradora no podrá conectar.",
                    ref = "ITC-BT-04"
                ),
                Question(
                    q = "Según el REBT, la verificación de las instalaciones deberá realizarse conforme a la norma:",
                    opts = listOf(
                        "UNE-HD 60.364-6",
                        "UNE-EN 60670-1",
                        "UNE 20324-3",
                        "UNE 50110-2"
                    ),
                    a = 0,
                    exp = "ITC-BT-05, punto 3: las instalaciones en baja tensión deberán ser verificadas antes de su puesta en servicio siguiendo la metodología de la norma UNE 20.460-6-61 anulada y sustituida por la norma UNE-HD 60.364-6.",
                    ref = "ITC-BT-05"
                ),
                Question(
                    q = "Según el REBT, las verificaciones deberán garantizar que:",
                    opts = listOf(
                        "La instalación cumple las prescripciones del Reglamento y sus ITC",
                        "La instalación funciona con la potencia contratada",
                        "Los equipos cumplen las normas ISO correspondientes",
                        "La documentación esté debidamente registrada"
                    ),
                    a = 0,
                    exp = "ITC-BT-05, puntos 3 y 5.1: las instalaciones se verifican según la metodología UNE 20.460-6-61 y las inspecciones se realizan sobre la base de las prescripciones del Reglamento y la documentación técnica, para comprobar el cumplimiento reglamentario.",
                    ref = "ITC-BT-05"
                ),
                Question(
                    q = "Según el REBT, las inspecciones de las instalaciones eléctricas serán realizadas por:",
                    opts = listOf(
                        "Organismos de Control acreditados según el Real Decreto 2200/1995",
                        "El titular de la instalación",
                        "La empresa suministradora de energía",
                        "El proyectista que redactó la memoria técnica"
                    ),
                    a = 0,
                    exp = "ITC-BT-05, punto 2.b: los agentes que lleven a cabo las inspecciones deberán tener la condición de Organismos de Control, según el Real Decreto 2200/1995.",
                    ref = "ITC-BT-05"
                ),
                Question(
                    q = "Según el REBT, las inspecciones podrán ser:",
                    opts = listOf(
                        "Iniciales y periódicas",
                        "Parciales y globales",
                        "Documentales y técnicas",
                        "De seguridad y mantenimiento"
                    ),
                    a = 0,
                    exp = "ITC-BT-05, punto 4: se indica que las inspecciones podrán ser iniciales (antes de la puesta en servicio) y periódicas.",
                    ref = "ITC-BT-05"
                ),
                Question(
                    q = "Según el REBT, las instalaciones eléctricas que precisen proyecto deberán ser objeto de inspección inicial cuando:",
                    opts = listOf(
                        "Su potencia instalada sea superior a 100 kW",
                        "Su potencia instalada sea superior a 50 kW",
                        "Superen los 10 circuitos independientes",
                        "Sean de uso doméstico con más de 25 receptores"
                    ),
                    a = 0,
                    exp = "ITC-BT-05, punto 4.1.a: instalaciones industriales que precisen proyecto con potencia instalada superior a 100 kW serán objeto de inspección inicial.",
                    ref = "ITC-BT-05"
                ),
                Question(
                    q = "Según el REBT, serán objeto de inspección inicial los locales de pública concurrencia:",
                    opts = listOf(
                        "Cualquiera que sea su potencia instalada",
                        "Solo si superan 50 kW de potencia",
                        "Siempre que estén destinados a uso industrial",
                        "Cuando dispongan de más de 100 luminarias"
                    ),
                    a = 0,
                    exp = "ITC-BT-05, punto 4.1.b: se incluyen los locales de pública concurrencia, sin condicionarlo a una potencia mínima.",
                    ref = "ITC-BT-05"
                ),
                Question(
                    q = "Según el REBT, en locales con riesgo de incendio o explosión de clase I no será exigible inspección inicial cuando se trate de:",
                    opts = listOf(
                        "Aparcamientos o estacionamientos de menos de 25 plazas",
                        "Locales de almacenamiento de combustibles",
                        "Talleres mecánicos con atmósfera explosiva",
                        "Zonas de carga de baterías industriales"
                    ),
                    a = 0,
                    exp = "ITC-BT-05, punto 4.1.c: se citan los locales con riesgo de incendio o explosión de clase I, excepto aparcamientos o estacionamientos de menos de 25 plazas.",
                    ref = "ITC-BT-05"
                ),
                Question(
                    q = "Según el REBT, las instalaciones en locales mojados deberán someterse a inspección inicial cuando su potencia instalada sea superior a:",
                    opts = listOf(
                        "10 kW",
                        "15 kW",
                        "20 kW",
                        "25 kW"
                    ),
                    a = 3,
                    exp = "ITC-BT-05, punto 4.1.d: locales mojados con potencia instalada superior a 25 kW.",
                    ref = "ITC-BT-05"
                ),
                Question(
                    q = "Según el REBT, las piscinas deberán ser objeto de inspección inicial cuando su potencia instalada sea superior a:",
                    opts = listOf(
                        "5 kW",
                        "10 kW",
                        "15 kW",
                        "20 kW"
                    ),
                    a = 1,
                    exp = "ITC-BT-05, punto 4.1.e: piscinas con potencia instalada superior a 10 kW.",
                    ref = "ITC-BT-05"
                ),
                Question(
                    q = "Según el REBT, las instalaciones de alumbrado exterior deberán someterse a inspección inicial cuando la potencia instalada supere:",
                    opts = listOf(
                        "2 kW",
                        "3 kW",
                        "5 kW",
                        "7,5 kW"
                    ),
                    a = 2,
                    exp = "ITC-BT-05, punto 4.1.k: instalaciones de alumbrado exterior con potencia instalada superior a 5 kW.",
                    ref = "ITC-BT-05"
                ),
                Question(
                    q = "Según el REBT, las instalaciones de recarga de vehículos eléctricos deberán ser objeto de inspección inicial cuando:",
                    opts = listOf(
                        "Requieran proyecto para su ejecución",
                        "Dispongan de más de 10 puntos de recarga",
                        "Superen 50 kW de potencia total",
                        "Sean de acceso público"
                    ),
                    a = 0,
                    exp = "ITC-BT-05, punto 4.1.h: instalaciones de estaciones de recarga para vehículo eléctrico que requieran elaboración de proyecto.",
                    ref = "ITC-BT-05"
                ),
                Question(
                    q = "Según el REBT, las instalaciones que precisaron inspección inicial deberán someterse a inspecciones periódicas cada:",
                    opts = listOf(
                        "3 años",
                        "5 años",
                        "10 años",
                        "15 años"
                    ),
                    a = 1,
                    exp = "ITC-BT-05, punto 4.2: las instalaciones que precisaron inspección inicial serán objeto de inspecciones periódicas cada 5 años.",
                    ref = "ITC-BT-05"
                ),
                Question(
                    q = "Según el REBT, las instalaciones comunes de edificios de viviendas con potencia total instalada superior a 100 kW deberán someterse a inspección periódica cada:",
                    opts = listOf(
                        "5 años",
                        "8 años",
                        "10 años",
                        "15 años"
                    ),
                    a = 2,
                    exp = "ITC-BT-05, punto 4.2: las comunes de edificios de viviendas de potencia total instalada superior a 100 kW cada 10 años.",
                    ref = "ITC-BT-05"
                ),
                Question(
                    q = "Según el REBT, los resultados de las inspecciones deberán documentarse en un:",
                    opts = listOf(
                        "Certificado de inspección emitido por el Organismo de Control",
                        "Informe técnico del titular de la instalación",
                        "Parte de mantenimiento anual",
                        "Certificado de instalación emitido por la empresa instaladora"
                    ),
                    a = 0,
                    exp = "ITC-BT-05, punto 5.2: como resultado de la inspección el Organismo de Control emitirá un Certificado de Inspección.",
                    ref = "ITC-BT-05"
                ),
                Question(
                    q = "Según el REBT, el certificado de inspección deberá incluir:",
                    opts = listOf(
                        "Los datos de identificación de la instalación y la relación de defectos detectados",
                        "La potencia máxima contratada y los consumos anuales",
                        "El coste económico de las deficiencias detectadas",
                        "Las firmas de todos los trabajadores de la instalación"
                    ),
                    a = 0,
                    exp = "ITC-BT-05, punto 5.2: el Certificado de Inspección recogerá los datos de identificación de la instalación, la relación de defectos con su clasificación y la calificación de la instalación.",
                    ref = "ITC-BT-05"
                ),
                Question(
                    q = "Según el REBT, se considerará que una instalación es favorable cuando:",
                    opts = listOf(
                        "No se determine ningún defecto muy grave o grave",
                        "Solo existan defectos graves corregibles",
                        "No haya defectos leves pendientes",
                        "Exista un defecto grave corregido antes del acta final"
                    ),
                    a = 0,
                    exp = "ITC-BT-05, punto 5.2.1: calificación favorable cuando no se determine la existencia de ningún defecto muy grave o grave.",
                    ref = "ITC-BT-05"
                ),
                Question(
                    q = "Según el REBT, los defectos leves deberán ser subsanados:",
                    opts = listOf(
                        "Antes de la próxima inspección periódica",
                        "En un plazo máximo de seis meses",
                        "En un plazo de treinta días",
                        "Antes de la puesta en servicio de la instalación"
                    ),
                    a = 0,
                    exp = "ITC-BT-05, punto 5.2.1: los defectos leves se anotan para constancia del titular, con la indicación de que deberá poner los medios para subsanarlos antes de la próxima inspección.",
                    ref = "ITC-BT-05"
                ),
                Question(
                    q = "Según el REBT, los defectos muy graves implican:",
                    opts = listOf(
                        "Riesgo inmediato para las personas o los bienes",
                        "Solo deficiencias administrativas",
                        "Falta de señalización en cuadros eléctricos",
                        "Error en el cálculo de la potencia contratada"
                    ),
                    a = 0,
                    exp = "ITC-BT-05, punto 6.1: defecto muy grave es aquel que constituye un peligro inmediato para la seguridad de las personas o los bienes.",
                    ref = "ITC-BT-05"
                ),
                Question(
                    q = "Según el REBT, la finalidad de las inspecciones es:",
                    opts = listOf(
                        "Asegurar que se cumple el Reglamento a lo largo de la vida de la instalación",
                        "Valorar económicamente los daños por defectos",
                        "Actualizar los datos de registro de instaladores",
                        "Comprobar los consumos energéticos del usuario"
                    ),
                    a = 0,
                    exp = "ITC-BT-05, punto 4: las instalaciones de especial relevancia deberán ser objeto de inspección para asegurar, en la medida de lo posible, el cumplimiento reglamentario a lo largo de su vida.",
                    ref = "ITC-BT-05"
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
,
                Question(
                    q = "Según el REBT, la electrificación básica es la necesaria para:",
                    opts = listOf(
                        "Cubrir las necesidades primarias sin obras posteriores y permitir el uso de aparatos eléctricos de uso común",
                        "Instalar sistemas de climatización eléctrica en toda vivienda",
                        "Superficies útiles de vivienda superiores a 160 m²",
                        "Viviendas con instalación de recarga de vehículo eléctrico"
                    ),
                    a = 0,
                    exp = "ITC-BT-10, punto 2.1.1 Electrificación básica: se define como la necesaria para la cobertura de las posibles necesidades de utilización primarias sin necesidad de obras posteriores de adecuación y que debe permitir la utilización de los aparatos eléctricos de uso común en una vivienda.",
                    ref = "ITC-BT-10"
                ),
                Question(
                    q = "Según el REBT, tendrán electrificación elevada las viviendas con:",
                    opts = listOf(
                        "Previsión de uso de más electrodomésticos que la básica o con calefacción/aire acondicionado, o superficie útil > 160 m², o con instalación de recarga de vehículo eléctrico en unifamiliares",
                        "Únicamente superficies útiles superiores a 120 m²",
                        "Instalación de telecomunicaciones avanzada",
                        "Iluminación LED en todas las estancias"
                    ),
                    a = 0,
                    exp = "ITC-BT-10, punto 2.1.2 Electrificación elevada: se refiere a viviendas con previsión de utilización de aparatos electrodomésticos superior a la básica, o con sistemas de calefacción eléctrica o acondicionamiento de aire, o con superficies útiles superiores a 160 m², o con una instalación para la recarga del vehículo eléctrico en viviendas unifamiliares, o cualquier combinación de estos casos.",
                    ref = "ITC-BT-10"
                ),
                Question(
                    q = "Según el REBT, en nuevas construcciones la potencia a prever por vivienda no será inferior a:",
                    opts = listOf(
                        "5 750 W a 230 V",
                        "4 600 W a 230 V",
                        "6 000 W a 230 V",
                        "7 360 W a 230 V"
                    ),
                    a = 0,
                    exp = "ITC-BT-10, punto 2.2 Previsión de la potencia: indica que, para nuevas construcciones, la potencia a prever en cada vivienda no será inferior a 5 750 W a 230 V.",
                    ref = "ITC-BT-10"
                ),
                Question(
                    q = "Según el REBT, en viviendas con electrificación elevada, la potencia a prever no será inferior a:",
                    opts = listOf(
                        "7 360 W",
                        "9 200 W",
                        "10 350 W",
                        "14 490 W"
                    ),
                    a = 1,
                    exp = "ITC-BT-10, punto 2.2: para las viviendas con grado de electrificación elevada, la potencia a prever no será inferior a 9 200 W.",
                    ref = "ITC-BT-10"
                ),
                Question(
                    q = "Según el REBT, la potencia a prever en una vivienda se corresponde con:",
                    opts = listOf(
                        "La capacidad máxima definida por la intensidad asignada del IGA, según ITC-BT-25",
                        "La potencia inicialmente contratada por el usuario",
                        "La suma de potencias de los receptores instalados",
                        "El valor que indique la empresa instaladora"
                    ),
                    a = 0,
                    exp = "ITC-BT-10, punto 2.2: en todos los casos, la potencia a prever se corresponderá con la capacidad máxima de la instalación, definida por la intensidad asignada del interruptor general automático, según se indica en la ITC-BT-25.",
                    ref = "ITC-BT-10"
                ),
                Question(
                    q = "Según el REBT, la carga correspondiente a un conjunto de viviendas se obtiene:",
                    opts = listOf(
                        "Multiplicando la media aritmética de las potencias máximas previstas por el coeficiente de simultaneidad de la tabla 1",
                        "Sumando todas las potencias máximas previstas sin reducción",
                        "Tomando el máximo entre todas las potencias individuales",
                        "Aplicando un coeficiente fijo del 0,5 a la suma de potencias"
                    ),
                    a = 0,
                    exp = "ITC-BT-10, punto 3.1: la carga correspondiente a un conjunto de viviendas se obtiene multiplicando la media aritmética de las potencias máximas previstas en cada vivienda por el coeficiente de simultaneidad indicado en la tabla 1, según el número de viviendas.",
                    ref = "ITC-BT-10"
                ),
                Question(
                    q = "Según el REBT, para edificios con tarifa nocturna, la simultaneidad en viviendas será:",
                    opts = listOf(
                        "Igual al número de viviendas (coeficiente = n.º de viviendas)",
                        "Constante e igual a 10",
                        "La mitad del número de viviendas",
                        "La de la tabla 1 menos una vivienda"
                    ),
                    a = 0,
                    exp = "ITC-BT-10, punto 3.1, nota bajo la tabla 1: para edificios cuya instalación esté prevista para la aplicación de la tarifa nocturna, la simultaneidad será 1, es decir, coeficiente de simultaneidad igual al número de viviendas.",
                    ref = "ITC-BT-10"
                ),
                Question(
                    q = "Según el REBT, para n > 21 viviendas, el coeficiente de simultaneidad de la tabla 1 es:",
                    opts = listOf(
                        "15,3 + (n − 21) · 0,5",
                        "15,0 + (n − 21) · 0,3",
                        "10,6 + (n − 15) · 0,7",
                        "n / 2"
                    ),
                    a = 0,
                    exp = "ITC-BT-10, tabla 1 (Coeficiente de simultaneidad según el número de viviendas): para n > 21 viviendas, el coeficiente viene dado por la expresión 15,3 + (n − 21) · 0,5.",
                    ref = "ITC-BT-10"
                ),
                Question(
                    q = "Según el REBT, la carga de los servicios generales del edificio será:",
                    opts = listOf(
                        "La suma de las potencias previstas (ascensores, centrales, alumbrado de comunes, etc.) con factor de simultaneidad 1",
                        "La mitad de la suma de las potencias previstas",
                        "La potencia del ascensor multiplicada por 1,8",
                        "La potencia del alumbrado de escalera más el 10 % del resto"
                    ),
                    a = 0,
                    exp = "ITC-BT-10, punto 3.2: la carga correspondiente a los servicios generales será la suma de la potencia prevista en ascensores, aparatos elevadores, centrales de calor y frío, grupos de presión, alumbrado de portal, caja de escalera, espacios comunes y demás servicios generales, sin aplicar ningún factor de reducción por simultaneidad (factor de simultaneidad = 1).",
                    ref = "ITC-BT-10"
                ),
                Question(
                    q = "Según el REBT, en locales comerciales y oficinas integrados en el edificio de viviendas se considerará:",
                    opts = listOf(
                        "Mínimo 100 W/m² y planta, con mínimo por local de 3450 W a 230 V y coeficiente 1",
                        "Mínimo 50 W/m², sin mínimo por local",
                        "Mínimo 125 W/m² y planta, con mínimo 10 350 W",
                        "Solo la potencia de iluminación instalada"
                    ),
                    a = 0,
                    exp = "ITC-BT-10, punto 3.3: la carga correspondiente a los locales comerciales y oficinas se calcula considerando un mínimo de 100 W por metro cuadrado y planta, con un mínimo por local de 3450 W a 230 V y coeficiente de simultaneidad 1.",
                    ref = "ITC-BT-10"
                ),
                Question(
                    q = "Según el REBT, la carga de garajes integrados se calculará considerando:",
                    opts = listOf(
                        "10 W/m² y planta para ventilación natural y 20 W/m² para ventilación forzada, con mínimo 3 450 W a 230 V y coeficiente 1",
                        "5 W/m² y planta, sin mínimos",
                        "15 W/m² y planta, con mínimo 9 200 W",
                        "Únicamente 100 W/m², coeficiente 1"
                    ),
                    a = 0,
                    exp = "ITC-BT-10, punto 3.4: la carga de los garajes se calcula con un mínimo de 10 W por metro cuadrado y planta para garajes de ventilación natural y de 20 W para los de ventilación forzada, con un mínimo de 3 450 W a 230 V y coeficiente de simultaneidad 1.",
                    ref = "ITC-BT-10"
                ),
                Question(
                    q = "Según el REBT, en edificios comerciales u oficinas (no preferentemente viviendas) la carga a prever será, como mínimo:",
                    opts = listOf(
                        "100 W/m² y planta, con mínimo por local de 3 450 W a 230 V y coeficiente 1",
                        "50 W/m² sin mínimos",
                        "125 W/m² y planta con mínimo 10 350 W",
                        "3 680 W por local"
                    ),
                    a = 0,
                    exp = "ITC-BT-10, punto 4.1: en edificios comerciales o de oficinas, la carga mínima se calcula con 100 W por metro cuadrado y planta, con un mínimo por local de 3 450 W a 230 V y coeficiente de simultaneidad 1.",
                    ref = "ITC-BT-10"
                ),
                Question(
                    q = "Según el REBT, en edificios destinados a concentración de industrias, la carga mínima a prever será:",
                    opts = listOf(
                        "125 W/m² y planta, con mínimo por local de 10 350 W a 230 V y coeficiente 1",
                        "100 W/m² con mínimo 3 450 W",
                        "75 W/m² sin mínimos",
                        "200 W/m² con coeficiente 0,8"
                    ),
                    a = 0,
                    exp = "ITC-BT-10, punto 4.2: para edificios destinados a concentración de industrias se considera un mínimo de 125 W por metro cuadrado y planta, con un mínimo por local de 10 350 W a 230 V y coeficiente de simultaneidad 1.",
                    ref = "ITC-BT-10"
                ),
                Question(
                    q = "Según el REBT, en viviendas unifamiliares con infraestructura para recarga de VE se considerará:",
                    opts = listOf(
                        "Grado de electrificación elevado",
                        "Electrificación básica con 5 750 W",
                        "Solo aumentar el coeficiente de simultaneidad",
                        "Un mínimo de 3 680 W adicional por vivienda sin más"
                    ),
                    a = 0,
                    exp = "ITC-BT-10, punto 5.1: para la previsión de cargas de viviendas unifamiliares dotadas de infraestructura para la recarga de vehículos eléctricos se considerará grado de electrificación elevado.",
                    ref = "ITC-BT-10"
                ),
                Question(
                    q = "Según el REBT, en aparcamientos colectivos en propiedad horizontal la previsión de cargas para VE se calcula:",
                    opts = listOf(
                        "Multiplicando 3.680 W por el 10 % de las plazas construidas; la suma se multiplica por el factor de simultaneidad y se añade al resto según el esquema y la ITC-BT-52",
                        "Sumando 3.680 W por cada plaza construida",
                        "Tomando 10 % de la potencia total del edificio",
                        "Aplicando 100 W/m² de la superficie de parking"
                    ),
                    a = 0,
                    exp = "ITC-BT-10, punto 5.2: la previsión de cargas para la recarga de vehículos eléctricos en aparcamientos colectivos se obtiene multiplicando 3 680 W por el 10 % del total de plazas de aparcamiento construidas; la suma de estas potencias se multiplica por el factor de simultaneidad correspondiente y se suma a la previsión de potencia del resto de la instalación, según el esquema y lo establecido en la ITC-BT-52.",
                    ref = "ITC-BT-10"
                ),
                Question(
                    q = "Según el REBT, el proyectista podrá prever una potencia instalada mayor para VE cuando:",
                    opts = listOf(
                        "Disponga de datos que lo justifiquen",
                        "Lo exija la empresa instaladora",
                        "Siempre, sin justificación",
                        "El promotor declare uso esporádico"
                    ),
                    a = 0,
                    exp = "ITC-BT-10, punto 5.2 (párrafo final): se indica que el proyectista podrá prever una potencia instalada mayor cuando disponga de los datos que lo justifiquen.",
                    ref = "ITC-BT-10"
                ),
                Question(
                    q = "Según el REBT, las empresas distribuidoras estarán obligadas, si lo solicita el cliente, a efectuar el suministro monofásico que permita:",
                    opts = listOf(
                        "El funcionamiento de cualquier receptor ≤ 5 750 W a 230 V, hasta un suministro máximo de 14 490 W a 230 V",
                        "El funcionamiento hasta 9 200 W por circuito",
                        "Solo receptores de hasta 3 450 W",
                        "Cargas monofásicas de 16 A máximo"
                    ),
                    a = 0,
                    exp = "ITC-BT-10, punto 7 Suministros monofásicos: las empresas distribuidoras estarán obligadas, siempre que lo solicite el cliente, a efectuar el suministro de forma que permita el funcionamiento de cualquier receptor monofásico de potencia menor o igual a 5 750 W a 230 V, hasta un suministro de potencia máxima de 14 490 W a 230 V.",
                    ref = "ITC-BT-10"
                ),
                Question(
                    q = "Según el REBT, en la previsión de potencia por local comercial dentro de edificio de viviendas, el coeficiente de simultaneidad aplicable es:",
                    opts = listOf(
                        "1",
                        "0,9",
                        "0,5",
                        "El de la tabla 1 para viviendas"
                    ),
                    a = 0,
                    exp = "ITC-BT-10, punto 3.3: para los locales comerciales y oficinas integrados en el edificio se indica un coeficiente de simultaneidad igual a 1.",
                    ref = "ITC-BT-10"
                ),
                Question(
                    q = "Según el REBT, la carga de garajes con ventilación forzada, a efectos de previsión, se toma con:",
                    opts = listOf(
                        "20 W/m² y planta, mínimo 3 450 W a 230 V y coeficiente 1",
                        "10 W/m² y planta, sin mínimo",
                        "100 W/m² y coeficiente 0,8",
                        "125 W/m², mínimo 10 350 W"
                    ),
                    a = 0,
                    exp = "ITC-BT-10, punto 3.4: para garajes de ventilación forzada se considera un mínimo de 20 W por metro cuadrado y planta, con un mínimo de 3 450 W a 230 V y coeficiente de simultaneidad 1.",
                    ref = "ITC-BT-10"
                ),
                Question(
                    q = "Según el REBT, las cajas generales de protección son las que alojan:",
                    opts = listOf(
                        "Los elementos de protección de las líneas generales de alimentación",
                        "Los equipos de medida de energía eléctrica",
                        "Los dispositivos generales de mando y protección",
                        "Las conexiones de la derivación individual"
                    ),
                    a = 0,
                    exp = "ITC-BT-13, apartado 1: define las cajas generales de protección como las que alojan los elementos de protección de las líneas generales de alimentación.",
                    ref = "ITC-BT-13"
                ),
                Question(
                    q = "Según el REBT, las cajas generales de protección se instalarán preferentemente:",
                    opts = listOf(
                        "Sobre las fachadas exteriores de los edificios en lugares de libre y permanente acceso",
                        "En el interior de las viviendas",
                        "Dentro del cuadro general de mando del usuario",
                        "En los locales del transformador"
                    ),
                    a = 0,
                    exp = "ITC-BT-13, apartado 1.1 Emplazamiento e instalación: indica que se instalarán preferentemente sobre las fachadas exteriores, en lugares de libre y permanente acceso.",
                    ref = "ITC-BT-13"
                ),
                Question(
                    q = "Según el REBT, la situación de la caja general de protección se fijará:",
                    opts = listOf(
                        "De común acuerdo entre la propiedad y la empresa suministradora",
                        "Por decisión del instalador autorizado",
                        "Por el promotor de la obra",
                        "Por el ayuntamiento correspondiente"
                    ),
                    a = 0,
                    exp = "ITC-BT-13, apartado 1.1: establece que su situación se fijará de común acuerdo entre la propiedad y la empresa suministradora.",
                    ref = "ITC-BT-13"
                ),
                Question(
                    q = "Según el REBT, en edificios con centro de transformación interior, los fusibles del cuadro de baja tensión podrán utilizarse como:",
                    opts = listOf(
                        "Protección de la línea general de alimentación, desempeñando la función de caja general de protección",
                        "Dispositivos generales de mando y protección del usuario",
                        "Protección de la derivación individual",
                        "Medida principal del suministro"
                    ),
                    a = 0,
                    exp = "ITC-BT-13, apartado 1.1: indica que los fusibles del cuadro de baja tensión podrán utilizarse como protección de la LGA, haciendo de caja general de protección.",
                    ref = "ITC-BT-13"
                ),
                Question(
                    q = "Según el REBT, cuando los fusibles del cuadro de baja tensión del centro de transformación actúan como caja general de protección:",
                    opts = listOf(
                        "La propiedad y mantenimiento serán de la empresa suministradora",
                        "El propietario del edificio asumirá su mantenimiento",
                        "El instalador autorizado será responsable de su custodia",
                        "El usuario será el único responsable de su conservación"
                    ),
                    a = 0,
                    exp = "ITC-BT-13, apartado 1.1: especifica que, en este caso, la propiedad y el mantenimiento de la protección serán de la empresa suministradora.",
                    ref = "ITC-BT-13"
                ),
                Question(
                    q = "Según el REBT, cuando la acometida sea aérea, las cajas generales de protección podrán instalarse:",
                    opts = listOf(
                        "En montaje superficial a una altura comprendida entre 3 m y 4 m sobre el suelo",
                        "A ras del suelo",
                        "A una altura mínima de 1 m",
                        "Empotradas en pared a 2 m"
                    ),
                    a = 0,
                    exp = "ITC-BT-13, apartado 1.1: para acometida aérea permite instalar la CGP en montaje superficial entre 3 m y 4 m sobre el suelo.",
                    ref = "ITC-BT-13"
                ),
                Question(
                    q = "Según el REBT, cuando esté previsto el paso de red aérea a subterránea, la caja general de protección se situará:",
                    opts = listOf(
                        "Como si se tratase de una acometida subterránea",
                        "A una altura mayor de 5 m",
                        "En el punto más alto de la fachada",
                        "Junto al cuadro de mando del usuario"
                    ),
                    a = 0,
                    exp = "ITC-BT-13, apartado 1.1: establece que si se prevé paso de red aérea a subterránea, la CGP se situará como si fuera una acometida subterránea.",
                    ref = "ITC-BT-13"
                ),
                Question(
                    q = "Según el REBT, cuando la acometida sea subterránea, la caja general de protección se instalará:",
                    opts = listOf(
                        "En un nicho en pared cerrado con una puerta preferentemente metálica",
                        "En superficie exterior protegida por armario plástico",
                        "En el interior de la vivienda más próxima",
                        "Bajo el nivel del suelo dentro de arqueta"
                    ),
                    a = 0,
                    exp = "ITC-BT-13, apartado 1.1: indica que, con acometida subterránea, la CGP se instalará siempre en un nicho en pared con puerta preferentemente metálica.",
                    ref = "ITC-BT-13"
                ),
                Question(
                    q = "Según el REBT, la puerta del nicho donde se ubica la caja subterránea tendrá un grado de protección mínimo:",
                    opts = listOf(
                        "IK10 según UNE-EN 50.102",
                        "IK08 según UNE-EN 50.102",
                        "IK09 según UNE-EN 50.102",
                        "IP43 según UNE 20.324"
                    ),
                    a = 0,
                    exp = "ITC-BT-13, apartado 1.1: exige que la puerta del nicho tenga grado de protección IK10 según UNE-EN 50.102.",
                    ref = "ITC-BT-13"
                ),
                Question(
                    q = "Según el REBT, la parte inferior de la puerta del nicho deberá encontrarse:",
                    opts = listOf(
                        "A un mínimo de 30 cm del suelo",
                        "A 50 cm del suelo",
                        "Al nivel del suelo",
                        "A un máximo de 10 cm del suelo"
                    ),
                    a = 0,
                    exp = "ITC-BT-13, apartado 1.1: indica que la parte inferior de la puerta se encontrará a un mínimo de 30 cm del suelo.",
                    ref = "ITC-BT-13"
                ),
                Question(
                    q = "Según el REBT, no se alojarán más de:",
                    opts = listOf(
                        "Dos cajas generales de protección en el interior del mismo nicho",
                        "Tres cajas generales de protección por línea general",
                        "Una caja general de protección por edificio",
                        "Cinco cajas generales en la fachada"
                    ),
                    a = 0,
                    exp = "ITC-BT-13, apartado 1.1: señala que no se alojarán más de dos cajas generales de protección en el mismo nicho.",
                    ref = "ITC-BT-13"
                ),
                Question(
                    q = "Según el REBT, se dispondrá una caja general de protección por cada:",
                    opts = listOf(
                        "Línea general de alimentación",
                        "Derivación individual",
                        "Contador de usuario",
                        "Suministro contratado"
                    ),
                    a = 0,
                    exp = "ITC-BT-13, apartado 1.1: especifica que se dispondrá una caja general de protección por cada línea general de alimentación.",
                    ref = "ITC-BT-13"
                ),
                Question(
                    q = "Según el REBT, cuando para un suministro se precisen más de dos cajas generales de protección:",
                    opts = listOf(
                        "Podrán utilizarse otras soluciones técnicas previo acuerdo entre la propiedad y la empresa suministradora",
                        "Se instalarán todas en el mismo nicho",
                        "Deberán ubicarse en el interior del edificio",
                        "Se conectarán en paralelo sin autorización"
                    ),
                    a = 0,
                    exp = "ITC-BT-13, apartado 1.1: indica que, si se necesitan más de dos CGP para un suministro, podrán emplearse otras soluciones técnicas previo acuerdo entre propiedad y suministradora.",
                    ref = "ITC-BT-13"
                ),
                Question(
                    q = "Según el REBT, los usuarios o instaladores solo podrán actuar sobre las conexiones a la LGA:",
                    opts = listOf(
                        "Previa comunicación a la empresa suministradora",
                        "Sin necesidad de autorización",
                        "Cuando exista un corte de suministro",
                        "Con presencia del titular de la vivienda"
                    ),
                    a = 0,
                    exp = "ITC-BT-13, apartado 1.1: establece que solo se podrá actuar sobre las conexiones con la línea general de alimentación previa comunicación a la empresa suministradora.",
                    ref = "ITC-BT-13"
                ),
                Question(
                    q = "Según el REBT, el neutro en la caja general de protección estará constituido por:",
                    opts = listOf(
                        "Una conexión amovible situada a la izquierda de las fases",
                        "Un borne fijo unido al chasis metálico",
                        "Un conductor aislado con doble aislamiento",
                        "Una barra equipotencial común"
                    ),
                    a = 0,
                    exp = "ITC-BT-13, apartado 1.2 Tipos y características: indica que el neutro estará constituido por una conexión amovible situada a la izquierda de las fases.",
                    ref = "ITC-BT-13"
                ),
                Question(
                    q = "Según el REBT, las cajas generales de protección tendrán un grado de protección mínimo:",
                    opts = listOf(
                        "IP43 según UNE 20.324 e IK08 según UNE-EN 50.102",
                        "IP55 e IK10",
                        "IP44 e IK09",
                        "IP54 e IK08"
                    ),
                    a = 0,
                    exp = "ITC-BT-13, apartado 1.2: fija que las cajas generales de protección tendrán grado IP43 según UNE 20.324 e IK08 según UNE-EN 50.102.",
                    ref = "ITC-BT-13"
                ),
                Question(
                    q = "Según el REBT, las cajas generales de protección cumplirán lo indicado en la norma:",
                    opts = listOf(
                        "UNE-EN 60.439-1",
                        "UNE-EN 50.102",
                        "UNE 20460-5-52",
                        "UNE 21123"
                    ),
                    a = 0,
                    exp = "ITC-BT-13, apartado 1.2: señala que las cajas generales de protección cumplirán lo indicado en la Norma UNE-EN 60.439-1.",
                    ref = "ITC-BT-13"
                ),
                Question(
                    q = "Según el REBT, las cajas generales de protección tendrán grado de inflamabilidad según:",
                    opts = listOf(
                        "UNE-EN 60.439-3",
                        "UNE-EN 60.439-1",
                        "UNE 20.324",
                        "UNE 21123"
                    ),
                    a = 0,
                    exp = "ITC-BT-13, mismo párrafo: indica que tendrán grado de inflamabilidad según UNE-EN 60.439-3.",
                    ref = "ITC-BT-13"
                ),
                Question(
                    q = "Según el REBT, las cajas de protección y medida combinan:",
                    opts = listOf(
                        "La caja general de protección y el equipo de medida en un único elemento",
                        "El cuadro general del usuario con el contador",
                        "La derivación individual con la CGP",
                        "Los dispositivos de mando con la acometida"
                    ),
                    a = 0,
                    exp = "ITC-BT-13, apartado 2: define la caja de protección y medida como un único elemento que integra la caja general de protección y el equipo de medida.",
                    ref = "ITC-BT-13"
                ),
                Question(
                    q = "Según el REBT, las cajas de protección y medida tendrán grado de protección mínimo:",
                    opts = listOf(
                        "IP43 según UNE 20.324 e IK09 según UNE-EN 50.102",
                        "IP55 e IK10",
                        "IP44 e IK08",
                        "IP54 e IK09"
                    ),
                    a = 0,
                    exp = "ITC-BT-13, apartado 2.2 Tipos y características: establece que las cajas de protección y medida tendrán, una vez instaladas, grado IP43 según UNE 20.324 e IK09 según UNE-EN 50.102.",
                    ref = "ITC-BT-13"
                ),
                Question(
                    q = "¿Qué tipo de conductores forman la línea general de alimentación según la ITC-BT-14?",
                    opts = listOf(
                        "Conductores aislados en el interior de tubos empotrados",
                        "Conductores en bandeja perforada",
                        "Cables con cubierta de PVC en montaje aéreo",
                        "Cables aislados sin protección adicional"
                    ),
                    a = 0,
                    exp = "ITC-BT-14, punto 1: Se listan los sistemas, entre ellos conductores aislados en tubos empotrados.",
                    ref = "ITC-BT-14"
                ),
                Question(
                    q = "¿Qué característica debe incluir siempre la canalización de la línea general de alimentación?",
                    opts = listOf(
                        "El conductor de protección",
                        "Un cable de mando",
                        "Un conductor de comunicaciones",
                        "Un embarrado de tierra"
                    ),
                    a = 0,
                    exp = "ITC-BT-14, punto 1: Las canalizaciones incluirán en cualquier caso el conductor de protección.",
                    ref = "ITC-BT-14"
                ),
                Question(
                    q = "¿Por dónde debe discurrir la línea general de alimentación?",
                    opts = listOf(
                        "Por zonas de uso común",
                        "Por el interior de viviendas",
                        "Por patios interiores ventilados",
                        "Por falsos techos registrables"
                    ),
                    a = 0,
                    exp = "ITC-BT-14, punto 2: El trazado será lo más corto posible discurriendo por zonas de uso común.",
                    ref = "ITC-BT-14"
                ),
                Question(
                    q = "¿De qué depende el diámetro de los tubos que alojan la línea general de alimentación?",
                    opts = listOf(
                        "De la sección del cable a instalar",
                        "De la tensión asignada",
                        "De la longitud del trazado",
                        "Del número de plantas del edificio"
                    ),
                    a = 0,
                    exp = "ITC-BT-14, punto 2: Su diámetro será el indicado en la tabla 1 en función de la sección del cable.",
                    ref = "ITC-BT-14"
                ),
                Question(
                    q = "¿Qué ampliación mínima deben permitir otros tipos de canalización que no sean tubos?",
                    opts = listOf(
                        "Un 100% de ampliación de la sección de los conductores",
                        "Un 50% de ampliación",
                        "Un 25% adicional",
                        "Ninguna ampliación"
                    ),
                    a = 0,
                    exp = "ITC-BT-14, punto 2: Deben permitir la ampliación de la sección de los conductores en un 100%.",
                    ref = "ITC-BT-14"
                ),
                Question(
                    q = "¿Cómo deben ser las uniones de los tubos rígidos utilizados en la línea general de alimentación?",
                    opts = listOf(
                        "Roscadas o embutidas",
                        "Soldadas",
                        "Pegadas",
                        "Atornilladas"
                    ),
                    a = 0,
                    exp = "ITC-BT-14, punto 2: Las uniones serán roscadas o embutidas para impedir la separación.",
                    ref = "ITC-BT-14"
                ),
                Question(
                    q = "¿Qué dimensiones mínimas debe tener el conducto vertical para la LGA?",
                    opts = listOf(
                        "30 x 30 cm",
                        "20 x 20 cm",
                        "40 x 20 cm",
                        "50 x 30 cm"
                    ),
                    a = 0,
                    exp = "ITC-BT-14, punto 2: Las dimensiones mínimas serán de 30 x 30 cm.",
                    ref = "ITC-BT-14"
                ),
                Question(
                    q = "¿Qué resistencia al fuego deben tener las paredes del conducto vertical de la LGA?",
                    opts = listOf(
                        "RF 120",
                        "RF 30",
                        "RF 60",
                        "RF 90"
                    ),
                    a = 0,
                    exp = "ITC-BT-14, punto 2: Las paredes deben tener RF 120.",
                    ref = "ITC-BT-14"
                ),
                Question(
                    q = "¿Qué resistencia al fuego deben tener las tapas de registro del conducto de la LGA?",
                    opts = listOf(
                        "RF 30",
                        "RF 60",
                        "RF 15",
                        "RF 120"
                    ),
                    a = 0,
                    exp = "ITC-BT-14, punto 2: Las tapas de registro tendrán RF 30.",
                    ref = "ITC-BT-14"
                ),
                Question(
                    q = "¿Cuántos conductores de fase debe incluir la línea general de alimentación?",
                    opts = listOf(
                        "Tres de fase y uno de neutro",
                        "Dos de fase y uno de neutro",
                        "Una fase y un neutro",
                        "Tres fases sin neutro"
                    ),
                    a = 0,
                    exp = "ITC-BT-14, punto 3: Los conductores a utilizar serán tres de fase y uno de neutro.",
                    ref = "ITC-BT-14"
                ),
                Question(
                    q = "¿Cuál es la tensión asignada de los conductores utilizados en la LGA?",
                    opts = listOf(
                        "0,6/1 kV",
                        "450/750 V",
                        "1,5/3 kV",
                        "230/400 V"
                    ),
                    a = 0,
                    exp = "ITC-BT-14, punto 3: Su tensión asignada será 0,6/1 kV.",
                    ref = "ITC-BT-14"
                ),
                Question(
                    q = "¿Qué norma deben cumplir las canalizaciones prefabricadas utilizadas?",
                    opts = listOf(
                        "UNE-EN 60.439-2",
                        "UNE 21.123",
                        "UNE 20.460",
                        "NBE-CPI-96"
                    ),
                    a = 0,
                    exp = "ITC-BT-14, punto 1: Las canalizaciones prefabricadas deben cumplir UNE-EN 60.439-2.",
                    ref = "ITC-BT-14"
                ),
                Question(
                    q = "¿Cuál es la sección mínima permitida para conductores de cobre en la LGA?",
                    opts = listOf(
                        "10 mm²",
                        "6 mm²",
                        "16 mm²",
                        "25 mm²"
                    ),
                    a = 0,
                    exp = "ITC-BT-14, punto 3: Sección mínima: 10 mm² en cobre.",
                    ref = "ITC-BT-14"
                ),
                Question(
                    q = "¿Cuál es la sección mínima permitida para conductores de aluminio en la LGA?",
                    opts = listOf(
                        "16 mm²",
                        "10 mm²",
                        "25 mm²",
                        "35 mm²"
                    ),
                    a = 0,
                    exp = "ITC-BT-14, punto 3: Sección mínima: 16 mm² en aluminio.",
                    ref = "ITC-BT-14"
                ),
                Question(
                    q = "¿Cuál es la caída de tensión máxima permitida para una LGA destinada a contadores totalmente centralizados?",
                    opts = listOf(
                        "0,50%",
                        "1%",
                        "1,50%",
                        "2%"
                    ),
                    a = 0,
                    exp = "ITC-BT-14, punto 3: Máxima caída: 0,5% para contadores totalmente centralizados.",
                    ref = "ITC-BT-14"
                ),
                Question(
                    q = "¿Cuál es la caída de tensión máxima permitida para centralizaciones parciales?",
                    opts = listOf(
                        "1%",
                        "0,50%",
                        "1,50%",
                        "2%"
                    ),
                    a = 0,
                    exp = "ITC-BT-14, punto 3: Máxima caída permitida: 1% para centralizaciones parciales.",
                    ref = "ITC-BT-14"
                ),
                Question(
                    q = "Según la ITC-BT-14, ¿qué debe considerarse para el cálculo de la sección de los cables?",
                    opts = listOf(
                        "La máxima caída de tensión y la intensidad máxima admisible",
                        "La longitud total de la línea",
                        "El tipo de edificio",
                        "La potencia contratada por el usuario"
                    ),
                    a = 0,
                    exp = "ITC-BT-14, punto 3: Para el cálculo se tendrá en cuenta caída de tensión e intensidad admisible.",
                    ref = "ITC-BT-14"
                ),
                Question(
                    q = "¿Cuál debe ser la sección del conductor neutro según la ITC-BT-14?",
                    opts = listOf(
                        "Aproximadamente el 50% de la de fase sin ser inferior a los valores de la tabla 1",
                        "Igual sección que fase",
                        "El doble que la fase",
                        "Una sección simbólica de 6 mm²"
                    ),
                    a = 0,
                    exp = "ITC-BT-14, punto 3: El neutro tendrá un 50% de la fase, no inferior a tabla 1.",
                    ref = "ITC-BT-14"
                ),
                Question(
                    q = "¿Cuál es el diámetro exterior del tubo para una sección de fase de 50 mm² y neutro de 25 mm² según la Tabla 1?",
                    opts = listOf(
                        "125 mm",
                        "110 mm",
                        "140 mm",
                        "160 mm"
                    ),
                    a = 0,
                    exp = "ITC-BT-14, Tabla 1: Fase 50 mm² / Neutro 25 mm² → Tubo 125 mm.",
                    ref = "ITC-BT-14"
                ),
                Question(
                    q = "Según el REBT, las derivaciones individuales comienzan en:",
                    opts = listOf(
                        "El embarrado general",
                        "La caja general de protección",
                        "El cuadro general de mando del usuario",
                        "El contador de energía"
                    ),
                    a = 0,
                    exp = "ITC-BT-15, punto 1: la derivación individual se inicia en el embarrado general.",
                    ref = "ITC-BT-15"
                ),
                Question(
                    q = "Según el REBT, las derivaciones individuales deben incluir siempre:",
                    opts = listOf(
                        "El conductor de protección",
                        "Un interruptor magnetotérmico general",
                        "Cableado exclusivo de alumbrado",
                        "Un tubo de reserva por planta"
                    ),
                    a = 0,
                    exp = "ITC-BT-15, punto 1: las canalizaciones incluirán, en cualquier caso, el conductor de protección.",
                    ref = "ITC-BT-15"
                ),
                Question(
                    q = "Según el REBT, cada derivación individual debe ser:",
                    opts = listOf(
                        "Totalmente independiente de las de otros usuarios",
                        "Instalada dentro de la vivienda",
                        "Compartida entre dos locales si no excede 5 kW",
                        "Instalada sin tubo protector si discurre enterrada"
                    ),
                    a = 0,
                    exp = "ITC-BT-15, punto 1: cada derivación individual será totalmente independiente de las correspondientes a otros usuarios.",
                    ref = "ITC-BT-15"
                ),
                Question(
                    q = "Según el REBT, los diámetros exteriores nominales mínimos de los tubos para derivaciones individuales serán:",
                    opts = listOf(
                        "25 mm",
                        "32 mm",
                        "40 mm",
                        "50 mm"
                    ),
                    a = 1,
                    exp = "ITC-BT-15, punto 2: los tubos tendrán un diámetro exterior nominal mínimo de 32 mm.",
                    ref = "ITC-BT-15"
                ),
                Question(
                    q = "Según el REBT, los tubos y canales para derivaciones individuales deberán permitir ampliar la sección de los conductores inicialmente instalados en un:",
                    opts = listOf(
                        "25%",
                        "50%",
                        "75%",
                        "100%"
                    ),
                    a = 3,
                    exp = "ITC-BT-15, punto 2: los tubos y canales deberán permitir ampliar en un 100% la sección de los conductores.",
                    ref = "ITC-BT-15"
                ),
                Question(
                    q = "Según el REBT, cuando se agrupen dos o más derivaciones individuales, podrán ser tendidas simultáneamente:",
                    opts = listOf(
                        "En el mismo canal protector mediante cable con cubierta",
                        "En un tubo rígido sin cubrir",
                        "En bandejas abiertas",
                        "En molduras sin tapa"
                    ),
                    a = 0,
                    exp = "ITC-BT-15, punto 2: la agrupación podrá realizarse en un canal protector mediante cable con cubierta.",
                    ref = "ITC-BT-15"
                ),
                Question(
                    q = "Según el REBT, se dispondrá un tubo de reserva en derivaciones individuales por cada:",
                    opts = listOf(
                        "5 derivaciones",
                        "8 derivaciones",
                        "10 derivaciones",
                        "15 derivaciones"
                    ),
                    a = 2,
                    exp = "ITC-BT-15, punto 2: se instalará un tubo de reserva por cada diez derivaciones individuales o fracción.",
                    ref = "ITC-BT-15"
                ),
                Question(
                    q = "Según el REBT, las derivaciones individuales discurrirán por lugares de uso común en:",
                    opts = listOf(
                        "Edificios destinados principalmente a viviendas",
                        "Locales mojados",
                        "Garajes de más de 25 plazas",
                        "Zonas de almacenamiento"
                    ),
                    a = 0,
                    exp = "ITC-BT-15, punto 2: en edificios destinados principalmente a viviendas, las derivaciones deberán discurrir por lugares de uso común.",
                    ref = "ITC-BT-15"
                ),
                Question(
                    q = "Según el REBT, cuando las derivaciones individuales discurran verticalmente deberán alojarse:",
                    opts = listOf(
                        "En una canaladura o conducto de obra de fábrica RF 120",
                        "En tubos metálicos flexibles",
                        "En bandejas perforadas",
                        "En falsos techos registrables"
                    ),
                    a = 0,
                    exp = "ITC-BT-15, punto 2: Cuando las derivaciones individuales discurran verticalmente se alojarán en el interior de una canaladura o conducto de obra de fábrica con paredes de resistencia al fuego RF 120, preparado única y exclusivamente para este fin.",
                    ref = "ITC-BT-15"
                ),
                Question(
                    q = "Según el REBT, las tapas de registro de la canaladura tendrán una resistencia al fuego mínima de:",
                    opts = listOf(
                        "RF 15",
                        "RF 30",
                        "RF 60",
                        "RF 120"
                    ),
                    a = 1,
                    exp = "ITC-BT-15, punto 2: las tapas de registro deberán tener resistencia al fuego mínima RF 30.",
                    ref = "ITC-BT-15"
                ),
                Question(
                    q = "Según el REBT, deberán colocarse elementos cortafuegos cada:",
                    opts = listOf(
                        "Dos plantas",
                        "Tres plantas",
                        "Cinco plantas",
                        "Diez metros"
                    ),
                    a = 1,
                    exp = "ITC-BT-15, punto 2: se dispondrán elementos cortafuegos, como mínimo, cada tres plantas.",
                    ref = "ITC-BT-15"
                ),
                Question(
                    q = "Según el REBT, la altura mínima de las tapas de registro será de:",
                    opts = listOf(
                        "0,10 m",
                        "0,20 m",
                        "0,30 m",
                        "0,50 m"
                    ),
                    a = 2,
                    exp = "ITC-BT-15, punto 2: la altura mínima de las tapas de registro será de 0,30 m.",
                    ref = "ITC-BT-15"
                ),
                Question(
                    q = "Según el REBT, las cajas de registro colocadas cada 15 m deberán ser:",
                    opts = listOf(
                        "Precintables y sin empalmes en su interior",
                        "De acero galvanizado",
                        "Ventiladas",
                        "De gran capacidad para bobinar cable"
                    ),
                    a = 0,
                    exp = "ITC-BT-15, punto 2: Con objeto de facilitar la instalación, cada 15 m se podrán colocar cajas de registro precintables, comunes a todos los tubos de derivación individual, en las que no se realizarán empalmes de conductores.",
                    ref = "ITC-BT-15"
                ),
                Question(
                    q = "Según el REBT, el hilo de mando incluido en cada derivación individual será de color:",
                    opts = listOf(
                        "Negro",
                        "Azul",
                        "Rojo",
                        "Verde-amarillo"
                    ),
                    a = 2,
                    exp = "ITC-BT-15, punto 3: el hilo de mando será de color rojo y sección mínima 1,5 mm².",
                    ref = "ITC-BT-15"
                ),
                Question(
                    q = "Según el REBT, no se admite en ningún caso el empleo de:",
                    opts = listOf(
                        "Conductor neutro común",
                        "Conductor de protección individual",
                        "Tubos empotrados",
                        "Canalizaciones prefabricadas UNE-EN 60439-2"
                    ),
                    a = 0,
                    exp = "ITC-BT-15, punto 3: no se admite conductor neutro común ni conductor de protección común para distintos suministros.",
                    ref = "ITC-BT-15"
                ),
                Question(
                    q = "Según el REBT, la sección mínima para los conductores polares, neutro y protección en derivaciones individuales será de:",
                    opts = listOf(
                        "2,5 mm²",
                        "4 mm²",
                        "6 mm²",
                        "10 mm²"
                    ),
                    a = 2,
                    exp = "ITC-BT-15, punto 3: la sección mínima será de 6 mm² para conductores polares, neutro y protección.",
                    ref = "ITC-BT-15"
                ),
                Question(
                    q = "Según el REBT, los cables utilizados deberán ser:",
                    opts = listOf(
                        "No propagadores del incendio y con baja emisión de humos",
                        "De PVC convencional sin requisitos adicionales",
                        "Sin cubierta para facilitar el enfriamiento",
                        "De tensión asignada mínima 0,3/0,5 kV"
                    ),
                    a = 0,
                    exp = "ITC-BT-15, punto 3: los cables deberán ser no propagadores del incendio y con humos y opacidad reducida.",
                    ref = "ITC-BT-15"
                ),
                Question(
                    q = "Según el REBT, los cables deberán instalarse de forma que no se reduzca:",
                    opts = listOf(
                        "La seguridad contra incendios del edificio",
                        "La intensidad máxima admisible",
                        "La capacidad de ventilación del conducto",
                        "El número de derivaciones individuales"
                    ),
                    a = 0,
                    exp = "ITC-BT-15, punto 3: los cables deben instalarse sin reducir la seguridad contra incendios del edificio.",
                    ref = "ITC-BT-15"
                ),
                Question(
                    q = "Según el REBT, la caída de tensión máxima admisible para derivaciones con contadores totalmente concentrados será:",
                    opts = listOf(
                        "0,50%",
                        "1%",
                        "1,50%",
                        "2%"
                    ),
                    a = 1,
                    exp = "ITC-BT-15, punto 3: caída de tensión máxima admisible del 1%.",
                    ref = "ITC-BT-15"
                ),
                Question(
                    q = "Según el REBT, la caída de tensión máxima admisible para derivaciones individuales sin línea general de alimentación será:",
                    opts = listOf(
                        "0,50%",
                        "1%",
                        "1,50%",
                        "2%"
                    ),
                    a = 2,
                    exp = "ITC-BT-15, punto 3 b: en suministros a un único usuario sin LGA, la caída máxima es 1,5%.",
                    ref = "ITC-BT-15"
                ),
                Question(
                    q = "Según el REBT, los contadores y dispositivos de medida pueden ubicarse en:",
                    opts = listOf(
                        "Módulos, paneles o armarios",
                        "Cualquier superficie accesible del edificio",
                        "Solo en locales técnicos",
                        "Únicamente en armarios metálicos"
                    ),
                    a = 0,
                    exp = "ITC-BT-16, apartado 1: se indica que pueden ubicarse en módulos, paneles o armarios.",
                    ref = "ITC-BT-16"
                ),
                Question(
                    q = "Según el REBT, el grado de protección mínimo para instalaciones de medida en interior es:",
                    opts = listOf(
                        "IP20; IK07",
                        "IP40; IK09",
                        "IP55; IK10",
                        "IP30; IK08"
                    ),
                    a = 1,
                    exp = "ITC-BT-16, apartado 1: establece IP40; IK09 para instalaciones interiores.",
                    ref = "ITC-BT-16"
                ),
                Question(
                    q = "Según el REBT, las partes transparentes de los módulos o armarios destinados a contadores deben ser:",
                    opts = listOf(
                        "Desmontables",
                        "Resistentes a los rayos ultravioleta",
                        "Tintadas para evitar deslumbramientos",
                        "De vidrio templado obligatorio"
                    ),
                    a = 1,
                    exp = "ITC-BT-16, apartado 1: se indica que deben ser resistentes a los rayos ultravioleta.",
                    ref = "ITC-BT-16"
                ),
                Question(
                    q = "Según el REBT, cada derivación individual debe llevar en su origen:",
                    opts = listOf(
                        "Un interruptor automático general",
                        "Su propia protección compuesta por fusibles de seguridad",
                        "Un interruptor diferencial independiente",
                        "Un seccionador bajo carga"
                    ),
                    a = 1,
                    exp = "ITC-BT-16, apartado 1: indica que cada derivación individual debe llevar su propia protección con fusibles de seguridad instalados antes del contador.",
                    ref = "ITC-BT-16"
                ),
                Question(
                    q = "Según el REBT, los cables de alimentación hacia el contador tendrán una sección mínima de:",
                    opts = listOf(
                        "4 mm²",
                        "6 mm²",
                        "10 mm²",
                        "2,5 mm²"
                    ),
                    a = 1,
                    exp = "ITC-BT-16, apartado 1: establece que los cables serán de 6 mm² salvo incumplimiento de previsión o caída de tensión.",
                    ref = "ITC-BT-16"
                ),
                Question(
                    q = "Según el REBT, la colocación individual de contadores se utiliza cuando:",
                    opts = listOf(
                        "Existen más de 16 contadores",
                        "Se trate de un suministro a un único usuario independiente o dos usuarios alimentados desde un mismo lugar",
                        "El edificio tenga más de 12 plantas",
                        "Los contadores se integren en un sistema de telegestión"
                    ),
                    a = 1,
                    exp = "ITC-BT-16, apartado 2.1: indica que esta disposición se utiliza en suministros a un único usuario o dos desde el mismo lugar.",
                    ref = "ITC-BT-16"
                ),
                Question(
                    q = "Según el REBT, en colocación individual se utilizará:",
                    opts = listOf(
                        "Un armario metálico independiente",
                        "Una Caja de Protección y Medida",
                        "Un cuadro de distribución normalizado",
                        "Un módulo doble de medida"
                    ),
                    a = 1,
                    exp = "ITC-BT-16, apartado 2.1: se hará uso de la Caja de Protección y Medida según ITC-BT-13.",
                    ref = "ITC-BT-16"
                ),
                Question(
                    q = "Según el REBT, en caso de medida indirecta en suministros industriales o comerciales:",
                    opts = listOf(
                        "El contador se instalará siempre en un armario metálico",
                        "La solución será la que especifiquen los requisitos particulares de la empresa suministradora",
                        "El usuario debe instalar su propio armario a elección",
                        "La medida deberá realizarse exclusivamente en local de contadores"
                    ),
                    a = 1,
                    exp = "ITC-BT-16, apartado 2.1: la solución se adopta según los requisitos particulares de la empresa suministradora.",
                    ref = "ITC-BT-16"
                ),
                Question(
                    q = "Según el REBT, la ubicación en local de contadores es obligatoria cuando el número de contadores es:",
                    opts = listOf(
                        "Mayor de 8",
                        "Mayor de 12",
                        "Mayor de 16",
                        "Mayor de 20"
                    ),
                    a = 2,
                    exp = "ITC-BT-16, apartado 2.2: si el número de contadores es superior a 16, su ubicación será en local.",
                    ref = "ITC-BT-16"
                ),
                Question(
                    q = "Según el REBT, en edificios de hasta 12 plantas la concentración de contadores se situará:",
                    opts = listOf(
                        "En cubierta",
                        "En cualquier planta",
                        "En planta baja, entresuelo o primer sótano",
                        "En plantas intermedias"
                    ),
                    a = 2,
                    exp = "ITC-BT-16, apartado 2.2: se indica esta ubicación para edificios de hasta 12 plantas.",
                    ref = "ITC-BT-16"
                ),
                Question(
                    q = "Según el REBT, podrán disponerse concentraciones por plantas cuando:",
                    opts = listOf(
                        "El edificio tenga más de 6 plantas",
                        "No exista espacio en planta baja",
                        "El número de contadores en cada concentración sea superior a 16",
                        "El edificio tenga suministro monofásico"
                    ),
                    a = 2,
                    exp = "ITC-BT-16, apartado 2.2: se permite concentración por plantas si cada una supera los 16 contadores.",
                    ref = "ITC-BT-16"
                ),
                Question(
                    q = "Según el REBT, el local destinado a contadores debe situarse preferentemente:",
                    opts = listOf(
                        "En cualquier planta del edificio",
                        "En planta baja, entresuelo o primer sótano",
                        "En el último piso",
                        "En zonas privadas del usuario"
                    ),
                    a = 1,
                    exp = "ITC-BT-16, apartado 2.2.1: el local estará situado en estas ubicaciones salvo concentraciones por plantas.",
                    ref = "ITC-BT-16"
                ),
                Question(
                    q = "Según el REBT, el local de contadores debe contar con una altura mínima de:",
                    opts = listOf(
                        "2,00 m",
                        "2,10 m",
                        "2,30 m",
                        "2,50 m"
                    ),
                    a = 2,
                    exp = "ITC-BT-16, apartado 2.2.1: altura mínima 2,30 m.",
                    ref = "ITC-BT-16"
                ),
                Question(
                    q = "Según el REBT, la puerta del local de contadores debe:",
                    opts = listOf(
                        "Abrir hacia el interior",
                        "Medir como mínimo 0,70 x 2 m",
                        "Ser blindada obligatoriamente",
                        "No requerir cerradura especial"
                    ),
                    a = 1,
                    exp = "ITC-BT-16, apartado 2.2.1: la puerta de acceso abrirá hacia el exterior y tendrá una dimensión mínima de 0,70 x 2 m.",
                    ref = "ITC-BT-16"
                ),
                Question(
                    q = "Según el REBT, cuando el número de contadores es igual o inferior a 16, la concentración puede ubicarse:",
                    opts = listOf(
                        "Solo en local",
                        "En local o en armario",
                        "En cualquier habitación del edificio",
                        "En el interior de viviendas"
                    ),
                    a = 1,
                    exp = "ITC-BT-16, apartado 2.2.2: permite ubicación en armario si el número de contadores ≤ 16.",
                    ref = "ITC-BT-16"
                ),
                Question(
                    q = "Según el REBT, desde la parte más saliente del armario hasta la pared opuesta deberá existir un pasillo mínimo de:",
                    opts = listOf(
                        "0,80 m",
                        "1,00 m",
                        "1,20 m",
                        "1,50 m"
                    ),
                    a = 3,
                    exp = "ITC-BT-16, apartado 2.2.2: se establece pasillo mínimo de 1,5 m.",
                    ref = "ITC-BT-16"
                ),
                Question(
                    q = "Según el REBT, la altura mínima desde la parte inferior de la concentración de contadores al suelo será de:",
                    opts = listOf(
                        "0,10 m",
                        "0,20 m",
                        "0,25 m",
                        "0,30 m"
                    ),
                    a = 2,
                    exp = "ITC-BT-16, apartado 3: se indica una altura mínima de 0,25 m.",
                    ref = "ITC-BT-16"
                ),
                Question(
                    q = "Según el REBT, la altura máxima del cuadrante de lectura del contador más alto será:",
                    opts = listOf(
                        "1,60 m",
                        "1,70 m",
                        "1,80 m",
                        "2,00 m"
                    ),
                    a = 2,
                    exp = "ITC-BT-16, apartado 3: el cuadrante de lectura no debe superar 1,80 m.",
                    ref = "ITC-BT-16"
                ),
                Question(
                    q = "Según el REBT, la unidad funcional obligatoria para concentraciones de más de dos usuarios es:",
                    opts = listOf(
                        "La unidad funcional de mando",
                        "La unidad funcional de interruptor general de maniobra",
                        "La unidad funcional de telecomunicaciones",
                        "La unidad de bornes de protección"
                    ),
                    a = 1,
                    exp = "ITC-BT-16, apartado 3: obligatoria para concentraciones de más de dos usuarios.",
                    ref = "ITC-BT-16"
                ),
                Question(
                    q = "Según el REBT, en caso de discrepancia sobre la elección del sistema de instalación resolverá:",
                    opts = listOf(
                        "La empresa instaladora",
                        "El proveedor del contador",
                        "El Organismo Competente de la Administración",
                        "El ayuntamiento del municipio"
                    ),
                    a = 2,
                    exp = "ITC-BT-16, apartado 4: resolverá el organismo competente.",
                    ref = "ITC-BT-16"
                ),
                Question(
                    q = "Según el REBT, la caja del interruptor de control de potencia debe colocarse:",
                    opts = listOf(
                        "Después de los dispositivos generales de mando y protección",
                        "Inmediatamente antes de los dispositivos de mando y protección",
                        "En cualquier punto del cuadro",
                        "En la parte superior del cuadro"
                    ),
                    a = 1,
                    exp = "ITC-BT-17, apartado 1.1: se colocará inmediatamente antes de los demás dispositivos.",
                    ref = "ITC-BT-17"
                ),
                Question(
                    q = "Según el REBT, en viviendas los dispositivos generales de mando y protección deben situarse:",
                    opts = listOf(
                        "En el salón",
                        "En la cocina",
                        "Junto a la puerta de entrada",
                        "En el baño"
                    ),
                    a = 2,
                    exp = "ITC-BT-17, apartado 1.1: en viviendas deberá preverse su situación junto a la puerta de entrada.",
                    ref = "ITC-BT-17"
                ),
                Question(
                    q = "Según el REBT, los dispositivos generales no podrán situarse en:",
                    opts = listOf(
                        "Dormitorios",
                        "Cocinas",
                        "Trasteros",
                        "Galerías"
                    ),
                    a = 0,
                    exp = "ITC-BT-17, apartado 1.1: En viviendas, deberá preverse la situación de los dispositivos generales de mando y protección junto a la puerta de entrada y no podrá colocarse en dormitorios, baños, aseos, etc.",
                    ref = "ITC-BT-17"
                ),
                Question(
                    q = "Según el REBT, en locales industriales o comerciales los dispositivos generales deben situarse:",
                    opts = listOf(
                        "Junto al cuadro de contadores",
                        "Lo más próximo posible a una puerta de entrada",
                        "En el fondo del local",
                        "En el interior de un armario cerrado"
                    ),
                    a = 1,
                    exp = "ITC-BT-17, apartado 1.1: deberán situarse lo más próximo posible a una puerta de entrada.",
                    ref = "ITC-BT-17"
                ),
                Question(
                    q = "Según el REBT, los dispositivos individuales de mando y protección de cada circuito pueden instalarse:",
                    opts = listOf(
                        "Solo en el mismo cuadro general",
                        "En cualquier lugar, incluidos pasillos públicos",
                        "En cuadros separados y en otros lugares",
                        "Únicamente junto a la puerta de entrada"
                    ),
                    a = 2,
                    exp = "ITC-BT-17, apartado 1.1: podrán instalarse en cuadros separados y en otros lugares.",
                    ref = "ITC-BT-17"
                ),
                Question(
                    q = "Según el REBT, la altura de instalación de los dispositivos generales en viviendas debe estar entre:",
                    opts = listOf(
                        "0,5 y 1 m",
                        "1 y 1,4 m",
                        "1,4 y 2 m",
                        "2 y 2,5 m"
                    ),
                    a = 2,
                    exp = "ITC-BT-17, apartado 1.1: la altura estará entre 1,4 y 2 m.",
                    ref = "ITC-BT-17"
                ),
                Question(
                    q = "Según el REBT, en locales comerciales la altura mínima para instalar los dispositivos generales es:",
                    opts = listOf(
                        "0,8 m",
                        "1 m",
                        "1,4 m",
                        "1,2 m"
                    ),
                    a = 1,
                    exp = "ITC-BT-17, apartado 1.1: en locales comerciales la altura mínima será 1 m.",
                    ref = "ITC-BT-17"
                ),
                Question(
                    q = "Según el REBT, los dispositivos de mando y protección en lugares de uso común deben:",
                    opts = listOf(
                        "Ser accesibles para mantenimiento público",
                        "Ser de libre acceso",
                        "No ser accesibles al público en general",
                        "Instalarse sin cerramiento"
                    ),
                    a = 2,
                    exp = "ITC-BT-17, apartado 1.1: deben tomarse precauciones para que no sean accesibles al público.",
                    ref = "ITC-BT-17"
                ),
                Question(
                    q = "Según el REBT, los cuadros donde se ubiquen los dispositivos deben instalarse:",
                    opts = listOf(
                        "En posición inclinada",
                        "En posición horizontal",
                        "En posición vertical",
                        "En posición opcional"
                    ),
                    a = 2,
                    exp = "ITC-BT-17, apartado 1.2: su posición de servicio será vertical.",
                    ref = "ITC-BT-17"
                ),
                Question(
                    q = "Según el REBT, la envolvente de los cuadros debe cumplir un grado de protección mínimo:",
                    opts = listOf(
                        "IP20; IK05",
                        "IP30; IK07",
                        "IP44; IK08",
                        "IP55; IK10"
                    ),
                    a = 1,
                    exp = "ITC-BT-17, apartado 1.2: grado mínimo IP30 e IK07.",
                    ref = "ITC-BT-17"
                ),
                Question(
                    q = "Según el REBT, la envolvente del interruptor de control de potencia debe ser:",
                    opts = listOf(
                        "Metálica y ventilada",
                        "Precintable y del modelo oficialmente aprobado",
                        "De acceso libre",
                        "Subterránea o empotrada"
                    ),
                    a = 1,
                    exp = "ITC-BT-17, apartado 1.2: la envolvente del ICP será precintable y de modelo oficialmente aprobado.",
                    ref = "ITC-BT-17"
                ),
                Question(
                    q = "Según el REBT, entre los dispositivos generales mínimos se incluye:",
                    opts = listOf(
                        "Un seccionador unipolar",
                        "Un interruptor general automático omnipolar",
                        "Un interruptor magnetotérmico unipolar",
                        "Un interruptor de corte visible"
                    ),
                    a = 1,
                    exp = "ITC-BT-17, apartado 1.2: debe existir un interruptor general automático omnipolar.",
                    ref = "ITC-BT-17"
                ),
                Question(
                    q = "Según el REBT, el interruptor diferencial general protege contra:",
                    opts = listOf(
                        "Sobrecargas",
                        "Sobretensiones",
                        "Contactos indirectos",
                        "Cortocircuitos"
                    ),
                    a = 2,
                    exp = "ITC-BT-17, apartado 1.2: el diferencial general es para protección contra contactos indirectos.",
                    ref = "ITC-BT-17"
                ),
                Question(
                    q = "Según el REBT, los dispositivos de protección de cada circuito interior deben ser:",
                    opts = listOf(
                        "Unipolares",
                        "Bipolares únicamente",
                        "De corte omnipolar",
                        "Siempre diferenciales"
                    ),
                    a = 2,
                    exp = "ITC-BT-17, apartado 1.2: los dispositivos de sobrecarga y cortocircuito serán de corte omnipolar.",
                    ref = "ITC-BT-17"
                ),
                Question(
                    q = "Según el REBT, si existen varios interruptores diferenciales en serie, debe garantizarse:",
                    opts = listOf(
                        "Que tengan la misma sensibilidad",
                        "La selectividad entre ellos",
                        "Que se disparen simultáneamente",
                        "Que estén en cuadros diferentes"
                    ),
                    a = 1,
                    exp = "ITC-BT-17, apartado 1.2: En el caso de que se instale más de un interruptor diferencial en serie, existirá una selectividad entre ellos.",
                    ref = "ITC-BT-17"
                ),
                Question(
                    q = "Según el REBT, el interruptor general automático debe tener un poder de corte mínimo de:",
                    opts = listOf(
                        "3.000 A",
                        "4.500 A",
                        "6.000 A",
                        "10.000 A"
                    ),
                    a = 1,
                    exp = "ITC-BT-17, apartado 1.3: se fija un mínimo de 4.500 A.",
                    ref = "ITC-BT-17"
                ),
                Question(
                    q = "Según el REBT, la sensibilidad de los interruptores diferenciales debe cumplir con:",
                    opts = listOf(
                        "ITC-BT-10",
                        "ITC-BT-15",
                        "ITC-BT-24",
                        "ITC-BT-27"
                    ),
                    a = 2,
                    exp = "ITC-BT-17, apartado 1.3: sensibilidad según lo señalado en ITC-BT-24.",
                    ref = "ITC-BT-17"
                ),
                Question(
                    q = "Según el REBT, los dispositivos de protección contra sobrecargas y cortocircuitos deben tener sus polos:",
                    opts = listOf(
                        "Protegidos solo en fase",
                        "Protegidos según el número de fases del circuito",
                        "Sin protección mecánica",
                        "Únicamente protegidos en neutro"
                    ),
                    a = 1,
                    exp = "ITC-BT-17, apartado 1.3: tendrán los polos protegidos que correspondan al número de fases.",
                    ref = "ITC-BT-17"
                ),
                Question(
                    q = "Según el REBT, las características de interrupción de los dispositivos deben adecuarse a:",
                    opts = listOf(
                        "La sección del cuadro general",
                        "Las corrientes admisibles de los conductores del circuito que protegen",
                        "La previsión de cargas de la vivienda",
                        "El tipo de interruptor diferencial instalado"
                    ),
                    a = 1,
                    exp = "ITC-BT-17, apartado 1.3: deben estar de acuerdo con las corrientes admisibles de los conductores.",
                    ref = "ITC-BT-17"
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
,
                Question(
                    q = "Según el REBT, la caída de tensión máxima admisible en circuitos interiores de viviendas es:",
                    opts = listOf(
                        "3 % de la tensión nominal",
                        "5 % de la tensión nominal",
                        "1,5 % de la tensión nominal",
                        "2 % de la tensión nominal"
                    ),
                    a = 0,
                    exp = "ITC-BT-19, punto 2.2.2: para viviendas, la caída de tensión debe ser menor del 3 %.",
                    ref = "ITC-BT-19"
                ),
                Question(
                    q = "Según el REBT, en instalaciones interiores industriales alimentadas desde un transformador propio, la caída de tensión máxima admisible para alumbrado es:",
                    opts = listOf(
                        "4,5 %",
                        "3 %",
                        "5 %",
                        "6,5 %"
                    ),
                    a = 0,
                    exp = "ITC-BT-19, punto 2.2.2: caída de tensión admisible 4,5 % para alumbrado en este caso.",
                    ref = "ITC-BT-19"
                ),
                Question(
                    q = "Según el REBT, ¿con qué color se identifica el conductor neutro?",
                    opts = listOf(
                        "Azul claro",
                        "Verde-amarillo",
                        "Marrón",
                        "Gris"
                    ),
                    a = 0,
                    exp = "ITC-BT-19, punto 2.2.4: el conductor neutro debe identificarse por el color azul claro.",
                    ref = "ITC-BT-19"
                ),
                Question(
                    q = "Según el REBT, el conductor de protección debe identificarse por el color:",
                    opts = listOf(
                        "Verde-amarillo",
                        "Azul claro",
                        "Marrón o negro",
                        "Gris"
                    ),
                    a = 0,
                    exp = "ITC-BT-19, punto 2.2.4: el conductor de protección se le identificará por el color verde-amarillo.",
                    ref = "ITC-BT-19"
                ),
                Question(
                    q = "Según el REBT, la sección mínima del conductor de protección para S ≤ 16 mm² es:",
                    opts = listOf(
                        "Igual a la sección del conductor de fase",
                        "16 mm²",
                        "La mitad de la sección del conductor de fase",
                        "2,5 mm² siempre"
                    ),
                    a = 0,
                    exp = "ITC-BT-19, punto 2.3 y tabla 2: para S ≤ 16 mm², Sp = S.",
                    ref = "ITC-BT-19"
                ),
                Question(
                    q = "Según el REBT, las instalaciones deben subdividirse con el fin de:",
                    opts = listOf(
                        "Limitar las consecuencias de un fallo",
                        "Reducir la potencia demandada",
                        "Evitar la necesidad de protecciones",
                        "Permitir la utilización de conductores más pequeños"
                    ),
                    a = 0,
                    exp = "ITC-BT-19, punto 2.4: se subdividen para evitar interrupciones innecesarias y limitar fallos.",
                    ref = "ITC-BT-19"
                ),
                Question(
                    q = "Según el REBT, la carga de los conductores debe mantenerse:",
                    opts = listOf(
                        "Lo más equilibrada posible",
                        "Al máximo valor permitido",
                        "Solo en una fase",
                        "Asignada a la fase neutra"
                    ),
                    a = 0,
                    exp = "ITC-BT-19, punto 2.5: se procurará un reparto equilibrado entre fases.",
                    ref = "ITC-BT-19"
                ),
                Question(
                    q = "Según el REBT, ¿qué dispositivos pueden emplearse para separar una instalación de su alimentación?",
                    opts = listOf(
                        "Cortacircuitos fusibles, seccionadores e interruptores con separación de contactos",
                        "Solo interruptores automáticos",
                        "Solo seccionadores bajo carga",
                        "Solo fusibles"
                    ),
                    a = 0,
                    exp = "ITC-BT-19, punto 2.6: lista de dispositivos admitidos para separación de la alimentación.",
                    ref = "ITC-BT-19"
                ),
                Question(
                    q = "Según el REBT, los dispositivos para conectar y desconectar en carga podrán ser:",
                    opts = listOf(
                        "Interruptores manuales, fusibles de accionamiento manual o clavijas hasta 16 A",
                        "Solo interruptores automáticos",
                        "Cualquier elemento mecánico",
                        "Únicamente clavijas industriales"
                    ),
                    a = 0,
                    exp = "ITC-BT-19, punto 2.7: lista de dispositivos válidos para conectar y desconectar en carga.",
                    ref = "ITC-BT-19"
                ),
                Question(
                    q = "Según el REBT, los dispositivos situados en el cuadro general deben ser:",
                    opts = listOf(
                        "De corte omnipolar",
                        "De corte unipolar",
                        "De corte solo en fase",
                        "De corte solo en neutro"
                    ),
                    a = 0,
                    exp = "ITC-BT-19, punto 2.7: deben ser de corte omnipolar los dispositivos del cuadro general.",
                    ref = "ITC-BT-19"
                ),
                Question(
                    q = "Según el REBT, las instalaciones deben protegerse contra contactos directos e indirectos aplicando:",
                    opts = listOf(
                        "Las medidas de la ITC-BT-24",
                        "Normas UNE únicamente",
                        "El criterio del instalador",
                        "Interruptores diferenciales"
                    ),
                    a = 0,
                    exp = "ITC-BT-19, punto 2.8: se deben aplicar las medidas de protección de la ITC-BT-24.",
                    ref = "ITC-BT-19"
                ),
                Question(
                    q = "Según el REBT, la resistencia mínima de aislamiento para instalaciones con tensión nominal ≤ 500 V es:",
                    opts = listOf(
                        "≥ 0,5 MΩ",
                        "≥ 0,25 MΩ",
                        "≥ 1 MΩ",
                        "≥ 2 MΩ"
                    ),
                    a = 0,
                    exp = "ITC-BT-19, punto 2.9 y tabla 3: para ≤ 500 V, resistencia mínima ≥ 0,5 MΩ.",
                    ref = "ITC-BT-19"
                ),
                Question(
                    q = "Según el REBT, ¿qué debe hacerse cuando la longitud total de la instalación supera los 100 metros para verificar correctamente la resistencia de aislamiento?",
                    opts = listOf(
                        "Fraccionar la instalación en partes de aproximadamente 100 metros",
                        "Aumentar la tensión de ensayo al doble",
                        "Desconectar todos los dispositivos de protección",
                        "Realizar la medida únicamente entre fases"
                    ),
                    a = 0,
                    exp = "ITC-BT-19, punto 2.9: si la instalación excede de 100 metros debe fraccionarse para comprobar que cada tramo cumple la resistencia mínima exigida.",
                    ref = "ITC-BT-19"
                ),
                Question(
                    q = "Según el REBT, la rigidez dieléctrica debe permitir resistir durante 1 minuto una tensión de:",
                    opts = listOf(
                        "2U + 1000 V, con mínimo 1.500 V",
                        "1.000 V siempre",
                        "500 V siempre",
                        "U + 500 V"
                    ),
                    a = 0,
                    exp = "ITC-BT-19, punto 2.9: Por lo que respecta a la rigidez dieléctrica de una instalación, ha de ser tal, que desconectados los aparatos de utilización (receptores), resista durante 1 minuto una prueba de tensión de 2U + 1000 voltios a frecuencia industrial, siendo U la tensión máxima de servicio expresada en voltios y con un mínimo de 1.500 voltios.",
                    ref = "ITC-BT-19"
                ),
                Question(
                    q = "Según el REBT, las bases de toma de corriente en instalaciones interiores serán del tipo:",
                    opts = listOf(
                        "C2a, C3a o ESB 25-5a de UNE 20315",
                        "Cualquier tipo con toma de tierra",
                        "Solo tipo Schuko",
                        "Solo industriales IEC-309"
                    ),
                    a = 0,
                    exp = "ITC-BT-19, punto 2.10: Las bases de toma de corriente utilizadas en las instalaciones interiores o receptoras serán del tipo indicado en las figuras C2a, C3a o ESB 25-5a de la norma UNE 20315",
                    ref = "ITC-BT-19"
                ),
                Question(
                    q = "Según el REBT, las conexiones de conductores deben realizarse:",
                    opts = listOf(
                        "Con bornes o regletas; nunca por simple retorcimiento",
                        "Por retorcimiento si es temporal",
                        "Con cinta aislante únicamente",
                        "Por empalme directo sin caja"
                    ),
                    a = 0,
                    exp = "ITC-BT-19, punto 2.11: prohíbe el retorcimiento y exige bornes o regletas.",
                    ref = "ITC-BT-19"
                ),
                Question(
                    q = "Según el REBT, los conductores de protección deberán estar:",
                    opts = listOf(
                        "Protegidos contra deterioros mecánicos y químicos",
                        "Instalados siempre sin protección",
                        "Pintados de gris",
                        "Separados físicamente de la canalización"
                    ),
                    a = 0,
                    exp = "ITC-BT-19, punto 2.3: se exige protección frente a deterioros mecánicos y químicos.",
                    ref = "ITC-BT-19"
                ),
                Question(
                    q = "Según el REBT, cuando las canalizaciones incluyen conductores en tubos ferromagnéticos:",
                    opts = listOf(
                        "El conductor de protección debe colocarse en el mismo tubo",
                        "El conductor de protección puede ir por fuera",
                        "Debe eliminarse el neutro",
                        "Debe aumentarse la sección del neutro"
                    ),
                    a = 0,
                    exp = "ITC-BT-19, punto 2.3: el conductor de protección debe ir en el mismo tubo o cable.",
                    ref = "ITC-BT-19"
                ),
                Question(
                    q = "Según el REBT, en instalaciones interiores la sección del conductor neutro será:",
                    opts = listOf(
                        "Como mínimo igual a la de las fases",
                        "Siempre menor que la de fase",
                        "El doble de la sección de fase",
                        "La mitad que la fase"
                    ),
                    a = 0,
                    exp = "ITC-BT-19, punto 2.2.2: En instalaciones interiores, para tener en cuenta las corrientes armónicas debidas cargas no lineales y posibles desequilibrios, salvo justificación por cálculo, la sección del conductor neutro será como mínimo igual a la de las fases..",
                    ref = "ITC-BT-19"
                ),
                Question(
                    q = "Según el REBT, varios circuitos pueden alojarse en el mismo tubo siempre que:",
                    opts = listOf(
                        "Todos los conductores estén aislados para la tensión más elevada presente",
                        "Los conductores de fase sean del mismo color",
                        "La intensidad total no supere la del conductor mayor",
                        "Exista un conductor neutro común para todos los circuitos"
                    ),
                    a = 0,
                    exp = "ITC-BT-20, apartado 2.1 Prescripciones generales: pueden coexistir varios circuitos si todos los conductores están aislados para la tensión asignada más elevada.",
                    ref = "ITC-BT-20"
                ),
                Question(
                    q = "Según el REBT, los circuitos de potencia y los circuitos MBTS o MBTP podrán ir en la misma canalización solamente si:",
                    opts = listOf(
                        "Cada conductor está aislado para la tensión más alta presente",
                        "La canalización dispone de tapa metálica",
                        "Los cables cumplen UNE 20315",
                        "Se instalan en tubos enterrados"
                    ),
                    a = 0,
                    exp = "ITC-BT-20, apartado 2.1 Prescripciones generales: no deben instalarse juntos salvo si cada conductor está aislado para la tensión más alta presente o existe separación adecuada.",
                    ref = "ITC-BT-20"
                ),
                Question(
                    q = "Según el REBT, la separación mínima entre canalizaciones eléctricas y conductos no eléctricos debe ser de:",
                    opts = listOf(
                        "3 cm",
                        "5 cm",
                        "10 cm",
                        "1 cm"
                    ),
                    a = 0,
                    exp = "ITC-BT-20, apartado 2.1.1 Disposiciones: debe mantenerse una distancia mínima de 3 cm entre superficies exteriores.",
                    ref = "ITC-BT-20"
                ),
                Question(
                    q = "Según el REBT, las canalizaciones eléctricas no deben situarse por debajo de conductos susceptibles de producir condensaciones salvo que:",
                    opts = listOf(
                        "Se adopten medidas para protegerlas de dichas condensaciones",
                        "Los cables sean armados",
                        "La altura supere 2 metros",
                        "La canalización tenga grado IP4X"
                    ),
                    a = 0,
                    exp = "ITC-BT-20, apartado 2.1.1 Disposiciones: no se colocarán debajo de conductos con condensaciones salvo con protecciones adecuadas.",
                    ref = "ITC-BT-20"
                ),
                Question(
                    q = "Según el REBT, en un mismo canal o hueco podrán ir canalizaciones eléctricas junto a no eléctricas únicamente si:",
                    opts = listOf(
                        "Se cumplen simultáneamente todas las condiciones de protección contra contactos indirectos y riesgos adicionales",
                        "Los cables eléctricos son todos multipolares",
                        "La canalización no eléctrica es de PVC",
                        "La distancia entre ambas es superior a 10 cm"
                    ),
                    a = 0,
                    exp = "ITC-BT-20, apartado 2.1.1 Disposiciones: se admiten ambas canalizaciones solo si se cumplen conjuntamente condiciones a) y b).",
                    ref = "ITC-BT-20"
                ),
                Question(
                    q = "Según el REBT, la identificación de las canalizaciones debe permitir:",
                    opts = listOf(
                        "Reparaciones y transformaciones siempre que se garantice la correcta identificación de circuitos",
                        "Reconocer únicamente la fase",
                        "Localizar exclusivamente el neutro",
                        "Eliminar la necesidad de planos"
                    ),
                    a = 0,
                    exp = "ITC-BT-20, apartado 2.1.3 Identificación: deben permitir reparaciones y transformaciones mediante identificación clara.",
                    ref = "ITC-BT-20"
                ),
                Question(
                    q = "Según el REBT, en conductores aislados bajo tubo protector, la tensión asignada mínima del cable debe ser:",
                    opts = listOf(
                        "450/750 V",
                        "300/500 V",
                        "0,6/1 kV",
                        "125/250 V"
                    ),
                    a = 0,
                    exp = "ITC-BT-20, apartado 2.2.1: los cables serán de tensión asignada no inferior a 450/750 V.",
                    ref = "ITC-BT-20"
                ),
                Question(
                    q = "Según el REBT, en instalaciones con conductores fijados directamente sobre paredes, la distancia máxima entre puntos de fijación debe ser:",
                    opts = listOf(
                        "0,40 m",
                        "1 m",
                        "0,20 m",
                        "0,60 m"
                    ),
                    a = 0,
                    exp = "ITC-BT-20, apartado 2.2.2: la distancia entre dos puntos de fijación sucesivos no excederá de 0,40 m.",
                    ref = "ITC-BT-20"
                ),
                Question(
                    q = "Según el REBT, el radio de curvatura de un cable fijado sobre pared no será inferior a:",
                    opts = listOf(
                        "10 veces el diámetro exterior del cable",
                        "5 veces el diámetro",
                        "El doble del diámetro",
                        "20 veces el diámetro exterior"
                    ),
                    a = 0,
                    exp = "ITC-BT-20, apartado 2.2.2: el radio no será inferior a 10 veces el diámetro exterior.",
                    ref = "ITC-BT-20"
                ),
                Question(
                    q = "Según el REBT, los extremos de los cables instalados sobre paredes deberán ser:",
                    opts = listOf(
                        "Estancos cuando el local lo exija",
                        "Pintados para su identificación",
                        "Soldados en todos los casos",
                        "Cubiertos con resina"
                    ),
                    a = 0,
                    exp = "ITC-BT-20, apartado 2.2.2: los extremos serán estancos cuando las características del local lo exijan.",
                    ref = "ITC-BT-20"
                ),
                Question(
                    q = "Según el REBT, los conductores enterrados deberán ir bajo tubo salvo que:",
                    opts = listOf(
                        "Tengan cubierta y tensión asignada 0,6/1 kV",
                        "Sean cables armados",
                        "La zanja sea superior a 1 metro",
                        "Se utilicen bandejas metálicas"
                    ),
                    a = 0,
                    exp = "ITC-BT-20, apartado 2.2.3: deben ir bajo tubo salvo que posean cubierta y 0,6/1 kV.",
                    ref = "ITC-BT-20"
                ),
                Question(
                    q = "Según el REBT, los conductores empotrados en estructuras deben tener:",
                    opts = listOf(
                        "Cubierta y temperatura de servicio hasta 90 ºC",
                        "Cubierta metálica obligatoria",
                        "Aislamiento PVC exclusivamente",
                        "Protección por bandeja"
                    ),
                    a = 0,
                    exp = "ITC-BT-20, apartado 2.2.4: deben ser conductores aislados con cubierta, temperatura -5 ºC a 90 ºC.",
                    ref = "ITC-BT-20"
                ),
                Question(
                    q = "Según el REBT, los huecos de construcción utilizados para canalizaciones deben tener una sección mínima:",
                    opts = listOf(
                        "Cuatro veces la ocupada por los cables o tubos",
                        "Igual a la del conducto mayor",
                        "El doble del diámetro del tubo",
                        "Tres veces la sección del cable mayor"
                    ),
                    a = 0,
                    exp = "ITC-BT-20, apartado 2.2.6: sección mínima cuatro veces la ocupación.",
                    ref = "ITC-BT-20"
                ),
                Question(
                    q = "Según el REBT, la dimensión mínima del lado menor del hueco donde se instalan canalizaciones será:",
                    opts = listOf(
                        "Dos veces el diámetro exterior del cable mayor, con un mínimo de 20 mm",
                        "10 mm en todos los casos",
                        "Depende del fabricante",
                        "El mismo diámetro del tubo"
                    ),
                    a = 0,
                    exp = "ITC-BT-20, apartado 2.2.6 su dimensión más pequeña no será inferior a dos veces el diámetro exterior de mayor sección de éstos, con un mínimo de 20 milímetros.",
                    ref = "ITC-BT-20"
                ),
                Question(
                    q = "Según el REBT, en canales protectoras IP4X se permite:",
                    opts = listOf(
                        "Realizar empalmes y conexiones a los mecanismos",
                        "Usar conductores desnudos",
                        "Instalar varios circuitos MBTS y MT en el mismo compartimento",
                        "Colocar tuberías de agua junto a ellas"
                    ),
                    a = 0,
                    exp = "ITC-BT-20, apartado 2.2.7 (C) Realizar empalmes de conductores en su interior y conexiones a los mecanismos.",
                    ref = "ITC-BT-20"
                ),
                Question(
                    q = "Según el REBT, en molduras la anchura mínima de ranura para cables rígidos ≤ 6 mm² debe ser:",
                    opts = listOf(
                        "6 mm",
                        "10 mm",
                        "3 mm",
                        "12 mm"
                    ),
                    a = 0,
                    exp = "ITC-BT-20, apartado 2.2.8: anchura mínima de 6 mm.",
                    ref = "ITC-BT-20"
                ),
                Question(
                    q = "Según el REBT, las molduras no podrán:",
                    opts = listOf(
                        "Estar totalmente empotradas ni recubiertas",
                        "Ser colocadas a más de 2 metros",
                        "Utilizarse en locales secos",
                        "Contener más de un cable por ranura"
                    ),
                    a = 0,
                    exp = "ITC-BT-20, apartado 2.2.8: no estarán completamente empotradas ni recubiertas.",
                    ref = "ITC-BT-20"
                ),
                Question(
                    q = "Según el REBT, en bandejas solo podrán utilizarse:",
                    opts = listOf(
                        "Cables con cubierta",
                        "Conductores desnudos",
                        "Cables 300/500 V",
                        "Tubos flexibles"
                    ),
                    a = 0,
                    exp = "ITC-BT-20, apartado 2.2.9: únicamente cables con cubierta, incluyendo armados o aislamiento mineral.",
                    ref = "ITC-BT-20"
                ),
                Question(
                    q = "Según el REBT, al atravesar elementos constructivos, no se permite que existan:",
                    opts = listOf(
                        "Empalmes o derivaciones en toda la longitud del paso",
                        "Tubos metálicos",
                        "Cables armados",
                        "Cables unipolares"
                    ),
                    a = 0,
                    exp = "ITC-BT-20, apartado 3: está prohibido realizar empalmes o derivaciones dentro del paso.",
                    ref = "ITC-BT-20"
                ),
                Question(
                    q = "Según el REBT, cuando se atraviesa un techo mediante tubo, éste debe:",
                    opts = listOf(
                        "Estar obturado y sobresalir por encima del suelo al menos 10 cm",
                        "Ser metálico obligatoriamente",
                        "Ser flexible y sin fijación",
                        "Contener cables sin aislamiento"
                    ),
                    a = 0,
                    exp = "ITC-BT-20, apartado 3: los tubos en pasos de techo deben estar obturados y sobresalir 10 cm.",
                    ref = "ITC-BT-20"
                ),
                Question(
                    q = "Según el REBT, todo circuito deberá protegerse frente a:",
                    opts = listOf(
                        "Los efectos de las sobreintensidades previsibles",
                        "Únicamente los cortocircuitos",
                        "Solo las sobrecargas permanentes",
                        "Exclusivamente las descargas atmosféricas"
                    ),
                    a = 0,
                    exp = "ITC-BT-22, apartado 1.1: todo circuito estará protegido contra los efectos de las sobreintensidades que puedan presentarse.",
                    ref = "ITC-BT-22"
                ),
                Question(
                    q = "Según el REBT, la interrupción de un circuito para protegerlo frente a sobreintensidades se realizará:",
                    opts = listOf(
                        "En un tiempo conveniente o mediante un dimensionado adecuado",
                        "Siempre de forma instantánea",
                        "Solo mediante fusibles calibrados",
                        "Únicamente mediante interruptores diferenciales"
                    ),
                    a = 0,
                    exp = "ITC-BT-22, apartado 1.1.",
                    ref = "ITC-BT-22"
                ),
                Question(
                    q = "Según el REBT, ¿cuál de las siguientes NO se cita como causa de sobreintensidades?",
                    opts = listOf(
                        "Sobrecargas",
                        "Cortocircuitos",
                        "Descargas eléctricas atmosféricas",
                        "Sobretensiones permanentes"
                    ),
                    a = 3,
                    exp = "ITC-BT-22, apartado 1.1 Las sobreintensidades pueden estar motivadas por, Sobrecargas, Cortocircuitos y Descargas eléctricas atmosféricas",
                    ref = "ITC-BT-22"
                ),
                Question(
                    q = "Según el REBT, las sobrecargas pueden estar motivadas por:",
                    opts = listOf(
                        "Defectos de aislamiento de gran impedancia",
                        "Defectos de aislamiento de baja impedancia",
                        "Falta de puesta a tierra",
                        "Sobretensiones transitorias"
                    ),
                    a = 0,
                    exp = "ITC-BT-22, apartado 1.1 Sobrecargas debidas a los aparatos de utilización o defectos de aislamiento de gran impedancia.",
                    ref = "ITC-BT-22"
                ),
                Question(
                    q = "Según el REBT, en la protección contra sobrecargas debe garantizarse:",
                    opts = listOf(
                        "La intensidad admisible del conductor",
                        "La tensión máxima del circuito",
                        "La potencia nominal del receptor",
                        "La corriente diferencial residual"
                    ),
                    a = 0,
                    exp = "ITC-BT-22, apartado a) Protección contra sobrecargas.",
                    ref = "ITC-BT-22"
                ),
                Question(
                    q = "Según el REBT, el dispositivo de protección contra sobrecargas podrá estar constituido por:",
                    opts = listOf(
                        "Un interruptor automático omnipolar con curva térmica de corte",
                        "Un interruptor diferencial de alta sensibilidad",
                        "Un relé de vigilancia de tensión",
                        "Un protector contra sobretensiones"
                    ),
                    a = 0,
                    exp = "ITC-BT-22, apartado a).",
                    ref = "ITC-BT-22"
                ),
                Question(
                    q = "Según el REBT, también se admiten como protección contra sobrecargas:",
                    opts = listOf(
                        "Cortacircuitos fusibles calibrados",
                        "Interruptores diferenciales",
                        "Relés electrónicos",
                        "Contactores de maniobra"
                    ),
                    a = 0,
                    exp = "ITC-BT-22, apartado a).",
                    ref = "ITC-BT-22"
                ),
                Question(
                    q = "Según el REBT, en el origen de todo circuito debe establecerse protección contra:",
                    opts = listOf(
                        "Cortocircuitos",
                        "Sobretensiones",
                        "Contactos indirectos",
                        "Fugas a tierra"
                    ),
                    a = 0,
                    exp = "ITC-BT-22, apartado b) Protección contra cortocircuitos.",
                    ref = "ITC-BT-22"
                ),
                Question(
                    q = "Según el REBT, la capacidad de corte del dispositivo contra cortocircuitos deberá estar de acuerdo con:",
                    opts = listOf(
                        "La intensidad de cortocircuito en el punto de conexión",
                        "La potencia instalada",
                        "La corriente diferencial asignada",
                        "La sección del neutro"
                    ),
                    a = 0,
                    exp = "ITC-BT-22, apartado b).",
                    ref = "ITC-BT-22"
                ),
                Question(
                    q = "Según el REBT, en circuitos derivados de uno principal se admite que:",
                    opts = listOf(
                        "Un solo dispositivo general proteja contra cortocircuitos a todos los circuitos derivados",
                        "Cada circuito tenga su propio diferencial",
                        "No exista protección contra sobrecargas",
                        "La protección sea únicamente por fusibles"
                    ),
                    a = 0,
                    exp = "ITC-BT-22, apartado b).",
                    ref = "ITC-BT-22"
                ),
                Question(
                    q = "Según el REBT, en los circuitos derivados cada circuito deberá disponer de protección contra:",
                    opts = listOf(
                        "Sobrecargas",
                        "Cortocircuitos",
                        "Sobretensiones",
                        "Contactos directos"
                    ),
                    a = 0,
                    exp = "ITC-BT-22, apartado b).",
                    ref = "ITC-BT-22"
                ),
                Question(
                    q = "Según el REBT, se admiten como dispositivos de protección contra cortocircuitos:",
                    opts = listOf(
                        "Fusibles calibrados e interruptores automáticos omnipolares",
                        "Interruptores diferenciales",
                        "Relés térmicos",
                        "Contactores"
                    ),
                    a = 0,
                    exp = "ITC-BT-22, apartado b).",
                    ref = "ITC-BT-22"
                ),
                Question(
                    q = "Según el REBT, la norma UNE que recoge los aspectos sobre dispositivos de protección es:",
                    opts = listOf(
                        "UNE-HD 60.364-4-43",
                        "UNE 21.302",
                        "UNE 20.460-5-52",
                        "UNE 20.460-6-61"
                    ),
                    a = 0,
                    exp = "ITC-BT-22, referencia normativa.",
                    ref = "ITC-BT-22"
                ),
                Question(
                    q = "Según el REBT, el apartado 432 de la norma UNE citada trata sobre:",
                    opts = listOf(
                        "Naturaleza de los dispositivos de protección",
                        "Protección contra contactos indirectos",
                        "Coordinación de diferenciales",
                        "Limitación de sobretensiones"
                    ),
                    a = 0,
                    exp = "ITC-BT-22, referencia UNE.",
                    ref = "ITC-BT-22"
                ),
                Question(
                    q = "Según el REBT, el apartado 433 de la UNE 20.460-4-43 trata sobre:",
                    opts = listOf(
                        "Protección contra las corrientes de sobrecarga",
                        "Protección contra cortocircuitos",
                        "Limitación de sobreintensidades",
                        "Naturaleza de la alimentación"
                    ),
                    a = 0,
                    exp = "ITC-BT-22, referencia UNE.",
                    ref = "ITC-BT-22"
                ),
                Question(
                    q = "Según el REBT, el apartado 434 de la UNE 20.460-4-43 se refiere a:",
                    opts = listOf(
                        "Protección contra las corrientes de cortocircuito",
                        "Protección contra sobrecargas",
                        "Limitación por características de alimentación",
                        "Coordinación entre protecciones"
                    ),
                    a = 0,
                    exp = "ITC-BT-22, referencia UNE.",
                    ref = "ITC-BT-22"
                ),
                Question(
                    q = "Según el REBT, el apartado 435 de la UNE-HD 60364-4-43 trata sobre:",
                    opts = listOf(
                        "Coordinación entre protección contra sobrecargas y cortocircuitos",
                        "Naturaleza de los dispositivos",
                        "Protección diferencial",
                        "Limitación de tensiones"
                    ),
                    a = 0,
                    exp = "ITC-BT-22, referencia UNE.",
                    ref = "ITC-BT-22"
                ),
                Question(
                    q = "Según el REBT, el apartado 436 de la UNE 20.460-4-43 se refiere a:",
                    opts = listOf(
                        "Limitación de las sobreintensidades por las características de alimentación",
                        "Protección contra contactos directos",
                        "Protección del conductor de neutro",
                        "Coordinación de diferenciales"
                    ),
                    a = 0,
                    exp = "ITC-BT-22, referencia UNE.",
                    ref = "ITC-BT-22"
                ),
                Question(
                    q = "Según el REBT, en la tabla de aplicación de medidas de protección, la letra P indica:",
                    opts = listOf(
                        "Que debe preverse un dispositivo de protección sobre el conductor correspondiente",
                        "Protección permanente obligatoria",
                        "Protección por puesta a tierra",
                        "Protección preferente"
                    ),
                    a = 0,
                    exp = "ITC-BT-22, apartado 1.2 tabla 1.",
                    ref = "ITC-BT-22"
                ),
                Question(
                    q = "Según el REBT, SN corresponde a:",
                    opts = listOf(
                        "Sección del conductor de neutro",
                        "Sistema de neutro",
                        "Sobrecarga nominal",
                        "Sección nominal del circuito"
                    ),
                    a = 0,
                    exp = "ITC-BT-22, apartado 1.2 tabla 1.",
                    ref = "ITC-BT-22"
                ),
                Question(
                    q = "Según el REBT, esta instrucción trata de la protección de las instalaciones interiores contra:",
                    opts = listOf(
                        "Las sobretensiones transitorias transmitidas por las redes de distribución",
                        "Las sobretensiones permanentes de origen interno",
                        "Las sobreintensidades por sobrecarga",
                        "Los contactos directos e indirectos"
                    ),
                    a = 0,
                    exp = "ITC-BT-23, punto 1: «Esta instrucción trata de la protección de las instalaciones eléctricas interiores contra las sobretensiones transitorias que se transmiten por las redes de distribución…»",
                    ref = "ITC-BT-23"
                ),
                Question(
                    q = "Según el REBT, las sobretensiones tratadas en esta ITC se originan fundamentalmente por:",
                    opts = listOf(
                        "Descargas atmosféricas, conmutaciones de red y defectos en las mismas",
                        "Sobrecargas prolongadas",
                        "Fugas de corriente a tierra",
                        "Defectos de aislamiento de baja impedancia"
                    ),
                    a = 0,
                    exp = "ITC-BT-23, punto 1: «…que se originan, fundamentalmente, como consecuencia de las descargas atmosféricas, conmutaciones de redes y defectos en las mismas.»",
                    ref = "ITC-BT-23"
                ),
                Question(
                    q = "Según el REBT, el nivel de sobretensión que puede aparecer en la red es función de:",
                    opts = listOf(
                        "Nivel isoceraúnico, tipo de acometida y proximidad del transformador",
                        "La potencia instalada",
                        "La categoría del diferencial",
                        "La sección del conductor de fase"
                    ),
                    a = 0,
                    exp = "ITC-BT-23, punto 1: «El nivel de sobretensión que puede aparecer en la red es función del: nivel isoceraúnico estimado, tipo de acometida aérea o subterránea, proximidad del transformador de MT/BT…»",
                    ref = "ITC-BT-23"
                ),
                Question(
                    q = "Según el REBT, la incidencia de la sobretensión en la seguridad depende, entre otros factores, de:",
                    opts = listOf(
                        "La coordinación del aislamiento de los equipos",
                        "La intensidad nominal del circuito",
                        "La frecuencia de la red",
                        "La longitud de los conductores"
                    ),
                    a = 0,
                    exp = "ITC-BT-23, punto 1: «La incidencia que la sobretensión puede tener en la seguridad… es función de: – La coordinación del aislamiento de los equipos…»",
                    ref = "ITC-BT-23"
                ),
                Question(
                    q = "Según el REBT, esta instrucción se aplica a líneas de alimentación principal de:",
                    opts = listOf(
                        "230/400 V en corriente alterna",
                        "400/690 V en corriente continua",
                        "1000 V en corriente continua",
                        "Muy baja tensión"
                    ),
                    a = 0,
                    exp = "ITC-BT-23, punto 1: «…cuando la protección contra sobretensiones está prescrita o recomendada en las líneas de alimentación principal 230/400 V en corriente alterna…»",
                    ref = "ITC-BT-23"
                ),
                Question(
                    q = "Según el REBT, las categorías de sobretensiones permiten distinguir:",
                    opts = listOf(
                        "Los grados de tensión soportada en las distintas partes de la instalación",
                        "Los tipos de puesta a tierra",
                        "Los niveles de corriente de cortocircuito",
                        "Las potencias máximas admisibles"
                    ),
                    a = 0,
                    exp = "ITC-BT-23, punto 2.1: «Las categorías de sobretensiones permiten distinguir los diversos grados de tensión soportada a las sobretensiones en cada una de las partes de la instalación, equipos y receptores.»",
                    ref = "ITC-BT-23"
                ),
                Question(
                    q = "Según el REBT, las categorías de sobretensiones indican:",
                    opts = listOf(
                        "Los valores de tensión soportada a la onda de choque",
                        "La intensidad máxima admisible",
                        "La corriente diferencial residual",
                        "La resistencia de puesta a tierra"
                    ),
                    a = 0,
                    exp = "ITC-BT-23, punto 2.1: «Las categorías indican los valores de tensión soportada a la onda de choque de sobretensión que deben de tener los equipos…»",
                    ref = "ITC-BT-23"
                ),
                Question(
                    q = "Según el REBT, la estrategia de protección en cascada integra:",
                    opts = listOf(
                        "Tres niveles de protección: basta, media y fina",
                        "Dos niveles de protección",
                        "Un único nivel de protección",
                        "Protección diferencial y magnetotérmica"
                    ),
                    a = 0,
                    exp = "ITC-BT-23, punto 2.1: «…una estrategia de protección en cascada que integra tres niveles de protección: basta, media y fina…»",
                    ref = "ITC-BT-23"
                ),
                Question(
                    q = "Según el REBT, la categoría I se aplica a:",
                    opts = listOf(
                        "Equipos muy sensibles a las sobretensiones",
                        "Equipos de distribución principal",
                        "Líneas aéreas de alimentación",
                        "Motores de conexión fija"
                    ),
                    a = 0,
                    exp = "ITC-BT-23, punto 2.2, Categoría I: «Se aplica a los equipos muy sensibles a las sobretensiones…»",
                    ref = "ITC-BT-23"
                ),
                Question(
                    q = "Según el REBT, los equipos destinados a conectarse a una instalación eléctrica fija pertenecen a:",
                    opts = listOf(
                        "Categoría II",
                        "Categoría I",
                        "Categoría III",
                        "Categoría IV"
                    ),
                    a = 0,
                    exp = "ITC-BT-23, punto 2.2, Categoría II: «Se aplica a los equipos destinados a conectarse a una instalación eléctrica fija.»",
                    ref = "ITC-BT-23"
                ),
                Question(
                    q = "Según el REBT, los armarios de distribución y la aparamenta pertenecen a:",
                    opts = listOf(
                        "Categoría III",
                        "Categoría II",
                        "Categoría I",
                        "Categoría IV"
                    ),
                    a = 0,
                    exp = "ITC-BT-23, punto 2.2, Categoría III: «Ejemplo: armarios de distribución, embarrados, aparamenta…»",
                    ref = "ITC-BT-23"
                ),
                Question(
                    q = "Según el REBT, los equipos conectados en el origen o muy próximos al origen de la instalación pertenecen a:",
                    opts = listOf(
                        "Categoría IV",
                        "Categoría III",
                        "Categoría II",
                        "Categoría I"
                    ),
                    a = 0,
                    exp = "ITC-BT-23, punto 2.2, Categoría IV: «Se aplica a los equipos y materiales que se conectan en el origen o muy próximos al origen de la instalación…»",
                    ref = "ITC-BT-23"
                ),
                Question(
                    q = "Según el REBT, la descarga directa del rayo:",
                    opts = listOf(
                        "No es tratada por esta instrucción",
                        "Es el caso principal tratado",
                        "Se considera situación natural",
                        "No produce sobretensiones"
                    ),
                    a = 0,
                    exp = "ITC-BT-23, punto 3: «Las producidas como consecuencia de la descarga directa del rayo. Esta instrucción no trata este caso.»",
                    ref = "ITC-BT-23"
                ),
                Question(
                    q = "Según el REBT, una situación natural se da cuando:",
                    opts = listOf(
                        "La instalación está alimentada por red subterránea en su totalidad",
                        "Existe una línea aérea con conductores desnudos",
                        "Hay alto riesgo de sobretensiones",
                        "Se instalan descargadores obligatoriamente"
                    ),
                    a = 0,
                    exp = "ITC-BT-23, punto 3.1: «…cuando no es preciso la protección… debido a que está alimentada por una red subterránea en su totalidad…»",
                    ref = "ITC-BT-23"
                ),
                Question(
                    q = "Según el REBT, una línea aérea con conductores aislados y pantalla metálica a tierra se considera:",
                    opts = listOf(
                        "Equivalente a una línea subterránea",
                        "Situación controlada",
                        "No permitida",
                        "De alto riesgo"
                    ),
                    a = 0,
                    exp = "ITC-BT-23, punto 3.1: «…se considera equivalente a una línea subterránea.»",
                    ref = "ITC-BT-23"
                ),
                Question(
                    q = "Según el REBT, se considera necesaria protección contra sobretensiones cuando:",
                    opts = listOf(
                        "La instalación incluye una línea aérea",
                        "La red es completamente subterránea",
                        "La potencia es inferior a 10 kW",
                        "Existe solo una categoría I"
                    ),
                    a = 0,
                    exp = "ITC-BT-23, punto 3.2: «Cuando una instalación se alimenta por, o incluye, una línea aérea… se considera necesaria una protección contra sobretensiones…»",
                    ref = "ITC-BT-23"
                ),
                Question(
                    q = "Según el REBT, en redes TT o IT los descargadores se conectarán entre:",
                    opts = listOf(
                        "Cada conductor, incluido el neutro, y tierra",
                        "Fase y fase",
                        "Neutro y conductor de protección",
                        "Solo fases"
                    ),
                    a = 0,
                    exp = "ITC-BT-23, punto 3.2: «En redes TT o IT, los descargadores se conectarán entre cada uno de los conductores, incluyendo el neutro… y la tierra…»",
                    ref = "ITC-BT-23"
                ),
                Question(
                    q = "Según el REBT, en redes TN-S los descargadores se conectarán entre:",
                    opts = listOf(
                        "Cada fase y el conductor de protección",
                        "Fase y neutro",
                        "Neutro y tierra",
                        "Fase y fase"
                    ),
                    a = 0,
                    exp = "ITC-BT-23, punto 3.2: «En redes TN-S, los descargadores se conectarán entre cada uno de los conductores de fase y el conductor de protección.»",
                    ref = "ITC-BT-23"
                ),
                Question(
                    q = "Según el REBT, los equipos deberán escogerse de forma que su tensión soportada a impulsos:",
                    opts = listOf(
                        "No sea inferior a la indicada en la tabla 1",
                        "Sea inferior a la tabla 1",
                        "No supere 1,5 kV",
                        "Dependa del diferencial"
                    ),
                    a = 0,
                    exp = "ITC-BT-23, punto 4: «Los equipos y materiales deben escogerse de manera que su tensión soportada a impulsos no sea inferior a la tensión soportada prescrita en la tabla 1…»",
                    ref = "ITC-BT-23"
                ),
                Question(
                    q = "Según el REBT, los equipos y materiales deben escogerse de manera que:",
                    opts = listOf(
                        "Su tensión soportada a impulsos no sea inferior a la prescrita en la tabla 1",
                        "Su tensión nominal sea siempre superior a 400 V",
                        "Dispongan obligatoriamente de protección diferencial",
                        "Se instalen únicamente en situación controlada"
                    ),
                    a = 0,
                    exp = "ITC-BT-23, punto 4: «Los equipos y materiales deben escogerse de manera que su tensión soportada a impulsos no sea inferior a la tensión soportada prescrita en la tabla 1, según su categoría.»",
                    ref = "ITC-BT-23"
                ),
                Question(
                    q = "Según el REBT, la protección contra contactos directos e indirectos a la vez se realiza mediante:",
                    opts = listOf(
                        "La utilización de muy baja tensión de seguridad (MBTS)",
                        "El uso exclusivo de interruptores automáticos",
                        "La conexión equipotencial principal",
                        "La puesta a tierra de las masas"
                    ),
                    a = 0,
                    exp = "ITC-BT-24, apartado 2: «La protección contra los choques eléctricos para contactos directos e indirectos a la vez se realiza mediante la utilización de muy baja tensión de seguridad MBTS.»",
                    ref = "ITC-BT-24"
                ),
                Question(
                    q = "Según el REBT, las pinturas, barnices y lacas:",
                    opts = listOf(
                        "No se consideran aislamiento suficiente contra contactos directos",
                        "Son equivalentes a un aislamiento principal",
                        "Pueden sustituir al aislamiento de las partes activas",
                        "Se aceptan como aislamiento reforzado"
                    ),
                    a = 0,
                    exp = "ITC-BT-24, apartado 3.1: «Las pinturas, barnices, lacas y productos similares no se considera que constituyan un aislamiento suficiente en el marco de la protección contra los contactos directos.»",
                    ref = "ITC-BT-24"
                ),
                Question(
                    q = "Según el REBT, las barreras o envolventes deben poseer como mínimo el grado de protección:",
                    opts = listOf(
                        "IP XXB",
                        "IP 2X",
                        "IP 1X",
                        "IP 00"
                    ),
                    a = 0,
                    exp = "ITC-BT-24, apartado 3.2: «Las partes activas deben estar situadas en el interior de las envolventes o detrás de barreras que posean, como mínimo, el grado de protección IP XXB.»",
                    ref = "ITC-BT-24"
                ),
                Question(
                    q = "Según el REBT, las superficies superiores horizontales fácilmente accesibles deben cumplir como mínimo:",
                    opts = listOf(
                        "IP4X o IP XXD",
                        "IP2X",
                        "IP XXB",
                        "IP00"
                    ),
                    a = 0,
                    exp = "ITC-BT-24, apartado 3.2: «Las superficies superiores de las barreras o envolventes horizontales que son fácilmente accesibles, deben responder como mínimo al grado de protección IP4X o IP XXD.»",
                    ref = "ITC-BT-24"
                ),
                Question(
                    q = "Según el REBT, la protección por medio de obstáculos:",
                    opts = listOf(
                        "No garantiza una protección completa",
                        "Garantiza protección total contra contactos voluntarios",
                        "Es válida para cualquier tipo de local",
                        "Sustituye al aislamiento de las partes activas"
                    ),
                    a = 0,
                    exp = "ITC-BT-24, apartado 3.3: «Esta medida no garantiza una protección completa y su aplicación se limita, en la práctica, a los locales de servicio eléctrico solo accesibles al personal autorizado.»",
                    ref = "ITC-BT-24"
                ),
                Question(
                    q = "Según el REBT, la protección por puesta fuera de alcance por alejamiento se limita a:",
                    opts = listOf(
                        "Locales de servicio eléctrico accesibles solo a personal autorizado",
                        "Locales de pública concurrencia",
                        "Viviendas",
                        "Locales húmedos"
                    ),
                    a = 0,
                    exp = "ITC-BT-24, apartado 3.4: «Esta medida no garantiza una protección completa y su aplicación se limita, en la práctica a los locales de servicio eléctrico solo accesibles al personal autorizado.»",
                    ref = "ITC-BT-24"
                ),
                Question(
                    q = "Según el REBT, la altura que limita el volumen de accesibilidad es de:",
                    opts = listOf(
                        "2,5 m",
                        "2,0 m",
                        "3,0 m",
                        "1,8 m"
                    ),
                    a = 0,
                    exp = "ITC-BT-24, apartado 3.4: «Por convenio, este volumen está limitado conforme a la figura 1, entendiendo que la altura que limita el volumen es 2,5 m.»",
                    ref = "ITC-BT-24"
                ),
                Question(
                    q = "Según el REBT, los dispositivos diferenciales como protección complementaria deben tener una corriente diferencial asignada:",
                    opts = listOf(
                        "Igual o inferior a 30 mA",
                        "Inferior a 300 mA",
                        "Superior a 30 mA",
                        "Exactamente de 100 mA"
                    ),
                    a = 0,
                    exp = "ITC-BT-24, apartado 3.5: «El empleo de dispositivos de corriente diferencial-residual, cuyo valor de corriente diferencial asignada de funcionamiento sea inferior o igual a 30 mA, se reconoce como medida de protección complementaria.»",
                    ref = "ITC-BT-24"
                ),
                Question(
                    q = "Según el REBT, el uso de dispositivos diferenciales no constituye por sí solo una protección completa porque:",
                    opts = listOf(
                        "Debe combinarse con otras medidas de protección",
                        "No actúa ante fallos a tierra",
                        "No interrumpe el circuito",
                        "No protege contra contactos indirectos"
                    ),
                    a = 0,
                    exp = "ITC-BT-24, apartado 3.5: «La utilización de tales dispositivos no constituye por sí mismo una medida de protección completa y requiere el empleo de una de las medidas de protección enunciadas en los apartados 3.1 a 3.4.»",
                    ref = "ITC-BT-24"
                ),
                Question(
                    q = "Según el REBT, la tensión límite convencional en corriente alterna es de:",
                    opts = listOf(
                        "50 V",
                        "24 V",
                        "120 V",
                        "230 V"
                    ),
                    a = 0,
                    exp = "ITC-BT-24, apartado 4.1: «La tensión límite convencional es igual a 50 V, valor eficaz en corriente alterna, en condiciones normales.»",
                    ref = "ITC-BT-24"
                ),
                Question(
                    q = "Según el REBT, en esquemas TN debe cumplirse la condición:",
                    opts = listOf(
                        "Zs × Ia ≤ U0",
                        "RA × Ia ≤ U",
                        "RA × Id ≤ UL",
                        "2 × Zs × Ia ≤ U"
                    ),
                    a = 0,
                    exp = "ITC-BT-24, apartado 4.1.1: «Las características de los dispositivos de protección (...) se eligen de manera que se cumpla la condición siguiente: Zs x Ia ≤ U0.»",
                    ref = "ITC-BT-24"
                ),
                Question(
                    q = "Según el REBT, en esquemas TN-C:",
                    opts = listOf(
                        "No pueden utilizarse dispositivos diferenciales",
                        "Es obligatorio el uso de diferenciales",
                        "Debe separarse el neutro y el conductor de protección aguas abajo",
                        "Se exige protección por MBTS"
                    ),
                    a = 0,
                    exp = "ITC-BT-24, apartado 4.1.1: «Cuando el conductor neutro y el conductor de protección sean comunes (esquemas TN-C), no podrá utilizarse dispositivos de protección de corriente diferencial-residual.»",
                    ref = "ITC-BT-24"
                ),
                Question(
                    q = "Según el REBT, en esquemas TT debe cumplirse la condición:",
                    opts = listOf(
                        "RA × Ia ≤ U",
                        "Zs × Ia ≤ U0",
                        "2 × Zs × Ia ≤ U",
                        "RA × Id ≤ UL"
                    ),
                    a = 0,
                    exp = "ITC-BT-24, apartado 4.1.2: «Se cumplirá la siguiente condición: RA x Ia ≤ U.»",
                    ref = "ITC-BT-24"
                ),
                Question(
                    q = "Según el REBT, en esquemas IT con un primer defecto:",
                    opts = listOf(
                        "No es imperativo el corte automático",
                        "Debe producirse el corte inmediato",
                        "Debe actuar un interruptor automático",
                        "Debe disparar un fusible"
                    ),
                    a = 0,
                    exp = "ITC-BT-24, apartado 4.1.3: «En caso de que exista un sólo defecto a masa o a tierra, la corriente de fallo es de poca intensidad y no es imperativo el corte.»",
                    ref = "ITC-BT-24"
                ),
                Question(
                    q = "Según el REBT, en esquemas IT ningún conductor activo debe:",
                    opts = listOf(
                        "Conectarse directamente a tierra",
                        "Tener aislamiento reforzado",
                        "Disponer de protección diferencial",
                        "Estar protegido por fusible"
                    ),
                    a = 0,
                    exp = "ITC-BT-24, apartado 4.1.3: «Ningún conductor activo debe conectarse directamente a tierra en la instalación.»",
                    ref = "ITC-BT-24"
                ),
                Question(
                    q = "Según el REBT, la protección por equipos de clase II se basa en:",
                    opts = listOf(
                        "Aislamiento doble o reforzado",
                        "Conexión equipotencial",
                        "Separación eléctrica",
                        "Corte automático"
                    ),
                    a = 0,
                    exp = "ITC-BT-24, apartado 4.2: «Utilización de equipos con un aislamiento doble o reforzado (clase II).»",
                    ref = "ITC-BT-24"
                ),
                Question(
                    q = "Según el REBT, en locales no conductores no debe existir:",
                    opts = listOf(
                        "Conductor de protección",
                        "Aislamiento principal",
                        "Separación eléctrica",
                        "Equipos de clase II"
                    ),
                    a = 0,
                    exp = "ITC-BT-24, apartado 4.3: «En estos locales (o emplazamientos), no debe estar previsto ningún conductor de protección.»",
                    ref = "ITC-BT-24"
                ),
                Question(
                    q = "Según el REBT, la resistencia mínima de paredes y suelos aislantes con tensión ≤ 500 V es de:",
                    opts = listOf(
                        "50 kΩ",
                        "100 kΩ",
                        "10 kΩ",
                        "1 MΩ"
                    ),
                    a = 0,
                    exp = "ITC-BT-24, apartado 4.3: «Las paredes y suelos aislantes deben presentar una resistencia no inferior a 50 kΩ, si la tensión nominal de la instalación no es superior a 500 V.»",
                    ref = "ITC-BT-24"
                ),
                Question(
                    q = "Según el REBT, en la protección por separación eléctrica con varios receptores:",
                    opts = listOf(
                        "Las masas deben conectarse entre sí mediante conductores equipotenciales no conectados a tierra",
                        "Las masas deben conectarse a tierra",
                        "Debe utilizarse un esquema TT",
                        "Debe emplearse MBTS"
                    ),
                    a = 0,
                    exp = "ITC-BT-24, apartado 4.5: «Las masas del circuito separado deben conectarse entre sí mediante conductores de equipotencialidad aislados, no conectados a tierra.»",
                    ref = "ITC-BT-24"
                ),
                Question(
                    q = "Según el REBT, el grado de electrificación básico se plantea como:",
                    opts = listOf(
                        "El sistema mínimo de la instalación interior de las viviendas en edificios nuevos",
                        "Un sistema opcional para viviendas existentes",
                        "Un sistema exclusivo para electrificación elevada",
                        "Un sistema aplicable solo a viviendas unifamiliares"
                    ),
                    a = 0,
                    exp = "ITC-BT-25, apartado 1: \"El grado de electrificación básico se plantea como el sistema mínimo, a los efectos de uso, de la instalación interior de las viviendas en edificios nuevos\".",
                    ref = "ITC-BT-25"
                ),
                Question(
                    q = "Según el REBT, el objeto del grado de electrificación básico es:",
                    opts = listOf(
                        "Permitir la utilización de los aparatos electrodomésticos de uso básico sin necesidad de obras posteriores",
                        "Garantizar el uso de sistemas de climatización",
                        "Permitir la recarga de vehículos eléctricos",
                        "Cubrir únicamente las necesidades de iluminación"
                    ),
                    a = 0,
                    exp = "ITC-BT-25, apartado 1: \"Su objeto es permitir la utilización de los aparatos electrodomésticos de uso básico sin necesidad de obras posteriores de adecuación\".",
                    ref = "ITC-BT-25"
                ),
                Question(
                    q = "Según el REBT, la capacidad de la instalación interior debe corresponderse como mínimo a:",
                    opts = listOf(
                        "La intensidad asignada del interruptor general automático",
                        "La potencia contratada",
                        "La sección de los conductores",
                        "El número de circuitos instalados"
                    ),
                    a = 0,
                    exp = "ITC-BT-25, apartado 1: \"La capacidad de instalación se corresponderá como mínimo al valor de la intensidad asignada determinada para el interruptor general automático\".",
                    ref = "ITC-BT-25"
                ),
                Question(
                    q = "Según el REBT, el interruptor general automático debe ser:",
                    opts = listOf(
                        "De corte omnipolar con accionamiento manual",
                        "Un interruptor diferencial de alta sensibilidad",
                        "El mismo que el interruptor de control de potencia",
                        "De corte unipolar"
                    ),
                    a = 0,
                    exp = "ITC-BT-25, apartado 2.1: \"Un interruptor general automático de corte omnipolar con accionamiento manual\".",
                    ref = "ITC-BT-25"
                ),
                Question(
                    q = "Según el REBT, la intensidad nominal mínima del interruptor general automático será de:",
                    opts = listOf(
                        "25 A",
                        "16 A",
                        "20 A",
                        "30 A"
                    ),
                    a = 0,
                    exp = "ITC-BT-25, apartado 2.1: \"de intensidad nominal mínima de 25 A\".",
                    ref = "ITC-BT-25"
                ),
                Question(
                    q = "Según el REBT, el interruptor general automático:",
                    opts = listOf(
                        "Es independiente del interruptor para el control de potencia",
                        "Puede ser sustituido por el ICP",
                        "Debe integrarse en el contador",
                        "Es opcional en viviendas"
                    ),
                    a = 0,
                    exp = "ITC-BT-25, apartado 2.1: \"El interruptor general es independiente del interruptor para el control de potencia (ICP) y no puede ser sustituido por éste\".",
                    ref = "ITC-BT-25"
                ),
                Question(
                    q = "Según el REBT, los interruptores diferenciales deben garantizar:",
                    opts = listOf(
                        "La protección contra contactos indirectos de todos los circuitos",
                        "La protección contra sobrecargas",
                        "La protección contra sobretensiones",
                        "La protección contra contactos directos exclusivamente"
                    ),
                    a = 0,
                    exp = "ITC-BT-25, apartado 2.1: \"Uno o varios interruptores diferenciales que garanticen la protección contra contactos indirectos de todos los circuitos\".",
                    ref = "ITC-BT-25"
                ),
                Question(
                    q = "Según el REBT, la intensidad diferencial-residual máxima de los diferenciales será de:",
                    opts = listOf(
                        "30 mA",
                        "100 mA",
                        "300 mA",
                        "10 mA"
                    ),
                    a = 0,
                    exp = "ITC-BT-25, apartado 2.1: \"con una intensidad diferencial-residual máxima de 30 mA\".",
                    ref = "ITC-BT-25"
                ),
                Question(
                    q = "Según el REBT, el circuito C1 está destinado a:",
                    opts = listOf(
                        "Alimentar los puntos de iluminación",
                        "Tomas de corriente de uso general",
                        "Cocina y horno",
                        "Lavadora y lavavajillas"
                    ),
                    a = 0,
                    exp = "ITC-BT-25, apartado 2.3.1: \"C1 circuito de distribución interna, destinado a alimentar los puntos de iluminación\".",
                    ref = "ITC-BT-25"
                ),
                Question(
                    q = "Según el REBT, el circuito C2 está destinado a:",
                    opts = listOf(
                        "Tomas de corriente de uso general y frigorífico",
                        "Cocina y horno",
                        "Calefacción eléctrica",
                        "Secadora independiente"
                    ),
                    a = 0,
                    exp = "ITC-BT-25, apartado 2.3.1: \"C2 circuito de distribución interna, destinado a tomas de corriente de uso general y frigorífico\".",
                    ref = "ITC-BT-25"
                ),
                Question(
                    q = "Según el REBT, el circuito C3 está destinado a:",
                    opts = listOf(
                        "Alimentar la cocina y horno",
                        "Alimentar puntos de luz",
                        "Alimentar tomas del baño",
                        "Alimentar climatización"
                    ),
                    a = 0,
                    exp = "ITC-BT-25, apartado 2.3.1: \"C3 circuito de distribución interna, destinado a alimentar la cocina y horno\".",
                    ref = "ITC-BT-25"
                ),
                Question(
                    q = "Según el REBT, el circuito C4 está destinado a:",
                    opts = listOf(
                        "Lavadora, lavavajillas y termo eléctrico",
                        "Cocina y horno",
                        "Tomas de uso general",
                        "Calefacción eléctrica"
                    ),
                    a = 0,
                    exp = "ITC-BT-25, apartado 2.3.1: \"C4 circuito de distribución interna, destinado a alimentar la lavadora, lavavajillas y termo eléctrico\".",
                    ref = "ITC-BT-25"
                ),
                Question(
                    q = "Según el REBT, el circuito C5 está destinado a:",
                    opts = listOf(
                        "Tomas de corriente de los cuartos de baño y bases auxiliares de cocina",
                        "Puntos de iluminación",
                        "Climatización",
                        "Secadora"
                    ),
                    a = 0,
                    exp = "ITC-BT-25, apartado 2.3.1: \"C5 circuito de distribución interna, destinado a alimentar tomas de corriente de los cuartos de baño, así como las bases auxiliares del cuarto de cocina\".",
                    ref = "ITC-BT-25"
                ),
                Question(
                    q = "Según el REBT, la electrificación elevada se aplica cuando:",
                    opts = listOf(
                        "La superficie útil de la vivienda es superior a 160 m2",
                        "La vivienda tiene una sola planta",
                        "La potencia contratada es inferior a 5,75 kW",
                        "No existe previsión de nuevos receptores"
                    ),
                    a = 0,
                    exp = "ITC-BT-25, apartado 2.3.2: \"o con superficies útiles de las viviendas superiores a 160 m2\".",
                    ref = "ITC-BT-25"
                ),
                Question(
                    q = "Según el REBT, el circuito C8 corresponde a:",
                    opts = listOf(
                        "Calefacción eléctrica",
                        "Aire acondicionado",
                        "Secadora",
                        "Automatización"
                    ),
                    a = 0,
                    exp = "ITC-BT-25, apartado 2.3.2: \"C8 Circuito de distribución interna, destinado a la instalación de calefacción eléctrica\".",
                    ref = "ITC-BT-25"
                ),
                Question(
                    q = "Según el REBT, el circuito C9 corresponde a:",
                    opts = listOf(
                        "Aire acondicionado",
                        "Calefacción eléctrica",
                        "Secadora",
                        "Cocina y horno"
                    ),
                    a = 0,
                    exp = "ITC-BT-25, apartado 2.3.2: \"C9 Circuito de distribución interna, destinado a la instalación aire acondicionado\".",
                    ref = "ITC-BT-25"
                ),
                Question(
                    q = "Según el REBT, el circuito C10 corresponde a:",
                    opts = listOf(
                        "Secadora independiente",
                        "Lavadora",
                        "Frigorífico",
                        "Iluminación"
                    ),
                    a = 0,
                    exp = "ITC-BT-25, apartado 2.3.2: \"C10 Circuito de distribución interna, destinado a la instalación de una secadora independiente\".",
                    ref = "ITC-BT-25"
                ),
                Question(
                    q = "Según el REBT, el circuito C13 está destinado a:",
                    opts = listOf(
                        "Infraestructura de recarga de vehículos eléctricos",
                        "Automatización de la vivienda",
                        "Calefacción eléctrica",
                        "Climatización"
                    ),
                    a = 0,
                    exp = "ITC-BT-25, apartado 2.3.2: \"C13 Circuito adicional para la infraestructura de recarga de vehículos eléctricos\".",
                    ref = "ITC-BT-25"
                ),
                Question(
                    q = "Según el REBT, la caída de tensión máxima en la instalación interior será de:",
                    opts = listOf(
                        "3 %",
                        "5 %",
                        "2 %",
                        "1 %"
                    ),
                    a = 0,
                    exp = "ITC-BT-25, apartado 3: \"la caída de tensión sea como máximo el 3 %\".",
                    ref = "ITC-BT-25"
                ),
                Question(
                    q = "Según el REBT, la intensidad prevista de cada circuito se calculará mediante la fórmula:",
                    opts = listOf(
                        "I = n × Ia × Fs × Fu",
                        "I = P / U",
                        "I = √3 × U × cosφ",
                        "I = Z × U"
                    ),
                    a = 0,
                    exp = "ITC-BT-25, apartado 3: \"El valor de la intensidad de corriente prevista en cada circuito se calculará de acuerdo con la fórmula: I = n × Ia × Fs × Fu\".",
                    ref = "ITC-BT-25"
                ),
                Question(
                    q = "Según el REBT, las instalaciones de las viviendas se consideran alimentadas por una red:",
                    opts = listOf(
                        "De esquema TT",
                        "De esquema TN-C",
                        "De esquema IT",
                        "De esquema TN-S"
                    ),
                    a = 0,
                    exp = "ITC-BT-26, apartado 2: «alimentadas por una red de distribución pública de baja tensión según el esquema de distribución “TT”».",
                    ref = "ITC-BT-26"
                ),
                Question(
                    q = "Según el REBT, la tensión de alimentación monofásica en viviendas es de:",
                    opts = listOf(
                        "230 V",
                        "400 V",
                        "120 V",
                        "127 V"
                    ),
                    a = 0,
                    exp = "ITC-BT-26, apartado 2: «a una tensión de 230 V en alimentación monofásica».",
                    ref = "ITC-BT-26"
                ),
                Question(
                    q = "Según el REBT, en toda nueva edificación se establecerá:",
                    opts = listOf(
                        "Una toma de tierra de protección",
                        "Una red equipotencial secundaria",
                        "Un sistema IT",
                        "Un electrodo independiente por vivienda"
                    ),
                    a = 0,
                    exp = "ITC-BT-26, apartado 3.1: «En toda nueva edificación se establecerá una toma de tierra de protección».",
                    ref = "ITC-BT-26"
                ),
                Question(
                    q = "Según el REBT, el conductor en anillo de la toma de tierra se instalará:",
                    opts = listOf(
                        "En el fondo de las zanjas de cimentación",
                        "En los falsos techos",
                        "En canalizaciones vistas",
                        "En el interior de las viviendas"
                    ),
                    a = 0,
                    exp = "ITC-BT-26, apartado 3.1: «Instalando en el fondo de las zanjas de cimentación de los edificios».",
                    ref = "ITC-BT-26"
                ),
                Question(
                    q = "Según el REBT, las conexiones del conductor de tierra se realizarán mediante:",
                    opts = listOf(
                        "Soldadura aluminotérmica o autógena",
                        "Bornes enchufables",
                        "Conectores rápidos",
                        "Empalmes mecánicos simples"
                    ),
                    a = 0,
                    exp = "ITC-BT-26, apartado 3.1: «Estas conexiones se establecerán… mediante soldadura aluminotérmica o autógena».",
                    ref = "ITC-BT-26"
                ),
                Question(
                    q = "Según el REBT, a la toma de tierra se conectarán:",
                    opts = listOf(
                        "Las masas metálicas accesibles de los aparatos",
                        "Solo los conductores de fase",
                        "Únicamente los cuadros eléctricos",
                        "Solo las carcasas aislantes"
                    ),
                    a = 0,
                    exp = "ITC-BT-26, apartado 3.2: «las masas metálicas accesibles de los aparatos receptores».",
                    ref = "ITC-BT-26"
                ),
                Question(
                    q = "Según el REBT, deberán conectarse a tierra las partes metálicas de:",
                    opts = listOf(
                        "Las instalaciones de agua y gas",
                        "Solo las instalaciones eléctricas",
                        "Únicamente las antenas",
                        "Exclusivamente los depósitos"
                    ),
                    a = 0,
                    exp = "ITC-BT-26, apartado 3.2: «las instalaciones de agua, de las instalaciones de gas canalizado».",
                    ref = "ITC-BT-26"
                ),
                Question(
                    q = "Según el REBT, uno de los puntos de puesta a tierra se situará:",
                    opts = listOf(
                        "En la caja general de protección",
                        "Dentro de cada vivienda",
                        "En el último circuito",
                        "En los puntos de luz"
                    ),
                    a = 0,
                    exp = "ITC-BT-26, apartado 3.3.d): «En el punto de ubicación de la caja general de protección».",
                    ref = "ITC-BT-26"
                ),
                Question(
                    q = "Según el REBT, las líneas principales de tierra se establecerán:",
                    opts = listOf(
                        "En las mismas canalizaciones que las líneas generales de alimentación",
                        "En canalizaciones independientes",
                        "Por el interior de las viviendas",
                        "En bandejas metálicas vistas"
                    ),
                    a = 0,
                    exp = "ITC-BT-26, apartado 3.4: «se establecerán en las mismas canalizaciones que las de las líneas generales de alimentación».",
                    ref = "ITC-BT-26"
                ),
                Question(
                    q = "Según el REBT, la sección mínima de la línea principal de tierra será de:",
                    opts = listOf(
                        "16 mm²",
                        "10 mm²",
                        "6 mm²",
                        "25 mm²"
                    ),
                    a = 0,
                    exp = "ITC-BT-26, apartado 3.4: «con un mínimo de 16 milímetros cuadrados».",
                    ref = "ITC-BT-26"
                ),
                Question(
                    q = "Según el REBT, no podrán utilizarse como conductores de tierra:",
                    opts = listOf(
                        "Las tuberías de agua y gas",
                        "Los conductores de cobre",
                        "Las pletinas de tierra",
                        "Los conductores desnudos"
                    ),
                    a = 0,
                    exp = "ITC-BT-26, apartado 3.4: «No podrán utilizarse como conductores de tierra las tuberías de agua, gas».",
                    ref = "ITC-BT-26"
                ),
                Question(
                    q = "Según el REBT, los conductores de protección se instalarán:",
                    opts = listOf(
                        "Acompañando a los conductores activos",
                        "Por canalizaciones independientes",
                        "Solo en circuitos especiales",
                        "Únicamente hasta el cuadro"
                    ),
                    a = 0,
                    exp = "ITC-BT-26, apartado 3.5: «Se instalarán conductores de protección acompañando a los conductores activos».",
                    ref = "ITC-BT-26"
                ),
                Question(
                    q = "Según el REBT, la protección contra contactos indirectos se realizará mediante:",
                    opts = listOf(
                        "Puesta a tierra de las masas y dispositivos de protección",
                        "Solo interruptores automáticos",
                        "Únicamente diferenciales selectivos",
                        "Aislamiento reforzado"
                    ),
                    a = 0,
                    exp = "ITC-BT-26, apartado 4: «mediante la puesta a tierra de las masas y empleo de los dispositivos descritos».",
                    ref = "ITC-BT-26"
                ),
                Question(
                    q = "Según el REBT, el cuadro general de distribución se ajustará a:",
                    opts = listOf(
                        "La ITC-BT-17",
                        "La ITC-BT-18",
                        "La ITC-BT-25",
                        "La ITC-BT-23"
                    ),
                    a = 0,
                    exp = "ITC-BT-26, apartado 5: «El cuadro general de distribución estará de acuerdo con lo indicado en la ITC-BT-17».",
                    ref = "ITC-BT-26"
                ),
                Question(
                    q = "Según el REBT, el cuadro general llevará una placa con:",
                    opts = listOf(
                        "Nombre del instalador y fecha",
                        "Solo el número de circuitos",
                        "El esquema unifilar",
                        "El código de la vivienda"
                    ),
                    a = 0,
                    exp = "ITC-BT-26, apartado 5: «una placa… en la que conste su nombre o marca comercial, fecha».",
                    ref = "ITC-BT-26"
                ),
                Question(
                    q = "Según el REBT, los conductores activos serán de:",
                    opts = listOf(
                        "Cobre",
                        "Aluminio",
                        "Aleación",
                        "Material sintético"
                    ),
                    a = 0,
                    exp = "ITC-BT-26, apartado 6.1.1: «Los conductores activos serán de cobre».",
                    ref = "ITC-BT-26"
                ),
                Question(
                    q = "Según el REBT, el conductor neutro se identificará por el color:",
                    opts = listOf(
                        "Azul claro",
                        "Verde",
                        "Amarillo",
                        "Negro"
                    ),
                    a = 0,
                    exp = "ITC-BT-26, apartado 6.2: «se identificarán éstos por el color azul claro».",
                    ref = "ITC-BT-26"
                ),
                Question(
                    q = "Según el REBT, el conductor de protección se identificará por el color:",
                    opts = listOf(
                        "Amarillo-verde",
                        "Azul",
                        "Negro",
                        "Gris"
                    ),
                    a = 0,
                    exp = "ITC-BT-26, apartado 6.2: «Al conductor de protección se le identificará por el doble color amarillo-verde».",
                    ref = "ITC-BT-26"
                ),
                Question(
                    q = "Según el REBT, no se utilizará un mismo conductor neutro para:",
                    opts = listOf(
                        "Varios circuitos",
                        "Un mismo circuito",
                        "Circuitos trifásicos",
                        "Circuitos de iluminación"
                    ),
                    a = 0,
                    exp = "ITC-BT-26, apartado 7.2: «No se utilizará un mismo conductor neutro para varios circuitos».",
                    ref = "ITC-BT-26"
                ),
                Question(
                    q = "Según el REBT, las prescripciones de la ITC-BT-27 son aplicables a:",
                    opts = listOf(
                        "Instalaciones interiores de viviendas que contengan bañera o ducha",
                        "Únicamente a locales industriales",
                        "Exclusivamente a locales de pública concurrencia",
                        "Solo a instalaciones exteriores"
                    ),
                    a = 0,
                    exp = "ITC-BT-27, apartado 1. Campo de aplicación: «Las prescripciones objeto de esta Instrucción son aplicables a las instalaciones interiores de viviendas [...] que contengan una bañera o una ducha».",
                    ref = "ITC-BT-27"
                ),
                Question(
                    q = "Según el REBT, para duchas de emergencia en zonas industriales:",
                    opts = listOf(
                        "Son de aplicación las reglas generales",
                        "Se aplican los volúmenes 0, 1, 2 y 3",
                        "Es obligatoria la MBTS",
                        "Se exige IPX7"
                    ),
                    a = 0,
                    exp = "ITC-BT-27, apartado 1: «Para duchas de emergencia en zonas industriales, son de aplicación las reglas generales».",
                    ref = "ITC-BT-27"
                ),
                Question(
                    q = "Según el REBT, para la ejecución de las instalaciones en locales con bañera o ducha se tendrán en cuenta:",
                    opts = listOf(
                        "Cuatro volúmenes: 0, 1, 2 y 3",
                        "Tres volúmenes: 0, 1 y 2",
                        "Dos volúmenes: 0 y 1",
                        "Únicamente el volumen 0"
                    ),
                    a = 0,
                    exp = "ITC-BT-27, apartado 2.1: «se tendrán en cuenta los cuatro volúmenes 0, 1, 2 y 3».",
                    ref = "ITC-BT-27"
                ),
                Question(
                    q = "Según el REBT, los falsos techos y mamparas:",
                    opts = listOf(
                        "No se consideran barreras a efectos de separación de volúmenes",
                        "Se consideran barreras aislantes",
                        "Definen nuevos volúmenes",
                        "Reducen el volumen 1"
                    ),
                    a = 0,
                    exp = "ITC-BT-27, apartado 2.1: «Los falsos techos y las mamparas no se consideran barreras a los efectos de la separación de volúmenes».",
                    ref = "ITC-BT-27"
                ),
                Question(
                    q = "Según el REBT, el volumen 0 comprende:",
                    opts = listOf(
                        "El interior de la bañera o ducha",
                        "Hasta 0,6 m alrededor de la ducha",
                        "Hasta 2,25 m de altura",
                        "El espacio bajo la bañera"
                    ),
                    a = 0,
                    exp = "ITC-BT-27, apartado 2.1.1: «Comprende el interior de la bañera o ducha».",
                    ref = "ITC-BT-27"
                ),
                Question(
                    q = "Según el REBT, en una ducha sin plato el volumen 0 está delimitado hasta:",
                    opts = listOf(
                        "0,05 m por encima del suelo",
                        "0,60 m por encima del suelo",
                        "1,20 m por encima del suelo",
                        "2,25 m por encima del suelo"
                    ),
                    a = 0,
                    exp = "ITC-BT-27, apartado 2.1.1: «el volumen 0 está delimitado por el suelo y por un plano horizontal situado a 0,05 m por encima del suelo».",
                    ref = "ITC-BT-27"
                ),
                Question(
                    q = "Según el REBT, cuando el difusor de la ducha es fijo, el volumen 0 queda limitado por un radio de:",
                    opts = listOf(
                        "0,6 m alrededor del difusor",
                        "1,2 m alrededor del difusor",
                        "2,4 m alrededor del difusor",
                        "0,3 m alrededor del difusor"
                    ),
                    a = 0,
                    exp = "ITC-BT-27, apartado 2.1.1.b: «situado a un radio de 0,6 m alrededor del difusor».",
                    ref = "ITC-BT-27"
                ),
                Question(
                    q = "Según el REBT, el volumen 1 está limitado en altura hasta:",
                    opts = listOf(
                        "2,25 m por encima del suelo",
                        "2,00 m por encima del suelo",
                        "3,00 m por encima del suelo",
                        "1,80 m por encima del suelo"
                    ),
                    a = 0,
                    exp = "ITC-BT-27, apartado 2.1.2.a: «plano horizontal situado a 2,25 m por encima del suelo».",
                    ref = "ITC-BT-27"
                ),
                Question(
                    q = "Según el REBT, el volumen 2 se extiende horizontalmente desde el volumen 1 una distancia de:",
                    opts = listOf(
                        "0,6 m",
                        "1,2 m",
                        "2,4 m",
                        "0,3 m"
                    ),
                    a = 0,
                    exp = "ITC-BT-27, apartado 2.1.3.a: «plano vertical paralelo situado a una distancia de 0,6 m».",
                    ref = "ITC-BT-27"
                ),
                Question(
                    q = "Según el REBT, el volumen 3 se extiende desde el volumen 2 una distancia de:",
                    opts = listOf(
                        "2,4 m",
                        "0,6 m",
                        "1,2 m",
                        "3,0 m"
                    ),
                    a = 0,
                    exp = "ITC-BT-27, apartado 2.1.4.a: «plano vertical paralelo situado a una distancia de éste de 2,4 m».",
                    ref = "ITC-BT-27"
                ),
                Question(
                    q = "Según el REBT, el volumen 3 puede incluir el espacio bajo la bañera si:",
                    opts = listOf(
                        "Es accesible solo mediante herramienta y tiene IPX4 mínimo",
                        "Tiene ventilación natural",
                        "Está a menos de 2,25 m",
                        "Está protegido por diferencial"
                    ),
                    a = 0,
                    exp = "ITC-BT-27, apartado 2.1.4: «accesible sólo mediante el uso de una herramienta siempre que el cierre [...] garantice una protección como mínimo IP X4».",
                    ref = "ITC-BT-27"
                ),
                Question(
                    q = "Según el REBT, cuando se utiliza MBTS la protección contra contactos directos se realiza mediante:",
                    opts = listOf(
                        "Barreras IP2X o aislamiento ensayado a 500 V",
                        "Interruptores automáticos",
                        "Fusibles calibrados",
                        "Transformadores de separación"
                    ),
                    a = 0,
                    exp = "ITC-BT-27, apartado 2.2: «barreras o envolventes con un grado de protección mínimo IP2X [...] o aislamiento capaz de soportar una tensión de ensayo de 500 V».",
                    ref = "ITC-BT-27"
                ),
                Question(
                    q = "Según el REBT, debe realizarse una conexión equipotencial local suplementaria en los volúmenes:",
                    opts = listOf(
                        "1, 2 y 3",
                        "Solo en el volumen 0",
                        "Solo en el volumen 3",
                        "Únicamente en el volumen 2"
                    ),
                    a = 0,
                    exp = "ITC-BT-27, apartado 2.2: «en los volúmenes 1, 2 y 3».",
                    ref = "ITC-BT-27"
                ),
                Question(
                    q = "Según el REBT, las canalizaciones metálicas de agua y gas deben:",
                    opts = listOf(
                        "Conectarse a la equipotencial local",
                        "Aislarse con PVC",
                        "Situarse fuera del volumen 3",
                        "Tener IPX7"
                    ),
                    a = 0,
                    exp = "ITC-BT-27, apartado 2.2: «Canalizaciones metálicas de los servicios de suministro y desagües (por ejemplo agua, gas)».",
                    ref = "ITC-BT-27"
                ),
                Question(
                    q = "Según el REBT, las bañeras metálicas pueden considerarse aisladas si la resistencia es como mínimo:",
                    opts = listOf(
                        "100 kΩ",
                        "50 kΩ",
                        "1 MΩ",
                        "10 kΩ"
                    ),
                    a = 0,
                    exp = "ITC-BT-27, apartado 2.2: «si la resistencia de aislamiento [...] es de cómo mínimo 100 kΩ».",
                    ref = "ITC-BT-27"
                ),
                Question(
                    q = "Según el REBT, todo equipo eléctrico incorporado en bañeras de hidromasaje debe cumplir:",
                    opts = listOf(
                        "UNE-EN 60.335-2-60",
                        "UNE 20.460-4-41",
                        "UNE 20315",
                        "UNE 20460-6-61"
                    ),
                    a = 0,
                    exp = "ITC-BT-27, apartado 3: «deberán cumplir los requisitos de la norma UNE-EN 60.335-2-60».",
                    ref = "ITC-BT-27"
                ),
                Question(
                    q = "Según el REBT, el grado de protección mínimo exigido en cajas de conexión bajo bañeras es:",
                    opts = listOf(
                        "IPX5",
                        "IPX4",
                        "IP2X",
                        "IPX7"
                    ),
                    a = 0,
                    exp = "ITC-BT-27, apartado 3: «un grado de protección mínimo IPX5».",
                    ref = "ITC-BT-27"
                ),
                Question(
                    q = "Según el REBT, para abrir las cajas de conexión en estos volúmenes:",
                    opts = listOf(
                        "Es necesario el uso de una herramienta",
                        "Debe hacerse sin tensión",
                        "Se requiere diferencial",
                        "Debe ser accesible manualmente"
                    ),
                    a = 0,
                    exp = "ITC-BT-27, apartado 3: «Para su apertura será necesario el uso de una herramienta».",
                    ref = "ITC-BT-27"
                ),
                Question(
                    q = "Según el REBT, no se admiten empalmes en los volúmenes salvo que:",
                    opts = listOf(
                        "Se realicen con cajas que cumplan IPX5",
                        "Se protejan con diferencial",
                        "Estén fuera del volumen 1",
                        "Sean empalmes soldados"
                    ),
                    a = 0,
                    exp = "ITC-BT-27, apartado 3: «No se admiten empalmes [...] salvo si estos se realizan con cajas que cumplan el requisito anterior».",
                    ref = "ITC-BT-27"
                ),
                Question(
                    q = "Según el REBT, ¿qué grado de protección mínimo debe garantizar el cierre del volumen debajo de la bañera accesible solo con herramienta?",
                    opts = listOf(
                        "IP X1",
                        "IP X4",
                        "IP 2X",
                        "IP 44"
                    ),
                    a = 1,
                    exp = "ITC-BT-27, apartado 2.1.4: Requisito para el espacio bajo la bañera en volumen 3. «siempre que el cierre de dicho volumen garantice una protección como mínimo IP X4.»",
                    ref = "ITC-BT-27"
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
,
                Question(
                    q = "Según el REBT, la puesta a tierra debe garantizar que las masas metálicas no presenten tensiones peligrosas y, además:",
                    opts = listOf(
                        "Permitir la circulación segura de corrientes de defecto o descarga atmosférica",
                        "Reducir la resistencia eléctrica del terreno por medios artificiales",
                        "Aumentar la capacidad de conducción de los electrodos enterrados",
                        "Eliminar la necesidad de medidas de protección adicionales"
                    ),
                    a = 0,
                    exp = "ITC-BT-18, apartado 1: la puesta a tierra debe limitar tensiones peligrosas y permitir el paso seguro de corrientes de defecto o atmosféricas.",
                    ref = "ITC-BT-18"
                ),
                Question(
                    q = "Según el REBT, una puesta a tierra consiste en la unión eléctrica directa de una parte de la instalación:",
                    opts = listOf(
                        "Mediante cualquier conductor protegido contra sobrecarga",
                        "Sin fusibles ni dispositivos de protección intermedios",
                        "Siempre a través de un transformador de aislamiento",
                        "Utilizando únicamente electrodos tipo pletina"
                    ),
                    a = 1,
                    exp = "ITC-BT-18, apartado 2: la conexión a tierra es directa, sin fusibles ni protección alguna.",
                    ref = "ITC-BT-18"
                ),
                Question(
                    q = "Según el REBT, los electrodos de tierra deben instalarse a una profundidad:",
                    opts = listOf(
                        "Siempre superior a 1 metro",
                        "No inferior a 0,50 m para evitar efectos climáticos adversos",
                        "Variable según el diámetro del electrodo",
                        "Dependiente exclusivamente del tipo de terreno"
                    ),
                    a = 1,
                    exp = "ITC-BT-18, apartado 3.1: la profundidad mínima es de 0,50 m.",
                    ref = "ITC-BT-18"
                ),
                Question(
                    q = "Según el REBT, las canalizaciones metálicas de otros servicios no deben utilizarse como tomas de tierra porque:",
                    opts = listOf(
                        "Pueden deteriorarse con el paso del tiempo",
                        "Podrían inducir tensiones peligrosas o fallos de seguridad",
                        "No cumplen con la resistividad mínima admisible",
                        "Carecen de continuidad eléctrica garantizada"
                    ),
                    a = 1,
                    exp = "ITC-BT-18, apartado 3.1: se prohíbe usarlas por razones de seguridad.",
                    ref = "ITC-BT-18"
                ),
                Question(
                    q = "Según el REBT, la sección del conductor de tierra enterrado debe cumplir:",
                    opts = listOf(
                        "Los valores mínimos establecidos en la tabla 1",
                        "Un valor equivalente al conductor de protección más pequeño",
                        "Siempre 25 mm² si es de cobre",
                        "La mitad de la sección del conductor neutro"
                    ),
                    a = 0,
                    exp = "ITC-BT-18, apartado 3.2 y tabla 1: establece las secciones mínimas de conductores enterrados.",
                    ref = "ITC-BT-18"
                ),
                Question(
                    q = "Según el REBT, el borne principal de tierra debe permitir:",
                    opts = listOf(
                        "La desconexión manual del electrodo sin herramientas",
                        "La conexión de todos los conductores de tierra, protección y equipotenciales",
                        "La medición remota mediante sistema digital",
                        "La derivación de corrientes funcionales por separado"
                    ),
                    a = 1,
                    exp = "ITC-BT-18, apartado 3.3: el borne principal debe unir todos los conductores asociados.",
                    ref = "ITC-BT-18"
                ),
                Question(
                    q = "Según el REBT, las conexiones del conductor de protección deben ser:",
                    opts = listOf(
                        "Intercaladas con dispositivos de corte para mantenimiento",
                        "Accesibles para verificación salvo en cajas selladas",
                        "Realizadas exclusivamente mediante soldadura exotérmica",
                        "Comprobadas cada tres años por un organismo de control"
                    ),
                    a = 1,
                    exp = "ITC-BT-18, apartado 3.4: las conexiones deben ser accesibles para comprobación.",
                    ref = "ITC-BT-18"
                ),
                Question(
                    q = "Según el REBT, el conductor de protección que no disponga de protección mecánica y no forma parte de la canalización de alimentación debe tener una sección mínima de:",
                    opts = listOf(
                        "2,5 mm² en cualquier caso",
                        "4 mm² si es de cobre",
                        "6 mm² si el circuito es monofásico",
                        "16 mm² si está enterrado"
                    ),
                    a = 1,
                    exp = "ITC-BT-18, apartado 3.4: mínimo 4 mm² sin protección mecánica.",
                    ref = "ITC-BT-18"
                ),
                Question(
                    q = "Según el REBT, un conductor CPN (PEN) solo puede emplearse en instalaciones fijas cuando:",
                    opts = listOf(
                        "Su sección sea al menos 6 mm² en cobre",
                        "Su sección sea al menos 10 mm² en cobre o aluminio",
                        "Se utilice exclusivamente en interiores",
                        "El neutro esté protegido por un diferencial"
                    ),
                    a = 1,
                    exp = "ITC-BT-18, apartado 7: cuando en las instalaciones fijas el conductor de protección tenga una sección al menos igual a 10 mm2, en cobre o aluminio, las funciones de conductor de protección y de conductor neutro pueden ser combinadas.",
                    ref = "ITC-BT-18"
                ),
                Question(
                    q = "Según el REBT, un conductor CPN concéntrico puede reducirse a 4 mm² solo si:",
                    opts = listOf(
                        "La línea es subterránea y trifásica",
                        "El cable es de cobre, tipo concéntrico y tiene conexiones duplicadas",
                        "La instalación es temporal",
                        "El electrodo de tierra tiene resistencia inferior a 5 Ω"
                    ),
                    a = 1,
                    exp = "ITC-BT-18, apartado 7: caso excepcional de 4 mm² con duplicación de continuidad.",
                    ref = "ITC-BT-18"
                ),
                Question(
                    q = "Según el REBT, la sección del conductor principal de equipotencialidad debe ser:",
                    opts = listOf(
                        "Igual a la sección del conductor de protección mayor",
                        "La mitad de la sección del conductor de protección mayor, con un mínimo de 6 mm²",
                        "El doble de la sección del conductor de protección",
                        "6 mm² únicamente si es enterrado"
                    ),
                    a = 1,
                    exp = "ITC-BT-18, apartado 8: sección ≥ 1/2 del mayor conductor de protección, mínimo 6 mm².",
                    ref = "ITC-BT-18"
                ),
                Question(
                    q = "Según el REBT, la tensión máxima de contacto permitida en locales conductores es de:",
                    opts = listOf(
                        "50 V",
                        "24 V",
                        "12 V",
                        "75 V"
                    ),
                    a = 1,
                    exp = "ITC-BT-18, apartado 9: 24 V en locales o emplazamientos conductores.",
                    ref = "ITC-BT-18"
                ),
                Question(
                    q = "Según el REBT, un electrodo cumple su función cuando su resistencia garantiza que:",
                    opts = listOf(
                        "La corriente de defecto no supere 30 mA",
                        "Las tensiones de contacto no excedan los valores de seguridad establecidos",
                        "La intensidad de cortocircuito sea inferior a la nominal",
                        "La caída de tensión sea menor del 1 %"
                    ),
                    a = 1,
                    exp = "ITC-BT-18, apartado 9: finalidad de la resistencia del electrodo.",
                    ref = "ITC-BT-18"
                ),
                Question(
                    q = "Según el REBT, se considera que dos tomas de tierra son independientes cuando:",
                    opts = listOf(
                        "Están separadas más de 10 m",
                        "Una de ellas no supera 50 V cuando por la otra circula la corriente máxima de defecto",
                        "Ambas tienen resistencias inferiores a 10 ohmios",
                        "Sus electrodos son de distinto tipo"
                    ),
                    a = 1,
                    exp = "ITC-BT-18, apartado 10: definición de independencia eléctrica.",
                    ref = "ITC-BT-18"
                ),
                Question(
                    q = "Según el REBT, para que la puesta a tierra de utilización sea independiente de la del centro de transformación debe verificarse, entre otras condiciones:",
                    opts = listOf(
                        "Que ambas resistencias sean iguales",
                        "Que no existan canalizaciones metálicas conductoras entre ambos puntos",
                        "Que la tensión de defecto no supere 100 V",
                        "Que los electrodos tengan la misma profundidad"
                    ),
                    a = 1,
                    exp = "ITC-BT-18, apartado 11.a: ausencia de canalizaciones metálicas conductoras.",
                    ref = "ITC-BT-18"
                ),
                Question(
                    q = "Según el REBT, la distancia mínima entre la toma de tierra del edificio y la del centro de transformación debe ser:",
                    opts = listOf(
                        "10 metros en cualquier terreno",
                        "15 metros para terrenos de resistividad inferior a 100 Ω·m",
                        "20 metros si se utilizan electrodos múltiples",
                        "La indicada en función de la caída de tensión"
                    ),
                    a = 1,
                    exp = "ITC-BT-18, apartado 11.b: ≥ 15 m si ρ < 100 Ω·m.",
                    ref = "ITC-BT-18"
                ),
                Question(
                    q = "Según el REBT, ¿qué profundidad mínima debe tener una toma de tierra para evitar que los efectos climáticos aumenten su resistencia?",
                    opts = listOf(
                        "0,20 m",
                        "0,30 m",
                        "0,50 m",
                        "1,00 m"
                    ),
                    a = 2,
                    exp = "ITC-BT-18, punto 3.1: la profundidad nunca será inferior a 0,50 m.",
                    ref = "ITC-BT-18"
                ),
                Question(
                    q = "Según el REBT, la revisión anual de una toma de tierra debe realizarse:",
                    opts = listOf(
                        "En cualquier época del año",
                        "Cuando el terreno esté más seco",
                        "Únicamente tras tormentas o descargas",
                        "Después de haber sustituido el electrodo"
                    ),
                    a = 1,
                    exp = "ITC-BT-18, apartado 12: revisión anual en la época de mayor sequedad del terreno.",
                    ref = "ITC-BT-18"
                ),
                Question(
                    q = "Según el REBT, en terrenos desfavorables los electrodos deben ponerse al descubierto cada cinco años con el fin de:",
                    opts = listOf(
                        "Reducir la resistividad del terreno",
                        "Inspeccionar su estado y el de los conductores de enlace",
                        "Medir la corriente de fuga del electrodo",
                        "Garantizar la continuidad del conductor de protección"
                    ),
                    a = 1,
                    exp = "ITC-BT-18, apartado 12: inspección quinquenal de electrodos en terrenos desfavorables.",
                    ref = "ITC-BT-18"
                ),
                Question(
                    q = "Según el REBT, cuando se utilicen dispositivos de control de tensión de defecto, la toma de tierra auxiliar debe instalarse:",
                    opts = listOf(
                        "A cualquier distancia siempre que la resistencia sea inferior a 10 ohmios",
                        "A una distancia suficiente para quedar fuera de la zona de influencia de la toma de tierra principal",
                        "Justo al lado del electrodo principal para garantizar la equipotencialidad",
                        "Únicamente en lugares con resistividad superior a 100 Ω·m"
                    ),
                    a = 1,
                    exp = "ITC-BT-18, apartado 4.1: la toma de tierra auxiliar del dispositivo debe ser eléctricamente independiente, situándose fuera de la zona de influencia de la toma principal.",
                    ref = "ITC-BT-18"
                )
            )
        ),
        "especiales" to ModuleDefinition(
            id = "especiales",
            label = "Locales Especiales, Garajes y Vehículo Eléctrico (ITC-28/29/52)",
            icon = "⚡",
            color = "#f5b041",
            questions = EspecialesQuestionsPart1.QUESTIONS +
                EspecialesQuestionsPart2.QUESTIONS +
                EspecialesQuestionsPart3.QUESTIONS +
                EspecialesQuestionsPart4.QUESTIONS +
                EspecialesQuestionsPart5.QUESTIONS
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
                ),
                Question(
                    q = "¿Cuál es la altura mínima reglamentaria que deben guardar los conductores desnudos de una red aérea de baja tensión sobre calles o carreteras transitables por vehículos?",
                    opts = listOf(
                        "6 metros",
                        "5 metros",
                        "4 metros",
                        "7 metros"
                    ),
                    a = 0,
                    exp = "Según la ITC-BT-06 apartado 3.1, en el cruce de vías de comunicación, calles o carreteras transitables por vehículos, la altura mínima de los conductores sobre el suelo en las condiciones más desfavorables de flecha no será inferior a 6 metros.",
                    ref = "ITC-BT-06 §3.1"
                ),
                Question(
                    q = "En redes aéreas de distribución de baja tensión no transitables por vehículos, ¿cuál es la distancia vertical mínima al suelo?",
                    opts = listOf(
                        "4 metros",
                        "5 metros",
                        "6 metros",
                        "3,5 metros"
                    ),
                    a = 1,
                    exp = "La ITC-BT-06 par. 3.1 establece que en zonas no transitables por vehículos, los conductores aéreos mantendrán una altura mínima sobre el terreno de 5 metros para garantizar la seguridad frente a contactos accidentales.",
                    ref = "ITC-BT-06 §3.1"
                ),
                Question(
                    q = "¿Qué sección mínima deben tener los conductores de cobre en redes aéreas de baja tensión tensadas sobre apoyos?",
                    opts = listOf(
                        "6 mm²",
                        "16 mm²",
                        "10 mm²",
                        "25 mm²"
                    ),
                    a = 2,
                    exp = "Conforme a la tabla de conductores de la ITC-BT-06 par. 2.1, la sección mínima de los conductores de cobre para redes aéreas de distribución tensadas entre apoyos es de 10 mm², asegurando resistencia mecánica y capacidad eléctrica.",
                    ref = "ITC-BT-06 §2.1"
                ),
                Question(
                    q = "¿Cuál es el coeficiente de seguridad mecánico mínimo exigido a los conductores de una línea aérea en la hipótesis más desfavorable de tracción?",
                    opts = listOf(
                        "1,5",
                        "2,0",
                        "3,0",
                        "2,5"
                    ),
                    a = 3,
                    exp = "La ITC-BT-06 par. 2.2 prescribe que los conductores de líneas aéreas se calcularán con un coeficiente de seguridad mecánico no inferior a 2,5 respecto a su carga de rotura para la hipótesis más desfavorable de viento o hielo.",
                    ref = "ITC-BT-06 §2.2"
                ),
                Question(
                    q = "En cables trenzados en haz con neutro fiador para redes aéreas, ¿de qué material debe ser dicho fiador portante?",
                    opts = listOf(
                        "Aleación de aluminio (Almelec)",
                        "Cobre recocido",
                        "Acero galvanizado desnudo",
                        "Aluminio puro al 99%"
                    ),
                    a = 0,
                    exp = "La ITC-BT-06 par. 2.3 establece que los cables en haz trenzado con neutro fiador portante utilizarán para el conductor neutro una aleación de aluminio de alta resistencia mecánica (tipo Almelec), que asume la tracción de la línea.",
                    ref = "ITC-BT-06 §2.3"
                ),
                Question(
                    q = "En el cruce de una línea aérea de baja tensión con una línea de alta tensión, ¿cómo deben disponerse las líneas?",
                    opts = listOf(
                        "La de BT debe cruzar siempre por encima de la de AT",
                        "La de BT debe cruzar siempre por debajo de la de AT",
                        "Pueden cruzarse a la misma altura en apoyos comunes",
                        "Es indiferente según la topografía del terreno"
                    ),
                    a = 1,
                    exp = "La ITC-BT-06 par. 4.1 estipula que en cruzamientos entre líneas aéreas de distinta tensión, las líneas de baja tensión deberán pasar siempre por debajo de las líneas de alta tensión, manteniendo las distancias de aislamiento prescritas.",
                    ref = "ITC-BT-06 §4.1"
                ),
                Question(
                    q = "Para conductores desnudos en apoyos con vano no superior a 40 metros, ¿cuál es la separación mínima horizontal entre conductores?",
                    opts = listOf(
                        "0,20 metros",
                        "0,30 metros",
                        "0,40 metros",
                        "0,60 metros"
                    ),
                    a = 2,
                    exp = "Según la ITC-BT-06 par. 3.2, la separación mínima entre conductores desnudos fijados sobre apoyos para vanos de hasta 40 metros será de 0,40 metros, evitando acercamientos peligrosos por oscilación producida por el viento.",
                    ref = "ITC-BT-06 §3.2"
                ),
                Question(
                    q = "¿Qué altura mínima sobre el suelo deben guardar los cables aislados posados sobre fachadas en zonas accesibles?",
                    opts = listOf(
                        "1,5 metros",
                        "2,0 metros",
                        "3,0 metros",
                        "2,5 metros"
                    ),
                    a = 3,
                    exp = "La ITC-BT-06 par. 3.3 fija que los cables aislados posados sobre fachada o muros se dispondrán a una altura mínima de 2,5 metros respecto al suelo para quedar fuera del alcance normal de las personas y vehículos.",
                    ref = "ITC-BT-06 §3.3"
                ),
                Question(
                    q = "¿En qué puntos de la red aérea de distribución en baja tensión debe ponerse a tierra el conductor neutro?",
                    opts = listOf(
                        "En el centro de transformación y en los extremos de ramas superiores a 200 m",
                        "Únicamente en el cuadro del usuario final",
                        "Solo en el apoyo central de la línea",
                        "En todos y cada uno de los apoyos sin excepción"
                    ),
                    a = 0,
                    exp = "La ITC-BT-06 par. 2.4 en concordancia con la ITC-BT-08 exige que el conductor neutro de distribución aérea esté puesto a tierra en el centro de transformación y, además, en los extremos de líneas cuya longitud supere los 200 metros.",
                    ref = "ITC-BT-06 §2.4"
                ),
                Question(
                    q = "En conductores de aluminio para redes aéreas tensadas, ¿cuál es la sección mínima reglamentaria?",
                    opts = listOf(
                        "10 mm²",
                        "16 mm²",
                        "25 mm²",
                        "35 mm²"
                    ),
                    a = 1,
                    exp = "La ITC-BT-06 par. 2.1 determina que la sección mínima admisible para conductores de aluminio en líneas aéreas tensadas sobre apoyos es de 16 mm², garantizando la integridad mecánica y térmica ante cortocircuitos.",
                    ref = "ITC-BT-06 §2.1"
                ),
                Question(
                    q = "¿Qué tensión asignada mínima deben tener los cables aislados en haz trenzado (tipo RZ) utilizados en redes aéreas de BT según la ITC-BT-06?",
                    opts = listOf(
                        "0,6/1 kV",
                        "450/750 V",
                        "300/500 V",
                        "1,8/3 kV"
                    ),
                    a = 0,
                    exp = "La ITC-BT-06 apartado 2.1 exige que los cables aislados para redes aéreas tensadas o posadas tengan una tensión asignada de 0,6/1 kV con aislamiento de polietileno reticulado (XLPE). La trampa habitual es pensar en 450/750 V, admisible en interiores de edificios pero totalmente prohibido en líneas de distribución aérea a la intemperie.",
                    ref = "ITC-BT-06"
                ),
                Question(
                    q = "¿Cuál es la distancia mínima que debe mantenerse entre un cable aislado posado sobre fachada y una ventana o balcón accesible según la ITC-BT-06?",
                    opts = listOf(
                        "0,05 m en cualquier dirección",
                        "0,30 m por encima de la ventana y 1,00 m por debajo o laterales",
                        "2,50 m en todo el perímetro",
                        "No se exige separación si el cable es trenzado"
                    ),
                    a = 1,
                    exp = "Según la ITC-BT-06 apartado 3.3, los cables posados sobre fachada deben situarse fuera del alcance de las personas, estableciendo al menos 0,30 m por encima de huecos practicables y 1,00 m por debajo o en sus laterales. Responder 0,05 m o creer que el aislamiento exime de distancia ignora el riesgo de manipulación indebida desde el balcón.",
                    ref = "ITC-BT-06"
                ),
                Question(
                    q = "En cables en haz trenzado con neutro fiador portante tensados sobre apoyos, ¿cuál es la sección normalizada más habitual del neutro de Almelec según la ITC-BT-06?",
                    opts = listOf(
                        "16 mm²",
                        "25 mm²",
                        "54,6 mm²",
                        "95 mm²"
                    ),
                    a = 2,
                    exp = "La ITC-BT-06 apartado 2.1 y la norma UNE 21030 fijan la sección de 54,6 mm² de aleación de aluminio-magnesio-silicio (Almelec) como la sección portante estándar para el conductor neutro en redes aéreas trenzadas con fiador. Confundir este valor con 16 mm² o 25 mm² es común porque estas últimas son secciones típicas de los conductores de fase de aluminio.",
                    ref = "ITC-BT-06"
                ),
                Question(
                    q = "¿Cuál es el coeficiente de seguridad mecánico mínimo que debe aplicarse a los apoyos de hormigón armado o metálicos en líneas aéreas según la ITC-BT-06?",
                    opts = listOf(
                        "1,0 sin sobrecarga",
                        "3,5 en cualquier caso",
                        "5,0 exclusivamente",
                        "1,5 respecto a su límite elástico o carga de rotura en condiciones normales de servicio"
                    ),
                    a = 3,
                    exp = "Conforme a la ITC-BT-06 apartado 2.2, los postes metálicos o de hormigón se dimensionan con un coeficiente de seguridad mecánico no inferior a 1,5 para la hipótesis simultánea más desfavorable de viento o tracciones unilaterales. Pensar en coeficientes de 1,0 olvida los márgenes de seguridad estructurales ante temporales.",
                    ref = "ITC-BT-06"
                ),
                Question(
                    q = "En un cruzamiento entre una red aérea de baja tensión y una línea aérea de telecomunicaciones, ¿qué disposición relativa debe mantenerse según la ITC-BT-06?",
                    opts = listOf(
                        "Los cables de energía eléctrica de BT deben pasar por encima de las líneas de telecomunicación",
                        "Las líneas de telecomunicación deben cruzar siempre por encima de las eléctricas",
                        "Deben amarrarse al mismo aislador sin separación",
                        "Es obligatorio enterrar la línea de telecomunicación en todos los casos"
                    ),
                    a = 0,
                    exp = "La ITC-BT-06 apartado 4.2 estipula que en cruces aéreos los conductores de energía en baja tensión deben discurrir siempre por encima de las líneas de telecomunicación. La trampa habitual es pensar que las corrientes débiles van por encima; la normativa exige la red eléctrica arriba para evitar que una caída del cable de telecomunicaciones arrastre o contacte con fases energizadas.",
                    ref = "ITC-BT-06"
                ),
                Question(
                    q = "¿Cuál es la distancia vertical mínima en el punto de cruce entre los cables aéreos de BT y los conductores de una línea de telecomunicaciones según la ITC-BT-06?",
                    opts = listOf(
                        "0,10 metros",
                        "0,50 metros",
                        "2,00 metros",
                        "5,00 metros"
                    ),
                    a = 1,
                    exp = "Según el apartado 4.2 de la ITC-BT-06, en el cruce sin apoyo común entre cables aéreos de baja tensión y líneas de telecomunicaciones, la distancia vertical mínima no será inferior a 0,50 m en las condiciones más desfavorables de flecha. La confusión con 0,10 m procede de distancias en fachadas o interiores, resultando totalmente insuficiente ante oscilaciones por viento en vanos aéreos.",
                    ref = "ITC-BT-06"
                ),
                Question(
                    q = "En zonas rurales donde se justifique el uso excepcional de conductores desnudos sobre apoyos, ¿dónde está terminantemente prohibido su tendido según la ITC-BT-06?",
                    opts = listOf(
                        "En campos abiertos no transitados",
                        "A lo largo de caminos de tierra privados",
                        "Sobre fachadas o tejados de edificios habitados",
                        "En apoyos compartidos con alumbrado público"
                    ),
                    a = 2,
                    exp = "La ITC-BT-06 apartado 3.2 prohíbe de forma tajante el tendido de conductores desnudos sobre tejados, azoteas transitables o fachadas de inmuebles habitados. Solo se admite cable aislado posado o tensado en fachada; los conductores desnudos en fachadas representan un peligro mortal inaceptable de electrocución para los ocupantes y labores de albañilería.",
                    ref = "ITC-BT-06"
                ),
                Question(
                    q = "¿Qué temperatura máxima de los conductores se toma como hipótesis reglamentaria para calcular la flecha máxima por temperatura según la ITC-BT-06?",
                    opts = listOf(
                        "25 ºC",
                        "40 ºC",
                        "70 ºC",
                        "50 ºC sin viento ni hielo"
                    ),
                    a = 3,
                    exp = "Para el cálculo de la flecha máxima por temperatura en líneas aéreas, la ITC-BT-06 apartado 2.2 prescribe considerar una temperatura ambiente máxima de 50 ºC en ausencia de viento y hielo. La trampa habitual es marcar 70 ºC o 90 ºC (que son temperaturas límites de los aislamientos bajo carga eléctrica), o 40 ºC que es la referencia de disipación térmica ambiente, no la hipótesis mecánica de flecha reglamentaria.",
                    ref = "ITC-BT-06"
                ),
                Question(
                    q = "¿Qué altura mínima sobre el suelo deben mantener los cables aislados posados sobre fachada en zonas de acceso de vehículos según la ITC-BT-06?",
                    opts = listOf(
                        "3,00 metros",
                        "2,00 metros",
                        "1,80 metros",
                        "6,00 metros"
                    ),
                    a = 0,
                    exp = "La ITC-BT-06 apartado 3.3 determina que mientras la altura general para cables posados en fachada en zonas peatonales es de 2,5 m, si existen pasos de carruajes, garajes o acceso de vehículos la altura mínima se eleva a 3,00 m para evitar impactos con cargas elevadas o furgonetas. Pensar que 2,5 m basta en cualquier tramo de fachada es el error habitual de diseño.",
                    ref = "ITC-BT-06"
                ),
                Question(
                    q = "¿Cómo deben ejecutarse las conexiones y derivaciones en cables aislados en haz trenzado tensados según la ITC-BT-06?",
                    opts = listOf(
                        "Retorciendo manualmente los hilos de aluminio y encintando",
                        "Mediante conectores de perforación de aislamiento estancos con tornillo de apriete dinamométrico",
                        "Soldando con estaño directamente sobre el aislamiento de polietileno",
                        "Utilizando bornes cerámicos al aire libre sin protección IP"
                    ),
                    a = 1,
                    exp = "Conforme a la ITC-BT-06 punto 2.1, las derivaciones en cables trenzados aéreos se realizan exclusivamente mediante conectores estancos de perforación de aislamiento (con cabeza de rotura a par calibrado). Esto asegura el contacto eléctrico perfecto sin pelar ni dañar los hilos conductores y mantiene la estanqueidad contra el agua y la intemperie (IPX8/IP67).",
                    ref = "ITC-BT-06"
                ),
                Question(
                    q = "¿Cada cuánta distancia como máximo debe conectarse a tierra el conductor neutro de una red aérea de distribución según la ITC-BT-06?",
                    opts = listOf(
                        "Cada 50 metros",
                        "Cada 100 metros",
                        "Al menos cada 500 metros a lo largo de la línea",
                        "Solo en el transformador de origen sin más puestas a tierra"
                    ),
                    a = 2,
                    exp = "La ITC-BT-06 apartado 2.4 en concordancia con la ITC-BT-08 exige que el conductor neutro se conecte a tierra en el centro de transformación de distribución y, a lo largo del trazado de las líneas aéreas, con una cadencia máxima de una puesta a tierra cada 500 m, además de en los extremos de líneas de más de 200 m, para estabilizar el potencial de neutro ante cargas desequilibradas.",
                    ref = "ITC-BT-06"
                ),
                Question(
                    q = "¿Cuál es el coeficiente de seguridad mecánico mínimo exigido para los apoyos de madera en redes aéreas de BT según la ITC-BT-06?",
                    opts = listOf(
                        "1,2",
                        "1,5",
                        "1,8",
                        "2 respecto a su carga de rotura por flexión"
                    ),
                    a = 3,
                    exp = "Según la ITC-BT-06 pto. 2.2, los postes de madera deben calcularse con un coeficiente de seguridad mecánico no inferior a 2 para resistir los esfuerzos máximos de flexión reglamentarios. La trampa habitual es elegir 1,5, que es el coeficiente aplicable a apoyos de hormigón armado o metálicos; la madera requiere un coeficiente mayor (2) debido a la heterogeneidad natural del material y su degradación biológica.",
                    ref = "ITC-BT-06"
                ),
                Question(
                    q = "En cruzamientos aéreos de ríos o canales navegables por embarcaciones, ¿cómo se determina la altura mínima de los conductores según la ITC-BT-06?",
                    opts = listOf(
                        "Gálibo náutico máximo de la vía navegable más una distancia de seguridad de al menos 1 metro",
                        "Siempre exactamente 4,5 metros",
                        "No se permite cruzar vías navegables en aéreo, debe ser obligatoriamente submarino",
                        "Igual a la altura de la orilla más 50 cm"
                    ),
                    a = 0,
                    exp = "La ITC-BT-06 apartado 4.4 señala que en cauces fluviales o canales navegables, la altura de los conductores sobre el nivel máximo de las aguas se fijará sumando al gálibo máximo autorizado para las embarcaciones una holgura libre de seguridad de al menos 1 metro. Suponer una cota fija de 4,5 m es un grave error que solo aplica a ríos o arroyos no navegables.",
                    ref = "ITC-BT-06"
                ),
                Question(
                    q = "En cruzamientos aéreos de cursos de agua o ríos no navegables, ¿cuál es la altura mínima reglamentaria de los conductores según la ITC-BT-06?",
                    opts = listOf(
                        "2,50 metros",
                        "4,50 metros sobre la cota del nivel máximo de las aguas",
                        "6,00 metros obligatoriamente",
                        "3,00 metros"
                    ),
                    a = 1,
                    exp = "La ITC-BT-06 apartado 4.4 fija en 4,50 m la altura mínima que deben mantener los cables aéreos de baja tensión sobre el nivel máximo alcanzable por las aguas en ríos, arroyos o canales no navegables. Se suele confundir con 6 m (altura mínima en cruce de calzadas transitables) o con 2,5 m (altura mínima en fachadas peatonales).",
                    ref = "ITC-BT-06"
                ),
                Question(
                    q = "¿Qué separación mínima debe guardarse entre un cable aéreo de baja tensión posado en fachada y una tubería vista de gas según la ITC-BT-06?",
                    opts = listOf(
                        "0,02 metros",
                        "0,10 metros",
                        "0,20 metros",
                        "1,00 metro"
                    ),
                    a = 2,
                    exp = "En paralelismo o proximidad sobre fachadas entre cables eléctricos de BT y conducciones exteriores de gas canalizado, la ITC-BT-06 apartado 3.3 prescribe una distancia mínima de separación de 0,20 m. Marcar 0,10 m o 0,02 m no cumple la distancia de seguridad preventiva para evitar que posibles arcos eléctricos o chispas perforen la canalización de combustible gaseoso.",
                    ref = "ITC-BT-06"
                ),
                Question(
                    q = "¿Qué dispositivo de protección y seccionamiento aéreo se utiliza habitualmente en redes aéreas trenzadas para dividir circuitos en sectores según la ITC-BT-06?",
                    opts = listOf(
                        "Interruptores automáticos domésticos en cajas de cartón",
                        "Interruptores diferenciales estándar sin envolvente estanca",
                        "Bornas de regleta atornilladas al aire",
                        "Seccionadores fusibles aéreos unipolares para bases de corte en carga (tipo BTVC)"
                    ),
                    a = 3,
                    exp = "Conforme a la ITC-BT-06 apartado 2.1 y especificaciones de distribución, la sectorización y protección contra cortocircuitos en líneas aéreas tensadas se ejecuta mediante seccionadores fusibles aéreos estancos (Bases Tripolares Verticales o seccionadores unipolares BTVC) con fusibles de alto poder de corte (NH/cuchillas), maniobrables mediante pértiga aislante desde el suelo o cesta.",
                    ref = "ITC-BT-06"
                ),
                Question(
                    q = "¿Cuál es la hipótesis de viento reglamentaria para el cálculo mecánico de conductores de BT en zona eólica tipo B según la ITC-BT-06?",
                    opts = listOf(
                        "Presión de viento de 60 daN/m² a una temperatura de 15 ºC",
                        "Presión de 10 daN/m² a 50 ºC",
                        "Viento de 120 daN/m² a -20 ºC",
                        "No se considera viento si los cables van trenzados"
                    ),
                    a = 0,
                    exp = "La ITC-BT-06 apartado 2.2 remite a las hipótesis mecánicas oficiales de líneas eléctricas aéreas, fijando para la hipótesis de viento una presión de 50 o 60 daN/m² (según zona climática) actuando perpendicularmente sobre la superficie proyectada del haz a una temperatura de 15 ºC. Considerar que los cables trenzados están exentos de acción del viento es un grave error de cálculo mecánico.",
                    ref = "ITC-BT-06"
                ),
                Question(
                    q = "En apoyos de fin de línea o esquinas pronunciadas en redes aéreas tensadas, ¿qué elemento mecánico debe instalarse para equilibrar las tracciones según la ITC-BT-06?",
                    opts = listOf(
                        "Cintas adhesivas reforzadas fijadas al bordillo",
                        "Tirantes con cable de acero y tensor aislante, o tornapuntas rígidas",
                        "Un contrapeso de plomo colgado del cable eléctrico",
                        "No se permite ningún refuerzo en apoyos de esquina"
                    ),
                    a = 1,
                    exp = "La ITC-BT-06 apartado 2.2 estipula que en apoyos terminales (fin de línea) o de ángulo donde las tracciones de los conductores originan momentos de vuelco importantes, los postes deben reforzarse mediante tirantes de acero anclados al terreno (con aislador tensor intercalado) o tornapuntas de compresión para evitar la deformación o caída del apoyo.",
                    ref = "ITC-BT-06"
                ),
                Question(
                    q = "¿Cuál es la sección mínima permitida para los conductores de cobre en redes aéreas tensadas sobre apoyos según la ITC-BT-06?",
                    opts = listOf(
                        "2,5 mm²",
                        "6 mm²",
                        "10 mm²",
                        "25 mm²"
                    ),
                    a = 2,
                    exp = "La ITC-BT-06 apartado 2.1 establece en 10 mm² la sección mínima de los conductores de cobre para redes aéreas tensadas sobre postes. La trampa habitual es marcar 6 mm² (que es la sección mínima para cobre en redes subterráneas según ITC-BT-07), pero en líneas aéreas tensadas se requiere un calibre mayor (10 mm²) para soportar las tensiones mecánicas de tracción continuada y la acción del viento.",
                    ref = "ITC-BT-06"
                ),
                Question(
                    q = "En caso de instalar cables posados en fachada por debajo de la cota de 2,5 m en zonas accesibles, ¿qué protección suplementaria exige la ITC-BT-06?",
                    opts = listOf(
                        "Doble capa de cinta aislante de colores",
                        "Pintar el cable con pintura antitérmica",
                        "No se permite en ningún caso pasar por debajo de 2,5 metros",
                        "Protección mecánica rígida de acero o plástico resistente contra impactos (mínimo IK08) hasta 2,5 m de altura"
                    ),
                    a = 3,
                    exp = "Según la ITC-BT-06 punto 3.3, cuando los cables en fachada deban discurrir a una altura inferior a 2,5 m del suelo (por ejemplo en bajantes hacia cajas generales de protección), deben protegerse obligatoriamente mediante tubos o canales rígidos de alta resistencia mecánica contra impactos para evitar deterioros por vandalismo, rozamiento o golpes mecánicos.",
                    ref = "ITC-BT-06"
                ),
                // --- ITC-BT-07 Redes subterráneas para distribución en baja tensión ---
                Question(
                    q = "¿Cuál es la profundidad mínima reglamentaria a la que debe enterrarse un cable de distribución de baja tensión bajo acera según la ITC-BT-07?",
                    opts = listOf(
                        "0,60 metros",
                        "0,80 metros",
                        "0,50 metros",
                        "1,00 metro"
                    ),
                    a = 0,
                    exp = "La ITC-BT-07 establece en su apartado 2.1 que los cables directamente enterrados deben colocarse a una profundidad mínima de 0,60 m en aceras o zonas peatonales. El valor de 0,80 m es la trampa típica, pero este se exige exclusivamente bajo calzada o zonas con tráfico rodado de vehículos para soportar las cargas mecánicas transmitidas por el pavimento.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "¿Qué profundidad mínima exige la ITC-BT-07 para zanjas con cables de distribución bajo calzadas o zonas de tráfico rodado?",
                    opts = listOf(
                        "0,60 metros",
                        "0,80 metros",
                        "1,20 metros",
                        "0,40 metros"
                    ),
                    a = 1,
                    exp = "Según la ITC-BT-07 apartado 2.1, en calzadas y cruces de vías con paso de tráfico pesado, la cota mínima de enterramiento desde la superficie hasta el lecho del cable es de 0,80 m. La confusión habitual de 0,60 m corresponde al tendido bajo acera sin paso de vehículos, que resulta insuficiente para soportar las sobrecargas mecánicas continuadas del tráfico.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "¿Qué sección mínima deben tener los conductores de aluminio en redes de distribución subterráneas de baja tensión?",
                    opts = listOf(
                        "6 mm²",
                        "10 mm²",
                        "16 mm²",
                        "25 mm²"
                    ),
                    a = 2,
                    exp = "La ITC-BT-07 (apartado 1.2) fija una sección nominal mínima de 16 mm² cuando los conductores empleados en redes de distribución subterránea son de aluminio. La trampa habitual es marcar 6 mm², pero dicha sección mínima de 6 mm² es aplicable únicamente a conductores de cobre; para aluminio siempre se exige un calibre mayor por razones de resistencia mecánica y conductividad.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "¿Cuál es la sección mínima obligatoria para los conductores de cobre en redes de distribución subterránea de BT?",
                    opts = listOf(
                        "1,5 mm²",
                        "2,5 mm²",
                        "4 mm²",
                        "6 mm²"
                    ),
                    a = 3,
                    exp = "De acuerdo con la ITC-BT-07 apartado 1.2, la sección mínima permitida para conductores de cobre en redes subterráneas de distribución es de 6 mm². Es habitual la confusión con 16 mm², pero dicha sección es el mínimo exigido cuando se utiliza aluminio. Las secciones inferiores como 2,5 mm² o 4 mm² son admisibles en instalaciones interiores pero no en distribución enterrada.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "¿A qué distancia por encima del cable enterrado debe colocarse la cinta de señalización en una zanja según la ITC-BT-07?",
                    opts = listOf(
                        "A un mínimo de 0,20 m por encima de la protección mecánica",
                        "Directamente sobre el lecho de arena",
                        "A 5 cm del cable",
                        "A ras del pavimento final"
                    ),
                    a = 0,
                    exp = "Conforme a la ITC-BT-07 apartado 2.1, la malla o cinta de señalización se dispone a una distancia mínima de 0,20 m (20 cm) por encima de las placas o elementos de protección mecánica del cable, quedando próxima a la superficie. La confusión radica en colocarla en contacto con la arena o el cable, lo que anularía su función de advertencia temprana frente a excavaciones mecánicas agresivas.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "¿Cuál es la tensión asignada mínima exigible a los cables utilizados en redes subterráneas de distribución según la ITC-BT-07?",
                    opts = listOf(
                        "450/750 V",
                        "0,6/1 kV",
                        "300/500 V",
                        "1,8/3 kV"
                    ),
                    a = 1,
                    exp = "La ITC-BT-07 estipula en su apartado 1.2 que los cables aislados para canalizaciones subterráneas de distribución de baja tensión deben tener una tensión asignada mínima de 0,6/1 kV. La trampa común es pensar en 450/750 V, que es admisible en instalaciones interiores de edificios bajo tubo (ITC-BT-19 e ITC-BT-20), pero completamente inaceptable y antirreglamentario en redes enterradas exteriores.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "¿Qué espesor mínimo debe tener el lecho de arena sobre el que reposan los cables directamente enterrados en zanja según la ITC-BT-07?",
                    opts = listOf(
                        "2 cm",
                        "15 cm",
                        "5 cm",
                        "20 cm"
                    ),
                    a = 2,
                    exp = "El apartado 2.1 de la ITC-BT-07 requiere que el fondo de la excavación se cubra con un lecho de arena limpia de río o tierra cribada de al menos 5 cm (0,05 m) de espesor antes de depositar el cable. Confundir este valor con 10 cm o 15 cm es usual porque sobre el cable se vierte otra capa posterior de 10 cm antes de colocar las rasillas o placas protectoras.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "¿Qué espesor mínimo de arena debe cubrir el cable directamente enterrado antes de colocar la protección mecánica en zanja?",
                    opts = listOf(
                        "5 cm",
                        "20 cm",
                        "30 cm",
                        "10 cm"
                    ),
                    a = 3,
                    exp = "La ITC-BT-07 apartado 2.1 indica que, una vez tendido el cable sobre el lecho de 5 cm, se debe recubrir con otra capa de arena o tierra cribada de al menos 10 cm (0,10 m) de espesor antes de colocar las placas o rasillas protectoras. Confundir esta capa superior con los 5 cm del lecho inferior es un fallo típico de examen.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "¿Cuál es la distancia mínima de separación en los cruzamientos subterráneos entre cables de baja tensión y conducciones de agua potable?",
                    opts = listOf(
                        "0,20 metros",
                        "0,05 metros",
                        "0,10 metros",
                        "0,50 metros"
                    ),
                    a = 0,
                    exp = "Según la ITC-BT-07 apartado 2.2, la distancia mínima de separación que debe respetarse en el cruce entre una línea subterránea de baja tensión y una tubería de agua es de 0,20 m. La trampa frecuente es responder 0,10 m, distancia que solo es válida en cruces entre cables de baja tensión entre sí, resultando insuficiente frente a riesgos de humedad o intervenciones en redes de fontanería.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "En un cruzamiento subterráneo entre cables eléctricos de BT y tuberías de agua, ¿qué disposición relativa debe procurarse según la ITC-BT-07?",
                    opts = listOf(
                        "El cable eléctrico siempre debe ir por debajo de la tubería de agua",
                        "La canalización eléctrica debe situarse preferentemente por encima de la tubería de agua",
                        "Ambos servicios deben compartir obligatoriamente el mismo prisma",
                        "La tubería de agua debe alojarse dentro del mismo tubo que el cable"
                    ),
                    a = 1,
                    exp = "La ITC-BT-07 (apartado 2.2) señala que, siempre que sea técnicamente viable, los cables de baja tensión se situarán por encima de las conducciones de agua potable. Se comete el error de creer que el agua va arriba por gravedad, pero situar el agua por debajo evita que posibles fugas, roturas o condensaciones inunden directamente el lecho o los conductos de la instalación eléctrica.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "¿Cuál es la distancia mínima que debe mantenerse en un cruzamiento entre cables subterráneos de baja tensión y líneas subterráneas de telecomunicación?",
                    opts = listOf(
                        "0,05 metros",
                        "0,50 metros",
                        "0,20 metros",
                        "0,80 metros"
                    ),
                    a = 2,
                    exp = "La ITC-BT-07 apartado 2.2 fija una separación mínima de 0,20 m en los cruces entre conductores de energía en baja tensión y canalizaciones de telecomunicación. La opción de 0,05 m es incorrecta e insegura al aumentar interferencias electromagnéticas, mientras que distancias como 0,50 m exceden el requisito reglamentario general cuando no existen tubos o tabiques de protección adicionales.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "¿A qué distancia mínima deben discurrir en paralelismo los cables de BT respecto a tuberías de gas de baja presión según la ITC-BT-07?",
                    opts = listOf(
                        "0,10 metros",
                        "0,50 metros",
                        "1,00 metro",
                        "0,20 metros"
                    ),
                    a = 3,
                    exp = "En paralelismos con conducciones de gas canalizado a baja o media presión, la ITC-BT-07 apartado 2.2 fija una distancia mínima de 0,20 m. La trampa habitual es contestar 0,10 m (que únicamente aplica a cables eléctricos de BT entre sí), olvidando que frente a canalizaciones de combustible se requiere mayor aislamiento preventivo contra fugas.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "¿Qué separación mínima debe respetarse entre dos cables subterráneos de baja tensión que se cruzan entre sí según la ITC-BT-07?",
                    opts = listOf(
                        "0,10 metros",
                        "0,20 metros",
                        "0,50 metros",
                        "0,05 metros"
                    ),
                    a = 0,
                    exp = "La ITC-BT-07 apartado 2.2 exige una distancia mínima de 0,10 m en los cruzamientos entre cables de baja tensión pertenecientes a distintos circuitos. Suele confundirse con 0,20 m, que es la distancia mínima reglamentaria requerida frente a conducciones de agua, gas o telecomunicaciones, o con 0,25 m frente a cables de alta tensión.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "¿Qué distancia mínima debe mantenerse en el cruce subterráneo entre cables de baja tensión y cables de alta tensión (hasta 30 kV) según la ITC-BT-07?",
                    opts = listOf(
                        "0,10 metros",
                        "0,25 metros",
                        "0,50 metros",
                        "1,00 metro"
                    ),
                    a = 1,
                    exp = "La ITC-BT-07 apartado 2.2 determina que la separación mínima en el cruce entre un cable de BT y uno de AT debe ser de 0,25 m, o estar separados por una hilada de ladrillo u hormigón continuo. Muchos aspirantes marcan erróneamente 0,10 m, que es la distancia mínima permitida entre cables de baja tensión, desconociendo el mayor riesgo inductivo y dieléctrico de la alta tensión.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "En galerías visitables para cables de baja tensión, ¿cuál es la altura libre mínima exigida por la ITC-BT-07?",
                    opts = listOf(
                        "1,80 metros",
                        "2,20 metros",
                        "2,00 metros",
                        "2,50 metros"
                    ),
                    a = 2,
                    exp = "En el apartado 2.3 de la ITC-BT-07 se indica que las dimensiones internas de las galerías visitables deben permitir el tránsito de personas con una altura mínima libre de 2 metros. Suele confundirse con 1,80 m pensando en gálibos mínimos de paso secundario, pero una altura inferior a 2,00 m compromete gravemente la seguridad en labores de mantenimiento y evacuación de operarios.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "¿Cuál es la anchura mínima de pasillo de maniobra exigida en galerías visitables con cables a ambos lados según la ITC-BT-07?",
                    opts = listOf(
                        "0,40 metros",
                        "0,60 metros",
                        "1,20 metros",
                        "0,80 metros"
                    ),
                    a = 3,
                    exp = "La ITC-BT-07 establece en su apartado 2.3 que en galerías visitables donde se instalen conducciones o bandejas a ambos lados, el pasillo central de circulación debe tener un ancho mínimo de 0,80 m (0,60 m si solo hay cables en un lado). La trampa radica en seleccionar 0,60 m ignorando la condición de doble lado, o 0,40 m que incumple la ergonomía de paso.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "¿Qué resistencia al fuego mínima deben tener las puertas de acceso y elementos de sectorización en galerías para cables según la ITC-BT-07?",
                    opts = listOf(
                        "EI-120",
                        "EI-30",
                        "EI-60",
                        "No se exige resistencia al fuego"
                    ),
                    a = 0,
                    exp = "Conforme a la ITC-BT-07 apartado 2.3, las galerías de servicios que alojan cables eléctricos deben contar con sectorización cortafuegos con elementos delimitadores resistentes al fuego al menos EI-120 (o RF-120 según nomenclatura clásica) para impedir la propagación de incendios entre sectores. Valores como EI-30 o EI-60 son habituales en edificación común, pero insuficientes en galerías técnicas de distribución.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "¿Qué temperatura de referencia del terreno se toma por defecto para el cálculo de intensidades admisibles en cables enterrados según la ITC-BT-07?",
                    opts = listOf(
                        "40 ºC",
                        "25 ºC",
                        "15 ºC",
                        "20 ºC"
                    ),
                    a = 1,
                    exp = "La tabla de intensidades admisibles para canalizaciones enterradas de la ITC-BT-07 (apartado 3.1) está calculada para una temperatura ambiente estándar del terreno de 25 ºC. La confusión más habitual es marcar 40 ºC, pero este último valor es la temperatura de referencia del aire en instalaciones al exterior o superficiales según la ITC-BT-19, no del terreno subterráneo.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "¿Cuál es la temperatura máxima admisible en servicio permanente para conductores aislados con polietileno reticulado (XLPE) según la ITC-BT-07?",
                    opts = listOf(
                        "70 ºC",
                        "85 ºC",
                        "90 ºC",
                        "105 ºC"
                    ),
                    a = 2,
                    exp = "Conforme a la tabla de características de cables de la ITC-BT-07 (apartado 1.2), el aislamiento termoestable de polietileno reticulado (XLPE) soporta una temperatura máxima de servicio continuo de 90 ºC. La trampa habitual es contestar 70 ºC, valor límite que corresponde a los aislamientos termoplásticos de policloruro de vinilo (PVC), cuya capacidad térmica es significativamente menor.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "¿Cuál es la temperatura máxima que puede alcanzar en cortocircuito un cable con aislamiento de EPR o XLPE según la ITC-BT-07?",
                    opts = listOf(
                        "160 ºC",
                        "200 ºC",
                        "300 ºC",
                        "250 ºC"
                    ),
                    a = 3,
                    exp = "Según la tabla 1 de la ITC-BT-07, los cables con aislamiento termoestable como el etileno-propileno (EPR) y el XLPE soportan una temperatura límite de cortocircuito de 250 ºC (durante un tiempo máximo de 5 segundos). La confusión recurrente es señalar 160 ºC, pero este último es el límite térmico admisible para aislamientos termoplásticos de PVC.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "¿Cuál es la temperatura máxima admisible en servicio permanente para conductores con aislamiento termoplástico de PVC según la ITC-BT-07?",
                    opts = listOf(
                        "70 ºC",
                        "85 ºC",
                        "90 ºC",
                        "105 ºC"
                    ),
                    a = 0,
                    exp = "En la tabla 1 de la ITC-BT-07 se estipula que los cables con aislamiento termoplástico de policloruro de vinilo (PVC) tienen una temperatura máxima admisible en servicio continuo de 70 ºC. Confundir esta cifra con 90 ºC es común porque los aislamientos termoestables (XLPE y EPR) sí alcanzan los 90 ºC, admitiendo por ello densidades de corriente más altas.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "¿Cuál es la temperatura límite de cortocircuito para conductores aislados con PVC según la ITC-BT-07?",
                    opts = listOf(
                        "120 ºC",
                        "160 ºC",
                        "200 ºC",
                        "250 ºC"
                    ),
                    a = 1,
                    exp = "La ITC-BT-07 fija en 160 ºC la temperatura máxima de cortocircuito admisible en conductores con aislamiento de PVC para una duración máxima de 5 segundos. La opción trampa de 250 ºC corresponde a los polímeros termoestables como XLPE o EPR; el PVC se degrada y pierde sus propiedades dieléctricas si rebasa los 160 ºC en defecto.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "¿Cuál es la distancia máxima reglamentaria entre arquetas o registros en tramos rectos de canalizaciones subterráneas bajo tubo según la ITC-BT-07?",
                    opts = listOf(
                        "25 metros",
                        "50 metros",
                        "40 metros",
                        "100 metros"
                    ),
                    a = 2,
                    exp = "En canalizaciones subterráneas con cables instalados en el interior de tubos, la ITC-BT-07 pto. 2.1 establece que la distancia entre arquetas de registro en tramos rectos no debe sobrepasar los 40 metros para facilitar el tiro del cableado. Es común errar respondiendo 50 m o 25 m por asimilación con normativas de alumbrado exterior (ITC-BT-09) o telecomunicaciones (ICT).",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "¿Está permitido instalar cables subterráneos de baja tensión por el interior de alcantarillados públicos según la ITC-BT-07?",
                    opts = listOf(
                        "Sí, siempre que los cables lleven armadura metálica",
                        "Sí, si se instalan dentro de tubos de PVC corrugado",
                        "Se permite si el ayuntamiento otorga permiso especial",
                        "Queda terminantemente prohibido"
                    ),
                    a = 3,
                    exp = "La ITC-BT-07 apartado 2.2 prohíbe taxativamente el paso o instalación de cables de energía por el interior de alcantarillas, cloacas, colectores o conductos de evacuación de aguas residuales. La trampa es creer que una armadura metálica o tubo de PVC subsana la prohibición, ignorando los gases explosivos, corrosión ambiental extrema y riesgo para operarios de saneamiento.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "¿Qué separación mínima debe dejarse entre cables de BT y la pared exterior de una canalización de alcantarillado según la ITC-BT-07?",
                    opts = listOf(
                        "0,20 metros",
                        "0,05 metros",
                        "0,10 metros",
                        "0,50 metros"
                    ),
                    a = 0,
                    exp = "La ITC-BT-07 apartado 2.2 especifica que las líneas eléctricas subterráneas mantendrán una distancia mínima de 0,20 m respecto a los paramentos exteriores de las conducciones de desagüe o alcantarillado. Se suele fallar eligiendo 0,05 m o 0,10 m, distancias que resultan insuficientes para prevenir socavones o filtraciones que degraden la estabilidad de la zanja eléctrica.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "¿Qué diámetro interior mínimo deben tener los tubos protectores respecto al diámetro exterior del cable o haz según la ITC-BT-07?",
                    opts = listOf(
                        "Igual al diámetro del cable más 2 mm",
                        "Mínimo 1,5 veces el diámetro exterior del cable o haz de cables",
                        "Exactamente el doble del diámetro exterior del cable",
                        "El triple del diámetro nominal del conductor más grueso"
                    ),
                    a = 1,
                    exp = "Conforme a la ITC-BT-07 punto 2.1, para posibilitar el tendido y posterior sustitución de los cables en canalizaciones bajo tubo, el diámetro interior del conducto debe ser al menos 1,5 veces el diámetro exterior del cable o haz instalado. Pensar que basta con dejar una holgura milimétrica ajustada ignora las tensiones mecánicas de rozamiento durante el tiro.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "En un cruce subterráneo con líneas de ferrocarril o tranvía, ¿cómo deben instalarse los cables de baja tensión según la ITC-BT-07?",
                    opts = listOf(
                        "Directamente enterrados en balasto a 0,50 m",
                        "En paralelo a las vías suspendidos de los raíles",
                        "Bajo tubo protector hormigonado y perpendicular a las vías a profundidad reglamentaria",
                        "Por encima de los raíles con grapas protectoras"
                    ),
                    a = 2,
                    exp = "La ITC-BT-07 apartado 2.2 determina que el cruce de vías férreas debe ejecutarse bajo tubos protectores alojados en zanja protegida u hormigonada, manteniéndose perpendiculares al eje de las vías para minimizar la exposición a vibraciones y corrientes vagabundas. Cualquier contacto directo con el balasto o proximidad no canalizada está prohibido por seguridad ferroviaria.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "En galerías visitables, ¿qué tipo de iluminación fija de servicio se debe instalar según prescribe la ITC-BT-07?",
                    opts = listOf(
                        "Exclusivamente lámparas halógenas directas a 400 V",
                        "Alumbrado a 230 V sin necesidad de diferencial",
                        "No se admite iluminación fija, solo linternas portátiles",
                        "Alimentada a muy baja tensión de seguridad o con medidas de protección de corte rápido por corriente diferencial"
                    ),
                    a = 3,
                    exp = "La ITC-BT-07 apartado 2.3 dispone que la iluminación de servicio en galerías visitables debe cumplir requisitos de seguridad reforzada, empleando preferentemente muy baja tensión de seguridad o protecciones de corte rápido por corriente diferencial. La creencia de que se puede emplear tensión de red convencional sin protecciones diferenciales específicas ignora el alto riesgo del entorno conductor.",
                    ref = "ITC-BT-07"
                ),
                Question(
                    q = "Cuando varios cables enterrados discurren agrupados en la misma zanja, ¿cómo se calcula su intensidad admisible según la ITC-BT-07?",
                    opts = listOf(
                        "Multiplicando la intensidad de la tabla por un factor de corrección por agrupamiento (< 1)",
                        "Sumando las intensidades nominales de cada cable sin corrección",
                        "Aumentando la intensidad admisible por efecto térmico acumulativo",
                        "No se aplica ningún coeficiente si van separados por 5 cm de arena"
                    ),
                    a = 0,
                    exp = "La ITC-BT-07 apartado 3.1 y tablas anexas prescriben la aplicación de un coeficiente reductor por proximidad de otros circuitos, ya que el calentamiento mutuo eleva la temperatura global del terreno reduciendo la capacidad de transporte. Pensar que no se aplica corrección o que se disipa mejor el calor en grupo es un grave error de diseño.",
                    ref = "ITC-BT-07"
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
,
                Question(
                    q = "Según el REBT, la superficie interior de los tubos protectores debe:",
                    opts = listOf(
                        "Carecer de aristas, asperezas o fisuras",
                        "Ser rugosa para mejorar la fijación",
                        "Estar lubricada permanentemente",
                        "Presentar ranuras longitudinales"
                    ),
                    a = 0,
                    exp = "ITC-BT-21, apartado 1.1: la superficie interior no debe presentar aristas ni asperezas que dañen los conductores.",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "Según el REBT, la denominación de los tubos protectores se realiza en función de:",
                    opts = listOf(
                        "Su diámetro exterior",
                        "Su diámetro interior",
                        "La sección de los conductores",
                        "El número de cables alojados"
                    ),
                    a = 0,
                    exp = "ITC-BT-21, apartado 1.1: la denominación de los tubos se realiza en función del diámetro exterior.",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "Según el REBT, en canalizaciones fijas en superficie los tubos deberán ser preferentemente:",
                    opts = listOf(
                        "Rígidos",
                        "Flexibles",
                        "Textiles reforzados",
                        "Metálicos corrugados"
                    ),
                    a = 0,
                    exp = "ITC-BT-21, apartado 1.2.1: en canalizaciones superficiales los tubos deberán ser preferentemente rígidos.",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "Según el REBT, cuando se instalan más de cinco conductores en un mismo tubo superficial, la sección interior mínima será:",
                    opts = listOf(
                        "2,5 veces la sección ocupada por los conductores",
                        "Igual a la suma de las secciones",
                        "El doble de la sección ocupada",
                        "Libre sin limitación"
                    ),
                    a = 0,
                    exp = "ITC-BT-21, apartado 1.2.1: para más de 5 conductores la sección interior será al menos 2,5 veces la ocupada.",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "Según el REBT, en canalizaciones empotradas los tubos protectores podrán ser:",
                    opts = listOf(
                        "Rígidos, curvables o flexibles",
                        "Solo rígidos",
                        "Solo flexibles",
                        "Exclusivamente metálicos"
                    ),
                    a = 0,
                    exp = "ITC-BT-21, apartado 1.2.2: los tubos empotrados pueden ser rígidos, curvables o flexibles.",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "Según el REBT, en tubos empotrados con más de cinco conductores, la sección interior mínima será:",
                    opts = listOf(
                        "Tres veces la sección ocupada",
                        "Dos veces la sección ocupada",
                        "Cuatro veces la sección ocupada",
                        "La misma sección ocupada"
                    ),
                    a = 0,
                    exp = "ITC-BT-21, apartado 1.2.2: para más de 5 conductores la sección interior será como mínimo 3 veces la ocupada.",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "Según el REBT, las canalizaciones al aire se permiten únicamente para:",
                    opts = listOf(
                        "Alimentación de máquinas o elementos de movilidad restringida",
                        "Cualquier circuito de alumbrado",
                        "Instalaciones interiores de viviendas",
                        "Circuitos empotrados"
                    ),
                    a = 0,
                    exp = "ITC-BT-21, apartado 1.2.3: el uso al aire se limita a máquinas o elementos de movilidad restringida.",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "Según el REBT, se recomienda no utilizar canalizaciones al aire para secciones superiores a:",
                    opts = listOf(
                        "16 mm²",
                        "10 mm²",
                        "25 mm²",
                        "35 mm²"
                    ),
                    a = 0,
                    exp = "ITC-BT-21, apartado 1.2.3: se recomienda no utilizar este tipo de instalación para secciones superiores a 16 mm².",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "Según el REBT, en canalizaciones enterradas los tubos deberán cumplir la norma:",
                    opts = listOf(
                        "UNE-EN 50.086-2-4",
                        "UNE-EN 60.439",
                        "UNE-EN 50.085-1",
                        "UNE 20.460-5-52"
                    ),
                    a = 0,
                    exp = "ITC-BT-21, apartado 1.2.4: los tubos enterrados serán conformes a UNE-EN 50.086-2-4.",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "Según el REBT, el trazado de las canalizaciones bajo tubo debe realizarse preferentemente siguiendo:",
                    opts = listOf(
                        "Líneas verticales y horizontales",
                        "Recorridos diagonales",
                        "El camino más corto",
                        "Trayectorias curvas"
                    ),
                    a = 0,
                    exp = "ITC-BT-21, apartado 2.1: el trazado se hará siguiendo líneas verticales y horizontales.",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "Según el REBT, la distancia máxima entre registros en tramos rectos será de:",
                    opts = listOf(
                        "15 metros",
                        "10 metros",
                        "20 metros",
                        "25 metros"
                    ),
                    a = 0,
                    exp = "ITC-BT-21, apartado 2.1: los registros no estarán separados más de 15 m en tramos rectos.",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "Según el REBT, el número máximo de curvas en ángulo entre dos registros consecutivos será:",
                    opts = listOf(
                        "Tres",
                        "Dos",
                        "Cuatro",
                        "Cinco"
                    ),
                    a = 0,
                    exp = "ITC-BT-21, apartado 2.1: no habrá más de 3 curvas en ángulo entre registros consecutivos.",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "Según el REBT, los tubos metálicos accesibles deberán:",
                    opts = listOf(
                        "Ponerse a tierra y asegurar su continuidad eléctrica",
                        "Aislarse con cinta",
                        "Pintarse de color verde",
                        "Utilizarse como conductor de protección"
                    ),
                    a = 0,
                    exp = "ITC-BT-21, apartado 2.1: los tubos metálicos accesibles deben ponerse a tierra.",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "Según el REBT, la distancia máxima entre fijaciones en tubos en montaje superficial será de:",
                    opts = listOf(
                        "0,50 metros",
                        "0,75 metros",
                        "1 metro",
                        "1,5 metros"
                    ),
                    a = 0,
                    exp = "ITC-BT-21, apartado 2.2: la distancia máxima entre fijaciones será de 0,50 m.",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "Según el REBT, en tubos empotrados las rozas deberán permitir un recubrimiento mínimo de:",
                    opts = listOf(
                        "1 cm de espesor",
                        "0,5 cm de espesor",
                        "2 cm de espesor",
                        "Sin recubrimiento mínimo"
                    ),
                    a = 0,
                    exp = "ITC-BT-21, apartado 2.3: los tubos empotrados quedarán recubiertos por una capa mínima de 1 cm.",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "Según el REBT, la longitud máxima de una canalización al aire será de:",
                    opts = listOf(
                        "4 metros",
                        "3 metros",
                        "5 metros",
                        "6 metros"
                    ),
                    a = 0,
                    exp = "ITC-BT-21, apartado 2.4: la longitud total al aire no será superior a 4 m.",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "Según el REBT, las canales protectoras se definen como perfiles destinados a alojar conductores y:",
                    opts = listOf(
                        "Cerrados por una tapa desmontable",
                        "Rellenos de material aislante",
                        "Sellados permanentemente",
                        "Empotrados obligatoriamente"
                    ),
                    a = 0,
                    exp = "ITC-BT-21, apartado 3.1: la canal protectora se cierra mediante una tapa desmontable.",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "Según el REBT, en canales con grado de protección IP4X o superior se permite:",
                    opts = listOf(
                        "Realizar empalmes y alojar mecanismos en su interior",
                        "Usar conductor desnudo",
                        "Eliminar la tapa",
                        "Instalar conductores sin aislamiento"
                    ),
                    a = 0,
                    exp = "ITC-BT-21, apartado 3.1: en canales IP4X se permiten empalmes y mecanismos.",
                    ref = "ITC-BT-21"
                ),
                Question(
                    q = "Según el REBT, las canales con conductividad eléctrica deberán:",
                    opts = listOf(
                        "Conectarse a la red de tierra",
                        "Usarse como conductor neutro",
                        "Aislarse completamente",
                        "Pintarse con señalización"
                    ),
                    a = 0,
                    exp = "ITC-BT-21, apartado 4.1: las canales con conductividad eléctrica deben conectarse a tierra.",
                    ref = "ITC-BT-21"
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
                        "El valor convencional de la tensión con el que se designa una red y para el cual ha sido proyectada",
                        "La tensión mínima por debajo de la cual los receptores térmicos deben interrumpir su servicio",
                        "La diferencia de potencial registrada entre el electrodo de tierra y el neutro en régimen de carga"
                    ),
                    a = 1,
                    exp = "La ITC-BT-01 define la tensión nominal como el valor convencional de la tensión eficaz con el que se designa un sistema o instalación y al que se refieren determinadas características de funcionamiento. En redes de BT en España, los valores normalizados son 230 V monofásica y 400 V trifásica.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Qué se considera reglamentariamente como 'Parte activa' de una instalación según la ITC-BT-01?",
                    opts = listOf(
                        "Cualquier estructura metálica del edificio susceptible de tocarse",
                        "Únicamente los conductores de fase, quedando excluido el neutro",
                        "Todo conductor o parte conductora destinada a estar bajo tensión en servicio normal, incluido el neutro",
                        "El conductor de protección PE conectado al borne principal de tierra"
                    ),
                    a = 2,
                    exp = "La ITC-BT-01 define 'Parte activa' como los conductores y partes conductoras destinadas a estar bajo tensión en servicio normal. Por convenio reglamentario expreso, incluye al conductor neutro, pero excluye terminantemente al conductor PEN y a los conductores de protección (PE).",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Cómo define la ITC-BT-01 a los receptores o aparatos eléctricos de 'Clase II'?",
                    opts = listOf(
                        "Aparatos con aislamiento básico que deben conectarse obligatoriamente a tierra",
                        "Aparatos alimentados exclusivamente por baterías recargables de 12 V",
                        "Equipos industriales sin ningún tipo de envolvente dieléctrica",
                        "Aparatos provistos de doble aislamiento o aislamiento reforzado y sin borne de puesta a tierra"
                    ),
                    a = 3,
                    exp = "Conforme a la ITC-BT-01, los aparatos de Clase II disponen de aislamiento complementario o reforzado en todas sus partes activas para evitar contactos indirectos, por lo que carecen de borne de toma de tierra y está prohibido conectar sus masas a un conductor de protección.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Qué define reglamentariamente la 'Tensión de contacto' según la terminología de la ITC-BT-01?",
                    opts = listOf(
                        "La diferencia de potencial que puede aparecer entre partes simultáneamente accesibles durante un defecto de aislamiento",
                        "La tensión nominal entre fases en bornes del transformador de distribución",
                        "La caída de tensión porcentual registrada a lo largo de una derivación individual",
                        "La tensión continua generada por frotamiento electrostático en el suelo"
                    ),
                    a = 0,
                    exp = "Según la ITC-BT-01, la tensión de contacto es la fracción de la tensión de defecto a la que puede quedar sometida una persona al tocar simultáneamente dos masas o una masa y un elemento conductor. Es el parámetro crítico que determina el riesgo fisiológico de electrocución.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Qué es la 'Tensión de paso' según la definición reglamentaria de la ITC-BT-01?",
                    opts = listOf(
                        "La diferencia de potencial entre el neutro y la fase al conectar un receptor de gran potencia",
                        "La parte de la tensión de tierra que puede puentear una persona entre sus pies con un paso convencional de un metro",
                        "La tensión necesaria para provocar la ruptura dieléctrica del aire entre dos conductores",
                        "El incremento de tensión que experimenta un motor durante el arranque estrella-triángulo"
                    ),
                    a = 1,
                    exp = "La ITC-BT-01 define tensión de paso como la parte de la tensión del electrodo de puesta a tierra a la que puede estar sometida una persona entre sus dos pies separados por una distancia convencional estandarizada de 1 metro en la superficie del terreno.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Cómo se define un conductor 'PEN' según la terminología oficial de la ITC-BT-01?",
                    opts = listOf(
                        "Un conductor de protección exclusivamente para redes subterráneas con aislamiento XLPE",
                        "Un conductor activo de fase reservado para alumbrado de emergencia",
                        "Un conductor puesto a tierra que combina simultáneamente las funciones de conductor de protección y de neutro",
                        "Un cable piloto de fibra óptica destinado a la telegestión de contadores inteligentes"
                    ),
                    a = 2,
                    exp = "En la ITC-BT-01, el conductor PEN (Protective Earth + Neutral) es un conductor puesto a tierra asignado a la doble función combinada de conductor de protección (PE) y conductor neutro (N), característico y exclusivo del esquema de conexión de masas TN-C.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Qué es el 'Borne principal de tierra' de una instalación según la definición de la ITC-BT-01?",
                    opts = listOf(
                        "El tornillo de masa ubicado en el chasis metálico de la lavadora",
                        "La pica de cobre más profunda del anillo enterrado en la cimentación",
                        "El fusible de corte situado en la caja general de protección del edificio",
                        "El borne o barra destinado a la unión de los conductores de protección, equipotencialidad y de tierra"
                    ),
                    a = 3,
                    exp = "Conforme a la ITC-BT-01, la barra o borne principal de tierra es el punto de conexión centralizado donde confluyen el conductor de tierra (línea de enlace con electrodos), los conductores de protección de los circuitos y los conductores de unión equipotencial principal.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Qué distingue a un sistema de 'Muy Baja Tensión de Seguridad' (MBTS o SELV) según la ITC-BT-01?",
                    opts = listOf(
                        "Tensión asignada máx. de 50 V c.a. o 75 V c.c., con fuente de aislamiento de seguridad y circuitos aislados de tierra",
                        "Tensión de 230 V con aislamiento cerámico reforzado y puesta a tierra en pica de acero",
                        "Alimentación directa desde la red general mediante autotransformador reductor",
                        "Cualquier instalación protegida por un diferencial de 300 mA con retardo tipo S"
                    ),
                    a = 0,
                    exp = "La ITC-BT-01 define MBTS como el circuito cuya tensión no supera 50 V en corriente alterna eficaz ni 75 V en corriente continua, alimentado por una fuente de seguridad independiente (transformador de seguridad) y con sus partes activas y masas totalmente aisladas de tierra.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Qué se entiende por 'Intensidad o corriente admisible (Iz)' de un conductor según la ITC-BT-01?",
                    opts = listOf(
                        "La máxima corriente de cortocircuito franco que soporta el conductor durante 5 segundos",
                        "La corriente máxima permanente que puede circular por el conductor en régimen continuo sin sobrepasar su temperatura límite",
                        "La corriente de disparo térmico instantáneo del interruptor magnetotérmico aguas arriba",
                        "El valor medio de corriente consumido por los receptores durante el horario diurno punta"
                    ),
                    a = 1,
                    exp = "Según la ITC-BT-01, la corriente admisible (Iz) es el valor máximo de corriente continua o eficaz que puede circular permanentemente por un conductor en unas condiciones de instalación y ambientales dadas sin que la temperatura del aislante supere su valor máximo asignado.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Cuál es la definición reglamentaria de 'Poder de corte' de un dispositivo de corte según la ITC-BT-01?",
                    opts = listOf(
                        "El par de apriete máximo en newton-metro que admiten los tornillos de sus bornes",
                        "El número mínimo de maniobras mecánicas de apertura y cierre garantizadas por el fabricante",
                        "La corriente de cortocircuito presumible máxima que el dispositivo es capaz de interrumpir a una tensión determinada",
                        "La sensibilidad en miliamperios a partir de la cual abre el relé electromagnético"
                    ),
                    a = 2,
                    exp = "La ITC-BT-01 define poder de corte (o capacidad de ruptura) como el valor límite de la corriente prevista que un interruptor automático o cortacircuito fusible es capaz de desconectar bajo una tensión especificada y en condiciones normalizadas de ensayo.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Qué es el 'Aislamiento funcional o principal' según la terminología de la ITC-BT-01?",
                    opts = listOf(
                        "La pintura decorativa aplicada sobre las carcasas metálicas exteriores",
                        "La envoltura de PVC que protege a los tubos contra la corrosión química en zanjas",
                        "El aislamiento de reserva que solo se activa ante una avería catastrófica previa",
                        "El aislamiento de las partes activas necesario para asegurar el funcionamiento normal y la protección básica contra choques"
                    ),
                    a = 3,
                    exp = "En la ITC-BT-01, el aislamiento principal es el aislamiento imprescindible aplicado directamente sobre las partes activas para posibilitar el funcionamiento normal del material eléctrico y proporcionar la barrera básica elemental contra choques eléctricos por contacto directo.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Qué define a los aparatos o materiales eléctricos de 'Clase III' según la ITC-BT-01?",
                    opts = listOf(
                        "Aparatos diseñados para ser alimentados exclusivamente a muy baja tensión (MBTS o MBTP) y sin tensiones superiores",
                        "Equipos provistos de carcasas de fundición pesada con doble borne de puesta a tierra",
                        "Materiales sumergibles aptos para funcionar en atmósferas explosivas con gas grisú",
                        "Receptores conectados a trifásica de 400 V con carcasa aislada en resina epoxi"
                    ),
                    a = 0,
                    exp = "La ITC-BT-01 clasifica como Clase III aquellos aparatos y equipos en los que la protección contra el choque eléctrico recae en su alimentación bajo Muy Baja Tensión de Seguridad (MBTS/MBTP, tensión límite 50 V c.a.) y en los que no se generan internamente tensiones superiores.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Qué se entiende por 'Tierra lejana o tierra de referencia' según la terminología de la ITC-BT-01?",
                    opts = listOf(
                        "La superficie de tierra situada justo encima de la pica vertical de acero cobrizado",
                        "La zona de tierra situada a distancia suficiente del electrodo para que su potencial eléctrico no varíe con respecto a tierra infinita",
                        "El terreno arcilloso situado en una cuenca hidrográfica a más de 10 kilómetros",
                        "El electrodo de cobre enterrado bajo los cimientos de la subestación transformadora"
                    ),
                    a = 1,
                    exp = "Según la ITC-BT-01, la tierra de referencia es aquella porción del terreno situada a una distancia tal del electrodo de puesta a tierra que el gradiente de potencial originado por la corriente dispersada resulta prácticamente despreciable, manteniéndose en potencial cero absoluto.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Qué representa la primera cifra del código 'IP' de un equipo eléctrico según las definiciones de la ITC-BT-01?",
                    opts = listOf(
                        "El grado de resistencia frente a la inmersión continua bajo agua a presión",
                        "La tensión máxima de aislamiento que soporta la envolvente en kilovoltios",
                        "El grado de protección contra la penetración de cuerpos sólidos extraños y acceso a partes peligrosas",
                        "El espesor en milímetros de la chapa de acero galvanizado de la caja"
                    ),
                    a = 2,
                    exp = "Conforme a la norma UNE 20324 citada por la ITC-BT-01, la primera cifra característica del índice IP (de 0 a 6) indica el nivel de protección de la envolvente contra el ingreso de cuerpos sólidos extraños (polvo, partículas) y contra el contacto accidental con partes activas interiores.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Qué indica la segunda cifra característica del código 'IP' de una envolvente según la ITC-BT-01?",
                    opts = listOf(
                        "La resistencia del equipo contra la penetración de vapores de aceite caliente",
                        "El grado de aislamiento acústico frente a ruidos procedentes de inductancias",
                        "La resistencia al choque mecánico medida en julios de energía cinética",
                        "El grado de protección que proporciona la envolvente contra los efectos perjudiciales de la penetración de agua"
                    ),
                    a = 3,
                    exp = "En el código IP reglamentario de la ITC-BT-01, la segunda cifra característica (de 0 a 8 o 9) cuantifica la estanqueidad de la envolvente frente a la entrada de agua en diversas modalidades: gotas verticales, lluvia, chorros a presión o inmersión temporal/continua.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Qué magnitud física mide el código 'IK' de una envolvente eléctrica según la ITC-BT-01?",
                    opts = listOf(
                        "El grado de protección proporcionado por la envolvente contra impactos mecánicos externos expresado en julios",
                        "La conductividad térmica del plástico empleado en la fabricación del envolvente",
                        "La resistencia óhmica entre los bornes interiores y la superficie exterior pintada",
                        "La presión hidrostática máxima admisible en atmósferas técnicas"
                    ),
                    a = 0,
                    exp = "La ITC-BT-01 y la norma UNE-EN 50102 definen el código IK (desde IK00 hasta IK10) como la escala normalizada que clasifica la resistencia de las envolventes frente a agresiones mecánicas por impacto directo, midiendo la energía del golpe absorbida sin rotura en julios (J).",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Qué es un 'Aparamenta bajo envolvente' según la terminología técnica de la ITC-BT-01?",
                    opts = listOf(
                        "Cualquier transformador de potencia enfriado por circulación forzada de aceite",
                        "Un conjunto de aparatos de conexión, mando o protección montados en el interior de una caja o armario protector",
                        "Una línea eléctrica enterrada protegida por una capa de arena de río cernida",
                        "Un interruptor de cuchillas abierto montado sobre un tablero de mármol antiguo"
                    ),
                    a = 1,
                    exp = "Según la ITC-BT-01, la aparamenta bajo envolvente es aquel equipo que integra dispositivos de maniobra, protección, medida o regulación debidamente ensamblados e interconectados dentro de una envolvente protectora que proporciona grados de protección IP e IK especificados.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Cómo define la ITC-BT-01 a los materiales eléctricos de 'Clase 0'?",
                    opts = listOf(
                        "Materiales con aislamiento reforzado y prueba dieléctrica a 5.000 voltios",
                        "Equipos con doble puesta a tierra para instalaciones de alta fiabilidad militar",
                        "Materiales en los que la protección contra choques se basa únicamente en el aislamiento principal, sin puesta a tierra",
                        "Aparatos diseñados para trabajar sumergidos permanentemente en agua potable"
                    ),
                    a = 2,
                    exp = "En la clasificación de receptores de la ITC-BT-01, la Clase 0 comprende aparatos provistos de aislamiento principal básico pero carentes de borne de puesta a tierra. Si falla el aislamiento, la masa queda bajo tensión sin protección; por su elevado riesgo, su instalación está prohibida en el REBT.",
                    ref = "ITC-BT-01"
                ),
                Question(
                    q = "¿Qué define el 'Coeficiente de simultaneidad' en el cálculo de cargas eléctricas según la ITC-BT-01 y el REBT?",
                    opts = listOf(
                        "La relación entre la potencia reactiva consumida y la potencia aparente total",
                        "El factor de sobrecarga admisible por un transformador durante 10 minutos de emergencia",
                        "El rendimiento electromecánico ponderado de los motores instalados en un edificio",
                        "El factor de reducción que refleja que no todos los receptores de una instalación funcionan a la vez a plena carga"
                    ),
                    a = 3,
                    exp = "Conforme a la ITC-BT-01 y las guías de previsión de cargas del REBT, el factor o coeficiente de simultaneidad es la relación porcentual (menor o igual a 1) que pondera la probabilidad estadística de que varios receptores o usuarios demanden potencia simultáneamente sobre la suma de sus potencias nominales.",
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
                        "Cables unipolares de cobre de al menos 16 mm² o de aluminio de 25 mm² de tensión 0,6/1 kV"
                    ),
                    a = 3,
                    exp = "Las acometidas que acometen a las Cajas Generales de Protección se ejecutan con conductores aislados para 0,6/1 kV, con secciones mínimas normalizadas no inferiores a 16 mm² en cobre o 25 mm² en aluminio por motivos de resistencia mecánica y capacidad frente a corrientes de cortocircuito de la red pública.",
                    ref = "ITC-BT-11 e ITC-BT-07"
                ),
                Question(
                    q = "¿Cuál es la función reglamentaria principal de la Caja General de Protección (CGP) según la ITC-BT-11?",
                    opts = listOf(
                        "Alojar los dispositivos de protección de las líneas generales de alimentación (LGA) frente a sobrecargas y cortocircuitos",
                        "Registrar el consumo de energía eléctrica activa y reactiva de cada vivienda del edificio",
                        "Interrumpir el suministro general mediante un botón de parada de emergencia para bomberos",
                        "Distribuir directamente los circuitos interiores de alumbrado y tomas de fuerza de las viviendas"
                    ),
                    a = 0,
                    exp = "La ITC-BT-11 apartado 1 define las Cajas Generales de Protección como los elementos de la instalación de enlace que alojan en su interior los elementos de protección de las líneas generales de alimentación y señalan el principio de la propiedad de las instalaciones de los usuarios.",
                    ref = "ITC-BT-11"
                ),
                Question(
                    q = "Cuando la acometida es subterránea, ¿cómo debe instalarse obligatoriamente la CGP según la ITC-BT-11?",
                    opts = listOf(
                        "Suspendida del forjado del techo en el sótano primero",
                        "Enterrada en arqueta estanca bajo la calzada de rodadura",
                        "En una hornacina o nicho sobre la fachada exterior, cerrada con puerta normalizada",
                        "En el vestíbulo interior del portal junto al ascensor"
                    ),
                    a = 2,
                    exp = "Según la ITC-BT-11 apartado 2.1, para acometidas subterráneas la CGP se ubica siempre en una hornacina en la fachada del inmueble, provista de puerta que garantice los grados de protección mínimos y cierre normalizado por la empresa distribuidora.",
                    ref = "ITC-BT-11"
                ),
                Question(
                    q = "Si la acometida a la CGP es aérea tensada sobre fachada, ¿a qué altura mínima debe fijarse según la ITC-BT-11?",
                    opts = listOf(
                        "Entre 3 y 4 metros sobre el nivel del suelo",
                        "A menos de 1 metro del bordillo de la acera",
                        "En la cumbrera del tejado a más de 12 metros",
                        "A 1,5 metros sin importar la vía pública"
                    ),
                    a = 0,
                    exp = "La ITC-BT-11 apartado 2.1 prescribe que en acometidas aéreas, la parte inferior de la CGP o el punto de fijación del amarre se situará a una altura comprendida entre 3 y 4 metros respecto al nivel de la acera o calle, evitando interferencias con el tránsito de personas.",
                    ref = "ITC-BT-11"
                ),
                Question(
                    q = "¿Por qué está expresamente prohibido colocar fusibles en el borne de neutro de una CGP según la ITC-BT-11?",
                    opts = listOf(
                        "Porque los fusibles de neutro son más caros que los de fase",
                        "Porque el neutro debe ser de mayor sección que los conductores activos",
                        "Porque la normativa europea exige que el neutro nunca transporte corriente",
                        "Porque la apertura del neutro dejaría a las fases monofásicas expuestas a sobretensiones graves por desplazamiento del punto neutro"
                    ),
                    a = 3,
                    exp = "La ITC-BT-11 pto. 1.2 prohíbe taxativamente intercalar fusibles en el conductor neutro. Debe utilizarse una cuchilla o borne de conexión seccionable sin fusible. Si un fusible de neutro fundiera dejando las fases conectadas, se provocaría una sobretensión extrema (hasta 400 V) en los receptores monofásicos.",
                    ref = "ITC-BT-11"
                ),
                Question(
                    q = "¿Qué tipo de curva de fusión deben tener los cartuchos fusibles instalados en la CGP según la ITC-BT-11 y normas UNE?",
                    opts = listOf(
                        "Curva aM (acompañamiento de motor para arranque pesado)",
                        "Curva ultrarrápida gR para semiconductores de potencia",
                        "Curva gG (fusibles de uso general para protección contra sobrecargas y cortocircuitos)",
                        "Curva B o C de disparo instantáneo magnético"
                    ),
                    a = 2,
                    exp = "Conforme a la ITC-BT-11 pto. 1.2 y la norma UNE-HD 60269-2, los cortacircuitos fusibles alojados en las Cajas Generales de Protección deben ser de tipo cuchilla con curva característica gG, garantizando la protección general de los cables de la LGA en todo el rango de sobrecargas y cortocircuitos.",
                    ref = "ITC-BT-11"
                ),
                Question(
                    q = "¿Cuál debe ser el poder de corte nominal mínimo de los fusibles de una CGP según la ITC-BT-11?",
                    opts = listOf(
                        "Al menos 3 kA",
                        "Como mínimo igual a la corriente de cortocircuito presumible en el punto de su instalación (típicamente 25 kA o 50 kA)",
                        "Exactamente 100 A a 230 V",
                        "No se exige poder de corte específico si la caja es de poliéster"
                    ),
                    a = 1,
                    exp = "La ITC-BT-11 apartado 1.2 estipula que el poder de corte de los fusibles de la CGP debe ser como mínimo igual a la corriente de cortocircuito que pueda presentarse en el punto de acometida, siendo valores habituales en distribución urbana 25 kA o 50 kA.",
                    ref = "ITC-BT-11"
                ),
                Question(
                    q = "¿Qué ensayo térmico de seguridad contra el fuego debe superar el material aislante de una CGP según la ITC-BT-11?",
                    opts = listOf(
                        "Ensayo del hilo incandescente a 960 ºC sin mantener la combustión tras retirar el hilo",
                        "Ensayo con soplete de gas a 2.000 ºC durante 1 hora",
                        "Resistencia térmica ordinaria a 40 ºC en horno seco",
                        "No se somete a ensayos de fuego si se instala al exterior"
                    ),
                    a = 0,
                    exp = "Según la ITC-BT-11 apartado 1.1 y normas UNE de producto, las envolventes aislantes de las CGP deben ser autoextinguibles, superando satisfactoriamente el ensayo del hilo incandescente a una temperatura de 960 ºC (norma UNE-EN 60695-2-11) para evitar la propagación de incendios.",
                    ref = "ITC-BT-11"
                ),
                Question(
                    q = "¿Qué grados de protección mínimos (IP e IK) debe tener la puerta de la hornacina que aloja una CGP según la ITC-BT-11?",
                    opts = listOf(
                        "IP20 e IK02",
                        "IP68 e IK05",
                        "IP30 sin ensayo de choque",
                        "Cierre que garantice al menos IP43 e impacto mínimo de IK09"
                    ),
                    a = 3,
                    exp = "Conforme a la ITC-BT-11 apartado 2.1 y especificaciones de enlace, cuando la CGP se ubique dentro de un nicho u hornacina sobre fachada, la puerta de protección exterior debe ofrecer un grado de impacto no inferior a IK09 y estanqueidad mínima IP43 frente a lluvia y polvo.",
                    ref = "ITC-BT-11"
                ),
                Question(
                    q = "¿En qué casos se admite instalar más de una CGP en un mismo edificio según la ITC-BT-11?",
                    opts = listOf(
                        "Únicamente si el edificio tiene más de 30 plantas de altura",
                        "Nunca, está prohibido bajo sanción tener más de una CGP por inmueble",
                        "Cuando la potencia prevista requiera varias LGA independientes o por existir usos diferenciados significativos (viviendas, locales, garajes)",
                        "Solo si la compañía distribuidora no dispone de contadores electrónicos"
                    ),
                    a = 2,
                    exp = "La ITC-BT-11 punto 1 autoriza la instalación de varias Cajas Generales de Protección en un edificio cuando la potencia global supere la capacidad asignable a una sola LGA, o para suministros independientes de gran envergadura (por ejemplo, garaje o zona comercial con tarifa y acometida separadas).",
                    ref = "ITC-BT-11"
                ),
                Question(
                    q = "¿Qué dispositivo de maniobra incorporan las bases portafusibles de una CGP moderna según la ITC-BT-11?",
                    opts = listOf(
                        "Pulsador de rearme piezoeléctrico con bluetooth",
                        "Bases tripolares verticales o seccionadores de corte en carga maniobrables mediante maneta o pértiga",
                        "Tirador de cuerda plástica accesible desde la acera",
                        "Bobina de disparo a distancia comandada por radiofrecuencia"
                    ),
                    a = 1,
                    exp = "En las CGP normalizadas según la ITC-BT-11 y normas UNE (como los esquemas 7 y 9), se montan bases tripolares verticales seccionables en carga (BTVC), permitiendo abrir y cerrar el circuito de forma segura bajo carga de servicio mediante una maneta aislada.",
                    ref = "ITC-BT-11"
                ),
                Question(
                    q = "¿Qué esquema normalizado de CGP se utiliza habitualmente para acometida subterránea con una sola LGA según la ITC-BT-11?",
                    opts = listOf(
                        "Esquema 7 (CGP con bases tripolares para una salida)",
                        "Esquema 2 (para acometida aérea tensada de neutro fiador)",
                        "Esquema 1 (para acometida aérea posada sobre fachada)",
                        "Esquema 0 (línea sin fusible de protección)"
                    ),
                    a = 0,
                    exp = "El Esquema 7 de las normas UNE adoptadas por la ITC-BT-11 corresponde a la CGP para acometidas subterráneas que alimentan una única Línea General de Alimentación mediante bases portafusibles seccionables en carga.",
                    ref = "ITC-BT-11"
                ),
                Question(
                    q = "¿Qué característica define al Esquema 9 de CGP según la normativa técnica de la ITC-BT-11?",
                    opts = listOf(
                        "Caja para dos suministros monofásicos rurales con fusibles de 10 A",
                        "Caja diseñada exclusivamente para recarga de vehículos en garajes comunitarios",
                        "Caja para acometida subterránea con entrada y salida de red en bucle o anillo de distribución",
                        "Armario para contadores concentrados en planta baja"
                    ),
                    a = 2,
                    exp = "El Esquema 9 de CGP está diseñado para redes subterráneas distribuidas en bucle o anillo. Dispone de bornes dobles para entrada y salida de los cables de la red de distribución pública (paso de red), manteniendo la alimentación a la LGA del edificio.",
                    ref = "ITC-BT-11"
                ),
                Question(
                    q = "¿Cómo deben estar protegidas las partes conductoras activas en el interior de una CGP contra contactos directos accidentales según ITC-BT-11?",
                    opts = listOf(
                        "Pintadas con pintura reflectante amarilla",
                        "Sin protección alguna, el instalador debe usar siempre guantes de cuero",
                        "Solo mediante advertencia verbal en la placa exterior",
                        "Mediante pantallas aislantes transparentes o tapas cubrebornes que garanticen un grado de protección IP2X"
                    ),
                    a = 3,
                    exp = "La ITC-BT-11 pto. 1.1 y la norma UNE-EN 60529 exigen que en el interior de la CGP exista una pantalla protectora transparente o cubrebornes que asegure un grado de protección mínimo IP2X (protección contra contactos con los dedos) aun con la puerta de la caja abierta.",
                    ref = "ITC-BT-11"
                ),
                Question(
                    q = "¿Dónde debe instalarse la Caja de Protección y Medida (CPM) en una vivienda unifamiliar según la ITC-BT-11 e ITC-BT-13?",
                    opts = listOf(
                        "En la valla de cerramiento o límite exterior de la parcela, accesible permanentemente desde la vía pública",
                        "En el cuarto de baño principal junto a los electrodomésticos",
                        "En el desván bajo tejado para evitar que se moje con la lluvia",
                        "Enterrada en el césped del jardín privado"
                    ),
                    a = 0,
                    exp = "Conforme a la ITC-BT-11 e ITC-BT-13, la CPM en viviendas unifamiliares debe situarse en la valla exterior o cerramiento de la parcela con acceso directo y libre desde la vía pública, permitiendo la lectura, precintado y maniobra de la distribuidora sin invadir la propiedad privada.",
                    ref = "ITC-BT-11"
                ),
                Question(
                    q = "¿Qué elemento es obligatorio incluir en las entradas de cables a una CGP para preservar su grado de estanqueidad según la ITC-BT-11?",
                    opts = listOf(
                        "Relleno con mortero de cemento rápido ordinario",
                        "Conos pasacables elásticos o prensaestopas adecuados al diámetro exterior de los cables",
                        "Espuma de poliuretano inflamable expandida al aire",
                        "No se exige ningún elemento si la zanja tiene grava"
                    ),
                    a = 1,
                    exp = "La ITC-BT-11 exige que las acometidas y salidas de LGA penetren en la CGP a través de prensaestopas, conos elásticos pasacables o manguitos estancos que garanticen el mantenimiento del grado de protección IP prescrito (mínimo IP43) e impidan la entrada de agua, polvo o insectos.",
                    ref = "ITC-BT-11"
                ),
                Question(
                    q = "¿Qué señal de seguridad debe exhibirse de forma indeleble en la cara frontal exterior de la CGP según la ITC-BT-11?",
                    opts = listOf(
                        "El logotipo comercial de la empresa instaladora",
                        "El número de teléfono del presidente de la comunidad de vecinos",
                        "El símbolo normalizado de peligro de descarga eléctrica (flecha quebrada)",
                        "Una indicación de prohibido aparcar delante"
                    ),
                    a = 2,
                    exp = "La ITC-BT-11 apartado 1.1 determina que las tapas o puertas exteriores de las CGP llevarán grabado o fijado de forma duradera el símbolo reglamentario de advertencia de riesgo eléctrico (triángulo con flecha de rayo quebrada) según normas UNE.",
                    ref = "ITC-BT-11"
                ),
                Question(
                    q = "¿Cómo se coordinan las intensidades de los fusibles de la CGP con la capacidad de los conductores de la LGA según la ITC-BT-11 e ITC-BT-14?",
                    opts = listOf(
                        "La intensidad nominal del fusible de la CGP debe ser menor o igual a la corriente admisible de los conductores de la LGA (In <= Iz)",
                        "La intensidad del fusible debe ser al menos el doble de la corriente admisible de la LGA para no fundir",
                        "No se coordinan, la LGA se dimensiona siempre al margen de los fusibles de la CGP",
                        "Se instalan siempre fusibles de 1.000 A en todas las CGP residenciales"
                    ),
                    a = 0,
                    exp = "La regla fundamental de protección contra sobrecargas (ITC-BT-11 e ITC-BT-14 coordinadas con la ITC-BT-22) exige que la corriente nominal del cartucho fusible de la CGP (In) no supere la intensidad admisible (Iz) de los conductores que integran la Línea General de Alimentación (In <= Iz).",
                    ref = "ITC-BT-11"
                ),
                Question(
                    q = "¿Quién tiene la potestad exclusiva de colocar y retirar los precintos de seguridad de una CGP según el REBT y la ITC-BT-11?",
                    opts = listOf(
                        "Cualquier vecino residente en el inmueble que sea titular del contrato",
                        "El conserje o administrador de la finca cuando lo considere oportuno",
                        "El operario de telecomunicaciones al tender fibra óptica en fachada",
                        "El personal debidamente autorizado de la empresa distribuidora de energía eléctrica"
                    ),
                    a = 3,
                    exp = "De acuerdo con el REBT y las condiciones generales de suministro eléctrico, los precintos de la CGP protegen elementos de corte y acometida que solo pueden ser manipulados, retirados o precintados por personal facultado de la empresa distribuidora para evitar fraudes o manipulaciones no seguras.",
                    ref = "ITC-BT-11"
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
,
                Question(
                    q = "Según el REBT, se denominan instalaciones de enlace aquellas que unen:",
                    opts = listOf(
                        "La caja general de protección con las instalaciones interiores o receptoras del usuario",
                        "El cuadro general del usuario con la red de distribución pública",
                        "El contador con la derivación individual",
                        "El interruptor general automático con el transformador de compañía"
                    ),
                    a = 0,
                    exp = "ITC-BT-12, punto 1.1 Definición: “Se denominan instalaciones de enlace, aquellas que unen la caja general de protección [...] con las instalaciones interiores o receptoras del usuario.”",
                    ref = "ITC-BT-12"
                ),
                Question(
                    q = "Según el REBT, las instalaciones de enlace comienzan en:",
                    opts = listOf(
                        "El final de la acometida",
                        "El contador del usuario",
                        "La derivación individual",
                        "El cuadro de mando y protección"
                    ),
                    a = 0,
                    exp = "ITC-BT-12, punto 1.1: “Comenzarán, por tanto, en el final de la acometida…”",
                    ref = "ITC-BT-12"
                ),
                Question(
                    q = "Según el REBT, las instalaciones de enlace terminan en:",
                    opts = listOf(
                        "Los dispositivos generales de mando y protección",
                        "El contador del usuario",
                        "La caja de derivación general",
                        "El cuadro secundario de alumbrado"
                    ),
                    a = 0,
                    exp = "ITC-BT-12, punto 1.1: “…y terminarán en los dispositivos generales de mando y protección.”",
                    ref = "ITC-BT-12"
                ),
                Question(
                    q = "Según el REBT, las instalaciones de enlace se situarán y discurrirán siempre por:",
                    opts = listOf(
                        "Lugares de uso común",
                        "Canalizaciones interiores del usuario",
                        "Locales de pública concurrencia",
                        "Recorridos interiores privados"
                    ),
                    a = 0,
                    exp = "ITC-BT-12, punto 1.1: “Estas instalaciones se situarán y discurrirán siempre por lugares de uso común…”",
                    ref = "ITC-BT-12"
                ),
                Question(
                    q = "Según el REBT, las instalaciones de enlace quedarán en propiedad de:",
                    opts = listOf(
                        "El usuario, que será responsable de su conservación y mantenimiento",
                        "La empresa distribuidora",
                        "El promotor del edificio",
                        "El ayuntamiento correspondiente"
                    ),
                    a = 0,
                    exp = "ITC-BT-12, punto 1.1: “…y quedarán de propiedad del usuario, que se responsabilizará de su conservación y mantenimiento.”",
                    ref = "ITC-BT-12"
                ),
                Question(
                    q = "Según el REBT, forman parte de las instalaciones de enlace:",
                    opts = listOf(
                        "Caja general de protección, línea general de alimentación, elementos para contadores, derivación individual, caja ICP y dispositivos generales de mando y protección",
                        "Derivación individual, cuadro principal y alumbrado de emergencia",
                        "Caja general de protección, cuadro general del usuario y red interior",
                        "Solo la línea general de alimentación y la derivación individual"
                    ),
                    a = 0,
                    exp = "ITC-BT-12, punto 1.2: lista exactamente estos seis elementos como partes de las instalaciones de enlace.",
                    ref = "ITC-BT-12"
                ),
                Question(
                    q = "Según el REBT, la línea general de alimentación (LGA) forma parte de:",
                    opts = listOf(
                        "Las instalaciones de enlace",
                        "La acometida",
                        "Las instalaciones interiores del usuario",
                        "Los servicios generales del edificio"
                    ),
                    a = 0,
                    exp = "ITC-BT-12, punto 1.2: La LGA aparece incluida en la lista de partes que constituyen las instalaciones de enlace.",
                    ref = "ITC-BT-12"
                ),
                Question(
                    q = "Según el REBT, en los esquemas de enlace la red de distribución se representa con el número:",
                    opts = listOf(
                        "1",
                        "2",
                        "3",
                        "4"
                    ),
                    a = 0,
                    exp = "ITC-BT-12, Leyenda: el número 1 corresponde a 'Red de distribución'.",
                    ref = "ITC-BT-12"
                ),
                Question(
                    q = "Según el REBT, en los esquemas de enlace la acometida se representa con el número:",
                    opts = listOf(
                        "2",
                        "3",
                        "4",
                        "5"
                    ),
                    a = 0,
                    exp = "ITC-BT-12, Leyenda: el número 2 corresponde a 'Acometida'.",
                    ref = "ITC-BT-12"
                ),
                Question(
                    q = "Según el REBT, el número que identifica la caja general de protección en la leyenda de esquemas es:",
                    opts = listOf(
                        "3",
                        "4",
                        "5",
                        "6"
                    ),
                    a = 0,
                    exp = "ITC-BT-12, Leyenda: el número 3 corresponde a 'Caja general de protección'.",
                    ref = "ITC-BT-12"
                ),
                Question(
                    q = "Según el REBT, el número que identifica la línea general de alimentación en la leyenda es:",
                    opts = listOf(
                        "4",
                        "5",
                        "6",
                        "7"
                    ),
                    a = 0,
                    exp = "ITC-BT-12, Leyenda: el número 4 corresponde a 'Línea general de alimentación'.",
                    ref = "ITC-BT-12"
                ),
                Question(
                    q = "Según el REBT, el número que corresponde a los dispositivos generales de mando y protección es:",
                    opts = listOf(
                        "12",
                        "10",
                        "8",
                        "9"
                    ),
                    a = 0,
                    exp = "ITC-BT-12, Leyenda: el número 12 corresponde a 'Dispositivos generales de mando y protección'.",
                    ref = "ITC-BT-12"
                ),
                Question(
                    q = "Según el REBT, el conjunto formado por la derivación individual y la instalación interior constituye:",
                    opts = listOf(
                        "La instalación privada",
                        "La instalación de enlace",
                        "La acometida del usuario",
                        "El circuito principal"
                    ),
                    a = 0,
                    exp = "ITC-BT-12, Nota de la leyenda: “El conjunto de derivación individual e instalación interior constituye la instalación privada.”",
                    ref = "ITC-BT-12"
                ),
                Question(
                    q = "Según el REBT, en el caso de un solo usuario se podrán simplificar las instalaciones de enlace porque:",
                    opts = listOf(
                        "Coinciden en el mismo lugar la CGP y el equipo de medida y no existe línea general de alimentación",
                        "No se requiere caja de protección ni ICP",
                        "La instalación pertenece a la empresa distribuidora",
                        "El usuario no necesita dispositivos de mando y protección"
                    ),
                    a = 0,
                    exp = "ITC-BT-12, punto 2.1: explica que para un solo usuario coinciden la CGP y el equipo de medida, y no existe LGA.",
                    ref = "ITC-BT-12"
                ),
                Question(
                    q = "Según el REBT, en instalaciones de un solo usuario el fusible de seguridad coincide con:",
                    opts = listOf(
                        "El fusible de la caja general de protección",
                        "El fusible del contador",
                        "El fusible del cuadro interior",
                        "El fusible del interruptor general automático"
                    ),
                    a = 0,
                    exp = "ITC-BT-12, punto 2.1: “…el fusible de seguridad coincide con el fusible de la CGP.”",
                    ref = "ITC-BT-12"
                ),
                Question(
                    q = "Según el REBT, el esquema para dos usuarios alimentados desde el mismo lugar:",
                    opts = listOf(
                        "Generaliza el esquema de un solo usuario y mantiene lo indicado para los fusibles de seguridad",
                        "Requiere dos líneas generales de alimentación independientes",
                        "Suprime la caja general de protección",
                        "No utiliza contadores independientes"
                    ),
                    a = 0,
                    exp = "ITC-BT-12, punto 2.2.1: “El esquema 2.1 puede generalizarse… Es válido lo indicado para los fusibles de seguridad (9).”",
                    ref = "ITC-BT-12"
                ),
                Question(
                    q = "Según el REBT, la colocación de contadores en forma centralizada en un lugar se utiliza normalmente en:",
                    opts = listOf(
                        "Conjuntos de edificación vertical u horizontal destinados a viviendas, edificios comerciales, de oficinas o concentración de industrias",
                        "Viviendas unifamiliares aisladas",
                        "Locales de pública concurrencia",
                        "Garajes y trasteros"
                    ),
                    a = 0,
                    exp = "ITC-BT-12, punto 2.2.2: indica literalmente los edificios donde se utiliza este esquema.",
                    ref = "ITC-BT-12"
                ),
                Question(
                    q = "Según el REBT, la centralización de contadores en más de un lugar se utiliza cuando:",
                    opts = listOf(
                        "La previsión de cargas hace aconsejable disponer de más de una centralización o en varias plantas",
                        "Se trata de edificios con un solo usuario",
                        "Los contadores se instalan dentro de cada vivienda",
                        "Las cargas sean inferiores a 5 kW"
                    ),
                    a = 0,
                    exp = "ITC-BT-12, punto 2.2.3: lo establece de manera literal.",
                    ref = "ITC-BT-12"
                ),
                Question(
                    q = "Según el REBT, el esquema 2.2.3 podrá aplicarse también en agrupaciones de viviendas:",
                    opts = listOf(
                        "En distribución horizontal dentro de un recinto privado",
                        "Solo si son verticales en bloque",
                        "Únicamente si comparten un cuadro general único",
                        "Cuando tengan menos de 10 viviendas"
                    ),
                    a = 0,
                    exp = "ITC-BT-12, punto 2.2.3: “…también podrá ser de aplicación en agrupaciones de viviendas en distribución horizontal dentro de un recinto privado.”",
                    ref = "ITC-BT-12"
                ),
                Question(
                    q = "Según el REBT, el esquema 2.2.3 será de aplicación en centralizaciones de contadores distribuidas mediante canalizaciones prefabricadas que cumplan:",
                    opts = listOf(
                        "La norma UNE-EN 60.439-2",
                        "La norma UNE 20460",
                        "La norma UNE 21123",
                        "La norma UNE-EN 50.102"
                    ),
                    a = 0,
                    exp = "ITC-BT-12, punto 2.2.3: “…será de aplicación [...] mediante canalizaciones eléctricas prefabricadas, que cumplan lo establecido en la norma UNE-EN 60.439-2.”",
                    ref = "ITC-BT-12"
                )
            )
        )
    )
}
