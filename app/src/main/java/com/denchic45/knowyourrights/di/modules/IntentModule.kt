package com.denchic45.knowyourrights.di.modules

import com.denchic45.knowyourrights.ui.quizDetails.QuizDetailsFragment
import com.denchic45.knowyourrights.ui.quizPlayer.QuizPlayerActivity
import com.denchic45.knowyourrights.ui.result.QuizResultFragment
import com.denchic45.knowyourrights.ui.resultts.QuizResultsFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object IntentModule {

    @Named(QuizDetailsFragment.QUIZ_ID)
    @Provides
    fun provideQuizIdToQuizDetailsFragment(quizDetailsFragment: QuizDetailsFragment): String {
        return quizDetailsFragment.navArgs.quizId
    }

    @Named(QuizPlayerActivity.QUIZ_ID)
    @Provides
    fun provideQuizIdToQuizPlayerActivity(quizPlayerActivity: QuizPlayerActivity): String {
        return quizPlayerActivity.intent.getStringExtra(QuizPlayerActivity.QUIZ_ID)!!
    }

    @Named(QuizResultFragment.QUIZ_RESULT_ID)
    @Provides
    fun provideQuizResultId(quizResultFragment: QuizResultFragment): String {
        return quizResultFragment.navArgs.quizResultId
    }
}