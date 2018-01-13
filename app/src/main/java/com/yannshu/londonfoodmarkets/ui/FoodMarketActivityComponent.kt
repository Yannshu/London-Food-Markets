package com.yannshu.londonfoodmarkets.ui

import com.yannshu.londonfoodmarkets.data.model.FoodMarket
import com.yannshu.londonfoodmarkets.di.activity.ActivityComponent
import com.yannshu.londonfoodmarkets.di.activity.ActivityComponentBuilder
import com.yannshu.londonfoodmarkets.di.activity.ActivityModule
import com.yannshu.londonfoodmarkets.di.activity.ActivityScope
import com.yannshu.londonfoodmarkets.presenters.FoodMarketActivityPresenter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(FoodMarketActivityComponent.FoodMarketActivityModule::class))
interface FoodMarketActivityComponent : ActivityComponent<FoodMarketActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<FoodMarketActivityModule, FoodMarketActivityComponent>

    @Module
    class FoodMarketActivityModule(activity: FoodMarketActivity, private val market: FoodMarket) : ActivityModule<FoodMarketActivity>(activity) {

        @Provides
        fun provideFoodMarketActivityPresenter(): FoodMarketActivityPresenter {
            return FoodMarketActivityPresenter(market)
        }
    }
}
