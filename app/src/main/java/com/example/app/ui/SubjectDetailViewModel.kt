package com.example.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.LabStatus
import com.example.app.data.LabWork
import com.example.app.data.StudyRepository
import com.example.app.data.Subject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SubjectDetailUiState(
    val subject: Subject? = null,
    val labs: List<LabWork> = emptyList(),
    val newLabTitle: String = ""
)

class SubjectDetailViewModel(
    private val repository: StudyRepository,
    subjectId: Long
) : ViewModel() {
    private val subjectFlow: Flow<Subject?> = repository.observeSubject(subjectId)
    private val labsFlow: Flow<List<LabWork>> = repository.observeLabs(subjectId)
    private val newLabTitleFlow = MutableStateFlow("")

    val uiState: StateFlow<SubjectDetailUiState> = combine(
        subjectFlow,
        labsFlow,
        newLabTitleFlow
    ) { subject, labs, title ->
        SubjectDetailUiState(subject = subject, labs = labs, newLabTitle = title)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SubjectDetailUiState())

    fun setNewLabTitle(value: String) { newLabTitleFlow.value = value }

    fun addLab(subjectId: Long, title: String) {
        if (title.isBlank()) return
        viewModelScope.launch { repository.addLab(subjectId, title) }
        newLabTitleFlow.value = ""
    }

    fun updateLabStatus(lab: LabWork, status: LabStatus) {
        viewModelScope.launch { repository.updateLab(lab.copy(status = status)) }
    }

    fun updateLabComment(lab: LabWork, comment: String) {
        viewModelScope.launch { repository.updateLab(lab.copy(comment = comment)) }
    }
}


