package com.example.fitpulse.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
        if (query.isBlank()) exercises
        else exercises.filter {
            it.name.contains(query, true) ||
            it.muscleGroup.contains(query, true) ||
            it.category.contains(query, true)
        }
    }
    val categories = remember(filtered) { filtered.map { it.category }.distinct() }

    Scaffold(
        containerColor = BgDeep,
        topBar = {
            TopAppBar(
                title = { Text("Bibliothèque", color = TextHi, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onBack) { Icon(Icons.Default.ArrowBack, null, tint = TextMid) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BgDeep)
            )
        }
    ) { pad ->
        Column(Modifier.padding(pad).padding(horizontal = 20.dp)) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Rechercher un exercice") },
                leadingIcon = { Icon(Icons.Default.Search, null, tint = TextMid) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonGreen,
                    unfocusedBorderColor = BorderGlass,
                    focusedLabelColor = NeonGreen,
                    unfocusedLabelColor = TextMid,
                    focusedLeadingIconColor = NeonGreen
                )
            )
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                categories.forEach { cat ->
                    item { SectionHeader(cat, accent = true) }
                    items(filtered.filter { it.category == cat }) { ex ->
                        GlassCard(Modifier.fillMaxWidth()) {
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Column(Modifier.weight(1f)) {
                                    Text(ex.name, style = MaterialTheme.typography.titleLarge, color = TextHi, fontWeight = FontWeight.Bold)
                                    Spacer(Modifier.height(4.dp))
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        Tag(ex.muscleGroup, NeonCyan)
                                        Tag("${ex.difficulty}", NeonPurple)
                                        Tag("${ex.durationSec}s", NeonAmber)
                                    }
                                }
                                NeonButton("Voir", { onExercise(ex.id) }, Modifier.height(36.dp), NeonCyan)
                            }
                        }
                    }
                }
                if (filtered.isEmpty()) {
                    item { EmptyState("Aucun résultat", "Essaie un autre mot-clé") }
                }
            }
        }
    }
}
