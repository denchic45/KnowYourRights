package com.denchic45.knowyourrights.ui.quizPlayer

import androidx.lifecycle.viewModelScope
import com.denchic45.knowyourrights.QuizNavigationDirections
import com.denchic45.knowyourrights.domain.model.PassedQuestion
import com.denchic45.knowyourrights.domain.model.Question
import com.denchic45.knowyourrights.domain.model.QuizResult
import com.denchic45.knowyourrights.domain.useCase.AddQuizResultUseCase
import com.denchic45.knowyourrights.domain.useCase.FindQuestionsByQuizUseCase
import com.denchic45.knowyourrights.domain.useCase.FindQuizUseCase
import com.denchic45.knowyourrights.ui.base.BaseViewModel
import com.denchic45.knowyourrights.utils.UUIDS
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class QuizPlayerViewModel @Inject constructor(
    @Named(QuizPlayerActivity.QUIZ_ID)
    private val quizId: String,
    private val findQuestionsByQuizUseCaseUseCase: FindQuestionsByQuizUseCase,
    private val addQuizResultUseCase: AddQuizResultUseCase,
    private val findQuizUseCase: FindQuizUseCase
) : BaseViewModel() {

    private val passedQuestions: MutableList<PassedQuestion> = mutableListOf()

    private var selectedAnswer: Question.Answer? = null

    val readyAnswer: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val showActualAnswer: MutableSharedFlow<ActualAnswer> = MutableSharedFlow()

    sealed class ActualAnswer {
        data class SingleActualAnswer(
            val correctAnswerPosition: Int,
            val wrongAnswerPosition: Int?,
        ) : ActualAnswer()

        data class MultiActualAnswer(
            val correctAnswerPositions: Set<Int>,
            val wrongAnswerPositions: Set<Int>
        ) : ActualAnswer()

        data class EnterActualAnswer(
            val correct: Boolean
        ) : ActualAnswer()
    }

    private var currentQuestionPosition = MutableStateFlow(0)

    fun onSingleAnswerSelect(position: Int) {
        viewModelScope.launch {
            selectedAnswer = Question.Answer.SingleAnswer(
                (currentQuestion.first().choice as Question.Choice.SingleChoice)
                    .answers[position]
            )
            readyAnswer.emit(true)
        }
    }

    fun onMultiAnswerSelect(positions: Set<Int>) {
        viewModelScope.launch {
            selectedAnswer = Question.Answer.MultiAnswer(
                positions.map { position ->
                    (currentQuestion.first().choice as Question.Choice.MultiChoice)
                        .answers[position]
                }.toSet()
            )

            readyAnswer.emit(true)
        }
    }


    private val questions = flow {
        emit(findQuestionsByQuizUseCaseUseCase(quizId))
    }.shareIn(viewModelScope, SharingStarted.Lazily, replay = 1)

    val currentQuestion =
        combine(questions, currentQuestionPosition) { questions, pos ->
            questions[pos]
        }.shareIn(viewModelScope, SharingStarted.Lazily, replay = 1)


    fun onTryAnswerClick() {
        viewModelScope.launch {
            currentQuestion.first().apply {

                showToast(choice.tryAnswer(selectedAnswer!!).toString())

                passedQuestions.add(
                    PassedQuestion(
                        question = currentQuestion.first(),
                        answer = selectedAnswer!!
                    )
                )

                showActualAnswer.emit(
                    when (choice) {
                        is Question.Choice.SingleChoice -> {
                            ActualAnswer.SingleActualAnswer(
                                choice.answers.indexOf(choice.correctAnswer.value),
                                if (!choice.tryAnswer(selectedAnswer!!)) {
                                    choice.answers.indexOf((selectedAnswer as Question.Answer.SingleAnswer).value)
                                } else null
                            )
                        }
                        is Question.Choice.MultiChoice -> {
                            val selectedMultiAnswer =
                                this@QuizPlayerViewModel.selectedAnswer as Question.Answer.MultiAnswer

                            choice.getCorrectAndWrongAnswerPositions(selectedMultiAnswer)
                                .run { ActualAnswer.MultiActualAnswer(first, second) }
                        }
                        is Question.Choice.EnterChoice -> {
                            ActualAnswer.EnterActualAnswer(
                                choice.tryAnswer(selectedAnswer as Question.Answer.EnterAnswer)
                            )
                        }
                    }
                )
            }

            delay(1000)
            if (currentQuestionPosition.value == questions.first().size - 1) {
                addQuizResultUseCase(
                    QuizResult(
                        UUIDS.createShort(),
                        findQuizUseCase(quizId),
                        passedQuestions
                    )
                )
                navigateTo(QuizNavigationDirections.actionGlobalFinishFragment())
            } else {
                currentQuestionPosition.update { it + 1 }
                navigateTo(QuizNavigationDirections.actionGlobalQuestionFragment())
            }
        }
    }

    init {
        viewModelScope.launch {
            delay(1000)
            navigateTo(QuizNavigationDirections.actionGlobalQuestionFragment())
        }
    }
}