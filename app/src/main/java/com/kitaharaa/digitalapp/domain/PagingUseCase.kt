package com.kitaharaa.digitalapp.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.kitaharaa.digitalapp.common.mapper.toTaskInfo
import com.kitaharaa.digitalapp.domain.entity.TaskInfo
import com.kitaharaa.digitalapp.domain.paging.PagingListItemsSource
import com.kitaharaa.digitalapp.presentation.home.entity.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/*
* Supply data on diff filtering & searching
*/
class PagingUseCase @Inject constructor(
    private val pagingListItemsSource: PagingListItemsSource
) {
    fun getPagingItemsFlow(query: String, type: SortType): Flow<PagingData<TaskInfo>> {
        return Pager(
            PagingConfig(
                pageSize = 5,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                when (type) {
                    SortType.Default -> pagingListItemsSource.getDefaultWithQuery(
                        query
                    )

                    SortType.BusinessUnitAsc -> pagingListItemsSource.getSortedByBusinessUnitAsc(
                        query
                    )

                    SortType.BusinessUnitDesc -> pagingListItemsSource.getSortedByBusinessUnitDesc(
                        query
                    )
                }
            }
        ).flow.map {
            it.map { entity ->
                entity.toTaskInfo()
            }
        }
    }
}