package com.denchic45.knowyourrights.data.model

import androidx.room.Embedded
import androidx.room.Relation


class QuizWithResultAndQuestionAndAnswerEntities(
    @Embedded
    val quizResultEntity: QuizResultEntity,

    @Relation(
        parentColumn = "quizId",
        entityColumn = "quizId"
    )
    val quizEntity: QuizEntity,

    @Relation(
        entity = AnswerEntity::class,
        parentColumn = "quizResultId",
        entityColumn = "quizResultId"
    )
    val answerAndQuestionEntities: List<AnswerAndQuestionEntities>,
)