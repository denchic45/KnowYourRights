package com.denchic45.knowyourrights.domain.useCase

import com.denchic45.knowyourrights.data.repository.QuizRepository
import com.denchic45.knowyourrights.ui.model.QuizItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindQuizUseCase @Inject constructor(
    private val quizRepository: QuizRepository
) {

    suspend operator fun invoke(quizId: String):QuizItem {
        return quizRepository.findQuiz(quizId)
    }
}