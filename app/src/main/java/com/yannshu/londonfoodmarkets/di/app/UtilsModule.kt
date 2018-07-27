package com.yannshu.londonfoodmarkets.di.app

import android.content.Context
import com.yannshu.londonfoodmarkets.utils.AdsWrapper
import com.yannshu.londonfoodmarkets.utils.FoodMarketPlaceholderProvider
import com.yannshu.londonfoodmarkets.utils.MapCameraPositionSaver
import com.yannshu.londonfoodmarkets.utils.MapsUtils
import com.yannshu.londonfoodmarkets.utils.SharedPreferencesWrapper
import dagger.Module
import dagger.Provides

@Module
class UtilsModule {
    @Provides
    fun provideMapsUtils() = MapsUtils()

    @Provides
    fun provideFoodMarketPlaceholder() = FoodMarketPlaceholderProvider()

    @Provides
    fun provideAdsWrapper() = AdsWrapper()

    @Provides
    fun provideSharedPreferencesWrapper(context: Context) = SharedPreferencesWrapper(context)

    @Provides
    fun provideMapCameraPositionSaver(sharedPreferencesWrapper: SharedPreferencesWrapper) = MapCameraPositionSaver(sharedPreferencesWrapper)
}