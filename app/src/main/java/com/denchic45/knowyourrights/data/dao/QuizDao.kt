package com.denchic45.knowyourrights.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.denchic45.knowyourrights.data.model.QuizEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class QuizDao : BaseDao<QuizEntity>() {

    @Query("SELECT * FROM quiz qz")
    abstract fun observeAll(): Flow<List<QuizEntity>>

    @Query("SELECT * FROM quiz WHERE quizId=:quizId")
    abstract fun observe(quizId: String): Flow<QuizEntity>

    @Query("SELECT * FROM quiz WHERE quizId=:quizId")
    abstract suspend fun get(quizId: String): QuizEntity
}