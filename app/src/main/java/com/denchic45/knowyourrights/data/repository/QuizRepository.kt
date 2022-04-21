package com.denchic45.knowyourrights.data.repository

import androidx.room.withTransaction
import com.denchic45.knowyourrights.data.DataBase
import com.denchic45.knowyourrights.data.dao.AnswerDao
import com.denchic45.knowyourrights.data.dao.QuestionDao
import com.denchic45.knowyourrights.data.dao.QuizDao
import com.denchic45.knowyourrights.data.dao.QuizResultDao
import com.denchic45.knowyourrights.data.mapper.AnswerMapper
import com.denchic45.knowyourrights.data.mapper.QuestionMapper
import com.denchic45.knowyourrights.data.mapper.QuizMapper
import com.denchic45.knowyourrights.data.mapper.QuizResultMapper
import com.denchic45.knowyourrights.domain.model.Question
import com.denchic45.knowyourrights.domain.model.QuizResult
import com.denchic45.knowyourrights.ui.model.QuizItem
import com.denchic45.knowyourrights.ui.model.QuizResultItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QuizRepository @Inject constructor(
    private val dataBase: DataBase,
    private val quizDao: QuizDao,
    private val quizResultDao: QuizResultDao,
    private val questionDao: QuestionDao,
    private val answerDao: AnswerDao,
    private val quizMapper: QuizMapper,
    private val quizResultMapper: QuizResultMapper,
    private val questionMapper: QuestionMapper,
    private val answerMapper: AnswerMapper
) {

    fun findQuizzes(): Flow<List<QuizItem>> {
        return quizDao.observeAll().map {
            it.map { quizEntity ->
                quizMapper.entityToDomain(
                    quizEntity,
                    questionDao.getByQuiz(quizEntity.id).size,
                    quizResultDao.getByQuizId(quizEntity.id).run {
                        if (isNotEmpty()) {
                            maxOf {
                                it.answerAndQuestionEntities.count { it.answerEntity.isCorrect }

                            }
                        } else 0
                    })
            }
        }
    }

    suspend fun findQuiz(quizId: String): QuizItem {
        return quizMapper.entityToDomain(quizDao.get(quizId),
            questionDao.getByQuiz(quizId).size,
            quizResultDao.getByQuizId(quizId)
                .run {
                    if (isNotEmpty()) {
                        maxOf {
                            it.answerAndQuestionEntities.count { it.answerEntity.isCorrect }

                        }
                    } else 0
                })
    }

    suspend fun findQuestionsByQuiz(quizId: String): List<Question> {
        return questionMapper.entityToDomain(questionDao.getByQuiz(quizId))
    }

    suspend fun addQuizResult(quizResult: QuizResult) {
        dataBase.withTransaction {
            quizResultDao.upsert(quizMapper.domainToEntity(quizResult))
            answerDao.upsert(answerMapper.quizResultToAnswerEntities(quizResult))
        }
    }

    fun findAllQuizResults(): Flow<List<QuizResultItem>> {
        return quizResultDao.getAll().map { quizResultMapper.entityToQuizResultItem(it) }
    }

    suspend fun findQuizResult(quizResultId: String): QuizResult {
        return answerMapper.entityToQuizResult(quizResultDao.get(quizResultId))
    }

    suspend fun findQuizResultByQuiz(quizId: String): QuizResultItem {
        return quizResultMapper.entityToQuizResultItem(quizResultDao.getLastByQuizId(quizId))
    }
}