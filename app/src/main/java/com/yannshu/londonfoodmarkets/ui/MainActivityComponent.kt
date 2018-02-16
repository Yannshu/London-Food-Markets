package com.yannshu.londonfoodmarkets.ui

import com.yannshu.londonfoodmarkets.data.FoodMarketsDataSource
import com.yannshu.londonfoodmarkets.di.activity.ActivityComponent
import com.yannshu.londonfoodmarkets.di.activity.ActivityComponentBuilder
import com.yannshu.londonfoodmarkets.di.activity.ActivityModule
import com.yannshu.londonfoodmarkets.di.activity.ActivityScope
import com.yannshu.londonfoodmarkets.presenters.MainActivityPresenter
import com.yannshu.londonfoodmarkets.ui.adapters.FoodMarketsAdapter
import com.yannshu.londonfoodmarkets.utils.FoodMarketPlaceholderProvider
import com.yannshu.londonfoodmarkets.utils.MapCameraPositionSaver
import com.yannshu.londonfoodmarkets.utils.MapsUtils
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(MainActivityComponent.MainActivityModule::class))
interface MainActivityComponent : ActivityComponent<MainActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<MainActivityModule, MainActivityComponent>

    @Module
    class MainActivityModule(activity: MainActivity) : ActivityModule<MainActivity>(activity) {

        @Provides
        fun provideMainActivityPresenter(foodMarketsDataSource: FoodMarketsDataSource, mapsUtils: MapsUtils, mapCameraPositionSaver: MapCameraPositionSaver): MainActivityPresenter =
                MainActivityPresenter(foodMarketsDataSource, mapsUtils, mapCameraPositionSaver)

        @Provides
        fun provideFoodMarketsAdapter(foodMarketPlaceholderProvider: FoodMarketPlaceholderProvider): FoodMarketsAdapter = FoodMarketsAdapter(activity, foodMarketPlaceholderProvider)
    }
}
