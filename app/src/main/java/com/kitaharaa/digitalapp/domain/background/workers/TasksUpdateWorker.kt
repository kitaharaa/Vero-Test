package com.kitaharaa.digitalapp.domain.background.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kitaharaa.digitalapp.data.local.dao.AuthorizationDao
import com.kitaharaa.digitalapp.domain.source.TaskSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/*
* Worker for refreshing tasks in local db
*/
@HiltWorker
class TasksUpdateWorker @AssistedInject constructor(
    @Assisted applicationContext: Context,
    @Assisted params: WorkerParameters,
    private val taskSource: TaskSource,
    private val authorizationDao: AuthorizationDao,
) : CoroutineWorker(applicationContext, params) {
    override suspend fun doWork(): Result = try {
        val token = authorizationDao.getToken()

        if (token.isBlank() || token == null.toString()) Result.retry()

        taskSource.refreshData(token)

        Result.success()
    } catch (e: Throwable) {

        Result.retry()
    }
}