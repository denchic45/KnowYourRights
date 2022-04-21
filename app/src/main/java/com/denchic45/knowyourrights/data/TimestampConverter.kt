package com.denchic45.knowyourrights.data

import androidx.room.TypeConverter
import java.util.*

class TimestampConverter {

    @TypeConverter
    fun toLong(date: Date): Long = date.time

    @TypeConverter
    fun toDate(timestamp: Long): Date = Date(timestamp)
}