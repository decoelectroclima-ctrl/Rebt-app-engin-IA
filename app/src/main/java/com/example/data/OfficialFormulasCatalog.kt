package com.example.data

data class OfficialFormula(
    val id: String,
    val category: String, // "Previsión y Potencias", "Caída de Tensión y Secciones", "Capacidad Térmica", "Cortocircuito", "Puesta a Tierra", "Taller y Verificación"
    val title: String,
    val formulaDisplay: String,
    val description: String,
    val variables: List<Pair<String, String>>,
    val example: String,
    val itcRef: String
)

object OfficialFormulasCatalog {

    val CATEGORIES = listOf(
        "Todas",
        "Previsión y Potencias",
        "Caída de Tensión y Secciones",
        "Capacidad Térmica",
        "Cortocircuito",
        "Puesta a Tierra",
        "Taller y Verificación"
    )

    val FORMULAS = listOf(
        // 1. PREVISIÓN Y POTENCIAS
        OfficialFormula(
            id = "prev_viviendas_n_mayor_21",
            category = "Previsión y Potencias",
            title = "Carga Total de un Conjunto de Viviendas (n > 21)",
            formulaDisplay = "P_viv = [15,3 + (n - 21) · 0,5] · P_m",
            description = "Cálculo de la potencia simultánea en edificios residenciales con más de 21 viviendas según ITC-BT-10.",
            variables = listOf(
                "n" to "Número total de viviendas del edificio",
                "P_m" to "Media aritmética de las potencias de las viviendas (Básica 5.750 W / Elevada 9.200 W)",
                "15,3" to "Coeficiente de simultaneidad base para 21 viviendas",
                "0,5" to "Factor de incremento por cada vivienda adicional a partir de 21"
            ),
            example = "Para 30 viviendas básicas (5.750 W): P_viv = [15,3 + (30-21)·0,5] · 5.750 W = [15,3 + 4,5] · 5.750 W = 19,8 · 5.750 W = 113.850 W.",
            itcRef = "ITC-BT-10 §3.1"
        ),
        OfficialFormula(
            id = "prev_locales_oficinas",
            category = "Previsión y Potencias",
            title = "Previsión de Carga en Locales Comerciales y Oficinas",
            formulaDisplay = "P_loc = max(100 W/m² · Superficie, 3.450 W)",
            description = "Potencia mínima asignada a locales y oficinas en baja tensión, con coeficiente de simultaneidad Cs = 1.",
            variables = listOf(
                "Superficie" to "Superficie útil del local u oficina en m²",
                "100 W/m²" to "Ratio mínimo reglamentario por metro cuadrado",
                "3.450 W" to "Mínimo absoluto por cada local independiente (230 V)"
            ),
            example = "Local de 25 m²: 25 · 100 = 2.500 W < 3.450 W -> Se adoptan 3.450 W. Local de 120 m²: 120 · 100 = 12.000 W.",
            itcRef = "ITC-BT-10 §4.1"
        ),
        OfficialFormula(
            id = "prev_garajes",
            category = "Previsión y Potencias",
            title = "Previsión de Carga en Garajes y Aparcamientos",
            formulaDisplay = "Natural: P = max(10 W/m², 3.450 W) | Forzada: P = max(20 W/m², 3.450 W)",
            description = "Potencia mínima para aparcamientos según el tipo de ventilación del local.",
            variables = listOf(
                "Ventilación Natural" to "Mínimo 10 W/m² (mín. 3.450 W por planta/garaje)",
                "Ventilación Forzada" to "Mínimo 20 W/m² (mín. 3.450 W por planta/garaje)"
            ),
            example = "Garaje forzado de 500 m²: P = 500 · 20 W/m² = 10.000 W.",
            itcRef = "ITC-BT-10 §4.2"
        ),
        OfficialFormula(
            id = "prev_industrias",
            category = "Previsión y Potencias",
            title = "Concentración de Industrias",
            formulaDisplay = "P_ind = max(125 W/m² · Superficie, 10.350 W)",
            description = "Potencia mínima reglamentaria por cada nave o local en polígonos o edificios industriales concentrados.",
            variables = listOf(
                "125 W/m²" to "Ratio mínimo reglamentario por m²",
                "10.350 W" to "Mínimo absoluto por local u ocupante (230/400 V)"
            ),
            example = "Nave de 300 m²: P = 300 · 125 = 37.500 W.",
            itcRef = "ITC-BT-10 §4.3"
        ),
        OfficialFormula(
            id = "prev_recarga_ve",
            category = "Previsión y Potencias",
            title = "Infraestructura de Recarga de Vehículo Eléctrico (VE)",
            formulaDisplay = "P_VE = 3.680 W · (10% plazas) · Cs",
            description = "Previsión de carga para plazas de estacionamiento en edificios en régimen de propiedad horizontal.",
            variables = listOf(
                "3.680 W" to "Potencia unitaria mínima asignada a cada punto de recarga modo 3 (16 A a 230 V)",
                "10% plazas" to "Proporción mínima de plazas de aparcamiento a prever con canalización",
                "Cs" to "Coeficiente de simultaneidad según sistema SPL (Sistema de Protección de Línea)"
            ),
            example = "Aparcamiento de 50 plazas: 10% = 5 plazas. P = 5 · 3.680 W · 1,0 = 18.400 W.",
            itcRef = "ITC-BT-52 §3"
        ),
        OfficialFormula(
            id = "pot_motores",
            category = "Previsión y Potencias",
            title = "Potencia de Cálculo para Motores Eléctricos",
            formulaDisplay = "1 motor: P_cal = 1,25 · P_n | Varios: P_cal = 1,25 · P_max + Σ P_resto",
            description = "Mayoración reglamentaria del 25% para absorber la sobreintensidad de arranque.",
            variables = listOf(
                "1,25" to "Factor de mayoración reglamentario (ITC-BT-47)",
                "P_max" to "Potencia nominal del motor de mayor potencia",
                "Σ P_resto" to "Suma de las potencias de los demás motores restantes",
                "1 CV" to "736 W (Caballo de Vapor) -> mayorado x1,25 = 920 W",
                "1 HP" to "746 W (Horse Power) -> mayorado x1,25 = 932,5 W"
            ),
            example = "Tres motores de 4 kW, 3 kW y 2 kW: P_cal = 1,25 · 4 kW + 3 kW + 2 kW = 5 kW + 5 kW = 10 kW.",
            itcRef = "ITC-BT-47 §2"
        ),
        OfficialFormula(
            id = "pot_alumbrado_descarga",
            category = "Previsión y Potencias",
            title = "Lámparas de Descarga, Fluorescencia y LED",
            formulaDisplay = "P_cal = 1,8 · P_lamparas",
            description = "Mayoración del 80% (factor 1,8) por reactancias, arrancadores, balastos y corrientes armónicas.",
            variables = listOf(
                "1,8" to "Factor reglamentario para compensar impedancia de balastos y armónicos",
                "P_lamparas" to "Suma de la potencia nominal de los tubos o luminarias"
            ),
            example = "Línea con 10 luminarias de 2x36 W: P_n = 10 · 72 W = 720 W -> P_cal = 1,8 · 720 W = 1.296 W.",
            itcRef = "ITC-BT-44 §3.1"
        ),
        OfficialFormula(
            id = "pot_ascensores",
            category = "Previsión y Potencias",
            title = "Aparatos Elevadores y Ascensores",
            formulaDisplay = "P_cal = 1,3 · P_ascensor",
            description = "Mayoración del 30% aplicada a los motores de aparatos elevadores por ciclo de maniobra frecuente.",
            variables = listOf(
                "1,3" to "Factor de servicio para elevación",
                "P_ascensor" to "Potencia nominal de tracción del ascensor"
            ),
            example = "Ascensor de 7,5 kW: P_cal = 1,3 · 7.500 W = 9.750 W.",
            itcRef = "ITC-BT-47 §5"
        ),

        // 2. CAÍDA DE TENSIÓN Y SECCIONES
        OfficialFormula(
            id = "intensidad_monofasica",
            category = "Caída de Tensión y Secciones",
            title = "Intensidad en Corriente Alterna Monofásica",
            formulaDisplay = "I = P / (U · cos φ)",
            description = "Corriente de servicio que circula por una línea monofásica a 230 V.",
            variables = listOf(
                "P" to "Potencia activa en Watios (W)",
                "U" to "Tensión de servicio (230 V entre fase y neutro)",
                "cos φ" to "Factor de potencia del receptor (si no se indica, usar 1)"
            ),
            example = "Carga de 4.600 W a 230 V con cos φ = 1: I = 4.600 / 230 = 20 A.",
            itcRef = "Fundamentos Electrotecnia"
        ),
        OfficialFormula(
            id = "intensidad_trifasica",
            category = "Caída de Tensión y Secciones",
            title = "Intensidad en Corriente Alterna Trifásica",
            formulaDisplay = "I = P / (√3 · U · cos φ)",
            description = "Corriente de servicio por fase en una línea trifásica a 400 V.",
            variables = listOf(
                "P" to "Potencia activa trifásica total en Watios (W)",
                "U" to "Tensión de línea compuesta (400 V entre fases)",
                "√3" to "1,73205",
                "cos φ" to "Factor de potencia global de la instalación"
            ),
            example = "Motor trifásico de 15 kW a 400 V con cos φ = 0,85: I = 15.000 / (1,732 · 400 · 0,85) = 25,48 A.",
            itcRef = "Fundamentos Electrotecnia"
        ),
        OfficialFormula(
            id = "seccion_caida_monofasica",
            category = "Caída de Tensión y Secciones",
            title = "Sección Mínima por Caída de Tensión (Monofásica)",
            formulaDisplay = "S = (2 · P · L) / (C · ΔV · U) = (2 · I · L · cos φ) / (C · ΔV)",
            description = "Dimensionamiento del conductor de fase y neutro para no superar la caída de tensión reglamentaria en monofásica.",
            variables = listOf(
                "S" to "Sección del conductor en mm²",
                "P" to "Potencia activa en Watios (W)",
                "L" to "Longitud de la línea en metros (m)",
                "C" to "Conductividad del metal (Cu: 56 a 20°C, 48 a 70°C, 44 a 90°C | Al: 35 a 20°C, 30 a 70°C, 28 a 90°C)",
                "ΔV" to "Caída de tensión máxima admisible en Voltios (V)",
                "U" to "Tensión nominal (230 V)"
            ),
            example = "P = 5.750 W, L = 20 m, Cu XLPE (C=44), Δe = 1,5% (ΔV = 3,45 V): S = (2 · 5.750 · 20) / (44 · 3,45 · 230) = 230.000 / 34.914 = 6,58 mm² -> Sección comercial: 10 mm².",
            itcRef = "ITC-BT-19 Guía Anexo 2"
        ),
        OfficialFormula(
            id = "seccion_caida_trifasica",
            category = "Caída de Tensión y Secciones",
            title = "Sección Mínima por Caída de Tensión (Trifásica)",
            formulaDisplay = "S = (P · L) / (C · ΔV · U) = (√3 · I · L · cos φ) / (C · ΔV)",
            description = "Dimensionamiento de los conductores de fase para líneas trifásicas a 400 V equilibradas.",
            variables = listOf(
                "S" to "Sección de los conductores de fase en mm²",
                "P" to "Potencia activa total trifásica en Watios (W)",
                "L" to "Longitud de la línea en metros (m)",
                "C" to "Conductividad térmica del metal",
                "ΔV" to "Caída de tensión máxima admisible en Voltios (400 V · Δe%)",
                "U" to "Tensión de línea (400 V)"
            ),
            example = "P = 40 kW, L = 50 m, Cu XLPE (C=44), Δe = 0,5% en LGA (ΔV = 2 V): S = (40.000 · 50) / (44 · 2 · 400) = 2.000.000 / 35.200 = 56,8 mm² -> Sección normalizada: 70 mm².",
            itcRef = "ITC-BT-14 §3"
        ),
        OfficialFormula(
            id = "porcentaje_caida_tension",
            category = "Caída de Tensión y Secciones",
            title = "Cálculo del Porcentaje de Caída de Tensión",
            formulaDisplay = "Mono: %e = (2 · P · L · 100) / (C · S · U²) | Tri: %e = (P · L · 100) / (C · S · U²)",
            description = "Comprobación de la caída de tensión porcentual producida en una línea existente.",
            variables = listOf(
                "%e" to "Porcentaje de caída de tensión producido",
                "S" to "Sección instalada en mm²",
                "U" to "230 V (monofásica, U² = 52.900) o 400 V (trifásica, U² = 160.000)"
            ),
            example = "Línea trifásica Cu XLPE (C=44), S = 25 mm², P = 20 kW, L = 30 m: %e = (20.000 · 30 · 100) / (44 · 25 · 160.000) = 60.000.000 / 176.000.000 = 0,34% (Cumple LGA < 0,5%).",
            itcRef = "ITC-BT-19 Guía"
        ),

        // 3. CAPACIDAD TÉRMICA Y PROTECCIONES
        OfficialFormula(
            id = "condicion_sobrecarga",
            category = "Capacidad Térmica",
            title = "Condición de Coordinación de Sobrecarga",
            formulaDisplay = "I_b ≤ I_n ≤ I_z  y  I_2 ≤ 1,45 · I_z",
            description = "Regla de oro de coordinación para proteger cualquier línea contra calentamiento inadmisible.",
            variables = listOf(
                "I_b" to "Intensidad de diseño o servicio que transporta el circuito",
                "I_n" to "Calibre o corriente nominal del interruptor automático (o fusible)",
                "I_z" to "Intensidad máxima admisible del conductor según método de instalación y temperatura",
                "I_2" to "Corriente de funcionamiento seguro del dispositivo (1,45 · I_n para magnetotérmicos industriales/domésticos)"
            ),
            example = "Circuito C2 (tomas): Ib = 14 A -> In = 16 A -> Conductor 2,5 mm² Cu empotrado en tubo PVC: Iz = 20 A -> Cumple: 14 ≤ 16 ≤ 20.",
            itcRef = "ITC-BT-22 §1.1"
        ),
        OfficialFormula(
            id = "regla_fusible_iz",
            category = "Capacidad Térmica",
            title = "Regla Práctica para Fusibles de Enlace",
            formulaDisplay = "I_b ≤ I_nominal_fusible ≤ I_z · 0,91",
            description = "Comprobación estricta para fusibles tipo gG para garantizar el disparo antes del límite térmico.",
            variables = listOf(
                "0,91" to "Factor de reducción por curva de fusión convencional (1,6 In frente a 1,45 Iz)"
            ),
            example = "Cable de cobre con Iz = 105 A: In_fusible ≤ 105 · 0,91 = 95,5 A -> Se escoge fusible normalizado de 80 A.",
            itcRef = "Guía Técnica BT-14"
        ),

        // 4. CORTOCIRCUITO
        OfficialFormula(
            id = "cortocircuito_trifasico",
            category = "Cortocircuito",
            title = "Corriente de Cortocircuito Trifásico Franco",
            formulaDisplay = "I_cc3 = U / (√3 · Z_1) = 400 / (√3 · Z_1)",
            description = "Corriente máxima de defecto para calcular el poder de corte necesario en cabecera.",
            variables = listOf(
                "Z_1" to "Impedancia directa del bucle de cortocircuito (R + jX)",
                "U" to "Tensión compuesta (400 V)"
            ),
            example = "Para Z_1 = 0,02 Ω: I_cc3 = 400 / (1,732 · 0,02) = 11.547 A = 11,55 kA -> Requiere IGA con poder de corte ≥ 15 kA.",
            itcRef = "Guía Técnica BT Anexo 3"
        ),
        OfficialFormula(
            id = "cortocircuito_monofasico",
            category = "Cortocircuito",
            title = "Corriente de Cortocircuito Monofásico Fase-Neutro",
            formulaDisplay = "I_cc1 = U_0 / (Z_F + Z_N) = 230 / (Z_F + Z_N)",
            description = "Determina la corriente mínima al final de línea para verificar que el relé magnético dispara instantáneamente.",
            variables = listOf(
                "U_0" to "Tensión simple fase-neutro (230 V)",
                "Z_F" to "Impedancia del conductor de fase a temperatura de cortocircuito",
                "Z_N" to "Impedancia del conductor neutro"
            ),
            example = "Z_F + Z_N = 0,46 Ω: I_cc1 = 230 / 0,46 = 500 A. Si se usa un magnetotérmico C16 (disparo magnético máx 10·In = 160 A) -> 500 A > 160 A (Disparo asegurado).",
            itcRef = "Guía Técnica BT Anexo 3"
        ),
        OfficialFormula(
            id = "comprobacion_termica_cc",
            category = "Cortocircuito",
            title = "Comprobación Térmica del Cable ante Cortocircuito",
            formulaDisplay = "I_cc² · t ≤ K² · S²   =>   t ≥ (K² · S²) / I_cc²",
            description = "Condición de no deterioro del aislamiento del conductor durante el tiempo de apertura de la protección.",
            variables = listOf(
                "I_cc" to "Corriente de cortocircuito en Amperios (A)",
                "t" to "Tiempo de corte de la protección en segundos (s)",
                "S" to "Sección del conductor en mm²",
                "K" to "Constante del cable: Cu/PVC = 115, Cu/XLPE = 135, Al/PVC = 74, Al/XLPE = 87"
            ),
            example = "Cable Cu/XLPE (K=135) de S = 10 mm², cortocircuito de 3.000 A: Energía soportada = (135 · 10)² = 1.822.500 A²·s. Tiempo máx soportado = 1.822.500 / 3.000² = 0,20 s.",
            itcRef = "UNE 20460-4-43 / ITC-BT-22"
        ),

        // 5. PUESTA A TIERRA
        OfficialFormula(
            id = "tierra_tension_contacto",
            category = "Puesta a Tierra",
            title = "Tensión de Contacto Máxima y Resistencia de Tierra",
            formulaDisplay = "R_A · I_a ≤ U_L   =>   R_A ≤ U_L / I_a",
            description = "Límite reglamentario de la resistencia de tierra asociada a la sensibilidad del interruptor diferencial.",
            variables = listOf(
                "R_A" to "Resistencia de la toma de tierra en Ohmios (Ω)",
                "I_a" to "Sensibilidad de disparo del interruptor diferencial (ej. 0,03 A para 30 mA, 0,3 A para 300 mA)",
                "U_L" to "Tensión de contacto límite convencional: 50 V en locales secos, 24 V en locales mojados o húmedos"
            ),
            example = "Vivienda (local mojado en baños, U_L = 24 V) con diferencial de 30 mA (0,03 A): R_A ≤ 24 / 0,03 = 800 Ω.",
            itcRef = "ITC-BT-18 §3 / ITC-BT-24"
        ),
        OfficialFormula(
            id = "tierra_pica_vertical",
            category = "Puesta a Tierra",
            title = "Resistencia de Pica Vertical Enterrada",
            formulaDisplay = "R = ρ / L",
            description = "Resistencia teórica de una pica de puesta a tierra hincada verticalmente en el terreno.",
            variables = listOf(
                "ρ" to "Resistividad del terreno en Ohm · metro (Ω·m)",
                "L" to "Longitud hincada de la pica en metros (m)"
            ),
            example = "Terreno de arcilla plástica (ρ = 50 Ω·m) con pica de L = 2 m: R = 50 / 2 = 25 Ω.",
            itcRef = "ITC-BT-18 Tabla 3"
        ),
        OfficialFormula(
            id = "tierra_conductor_horizontal",
            category = "Puesta a Tierra",
            title = "Resistencia de Conductor Enterrado Horizontalmente",
            formulaDisplay = "R = 2 · ρ / L",
            description = "Resistencia de un anillo de cimentación o zanja perimetral con cable de cobre desnudo de 35 mm².",
            variables = listOf(
                "ρ" to "Resistividad del terreno en Ω·m",
                "L" to "Longitud total del bucle o zanja en metros (m)"
            ),
            example = "Anillo perimetral de edificio de L = 40 m en terreno arcilloso (ρ = 100 Ω·m): R = (2 · 100) / 40 = 5 Ω.",
            itcRef = "ITC-BT-18 Tabla 3"
        ),
        OfficialFormula(
            id = "tierra_placa_malla",
            category = "Puesta a Tierra",
            title = "Resistencia de Placa y Malla de Tierra",
            formulaDisplay = "Placa: R = 0,8 · ρ / P | Malla: R = (ρ / 4r) + (ρ / L)",
            description = "Cálculo aproximado para placas verticales o mallas malladas de tierra.",
            variables = listOf(
                "P" to "Perímetro de la placa en metros (m)",
                "r" to "Radio de un círculo de superficie equivalente al área de la malla (m)",
                "L" to "Longitud total de los conductores mallados (m)"
            ),
            example = "Placa de 1x0,5 m (P = 3 m) en terreno de 60 Ω·m: R = 0,8 · 60 / 3 = 16 Ω.",
            itcRef = "ITC-BT-18 Tabla 3"
        ),

        // 6. TALLER Y VERIFICACIÓN (EXAMEN PRÁCTICO)
        OfficialFormula(
            id = "verif_continuidad",
            category = "Taller y Verificación",
            title = "Medida de Continuidad del Conductor de Protección (PE)",
            formulaDisplay = "R_LOW ≤ 0,20 Ω  (200 mΩ) a 200 mA",
            description = "Ensayo obligatorio sin tensión con equipo multifunción en escala R_LO. Comprueba la continuidad entre el embarrado de tierra del cuadro y cada toma o masa metálica.",
            variables = listOf(
                "R_LOW" to "Resistencia óhmica medida con inversión de polaridad",
                "0,20 Ω" to "Umbral máximo de aceptación reglamentaria"
            ),
            example = "Medida obtenida en el borne de tierra de la toma del baño: 0,14 Ω <= 0,20 Ω -> CORRECTO.",
            itcRef = "ITC-BT-05 / Guía Técnica Verificaciones"
        ),
        OfficialFormula(
            id = "verif_aislamiento",
            category = "Taller y Verificación",
            title = "Resistencia de Aislamiento de la Instalación",
            formulaDisplay = "R_ISO ≥ 0,50 MΩ  (500.000 Ω) a 500 V CC",
            description = "Ensayo sin tensión con megóhmetro entre conductores activos (fase-fase, fase-neutro) y entre cada activo y tierra (PE).",
            variables = listOf(
                "500 V" to "Tensión de ensayo continua normalizada para instalaciones de 230/400 V",
                "0,50 MΩ" to "Valor mínimo reglamentario exigido por ITC-BT-19 e ITC-BT-05"
            ),
            example = "Medida obtenida entre fase y neutro en cuadro principal: > 500 MΩ -> Aislamiento perfecto (Apto).",
            itcRef = "ITC-BT-19 §2.9 / ITC-BT-05"
        ),
        OfficialFormula(
            id = "verif_bucle_zs",
            category = "Taller y Verificación",
            title = "Impedancia de Bucle de Defecto (Zs) y Criterio por Calibre",
            formulaDisplay = "Z_s ≤ U_0 / I_a   (U_0 = 230 V)",
            description = "Medida con tensión con multifunción en modo Z_I NO TRIP. La impedancia de bucle debe ser menor al umbral del magnetotérmico para asegurar corte instantáneo.",
            variables = listOf(
                "6 A" to "Zs < 3,81 Ω",
                "10 A" to "Zs < 2,30 Ω",
                "16 A" to "Zs < 1,43 Ω",
                "20 A" to "Zs < 1,15 Ω",
                "25 A" to "Zs < 0,92 Ω",
                "32 A" to "Zs < 0,72 Ω",
                "40 A" to "Zs < 0,57 Ω"
            ),
            example = "Circuito protegido por PIA de 16 A: medida de Zs = 0,50 Ω. Como 0,50 Ω < 1,43 Ω -> Cumple (Apto).",
            itcRef = "Guía Técnica BT-05 / UNE 20460"
        ),
        OfficialFormula(
            id = "verif_tiempo_disparo_diferencial",
            category = "Taller y Verificación",
            title = "Tiempo de Disparo de Diferenciales (In = 30 mA)",
            formulaDisplay = "t_disparo < 300 ms a 1 · I_Δn  |  t < 40 ms a 5 · I_Δn",
            description = "Comprobación de la velocidad de desconexión del interruptor diferencial pulsando el botón de prueba del multifunción en 0° y 180°.",
            variables = listOf(
                "I_Δn" to "Sensibilidad nominal diferencial (30 mA)",
                "300 ms" to "Tiempo máximo admisible a corriente nominal",
                "40 ms" to "Tiempo máximo admisible a 5 veces la corriente nominal"
            ),
            example = "Diferencial general de 40 A / 30 mA: tiempo de disparo medido = 28 ms < 300 ms -> Disparo ultra-rápido conforme.",
            itcRef = "ITC-BT-24 / UNE-EN 61008"
        )
    )
}
