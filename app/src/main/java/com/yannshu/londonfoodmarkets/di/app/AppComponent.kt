package com.yannshu.londonfoodmarkets.di.app

import com.yannshu.londonfoodmarkets.LondonFoodMarketsApplication
import com.yannshu.londonfoodmarkets.di.activity.ActivityBindingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    ActivityBindingModule::class,
    DataSourcesModule::class,
    TimeModule::class,
    UtilsModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: LondonFoodMarketsApplication): Builder

        fun build(): AppComponent
    }

    fun inject(application: LondonFoodMarketsApplication): LondonFoodMarketsApplication
}