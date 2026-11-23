package com.example.fitpulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fitpulse.ui.screens.*
import com.example.fitpulse.ui.theme.*

// ─── Bottom Tab ───────────────────────────────────────────────────────────────
enum class Tab { DASHBOARD, WORKOUTS, PROGRESS, PROFILE }

@Composable
fun BottomBar(current: Tab, onTab: (Tab) -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(BgMid)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0x1FFFFFFF))
                .padding(8.dp)
        ) {
            // Accueil
            Box(
                Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (current == Tab.DASHBOARD) NeonGreen.copy(alpha = 0.18f) else Color.Transparent)
                    .padding(vertical = 10.dp, horizontal = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Filled.Home,
                        null,
                        tint = if (current == Tab.DASHBOARD) NeonGreen else TextLo,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Accueil",
                        color = if (current == Tab.DASHBOARD) NeonGreen else TextLo,
                        fontSize = 10.sp,
                        fontWeight = if (current == Tab.DASHBOARD) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
            // Séances
            Box(
                Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (current == Tab.WORKOUTS) NeonGreen.copy(alpha = 0.18f) else Color.Transparent)
                    .padding(vertical = 10.dp, horizontal = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Filled.FitnessCenter,
                        null,
                        tint = if (current == Tab.WORKOUTS) NeonGreen else TextLo,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Séances",
                        color = if (current == Tab.WORKOUTS) NeonGreen else TextLo,
                        fontSize = 10.sp,
                        fontWeight = if (current == Tab.WORKOUTS) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
            // Progrès
            Box(
                Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (current == Tab.PROGRESS) NeonGreen.copy(alpha = 0.18f) else Color.Transparent)
                    .padding(vertical = 10.dp, horizontal = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Filled.TrendingUp,
                        null,
                        tint = if (current == Tab.PROGRESS) NeonGreen else TextLo,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Progrès",
                        color = if (current == Tab.PROGRESS) NeonGreen else TextLo,
                        fontSize = 10.sp,
                        fontWeight = if (current == Tab.PROGRESS) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
            // Profil
            Box(
                Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (current == Tab.PROFILE) NeonGreen.copy(alpha = 0.18f) else Color.Transparent)
                    .padding(vertical = 10.dp, horizontal = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Filled.Person,
                        null,
                        tint = if (current == Tab.PROFILE) NeonGreen else TextLo,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Profil",
                        color = if (current == Tab.PROFILE) NeonGreen else TextLo,
                        fontSize = 10.sp,
                        fontWeight = if (current == Tab.PROFILE) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }
    }
}

// ─── Main Activity ────────────────────────────────────────────────────────────
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitPulseTheme {
                val nav = rememberNavController()
                var tab by remember { mutableStateOf(Tab.DASHBOARD) }

                Surface(Modifier.fillMaxSize()) {
                    Box(Modifier.fillMaxSize()) {
                        NavHost(nav, startDestination = "dashboard", Modifier.fillMaxSize()) {
                            composable("dashboard") {
                                DashboardScreen(
                                    onWorkouts = { nav.navigate("workouts"); tab = Tab.WORKOUTS },
                                    onProgress = { nav.navigate("progress"); tab = Tab.PROGRESS },
                                    onProfile = { nav.navigate("profile"); tab = Tab.PROFILE },
                                    onLibrary = { nav.navigate("library") }
                                )
                            }
                            composable("workouts") {
                                WorkoutsScreen({ nav.popBackStack() }) { id -> nav.navigate("active/$id") }
                            }
                            composable("active/{id}", arguments = listOf(navArgument("id") { type = NavType.LongType })) { bs ->
                                val id = bs.arguments?.getLong("id") ?: 0L
                                ActiveWorkoutScreen({ nav.popBackStack() }, id)
                            }
                            composable("library") {
                                LibraryScreen({ nav.popBackStack() }) { id -> nav.navigate("exercise/$id") }
                            }
                            composable("exercise/{id}", arguments = listOf(navArgument("id") { type = NavType.LongType })) { bs ->
                                val id = bs.arguments?.getLong("id") ?: 0L
                                ExerciseDetailScreen({ nav.popBackStack() }, id)
                            }
                            composable("progress") {
                                ProgressScreen({ nav.popBackStack() })
                            }
                            composable("profile") {
                                ProfileScreen({ nav.popBackStack() }, { nav.navigate("settings") })
                            }
                            composable("settings") {
                                SettingsScreen({ nav.popBackStack() })
                            }
                        }

                        Box(Modifier.align(Alignment.BottomCenter)) {
                            BottomBar(current = tab) { newTab ->
                                when (newTab) {
                                    Tab.DASHBOARD -> nav.navigate("dashboard") { popUpTo(0) { inclusive = false } }
                                    Tab.WORKOUTS -> nav.navigate("workouts")
                                    Tab.PROGRESS -> nav.navigate("progress")
                                    Tab.PROFILE -> nav.navigate("profile")
                                }
                                tab = newTab
                            }
                        }
                    }
                }
            }
        }
    }
}
