package com.example.fitpulse.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitpulse.ui.components.*
import com.example.fitpulse.ui.theme.*

@Composable
fun SettingsScreen(onBack: () -> Unit) {
    var reminderEnabled by remember { mutableStateOf(true) }
    var hour by remember { mutableStateOf(18) }
    Scaffold(containerColor = DarkBg, topBar = {
        TopAppBar(title = { Text("Réglages", color = TextPrimary) }, navigationIcon = {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = null, tint = TextPrimary) }
        }, colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBg))
    }) { pad ->
        Column(Modifier.padding(pad).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            GlassCard(Modifier.fillMaxWidth()) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Rappel quotidien", color = TextPrimary)
                    Switch(checked = reminderEnabled, onCheckedChange = { reminderEnabled = it }, colors = SwitchDefaults.colors(checkedThumbColor = NeonGreen, checkedTrackColor = NeonGreen.copy(alpha = 0.4f)))
                }
            }
            GlassCard(Modifier.fillMaxWidth()) {
                Column {
                    Text("Heure du rappel : $hour h", color = TextPrimary)
                    Slider(value = hour.toFloat(), onValueChange = { hour = it.toInt() }, valueRange = 6f..22f, steps = 15, colors = SliderDefaults.colors(thumbColor = NeonGreen, activeTrackColor = NeonGreen))
                }
            }
            GlassCard(Modifier.fillMaxWidth()) {
                Text("Langue : Français", color = TextSecondary)
                Text("Animations : SVG", color = TextSecondary)
                Text("Persistance : Room", color = TextSecondary)
            }
        }
    }
}
