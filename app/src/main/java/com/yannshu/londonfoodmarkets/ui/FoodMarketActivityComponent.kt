package com.yannshu.londonfoodmarkets.ui

import com.yannshu.londonfoodmarkets.data.model.FoodMarket
import com.yannshu.londonfoodmarkets.di.activity.ActivityComponent
import com.yannshu.londonfoodmarkets.di.activity.ActivityComponentBuilder
import com.yannshu.londonfoodmarkets.di.activity.ActivityModule
import com.yannshu.londonfoodmarkets.di.activity.ActivityScope
import com.yannshu.londonfoodmarkets.di.app.TimeModule.Companion.NAME_DAY_OF_WEEK_STRING
import com.yannshu.londonfoodmarkets.presenters.FoodMarketActivityPresenter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Named

@ActivityScope
@Subcomponent(modules = [FoodMarketActivityComponent.FoodMarketActivityModule::class])
interface FoodMarketActivityComponent : ActivityComponent<FoodMarketActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<FoodMarketActivityModule, FoodMarketActivityComponent>

    @Module
    class FoodMarketActivityModule(activity: FoodMarketActivity, private val market: FoodMarket) : ActivityModule<FoodMarketActivity>(activity) {

        @Provides
        fun provideFoodMarketActivityPresenter(@Named(NAME_DAY_OF_WEEK_STRING) dayOfWeek: String): FoodMarketActivityPresenter {
            return FoodMarketActivityPresenter(market, dayOfWeek)
        }
    }
}
