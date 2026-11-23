package com.example.fitpulse.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitpulse.ExerciseViewModel
import com.example.fitpulse.ui.components.*
import com.example.fitpulse.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailScreen(onBack: () -> Unit, exerciseId: Long) {
    val vm: ExerciseViewModel = viewModel()
    val ex = vm.getById(exerciseId)

    Scaffold(
        containerColor = BgDeep,
        topBar = {
            TopAppBar(
                title = { Text(ex?.name ?: "Exercice", color = TextHi, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, null, tint = TextMid)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BgDeep)
            )
        }
    ) { pad ->
        if (ex == null) {
            Box(Modifier.padding(pad).fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Exercice introuvable", color = TextMid)
            }
        } else {
            val infinite = rememberInfiniteTransition(label = "anim")
            val pulse by infinite.animateFloat(
                initialValue = 0.92f,
                targetValue = 1.08f,
                animationSpec = infiniteRepeatable(tween(900), RepeatMode.Reverse),
                label = "pulse"
            )

            LazyColumn(
                modifier = Modifier.padding(pad).padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Animated Hero
                item {
                    GlassCard(Modifier.fillMaxWidth().height(180.dp)) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                "▶ ${ex.animationRes}",
                                color = NeonGreen,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier.scale(pulse)
                            )
                        }
                    }
                }

                // Tags
                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Tag(ex.muscleGroup, NeonCyan)
                        Tag(ex.difficulty, NeonPurple)
                        Tag("${ex.durationSec}s", NeonAmber)
                        Tag("${ex.caloriesPerMin} kcal/min", NeonRed)
                    }
                }

                // Description
                item {
                    GlassCard(Modifier.fillMaxWidth()) {
                        Text(ex.description, style = MaterialTheme.typography.bodyLarge, color = TextHi, fontWeight = FontWeight.Normal)
                    }
                }

                // Instructions
                item {
                    SectionHeader("Instructions", accent = true)
                }
                ex.instructions.forEachIndexed { i, step ->
                    item {
                        GlassCard(Modifier.fillMaxWidth()) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                Box(
                                    Modifier
                                        .size(28.dp)
                                        .clip(RoundedCornerShape(50))
                                        .background(NeonGreen.copy(alpha = 0.15f))
                                        .border(1.dp, NeonGreen.copy(alpha = 0.3f), RoundedCornerShape(50)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("${i + 1}", color = NeonGreen, fontWeight = FontWeight.Black, fontSize = 12.sp)
                                }
                                Text(step, color = TextHi, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}
