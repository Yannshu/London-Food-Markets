package com.yannshu.londonfoodmarkets.di.activity

import com.yannshu.londonfoodmarkets.ui.AboutActivity
import com.yannshu.londonfoodmarkets.ui.AboutActivityModule
import com.yannshu.londonfoodmarkets.ui.FoodMarketActivity
import com.yannshu.londonfoodmarkets.ui.FoodMarketActivityModule
import com.yannshu.londonfoodmarkets.ui.MainActivity
import com.yannshu.londonfoodmarkets.ui.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [FoodMarketActivityModule::class])
    internal abstract fun foodMarketActivity(): FoodMarketActivity

    @ContributesAndroidInjector(modules = [AboutActivityModule::class])
    internal abstract fun aboutActivity(): AboutActivity
}