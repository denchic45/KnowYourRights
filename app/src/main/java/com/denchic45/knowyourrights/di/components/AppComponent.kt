package com.denchic45.knowyourrights.di.components

import com.denchic45.knowyourrights.App
import com.denchic45.knowyourrights.di.modules.ActivityModule
import com.denchic45.knowyourrights.di.modules.FragmentModule
import com.denchic45.knowyourrights.di.modules.MapperModule
import com.denchic45.knowyourrights.di.modules.PreferenceModule
import com.denchic45.kts.di.modules.AppModule
import com.denchic45.knowyourrights.di.modules.DatabaseModule
import com.denchic45.kts.di.modules.DispatcherModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        DatabaseModule::class,
        MapperModule::class,
        PreferenceModule::class,
        DispatcherModule::class,
        FragmentModule::class,
        ActivityModule::class,
        AndroidInjectionModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>() {
        abstract fun appModule(appModule: AppModule): Builder
        abstract override fun build(): AppComponent
    }
}