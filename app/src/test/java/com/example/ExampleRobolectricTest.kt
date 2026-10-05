package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.itcNumber
import com.example.data.withShuffledOptions
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
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
    assertEquals(43, pool33.size)

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

    // Intento 2: 20 preguntas más (quedan 23 no vistas)
    viewModel.repeatCurrentExamShuffled()
    val session2 = viewModel.activeExamModule
    org.junit.Assert.assertNotNull(session2)
    assertEquals(20, session2!!.questions.size)
    org.junit.Assert.assertFalse(viewModel.examCompleted)

    // Simulamos completar el test 2 (quedan 3 no vistas)
    session2.questions.forEach { _ ->
      viewModel.selectedOptionIndex = 0
      viewModel.submitAnswer()
      viewModel.nextQuestion()
    }

    // Intento 3: Quedan solo 3 no vistas (< 20 target) -> entra en Modo Asimilación y Refuerzo reciclado
    viewModel.repeatCurrentExamShuffled()
    val session3 = viewModel.activeExamModule
    org.junit.Assert.assertNotNull(session3)
    assertEquals(20, session3!!.questions.size)
    org.junit.Assert.assertTrue(session3.label.contains("Refuerzo y Asimilación"))
  }

  @Test
  fun `student calendar and study plan initialization and custom event insertion`() = runBlocking {
    val app = ApplicationProvider.getApplicationContext<android.app.Application>()
    val repository = com.example.data.EnigmaRepository(app)

    repository.initializeDefaultCalendarAndPlan()
    val initialEvents = repository.studentEventsFlow.first()
    org.junit.Assert.assertTrue("Debe inicializar eventos por defecto", initialEvents.isNotEmpty())

    val theoryExam = initialEvents.firstOrNull { it.eventType == "EXAM_THEORY" }
    val practiceExam = initialEvents.firstOrNull { it.eventType == "EXAM_PRACTICE" }
    val theoryClass = initialEvents.firstOrNull { it.eventType == "CLASS_THEORY" }
    val practiceClass = initialEvents.firstOrNull { it.eventType == "CLASS_PRACTICE" }

    org.junit.Assert.assertNotNull("Debe existir evento de Examen Teórico", theoryExam)
    org.junit.Assert.assertNotNull("Debe existir evento de Examen Práctico", practiceExam)
    org.junit.Assert.assertNotNull("Debe existir evento de Clase Teórica", theoryClass)
    org.junit.Assert.assertNotNull("Debe existir evento de Clase Práctica", practiceClass)

    // Insertar un evento nuevo del alumno
    val customEvent = com.example.data.StudentCalendarEventEntity(
      title = "Tutoría de Dudas sobre ITC-BT-52",
      description = "Revisión de esquemas de recarga de VE y protecciones.",
      date = "2026-11-10",
      time = "17:00",
      durationMinutes = 60,
      eventType = "CLASS_THEORY",
      relatedItc = "ITC-BT-52"
    )
    val eventId = repository.insertStudentEvent(customEvent)
    org.junit.Assert.assertTrue(eventId > 0)

    // Toggle completion
    repository.toggleStudentEventCompletion(eventId.toInt(), true)
    val updatedEvents = repository.studentEventsFlow.first()
    val savedEvent = updatedEvents.firstOrNull { it.id == eventId.toInt() }
    org.junit.Assert.assertNotNull(savedEvent)
    org.junit.Assert.assertTrue(savedEvent!!.isCompleted)

    // Test Automated Study Plan Generator
    repository.generateAutomatedStudyPlan(
      targetTheoryDate = "2026-12-01",
      targetPracticeDate = "2026-12-08",
      planMode = "INTENSIVO",
      callName = "Convocatoria Extraordinaria 2026"
    )

    val plan = repository.getStudyPlanDirect()
    org.junit.Assert.assertNotNull(plan)
    assertEquals("2026-12-01", plan!!.targetExamDate)
    assertEquals("2026-12-08", plan.targetPracticeExamDate)
    assertEquals("INTENSIVO", plan.studyPlanMode)
    assertEquals(15, plan.hoursPerWeek)

    val generatedEvents = repository.studentEventsFlow.first()
    org.junit.Assert.assertTrue(generatedEvents.any { it.eventType == "EXAM_THEORY" && it.date == "2026-12-01" })
    org.junit.Assert.assertTrue(generatedEvents.any { it.eventType == "EXAM_PRACTICE" && it.date == "2026-12-08" })
  }
}

