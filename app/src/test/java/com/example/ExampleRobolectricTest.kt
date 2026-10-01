package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.itcNumber
import com.example.data.withShuffledOptions
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("EnginIA REBT", appName)
  }

  @Test
  fun `load questions from csv and verify count and integrity`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val questions = com.example.data.QuestionLoader.loadQuestions(context)
    org.junit.Assert.assertTrue("Debe cargar al menos 100 preguntas del CSV", questions.size >= 100)

    val itc07Count = questions.count { it.itcNumber() == 7 }
    val itc38Count = questions.count { it.itcNumber() == 38 }
    val itc06Count = questions.count { it.itcNumber() == 6 }
    val itc01Count = questions.count { it.itcNumber() == 1 }
    val itc33Count = questions.count { it.itcNumber() == 33 }

    assertEquals(29, itc07Count)
    assertEquals(27, itc38Count)
    assertEquals(20, itc06Count)
    assertEquals(18, itc01Count)
    assertEquals(10, itc33Count)

    val module = com.example.data.ModuleDefinition("csv_test", "CSV", "⚡", "#fff", questions)
    val errors = com.example.data.QuestionValidator.validateAll(mapOf("csv" to module))
    org.junit.Assert.assertTrue("No deben existir errores de integridad en el CSV: $errors", errors.isEmpty())
  }

  @Test
  fun `withShuffledOptions preserves correct answer text`() {
    val q = com.example.data.Question(
      q = "¿Cuál es la profundidad mínima en acera?",
      opts = listOf("0,60 m", "0,80 m", "1,00 m", "0,50 m"),
      a = 0,
      exp = "0,60 m según ITC-BT-07",
      ref = "ITC-BT-07"
    )
    val originalCorrect = q.opts[q.a]

    // Ejecutamos varias veces para probar el barajado
    repeat(10) {
      val shuffled = q.withShuffledOptions()
      assertEquals(originalCorrect, shuffled.opts[shuffled.a])
      assertEquals(4, shuffled.opts.size)
      org.junit.Assert.assertTrue(shuffled.opts.containsAll(q.opts))
    }
  }

  @Test
  fun `continuous itc practice allows endless tests with shuffled order after bank exhausted`() {
    val app = ApplicationProvider.getApplicationContext<android.app.Application>()
    val viewModel = MainViewModel(app)

    val pool33 = viewModel.allQuestions.values.flatMap { it.questions }.distinctBy { it.q }.filter { it.itcNumber() == 33 }
    assertEquals(30, pool33.size)

    // Intento 1 en ITC-33 (bloque de 20 preguntas)
    viewModel.startItcPractice(33, "Instalaciones de obras")
    val session1 = viewModel.activeExamModule
    org.junit.Assert.assertNotNull(session1)
    assertEquals(20, session1!!.questions.size)

    // Simulamos completar todas las preguntas del test 1
    session1.questions.forEach { _ ->
      viewModel.selectedOptionIndex = 0
      viewModel.submitAnswer()
      viewModel.nextQuestion()
    }
    org.junit.Assert.assertTrue(viewModel.examCompleted)

    // Intento 2 tras agotar el primer bloque: quedan 10 no vistas + 10 recicladas
    viewModel.repeatCurrentExamShuffled()
    val session2 = viewModel.activeExamModule
    org.junit.Assert.assertNotNull(session2)
    assertEquals(20, session2!!.questions.size)
    org.junit.Assert.assertFalse(viewModel.examCompleted)

    // Simulamos completar el test 2 para agotar las 30 preguntas del banco
    session2.questions.forEach { _ ->
      viewModel.selectedOptionIndex = 0
      viewModel.submitAnswer()
      viewModel.nextQuestion()
    }

    // Intento 3: Banco totalmente agotado -> entra en Modo Asimilación y Refuerzo
    viewModel.repeatCurrentExamShuffled()
    val session3 = viewModel.activeExamModule
    org.junit.Assert.assertNotNull(session3)
    assertEquals(20, session3!!.questions.size)
    org.junit.Assert.assertTrue(session3.label.contains("Refuerzo y Asimilación"))
  }
}

