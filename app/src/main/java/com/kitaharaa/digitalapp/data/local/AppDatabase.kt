package com.kitaharaa.digitalapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kitaharaa.digitalapp.data.local.converter.DateConverter
import com.kitaharaa.digitalapp.data.local.dao.AuthorizationDao
import com.kitaharaa.digitalapp.data.local.dao.TaskInfoDao
import com.kitaharaa.digitalapp.data.local.entity.AuthorizationEntity
import com.kitaharaa.digitalapp.data.local.entity.TaskInfoEntity

@Database(entities = [AuthorizationEntity::class, TaskInfoEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAuthDao(): AuthorizationDao
    abstract fun getTaskInfoDao(): TaskInfoDao
}