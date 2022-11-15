package com.saigon.compose.utils

import android.content.Context
import android.content.res.Configuration
import com.saigon.compose.data.local.SharePreferenceManager
import java.util.*

object LocaleUtils {

// [AppPrefs] is sharedpreferences or datastore
    fun setLocale(c: Context, pref: SharePreferenceManager) = updateResources(c, pref.language() ?: "en") // use locale codes

    fun updateResources(context: Context, language: String) {
        context.resources.apply {
            val locale = Locale(language)
            val config = Configuration(configuration)

            context.createConfigurationContext(configuration)
            Locale.setDefault(locale)
            config.setLocale(locale)
            context.resources.updateConfiguration(config, displayMetrics)
        }
    }
}
