package com.yannshu.londonfoodmarkets.ui

import com.yannshu.londonfoodmarkets.di.app.TimeModule.Companion.NAME_DAY_OF_WEEK_STRING
import com.yannshu.londonfoodmarkets.presenters.FoodMarketActivityPresenter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.android.AndroidInjector
import javax.inject.Named

@Subcomponent(modules = [FoodMarketActivityComponent.FoodMarketActivityModule::class])
interface FoodMarketActivityComponent : AndroidInjector<FoodMarketActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<FoodMarketActivity>()

    @Module
    class FoodMarketActivityModule {

        @Provides
        fun provideFoodMarketActivityPresenter(activity: FoodMarketActivity, @Named(NAME_DAY_OF_WEEK_STRING) dayOfWeek: String) =
            FoodMarketActivityPresenter(activity.market, dayOfWeek)
    }
}
