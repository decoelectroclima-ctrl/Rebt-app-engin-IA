package com.example.data

object EspecialesQuestionsPart4 {
    val QUESTIONS = listOf(
Question(
                    q = "¿Qué tipo de cable de alimentación flexible debe utilizarse para conectar grupos móviles de soldadura eléctrica según la ITC-BT-45?",
                    opts = listOf(
                        "Cables flexibles con aislamiento y cubierta elastomérica para servicio pesado (tipo H07RN-F o equivalente)",
                        "Mangueras flexibles domésticas tipo H03VV-F",
                        "Hilos rígidos de PVC bajo tubo corrugado ligero",
                        "Conductores planos trenzados sin funda exterior"
                    ),
                    a = 0,
                    exp = "La ITC-BT-45 par. 3.3 exige que los aparatos de soldadura por arco móviles se conecten a la red mediante conductores flexibles dotados de cubierta elastomérica de policloropreno (tipo H07RN-F) de alta resistencia al arrastre, abrasión, calor y aceites.",
                    ref = "ITC-BT-45"
                ),
Question(
                    q = "¿Qué característica deben poseer las pinzas porta-electrodos en soldadura eléctrica manual según la ITC-BT-45?",
                    opts = listOf(
                        "Deben ser de latón pulido sin recubrimiento para enfriarse rápido",
                        "Deben estar completamente aisladas exteriormente de forma que no presenten partes metálicas bajo tensión accesibles",
                        "Deben conectarse directamente al neutro de la instalación",
                        "Deben llevar una empuñadura de hierro forjado"
                    ),
                    a = 1,
                    exp = "Conforme a la ITC-BT-45 pto. 3.3, las pinzas porta-electrodos deben estar enteramente recubiertas con material aislante dieléctrico y térmico de alto impacto, evitando que el soldador o piezas puestas a tierra entren en contacto accidental con partes bajo tensión al depositar la pinza en pausas de trabajo.",
                    ref = "ITC-BT-45"
                ),
Question(
                    q = "En calentadores de agua industriales con electrodos sumergidos en agua, ¿a qué debe conectarse obligatoriamente el neutro de la red trifásica según la ITC-BT-45?",
                    opts = listOf(
                        "Debe dejarse aislado con cinta vulcanizada",
                        "Debe conectarse a una fase mediante un condensador",
                        "Debe conectarse a la envolvente metálica (cuba) y al circuito general de puesta a tierra",
                        "Debe conectarse a la tubería de plástico de evacuación"
                    ),
                    a = 2,
                    exp = "La ITC-BT-45 par. 3.1.1 letra a establece que en calentadores de agua por electrodos el conductor neutro de la red debe estar sólidamente unido a la cuba metálica del calentador y puesto a tierra para evitar que el agua o la cuba adquieran potenciales peligrosos respecto al suelo.",
                    ref = "ITC-BT-45"
                ),
Question(
                    q = "¿Qué protección contra contactos indirectos es OBLIGATORIA para aparatos de caldeo de Clase I en uso doméstico según la ITC-BT-45 e ITC-BT-24?",
                    opts = listOf(
                        "Aislamiento de madera en el suelo de la cocina",
                        "Uso obligatorio de guantes dieléctricos por el usuario",
                        "Alimentación a través de un transformador de 12 V",
                        "Puesta a tierra de sus masas metálicas asociada a un interruptor diferencial de sensibilidad no superior a 30 mA"
                    ),
                    a = 3,
                    exp = "La ITC-BT-45 en concordancia con la ITC-BT-24 exige que todos los aparatos de caldeo domésticos de Clase I (hornos, termos, encimeras, tostadores) tengan sus masas conectadas al conductor PE y estén protegidos por interruptor diferencial de alta sensibilidad (IΔn ≤ 30 mA).",
                    ref = "ITC-BT-45"
                ),
Question(
                    q = "¿Qué requisito deben cumplir los elementos calefactores blindados sumergidos en líquidos inflamables o aceites térmicos industriales según la ITC-BT-45?",
                    opts = listOf(
                        "Disponer de un control de nivel que impida su energización si las resistencias no están completamente sumergidas",
                        "Funcionar únicamente durante la noche con tarifa nocturna",
                        "Estar construidos exclusivamente con tubos de plomo blando",
                        "Alimentarse exclusivamente con corriente continua a 12 V"
                    ),
                    a = 0,
                    exp = "La ITC-BT-45 punto 3 previene el riesgo de explosión e ignición en calentadores de líquidos inflamables o aceites exigiendo un enclavamiento automático por nivel o presostato que garantice que las resistencias estén sumergidas antes de encenderse, evitando puntos calientes en contacto con vapores.",
                    ref = "ITC-BT-45"
                ),
Question(
                    q = "En aparatos de secado por aire caliente (p. ej. secadoras de ropa o túneles de secado), ¿qué enclavamiento de seguridad exige la ITC-BT-45?",
                    opts = listOf(
                        "Que el ventilador se apague antes de encender las resistencias",
                        "Que las resistencias de caldeo no puedan conectarse si el sistema de ventilación no está en marcha efectiva",
                        "Que la puerta se abra automáticamente cada 3 minutos",
                        "Que la temperatura interior no supere en ningún caso los 20 ºC"
                    ),
                    a = 1,
                    exp = "Conforme a la ITC-BT-45 y normas UNE de seguridad térmica, en los aparatos de caldeo por convección forzada las resistencias deben estar enclavadas con el motor de ventilación, de forma que un fallo o parada del flujo de aire desconecte inmediatamente la calefacción para evitar el sobrecalentamiento destructivo.",
                    ref = "ITC-BT-45"
                ),
Question(
                    q = "¿Qué sección mínima debe tener el circuito independiente dedicado a una cocina/horno eléctrico doméstico según el REBT (ITC-BT-45 e ITC-BT-25)?",
                    opts = listOf(
                        "2,5 mm² protegida por PIA de 16 A",
                        "4 mm² protegida por PIA de 20 A",
                        "6 mm² en cobre protegida por un interruptor automático magnetotérmico de 25 A (circuito C3)",
                        "10 mm² con fusible de 63 A"
                    ),
                    a = 2,
                    exp = "De acuerdo con la ITC-BT-25 tabla 1 e ITC-BT-45 par. 2.3, el circuito individual para cocina y horno (C3) debe ejecutarse con conductores de cobre de sección mínima de 6 mm² y protegerse mediante un PIA de 25 A.",
                    ref = "ITC-BT-45"
                ),
Question(
                    q = "¿Qué limitación rige para los conductores de alimentación de aparatos de calefacción portátiles según la ITC-BT-45?",
                    opts = listOf(
                        "Deben tener al menos 10 metros de longitud para disipar calor",
                        "Pueden ser hilos desnudos suspendidos por aisladores cerámicos",
                        "No pueden disponer de conductor de protección si el aparato pesa menos de 3 kg",
                        "Deben tener una longitud máxima razonable (típicamente no superior a 2 metros en uso ordinario) para evitar caídas de tensión y tropiezos"
                    ),
                    a = 3,
                    exp = "La ITC-BT-45 y las normas de producto UNE fijan que los cordones de alimentación de aparatos móviles o portátiles de calefacción deben ser cortos y resistentes, reduciendo la exposición al calor de la propia estufa y minimizando el riesgo de rozaduras y daños mecánicos.",
                    ref = "ITC-BT-45"
                ),
Question(
                    q = "¿Qué tipo de bornes de conexión deben emplearse en las resistencias calefactoras industriales sometidas a ciclos continuos de dilatación según la ITC-BT-45?",
                    opts = listOf(
                        "Bornes metálicos resistentes al calor con arandelas elásticas u otros sistemas que compensen la dilatación térmica y eviten el aflojamiento",
                        "Soldadura blanda de estaño-plomo convencional",
                        "Cinta vulcanizada con adhesivo común",
                        "Regletas de plástico termoplástico de polietileno estándar"
                    ),
                    a = 0,
                    exp = "La ITC-BT-45 punto 3 y normas UNE de hornos prescriben que las conexiones eléctricas a elementos calefactores deben realizarse mediante bornes de materiales refractarios o acero inoxidable con arandelas Belleville o tuercas frenadas, impidiendo que la dilatación y contracción cíclica afloje la conexión y genere puntos calientes.",
                    ref = "ITC-BT-45"
                ),
Question(
                    q = "¿Cuál es el valor máximo reglamentario para el calibre del interruptor magnetotérmico que protege el circuito de un termo eléctrico doméstico (C4.3) según el REBT e ITC-BT-45?",
                    opts = listOf(
                        "10 A",
                        "16 A con conductores de sección mínima 2,5 mm² de cobre",
                        "25 A con conductores de 1,5 mm²",
                        "32 A con interruptor curva D"
                    ),
                    a = 1,
                    exp = "Según la ITC-BT-25 en concordancia con la ITC-BT-45, la línea destinada a receptores térmicos de agua (termo acumulador eléctrico, circuito C4/C4.3) se protege con un interruptor automático de calibre máximo 16 A con sección de conductor no inferior a 2,5 mm².",
                    ref = "ITC-BT-45"
                ),
Question(
                    q = "En hornos industriales de resistencias alimentados bajo esquema TN-C según la ITC-BT-45, ¿qué conductor cumple la doble función de neutro y protección?",
                    opts = listOf(
                        "El conductor de fase secundaria R2",
                        "El cable coaxial apantallado de control",
                        "El conductor PEN (Protective Earth + Neutral)",
                        "El conductor de equipotencialidad suplementaria sin conectar al transformador"
                    ),
                    a = 2,
                    exp = "En el esquema TN-C contemplado en la ITC-BT-45 par. 3.2 para hornos con elevadas corrientes de fuga de origen resistivo-capacitivo, el conductor PEN combina las funciones de neutro de servicio y conductor de protección a tierra, impidiendo disparos intempestivos de protecciones diferenciales.",
                    ref = "ITC-BT-45"
                ),
Question(
                    q = "¿Qué requisito deben cumplir los calefactores eléctricos de inmersión para bidones o depósitos metálicos portátiles según la ITC-BT-45?",
                    opts = listOf(
                        "Deben conectarse únicamente a generadores diésel trifásicos",
                        "Deben carecer de termostato para asegurar temperatura constante",
                        "Deben pintarse con esmalte sintético inflamable",
                        "Deben contar con envolvente metálica conectada a tierra y protección diferencial de 30 mA, asegurando además la equipotencialidad del bidón"
                    ),
                    a = 3,
                    exp = "La ITC-BT-45 y la ITC-BT-24 exigen que los calentadores de inmersión portátiles utilizados en recipientes metálicos mantengan la carcasa del calentador y el propio bidón firmemente unidos al circuito de tierra de protección bajo vigilancia diferencial de alta sensibilidad (30 mA).",
                    ref = "ITC-BT-45"
                ),
Question(
                    q = "¿Qué precaución se debe tener con los aparatos de caldeo radiante por infrarrojos instalados en locales con presencia de polvo combustible según la ITC-BT-45 e ITC-BT-29?",
                    opts = listOf(
                        "Aumentar la potencia al doble para quemar el polvo al vuelo",
                        "Asegurar que la temperatura superficial de los emisores no alcance la temperatura de ignición de las capas o nubes de polvo presentes",
                        "Sumergir los tubos infrarrojos en agua destilada",
                        "Orientar los reflectores hacia las salidas de ventilación de serrín"
                    ),
                    a = 1,
                    exp = "Conforme a la ITC-BT-45 y la ITC-BT-29 (atmósferas explosivas o locales con riesgo de incendio), los aparatos de caldeo deben tener clasificada su temperatura máxima superficial (clase de temperatura T1 a T6), garantizando que permanezca por debajo de la temperatura de autoinflamación del polvo o fibras combustibles acumuladas.",
                    ref = "ITC-BT-45"
                ),
Question(
                    q = "¿Qué exigencia rige para las canalizaciones eléctricas situadas en la proximidad inmediata de aparatos de caldeo según la ITC-BT-45 e ITC-BT-19?",
                    opts = listOf(
                        "Pueden utilizarse tubos de cartón alquitranado",
                        "Deben fijarse directamente sobre las chapas calientes del calefactor",
                        "No se requiere aislamiento si el voltaje es inferior a 230 V",
                        "Deben estar protegidas contra el calor radiante o emplear conductores aislados con elastómeros de silicona y tubos metálicos resistentes al fuego"
                    ),
                    a = 3,
                    exp = "La ITC-BT-45 y la ITC-BT-19 par. 2.7 determinan que cuando las canalizaciones eléctricas discurran junto a aparatos térmicos u hornos, deben interponerse pantallas calorífugas o emplear cables con aislamiento especial resistente al calor (silicona, fibra mineral) para evitar la degradación del aislante por temperatura ambiental extrema.",
                    ref = "ITC-BT-45"
                ),
Question(
                    q = "¿Cuál es la tensión asignada normalizada para los cables y folios radiantes de calefacción empotrados en suelos o techos?",
                    opts = listOf(
                        "300/500 V",
                        "230/400 V",
                        "450/750 V",
                        "0,6/1 kV"
                    ),
                    a = 0,
                    exp = "La ITC-BT-46 apartado 1 establece que los cables eléctricos y folios radiantes calefactores utilizados en suelos o techos tendrán una tensión nominal asignada de 300/500 V, garantizando su aislamiento frente a tensiones de red.",
                    ref = "ITC-BT-46 §1"
                ),
Question(
                    q = "En locales con bañera o ducha, ¿dónde está prohibido instalar cables y folios calefactores radiantes?",
                    opts = listOf(
                        "En ningún volumen si tienen diferencial",
                        "Dentro de los volúmenes de prohibición 0 y 1",
                        "Únicamente en el volumen 3",
                        "En todo el cuarto de baño sin excepción"
                    ),
                    a = 1,
                    exp = "La ITC-BT-46 par. 2 determina que estas instalaciones de calefacción radiante no deben realizarse dentro de los volúmenes 0 y 1 de los cuartos de baño para evitar cualquier riesgo por contacto directo con elementos energizados.",
                    ref = "ITC-BT-46 §2"
                ),
Question(
                    q = "¿Qué prescripción reglamentaria rige para las uniones frías entre el cable calefactor y los conductores de alimentación?",
                    opts = listOf(
                        "Pueden empalmarse en obra mediante regletas convencionales",
                        "Deben ubicarse siempre en el volumen 1 del baño",
                        "Deben venir obligatoriamente realizadas de fábrica y no en obra",
                        "Se permiten uniones manuales con cinta termorretráctil"
                    ),
                    a = 2,
                    exp = "La ITC-BT-46 par. 3.2.1 exige que las uniones frías vengan realizadas de fábrica con ensayos de estanqueidad y rigidez superados, quedando terminantemente prohibida su ejecución improvisada en la obra.",
                    ref = "ITC-BT-46 §3.2.1"
                ),
Question(
                    q = "¿Cuál es el espesor mínimo de la capa de hormigón o mortero no aislante que debe recubrir los cables calefactores en suelos radiantes?",
                    opts = listOf(
                        "10 mm",
                        "20 mm",
                        "40 mm",
                        "30 mm"
                    ),
                    a = 3,
                    exp = "Según la ITC-BT-46 par. 4.1, la capa superior de mortero u hormigón de recubrimiento (de tipo no aislante) en la que se embeben los cables calefactores deberá tener un espesor mínimo de 30 mm para asegurar disipación térmica y solidez.",
                    ref = "ITC-BT-46 §4.1"
                ),
Question(
                    q = "En instalaciones de calefacción mediante cables o folios radiantes empotrados en el techo, ¿cuál es la altura mínima reglamentaria del local?",
                    opts = listOf(
                        "3,5 metros",
                        "2,5 metros",
                        "3,0 metros",
                        "2,8 metros"
                    ),
                    a = 0,
                    exp = "La ITC-BT-46 par. 5.1 establece que la altura mínima de los locales acondicionados mediante elementos calefactores radiantes en el techo será de 3,5 metros para garantizar el confort fisiológico y evitar radiación cenital excesiva.",
                    ref = "ITC-BT-46 §5.1"
                ),
Question(
                    q = "¿Qué protección diferencial es obligatoria para cada circuito de calefacción por cable o folio radiante?",
                    opts = listOf(
                        "Diferencial selectivo de 300 mA",
                        "Diferencial de alta sensibilidad de 30 mA",
                        "Diferencial retardado de 100 mA",
                        "No se exige diferencial si hay toma de tierra"
                    ),
                    a = 1,
                    exp = "La ITC-BT-46 par. 3.2 estipula como obligatoria la instalación de una protección diferencial de alta sensibilidad con corriente asignada no superior a 30 mA para cada circuito de calefacción radiante.",
                    ref = "ITC-BT-46 §3.2"
                ),
Question(
                    q = "¿Cuál es la intensidad máxima admisible por fase y circuito en instalaciones de calefacción radiante?",
                    opts = listOf(
                        "16 A",
                        "20 A",
                        "25 A",
                        "32 A"
                    ),
                    a = 2,
                    exp = "Conforme a la ITC-BT-46 par. 3.2, cada circuito de calefacción estará protegido por un interruptor automático de corte omnipolar con un límite máximo de 25 A por fase y circuito.",
                    ref = "ITC-BT-46 §3.2"
                ),
Question(
                    q = "¿Qué valor mínimo de resistencia de aislamiento respecto a tierra debe comprobarse tras cubrir el elemento calefactor y antes de pavimentar?",
                    opts = listOf(
                        "50.000 Ω",
                        "100.000 Ω",
                        "500.000 Ω",
                        "250.000 Ω"
                    ),
                    a = 3,
                    exp = "La ITC-BT-46 par. 3.2 exige comprobar la resistencia de aislamiento eléctrico respecto a tierra antes de aplicar el pavimento definitivo, debiendo ser igual o superior a 250.000 Ω.",
                    ref = "ITC-BT-46 §3.2"
                ),
Question(
                    q = "¿Cuál es el radio de curvatura mínimo admisible para cables calefactores que no disponen de armadura metálica?",
                    opts = listOf(
                        "6 veces el diámetro exterior del cable",
                        "10 veces el diámetro exterior del cable",
                        "4 veces el diámetro exterior del cable",
                        "12 veces el diámetro exterior del cable"
                    ),
                    a = 0,
                    exp = "Según la ITC-BT-46 par. 3.4, el radio de curvatura en los cambios de dirección no deberá ser inferior a 6 veces el diámetro exterior del cable para modelos sin armadura (y 10 veces cuando dispongan de armadura).",
                    ref = "ITC-BT-46 §3.4"
                ),
Question(
                    q = "¿Qué valor máximo no debe sobrepasar el diferencial de temperatura del termostato de regulación ambiental?",
                    opts = listOf(
                        "0,5 K",
                        "1,5 K",
                        "2,0 K",
                        "3,0 K"
                    ),
                    a = 1,
                    exp = "La ITC-BT-46 apartado 6 prescribe que el diferencial de temperatura de disparo y rearme del termostato de control no deberá ser superior a 1,5 K para mantener la estabilidad térmica del recinto.",
                    ref = "ITC-BT-46 §6"
                ),
Question(
                    q = "¿Cuál es la temperatura superficial máxima admisible en el pavimento de un suelo radiante en zonas de ocupación continua según criterios de confort e ITC-BT-46?",
                    opts = listOf(
                        "35 ºC",
                        "40 ºC",
                        "Entre 28 ºC y 29 ºC por razones de salud circulatoria y bienestar fisiológico",
                        "No existe límite máximo si el mortero es autonivelante"
                    ),
                    a = 2,
                    exp = "Conforme a la normativa técnica de calefacción por suelo radiante y la ITC-BT-46, la temperatura superficial del suelo acabado en áreas de permanencia habitual de personas no debe sobrepasar los 28-29 ºC para evitar afecciones en el retorno venoso y garantizar el confort térmico.",
                    ref = "ITC-BT-46"
                ),
Question(
                    q = "¿Qué distancia mínima debe mantenerse entre pasadas o espiras consecutivas de un cable calefactor en suelos radiantes según la ITC-BT-46?",
                    opts = listOf(
                        "10 mm",
                        "20 mm",
                        "30 mm",
                        "Al menos 50 mm (5 cm) para evitar sobrecalentamientos locales por concentración térmica"
                    ),
                    a = 3,
                    exp = "La ITC-BT-46 apartado 3.4 y normas UNE de instalación establecen que los tramos adyacentes de un cable calefactor deben guardar una distancia de separación mínima de 50 mm, impidiendo que el calor acumulado deteriore el aislamiento de espiras contiguas.",
                    ref = "ITC-BT-46"
                ),
Question(
                    q = "¿Está permitido cruzar dos cables calefactores entre sí en una instalación de suelo o techo radiante según la ITC-BT-46?",
                    opts = listOf(
                        "No, está expresamente prohibido cruzar o superponer cables calefactores entre sí para evitar puntos calientes destructivos",
                        "Sí, siempre que el cruce se aísle con cinta vulcanizada",
                        "Se admite únicamente si los dos cables pertenecen al mismo circuito monofásico",
                        "Es obligatorio cruzarlos en las esquinas de la habitación"
                    ),
                    a = 0,
                    exp = "La ITC-BT-46 punto 3.4 prohíbe de forma terminante que los cables calefactores se crucen, se toquen o se superpongan en cualquier punto del tendido, ya que en el punto de contacto el calor disipado no se evacua adecuadamente y quemaría el aislante.",
                    ref = "ITC-BT-46"
                ),
Question(
                    q = "¿Cuál es el radio de curvatura mínimo admisible para cables calefactores dotados de armadura o pantalla metálica exterior según la ITC-BT-46?",
                    opts = listOf(
                        "5 veces el diámetro exterior",
                        "10 veces el diámetro exterior del cable",
                        "15 veces el diámetro exterior",
                        "20 veces el diámetro exterior"
                    ),
                    a = 1,
                    exp = "La ITC-BT-46 par. 3.4 fija que para cables calefactores provistos de armadura o envoltura metálica continua, el radio interior de curvatura en los cambios de sentido no podrá ser menor de 10 veces el diámetro exterior del cable (10d).",
                    ref = "ITC-BT-46"
                ),
Question(
                    q = "¿Qué condición es indispensable cumplir para poder instalar cables calefactores en el suelo del volumen 2 de un cuarto de baño según la ITC-BT-46?",
                    opts = listOf(
                        "Alimentarlos a 400 V trifásicos con neutro aislado",
                        "Colocar el cable directamente sobre las baldosas cerámicas",
                        "Disponer de una pantalla metálica o malla de equipotencialidad conectada a tierra y protección diferencial de 30 mA",
                        "Utilizar cables sin aislamiento de cobre desnudo"
                    ),
                    a = 2,
                    exp = "Conforme a la ITC-BT-46 pto. 2 e ITC-BT-27, en los volúmenes 2 y 3 de cuartos de baño solo se permite suelo radiante si los cables incorporan pantalla metálica conectada al conductor de protección (PE) o se instala una malla metálica equipotencial puesta a tierra sobre ellos, bajo protección diferencial IΔn <= 30 mA.",
                    ref = "ITC-BT-46"
                ),
Question(
                    q = "¿Cómo debe procederse si un cable calefactor debe atravesar forzosamente una junta de dilatación estructural del edificio según la ITC-BT-46?",
                    opts = listOf(
                        "Cortar el cable en la junta y empalmarlo con clemas",
                        "Tensar el cable al máximo para que absorba el movimiento",
                        "Rellenar la junta con cemento rápido sin holgura",
                        "Alojar el cable dentro de un tubo protector elástico o flexible con longitud y holgura suficiente a ambos lados de la junta"
                    ),
                    a = 3,
                    exp = "La ITC-BT-46 apartado 3.4 exige evitar el paso sobre juntas de dilatación; si fuera inevitable, el cable debe protegerse introduciéndolo en un manguito o tubo elástico con sobrante y holgura mecánica para que los movimientos estructurales no cizallen el conductor.",
                    ref = "ITC-BT-46"
                ),
Question(
                    q = "¿Qué densidad máxima de potencia superficial se recomienda con carácter general para calefacción continua en suelo radiante según la ITC-BT-46 y normas técnicas?",
                    opts = listOf(
                        "100 W/m² en zonas centrales habitables",
                        "250 W/m² en toda la vivienda",
                        "400 W/m² en dormitorios",
                        "500 W/m² con suelo cerámico"
                    ),
                    a = 0,
                    exp = "Según la ITC-BT-46 y las directrices técnicas de confort en suelo radiante, la densidad de potencia media superficial en zonas de estancia continua no debe superar los 100 W/m² para no rebasar el límite higiénico superficial de temperatura del pavimento.",
                    ref = "ITC-BT-46"
                ),
Question(
                    q = "¿Por qué no se debe cortar a medida un cable calefactor resistivo convencional en la obra según la ITC-BT-46?",
                    opts = listOf(
                        "Porque la empresa suministradora penaliza los cables cortos",
                        "Porque al acortar un cable resistivo disminuye su resistencia total y se dispara la potencia disipada por metro, quemándose inmediatamente",
                        "Porque se pierde la garantía del mortero autonivelante",
                        "Porque la tensión de red bajaría a la mitad"
                    ),
                    a = 1,
                    exp = "En cables calefactores de resistencia fija (ITC-BT-46 par. 3.2.1), la potencia disipada responde a P = V²/R. Si se recorta la longitud, la resistencia total disminuye, provocando un aumento drástico e incontrolado de la intensidad y del calor lineal que destruye el cable.",
                    ref = "ITC-BT-46"
                ),
Question(
                    q = "¿Dónde debe ubicarse la sonda de temperatura de suelo en una instalación de calefacción radiante según la ITC-BT-46?",
                    opts = listOf(
                        "Pegada directamente a la resistencia calefactora más próxima",
                        "En el exterior de la fachada orientada al norte",
                        "A media distancia entre dos espiras de cable calefactor, instalada dentro de un tubo ciego que permita su sustitución",
                        "En el interior de la caja de automáticos del cuadro eléctrico"
                    ),
                    a = 2,
                    exp = "La sonda térmica de suelo debe situarse equidistante entre dos pasadas contiguas de cable calefactor y alojada en el interior de un tubo corrugado con extremo ciego sellado, garantizando una lectura térmica representativa y facilitando su recambio en caso de avería.",
                    ref = "ITC-BT-46"
                ),
Question(
                    q = "¿Qué tiempo mínimo de fraguado o curado del mortero debe respetarse antes de poner en servicio por primera vez un suelo radiante según la ITC-BT-46 y normas de edificación?",
                    opts = listOf(
                        "24 horas",
                        "48 horas",
                        "7 días",
                        "Al menos 21 a 28 días (según el tipo de cemento o mortero) para evitar fisuras y desprendimientos de pavimento"
                    ),
                    a = 3,
                    exp = "La ITC-BT-46 y las normas de construcción UNE imponen que el recrecido de mortero u hormigón complete su fraguado hidráulico (típicamente 21 a 28 días) antes de encender la calefacción, iniciándose el calentamiento de forma gradual para evitar dilataciones violentas y grietas.",
                    ref = "ITC-BT-46"
                ),
Question(
                    q = "¿Qué elemento debe disponerse bajo los cables calefactores en el suelo antes de verter el mortero según la ITC-BT-46?",
                    opts = listOf(
                        "Una capa de aislamiento térmico resistente a la compresión con barrera impermeable para evitar pérdidas de calor hacia el forjado inferior",
                        "Una plancha de plomo de 10 mm de espesor",
                        "Arena suelta de playa sin compactar",
                        "Un panel de madera aglomerada sin tratar"
                    ),
                    a = 0,
                    exp = "Conforme a la ITC-BT-46 par. 4.1, bajo el tendido calefactor debe instalarse un aislante térmico rígido (como poliestireno extruido de alta densidad) con lámina impermeable, garantizando que el flujo calorífico se transmita hacia el interior de la vivienda y no hacia plantas inferiores.",
                    ref = "ITC-BT-46"
                ),
Question(
                    q = "¿Qué ensayo debe realizarse obligatoriamente antes y después del vertido del mortero sobre los cables calefactores según la ITC-BT-46?",
                    opts = listOf(
                        "Medición de radiación electromagnética con osciloscopio",
                        "Medición de la resistencia de aislamiento dieléctrico y de la resistencia óhmica de los conductores",
                        "Pesaje dinámico del forjado",
                        "Prueba de estanqueidad con humo coloreado"
                    ),
                    a = 1,
                    exp = "La ITC-BT-46 pto. 3.2 estipula que es obligatorio verificar con polímetro y megóhmetro la resistencia óhmica y el aislamiento a tierra del elemento calefactor tanto antes de hormigonar como durante y después del curado, detectando inmediatamente cualquier daño mecánico accidental.",
                    ref = "ITC-BT-46"
                ),
Question(
                    q = "¿Qué tipo de mortero debe emplearse para recubrir los cables calefactores de suelo radiante según la ITC-BT-46?",
                    opts = listOf(
                        "Mortero poroso aligerado con bolitas de poliestireno expandido",
                        "Mortero de yeso rápido para tabiquería seca",
                        "Mortero denso no aislante, homogéneo y sin oquedades de aire que envuelva perfectamente los cables",
                        "Mortero refractario para chimeneas de leña"
                    ),
                    a = 2,
                    exp = "La ITC-BT-46 par. 4.1 exige que el mortero de recubrimiento sea de tipo no aislante, compacto y bien compactado o autonivelante para que transmita eficazmente el calor y no forme burbujas de aire sobre la superficie de los cables.",
                    ref = "ITC-BT-46"
                ),
Question(
                    q = "En una instalación de folio radiante en techo, ¿qué limitación de potencia por circuito individual fija la ITC-BT-46?",
                    opts = listOf(
                        "No superior a 500 W",
                        "Máximo 1.000 W por vivienda",
                        "Máximo 10 A en trifásica",
                        "La intensidad asignada no sobrepasará los 25 A por fase y circuito"
                    ),
                    a = 3,
                    exp = "De acuerdo con la ITC-BT-46 par. 3.2, la potencia de cada circuito individual de calefacción radiante (sea por suelo o techo) queda limitada por el calibre de su protección contra sobreintensidades, que no podrá exceder de 25 A por fase.",
                    ref = "ITC-BT-46"
                ),
Question(
                    q = "¿Qué advertencia debe reflejarse en los planos de la vivienda y en el cuadro eléctrico según la ITC-BT-46?",
                    opts = listOf(
                        "La prohibición de clavar puntas, taladrar o empotrar elementos en el suelo o techo donde discurran los elementos calefactores",
                        "La recomendación de fregar el suelo con agua hirviendo",
                        "La obligatoriedad de usar calzado de madera dentro de la vivienda",
                        "El horario obligatorio de encendido nocturno"
                    ),
                    a = 0,
                    exp = "La ITC-BT-46 par. 3.4 y directrices de seguridad prescriben que el usuario debe contar con un plano o croquis del trazado de los cables radiantes y una advertencia expresa contra la realización de taladros o perforaciones mecánicas en el suelo que pudieran seccionar los conductores.",
                    ref = "ITC-BT-46"
                ),
Question(
                    q = "¿Qué tensión de ensayo en corriente continua debe aplicarse para medir la resistencia de aislamiento respecto a tierra según la ITC-BT-46?",
                    opts = listOf(
                        "100 V c.c.",
                        "500 V c.c. con resultado mínimo de 250.000 Ω (0,25 MΩ)",
                        "1.500 V c.a.",
                        "24 V c.c."
                    ),
                    a = 1,
                    exp = "Conforme a la ITC-BT-46 par. 3.2 y concordantes de medida de aislamiento en baja tensión, el ensayo de aislamiento a tierra se realiza aplicando una tensión de 500 V en corriente continua, debiendo arrojar una lectura no inferior a 250.000 Ω.",
                    ref = "ITC-BT-46"
                ),
Question(
                    q = "¿Cómo deben fijarse los cables calefactores sobre el soporte antes de verter el recrecido según la ITC-BT-46?",
                    opts = listOf(
                        "Atados con alambre de espino galvanizado",
                        "Clavados con puntas de acero directamente sobre el aislante perforando la cubierta",
                        "Mediante bandas o rieles de fijación troquelados, mallas electrosoldadas o grapas plásticas diseñadas para no pellizcar el cable",
                        "Pegados con cola de contacto inflamable"
                    ),
                    a = 2,
                    exp = "La ITC-BT-46 par. 3.4 estipula que la inmovilización de los cables sobre el suelo se realizará mediante guías plásticas ranuradas, pletinas troqueladas o bridas suaves sobre malla, evitando cualquier elemento cortante o deformaciones mecánicas que dañen la funda exterior.",
                    ref = "ITC-BT-46"
                ),
Question(
                    q = "¿Qué sucede si se cubre un suelo radiante con moquetas gruesas o muebles macizos sin patas según las recomendaciones técnicas de la ITC-BT-46?",
                    opts = listOf(
                        "La habitación se calienta el triple de rápido",
                        "El consumo eléctrico disminuye a cero",
                        "El cable calefactor absorbe la humedad de la moqueta",
                        "Se produce un atrapamiento térmico que sobrecalienta el pavimento y puede dañar el cable o disparar la sonda limitadora"
                    ),
                    a = 3,
                    exp = "La ITC-BT-46 y la física de transferencia térmica alertan de que colocar aislantes gruesos (alfombras pesadas, muebles con zócalo cerrado directo al suelo) estrangula la disipación del calor hacia el ambiente, concentrando temperaturas excesivas en el forjado con riesgo de fisura o disparo continuo de seguridad.",
                    ref = "ITC-BT-46"
                ),
Question(
                    q = "¿Qué grado de protección IP mínimo deben tener los folios radiantes de techo según la ITC-BT-46?",
                    opts = listOf(
                        "IP00",
                        "Al menos IPX1 (o IPX4 en locales húmedos como baños y cocinas)",
                        "IP68 permanente",
                        "IP10 exclusivamente"
                    ),
                    a = 1,
                    exp = "Según la ITC-BT-46 punto 5, los folios o placas calefactoras instaladas en techos deben contar como mínimo con grado de protección IPX1 contra caídas verticales de gotas de condensación, elevándose a IPX4 en cocinas o cuartos de baño según las condiciones del local.",
                    ref = "ITC-BT-46"
                ),
Question(
                    q = "¿Qué función cumple el termostato de seguridad con sonda de contacto de suelo en pavimentos de madera sobre suelo radiante según la ITC-BT-46?",
                    opts = listOf(
                        "Medir el nivel de humedad relativa del aire exterior",
                        "Detectar escapes de agua subterránea",
                        "Encender las luces de emergencia",
                        "Limitar la temperatura de contacto del pavimento a un máximo seguro (típicamente 27 ºC) para evitar alabeos y deformaciones del parqué"
                    ),
                    a = 3,
                    exp = "La ITC-BT-46 apartado 6 y normas de pavimentos de madera exigen incorporar un limitador de temperatura máxima en la masa del suelo que desactive el circuito calefactor si la superficie alcanza 27 ºC, protegiendo las tarimas de madera noble frente a contracciones, grietas y decoloración térmica.",
                    ref = "ITC-BT-46"
                ),
Question(
                    q = "Los conductores que alimentan a un único motor eléctrico deben dimensionarse para una intensidad mínima respecto a su plena carga de:",
                    opts = listOf(
                        "125 % de la intensidad a plena carga",
                        "100 % de la intensidad a plena carga",
                        "115 % de la intensidad a plena carga",
                        "150 % de la intensidad a plena carga"
                    ),
                    a = 0,
                    exp = "La ITC-BT-47 apartado 3.1 dispone que los conductores de conexión que alimentan a un solo motor deben estar dimensionados para una intensidad no inferior al 125 % de la intensidad a plena carga del motor.",
                    ref = "ITC-BT-47 §3.1"
                ),
Question(
                    q = "Al dimensionar los conductores que alimentan a una línea con varios motores, ¿qué criterio reglamentario debe aplicarse?",
                    opts = listOf(
                        "El 100 % de la suma aritmética de todos los motores",
                        "El 125 % del motor de mayor potencia más la suma de intensidades a plena carga del resto",
                        "El 125 % de la suma total de intensidades",
                        "El 150 % del motor más potente exclusivamente"
                    ),
                    a = 1,
                    exp = "Según la ITC-BT-47 par. 3.2, la línea principal que alimenta a un grupo de motores se dimensionará para al menos el 125 % de la intensidad a plena carga del motor de mayor potencia más la intensidad nominal de todos los demás.",
                    ref = "ITC-BT-47 §3.2"
                ),
Question(
                    q = "En motores de rotor devanado en régimen continuo, ¿para qué intensidad deben dimensionarse los conductores del circuito secundario?",
                    opts = listOf(
                        "Para el 100 % de la intensidad primaria del estator",
                        "Para el 85 % de la intensidad secundaria",
                        "Para el 125 % de la intensidad a plena carga del rotor",
                        "Para la corriente de cortocircuito rotórica"
                    ),
                    a = 2,
                    exp = "La ITC-BT-47 par. 3.1 fija que los conductores secundarios de conexión rotórica en motores de anillos rozantes en servicio continuo deben dimensionarse para el 125 % de la intensidad a plena carga del rotor.",
                    ref = "ITC-BT-47 §3.1"
                ),
Question(
                    q = "¿Qué sección mínima deben tener los conductores secundarios en motores de rotor devanado destinados a servicio intermitente?",
                    opts = listOf(
                        "No inferior a la correspondiente al 50 % de la intensidad del rotor",
                        "No inferior a la del 100 % de la corriente de arranque",
                        "No inferior a la de fase del estator",
                        "No inferior a la correspondiente al 85 % de la intensidad a plena carga del rotor"
                    ),
                    a = 3,
                    exp = "Conforme a la ITC-BT-47 par. 3.1, en motores para servicio intermitente los conductores del circuito del rotor no tendrán en ningún caso una sección inferior a la correspondiente al 85 % de la intensidad a plena carga rotórica.",
                    ref = "ITC-BT-47 §3.1"
                ),
Question(
                    q = "¿A partir de qué potencia nominal es preceptivo que los motores eléctricos incorporen limitación de la corriente de arranque?",
                    opts = listOf(
                        "Superior a 0,75 kW",
                        "Superior a 1,5 kW",
                        "Superior a 3 kW",
                        "Superior a 5 kW"
                    ),
                    a = 0,
                    exp = "La ITC-BT-47 par. 6 establece con carácter general que los motores de potencia superior a 0,75 kW deben disponer de dispositivos de arranque progresivo o reóstatos que limiten la punta de corriente en la red.",
                    ref = "ITC-BT-47 §6"
                ),
Question(
                    q = "En el cálculo de la corriente de arranque para motores de ascensores y elevadores, ¿por qué coeficiente se multiplica la intensidad normal de carga?",
                    opts = listOf(
                        "1,15",
                        "1,30",
                        "1,50",
                        "2,00"
                    ),
                    a = 1,
                    exp = "Según la ITC-BT-47 par. 6, para los motores de ascensores la intensidad absorbida a plena carga para elevar la carga nominal se multiplicará por el coeficiente 1,3 para verificar las relaciones de corriente de arranque admisibles.",
                    ref = "ITC-BT-47 §6"
                ),
Question(
                    q = "En motores trifásicos, ¿qué contingencia específica debe cubrir obligatoriamente la protección contra sobrecargas?",
                    opts = listOf(
                        "El desequilibrio de impedancias de tierra",
                        "La inversión del sentido de giro",
                        "El riesgo de falta de tensión en una de sus fases",
                        "La elevación de armónicos de tercer orden"
                    ),
                    a = 2,
                    exp = "La ITC-BT-47 par. 4 exige que los motores estén protegidos contra sobrecargas en todas sus fases, y en motores trifásicos este dispositivo debe proteger específicamente contra el riesgo de funcionamiento bifásico por falta de una fase.",
                    ref = "ITC-BT-47 §4"
                ),
Question(
                    q = "¿Qué condición de seguridad debe cumplir la protección térmica en motores con arrancador estrella-triángulo?",
                    opts = listOf(
                        "Proteger únicamente durante la conexión en triángulo",
                        "Proteger únicamente en el momento del arranque en estrella",
                        "Desconectarse automáticamente durante el cambio de contacto",
                        "Asegurar la protección contra sobrecargas tanto en la conexión en estrella como en triángulo"
                    ),
                    a = 3,
                    exp = "La ITC-BT-47 par. 4 prescribe expresamente que en motores dotados de arrancador estrella-triángulo debe asegurarse la protección contra sobrecargas para ambas fases de la marcha: tanto en la posición estrella como en triángulo.",
                    ref = "ITC-BT-47 §4"
                ),
Question(
                    q = "¿Cuándo es obligatorio instalar un dispositivo de protección contra falta de tensión en un motor?",
                    opts = listOf(
                        "Cuando el arranque espontáneo tras restablecerse la tensión entrañe peligro para personas o maquinaria",
                        "En todos los motores monofásicos sin excepción",
                        "Únicamente en motores de potencia superior a 50 kW",
                        "Solo si están conectados a una red rural aislada"
                    ),
                    a = 0,
                    exp = "La ITC-BT-47 par. 5 hace obligatoria la protección contra falta de tensión siempre que el arranque no intencionado tras el restablecimiento del suministro pueda ocasionar accidentes a personas o daños mecánicos.",
                    ref = "ITC-BT-47 §5"
                ),
Question(
                    q = "¿Qué distancia mínima de separación respecto a los muros deben guardar los reóstatos de arranque y resistencias?",
                    opts = listOf(
                        "2 cm",
                        "5 cm",
                        "10 cm",
                        "15 cm"
                    ),
                    a = 1,
                    exp = "Según la ITC-BT-47 apartado 7, los reóstatos y resistencias se colocarán distanciados de los muros al menos cinco centímetros para permitir una adecuada ventilación y evitar la transmisión de calor a las paredes.",
                    ref = "ITC-BT-47 §7"
                ),
Question(
                    q = "¿Qué tipo de fusibles están diseñados específicamente para acompañar al relé térmico en la protección de motores protegiendo solo contra cortocircuitos según la ITC-BT-47?",
                    opts = listOf(
                        "Fusibles tipo gG de uso general exclusivamente",
                        "Fusibles ultra-rápidos tipo aR para semiconductores",
                        "Fusibles tipo aM (acompañamiento de motor) que soportan la punta de arranque",
                        "Fusibles domésticos tipo gL no calibrados"
                    ),
                    a = 2,
                    exp = "Los fusibles tipo aM (acompañamiento de motor) presentan una curva de fusión lenta ante sobrecargas moderadas para aguantar la corriente de arranque sin fundirse, protegiendo exclusivamente frente a cortocircuitos; por ello, la ITC-BT-47 exige asociarlos siempre a un relé térmico para la protección contra sobrecargas.",
                    ref = "ITC-BT-47"
                ),
Question(
                    q = "¿Cuál es la relación máxima reglamentaria admisible entre la intensidad de arranque (Ia) y la intensidad nominal (In) para motores de más de 15 kW según la tabla de la ITC-BT-47?",
                    opts = listOf(
                        "Ia / In <= 4,5",
                        "Ia / In <= 3,0",
                        "Ia / In <= 2,0",
                        "Ia / In <= 1,5"
                    ),
                    a = 3,
                    exp = "Según la tabla del apartado 6 de la ITC-BT-47, para motores de potencia superior a 15 kW la relación entre la corriente de arranque y la de plena carga no debe rebasar 1,5 para evitar caídas de tensión bruscas en la red de distribución.",
                    ref = "ITC-BT-47"
                ),
Question(
                    q = "¿Por qué factor se reduce la corriente de arranque en la red al arrancar un motor trifásico mediante arrancador estrella-triángulo según la ITC-BT-47?",
                    opts = listOf(
                        "Se reduce a la tercera parte (1/3 o aproximadamente el 33 %) de la corriente de arranque directo",
                        "Se reduce exactamente a la mitad (50 %)",
                        "Se reduce a una décima parte (10 %)",
                        "No varía la corriente pero se duplica la velocidad"
                    ),
                    a = 0,
                    exp = "En el arranque estrella-triángulo, al conectar los devanados en estrella durante el arranque, la tensión aplicada a cada fase es Vf = VL / √3, por lo que la corriente de línea se reduce a 1/3 (33%) de la que absorbería en arranque directo en triángulo (ITC-BT-47 pto. 6).",
                    ref = "ITC-BT-47"
                ),
Question(
                    q = "¿A partir de qué potencia nominal exige la ITC-BT-47 que los motores dispongan de protección obligatoria contra sobrecargas?",
                    opts = listOf(
                        "Superior a 0,1 kW",
                        "Superior a 0,5 kW",
                        "Superior a 2,2 kW",
                        "Superior a 10 kW"
                    ),
                    a = 1,
                    exp = "La ITC-BT-47 par. 4 establece que todos los motores de potencia nominal superior a 0,5 kW deberán estar obligatoriamente protegidos contra sobrecargas en todos sus conductores de fase.",
                    ref = "ITC-BT-47"
                ),
Question(
                    q = "¿Qué intensidad debe soportar el cableado de conexión entre el arrancador estrella-triángulo y los bornes del motor según la ITC-BT-47?",
                    opts = listOf(
                        "El 150 % de la intensidad total del cuadro",
                        "El 50 % de la corriente de cortocircuito",
                        "Al menos el 125 % de la intensidad de fase en triángulo (equivalente a 1,25 x In / √3 ≈ 0,72 x In)",
                        "El 100 % de la intensidad de arranque en estrella"
                    ),
                    a = 2,
                    exp = "Conforme a la ITC-BT-47 apartado 3.1, los conductores que unen el equipo de arranque estrella-triángulo con las seis bornas del estator del motor conducen únicamente la corriente de fase del devanado, debiendo dimensionarse para el 125 % de dicha corriente de fase.",
                    ref = "ITC-BT-47"
                ),
Question(
                    q = "¿Qué ocurre en un motor trifásico en funcionamiento continuo si se funde un fusible de una de las fases y carece de relé térmico con dispositivo diferencial?",
                    opts = listOf(
                        "El motor se invierte de giro instantáneamente",
                        "El motor genera energía y la inyecta a la red",
                        "La velocidad del motor se multiplica por dos",
                        "El motor continúa girando en régimen bifásico, aumentando drásticamente la corriente en las otras dos fases hasta quemar los devanados"
                    ),
                    a = 3,
                    exp = "La marcha en dos fases (bifásica) provocada por la falta de una fase obliga al motor a absorber una sobrecorriente de hasta el 173 % en las fases sanas para mantener el par mecánico, destruyendo térmicamente los aislamientos si el relé no dispone de función diferencial de corte rápido (ITC-BT-47 par. 4).",
                    ref = "ITC-BT-47"
                ),
Question(
                    q = "¿Qué dispositivo de mando garantiza que un motor no vuelva a arrancar por sí solo tras un corte de suministro según la ITC-BT-47?",
                    opts = listOf(
                        "Un circuito de maniobra por pulsadores de marcha/paro con contacto de autorretención en el contactor (o bobina de mínima tensión)",
                        "Un interruptor manual basculante de dos posiciones fijas",
                        "Un interruptor horario analógico de levas",
                        "Un enchufe macho trifásico directo a toma"
                    ),
                    a = 0,
                    exp = "Conforme a la ITC-BT-47 apartado 5, la retención por contacto auxiliar normalmente abierto (pulsador de marcha NA en paralelo con contacto 13-14 del contactor) se desenclava automáticamente al caer la tensión, impidiendo que el motor arranque accidentalmente cuando regrese la luz.",
                    ref = "ITC-BT-47"
                ),
Question(
                    q = "¿Cuál es la relación máxima admisible entre corriente de arranque y nominal (Ia / In) para motores de potencia comprendida entre 0,75 kW y 1,5 kW según la ITC-BT-47?",
                    opts = listOf(
                        "Ia / In <= 2,0",
                        "Ia / In <= 4,5",
                        "Ia / In <= 6,0",
                        "Ia / In <= 1,5"
                    ),
                    a = 1,
                    exp = "La tabla del apartado 6 de la ITC-BT-47 fija para motores de potencia mayor de 0,75 kW y menor o igual a 1,5 kW un factor máximo de corriente de arranque Ia / In de 4,5 veces la intensidad nominal a plena carga.",
                    ref = "ITC-BT-47"
                ),
Question(
                    q = "¿Qué elemento es OBLIGATORIO instalar en la proximidad inmediata de un motor accesible a los operarios según la ITC-BT-47 y normativa de máquinas?",
                    opts = listOf(
                        "Un termómetro de mercurio",
                        "Una lámpara estroboscópica de señalización",
                        "Un dispositivo de corte o seccionamiento para maniobras de mantenimiento que pueda bloquearse en posición abierta",
                        "Un juego de destornilladores dieléctricos fijado a la carcasa"
                    ),
                    a = 2,
                    exp = "La ITC-BT-47 apartado 8 y normas de seguridad en máquinas exigen un interruptor de corte en carga o seccionador visible desde el motor (o provisto de enclavamiento por candado en posición de 'abierto') para garantizar la seguridad del personal durante intervenciones mecánicas o eléctricas.",
                    ref = "ITC-BT-47"
                ),
Question(
                    q = "¿Cómo debe ajustarse el calibre de disparo del relé térmico cuando está conectado directamente en la línea de alimentación de un motor trifásico según la ITC-BT-47?",
                    opts = listOf(
                        "Al 150 % de la corriente de arranque",
                        "Al 200 % de la potencia en vatios",
                        "Al doble de la intensidad en vacío",
                        "A la intensidad nominal a plena carga (In) indicada en la placa de características del motor"
                    ),
                    a = 3,
                    exp = "La ITC-BT-47 punto 4 determina que los relés de protección contra sobrecargas colocados en la línea de alimentación deben regularse exactamente a la intensidad nominal asignada (In) del motor para la tensión de servicio, asegurando su disparo ante cualquier sobrecarga permanente.",
                    ref = "ITC-BT-47"
                ),
Question(
                    q = "¿Qué dispositivo de parada de emergencia debe preverse en máquinas accionadas por motores que presenten peligro de atrapamiento según la ITC-BT-47?",
                    opts = listOf(
                        "Pulsadores de seta con retención mecánica y desenclavamiento manual por giro o llave, o cables perimetrales de parada de emergencia",
                        "Un interruptor de pedal sin enclavamiento",
                        "Un conmutador de cruce doméstico",
                        "Un reloj temporizador de cuenta atrás"
                    ),
                    a = 0,
                    exp = "Conforme a la ITC-BT-47 pto. 8 y la Directiva de Máquinas, en accionamientos peligrosos deben instalarse paradas de emergencia accesibles y señalizadas (setas rojas con fondo amarillo de enganche mecánico) que corten de inmediato la potencia al motor mediante corte omnipolar.",
                    ref = "ITC-BT-47"
                ),
Question(
                    q = "¿Qué criterio rige para la protección térmica de motores mediante sondas PTC (termistores) embebidas en los devanados según la ITC-BT-47?",
                    opts = listOf(
                        "Miden la tensión de línea del primario",
                        "Detectan directamente el calentamiento interno del cobre del devanado, siendo ideales para servicios intermitentes y arranques frecuentes",
                        "Actúan únicamente cuando el motor está apagado",
                        "Sustituyen a la toma de tierra del chasis metálico"
                    ),
                    a = 1,
                    exp = "Las sondas térmicas de tipo termistor (PTC) alojadas en las cabezas de bobinas censan la temperatura real del aislamiento del devanado, actuando de forma precisa ante sobrecalentamientos causados por ventilación defectuosa, temperatura ambiente elevada o arranques reiterados donde el relé térmico bimetálico externo no es suficiente.",
                    ref = "ITC-BT-47"
                ),
Question(
                    q = "¿Cuál es la relación máxima admisible de corriente de arranque (Ia / In) para motores de potencia entre 5 kW y 15 kW según la ITC-BT-47?",
                    opts = listOf(
                        "Ia / In <= 3,5",
                        "Ia / In <= 3,0",
                        "Ia / In <= 2,0",
                        "Ia / In <= 1,0"
                    ),
                    a = 2,
                    exp = "La tabla del apartado 6 de la ITC-BT-47 estipula que los motores con potencia útil comprendida entre 5 kW y 15 kW deben incorporar métodos de arranque que limiten la relación Ia / In a un valor máximo de 2,0.",
                    ref = "ITC-BT-47"
                ),
Question(
                    q = "¿Qué dato OBLIGATORIO debe constar en la placa de características de todo motor eléctrico según la ITC-BT-47?",
                    opts = listOf(
                        "El número de teléfono del bobinador",
                        "El precio de venta al público en fábrica",
                        "El color de la pintura anticorrosiva de la carcasa",
                        "Tensión nominal, intensidad nominal a plena carga, potencia útil en kW, factor de potencia (cos phi) y velocidad en rpm"
                    ),
                    a = 3,
                    exp = "De acuerdo con la ITC-BT-47 par. 2 y la norma UNE-EN 60034-1, la placa de características indeleble del motor debe indicar de forma clara la potencia nominal, las tensiones de conexión (ej. 230/400 V o 400/690 V), las intensidades correspondientes, la velocidad nominal, el cos phi y la clase térmica de aislamiento.",
                    ref = "ITC-BT-47"
                ),
Question(
                    q = "¿Qué distancia mínima de separación respecto al suelo deben guardar las resistencias y reóstatos de arranque de motores según la ITC-BT-47?",
                    opts = listOf(
                        "Al menos 30 cm cuando no estén provistos de envolvente protectora adecuada",
                        "No se exige separación si el suelo es de losas cerámicas",
                        "Exactamente 5 mm",
                        "Deben estar enterradas bajo tierra 1 metro"
                    ),
                    a = 0,
                    exp = "La ITC-BT-47 apartado 7 dispone que las resistencias y reóstatos montados sin envolvente cerrada deben colocarse separados del suelo al menos 30 cm, y a 5 cm de muros incombustibles, evitando la ignición de polvo o suciedad y asegurando el tiro natural de aire de refrigeración.",
                    ref = "ITC-BT-47"
                ),
Question(
                    q = "¿Qué ventaja ofrece un arrancador electrónico progresivo (soft-starter) frente al arranque estrella-triángulo tradicional en motores según la ITC-BT-47?",
                    opts = listOf(
                        "Genera corriente continua para frenar las poleas",
                        "Controla la tensión de forma continua mediante tiristores, eliminando los picos transitorios de corriente y los golpes de ariete mecánicos",
                        "Aumenta la velocidad nominal al triple",
                        "Permite prescindir de la toma de tierra del motor"
                    ),
                    a = 1,
                    exp = "Los arrancadores estáticos suaves (soft starters) modulan el ángulo de conducción de tiristores en antiparalelo, proporcionando una rampa de tensión suave y continua que suprime el brusco transitorio de conmutación de estrella a triángulo y protege las transmisiones mecánicas (ITC-BT-47 pto. 6).",
                    ref = "ITC-BT-47"
                ),
Question(
                    q = "¿Cuál es la relación máxima de corriente de arranque (Ia / In) fijada para motores de entre 1,5 kW y 5 kW según la tabla de la ITC-BT-47?",
                    opts = listOf(
                        "Ia / In <= 4,5",
                        "Ia / In <= 3,5",
                        "Ia / In <= 3,0",
                        "Ia / In <= 1,5"
                    ),
                    a = 2,
                    exp = "La escala de la ITC-BT-47 apartado 6 prescribe un factor límite de corriente de arranque Ia / In de 3,0 para los motores cuya potencia nominal se halle comprendida entre 1,5 kW y 5 kW.",
                    ref = "ITC-BT-47"
                ),
Question(
                    q = "¿Qué esquema de conexión en bornes debe realizarse en un motor trifásico de 230/400 V alimentado por una red trifásica de 400 V entre fases según la ITC-BT-47?",
                    opts = listOf(
                        "Conexión en triángulo (pólizas en paralelo U1-W2, V1-U2, W1-V2)",
                        "Conexión monofásica con condensador permanente",
                        "Conexión en doble estrella serie",
                        "Conexión en estrella (puenteando los bornes W2, U2, V2 y aplicando fases a U1, V1, W1)"
                    ),
                    a = 3,
                    exp = "En un motor de 230/400 V, cada devanado soporta una tensión simple máxima de 230 V. En una red de 400 V entre fases, debe conectarse obligatoriamente en estrella, ya que la tensión por bobina será 400 / √3 = 230 V. Si se conectara en triángulo a 400 V se quemaría de inmediato.",
                    ref = "ITC-BT-47"
                ),
Question(
                    q = "¿Cómo debe efectuarse la puesta a tierra de la carcasa metálica de un motor montado sobre bancada antivibratoria de caucho según la ITC-BT-47 e ITC-BT-18?",
                    opts = listOf(
                        "No hace falta tierra porque el caucho aísla el motor del suelo",
                        "Mediante una trenza flexible de cobre que puentee los soportes elásticos uniendo la carcasa con el conductor de protección general",
                        "Clavando una pica en la polea de transmisión",
                        "Mediante la tubería de refrigeración de plástico"
                    ),
                    a = 1,
                    exp = "Al estar el motor montado sobre aisladores elásticos de goma (silentblocks), la bancada pierde continuidad eléctrica con el suelo, por lo que la ITC-BT-47 y la ITC-BT-18 exigen un puente flexible mediante trenza de cobre de sección adecuada que conecte sólidamente el chasis al conductor PE.",
                    ref = "ITC-BT-47"
                ),
Question(
                    q = "¿Qué consideración se debe tener al arrancar motores mediante variadores de frecuencia (VFD) según las prescripciones de la ITC-BT-47 y compatibilidad electromagnética?",
                    opts = listOf(
                        "Los variadores no limitan la corriente de arranque",
                        "Deben conectarse sin cables apantallados para emitir radio",
                        "El neutro debe cortarse con un fusible ultrarrápido",
                        "Deben utilizarse cables apantallados o canalizaciones metálicas continuas puestas a tierra y filtros CEM para suprimir perturbaciones de alta frecuencia"
                    ),
                    a = 3,
                    exp = "Los variadores de frecuencia controlan la velocidad y limitan la corriente de arranque (Ia <= In), pero los pulsos PWM de alta frecuencia generan armónicos e interferencias, requiriendo cables apantallados simétricos conectados a tierra a 360º en ambos extremos y filtros de red (ITC-BT-47 y normas CEM).",
                    ref = "ITC-BT-47"
                ),
Question(
                    q = "¿A partir de qué tensión en arrollamientos o elementos bajo tensión deben ser inaccesibles los transformadores al alcance de personas no especializadas?",
                    opts = listOf(
                        "Superior a 50 V",
                        "Superior a 24 V",
                        "Superior a 120 V",
                        "Superior a 230 V"
                    ),
                    a = 0,
                    exp = "La ITC-BT-48 apartado 2.1 establece que los transformadores que puedan quedar al alcance de personas no especializadas estarán construidos o ubicados de modo que sus arrollamientos bajo tensión superior a 50 V sean inaccesibles.",
                    ref = "ITC-BT-48 §2.1"
                ),
Question(
                    q = "En transformadores fijos montados cerca de partes combustibles, ¿cuándo se exige colocar pantallas incombustibles de separación a 1 cm?",
                    opts = listOf(
                        "Para potencias superiores a 10 kVA",
                        "Para potencias de hasta 3.000 VA",
                        "En transformadores trifásicos únicamente",
                        "Solo si el aislamiento es seco con resina"
                    ),
                    a = 1,
                    exp = "Conforme a la ITC-BT-48 par. 2.1, cuando la potencia del transformador sea inferior o igual a 3.000 VA la separación a pantallas incombustibles será de 1 cm, aumentándose proporcionalmente para potencias superiores.",
                    ref = "ITC-BT-48 §2.1"
                ),
Question(
                    q = "Al conectar un autotransformador a una red con neutro distribuido, ¿cómo debe conectarse el arrollamiento común?",
                    opts = listOf(
                        "Al conductor de fase de mayor tensión",
                        "Al conductor de tierra de protección",
                        "El borne del extremo del arrollamiento común debe unirse al conductor neutro",
                        "Debe dejarse flotante y aislado"
                    ),
                    a = 2,
                    exp = "La ITC-BT-48 par. 2.1 determina que en la conexión de un autotransformador a una fuente con neutro, el borne del extremo del arrollamiento común al primario y secundario se unirá preceptivamente al conductor neutro.",
                    ref = "ITC-BT-48 §2.1"
                ),
Question(
                    q = "¿En qué circunstancia queda expresamente prohibido el empleo de autotransformadores según la ITC-BT-48?",
                    opts = listOf(
                        "En instalaciones industriales con potencia mayor a 5 kVA",
                        "En suministros monofásicos a 230 V",
                        "En equipos con aislamiento de clase II",
                        "Si los dos circuitos conectados no tienen un aislamiento previsto para la tensión mayor"
                    ),
                    a = 3,
                    exp = "La ITC-BT-48 par. 2.1 prohíbe el uso de autotransformadores si ambos circuitos conectados a ellos no disponen de un nivel de aislamiento coordinado y dimensionado para soportar la tensión más alta presente.",
                    ref = "ITC-BT-48 §2.1"
                ),
Question(
                    q = "¿Para qué rango de intensidades en régimen permanente deben dimensionarse los aparatos de mando y protección de los condensadores?",
                    opts = listOf(
                        "De 1,5 a 1,8 veces la intensidad nominal asignada",
                        "Exactamente para el 100 % de su corriente asignada",
                        "De 1,1 a 1,2 veces la intensidad nominal",
                        "De 2,0 a 2,5 veces la intensidad de cortocircuito"
                    ),
                    a = 0,
                    exp = "La ITC-BT-48 par. 2.3 exige que los dispositivos de maniobra y protección de condensadores soporten de 1,5 a 1,8 veces la intensidad nominal para considerar armónicos y sobreintensidades transitorias de conexión.",
                    ref = "ITC-BT-48 §2.3"
                ),
Question(
                    q = "Si la carga residual de una batería de condensadores entraña peligro para las personas, ¿qué elemento debe incorporar obligatoriamente?",
                    opts = listOf(
                        "Un extintor automático de CO2",
                        "Un dispositivo automático de descarga o un rótulo de advertencia bien visible",
                        "Un aislamiento reforzado de silicona",
                        "Una pica de tierra independiente"
                    ),
                    a = 1,
                    exp = "Según la ITC-BT-48 par. 2.3, los condensadores cuya carga residual tras la desconexión pueda poner en riesgo a personas deberán incorporar un dispositivo automático de descarga o señalizarse con un rótulo de peligro visible.",
                    ref = "ITC-BT-48 §2.3"
                ),
Question(
                    q = "Los condensadores que no incluyan marcado con indicación de temperatura máxima admisible no podrán utilizarse en locales donde la temperatura sea:",
                    opts = listOf(
                        "Superior a 30 ºC",
                        "Superior a 40 ºC",
                        "De 50 ºC o mayor",
                        "Superior a 60 ºC"
                    ),
                    a = 2,
                    exp = "La ITC-BT-48 par. 2.3 prohíbe instalar condensadores sin indicación de temperatura máxima en aquellos emplazamientos donde la temperatura ambiente alcance o supere los 50 ºC.",
                    ref = "ITC-BT-48 §2.3"
                ),
Question(
                    q = "En instalaciones con rectificadores, ¿qué prescripción rige para las canalizaciones de corriente alterna y corriente continua?",
                    opts = listOf(
                        "Deben compartir siempre el mismo tubo para reducir inductancia",
                        "Pueden identificarse únicamente con cinta aislante negra",
                        "No se permite su instalación en el mismo edificio",
                        "Serán distintas y estarán convenientemente señalizadas o separadas entre sí"
                    ),
                    a = 3,
                    exp = "La ITC-BT-48 par. 2.2 exige que las canalizaciones de corrientes de diferente naturaleza (CA y CC) sean independientes y estén convenientemente separadas o señalizadas para evitar confusiones y acoplamientos.",
                    ref = "ITC-BT-48 §2.2"
                ),
Question(
                    q = "Para la instalación de condensadores situados a más de 2.000 metros de altitud sobre el nivel del mar, ¿qué medida debe adoptarse?",
                    opts = listOf(
                        "Tomar precauciones de acuerdo con el fabricante según la norma UNE-EN 60831-1",
                        "Duplicar la tensión nominal del condensador",
                        "Reducir la capacidad en microfaradios a la mitad",
                        "Instalar refrigeración líquida obligatoria"
                    ),
                    a = 0,
                    exp = "La ITC-BT-48 par. 2.3 remite a la norma UNE-EN 60831-1 indicando que para altitudes superiores a 2.000 m deben adoptarse precauciones especiales acordadas con el fabricante debido a la menor rigidez dieléctrica del aire.",
                    ref = "ITC-BT-48 §2.3"
                ),
Question(
                    q = "Todo transformador de potencia en baja tensión debe estar protegido en su alimentación por:",
                    opts = listOf(
                        "Un interruptor diferencial de 30 mA exclusivamente",
                        "Un dispositivo de corte por sobreintensidad adecuado a su placa y uso",
                        "Un fusible en el secundario únicamente",
                        "Un relé térmico regulado al 50 %"
                    ),
                    a = 1,
                    exp = "Según la ITC-BT-48 apartado 3, todo transformador estará protegido por un dispositivo de corte por sobreintensidad acorde con las características de su placa de características y sus condiciones de servicio.",
                    ref = "ITC-BT-48 §3"
                ),
Question(
                    q = "¿Qué tiempo máximo establece la ITC-BT-48 para que los condensadores reduzcan su tensión residual a 50 V o menos tras desconectarse de la red?",
                    opts = listOf(
                        "5 minutos",
                        "3 minutos",
                        "1 minuto (60 segundos) mediante resistencias de descarga permanente sólidamente unidas",
                        "No hay tiempo límite si el recinto está cerrado con llave"
                    ),
                    a = 2,
                    exp = "La ITC-BT-48 apartado 2.3 establece que los condensadores deben disponer de un dispositivo de descarga automático (como resistencias de descarga) que reduzca la tensión residual en bornes a un valor igual o inferior a 50 V en un tiempo máximo de 1 minuto desde la desconexión.",
                    ref = "ITC-BT-48"
                ),
Question(
                    q = "¿Cuál es el incremento máximo de corriente permanente que deben soportar los condensadores de baja tensión debido a la presencia de armónicos y sobretensiones según la ITC-BT-48 y normas UNE asociadas?",
                    opts = listOf(
                        "El 10 % de su intensidad nominal",
                        "El 20 % de su intensidad nominal",
                        "El 50 % de su intensidad nominal",
                        "Hasta un 30 % de incremento permanente sobre la intensidad nominal (factor 1,3)"
                    ),
                    a = 3,
                    exp = "Conforme a la norma UNE-EN 60831-1 referenciada en la ITC-BT-48, las unidades de condensadores deben estar diseñadas para operar de forma continua con una corriente de hasta 1,3 veces la intensidad nominal, absorbiendo así el efecto de los armónicos de tensión.",
                    ref = "ITC-BT-48"
                ),
Question(
                    q = "Según la ITC-BT-48, ¿qué seccionamiento y maniobra debe asociarse obligatoriamente a toda batería de condensadores de compensación general?",
                    opts = listOf(
                        "Un interruptor automático de corte omnipolar con capacidad de corte de corrientes capacitivas y dimensionado para soportar las corrientes transitorias de inserción",
                        "Un fusible de plomo rápido sin cámara de soplado",
                        "Un interruptor diferencial de 10 mA sin protección magnetotérmica",
                        "Un relé de paso por cero de estado sólido monofásico"
                    ),
                    a = 0,
                    exp = "La ITC-BT-48 par. 2.3 exige que la aparamenta de maniobra y corte de los condensadores esté diseñada específicamente para servicio capacitivo continuo, soportando la sobrecorriente permanente de armónicos y las elevadas corrientes de inserción transitorias.",
                    ref = "ITC-BT-48"
                ),
Question(
                    q = "¿Por qué está expresamente prohibido alimentar un circuito de Muy Baja Tensión de Seguridad (MBTS) mediante un autotransformador según la ITC-BT-48 e ITC-BT-36?",
                    opts = listOf(
                        "Porque los autotransformadores reducen demasiado el factor de potencia",
                        "Porque carecen de aislamiento galvánico, existiendo unión física directa entre el primario y el secundario, lo que trasladaría tensiones peligrosas en caso de fallo",
                        "Porque no admiten corriente continua",
                        "Porque se calientan excesivamente a menos de 50 V"
                    ),
                    a = 1,
                    exp = "En un autotransformador, el devanado secundario es parte física del devanado primario. Al carecer de aislamiento de separación galvánica galvánica (separación de arrollamientos), un fallo en el conductor neutro o aislamiento trasladaría la tensión de red directamente al circuito de MBTS, anulando la protección (ITC-BT-48 pto. 2.1).",
                    ref = "ITC-BT-48"
                ),
Question(
                    q = "¿Qué requisito deben cumplir las reactancias o balastos inductivos instalados dentro de luminarias respecto a las superficies inflamables según la ITC-BT-48?",
                    opts = listOf(
                        "Deben pintarse con esmalte cerámico ignífugo en obra",
                        "Deben conectarse únicamente a tierra con un hilo de plomo",
                        "Estarán situados sobre soportes incombustibles o distanciados de cualquier parte combustible mediante pantallas de aislamiento térmico",
                        "Deben sumergirse en resina epoxi líquida antes de encenderse"
                    ),
                    a = 2,
                    exp = "Las reactancias electromagnéticas alcanzan temperaturas elevadas de funcionamiento normal. La ITC-BT-48 par. 2.2 prescribe que deben montarse de modo que el calor disipado no suponga un riesgo de incendio para las superficies inflamables contiguas.",
                    ref = "ITC-BT-48"
                ),
Question(
                    q = "Para transformadores en baño de aceite mineral de gran volumen, ¿qué medida adicional contra incendios es obligatoria según la ITC-BT-48 e instrucciones técnicas?",
                    opts = listOf(
                        "Disponer de un extintor de polvo portátil colgado del propio transformador",
                        "Pintar el transformador de color verde fosforescente",
                        "Instalar un sistema de ventilación forzada permanente",
                        "Un foso de recogida de aceite o sistema de drenaje a depósito de expansión incombustible para contener el líquido dieléctrico en caso de fuga o rotura de la cuba"
                    ),
                    a = 3,
                    exp = "Los transformadores en baño de líquido dieléctrico inflamable exigen precauciones de seguridad pasiva. La ITC-BT-48 y reglamentos complementarios imponen disponer de cubetos o fosos con lecho de guijarros cortafuegos capaces de recoger y autoextinguir el aceite derramado.",
                    ref = "ITC-BT-48"
                ),
Question(
                    q = "¿Qué ocurre con los condensadores de compensación que no disponen de placa con indicación de temperatura máxima de servicio según la ITC-BT-48?",
                    opts = listOf(
                        "Queda terminantemente prohibida su instalación",
                        "Solo pueden utilizarse de noche",
                        "Deben alimentarse a través de un transformador de aislamiento",
                        "Deben limitarse a una potencia de 1 kVAr"
                    ),
                    a = 0,
                    exp = "La ITC-BT-48 par. 2.3 establece de forma categórica que no se instalarán condensadores que carezcan de placa indicadora de la temperatura máxima de servicio compatible con las condiciones térmicas del local.",
                    ref = "ITC-BT-48"
                ),
Question(
                    q = "Cuando un autotransformador se conecta a una instalación de baja tensión, ¿a qué conductor debe unirse obligatoriamente el terminal común o neutro de ambos circuitos según la ITC-BT-48?",
                    opts = listOf(
                        "Al conductor de fase R1",
                        "Al conductor neutro de la red de alimentación, que debe estar conectado directamente a tierra",
                        "Al conductor de equipotencialidad sin conexión a tierra",
                        "Al chasis metálico sin conexión al cable PEN"
                    ),
                    a = 1,
                    exp = "La ITC-BT-48 par. 2.1 determina que para evitar que el circuito de menor tensión quede sometido a un potencial peligroso respecto a tierra en caso de defecto, el conductor común del autotransformador debe ir conectado fijamente al neutro de la instalación.",
                    ref = "ITC-BT-48"
                ),
Question(
                    q = "En una instalación industrial con alto contenido de armónicos en la corriente, ¿qué componente es preceptivo instalar en serie con los condensadores de compensación según la ITC-BT-48 y buenas prácticas?",
                    opts = listOf(
                        "Una resistencia shunt de bajo valor",
                        "Un fusible de fusión rápida de silicio",
                        "Reactancias de rechazo o filtros desintonizados para evitar la resonancia armónica destructiva",
                        "Un rectificador de diodos trifásico"
                    ),
                    a = 2,
                    exp = "La presencia de armónicos puede originar un fenómeno de resonancia paralela con la inductancia de la red, amplificando las corrientes y destruyendo los condensadores. Las inductancias de bloqueo de reactiva sintonizan la rama a una frecuencia inferior para proteger la batería de condensadores (ITC-BT-48 pto. 2.3).",
                    ref = "ITC-BT-48"
                ),
Question(
                    q = "¿Qué limitación rige para la caída de tensión residual en los bornes de las baterías de condensadores antes de un reenganche automático según la ITC-BT-48?",
                    opts = listOf(
                        "No debe ser inferior al 90 % de la tensión nominal",
                        "Debe bajar a menos de 100 V en 10 segundos",
                        "Debe alcanzar los 230 V eficaces en todo momento",
                        "El condensador debe estar prácticamente descargado (tensión residual inferior al 10 % de la nominal) para evitar sobrecorrientes transitorias destructivas por desfase"
                    ),
                    a = 3,
                    exp = "Si se conecta una batería de condensadores que retiene carga residual desfasada respecto a la tensión de red, se produce una punta de corriente transitoria extremadamente destructiva. La ITC-BT-48 exige dispositivos de control que bloqueen la reconexión hasta que se descarguen adecuadamente.",
                    ref = "ITC-BT-48"
                ),
Question(
                    q = "Todo transformador con arrollamientos bajo tensión superior a 50 V que quede al alcance de personas no especializadas debe disponer de:",
                    opts = listOf(
                        "Una envolvente rígida puesta a tierra con grado de protección IP mínimo IP2X",
                        "Una alfombra de goma dieléctrica a su alrededor exclusivamente",
                        "Un rótulo de peligro de muerte en español exclusivamente",
                        "Un fusible cerámico colgado de la carcasa"
                    ),
                    a = 0,
                    exp = "Según la ITC-BT-48 apartado 2.1, para transformadores al alcance del público en general se debe garantizar la inaccesibilidad a las partes activas bajo tensiones superiores a 50 V mediante envolventes adecuadas puestas a tierra o aislamiento equivalente.",
                    ref = "ITC-BT-48"
                ),
Question(
                    q = "En transformadores monofásicos de potencia inferior o igual a 3.000 VA, ¿qué distancia mínima de separación respecto a partes combustibles se exige para prescindir de pantallas incombustibles según la ITC-BT-48?",
                    opts = listOf(
                        "1 centímetro",
                        "5 centímetros",
                        "10 centímetros",
                        "20 centímetros"
                    ),
                    a = 1,
                    exp = "La ITC-BT-48 par. 2.1 estipula que cuando la potencia nominal del transformador sea inferior o igual a 3.000 VA monofásicos, la distancia mínima a materiales combustibles será de 5 cm, reduciéndose a 1 cm si se interpone una pantalla incombustible.",
                    ref = "ITC-BT-48"
                ),
Question(
                    q = "¿Qué limitación establece la ITC-BT-48 para la instalación de autotransformadores destinados a alimentar herramientas portátiles?",
                    opts = listOf(
                        "No se permite su instalación si pesan más de 5 kg",
                        "Deben alimentarse a través de un diferencial de tipo B obligatoriamente",
                        "Queda terminantemente prohibido su uso para alimentar receptores o herramientas portátiles por el riesgo derivado de la ausencia de aislamiento de separación galvánica",
                        "Solo se admiten si las herramientas disponen de carcasa metálica sin cable de tierra"
                    ),
                    a = 2,
                    exp = "Debido a la conexión directa por cobre entre primario y secundario en los autotransformadores, cualquier fallo de tierra o rotura del neutro común de red trasladaría voltajes peligrosos directos a la herramienta que empuña el operario, por lo que la ITC-BT-48 prohíbe taxativamente este uso.",
                    ref = "ITC-BT-48"
                ),
Question(
                    q = "Para transformadores de potencia de más de 3.000 VA montados fijos en baja tensión, ¿cuál es la distancia mínima reglamentaria a paredes o partes inflamables sin interponer pantallas según la ITC-BT-48?",
                    opts = listOf(
                        "5 cm",
                        "10 cm",
                        "15 cm",
                        "20 cm (reduciéndose a 5 cm con pantalla incombustible)"
                    ),
                    a = 3,
                    exp = "Según la ITC-BT-48 par. 2.1, los transformadores de potencia de más de 3.000 VA monofásicos o 10.000 VA trifásicos deben distanciarse de materiales inflamables al menos 20 cm para evitar riesgos de incendio, admitiéndose 5 cm si se interpone pantalla aislante de fuego.",
                    ref = "ITC-BT-48"
                ),
Question(
                    q = "¿Cómo se debe verificar la eficacia de la protección diferencial en circuitos que alimentan reactancias electromagnéticas con corrientes de fuga estables?",
                    opts = listOf(
                        "La suma de las corrientes de fuga naturales de todas las reactancias conectadas aguas abajo de un mismo diferencial de 30 mA no debe superar un tercio (1/3) de su sensibilidad de disparo (máximo 10 mA)",
                        "No importa el valor de la fuga si las reactancias están homologadas",
                        "Debe anularse el conductor de tierra para que no salte el diferencial",
                        "Sustituyendo el diferencial de alta sensibilidad por un fusible rápido de 63 A"
                    ),
                    a = 1,
                    exp = "Las corrientes de fuga capacitivas a tierra de balastos inductivos y filtros acumulados pueden provocar disparos intempestivos de diferenciales de 30 mA si superan los 10 mA acumulados, requiriendo fraccionar los circuitos según buenas prácticas de la ITC-BT-48 e ITC-BT-24.",
                    ref = "ITC-BT-48"
                ),
Question(
                    q = "¿Qué grado de protección IP debe tener el local técnico destinado exclusivamente a albergar un transformador seco de potencia según la ITC-BT-48?",
                    opts = listOf(
                        "Grado IP00 ordinario",
                        "Debe estar diseñado para impedir la entrada de agua y polvo nocivo, contando las aberturas de ventilación con rejillas que den un grado de protección IP no inferior a IP2X",
                        "IP68 estanco sumergible permanentemente",
                        "IP10 contra caída de herramientas de gran peso"
                    ),
                    a = 1,
                    exp = "La ITC-BT-48 punto 2 exige que los recintos técnicos para transformadores garanticen condiciones térmicas y de estanqueidad apropiadas, disponiendo las aberturas de rejillas de protección contra la introducción de cuerpos extraños de diámetro superior a 12,5 mm (IP2X).",
                    ref = "ITC-BT-48"
                ),
Question(
                    q = "Al compensar la energía reactiva de forma individual en los bornes de un motor de inducción (compensación fija), ¿qué limitación rige para evitar la autoexcitación del motor al desconectarse?",
                    opts = listOf(
                        "La potencia de la batería debe ser el doble de la potencia del motor",
                        "No existe límite de potencia reactiva para compensar en bornes",
                        "La potencia reactiva (kVAr) de los condensadores no debe superar el 90 % de la corriente absorbida por el motor en vacío",
                        "La tensión de los condensadores debe ser la mitad de la tensión nominal"
                    ),
                    a = 2,
                    exp = "Si la reactiva de los condensadores es muy elevada respecto a la corriente de vacío, al abrir el contactor el motor actúa como generador autoexcitado por los condensadores, produciendo sobretensiones destructivas. Se limita la potencia de compensación al 90% de la reactiva en vacío del motor (ITC-BT-48 pto. 2.3).",
                    ref = "ITC-BT-48"
                ),
Question(
                    q = "En locales con alto contenido de polvo o fibras combustibles, ¿dónde deben alojarse preferentemente los condensadores de compensación según la ITC-BT-48?",
                    opts = listOf(
                        "En el suelo junto a la maquinaria de aserradero",
                        "Al aire libre suspendidos de los postes de acometida",
                        "Bajo bancadas de madera accesibles",
                        "En envolventes o armarios herméticos con un grado de protección mínimo IP5X o superior"
                    ),
                    a = 3,
                    exp = "La presencia de polvo inflamable sobre los bornes de condensadores genera riesgo de cortocircuitos e ignición. La ITC-BT-48 en concordancia con la ITC-BT-29 exige alojarlos en armarios estancos al polvo (IP5X o superior) con ventilación filtrada.",
                    ref = "ITC-BT-48"
                ),
Question(
                    q = "Para compensación de reactiva en circuitos monofásicos domésticos (ej. reactancias de descarga), ¿cuál es el factor de potencia mínimo que debe alcanzarse en los receptores según la ITC-BT-48 e ITC-BT-44?",
                    opts = listOf(
                        "No inferior a 0,5",
                        "No inferior a 0,9 (tanto para c.a. inductiva como capacitiva)",
                        "No inferior a 0,7",
                        "Debe ser exactamente igual a 1,00"
                    ),
                    a = 1,
                    exp = "La ITC-BT-44 y la ITC-BT-48 exigen que la corrección del factor de potencia de receptores individuales o colectivos garantice un cos phi igual o mayor de 0,9 en régimen de funcionamiento normal para evitar penalizaciones y optimizar la red.",
                    ref = "ITC-BT-48"
                ),
Question(
                    q = "En transformadores instalados fijos dentro de locales húmedos o mojados, ¿cómo debe efectuarse la conexión de la envolvente metálica según la ITC-BT-48 e ITC-BT-30?",
                    opts = listOf(
                        "No se requiere conexión si el transformador es de Clase II",
                        "Debe aislarse completamente del suelo mediante tacos de madera",
                        "Debe unirse a las tuberías de plástico de agua",
                        "Debe conectarse de forma sólida e inequívoca al conductor de protección general (PE) de la instalación"
                    ),
                    a = 3,
                    exp = "La carcasa de transformadores Clase I debe mantener una conexión a tierra permanente de muy baja impedancia, asegurando la inmediata actuación de las protecciones magnetotérmicas o diferenciales en locales mojados ante cualquier fallo de aislamiento (ITC-BT-48).",
                    ref = "ITC-BT-48"
                ),
Question(
                    q = "¿Qué consideración reglamentaria otorga la ITC-BT-49 a cualquier mueble comercializado que incorpore un equipo eléctrico montado?",
                    opts = listOf(
                        "Se considerará como un receptor a todos los efectos",
                        "Se considerará una instalación de enlace",
                        "Se clasifica como cuadro general de distribución",
                        "Se considera un local especial"
                    ),
                    a = 0,
                    exp = "La ITC-BT-49 apartado 1 establece que cualquier mueble comercializado con un equipo eléctrico montado en él se considerará como un receptor, debiendo cumplir los requisitos de seguridad correspondientes.",
                    ref = "ITC-BT-49 §1"
                ),
Question(
                    q = "En el cableado interior de muebles, ¿qué tipo de cable flexible con aislamiento de PVC se exige como mínimo?",
                    opts = listOf(
                        "H03VV-F",
                        "H05VV-F (o equivalente)",
                        "H07V-K unipolar sin cubierta",
                        "Cable coaxial con malla de cobre"
                    ),
                    a = 1,
                    exp = "La ITC-BT-49 par. 2.2 especifica que los cables flexibles bajo cubierta de PVC utilizados dentro de muebles deben ser equivalentes, como mínimo, al tipo normalizado H05VV-F para soportar esfuerzos mecánicos.",
                    ref = "ITC-BT-49 §2.2"
                ),
Question(
                    q = "Si un mueble comercializado incorpora alguna base de toma de corriente, ¿cuál es la sección mínima obligatoria de los conductores de cobre?",
                    opts = listOf(
                        "1,5 mm²",
                        "0,75 mm²",
                        "2,5 mm²",
                        "4 mm²"
                    ),
                    a = 2,
                    exp = "Conforme a la ITC-BT-49 par. 2.3, la sección mínima de los conductores de cobre dentro del mueble será de 2,5 mm² siempre que incorpore bases de toma de corriente, asegurando la capacidad térmica adecuada.",
                    ref = "ITC-BT-49 §2.3"
                ),
Question(
                    q = "¿Qué sección mínima de conductor de cobre se permite en muebles para circuitos de alumbrado exclusivo con longitud no superior a 10 m sin tomas?",
                    opts = listOf(
                        "0,5 mm²",
                        "1,5 mm²",
                        "1,0 mm²",
                        "0,75 mm²"
                    ),
                    a = 3,
                    exp = "La ITC-BT-49 par. 2.3 permite emplear conductores de cobre de 0,75 mm² para instalaciones de alumbrado exclusivo en muebles siempre que la longitud entre la conexión fija y el aparato no supere los 10 metros.",
                    ref = "ITC-BT-49 §2.3"
                ),
Question(
                    q = "¿Qué grado de protección mínimo y sistema de cierre deben tener las cajas de conexión eléctrica situadas en los muebles?",
                    opts = listOf(
                        "IP 3X y con tapa que solo pueda abrirse con la ayuda de un útil o llave",
                        "IP 20 con tapa a presión manual",
                        "IP 55 con tornillos inviolables",
                        "IP 44 con cierre magnético"
                    ),
                    a = 0,
                    exp = "Según la ITC-BT-49 par. 2.5, las conexiones eléctricas en muebles deben alojarse en cajas con grado de protección mínimo IP 3X cuya tapa sólo pueda ser abierta mediante el empleo de una llave o herramienta.",
                    ref = "ITC-BT-49 §2.5"
                ),
Question(
                    q = "Los muebles que incorporan equipo eléctrico para instalarse en cuartos de baño o aseos deben ser obligatoriamente:",
                    opts = listOf(
                        "Móviles con ruedas bloqueables",
                        "Fijos y respetar los volúmenes de la ITC-BT-27",
                        "Portátiles con clavija estanca",
                        "De Clase 0 con toma de tierra"
                    ),
                    a = 1,
                    exp = "La ITC-BT-49 apartado 3 exige que los muebles con equipo eléctrico destinados a cuartos de baño o aseo sean fijos y cumplan rigurosamente los volúmenes de protección e instalación de la ITC-BT-27.",
                    ref = "ITC-BT-49 §3"
                ),
Question(
                    q = "¿Qué símbolo normativo deben llevar marcado las luminarias instaladas sobre superficies inflamables del mueble (madera, aglomerado)?",
                    opts = listOf(
                        "Símbolo de doble aislamiento",
                        "Símbolo CE exclusivamente",
                        "Símbolo F (según norma UNE-EN 60598-1)",
                        "Símbolo de toma de tierra"
                    ),
                    a = 2,
                    exp = "La ITC-BT-49 par. 2.1 establece que las luminarias destinadas a montaje directo sobre superficies inflamables de mobiliario deben llevar el marcado homologado con el símbolo F según la norma UNE-EN 60598-1.",
                    ref = "ITC-BT-49 §2.1"
                ),
Question(
                    q = "En muebles donde los equipos eléctricos disipen calor en recintos cerrados (ej. camas abatibles), ¿qué dispositivo de seguridad debe instalarse?",
                    opts = listOf(
                        "Un ventilador de 230 V permanente",
                        "Un fusible térmico desechable",
                        "Un termómetro visible desde el exterior",
                        "Un interruptor accionado por el cierre que deje fuera de servicio el equipo"
                    ),
                    a = 3,
                    exp = "Según la ITC-BT-49 par. 2.1, si la potencia disipada puede generar sobrecalentamiento peligroso al cerrar el mueble, debe instalarse un interruptor de fin de carrera accionado por el cierre que desconecte el equipo.",
                    ref = "ITC-BT-49 §2.1"
                ),
Question(
                    q = "Para la conexión fija a la red de un mueble de cuarto de baño con equipo eléctrico, ¿qué elemento debe incorporar el mueble?",
                    opts = listOf(
                        "Una caja de conexión con bornes fija accesible solo mediante herramienta",
                        "Una clavija móvil Schuko de goma",
                        "Un interruptor unipolar en el cable",
                        "Un enchufe hembra en la parte trasera"
                    ),
                    a = 0,
                    exp = "La ITC-BT-49 apartado 3 exige que los muebles de baño dispongan de una caja de conexión fija con bornes, accesible únicamente tras retirar una tapa con la ayuda de una herramienta.",
                    ref = "ITC-BT-49 §3"
                ),
Question(
                    q = "En el cableado de muebles, ¿contra qué esfuerzos mecánicos deben quedar especialmente protegidos los conductores?",
                    opts = listOf(
                        "Vibraciones acústicas",
                        "Tracción y torsión",
                        "Presión hidrostática",
                        "Fricción con superficies metálicas"
                    ),
                    a = 1,
                    exp = "La ITC-BT-49 par. 2.4 dispone que los cables estarán firmemente fijados y dotados de dispositivos antitracción en los puntos de entrada, quedando protegidos en particular contra esfuerzos de tracción y torsión.",
                    ref = "ITC-BT-49 §2.4"
                ),
Question(
                    q = "¿Qué sección mínima deben tener los conductores de cobre en un mueble destinado a iluminación que no incorpore tomas de corriente y cuya línea supere los 10 metros de longitud según la ITC-BT-49?",
                    opts = listOf(
                        "0,5 mm²",
                        "0,75 mm²",
                        "1,5 mm²",
                        "2,5 mm²"
                    ),
                    a = 2,
                    exp = "Según la ITC-BT-49 par. 2.3, los conductores para alumbrado interno de muebles deben ser de cobre de sección mínima de 1,5 mm², reduciéndose la exigencia a 0,75 mm² solo si la longitud de la manguera es igual o inferior a 10 metros y no hay bases de enchufe.",
                    ref = "ITC-BT-49"
                ),
Question(
                    q = "En muebles de cocina con electrodomésticos integrados, ¿cómo deben discurrir los cables eléctricos respecto a las canalizaciones de gas según la ITC-BT-49 e ITC-BT-19?",
                    opts = listOf(
                        "Deben ir atados directamente a la tubería de gas",
                        "Bajo abrazaderas de acero conductoras comunes",
                        "Envolviendo la tubería de gas con mangueras de PVC",
                        "Separados de las tuberías de gas al menos 3 cm (o interponer pantalla) y colocados a una distancia donde no sufran recalentamiento por escapes o calor"
                    ),
                    a = 3,
                    exp = "Las canalizaciones de energía eléctrica de muebles contiguos a servicios de gas o calefacción deben mantener distancias de separación seguras para prevenir fugas eléctricas que provoquen arcos en la conducción de gas inflamable (ITC-BT-19 par. 2.5 e ITC-BT-49).",
                    ref = "ITC-BT-49"
                ),
Question(
                    q = "¿Qué grado de aislamiento mínimo deben poseer los cables de alimentación principales de muebles de Clase II según la ITC-BT-49?",
                    opts = listOf(
                        "Doble aislamiento o aislamiento reforzado sin conductor de protección a tierra",
                        "Aislamiento simple con hilo de cobre desnudo enrollado exteriormente",
                        "Mangueras telefónicas planas tipo cable bus",
                        "Hilos rígidos tipo H07V-U bajo canaleta de cartón"
                    ),
                    a = 0,
                    exp = "La ITC-BT-49 de muebles con equipos eléctricos exige para muebles de Clase II (con doble aislamiento) conductores flexibles con cubierta exterior elastomérica o termoplástica equivalente (ej. H05VV-F) que garanticen aislamiento de protección de doble barrera sin toma de tierra.",
                    ref = "ITC-BT-49"
                ),
Question(
                    q = "Para evitar pellizcos y cizallamiento en partes móviles de muebles (cajones, puertas, camas abatibles), ¿qué requisito exige la ITC-BT-49 para las canalizaciones?",
                    opts = listOf(
                        "Que los cables cuelguen sueltos por el centro del cajón",
                        "Que se disponga de elementos de guiado o mangueras flexibles articuladas autoprotegidas que acompañen el movimiento sin tensarse ni sufrir rozamientos nocivos",
                        "Que los cables se fijen rígidamente con clavos de acero",
                        "Utilizar conductores desnudos de acero de alta resistencia"
                    ),
                    a = 1,
                    exp = "La ITC-BT-49 par. 2.4 dispone que cuando las canalizaciones crucen o sigan articulaciones o guías de muebles móviles, los conductores flexibles deben estar dotados de guías protectoras mecánicas que prevengan aplastamientos o rozaduras destructivas de la funda.",
                    ref = "ITC-BT-49"
                ),
Question(
                    q = "En un armario ropero que incorpora focos halógenos o de alta temperatura, ¿qué tipo de conductores deben emplearse en la proximidad inmediata de las luminarias según la ITC-BT-49?",
                    opts = listOf(
                        "Conductores normales de PVC (H05VV-F)",
                        "Mangueras blindadas con pantalla de aluminio",
                        "Conductores de silicona o elastómero resistente al calor de clase térmica de al menos 150 ºC (tipo H05SS-F o similar)",
                        "Hilos unipolar rígidos sin funda protectora"
                    ),
                    a = 2,
                    exp = "Las luminarias con bombillas de descarga, incandescentes o halógenas generan calor radiante extremo. La ITC-BT-49 par. 2.2 exige cables especiales de silicona resistentes al calor para el cableado cercano a focos térmicos para evitar incendios.",
                    ref = "ITC-BT-49"
                ),
Question(
                    q = "¿Qué característica deben reunir las tomas de corriente instaladas dentro de un mueble de oficina o escritorio según la ITC-BT-49?",
                    opts = listOf(
                        "Pueden ser tomas sin contacto de protección a tierra (Clase 0)",
                        "Deben conectarse en serie con la lámpara piloto",
                        "Deben instalarse de forma colgante",
                        "Deben poseer bornes de protección activa a tierra conectados a la red general del edificio e incorporar sistemas de obturación de alvéolos (protección infantil)"
                    ),
                    a = 3,
                    exp = "Las bases de toma de corriente integradas en muebles para uso general de aparatos Clase I deben garantizar la puesta a tierra de sus masas y cumplir las exigencias de seguridad mecánica infantil (alvéolos protegidos según la ITC-BT-49 e ITC-BT-25).",
                    ref = "ITC-BT-49"
                )
    )
}
