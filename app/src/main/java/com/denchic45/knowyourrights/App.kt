package com.denchic45.knowyourrights

import com.denchic45.knowyourrights.di.components.DaggerAppComponent
import com.denchic45.kts.di.modules.AppModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {
    private val appComponent: AndroidInjector<App> = DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .create(this)

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent
    }
}