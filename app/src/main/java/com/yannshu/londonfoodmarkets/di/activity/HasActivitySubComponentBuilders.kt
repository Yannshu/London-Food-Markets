package com.yannshu.londonfoodmarkets.di.activity

import android.app.Activity

interface HasActivitySubComponentBuilders {
    fun getActivityComponentBuilder(activityClass: Class<out Activity>): ActivityComponentBuilder<*, *>?
}