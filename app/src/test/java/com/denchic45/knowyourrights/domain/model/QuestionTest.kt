package com.denchic45.knowyourrights.domain.model

import org.junit.jupiter.api.Test

class QuestionTest {

    @Test
    fun testMultiChoice() {
//        Question(
//            id = "",
//            quizId = "",
//            title = "Языки, написанные на jvm",
//            choiceType = Question.ChoiceType.MultiChoice(
//                answers = listOf("C#", "Java", "Pascal", "Kotlin"),
//                correctAnswers = setOf("Java", "Kotlin")
//            ).apply {
//                tryAnswer(1, 3, 2, 0).apply {
//                    println("Правильные ответы: ${first.map { answers[it] }}")
//                    println("Неправильные ответы: ${second.map { answers[it] }}")
//                }
//
//            }
//        )

        Question(
            id = "",
            quizId = "",
            title = "???",
            choice = Question.Choice.SingleChoice(
                listOf("1","2","3"),
                Question.Answer.SingleAnswer("2")
            )
        ).apply { tryAnswer(Question.Answer.MultiAnswer(setOf(""))) }
    }
}