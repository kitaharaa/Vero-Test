package com.kitaharaa.digitalapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("authorization_table")
data class AuthorizationEntity(
    @PrimaryKey(true)
    val id: Int = 0,
    val token: String,
    @ColumnInfo("update_date")
    val updateDate: Long,
    @ColumnInfo("expires_in")
    val expiresIn: Int
)