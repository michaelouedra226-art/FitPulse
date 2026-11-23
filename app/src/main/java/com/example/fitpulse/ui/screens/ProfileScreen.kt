package com.example.fitpulse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onBack: () -> Unit, onSettings: () -> Unit = {}) {
    val vm: WorkoutViewModel = viewModel()
    val profile by vm.profile.collectAsState()
    var name by remember(profile) { mutableStateOf(profile?.name ?: "Athlète") }

    Scaffold(
        containerColor = BgDeep,
        topBar = {
            TopAppBar(
                title = { Text("Profil", color = TextHi, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onBack) { Icon(Icons.Default.ArrowBack, null, tint = TextMid) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BgDeep)
            )
        }
    ) { pad ->
        Column(Modifier.padding(pad).padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            // Avatar + Name
            GlassCard(Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(50))
                            .background(NeonPurple.copy(alpha = 0.2f))
                            .border(2.dp, NeonPurple.copy(alpha = 0.4f), RoundedCornerShape(50)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("A", color = NeonPurple, fontSize = 24.sp, fontWeight = FontWeight.Black)
                    }
                    Spacer(Modifier.width(16.dp))
                    Column(Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Nom") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = NeonGreen,
                                unfocusedBorderColor = BorderGlass,
                                focusedLabelColor = NeonGreen,
                                unfocusedLabelColor = TextMid
                            )
                        )
                    }
                }
            }

            // Stats
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard("Objectif", profile?.goal ?: "Muscle", Icons.Default.Target, Modifier.weight(1f), NeonGreen)
                StatCard("Séances", "${profile?.totalWorkouts ?: 0}", Icons.Default.FitnessCenter, Modifier.weight(1f), NeonCyan)
            }

            // Settings shortcut
            GlassCard(Modifier.fillMaxWidth().clickable(onClick = onSettings)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Réglages", style = MaterialTheme.typography.titleLarge, color = TextHi, fontWeight = FontWeight.Bold)
                        Text("Rappels, langue, unités", style = MaterialTheme.typography.labelMedium, color = TextMid)
                    }
                    Icon(Icons.Default.ChevronRight, null, tint = TextMid)
                }
            }
        }
    }
}
