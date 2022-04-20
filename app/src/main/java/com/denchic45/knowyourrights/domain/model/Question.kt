package com.denchic45.knowyourrights.domain.model

class Question(
    val id: String,
    val quizId: String,
    val title: String,
    val imageUrl: String?,
    val choice: Choice
) {

    fun tryAnswer(answer: Answer): Boolean {
        return choice.tryAnswer(answer)
    }

//    sealed class ChoiceType {
//
//        class SingleChoice(
//            val answers: List<String>,
//            private val correctAnswer: String
//        ) : ChoiceType() {
//
//            fun tryAnswer(position: Int): Boolean {
//                return answers[position] == correctAnswer
//            }
//        }
//
//        class MultiChoice(
//            val answers: List<String>,
//            private val correctAnswers: Set<String>
//        ) : ChoiceType() {
//
//            fun tryAnswer(
//                checkPositions: List<Int>
//            ): Pair<Set<Int>, Set<Int>> {
//                return Pair(
//                    checkPositions.map { answers[it] }.intersect(correctAnswers)
//                        .map { answers.indexOf(it) }.toSet(),
//                    checkPositions.map { answers[it] }.subtract(correctAnswers)
//                        .map { answers.indexOf(it) }.toSet()
//                )
//            }
//        }
//
//        class EnterChoice(
//            private val correctAnswer: String
//        ) : ChoiceType() {
//            fun tryAnswer(checkAnswer: String): Boolean {
//                return checkAnswer == correctAnswer
//            }
//        }
//    }

    sealed class Choice {

        abstract val correctAnswer: Answer

        fun tryAnswer(answer: Answer): Boolean {
            return this.correctAnswer == answer
        }

        class SingleChoice(
            val answers: List<String>,
            override val correctAnswer: Answer.SingleAnswer
        ) : Choice()

        class MultiChoice(
            val answers: List<String>,
            override val correctAnswer: Answer.MultiAnswer
        ) : Choice() {

            fun getCorrectAndWrongAnswerPositions(selectedMultiAnswer: Answer.MultiAnswer):Pair<Set<Int>,Set<Int>> {
                return Pair(
                    correctAnswer.value.map { answers.indexOf(it) }.toSet(),
                    selectedMultiAnswer.value.subtract(correctAnswer.value)
                        .map { answers.indexOf(it) }.toSet()
                )
            }
        }

        class EnterChoice(
            override val correctAnswer: Answer.EnterAnswer
        ) : Choice()
    }

    sealed class Answer {

       data class SingleAnswer(
             val value: String
        ) : Answer()

       data class MultiAnswer(
             val value: Set<String>
        ) : Answer()

       data class EnterAnswer(
             val value: String
        ) : Answer()
    }
}
