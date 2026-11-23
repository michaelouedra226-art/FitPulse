package com.example.fitpulse.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitpulse.WorkoutViewModel
import com.example.fitpulse.ui.components.*
import com.example.fitpulse.ui.theme.*

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onBack: () -> Unit) {
    val vm: WorkoutViewModel = viewModel()
    val profile by vm.profile.collectAsState()
    var name by remember(profile) { mutableStateOf(profile?.name ?: "Athlete") }

    Scaffold(containerColor = DarkBg, topBar = {
        TopAppBar(title = { Text("Profil", color = TextPrimary) }, navigationIcon = {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = null, tint = TextPrimary) }
        }, colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBg))
    }) { pad ->
        Column(Modifier.padding(pad).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            GradientHeader(name, profile?.goal ?: "Build muscle")
            GlassCard(Modifier.fillMaxWidth()) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nom") }, modifier = Modifier.fillMaxWidth())
                    Text("Objectif hebdo : ${profile?.weeklyTarget ?: 4} séances", color = TextSecondary)
                    Text("Total séances : ${profile?.totalWorkouts ?: 0}", color = TextSecondary)
                }
            }
        }
    }
}
