package com.kitaharaa.digitalapp.domain.paging

import com.kitaharaa.digitalapp.data.local.dao.TaskInfoDao
import javax.inject.Inject

class PagingListItemsSource @Inject constructor(
    private val tasksDao: TaskInfoDao
) {

    fun getSortedByBusinessUnitAsc() = tasksDao.getAllSortedByBusinessUnitAsc()
    fun getSortedByBusinessUnitDesc() = tasksDao.getAllSortedByBusinessUnitDesc()
    fun getDefaultWithQuery(query: String) = tasksDao.getAllTaskSortedByDefaultWithQuery(query)
    fun getDefault() = tasksDao.getAllTaskSortedByDefault()
}