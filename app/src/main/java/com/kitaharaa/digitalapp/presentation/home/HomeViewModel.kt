package com.kitaharaa.digitalapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.kitaharaa.digitalapp.common.mapper.toTaskInfo
import com.kitaharaa.digitalapp.domain.paging.PagingListItemsSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pagingListItemsSource: PagingListItemsSource
) : ViewModel() {
    private val _searchQuery =
        MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _sotType = MutableStateFlow(SortType.Default)
    val sortType = _sotType.asStateFlow()

    //todo check sorting
    val mList = _searchQuery.debounce(200).combine(_sotType) { query, type ->
        Pager(
            PagingConfig(
                pageSize = 5,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                if (_searchQuery.value.isNotBlank()) _sotType.value = SortType.Default

                when (type) {
                    SortType.Default -> if (_searchQuery.value.isNotBlank()) pagingListItemsSource.getDefaultWithQuery(
                        _searchQuery.value
                    ) else pagingListItemsSource.getDefault()

                    SortType.BusinessUnitAsc -> pagingListItemsSource.getSortedByBusinessUnitAsc()
                    SortType.BusinessUnitDesc -> pagingListItemsSource.getSortedByBusinessUnitDesc()
                }
            }
        ).flow.map {
            it.map { entity ->
                entity.toTaskInfo()
            }
        }
    }

    fun onQueryUpdate(query: String) {
        _searchQuery.value = query
    }

    fun updateSortType(type: SortType) {
        _sotType.value = type
    }
}

enum class SortType {
    Default,
    BusinessUnitAsc,
    BusinessUnitDesc;
}