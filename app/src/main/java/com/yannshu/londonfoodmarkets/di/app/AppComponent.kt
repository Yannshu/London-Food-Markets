package com.yannshu.londonfoodmarkets.di.app

import com.yannshu.londonfoodmarkets.LondonFoodMarketsApplication
import com.yannshu.londonfoodmarkets.di.activity.ActivityBindingModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    ActivityBindingModule::class,
    DataSourcesModule::class,
    TimeModule::class,
    UtilsModule::class
])
interface AppComponent {
    fun inject(application: LondonFoodMarketsApplication): LondonFoodMarketsApplication
}