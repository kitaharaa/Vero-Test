package com.kitaharaa.digitalapp.domain.source

import android.util.Log
import com.kitaharaa.digitalapp.common.AuthorizationParameterException
import com.kitaharaa.digitalapp.common.mapper.toAuthEntity
import com.kitaharaa.digitalapp.data.local.dao.AuthorizationDao
import com.kitaharaa.digitalapp.data.remote.AuthorizationDataSource
import com.kitaharaa.digitalapp.data.remote.entity.auth.AuthorizationResponse
import java.util.Date
import javax.inject.Inject

/*
* Handles Authorization manipulations like:
*  counting next update time,
*  request for data,
*  saving them locally
*/
class AuthorizationSource @Inject constructor(
    private val authorizationDao: AuthorizationDao,
    private val authorizationDataSource: AuthorizationDataSource
) {

    suspend fun countTokenExpireTime(): Long {
        return try {
            val tokenExpireDate = authorizationDao.getTokenExpireDate()
                ?: throw AuthorizationParameterException("Local parameter is null")

            val currentTime = Date()

            val millisDiff = tokenExpireDate.time - currentTime.time
            if (millisDiff > 0) millisDiff / 1000 else 0L
        } catch (e: Exception) {
            Log.e(TAG, "countNexUpdateTime: $e")

            0L
        }
    }

    suspend fun getAuthorizationData() = try {
        authorizationDataSource.getAuthData()
    } catch (e: Exception) {
        Log.e(TAG, "getAuthorizationData: $e")
        null
    }

    suspend fun saveAuthorizationData(response: AuthorizationResponse) {
        authorizationDao.updateData(response.toAuthEntity())
    }

    suspend fun getTokenExpireTimeInSeconds(): Int {
        return try {
            authorizationDao.getValidIn()
        } catch (e: Exception) {
            Log.e(TAG, "getTokenExpireTimeInSeconds: $e" )
        }
    }

    companion object {
        private const val TAG = "AuthorizationUseCase"
    }
}