package com.yannshu.londonfoodmarkets.di.app

import com.yannshu.londonfoodmarkets.LondonFoodMarketsApplication
import com.yannshu.londonfoodmarkets.di.activity.ActivityBindingModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        ActivityBindingModule::class
))
interface AppComponent {
    fun inject(application: LondonFoodMarketsApplication): LondonFoodMarketsApplication
}