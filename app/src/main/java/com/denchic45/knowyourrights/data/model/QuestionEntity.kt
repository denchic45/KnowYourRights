package com.denchic45.knowyourrights.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "question",
foreignKeys = [
    ForeignKey(
        entity = QuizEntity::class,
        parentColumns = ["quizId"],
        childColumns = ["quizId"]
    )
])
class QuestionEntity(
    @PrimaryKey
    @ColumnInfo(name = "questionId")
    val id: String,
    val quizId: String,
    val title: String,
    val imageUrl: String?,
    val choiceType: ChoiceType,
    val answers: String,
    val correctAnswer: String
) {
    enum class ChoiceType {
        SINGLE_CHOICE,
        MULTI_CHOICE,
        ENTER_CHOICE
    }
}