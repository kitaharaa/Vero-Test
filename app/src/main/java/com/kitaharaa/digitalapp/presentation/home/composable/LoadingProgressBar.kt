package com.kitaharaa.digitalapp.presentation.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kitaharaa.digitalapp.R
import com.kitaharaa.digitalapp.common.theme.LoadingIconSize

@Composable
fun LoadingProgressBar(
    modifier: Modifier = Modifier,
    text: String = "",
    showProgressBar: Boolean = false,
    drawable: ImageVector = ImageVector.vectorResource(id = R.drawable.empty_data)
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (showProgressBar) {
            CircularProgressIndicator()
        } else {
            Icon(
                modifier = Modifier.size(LoadingIconSize),
                imageVector = drawable,
                contentDescription = stringResource(R.string.error_icon)
            )
        }

        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = text,
            style = MaterialTheme
                .typography
                .bodyLarge
                .copy(fontWeight = FontWeight.Bold)
        )
    }
}