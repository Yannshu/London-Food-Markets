package com.yannshu.londonfoodmarkets.di.app

import com.yannshu.londonfoodmarkets.utils.MapsUtils
import dagger.Module
import dagger.Provides

@Module
class UtilsModule {
    @Provides
    fun provideMapsUtils(): MapsUtils = MapsUtils()
}