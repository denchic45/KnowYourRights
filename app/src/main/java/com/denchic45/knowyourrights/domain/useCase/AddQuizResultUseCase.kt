package com.denchic45.knowyourrights.domain.useCase

import com.denchic45.knowyourrights.data.repository.QuizRepository
import com.denchic45.knowyourrights.domain.model.QuizResult
import javax.inject.Inject

class AddQuizResultUseCase @Inject constructor(private val quizRepository: QuizRepository) {

    suspend operator fun invoke(quizResult: QuizResult) {
        quizRepository.addQuizResult(quizResult)
    }
}