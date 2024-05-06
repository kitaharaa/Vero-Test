package com.kitaharaa.digitalapp.common.mapper

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.kitaharaa.digitalapp.data.local.entity.AuthorizationEntity
import com.kitaharaa.digitalapp.data.local.entity.TaskInfoEntity
import com.kitaharaa.digitalapp.data.remote.entity.auth.AuthorizationResponse
import com.kitaharaa.digitalapp.data.remote.entity.task.TaskInfoResponse
import com.kitaharaa.digitalapp.domain.entity.TaskInfo
import java.util.Calendar
import java.util.Date

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

fun AuthorizationResponse.toAuthEntity(): AuthorizationEntity {
    val calendar: Calendar = Calendar.getInstance().apply {
        setTime(Date())
        add(Calendar.SECOND, this@toAuthEntity.oauth?.expiresIn ?: 0)
    }

    return AuthorizationEntity(
        id = 1,
        token = this.oauth?.accessToken.toString(),
        updateDate = calendar.time,
        validIn = this.oauth?.expiresIn ?: 1200
    )
}

fun TaskInfoEntity.toTaskInfo(): TaskInfo {
    val color = try {
        Color(android.graphics.Color.parseColor(colorCode))
    } catch (e: Exception) {
        Log.e("TaskInfoMapper", "toTaskInfo: $e")
        null
    }

    return TaskInfo(
        id = id,
        businessUnit = businessUnit, // Gerüstbau
        businessUnitKey = businessUnitKey, // Gerüstbau
        color = color, // #1df70e
        description = description, // Gerüste montieren.
        isAvailableInTimeTrackingKioskMode = isAvailableInTimeTrackingKioskMode, // false
        parentTaskID = parentTaskID,
        preplanningBoardQuickSelect = preplanningBoardQuickSelect, // null
        sort = sort, // 0
        task = task, // 10 Aufbau
        title = title, // Gerüst montieren
        wageType = wageType, // 10 Aufbau
        workingTime = workingTime // null

    )
}