package com.yannshu.londonfoodmarkets.di.activity

import com.yannshu.londonfoodmarkets.ui.base.BaseActivity
import dagger.Module
import dagger.Provides

@Module
abstract class ActivityModule<out T : BaseActivity>(protected val activity: T) {

    @Provides
    @ActivityScope
    fun provideActivity(): T {
        return activity
    }
}
