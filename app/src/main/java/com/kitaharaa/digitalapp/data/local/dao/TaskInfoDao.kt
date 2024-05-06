package com.kitaharaa.digitalapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kitaharaa.digitalapp.data.local.entity.TaskInfoEntity

@Dao
interface TaskInfoDao {
    @Upsert
    suspend fun upsertTasks(tasks: List<TaskInfoEntity>)

    @Query("DELETE FROM task_info_table")
    suspend fun deleteAll()

    @Query(
        "SELECT * FROM task_info_table  WHERE " + SEARCH_QUERY +
                "ORDER BY task ASC"
    )
    fun getAllTaskSortedByDefaultWithQuery(searchString: String): PagingSource<Int, TaskInfoEntity>

    @Query(
        "SELECT * FROM task_info_table WHERE" + SEARCH_QUERY + "ORDER BY business_unit ASC"
    )
    fun getAllSortedByBusinessUnitAsc(searchString: String): PagingSource<Int, TaskInfoEntity>

    @Query(
        "SELECT * FROM task_info_table WHERE " + SEARCH_QUERY + "ORDER BY business_unit DESC"
    )
    fun getAllSortedByBusinessUnitDesc(searchString: String): PagingSource<Int, TaskInfoEntity>

    companion object {

        private const val SEARCH_QUERY = "(business_unit LIKE '%' || :searchString || '%' OR " +
                "business_nit_key LIKE '%' || :searchString || '%' OR " +
                "color_code LIKE '%' || :searchString || '%' OR " +
                "description LIKE '%' || :searchString || '%' OR " +
                "is_available_in_time_tracking_kiosk_mode LIKE '%' || :searchString || '%' OR " +
                "parent_task_id LIKE '%' || :searchString || '%' OR " +
                "preplanning_board_quick_select LIKE '%' || :searchString || '%' OR " +
                "sort LIKE '%' || :searchString || '%' OR " +
                "task LIKE '%' || :searchString || '%' OR " +
                "title LIKE '%' || :searchString || '%' OR " +
                "wage_type LIKE '%' || :searchString || '%' OR " +
                "working_time LIKE '%' || :searchString || '%'" +
                "AND :searchString != '')"
    }
}