package com.kitaharaa.digitalapp.domain

import com.kitaharaa.digitalapp.data.local.dao.AuthorizationDao
import com.kitaharaa.digitalapp.domain.source.TaskSource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RefreshDataUseCase @Inject constructor(
    private val authorizationDao: AuthorizationDao,
    private val refreshData: TaskSource
) {
    suspend fun refreshData() = withContext(IO){
        refreshData.refreshData(authorizationDao.getToken())
    }
}