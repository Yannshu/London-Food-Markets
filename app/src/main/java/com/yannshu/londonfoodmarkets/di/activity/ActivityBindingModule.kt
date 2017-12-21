package com.yannshu.londonfoodmarkets.di.activity

import com.yannshu.londonfoodmarkets.ui.MainActivity
import com.yannshu.londonfoodmarkets.ui.MainActivityComponent
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(subcomponents = arrayOf(MainActivityComponent::class))
abstract class ActivityBindingModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun mainActivityComponentBuilder(impl: MainActivityComponent.Builder): ActivityComponentBuilder<*, *>
}