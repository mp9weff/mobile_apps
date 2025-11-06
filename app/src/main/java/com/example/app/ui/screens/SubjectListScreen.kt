package com.example.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import androidx.navigation.NavController
import com.example.app.ui.SubjectListViewModel
import com.example.app.ui.SubjectListUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectListScreen(navController: NavController) {
    val vm: SubjectListViewModel = koinViewModel()

    val state = vm.uiState.collectAsState()

    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("Дисципліни семестру") }) }) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            Row(Modifier.fillMaxWidth()) {
                var text by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.weight(1f),
                    label = { Text("Нова дисципліна") }
                )
                Spacer(Modifier.width(8.dp))
                Button(onClick = { vm.addSubject(text); text = "" }) { Text("Додати") }
            }
            Spacer(Modifier.height(16.dp))

            val ui: SubjectListUiState = state.value
            LazyColumn {
                items(ui.subjects, key = { it.id }) { subject ->
                    ListItem(
                        headlineContent = { Text(subject.name) },
                        supportingContent = { subject.teacher?.let { Text(it) } },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navController.navigate("subject/${subject.id}") }
                    )
                    Divider()
                }
            }
        }
    }
}


