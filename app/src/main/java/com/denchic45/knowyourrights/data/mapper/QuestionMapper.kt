package com.denchic45.knowyourrights.data.mapper

import com.denchic45.knowyourrights.data.model.QuestionEntity
import com.denchic45.knowyourrights.domain.model.Question
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named

@Mapper
abstract class QuestionMapper {

    @Mapping(source = "questionEntity", target = "choice", qualifiedByName = ["mapChoice"])
    abstract fun entityToDomain(questionEntity: QuestionEntity): Question

    abstract fun entityToDomain(questionEntity: List<QuestionEntity>): List<Question>

    @Named("mapChoice")
    fun mapChoice(questionEntity: QuestionEntity): Question.Choice {
        return when (questionEntity.choiceType) {
            QuestionEntity.ChoiceType.SINGLE_CHOICE -> {
                Question.Choice.SingleChoice(
                    answers = questionEntity.answers.split(";"),
                    correctAnswer = Question.Answer.SingleAnswer(questionEntity.correctAnswer)
                )
            }
            QuestionEntity.ChoiceType.MULTI_CHOICE -> {
                Question.Choice.MultiChoice(
                    answers = questionEntity.answers.split(";"),
                    correctAnswer = Question.Answer.MultiAnswer(
                        questionEntity.correctAnswer.split(";").toSet()
                    )
                )
            }
            QuestionEntity.ChoiceType.ENTER_CHOICE -> {
                Question.Choice.EnterChoice(Question.Answer.EnterAnswer(questionEntity.correctAnswer))
            }
        }
    }
}