package com.denchic45.knowyourrights.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
    val quizId: String
)