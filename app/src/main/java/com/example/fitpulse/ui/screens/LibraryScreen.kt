package com.example.fitpulse.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitpulse.ExerciseViewModel
import com.example.fitpulse.ui.components.*
import com.example.fitpulse.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(onBack: () -> Unit, onExercise: (Long) -> Unit = {}) {
    val vm: ExerciseViewModel = viewModel()
    val exercises by vm.exercises.collectAsState()
    var query by remember { mutableStateOf("") }
    val filtered = remember(query, exercises) {
        if (query.isBlank()) exercises else exercises.filter { it.name.contains(query, true) || it.muscleGroup.contains(query, true) || it.category.contains(query, true) }
    }
    val categories = remember(filtered) { filtered.map { it.category }.distinct() }

    Scaffold(containerColor = DarkBg, topBar = {
        TopAppBar(title = { Text("Bibliothèque", color = TextPrimary) }, navigationIcon = {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = null, tint = TextPrimary) }
        }, colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBg))
    }) { pad ->
        Column(Modifier.padding(pad).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(value = query, onValueChange = { query = it }, label = { Text("Rechercher un exercice") }, leadingIcon = { Icon(Icons.Default.Search, null) }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = NeonGreen, unfocusedBorderColor = DarkBorder, focusedLabelColor = NeonGreen, unfocusedLabelColor = TextSecondary))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                categories.forEach { cat ->
                    item { SectionHeader(cat) }
                    items(filtered.filter { it.category == cat }) { ex ->
                        GlassCard(Modifier.fillMaxWidth()) {
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Column(Modifier.weight(1f)) {
                                    Text(ex.name, style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                                    Text("${ex.muscleGroup} • ${ex.difficulty} • ${ex.durationSec}s", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                                }
                                NeonButton("Voir", { onExercise(ex.id) }, Modifier.height(36.dp), NeonCyan)
                            }
                        }
                    }
                }
            }
        }
    }
}
