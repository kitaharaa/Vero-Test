package com.kitaharaa.digitalapp.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()

    Text(
        modifier = Modifier.fillMaxSize(),
        text = "Hi, Barbie",
        textAlign = TextAlign.Center
    )
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}