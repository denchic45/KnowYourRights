package com.denchic45.knowyourrights.data.model

import androidx.room.*
import com.denchic45.knowyourrights.data.TimestampConverter
import java.time.LocalDateTime
import java.util.*

@Entity(
    tableName = "quiz_result",
    foreignKeys = [
        ForeignKey(
            entity = QuizEntity::class,
            parentColumns = ["quizId"],
            childColumns = ["quizId"]
        )
    ]
)
class QuizResultEntity(
    @PrimaryKey
    @ColumnInfo(name = "quizResultId")
    val id: String,
    val quizId: String,
    @field:TypeConverters(TimestampConverter::class)
    val timestamp: LocalDateTime
)