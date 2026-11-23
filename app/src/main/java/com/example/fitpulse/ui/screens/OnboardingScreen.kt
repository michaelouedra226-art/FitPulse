package com.example.fitpulse.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitpulse.ui.components.*
import com.example.fitpulse.ui.theme.*

@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    var step by remember { mutableStateOf(0) }
    val goals = listOf("Perte de poids", "Prise de muscle", "Endurance", "Souplesse")
    val levels = listOf("Débutant", "Intermédiaire", "Avancé")
    var goal by remember { mutableStateOf(goals[0]) }
    var level by remember { mutableStateOf(levels[0]) }
    var days by remember { mutableStateOf(3) }

    Box(
        Modifier
            .fillMaxSize()
            .background(BgDeep)
    ) {
        Column(
            Modifier
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Box(Modifier.fillMaxWidth()) {
                Text(
                    "Bienvenue sur",
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextHi,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Text("FitPulse", style = MaterialTheme.typography.displayLarge, color = NeonGreen, fontWeight = FontWeight.Black)

            when (step) {
                0 -> {
                    SectionHeader("Ton objectif", accent = true)
                    goals.forEach { g ->
                        GlassCard(Modifier.fillMaxWidth().clickable(onClick = { goal = g })) {
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Text(g, color = if (goal == g) NeonGreen else TextHi, fontWeight = if (goal == g) FontWeight.Bold else FontWeight.Normal)
                                androidx.compose.material3.RadioButton(
                                    selected = goal == g,
                                    onClick = { goal = g },
                                    colors = RadioButtonDefaults.colors(selectedColor = NeonGreen)
                                )
                            }
                        }
                    }
                }
                1 -> {
                    SectionHeader("Ton niveau", accent = true)
                    levels.forEach { l ->
                        GlassCard(Modifier.fillMaxWidth().clickable(onClick = { level = l })) {
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Text(l, color = if (level == l) NeonGreen else TextHi, fontWeight = if (level == l) FontWeight.Bold else FontWeight.Normal)
                                androidx.compose.material3.RadioButton(
                                    selected = level == l,
                                    onClick = { level = l },
                                    colors = androidx.compose.material3.RadioButtonDefaults.colors(selectedColor = NeonGreen)
                                )
                            }
                        }
                    }
                }
                else -> {
                    SectionHeader("Jours par semaine", accent = true)
                    GlassCard(Modifier.fillMaxWidth()) {
                        Column {
                            Text("$days jours / semaine", style = MaterialTheme.typography.titleLarge, color = TextHi, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(12.dp))
                            Slider(
                                value = days.toFloat(),
                                onValueChange = { days = it.toInt() },
                                valueRange = 2f..6f,
                                steps = 3,
                                colors = SliderDefaults.colors(
                                    thumbColor = NeonGreen,
                                    activeTrackColor = NeonGreen,
                                    inactiveTrackColor = BgCard
                                )
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.weight(1f))
            NeonButton(
                if (step < 2) "Suivant →" else "Commencer ✓",
                { if (step < 2) step++ else onFinish() },
                Modifier.fillMaxWidth()
            )
        }
    }
}
