package com.yannshu.londonfoodmarkets.utils.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

object AnalyticsWrapper {
    fun logEvent(context: Context, event: String, args: Bundle? = null) =
        FirebaseAnalytics.getInstance(context).logEvent(event, args)
}