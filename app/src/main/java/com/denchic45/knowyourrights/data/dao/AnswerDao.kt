package com.denchic45.knowyourrights.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.denchic45.knowyourrights.data.model.AnswerEntity
import com.denchic45.knowyourrights.data.model.QuizWithQuestionAndAnswerEntities

@Dao
abstract class AnswerDao : BaseDao<AnswerEntity>() {

    @Query("SELECT * FROM quiz_result WHERE quizId=:quizId")
    abstract fun getByQuizId(quizId: String): QuizWithQuestionAndAnswerEntities
}