package com.example.data

/**
 * Configuración única y centralizada para los exámenes y simulacros oficiales de REBT 2026.
 *
 * NOTA PARA JAVI:
 * 90 min y 75 % son los valores que hay hoy en el código, no los he podido contrastar con la
 * convocatoria oficial de Industria. Deben confirmarse con las bases oficiales vigentes;
 * por eso van centralizados en estas constantes editables sin números mágicos dispersos.
 */
object ExamConfig {
    const val OFFICIAL_QUESTIONS = 40
    const val OFFICIAL_MINUTES = 90        // 90 min según código actual; confirmar con convocatoria
    const val SHORT_QUESTIONS = 20
    const val SHORT_MINUTES = 45          // Tiempo relajado para simulacro corto
    const val ITC_BLOCK_QUESTIONS = 20
    const val ITC_BLOCK_MINUTES = 60       // Modelo de referencia: 60 min para 20 preguntas por ITC
    const val PASS_THRESHOLD = 0.75f       // 75 % mínimo de aciertos para Apto oficial

    // TAREA 4: ITCs exclusivas de instalador especialista (IBTE), excluidas del simulacro básico (IBTB)
    // 6: Redes aéreas de distribución
    // 7: Redes subterráneas de distribución
    // 38: Quirófanos y salas de intervención
    // 51: Sistemas de automatización domótica avanzada
    val SPECIALIST_ONLY_ITC = setOf(6, 7, 38, 51) // TODO(javi): confirmar lista con el temario oficial

    // Reparto estratificado del examen oficial de 40 preguntas (Tarea 2)
    const val OFFICIAL_ARTICULADO_RATIO = 0.25f // ~25% Articulado (10 preguntas)
    const val OFFICIAL_ITC_RATIO = 0.75f        // ~75% ITCs (30 preguntas)
    const val OFFICIAL_ARTICULADO_COUNT = 10
    const val OFFICIAL_ITC_COUNT = 30
}
