package com.example.data

import android.util.Log

/**
 * Validador de integridad para el catálogo de preguntas del REBT.
 * Garantiza cumplimiento estricto de las reglas pedagógicas y normativas:
 * 1. Exactamente 4 opciones por pregunta.
 * 2. Índice de respuesta correcta 'a' en rango 0..3.
 * 3. Opciones no vacías y sin duplicados entre sí.
 * 4. 'q', 'exp' y 'ref' no vacíos.
 * 5. 'ref' parseable canónicamente (itcNumber() != null || isArticulado()).
 * 6. Enunciado único en todo el banco de preguntas.
 */
object QuestionValidator {

    data class ValidationError(
        val questionIdentifier: String,
        val reason: String
    )

    fun validateAll(questionsMap: Map<String, ModuleDefinition> = Content.QUESTIONS): List<ValidationError> {
        val errors = mutableListOf<ValidationError>()
        val seenEnunciados = mutableMapOf<String, String>() // normalized q -> moduleId

        for ((moduleId, module) in questionsMap) {
            for ((idx, q) in module.questions.withIndex()) {
                val qIdentifier = "[$moduleId #$idx] \"${q.q.take(50)}\""

                // 1. Enunciado no vacío
                if (q.q.isBlank()) {
                    errors.add(ValidationError(qIdentifier, "Enunciado (q) está vacío o en blanco."))
                } else {
                    // 6. Enunciado único en todo el banco
                    val normalizedQ = q.q.trim().lowercase()
                    if (seenEnunciados.containsKey(normalizedQ)) {
                        val prevModule = seenEnunciados[normalizedQ]
                        errors.add(
                            ValidationError(
                                qIdentifier,
                                "Enunciado duplicado en el banco (previamente visto en '$prevModule'): \"${q.q}\""
                            )
                        )
                    } else {
                        seenEnunciados[normalizedQ] = moduleId
                    }
                }

                // 2. Exactamente 4 opciones
                if (q.opts.size != 4) {
                    errors.add(ValidationError(qIdentifier, "Debe tener exactamente 4 opciones; tiene ${q.opts.size}."))
                }

                // 3. Índice 'a' en 0..3
                if (q.a !in 0..3) {
                    errors.add(ValidationError(qIdentifier, "Índice de respuesta 'a' (${q.a}) fuera del rango válido 0..3."))
                }

                // 4. Opciones no vacías y sin duplicados entre sí
                if (q.opts.any { it.isBlank() }) {
                    errors.add(ValidationError(qIdentifier, "Contiene opciones de respuesta vacías o en blanco."))
                }
                val uniqueOpts = q.opts.map { it.trim().lowercase() }.distinct()
                if (uniqueOpts.size != q.opts.size) {
                    errors.add(ValidationError(qIdentifier, "Opciones duplicadas entre sí en la misma pregunta."))
                }

                // 5. Explicación y referencia no vacías
                if (q.exp.isBlank()) {
                    errors.add(ValidationError(qIdentifier, "Explicación (exp) vacía o en blanco."))
                }
                if (q.ref.isBlank()) {
                    errors.add(ValidationError(qIdentifier, "Referencia (ref) vacía o en blanco."))
                }

                // 6. Referencia parseable (itcNumber() != null || isArticulado())
                if (q.itcNumber() == null && !q.isArticulado()) {
                    errors.add(ValidationError(qIdentifier, "Referencia no parseable ni como ITC ni como Articulado: \"${q.ref}\"."))
                }
            }
        }

        return errors
    }

    /**
     * En builds DEBUG, ejecuta la validación al arrancar y reporta advertencias detalladas en Logcat.
     * En builds RELEASE, es una operación vacía (no-op) para no penalizar rendimiento ni arranque.
     */
    fun validateOnDebugStartup() {
        if (com.example.BuildConfig.DEBUG) {
            val errors = validateAll()
            if (errors.isNotEmpty()) {
                Log.w("QuestionValidator", "⚠️ Se detectaron ${errors.size} anomalías en el banco de preguntas:")
                for (err in errors) {
                    Log.w("QuestionValidator", "   • ${err.questionIdentifier}: ${err.reason}")
                }
            } else {
                Log.i("QuestionValidator", "✓ Banco de preguntas REBT verificado: 100 % íntegro y sin errores.")
            }
        }
    }
}
