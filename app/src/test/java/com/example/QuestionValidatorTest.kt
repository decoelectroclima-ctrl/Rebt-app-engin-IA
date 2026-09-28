package com.example

import com.example.data.Content
import com.example.data.Question
import com.example.data.QuestionValidator
import com.example.data.isArticulado
import com.example.data.itcNumber
import org.junit.Assert.*
import org.junit.Test

class QuestionValidatorTest {

    @Test
    fun testAllQuestionsInBankAreValid() {
        val errors = QuestionValidator.validateAll(Content.QUESTIONS)
        if (errors.isNotEmpty()) {
            val failureMessage = buildString {
                append("Se encontraron ${errors.size} errores en el banco de preguntas:\n")
                errors.forEach { err ->
                    append(" - ${err.questionIdentifier}: ${err.reason}\n")
                }
            }
            fail(failureMessage)
        }
        assertTrue("El banco de preguntas debe tener preguntas cargadas", Content.QUESTIONS.isNotEmpty())
    }

    @Test
    fun testQuestionParserFormats() {
        fun dummyQ(ref: String) = Question(
            q = "¿Pregunta de prueba?",
            opts = listOf("A", "B", "C", "D"),
            a = 0,
            exp = "Explicación de prueba suficientemente descriptiva.",
            ref = ref
        )

        // Caso 1: ITC estándar con guión
        val q1 = dummyQ("ITC-BT-18 §3.1")
        assertEquals(18, q1.itcNumber())
        assertFalse(q1.isArticulado())

        // Caso 2: ITC con espacio en lugar de guión
        val q2 = dummyQ("ITC-BT 18")
        assertEquals(18, q2.itcNumber())
        assertFalse(q2.isArticulado())

        // Caso 3: ITC con cero a la izquierda
        val q3 = dummyQ("ITC-BT-05 §4.2")
        assertEquals(5, q3.itcNumber())
        assertFalse(q3.isArticulado())

        // Caso 4: ITC con coma y tabla
        val q4 = dummyQ("ITC-BT-21, Tabla 21.1")
        assertEquals(21, q4.itcNumber())
        assertFalse(q4.isArticulado())

        // Caso 5: Referencia que menciona varias ITC (debe asignar la primera)
        val q5 = dummyQ("ITC-BT-17 e ITC-BT-24")
        assertEquals(17, q5.itcNumber())
        assertFalse(q5.isArticulado())

        // Caso 6: Formato simple "ITC 04"
        val q6 = dummyQ("ITC 04")
        assertEquals(4, q6.itcNumber())
        assertFalse(q6.isArticulado())

        // Caso 7: Formato con guión "ITC-01"
        val q7 = dummyQ("ITC-01")
        assertEquals(1, q7.itcNumber())
        assertFalse(q7.isArticulado())

        // Caso 8: ITC moderna alta
        val q8 = dummyQ("ITC-BT-52 §5")
        assertEquals(52, q8.itcNumber())
        assertFalse(q8.isArticulado())

        // Caso 9: Artículo con año REBT
        val q9 = dummyQ("Art. 1, REBT 2002")
        assertNull(q9.itcNumber())
        assertTrue(q9.isArticulado())

        // Caso 10: Artículo con número de apartado y RD
        val q10 = dummyQ("Art. 10.1 RD 842/2002")
        assertNull(q10.itcNumber())
        assertTrue(q10.isArticulado())

        // Caso 11: Mixto (cita Art. e ITC simultáneamente)
        val q11 = dummyQ("Art. 19 e ITC-BT-05")
        assertEquals(5, q11.itcNumber())
        assertTrue(q11.isArticulado())

        // Caso 12: Múltiples artículos de reglamento
        val q12 = dummyQ("Art. 79 y 81 REBT")
        assertNull(q12.itcNumber())
        assertTrue(q12.isArticulado())

        // Caso 13: Palabra completa "Artículo"
        val q13 = dummyQ("Artículo 23 RD 842/2002")
        assertNull(q13.itcNumber())
        assertTrue(q13.isArticulado())

        // Caso 14: ITC seguida de RD
        val q14 = dummyQ("ITC-BT-02 RD 842/2002")
        assertEquals(2, q14.itcNumber())
        assertFalse(q14.isArticulado())

        // Caso 15: Múltiples ITC separadas por conjunción
        val q15 = dummyQ("ITC-BT-07 e ITC-BT-21")
        assertEquals(7, q15.itcNumber())
        assertFalse(q15.isArticulado())

        // Caso 16: ITC con decreto específico de autoconsumo
        val q16 = dummyQ("ITC-BT-40 y RD 244/2019")
        assertEquals(40, q16.itcNumber())
        assertFalse(q16.isArticulado())
    }

    @Test
    fun testValidationRulesDetectBadQuestions() {
        val badQ1 = Question(
            q = "",
            opts = listOf("A", "B", "C", "D"),
            a = 0,
            exp = "Exp",
            ref = "ITC-BT-05"
        )
        val errors1 = QuestionValidator.validateAll(mapOf("test" to com.example.data.ModuleDefinition("test", "Test", "icon", "#fff", listOf(badQ1))))
        assertTrue(errors1.any { it.reason.contains("está vacío") })

        val badQ2 = Question(
            q = "¿Pregunta con 3 opciones?",
            opts = listOf("A", "B", "C"),
            a = 0,
            exp = "Exp",
            ref = "ITC-BT-05"
        )
        val errors2 = QuestionValidator.validateAll(mapOf("test" to com.example.data.ModuleDefinition("test", "Test", "icon", "#fff", listOf(badQ2))))
        assertTrue(errors2.any { it.reason.contains("exactamente 4 opciones") })

        val badQ3 = Question(
            q = "¿Pregunta con a fuera de rango?",
            opts = listOf("A", "B", "C", "D"),
            a = 4,
            exp = "Exp",
            ref = "ITC-BT-05"
        )
        val errors3 = QuestionValidator.validateAll(mapOf("test" to com.example.data.ModuleDefinition("test", "Test", "icon", "#fff", listOf(badQ3))))
        assertTrue(errors3.any { it.reason.contains("fuera del rango") })

        val badQ4 = Question(
            q = "¿Pregunta con opciones duplicadas?",
            opts = listOf("Repetida", "Repetida", "Otra", "D"),
            a = 0,
            exp = "Exp",
            ref = "ITC-BT-05"
        )
        val errors4 = QuestionValidator.validateAll(mapOf("test" to com.example.data.ModuleDefinition("test", "Test", "icon", "#fff", listOf(badQ4))))
        assertTrue(errors4.any { it.reason.contains("Opciones duplicadas") })
    }
}
