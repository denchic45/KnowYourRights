package com.denchic45.knowyourrights.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "answer",
    foreignKeys = [
        ForeignKey(
            entity = QuestionEntity::class,
            parentColumns = ["questionId"],
            childColumns = ["questionId"]
        ),
        ForeignKey(
            entity = QuizResultEntity::class,
            parentColumns = ["quizResultId"],
            childColumns = ["quizResultId"]
        )
    ]
)
class AnswerEntity(
    @PrimaryKey
    @ColumnInfo(name = "answerId")
    val id: String,
    val questionId: String,
    val quizResultId: String,
    val answer: String,
    val isCorrect: Boolean
)