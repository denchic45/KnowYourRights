package com.denchic45.knowyourrights.di.modules

import com.denchic45.knowyourrights.ui.quizDetails.QuizDetailsFragment
import com.denchic45.knowyourrights.ui.quizPlayer.enterChoice.EnterChoiceFragment
import com.denchic45.knowyourrights.ui.quizPlayer.finish.FinishFragment
import com.denchic45.knowyourrights.ui.quizPlayer.multiChoice.MultiChoiceFragment
import com.denchic45.knowyourrights.ui.quizPlayer.question.QuestionFragment
import com.denchic45.knowyourrights.ui.quizPlayer.singleChoice.SingleChoiceFragment
import com.denchic45.knowyourrights.ui.quizPlayer.start.StartQuizFragment
import com.denchic45.knowyourrights.ui.quizzes.QuizzesFragment
import com.denchic45.knowyourrights.ui.result.QuizResultFragment
import com.denchic45.knowyourrights.ui.resultts.QuizResultsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentModule {

    @ContributesAndroidInjector
    fun contributeQuizzesFragment(): QuizzesFragment

    @ContributesAndroidInjector
    fun contributeQuizResultsFragment(): QuizResultsFragment

    @ContributesAndroidInjector(modules = [IntentModule::class])
    fun contributeQuizDetailsFragment(): QuizDetailsFragment

    @ContributesAndroidInjector(modules = [IntentModule::class])
    fun contributeQuizResultFragment(): QuizResultFragment

    @ContributesAndroidInjector(modules = [IntentModule::class])
    fun contributeStartQuizFragment(): StartQuizFragment

    @ContributesAndroidInjector(modules = [IntentModule::class])
    fun contributeFinishFragment(): FinishFragment

    @ContributesAndroidInjector(modules = [IntentModule::class])
    fun contributeQuestionFragment(): QuestionFragment

    @ContributesAndroidInjector(modules = [IntentModule::class])
    fun contributeSingleChoiceFragment(): SingleChoiceFragment

    @ContributesAndroidInjector(modules = [IntentModule::class])
    fun contributeMultiChoiceFragment(): MultiChoiceFragment

    @ContributesAndroidInjector(modules = [IntentModule::class])
    fun contributeEnterChoiceFragment(): EnterChoiceFragment

}