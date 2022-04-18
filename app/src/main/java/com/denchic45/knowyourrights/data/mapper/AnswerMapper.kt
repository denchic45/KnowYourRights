package com.denchic45.knowyourrights.data.mapper

import com.denchic45.knowyourrights.data.model.*
import com.denchic45.knowyourrights.domain.model.PassedQuestion
import com.denchic45.knowyourrights.domain.model.Question
import com.denchic45.knowyourrights.domain.model.QuizResult
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named

@Mapper(uses = [QuestionMapper::class])
abstract class AnswerMapper {

    @Mapping(
        source = ".",
        target = "answer",
        qualifiedByName = ["stringToAnswer"]
    )
    @Mapping(
        source = "questionEntity",
        target = "question"
    )
    @Mapping(source = "answerEntity.id", target = "id")
    abstract fun entityToDomain(
        answerAndQuestionEntities: AnswerAndQuestionEntities
    ): PassedQuestion

    abstract fun entityToDomain(
        answerAndQuestionEntities: List<AnswerAndQuestionEntities>
    ): List<PassedQuestion>

    @Mapping(source = "passedQuestion.question.id", target = "questionId")
    @Mapping(
        source = "passedQuestion.answer",
        target = "answer",
        qualifiedByName = ["answerValueToString"]
    )
    abstract fun domainToEntity(passedQuestion: PassedQuestion, quizResultId: String): AnswerEntity

    @Named("passedQuestionsToAnswerEntities")
    fun domainToEntity(passedQuestion: List<PassedQuestion>, quizResultId: String): List<AnswerEntity> {
        return passedQuestion.map { domainToEntity(it, quizResultId) }
    }

    fun quizResultToAnswerEntities(quizResult: QuizResult): List<AnswerEntity> {
        return domainToEntity(quizResult.passedQuestions, quizResult.id)
    }

    abstract fun quizResultToEntity(quizResult: QuizResult):QuizResultEntity

    @Named("answerValueToString")
    fun answerValueToString(answer: Question.Answer): String {
        return when (answer) {
            is Question.Answer.SingleAnswer -> {
                answer.value
            }
            is Question.Answer.MultiAnswer -> {
                answer.value.joinToString(";")
            }
            is Question.Answer.EnterAnswer -> {
                answer.value
            }
        }
    }

    @Named("stringToAnswer")
    fun stringToAnswer(answerAndQuestionEntities: AnswerAndQuestionEntities): Question.Answer {
        return when (answerAndQuestionEntities.questionEntity.choiceType) {
            QuestionEntity.ChoiceType.SINGLE_CHOICE -> {
                Question.Answer.SingleAnswer(answerAndQuestionEntities.answerEntity.answer)
            }
            QuestionEntity.ChoiceType.MULTI_CHOICE -> {
                Question.Answer.MultiAnswer(
                    answerAndQuestionEntities.answerEntity.answer.split(";").toSet()
                )
            }
            QuestionEntity.ChoiceType.ENTER_CHOICE -> {
                Question.Answer.EnterAnswer(answerAndQuestionEntities.answerEntity.answer)
            }
        }
    }


    @Mapping(source = "answerAndQuestionEntities", target = "passedQuestions")
    @Mapping(source = "quizResultEntity", target = ".")
    @Mapping(source = "quizEntity", target = "quizItem")
    abstract fun entityToQuizResult(quizWithQuestionAndAnswerEntities: QuizWithResultAndQuestionAndAnswerEntities): QuizResult
}