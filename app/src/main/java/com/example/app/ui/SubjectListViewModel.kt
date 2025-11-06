package com.example.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.StudyRepository
import com.example.app.data.Subject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SubjectListUiState(
    val subjects: List<Subject> = emptyList(),
    val newSubjectName: String = ""
)

class SubjectListViewModel(private val repository: StudyRepository) : ViewModel() {
    val uiState: StateFlow<SubjectListUiState> = repository.observeSubjects()
        .map { SubjectListUiState(subjects = it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SubjectListUiState())

    fun addSubject(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch { repository.addSubject(name) }
    }
}


