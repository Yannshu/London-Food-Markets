package com.yannshu.londonfoodmarkets.di.activity

import com.yannshu.londonfoodmarkets.ui.MainActivity
import com.yannshu.londonfoodmarkets.ui.MainActivityComponent
import com.yannshu.londonfoodmarkets.ui.FoodMarketActivity
import com.yannshu.londonfoodmarkets.ui.FoodMarketActivityComponent
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(subcomponents = arrayOf(
        MainActivityComponent::class,
        FoodMarketActivityComponent::class
))
abstract class ActivityBindingModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun mainActivityComponentBuilder(impl: MainActivityComponent.Builder): ActivityComponentBuilder<*, *>

    @Binds
    @IntoMap
    @ActivityKey(FoodMarketActivity::class)
    abstract fun marketActivityComponentBuilder(impl: FoodMarketActivityComponent.Builder): ActivityComponentBuilder<*, *>
}