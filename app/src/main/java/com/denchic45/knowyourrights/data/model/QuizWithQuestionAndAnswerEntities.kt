package com.denchic45.knowyourrights.data.model

import androidx.room.Embedded
import androidx.room.Relation


class QuizWithQuestionAndAnswerEntities(

    @Embedded
    val quizResultEntity: QuizResultEntity,

    @Relation(
        parentColumn = "quizId",
        entityColumn = "quizId"
    )
    val quizEntity: QuizEntity,

    @Relation(
        entity = QuestionEntity::class,
        parentColumn = "quizId",
        entityColumn = "quizId"
    )
    val questionAndAnswerEntities: List<QuestionAndAnswerEntities>,
)