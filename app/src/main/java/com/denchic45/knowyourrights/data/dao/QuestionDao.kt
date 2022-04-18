package com.denchic45.knowyourrights.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.denchic45.knowyourrights.data.model.QuestionEntity

@Dao
abstract class QuestionDao : BaseDao<QuestionEntity>() {

    @Query("SELECT * FROM question WHERE quizId=:quizId")
    abstract suspend fun getByQuiz(quizId: String): List<QuestionEntity>
}