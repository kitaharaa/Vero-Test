package com.kitaharaa.digitalapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kitaharaa.digitalapp.data.local.entity.AuthorizationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AuthorizationDao {
    @Upsert
    fun updateData(authEntity: AuthorizationEntity)

    @Query("SELECT token FROM authorization_table WHERE id=0")
    fun getTokenFlow(): Flow<String>

    @Query("SELECT token FROM authorization_table WHERE id=0")
    fun getToken(): String
}