package com.kitaharaa.digitalapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity("authorization_table")
data class AuthorizationEntity(
    @PrimaryKey(false)
    val id: Int = 1,
    val token: String,
    @ColumnInfo("expire_date")
    val updateDate: Date?,
    @ColumnInfo("valid_in")
    val validIn: Int
)