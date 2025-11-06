package com.example.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.app.data.LabStatus
import com.example.app.data.LabWork
import com.example.app.di.StudyContainer
import com.example.app.ui.SubjectDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectDetailScreen(navController: NavController, subjectId: Long) {
    val context = LocalContext.current
    val vm: SubjectDetailViewModel = viewModel(factory = remember {
        val repo = StudyContainer(context).repository
        object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SubjectDetailViewModel(repo, subjectId) as T
            }
        }
    })

    val ui = vm.uiState.collectAsState().value
    val subject = ui.subject

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(subject?.name ?: "Дисципліна") }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            Row(Modifier.fillMaxWidth()) {
                var title by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier.weight(1f),
                    label = { Text("Нова лабораторна") }
                )
                Spacer(Modifier.width(8.dp))
                Button(onClick = { vm.addLab(subjectId, title); title = "" }) { Text("Додати") }
            }
            Spacer(Modifier.height(16.dp))

            LazyColumn {
                items(ui.labs, key = { it.id }) { lab ->
                    LabItem(lab = lab, onStatusChange = { vm.updateLabStatus(lab, it) }, onCommentChange = { vm.updateLabComment(lab, it) })
                    Divider()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LabItem(
    lab: LabWork,
    onStatusChange: (LabStatus) -> Unit,
    onCommentChange: (String) -> Unit
) {
    Column(Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(text = lab.title, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        Row {
            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    readOnly = true,
                    value = lab.status.name,
                    onValueChange = {},
                    label = { Text("Статус") },
                    modifier = Modifier.menuAnchor().weight(1f)
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    LabStatus.values().forEach { status ->
                        DropdownMenuItem(
                            text = { Text(status.name) },
                            onClick = { expanded = false; onStatusChange(status) }
                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        var comment by remember(lab.id) { mutableStateOf(lab.comment.orEmpty()) }
        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            label = { Text("Коментар") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            TextButton(onClick = { onCommentChange(comment) }) { Text("Зберегти") }
        }
    }
}


