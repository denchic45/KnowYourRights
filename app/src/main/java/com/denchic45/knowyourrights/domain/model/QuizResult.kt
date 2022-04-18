package com.denchic45.knowyourrights.domain.model

import com.denchic45.knowyourrights.ui.model.QuizItem
import com.denchic45.knowyourrights.utils.UUIDS

data class QuizResult(
    override val id: String,
    val quizItem: QuizItem,
    val passedQuestions: List<PassedQuestion>
) : DomainModel

data class PassedQuestion(
    val question: Question,
    val answer: Question.Answer,
    val id: String = UUIDS.createShort()
) {
    val isCorrectAnswer: Boolean
        get() = question.tryAnswer(answer)
}