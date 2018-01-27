package com.yannshu.londonfoodmarkets.di.app

import com.yannshu.londonfoodmarkets.utils.AdsWrapper
import com.yannshu.londonfoodmarkets.utils.FoodMarketPlaceholderProvider
import com.yannshu.londonfoodmarkets.utils.MapsUtils
import dagger.Module
import dagger.Provides

@Module
class UtilsModule {
    @Provides
    fun provideMapsUtils(): MapsUtils = MapsUtils()

    @Provides
    fun provideFoodMarketPlaceholder(): FoodMarketPlaceholderProvider = FoodMarketPlaceholderProvider()

    @Provides
    fun provideAdsWrapper(): AdsWrapper = AdsWrapper()
}