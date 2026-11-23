package com.example.fitpulse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(onBack: () -> Unit) {
    val vm: WorkoutViewModel = viewModel()
    val sessions by vm.sessions.collectAsState()
    val completed by vm.completedCount.collectAsState()
    val calories by vm.totalCalories.collectAsState()

    Scaffold(
        containerColor = BgDeep,
        topBar = {
            TopAppBar(
                title = { Text("Progrès", color = TextHi, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onBack) { Icon(Icons.Default.ArrowBack, null, tint = TextMid) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BgDeep)
            )
        }
    ) { pad ->
        LazyColumn(Modifier.padding(pad).padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            item {
                SectionHeader("Vue d'ensemble", accent = true)
            }
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatCard("Terminés", completed.toString(), Icons.Default.FitnessCenter, Modifier.weight(1f))
                    StatCard("Calories", "${calories}", Icons.Default.LocalFireDepartment, Modifier.weight(1f), NeonAmber)
                }
            }

            item {
                SectionHeader("Séances récentes", accent = true)
            }
            if (sessions.isEmpty()) {
                item { EmptyState("Aucune séance", "Lance un entraînement pour voir tes progrès") }
            } else {
                items(sessions.take(8)) { s ->
                    GlassCard(Modifier.fillMaxWidth()) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text(s.workoutName, style = MaterialTheme.typography.titleLarge, color = TextHi, fontWeight = FontWeight.Bold)
                                Spacer(Modifier.height(4.dp))
                                Text("${s.caloriesBurned} kcal", style = MaterialTheme.typography.labelMedium, color = TextMid)
                            }
                            val done = s.completed
                            Box(
                                Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (done) NeonGreen.copy(alpha = 0.15f) else NeonAmber.copy(alpha = 0.15f))
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    if (done) "✓ Fait" else "En cours",
                                    color = if (done) NeonGreen else NeonAmber,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
