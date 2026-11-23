package com.example.fitpulse

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class Exercise(
    val id: Long,
    val name: String,
    val category: String,
    val muscleGroup: String,
    val description: String,
    val difficulty: String = "Débutant",
    val caloriesPerMin: Int = 5,
    val durationSec: Int = 30,
    val restSec: Int = 15,
    val animationRes: String = "",
    val instructions: List<String> = emptyList()
)
data class WorkoutPlan(
    val id: Long,
    val name: String,
    val description: String,
    val category: String,
    val durationMin: Int,
    val difficulty: String,
    val calories: Int,
    val exerciseIds: List<Long> = emptyList()
)
data class WorkoutSession(val id: Long, val workoutId: Long, val workoutName: String, val startTime: Long, val endTime: Long = 0, val caloriesBurned: Int = 0, val completed: Boolean = false)
data class UserProfile(val name: String = "Athlete", val goal: String = "Build muscle", val weeklyTarget: Int = 4, val streak: Int = 0, val totalWorkouts: Int = 0)

class ExerciseViewModel(app: Application) : AndroidViewModel(app) {
    private val _exercises = MutableStateFlow(seedExercises())
    val exercises: StateFlow<List<Exercise>> = _exercises
    val categories: StateFlow<List<String>> = _exercises.map { it.map { e -> e.category }.distinct() }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    fun add(exercise: Exercise) { _exercises.value = _exercises.value + exercise }
    fun getById(id: Long): Exercise? = _exercises.value.firstOrNull { it.id == id }
}

typealias Workout = WorkoutPlan
class WorkoutViewModel(app: Application) : AndroidViewModel(app) {
    private val _workouts = MutableStateFlow(seedWorkouts())
    val workouts: StateFlow<List<Workout>> = _workouts
    private val _sessions = MutableStateFlow<List<WorkoutSession>>(emptyList())
    val sessions: StateFlow<List<WorkoutSession>> = _sessions
    private val _completed = MutableStateFlow(0)
    val completedCount: StateFlow<Int> = _completed
    private val _calories = MutableStateFlow(0)
    val totalCalories: StateFlow<Int> = _calories
    private val _profile = MutableStateFlow(UserProfile())
    val profile: StateFlow<UserProfile?> = _profile
    private val _active = MutableStateFlow<Long?>(null)
    val activeSessionId: StateFlow<Long?> = _active

    fun beginWorkout(workout: Workout) {
        val id = System.currentTimeMillis()
        _sessions.value = _sessions.value + WorkoutSession(id = id, workoutId = workout.id, workoutName = workout.name, startTime = id)
        _active.value = id
    }
    fun finishWorkout(sessionId: Long, calories: Int) {
        _sessions.value = _sessions.value.map { if (it.id == sessionId) it.copy(endTime = System.currentTimeMillis(), caloriesBurned = calories, completed = true) else it }
        _completed.value = _sessions.value.count { it.completed }
        _calories.value = _sessions.value.filter { it.completed }.sumOf { it.caloriesBurned }
        _active.value = null
        _profile.value = _profile.value.copy(totalWorkouts = _profile.value.totalWorkouts + 1, streak = _profile.value.streak + 1)
    }
    fun logSet(sessionId: Long, exerciseName: String, setNumber: Int, reps: Int, weight: Double) {}
    fun addWorkout(w: Workout) { _workouts.value = _workouts.value + w }
    fun deleteWorkout(w: Workout) { _workouts.value = _workouts.value.filter { it.id != w.id } }
}

