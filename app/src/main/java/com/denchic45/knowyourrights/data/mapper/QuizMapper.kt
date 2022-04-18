package com.denchic45.knowyourrights.data.mapper

import com.denchic45.knowyourrights.data.model.QuizEntity
import com.denchic45.knowyourrights.data.model.QuizResultEntity
import com.denchic45.knowyourrights.domain.model.QuizResult
import com.denchic45.knowyourrights.ui.model.QuizItem
import org.mapstruct.Mapper

@Mapper(uses = [AnswerMapper::class])
abstract class QuizMapper {

    abstract fun entityToDomain(quizEntity: QuizEntity): QuizItem

    abstract fun entityToDomain(quizEntity: List<QuizEntity>): List<QuizItem>

    abstract fun domainToEntity(quizResult: QuizResult): QuizResultEntity

}