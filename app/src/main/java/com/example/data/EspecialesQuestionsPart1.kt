package com.example.data

object EspecialesQuestionsPart1 {
    val QUESTIONS = listOf(
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
                    q = "¿Entre qué límites de potencia nominal debe situarse el transformador de aislamiento para uso médico en quirófanos según la ITC-BT-38?",
                    opts = listOf(
                        "Entre 0,1 kVA y 1 kVA",
                        "Entre 0,5 kVA y 10 kVA",
                        "Entre 5 kVA y 50 kVA",
                        "No existe límite de potencia máxima"
                    ),
                    a = 1,
                    exp = "La ITC-BT-38 en su apartado 2.1 establece que la potencia nominal del transformador de separación de circuitos de uso médico no será inferior a 0,5 kVA ni superior a 10 kVA tanto para alimentación monofásica como trifásica. Limitar la potencia a 10 kVA evita corrientes de fuga capacitivas excesivas a tierra a través de los aislamientos y devanados que comprometerían la seguridad del paciente.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "¿Cuál es el valor máximo de resistencia permitido para los conductores de unión equipotencial entre el embarrado EE y las masas del quirófano?",
                    opts = listOf(
                        "1,0 Ω",
                        "0,5 Ω",
                        "0,2 Ω",
                        "2,0 Ω"
                    ),
                    a = 2,
                    exp = "Según la ITC-BT-38 apartado 2.2, la resistencia eléctrica de los conductores de protección y de equipotencialidad suplementaria entre el embarrado de equipotencialidad (EE) y los bornes de tierra de tomas o masas metálicas no debe exceder de 0,2 Ω. Un valor superior como 1 Ω provocaría caídas de tensión inadmisibles durante derivaciones de corriente, superando los 10 mV seguros para el paciente.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "¿Qué sección mínima debe tener el conductor de cobre del embarrado principal de equipotencialidad suplementaria (EE) del quirófano?",
                    opts = listOf(
                        "4 mm²",
                        "6 mm²",
                        "10 mm²",
                        "16 mm²"
                    ),
                    a = 3,
                    exp = "Conforme a la ITC-BT-38 punto 2.2, la barra de equipotencialidad del quirófano debe unirse al borne principal de puesta a tierra mediante un conductor de cobre de sección no inferior a 16 mm². La trampa frecuente es pensar en 4 mm², pero esta última es la sección mínima para las derivaciones secundarias hacia las tomas o masas metálicas individuales, no para el embarrado troncal.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "¿Qué sección mínima deben tener los conductores de cobre que unen masas individuales al embarrado de equipotencialidad del quirófano?",
                    opts = listOf(
                        "4 mm²",
                        "1,5 mm²",
                        "2,5 mm²",
                        "10 mm²"
                    ),
                    a = 0,
                    exp = "La ITC-BT-38 apartado 2.2 prescribe que los conductores de equipotencialidad suplementaria que conectan las masas metálicas, tomas de corriente y equipos fijos al embarrado EE deben tener una sección mínima de 4 mm² de cobre. Se suele caer en la trampa de 2,5 mm² por ser el estándar de fuerza general, pero en quirófanos se exige mayor robustez mecánica e impedancia ultrabaja.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "¿Entre qué valores debe situarse la resistencia eléctrica del suelo de un quirófano respecto a tierra según la ITC-BT-38?",
                    opts = listOf(
                        "Inferior a 100 Ω",
                        "Entre 50 kΩ y 1 MΩ",
                        "Entre 1 MΩ y 10 MΩ",
                        "Superior a 100 MΩ"
                    ),
                    a = 1,
                    exp = "La ITC-BT-38 en su punto 2.4 especifica que el suelo del quirófano debe ser de tipo antiestático y disipativo, con una resistencia comprendida entre 50.000 Ω (50 kΩ) y 1.000.000 Ω (1 MΩ). Si fuese menor de 50 kΩ no protegería al personal de contactos indirectos, y si fuera superior a 1 MΩ acumularía electricidad estática peligrosa en presencia de gases medicinales.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "¿Cuál es el tiempo máximo de conmutación admitido para la fuente de emergencia que alimenta la lámpara de quirófano (scialítica)?",
                    opts = listOf(
                        "5 segundos",
                        "1 segundo",
                        "0,5 segundos",
                        "15 segundos"
                    ),
                    a = 2,
                    exp = "Según la ITC-BT-38 apartado 2.3, la lámpara scialítica principal y los equipos de soporte de funciones vitales deben contar con una fuente especial de emergencia (como un SAI) capaz de restablecer el suministro en un tiempo no superior a 0,5 segundos. Tiempos como 15 segundos corresponden a grupos electrógenos para servicios generales no críticos de reserva del hospital.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "¿Qué autonomía mínima debe proporcionar la fuente de alimentación de reserva para quirófanos ante un corte de la red general?",
                    opts = listOf(
                        "30 minutos",
                        "1 hora",
                        "4 horas",
                        "2 horas"
                    ),
                    a = 3,
                    exp = "La ITC-BT-38 pto. 2.3 determina que la fuente de emergencia dedicada para quirófanos y salas de intervención debe tener una autonomía mínima de funcionamiento de al menos 2 horas continuadas. Muchos opositores marcan erróneamente 1 hora por asimilación con el alumbrado de emergencia estándar en edificios de pública concurrencia (ITC-BT-28), lo que resulta insuficiente en una cirugía mayor.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "¿Cuántas tomas de corriente como mínimo deben instalarse en cada quirófano según las prescripciones de la ITC-BT-38?",
                    opts = listOf(
                        "Al menos 16 tomas de corriente",
                        "8 tomas de corriente",
                        "10 tomas de corriente",
                        "24 tomas de corriente"
                    ),
                    a = 0,
                    exp = "De acuerdo con la ITC-BT-38 apartado 2.1, en cada quirófano se dispondrá como mínimo de 16 tomas de corriente, distribuidas adecuadamente en las zonas de trabajo alrededor de la mesa de operaciones. La opción de 8 o 10 tomas es insuficiente dado el elevado número de equipos electromédicos simultáneos de monitorización, anestesia, electrocirugía y soporte vital requeridos en una intervención.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "¿Cómo debe ser la protección contra sobrecargas en el secundario del transformador de aislamiento de un quirófano según la ITC-BT-38?",
                    opts = listOf(
                        "Mediante interruptor magnetotérmico con disparo instantáneo obligatorio",
                        "No debe provocar el corte automático del suministro, debiendo activar una alarma óptica y acústica",
                        "Con fusibles calibrados de acción ultrarrápida",
                        "Mediante interruptor diferencial superinmunizado de 10 mA"
                    ),
                    a = 1,
                    exp = "La ITC-BT-38 pto. 2.1 prohíbe el corte automático de la alimentación por sobrecarga en el circuito aislado médico, ya que la desconexión imprevista de un equipo de soporte vital acarrearía riesgo letal inmediato para el paciente. Por ello, solo se permite señalización y alarma óptica/acústica de sobrecarga, reservando el disparo automático exclusivamente para cortocircuitos francos.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "¿Qué tensión asignada máxima puede tener el circuito secundario del transformador de separación para quirófanos según la ITC-BT-38?",
                    opts = listOf(
                        "400 V",
                        "127 V",
                        "250 V",
                        "500 V"
                    ),
                    a = 2,
                    exp = "La ITC-BT-38 en su apartado 2.1 fija que la tensión asignada en el circuito secundario del transformador de separación médico no será superior a 250 V (frecuentemente 230 V entre fases en esquema IT monofásico). La trampa radica en pensar en 400 V por tratarse de instalaciones hospitalarias, pero el reglamento veta tensiones superiores para acotar las posibles tensiones de defecto respecto a tierra.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "¿Dónde debe situarse preferentemente el cuadro general de mando y protección específico de un quirófano según la ITC-BT-38?",
                    opts = listOf(
                        "En el sótano del hospital junto a la acometida",
                        "Bajo la mesa quirúrgica en el suelo del quirófano",
                        "Dentro de la sala de calderas de vapor",
                        "Fuera del quirófano, en sus inmediaciones y fácilmente accesible al personal técnico"
                    ),
                    a = 3,
                    exp = "La ITC-BT-38 punto 2.1 establece que el cuadro de distribución y protección del quirófano debe emplazarse fuera de la sala quirúrgica, pero en sus inmediaciones inmediatas (en zona limpia o pasillo técnico), para posibilitar intervenciones de mantenimiento sin vulnerar la asepsia ni interferir en la cirugía. Colocarlo lejos o dentro de la zona estéril es contrario a la norma.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "¿Qué características de pantalla debe incorporar obligatoriamente el transformador de separación para uso médico según la ITC-BT-38?",
                    opts = listOf(
                        "Una pantalla electrostática metálica entre los devanados primario y secundario conectada a tierra",
                        "Una carcasa de plástico hermética sin conexión de masa",
                        "Aislamiento de cartón impregnado en aceite dieléctrico inflamable",
                        "Una pantalla magnética exterior conectada al polo positivo"
                    ),
                    a = 0,
                    exp = "Conforme a la ITC-BT-38 apartado 2.1 y la norma UNE 20615, el transformador de aislamiento debe disponer de una pantalla electrostática intermedia entre el primario y el secundario, conectada al embarrado de equipotencialidad. Esta pantalla drena a tierra cualquier corriente de fuga por capacidad parásita e impide que una perforación del aislamiento primario traslade la red de distribución al circuito médico.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "En el esquema IT médico de un quirófano, ¿está permitido distribuir el conductor neutro en el secundario del transformador?",
                    opts = listOf(
                        "Sí, es obligatorio para obtener 230 V con respecto a tierra",
                        "No, queda prohibida la distribución del neutro en el secundario del transformador médico",
                        "Solo si el neutro se conecta al chasis de la mesa de operaciones",
                        "Se permite únicamente si el transformador es trifásico de 400 V"
                    ),
                    a = 1,
                    exp = "La ITC-BT-38 apartado 2.1 prohíbe taxativamente la distribución del conductor neutro en el secundario del transformador de aislamiento médico. El circuito IT debe funcionar como bifásico aislado (dos conductores de fase flotantes); distribuir el neutro aumentaría las capacidades a tierra y alteraría la simetría de aislamiento necesaria para la correcta vigilancia del monitor DLI.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "¿Qué resistencia interna mínima debe tener el dispositivo vigilante del nivel de aislamiento (DLI) en un quirófano según la ITC-BT-38?",
                    opts = listOf(
                        "10 kΩ",
                        "50 kΩ",
                        "100 kΩ",
                        "10 Ω"
                    ),
                    a = 2,
                    exp = "El dispositivo de vigilancia de aislamiento debe tener una resistencia interna de al menos 100 kΩ según la ITC-BT-38 punto 2.1 y normas técnicas de referencia UNE. La confusión común es marcar 50 kΩ, pero 50 kΩ es el umbral de disparo de la alarma acústica y visual cuando desciende el aislamiento de la red, no la impedancia interna de medida del propio aparato.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "¿Cuál es la tensión máxima de medida en corriente continua que puede inyectar el monitor de aislamiento en el circuito de quirófano?",
                    opts = listOf(
                        "230 V",
                        "50 V",
                        "220 V",
                        "24 V"
                    ),
                    a = 3,
                    exp = "Según la ITC-BT-38 apartado 2.1, la tensión de prueba que el monitor continuo de aislamiento inyecta para verificar el estado de las líneas no debe exceder de 24 V en corriente continua, y la corriente de medida máxima no superará 1 mA. Se suele confundir con 50 V (tensión límite en locales secos), pero en el entorno hospitalario del paciente la tensión debe ser intrínsecamente inocua.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "¿Cuál es la corriente máxima de medida que puede circular a tierra generada por el monitor permanente de aislamiento según la ITC-BT-38?",
                    opts = listOf(
                        "1 mA",
                        "10 mA",
                        "30 mA",
                        "0,01 mA"
                    ),
                    a = 0,
                    exp = "La ITC-BT-38 pto. 2.1 estipula que la corriente máxima de medida emitida por el vigilante de aislamiento en caso de defecto franco a tierra no debe sobrepasar 1 mA. La trampa habitual es contestar 30 mA por asimilación con la sensibilidad de los diferenciales domésticos, pero 30 mA a través del miocardio de un paciente cateterizado resultaría mortal (umbral de fibrilación ventricular).",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "En el panel de alarma del monitor de aislamiento situado dentro del quirófano, ¿qué indica el piloto de color verde según la ITC-BT-38?",
                    opts = listOf(
                        "Fallo grave de aislamiento en curso",
                        "Funcionamiento normal y nivel de aislamiento correcto de la instalación",
                        "Disparo térmico del transformador de separación",
                        "Conexión activa de la fuente de baterías de reserva"
                    ),
                    a = 1,
                    exp = "La ITC-BT-38 apartado 2.1 establece que el repetidor de alarma en quirófano contará con un piloto verde permanente que señaliza el servicio normal con aislamiento correcto, y un piloto rojo asociado a la señal acústica cuando el aislamiento baja de 50 kΩ. Suponer que el verde indica fallo o recarga de baterías es un error básico de código de colores.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "¿Cómo debe comportarse el avisador acústico del panel de quirófano cuando el personal pulsa el botón de silenciador ante un fallo de aislamiento?",
                    opts = listOf(
                        "El piloto rojo debe apagarse de inmediato aunque persista el defecto",
                        "El sistema debe desconectar automáticamente todos los enchufes del quirófano",
                        "La señal acústica puede silenciarse, pero el piloto rojo de alarma luminosa debe permanecer encendido hasta resolver el defecto",
                        "El botón de silenciador está terminantemente prohibido por normativa"
                    ),
                    a = 2,
                    exp = "Conforme a la ITC-BT-38 pto. 2.1, el cuadro de alarma permite al cirujano o anestesista silenciar la alarma acústica para evitar distracciones durante la intervención, pero la indicación luminosa roja debe permanecer fija e inextinguible mientras el aislamiento continúe por debajo de 50 kΩ, garantizando que el personal técnico repare la avería posteriormente.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "¿Está permitido alimentar más de un quirófano desde un mismo transformador de separación de uso médico según la ITC-BT-38?",
                    opts = listOf(
                        "Sí, hasta un máximo de cuatro quirófanos contiguos",
                        "Sí, siempre que el transformador tenga una potencia de 50 kVA",
                        "Se admite si los quirófanos comparten el mismo equipo médico",
                        "No, debe instalarse al menos un transformador de separación exclusivo para cada quirófano o sala de intervención"
                    ),
                    a = 3,
                    exp = "La ITC-BT-38 punto 2.1 prescribe que cada quirófano o sala de intervención debe disponer de su propio transformador de aislamiento independiente. Compartir transformador entre varios quirófanos crearía el peligro inadmisible de que una fuga o defecto eléctrico en una sala disparara la alarma o dejara fuera de servicio al quirófano adyacente durante una operación crítica.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "¿Qué clase de interruptores diferenciales deben instalarse en los circuitos de quirófano no alimentados por transformador de aislamiento?",
                    opts = listOf(
                        "Diferenciales de Clase A o B con sensibilidad de 30 mA",
                        "Diferenciales estándar de Clase AC de 300 mA",
                        "Diferenciales retardados tipo S de 500 mA",
                        "No se requiere ningún interruptor diferencial si hay puesta a tierra"
                    ),
                    a = 0,
                    exp = "Para los circuitos secundarios no críticos del quirófano que no procedan del sistema IT médico (por ejemplo tomas generales auxiliares o alumbrado de ambiente), la ITC-BT-38 apartado 2.1 exige protección diferencial de alta sensibilidad (máx. 30 mA) de clase A o B, capaces de detectar corrientes de defecto continuas o pulsantes generadas por fuentes conmutadas de equipos electrónicos modernos.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "En quirófanos, ¿qué periodicidad mínima se exige para comprobar el funcionamiento del dispositivo vigilante de aislamiento mediante su botón de prueba?",
                    opts = listOf(
                        "Cada 5 años por un Organismo de Control",
                        "Al menos una vez al mes por personal técnico competente",
                        "Una vez cada 2 años",
                        "Únicamente durante la puesta en marcha inicial del quirófano"
                    ),
                    a = 1,
                    exp = "La ITC-BT-38 apartado 3 establece que el vigilante del nivel de aislamiento y los dispositivos de señalización óptica y acústica deben verificarse periódicamente al menos una vez al mes por el servicio técnico de mantenimiento del hospital, accionando el pulsador de prueba integrado y anotando el resultado en el libro de registro de la instalación.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "¿Con qué periodicidad deben pasar los quirófanos la inspección reglamentaria por Organismo de Control Autorizado (OCA) según el REBT?",
                    opts = listOf(
                        "Cada año",
                        "Cada 10 años",
                        "Cada 5 años",
                        "Están exentos de inspecciones periódicas si cuentan con técnico propio"
                    ),
                    a = 2,
                    exp = "Los quirófanos y salas de intervención, clasificados como locales con riesgo especial y pública concurrencia (ITC-BT-05 pto. 4.2 e ITC-BT-38), deben someterse a inspección técnica periódica por una OCA cada 5 años. Confundir este plazo con los 10 años aplicables a zonas comunes de edificios residenciales de más de 100 kW es un error recurrente de examen.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "En una sala de operaciones, ¿cómo deben identificarse las tomas de corriente alimentadas por el sistema IT médico respecto a las estándar?",
                    opts = listOf(
                        "Tienen exactamente el mismo aspecto sin ninguna diferenciación",
                        "Las tomas IT solo pueden ubicarse en el techo y sin conexión de tierra",
                        "Únicamente mediante una advertencia verbal del responsable de planta",
                        "Deben estar claramente diferenciadas mediante rotulación inequívoca, color específico o diseño exclusivo"
                    ),
                    a = 3,
                    exp = "Conforme a la ITC-BT-38 punto 2.1, para evitar que se conecten cargas generales no vitales (como aspiradoras de limpieza o calefactores) en el circuito aislado médico, las tomas alimentadas por el transformador de separación deben estar claramente rotuladas o señalizadas con un color característico (por ejemplo rojo o verde) y diferenciadas de las tomas convencionales.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "¿Qué control térmico debe incorporarse en los devanados del transformador de aislamiento médico según la ITC-BT-38?",
                    opts = listOf(
                        "Sondas térmicas de temperatura para supervisión y alarma por sobrecalentamiento",
                        "Un termostato bimetálico que corte automáticamente la energía",
                        "Refrigeración por ventilador forzado directo a 230 V sin alarma",
                        "No se requiere supervisión térmica si el transformador está bajo tierra"
                    ),
                    a = 0,
                    exp = "El transformador médico debe contar con sensores de temperatura (como sondas PT100 o termistores PTC) embebidos en sus devanados para transmitir una señal de alarma al cuadro de supervisión técnica cuando la temperatura interna alcance límites críticos, alertando de una sobrecarga persistente sin cortar intempestivamente el suministro al paciente intervenido.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "¿A qué elementos debe conectarse el embarrado de equipotencialidad suplementaria (EE) del quirófano según la ITC-BT-38?",
                    opts = listOf(
                        "Únicamente a la tubería de gas anestésico",
                        "A las masas de los equipos electromédicos, partes metálicas accesibles de la sala, tomas de tierra de enchufes y mesa quirúrgica",
                        "Solo a la estructura de hormigón exterior del hospital",
                        "A la fase neutra del secundario del transformador"
                    ),
                    a = 1,
                    exp = "La ITC-BT-38 apartado 2.2 exige que la red de equipotencialidad suplementaria interconecte todas las masas metálicas de la sala (mesa de operaciones, brazos articulados, canalizaciones metálicas de gases y agua, marcos de puertas y bornes de tierra de todas las tomas) para mantener todas las superficies accesibles al mismo e idéntico potencial eléctrico absoluto.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "¿Cuál es el tiempo máximo de conmutación del grupo electrógeno para servicios generales de quirófano no asistidos por SAI según el REBT?",
                    opts = listOf(
                        "0,5 segundos",
                        "60 segundos",
                        "15 segundos",
                        "5 minutos"
                    ),
                    a = 2,
                    exp = "Para los servicios de reserva hospitalarios no clasificados como de corte instantáneo o ultracorto (como la climatización, alumbrado general del área quirúrgica y fuerza auxiliar no crítica), la ITC-BT-28 y la ITC-BT-38 admiten un tiempo de conmutación de hasta 15 segundos para el arranque y toma de carga del grupo electrógeno diesel de emergencia.",
                    ref = "ITC-BT-38"
                ),
Question(
                    q = "En caso de utilizarse mezclas de gases anestésicos inflamables en quirófano, ¿qué zona reglamentaria se considera peligrosa por defecto?",
                    opts = listOf(
                        "Todo el edificio hospitalario",
                        "El techo del quirófano por encima de 2 metros",
                        "Solo el interior del tubo endotraqueal del paciente",
                        "El volumen comprendido entre el suelo y una altura de 0,25 m, o según prescripciones de la ITC-BT-29"
                    ),
                    a = 3,
                    exp = "La ITC-BT-38 en su punto 2.4 establece que si se emplean sustancias o gases inflamables (como éter o ciclopropano históricos), los vapores más pesados que el aire se acumulan en el estrato inferior, delimitándose una zona de riesgo entre el suelo y 0,25 m (o hasta la cota fijada por la ITC-BT-29), donde no pueden existir fuentes de ignición o mecanismos eléctricos estándar.",
                    ref = "ITC-BT-38"
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
                ),
Question(
                    q = "Según el REBT, en locales húmedos las canalizaciones eléctricas serán:",
                    opts = listOf(
                        "Estancas y con grado de protección IPX1",
                        "Estancas con grado IPX4",
                        "Protegidas como mínimo IP5X",
                        "De cualquier tipo sin exigencia de protección"
                    ),
                    a = 0,
                    exp = "ITC-BT-30, apartado 1.1: «Las canalizaciones serán estancas, utilizándose, para terminales, empalmes y conexiones de las mismas, sistemas o dispositivos que presenten el grado de protección correspondiente a la caída vertical de gotas de agua (IPX1).»",
                    ref = "ITC-BT-30"
                ),
Question(
                    q = "Según el REBT, la tensión asignada de los conductores instalados en tubos en locales húmedos será de:",
                    opts = listOf(
                        "450/750 V",
                        "230/400 V",
                        "0,6/1 kV",
                        "300/500 V"
                    ),
                    a = 0,
                    exp = "ITC-BT-30, apartado 1.1.1: «Los conductores tendrán una tensión asignada de 450/750V y discurrirán por el interior de tubos.»",
                    ref = "ITC-BT-30"
                ),
Question(
                    q = "Según el REBT, los cables armados sin tubo protector en locales húmedos tendrán una tensión asignada de:",
                    opts = listOf(
                        "0,6/1 kV",
                        "450/750 V",
                        "230/400 V",
                        "300/500 V"
                    ),
                    a = 0,
                    exp = "ITC-BT-30, apartado 1.1.3: «Los conductores tendrán una tensión asignada de 0,6/1 kV.»",
                    ref = "ITC-BT-30"
                ),
Question(
                    q = "Según el REBT, la aparamenta en locales húmedos deberá presentar un grado de protección:",
                    opts = listOf(
                        "IPX1",
                        "IPX4",
                        "IP5X",
                        "IPXXB"
                    ),
                    a = 0,
                    exp = "ITC-BT-30, apartado 1.2: «Toda la aparamenta utilizada, deberá presentar el grado de protección correspondiente a la caída vertical de gotas de agua, IPX1.»",
                    ref = "ITC-BT-30"
                ),
Question(
                    q = "Según el REBT, los aparatos portátiles de alumbrado en locales húmedos serán:",
                    opts = listOf(
                        "De la Clase II",
                        "De la Clase 0",
                        "De la Clase I",
                        "Sin exigencia de clase"
                    ),
                    a = 0,
                    exp = "ITC-BT-30, apartado 1.3: «Los aparatos de alumbrado portátiles serán de la Clase II, según la Instrucción ITC-BT-43.»",
                    ref = "ITC-BT-30"
                ),
Question(
                    q = "Según el REBT, se consideran locales o emplazamientos mojados aquellos en que:",
                    opts = listOf(
                        "Los suelos, techos y paredes estén o puedan estar impregnados de humedad",
                        "Exista únicamente condensación ligera",
                        "Solo aparezcan manchas salinas",
                        "Únicamente estén situados a la intemperie"
                    ),
                    a = 0,
                    exp = "ITC-BT-30, apartado 2: «Locales o emplazamientos mojados son aquellos en que los suelos, techos y paredes estén o puedan estar impregnados de humedad.»",
                    ref = "ITC-BT-30"
                ),
Question(
                    q = "Según el REBT, las canalizaciones en locales mojados deberán presentar un grado de protección:",
                    opts = listOf(
                        "IPX4",
                        "IPX1",
                        "IP5X",
                        "IPXXB"
                    ),
                    a = 0,
                    exp = "ITC-BT-30, apartado 2.1: «Las canalizaciones serán estancas… con el grado de protección correspondiente a las proyecciones de agua, IPX4.»",
                    ref = "ITC-BT-30"
                ),
Question(
                    q = "Según el REBT, en locales mojados los aparatos de mando y protección:",
                    opts = listOf(
                        "Se instalarán fuera del local o deberán ser IPX4",
                        "Se instalarán siempre dentro del local",
                        "No requieren protección especial",
                        "Podrán ser de clase 0"
                    ),
                    a = 0,
                    exp = "ITC-BT-30, apartado 2.2: «Se instalarán los aparatos de mando y protección y tomas de corriente fuera de estos locales… serán del tipo protegido contra las proyecciones de agua, IPX4.»",
                    ref = "ITC-BT-30"
                ),
Question(
                    q = "Según el REBT, en locales mojados se instalará un dispositivo de protección:",
                    opts = listOf(
                        "En el origen de cada circuito que penetre en el local",
                        "Únicamente en el cuadro general",
                        "Solo si el circuito es trifásico",
                        "Cuando lo determine el instalador"
                    ),
                    a = 0,
                    exp = "ITC-BT-30, apartado 2.3: «Se instalará, en cualquier caso, un dispositivo de protección en el origen de cada circuito derivado de otro que penetre en el local mojado.»",
                    ref = "ITC-BT-30"
                ),
Question(
                    q = "Según el REBT, en locales mojados queda prohibida la utilización de aparatos móviles o portátiles:",
                    opts = listOf(
                        "Excepto cuando se utilice separación de circuitos o MBTS",
                        "En todos los casos sin excepción",
                        "Solo si son de clase I",
                        "Solo en corriente alterna"
                    ),
                    a = 0,
                    exp = "ITC-BT-30, apartado 2.4: «Queda prohibido… excepto cuando se utilice como sistema de protección la separación de circuitos o el empleo de muy bajas tensiones de seguridad, MBTS.»",
                    ref = "ITC-BT-30"
                ),
Question(
                    q = "Según el REBT, los receptores de alumbrado en locales mojados estarán protegidos contra:",
                    opts = listOf(
                        "Las proyecciones de agua, IPX4",
                        "La caída vertical de gotas, IPX1",
                        "El polvo, IP5X",
                        "Contactos directos exclusivamente"
                    ),
                    a = 0,
                    exp = "ITC-BT-30, apartado 2.5: «Los receptores de alumbrado estarán protegidos contra las proyecciones de agua, IPX4.»",
                    ref = "ITC-BT-30"
                ),
Question(
                    q = "Según el REBT, los locales con riesgo de corrosión son aquellos en los que:",
                    opts = listOf(
                        "Existan gases o vapores que puedan atacar a los materiales eléctricos",
                        "Exista únicamente humedad ambiental",
                        "Se superen los 40 ºC",
                        "Haya polvo en suspensión"
                    ),
                    a = 0,
                    exp = "ITC-BT-30, apartado 3: «Locales o emplazamientos con riesgo de corrosión son aquellos en los que existan gases o vapores que puedan atacar a los materiales eléctricos utilizados en la instalación.»",
                    ref = "ITC-BT-30"
                ),
Question(
                    q = "Según el REBT, en locales polvorientos sin riesgo de incendio el grado mínimo de protección será:",
                    opts = listOf(
                        "IP5X",
                        "IPX4",
                        "IPX1",
                        "IP2X"
                    ),
                    a = 0,
                    exp = "ITC-BT-30, apartado 4: «Las canalizaciones… tendrán un grado de protección mínimo IP5X.»",
                    ref = "ITC-BT-30"
                ),
Question(
                    q = "Según el REBT, se consideran locales a temperatura elevada aquellos donde:",
                    opts = listOf(
                        "La temperatura pueda sobrepasar frecuentemente los 40 ºC",
                        "La temperatura supere ocasionalmente los 30 ºC",
                        "La temperatura sea inferior a -20 ºC",
                        "Exista condensación permanente"
                    ),
                    a = 0,
                    exp = "ITC-BT-30, apartado 5: «Locales o emplazamientos a temperatura elevada son aquellos donde la temperatura del aire ambiente es susceptible de sobrepasar frecuentemente los 40 ºC.»",
                    ref = "ITC-BT-30"
                ),
Question(
                    q = "Según el REBT, se consideran locales a muy baja temperatura aquellos donde:",
                    opts = listOf(
                        "Pueden presentarse temperaturas inferiores a -20 ºC",
                        "La temperatura no supere los 0 ºC",
                        "Existan corrientes de aire",
                        "La temperatura supere los 50 ºC"
                    ),
                    a = 0,
                    exp = "ITC-BT-30, apartado 6: «Locales o emplazamientos a muy baja temperatura son aquellos donde pueden presentarse y mantenerse temperaturas ambientales inferiores a -20 ºC.»",
                    ref = "ITC-BT-30"
                ),
Question(
                    q = "Según el REBT, los locales con baterías de acumuladores se considerarán:",
                    opts = listOf(
                        "Locales con riesgo de corrosión",
                        "Locales mojados",
                        "Locales polvorientos",
                        "Locales a temperatura elevada"
                    ),
                    a = 0,
                    exp = "ITC-BT-30, apartado 7: «Los locales en que deban disponerse baterías de acumuladores… se considerarán como locales o emplazamientos con riesgo de corrosión.»",
                    ref = "ITC-BT-30"
                ),
Question(
                    q = "Según el REBT, los locales afectos a un servicio eléctrico:",
                    opts = listOf(
                        "Solo tienen acceso personas cualificadas",
                        "Pueden ser accesibles al público",
                        "No requieren medidas especiales",
                        "No necesitan alumbrado de seguridad"
                    ),
                    a = 0,
                    exp = "ITC-BT-30, apartado 8: «Locales… destinados a la explotación de instalaciones eléctricas y, en general, sólo tienen acceso a los mismos personas cualificadas para ello.»",
                    ref = "ITC-BT-30"
                ),
Question(
                    q = "Según el REBT, los locales con personal de servicio permanente estarán dotados de:",
                    opts = listOf(
                        "Alumbrado de seguridad",
                        "Alumbrado de reemplazamiento",
                        "Iluminación portátil",
                        "Iluminación decorativa"
                    ),
                    a = 0,
                    exp = "ITC-BT-30, apartado 8: «Los locales que tengan personal de servicio permanente, estarán dotados de un alumbrado de seguridad.»",
                    ref = "ITC-BT-30"
                ),
Question(
                    q = "Según el REBT, la norma que establece la clasificación de las influencias externas es:",
                    opts = listOf(
                        "UNE-HD 60.364-1",
                        "UNE 20.324",
                        "UNE 21.123",
                        "UNE-EN 60598"
                    ),
                    a = 0,
                    exp = "ITC-BT-30, apartado 9.1: «La norma UNE 20.460-3 (Anulada y sustituida por UNE-HD 60.364-1) establece una clasificación y una codificación de las influencias que deben ser tenidas en cuenta para el proyecto y la ejecución de las instalaciones eléctricas.»",
                    ref = "ITC-BT-30"
                ),
Question(
                    q = "Según el REBT, esta ITC trata de las prescripciones de las instalaciones eléctricas de:",
                    opts = listOf(
                        "Las piscinas, pediluvios y fuentes ornamentales",
                        "Las piscinas cubiertas exclusivamente",
                        "Las fuentes públicas únicamente",
                        "Las instalaciones deportivas en general"
                    ),
                    a = 0,
                    exp = "ITC-BT-31, apartado 1: «Esta ITC trata de las prescripciones de las instalaciones eléctricas de las piscinas, pediluvios y fuentes ornamentales.»",
                    ref = "ITC-BT-31"
                ),
Question(
                    q = "Según el REBT, la Zona 0 en piscinas comprende:",
                    opts = listOf(
                        "El interior de los recipientes, incluyendo cualquier canal en paredes o suelos",
                        "El área hasta 2 m del borde del vaso",
                        "El volumen situado a 2,5 m de altura",
                        "El cuarto de máquinas"
                    ),
                    a = 0,
                    exp = "ITC-BT-31, apartado 2.1.a): «Esta zona comprende el interior de los recipientes, incluyendo cualquier canal en las paredes o suelos, y los pediluvios o el interior de los inyectores de agua o cascadas.»",
                    ref = "ITC-BT-31"
                ),
Question(
                    q = "Según el REBT, la Zona 1 de una piscina está limitada, entre otros, por:",
                    opts = listOf(
                        "Un plano vertical a 2 m del borde del recipiente",
                        "Un plano vertical a 1 m del borde",
                        "Un plano vertical a 3 m del borde",
                        "Únicamente por el borde del vaso"
                    ),
                    a = 0,
                    exp = "ITC-BT-31, apartado 2.1.b): «Esta zona está limitada por: – un plano vertical a 2 m del borde del recipiente.»",
                    ref = "ITC-BT-31"
                ),
Question(
                    q = "Según el REBT, la altura del plano horizontal que limita la Zona 1 es de:",
                    opts = listOf(
                        "2,5 m por encima del suelo o superficie",
                        "2 m por encima del suelo",
                        "3 m por encima del suelo",
                        "1,25 m por encima del suelo"
                    ),
                    a = 0,
                    exp = "ITC-BT-31, apartado 2.1.b): «el plano horizontal a 2,5 m por encima del suelo o la superficie.»",
                    ref = "ITC-BT-31"
                ),
Question(
                    q = "Según el REBT, la Zona 2 de una piscina está limitada por:",
                    opts = listOf(
                        "Un plano paralelo situado a 1,5 m del límite de la Zona 1",
                        "Un plano paralelo situado a 2 m del límite de la Zona 1",
                        "Un plano paralelo situado a 0,6 m del límite de la Zona 1",
                        "Un plano paralelo situado a 2,5 m del límite de la Zona 1"
                    ),
                    a = 0,
                    exp = "ITC-BT-31, apartado 2.1.c): «el plano vertical externo a la Zona 1 y el plano paralelo a 1,5 m del anterior.»",
                    ref = "ITC-BT-31"
                ),
Question(
                    q = "Según el REBT, en las fuentes:",
                    opts = listOf(
                        "No existe Zona 2",
                        "Existen las Zonas 0, 1 y 2",
                        "Solo existe la Zona 2",
                        "Se aplican las mismas zonas que en piscinas"
                    ),
                    a = 0,
                    exp = "ITC-BT-31, apartado 2.1: «No existe Zona 2 para fuentes.»",
                    ref = "ITC-BT-31"
                ),
Question(
                    q = "Según el REBT, el grado de protección mínimo de los equipos eléctricos en la Zona 0 de piscinas será:",
                    opts = listOf(
                        "IPX8",
                        "IPX5",
                        "IPX4",
                        "IPX2"
                    ),
                    a = 0,
                    exp = "ITC-BT-31, apartado 2.2: «Zona 0: IP X8.»",
                    ref = "ITC-BT-31"
                ),
Question(
                    q = "Según el REBT, el grado de protección exigido en la Zona 1 será:",
                    opts = listOf(
                        "IPX5",
                        "IPX8",
                        "IPX2",
                        "IPXXB"
                    ),
                    a = 0,
                    exp = "ITC-BT-31, apartado 2.2: «Zona 1: IP X5.»",
                    ref = "ITC-BT-31"
                ),
Question(
                    q = "Según el REBT, cuando se usa MBTS, la protección contra contactos directos debe proporcionarse mediante:",
                    opts = listOf(
                        "Barreras o cubiertas con IP2X o IPXXB o aislamiento ensayado a 500 V",
                        "Únicamente mediante obstáculos",
                        "Puesta fuera de alcance",
                        "Locales no conductores"
                    ),
                    a = 0,
                    exp = "ITC-BT-31, apartado 2.2: «la protección contra los contactos directos debe proporcionarse mediante: – barreras o cubiertas que proporcionen un grado de protección mínimo IP 2X ó IP XXB… o – un aislamiento capaz de soportar una tensión de ensayo de 500 V… durante 1 minuto.»",
                    ref = "ITC-BT-31"
                ),
Question(
                    q = "Según el REBT, las medidas de protección por obstáculos o puesta fuera de alcance:",
                    opts = listOf(
                        "No son admisibles",
                        "Son obligatorias",
                        "Son preferentes",
                        "Se admiten en Zona 2"
                    ),
                    a = 0,
                    exp = "ITC-BT-31, apartado 2.2: «Las medidas de protección contra los contactos directos por medio de obstáculos o por puesta fuera de alcance por alejamiento, no son admisibles.»",
                    ref = "ITC-BT-31"
                ),
Question(
                    q = "Según el REBT, todos los elementos conductores de los volúmenes 0, 1 y 2 deben:",
                    opts = listOf(
                        "Conectarse a una conexión equipotencial suplementaria local",
                        "Estar aislados del terreno",
                        "Ser de material plástico",
                        "Estar conectados al neutro"
                    ),
                    a = 0,
                    exp = "ITC-BT-31, apartado 2.2: «Todos los elementos conductores de los volúmenes 0, 1 y 2… deben conectarse a una conexión equipotencial suplementaria local.»",
                    ref = "ITC-BT-31"
                ),
Question(
                    q = "Según el REBT, en las Zonas 0 y 1 solo se admite protección mediante MBTS con tensiones no superiores a:",
                    opts = listOf(
                        "12 V en corriente alterna o 30 V en corriente continua",
                        "25 V en corriente alterna",
                        "50 V en corriente alterna",
                        "60 V en corriente continua"
                    ),
                    a = 0,
                    exp = "ITC-BT-31, apartado 2.2: «solo se admite protección mediante MBTS a tensiones asignadas no superiores a 12 V en corriente alterna o 30 V en corriente continua.»",
                    ref = "ITC-BT-31"
                ),
Question(
                    q = "Según el REBT, en el volumen 0 no se permitirá:",
                    opts = listOf(
                        "Ninguna canalización al alcance de los bañistas",
                        "La instalación de luminarias",
                        "La instalación de bombas",
                        "La conexión equipotencial"
                    ),
                    a = 0,
                    exp = "ITC-BT-31, apartado 2.2.1: «En el volumen 0 ninguna canalización se encontrará en el interior de la piscina al alcance de los bañistas.»",
                    ref = "ITC-BT-31"
                ),
Question(
                    q = "Según el REBT, en los volúmenes 0 y 1 no se admitirán cajas de conexión:",
                    opts = listOf(
                        "Salvo cajas de MBTS en volumen 1 con IPX5",
                        "En ningún caso",
                        "Salvo cajas metálicas",
                        "Salvo cajas empotradas"
                    ),
                    a = 0,
                    exp = "ITC-BT-31, apartado 2.2.2: «En los volúmenes 0 y 1 no se admitirán cajas de conexión, salvo que en el volumen 1 se admitirán cajas para muy baja tensión de seguridad (MBTS)… IP X5.»",
                    ref = "ITC-BT-31"
                ),
Question(
                    q = "Según el REBT, las luminarias para uso en el agua deben cumplir la norma:",
                    opts = listOf(
                        "UNE-EN 60.598-2-18",
                        "UNE 20.324",
                        "UNE 20.460-3",
                        "UNE-EN 60.335-2-41"
                    ),
                    a = 0,
                    exp = "ITC-BT-31, apartado 2.2.3: «Las luminarias para uso en el agua o en contacto con el agua deben cumplir con la norma UNE-EN 60.598-2-18.»",
                    ref = "ITC-BT-31"
                ),
Question(
                    q = "Según el REBT, en los volúmenes 0 y 1:",
                    opts = listOf(
                        "No deben instalarse interruptores ni bases de toma de corriente",
                        "Se permiten bases de toma de corriente sin protección",
                        "Se admiten interruptores con IPX4",
                        "Se permiten tomas de corriente metálicas"
                    ),
                    a = 0,
                    exp = "ITC-BT-31, apartado 2.2.4: «Elementos tales como interruptores, programadores, y bases de toma de corriente no deben instalarse en los volúmenes 0 y 1.»",
                    ref = "ITC-BT-31"
                ),
Question(
                    q = "Según el REBT, en las fuentes solo se diferencian los volúmenes:",
                    opts = listOf(
                        "0 y 1",
                        "0, 1 y 2",
                        "1 y 2",
                        "Solo volumen 0"
                    ),
                    a = 0,
                    exp = "ITC-BT-31, apartado 3: «En las fuentes se diferencian sólo dos volúmenes 0 y 1.»",
                    ref = "ITC-BT-31"
                ),
Question(
                    q = "Según el REBT, en los volúmenes 0 y 1 de las fuentes debe instalarse:",
                    opts = listOf(
                        "Una conexión equipotencial suplementaria local",
                        "Un transformador de aislamiento",
                        "Un interruptor general",
                        "Un seccionador manual"
                    ),
                    a = 0,
                    exp = "ITC-BT-31, apartado 3.2: «En los volúmenes 0 y 1 debe instalarse una conexión equipotencial suplementaria local.»",
                    ref = "ITC-BT-31"
                ),
Question(
                    q = "Según el REBT, el grado mínimo de protección contra la penetración del agua en el volumen 0 de fuentes será:",
                    opts = listOf(
                        "IPX8",
                        "IPX5",
                        "IPX4",
                        "IPX2"
                    ),
                    a = 0,
                    exp = "ITC-BT-31, apartado 3.3: «Volumen 0 IPX8.»",
                    ref = "ITC-BT-31"
                ),
Question(
                    q = "Según el REBT, los equipos eléctricos fijos de baja tensión instalados en el volumen 1 se admitirán si:",
                    opts = listOf(
                        "Cumplen las prescripciones del apartado 4",
                        "Funcionan únicamente en vacío",
                        "Son portátiles",
                        "Están alimentados directamente en BT"
                    ),
                    a = 0,
                    exp = "ITC-BT-31, apartado 4: «Los equipos eléctricos fijos especialmente destinados a ser utilizados en las piscinas… se admiten en el volumen 1, siempre que cumplan los siguientes requisitos listados en el mismo, a, b, c y d»",
                    ref = "ITC-BT-31"
                ),
Question(
                    q = "Según el REBT, esta instrucción trata de los requisitos particulares de los sistemas de instalación del equipo eléctrico de:",
                    opts = listOf(
                        "Grúas, aparatos de elevación y transporte y otros equipos similares",
                        "Únicamente ascensores de edificios",
                        "Máquinas industriales fijas",
                        "Instalaciones temporales de obra"
                    ),
                    a = 0,
                    exp = "ITC-BT-32, apartado 1: «Esta instrucción trata de los requisitos particulares de los sistemas de instalación del equipo eléctrico de grúas, aparatos de elevación y transporte y otros equipos similares tales como escaleras mecánicas, cintas transportadoras, puentes rodantes, cabrestantes, andamios eléctricos, etc.»",
                    ref = "ITC-BT-32"
                ),
Question(
                    q = "Según el REBT, la instalación podrá ponerse fuera de servicio mediante:",
                    opts = listOf(
                        "Un interruptor omnipolar general de accionamiento manual",
                        "Un interruptor unipolar",
                        "Un seccionador automático",
                        "Un contactor de potencia"
                    ),
                    a = 0,
                    exp = "ITC-BT-32, apartado 2: «La instalación en su conjunto se podrá poner fuera de servicio mediante un interruptor omnipolar general de accionamiento manual, colocado en el circuito principal.»",
                    ref = "ITC-BT-32"
                ),
Question(
                    q = "Según el REBT, el interruptor omnipolar general deberá estar situado:",
                    opts = listOf(
                        "En lugares fácilmente accesibles desde el suelo y en el mismo local del equipo",
                        "En el interior del motor exclusivamente",
                        "En un local distinto al del equipo",
                        "En el cuadro general del edificio"
                    ),
                    a = 0,
                    exp = "ITC-BT-32, apartado 2: «Este interruptor deberá estar situado en lugares fácilmente accesibles desde el suelo, en el mismo local o recinto en el que esté situado el equipo eléctrico de accionamiento.»",
                    ref = "ITC-BT-32"
                ),
Question(
                    q = "Según el REBT, la caída de tensión en el arranque del motor no deberá ser superior al:",
                    opts = listOf(
                        "5 %",
                        "3 %",
                        "10 %",
                        "2 %"
                    ),
                    a = 0,
                    exp = "ITC-BT-32, apartado 2: «Las canalizaciones ... deberán estar dimensionadas de manera que el arranque del motor no provoque una caída de tensión superior al 5 %.»",
                    ref = "ITC-BT-32"
                ),
Question(
                    q = "Según el REBT, se permitirá la instalación de interruptores suspendidos de la canalización móvil únicamente cuando:",
                    opts = listOf(
                        "Las máquinas estén destinadas exclusivamente al transporte de mercancías sin jaulas",
                        "Se trate de grúas de personas",
                        "Existan jaulas de transporte",
                        "Sean ascensores"
                    ),
                    a = 0,
                    exp = "ITC-BT-32, apartado 2: «Únicamente en el caso de que las máquinas destinadas exclusivamente al transporte de mercancías no dispongan de jaulas para el transporte, se permitirá la instalación de interruptores suspendidos de la extremidad de la canalización móvil.»",
                    ref = "ITC-BT-32"
                ),
Question(
                    q = "Según el REBT, en instalaciones exteriores para servicios móviles se utilizarán:",
                    opts = listOf(
                        "Cables flexibles con cubierta de policloropreno o similar",
                        "Cables rígidos armados",
                        "Conductores desnudos",
                        "Cables con aislamiento mineral"
                    ),
                    a = 0,
                    exp = "ITC-BT-32, apartado 2: «En las instalaciones en el exterior para servicios móviles se utilizarán cables flexibles con cubierta de policloropeno o similar según UNE 21.027 ó UNE 21.150.»",
                    ref = "ITC-BT-32"
                ),
Question(
                    q = "Según el REBT, los ascensores y estructuras metálicas de los motores:",
                    opts = listOf(
                        "Se conectarán a tierra",
                        "Quedarán aislados",
                        "Se conectarán al neutro",
                        "No requieren conexión"
                    ),
                    a = 0,
                    exp = "ITC-BT-32, apartado 2: «Los ascensores, las estructuras de todos los motores, máquinas elevadoras, combinadores y cubiertas metálicas ... se conectarán a tierra.»",
                    ref = "ITC-BT-32"
                ),
Question(
                    q = "Según el REBT, los locales donde esté instalado el equipo eléctrico de accionamiento:",
                    opts = listOf(
                        "Sólo deberán ser accesibles a personas cualificadas",
                        "Podrán ser accesibles al público",
                        "No requieren restricciones de acceso",
                        "Podrán ser utilizados como almacén"
                    ),
                    a = 0,
                    exp = "ITC-BT-32, apartado 2: «Los locales, recintos, etc. en los que esté instalado el equipo eléctrico de accionamiento, sólo deberán ser accesibles a personas cualificadas.»",
                    ref = "ITC-BT-32"
                ),
Question(
                    q = "Según el REBT, los sistemas colectores deben estar dispuestos de forma que:",
                    opts = listOf(
                        "Exista protección frente al contacto directo",
                        "Permitan el contacto accidental",
                        "Sean accesibles sin protección",
                        "No requieran cerramientos"
                    ),
                    a = 0,
                    exp = "ITC-BT-32, apartado 3.1: «... tenga protección frente al contacto directo con las partes en tensión, de acuerdo con el apartado 2 de la ITC-BT-24.»",
                    ref = "ITC-BT-32"
                ),
Question(
                    q = "Según el REBT, la protección por puesta fuera de alcance:",
                    opts = listOf(
                        "Está pensada únicamente para evitar el contacto accidental",
                        "Garantiza protección total",
                        "Sustituye al aislamiento",
                        "Es válida para cualquier persona"
                    ),
                    a = 0,
                    exp = "ITC-BT-32, apartado 3.1: «La protección mediante la colocación fuera del alcance está pensada únicamente para evitar el contacto accidental con las partes en tensión.»",
                    ref = "ITC-BT-32"
                ),
Question(
                    q = "Según el REBT, el equipo eléctrico se protegerá contra sobreintensidades mediante:",
                    opts = listOf(
                        "Dispositivos automáticos de protección",
                        "Fusibles únicamente",
                        "Relés térmicos exclusivamente",
                        "Contactores"
                    ),
                    a = 0,
                    exp = "ITC-BT-32, apartado 3.2: «El equipo eléctrico se protegerá mediante uno o más dispositivos automáticos de protección que actúen en caso de una sobreintensidad provocada por sobrecarga o cortocircuito.»",
                    ref = "ITC-BT-32"
                ),
Question(
                    q = "Según el REBT, los interruptores de mantenimiento deberán ser:",
                    opts = listOf(
                        "De corte omnipolar",
                        "Unipolares",
                        "De mando a distancia",
                        "Automáticos"
                    ),
                    a = 0,
                    exp = "ITC-BT-32, apartado 4.1: «Los interruptores deben ser de corte omnipolar y deberá tener los medios necesarios para impedir toda puesta en tensión de las instalaciones de forma imprevista.»",
                    ref = "ITC-BT-32"
                ),
Question(
                    q = "Según el REBT, cada grúa deberá disponer de:",
                    opts = listOf(
                        "Uno o más mecanismos de parada de emergencia",
                        "Un interruptor unipolar",
                        "Un relé térmico",
                        "Un temporizador"
                    ),
                    a = 0,
                    exp = "ITC-BT-32, apartado 4.2: «Cada grúa, aparato de elevación o transporte debe tener uno o más mecanismos de parada de emergencia.»",
                    ref = "ITC-BT-32"
                ),
Question(
                    q = "Según el REBT, la reconexión tras una parada de emergencia:",
                    opts = listOf(
                        "Sólo puede realizarse desde el dispositivo desde el cual se realizó el corte",
                        "Puede realizarse desde cualquier punto",
                        "Es automática",
                        "No está regulada"
                    ),
                    a = 0,
                    exp = "ITC-BT-32, apartado 4.2: «La reconexión solamente puede ser posible desde el dispositivo de control desde el cual se realizó el corte de emergencia.»",
                    ref = "ITC-BT-32"
                ),
Question(
                    q = "Según el REBT, los interruptores deberán cumplir la norma:",
                    opts = listOf(
                        "UNE-EN 60.947-2",
                        "UNE 20.460-3",
                        "UNE-EN 60.598-2-18",
                        "UNE 21.027"
                    ),
                    a = 0,
                    exp = "ITC-BT-32, apartado 5.1: «Los interruptores deberán cumplir la UNE-EN 60.947-2.»",
                    ref = "ITC-BT-32"
                ),
Question(
                    q = "Según el REBT, los contactores:",
                    opts = listOf(
                        "No deben utilizarse para seccionamiento",
                        "Son obligatorios para seccionamiento",
                        "Sustituyen a los interruptores",
                        "Se usan como protección diferencial"
                    ),
                    a = 0,
                    exp = "ITC-BT-32, apartado 5.1: «Los contactores no deben utilizarse para seccionamiento.»",
                    ref = "ITC-BT-32"
                ),
Question(
                    q = "Según el REBT, los interruptores del lado de la alimentación deben permitir:",
                    opts = listOf(
                        "Aislar los anillos y barras del suministro principal",
                        "Sólo el corte del neutro",
                        "El arranque automático",
                        "La regulación de velocidad"
                    ),
                    a = 0,
                    exp = "ITC-BT-32, apartado 5.2: «Debe ser posible aislar los anillos del colector y las barras o cables del suministro principal antes del punto de conexión de la grúa.»",
                    ref = "ITC-BT-32"
                ),
Question(
                    q = "Según el REBT, el conductor de protección en sistemas con anillos colectores:",
                    opts = listOf(
                        "Debe tener un anillo o barra colectora individual",
                        "Puede compartirse con conductores activos",
                        "No es obligatorio",
                        "Puede ser móvil"
                    ),
                    a = 0,
                    exp = "ITC-BT-32, apartado 6: «El conductor de protección debe tener un anillo colector individual o una barra colectora, cuyos soportes sean claramente visibles y distinguibles de aquellos de los anillos o barras colectoras activos.»",
                    ref = "ITC-BT-32"
                ),
Question(
                    q = "Según el REBT, los conductores de protección:",
                    opts = listOf(
                        "No deben transportar corriente en funcionamiento normal",
                        "Deben transportar corriente permanentemente",
                        "Pueden sustituirse por ruedas",
                        "Son opcionales"
                    ),
                    a = 0,
                    exp = "ITC-BT-32, apartado 6: «Los conductores de protección no deben transportar ninguna corriente cuando funcionen normalmente.»",
                    ref = "ITC-BT-32"
                ),
Question(
                    q = "Según el REBT, los aparatos de elevación:",
                    opts = listOf(
                        "Deben conectarse a los conductores de protección",
                        "Pueden conectarse mediante ruedas",
                        "No requieren conexión",
                        "Se conectan al neutro"
                    ),
                    a = 0,
                    exp = "ITC-BT-32, apartado 6: «Los aparatos de elevación deben conectarse a los conductores de protección no admitiéndose ruedas o rodillos para su conexión.»",
                    ref = "ITC-BT-32"
                ),
Question(
                    q = "Según el REBT, las prescripciones de esta instrucción se aplican a las instalaciones temporales destinadas a:",
                    opts = listOf(
                        "La construcción de nuevos edificios, trabajos de reparación, trabajos públicos y excavaciones",
                        "Únicamente a edificios industriales",
                        "Instalaciones permanentes en viviendas",
                        "Centros de transformación"
                    ),
                    a = 0,
                    exp = "ITC-BT-33, apartado 1: «Las prescripciones particulares de esta instrucción se aplican a las instalaciones temporales destinadas: – a la construcción de nuevos edificios – a trabajos de reparación, modificación, extensión o demolición de edificios existentes. – a trabajos públicos – a trabajos de excavación, y – a trabajos similares.»",
                    ref = "ITC-BT-33"
                ),
Question(
                    q = "Según el REBT, las partes de edificios que sufran transformaciones importantes serán consideradas como:",
                    opts = listOf(
                        "Obras durante el tiempo que duren los trabajos",
                        "Instalaciones fijas",
                        "Locales de pública concurrencia",
                        "Locales húmedos"
                    ),
                    a = 0,
                    exp = "ITC-BT-33, apartado 1: «Las partes de edificios que sufran transformaciones tales como ampliaciones, reparaciones importantes o demoliciones serán consideradas como obras durante el tiempo que duren los trabajos correspondientes.»",
                    ref = "ITC-BT-33"
                ),
Question(
                    q = "Según el REBT, en los locales de servicios de las obras serán aplicables:",
                    opts = listOf(
                        "Las prescripciones técnicas recogidas en la ITC-BT-24",
                        "Las de la ITC-BT-19",
                        "Las de la ITC-BT-30",
                        "Las de la ITC-BT-52"
                    ),
                    a = 0,
                    exp = "ITC-BT-33, apartado 1: «En los locales de servicios de las obras (oficinas, vestuarios, salas de reunión, restaurantes, dormitorios, locales sanitarios, etc.) serán aplicables las prescripciones técnicas recogidas en la ITC-BT-24.»",
                    ref = "ITC-BT-33"
                ),
Question(
                    q = "Según el REBT, en las instalaciones de obras las instalaciones fijas están limitadas a:",
                    opts = listOf(
                        "El cuadro general de mando y los dispositivos de protección principales",
                        "Todos los circuitos de utilización",
                        "Los receptores portátiles",
                        "Las tomas de corriente"
                    ),
                    a = 0,
                    exp = "ITC-BT-33, apartado 1: «En las instalaciones de obras, las instalaciones fijas están limitadas al conjunto que comprende el cuadro general de mando y los dispositivos de protección principales.»",
                    ref = "ITC-BT-33"
                ),
Question(
                    q = "Según el REBT, toda instalación deberá estar identificada según:",
                    opts = listOf(
                        "La fuente que la alimente",
                        "El tipo de cable utilizado",
                        "La potencia instalada",
                        "El número de circuitos"
                    ),
                    a = 0,
                    exp = "ITC-BT-33, apartado 2.1: «Toda instalación deberá estar identificada según la fuente que la alimente y sólo debe incluir elementos alimentados por ella.»",
                    ref = "ITC-BT-33"
                ),
Question(
                    q = "Según el REBT, una misma obra puede ser alimentada:",
                    opts = listOf(
                        "A partir de varias fuentes de alimentación",
                        "Únicamente desde la red pública",
                        "Sólo desde un generador",
                        "Exclusivamente mediante baterías"
                    ),
                    a = 0,
                    exp = "ITC-BT-33, apartado 2.1: «Una misma obra puede ser alimentada a partir de varias fuentes de alimentación incluidos los generadores fijos o móviles.»",
                    ref = "ITC-BT-33"
                ),
Question(
                    q = "Según el REBT, las distintas alimentaciones deben conectarse mediante dispositivos que:",
                    opts = listOf(
                        "Impidan la interconexión entre ellas",
                        "Permitan su conexión simultánea",
                        "Unifiquen las fases",
                        "Compartan el neutro"
                    ),
                    a = 0,
                    exp = "ITC-BT-33, apartado 2.1: «Las distintas alimentaciones deben ser conectadas mediante dispositivos diseñados de modo que impidan la interconexión entre ellas.»",
                    ref = "ITC-BT-33"
                ),
Question(
                    q = "Según el REBT, deberán preverse instalaciones de seguridad cuando:",
                    opts = listOf(
                        "Existan riesgos para la seguridad de las personas",
                        "La obra sea pequeña",
                        "El suministro sea monofásico",
                        "No haya alumbrado"
                    ),
                    a = 0,
                    exp = "ITC-BT-33, apartado 3: «Cuando debido al posible fallo de la alimentación normal de un circuito o aparato existan riesgos para la seguridad de las personas, deberán preverse instalaciones de seguridad.»",
                    ref = "ITC-BT-33"
                ),
Question(
                    q = "Según el REBT, el alumbrado de seguridad permitirá:",
                    opts = listOf(
                        "La evacuación del personal y la puesta en marcha de las medidas de seguridad",
                        "El trabajo normal continuado",
                        "La alimentación de maquinaria",
                        "La iluminación decorativa"
                    ),
                    a = 0,
                    exp = "ITC-BT-33, apartado 3.1: «El alumbrado de seguridad permitirá, en caso de fallo del alumbrado normal, la evacuación del personal y la puesta en marcha de las medidas de seguridad previstas.»",
                    ref = "ITC-BT-33"
                ),
Question(
                    q = "Según el REBT, los circuitos de seguridad cuya continuidad sea esencial deberán:",
                    opts = listOf(
                        "Quedar asegurados sin corte automático de la alimentación",
                        "Protegerse exclusivamente con diferenciales",
                        "Interrumpirse automáticamente",
                        "Ser alimentados sólo por la red pública"
                    ),
                    a = 0,
                    exp = "ITC-BT-33, apartado 3.2: «Otros circuitos ... deberán preverse de tal forma que la protección contra los contactos indirectos quede asegurada sin corte automático de la alimentación.»",
                    ref = "ITC-BT-33"
                ),
Question(
                    q = "Según el REBT, estos circuitos estarán alimentados por un sistema automático con:",
                    opts = listOf(
                        "Corte breve",
                        "Corte largo",
                        "Corte manual",
                        "Sin corte"
                    ),
                    a = 0,
                    exp = "ITC-BT-33, apartado 3.2: «Dichos circuitos estarán alimentados por un sistema automático con corte breve.»",
                    ref = "ITC-BT-33"
                ),
Question(
                    q = "Según el REBT, uno de los sistemas de alimentación de seguridad admitidos es:",
                    opts = listOf(
                        "Grupos generadores con motores térmicos",
                        "Transformadores de aislamiento",
                        "Líneas aéreas",
                        "UPS domésticos"
                    ),
                    a = 0,
                    exp = "ITC-BT-33, apartado 3.2: «... que podrá ser de uno de los tipos siguientes: – Grupos generadores con motores térmicos, o – Baterías de acumuladores asociadas a un rectificador o un ondulador.»",
                    ref = "ITC-BT-33"
                )
    )
}
