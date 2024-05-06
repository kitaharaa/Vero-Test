package com.kitaharaa.digitalapp.di

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.kitaharaa.digitalapp.data.local.dao.AuthorizationDao
import com.kitaharaa.digitalapp.domain.WorkerUseCase
import com.kitaharaa.digitalapp.domain.background.workers.AuthorizationWorker
import com.kitaharaa.digitalapp.domain.background.workers.TasksUpdateWorker
import com.kitaharaa.digitalapp.domain.source.AuthorizationSource
import com.kitaharaa.digitalapp.domain.source.TaskSource
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication: Application(), Configuration.Provider {

    @Inject
    lateinit var authWorkerFactory: AuthWorkerFactory

    @Inject
    lateinit var workerUseCase: WorkerUseCase

    override fun onCreate() {
        super.onCreate()

        workerUseCase()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(authWorkerFactory)
            .build()
    }
}

class AuthWorkerFactory @Inject constructor(
    private val dao: AuthorizationSource,
    private val taskSource: TaskSource,
    private val authorizationDao: AuthorizationDao,
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {

        return if (workerClassName.contains(AuthorizationWorker::class.simpleName.toString())) {
            AuthorizationWorker(
                appContext, workerParameters, dao
            )
        } else {
            TasksUpdateWorker(
                appContext,
                workerParameters,
                taskSource,
                authorizationDao
            )
        }
    }
}