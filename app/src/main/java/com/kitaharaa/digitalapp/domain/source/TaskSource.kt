package com.kitaharaa.digitalapp.domain.source

import android.util.Log
import androidx.room.withTransaction
import com.kitaharaa.digitalapp.common.InvalidRemoteData
import com.kitaharaa.digitalapp.common.mapper.toTaskEntity
import com.kitaharaa.digitalapp.data.local.AppDatabase
import com.kitaharaa.digitalapp.data.local.dao.TaskInfoDao
import com.kitaharaa.digitalapp.data.remote.TasksDataSource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

/* Class for refreshing local saved data */
class TaskSource @Inject constructor(
    private val database: AppDatabase,
    private val tasksDao: TaskInfoDao,
    private val tasksDataSource: TasksDataSource,
) {

    //call when you need swipe to refresh or every 60 minutes in worker
    @Throws(InvalidRemoteData::class)
    suspend fun refreshData(token: String) = withContext(IO) {
        val data = try {
            tasksDataSource.getTaskList(token)
        } catch (e: Exception) {
            Log.e("TaskDataUseCase", "refreshData: $e")

            throw InvalidRemoteData()
        }

        database.withTransaction {
            tasksDao.deleteAll()
            tasksDao.upsertTasks(
                data.map {
                    it.toTaskEntity()
                }
            )
        }
    }
}