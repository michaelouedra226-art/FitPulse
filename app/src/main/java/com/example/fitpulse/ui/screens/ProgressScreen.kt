package com.example.fitpulse.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitpulse.WorkoutViewModel
import com.example.fitpulse.ui.components.*
import com.example.fitpulse.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(onBack: () -> Unit) {
    val vm: WorkoutViewModel = viewModel()
    val sessions by vm.sessions.collectAsState()
    val completed by vm.completedCount.collectAsState()
    val calories by vm.totalCalories.collectAsState()

    Scaffold(containerColor = DarkBg, topBar = {
        TopAppBar(title = { Text("Progrès", color = TextPrimary) }, navigationIcon = {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = null, tint = TextPrimary) }
        }, colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBg))
    }) { pad ->
        Column(Modifier.padding(pad).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            GradientHeader("Tes progrès", "Continue")
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard("Terminés", completed.toString(), Icons.Default.Star, Modifier.weight(1f))
                StatCard("Calories", calories.toString(), Icons.Default.Star, Modifier.weight(1f), AccentOrange)
            }
            SectionHeader("Séances récentes")
            if (sessions.isEmpty()) EmptyState("Aucune séance", "Lance un entraînement pour voir tes progrès") else
                sessions.take(8).forEach { s ->
                    GlassCard(Modifier.fillMaxWidth()) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text(s.workoutName, style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                                Text("${s.caloriesBurned} kcal", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                            }
                            Text(if (s.completed) "Done" else "In progress", color = if (s.completed) NeonGreen else AccentOrange, style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }
        }
    }
}
