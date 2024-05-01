package com.kitaharaa.digitalapp.domain

import android.util.Log
import androidx.room.withTransaction
import com.kitaharaa.digitalapp.common.InvalidRemoteData
import com.kitaharaa.digitalapp.common.mapper.toTaskEntity
import com.kitaharaa.digitalapp.data.local.AppDatabase
import com.kitaharaa.digitalapp.data.remote.TasksDataSource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

/* Class for refreshing local saved data */
class TaskDataUseCase @Inject constructor(
    private val db: AppDatabase,
    private val tasksDataSource: TasksDataSource
) {

    //call when you need swipe to refresh or every 60 minutes in worker
    @Throws(InvalidRemoteData::class)
    suspend fun refreshData()  = withContext(IO) {
        val token = db.getAuthDao().getToken()

        val data = try {
            tasksDataSource.getTaskList(token)
        } catch (e: Exception) {
            Log.e("TaskDataUseCase", "refreshData: $e")

            throw InvalidRemoteData()
        }

        db.withTransaction {
            db.getTaskInfoDao().deleteAll()
            db.getTaskInfoDao().upsertTasks(
                data.map {
                    it.toTaskEntity()
                }
            )
        }
    }
}