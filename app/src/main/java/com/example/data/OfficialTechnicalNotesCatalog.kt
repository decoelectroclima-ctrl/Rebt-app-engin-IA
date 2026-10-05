package com.example.data

data class TechnicalNoteSection(
    val id: String,
    val unitNumber: Int,
    val unitTitle: String,
    val themeNumber: Int,
    val themeTitle: String,
    val summary: String,
    val keyPoints: List<String>,
    val practicalTip: String,
    val normativeRefs: List<String>
)

object OfficialTechnicalNotesCatalog {

    val SECTIONS = listOf(
        // UNIDAD DIDÁCTICA 1
        TechnicalNoteSection(
            id = "ud1_t1_aspectos_generales",
            unitNumber = 1,
            unitTitle = "Estructura y Descripción de Instalaciones Eléctricas de BT",
            themeNumber = 1,
            themeTitle = "Aspectos Generales y Marco Normativo REBT",
            summary = "Estructura del sistema eléctrico nacional (generación, transporte en AT/MAT, subestaciones y distribución en BT). Campo de aplicación del Real Decreto 842/2002 y relación jerárquica entre Reglamento, Guías Técnicas, Normas Particulares de Distribuidora y Normas UNE.",
            keyPoints = listOf(
                "Definición de Baja Tensión: Tensión nominal ≤ 1.000 V en corriente alterna y ≤ 1.500 V en corriente continua.",
                "Tensiones normalizadas de distribución en España: 230 V entre fase y neutro (tensión simple) y 400 V entre fases (tensión compuesta) a 50 Hz.",
                "Obligatoriedad normativa: El REBT (29 Artículos y 52 ITCs) y las Normas Particulares de la distribuidora aprobadas son de obligado cumplimiento. La Guía Técnica de Aplicación es no vinculante.",
                "Clasificación de Instaladores Autorizados (ITC-BT-03): Categoría Básica (IBTB) para edificios de viviendas, comerciales y oficinas sin riesgo especial. Categoría Especialista (IBTE) para generadores BT, rótulos de alta tensión, quirófanos, ATEX, líneas de distribución, automatización y recarga de vehículos eléctricos.",
                "Documentación de diseño (ITC-BT-04): Requieren Proyecto visado las industrias > 20 kW, locales húmedos > 10 kW, garajes forzados (todos) y naturales > 5 plazas, pública concurrencia (todos) y viviendas unifamiliares > 50 kW o edificios > 100 kW. El resto se legaliza con Memoria Técnica de Diseño (MTD).",
                "Inspecciones Oficiales OCA (ITC-BT-05): Inspección inicial obligatoria antes de puesta en servicio en pública concurrencia, garajes > 25 plazas, piscinas > 10 kW. Inspección periódica cada 5 años (LPC, garajes > 25 plazas, ATEX) y cada 10 años en zonas comunes de edificios > 100 kW."
            ),
            practicalTip = "Para el examen: recuerda que la declaración responsable de instalador tiene validez por tiempo indefinido y en todo el territorio español.",
            normativeRefs = listOf("Art. 1-29 REBT", "ITC-BT-03", "ITC-BT-04", "ITC-BT-05")
        ),
        TechnicalNoteSection(
            id = "ud1_t2_estructura_instalacion",
            unitNumber = 1,
            unitTitle = "Estructura y Descripción de Instalaciones Eléctricas de BT",
            themeNumber = 2,
            themeTitle = "Estructura de la Instalación: Acometida, Enlace e Interiores",
            summary = "Delimitación de responsabilidades y propiedad técnica: desde la red de distribución pública hasta el cuadro general y los receptores interiores de los usuarios.",
            keyPoints = listOf(
                "Acometidas (ITC-BT-11): Propiedad de la empresa distribuidora. Aéreas (posadas en fachada mín. 2,5 m del suelo; tensadas sobre postes mín. 6 m sobre calzadas) y subterráneas (tubo de polietileno enterrado con cinta de señalización).",
                "Instalaciones de enlace (ITC-BT-12): Propiedad de los usuarios. Unen la CGP con el cuadro de mando y protección interior.",
                "Caja General de Protección - CGP (ITC-BT-13): Delimita el inicio de la propiedad del cliente. Se instala en fachada exterior accesible o en nicho en pared con puerta metálica IK10 y cerradura de la distribuidora. Aloja los fusibles de corte de la LGA.",
                "Caja de Protección y Medida - CPM: Para 1 o 2 usuarios alimentados desde el mismo punto; unifica la CGP y el contador en un solo conjunto, eliminando la LGA.",
                "Línea General de Alimentación - LGA (ITC-BT-14): Une la CGP con la centralización de contadores. Conductores de cobre (mín. 10 mm²) o aluminio (mín. 16 mm²), tensión 0,6/1 kV, no propagadores de llama y libres de halógenos (tipo RZ1-K o DZ1-K).",
                "Centralización de Contadores (ITC-BT-16): En armario si el número de contadores es ≤ 16, o en local exclusivo si es > 16 contadores. El local requiere altura mín. 2,30 m, pasillo libre de 1,10 m, puerta que abre hacia fuera (0,70 x 2 m), extintor móvil al exterior y alumbrado de emergencia de 5 lux durante 1 hora.",
                "Interruptor General de Maniobra - IGM: Obligatorio para más de 2 usuarios. Mínimo 160 A para cargas hasta 90 kW, y 250 A para cargas de 90 a 150 kW.",
                "Derivación Individual - DI (ITC-BT-15): Enlaza el contador con el cuadro interior. Tubo protector exterior mín. Ø 32 mm, reserva de 1 tubo por cada 10 DIs. Sección mínima de 6 mm² Cu para fases, neutro y protección PE, más hilo de mando rojo de 1,5 mm².",
                "Dispositivos Generales de Mando y Protección (ITC-BT-17): IGA omnipolar (poder de corte mín. 4.500 A), ID diferencial de 30 mA de alta sensibilidad, ICP (hasta 63 A) o maxímetro (> 63 A), y protector contra sobretensiones."
            ),
            practicalTip = "Regla de examen: En derivaciones individuales la caída de tensión máxima es del 1,5% para contadores centralizados y del 0,5% para centralizaciones parciales.",
            normativeRefs = listOf("ITC-BT-11", "ITC-BT-12", "ITC-BT-13", "ITC-BT-14", "ITC-BT-15", "ITC-BT-16", "ITC-BT-17")
        ),

        // UNIDAD DIDÁCTICA 2
        TechnicalNoteSection(
            id = "ud2_t3_prevision_cargas",
            unitNumber = 2,
            unitTitle = "Previsión y Cálculo de Potencias",
            themeNumber = 3,
            themeTitle = "Previsión de Cargas en Edificios y Receptores",
            summary = "Metodología reglamentaria para calcular la potencia total simultánea demandada por viviendas, servicios generales, garajes, locales comerciales y receptores industriales.",
            keyPoints = listOf(
                "Fórmula global de edificio: P_TOTAL = P_VIVIENDAS + P_SERV.GEN + P_LOCALES + P_GARAJES.",
                "Grados de electrificación en viviendas (ITC-BT-10): Básica (5.750 W / IGA 25 A) para cubrir circuitos C1 a C5. Elevada (9.200 W / IGA 40 A, 11.500 W / 50 A, 14.490 W / 63 A) obligatoria si superficie útil > 160 m², o si dispone de aire acondicionado, calefacción eléctrica, secadora o recarga de vehículo eléctrico (ITC-52).",
                "Coeficiente de simultaneidad para conjunto de viviendas (Cs): 1 viv -> 1; 2 viv -> 2; 3 viv -> 3; 4 viv -> 3,8; 5 viv -> 4,6; 10 viv -> 8,5; 20 viv -> 14,8; 21 viv -> 15,3. Para n > 21: Cs = 15,3 + (n - 21) · 0,5.",
                "Servicios generales: Ascensor según tabla ITA (ej. 4 personas 1 m/s = 7,5 kW x 1,3 = 9,75 kW). Alumbrado de portal y zonas comunes: 15 W/m² (incandescencia) o 14,4 W/m² (LED/fluorescencia). Caja de escalera: 7 W/m² (incandescencia) o 7,2 W/m² (LED/fluorescencia). Coeficiente simultaneidad = 1.",
                "Locales comerciales y oficinas: Mínimo 100 W/m² de superficie útil, con un mínimo absoluto de 3.450 W por local (Cs = 1).",
                "Garajes: Ventilación natural 10 W/m² (mín. 3.450 W). Ventilación forzada 20 W/m² (mín. 3.450 W).",
                "Concentración de industrias: Mínimo 125 W/m², con un mínimo absoluto de 10.350 W por nave o local.",
                "Cálculo de motores (ITC-BT-47): 1 motor -> P_cal = 1,25 · P_n. Varios motores -> P_cal = 1,25 · P_max + Σ P_resto.",
                "Lámparas de descarga (ITC-BT-44): P_cal = 1,8 · P_nominal_tubos.",
                "Equilibrado de fases: Las cargas monofásicas deben repartirse de manera simétrica entre las fases R, S, T para evitar sobrecargas en una fase y circulación excesiva de corriente por el neutro."
            ),
            practicalTip = "Si en un examen te dan la potencia del motor en CV, pásala a Watios multiplicando por 736 W (1 CV = 736 W) antes de aplicar el coeficiente 1,25.",
            normativeRefs = listOf("ITC-BT-10", "ITC-BT-25", "ITC-BT-44", "ITC-BT-47", "ITC-BT-52")
        ),

        // UNIDAD DIDÁCTICA 3
        TechnicalNoteSection(
            id = "ud3_t4_diseno_lineas",
            unitNumber = 3,
            unitTitle = "Diseño y Cálculo de Líneas Eléctricas en BT",
            themeNumber = 4,
            themeTitle = "Dimensionamiento por Caída de Tensión y Capacidad Térmica",
            summary = "Cálculo técnico y comprobación analítica de la sección de los conductores según el criterio de caída de tensión máxima reglamentaria y la intensidad admisible por calentamiento.",
            keyPoints = listOf(
                "Tres criterios obligatorios de cálculo: 1) Caída de tensión (tensión en bornes de la carga admisible); 2) Capacidad térmica (intensidad máxima no deteriora el aislamiento); 3) Cortocircuito (el cable soporta el esfuerzo térmico durante el tiempo de apertura).",
                "Conductividades reglamentarias a temperatura máxima de régimen: Cobre a 20°C: C = 56 | Cu a 70°C (PVC): C = 48 | Cu a 90°C (XLPE/EPR): C = 44. Aluminio a 20°C: C = 35 | Al a 70°C (PVC): C = 30 | Al a 90°C (XLPE/EPR): C = 28.",
                "Límites de caída de tensión (ITC-BT-14, 15, 19): LGA con contadores concentrados: 0,5% | LGA con centralizaciones parciales: 1,0% | DI contadores concentrados: 1,5% | DI centralizaciones parciales: 0,5% | DI suministro único (sin LGA): 1,5% | Circuitos interiores viviendas: 3% | Alumbrado en general: 3% | Fuerza motriz e industrial: 5%.",
                "Fórmulas de sección por caída de tensión: Monofásica: S = (2 · P · L) / (C · ΔV · U) | Trifásica: S = (P · L) / (C · ΔV · U).",
                "Comprobación térmica: La intensidad de diseño Ib debe ser menor o igual que la intensidad admisible corregida del conductor: I_b ≤ I_n ≤ I_z · f_temp · f_agrup.",
                "Conductor neutro: Misma sección que fase si fase ≤ 16 mm². Para secciones mayores se permite neutro reducido normalizado (ej. 25/16, 35/16, 50/25, 70/35, 95/50, 120/70, 150/70, 185/95, 240/120 mm²).",
                "Conductor de protección (PE): Si S_fase ≤ 16 mm² -> S_PE = S_fase. Si 16 < S_fase ≤ 35 mm² -> S_PE = 16 mm². Si S_fase > 35 mm² -> S_PE = S_fase / 2."
            ),
            practicalTip = "Recuerda que en viviendas el conductor de protección PE debe ser SIEMPRE de cobre y nunca inferior a 2,5 mm² bajo tubo protector.",
            normativeRefs = listOf("ITC-BT-14", "ITC-BT-15", "ITC-BT-19", "Guía Técnica BT Anexo 2")
        ),

        // UNIDAD DIDÁCTICA 4
        TechnicalNoteSection(
            id = "ud4_t5_t6_cortocircuito_protecciones",
            unitNumber = 4,
            unitTitle = "Corrientes de Cortocircuito y Protecciones Eléctricas",
            themeNumber = 5,
            themeTitle = "Cortocircuito, Magnetotérmicos, Diferenciales y Sobretensiones",
            summary = "Análisis de fallos de impedancia despreciable, selección de aparamenta de corte, coordinación por selectividad y filiación, y protección contra sobretensiones transitorias y permanentes.",
            keyPoints = listOf(
                "Tipos de cortocircuito: Trifásico franco (Icc3 = 400 / [√3 · Z1]), bifásico (Icc2 = 400 / [2 · ZF]), monofásico fase-neutro (Icc1 = 230 / [ZF + ZN]).",
                "Icc máxima (al inicio de línea): Se calcula a 20°C. Sirve para elegir el poder de corte del IGA o fusibles (debe ser mayor que la Icc máxima).",
                "Icc mínima (al final de línea): Se calcula a temperatura máxima de servicio (70°C o 90°C). Sirve para garantizar que el relé electromagnético del magnetotérmico dispara de forma instantánea.",
                "Curvas de disparo magnetotérmico: Curva B (3 a 5 In) para generadores y líneas muy largas; Curva C (5 a 10 In) para usos generales e instalaciones interiores; Curva D (10 a 20 In) para motores con fuerte arranque y transformadores.",
                "Interruptores diferenciales (ITC-BT-24): Tipo AC (corriente alterna senoidal); Tipo A (alterna senoidal y continua pulsante, obligatorio en cargadores VE e informática); Tipo B (todo tipo de fugas, incluidas continuas alisadas).",
                "Sensibilidad diferencial: 30 mA en viviendas y alumbrado (protección de personas contra contactos indirectos); 300 mA en instalaciones industriales, motores y protección contra incendios.",
                "Sobretensiones transitorias (ITC-BT-23): Originadas por rayos o maniobras de red. Se protegen con limitadores (varistores ZnO y descargadores de gas). Tipo 1 (10/350 μs) para acometidas con pararrayos; Tipo 2 (8/20 μs) para cuadros de distribución general; Tipo 3 (8/20 μs) para equipos sensibles en el punto de utilización.",
                "Sobretensiones permanentes: Provocadas por corte del neutro en la red de distribución. Se protegen con bobinas de disparo asociadas al IGA que abren cuando la tensión supera en más de un 10% el valor nominal (≥ 255 V)."
            ),
            practicalTip = "Para selectividad de diferenciales: el diferencial aguas arriba debe ser de tipo selectivo (S) y tener una sensibilidad de al menos el doble (habitualmente 300 mA frente a 30 mA aguas abajo).",
            normativeRefs = listOf("ITC-BT-17", "ITC-BT-22", "ITC-BT-23", "ITC-BT-24", "UNE-EN 61008")
        ),

        // UNIDAD DIDÁCTICA 5
        TechnicalNoteSection(
            id = "ud5_t7_puesta_tierra_verificaciones",
            unitNumber = 5,
            unitTitle = "Instalaciones de Puesta a Tierra y Verificaciones de Taller",
            themeNumber = 7,
            themeTitle = "Puesta a Tierra y Verificaciones Técnicas",
            summary = "Puesta a tierra reglamentaria según ITC-BT-18, sistemas de distribución TT/TN/IT, fórmulas de electrodos y protocolo completo de verificación con instrumental multifunción para la prueba práctica.",
            keyPoints = listOf(
                "Finalidad de la puesta a tierra: Limitar la tensión de las masas metálicas con respecto a tierra y asegurar la actuación rápida de las protecciones diferenciales.",
                "Tensiones de contacto límite convencionales (UL): 50 V en locales secos y 24 V en locales mojados, húmedos o de pública concurrencia.",
                "Esquema de distribución oficial en España: Esquema TT (neutro del transformador puesto a tierra y masas de la instalación conectadas a tierra independiente). En esquemas TT el uso del interruptor diferencial es obligatorio.",
                "Electrodos y fórmulas de resistencia (ITC-BT-18): Pica vertical R = ρ / L; Conductor enterrado en zanja horizontal R = 2ρ / L; Placa enterrada R = 0,8ρ / P; Malla mallada R = ρ / (4r) + ρ / L.",
                "Anillo de cimentación (ITC-BT-26): En edificios de viviendas es obligatorio un anillo cerrado perimetral en la cimentación con cable de cobre desnudo de mín. 35 mm² a profundidad ≥ 0,5 m (o 0,8 m en zonas con nieve/heladas).",
                "Separación de tierras con centros de transformación (ITC-RAT-13): Deben separarse cuando la tensión transferida sea peligrosa. Distancia mínima D = (ρ · Id) / (2π · U).",
                "Protocolo de Verificación Práctica en Taller (SIN TENSIÓN): 1) Continuidad del conductor de protección PE con multifunción en R_LO: R_low ≤ 0,20 Ω (200 mΩ). 2) Aislamiento entre fases y a tierra con megóhmetro a 500 V CC: R_iso ≥ 0,50 MΩ (500.000 Ω).",
                "Protocolo de Verificación Práctica en Taller (CON TENSIÓN): 1) Impedancia de bucle Zs (F-N) con multifunción en ZI NO TRIP: verificar que Zs sea inferior al valor límite del calibre del PIA (ej. < 1,43 Ω para 16 A). 2) Resistencia de tierra mediante método de bucle: Rt ≤ 75 Ω. 3) Tensión de contacto: Uc = Rt · 0,03 A (debe ser ≤ 24 V en local mojado). 4) Tiempo de disparo del diferencial de 30 mA a corriente nominal: t < 300 ms."
            ),
            practicalTip = "En la prueba práctica de taller: antes de medir aislamiento o continuidad recuerda bajar SIEMPRE el IGA y los magnetotérmicos (ensayos sin tensión). Antes de medir bucle o disparo del diferencial, avisa al examinador y conecta la tensión.",
            normativeRefs = listOf("ITC-BT-05", "ITC-BT-18", "ITC-BT-24", "ITC-BT-26", "ITC-RAT-13")
        )
    )
}
