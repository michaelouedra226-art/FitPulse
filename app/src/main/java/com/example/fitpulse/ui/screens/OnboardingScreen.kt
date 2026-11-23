package com.example.fitpulse.ui.screens

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
    val goals = listOf("Perte de poids","Prise de muscle","Endurance","Souplesse")
    val levels = listOf("Débutant","Intermédiaire","Avancé")
    var goal by remember { mutableStateOf(goals[0]) }
    var level by remember { mutableStateOf(levels[0]) }
    var days by remember { mutableStateOf(3) }

    Scaffold(containerColor = DarkBg) { pad ->
        Column(Modifier.padding(pad).padding(24.dp).fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            GradientHeader("Bienvenue sur FitPulse", "Configurons ton plan")
            when (step) {
                0 -> {
                    SectionHeader("Ton objectif")
                    goals.forEach { g ->
                        GlassCard(Modifier.fillMaxWidth()) {
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Text(g, color = TextPrimary)
                                RadioButton(selected = goal == g, onClick = { goal = g }, colors = RadioButtonDefaults.colors(selectedColor = NeonGreen))
                            }
                        }
                    }
                }
                1 -> {
                    SectionHeader("Ton niveau")
                    levels.forEach { l ->
                        GlassCard(Modifier.fillMaxWidth()) {
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Text(l, color = TextPrimary)
                                RadioButton(selected = level == l, onClick = { level = l }, colors = RadioButtonDefaults.colors(selectedColor = NeonGreen))
                            }
                        }
                    }
                }
                else -> {
                    SectionHeader("Jours par semaine : $days")
                    Slider(value = days.toFloat(), onValueChange = { days = it.toInt() }, valueRange = 2f..6f, steps = 3, colors = SliderDefaults.colors(thumbColor = NeonGreen, activeTrackColor = NeonGreen))
                    Text("Rappel quotidien activé", color = TextSecondary)
                }
            }
            Spacer(Modifier.weight(1f))
            NeonButton(if (step < 2) "Suivant" else "Commencer", {
                if (step < 2) step++ else onFinish()
            }, Modifier.fillMaxWidth())
        }
    }
}