private fun seedExercises() = listOf(
    Exercise(1,"Pompes classiques","Force","Poitrine","Pompe fondamentale au poids du corps","Débutant",7,30,15,"pushup",listOf("Mains sous les épaules","Corps gainé","Descendre lentement","Pousser fort")),
    Exercise(2,"Pompes diamant","Force","Poitrine","Pompes serrées pour triceps","Intermédiaire",8,30,15,"diamond",listOf("Mains jointes en diamant","Coudes serrés","Descendre contrôlé","Pousser")),
    Exercise(3,"Pompes déclinées","Force","Poitrine","Pieds surélevés","Intermédiaire",9,30,15,"decline",listOf("Pieds sur chaise","Mains au sol","Corps droit","Pousser")),
    Exercise(4,"Pompes inclinées","Force","Poitrine","Mains surélevées","Débutant",6,30,15,"incline",listOf("Mains sur chaise","Corps droit","Descendre","Pousser")),
    Exercise(5,"Pompes archer","Force","Poitrine","Pompe unilatérale avancée","Avancé",10,30,15,"archer",listOf("Bras écartés","Un bras tendu","Descendre côté actif","Alterner")),
    Exercise(6,"Tirage australien","Force","Dos","Tirage sous une barre","Intermédiaire",8,30,15,"row",listOf("Corps sous la barre","Tirer la poitrine","Serrer les omoplates","Contrôler")),
    Exercise(7,"Superman","Force","Dos","Renforcement lombaires","Débutant",5,30,15,"superman",listOf("Sur le ventre","Bras et jambes tendus","Soulever","Tenir 2s")),
    Exercise(8,"Pike push-up","Force","Épaules","Pompe en V","Intermédiaire",8,30,15,"pike",listOf("Hanches en l'air","Forme en V","Descendre la tête","Pousser")),
    Exercise(9,"Dips sur chaise","Force","Triceps","Triceps au poids du corps","Intermédiaire",6,30,15,"dip",listOf("Mains sur une chaise","Jambes tendues","Descendre","Pousser")),
    Exercise(10,"Squats","Force","Jambes","Mouvement fondamental","Débutant",8,30,15,"squat",listOf("Pieds largeur épaules","Dos droit","Descendre à 90°","Remonter")),
    Exercise(11,"Fentes avant","Force","Jambes","Renforcement unilatéral","Débutant",7,30,15,"lunge",listOf("Un pas en avant","Genou arrière vers le sol","Dos droit","Alterner")),
    Exercise(12,"Fentes bulgares","Force","Jambes","Fente surélevée intense","Avancé",10,30,15,"bulgarian",listOf("Pied arrière sur chaise","Descendre","Genou avant 90°","Pousser")),
    Exercise(13,"Squats sautés","Plyo","Jambes","Explosivité","Intermédiaire",11,30,15,"jumpsquat",listOf("Squat puis saut","Atterrissage doux","Enchaîner","Respirer")),
    Exercise(14,"Pont fessier","Force","Fessiers","Renforcement fessiers","Débutant",5,30,15,"glutebridge",listOf("Sur le dos","Pieds au sol","Soulever les hanches","Contracter")),
    Exercise(15,"Wall sit","Force","Jambes","Gainage statique","Débutant",6,40,20,"wallsit",listOf("Dos contre le mur","Genoux 90°","Tenir","Respirer")),
    Exercise(16,"Calf raises","Force","Mollets","Mollets au poids du corps","Débutant",4,30,15,"calf",listOf("Sur la pointe des pieds","Monter haut","Descendre lentement","Répéter")),
    Exercise(17,"Planche","Core","Abdos","Gainage isométrique","Débutant",4,30,15,"plank",listOf("Avant-bras au sol","Corps aligné","Respirer","Tenir")),
    Exercise(18,"Crunchs","Core","Abdos","Travail des abdos","Débutant",5,30,15,"crunch",listOf("Sur le dos","Mains derrière la tête","Remonter les épaules","Contrôler")),
    Exercise(19,"Mountain climber","Cardio","Core","Cardio rapide pour les abdos","Intermédiaire",10,30,15,"mountain",listOf("Position planche","Genoux vers la poitrine","Rythme soutenu","Respirer")),
    Exercise(20,"Leg raises","Core","Abdos","Bas des abdos","Intermédiaire",6,30,15,"legraise",listOf("Sur le dos","Jambes tendues","Monter à 90°","Descendre lentement")),
    Exercise(21,"Bicycle crunch","Core","Obliques","Obliques dynamiques","Intermédiaire",7,30,15,"bicycle",listOf("Sur le dos","Coude vers genou opposé","Alterner","Contrôler")),
    Exercise(22,"Gainage latéral","Core","Obliques","Renforcement obliques","Intermédiaire",5,30,15,"sideplank",listOf("Sur le côté","Appui avant-bras","Corps aligné","Tenir")),
    Exercise(23,"Hollow body hold","Core","Abdos","Gainage avancé","Avancé",6,30,15,"hollow",listOf("Sur le dos","Bras et jambes tendus","Décoller du sol","Tenir")),
    Exercise(24,"Burpees","HIIT","Full body","Mouvement haute intensité","Avancé",12,30,15,"burpee",listOf("Squat puis planche","Pompe","Saut vertical","Enchaîner")),
    Exercise(25,"Jumping jacks","Cardio","Full body","Échauffement cardio","Débutant",9,30,15,"jack",listOf("Pieds joints","Bras le long du corps","Sauter en écartant","Enchaîner")),
    Exercise(26,"High knees","Cardio","Jambes","Cardio jambes","Débutant",10,30,15,"highknees",listOf("Sur place","Genoux hauts","Bras actifs","Rythme soutenu")),
    Exercise(27,"Skater jumps","Plyo","Jambes","Latéral explosif","Intermédiaire",11,30,15,"skater",listOf("Saut latéral","Atterrissage sur un pied","Bras opposé","Enchaîner")),
    Exercise(28,"Shadow boxing","Cardio","Full body","Cardio sans équipement","Débutant",9,60,15,"boxing",listOf("Position de garde","Crochets et directs","Pieds actifs","Respirer")),
    Exercise(29,"Étirement quadriceps","Étirement","Jambes","Récupération","Débutant",2,30,0,"quadstretch",listOf("Debout","Talon vers la fesse","Genou vers le bas","Tenir 20s")),
    Exercise(30,"Étirement ischio","Étirement","Jambes","Souplesse postérieure","Débutant",2,30,0,"hamstretch",listOf("Jambe tendue","Se pencher en avant","Dos droit","Tenir 20s")),
    Exercise(31,"Étirement poitrine","Étirement","Poitrine","Ouverture thoracique","Débutant",2,30,0,"cheststretch",listOf("Bras contre un mur","Tourner le corps","Sentir l'étirement","Tenir 20s")),
    Exercise(32,"Child pose","Étirement","Dos","Relaxation","Débutant",2,30,0,"childpose",listOf("À genoux","Fessiers sur les talons","Bras tendus devant","Respirer"))
)

private fun seedWorkouts() = listOf(
    Workout(1,"Full Body Burn","Complete full-body HIIT session","HIIT",30,"Intermediate",320),
    Workout(2,"Upper Body Power","Chest, back and arms","Strength",45,"Intermediate",280),
    Workout(3,"Core Crusher","Ab and core focused","Core",20,"Beginner",150),
    Workout(4,"Leg Day","Lower body strength","Strength",40,"Advanced",300)
)
