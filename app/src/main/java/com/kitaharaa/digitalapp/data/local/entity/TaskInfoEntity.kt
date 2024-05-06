package com.kitaharaa.digitalapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("task_info_table")
data class TaskInfoEntity(
    @PrimaryKey(true)
    val id: Int = 0,
    @ColumnInfo("business_unit")
    val businessUnit: String? = null, // Gerüstbau
    @ColumnInfo("business_nit_key")
    val businessUnitKey: String? = null, // Gerüstbau
    @ColumnInfo("color_code")
    val colorCode: String? = null, // #1df70e
    @ColumnInfo("description")
    val description: String? = null, // Gerüste montieren.
    @ColumnInfo("is_available_in_time_tracking_kiosk_mode")
    val isAvailableInTimeTrackingKioskMode: Boolean? = null, // false
    @ColumnInfo("parent_task_id")
    val parentTaskID: String? = null,
    @ColumnInfo("preplanning_board_quick_select")
    val preplanningBoardQuickSelect: String? = null, // null
    @ColumnInfo("sort")
    val sort: String? = null, // 0
    @ColumnInfo("task")
    val task: String? = null, // 10 Aufbau
    @ColumnInfo("title")
    val title: String? = null, // Gerüst montieren
    @ColumnInfo("wage_type")
    val wageType: String? = null, // 10 Aufbau
    @ColumnInfo("working_time")
    val workingTime: String? = null // null
)
