package com.denchic45.knowyourrights.domain.useCase

import com.denchic45.knowyourrights.data.repository.QuizRepository
import com.denchic45.knowyourrights.domain.model.QuizResult
import com.denchic45.knowyourrights.ui.model.QuizResultItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindQuizResultUseCase @Inject constructor(
    private val quizRepository: QuizRepository
) {

    suspend operator fun invoke(quizResultId: String): QuizResult {
        return quizRepository.findQuizResult(quizResultId)
    }
}