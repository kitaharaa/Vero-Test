package com.kitaharaa.digitalapp.domain.background.workers

import android.content.Context
import android.util.Log
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

        Log.e(TAG, "doWork: $data")
        return try {
            authSource.saveAuthorizationData(data)

            Log.e(TAG, "doWork: success")
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        private const val TAG = "AuthorizationWorker"
    }
}