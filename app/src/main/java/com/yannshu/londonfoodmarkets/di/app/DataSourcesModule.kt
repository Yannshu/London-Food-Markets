package com.yannshu.londonfoodmarkets.di.app

import com.google.firebase.firestore.FirebaseFirestore
import com.yannshu.londonfoodmarkets.data.FoodMarketsDataSource
import dagger.Module
import dagger.Provides

@Module
class DataSourcesModule {
    @Provides
    fun provideFoodMarketsDataSource(firestore: FirebaseFirestore): FoodMarketsDataSource {
        return FoodMarketsDataSource(firestore)
    }
}