package com.example.data

object DocumentsCatalog {

    val ALL_DOCUMENTS: List<SharedDocument> = listOf(
        // Apuntes 1: Cuadros CGMP y Circuitos Vivienda
        SharedDocument(
            id = "apuntes_cgmp_circuitos",
            title = "Apuntes Técnicos: Cuadro CGMP y Circuitos C1 a C13",
            description = "Resumen exhaustivo de calibres PIA, secciones de conductores, interruptores diferenciales y reglas de diseño para electrificación básica y elevada.",
            fileName = "Apuntes_CGMP_Circuitos_C1_C13_Oficial.pdf",
            fileSize = "1.4 MB",
            type = "Apuntes",
            content = """
# APUNTES TÉCNICOS OFICIALES: CUADRO GENERAL (CGMP) Y CIRCUITOS C1 A C13
## Reglamento Electrotécnico de Baja Tensión (ITC-BT-17, ITC-BT-25, ITC-BT-26)

---

### 1. COMPOSICIÓN REGLAMENTARIA DEL CUADRO GENERAL (CGMP)
El Cuadro General de Mando y Protección debe situarse lo más cerca posible del punto de entrada de la derivación individual a la vivienda (altura reglamentaria del dispositivo de mando: entre 1,40 m y 2,00 m sobre el nivel del suelo).

1. **Interruptor General Automático (IGA)**:
   - Corte omnipolar obligatorio (fase y neutro).
   - Poder de corte mínimo: 4.500 A (en cuadros de vivienda protegidos por fusibles de DI).
   - Calibre mínimo: 25 A para electrificación básica (5.750 W a 230 V) o 40 A para electrificación elevada (9.200 W a 230 V).
   - Debe ser independiente del interruptor de control de potencia (ICP) que actualmente está integrado en los contadores digitales inteligentes.

2. **Protectores contra Sobretensiones (ITC-BT-23)**:
   - Obligatorio protector contra sobretensiones transitorias y permanentes según la normativa de la comunidad autónoma o si la línea de alimentación aérea es susceptible de descargas atmosféricas.

3. **Interruptores Diferenciales (ID)**:
   - Sensibilidad reglamentaria máxima: 30 mA (alta sensibilidad para protección de personas contra contactos indirectos).
   - Calibre nominal: mínimo igual o superior al calibre del IGA que lo protege (mínimo 40 A nominal).
   - Regla de oro: Máximo 5 circuitos por cada interruptor diferencial. En electrificación elevada se requieren obligatoriamente dos o más diferenciales.
   - Tipo de diferencial: Tipo A o AC (Tipo A obligatorio en circuitos con electrónica de potencia, variadores o recarga de VE).

---

### 2. CIRCUITOS DE ELECTRIFICACIÓN BÁSICA (Mínimo 5.750 W / 230 V)
- **C1 (Iluminación)**:
  - PIA: 10 A
  - Sección mínima: 1,5 mm²
  - Tubo protector: diámetro exterior mínimo 16 mm
  - Puntos máximos autorizados: 30 puntos de luz
- **C2 (Tomas de corriente de uso general y frigorífico)**:
  - PIA: 16 A
  - Sección mínima: 2,5 mm²
  - Tubo protector: 20 mm
  - Tomas máximas: 20 tomas de 16 A 2P+T
- **C3 (Cocina y Horno eléctrico)**:
  - PIA: 25 A
  - Sección mínima: 6 mm²
  - Tubo protector: 25 mm
  - Base de enchufe: toma especial de 25 A
- **C4 (Lavadora, Lavavajillas y Termo eléctrico)**:
  - Opción tradicional: PIA 20 A con sección de 4 mm² y tubo de 20 mm alimentando 3 tomas de 16 A.
  - Opción desglosada oficial (muy habitual en examen): 3 circuitos independientes protegidos cada uno por PIA de 16 A con cables de 2,5 mm² (C4.1 Lavadora, C4.2 Lavavajillas, C4.3 Termo).
- **C5 (Tomas de corriente de baños y auxiliares de cocina)**:
  - PIA: 16 A
  - Sección mínima: 2,5 mm²
  - Tubo protector: 20 mm
  - Tomas máximas: 6 tomas

---

### 3. CIRCUITOS DE ELECTRIFICACIÓN ELEVADA (Mínimo 9.200 W / 230 V)
Obligatoria cuando la vivienda tiene superficie útil > 160 m², calefacción eléctrica, aire acondicionado o más de 30 puntos de luz / 20 tomas.
- **C6**: Adicional de tipo C1 por cada 30 puntos de luz extra.
- **C7**: Adicional de tipo C2 por cada 20 tomas de uso general extra.
- **C8**: Calefacción eléctrica (PIA 25 A, sección 6 mm², tubo 25 mm).
- **C9**: Aire acondicionado (PIA 25 A, sección 6 mm², tubo 25 mm).
- **C10**: Secadora independiente (PIA 16 A, sección 2,5 mm², tubo 20 mm).
- **C11**: Automatización y domótica (PIA 10 A, sección 1,5 mm², tubo 16 mm).
- **C12**: Circuito adicional de apoyo o tomas de uso general en cocina.
- **C13**: Circuito para recarga de vehículo eléctrico (ITC-BT-52).

---

### 4. CAÍDAS DE TENSIÓN REGLAMENTARIAS EN INTERIORES
- Circuitos interiores de alumbrado: Caída máxima permitida = 3,0 % (6,9 V a 230 V).
- Circuitos interiores de fuerza / otros usos: Caída máxima permitida = 5,0 % (11,5 V a 230 V).
            """.trimIndent()
        ),

        // Apuntes 2: Enlace, LGA y Derivaciones Individuales
        SharedDocument(
            id = "apuntes_enlace_lga_di",
            title = "Apuntes Técnicos: Redes de Enlace, LGA y Derivaciones",
            description = "Prescripciones técnicas para CGP, Línea General de Alimentación, centralizaciones de contadores y Derivaciones Individuales.",
            fileName = "Apuntes_Enlace_LGA_DI_Oficial.pdf",
            fileSize = "1.2 MB",
            type = "Apuntes",
            content = """
# APUNTES TÉCNICOS OFICIALES: INSTALACIONES DE ENLACE Y DERIVACIONES INDIVIDUALES
## Reglamento Electrotécnico de Baja Tensión (ITC-BT-11 a ITC-BT-16)

---

### 1. CAJA GENERAL DE PROTECCIÓN (CGP) - ITC-BT-13
- Aloja los fusibles generales de protección de la Línea General de Alimentación (LGA).
- Instalación preferente en fachada exterior del edificio o en valla perimetral en el límite de la propiedad, accesible permanentemente al personal de la distribuidora.
- Grado de protección mínimo: IP43 / IK08 en intemperie.
- Fusibles tipo cuchilla con poder de corte mínimo de 100 kA.
- Cuando la acometida alimenta a un solo usuario (vivienda unifamiliar o nave independiente), se integra en una Caja de Protección y Medida (CPM) que agrupa fusibles y contador.

---

### 2. LÍNEA GENERAL DE ALIMENTACIÓN (LGA) - ITC-BT-14
- Enlaza la CGP con la concentración o centralización de contadores.
- Conductores: de cobre o aluminio, unipolares, aislados para tensión asignada 0,6/1 kV.
- Características del aislamiento: No propagadores del incendio y de reducida emisión de humos y opacidad (AS / CPR Cca-s1b,d1,a1).
- Sección mínima reglamentaria: 10 mm² en cobre (o 16 mm² en aluminio).
- Caída de tensión máxima admisible en la LGA:
  - Contadores totalmente centralizados en un único punto: e_máx = 0,5 %.
  - Contadores centralizados por plantas o en varios puntos: e_máx = 1,0 %.

---

### 3. CENTRALIZACIÓN DE CONTADORES - ITC-BT-16
- Local exclusivo cuando el número de contadores es superior a 16.
- Interruptor General de Maniobra (IGM):
  - Obligatorio cuando la potencia total contratada del conjunto supera los 150 kW o cuando existen dos o más líneas repartidoras.
  - Debe permitir el corte en carga del conjunto de los contadores.
- Embarrado general: barras de cobre de sección adecuada que alimentan las bases portafusibles de seguridad de cada derivación individual.

---

### 4. DERIVACIÓN INDIVIDUAL (DI) - ITC-BT-15
- Conecta el contador asignado a cada abonado con el cuadro CGMP de la vivienda o local.
- Conductores: fase(s), neutro y conductor de protección independiente (CP).
- Hilo de mando: cable unipolar de 1,5 mm² de color rojo para discriminación horaria (cuando se requiera).
- Sección mínima reglamentaria de fase y neutro: 6 mm² de cobre.
- Tubo protector exterior mínimo: Diámetro exterior 32 mm.
- Caída de tensión máxima admisible en la DI:
  - Para contadores totalmente centralizados: e_máx = 1,5 %.
  - Para contadores concentrados en plantas: e_máx = 1,0 %.
  - Para un solo usuario (sin LGA): e_máx = 1,5 %.
            """.trimIndent()
        ),

        // Apuntes 3: Tierras y Protecciones Eléctricas
        SharedDocument(
            id = "apuntes_tierras_proteccion",
            title = "Apuntes Técnicos: Puesta a Tierra y Protección Eléctrica",
            description = "Resistencia de difusión de electrodos, esquemas de distribución TT/TN/IT, tensiones de contacto límite y fórmulas de verificación.",
            fileName = "Apuntes_Puesta_Tierra_Protecciones_Oficial.pdf",
            fileSize = "1.6 MB",
            type = "Apuntes",
            content = """
# APUNTES TÉCNICOS OFICIALES: PUESTA A TIERRA Y PROTECCIONES CONTRA CHOQUES
## Reglamento Electrotécnico de Baja Tensión (ITC-BT-18 e ITC-BT-24)

---

### 1. OBJETIVO DE LA PUESTA A TIERRA
Limitar las tensiones que respecto a tierra puedan presentar en un momento dado las masas metálicas, asegurar la actuación de las protecciones automáticas y eliminar el riesgo de avería en los receptores.

---

### 2. ELECTRODOS DE TIERRA
- **Pica vertical**:
  - Longitud mínima: 2,00 metros.
  - Diámetro mínimo: 14 mm si es de acero cobrizado (con recubrimiento mínimo de 250 micras de cobre) o 25 mm si es de acero galvanizado.
  - Fórmula de resistencia aproximada: R = ρ / L (donde ρ es la resistividad del terreno en Ω·m y L la longitud en m).
- **Conductor enterrado horizontalmente (anillo de tierra)**:
  - Cable de cobre desnudo de sección mínima 35 mm² enterrado en zanja perimetral en la cimentación del edificio a profundidad mínima de 0,80 m.
  - Fórmula: R = 2ρ / L.
- **Placa de cobre enterrada**:
  - Espesor mínimo: 2 mm. Dimensiones habituales: 0,5 m x 1 m.
  - Fórmula: R = 0,8ρ / P (donde P es el perímetro en m).

---

### 3. CONDUCTORES DEL CIRCUITO DE TIERRA
- **Línea de enlace con tierra**: une el electrodo con el borne principal de tierra.
  - Sección mínima: 35 mm² de cobre desnudo o 16 mm² de cobre aislado.
- **Líneas principales de tierra**:
  - Sección mínima: 16 mm² de cobre.
- **Conductores de protección (CP)**:
  - Si sección de fase S ≤ 16 mm² -> S_cp = S.
  - Si 16 < S ≤ 35 mm² -> S_cp = 16 mm².
  - Si S > 35 mm² -> S_cp = S / 2.

---

### 4. ESQUEMAS DE DISTRIBUCIÓN (NEUTRO Y MASAS)
- **Esquema TT**:
  - Neutro de la alimentación puesto a tierra.
  - Masas de los receptores conectadas a una toma de tierra eléctricamente independiente de la toma de tierra del neutro.
  - OBLIGATORIO en España en redes de distribución pública de baja tensión.
  - CONDICIÓN DE SEGURIDAD (ITC-BT-24): R_A · I_Δn ≤ U_L
    - En locales secos: U_L = 50 V -> Para diferencial de 30 mA: R_A ≤ 50 / 0,030 = 1.666 Ω.
    - En locales húmedos: U_L = 24 V -> Para diferencial de 30 mA: R_A ≤ 24 / 0,030 = 800 Ω.

- **Esquema TN (TN-S, TN-C, TN-C-S)**:
  - Masas conectadas directamente al neutro de la fuente puesto a tierra. El defecto se convierte en cortocircuito franco fase-neutro.
  - Prohibido TN-C en conductores con sección inferior a 10 mm² de cobre o 16 mm² de aluminio.

- **Esquema IT**:
  - Neutro aislado de tierra o conectado a través de una impedancia muy elevada. Las masas están conectadas a tierra.
  - Primer defecto no provoca disparo (mantiene suministro ininterrumpido). Obligatorio en quirófanos y salas de intervención médica (ITC-BT-38).
            """.trimIndent()
        ),

        // Apuntes 4: Locales de Pública Concurrencia
        SharedDocument(
            id = "apuntes_publica_concurrencia",
            title = "Apuntes Técnicos: Pública Concurrencia y Alumbrado de Emergencia",
            description = "Clasificación de locales, características de cables de alta seguridad AS, suministros de socorro y alumbrados de emergencia.",
            fileName = "Apuntes_Publica_Concurrencia_Oficial.pdf",
            fileSize = "1.3 MB",
            type = "Apuntes",
            content = """
# APUNTES TÉCNICOS OFICIALES: LOCALES DE PÚBLICA CONCURRENCIA
## Reglamento Electrotécnico de Baja Tensión (ITC-BT-28)

---

### 1. CLASIFICACIÓN DE LOCALES DE PÚBLICA CONCURRENCIA
1. **Locales de espectáculos y actividades recreativas**: Cines, teatros, auditorios, estadios, pabellones deportivos, discotecas, salas de fiesta (cualquiera que sea su capacidad de ocupación).
2. **Locales de reunión y trabajo**:
   - Bares, cafeterías, restaurantes y templos con ocupación prevista superior a 50 personas.
   - Hoteles, hostales, museos, bibliotecas, residencias y centros docentes con ocupación superior a 50 personas.
   - Centros comerciales, hipermercados y estaciones de viajeros con ocupación superior a 50 personas.
   - Cualquier otro local no citado con ocupación superior a 100 personas.
3. **Locales de uso sanitario**: Hospitales, clínicas, ambulatorios, consultas médicas (cualquiera que sea su capacidad).

---

### 2. PRESCRIPCIONES TÉCNICAS ESPECÍFICAS
- **Cables obligatorios**:
  - Cables no propagadores del incendio y de reducida emisión de humos y opacidad (norma UNE 21123-4 / clasificación CPR mínima Cca-s1b,d1,a1).
  - Los cables para circuitos de seguridad de alumbrado de evacuación deben ser además resistentes al fuego durante un mínimo de 90 minutos (AS+ / clase P90 o PH120).

- **Fuentes propias de energía y suministros de seguridad**:
  - **Suministro de socorro**: mínimo 15 % de la potencia total contratada (autonomía mínima de 2 horas).
  - **Suministro de reserva**: mínimo 25 % de la potencia total contratada.
  - **Suministro duplicado**: 100 % de la potencia total con línea independiente.

---

### 3. ALUMBRADO DE EMERGENCIA
Debe entrar en funcionamiento de forma automática ante un fallo de la tensión de red en la zona (caída > 30 % de la tensión nominal):
1. **Alumbrado de evacuación**:
   - En el eje central de las rutas de evacuación: Iluminancia horizontal mínima de 1 lux a nivel del suelo.
   - Relación entre iluminancia máxima y mínima: no superior a 40:1 (para evitar deslumbramientos o zonas oscuras).
   - Autonomía mínima obligatoria: 1 hora.
2. **Alumbrado de puntos de seguridad**:
   - En botiquines de primeros auxilios y puestos de extinción de incendios (extintores, BIEs): Iluminancia mínima de 5 lux en el plano de trabajo.
3. **Alumbrado de zonas de alto riesgo**:
   - Quirófanos, salas de máquinas, calderas, cocinas industriales: Mínimo 15 lux o el 10 % de la iluminación normal previa.
            """.trimIndent()
        ),

        // Apuntes 5: Recarga Vehículo Eléctrico
        SharedDocument(
            id = "apuntes_recarga_ve",
            title = "Apuntes Técnicos: Recarga de Vehículo Eléctrico (VE)",
            description = "Esquemas oficiales de conexión (1 a 4), modos de carga (1 a 4), sistema de protección SPL y protecciones diferenciales tipo A.",
            fileName = "Apuntes_Recarga_Vehiculo_Electrico_ITC52_Oficial.pdf",
            fileSize = "1.5 MB",
            type = "Apuntes",
            content = """
# APUNTES TÉCNICOS OFICIALES: INFRAESTRUCTURA DE RECARGA DE VEHÍCULOS ELÉCTRICOS
## Reglamento Electrotécnico de Baja Tensión (ITC-BT-52)

---

### 1. ESQUEMAS OFICIALES DE INSTALACIÓN
- **Esquema 1 (Colectivo con contador principal común)**:
  - 1a: Contador principal en origen con contador secundario por cada punto.
  - 1b: Sin contadores secundarios (tarifa plana o reparto proporcional).
  - 1c: Con contadores individuales integrados en estaciones de recarga inteligentes.
- **Esquema 2 (Individual con contador exclusivo)**:
  - Cada plaza de garaje dispone de un contador exclusivo centralizado en el local de contadores del edificio conectado a la red de distribución.
- **Esquema 3 (Derivación individual vinculada al contador de vivienda)**:
  - Muy habitual en edificios residenciales comunitarios. La línea del punto de recarga se conecta a la salida del contador individual de la vivienda del usuario.
  - 3a: La línea del punto de recarga arranca desde el cuadro CGMP interior de la vivienda.
  - 3b: La línea arranca directamente desde los bornes de salida del contador en la concentración común.
- **Esquema 4 (Vivienda unifamiliar)**:
  - Circuito adicional exclusivo C13 que arranca del cuadro CGMP de la vivienda.

---

### 2. MODOS DE CARGA REGLAMENTARIOS
- **Modo 1**: Conexión a toma doméstica estándar sin comunicación piloto (PROHIBIDO para recarga habitual).
- **Modo 2**: Cable con caja intermedia de control y protección (ICCB) conectado a base doméstica estándar (carga ocasional limitada a 10 A / 13 A).
- **Modo 3**: Modo reglamentario de carga lenta o semirrápida en corriente alterna. Estación fija (Wallbox) con toma Tipo 2 (Mennekes) y protocolo de control y modulación piloto PWM.
- **Modo 4**: Carga rápida o ultrarrápida en corriente continua. El convertidor AC/DC reside en la estación de recarga exterior al vehículo. Conectores Combo CCS o CHAdeMO.

---

### 3. PROTECCIONES OBLIGATORIAS
- Interruptor magnetotérmico con curva C y calibre adecuado a la potencia del punto.
- Interruptor diferencial exclusivo para el punto de recarga:
  - Tipo A con detector interno de fuga en corriente continua superior a 6 mA (RDC-DD).
  - O bien Interruptor Diferencial Tipo B.
- Sistema de Protección de la Línea General de Alimentación (SPL): Dispositivo inteligente que modula la potencia de carga para evitar que la suma de consumos supere la capacidad máxima de la LGA.
            """.trimIndent()
        ),

        // Apuntes 6: Trámites Administrativos y Verificaciones
        SharedDocument(
            id = "apuntes_tramitaciones_cie",
            title = "Apuntes Técnicos: Tramitaciones, CIE y MTD vs Proyecto",
            description = "Límites para exigir proyecto visado por facultativo, memoria técnica MTD, certificado CIE e instrumentos de verificación obligatorios.",
            fileName = "Apuntes_Tramitaciones_CIE_MTD_Oficial.pdf",
            fileSize = "1.1 MB",
            type = "Apuntes",
            content = """
# APUNTES TÉCNICOS OFICIALES: TRAMITACIONES, CIE Y VERIFICACIONES TÉCNICAS
## Reglamento Electrotécnico de Baja Tensión (ITC-BT-03, ITC-BT-04 e ITC-BT-05)

---

### 1. EXIGENCIA DE PROYECTO TÉCNICO VISADO vs MTD
Se exige obligatoriamente Proyecto redactado y visado por Técnico Titulado Competente en los siguientes casos:
1. **Industrias en general**: Potencia instalada > 20 kW.
2. **Locales de pública concurrencia**: Cualquier potencia (sin umbral mínimo).
3. **Locales con riesgo de incendio o explosión (clase I y II)**: Cualquier potencia (excepto talleres mecánicos que no clasifiquen zonas).
4. **Locales mojados / polvorientos**: Potencia instalada > 10 kW.
5. **Bombas de extracción de agua / pozos**: Potencia instalada > 10 kW.
6. **Garajes**:
   - Con ventilación forzada: Cualquier capacidad o potencia.
   - Con ventilación natural: A partir de más de 5 vehículos.
   - Con infraestructura de recarga de vehículos eléctricos: > 50 kW en interiores o > 10 kW en exterior.
7. **Instalaciones temporales (ferias, obras)**: Potencia > 50 kW.
8. **Generadores / Fotovoltaica conectada a red**: Potencia > 10 kW.

**En todas las instalaciones que NO alcancen estos límites**, la documentación técnica exigible es la **Memoria Técnica de Diseño (MTD)**, que puede ser redactada y firmada directamente por el **Instalador Autorizado en Baja Tensión**.

---

### 2. CERTIFICADO DE INSTALACIÓN ELÉCTRICA (CIE / "Boletín")
Documento oficial expedido por la empresa instaladora habilitada que certifica que la instalación cumple íntegramente las prescripciones del REBT y las especificaciones particulares de la compañía distribuidora. Consta de 5 copias: Administración, Distribuidora, Titular, Instalador y OCA (si procede).

---

### 3. INSTRUMENTAL REGLAMENTARIO OBLIGATORIO DEL INSTALADOR
1. **Telurómetro**: Medida de la resistencia de difusión de tomas de tierra con picas auxiliares.
2. **Medidor de aislamiento**: Generador de ensayo a 500 V CC. Valor mínimo reglamentario entre conductores activos y tierra: 0,5 MΩ (500.000 Ω).
3. **Comprobador de interruptores diferenciales**: Mide la intensidad de disparo (rampa en mA) y el tiempo de respuesta (en milisegundos).
4. **Medidor de impedancia de bucle**: Para comprobar el disparo seguro de magnetotérmicos en defecto fase-tierra.
5. **Comprobador de continuidad**: Corriente de ensayo mínima de 200 mA para verificar la continuidad de conductores de protección y equipotenciales.
6. **Multímetro True RMS y Pinza voltiamperimétrica**: Medición de tensiones, intensidades y armónicos.
            """.trimIndent()
        ),

        // Documentos Originales Oficiales
        SharedDocument(
            id = "esquema_cgmp",
            title = "Esquema Unifilar General Vivienda",
            description = "Guía unifilar de representación técnica obligatoria para cuadros CGMP (C1 a C13).",
            fileName = "Esquema_Unifilar_CGMP_REBT.pdf",
            fileSize = "1.8 MB",
            type = "Esquema",
            content = """
# ESQUEMA UNIFILAR NORMALIZADO DE VIVIENDA (ITC-BT-17 e ITC-BT-25)
## Representación Técnica Oficial Reglamentaria del Cuadro General (CGMP)

---

### 1. ESTRUCTURA JERÁRQUICA DEL ESQUEMA UNIFILAR
El esquema unifilar es el documento gráfico primordial para la ejecución, legalización y verificación de la instalación interior de cualquier vivienda según el Art. 19 del REBT:

1. **ACOMETIDA Y DERIVACIÓN INDIVIDUAL (DI)**:
   - Tensión de suministro: 230 V (Fase + Neutro + PE de protección).
   - Conductor: Cobre unipolar 0,6/1 kV libre de halógenos, no propagador del incendio (tipo RZ1-K o H07Z1-K).
   - Sección mínima: 6 mm² (10 mm² habitual en electrificación básica 25 A; 16 mm² en electrificación elevada 40 A).
   - Hilo de mando: Conductor rojo de 1,5 mm² para discriminación horaria / telegestión.
   - Tubo protector: Diámetro exterior mínimo de 32 mm. Caída de tensión máxima admisible: 1,5% (3,45 V a 230 V).

2. **INTERRUPTOR GENERAL AUTOMÁTICO (IGA)**:
   - Dispositivo general de corte y protección contra sobrecargas y cortocircuitos.
   - Corte omnipolar obligatorio (corta simultáneamente fase y neutro).
   - Poder de corte mínimo: 4.500 A (habitual 6.000 A en cuadros residenciales modernos).
   - Curva de disparo: Curva C (disparo magnético entre 5 y 10 veces la intensidad nominal In).
   - Calibre nominal:
     * 25 A para Electrificación Básica (Potencia máxima contratada 5.750 W).
     * 40 A para Electrificación Elevada estándar (Potencia 9.200 W).
     * 50 A para Electrificación Elevada con bomba de calor / calefacción (11.500 W).
     * 63 A para Electrificación Elevada máxima monofásica (14.490 W).

3. **PROTECTOR CONTRA SOBRETENSIONES (ITC-BT-23)**:
   - Sobretensiones permanentes (POP): Bobina asociada al IGA que desconecta ante tensiones Fase-Neutro superiores a 255 V (+10% Un) mantenidas en el tiempo por fallo o rotura del neutro de red.
   - Sobretensiones transitorias: Descargador de sobretensiones Tipo 2 con varistores de óxido de zinc (ZnO), In = 15-20 kA, nivel de protección Up ≤ 1,5 kV, conectado en paralelo a la barra principal de tierra (PE).

4. **INTERRUPTORES DIFERENCIALES (ID)**:
   - Sensibilidad nominal obligatoria: IΔn = 30 mA (alta sensibilidad para protección de personas contra contactos indirectos y conatos de incendio).
   - Calibre nominal: Mínimo igual o mayor que el calibre del IGA que lo precede (típicamente 40 A).
   - Regla limitativa REBT: Máximo 5 circuitos derivados por cada interruptor diferencial.
   - Tipo de diferencial:
     * Tipo AC: Para corrientes de fuga alternas sinusoidales puras.
     * Tipo A: Con detección de corrientes continuas pulsantes (obligatorio para circuitos con electrónica, ordenadores, placas de inducción y cargadores de vehículos eléctricos).

---

### 2. DESGLOSE TÉCNICO DE CIRCUITOS DERIVADOS

#### A) ELECTRIFICACIÓN BÁSICA (IGA 25 A - Mínimo 5.750 W)
- **C1 - Iluminación y alumbrado**:
  * PIA: 10 A (Curva C, 6 kA)
  * Sección: 1,5 mm² Cu (F + N + PE)
  * Tubo protector: Ø 16 mm exterior
  * Capacidad máxima: Hasta 30 puntos de luz
  * Caída de tensión máxima: 3% (6,9 V)

- **C2 - Tomas de corriente de uso general y frigorífico**:
  * PIA: 16 A (Curva C, 6 kA)
  * Sección: 2,5 mm² Cu (F + N + PE)
  * Tubo protector: Ø 20 mm exterior
  * Capacidad máxima: Hasta 20 tomas de 16 A 2P+T
  * Caída de tensión máxima: 5% (11,5 V)

- **C3 - Cocina eléctrica y horno**:
  * PIA: 25 A (Curva C, 6 kA)
  * Sección: 6 mm² Cu (F + N + PE)
  * Tubo protector: Ø 25 mm exterior
  * Capacidad máxima: 2 tomas especiales de 25 A (o 1 caja de conexión)

- **C4 - Lavadora, lavavajillas y termo eléctrico**:
  * Opción combinada clásica: PIA 20 A, sección 4 mm², tubo Ø 20 mm alimentando 3 tomas de 16 A.
  * Opción desglosada oficial recomendada:
    - C4.1 (Lavadora): PIA 16 A | 2,5 mm² | Tubo Ø 20 mm
    - C4.2 (Lavavajillas): PIA 16 A | 2,5 mm² | Tubo Ø 20 mm
    - C4.3 (Termo eléctrico): PIA 16 A | 2,5 mm² | Tubo Ø 20 mm

- **C5 - Tomas de corriente en cuartos de baño y auxiliares de cocina**:
  * PIA: 16 A (Curva C, 6 kA)
  * Sección: 2,5 mm² Cu (F + N + PE)
  * Tubo protector: Ø 20 mm exterior
  * Capacidad máxima: Hasta 6 tomas distribuidas entre cocina y baños

#### B) ELECTRIFICACIÓN ELEVADA (IGA ≥ 40 A - Mínimo 9.200 W)
Obligatoria si superficie útil > 160 m² o si se instala calefacción eléctrica, aire acondicionado, secadora o punto de recarga VE:
- Requiere un SEGUNDO Interruptor Diferencial (ID2) de 40 A / 30 mA Tipo A.
- Circuitos adicionales:
  * **C6**: Circuito adicional de iluminación (por cada 30 puntos de luz adicionales a C1).
  * **C7**: Circuito adicional de tomas de corriente (por cada 20 tomas adicionales a C2).
  * **C8**: Calefacción eléctrica (PIA 25 A | 6 mm² | Tubo Ø 25 mm).
  * **C9**: Aire acondicionado / Climatización (PIA 25 A | 6 mm² | Tubo Ø 25 mm).
  * **C10**: Secadora independiente (PIA 16 A | 2,5 mm² | Tubo Ø 20 mm).
  * **C11**: Automatización, control y domótica (PIA 10 A | 1,5 mm² | Tubo Ø 16 mm).
  * **C12**: Circuito adicional de tomas o apoyo en cocinas de gran tamaño.
  * **C13**: Circuito exclusivo de infraestructura para recarga de vehículo eléctrico (ITC-BT-52: PIA 32 A | 6 mm² | Tubo Ø 25 mm + diferencial propio Tipo A).

---

### 3. BARRA COLECTORA Y REPARTIDOR DE TIERRA (PE)
- Conexión del conductor de protección principal procedente de la arqueta de tierra.
- Código de color normalizado: Verde-Amarillo.
- Conexión equipotencial suplementaria a masas metálicas de baños (tuberías de agua fría y caliente, desagües metálicos y marcos de carpintería).
            """.trimIndent()
        ),
        SharedDocument(
            id = "tabla_itc_21",
            title = "Prontuario Diámetros de Tubos ITC-BT-21",
            description = "Fórmula rápida e interpolación de diámetros reglamentarios según hilos empotrados.",
            fileName = "Prontuario_Tubos_ITC_21.pdf",
            fileSize = "820 KB",
            type = "Calculadora",
            content = """
# PRONTUARIO OFICIAL: DIÁMETROS DE TUBOS PROTECTORES (ITC-BT-21)
## Tablas Reglamentarias de Selección según Tipo de Instalación y Sección

---

### 1. REGLAS BÁSICAS DE DIMENSIONAMIENTO REGLAMENTARIO
1. Los conductores deben poder alojarse y extraerse fácilmente tras la colocación y fijación de los tubos.
2. Para conductores aislados de tensión asignada 450/750 V alojados en tubos, la sección interior del tubo debe ser como mínimo igual a **3 veces** la sección total ocupada por los conductores (o **4 veces** en canalizaciones enterradas).
3. En curvas y codos no se admitirán deformaciones del tubo que reduzcan su sección transversal en más de un 15%.

---

### 2. TABLA OFICIAL 2 (ITC-BT-21): CONDUCTORES EN TUBOS EMPOTRADOS EN PAREDES
Diámetros exteriores mínimos del tubo (en mm) en función del número y sección de conductores unipolares:

| Sección mm² | 1 Conductor | 2 Conductores | 3 Conductores | 4 Conductores | 5 Conductores |
|:---:|:---:|:---:|:---:|:---:|:---:|
| **1,5** | 16 | 16 | 16 | 16 | 16 |
| **2,5** | 16 | 16 | 16 | 20 | 20 |
| **4** | 16 | 16 | 20 | 20 | 20 |
| **6** | 16 | 20 | 20 | 25 | 25 |
| **10** | 20 | 25 | 25 | 32 | 32 |
| **16** | 20 | 25 | 32 | 32 | 40 |
| **25** | 25 | 32 | 32 | 40 | 50 |
| **35** | 25 | 32 | 40 | 50 | 50 |
| **50** | 32 | 40 | 50 | 50 | 63 |
| **70** | 32 | 50 | 50 | 63 | 63 |
| **95** | 40 | 50 | 63 | - | - |

---

### 3. TABLA OFICIAL 1 (ITC-BT-21): CONDUCTORES EN TUBOS EN MONTAJE SUPERFICIAL
Diámetros exteriores mínimos del tubo (en mm):

| Sección mm² | 1 Conductor | 2 Conductores | 3 Conductores | 4 Conductores | 5 Conductores |
|:---:|:---:|:---:|:---:|:---:|:---:|
| **1,5** | 16 | 16 | 16 | 16 | 16 |
| **2,5** | 16 | 16 | 16 | 20 | 20 |
| **4** | 16 | 16 | 20 | 20 | 20 |
| **6** | 16 | 20 | 20 | 25 | 25 |
| **10** | 20 | 20 | 25 | 32 | 32 |
| **16** | 20 | 25 | 32 | 32 | 32 |
| **25** | 25 | 32 | 32 | 40 | 40 |
| **35** | 25 | 32 | 40 | 40 | 50 |
| **50** | 32 | 40 | 50 | 50 | 63 |

---

### 4. TABLA OFICIAL 5 (ITC-BT-21): TUBOS ENTERRADOS EN ZANJA
- Diámetro exterior mínimo comercial para cables enterrados: Ø 40 mm (norma general) y Ø 50 mm para acometidas y redes de distribución subterráneas.
- Resistencia a la compresión: Mínimo 450 N (código 4541).
- Profundidad reglamentaria: Mínimo 0,60 m bajo acera y 0,80 m bajo calzada transitable por vehículos.
- Banda de señalización: Cinta amarilla de advertencia a 20 cm por encima del tubo.
            """.trimIndent()
        ),
        SharedDocument(
            id = "esquema_tierras",
            title = "Detalle Constructivo Puesta a Tierra",
            description = "Esquema báculo de farolas y electrodos verticales con desconectador rápido.",
            fileName = "Esquema_Puesta_Tierra_BT.pdf",
            fileSize = "1.1 MB",
            type = "Esquema",
            content = """
# DETALLE CONSTRUCTIVO Y ESQUEMA DE PUESTA A TIERRA (ITC-BT-18 e ITC-BT-26)
## Prescripciones Técnicas Oficiales de Electrodos, Arquetas y Medición

---

### 1. ELEMENTOS CONSTRUCTIVOS DE LA TOMA DE TIERRA
La instalación de puesta a tierra comprende:
1. **Toma de tierra / Electrodo**:
   - Pica vertical de acero con recubrimiento de cobre (mínimo 250 micras), longitud mínima de 2,00 m y diámetro ≥ 14 mm.
   - Anillo horizontal de cimentación: Conductor de cobre desnudo de 35 mm² de sección mínima enterrado en zanja perimetral a profundidad mínima de 0,80 m.
   - Placa de cobre: Espesor mínimo de 2 mm y dimensiones habituales de 0,50 m x 1,00 m.

2. **Línea de Enlace con Tierra**:
   - Une el electrodo de tierra con el borne principal de tierra en la arqueta de registro.
   - Sección mínima: 35 mm² si es de cobre desnudo o 16 mm² si es de cobre aislado (color verde-amarillo).

3. **Arqueta de Registro con Puente de Comprobación Seccionable**:
   - Arqueta registrable de hormigón o PVC (mín. 30x30 cm) con tapa de fundición o composite con inscripción reglamentaria "PUESTA A TIERRA".
   - Aloja el puente de prueba seccionable que permite desconectar mecánicamente mediante tornillería la toma de tierra del resto del edificio para su medición independiente con telurómetro.

4. **Borne Principal de Puesta a Tierra**:
   - Regleta metálica de cobre o latón en el cuadro general o local de contadores. A él se conectan:
     * La línea principal de tierra.
     * Los conductores de protección (PE) de los circuitos.
     * La unión equipotencial principal (UEP) que conecta las tuberías metálicas de agua, gas, calefacción, chimeneas, antenas y estructura metálica del edificio.

---

### 2. ESQUEMAS DE DISTRIBUCIÓN OFICIALES
1. **Esquema TT (Regla General en España)**:
   - Neutro de la compañía distribuidora puesto a tierra en el centro de transformación.
   - Masas metálicas de la vivienda puestas a tierra independiente.
   - Condición obligatoria de corte: Ra · IΔn ≤ UL (50 V en seco / 24 V en húmedo). Con ID de 30 mA: Ra ≤ 1.666 Ω (seco) y Ra ≤ 800 Ω (húmedo). En la práctica se exige Ra < 15 Ω para garantizar operatividad.

2. **Esquema TN (Industrial y Redes Propias)**:
   - Masas conectadas directamente al neutro puesto a tierra. Un defecto masa-fase se convierte en cortocircuito franco.
   - TN-S: Neutro y conductor de protección separados en toda la instalación.
   - TN-C: Neutro y conductor de protección comunes en un único conductor PEN (mínimo 10 mm² Cu o 16 mm² Al). Prohibido en viviendas.

3. **Esquema IT (Quirófanos y Procesos Críticos)**:
   - Neutro aislado de tierra o a través de impedancia elevada (1.000 Ω). Masas a tierra.
   - El primer defecto a masa no provoca disparo de corriente, permitiendo continuar la intervención médica (requiere vigilante de aislamiento que avise acústica y visualmente).
            """.trimIndent()
        ),
        SharedDocument(
            id = "boe_rebt",
            title = "BOE Reglamento Electrotécnico de Baja Tensión",
            description = "Real Decreto 842/2002 oficial completo con todas las ITCs vigentes.",
            fileName = "BOE_REBT_Completo_2026.pdf",
            fileSize = "4.2 MB",
            type = "BOE",
            content = """
# BOE: REAL DECRETO 842/2002 - REGLAMENTO ELECTROTÉCNICO DE BAJA TENSIÓN
## Marco Jurídico, Articulado y Estructura Completa de las 52 ITCs

---

### 1. MARCO LEGAL Y ESTRUCTURA GENERAL
Aprobado por Real Decreto 842/2002 de 2 de agosto (BOE núm. 224 de 18 de septiembre de 2002), actualizado con las modificaciones vigentes de eficiencia energética (RD 1890/2008) y recarga de vehículos eléctricos (ITC-BT-52, RD 1053/2014):

- **29 Artículos del Reglamento**: Fijan el régimen jurídico, definiciones, campo de aplicación, exigencias de seguridad, inspecciones de OCA y régimen sancionador de la Ley 21/1992 de Industria.
- **52 Instrucciones Técnicas Complementarias (ITCs)**: Detallan las especificaciones técnicas obligatorias:
  * **ITC-BT-01 a 08**: Administrativas, terminología, instaladores, proyectos e inspecciones.
  * **ITC-BT-09 a 10**: Alumbrado exterior y previsión de cargas.
  * **ITC-BT-11 a 16**: Redes de distribución y acometidas, LGA y centralización de contadores.
  * **ITC-BT-17 a 27**: Instalaciones interiores, CGMP, puesta a tierra, tubos, conductores, sobretensiones y locales húmedos.
  * **ITC-BT-28 a 42**: Locales de pública concurrencia, atmósferas explosivas ATEX, quirófanos, piscinas, obras y caravanas.
  * **ITC-BT-43 a 52**: Receptores a motor, lámparas de descarga, calderas, vallas, domótica e infraestructura de recarga VE.

---

### 2. SILENCIO ADMINISTRATIVO EN EL REBT (PREGUNTAS TEST CRÍTICAS)
- **Especificaciones Particulares de Distribuidoras (Art. 14)**: Si la Comunidad Autónoma no resuelve en un plazo de 3 meses, rige el **SILENCIO ADMINISTRATIVO POSITIVO** (quedan aprobadas).
- **Excepciones Técnicas por Circunstancias Especiales (Art. 24)**: Si la Dirección General de Industria no resuelve en 3 meses, rige el **SILENCIO ADMINISTRATIVO NEGATIVO** (se consideran desestimadas).
            """.trimIndent()
        )
    )
}

