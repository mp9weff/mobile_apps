package com.example.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestinationDetailScreen(placeName: String) {
    val imageRes = when (placeName) {
        "Київ" -> R.drawable.kyiv
        "Львів" -> R.drawable.lviv
        "Одеса" -> R.drawable.odesa
        "Карпати" -> R.drawable.karpaty
        else -> R.drawable.ic_launcher_foreground
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = placeName) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = placeName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(bottom = 16.dp)
            )

            Text(
                text = """
                    Детальна інформація про $placeName.
                    Тут ви можете дізнатися більше про історію, цікаві місця 
                    та особливості цього регіону.
                """.trimIndent(),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
