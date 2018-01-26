package com.yannshu.londonfoodmarkets.utils

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.yannshu.londonfoodmarkets.R

class AdsWrapper {

    fun init(context: Context) {
        MobileAds.initialize(context, context.getString(R.string.google_admob_app_id))
    }

    fun loadAd(adView: AdView) {
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
}