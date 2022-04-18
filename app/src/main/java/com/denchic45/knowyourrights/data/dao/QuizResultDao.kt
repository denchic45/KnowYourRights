package com.denchic45.knowyourrights.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.denchic45.knowyourrights.data.model.QuizResultEntity
import com.denchic45.knowyourrights.data.model.QuizWithResultAndQuestionAndAnswerEntities
import kotlinx.coroutines.flow.Flow

@Dao
abstract class QuizResultDao : BaseDao<QuizResultEntity>() {

    @Query("SELECT * FROM quiz_result")
    abstract fun getAll(): Flow<List<QuizWithResultAndQuestionAndAnswerEntities>>

    @Query("SELECT * FROM quiz_result WHERE quizResultId=:quizResultId")
    abstract suspend fun get(quizResultId: String): QuizWithResultAndQuestionAndAnswerEntities
}