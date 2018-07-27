package com.yannshu.londonfoodmarkets.ui

import com.yannshu.londonfoodmarkets.data.FoodMarketsDataSource
import com.yannshu.londonfoodmarkets.di.app.TimeModule
import com.yannshu.londonfoodmarkets.presenters.MainActivityPresenter
import com.yannshu.londonfoodmarkets.ui.adapters.FoodMarketsAdapter
import com.yannshu.londonfoodmarkets.utils.FoodMarketPlaceholderProvider
import com.yannshu.londonfoodmarkets.utils.MapCameraPositionSaver
import com.yannshu.londonfoodmarkets.utils.MapsUtils
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.android.AndroidInjector
import javax.inject.Named

@Subcomponent(modules = [MainActivityComponent.MainActivityModule::class])
interface MainActivityComponent : AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()

    @Module
    class MainActivityModule {

        @Provides
        fun provideMainActivityPresenter(foodMarketsDataSource: FoodMarketsDataSource,
            mapsUtils: MapsUtils,
            mapCameraPositionSaver: MapCameraPositionSaver,
            @Named(TimeModule.NAME_DAY_OF_WEEK_STRING) dayOfWeek: String) =
            MainActivityPresenter(foodMarketsDataSource, mapsUtils, mapCameraPositionSaver, dayOfWeek)

        @Provides
        fun provideFoodMarketsAdapter(activity: MainActivity, foodMarketPlaceholderProvider: FoodMarketPlaceholderProvider) =
            FoodMarketsAdapter(activity, foodMarketPlaceholderProvider)
    }
}
