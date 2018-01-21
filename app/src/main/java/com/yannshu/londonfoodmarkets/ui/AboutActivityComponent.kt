package com.yannshu.londonfoodmarkets.ui

import com.yannshu.londonfoodmarkets.di.activity.ActivityComponent
import com.yannshu.londonfoodmarkets.di.activity.ActivityComponentBuilder
import com.yannshu.londonfoodmarkets.di.activity.ActivityModule
import com.yannshu.londonfoodmarkets.di.activity.ActivityScope
import dagger.Module
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(AboutActivityComponent.AboutActivityModule::class))
interface AboutActivityComponent : ActivityComponent<AboutActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<AboutActivityModule, AboutActivityComponent>

    @Module
    class AboutActivityModule(activity: AboutActivity) : ActivityModule<AboutActivity>(activity)
}
