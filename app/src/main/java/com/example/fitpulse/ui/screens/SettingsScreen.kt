package com.example.fitpulse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitpulse.ui.components.*
import com.example.fitpulse.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    var reminderEnabled by remember { mutableStateOf(true) }
    var hour by remember { mutableStateOf(18) }

    Scaffold(
        containerColor = BgDeep,
        topBar = {
            TopAppBar(
                title = { Text("Réglages", color = TextHi, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onBack) { Icon(Icons.Default.ArrowBack, null, tint = TextMid) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BgDeep)
            )
        }
    ) { pad ->
        Column(Modifier.padding(pad).padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            SectionHeader("Rappels", accent = true)

            GlassCard(Modifier.fillMaxWidth()) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Rappel quotidien", color = TextHi, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    Switch(
                        checked = reminderEnabled,
                        onCheckedChange = { reminderEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFF041A0E),
                            checkedTrackColor = NeonGreen,
                            uncheckedThumbColor = TextLo,
                            uncheckedTrackColor = BgCard
                        )
                    )
                }
            }

            GlassCard(Modifier.fillMaxWidth()) {
                Column {
                    Text("Heure du rappel : $hour h", color = TextHi, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(8.dp))
                    Slider(
                        value = hour.toFloat(),
                        onValueChange = { hour = it.toInt() },
                        valueRange = 6f..22f,
                        steps = 15,
                        colors = SliderDefaults.colors(
                            thumbColor = NeonGreen,
                            activeTrackColor = NeonGreen,
                            inactiveTrackColor = BgCard
                        )
                    )
                }
            }

            SectionHeader("Préférences", accent = true)

            GlassCard(Modifier.fillMaxWidth()) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Langue", color = TextMid)
                        Text("Français", color = NeonCyan, fontWeight = FontWeight.Bold)
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Animations", color = TextMid)
                        Text("SVG", color = NeonPurple, fontWeight = FontWeight.Bold)
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Persistance", color = TextMid)
                        Text("Room", color = NeonGreen, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
