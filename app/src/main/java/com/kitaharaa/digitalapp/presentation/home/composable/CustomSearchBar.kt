package com.kitaharaa.digitalapp.presentation.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kitaharaa.digitalapp.R
import kotlin.reflect.KFunction1

@Composable
fun CustomSearchBar(
    searchQuery: String,
    onQueryUpdate: KFunction1<String, Unit>,
    openFilterDialog: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(
                vertical = 7.dp,
                horizontal = 10.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        OutlinedTextField(
            modifier = Modifier.weight(8f),
            value = searchQuery,
            onValueChange = onQueryUpdate,
            maxLines = 1,
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge,
            label = {
                Text(text = stringResource(R.string.search_for_task))
            },
            placeholder = {
                Text(text = stringResource(R.string.task_placeholder))
            }
        )

        IconButton(
            modifier = Modifier.weight(1f),
            onClick = openFilterDialog) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_filter_list_24),
                contentDescription = stringResource(R.string.change_filtering)
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun CustomSearchBarPreview () {
    fun updateFunction(onUpdate: String) {}

    CustomSearchBar("Interesting task", ::updateFunction) {}
}