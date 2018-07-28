package com.yannshu.londonfoodmarkets.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.design.widget.Snackbar
import android.view.View
import com.yannshu.londonfoodmarkets.R
import timber.log.Timber

fun Context.safeStartActivity(intent: Intent): Boolean {
    return try {
        startActivity(intent)
        true
    } catch (e: ActivityNotFoundException) {
        Timber.e(e)
        false
    }
}

fun Context.safeStartActivityWithViewAction(data: String): Boolean {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(data)
    return safeStartActivity(intent)
}

fun Context.safeStartActivityWithViewActionAndErrorDisplay(url: String, rootView: View) {
    if (!safeStartActivityWithViewAction(url)) {
        Snackbar.make(rootView, R.string.install_browser, Snackbar.LENGTH_LONG).show()
    }
}