package com.example.data

object EspecialesQuestionsPart5 {
    val QUESTIONS = listOf(
Question(
                    q = "¿Qué protección contra la humedad se exige para un mueble que incorpore equipo eléctrico de Clase I destinado a una cocina comercial según la ITC-BT-49?",
                    opts = listOf(
                        "Un grado de estanqueidad mínimo IPX4 frente a goteos y salpicaduras",
                        "No se exige protección especial si la cocina es de vitrocerámica",
                        "Estar fabricado de madera de pino sin esmalte",
                        "Un interruptor con flotador que lo desconecte al fregar"
                    ),
                    a = 0,
                    exp = "En entornos húmedos de cocinas industriales, las masas metálicas de muebles Clase I albergan riesgos de contactos indirectos por condensación. La ITC-BT-49 exige el grado de estanqueidad mínimo de IPX4 adaptado al local mojado de cocina.",
                    ref = "ITC-BT-49"
                ),
Question(
                    q = "Cuando un mueble con instalación eléctrica incorpore dispositivos de conmutación o interruptores, ¿cuál debe ser su tensión nominal de aislamiento mínima según la ITC-BT-49?",
                    opts = listOf(
                        "24 V",
                        "Igual o superior a la tensión de red presente (mínimo 230/400 V) y adaptados a la intensidad de carga",
                        "De tipo telefónico analógico de baja señal",
                        "No se requiere aislamiento si el conmutador es de plástico"
                    ),
                    a = 1,
                    exp = "Los interruptores e interruptores de paso de muebles deben poseer una tensión de aislamiento equivalente al circuito de potencia que controlan (230 V nominales) para soportar de forma segura sobretensiones de maniobra sin degradación dieléctrica (ITC-BT-49 par. 2.1).",
                    ref = "ITC-BT-49"
                ),
Question(
                    q = "¿Qué símbolo rige en el marcado de un mueble que ha sido ensayado y certified como un receptor completo seguro según la directiva de baja tensión e ITC-BT-49?",
                    opts = listOf(
                        "Símbolo del rayo de alta tensión",
                        "Símbolo del grado de protección IP00",
                        "Marcado 'CE' impreso de forma indeleble junto a la placa de características",
                        "La silueta de una toma de corriente tachada"
                    ),
                    a = 2,
                    exp = "Cualquier mueble con equipos eléctricos comercializado conjuntamente debe ostentar el marcado de conformidad CE de la Unión Europea, acreditando el cumplimiento de las directivas europeas de seguridad eléctrica y compatibilidad electromagnética (ITC-BT-49).",
                    ref = "ITC-BT-49"
                ),
Question(
                    q = "En muebles de espejos de baño que incorporan tomas de corriente, ¿cuál es la zona mínima del baño de la ITC-BT-27 donde está totalmente prohibida su ubicación según la ITC-BT-49?",
                    opts = listOf(
                        "Fuera de las zonas 0, 1, 2 y 3",
                        "Únicamente en el volumen 3",
                        "En las zonas 2 y 3 indiferentemente",
                        "Dentro de los volúmenes de exclusión 0 y 1 de cuartos de baño"
                    ),
                    a = 3,
                    exp = "La ITC-BT-49 punto 3 prohíbe tajantemente la colocación de armarios, espejos o muebles con componentes eléctricos activos (enchufes, interruptores) en las zonas de prohibición y peligro 0 y 1 de baños para eliminar el riesgo mortal por contacto con agua.",
                    ref = "ITC-BT-49"
                ),
Question(
                    q = "¿Qué sección de cable flexible mínimo de cobre se requiere para conectar un mueble receptor a la instalación fija del local a través de una clavija monofásica?",
                    opts = listOf(
                        "Debe dimensionarse según la potencia de consumo del mueble, con sección de cobre no inferior a 1,5 mm²",
                        "Siempre de 0,5 mm² fijos",
                        "No inferior a 4,0 mm² de aluminio",
                        "Con hilos de timbre plano trenzado"
                    ),
                    a = 0,
                    exp = "El cable flexible de alimentación que une el enchufe de un mueble a la red del local debe resistir los esfuerzos mecánicos ordinarios, fijándose una sección mínima de cobre de 1,5 mm² para garantizar suficiente resistencia física y térmica (ITC-BT-49).",
                    ref = "ITC-BT-49"
                ),
Question(
                    q = "Cuando se instalan tiras de LED en un mueble empotrado (ej. vestidor), ¿qué tensión de alimentación se recomienda utilizar para garantizar protección pasiva según la ITC-BT-49 e ITC-BT-36?",
                    opts = listOf(
                        "230 V c.a. directo sin transformador",
                        "Muy Baja Tensión de Seguridad (MBTS) a 12 V c.c. o 24 V c.c. mediante transformador SELV conforme a UNE-EN 61558-2-6",
                        "400 V trifásicos rectificados",
                        "Muy Baja Tensión Funcional (MBTF) con el neutro conectado al armario"
                    ),
                    a = 1,
                    exp = "La alimentación a MBTS mediante transformadores de seguridad de aislamiento galvánico absoluto (UNE-EN 61558-2-6) proporciona máxima seguridad contra contactos directos en espacios estrechos como muebles de madera (ITC-BT-49 e ITC-BT-36).",
                    ref = "ITC-BT-49"
                ),
Question(
                    q = "¿Qué requisito deben cumplir las uniones o empalmes de conductores en las canalizaciones de muebles según la ITC-BT-49?",
                    opts = listOf(
                        "Deben realizarse mediante retorcido de hilos y cinta de embalar",
                        "Se prohíben las conexiones internas en el mueble",
                        "Deben efectuarse de forma segura mediante bornes o regletas de conexión alojadas dentro de cajas IP3X fijas",
                        "Pueden quedar al aire detrás del fondo del cajón móvil"
                    ),
                    a = 2,
                    exp = "La ITC-BT-49 par. 2.5 exige que todas las uniones de conductores eléctricos en mobiliario se alojen en cajas rígidas incombustibles con tapa a rosca o herramienta para impedir contactos fortuitos o que las chispas alcancen la madera combustible.",
                    ref = "ITC-BT-49"
                ),
Question(
                    q = "Si el mueble dispone de conductos para el cableado integrados de fábrica, ¿qué material de canalización se exige según la ITC-BT-49?",
                    opts = listOf(
                        "Material de cartón prensado no conductor",
                        "Conductos metálicos de chapa de cinc oxidable",
                        "Tubos de plástico corrugado inflamable",
                        "Conductores o tubos de PVC que cumplan la propiedad de no ser propagadores de la llama"
                    ),
                    a = 3,
                    exp = "El cableado integrado en el mobiliario debe discurrir por canales o tubos protectores que cumplan con la clasificación de autoextinguibles / no propagadores de llama para evitar que un sobrecalentamiento eléctrico propague un incendio al mueble (ITC-BT-49 par. 2.5).",
                    ref = "ITC-BT-49"
                ),
Question(
                    q = "Los conductores flexibles de Clase II que alimentan muebles móviles como mostradores o stands, ¿qué limitación rigen?",
                    opts = listOf(
                        "No deben cruzar zonas de tránsito peatonal directo sin una protección mecánica o rampa pasacables autorizada",
                        "Deben estar formados por hilos de cobre desnudo rígido",
                        "Deben fijarse rígidamente al suelo de hormigón con cola de contacto",
                        "No pueden medir más de 50 cm de longitud total"
                    ),
                    a = 0,
                    exp = "Los cables que alimentan de forma temporal mobiliario móvil en stands o locales comerciales están expuestos al aplastamiento por pisadas. La ITC-BT-49 y la ITC-BT-21 exigen protegerlos con pasacables mecánicos o rampas de goma para prevenir cortocircuitos e incendios.",
                    ref = "ITC-BT-49"
                ),
Question(
                    q = "Cuando la instalación eléctrica del mueble incorpora reactancias o fuentes de alimentación de alta temperatura, ¿dónde se prohíbe su montaje según la ITC-BT-49?",
                    opts = listOf(
                        "En el exterior del local exclusivamente",
                        "Directamente fijadas sobre superficies de materiales cuya inflamabilidad no sea compatible con la temperatura de la placa, sin interponer aislamiento incombustible",
                        "En zonas donde el aire circule a velocidad normal",
                        "Bajo pantallas metálicas puestas a tierra de Clase I"
                    ),
                    a = 1,
                    exp = "Las fuentes y balastos calientes no deben adherirse a maderas o plásticos combustibles si su temperatura supera el límite de seguridad de ignición del soporte. La ITC-BT-49 par. 2.1 exige pantallas calorífugas de fibrosilicato o metal distanciadas.",
                    ref = "ITC-BT-49"
                ),
Question(
                    q = "¿Qué longitud máxima de cable flexible de alimentación a enchufe de pared admite la ITC-BT-49 para un mueble de oficina ordinario?",
                    opts = listOf(
                        "10 metros",
                        "15 metros",
                        "La longitud máxima recomendada será de 3 metros, evitando bucles y enrollamientos excesivos",
                        "No existe límite superior para cables flexibles"
                    ),
                    a = 2,
                    exp = "Los cables de alimentación de muebles muy largos conllevan riesgos de caídas de tensión, tropiezos y recalentamientos por inducción si quedan enrollados. Se recomienda limitar el cable de conexión a un máximo de 3 metros (ITC-BT-49).",
                    ref = "ITC-BT-49"
                ),
Question(
                    q = "¿Cómo debe garantizarse la accesibilidad de las uniones frías en la pared para la conexión fija de un mueble según la ITC-BT-49?",
                    opts = listOf(
                        "Deberán cubrirse con yeso en la pared",
                        "Pueden dejarse flotando detrás de una placa de yeso sin caja",
                        "Debe ir en caja de Clase 0 sin tornillos",
                        "Deben alojarse en una caja de derivación empotrada que quede accesible para revisión o mantenimiento, sin necesidad de destruir partes del mueble"
                    ),
                    a = 3,
                    exp = "Toda conexión fija entre el cableado del local y la alimentación del mueble debe realizarse en caja de empalmes empotrada registrada y accesible para poder diagnosticar averías o realizar desconexiones seguras (ITC-BT-49 pto. 3).",
                    ref = "ITC-BT-49"
                ),
Question(
                    q = "¿Qué tipo de mecanismos o interruptores de encendido están totalmente prohibidos en el interior de armarios sin ventilación según la ITC-BT-49?",
                    opts = listOf(
                        "Interruptores de corte de chispa al aire, por peligro de ignición de fibras de ropa y aglomeración de polvo",
                        "Interruptores con envolvente de Clase II",
                        "Sondas PTC térmicas",
                        "Dispositivos que operen a MBTS"
                    ),
                    a = 0,
                    exp = "En armarios cerrados (recintos de acumulación de fibras textiles combustibles), el uso de interruptores ordinarios de chispa abierta es peligroso por la atmósfera inflamable latente. Se deben emplear interruptores estancos o microruptores blindados (ITC-BT-49).",
                    ref = "ITC-BT-49"
                ),
Question(
                    q = "Para un mueble con iluminación de Clase I, ¿qué conductor debe estar cableado junto a las fases y el neutro hasta cada chasis metálico según la ITC-BT-49?",
                    opts = listOf(
                        "Un cable coaxial de datos",
                        "Un hilo fusible de seguridad",
                        "Un conductor de fase secundario puenteado",
                        "Un conductor de protección (PE) con aislamiento verde-amarillo de sección mínima idéntica a las fases"
                    ),
                    a = 3,
                    exp = "Todo aparato de Clase I montado en mobiliario exige que sus partes metálicas accesibles estén sólidamente unidas al conductor de protección (PE) de la red para garantizar el disparo del diferencial de la instalación ante un fallo de fase a masa (ITC-BT-49 par. 2.1).",
                    ref = "ITC-BT-49"
                ),
Question(
                    q = "¿Cuál es el objeto y campo de aplicación de la ITC-BT-50 del REBT?",
                    opts = listOf(
                        "Determinar los requisitos de instalación de equipos eléctricos en locales que contienen radiadores para saunas",
                        "Regular la temperatura máxima de los baños turcos",
                        "Establecer la potencia de los calentadores de spa comunitarios",
                        "Normalizar la ventilación en piscinas climatizadas"
                    ),
                    a = 0,
                    exp = "La ITC-BT-50 punto 1 define expresamente su objeto como la determinación de los requisitos de instalación de los equipos eléctricos en aquellos locales que contienen radiadores o calentadores para saunas.",
                    ref = "ITC-BT-50 §1"
                ),
Question(
                    q = "¿A qué norma técnica remite la ITC-BT-50 para las prescripciones particulares de instalación en saunas?",
                    opts = listOf(
                        "UNE-EN 60598-2-22",
                        "UNE 20.460-7-703",
                        "UNE-EN 60335-2-41",
                        "UNE-HD 60364-4-41"
                    ),
                    a = 1,
                    exp = "La ITC-BT-50 punto 2 establece que las prescripciones particulares para los locales con radiadores de sauna son las contempladas en la norma UNE 20.460-7-703 (y su equivalente HD 60364-7-703).",
                    ref = "ITC-BT-50 §2"
                ),
Question(
                    q = "En el interior de la cabina de sauna (zonas 1, 2 y 3), ¿qué elemento está terminantemente prohibido instalar?",
                    opts = listOf(
                        "Sensores de temperatura de seguridad",
                        "Luminarias con aislamiento de silicona",
                        "Bases de toma de corriente y aparamenta de mando ajena al calentador",
                        "El propio calefactor de sauna"
                    ),
                    a = 2,
                    exp = "Conforme a la norma UNE 20.460-7-703 referenciada por la ITC-BT-50, no se permite la instalación de bases de toma de corriente ni interruptores generales de mando dentro del volumen de la sauna.",
                    ref = "ITC-BT-50 §2"
                ),
Question(
                    q = "¿Qué tipo de cable debe emplearse para la alimentación del radiador de sauna en zonas sometidas a alta temperatura?",
                    opts = listOf(
                        "Cable con aislamiento de PVC convencional (H07V-K)",
                        "Cable con aislamiento de polietileno reticulado",
                        "Cable plano bajo rodapié",
                        "Cable con aislamiento de silicona resistente al calor (tipo H05SS-F o equivalente)"
                    ),
                    a = 3,
                    exp = "La normativa de saunas exige conductores con aislamiento elastomérico de silicona resistentes a elevadas temperaturas (al menos 170 ºC, tipo H05SS-F) para prevenir la degradación del dieléctrico por calor.",
                    ref = "ITC-BT-50 §2"
                ),
Question(
                    q = "¿Qué grado de protección mínimo contra la penetración de agua deben poseer los equipos eléctricos situados en la cabina de sauna?",
                    opts = listOf(
                        "Al menos IPX4 (protegido contra salpicaduras de agua)",
                        "IPX0 ordinario",
                        "IPX7 sumergible",
                        "IP20 contra polvo"
                    ),
                    a = 0,
                    exp = "En las instalaciones de saunas, los equipos eléctricos y envolventes que puedan quedar expuestos al vapor o agua deben garantizar un grado de protección estanco no inferior a IPX4 (o IP24 según ubicación).",
                    ref = "ITC-BT-50 §2"
                ),
Question(
                    q = "¿Qué protección diferencial obligatoria debe preverse para los circuitos de alimentación de la cabina de sauna?",
                    opts = listOf(
                        "Diferencial industrial de 300 mA",
                        "Dispositivo diferencial-residual de alta sensibilidad no superior a 30 mA",
                        "Diferencial selectivo de 500 mA",
                        "No se requiere diferencial si hay limitador térmico"
                    ),
                    a = 1,
                    exp = "Para la protección contra contactos indirectos en saunas se exige que todos los circuitos estén protegidos por uno o varios dispositivos diferenciales de alta sensibilidad con corriente residual asignada no mayor de 30 mA.",
                    ref = "ITC-BT-50 §2"
                ),
Question(
                    q = "En la zona 1 de una sauna (espacio ocupado por el calentador hasta el techo), ¿qué equipos eléctricos se permiten?",
                    opts = listOf(
                        "Tomas de corriente estancas",
                        "Luminarias de lectura",
                        "Únicamente el calefactor de sauna y sus elementos de regulación directa",
                        "Cualquier receptor de clase II"
                    ),
                    a = 2,
                    exp = "En la zona 1 de la cabina sólo se permite instalar el propio calentador de sauna y los componentes directamente asociados a su funcionamiento y fijación, quedando excluido cualquier otro receptor.",
                    ref = "ITC-BT-50 §2"
                ),
Question(
                    q = "¿Qué dispositivo de seguridad debe incorporar el calefactor de sauna para evitar incendios por sobrecalentamiento?",
                    opts = listOf(
                        "Un interruptor horario de rearme automático",
                        "Un fusible unipolar en la fase L1",
                        "Un limitador térmico de seguridad con corte automático e independiente del termostato",
                        "Un voltímetro analógico"
                    ),
                    a = 2,
                    exp = "Los calefactores de sauna deben incorporar un limitador de temperatura de seguridad no autorrearmable que corte la alimentación eléctrica si se sobrepasa la temperatura máxima de seguridad admisible.",
                    ref = "ITC-BT-50 §2"
                ),
Question(
                    q = "En la zona 3 de la cabina de sauna (porción superior a más de 1 metro del suelo), ¿qué temperatura nominal mínima deben soportar los materiales aislantes?",
                    opts = listOf(
                        "Al menos 125 ºC a 170 ºC",
                        "60 ºC",
                        "70 ºC",
                        "90 ºC"
                    ),
                    a = 0,
                    exp = "En la zona 3 de la sauna, donde se acumula la masa de aire más caliente, los conductores y aislamientos deben estar clasificados para soportar temperaturas de servicio elevadas (125 ºC a 170 ºC).",
                    ref = "ITC-BT-50 §2"
                ),
Question(
                    q = "¿Dónde deben situarse preferentemente los órganos de accionamiento y mando del calentador de la sauna?",
                    opts = listOf(
                        "Bajo las bancadas de madera",
                        "Fuera de la cabina de sauna en un panel de control exterior",
                        "Junto al calefactor en el suelo",
                        "En el techo sobre el calefactor"
                    ),
                    a = 1,
                    exp = "Los cuadros de control, termostatos de maniobra manual y dispositivos de encendido deben colocarse fuera del recinto de la cabina de sauna para protegerlos del calor excesivo y facilitar su operación segura.",
                    ref = "ITC-BT-50 §2"
                ),
Question(
                    q = "¿En qué zona de la cabina de sauna está permitida la instalación de un sensor de temperatura con disyuntor por sobrecalentamiento según la ITC-BT-50 y la norma UNE 20.460-7-703?",
                    opts = listOf(
                        "Únicamente en la zona 1 junto al suelo",
                        "En cualquier zona indiferentemente",
                        "En la zona 4 (zona del techo) o la parte más elevada de la cabina donde se registra la temperatura límite",
                        "Está estrictamente prohibido instalar cualquier tipo de sensor dentro de la cabina"
                    ),
                    a = 2,
                    exp = "El sensor de temperatura de seguridad y control de sobrecalentamiento del radiador de sauna debe instalarse en la parte superior del techo o paredes altas (Zona 4) para medir de forma fidedigna la acumulación máxima de calor (ITC-BT-50 e instrucciones asociadas).",
                    ref = "ITC-BT-50"
                ),
Question(
                    q = "¿Qué especificación rige para los conductores eléctricos instalados detrás de los paneles de madera de la cabina de sauna según la ITC-BT-50?",
                    opts = listOf(
                        "No se exige resistencia térmica especial",
                        "Pueden ser hilos unipolar rígidos sin tubo protector",
                        "Pueden ir pegados con cola termoestable a las resistencias",
                        "Deben estar dotados de aislamiento de Clase Térmica H (mínimo resistente a 170 ºC) como silicona o colocarse con barrera incombustible"
                    ),
                    a = 3,
                    exp = "La ITC-BT-50 y la norma UNE de saunas establecen que las canalizaciones empotradas en los paneles o detrás del recubrimiento térmico de madera deben resistir temperaturas extremas debidas al traspaso térmico continuado de la cabina.",
                    ref = "ITC-BT-50"
                ),
Question(
                    q = "En el montaje del propio radiador de la sauna, ¿qué distancia mínima al suelo se prescribe para asegurar el tiro térmico según las directrices técnicas de la ITC-BT-50?",
                    opts = listOf(
                        "Debe respetarse rigurosamente la indicada por el fabricante en su marcado de seguridad, permitiendo la convección de aire frío inferior",
                        "Debe quedar pegado al suelo de hormigón sin soportes",
                        "Debe estar a una distancia fija de 1,5 metros",
                        "No importa la altura si se utiliza ventilador"
                    ),
                    a = 0,
                    exp = "Los calentadores de sauna calientan el aire por convección natural. La distancia de separación al suelo del chasis metálico debe seguir las especificaciones ensayadas por el fabricante para evitar incendios de la tarima o solera (ITC-BT-50).",
                    ref = "ITC-BT-50"
                ),
Question(
                    q = "¿A qué altura máxima del suelo de la cabina de sauna se sitúa el límite de la zona 2 donde se permite alojar conductores con aislamiento ordinario?",
                    opts = listOf(
                        "Hasta 50 cm del suelo",
                        "Hasta 1,0 metro sobre el nivel del suelo",
                        "Hasta 1,5 metros del suelo",
                        "Hasta el techo del local"
                    ),
                    a = 1,
                    exp = "De acuerdo con la norma de saunas referenciada por la ITC-BT-50, la zona 2 comprende la porción del local situada desde el suelo hasta 1,0 m de altura, donde la temperatura es inferior y no se imponen requisitos especiales de resistencia al calor extremo para el cableado terminal.",
                    ref = "ITC-BT-50"
                ),
Question(
                    q = "¿Qué grado de protección IP mínimo se exige formalmente para los aparatos calefactores de sauna instalados en recintos comunitarios o de uso público?",
                    opts = listOf(
                        "IP20 ordinario",
                        "IPX1 con rejilla gruesa",
                        "Al menos IPX4 (estanco contra salpicaduras de agua)",
                        "IP68 sumergible permanentemente"
                    ),
                    a = 2,
                    exp = "Los calefactores de sauna de uso público, donde es habitual proyectar agua sobre las piedras calientes para generar vapor (efecto loyly), deben poseer una estanqueidad mínima homologada IPX4 contra salpicaduras en cualquier dirección (ITC-BT-50).",
                    ref = "ITC-BT-50"
                ),
Question(
                    q = "¿Por qué no se permite instalar interruptores o interruptores de encendido locales dentro de la cabina de la sauna según la ITC-BT-50?",
                    opts = listOf(
                        "Porque se oxidarían los contactos por el aire seco",
                        "Porque la normativa exige usar únicamente mandos inalámbricos",
                        "Porque la luz de la sauna debe estar encendida permanentemente",
                        "Por el peligro de choque eléctrico al operarlos con el cuerpo mojado y con un coeficiente de resistencia cutánea muy disminuido por el sudor y calor"
                    ),
                    a = 3,
                    exp = "El ambiente de la sauna (calor extremo y humedad por sudor/vapor) anula casi por completo la resistencia de la piel humana. La aparamenta de mando debe ubicarse en el exterior para evitar contactos directos o fallos de aislamiento peligrosos (ITC-BT-50 e ITC-BT-24).",
                    ref = "ITC-BT-50"
                ),
Question(
                    q = "Para masas metálicas del calentador de sauna, ¿cómo se garantiza la equipotencialidad protectora según la ITC-BT-50?",
                    opts = listOf(
                        "Mediante una conexión fija y permanente al conductor de protección de tierra de la instalación",
                        "Pintando la cuba con pintura aislante acrílica",
                        "Dejándolas flotantes sin unión a tierra",
                        "Conectándolas al conductor neutro del local únicamente"
                    ),
                    a = 0,
                    exp = "Todo radiador de sauna de Clase I debe mantener una conexión directa al conductor de puesta a tierra general (PE) para derivar de forma instantánea cualquier fuga de corriente y provocar el disparo automático del diferencial (ITC-BT-50).",
                    ref = "ITC-BT-50"
                ),
Question(
                    q = "En la zona 3 de la cabina de sauna (por encima de 1,0 m de altura respecto al suelo), ¿qué requisitos térmicos deben cumplir las luminarias instaladas?",
                    opts = listOf(
                        "No se exige resistencia térmica especial",
                        "Deben soportar una temperatura ambiente de servicio de al menos 125 ºC y contar con grado IPX4",
                        "Deben ser de plástico blando con lámparas piloto",
                        "Deben alimentarse únicamente a alta tensión superior a 1 kV"
                    ),
                    a = 1,
                    exp = "En la porción alta de la sauna (Zona 3), las luminarias quedan expuestas al estrato térmico más elevado de la cabina, exigiéndose carcasas de vidrio de seguridad templado o metal aptas para trabajar a temperaturas superiores a 125 ºC (ITC-BT-50).",
                    ref = "ITC-BT-50"
                ),
Question(
                    q = "¿Cuál es la norma internacional equivalente a la norma española UNE 20.460-7-703 a la que remite la ITC-BT-50?",
                    opts = listOf(
                        "UNE-EN 60335-2-30",
                        "UNE-EN 60598-1",
                        "CENELEC HD 60364-7-703 (o IEC 60364-7-703)",
                        "UNE-EN 50178 de electrónica de potencia"
                    ),
                    a = 2,
                    exp = "La ITC-BT-50 del REBT armoniza las instalaciones eléctricas de baja tensión con los estándares europeos CENELEC de la serie HD 60364, correspondiendo la sección de saunas a la norma HD 60364-7-703.",
                    ref = "ITC-BT-50"
                ),
Question(
                    q = "En el interior de la cabina de sauna (zonas 1, 2 y 3), ¿qué elemento de canalización está prohibido instalar por razones de seguridad de quemaduras?",
                    opts = listOf(
                        "Mangueras flexibles con cubierta de silicona",
                        "Tubos empotrados bajo los paneles de madera",
                        "Conductores unipolar en conductos incombustibles",
                        "Canalizaciones con tubos o cubiertas metálicas accesibles que puedan calentarse por inducción o conducción, provocando quemaduras severas al contacto fortuito"
                    ),
                    a = 3,
                    exp = "Se prohíben canalizaciones de envolturas metálicas expuestas al alcance de los usuarios en saunas para evitar quemaduras por contacto y reducir la presencia de masas conductoras extrañas en zonas con peligro de choque eléctrico (ITC-BT-50).",
                    ref = "ITC-BT-50"
                ),
Question(
                    q = "Cuando la sauna incorpore iluminación LED decorativa en bancos (efecto cielo estrellado), ¿cómo se deben instalar las fuentes de alimentación?",
                    opts = listOf(
                        "Siempre en el exterior de la cabina de sauna, transportando únicamente la Muy Baja Tensión de Seguridad (MBTS) al interior",
                        "Bajo las bancadas de madera dentro de la sauna",
                        "Detrás del calefactor de sauna para ocultar cables",
                        "Envolviéndolas en aislamiento de lana de roca incombustible"
                    ),
                    a = 0,
                    exp = "Los transformadores y drivers electrónicos de tiras LED no soportan las temperaturas extremas de la sauna, debiendo colocarse fuera del volumen de calor para garantizar su vida útil y aislamiento de seguridad de Clase II (ITC-BT-50 e ITC-BT-36).",
                    ref = "ITC-BT-50"
                ),
Question(
                    q = "¿Qué sistema de protección activa contra sobreintensidades debe asociarse al circuito de fuerza del radiador de sauna según la ITC-BT-50?",
                    opts = listOf(
                        "Un interruptor magnetotérmico unipolar",
                        "Un cortocircuito fusible rápido de vidrio",
                        "Un interruptor magnetotérmico de corte omnipolar calibrado a la corriente nominal de carga del calefactor",
                        "Un relé de intensidad con rearme remoto"
                    ),
                    a = 2,
                    exp = "El circuito trifásico o monofásico de fuerza que alimenta las resistencias de caldeo de la sauna debe protegerse con un PIA omnipolar para interrumpir simultáneamente todas las fases ante sobrecargas o cortocircuitos (ITC-BT-47, ITC-BT-22 e ITC-BT-50).",
                    ref = "ITC-BT-50"
                ),
Question(
                    q = "En saunas domésticas instaladas en cuartos de baño, ¿qué relación existe entre las zonas de la sauna (UNE 20460-7-703) y los volúmenes del baño (ITC-BT-27)?",
                    opts = listOf(
                        "Son zonas idénticas que se anulan mutuamente",
                        "La sauna anula los volúmenes del baño automáticamente",
                        "Se puede instalar un enchufe de baño dentro de la sauna",
                        "Se deben aplicar simultáneamente y de forma rigurosa ambas instrucciones, quedando la cabina de sauna considerada local mojado/seco según fase de uso"
                    ),
                    a = 3,
                    exp = "Al coexistir ambas instalaciones en una vivienda, el cuarto de baño debe respetar las limitaciones de la ITC-BT-27, y la cabina de sauna internamente las exigencias de la ITC-BT-50 y norma UNE de saunas, de modo que la aparamenta del baño permanezca fuera de la cabina de calor.",
                    ref = "ITC-BT-50"
                ),
Question(
                    q = "¿Qué tipo de madera de revestimiento de sauna se recomienda para evitar la conductividad y el peligro de choque eléctrico estático en el chasis del calentador según la ITC-BT-50?",
                    opts = listOf(
                        "Maderas de baja conductividad térmica y nula secreción de resinas (como abeto nórdico o chopo trembilo)",
                        "Madera de pino resinosa tratada con sales de cobre",
                        "Madera aglomerada de alta densidad con resina de urea",
                        "Planchas de MDF prensado de pino"
                    ),
                    a = 0,
                    exp = "La madera interior de saunas debe carecer de resinas (que gotean hirviendo) y presentar una conductividad térmica extremadamente baja para que no queme la piel de los usuarios en contacto directo con bancos o paredes (ITC-BT-50).",
                    ref = "ITC-BT-50"
                ),
Question(
                    q = "Si el cableado de la sauna discurre en el exterior de la cabina (ej. pared adyacente), ¿qué aislamiento térmico debe preverse en el tabique?",
                    opts = listOf(
                        "No se requiere aislamiento si el cable es de PVC",
                        "Debe instalarse aislamiento térmico de conductividad extremadamente baja (como lana de roca o fibra mineral) para limitar el traspaso de temperatura a las canalizaciones",
                        "Pegar el tubo de PVC directo a la madera caliente",
                        "Instalar una placa de plomo de 10 mm"
                    ),
                    a = 1,
                    exp = "Las paredes de la cabina de sauna acumulan calor que puede transferirse a los tabiques colindantes, resecando y degradando el aislamiento de los cables de potencia ordinarios si no se interpone aislamiento térmico de fibra de roca (ITC-BT-50).",
                    ref = "ITC-BT-50"
                ),
Question(
                    q = "¿Qué tensión nominal máxima de alimentación se permite para los radiadores de saunas según las prescripciones de la ITC-BT-50?",
                    opts = listOf(
                        "Únicamente baja tensión continua de 12 V c.c.",
                        "Hasta 1 kV de valor eficaz",
                        "La tensión normalizada de distribución de baja tensión (monofásica de 230 V o trifásica de 400 V)",
                        "Alta tensión superior a 10 kV"
                    ),
                    a = 2,
                    exp = "Los calentadores industriales o domésticos de sauna se conectan a las redes estándar de distribución de energía de baja tensión (monofásica de 230 V c.a. o trifásica de 400 V c.a.) de forma fija y equilibrada (ITC-BT-50 e ITC-BT-08).",
                    ref = "ITC-BT-50"
                ),
Question(
                    q = "Cuando una luminaria se monta en la zona 2 de la cabina de sauna (por debajo de 1,0 m), ¿qué restricciones rigen según la ITC-BT-50?",
                    opts = listOf(
                        "Ninguna, se permite cualquier luminaria portátil",
                        "Deben ser luminarias de Clase 0 sin tierra",
                        "Se prohíbe el montaje de luminarias en la zona 2",
                        "Debe ser de instalación fija, con grado de protección IPX4 mínimo y con su masa metálica conectada a la red de tierra general"
                    ),
                    a = 3,
                    exp = "Aunque la Zona 2 tiene menores exigencias térmicas, cualquier luminaria empotrada o en pared debe poseer el grado de protección IPX4 contra goteos de vapor condensado y estar adecuadamente conectada al circuito de protección general si es Clase I (ITC-BT-50).",
                    ref = "ITC-BT-50"
                ),
Question(
                    q = "¿Qué tiempo máximo de desconexión automática exige la protección contra contactos indirectos en saunas con esquema TT (diferencial de 30 mA) según la ITC-BT-24 e ITC-BT-50?",
                    opts = listOf(
                        "5 segundos",
                        "Inmediato, no superior a 0,2 segundos (o 0,4 segundos a 230 V) para prevenir fibrilación ventricular bajo condiciones de humedad severas",
                        "1 minuto",
                        "No hay tiempo definido si la resistencia de tierra es de 15 ohmios"
                    ),
                    a = 1,
                    exp = "La extrema vulnerabilidad del cuerpo humano en saunas (sudor de sales de alta conductividad, vaso-dilatación cutánea masiva) exige tiempos de corte ultrarrápidos ante fugas de corriente para evitar paros cardíacos (ITC-BT-24, ITC-BT-18 e ITC-BT-50).",
                    ref = "ITC-BT-50"
                ),
Question(
                    q = "¿Qué propiedad deben cumplir los tubos o conductores protectores aislados que discurren por el interior del falso techo de madera de la cabina de sauna según la ITC-BT-50?",
                    opts = listOf(
                        "Pueden ser de cartón ligero",
                        "Deben estar abiertos por la base para ventilar",
                        "Se permite tubo corrugado inflamable ordinario de color amarillo",
                        "Deben ser obligatoriamente tubos rígidos o curvables de PVC no propagadores de la llama y aptos para soportar temperaturas de al menos 125 ºC"
                    ),
                    a = 3,
                    exp = "Las canalizaciones ubicadas en el falso techo (Zona 4 de máxima temperatura) deben estar construidas con de materiales autoextinguibles que no goteen inflamados y que resistan la degradación térmica continua (ITC-BT-50 e ITC-BT-21).",
                    ref = "ITC-BT-50"
                ),
Question(
                    q = "¿Qué limitación rige para el montaje de cajas de derivación eléctrica en el interior de una sauna según la ITC-BT-50?",
                    opts = listOf(
                        "No deben instalarse cajas de conexión en el interior de la cabina de sauna (deben quedar fuera)",
                        "Deben instalarse en la zona 1 bajo el calefactor únicamente",
                        "Solo se admiten cajas de plástico de Clase 0 sin tornillos",
                        "Deben empotrarse en el suelo debajo de las baldosas"
                    ),
                    a = 0,
                    exp = "Para evitar fallos dieléctricos masivos inducidos por las condiciones severas de humedad por vapor y elevadas temperaturas, las cajas de empalme o derivación de conductores se deben colocar en el exterior de la cabina de sauna (ITC-BT-50).",
                    ref = "ITC-BT-50"
                ),
Question(
                    q = "¿Cuál es el campo de aplicación principal de la instrucción técnica ITC-BT-51 del REBT?",
                    opts = listOf(
                        "Instalaciones de sistemas de automatización, gestión técnica de la energía y seguridad para viviendas y edificios",
                        "Redes de distribución de telecomunicaciones por fibra óptica",
                        "Sistemas de iluminación exterior con temporizador mecánico",
                        "Circuitos de potencia de tracción de ascensores"
                    ),
                    a = 0,
                    exp = "La ITC-BT-51 apartado 1 define su ámbito para las instalaciones de sistemas de automatización, gestión técnica de la energía y seguridad (sistemas domóticos e inmóticos) en viviendas y edificios.",
                    ref = "ITC-BT-51 §1"
                ),
Question(
                    q = "Según su topología física y funcional, ¿cómo se clasifican las arquitecturas de los sistemas de automatización en la ITC-BT-51?",
                    opts = listOf(
                        "Sistemas analógicos, digitales y mixtos",
                        "Sistemas centralizados, descentralizados y distribuidos",
                        "Sistemas trifásicos, monofásicos y bipolares",
                        "Sistemas alámbricos de cobre y ópticos"
                    ),
                    a = 1,
                    exp = "La ITC-BT-51 apartado 2 clasifica las instalaciones domóticas según su estructura funcional en tres tipologías básicas: sistemas centralizados, sistemas descentralizados y sistemas distribuidos.",
                    ref = "ITC-BT-51 §2"
                ),
Question(
                    q = "¿Qué niveles de tensión pueden utilizar los circuitos de control y bus de los sistemas de automatización según la ITC-BT-51?",
                    opts = listOf(
                        "Únicamente tensión continua de 12 V",
                        "Exclusivamente alta tensión de 1.000 V",
                        "Muy Baja Tensión de Seguridad (MBTS), Muy Baja Tensión de Protección (MBTP) o 230 V",
                        "Solo tensiones inferiores a 5 V TTL"
                    ),
                    a = 2,
                    exp = "La ITC-BT-51 apartado 3 admite circuitos de bus y control que operen a Muy Baja Tensión de Seguridad (MBTS), Muy Baja Tensión de Protección (MBTP) o a tensión de red (230 V), según las especificaciones del fabricante.",
                    ref = "ITC-BT-51 §3"
                ),
Question(
                    q = "Para que los cables de un bus domótico coexistan con cables de energía de 230/400 V en el mismo tubo o canal, ¿qué condición se exige?",
                    opts = listOf(
                        "Que los cables de energía sean de aluminio",
                        "Que la longitud del tendido sea menor de 5 metros",
                        "Que el circuito domótico sea alimentado con corriente continua",
                        "Que los cables del bus domótico dispongan de aislamiento para la tensión más elevada presente (mínimo 450/750 V) o separación física"
                    ),
                    a = 3,
                    exp = "Según la ITC-BT-51 par. 4.1 e ITC-BT-20, los cables de señales o bus pueden compartir canalización con cables de energía de BT solo si están aislados para la tensión más alta presente o si existe un tabique separador continuo.",
                    ref = "ITC-BT-51 §4.1"
                ),
Question(
                    q = "¿Qué grado de protección mínimo deben proporcionar las envolventes o cuadros de distribución donde se ubiquen actuadores y pasarelas domóticas?",
                    opts = listOf(
                        "Mínimo IP30 e IK07 en el interior de viviendas",
                        "IP20 e IK02 sin requisitos mecánicos",
                        "IP55 e IK10 obligatorio siempre",
                        "IP65 estanco para cualquier estancia"
                    ),
                    a = 0,
                    exp = "La ITC-BT-51 par. 4.2 remite a las condiciones generales de cuadros (ITC-BT-17), exigiendo para los módulos de control domótico en interior un grado de protección no inferior a IP30 contra sólidos e IK07 contra impactos.",
                    ref = "ITC-BT-51 §4.2"
                ),
Question(
                    q = "¿Qué normativa deben cumplir los equipos y dispositivos de automatización respecto a la compatibilidad electromagnética (CEM)?",
                    opts = listOf(
                        "Solo normas ISO de calidad",
                        "Normas armonizadas de la serie UNE-EN 50090 y directivas de compatibilidad electromagnética",
                        "Ninguna si funcionan a baterías",
                        "El código técnico de edificación exclusivamente"
                    ),
                    a = 1,
                    exp = "La ITC-BT-51 par. 5 exige que los equipos cumplan las directivas europeas de CEM y las normas de la serie UNE-EN 50090 para garantizar inmunidad frente a perturbaciones y no inducir ruidos en la red eléctrica.",
                    ref = "ITC-BT-51 §5"
                ),
Question(
                    q = "En sistemas domóticos cuya fuente de alimentación entrega Muy Baja Tensión de Seguridad (MBTS), ¿qué condición rige para las partes activas?",
                    opts = listOf(
                        "Deben conectarse sólidamente al conductor neutro",
                        "Deben conectarse a la red de tierra del edificio",
                        "No deben estar conectadas a tierra en ningún punto",
                        "Deben unirse al chasis metálico del cuadro"
                    ),
                    a = 2,
                    exp = "En conformidad con la ITC-BT-36 e ITC-BT-51 par. 3.1, las partes activas y masas de los circuitos alimentados mediante MBTS no deben conectarse a tierra para mantener el aislamiento galvánico de seguridad.",
                    ref = "ITC-BT-51 §3.1"
                ),
Question(
                    q = "Al implementar sistemas automáticos de desconexión o gestión de cargas, ¿qué circuitos no deben desconectarse de forma involuntaria?",
                    opts = listOf(
                        "El circuito C2 de tomas de uso general",
                        "El circuito C4 de lavadora y termo",
                        "El circuito C9 de aire acondicionado",
                        "Los circuitos de servicios de seguridad, detección de incendios o alarmas técnicas"
                    ),
                    a = 3,
                    exp = "La ITC-BT-51 par. 6 prohíbe que los sistemas de gestión de demanda o racionalización energética desconecten circuitos prioritarios o destinados a servicios de seguridad y protección de las personas.",
                    ref = "ITC-BT-51 §6"
                ),
Question(
                    q = "En viviendas, ¿a qué circuito interno normalizado de la ITC-BT-25 corresponde la alimentación de los sistemas de automatización?",
                    opts = listOf(
                        "Circuito C11",
                        "Circuito C1",
                        "Circuito C3",
                        "Circuito C8"
                    ),
                    a = 0,
                    exp = "La ITC-BT-25 par. 2.3.2 y la ITC-BT-51 indican que la alimentación de la central y actuadores de domótica se realiza mediante el circuito C11 específico para automatización, con potencia prevista de hasta 2.300 W.",
                    ref = "ITC-BT-51 §3"
                ),
Question(
                    q = "Cuando se utilicen cables apantallados para el bus domótico frente a interferencias, ¿cómo debe conectarse la pantalla metálica?",
                    opts = listOf(
                        "Debe dejarse aislada en ambos extremos",
                        "Debe conectarse a tierra en un único punto para evitar bucles de corriente",
                        "Debe conectarse al conductor neutro",
                        "Debe unirse a las fases mediante varistores"
                    ),
                    a = 1,
                    exp = "Para evitar corrientes inducidas por bucles de masa, la pantalla metálica de los cables de datos domóticos se conectará a tierra en un solo punto, preferentemente en el cuadro principal de mando.",
                    ref = "ITC-BT-51 §4.1"
                ),
Question(
                    q = "¿Qué tipo de topología domótica se caracteriza por que todos los sensores y actuadores envían sus tramas de datos de forma directa y autónoma por el bus sin depender de una CPU central según la ITC-BT-51?",
                    opts = listOf(
                        "Arquitectura centralizada de estrella",
                        "Arquitectura analógica de relés",
                        "Arquitectura distribuida (como el bus de campo KNX)",
                        "Arquitectura multiplexada pasiva"
                    ),
                    a = 2,
                    exp = "En la arquitectura distribuida descrita en la ITC-BT-51 apartado 2, cada nodo del bus incorpora su propia inteligencia (microprocesador), comunicándose de manera directa con otros elementos, lo que aumenta drásticamente la robustez del sistema frente a fallos individuales de hardware.",
                    ref = "ITC-BT-51"
                ),
Question(
                    q = "Cuando se utilizan cables de bus para control domótico que funcionan a Muy Baja Tensión de Seguridad (MBTS), ¿qué aislamiento mínimo se exige si discurren por tubos diferentes del cableado de potencia según la ITC-BT-51?",
                    opts = listOf(
                        "No se requiere aislamiento si el voltaje es menor de 12 V",
                        "Aislamiento de silicona para alta tensión",
                        "Pantalla metálica de plomo de 2 mm de espesor",
                        "Aislamiento adecuado para soportar las tensiones nominales de la red del bus (típicamente 300 V o 500 V nominales)"
                    ),
                    a = 3,
                    exp = "La ITC-BT-51 apartado 4.1 dispone que para los buses domóticos que operan a MBTS por canalizaciones exclusivas e independientes, los cables deben poseer al menos aislamiento de fábrica nominal de 300/300 V para asegurar una adecuada resistencia mecánica y aislamiento base.",
                    ref = "ITC-BT-51"
                ),
Question(
                    q = "En una instalación domótica bajo arquitectura centralizada, ¿qué riesgo principal identifica la ITC-BT-51 e ingeniería de control ante un fallo de la central?",
                    opts = listOf(
                        "Un fallo único de la CPU central provoca la caída o inoperatividad total de todo el sistema domótico",
                        "El consumo eléctrico se eleva al doble",
                        "Los cables de bus sufren sobrecalentamiento instantáneo",
                        "Se produce una inversión de fases en la acometida trifásica"
                    ),
                    a = 0,
                    exp = "La arquitectura centralizada posee un único punto crítico de fallo (Single Point of Failure). Si la unidad central se avería, todos los actuadores y funciones automatizadas asociadas a ella quedan fuera de servicio, comprometiendo la disponibilidad del sistema (ITC-BT-51 par. 2).",
                    ref = "ITC-BT-51"
                ),
Question(
                    q = "Para canalizaciones empotradas de bus domótico en viviendas, ¿cuál es el diámetro exterior mínimo del tubo corrugado de PVC según la ITC-BT-51 e ITC-BT-21?",
                    opts = listOf(
                        "16 mm",
                        "20 mm",
                        "25 mm",
                        "32 mm"
                    ),
                    a = 1,
                    exp = "La ITC-BT-21 y la ITC-BT-51 recomiendan para canalizaciones de señales y buses domóticos un diámetro nominal de tubo corrugado de al menos 20 mm, facilitando el tendido y futuras ampliaciones de conductores sin dañar los hilos.",
                    ref = "ITC-BT-51"
                ),
Question(
                    q = "Al alimentar las fuentes de bus o controladores domóticos en el cuadro general, ¿qué protección magnetotérmica individual se prescribe según la ITC-BT-51 e ITC-BT-25?",
                    opts = listOf(
                        "Un interruptor automático de 32 A con sección de 6 mm²",
                        "Un disyuntor de 2 A unipolar sin neutro",
                        "Un interruptor automático magnetotérmico (PIA) de calibre máximo 16 A asociado a conductores de 2,5 mm² de cobre (circuito C11)",
                        "No se exige protección magnetotérmica si la fuente es de Clase II"
                    ),
                    a = 2,
                    exp = "El circuito C11 de la vivienda, destinado a la alimentación fija de las centrales domóticas y equipos de control, cuenta reglamentariamente con conductores de cobre de sección mínima de 2,5 mm² y una protección mediante PIA de 16 A (ITC-BT-25 e ITC-BT-51).",
                    ref = "ITC-BT-51"
                ),
Question(
                    q = "Según la ITC-BT-51, ¿qué condición se exige a los actuadores automáticos domóticos que regulan persianas metálicas enrollables u otros motores?",
                    opts = listOf(
                        "Deben activarse simultáneamente todos los motores en paralelo",
                        "No deben requerir puesta a tierra en ningún caso",
                        "Deben controlarse únicamente de noche",
                        "Deben incorporar enclavamientos de seguridad mecánicos o lógicos que impidan accionar al mismo tiempo la subida y bajada del motor para evitar cortocircuitos"
                    ),
                    a = 3,
                    exp = "Para la protección de motores de accionamiento eléctrico de persianas o toldos, los actuadores de salida de relé domótico deben poseer enclavamiento de seguridad cruzado (mecánico en relés o por software) para evitar cortocircuitos entre bobinados de giro inverso (ITC-BT-51 e ITC-BT-47).",
                    ref = "ITC-BT-51"
                ),
Question(
                    q = "En sistemas domóticos que transmiten datos por corrientes portadoras (buses PLC sobre cables de 230 V de energía), ¿qué elemento de seguridad exige la ITC-BT-51?",
                    opts = listOf(
                        "Filtros de red de compatibilidad electromagnética que eviten la propagación de datos y ruido fuera de los límites de la vivienda",
                        "La puesta a tierra de todos los conductores activos de fase",
                        "Alimentar la red de fuerza mediante un alternador",
                        "La prohibición absoluta de conectar lavadoras en esa fase"
                    ),
                    a = 0,
                    exp = "El bus de corrientes portadoras (Power Line Communication) utiliza el cableado de energía de red de la vivienda como canal físico de transmisión de RF. Para evitar la interferencia o control no autorizado desde viviendas contiguas conectadas a la misma derivación individual, se instalan filtros de bloqueo de RF en la cabecera (ITC-BT-51).",
                    ref = "ITC-BT-51"
                ),
Question(
                    q = "Si la centralización domótica se dispone dentro de una envolvente o armario metálico, ¿cómo se debe actuar según la ITC-BT-51?",
                    opts = listOf(
                        "Dejar el armario flotante sin ninguna unión mecánica",
                        "Conectar la envolvente de chapa metálica de forma sólida al circuito de puesta a tierra general del local",
                        "Conectar la chapa metálica al borne del neutro",
                        "Pintar el interior de la envolvente con cola plástica conductora"
                    ),
                    a = 1,
                    exp = "Toda masa metálica conductora accesible de armarios que alberguen receptores o equipos Clase I debe conectarse de forma redundante y segura al conductor PE de tierra del edificio para provocar la desconexión rápida del diferencial ante cualquier defecto o derivación (ITC-BT-51 e ITC-BT-17).",
                    ref = "ITC-BT-51"
                ),
Question(
                    q = "¿Qué tipo de cable se emplea habitualmente como soporte físico de bus domótico bajo el estándar internacional KNX referenciado por la ITC-BT-51?",
                    opts = listOf(
                        "Cable coaxial flexible de antena",
                        "Manguera plana de manguera manguera telefónica",
                        "Par trenzado apantallado de dos hilos (o cuatro hilos, p. ej. JY(St)Y 2x2x0,8 mm) resistente a interferencias",
                        "Fibra óptica de vidrio multimodo de 50 micras"
                    ),
                    a = 2,
                    exp = "El estándar KNX utiliza par trenzado (TP) apantallado que trenza el par de ida y vuelta para anular los campos electromagnéticos inducidos externos y disipar las interferencias inducidas a través de la pantalla metálica que se conecta a tierra (ITC-BT-51).",
                    ref = "ITC-BT-51"
                ),
Question(
                    q = "¿Qué limitación establece la ITC-BT-51 para los sistemas de racionalización o gestión activa de demanda de potencia en viviendas?",
                    opts = listOf(
                        "No se permite su instalación si la electrificación es elevada",
                        "Deben desconectar de forma cíclica los aparatos de Clase II únicamente",
                        "Deben operar únicamente en corriente continua",
                        "No deben desconectar en ningún caso equipos de ventilación forzada o climatización en locales de pública concurrencia si ello compromete la seguridad higiénica o la salud"
                    ),
                    a = 3,
                    exp = "La gestión activa de carga domótica (racionalizadores) optimiza el consumo desactivando circuitos no prioritarios. Sin embargo, la ITC-BT-51 par. 6 prohíbe interrumpir extractores mecánicos de humo, ventilación ambiental forzada u otros servicios de seguridad biológica o personal.",
                    ref = "ITC-BT-51"
                ),
Question(
                    q = "En sistemas de control de accesos domóticos de viviendas (p. ej. cerraduras electrónicas), ¿qué condición rige ante un fallo total de alimentación de red eléctrica según la ITC-BT-51?",
                    opts = listOf(
                        "Deben disponer de mecanismos de desbloqueo mecánico manual en el lado interior de evacuación (o sistema de 'fallo seguro' que abra al desenergizarse)",
                        "Deben quedar bloqueadas permanentemente sin posibilidad de apertura",
                        "Deben sonar continuamente hasta vaciar la batería",
                        "Deben inyectar una descarga de 230 V al picaporte exterior"
                    ),
                    a = 0,
                    exp = "Ante situaciones de emergencia o incendios con pérdida de energía (Blackout), las vías de evacuación de edificios automatizados deben garantizar la salida libre y segura de los ocupantes, exigiendo mecanismos de desbloqueo pasivo tipo antipánico o 'fail-safe' (ITC-BT-51 par. 6 e ITC-BT-28).",
                    ref = "ITC-BT-51"
                ),
Question(
                    q = "¿Cómo se debe proteger a la central domótica contra sobretensiones transitorias propagadas por la red eléctrica externa según la ITC-BT-51 e ITC-BT-23?",
                    opts = listOf(
                        "Instalando fusibles de plomo rápido",
                        "Mediante dispositivos limitadores de sobretensiones (DPS) con varistores e descargadores de gas coordinados en el cuadro general de entrada",
                        "Sustituyendo el diferencial de alta sensibilidad por uno selectivo",
                        "Anulando de forma temporal el cable de tierra del bus"
                    ),
                    a = 1,
                    exp = "Las sensibles placas electrónicas domóticas pueden dañarse fácilmente por picos de sobretensión debidos a descargas atmosféricas o maniobras en la red de distribución. La ITC-BT-23 exige la instalación de descargadores coordinados Tipo 1+2 (o Tipo 2) en cabecera de instalación (ITC-BT-51).",
                    ref = "ITC-BT-51"
                ),
Question(
                    q = "Cuando los módulos domóticos de salida de relé conmuten circuitos trifásicos (ej. bombas o climatizadores), ¿qué protección térmica adicional se debe asociar al motor según la ITC-BT-51 e ITC-BT-47?",
                    opts = listOf(
                        "El propio relé domótico de baja potencia es suficiente",
                        "Un interruptor automático magnetotérmico de Clase B",
                        "Un contactor auxiliar trifásico asociado a un relé térmico calibrado o un guardamotor magnetotérmico calibrado a la corriente nominal del motor",
                        "Un fusible rápido de vidrio en la fase S"
                    ),
                    a = 2,
                    exp = "Los relés domóticos son contactos de maniobra seca y baja capacidad de corte, de modo que la ITC-BT-47 e ITC-BT-51 exigen intercalar contactores industriales con protección térmica calibrada contra sobrecargas y faltas de fase para gobernar cargas trifásicas.",
                    ref = "ITC-BT-51"
                ),
Question(
                    q = "¿Qué grado de inflamabilidad deben presentar las carcasas y envolventes de los equipos domóticos instalados empotrados en tabiques de yeso según la ITC-BT-51 e ITC-BT-21?",
                    opts = listOf(
                        "Deberán ser inflamables con marcado auto-ignición",
                        "No tienen requisitos de inflamabilidad",
                        "Deben resistir la inmersión en aceite mineral caliente",
                        "Deben estar fabricadas con plásticos autoextinguibles que superen el ensayo de hilo incandescente a un mínimo de 650 ºC (u 850 ºC según ubicación)"
                    ),
                    a = 3,
                    exp = "Toda caja o carcasa plástica empotrada en tabiquería o falsos techos combustibles debe resistir la acumulación de calor de fallos de contacto, certificando propiedades de no propagación de llama según ensayo de hilo incandescente de la norma UNE-EN 60695-2-11 (ITC-BT-21 e ITC-BT-51).",
                    ref = "ITC-BT-51"
                ),
Question(
                    q = "Para las canalizaciones domóticas exteriores que recorran fachadas (ej. sensores de viento o estaciones meteorológicas), ¿qué protección mecánica se exige según la ITC-BT-51?",
                    opts = listOf(
                        "Discurrir por el interior de tubos rígidos de PVC con resistencia a la radiación ultravioleta y grado de impacto IK08 mínimo, o canalizaciones de acero galvanizado",
                        "Sujetarse directamente a las paredes mediante grapas metálicas cortantes",
                        "Utilizar cables unipolar de PVC sin tubo",
                        "No se permite su instalación a la intemperie"
                    ),
                    a = 0,
                    exp = "Las canalizaciones domóticas expuestas a la intemperie deben resistir los rigores climáticos (sol, lluvia, heladas) y el vandalismo, imponiéndose tubos estancos (mínimo IP55) protegidos contra la radiación UV e impactos mecánicos (ITC-BT-51).",
                    ref = "ITC-BT-51"
                ),
Question(
                    q = "¿Qué se debe verificar tras completar la instalación de un sistema domótico de bus cableado antes de su puesta en servicio definitiva según la ITC-BT-51?",
                    opts = listOf(
                        "La temperatura exacta del cobre en cada borne",
                        "La continuidad de los conductores, la resistencia de aislamiento a tierra de todas las líneas y la correcta coordinación de las protecciones activas de la vivienda",
                        "Que los variadores de velocidad no giren a derechas",
                        "La velocidad de transmisión de datos en megabits"
                    ),
                    a = 1,
                    exp = "Antes de entregar la instalación domótica, la ITC-BT-51 y la ITC-BT-05 imponen verificar el correcto aislamiento de todos los circuitos y la efectividad de las medidas de protección contra contactos directos e indirectos, registrándose en el Certificado de Instalación.",
                    ref = "ITC-BT-51"
                ),
Question(
                    q = "En sistemas domóticos inalámbricos (buses RF), ¿qué precaución rige ante la saturación del espectro radioeléctrico según la ITC-BT-51 e ingeniería de telecomunicación?",
                    opts = listOf(
                        "Está prohibido usar redes inalámbricas de más de 12 V",
                        "Anular la antena receptora para que no capte ruidos",
                        "Operar en bandas de frecuencia homologadas e inmunes a interferencias de banda estrecha, cumpliendo de forma taxativa los límites de potencia radiada de telecomunicaciones",
                        "Instalar un fusible ultrarrápido en bornes de la antena"
                    ),
                    a = 2,
                    exp = "Las centrales y receptores de radiofrecuencia para automatización deben cumplir con la normativa técnica de espectro de la CEPT/ISED, evitando interferir con servicios de telecomunicaciones prioritarios (ITC-BT-51 par. 5).",
                    ref = "ITC-BT-51"
                ),
Question(
                    q = "¿Qué función cumple el módulo watchdog (temporizador de vigilancia) en los controladores lógicos programables de una instalación domótica según la ITC-BT-51?",
                    opts = listOf(
                        "Medir el nivel de humedad ambiental en los cuadros",
                        "Regular el factor de potencia de la central",
                        "Acelerar la velocidad del bus de datos",
                        "Garantizar que si el microprocesador del autómata se bloquea o sufre un bucle infinito de software, el sistema se reinicie de forma automática a un estado seguro predefinido"
                    ),
                    a = 3,
                    exp = "El temporizador watchdog es una medida de seguridad pasiva en control digital. Si el programa del microcontrolador deja de refrescar el temporizador periódico (debido a un cuelgue de firmware), el hardware genera un reset automático para recuperar el control operacional seguro del edificio (ITC-BT-51).",
                    ref = "ITC-BT-51"
                ),
Question(
                    q = "En la coexistencia de cables de bus y de potencia en el interior de canales de PVC de suelo o pared, ¿qué elemento mecánico es obligatorio instalar según la ITC-BT-51?",
                    opts = listOf(
                        "No se permite su coexistencia bajo ninguna forma",
                        "Un tabique separador continuo y aislante de PVC que separe físicamente ambos compartimentos a lo largo de toda la canalización",
                        "Una cinta adhesiva de embalaje común cubriendo el bus",
                        "Un cable de cobre de separación"
                    ),
                    a = 1,
                    exp = "La ITC-BT-51 par. 4.1 y la ITC-BT-20 determinan que para la coexistencia de redes de corrientes de diferente naturaleza (energía y datos) en una canalización común (como canaletas), debe interponerse un separador aislante continuo puesto a tierra si es metálico o aislante si es plástico.",
                    ref = "ITC-BT-51"
                ),
Question(
                    q = "Para un sistema domótico que gestiona las alarmas técnicas de la vivienda (escapes de agua o gas), ¿qué actuación prioritaria exige la ITC-BT-51?",
                    opts = listOf(
                        "Enviar una notificación al teléfono móvil exclusivamente",
                        "Encender las luces de emergencia",
                        "Subir la temperatura de la calefacción",
                        "Provocar de forma automática y mecánica el corte omnipolar del suministro afectado (mediante electroválvula de corte de paso de gas o agua) para contener el siniestro de forma pasiva"
                    ),
                    a = 3,
                    exp = "Las alarmas técnicas domóticas no deben limitarse a emitir avisos acústicos o de red, sino que deben actuar mecánicamente de inmediato cerrando las electroválvulas de corte hidráulico o neumático para suprimir el peligro de fuga masiva e inundación o deflagración (ITC-BT-51 par. 6).",
                    ref = "ITC-BT-51"
                ),
Question(
                    q = "¿Cuál es el esquema de conexión de la ITC-BT-52 en el que la línea de alimentación de la plaza de garaje se conecta directamente al contador individual de la vivienda del propio usuario?",
                    opts = listOf(
                        "Esquema 1 (Colectivo)",
                        "Esquema 2 (Troncal colectivo)",
                        "Esquema 3 (Individual con contador común para vivienda y garaje)",
                        "Esquema 4 (Individual con contador exclusivo para recarga)"
                    ),
                    a = 2,
                    exp = "El Esquema 3 de la ITC-BT-52 es idóneo para viviendas unifamiliares o bloques residenciales donde el garaje y la vivienda pertenecen al mismo abonado, aprovechando el mismo contrato y contador de suministro para la recarga.",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "¿Qué función prioritaria cumple el Sistema de Protección de Línea (SPL) o control dinámico de potencia en una infraestructura de recarga colectiva según la ITC-BT-52?",
                    opts = listOf(
                        "Aumentar la tensión de red por encima de 400 V",
                        "Anular la toma de tierra del edificio durante la recarga",
                        "Registrar los datos de matrícula de los vehículos",
                        "Modular dinámicamente la corriente demandada por los vehículos para no sobrepasar la intensidad máxima admisible de la Línea General de Alimentación (LGA)"
                    ),
                    a = 3,
                    exp = "El SPL es un dispositivo inteligente de gestión activa de carga (ITC-BT-52 par. 3). Si la demanda de energía del edificio es alta, reduce transitoriamente la potencia de carga de los vehículos, evitando el disparo del interruptor general de LGA por sobrecarga.",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "Cuando se instala un Sistema de Protección de Línea (SPL) en un garaje comunitario con recarga inteligente, ¿qué coeficiente de simultaneidad (fs) mínimo permite aplicar la ITC-BT-52 para dimensionar la Línea General de Alimentación (LGA)?",
                    opts = listOf(
                        "Un factor de simultaneidad fs = 0,3 fijos",
                        "Un factor de simultaneidad fs = 1,0 fijos",
                        "Un factor de simultaneidad fs = 0,8 fijos",
                        "Un factor de simultaneidad fs = 0,5 fijos"
                    ),
                    a = 0,
                    exp = "La ITC-BT-52 par. 5.2 establece que si se dispone de un SPL que supervise la potencia consumida por el edificio y los puntos de recarga, se puede reducir el coeficiente de simultaneidad a fs = 0,3 fijos, reduciendo drásticamente la sección de la LGA.",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "En ausencia de un Sistema de Protección de Línea (SPL), ¿qué coeficiente de simultaneidad (fs) exige aplicar la ITC-BT-52 para calcular la carga prevista de la LGA destinada a la recarga de vehículos?",
                    opts = listOf(
                        "fs = 0,3",
                        "fs = 1,0 (100 % de la potencia máxima de todos los puntos de recarga previstos)",
                        "fs = 0,7",
                        "fs = 0,5"
                    ),
                    a = 1,
                    exp = "A falta de un control dinámico (SPL) que module activamente la demanda de los cargadores, se debe asumir el peor escenario de carga concurrente simultánea de todos los vehículos conectados, exigiéndose fs = 1,0 (ITC-BT-52 par. 5.2).",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "¿Qué modo de carga de los vehículos eléctricos según la ITC-BT-52 se caracteriza por la carga en corriente alterna mediante una toma dedicada (p. ej. Tipo 2 Mennekes) con comunicación piloto continua entre el cargador fijo (wallbox) y el coche?",
                    opts = listOf(
                        "Modo de carga 1",
                        "Modo de carga 2",
                        "Modo de carga 3",
                        "Modo de carga 4"
                    ),
                    a = 2,
                    exp = "El Modo 3 (ITC-BT-52 par. 4) es el estándar recomendado para recarga semirrápida y doméstica fija, ya que utiliza un punto de recarga exclusivo que establece comunicación por cable piloto para monitorizar de forma segura la continuidad de tierra y el límite de corriente.",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "¿Qué modo de carga de la ITC-BT-52 se reserva para recarga rápida en corriente continua utilizando un convertidor rectificador externo y manguera con cable piloto fijo?",
                    opts = listOf(
                        "Modo de carga 1",
                        "Modo de carga 2",
                        "Modo de carga 3",
                        "Modo de carga 4"
                    ),
                    a = 3,
                    exp = "El Modo 4 de la ITC-BT-52 describe la carga rápida en corriente continua (DC Fast Charging), donde el rectificador está integrado en la estación de recarga externa (no en el coche), aplicando energía directa a la batería a potencias de hasta más de 150 kW.",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "¿Qué requisito contra sobretensiones exige la ITC-BT-52 para proteger de forma específica los puntos de recarga y el inversor del vehículo conectado?",
                    opts = listOf(
                        "La obligatoriedad de instalar dispositivos de protección contra sobretensiones transitorias y permanentes en el circuito de alimentación del cargador",
                        "Instalar un pararrayos de cebado en el techo de la plaza",
                        "Utilizar conductores blindados de Clase II únicamente",
                        "Anular temporalmente el limitador general de la vivienda"
                    ),
                    a = 0,
                    exp = "Los vehículos eléctricos albergan componentes electrónicos muy sensibles a picos de tensión inducidos por rayos y maniobras de red. La ITC-BT-52 exige de forma taxativa la colocación de protecciones contra sobretensiones transitorias y permanentes coordinadas.",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "Para un circuito monofásico destinado a un punto de recarga doméstico (Modo 3 o Modo 2) de hasta 3,7 kW de potencia, ¿cuál debe ser la sección mínima del conductor de cobre según la ITC-BT-52?",
                    opts = listOf(
                        "1,5 mm²",
                        "2,5 mm² (equivalente a un circuito C16 protegido por magnetotérmico de 16 A)",
                        "4,0 mm²",
                        "6,0 mm²"
                    ),
                    a = 1,
                    exp = "La sección mínima obligatoria de conductores de cobre para la recarga lenta de 16 A (hasta 3,68 kW) es de 2,5 mm² según la ITC-BT-52, debiendo de incrementarse según la longitud del circuito para cumplir con el límite máximo de caída de tensión del 5%.",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "Para un circuito de recarga trifásico de 22 kW de potencia (Modo 3 a 32 A), ¿qué sección mínima de conductor de cobre se recomienda de forma ordinaria para no sobrepasar la temperatura máxima del cable?",
                    opts = listOf(
                        "2,5 mm²",
                        "4,0 mm²",
                        "6,0 mm² protegida por PIA de 32 A (circuito de recarga individual)",
                        "10 mm² con fusible de acompañamiento"
                    ),
                    a = 2,
                    exp = "Una corriente nominal continua de 32 A para recarga trifásica exige conductores de cobre de sección mínima de 6 mm² bajo tubo, garantizando la capacidad de disipación térmica y una caída de tensión muy baja a lo largo del trayecto (ITC-BT-52).",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "¿Qué grado de protección mecánica contra impactos (IK) mínimo deben poseer las estaciones de recarga (wallboxes) instaladas en la vía pública o parkings exteriores según la ITC-BT-52?",
                    opts = listOf(
                        "IK05",
                        "IK07",
                        "IK08",
                        "IK10 (grado antivandálico máximo)"
                    ),
                    a = 3,
                    exp = "La ITC-BT-52 y normas constructivas de puntos de recarga exigen un grado de protección frente a impactos de IK10 en exteriores y vía pública para garantizar la integridad mecánica del equipo ante el vandalismo y atropellos accidentales.",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "En garajes comunitarios residenciales interiores, ¿qué grado de protección mecánica (IK) e IP mínimo deben tener las envolventes fijas de recarga según la ITC-BT-52?",
                    opts = listOf(
                        "Mínimo IP4X e IK08 contra impactos moderados",
                        "IP20 e IK02 sin exigencias de polvo",
                        "IP54 e IK10 obligatoriamente siempre",
                        "IP68 sumergible permanentemente"
                    ),
                    a = 0,
                    exp = "En interiores de garajes cerrados, la ITC-BT-52 fija un grado mínimo de IK08 para las estaciones de carga e IP4X contra la introducción de herramientas e hilos metálicos de diámetro superior a 1,0 mm.",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "¿Cuál es la caída de tensión máxima admisible desde el origen de la instalación (ej. centralización de contadores) hasta el punto de conexión del vehículo eléctrico según la ITC-BT-52?",
                    opts = listOf(
                        "3,0 % máximo",
                        "5,0 % máximo a la intensidad máxima de carga prevista en régimen permanente",
                        "8,0 % máximo",
                        "1,5 % máximo"
                    ),
                    a = 1,
                    exp = "La ITC-BT-52 par. 5.2 limita de forma estricta la caída de tensión al 5,0% para optimizar la transferencia de potencia y evitar que la tensión efectiva en bornes baje de los valores nominales mínimos exigidos por el vehículo para iniciar la recarga.",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "En el Esquema 1 de la ITC-BT-52 (Esquema colectivo con un contador común para recarga), ¿cómo se factura la electricidad consumida por los diferentes usuarios del garaje?",
                    opts = listOf(
                        "La paga el ayuntamiento local directamente",
                        "Mediante una tarifa plana fija e igual para todas las plazas",
                        "A través de contadores secundarios individuales homologados instalados aguas abajo del contador común colectivo, que miden el consumo exacto de cada cargador",
                        "La asume la comunidad de propietarios dividida entre los coeficientes de copropiedad"
                    ),
                    a = 2,
                    exp = "En el Esquema 1 colectiva de la ITC-BT-52, un único contador general mide la energía de la infraestructura de recarga, empleándose analizadores de red o contadores modulares secundarios con certificación MID para prorratear los consumos individuales con precisión.",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "¿Qué tipo de canalización se considera de uso preferente para las líneas troncales colectivas de recarga en garajes comunitarios según la ITC-BT-52?",
                    opts = listOf(
                        "Hilos rígidos grapados directamente al techo sin tubo",
                        "Tuberías de gas de acero sin costura",
                        "Conductores planos de aluminio",
                        "Bandejas metálicas tipo rejilla o chapa perforada puestas a tierra, o canaletas de PVC autoextinguible con compartimentos independientes"
                    ),
                    a = 3,
                    exp = "Para soportar el peso de múltiples derivaciones individuales que discurren por los sótanos, la ITC-BT-52 y la ITC-BT-21 prescriben canalizaciones continuas como bandejas metálicas puestas a tierra que faciliten el tendido y garanticen alta protección mecánica.",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "Cuando la infraestructura de recarga se instala a la intemperie (vía pública o parkings de superficie), ¿qué grado de estanqueidad (IP) mínimo rige según la ITC-BT-52?",
                    opts = listOf(
                        "Al menos IP54 (protegido contra el polvo nocivo y salpicaduras de agua en todas direcciones)",
                        "IP20 para zonas secas",
                        "IPX0 sin protección contra la lluvia",
                        "Debe ser IP68 hermético al agua a 10 metros de profundidad"
                    ),
                    a = 0,
                    exp = "En instalaciones expuestas a la intemperie (lluvia, nieve, viento, polvo), la ITC-BT-52 exige carcasas estancas con grado mínimo IP54 para proteger los circuitos internos de potencia y control frente a averías e incendios.",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "¿Qué especificación de corriente de fuga de corriente continua (DC) exige el interruptor diferencial de un circuito de recarga de vehículos si el cargador no incorpora detección interna de fugas en DC de 6 mA según la ITC-BT-52?",
                    opts = listOf(
                        "Diferencial estándar Clase AC",
                        "Interruptor diferencial Clase B, o Clase A acompañado de un dispositivo RDC-DD que desconecte el suministro ante fugas en c.c. iguales o superiores a 6 mA",
                        "Diferencial de 300 mA selectivo",
                        "No se exige protección contra continua si el motor es trifásico"
                    ),
                    a = 1,
                    exp = "El rectificador de carga del coche puede generar fugas de corriente continua hacia la red de c.a., las cuales pueden saturar magnéticamente el núcleo de diferenciales Clase A ordinarios, impidiendo su disparo. Por ello, se impone protección de Clase B o Clase A combinada con sensor RDC-DD de 6 mA en DC (ITC-BT-52 e IEC 62955).",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "En una infraestructura de recarga en un garaje colectivo de viviendas nuevas, ¿qué previsión de potencia mínima por plaza de aparcamiento rige de forma genérica para calcular el suministro total según el REBT y el CTE (DB-HE5)?",
                    opts = listOf(
                        "No se exige previsión para edificios residenciales",
                        "1,0 kW monofásicos por plaza",
                        "3,68 kW (monofásico a 16 A) o la correspondiente al esquema de control de carga previsto",
                        "22 kW trifásicos para el 100 % de las plazas"
                    ),
                    a = 2,
                    exp = "El Código Técnico de la Edificación y el REBT fijan una reserva de potencia para la infraestructura de recarga de coches en edificios residenciales nuevos que se calcula habitualmente con 3,68 kW por plaza de garaje, coordinándose mediante coeficientes de simultaneidad fs según el tipo de control (SPL).",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "¿Está permitido utilizar el Modo de carga 1 (sin comunicación ni protección integrada) en parkings públicos o de uso comercial según la ITC-BT-52?",
                    opts = listOf(
                        "Sí, sin ninguna limitación de potencia",
                        "Solo para camiones de gran tonelaje",
                        "Únicamente si la tensión es trifásica a 400 V",
                        "No, está terminantemente prohibido el Modo de carga 1 para recarga pública o colectiva por razones de seguridad al carecer de control de tierra"
                    ),
                    a = 3,
                    exp = "El Modo 1 carece de cable piloto de control de seguridad de continuidad de tierra y de protección contra sobrecorrientes en bornes del coche. La ITC-BT-52 limita su uso a recargas domésticas muy básicas con potencias menores de 3,7 kW y lo prohíbe en cargadores públicos.",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "¿A qué altura mínima sobre el suelo deben situarse las bases de toma de corriente integradas en cargadores instalados en plazas de garaje comunitarias según la ITC-BT-52?",
                    opts = listOf(
                        "A 10 cm del suelo",
                        "Entre 0,5 metros y 1,2 metros sobre el pavimento (para prevenir inundaciones bajas y favorecer la ergonomía de conexión)",
                        "A no menos de 2 metros de altura obligatoriamente",
                        "En el techo suspendidas de poleas"
                    ),
                    a = 1,
                    exp = "La ITC-BT-52 par. 5 fija que en garajes cubiertos interiores las tomas de corriente y conectores deben ubicarse a una altura comprendida entre 0,5 m y 1,2 m (elevándose a un rango mayor en exteriores) para proteger el mecanismo frente a charcos y facilitar el enchufado seguro.",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "¿Cómo debe discurrir la derivación individual de recarga si atraviesa zonas comunes del edificio desde la centralización de contadores según la ITC-BT-52?",
                    opts = listOf(
                        "Se permite colgarla por los pasillos con ganchos de plástico",
                        "Debe ir empotrada en las paredes de yeso sin tubo protector",
                        "Debe unirse a las mangueras de agua para refrigerarse",
                        "Debe discurrir de forma visible y continua por el interior de tubos rígidos de PVC autoextinguibles, bandejas metálicas puestas a tierra o canales que ofrezcan suficiente resistencia al fuego"
                    ),
                    a = 3,
                    exp = "La derivación individual que alimenta el cargador desde el contador general debe protegerse físicamente a lo largo de todo su trazado por zonas comunes, asegurando que un fallo de aislamiento no propague incendios ni afecte a circuitos de servicios generales del edificio (ITC-BT-52 e ITC-BT-15).",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "¿Qué tipo de toma de corriente específica para recarga doméstica (Modo 2) admite la ITC-BT-52 para corrientes permanentes de 16 A con alta durabilidad térmica?",
                    opts = listOf(
                        "Una base Schuko ordinaria de uso doméstico básico",
                        "Una base Schuko reforzada de tipo industrial (p. ej. tipo Green'up o similar) ensayada para soportar 16 A continuos en recarga prolongada",
                        "Un conector plano telefónico de datos",
                        "Una base de toma de Clase 0 sin conductor de protección"
                    ),
                    a = 1,
                    exp = "Las tomas domésticas ordinarias Schuko sufren desgaste y sobrecalentamiento si conducen 16 A permanentes durante 8 o 10 horas seguidas. La ITC-BT-52 y las directrices técnicas recomiendan emplear tomas reforzadas industriales ensayadas específicamente para ciclo continuo de recarga.",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "¿A qué norma UNE deben estar adaptados los conectores tipo toma de corriente destinados a la recarga de vehículos eléctricos en Modo 3 según la ITC-BT-52?",
                    opts = listOf(
                        "UNE-EN 60335-1 de electrodomésticos",
                        "UNE-EN 62196-2 (que normaliza los conectores Tipo 2 o Mennekes y Tipo 1 Yazaki)",
                        "UNE 20460-7-701 de baños",
                        "UNE-EN 50110 de explotación de instalaciones"
                    ),
                    a = 1,
                    exp = "La norma UNE-EN 62196-2 define las características de diseño geométrico, eléctrico y mecánico de los conectores estándar homologados en la Unión Europea para la carga en c.a. de vehículos eléctricos, asegurando la interoperabilidad (ITC-BT-52).",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "En una acometida de un cargador de vehículo eléctrico que también suministra corriente al circuito del trastero adyacente de la plaza, ¿cómo debe estructurarse el circuito según la ITC-BT-52?",
                    opts = listOf(
                        "El circuito de recarga puede compartirse libremente con el trastero sin protecciones añadidas",
                        "Está prohibido alimentar el trastero desde la misma línea",
                        "El trastero debe anularse y no tener luz",
                        "El circuito del punto de recarga debe ser exclusivo y estar debidamente independizado y protegido respecto al trastero mediante magnetotérmico y diferencial dedicados"
                    ),
                    a = 3,
                    exp = "La ITC-BT-52 exige que la línea de alimentación de la estación de recarga sea de uso exclusivo para el coche eléctrico, prohibiéndose intercalar otras cargas del trastero (herramientas, luces) en el mismo circuito terminal para evitar disparos intempestivos o sobrecargas.",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "Cuando el Esquema de medida es el Esquema 4 de la ITC-BT-52, ¿qué contador físico se instala para el usuario?",
                    opts = listOf(
                        "Un contador individual específico para recarga que es independiente de la vivienda de ese mismo usuario",
                        "No se instala contador y la carga es gratuita",
                        "El contador colectivo de la comunidad de propietarios",
                        "Un reloj analógico de horas contratadas"
                    ),
                    a = 0,
                    exp = "El Esquema 4 (Individual con contador exclusivo) se utiliza habitualmente cuando el garaje comunitario está en un edificio diferente de la vivienda del abonado o cuando se desea formalizar un contrato de tarifa eléctrica exclusivo para el coche eléctrico (ITC-BT-52 par. 3).",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "¿Qué limitación rige para el tendido de los cables de derivación individual de recarga por el interior de patinillos o conductos verticales de otras instalaciones?",
                    opts = listOf(
                        "Se prohíbe totalmente cualquier paso de derivaciones por patinillos",
                        "Se permite el uso libre compartiendo espacio con conductos de gas e instalaciones sanitarias de agua",
                        "Deben discurrir por patinillos técnicos de uso exclusivo eléctrico que no contengan conducciones combustibles ni de fluidos calientes, y estar separados por tubos protectores no propagadores",
                        "No hay restricciones si los cables son unipolar rígidos"
                    ),
                    a = 2,
                    exp = "La derivación individual debe ir protegida físicamente para evitar cortocircuitos cruzados con tuberías metálicas o que una fuga de agua moje los cables eléctricos en carga prolongada (ITC-BT-52 e ITC-BT-15).",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "¿Qué clase de aislamiento deben poseer como mínimo los cables de potencia utilizados para el circuito terminal de carga de Modo 3 en garajes interiores según la ITC-BT-52?",
                    opts = listOf(
                        "Aislamiento de papel impregnado de aceite",
                        "Cables de tensión asignada 0,6/1 kV con cubierta exterior no propagadora de la llama, libre de halógenos y baja emisión de humos opacos (tipo RZ1-K o equivalente)",
                        "Hilos unipolar rígidos H07V-U bajo tubo metálico accesible",
                        "Conductores desnudos suspendidos por aisladores cerámicos"
                    ),
                    a = 1,
                    exp = "Para canalizaciones en garajes comunitarios (locales con peligro latente de acumulación de monóxido y humos en incendios), el REBT (ITC-BT-28 e ITC-BT-52) impone el uso de cables con aislamiento de seguridad libre de halógenos (tipo RZ1-K) para evitar gases tóxicos y corrosivos.",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "¿Qué consideración rige para la instalación de puntos de recarga de vehículos eléctricos en locales clasificados con riesgo de explosión (ATEX), como estaciones de servicio de combustible líquido?",
                    opts = listOf(
                        "No se permite instalar puntos de recarga en ninguna zona de la gasolinera",
                        "Se permite la instalación de cargadores domésticos ordinarios junto a los surtidores",
                        "Se exige usar cargadores portátiles únicamente",
                        "Deben instalarse strictly fuera de la clasificación de volúmenes de peligro ATEX o emplear equipos certificados para atmósferas explosivas con protección Ex de Clase I, según la ITC-BT-29 e ITC-BT-52"
                    ),
                    a = 3,
                    exp = "En las estaciones de servicio (con volúmenes de vapores inflamables de hidrocarburos), los cargadores de vehículos eléctricos (que generan chispas mecánicas en clavijas o contactores) deben quedar alejados de las zonas clasificadas peligrosas o utilizar protecciones antideflagrantes homologadas (ITC-BT-29 e ITC-BT-52).",
                    ref = "ITC-BT-52"
                ),
Question(
                    q = "¿Cuál es el objeto de la instrucción ITC-BT-33 del REBT?",
                    opts = listOf(
                        "Regular las instalaciones de alta tensión en industrias",
                        "Establecer las prescripciones para las instalaciones eléctricas provisionales y temporales de obra",
                        "Normar la instalación de piscinas",
                        "Definir el alumbrado en estadios deportivos"
                    ),
                    a = 1,
                    exp = "La ITC-BT-33 tiene por objeto establecer las prescripciones para las instalaciones eléctricas provisionales y temporales de obra (públicas o privadas) destinadas a suministrar energía durante la ejecución de los trabajos.",
                    ref = "ITC-BT-33 §1"
                ),
Question(
                    q = "¿Quién es el responsable de la seguridad de la instalación eléctrica temporal en una obra según la ITC-BT-33?",
                    opts = listOf(
                        "El titular de la obra y el instalador autorizado ejecutor",
                        "Únicamente el ayuntamiento",
                        "El fabricante de los cables",
                        "La compañía distribuidora exclusivamente"
                    ),
                    a = 0,
                    exp = "Según la ITC-BT-33, el titular de la obra y el instalador o empresa instaladora autorizada que ejecute la instalación son responsables de que esta cumpla con las prescripciones de seguridad establecidas.",
                    ref = "ITC-BT-33 §2"
                ),
Question(
                    q = "¿Qué grado de protección mínimo deben poseer las envolventes de los cuadros eléctricos instalados a la intemperie en una obra según la ITC-BT-33?",
                    opts = listOf(
                        "IP20",
                        "IP44",
                        "IP67",
                        "IP30"
                    ),
                    a = 1,
                    exp = "La ITC-BT-33 exige que todos los cuadros eléctricos de obra instalados a la intemperie cuenten con un grado de protección mínimo IP44 para garantizar su funcionamiento seguro frente a la lluvia y objetos sólidos.",
                    ref = "ITC-BT-33 §2"
                )
    )
}
