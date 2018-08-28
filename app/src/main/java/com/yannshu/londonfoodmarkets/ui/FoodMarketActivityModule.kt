package com.yannshu.londonfoodmarkets.ui

import com.yannshu.londonfoodmarkets.di.app.TimeModule.Companion.NAME_DAY_OF_WEEK_STRING
import com.yannshu.londonfoodmarkets.presenters.FoodMarketActivityPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class FoodMarketActivityModule {

    @Provides
    fun provideFoodMarketActivityPresenter(activity: FoodMarketActivity, @Named(NAME_DAY_OF_WEEK_STRING) dayOfWeek: String) =
        FoodMarketActivityPresenter(activity.market, dayOfWeek)
}
