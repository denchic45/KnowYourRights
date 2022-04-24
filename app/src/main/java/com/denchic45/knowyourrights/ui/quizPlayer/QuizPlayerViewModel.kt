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
import java.time.LocalDateTime
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

    val quiz = flow { emit(findQuizUseCase(quizId)) }.shareIn(
        viewModelScope,
        SharingStarted.Lazily,
        replay = 1
    )

    val readyAnswer: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val showActualAnswer: MutableSharedFlow<ActualAnswer> = MutableSharedFlow()

    val retryQuiz = MutableSharedFlow<String>()



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
            val correctAnswer: String,
            val correct: Boolean
        ) : ActualAnswer()
    }

    private var currentQuestionPosition = MutableStateFlow(0)

    fun onSingleAnswerSelect(position: Int) {
        viewModelScope.launch {
            selectedAnswer = Question.Answer.SingleAnswer(
                (currentQuestion.first().choice as Question.Choice.SingleChoice)
                    .mixedAnswers[position]
            )
            readyAnswer.emit(true)
        }
    }

    fun onMultiAnswerSelect(positions: Set<Int>) {
        viewModelScope.launch {
            selectedAnswer = Question.Answer.MultiAnswer(
                positions.map { position ->
                    (currentQuestion.first().choice as Question.Choice.MultiChoice)
                        .mixedAnswers[position]
                }.toSet()
            )

            readyAnswer.emit(true)
        }
    }

    fun onEnterAnswerSelect(answer: String) {
        viewModelScope.launch {
            selectedAnswer = Question.Answer.EnterAnswer(answer)
            readyAnswer.emit(true)
        }
    }


    private val questions = flow {
        emit(findQuestionsByQuizUseCaseUseCase(quizId))
    }.map { it.shuffled() }.shareIn(viewModelScope, SharingStarted.Lazily, replay = 1)

    val currentQuestion =
        combine(questions, currentQuestionPosition) { questions, pos ->
            questions[pos]
        }.shareIn(viewModelScope, SharingStarted.Lazily, replay = 1)


    fun onTryAnswerClick() {
        viewModelScope.launch {
            readyAnswer.emit(false)
            currentQuestion.first().apply {
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
                                choice.mixedAnswers.indexOf(choice.correctAnswer.value),
                                if (!choice.tryAnswer(selectedAnswer!!)) {
                                    choice.mixedAnswers.indexOf((selectedAnswer as Question.Answer.SingleAnswer).value)
                                } else null
                            )
                        }
                        is Question.Choice.MultiChoice -> {
                            val selectedMultiAnswer =
                                this@QuizPlayerViewModel.selectedAnswer as Question.Answer.MultiAnswer

                            choice.getCorrectAndWrongMixAnswerPositions(selectedMultiAnswer)
                                .run { ActualAnswer.MultiActualAnswer(first, second) }
                        }
                        is Question.Choice.EnterChoice -> {
                            ActualAnswer.EnterActualAnswer(
                                (currentQuestion.first().choice.correctAnswer as Question.Answer.EnterAnswer).value,
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
                        quiz.first(),
                        passedQuestions,
                        LocalDateTime.now()
                    )
                )
                navigateTo(QuizNavigationDirections.actionGlobalFinishFragment(quizId))
            } else {
                currentQuestionPosition.update { it + 1 }
                navigateTo(QuizNavigationDirections.actionGlobalQuestionFragment())
            }
        }
    }

    fun onRetryClick() {
        viewModelScope.launch {
            retryQuiz.emit(quizId)
        }
    }

    init {
        viewModelScope.launch {
            delay(2500)
            navigateTo(QuizNavigationDirections.actionGlobalQuestionFragment())
        }
    }
}