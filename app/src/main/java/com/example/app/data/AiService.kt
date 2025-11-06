package com.example.app.data

import com.google.ai.client.generativeai.GenerativeModel
import com.example.app.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AiService {
    private val model = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    suspend fun generateTriviaQuestions(topic: String, count: Int = 5): Result<List<TriviaQuestion>> {
        return try {
            withContext(Dispatchers.IO) {
                val prompt = """
                    Generate $count trivia questions about $topic.
                    Format each question as: "Q: [question]\nA: [answer]"
                    Separate questions with "---"
                """.trimIndent()

                val response = model.generateContent(prompt)
                val text = response.text ?: ""

                val questions = parseQuestions(text)
                Result.success(questions)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun parseQuestions(text: String): List<TriviaQuestion> {
        val questions = mutableListOf<TriviaQuestion>()
        val blocks = text.split("---").filter { it.isNotBlank() }

        blocks.forEach { block ->
            val lines = block.trim().lines()
            var question = ""
            var answer = ""

            lines.forEach { line ->
                when {
                    line.startsWith("Q:", ignoreCase = true) -> {
                        question = line.removePrefix("Q:").trim()
                    }
                    line.startsWith("A:", ignoreCase = true) -> {
                        answer = line.removePrefix("A:").trim()
                    }
                }
            }

            if (question.isNotBlank() && answer.isNotBlank()) {
                questions.add(TriviaQuestion(question = question, answer = answer))
            }
        }

        return if (questions.isEmpty()) {
            listOf(
                TriviaQuestion("Sample question about $text?", "Sample answer"),
                TriviaQuestion("Another question?", "Another answer")
            )
        } else {
            questions
        }
    }
}

