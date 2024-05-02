package com.kitaharaa.digitalapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kitaharaa.digitalapp.data.local.entity.TaskInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskInfoDao {
    @Upsert
    suspend fun upsertTasks(tasks: List<TaskInfoEntity>)

    @Query("DELETE FROM task_info_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM task_info_table")
    fun getAllTask(): Flow<List<TaskInfoEntity>>
}