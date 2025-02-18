package com.health.diafit.util

import android.content.Context
import java.util.Locale

object LocaleHelper {
    @Suppress("DEPRECATION")
    fun updateLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}