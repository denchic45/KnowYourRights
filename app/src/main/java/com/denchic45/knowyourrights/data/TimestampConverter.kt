package com.denchic45.knowyourrights.data

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class TimestampConverter {

    @TypeConverter
    fun toLong(localDateTime: LocalDateTime): Long {
        return ZonedDateTime.of(localDateTime, ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    @TypeConverter
    fun toDate(timestamp: Long): LocalDateTime {
        return LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timestamp),
            ZoneId.systemDefault()
        )
    }
}