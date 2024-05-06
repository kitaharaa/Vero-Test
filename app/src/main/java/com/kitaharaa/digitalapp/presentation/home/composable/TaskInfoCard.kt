package com.kitaharaa.digitalapp.presentation.home.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kitaharaa.digitalapp.R
import com.kitaharaa.digitalapp.common.theme.MinTextHeight
import com.kitaharaa.digitalapp.common.theme.TaskCardContentVerticalHorizontal
import com.kitaharaa.digitalapp.common.theme.TaskCardVerticalPadding
import com.kitaharaa.digitalapp.domain.entity.TaskInfo

@Composable
fun TaskInfoCard(info: TaskInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = info.color)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = TaskCardContentVerticalHorizontal,
                    vertical = TaskCardVerticalPadding
                ),
        ) {
            val textColor = remember { if (info.color.luminance() > 0.5) Black else White }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                style = MaterialTheme.typography.titleMedium.copy(color = textColor),
                text = info.title
            )

            HorizontalDivider(
                Modifier.padding(
                    vertical = TaskCardVerticalPadding
                )
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = TaskCardVerticalPadding),
                style = MaterialTheme.typography.titleMedium.copy(color = textColor),
                text = stringResource(R.string.task, info.task)
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = MinTextHeight)
                    .padding(horizontal = TaskCardContentVerticalHorizontal),
                style = MaterialTheme.typography.titleMedium.copy(textColor),
                text = info.description
            )
        }
    }
}

@Composable
@Preview
fun TaskInfoCardPreview() {
    TaskInfoCard(
        info = TaskInfo(
            title = "Do something work",
            task = "Fix something important",
            description = "Make something work because this is out work",
            color = Gray,
            businessUnit = "",
            businessUnitKey = "",
            parentTaskID = "",
            isAvailableInTimeTrackingKioskMode = false,
            wageType = "",
            sort = "",
            workingTime = "",
            preplanningBoardQuickSelect = ""
        )
    )
}
