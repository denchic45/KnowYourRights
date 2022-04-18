package com.denchic45.knowyourrights.domain.useCase

import com.denchic45.knowyourrights.data.repository.QuizRepository
import com.denchic45.knowyourrights.ui.model.QuizItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindQuizzesUseCase @Inject constructor(
    private val quizRepository: QuizRepository
) {

    operator fun invoke():Flow<List<QuizItem>> {
        return quizRepository.findQuizzes()
    }
}