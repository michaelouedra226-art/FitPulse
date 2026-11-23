package com.example.fitpulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
    val items = listOf(
        Tab.DASHBOARD to (Icons.Default.Home to "Accueil"),
        Tab.WORKOUTS to (Icons.Default.FitnessCenter to "Séances"),
        Tab.PROGRESS to (Icons.Default.TrendingUp to "Progrès"),
        Tab.PROFILE to (Icons.Default.Person to "Profil")
    )
    Box(
        Modifier
            .fillMaxWidth()
            .background(BgMid)
            .padding(bottom = 8.dp, top = 8.dp, start = 12.dp, end = 12.dp)
    ) {
        Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(Color(0x1FFFFFFF)).padding(8.dp)) {
            items.forEach { (tab, (icon, label)) ->
                val active = tab == current
                Box(
                    Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(if (active) NeonGreen.copy(alpha = 0.18f) else Color.Transparent)
                        .then(
                            if (active) Modifier.border(1.dp, NeonGreen.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
                            else Modifier
                        )
                        .padding(vertical = 10.dp, horizontal = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            icon,
                            null,
                            tint = if (active) NeonGreen else TextLo,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            label,
                            color = if (active) NeonGreen else TextLo,
                            fontSize = 10.sp,
                            fontWeight = if (active) FontWeight.Bold else FontWeight.Medium
                        )
                    }
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
                        // Content
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

                        // Bottom Bar
                        Box(Modifier.align(Alignment.BottomCenter)) {
                            BottomBar(current = tab) { newTab ->
                                when (newTab) {
                                    Tab.DASHBOARD -> { nav.navigate("dashboard") { popUpTo(0) { inclusive = false } } }
                                    Tab.WORKOUTS -> { nav.navigate("workouts") }
                                    Tab.PROGRESS -> { nav.navigate("progress") }
                                    Tab.PROFILE -> { nav.navigate("profile") }
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
