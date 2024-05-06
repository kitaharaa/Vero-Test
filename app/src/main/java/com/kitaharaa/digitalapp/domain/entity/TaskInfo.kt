package com.kitaharaa.digitalapp.domain.entity

import androidx.compose.ui.graphics.Color

data class TaskInfo (
    val id: Int = 0,
    val businessUnit: String? = null, // Gerüstbau
    val businessUnitKey: String? = null, // Gerüstbau
    val color: Color?, // #1df70e
    val description: String? = null, // Gerüste montieren.
    val isAvailableInTimeTrackingKioskMode: Boolean? = null, // false
    val parentTaskID: String? = null,
    val preplanningBoardQuickSelect: String? = null, // null
    val sort: String? = null, // 0
    val task: String? = null, // 10 Aufbau
    val title: String? = null, // Gerüst montieren
    val wageType: String? = null, // 10 Aufbau
    val workingTime: String? = null // null
)