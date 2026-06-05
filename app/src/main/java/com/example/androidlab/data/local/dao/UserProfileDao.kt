package com.example.androidlab.data.local.dao

import androidx.room.*
import com.example.androidlab.data.local.entity.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profiles WHERE id = :id")
    suspend fun getUserProfile(id: String): UserProfile?

    @Query("SELECT * FROM user_profiles LIMIT 1")
    fun getUserProfileFlow(): Flow<UserProfile?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(profile: UserProfile)

    @Delete
    suspend fun delete(profile: UserProfile)

    @Query("SELECT * FROM user_profiles WHERE isSynced = 0")
    suspend fun getUnsyncedProfiles(): List<UserProfile>

    @Query("SELECT * FROM user_profiles")
    suspend fun getAllProfiles(): List<UserProfile>
}
