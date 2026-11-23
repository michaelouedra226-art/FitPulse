package com.example.fitpulse.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitpulse.WorkoutViewModel
import com.example.fitpulse.ui.components.*
import com.example.fitpulse.ui.theme.*

@Composable
fun DashboardScreen(onWorkouts: () -> Unit, onProgress: () -> Unit, onProfile: () -> Unit, onLibrary: () -> Unit = {}) {
    val vm: WorkoutViewModel = viewModel()
    val workouts by vm.workouts.collectAsState()
    val completed by vm.completedCount.collectAsState()
    val calories by vm.totalCalories.collectAsState()
    val profile by vm.profile.collectAsState()

    Scaffold(containerColor = DarkBg) { pad ->
        LazyColumn(Modifier.padding(pad).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            item { GradientHeader("Bonjour, ${profile?.name ?: "Athlète"}", "Écrase ta séance du jour") }
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatCard("Séances", completed.toString(), Icons.Default.Star, Modifier.weight(1f))
                    StatCard("Calories", calories.toString(), Icons.Default.Star, Modifier.weight(1f), AccentOrange)
                }
            }
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatCard("Série", "${profile?.streak ?: 0} j", Icons.Default.Star, Modifier.weight(1f), AccentRed)
                    StatCard("Goal", profile?.goal ?: "Build muscle", Icons.Default.Star, Modifier.weight(1f), NeonCyan)
                }
            }
            item { SectionHeader("Quick actions") }
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
    NeonButton("Démarrer", onWorkouts, Modifier.weight(1f))
    OutlinedButton(onClick = onLibrary, modifier = Modifier.weight(1f).height(52.dp), shape = RoundedCornerShape(14.dp)) {
        Text("Bibliothèque", color = TextPrimary)
    }
}
            }
            item { SectionHeader("Suggestions") }
            items(workouts.take(4)) { w ->
                GlassCard(Modifier.fillMaxWidth()) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text(w.name, style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                            Text("${w.durationMin} min • ${w.difficulty} • ${w.calories} kcal", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                        }
                        Icon(Icons.Default.Star, contentDescription = null, tint = NeonGreen)
                    }
                }
            }
        }
    }
}
