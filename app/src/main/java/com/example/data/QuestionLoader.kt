package com.example.data

import android.content.Context
import com.example.R
import java.io.BufferedReader
import java.io.InputStreamReader

object QuestionLoader {
    fun loadQuestions(context: Context): List<Question> {
        val questions = mutableListOf<Question>()
        context.resources.openRawResource(R.raw.questions).use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                reader.readLine() // Skip header
                reader.forEachLine { line ->
                    if (line.isBlank()) return@forEachLine
                    // CSV format: "Pregunta",Opcion1,Opcion2,Opcion3,Opcion4,RespuestaCorrectaIndex,Explicacion,Referencia
                    // Need a robust CSV parser here due to quoted strings with commas
                    val parts = parseCsvLine(line)
                    if (parts.size >= 8) {
                        try {
                            questions.add(Question(
                                q = parts[0],
                                opts = listOf(parts[1], parts[2], parts[3], parts[4]),
                                a = parts[5].toInt(),
                                exp = parts[6],
                                ref = parts[7]
                            ))
                        } catch (e: Exception) {
                            // Log error
                        }
                    }
                }
            }
        }
        return questions
    }

    private fun parseCsvLine(line: String): List<String> {
        val list = mutableListOf<String>()
        var inQuotes = false
        var current = StringBuilder()
        for (char in line) {
            when {
                char == '\"' -> inQuotes = !inQuotes
                char == ',' && !inQuotes -> {
                    list.add(current.toString())
                    current = StringBuilder()
                }
                else -> current.append(char)
            }
        }
        list.add(current.toString())
        return list
    }
}
