package com.denchic45.knowyourrights.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.denchic45.knowyourrights.data.model.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class QuestionDao : BaseDao<QuestionEntity>() {

    @Query("SELECT * FROM question WHERE quizId=:quizId")
    abstract suspend fun getByQuiz(quizId: String): List<QuestionEntity>

    @Query("SELECT * FROM question WHERE quizId=:quizId")
    abstract suspend fun observeByQuiz(quizId: String): Flow<List<QuestionEntity>>
}