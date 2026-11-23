package com.example.fitpulse.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
fun ActiveWorkoutScreen(onBack: () -> Unit, sessionId: Long) {
    val vm: WorkoutViewModel = viewModel()
    var reps by remember { mutableStateOf(10) }
    var weight by remember { mutableStateOf(20.0) }
    var setNum by remember { mutableStateOf(1) }
    var remaining by remember { mutableStateOf(30) }
    var running by remember { mutableStateOf(true) }

    LaunchedEffect(running) {
        while (running && remaining > 0) {
            kotlinx.coroutines.delay(1000)
            if (running) remaining--
        }
    }

    Scaffold(containerColor = DarkBg, topBar = {
        TopAppBar(title = { Text("Séance active", color = TextPrimary) }, navigationIcon = {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = null, tint = TextPrimary) }
        }, colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBg))
    }) { pad ->
        Column(Modifier.padding(pad).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            GradientHeader("Séance #$sessionId", "Entraînement en cours")
            GlassCard(Modifier.fillMaxWidth()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                    Text("$remaining s", style = MaterialTheme.typography.displayLarge, color = NeonGreen)
                    LinearProgressIndicator(progress = { 1f - remaining / 30f }, modifier = Modifier.fillMaxWidth(), color = NeonGreen, trackColor = DarkBorder)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        NeonButton(if (running) "Pause" else "Reprendre", { running = !running }, Modifier.weight(1f))
                        NeonButton("Passer", { remaining = 30 }, Modifier.weight(1f), NeonCyan)
                    }
                }
            }
            GlassCard(Modifier.fillMaxWidth()) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Série $setNum", style = MaterialTheme.typography.titleLarge, color = NeonGreen)
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedTextField(value = reps.toString(), onValueChange = { it.toIntOrNull()?.let { v -> reps = v } }, label = { Text("Répétitions") }, modifier = Modifier.weight(1f))
                        OutlinedTextField(value = weight.toString(), onValueChange = { it.toDoubleOrNull()?.let { v -> weight = v } }, label = { Text("Poids kg") }, modifier = Modifier.weight(1f))
                    }
                    NeonButton("Valider la série", {
                        vm.logSet(sessionId, "Exercice", setNum, reps, weight)
                        setNum++
                        remaining = 30
                    }, Modifier.fillMaxWidth())
                }
            }
            NeonButton("Terminer la séance", { vm.finishWorkout(sessionId, 250); onBack() }, Modifier.fillMaxWidth(), AccentOrange)
        }
    }
}
