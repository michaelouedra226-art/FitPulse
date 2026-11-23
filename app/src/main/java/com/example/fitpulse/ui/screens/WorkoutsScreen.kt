package com.example.fitpulse.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitpulse.WorkoutViewModel

import com.example.fitpulse.ui.components.*
import com.example.fitpulse.ui.theme.*

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun WorkoutsScreen(onBack: () -> Unit, onStart: (Long) -> Unit) {
    val vm: WorkoutViewModel = viewModel()
    val workouts by vm.workouts.collectAsState()

    Scaffold(containerColor = DarkBg, topBar = {
        TopAppBar(title = { Text("Entraînements", color = TextPrimary) }, navigationIcon = {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = null, tint = TextPrimary) }
        }, colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBg))
    }) { pad ->
        LazyColumn(Modifier.padding(pad).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(workouts) { w ->
                GlassCard(Modifier.fillMaxWidth()) {
                    Column {
                        Text(w.name, style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                        Text(w.description, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                        Spacer(Modifier.height(8.dp))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("${w.durationMin} min • ${w.difficulty} • ${w.calories} kcal", style = MaterialTheme.typography.bodySmall, color = NeonGreen)
                            NeonButton("Démarrer", { vm.beginWorkout(w); onStart(w.id) }, Modifier.height(40.dp))
                        }
                    }
                }
            }
        }
    }
}
