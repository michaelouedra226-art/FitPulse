package com.example.fitpulse.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitpulse.WorkoutViewModel
import com.example.fitpulse.ui.components.*
import com.example.fitpulse.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
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

    val progress = 1f - remaining / 30f

    Scaffold(
        containerColor = BgDeep,
        topBar = {
            TopAppBar(
                title = { Text("Séance active", color = TextHi, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null, tint = TextMid) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BgDeep)
            )
        }
    ) { pad ->
        Column(
            Modifier
                .padding(pad)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Timer Ring
            Spacer(Modifier.height(12.dp))
            GlassCard(Modifier.fillMaxWidth().height(160.dp)) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    ProgressRing(
                        progress = progress,
                        size = 120,
                        strokeWidth = 8,
                        color = NeonGreen,
                        label = "$remaining s"
                    )
                }
            }

            // Controls
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                GlassCard(Modifier.weight(1f)) {
                    Text(
                        if (running) "En pause" else "En cours",
                        style = MaterialTheme.typography.labelLarge,
                        color = if (running) NeonAmber else NeonGreen,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                GlassCard(Modifier.weight(1f)) {
                    Text(
                        "Série $setNum",
                        style = MaterialTheme.typography.labelLarge,
                        color = TextHi,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Buttons
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                NeonButton(
                    if (running) "Pause" else "Reprendre",
                    { running = !running },
                    Modifier.weight(1f),
                    color = NeonAmber
                )
                NeonButton(
                    "Passer",
                    { remaining = 30 },
                    Modifier.weight(1f),
                    color = NeonCyan
                )
            }

            // Log Set
            GlassCard(Modifier.fillMaxWidth()) {
                Text("Valider la série", style = MaterialTheme.typography.labelLarge, color = TextHi, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = reps.toString(),
                        onValueChange = { it.toIntOrNull()?.let { v -> reps = v } },
                        label = { Text("Répétitions") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonGreen,
                            unfocusedBorderColor = BorderGlass,
                            focusedLabelColor = NeonGreen,
                            unfocusedLabelColor = TextMid
                        )
                    )
                    OutlinedTextField(
                        value = weight.toString(),
                        onValueChange = { it.toDoubleOrNull()?.let { v -> weight = v } },
                        label = { Text("Poids kg") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonGreen,
                            unfocusedBorderColor = BorderGlass,
                            focusedLabelColor = NeonGreen,
                            unfocusedLabelColor = TextMid
                        )
                    )
                }
                Spacer(Modifier.height(12.dp))
                NeonButton("Valider", {
                    vm.logSet(sessionId, "Exercice", setNum, reps, weight)
                    setNum++
                    remaining = 30
                }, Modifier.fillMaxWidth())
            }

            // Finish
            NeonButton("Terminer la séance", {
                vm.finishWorkout(sessionId, 250)
                onBack()
            }, Modifier.fillMaxWidth(), color = NeonRed)
        }
    }
}
