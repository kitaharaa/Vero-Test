package com.kitaharaa.digitalapp.presentation.home.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.kitaharaa.digitalapp.R
import com.kitaharaa.digitalapp.common.sort_types.SortType

/*
* Dialog with filtering types
*/
@Composable
fun FilterDialog(
    currentSortType: SortType,
    onDismiss: () -> Unit,
    changeSortType: (SortType) -> Unit
) {
    var selectedSortType by remember {
        mutableStateOf(currentSortType)
    }

    Dialog(properties = DialogProperties(), onDismissRequest = onDismiss) {
        Card(modifier = Modifier.padding(13.dp)) {
            Text(
                modifier = Modifier.padding(15.dp),
                text = stringResource(R.string.list_filtering),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            )

            SortType.entries.forEach { sortType ->
                Row(
                    modifier = Modifier
                        .clickable(MutableInteractionSource(), rememberRipple()) {
                            selectedSortType = sortType
                        },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(
                        modifier = Modifier.weight(2f),
                        selected = selectedSortType == sortType,
                        onClick = { selectedSortType = sortType })
                    Spacer(modifier = Modifier.width(15.dp))

                    Text(
                        modifier = Modifier.weight(6f),
                        text = sortType.typeName,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End),
            ) {
                Button(onClick = onDismiss) {
                    Text(
                        text = stringResource(R.string.cancel),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Button(
                    onClick = {
                        changeSortType(selectedSortType)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.sort),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun FilterDialogPreview() {
    FilterDialog(SortType.BusinessUnitAsc, {}) {}
}