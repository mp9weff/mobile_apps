package com.example.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.AiService
import com.example.app.data.TriviaQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TriviaUiState(
    val questions: List<TriviaQuestion> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val topic: String = ""
)

class TriviaViewModel(private val aiService: AiService) : ViewModel() {
    private val _uiState = MutableStateFlow(TriviaUiState())
    val uiState: StateFlow<TriviaUiState> = _uiState.asStateFlow()

    fun generateQuestions(topic: String) {
        if (topic.isBlank()) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null,
                topic = topic,
                questions = emptyList()
            )

            val result = aiService.generateTriviaQuestions(topic)
            result.getOrNull()?.let { questions ->
                _uiState.value = _uiState.value.copy(
                    questions = questions,
                    isLoading = false,
                    error = null
                )
            } ?: run {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to generate questions: ${result.exceptionOrNull()?.message ?: "Unknown error"}"
                )
            }
        }
    }

    fun revealAnswer(questionIndex: Int) {
        val currentQuestions = _uiState.value.questions.toMutableList()
        if (questionIndex in currentQuestions.indices) {
            currentQuestions[questionIndex] = currentQuestions[questionIndex].copy(isRevealed = true)
            _uiState.value = _uiState.value.copy(questions = currentQuestions)
        }
    }
}

