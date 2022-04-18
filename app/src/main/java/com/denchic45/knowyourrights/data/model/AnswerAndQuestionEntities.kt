package com.denchic45.knowyourrights.data.model

import androidx.room.Embedded
import androidx.room.Relation

class AnswerAndQuestionEntities(
    @Embedded
    val answerEntity: AnswerEntity,

    @Relation(
        parentColumn = "questionId",
        entityColumn = "questionId"
    )
    val questionEntity: QuestionEntity,
    )