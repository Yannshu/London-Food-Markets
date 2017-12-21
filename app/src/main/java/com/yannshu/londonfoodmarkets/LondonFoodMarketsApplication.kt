package com.yannshu.londonfoodmarkets

import android.app.Activity
import android.app.Application
import com.yannshu.londonfoodmarkets.di.activity.ActivityComponentBuilder
import com.yannshu.londonfoodmarkets.di.activity.HasActivitySubComponentBuilders
import com.yannshu.londonfoodmarkets.di.app.AppComponent
import com.yannshu.londonfoodmarkets.di.app.AppModule
import com.yannshu.londonfoodmarkets.di.app.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class LondonFoodMarketsApplication : Application(), HasActivitySubComponentBuilders {

    @Inject
    lateinit var mActivityComponentBuilders: Map<Class<out Activity>, @JvmSuppressWildcards Provider<ActivityComponentBuilder<*, *>>>

    lateinit var mAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        Timber.d("LondonFoodMarketsApplication.onCreate")
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        initAppComponent()
    }

    private fun initAppComponent() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        mAppComponent.inject(this)
    }

    override fun getActivityComponentBuilder(activityClass: Class<out Activity>): ActivityComponentBuilder<*, *>? {
        return mActivityComponentBuilders[activityClass]?.get()
    }
}