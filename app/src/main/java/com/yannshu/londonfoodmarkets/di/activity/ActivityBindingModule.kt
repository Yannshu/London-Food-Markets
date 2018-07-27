package com.yannshu.londonfoodmarkets.di.activity

import android.app.Activity
import com.yannshu.londonfoodmarkets.ui.AboutActivity
import com.yannshu.londonfoodmarkets.ui.AboutActivityComponent
import com.yannshu.londonfoodmarkets.ui.FoodMarketActivity
import com.yannshu.londonfoodmarkets.ui.FoodMarketActivityComponent
import com.yannshu.londonfoodmarkets.ui.MainActivity
import com.yannshu.londonfoodmarkets.ui.MainActivityComponent
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [
    MainActivityComponent::class,
    FoodMarketActivityComponent::class,
    AboutActivityComponent::class
])
abstract class ActivityBindingModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun mainActivityInjectorFactory(impl: MainActivityComponent.Builder): AndroidInjector.Factory<out Activity>

    @Binds
    @IntoMap
    @ActivityKey(FoodMarketActivity::class)
    abstract fun foodMarketActivityInjectorFactory(impl: FoodMarketActivityComponent.Builder): AndroidInjector.Factory<out Activity>

    @Binds
    @IntoMap
    @ActivityKey(AboutActivity::class)
    abstract fun aboutActivityInjectorFactory(impl: AboutActivityComponent.Builder): AndroidInjector.Factory<out Activity>
}