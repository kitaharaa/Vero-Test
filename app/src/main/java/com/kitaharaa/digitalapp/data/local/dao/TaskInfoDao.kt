package com.kitaharaa.digitalapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.kitaharaa.digitalapp.data.local.entity.TaskInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskInfoDao {
    @Upsert
    fun upsertTasks(tasks: List<TaskInfoEntity>)
    @Delete
    fun deleteAll()

    @Query("SELECT * FROM task_info_table")
    fun getAllTask(): Flow<List<TaskInfoEntity>>

    @Query("Select * From task_info_table")
    fun getMoviesPagingSource(): PagingSource<Int, TaskInfoEntity>
}