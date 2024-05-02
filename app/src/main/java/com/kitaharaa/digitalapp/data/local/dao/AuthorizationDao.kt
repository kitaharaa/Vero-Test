package com.kitaharaa.digitalapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kitaharaa.digitalapp.data.local.entity.AuthorizationEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface AuthorizationDao {
    @Upsert
    suspend fun updateData(authEntity: AuthorizationEntity)

    @Query("SELECT token FROM authorization_table WHERE id=1")
    fun getTokenFlow(): Flow<String>

    @Query("SELECT token FROM authorization_table WHERE id=1")
    suspend fun getToken(): String

    @Query("SELECT expire_date FROM authorization_table WHERE id=1")
    suspend fun getTokenExpireDate(): Date?

    @Query("SELECT valid_in FROM authorization_table WHERE id=1")
    suspend fun getValidIn(): Int
}