package com.yannshu.londonfoodmarkets.di.app

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Resources
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.yannshu.londonfoodmarkets.LondonFoodMarketsApplication
import com.yannshu.londonfoodmarkets.config.GeoPointDeserializer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    companion object {
        private const val SHARED_PREF_NAME = "app_shared_preferences"
    }

    @Provides
    fun provideSharedPreferences(application: LondonFoodMarketsApplication): SharedPreferences = application.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)

    @Provides
    fun provideAppContext(application: LondonFoodMarketsApplication): Context = application.applicationContext

    @Provides
    fun provideResources(application: LondonFoodMarketsApplication): Resources = application.resources

    @Provides
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideObjectMapper(module: com.fasterxml.jackson.databind.Module): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        objectMapper.registerModule(module)
        return objectMapper
    }

    @Provides
    fun provideObjectMapperModule(): com.fasterxml.jackson.databind.Module {
        val module = SimpleModule()
        module.addDeserializer(GeoPoint::class.java, GeoPointDeserializer())
        return module
    }
}