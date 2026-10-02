package com.example.data

object EspecialesQuestionsPart3 {
    val QUESTIONS = listOf(
Question(
                    q = "¿Qué dispositivo de sujeción debe disponerse en la entrada de cables de un receptor móvil según la ITC-BT-43?",
                    opts = listOf(
                        "Una abrazadera de alambre de cobre soldado",
                        "Una cinta adhesiva vulcanizada con sellado de cera",
                        "Un manguito retráctil de plomo fundido",
                        "Un dispositivo de anclaje antitracción que evite que los esfuerzos mecánicos se transmitan a los bornes de conexión"
                    ),
                    a = 3,
                    exp = "La ITC-BT-43 apartado 2 exige que los receptores eléctricos alimentados mediante cables flexibles cuenten con un dispositivo de retención mecánica (prensaestopas o abrazadera antitracción) que impida que los tirones o torsiones del cable exterior sometan a tracción a los bornes eléctricos internos.",
                    ref = "ITC-BT-43"
                ),
Question(
                    q = "En el cable de alimentación flexible de un receptor de Clase I, ¿qué regla de seguridad rige para la longitud del conductor de protección (PE) según ITC-BT-43?",
                    opts = listOf(
                        "El conductor de protección debe cortarse más corto para que se rompa primero en caso de tirón",
                        "Debe tener mayor longitud que los conductores activos para que sea el último en desconectarse si falla el anclaje antitracción",
                        "Debe tener exactamente la misma longitud que la fase y el neutro",
                        "El conductor de protección no se conecta en el interior del aparato, solo en el enchufe"
                    ),
                    a = 1,
                    exp = "Conforme a las normas UNE de seguridad de aparatos recogidas por la ITC-BT-43, el conductor de protección (verde-amarillo) debe disponerse con holgura y mayor longitud que las fases y el neutro, garantizando que ante un tirón violento que arranque el cable, la protección a tierra sea la última en separarse.",
                    ref = "ITC-BT-43"
                ),
Question(
                    q = "¿Qué tipo de cable flexible es preceptivo para receptores portátiles utilizados en ambientes industriales o de obra según ITC-BT-43 e ITC-BT-30?",
                    opts = listOf(
                        "Cables con conductores rígidos de aluminio sin cubierta",
                        "Hilos simples de PVC tipo H07V-K instalados al aire",
                        "Cables flexibles con aislamiento y cubierta elastomérica para servicio pesado tipo H07RN-F (neopreno)",
                        "Cables planos bajo yeso tipo H03VVH2-F"
                    ),
                    a = 2,
                    exp = "La ITC-BT-43 en coordinación con las instrucciones de locales húmedos, mojados y de obras exige que los receptores portátiles sometidos a esfuerzos mecánicos severos se alimenten mediante cables flexibles con cubierta de policloropreno resistente al agua, aceites e impactos (tipo H07RN-F o equivalente).",
                    ref = "ITC-BT-43"
                ),
Question(
                    q = "¿Qué medios de desconexión deben preverse para receptores fijos o de gran potencia según la ITC-BT-43 e ITC-BT-22?",
                    opts = listOf(
                        "Desmontar el cuadro general de contadores del edificio",
                        "Ninguno, se dejan permanentemente cableados a la acometida",
                        "Un fusible aéreo cortado manualmente con alicates aislados",
                        "Un interruptor de corte omnipolar accesible o clavija de toma de corriente que permita seccionarlos de la red de forma segura"
                    ),
                    a = 3,
                    exp = "La ITC-BT-43 pto. 2.1 y la ITC-BT-22 exigen que los receptores eléctricos fijos dispongan de dispositivos de corte y seccionamiento omnipolar (interruptores manuales, automáticos de circuito o combinaciones clavija-base accesibles) para permitir maniobras de mantenimiento o parada segura sin riesgo eléctrico.",
                    ref = "ITC-BT-43"
                ),
Question(
                    q = "¿Qué prescripción térmica deben cumplir los receptores que desprendan calor durante su funcionamiento según la ITC-BT-43?",
                    opts = listOf(
                        "Deben sumergirse en cubas de agua fría antes de encenderse",
                        "Deben instalarse manteniendo las distancias de seguridad a materiales combustibles prescritas por el fabricante y el REBT",
                        "Deben montarse obligatoriamente sobre paneles de madera de pino sin tratar",
                        "No pueden utilizarse en locales donde la temperatura ambiente sea superior a 15 ºC"
                    ),
                    a = 1,
                    exp = "La ITC-BT-43 establece que los aparatos que produzcan calor o alcancen elevadas temperaturas superficiales deben emplazarse de forma que no transmitan calor peligroso a partes adyacentes de la edificación, respetando distancias mínimas y empleando pantallas incombustibles o aislantes térmicos.",
                    ref = "ITC-BT-43"
                ),
Question(
                    q = "Cuando un receptor incorpora bornes para conexión de conductores de aluminio, ¿qué característica deben poseer según la ITC-BT-43?",
                    opts = listOf(
                        "Deben ser de plástico termoestable sin piezas metálicas",
                        "Deben engrasarse con vaselina pura antes de cada inspección",
                        "Deben ser bornes específicamente diseñados y homologados para aluminio o bimetálicos para evitar corrosión galvánica y aflojamiento",
                        "No se admite la conexión de conductores de aluminio en ningún receptor en baja tensión"
                    ),
                    a = 2,
                    exp = "Conforme a la ITC-BT-43 y normas UNE de conexionado, la unión de conductores de aluminio exige bornes bimetálicos (Al/Cu) o terminales diseñados contra el efecto de fluencia (creep) y la corrosión electroquímica, impidiendo el recalentamiento por falsos contactos.",
                    ref = "ITC-BT-43"
                ),
Question(
                    q = "¿Qué protección contra sobreintensidades debe preverse para receptores individuales según la ITC-BT-43 y la ITC-BT-22?",
                    opts = listOf(
                        "Protección exclusiva por limitador de potencia ICP del contador",
                        "No se requiere protección si el receptor consume menos de 16 A",
                        "Un relé diferencial de sensibilidad 300 mA con rearme cíclico",
                        "Dispositivos de protección contra sobrecargas y cortocircuitos dimensionados conforme a las características nominales del receptor y su circuito"
                    ),
                    a = 3,
                    exp = "La ITC-BT-43 apartado 2.1 prescribe que todo receptor debe estar adecuadamente protegido contra sobrecargas y cortocircuitos mediante interruptores automáticos o fusibles calibrados para la intensidad del circuito y del receptor, evitando que las sobreintensidades originen incendios.",
                    ref = "ITC-BT-43"
                ),
Question(
                    q = "¿Qué grado de protección IP mínimo debe tener un receptor eléctrico instalado a la intemperie sin resguardo según la ITC-BT-43 e ITC-BT-30?",
                    opts = listOf(
                        "IP20",
                        "Al menos IPX4 (protección contra proyecciones de agua en todas direcciones) o superior según el emplazamiento",
                        "IP00",
                        "IP68 sumergible a 50 metros obligatoriamente"
                    ),
                    a = 1,
                    exp = "Según la ITC-BT-43 y las condiciones de influencias externas (AD4 o superior, intemperie), los receptores no resguardados deben garantizar como mínimo un grado de estanqueidad IPX4 (proyecciones de agua) y protección adecuada contra penetración de partículas sólidas (mínimo IP44 o IP54).",
                    ref = "ITC-BT-43"
                ),
Question(
                    q = "¿Cómo debe realizarse la conexión equipotencial de receptores con envolventes metálicas de Clase I en locales con presencia de agua (p. ej. cocinas industriales) según la ITC-BT-43?",
                    opts = listOf(
                        "Aislando las carcasas del suelo mediante tacos de madera secos",
                        "Conectando las carcasas al neutro de la toma de corriente",
                        "Interconectando todas las masas metálicas accesibles y elementos conductores ajenos a la red de equipotencialidad suplementaria",
                        "Retirando el cable de tierra para evitar derivaciones"
                    ),
                    a = 2,
                    exp = "En locales especiales o húmedos donde operan receptores de Clase I (ITC-BT-43 e ITC-BT-24/30), es preceptiva la unión equipotencial suplementaria de todas las masas metálicas simultáneamente accesibles y elementos conductores ajenos, igualando potenciales para evitar diferencias de tensión de contacto peligrosas.",
                    ref = "ITC-BT-43"
                ),
Question(
                    q = "¿Qué fenómeno deben mitigar o evitar los receptores inductivos o electrónicos según las exigencias de compatibilidad de la ITC-BT-43 y REBT?",
                    opts = listOf(
                        "La generación de corriente continua en las bobinas de arranque",
                        "El incremento de la presión hidrostática en las tuberías de condensados",
                        "La ionización natural del aire circundante",
                        "La inyección de armónicos excesivos, perturbaciones electromagnéticas y bajo factor de potencia en la red de distribución"
                    ),
                    a = 3,
                    exp = "La ITC-BT-43 par. 2.1 y la legislación europea de compatibilidad electromagnética (CEM) imponen que los receptores conectados no generen perturbaciones parásitas, sobretensiones ni corrientes armónicas que degraden la calidad de onda ni interfieran en otros receptores de la red.",
                    ref = "ITC-BT-43"
                ),
Question(
                    q = "¿Qué se debe verificar obligatoriamente en la placa de características de un receptor importado antes de su instalación según la ITC-BT-43?",
                    opts = listOf(
                        "Que incluya la firma manual del importador",
                        "Que su tensión asignada de diseño sea compatible con los 230 V (monofásica) o 400 V (trifásica) a 50 Hz de la red española",
                        "Que su peso total en kilogramos sea inferior a 25 kg",
                        "Que indique que puede funcionar tanto a 60 Hz como a 100 Hz indistintamente"
                    ),
                    a = 1,
                    exp = "La ITC-BT-43 apartado 2.4 prohíbe la conexión de receptores cuyas tensiones o frecuencias asignadas difieran de las de la instalación suministradora. En España (red normalizada 230/400 V a 50 Hz), instalar receptores a 110-120 V o 60 Hz sin transformadores/convertidores provoca sobrecalentamiento y avería inmediata.",
                    ref = "ITC-BT-43"
                ),
Question(
                    q = "¿Cómo se define un receptor eléctrico de 'aislamiento reforzado' según la terminología de la ITC-BT-43 e ITC-BT-01?",
                    opts = listOf(
                        "Un aparato dotado de una chapa metálica de 5 mm de espesor",
                        "Un receptor con dos cables de toma de tierra en paralelo",
                        "Un sistema de aislamiento único aplicado sobre las partes activas que proporciona un grado de protección contra choques equivalente al doble aislamiento",
                        "Un transformador enfriado por aceite mineral dieléctrico"
                    ),
                    a = 2,
                    exp = "Según la ITC-BT-43 y la ITC-BT-01, el aislamiento reforzado es una estructura de aislamiento único continuo que ofrece propiedades mecánicas y dieléctricas tan elevadas que su nivel de seguridad ante descargas eléctricas equivale íntegramente al conseguido mediante la suma de aislamiento principal más suplementario (Clase II).",
                    ref = "ITC-BT-43"
                ),
Question(
                    q = "¿Qué verificación periódica de seguridad es esencial realizar sobre los receptores de Clase I según la ITC-BT-43 e ITC-BT-05?",
                    opts = listOf(
                        "Medir el nivel de ruido acústico con sonómetro en vacío",
                        "Comprobar que el enchufe esté pintado de color azul",
                        "Pesar el aparato para detectar fugas de electrones",
                        "Comprobar la continuidad del conductor de protección hasta la masa metálica y la resistencia de aislamiento respecto a tierra"
                    ),
                    a = 3,
                    exp = "Para asegurar que un receptor de Clase I mantenga intacta su protección preventiva, es preceptivo ensayar periódicamente la continuidad eléctrica del circuito de puesta a tierra (resistencia muy baja entre clavija y carcasa) y verificar que la resistencia de aislamiento dieléctrico supere los valores reglamentarios (mínimo 0,5 MΩ a 500 V c.c.).",
                    ref = "ITC-BT-43"
                ),
Question(
                    q = "¿Qué exigencia rige para la sustitución de cables de alimentación deteriorados en receptores según la ITC-BT-43?",
                    opts = listOf(
                        "Pueden repararse uniendo los hilos con cinta aislante ordinaria sin desmontar la clavija",
                        "Se puede utilizar cualquier hilo telefónico si la corriente es inferior a 2 A",
                        "Deben sustituirse por cables de iguales o superiores características de aislamiento, tensión, sección y resistencia mecánica que el original",
                        "Solo puede cambiarse el cable si el fabricante emite un nuevo certificado de conformidad CE"
                    ),
                    a = 2,
                    exp = "La ITC-BT-43 prescribe que el mantenimiento y sustitución de cables de alimentación en receptores eléctricos debe preservar las características técnicas del equipo original (clase térmica, tensión 450/750 V o 300/500 V, sección y tipo de cubierta flexible), garantizando la estanqueidad y retención mecánica.",
                    ref = "ITC-BT-43"
                ),
Question(
                    q = "Según el REBT, la ITC-BT-44 se aplica a:",
                    opts = listOf(
                        "Las instalaciones de receptores para alumbrado (luminarias)",
                        "El alumbrado exterior público",
                        "El alumbrado de emergencia en locales de pública concurrencia",
                        "Las instalaciones de señalización"
                    ),
                    a = 0,
                    exp = "ITC-BT-44, apartado 1: «La presente instrucción se aplica a las instalaciones de receptores para alumbrado (luminarias).»",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "Según el REBT, la ITC-BT-44 no incluye prescripciones relativas a:",
                    opts = listOf(
                        "El alumbrado exterior y el alumbrado de emergencia en locales de pública concurrencia",
                        "Las luminarias interiores",
                        "Los rótulos luminosos",
                        "La utilización de muy bajas tensiones"
                    ),
                    a = 0,
                    exp = "ITC-BT-44, apartado 1: «En esta instrucción no se incluyen prescripciones relativas al alumbrado exterior recogido en la ITC-BT-09 ni al alumbrado de emergencia…»",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "Según el REBT, las luminarias deberán ser conformes a los requisitos establecidos en las normas de la serie:",
                    opts = listOf(
                        "UNE-EN 60598",
                        "UNE-EN 60309",
                        "La ITC-BT-24",
                        "UNE-EN 50107"
                    ),
                    a = 0,
                    exp = "ITC-BT-44, apartado 2.1: «Las luminarias serán conformes a los requisitos establecidos en las normas de la serie UNE-EN 60598.»",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "Según el REBT, la masa máxima de las luminarias suspendidas excepcionalmente de cables flexibles será de:",
                    opts = listOf(
                        "5 kg",
                        "3 kg",
                        "10 kg",
                        "15 kg"
                    ),
                    a = 0,
                    exp = "ITC-BT-44, apartado 2.1.1: «La masa de las luminarias suspendidas excepcionalmente de cables flexibles no deben exceder de 5 kg.»",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "Según el REBT, los conductores de luminarias suspendidas:",
                    opts = listOf(
                        "No deben presentar empalmes intermedios",
                        "Pueden presentar empalmes",
                        "Deben ser de aluminio",
                        "Deben ir siempre canalizados"
                    ),
                    a = 0,
                    exp = "ITC-BT-44, apartado 2.1.1: «Los conductores… no deben presentar empalmes intermedios…»",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "Según el REBT, la tracción máxima admisible en los conductores de suspensión será inferior a:",
                    opts = listOf(
                        "15 N/mm²",
                        "10 N/mm²",
                        "20 N/mm²",
                        "25 N/mm²"
                    ),
                    a = 0,
                    exp = "ITC-BT-44, apartado 2.1.1: «…la tracción máxima a la que estén sometidos los conductores sea inferior a 15 N/mm2.»",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "Según el REBT, la tensión asignada mínima de los cables del cableado interno será:",
                    opts = listOf(
                        "300/300 V",
                        "230/400 V",
                        "450/750 V",
                        "1000 V"
                    ),
                    a = 0,
                    exp = "ITC-BT-44, apartado 2.1.2: «…nunca inferior a 300/300 V.»",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "Según el REBT, el cableado externo que penetra en la luminaria deberá tener:",
                    opts = listOf(
                        "Aislamiento eléctrico y térmico adecuados",
                        "Únicamente aislamiento eléctrico",
                        "Protección mecánica únicamente",
                        "Pantalla metálica"
                    ),
                    a = 0,
                    exp = "ITC-BT-44, apartado 2.1.3: «…tenga el adecuado aislamiento eléctrico y térmico.»",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "Según el REBT, deberán disponer de conexión a tierra las luminarias que:",
                    opts = listOf(
                        "No sean de Clase II ni Clase III",
                        "Sean de Clase II",
                        "Sean de Clase III",
                        "Funcionen a muy baja tensión"
                    ),
                    a = 0,
                    exp = "ITC-BT-44, apartado 2.1.4: «Las partes metálicas accesibles de las luminarias que no sean de Clase II o Clase III, deberán tener un elemento de conexión para su puesta a tierra.»",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "¿Cuál es el peso o masa máxima que puede tener una luminaria para admitir su suspensión directa del cable flexible según la ITC-BT-44?",
                    opts = listOf(
                        "Hasta 25 kg",
                        "No más de 5 kg",
                        "Hasta 10 kg si el cable tiene malla de acero",
                        "No hay límite mientras el techo sea de hormigón"
                    ),
                    a = 1,
                    exp = "La ITC-BT-44 apartado 2.1.1 prescribe expresamente que, en los casos excepcionales en que una luminaria esté suspendida directamente de su cable de alimentación flexible, la masa de la misma no excederá de 5 kg.",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "Cuando una luminaria se suspende directamente de sus conductores flexibles, ¿cuál es el esfuerzo de tracción máximo admisible según la ITC-BT-44?",
                    opts = listOf(
                        "50 N/mm² de sección de cobre",
                        "100 N/mm² con abrazadera metálica",
                        "15 N/mm² (equivalente a 1,5 kg/mm²) de sección total de los conductores",
                        "5 N/mm² exclusivamente en cables unipolares"
                    ),
                    a = 2,
                    exp = "La ITC-BT-44 punto 2.1.1 establece que la tracción mecánica continua ejercida sobre los conductores de cobre en luminarias suspendidas no debe sobrepasar los 15 N/mm² (1,5 kg/mm²) para evitar la elongación y rotura de los filamentos.",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "Si una luminaria supera los 5 kg de masa, ¿cómo debe fijarse mecánicamente según la ITC-BT-44?",
                    opts = listOf(
                        "Pegada directamente con masilla acrílica al falso techo",
                        "Atornillada únicamente a una placa de yeso laminado de 10 mm",
                        "Colgada del conductor neutro reforzado con cinta aislante",
                        "Suspendida mediante un elemento mecánico independiente (cadena, varilla, gancho o cable de acero) anclado firmemente a la estructura"
                    ),
                    a = 3,
                    exp = "Conforme a la ITC-BT-44 apartado 2.1.1, las luminarias que pesen más de 5 kg no pueden suspenderse de sus cables eléctricos, requiriendo un elemento resistente mecánico independiente capaz de soportar con holgura su peso y fijado a elementos estructurales.",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "¿Qué tensión asignada mínima de aislamiento deben tener los cables utilizados en la alimentación de luminarias según la ITC-BT-44?",
                    opts = listOf(
                        "No inferior a 100/100 V",
                        "Nunca inferior a 300/300 V",
                        "Exactamente 1.000 V en corriente continua obligatoriamente",
                        "Basta con aislamiento barnizado simple"
                    ),
                    a = 1,
                    exp = "La ITC-BT-44 apartado 2.1.2 especifica que la tensión asignada de los conductores empleados en el conexionado y alimentación de luminarias nunca será inferior a 300/300 V, garantizando rigidez dieléctrica adecuada.",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "¿Qué símbolo marcado en una luminaria indica que es apta para montarse directamente sobre superficies normalmente inflamables según la ITC-BT-44?",
                    opts = listOf(
                        "Un círculo con la letra 'M' en su interior",
                        "Una cruz verde sobre fondo blanco",
                        "Una letra 'F' mayúscula dentro de un triángulo equilátero invertido",
                        "Una bombilla tachada con una línea diagonal"
                    ),
                    a = 2,
                    exp = "Según la ITC-BT-44 pto. 2.1.3 y las normas de la serie UNE-EN 60598, el símbolo de la letra F inscrita en un triángulo indica que la luminaria cumple los ensayos térmicos para su instalación directa sobre materiales normalmente inflamables (como madera).",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "En una instalación fija interior, ¿es obligatorio tender el conductor de protección (PE) a todos los puntos de luz según el REBT y la ITC-BT-44?",
                    opts = listOf(
                        "Solo si la vivienda tiene más de 200 metros cuadrados",
                        "Únicamente si la lámpara comprada por el usuario es de Clase I",
                        "No, en alumbrado nunca se instala conductor de tierra",
                        "Sí, es obligatorio llevar el conductor de protección a todos los puntos de luz, aunque la luminaria instalada sea de Clase II"
                    ),
                    a = 3,
                    exp = "Conforme a la ITC-BT-19, ITC-BT-25 y la ITC-BT-44 apartado 2.1.4, en toda instalación receptora debe tenderse el conductor de protección hasta cada punto de utilización de alumbrado para garantizar la seguridad si en el futuro se coloca una luminaria de Clase I.",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "¿Qué tipo de conductores deben emplearse en el cableado interno de luminarias donde se generen altas temperaturas según la ITC-BT-44?",
                    opts = listOf(
                        "Cables de polietileno ordinario de baja densidad",
                        "Conductores con aislamiento resistente a la temperatura (como silicona o fibra de vidrio) de clase térmica apropiada",
                        "Hilos telefónicos trenzados sin funda",
                        "Cables con cubierta de plomo y alquitrán"
                    ),
                    a = 1,
                    exp = "La ITC-BT-44 apartado 2.1.2 prescribe que cuando los conductores pasen cerca de lámparas o fuentes de calor interno en la luminaria, deben estar protegidos con aislamientos resistentes a temperaturas elevadas (elastómeros de silicona, trenzas de fibra de vidrio) para evitar el tostado del aislante.",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "¿Qué factor de potencia mínimo (cos phi) deben alcanzar las luminarias con lámparas de descarga según la ITC-BT-44?",
                    opts = listOf(
                        "Cos phi igual a 0,2 inductivo",
                        "No se exige compensación de energía reactiva",
                        "Cos phi corregido a un valor mínimo de 0,9",
                        "Factor de potencia obligatoriamente capacitivo de 0,5"
                    ),
                    a = 2,
                    exp = "De acuerdo con el REBT y la ITC-BT-44 punto 2.1.5, los receptores para alumbrado que utilicen lámparas de descarga o balastos electromagnéticos deben incorporar condensadores de compensación para elevar el factor de potencia a un mínimo de 0,9.",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "¿Qué indica el símbolo de una bombilla orientada hacia una superficie con una distancia (p. ej. '0,5 m') en una luminaria según la ITC-BT-44?",
                    opts = listOf(
                        "La longitud máxima del cable de alimentación",
                        "La altura máxima del techo donde puede colocarse",
                        "El radio de cobertura lumínica en metros cuadrados",
                        "La distancia mínima que debe mantenerse entre la luminaria y cualquier objeto o material combustible iluminado"
                    ),
                    a = 3,
                    exp = "El pictograma de una luminaria con indicación métrica frente a una superficie representa la distancia mínima de seguridad requerida para evitar que el haz de radiación térmica caliente excesivamente objetos combustibles o superficies iluminadas (ITC-BT-44 pto. 2.1.3).",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "En rótulos luminosos de descarga alimentados a alta tensión (más de 1 kV), ¿dónde debe situarse el interruptor de corte para bomberos según la ITC-BT-44?",
                    opts = listOf(
                        "Oculto en el falso techo del interior del local",
                        "En el exterior del edificio, en un lugar visible y fácilmente accesible, claramente rotulado 'CORTE BOMBEROS'",
                        "Dentro de la arqueta de acometida bajo la calzada",
                        "En el cuarto de contadores del edificio únicamente"
                    ),
                    a = 1,
                    exp = "La ITC-BT-44 apartado 2.2 y la norma UNE-EN 50107 exigen para rótulos luminosos de alta tensión (tubos de neón) un interruptor de seccionamiento de emergencia en la fachada exterior, accesible a los servicios de extinción de incendios y señalizado de forma inequívoca.",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "¿Qué protección específica deben incorporar los transformadores para rótulos de neón de alta tensión según la ITC-BT-44 y normas UNE?",
                    opts = listOf(
                        "Un filtro de absorción de agua",
                        "Un presostato hidráulico de aceite mineral",
                        "Dispositivo de protección contra circuito abierto en el secundario y protección contra defectos a tierra",
                        "Un interruptor diferencial de sensibilidad 3 A sin retardo"
                    ),
                    a = 2,
                    exp = "Conforme a la ITC-BT-44 y la norma UNE-EN 50107, los transformadores e convertidores de alta tensión para rótulos luminosos deben incorporar sistemas automáticos que corten la alimentación primaria si se abre el circuito de alta tensión o ante fugas de corriente a tierra.",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "En sistemas de alumbrado de muy baja tensión (MBTS) con conductores desnudos según la ITC-BT-44, ¿cuál es la tensión máxima en corriente alterna?",
                    opts = listOf(
                        "Hasta 230 V eficaces",
                        "Hasta 110 V eficaces",
                        "Hasta 48 V eficaces con aislamiento doble",
                        "No superior a 25 V eficaces en corriente alterna para conductores desnudos accesibles"
                    ),
                    a = 3,
                    exp = "Para sistemas de iluminación a muy baja tensión con cables o varillas desnudas suspendidas al alcance de las personas, la ITC-BT-44 y la norma UNE-EN 60598-2-23 limitan la tensión de servicio a un valor máximo de 25 V c.a. eficaz por motivos de seguridad frente a contactos directos.",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "¿Qué tipo de transformador es preceptivo para alimentar luminarias a muy baja tensión de seguridad (MBTS) según la ITC-BT-44?",
                    opts = listOf(
                        "Un autotransformador monofásico común sin separación galvánica",
                        "Un transformador de aislamiento de seguridad conforme a la norma UNE-EN 61558-2-6",
                        "Un reóstato regulable con cursor de carbón",
                        "Un transformador toroidal de intensidad 500/5 A"
                    ),
                    a = 1,
                    exp = "La ITC-BT-44 pto. 2.4 y la ITC-BT-36 exigen que la fuente de MBTS sea un transformador de seguridad certificado según la norma UNE-EN 61558-2-6 (o fuente electrónica equivalente SELV), garantizando un aislamiento doble o reforzado entre el primario y el secundario.",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "¿Cómo deben protegerse los cables que alimentan luminarias halógenas empotradas en falsos techos según la ITC-BT-44?",
                    opts = listOf(
                        "Envolviéndolos con papel de periódico",
                        "Sujetándolos directamente al cuerpo de la bombilla halógena",
                        "Manteniéndolos apartados del foco de calor y utilizando conductores resistentes a la temperatura y tubos no propagadores de la llama",
                        "Sumergiéndolos en un baño de resina inflamable"
                    ),
                    a = 2,
                    exp = "La ITC-BT-44 apartado 2.1.3 alerta del riesgo de incendio en focos empotrados, exigiendo que el cableado discurra por zonas ventiladas, alejado de las carcasas calientes y canalizado en tubos que cumplan el ensayo de no propagación de la llama.",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "¿Qué sección mínima en cobre debe tener la línea de alimentación de un circuito de alumbrado general en viviendas según el REBT (ITC-BT-44 e ITC-BT-25)?",
                    opts = listOf(
                        "0,5 mm²",
                        "0,75 mm²",
                        "1,0 mm²",
                        "1,5 mm² protegida por un interruptor magnetotérmico de 10 A"
                    ),
                    a = 3,
                    exp = "De acuerdo con la ITC-BT-25 y la ITC-BT-19 en consonancia con la ITC-BT-44, la sección mínima normalizada para circuitos interiores de alumbrado en viviendas (circuito C1) es de 1,5 mm² en cobre, con un calibre de protección magnetotérmica asignado de 10 A.",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "¿Por qué no se admiten empalmes intermedios en los conductores de suspensión de luminarias según la ITC-BT-44?",
                    opts = listOf(
                        "Porque encarecen el coste de la regleta",
                        "Porque los empalmes bajo tracción mecánica se aflojan, generan arcos eléctricos y suponen riesgo inminente de desprendimiento y cortocircuito",
                        "Porque reducen la luminosidad de la lámpara en más del 50%",
                        "Porque la normativa europea prohíbe el estaño en techos"
                    ),
                    a = 1,
                    exp = "La ITC-BT-44 par. 2.1.1 prohíbe taxativamente intercalar uniones o empalmes en el tramo de cable que soporta mecánicamente una luminaria colgada, ya que las vibraciones y el peso someterían a los bornes a fatiga mecánica y falso contacto con riesgo de ignición.",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "Cuando se instalan luminarias en locales con riesgo de incendio o explosión (polvo o gases), ¿qué marcado reglamentario deben ostentar según la ITC-BT-44 e ITC-BT-29?",
                    opts = listOf(
                        "Únicamente el marcado IP20",
                        "El distintivo de eficiencia energética clase A++",
                        "Marcado CE con certificación ATEX de grupo, categoría y clase de temperatura adecuada a la zona clasificada",
                        "Sello de calidad de la compañía suministradora"
                    ),
                    a = 2,
                    exp = "Conforme a la ITC-BT-44 y la ITC-BT-29, en atmósferas explosivas las luminarias deben ser antideflagrantes o de seguridad aumentada conformes a la Directiva ATEX, certificadas para no originar chispas ni superar la temperatura de ignición de las sustancias presentes.",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "¿Qué grado de protección IP mínimo debe tener una luminaria instalada en una zona donde reciba chorros de agua para su limpieza según ITC-BT-44 e ITC-BT-30?",
                    opts = listOf(
                        "IP20",
                        "IP31",
                        "IPX3",
                        "Al menos IPX5 (protección contra chorros de agua en cualquier dirección)"
                    ),
                    a = 3,
                    exp = "En locales donde la limpieza se realiza con manguera a presión (como obradores, mataderos o lavaderos industriales), la ITC-BT-44 y la ITC-BT-30 exigen luminarias con grado de estanqueidad no inferior a IPX5 contra chorros de agua.",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "¿Qué precaución debe tomarse con los condensadores de compensación de reactiva instalados en luminarias según la ITC-BT-44 e ITC-BT-48?",
                    opts = listOf(
                        "Deben descargarse manualmente con un destornillador antes de cada uso",
                        "Deben incorporar resistencia de descarga interna para evitar tensiones residuales peligrosas tras el corte de alimentación",
                        "Deben instalarse en el exterior del edificio al aire libre",
                        "No deben conectarse nunca en paralelo con la reactancia"
                    ),
                    a = 1,
                    exp = "Según la ITC-BT-44 y la ITC-BT-48, los condensadores utilizados para la corrección del factor de potencia de luminarias deben incorporar resistencias de descarga que reduzcan la tensión remanente a valores seguros (< 50 V) en menos de 1 minuto tras desconectar el interruptor.",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "¿Qué distancia mínima se exige entre las partes activas desnudas de dos conductores en un sistema de iluminación por cables tensados a MBTS según la ITC-BT-44?",
                    opts = listOf(
                        "Al menos 5 metros de separación",
                        "1 milímetro mediante espaciadores de cartón",
                        "Separación suficiente (típicamente no menor a 50 mm) mantenida mediante aisladores y tensores que impidan el contacto por oscilación",
                        "No se exige separación si los cables son de cobre pulido"
                    ),
                    a = 2,
                    exp = "La ITC-BT-44 y normas UNE de producto exigen que en tendidos de cables desnudos a muy baja tensión se mantenga una separación física constante mediante aisladores intermedios y tensores mecánicos, impidiendo que el balanceo o vibraciones produzcan cortocircuitos entre polos.",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "¿Qué elemento es OBLIGATORIO colocar en los puntos de entrada de cables a luminarias con envolvente metálica según la ITC-BT-44?",
                    opts = listOf(
                        "Relleno con yeso de fraguado rápido",
                        "Cinta adhesiva de papel para carrocero",
                        "Grasa grafitada conductora",
                        "Pasacables o boquillas de material aislante con bordes redondeados para no cortar el aislamiento del cable"
                    ),
                    a = 3,
                    exp = "La ITC-BT-44 apartado 2.1.2 determina que los orificios de entrada de cables en carcasas de luminarias metálicas deben estar provistos de boquillas o pasamuros elásticos de bordes lisos, impidiendo que las aristas de la chapa dañen el aislamiento por rozamiento o vibraciones.",
                    ref = "ITC-BT-44"
                ),
Question(
                    q = "Según el REBT, los esquemas de distribución se definen en función de:",
                    opts = listOf(
                        "Las conexiones a tierra de la red de distribución y de las masas de la instalación receptora",
                        "El tipo de protección diferencial utilizado",
                        "La sección del conductor neutro y de fase",
                        "La tensión nominal y el número de fases"
                    ),
                    a = 0,
                    exp = "ITC-BT-08, apartado 1 (Esquemas de distribución): indica que los esquemas de distribución se establecen en función de las conexiones a tierra de la red de distribución o de la alimentación, por un lado, y de las masas de la instalación receptora, por otro.",
                    ref = "ITC-BT-08"
                ),
Question(
                    q = "Según el REBT, la primera letra del código de un esquema de distribución indica:",
                    opts = listOf(
                        "La situación de la alimentación con respecto a tierra",
                        "La disposición del neutro en el cuadro general",
                        "La protección frente a contactos indirectos",
                        "La forma de conexión del conductor de protección"
                    ),
                    a = 0,
                    exp = "ITC-BT-08, apartado 1: bajo el epígrafe 'Primera letra', se indica que se refiere a la situación de la alimentación con respecto a tierra.",
                    ref = "ITC-BT-08"
                ),
Question(
                    q = "Según el REBT, la letra 'T' en la primera posición del código del esquema significa:",
                    opts = listOf(
                        "Conexión directa de un punto de la alimentación a tierra",
                        "Aislamiento de la alimentación respecto a tierra",
                        "Masas conectadas directamente al neutro",
                        "Neutro puesto a tierra a través de una impedancia"
                    ),
                    a = 0,
                    exp = "ITC-BT-08, apartado 1: en la definición de la primera letra, indica 'T = Conexión directa de un punto de la alimentación a tierra'.",
                    ref = "ITC-BT-08"
                ),
Question(
                    q = "Según el REBT, la letra 'I' en la primera posición del código indica:",
                    opts = listOf(
                        "Aislamiento de todas las partes activas de la alimentación respecto a tierra o conexión a tierra mediante impedancia",
                        "Masas conectadas directamente al punto neutro de la alimentación",
                        "Sistema interconectado de neutros y tierras",
                        "Instalación interior sin conductor de protección"
                    ),
                    a = 0,
                    exp = "ITC-BT-08, apartado 1: para la primera letra, 'I = Aislamiento de todas las partes activas de la alimentación con respecto a tierra o conexión de un punto a tierra a través de una impedancia'.",
                    ref = "ITC-BT-08"
                ),
Question(
                    q = "Según el REBT, la segunda letra del código de un esquema se refiere a:",
                    opts = listOf(
                        "La situación de las masas de la instalación receptora respecto a tierra",
                        "El tipo de aislamiento de los conductores",
                        "La protección frente a sobretensiones",
                        "El número de conductores activos del sistema"
                    ),
                    a = 0,
                    exp = "ITC-BT-08, apartado 1: bajo 'Segunda letra' se indica que se refiere a la situación de las masas de la instalación receptora con respecto a tierra.",
                    ref = "ITC-BT-08"
                ),
Question(
                    q = "Según el REBT, la letra 'N' en la segunda posición del código indica:",
                    opts = listOf(
                        "Masas conectadas directamente al punto de la alimentación puesto a tierra",
                        "Masas conectadas a tierra independiente del neutro",
                        "Masas conectadas mediante una impedancia de tierra",
                        "Masas sin conexión a tierra directa"
                    ),
                    a = 0,
                    exp = "ITC-BT-08, apartado 1, 'Segunda letra': 'N = Masas conectadas directamente al punto de la alimentación puesto a tierra (en corriente alterna, este punto es normalmente el punto neutro)'.",
                    ref = "ITC-BT-08"
                ),
Question(
                    q = "Según el REBT, la letra 'S' añadida al código significa:",
                    opts = listOf(
                        "Conductor de neutro y conductor de protección separados",
                        "Neutro y protección combinados en un solo conductor",
                        "Sistema con doble puesta a tierra",
                        "Instalación sin neutro accesible"
                    ),
                    a = 0,
                    exp = "ITC-BT-08, apartado 1, 'Otras letras': 'S = Las funciones de neutro y de protección, aseguradas por conductores separados'.",
                    ref = "ITC-BT-08"
                ),
Question(
                    q = "Según el REBT, la letra 'C' añadida al código significa:",
                    opts = listOf(
                        "Neutro y protección combinados en un solo conductor (conductor CPN)",
                        "Conductor de control común para fases y neutro",
                        "Sistema con neutro desconectable",
                        "Conexión a tierra con resistencia controlada"
                    ),
                    a = 0,
                    exp = "ITC-BT-08, apartado 1, 'Otras letras': 'C = Las funciones de neutro y de protección, combinadas en un solo conductor (conductor CPN)'.",
                    ref = "ITC-BT-08"
                ),
Question(
                    q = "Según el REBT, en el esquema TN las masas están conectadas:",
                    opts = listOf(
                        "Al punto de la alimentación puesto a tierra",
                        "A una toma de tierra independiente del neutro",
                        "A través de una impedancia de protección",
                        "A una tierra común con el sistema IT"
                    ),
                    a = 0,
                    exp = "ITC-BT-08, apartado 1.1 Esquema TN: los esquemas TN tienen un punto de la alimentación conectado directamente a tierra y 'las masas de la instalación receptora conectadas a dicho punto mediante conductores de protección'.",
                    ref = "ITC-BT-08"
                ),
Question(
                    q = "Según el REBT, en el esquema TN-C las funciones de neutro y protección:",
                    opts = listOf(
                        "Se combinan en un solo conductor denominado CPN",
                        "Se separan en toda la instalación",
                        "Se aíslan del neutro de la red",
                        "Se conectan mediante transformador de aislamiento"
                    ),
                    a = 0,
                    exp = "ITC-BT-08, apartado 1.1: define el esquema TN-C como aquel 'en el que las funciones de neutro y protección están combinados en un solo conductor en todo el esquema'; y en la definición de la letra C se indica que es el conductor CPN.",
                    ref = "ITC-BT-08"
                ),
Question(
                    q = "Según el REBT, en el esquema TN-S las funciones de neutro y protección:",
                    opts = listOf(
                        "Se aseguran mediante conductores separados",
                        "Se combinan en un único conductor hasta el receptor",
                        "No requieren conexión equipotencial",
                        "Se conectan a tierra a través de impedancia"
                    ),
                    a = 0,
                    exp = "ITC-BT-08, apartado 1.1: define el esquema TN-S como aquel 'en el que el conductor neutro y el de protección son distintos en todo el esquema'.",
                    ref = "ITC-BT-08"
                ),
Question(
                    q = "Según el REBT, en el esquema TT las masas de la instalación están:",
                    opts = listOf(
                        "Conectadas directamente a una toma de tierra independiente de la alimentación",
                        "Unidas al neutro de la red de distribución",
                        "Aisladas completamente de tierra",
                        "Conectadas al conductor de protección del generador"
                    ),
                    a = 0,
                    exp = "ITC-BT-08, apartado 1.2 Esquema TT: 'Las masas de la instalación receptora están conectadas a una toma de tierra separada de la toma de tierra de la alimentación'.",
                    ref = "ITC-BT-08"
                ),
Question(
                    q = "Según el REBT, en el esquema IT las masas están conectadas:",
                    opts = listOf(
                        "A una toma de tierra propia de la instalación receptora",
                        "Directamente al punto neutro de la red",
                        "A una impedancia común con el neutro",
                        "A una red equipotencial flotante"
                    ),
                    a = 0,
                    exp = "ITC-BT-08, apartado 1.3 Esquema IT: 'Las masas de la instalación receptora están puestas directamente a tierra'.",
                    ref = "ITC-BT-08"
                ),
Question(
                    q = "Según el REBT, en el esquema IT, respecto al neutro de la instalación:",
                    opts = listOf(
                        "Se recomienda no distribuir el neutro",
                        "Debe estar siempre conectado directamente a tierra",
                        "Debe estar siempre aislado de cualquier puesta a tierra",
                        "Debe conectarse a tierra mediante resistencia de 5 Ω"
                    ),
                    a = 0,
                    exp = "ITC-BT-08, apartado 1.3 Esquema IT: después de describir la limitación de la intensidad de defecto, se indica expresamente que 'En este tipo de esquema se recomienda no distribuir el neutro'.",
                    ref = "ITC-BT-08"
                ),
Question(
                    q = "Según el REBT, el esquema más utilizado para instalaciones receptoras alimentadas directamente desde redes de distribución pública de baja tensión es:",
                    opts = listOf(
                        "TN",
                        "TN-S",
                        "TT",
                        "IT"
                    ),
                    a = 2,
                    exp = "ITC-BT-08, apartado 1.4, letra a): se indica que las redes de distribución pública de baja tensión tienen el neutro puesto a tierra y que 'el esquema de distribución para instalaciones receptoras alimentadas directamente de una red de distribución pública de baja tensión es el esquema TT'.",
                    ref = "ITC-BT-08"
                ),
Question(
                    q = "Según el REBT, el esquema TT se aplica habitualmente en:",
                    opts = listOf(
                        "Redes de distribución públicas y suministros individuales",
                        "Sistemas trifásicos sin neutro",
                        "Instalaciones de potencia superior a 1 kV",
                        "Redes subterráneas con conductor neutro aislado"
                    ),
                    a = 0,
                    exp = "ITC-BT-08, apartado 1.4 a): al hablar de redes de distribución pública de baja tensión y de las instalaciones receptoras alimentadas directamente de ellas, se establece que el esquema de distribución es el TT; de ahí que se aplique en los suministros individuales conectados a estas redes.",
                    ref = "ITC-BT-08"
                ),
Question(
                    q = "Según el REBT, en el esquema TN la corriente de defecto regresa a la fuente a través de:",
                    opts = listOf(
                        "El conductor de protección o del neutro puesto a tierra",
                        "El terreno circundante",
                        "Una impedancia de fuga",
                        "Un transformador de aislamiento"
                    ),
                    a = 0,
                    exp = "ITC-BT-08, apartado 1.1: se indica que en los esquemas TN cualquier intensidad de defecto franco fase-masa es una intensidad de cortocircuito y que el bucle de defecto está constituido exclusivamente por elementos conductores metálicos. Esto implica que la corriente de defecto vuelve a la fuente por los conductores de protección y/o neutro puestos a tierra, sin pasar por el terreno.",
                    ref = "ITC-BT-08"
                ),
Question(
                    q = "Según el REBT, en el esquema IT la corriente de defecto tiene un valor:",
                    opts = listOf(
                        "Muy reducido, limitado por la alta impedancia de puesta a tierra del sistema",
                        "Elevado debido a la baja resistencia del neutro",
                        "Igual al de un cortocircuito entre fases",
                        "Variable según la carga conectada"
                    ),
                    a = 0,
                    exp = "ITC-BT-08, apartado 1.3: se establece que en el esquema IT la intensidad resultante de un primer defecto fase-masa o fase-tierra tiene un valor suficientemente reducido para no provocar tensiones de contacto peligrosas, y que dicha limitación se obtiene por ausencia de conexión a tierra o por la inserción de una impedancia suficiente entre la alimentación y tierra.",
                    ref = "ITC-BT-08"
                ),
Question(
                    q = "Según el REBT, para aplicar el esquema TN en redes de distribución la sección del conductor neutro debe ser:",
                    opts = listOf(
                        "Como mínimo la indicada en la tabla 1, en función de la sección de los conductores de fase",
                        "Siempre igual a la sección de fase",
                        "La mitad de la sección de los conductores de fase",
                        "Indiferente, ya que solo se usa como conductor de protección"
                    ),
                    a = 0,
                    exp = "ITC-BT-08, apartado 2, prescripción a): 'La sección del conductor neutro debe, en todo su recorrido, ser como mínimo igual a la indicada en la tabla siguiente, en función de la sección de los conductores de fase.'",
                    ref = "ITC-BT-08"
                ),
Question(
                    q = "Según el REBT, en el esquema TN la resistencia de tierra del neutro no será superior a:",
                    opts = listOf(
                        "5 Ω en la proximidad del centro y 2 Ω global",
                        "10 Ω en la proximidad del centro",
                        "2 Ω en todos los puntos de la red",
                        "1 Ω en cualquier punto de la instalación"
                    ),
                    a = 0,
                    exp = "ITC-BT-08, apartado 2, prescripciones d) y e): la resistencia de tierra del neutro no será superior a 5 Ω en las proximidades de la central generadora o del centro de transformación y en los 200 últimos metros de cualquier derivación, y la resistencia global de todas las tomas de tierra del neutro no será superior a 2 Ω.",
                    ref = "ITC-BT-08"
                ),
Question(
                    q = "Según el REBT, las instalaciones de alumbrado exterior se aplican a la iluminación de:",
                    opts = listOf(
                        "Autopistas, calles, plazas, parques y zonas análogas",
                        "Locales de pública concurrencia",
                        "Interiores de edificios industriales",
                        "Fachadas interiores de viviendas"
                    ),
                    a = 0,
                    exp = "ITC-BT-09, punto 1 (Campo de aplicación): se indica que esta instrucción se aplicará a instalaciones de alumbrado exterior destinadas a iluminar autopistas, carreteras, calles, plazas, parques, jardines, pasos elevados o subterráneos, caminos, etc.",
                    ref = "ITC-BT-09"
                ),
Question(
                    q = "Según el REBT, se incluyen también en el alumbrado exterior las instalaciones destinadas a:",
                    opts = listOf(
                        "Cabinas telefónicas, anuncios publicitarios y monumentos",
                        "Piscinas y fuentes ornamentales",
                        "Balizas autónomas",
                        "Semáforos de tráfico"
                    ),
                    a = 0,
                    exp = "ITC-BT-09, punto 1 (Campo de aplicación): se incluyen las instalaciones de alumbrado para cabinas telefónicas, anuncios publicitarios, mobiliario urbano en general, monumentos o similares y todos los receptores que se conecten a la red de alumbrado exterior.",
                    ref = "ITC-BT-09"
                ),
Question(
                    q = "Según el REBT, se excluyen del ámbito de aplicación de esta instrucción las instalaciones de:",
                    opts = listOf(
                        "Fuentes, piscinas, semáforos y balizas autónomas",
                        "Parques públicos y jardines",
                        "Pasos subterráneos y elevados",
                        "Calles y plazas"
                    ),
                    a = 0,
                    exp = "ITC-BT-09, punto 1 (Campo de aplicación): se excluyen la instalación para la iluminación de fuentes y piscinas y las de los semáforos y las balizas cuando sean completamente autónomos.",
                    ref = "ITC-BT-09"
                ),
Question(
                    q = "Según el REBT, la acometida de las instalaciones de alumbrado exterior podrá ser:",
                    opts = listOf(
                        "Subterránea o aérea con cables aislados",
                        "Solo aérea con cables desnudos",
                        "Por canalización empotrada exclusivamente",
                        "Mediante cable coaxial protegido"
                    ),
                    a = 0,
                    exp = "ITC-BT-09, punto 2 (Acometidas): se indica que la acometida podrá ser subterránea o aérea con cables aislados y se realizará según las prescripciones particulares de la compañía suministradora.",
                    ref = "ITC-BT-09"
                ),
Question(
                    q = "Según el REBT, la potencia aparente mínima en VA se considerará:",
                    opts = listOf(
                        "1,8 veces la potencia en vatios de las lámparas o tubos de descarga",
                        "Igual a la potencia en vatios de las lámparas",
                        "1,5 veces la potencia nominal de la instalación",
                        "El doble de la potencia reactiva de los equipos"
                    ),
                    a = 0,
                    exp = "ITC-BT-09, punto 3 (Dimensionamiento de las instalaciones): se establece que la potencia aparente mínima en VA se considerará 1,8 veces la potencia en vatios de las lámparas o tubos de descarga.",
                    ref = "ITC-BT-09"
                ),
Question(
                    q = "Según el REBT, el factor de potencia de cada punto de luz deberá corregirse hasta un valor:",
                    opts = listOf(
                        "Mayor o igual a 0,90",
                        "Igual a 0,80",
                        "No inferior a 0,85",
                        "Exactamente 1,00"
                    ),
                    a = 0,
                    exp = "ITC-BT-09, punto 3: se indica que el factor de potencia de cada punto de luz deberá corregirse hasta un valor mayor o igual a 0,90.",
                    ref = "ITC-BT-09"
                ),
Question(
                    q = "Según el REBT, la máxima caída de tensión entre el origen y cualquier punto de la instalación será menor o igual que:",
                    opts = listOf(
                        "1 %",
                        "2 %",
                        "3 %",
                        "5 %"
                    ),
                    a = 2,
                    exp = "ITC-BT-09, punto 3: se establece que la máxima caída de tensión entre el origen de la instalación y cualquier otro punto será menor o igual que el 3 %.",
                    ref = "ITC-BT-09"
                ),
Question(
                    q = "Según el REBT, con el fin de conseguir ahorros energéticos, las instalaciones se proyectarán:",
                    opts = listOf(
                        "Con distintos niveles de iluminación que decrezcan en horas de menor necesidad",
                        "Con iluminación constante durante toda la noche",
                        "Sin regulación de flujo luminoso",
                        "Solo con sistemas de encendido manual"
                    ),
                    a = 0,
                    exp = "ITC-BT-09, punto 3: se indica que, para conseguir ahorros energéticos, las instalaciones de alumbrado público se proyectarán con distintos niveles de iluminación, de forma que ésta decrezca durante las horas de menor necesidad.",
                    ref = "ITC-BT-09"
                ),
Question(
                    q = "Según el REBT, la intensidad máxima de defecto para los diferenciales en alumbrado exterior será de:",
                    opts = listOf(
                        "300 mA",
                        "30 mA",
                        "100 mA",
                        "10 A"
                    ),
                    a = 0,
                    exp = "ITC-BT-09, punto 4 (Cuadros de protección, medida y control): se establece que la intensidad de defecto, umbral de desconexión de los interruptores diferenciales, será como máximo de 300 mA.",
                    ref = "ITC-BT-09"
                ),
Question(
                    q = "Según el REBT, se admitirán diferenciales de hasta 500 mA o 1 A cuando la resistencia de tierra sea:",
                    opts = listOf(
                        "≤ 5 Ω y ≤ 1 Ω respectivamente",
                        "≤ 10 Ω y ≤ 2 Ω respectivamente",
                        "≤ 15 Ω y ≤ 3 Ω respectivamente",
                        "≤ 20 Ω y ≤ 5 Ω respectivamente"
                    ),
                    a = 0,
                    exp = "ITC-BT-09, punto 4: se admite el uso de interruptores diferenciales de 500 mA o 1 A siempre que la resistencia de puesta a tierra medida en la puesta en servicio sea ≤ 5 Ω y ≤ 1 Ω respectivamente.",
                    ref = "ITC-BT-09"
                ),
Question(
                    q = "Según el REBT, la envolvente del cuadro de protección y control tendrá un grado de protección mínimo:",
                    opts = listOf(
                        "IP55 e IK10",
                        "IP44 e IK08",
                        "IP54 e IK08",
                        "IP65 e IK10"
                    ),
                    a = 0,
                    exp = "ITC-BT-09, punto 4: se indica que la envolvente del cuadro proporcionará un grado de protección mínimo IP55 según UNE 20.324 e IK10 según UNE-EN 50.102.",
                    ref = "ITC-BT-09"
                ),
Question(
                    q = "Según el REBT, los cables de alimentación deberán tener conductores de:",
                    opts = listOf(
                        "Cobre y tensión asignada 0,6/1 kV",
                        "Aluminio y tensión 1,1 kV",
                        "Cobre y tensión 450/750 V",
                        "Aluminio y tensión 0,4/0,8 kV"
                    ),
                    a = 0,
                    exp = "ITC-BT-09, punto 5.1 (Cables): se establece que los cables serán multipolares o unipolares con conductores de cobre y tensión asignada de 0,6/1 kV.",
                    ref = "ITC-BT-09"
                ),
Question(
                    q = "Según el REBT, en redes subterráneas los tubos irán enterrados a una profundidad mínima de:",
                    opts = listOf(
                        "0,3 m",
                        "0,4 m",
                        "0,5 m",
                        "0,6 m"
                    ),
                    a = 1,
                    exp = "ITC-BT-09, punto 5.2.1 (Redes subterráneas): se indica que los tubos irán enterrados a una profundidad mínima de 0,4 m desde la cota inferior del tubo al nivel del suelo.",
                    ref = "ITC-BT-09"
                ),
Question(
                    q = "Según el REBT, la cinta de señalización se colocará a una distancia mínima del nivel del suelo de:",
                    opts = listOf(
                        "0,10 m",
                        "0,20 m",
                        "0,25 m",
                        "0,30 m"
                    ),
                    a = 0,
                    exp = "ITC-BT-09, punto 5.2.1: se establece que se colocará una cinta de señalización situada a una distancia mínima del nivel del suelo de 0,10 m y a 0,25 m por encima del tubo.",
                    ref = "ITC-BT-09"
                ),
Question(
                    q = "Según el REBT, la sección mínima de los conductores de alumbrado exterior subterráneos será de:",
                    opts = listOf(
                        "4 mm²",
                        "6 mm²",
                        "10 mm²",
                        "16 mm²"
                    ),
                    a = 1,
                    exp = "ITC-BT-09, punto 5.2.1: se fija que la sección mínima a emplear en los conductores de los cables, incluido el neutro, será de 6 mm².",
                    ref = "ITC-BT-09"
                ),
Question(
                    q = "Según el REBT, los soportes de las luminarias deben resistir las solicitaciones mecánicas con un coeficiente de seguridad no inferior a:",
                    opts = listOf(
                        "2",
                        "2,5",
                        "3",
                        "3,5"
                    ),
                    a = 1,
                    exp = "ITC-BT-09, punto 6.1 (Características de los soportes): se indica que se dimensionarán para resistir las solicitaciones mecánicas, particularmente la acción del viento, con un coeficiente de seguridad no inferior a 2,5.",
                    ref = "ITC-BT-09"
                ),
Question(
                    q = "Según el REBT, la puerta o trampilla de los soportes tendrá un grado de protección mínimo:",
                    opts = listOf(
                        "IP44 e IK10",
                        "IP55 e IK08",
                        "IP54 e IK08",
                        "IP65 e IK10"
                    ),
                    a = 0,
                    exp = "ITC-BT-09, punto 6.1: se establece que la puerta o trampilla tendrá un grado de protección IP44 según UNE 20.324 (EN 60529) e IK10 según UNE-EN 50.102.",
                    ref = "ITC-BT-09"
                ),
Question(
                    q = "Según el REBT, la suspensión de luminarias se hará mediante cables de acero con un coeficiente de seguridad no inferior a:",
                    opts = listOf(
                        "2,5",
                        "3",
                        "3,5",
                        "4"
                    ),
                    a = 2,
                    exp = "ITC-BT-09, punto 7.2 (Instalación eléctrica de luminarias suspendidas): se indica que la suspensión se hará mediante cables de acero protegidos contra la corrosión con coeficiente de seguridad no inferior a 3,5.",
                    ref = "ITC-BT-09"
                ),
Question(
                    q = "Según el REBT, los equipos eléctricos para montaje exterior poseerán un grado de protección mínimo:",
                    opts = listOf(
                        "IP54 e IK8",
                        "IP55 e IK10",
                        "IP44 e IK10",
                        "IP65 e IK8"
                    ),
                    a = 0,
                    exp = "ITC-BT-09, punto 8 (Equipos eléctricos de los puntos de luz): se señala que los equipos eléctricos para montaje exterior poseerán un grado de protección mínima IP54 según UNE 20.324 e IK 8 según UNE-EN 50.102.",
                    ref = "ITC-BT-09"
                ),
Question(
                    q = "Según el REBT, la puesta a tierra de los soportes se realizará por conexión a:",
                    opts = listOf(
                        "Una red de tierra común para todas las líneas que partan del mismo cuadro",
                        "Un electrodo independiente por cada soporte",
                        "La estructura metálica del alumbrado",
                        "El neutro de la instalación"
                    ),
                    a = 0,
                    exp = "ITC-BT-09, punto 10 (Puestas a tierra): se establece que la puesta a tierra de los soportes se realizará por conexión a una red de tierra común para todas las líneas que partan del mismo cuadro de protección, medida y control.",
                    ref = "ITC-BT-09"
                ),
Question(
                    q = "Según el REBT, la ITC-BT-28 se aplica a:",
                    opts = listOf(
                        "Locales de pública concurrencia",
                        "Únicamente a viviendas",
                        "Exclusivamente a locales industriales",
                        "Solo a locales administrativos"
                    ),
                    a = 0,
                    exp = "ITC-BT-28, apartado 1: «La presente instrucción se aplica a locales de pública concurrencia».",
                    ref = "ITC-BT-28"
                ),
Question(
                    q = "Según el REBT, la ocupación prevista de los locales se calculará como:",
                    opts = listOf(
                        "1 persona por cada 0,8 m2 de superficie útil",
                        "1 persona por cada 1 m2",
                        "1 persona por cada 0,5 m2",
                        "1 persona por cada 2 m2"
                    ),
                    a = 0,
                    exp = "ITC-BT-28, apartado 1: «La ocupación prevista de los locales se calculará como 1 persona por cada 0,8 m2 de superficie útil».",
                    ref = "ITC-BT-28"
                ),
Question(
                    q = "Según el REBT, esta instrucción tiene por objeto:",
                    opts = listOf(
                        "Garantizar la correcta instalación y funcionamiento de los servicios de seguridad",
                        "Regular únicamente el alumbrado normal",
                        "Definir la potencia máxima instalada",
                        "Establecer criterios de eficiencia energética"
                    ),
                    a = 0,
                    exp = "ITC-BT-28, apartado 1: «Esta instrucción tiene por objeto garantizar la correcta instalación y funcionamiento de los servicios de seguridad».",
                    ref = "ITC-BT-28"
                ),
Question(
                    q = "Según el REBT, la alimentación de los servicios de seguridad puede ser:",
                    opts = listOf(
                        "Automática o no automática",
                        "Solo automática",
                        "Solo manual",
                        "Exclusivamente autónoma"
                    ),
                    a = 0,
                    exp = "ITC-BT-28, apartado 2: «La alimentación para los servicios de seguridad […] puede ser automática o no automática».",
                    ref = "ITC-BT-28"
                ),
Question(
                    q = "Según el REBT, una alimentación automática sin corte es aquella que:",
                    opts = listOf(
                        "Puede estar asegurada de forma continua durante el periodo de transición",
                        "Está disponible en 0,5 segundos",
                        "Está disponible en 15 segundos",
                        "Está disponible en más de 15 segundos"
                    ),
                    a = 0,
                    exp = "ITC-BT-28, apartado 2: «Sin corte: alimentación automática que puede estar asegurada de forma continua […] durante el periodo de transición».",
                    ref = "ITC-BT-28"
                ),
Question(
                    q = "Según el REBT, una alimentación automática con corte muy breve debe estar disponible en:",
                    opts = listOf(
                        "0,15 segundos como máximo",
                        "0,5 segundos como máximo",
                        "15 segundos como máximo",
                        "Más de 15 segundos"
                    ),
                    a = 0,
                    exp = "ITC-BT-28, apartado 2: «Con corte muy breve: alimentación automática disponible en 0,15 segundos como máximo».",
                    ref = "ITC-BT-28"
                ),
Question(
                    q = "Según el REBT, en el esquema IT debe preverse:",
                    opts = listOf(
                        "Un controlador permanente de aislamiento",
                        "Un interruptor diferencial de 30 mA",
                        "Un transformador de separación",
                        "Un fusible calibrado"
                    ),
                    a = 0,
                    exp = "ITC-BT-28, apartado 2.1: «En el esquema IT debe preverse un controlador permanente de aislamiento».",
                    ref = "ITC-BT-28"
                ),
Question(
                    q = "Según el REBT, las fuentes de alimentación para servicios de seguridad pueden ser:",
                    opts = listOf(
                        "Baterías, generadores independientes o derivaciones separadas",
                        "Únicamente baterías",
                        "Solo grupos electrógenos",
                        "Exclusivamente la red pública"
                    ),
                    a = 0,
                    exp = "ITC-BT-28, apartado 2.1: «Se pueden utilizar las siguientes fuentes de alimentación: baterías de acumuladores, generadores independientes, derivaciones separadas de la red de distribución».",
                    ref = "ITC-BT-28"
                ),
Question(
                    q = "Según el REBT, una fuente propia de energía se pondrá en funcionamiento cuando:",
                    opts = listOf(
                        "La tensión descienda por debajo del 70% de su valor nominal",
                        "La tensión alcance el 90% del valor nominal",
                        "Exista sobrecarga",
                        "Se produzca un cortocircuito"
                    ),
                    a = 0,
                    exp = "ITC-BT-28, apartado 2.2: «cuando aquella tensión descienda por debajo del 70% de su valor nominal».",
                    ref = "ITC-BT-28"
                ),
Question(
                    q = "Según el REBT, todos los locales de pública concurrencia deberán disponer de:",
                    opts = listOf(
                        "Alumbrado de emergencia",
                        "Alumbrado de reemplazamiento",
                        "Suministro de reserva",
                        "Suministro de socorro"
                    ),
                    a = 0,
                    exp = "ITC-BT-28, apartado 2.3: «Todos los locales de pública concurrencia deberán disponer de alumbrado de emergencia».",
                    ref = "ITC-BT-28"
                ),
Question(
                    q = "Según el REBT, el alumbrado de emergencia tiene por objeto:",
                    opts = listOf(
                        "Asegurar la iluminación para una eventual evacuación",
                        "Sustituir al alumbrado normal",
                        "Aumentar la iluminancia habitual",
                        "Iluminar únicamente los accesos exteriores"
                    ),
                    a = 0,
                    exp = "ITC-BT-28, apartado 3: «asegurar, en caso de fallo […] la iluminación […] para una eventual evacuación del público».",
                    ref = "ITC-BT-28"
                ),
Question(
                    q = "Según el REBT, la alimentación del alumbrado de emergencia será:",
                    opts = listOf(
                        "Automática con corte breve",
                        "Manual",
                        "Automática sin corte",
                        "No automática"
                    ),
                    a = 0,
                    exp = "ITC-BT-28, apartado 3: «La alimentación del alumbrado de emergencia será automática con corte breve».",
                    ref = "ITC-BT-28"
                ),
Question(
                    q = "Según el REBT, el alumbrado de evacuación debe proporcionar una iluminancia mínima de:",
                    opts = listOf(
                        "1 lux a nivel del suelo",
                        "0,5 lux",
                        "5 lux",
                        "15 lux"
                    ),
                    a = 0,
                    exp = "ITC-BT-28, apartado 3.1.1: «una iluminancia horizontal mínima de 1 lux».",
                    ref = "ITC-BT-28"
                ),
Question(
                    q = "Según el REBT, en los cuadros de distribución del alumbrado la iluminancia mínima será de:",
                    opts = listOf(
                        "5 lux",
                        "1 lux",
                        "0,5 lux",
                        "15 lux"
                    ),
                    a = 0,
                    exp = "ITC-BT-28, apartado 3.1.1: «en los cuadros de distribución del alumbrado, la iluminancia mínima será de 5 lux».",
                    ref = "ITC-BT-28"
                ),
Question(
                    q = "Según el REBT, el alumbrado de evacuación deberá funcionar como mínimo durante:",
                    opts = listOf(
                        "Una hora",
                        "30 minutos",
                        "Dos horas",
                        "El tiempo que dure la evacuación"
                    ),
                    a = 0,
                    exp = "ITC-BT-28, apartado 3.1.1: «como mínimo durante una hora».",
                    ref = "ITC-BT-28"
                ),
Question(
                    q = "Según el REBT, el alumbrado ambiente o anti-pánico debe proporcionar una iluminancia mínima de:",
                    opts = listOf(
                        "0,5 lux",
                        "1 lux",
                        "5 lux",
                        "15 lux"
                    ),
                    a = 0,
                    exp = "ITC-BT-28, apartado 3.1.2: «una iluminancia horizontal mínima de 0,5 lux».",
                    ref = "ITC-BT-28"
                ),
Question(
                    q = "Según el REBT, el alumbrado de zonas de alto riesgo debe proporcionar una iluminancia mínima de:",
                    opts = listOf(
                        "15 lux o el 10% de la iluminancia normal",
                        "5 lux",
                        "1 lux",
                        "0,5 lux"
                    ),
                    a = 0,
                    exp = "ITC-BT-28, apartado 3.1.3: «una iluminancia mínima de 15 lux o el 10% de la iluminancia normal».",
                    ref = "ITC-BT-28"
                ),
Question(
                    q = "Según el REBT, es obligatorio situar alumbrado de seguridad en recintos con una ocupación mayor de:",
                    opts = listOf(
                        "100 personas",
                        "50 personas",
                        "300 personas",
                        "1.000 personas"
                    ),
                    a = 0,
                    exp = "ITC-BT-28, apartado 3.3.1.a: «en todos los recintos cuya ocupación sea mayor de 100 personas».",
                    ref = "ITC-BT-28"
                ),
Question(
                    q = "Según el REBT, cerca significa una distancia inferior a:",
                    opts = listOf(
                        "2 metros",
                        "1 metro",
                        "3 metros",
                        "5 metros"
                    ),
                    a = 0,
                    exp = "ITC-BT-28, apartado 3.3.1, nota (1): «Cerca significa a una distancia inferior a 2 metros».",
                    ref = "ITC-BT-28"
                ),
Question(
                    q = "Según el REBT, los aparatos autónomos para alumbrado de emergencia deben cumplir la norma:",
                    opts = listOf(
                        "UNE-EN 60.598-2-22",
                        "UNE 20.460-4-41",
                        "UNE 21.123",
                        "UNE-EN 50.200"
                    ),
                    a = 0,
                    exp = "ITC-BT-28, apartado 3.4.1: «deberán cumplir las normas UNE-EN 60.598-2-22».",
                    ref = "ITC-BT-28"
                ),
Question(
                    q = "En receptores de caldeo para usos domésticos, ¿cuál de los siguientes sistemas está terminantemente prohibido?",
                    opts = listOf(
                        "Aparatos provistos de elementos de caldeo desnudos sumergidos en agua",
                        "Termos eléctricos con resistencia envainada",
                        "Radiadores de aceite térmico sellados",
                        "Placas de cocción de inducción magnética"
                    ),
                    a = 0,
                    exp = "La ITC-BT-45 apartado 2.1 prohíbe expresamente en instalaciones domésticas el empleo de calentadores provistos de elementos de caldeo desnudos en contacto directo con agua o en los que el agua forme parte del circuito eléctrico.",
                    ref = "ITC-BT-45 §2.1"
                ),
Question(
                    q = "En ausencia de instrucciones del fabricante, ¿qué distancia mínima deben mantener los aparatos de calefacción a superficies de materiales combustibles?",
                    opts = listOf(
                        "4 cm",
                        "8 cm",
                        "12 cm",
                        "15 cm"
                    ),
                    a = 1,
                    exp = "Según la ITC-BT-45 par. 2.2, a falta de indicaciones expresas del fabricante en el manual técnico, los aparatos de calefacción deberán montarse manteniendo una separación mínima de 8 cm respecto a cualquier pared o superficie combustible.",
                    ref = "ITC-BT-45 §2.2"
                ),
Question(
                    q = "Para aparatos calefactores con elementos incandescentes luminosos detrás de aberturas o rejillas, ¿cuál es la distancia mínima a materiales combustibles?",
                    opts = listOf(
                        "25 cm",
                        "30 cm",
                        "50 cm",
                        "75 cm"
                    ),
                    a = 2,
                    exp = "La ITC-BT-45 par. 2.2 establece que cuando existan elementos calefactores incandescentes visibles o luminosos tras rejillas, la distancia frontal a cualquier elemento combustible será de al menos 50 cm para prevenir riesgos de ignición.",
                    ref = "ITC-BT-45 §2.2"
                ),
Question(
                    q = "¿Cómo debe realizarse la conexión a la red de las cocinas y hornos domésticos según la ITC-BT-45?",
                    opts = listOf(
                        "Mediante clavijas domésticas estándar de 10 A",
                        "Compartiendo la toma con el frigorífico",
                        "A través del circuito de alumbrado general",
                        "Mediante interruptores de corte omnipolar o tomas de corriente dedicados exclusivamente a ellos"
                    ),
                    a = 3,
                    exp = "La ITC-BT-45 par. 2.3 dispone que los aparatos de cocción y hornos estarán conectados a su circuito de alimentación mediante interruptores de corte omnipolar o bases de toma de corriente diseñadas y destinadas exclusivamente a ellos.",
                    ref = "ITC-BT-45 §2.3"
                ),
Question(
                    q = "¿Cuál es la tensión máxima en vacío en corriente alterna permitida para aparatos de soldadura por arco en locales no muy conductores?",
                    opts = listOf(
                        "90 V de valor eficaz",
                        "50 V de valor eficaz",
                        "120 V de valor eficaz",
                        "230 V de valor eficaz"
                    ),
                    a = 0,
                    exp = "Según la ITC-BT-45 par. 3.3 letra f, la tensión en vacío entre el electrodo y la pieza a soldar en soldadura eléctrica manual por arco no debe sobrepasar 90 V de valor eficaz en corriente alterna para locales ordinarios.",
                    ref = "ITC-BT-45 §3.3"
                ),
Question(
                    q = "En aparatos de soldadura por arco en corriente continua, ¿cuál es la tensión máxima en vacío reglamentaria?",
                    opts = listOf(
                        "100 V",
                        "150 V",
                        "200 V",
                        "75 V"
                    ),
                    a = 1,
                    exp = "Conforme a la ITC-BT-45 par. 3.3, la tensión en vacío admisible en corriente continua entre el porta-electrodos y la masa de la pieza a soldar no podrá ser superior a 150 V en condiciones normales de trabajo.",
                    ref = "ITC-BT-45 §3.3"
                ),
Question(
                    q = "¿A qué porcentaje máximo de la intensidad nominal de alimentación debe regularse el dispositivo de sobrecarga en un aparato de soldadura por arco?",
                    opts = listOf(
                        "125 %",
                        "150 %",
                        "200 %",
                        "250 %"
                    ),
                    a = 2,
                    exp = "La ITC-BT-45 par. 3.3 exige que cada equipo de soldadura por arco incorpore un dispositivo de protección contra sobrecargas regulado, como máximo, al 200 % de la intensidad nominal de su alimentación.",
                    ref = "ITC-BT-45 §3.3"
                ),
Question(
                    q = "¿Qué sección mínima debe tener el conductor de puesta a tierra de la cuba metálica en calentadores de agua industriales por electrodos sumergidos?",
                    opts = listOf(
                        "1,5 mm²",
                        "2,5 mm²",
                        "6 mm²",
                        "4 mm²"
                    ),
                    a = 3,
                    exp = "Según la ITC-BT-45 par. 3.1.1 letra c, la sección del conductor de puesta a tierra de la cuba metálica en calentadores industriales de electrodos no será inferior a 4 mm² de cobre, garantizando la evacuación segura de corrientes de fuga.",
                    ref = "ITC-BT-45 §3.1.1"
                ),
Question(
                    q = "En calentadores de agua industriales conectados a más de 440 V trifásicos por electrodos, ¿a qué valor de fuga a tierra debe actuar el corte automático?",
                    opts = listOf(
                        "Superior al 10 % de la corriente nominal (hasta 15 % por estabilidad)",
                        "Exactamente a 30 mA fijos",
                        "Superior a 300 mA",
                        "Al 50 % de la corriente de cortocircuito"
                    ),
                    a = 0,
                    exp = "La ITC-BT-45 par. 3.1.1 letra d especifica que el interruptor diferencial o relé de defecto debe desconectar el suministro cuando se registre una fuga a tierra superior al 10 % de la intensidad nominal, admitiéndose hasta el 15 % por razones de estabilidad.",
                    ref = "ITC-BT-45 §3.1.1"
                ),
Question(
                    q = "¿Qué esquema de distribución es obligatorio para la alimentación de hornos industriales que presenten corrientes de fuga importantes?",
                    opts = listOf(
                        "Esquema TT",
                        "Esquema TN-C",
                        "Esquema IT",
                        "Esquema TN-S con diferencial de 30 mA"
                    ),
                    a = 1,
                    exp = "La ITC-BT-45 par. 3.2 estipula de forma taxativa que cuando los hornos industriales presenten corrientes de fuga apreciables, como en los hornos de resistencias, deberán alimentarse obligatoriamente bajo el esquema TN-C.",
                    ref = "ITC-BT-45 §3.2"
                ),
Question(
                    q = "¿Por qué está prohibido instalar radiadores de calefacción o convectores directamente debajo de tomas de corriente según la ITC-BT-45?",
                    opts = listOf(
                        "Porque la corriente eléctrica se congela en los bornes por convección",
                        "Para evitar que la potencia del radiador sobrecargue el contador",
                        "Porque la corriente ascensional de aire caliente deteriora térmicamente las clavijas, los aislantes y los cables enchufados",
                        "Porque la normativa prohíbe tomas de corriente a menos de 2 metros del suelo"
                    ),
                    a = 2,
                    exp = "La ITC-BT-45 prohíbe situar bases de toma de corriente directamente encima de aparatos de calefacción o convectores debido a que la columna continua de aire caliente desprendida reseca y degrada el aislamiento termoplástico de las clavijas y conductores, originando cortocircuitos o incendios.",
                    ref = "ITC-BT-45"
                ),
Question(
                    q = "¿Qué doble dispositivo térmico debe incorporar obligatoriamente un termo acumulador de agua eléctrico según la ITC-BT-45 y normas de producto?",
                    opts = listOf(
                        "Dos termómetros de mercurio digitales",
                        "Un sensor barométrico y una sonda de ionización",
                        "Un interruptor horario y una resistencia blindada con electrodo de titanio",
                        "Un termostato de regulación funcional y un limitador térmico de seguridad de rearme manual independiente"
                    ),
                    a = 3,
                    exp = "Conforme a la ITC-BT-45 y normas UNE-EN de seguridad de termos eléctricos, es imperativo disponer de un termostato de regulación y, adicionalmente, un limitador de seguridad térmico que corte omnipolarmente la alimentación si el primero falla, debiendo rearmarse manualmente tras la intervención técnica.",
                    ref = "ITC-BT-45"
                ),
Question(
                    q = "¿Cuál es la tensión máxima en vacío en corriente alterna para soldadura por arco manual en locales especialmente conductores (p. ej. interior de calderas o cubas) según la ITC-BT-45?",
                    opts = listOf(
                        "48 V de valor eficaz (o inferior si se requiere MBTS)",
                        "90 V de valor eficaz",
                        "110 V de valor eficaz",
                        "230 V de valor eficaz"
                    ),
                    a = 0,
                    exp = "La ITC-BT-45 punto 3.3 determina que en locales o emplazamientos muy conductores (espacios confinados metálicos, calderas, galerías húmedas), la tensión en vacío entre el electrodo y la pieza a soldar no debe superar 48 V eficaces en c.a. para evitar riesgo mortal de fibrilación ante contactos accidentales.",
                    ref = "ITC-BT-45"
                ),
Question(
                    q = "¿Cómo debe conectarse el conductor de retorno de la corriente de soldadura (pinza de masa) según la ITC-BT-45?",
                    opts = listOf(
                        "A cualquier tubería de gas o calefacción del edificio",
                        "Directamente a la pieza que se va a soldar o lo más cerca posible de la zona de soldadura",
                        "Al borne de tierra del cuadro general de distribución más próximo",
                        "A la armadura de acero del hormigón del edificio"
                    ),
                    a = 1,
                    exp = "La ITC-BT-45 apartado 3.3 prescribe terminantemente que el retorno de masa del circuito de soldadura debe conectarse de forma directa y firme sobre la pieza que se suelda o sobre su banco de apoyo, prohibiendo usar tuberías, estructuras del edificio o conductores de protección (PE) como retorno, ya que provocaría incendios y electrocución por derivación.",
                    ref = "ITC-BT-45"
                ),
Question(
                    q = "¿Qué dispositivo de seguridad hidráulico es preceptivo instalar en los termos eléctricos acumuladores según la ITC-BT-45 e instalaciones de fontanería?",
                    opts = listOf(
                        "Un sifón de descarga libre sin desagüe",
                        "Una bomba centrífuga de recirculación continua",
                        "Una válvula de seguridad o grupo hidráulico de alivio de sobrepresión tarado a la presión de trabajo del termo",
                        "Un vaso de expansión abierto en el tejado obligatoriamente"
                    ),
                    a = 2,
                    exp = "La ITC-BT-45 par. 2.1 y la normativa técnica de receptores térmicos exigen que todo calentador de agua con depósito cerrado incorpore una válvula de seguridad hidráulica contra sobrepresiones que impida que la dilatación del agua al calentarse reviente la cuba.",
                    ref = "ITC-BT-45"
                ),
Question(
                    q = "En hornos industriales con resistencias calefactoras desnudas accesibles, ¿qué sistema de enclavamiento de seguridad exige la ITC-BT-45?",
                    opts = listOf(
                        "Un aviso sonoro de 90 decibelios que suene continuamente",
                        "Un cartel adhesivo fotoluminiscente en la puerta",
                        "Un termómetro de infrarrojos enfocado a la mirilla",
                        "Un dispositivo de corte automático omnipolar asociado a las puertas que corte la tensión de las resistencias al abrirlas"
                    ),
                    a = 3,
                    exp = "La ITC-BT-45 punto 3.2 estipula que en hornos industriales donde los elementos de caldeo permanezcan desnudos, las puertas de acceso deben disponer de un enclavamiento mecánico-eléctrico que desconecte automáticamente la alimentación eléctrica de las resistencias antes de que sea posible cualquier contacto fortuito con partes activas.",
                    ref = "ITC-BT-45"
                )
    )
}
