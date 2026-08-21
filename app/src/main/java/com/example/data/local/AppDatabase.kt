package com.example.data.local

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {
    @Query("SELECT * FROM jobs_table ORDER BY timestamp DESC")
    fun getAllJobsFlow(): Flow<List<JobEntity>>

    @Query("SELECT * FROM jobs_table WHERE id = :jobId LIMIT 1")
    suspend fun getJobById(jobId: String): JobEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJobs(jobs: List<JobEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: JobEntity)

    @Query("DELETE FROM jobs_table WHERE id = :jobId")
    suspend fun deleteJob(jobId: String)

    @Query("DELETE FROM jobs_table")
    suspend fun clearAll()
}

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profiles_table ORDER BY timestamp DESC")
    fun getAllProfilesFlow(): Flow<List<ProfileEntity>>

    @Query("SELECT * FROM profiles_table WHERE id = :profileId LIMIT 1")
    suspend fun getProfileById(profileId: String): ProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfiles(profiles: List<ProfileEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: ProfileEntity)

    @Query("DELETE FROM profiles_table WHERE id = :profileId")
    suspend fun deleteProfile(profileId: String)

    @Query("DELETE FROM profiles_table")
    suspend fun clearAll()
}

@Database(entities = [JobEntity::class, ProfileEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jobDao(): JobDao
    abstract fun profileDao(): ProfileDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "clipzone_ajira_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
