package com.denchic45.knowyourrights.di.modules

import android.content.Context
import com.denchic45.knowyourrights.data.DataBase
import com.denchic45.knowyourrights.data.dao.AnswerDao
import com.denchic45.knowyourrights.data.dao.QuestionDao
import com.denchic45.knowyourrights.data.dao.QuizDao
import com.denchic45.knowyourrights.data.dao.QuizResultDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(context: Context): DataBase = DataBase.getInstance(context)

    @Provides
    fun provideQuizDao(dataBase: DataBase): QuizDao = dataBase.quizDao()

    @Provides
    fun provideQuizResultDao(dataBase: DataBase): QuizResultDao = dataBase.quizResultDao()

    @Provides
    fun provideQuestionDao(dataBase: DataBase): QuestionDao = dataBase.questionDao()

    @Provides
    fun provideAnswerDao(dataBase: DataBase): AnswerDao = dataBase.answerDao()
}