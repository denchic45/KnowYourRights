package com.denchic45.knowyourrights.data.mapper

import com.denchic45.knowyourrights.data.model.QuizWithResultAndQuestionAndAnswerEntities
import com.denchic45.knowyourrights.ui.model.QuizResultItem
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper(uses = [AnswerMapper::class])
abstract class QuizResultMapper {

    private val answerMapper: AnswerMapper = Mappers.getMapper(AnswerMapper::class.java)

    fun entityToQuizResultItem(quizWithQuestionAndAnswerEntities: QuizWithResultAndQuestionAndAnswerEntities): QuizResultItem {
        return QuizResultItem(
            id = quizWithQuestionAndAnswerEntities.quizResultEntity.id,
            name = quizWithQuestionAndAnswerEntities.quizEntity.name,
            questionsCount = quizWithQuestionAndAnswerEntities.answerAndQuestionEntities.size,
            maxResult = answerMapper.entityToDomain(quizWithQuestionAndAnswerEntities.answerAndQuestionEntities)
                .count { it.isCorrectAnswer },
            timestamp = quizWithQuestionAndAnswerEntities.quizResultEntity.timestamp
        )
    }

    abstract fun entityToQuizResultItem(quizResultEntity: List<QuizWithResultAndQuestionAndAnswerEntities>): List<QuizResultItem>
}