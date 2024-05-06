package com.kitaharaa.digitalapp.domain.entity

import androidx.compose.ui.graphics.Color

data class TaskInfo (
    val id: Int = 0,
    val businessUnit: String, // Gerüstbau
    val businessUnitKey: String, // Gerüstbau
    val color: Color, // #1df70e
    val description: String, // Gerüste montieren.
    val isAvailableInTimeTrackingKioskMode: Boolean, // false
    val parentTaskID: String,
    val preplanningBoardQuickSelect: String, // null
    val sort: String, // 0
    val task: String, // 10 Aufbau
    val title: String, // Gerüst montieren
    val wageType: String, // 10 Aufbau
    val workingTime: String // null
)