package com.denchic45.knowyourrights.data.model

import androidx.room.Embedded
import androidx.room.Relation

class QuizWithResultEntities(
    @Embedded
    val quizEntity: QuizEntity,
    @Relation(
        parentColumn = "quizId",
        entityColumn = "quizId"
    )
    val quizResultEntity: QuizResultEntity
)