package com.denchic45.knowyourrights.di.modules

import com.denchic45.knowyourrights.MainActivity
import com.denchic45.knowyourrights.ui.quizPlayer.QuizPlayerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {

    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [IntentModule::class])
    fun contributeQuizPlayerActivity(): QuizPlayerActivity
}