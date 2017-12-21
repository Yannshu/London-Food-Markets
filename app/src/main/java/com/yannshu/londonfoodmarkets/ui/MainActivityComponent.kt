package com.yannshu.londonfoodmarkets.ui

import com.yannshu.londonfoodmarkets.di.activity.ActivityComponent
import com.yannshu.londonfoodmarkets.di.activity.ActivityComponentBuilder
import com.yannshu.londonfoodmarkets.di.activity.ActivityModule
import com.yannshu.londonfoodmarkets.di.activity.ActivityScope
import dagger.Module
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(MainActivityComponent.MainActivityModule::class))
interface MainActivityComponent : ActivityComponent<MainActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<MainActivityModule, MainActivityComponent>

    @Module
    class MainActivityModule(activity: MainActivity) : ActivityModule<MainActivity>(activity)
}
