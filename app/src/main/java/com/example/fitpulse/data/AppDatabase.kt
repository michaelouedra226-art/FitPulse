package com.example.fitpulse.data

import androidx.room.*

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val category: String,
    val muscleGroup: String,
    val description: String,
    val difficulty: String,
    val caloriesPerMin: Int,
    val durationSec: Int,
    val restSec: Int,
    val animationRes: String,
    val instructions: String
)

@Entity(tableName = "workout_sessions")
data class SessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val workoutName: String,
    val startedAt: Long,
    val endedAt: Long,
    val caloriesBurned: Int,
    val completed: Boolean
)

@Dao
interface FitDao {
    @Query("SELECT * FROM exercises") suspend fun getAllExercises(): List<ExerciseEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertExercises(list: List<ExerciseEntity>)
    @Query("SELECT COUNT(*) FROM exercises") suspend fun countExercises(): Int

    @Insert suspend fun insertSession(s: SessionEntity): Long
    @Query("SELECT * FROM workout_sessions ORDER BY startedAt DESC") suspend fun getAllSessions(): List<SessionEntity>
    @Query("SELECT COUNT(*) FROM workout_sessions WHERE completed=1") suspend fun completedCount(): Int
    @Query("SELECT COALESCE(SUM(caloriesBurned),0) FROM workout_sessions WHERE completed=1") suspend fun totalCalories(): Int
}

@Database(entities = [ExerciseEntity::class, SessionEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): FitDao
}
