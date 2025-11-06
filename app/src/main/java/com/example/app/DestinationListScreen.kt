package com.example.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app.R

data class Destination(val name: String, val description: String, val image: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestinationListScreen(navController: NavController) {
    val destinations = listOf(
        Destination("Київ", "Столиця України з величною історією", R.drawable.kyiv),
        Destination("Львів", "Місто кави, музики та архітектури", R.drawable.lviv),
        Destination("Одеса", "Морське місто з гумором і шармом", R.drawable.odesa),
        Destination("Карпати", "Гори, природа та найкращі краєвиди України", R.drawable.karpaty)
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Популярні напрямки", style = MaterialTheme.typography.titleLarge) })
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize().padding(12.dp)
        ) {
            items(destinations.size) { index ->
                val dest = destinations[index]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { navController.navigate("details/${dest.name}") },
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Image(
                            painter = painterResource(id = dest.image),
                            contentDescription = dest.name,
                            modifier = Modifier
                                .size(80.dp)
                                .padding(end = 12.dp)
                        )
                        Column {
                            Text(text = dest.name, style = MaterialTheme.typography.titleMedium)
                            Text(text = dest.description, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}
