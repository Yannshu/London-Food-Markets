package com.yannshu.londonfoodmarkets.di.app

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Resources
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.yannshu.londonfoodmarkets.config.GeoPointDeserializer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    companion object {
        private val SHARED_PREF_NAME = "app_shared_preferences"
    }

    @Provides
    fun provideSharedPreferences(): SharedPreferences = application.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)

    @Provides
    fun provideAppContext(): Context = application.applicationContext

    @Provides
    fun provideResources(): Resources = application.resources

    @Provides
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideObjectMapper(module: com.fasterxml.jackson.databind.Module): ObjectMapper {
        val objectMapper = ObjectMapper()
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