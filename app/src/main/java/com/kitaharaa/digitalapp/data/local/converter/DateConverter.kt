package com.kitaharaa.digitalapp.data.local.converter

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}