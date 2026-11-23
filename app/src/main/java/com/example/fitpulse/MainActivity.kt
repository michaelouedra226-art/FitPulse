package com.example.fitpulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fitpulse.ui.screens.*
import com.example.fitpulse.ui.theme.FitPulseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitPulseTheme {
                val nav = rememberNavController()
                Surface(Modifier) {
                    NavHost(nav, startDestination = "dashboard") {
                        composable("dashboard") { DashboardScreen({ nav.navigate("workouts") }, { nav.navigate("progress") }, { nav.navigate("profile") }, { nav.navigate("library") }) }
                        composable("onboarding") { OnboardingScreen({ nav.popBackStack() }) }
                        composable("workouts") { WorkoutsScreen({ nav.popBackStack() }, { id -> nav.navigate("active/$id") }) }
                        composable("active/{id}", arguments = listOf(navArgument("id") { type = NavType.LongType })) { bs ->
                            val id = bs.arguments?.getLong("id") ?: 0L
                            ActiveWorkoutScreen({ nav.popBackStack() }, id)
                        }
                        composable("library") { LibraryScreen({ nav.popBackStack() }, { id -> nav.navigate("exercise/$id") }) }
                        composable("exercise/{id}", arguments = listOf(navArgument("id") { type = NavType.LongType })) { bs ->
                            val id = bs.arguments?.getLong("id") ?: 0L
                            ExerciseDetailScreen({ nav.popBackStack() }, id)
                        }
                        composable("progress") { ProgressScreen({ nav.popBackStack() }) }
                        composable("settings") { SettingsScreen({ nav.popBackStack() }) }
                    }
                }
            }
        }
    }
}
