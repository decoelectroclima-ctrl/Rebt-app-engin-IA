package com.example

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.example.data.Content
import com.example.data.ModuleDefinition
import com.example.data.Question
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class MainViewModelPracticeSourceTest {

    @Test
    fun `test startItcPractice uses fused allQuestions property and not raw Content QUESTIONS`() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        val vm = MainViewModel(app)

        // Inject a synthetic question only into the vm.allQuestions map (not in Content.QUESTIONS)
        val syntheticQuestion = Question(
            q = "¿Pregunta sintética de prueba para ITC-BT-14 no presente en Content.kt?",
            opts = listOf("Opt A", "Opt B", "Opt C", "Opt D"),
            a = 0,
            exp = "Explicación de prueba para verificar fusión de fuentes.",
            ref = "ITC-BT-14 §99"
        )
        assertFalse(
            "La pregunta sintética no debe existir en Content.QUESTIONS",
            Content.QUESTIONS.values.flatMap { it.questions }.any { it.q == syntheticQuestion.q }
        )

        val existingEnlace = vm.allQuestions["enlace"]
        vm.allQuestions["enlace"] = existingEnlace?.copy(
            questions = listOf(syntheticQuestion) // Ensure it is the only one or top so it is taken
        ) ?: ModuleDefinition("enlace", "Enlace", "⚡", "#000", listOf(syntheticQuestion))

        // Start ITC practice for ITC 14
        vm.startItcPractice(14, "LGA Test")

        val activeQuestions = vm.activeExamModule?.questions ?: emptyList()
        assertTrue(
            "El test de ITC 14 debe incluir la pregunta inyectada en la propiedad fusionada vm.allQuestions",
            activeQuestions.any { it.q == syntheticQuestion.q }
        )
    }

    @Test
    fun `test startArticuladoPractice uses fused allQuestions property and not raw Content QUESTIONS`() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        val vm = MainViewModel(app)

        val syntheticArtQuestion = Question(
            q = "¿Pregunta sintética de prueba para Articulado no presente en Content.kt?",
            opts = listOf("Opt 1", "Opt 2", "Opt 3", "Opt 4"),
            a = 1,
            exp = "Explicación de prueba para verificar fusión de articulado.",
            ref = "Art. 99 RD 842/2002"
        )
        assertFalse(
            "La pregunta sintética no debe existir en Content.QUESTIONS",
            Content.QUESTIONS.values.flatMap { it.questions }.any { it.q == syntheticArtQuestion.q }
        )

        val existingArt = vm.allQuestions["articulado"]
        vm.allQuestions["articulado"] = existingArt?.copy(
            questions = listOf(syntheticArtQuestion)
        ) ?: ModuleDefinition("articulado", "Articulado", "⚡", "#000", listOf(syntheticArtQuestion))

        vm.startArticuladoPractice()

        val activeQuestions = vm.activeExamModule?.questions ?: emptyList()
        assertTrue(
            "El test de articulado debe incluir la pregunta inyectada en la propiedad fusionada vm.allQuestions",
            activeQuestions.any { it.q == syntheticArtQuestion.q }
        )
    }
}
