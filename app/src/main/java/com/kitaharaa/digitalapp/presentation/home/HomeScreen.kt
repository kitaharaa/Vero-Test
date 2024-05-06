@file:OptIn(FlowPreview::class)

package com.kitaharaa.digitalapp.presentation.home

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.kitaharaa.digitalapp.R
import com.kitaharaa.digitalapp.common.theme.SwipeRefreshWorkDistance
import com.kitaharaa.digitalapp.common.theme.TaskOuterHorizontalPadding
import com.kitaharaa.digitalapp.common.theme.TaskOuterVerticalPadding
import com.kitaharaa.digitalapp.isCameraPermissionGranted
import com.kitaharaa.digitalapp.presentation.home.composable.CustomSearchBar
import com.kitaharaa.digitalapp.presentation.home.composable.FilterDialog
import com.kitaharaa.digitalapp.presentation.home.composable.LoadingProgressBar
import com.kitaharaa.digitalapp.presentation.home.composable.TaskInfoCard
import com.kitaharaa.digitalapp.presentation.qr.CaptureActivity
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()
    val context = LocalContext.current

    val searchQuery by viewModel.searchQuery.collectAsState()
    val sortType by viewModel.sortType.collectAsState()
    val pagingDataFlow by viewModel.mList.collectAsState(initial = flowOf())

    val isLoading by viewModel.isRefreshing.collectAsState()

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    val pagingData = pagingDataFlow.collectAsLazyPagingItems()

    var shouldShowFilteringDialog by remember {
        mutableStateOf(false)
    }

    val options by lazy {
        ScanOptions().apply {
            setPrompt(context.getString(R.string.scanning_qr_code))
            setBeepEnabled(true)
            setOrientationLocked(true)
            captureActivity = CaptureActivity::class.java
        }
    }

    val barLauncher = rememberLauncherForActivityResult(ScanContract()) { result ->
        result.contents?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()

            viewModel.onQueryUpdate(it)
        }
    }

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                barLauncher.launch(options)
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.permission_was_not_granted), Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    context.isCameraPermissionGranted(
                        onSuccess = {
                            barLauncher.launch(options)
                        }, onFailure = {
                            requestPermissionLauncher.launch(it)
                        }
                    )
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_qr_code_scanner_24),
                    contentDescription = stringResource(R.string.floating_action_button)
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            CustomSearchBar(searchQuery, viewModel::onQueryUpdate) {
                shouldShowFilteringDialog = true
            }
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = viewModel::onRefreshData,
                refreshTriggerDistance = SwipeRefreshWorkDistance
            ) {
                when {
                    pagingData.loadState.refresh is LoadState.Loading -> {
                        //first loading
                        LoadingProgressBar(
                            text = stringResource(R.string.initial_loading),
                            showProgressBar = true
                        )
                    }

                    pagingData.itemCount == 0 -> {
                        LoadingProgressBar(
                            text = stringResource(R.string.there_is_no_data),
                            showProgressBar = false
                        )
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier.padding(it),
                            contentPadding = PaddingValues(
                                vertical = TaskOuterVerticalPadding,
                                horizontal = TaskOuterHorizontalPadding
                            ),
                            verticalArrangement = Arrangement.spacedBy(TaskOuterVerticalPadding)
                        ) {
                            items(
                                count = pagingData.itemCount,
                                key = pagingData.itemKey { taskInfo ->
                                    taskInfo.id
                                },
                                contentType = pagingData.itemContentType { context.getString(R.string.tasks) }
                            ) { index ->

                                pagingData[index]?.let { info ->
                                    TaskInfoCard(info)
                                }
                            }

                            item {
                                if (pagingData.loadState.append is LoadState.Loading) {
                                    LoadingProgressBar(
                                        text = stringResource(R.string.loading),
                                        showProgressBar = true
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    AnimatedVisibility(visible = shouldShowFilteringDialog) {
        FilterDialog(currentSortType = sortType,
            onDismiss = {
                shouldShowFilteringDialog = false
            },
            changeSortType = {
                viewModel.changeSortType(it) {
                    shouldShowFilteringDialog = false
                }
            }
        )
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}