package com.kitaharaa.digitalapp.domain.background.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kitaharaa.digitalapp.domain.source.AuthorizationSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/*`
* Request Auth data and save locally
*/
@HiltWorker
class AuthorizationWorker @AssistedInject constructor(
    @Assisted applicationContext: Context,
    @Assisted params: WorkerParameters,
    @Assisted private val authSource: AuthorizationSource,
) : CoroutineWorker(applicationContext, params) {

    override suspend fun doWork(): Result {
        val data = authSource.getAuthorizationData() ?: return Result.retry()

        return try {
            authSource.saveAuthorizationData(data)

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}