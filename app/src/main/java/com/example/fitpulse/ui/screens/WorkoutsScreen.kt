package com.example.fitpulse.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitpulse.WorkoutViewModel
import com.example.fitpulse.ui.components.*
import com.example.fitpulse.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutsScreen(onBack: () -> Unit, onStart: (Long) -> Unit) {
    val vm: WorkoutViewModel = viewModel()
    val workouts by vm.workouts.collectAsState()

    Scaffold(
        containerColor = BgDeep,
        topBar = {
            TopAppBar(
                title = { Text("Entraînements", color = TextHi, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onBack) { Icon(Icons.Default.ArrowBack, null, tint = TextMid) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BgDeep)
            )
        }
    ) { pad ->
        LazyColumn(Modifier.padding(pad).padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(workouts) { w ->
                GlassCard(Modifier.fillMaxWidth()) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text(w.name, style = MaterialTheme.typography.titleLarge, color = TextHi, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(4.dp))
                            Text(w.description, style = MaterialTheme.typography.labelMedium, color = TextMid, maxLines = 2)
                            Spacer(Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Tag("${w.durationMin} min", NeonCyan)
                                Tag(w.difficulty, NeonPurple)
                                Tag("${w.calories} kcal", NeonAmber)
                            }
                        }
                    }
                    Spacer(Modifier.height(14.dp))
                    NeonButton("Démarrer", { vm.beginWorkout(w); onStart(w.id) }, Modifier.fillMaxWidth(),
                        icon = Icons.Default.FitnessCenter
                    )
                }
            }
        }
    }
}
