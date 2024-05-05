@file:OptIn(FlowPreview::class)

package com.kitaharaa.digitalapp.presentation.home

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.kitaharaa.digitalapp.R
import com.kitaharaa.digitalapp.isCameraPermissionGranted
import com.kitaharaa.digitalapp.presentation.home.composable.FilterDialog
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
            setPrompt("Scanning QR Code")
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
                Toast.makeText(context, "Permission was not granted!", Toast.LENGTH_SHORT)
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
                    contentDescription = "Floating action button."
                )
            }
        }
    ) {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = viewModel::onRefreshData
        ) {
            LazyColumn(
                modifier = Modifier.padding(it)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .padding(
                                vertical = 7.dp,
                                horizontal = 10.dp
                            ),
                        horizontalArrangement = Arrangement.spacedBy(7.dp)
                    ) {

                        OutlinedTextField(
                            modifier = Modifier.weight(8f),
                            value = searchQuery,
                            onValueChange = viewModel::onQueryUpdate,
                            maxLines = 1
                        )

                        IconButton(
                            modifier = Modifier.weight(1f),
                            onClick = { shouldShowFilteringDialog = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_filter_list_24),
                                contentDescription = "Change filtering"
                            )
                        }
                    }
                }

                items(
                    count = pagingData.itemCount,
                    key = pagingData.itemKey { taskInfo ->
                        taskInfo.id
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