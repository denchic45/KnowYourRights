package com.denchic45.knowyourrights.domain.useCase

import com.denchic45.knowyourrights.data.repository.QuizRepository
import com.denchic45.knowyourrights.domain.model.Question
import javax.inject.Inject

class FindQuestionsByQuizUseCase @Inject constructor(
    private val quizRepository: QuizRepository
) {

    suspend operator fun invoke(quizId: String):  List<Question> {
        return quizRepository.findQuestionsByQuiz(quizId)
    }
}