package com.kitaharaa.digitalapp.domain

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.kitaharaa.digitalapp.domain.background.workers.AuthorizationWorker
import com.kitaharaa.digitalapp.domain.background.workers.TasksUpdateWorker
import com.kitaharaa.digitalapp.domain.source.AuthorizationSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.time.Duration
import javax.inject.Inject

class WorkerUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authSource: AuthorizationSource,
) {
    operator fun invoke() {
        val workManager = WorkManager.getInstance(context)

        CoroutineScope(Main).launch {
            val tokenUpdateTime = authSource.countTokenExpireTime()

            val authWork = PeriodicWorkRequestBuilder<AuthorizationWorker>(
                repeatInterval = Duration.ofSeconds(authSource.getTokenExpireTimeInSeconds().toLong())
            )
                .setInitialDelay(Duration.ofSeconds(tokenUpdateTime))
                .setBackoffCriteria(BackoffPolicy.LINEAR, duration = Duration.ofSeconds(10L))
                .setConstraints(
                    Constraints(
                        requiredNetworkType = NetworkType.CONNECTED
                    )
                )
                .build()

            workManager.enqueue(authWork)

               val taskUpdatingWork = PeriodicWorkRequestBuilder<TasksUpdateWorker>(
                Duration.ofHours(60)
            )
                .setBackoffCriteria(BackoffPolicy.LINEAR, duration = Duration.ofSeconds(5L))
                .setConstraints(
                    Constraints(
                        requiredNetworkType = NetworkType.CONNECTED
                    )
                )
                .build()
            workManager.enqueue(taskUpdatingWork)

        }
    }
}