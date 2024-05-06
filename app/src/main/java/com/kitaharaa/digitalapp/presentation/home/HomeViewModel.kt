package com.kitaharaa.digitalapp.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitaharaa.digitalapp.common.sort_types.SortType
import com.kitaharaa.digitalapp.domain.PagingUseCase
import com.kitaharaa.digitalapp.domain.RefreshDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val refreshDataUseCase: RefreshDataUseCase,
    private val pagingUseCase: PagingUseCase
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _sortType = MutableStateFlow(SortType.Default)
    val sortType = _sortType.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    val mList = _searchQuery.debounce(700).combine(_sortType) { query, type ->
        pagingUseCase.getPagingItemsFlow(query, type)
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
                Log.e("HomeViewModel", "onRefreshData: $e")
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}