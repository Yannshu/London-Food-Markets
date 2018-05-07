package com.yannshu.londonfoodmarkets.di.app

import android.content.Context
import com.yannshu.londonfoodmarkets.utils.AdsWrapper
import com.yannshu.londonfoodmarkets.utils.FoodMarketPlaceholderProvider
import com.yannshu.londonfoodmarkets.utils.MapCameraPositionSaver
import com.yannshu.londonfoodmarkets.utils.MapsUtils
import com.yannshu.londonfoodmarkets.utils.SharedPreferencesWrapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilsModule {
    @Provides
    @Singleton
    fun provideMapsUtils(): MapsUtils = MapsUtils()

    @Provides
    fun provideFoodMarketPlaceholder(): FoodMarketPlaceholderProvider = FoodMarketPlaceholderProvider()

    @Provides
    fun provideAdsWrapper(): AdsWrapper = AdsWrapper()

    @Provides
    fun provideSharedPreferencesWrapper(context: Context): SharedPreferencesWrapper = SharedPreferencesWrapper(context)

    @Provides
    fun provideMapCameraPositionSaver(sharedPreferencesWrapper: SharedPreferencesWrapper) = MapCameraPositionSaver(sharedPreferencesWrapper)
}