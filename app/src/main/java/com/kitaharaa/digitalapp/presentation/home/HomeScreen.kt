package com.kitaharaa.digitalapp.presentation.home

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.kitaharaa.digitalapp.presentation.home.composable.TaskInfoCard
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()

    val searchQuery by viewModel.searchQuery.collectAsState()
//    val pagingDataFlow = remember { viewModel.list.value }
    LaunchedEffect(searchQuery) {
        Log.e("SQ", "HomeScreen: sq = $searchQuery")
    }
    val pagingDataFlow by viewModel.mList.collectAsState(initial = flowOf())
    val pagingData = pagingDataFlow.collectAsLazyPagingItems()
    Scaffold {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = "Hi, Barbie",
                    textAlign = TextAlign.Center
                )
            }
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = viewModel::onQueryUpdate
                )
            }

            items(
                count = pagingData.itemCount,
                key = pagingData.itemKey {
                    it.id
                },
                contentType = pagingData.itemContentType { "Tasks" }
            ) { index ->

                pagingData[index]?.let { info ->
                    TaskInfoCard(info)
                }
            }
        }
    }
}


@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}