package com.denchic45.knowyourrights.data.model

import androidx.room.Embedded
import androidx.room.Relation

class QuestionAndAnswerEntities(

    @Embedded
    val questionEntity: QuestionEntity,

    @Relation(
        parentColumn = "questionId",
        entityColumn = "questionId"
    )
    val answerEntity: AnswerEntity
)