package com.kitaharaa.digitalapp.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.kitaharaa.digitalapp.common.mapper.toTaskInfo
import com.kitaharaa.digitalapp.domain.RefreshDataUseCase
import com.kitaharaa.digitalapp.domain.paging.PagingListItemsSource
import com.kitaharaa.digitalapp.presentation.home.entity.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pagingListItemsSource: PagingListItemsSource,
    private val refreshDataUseCase: RefreshDataUseCase
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _sortType = MutableStateFlow(SortType.Default)
    val sortType = _sortType.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    val mList = _searchQuery.debounce(400).combine(_sortType) { query, type ->
        Pager(
            PagingConfig(
                pageSize = 5,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                if (_searchQuery.value.isNotBlank()) _sortType.value = SortType.Default

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

    fun changeSortType(it: SortType, onEnd: () -> Unit) {
        _sortType.value = it

        onEnd()
    }

    fun onRefreshData() {
        viewModelScope.launch {
            _isRefreshing.value = true

            try {
                refreshDataUseCase.refreshData()
            } catch (e: Exception) {
                Log.e("HomeViewModel", "onRefreshData: e")
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}