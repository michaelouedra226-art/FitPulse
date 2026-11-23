package com.example.fitpulse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitpulse.WorkoutViewModel
import com.example.fitpulse.ui.components.*
import com.example.fitpulse.ui.theme.*

@Composable
fun DashboardScreen(
    onWorkouts: () -> Unit,
    onProgress: () -> Unit,
    onProfile: () -> Unit,
    onLibrary: () -> Unit = {}
) {
    val vm: WorkoutViewModel = viewModel()
    val workouts by vm.workouts.collectAsState()
    val completed by vm.completedCount.collectAsState()
    val calories by vm.totalCalories.collectAsState()
    val profile by vm.profile.collectAsState()

    LazyColumn(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Card
        item {
            HeroCard(
                title = "Bonjour, ${profile?.name ?: "Athlète"}",
                subtitle = "Prêt à écraser ta séance du jour ?"
            ) {
                NeonButton("Démarrer", onWorkouts, Modifier.weight(1f))
            }
        }

        // Stats Row
        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard("Séances", completed.toString(), Icons.Default.FitnessCenter, Modifier.weight(1f))
                StatCard("Calories", "${calories} kcal", Icons.Default.LocalFireDepartment, Modifier.weight(1f), NeonAmber)
            }
        }

        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard("Série", "${profile?.streak ?: 0} j", Icons.Default.Star, Modifier.weight(1f), NeonPurple)
                StatCard("Objectif", profile?.goal ?: "Muscle", Icons.Default.TrendingUp, Modifier.weight(1f), NeonCyan)
            }
        }

        // Quick Actions
        item {
            SectionHeader("Actions rapides", accent = true)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                GlassCard(Modifier.weight(1f).clickable(onClick = onLibrary)) {
                    Icon(Icons.Default.FitnessCenter, null, tint = NeonGreen, modifier = Modifier.size(24.dp))
                    Spacer(Modifier.height(10.dp))
                    Text("Bibliothèque", style = MaterialTheme.typography.labelLarge, color = TextHi, fontWeight = FontWeight.Bold)
                }
                GlassCard(Modifier.weight(1f).clickable(onClick = onProgress)) {
                    Icon(Icons.Default.TrendingUp, null, tint = NeonCyan, modifier = Modifier.size(24.dp))
                    Spacer(Modifier.height(10.dp))
                    Text("Progrès", style = MaterialTheme.typography.labelLarge, color = TextHi, fontWeight = FontWeight.Bold)
                }
                GlassCard(Modifier.weight(1f).clickable(onClick = onProfile)) {
                    Icon(Icons.Default.Person, null, tint = NeonPurple, modifier = Modifier.size(24.dp))
                    Spacer(Modifier.height(10.dp))
                    Text("Profil", style = MaterialTheme.typography.labelLarge, color = TextHi, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Suggested Workouts
        item {
            SectionHeader("Suggestions", accent = true)
        }
        items(workouts.take(4)) { w ->
            GlassCard(Modifier.fillMaxWidth()) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(w.name, style = MaterialTheme.typography.titleLarge, color = TextHi, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(4.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Tag("${w.durationMin} min", NeonCyan)
                            Tag(w.difficulty, NeonPurple)
                            Tag("${w.calories} kcal", NeonAmber)
                        }
                    }
                    NeonButton("Go", { onWorkouts() }, Modifier.height(36.dp))
                }
            }
        }
    }
}
