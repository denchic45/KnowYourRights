package com.denchic45.knowyourrights.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz")
class QuizEntity(
    @PrimaryKey
    @ColumnInfo(name = "quizId")
    val id: String,
    val name: String,
    val imageUrl: String,
    val lectureUrl: String
)