package com.example.androidlab.data.repository

import com.example.androidlab.data.local.entity.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserProfileFlow(): Flow<UserProfile?>
    suspend fun getUserProfile(id: String): UserProfile?
    suspend fun saveUserProfile(profile: UserProfile)
    
    // Future expansion: Import/Export
    suspend fun exportUserData(): String
    suspend fun importUserData(jsonData: String)
    
    // Future expansion: Cloud Sync
    suspend fun syncWithCloud()
}
