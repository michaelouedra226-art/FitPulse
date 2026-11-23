package com.example.fitpulse.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitpulse.ExerciseViewModel
import com.example.fitpulse.ui.components.*
import com.example.fitpulse.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailScreen(onBack: () -> Unit, exerciseId: Long) {
    val vm: ExerciseViewModel = viewModel()
    val ex = vm.getById(exerciseId)
    Scaffold(containerColor = DarkBg, topBar = {
        TopAppBar(title = { Text(ex?.name ?: "Exercice", color = TextPrimary) }, navigationIcon = {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = null, tint = TextPrimary) }
        }, colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBg))
    }) { pad ->
        if (ex == null) {
            Box(Modifier.padding(pad).fillMaxSize(), contentAlignment = Alignment.Center) { Text("Exercice introuvable", color = TextSecondary) }
        } else {
            val infinite = rememberInfiniteTransition(label = "anim")
            val scale by infinite.animateFloat(initialValue = 0.9f, targetValue = 1.1f, animationSpec = infiniteRepeatable(tween(800), RepeatMode.Reverse), label = "scale")
            Column(Modifier.padding(pad).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                GlassCard(Modifier.fillMaxWidth().height(200.dp)) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("▶ ${ex.animationRes}", color = NeonGreen, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.scale(scale))
                    }
                }
                GlassCard(Modifier.fillMaxWidth()) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(ex.muscleGroup, style = MaterialTheme.typography.labelLarge, color = NeonCyan)
                        Text("${ex.difficulty} • ${ex.durationSec}s effort • ${ex.restSec}s repos • ${ex.caloriesPerMin} kcal/min", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                        Text(ex.description, style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
                    }
                }
                SectionHeader("Instructions")
                ex.instructions.forEachIndexed { i, step ->
                    GlassCard(Modifier.fillMaxWidth()) {
                        Text("${i + 1}. $step", color = TextPrimary)
                    }
                }
            }
        }
    }
}
