package com.kitaharaa.digitalapp.data.remote.entity.task


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep
import com.kitaharaa.digitalapp.data.local.entity.TaskInfoEntity

@Keep
@Serializable
data class TaskInfoDto(
    @SerialName("businessUnit")
    val businessUnit: String? = null, // Gerüstbau
    @SerialName("BusinessUnitKey")
    val businessUnitKey: String? = null, // Gerüstbau
    @SerialName("colorCode")
    val colorCode: String? = null, // #1df70e
    @SerialName("description")
    val description: String? = null, // Gerüste montieren.
    @SerialName("isAvailableInTimeTrackingKioskMode")
    val isAvailableInTimeTrackingKioskMode: Boolean? = null, // false
    @SerialName("parentTaskID")
    val parentTaskID: String? = null,
    @SerialName("preplanningBoardQuickSelect")
    val preplanningBoardQuickSelect: String? = null, // null
    @SerialName("sort")
    val sort: String? = null, // 0
    @SerialName("task")
    val task: String? = null, // 10 Aufbau
    @SerialName("title")
    val title: String? = null, // Gerüst montieren
    @SerialName("wageType")
    val wageType: String? = null, // 10 Aufbau
    @SerialName("workingTime")
    val workingTime: String? = null // null
) {
    fun toTaskEntity(): TaskInfoEntity {
        return TaskInfoEntity(
            businessUnit = businessUnit,
            businessUnitKey = businessUnitKey,
            colorCode = colorCode,
            description = description,
            isAvailableInTimeTrackingKioskMode = isAvailableInTimeTrackingKioskMode,
            parentTaskID = parentTaskID,
            preplanningBoardQuickSelect = preplanningBoardQuickSelect,
            sort = sort,
            task = task,
            title = title,
            wageType = wageType,
            workingTime = workingTime
        )
    }
}