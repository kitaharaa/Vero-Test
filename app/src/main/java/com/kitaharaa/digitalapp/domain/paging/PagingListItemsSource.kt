package com.kitaharaa.digitalapp.domain.paging

import com.kitaharaa.digitalapp.data.local.dao.TaskInfoDao
import javax.inject.Inject

class PagingListItemsSource @Inject constructor(
    private val tasksDao: TaskInfoDao
) {

    fun getSortedByBusinessUnitAsc(query: String) = tasksDao.getAllSortedByBusinessUnitAsc(query)
    fun getSortedByBusinessUnitDesc(query: String) = tasksDao.getAllSortedByBusinessUnitDesc(query)
    fun getDefaultWithQuery(query: String) = tasksDao.getAllTaskSortedByDefaultWithQuery(query)
}