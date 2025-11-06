package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoApp()
        }
    }
}

@Composable
fun ToDoApp() {
    var text by remember { mutableStateOf("") }
    var tasks by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Введіть завдання") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp)
        )
        Button(
            onClick = {
                if (text.isNotBlank()) {
                    tasks = tasks + text
                    text = ""
                }
            },
            modifier = Modifier
                .padding(top = 8.dp)
        ) {
            Text("Додати")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Список завдань:", style = MaterialTheme.typography.titleMedium)
        for (task in tasks) {
            Text("• $task")
        }
    }
}
