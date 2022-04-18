package com.denchic45.knowyourrights.di.modules

import com.denchic45.knowyourrights.data.mapper.*
import dagger.Module
import dagger.Provides

@Module
object MapperModule {

    @Provides
    fun provideQuizMapper(): QuizMapper = QuizMapperImpl()

    @Provides
    fun provideQuizResultMapper(): QuizResultMapper = QuizResultMapperImpl()

    @Provides
    fun provideQuestionMapper(): QuestionMapper = QuestionMapperImpl()

    @Provides
    fun provideAnswerMapper(): AnswerMapper = AnswerMapperImpl()
}