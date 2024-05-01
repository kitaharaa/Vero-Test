package com.kitaharaa.digitalapp.common.mapper

import com.kitaharaa.digitalapp.data.local.entity.TaskInfoEntity
import com.kitaharaa.digitalapp.data.remote.entity.task.TaskInfoResponse

fun TaskInfoResponse.toTaskEntity(): TaskInfoEntity {
    return TaskInfoEntity(
        businessUnit = this.businessUnit,
        businessUnitKey = this.businessUnitKey,
        colorCode = this.colorCode,
        description = this.description,
        isAvailableInTimeTrackingKioskMode = this.isAvailableInTimeTrackingKioskMode,
        parentTaskID = this.parentTaskID,
        preplanningBoardQuickSelect = this.preplanningBoardQuickSelect,
        sort = this.sort,
        task = this.task,
        title = this.title,
        wageType = this.wageType,
        workingTime = this.workingTime
    )
}