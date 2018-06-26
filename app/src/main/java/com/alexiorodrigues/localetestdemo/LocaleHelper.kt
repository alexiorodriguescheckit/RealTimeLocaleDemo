package com.alexiorodrigues.localetestdemo

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.preference.PreferenceManager
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import java.util.Locale


class LocaleHelper {

    companion object {

        val SELECTED_LANGUAGE: String = "Locale.Helper.Selected.Language"


        fun updateToLanguage(activity: Activity, language: String) {
            setLocale(activity.applicationContext, language)
            activity.recreate()
        }

        fun onAttach(context: Context): Context {
            val language: String = getPrefLanguage(context, Locale.getDefault().language)
            return setLocale(context, language)
        }

        fun onAttach(context: Context, defaultLanguage: String): Context {
            val language: String = defaultLanguage
            return setLocale(context, language)
        }

        private fun setLocale(context: Context, language: String): Context {
            setPrefLanguage(context, language)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                return updateResources(context, language)
            }

            return updateResourcesLegacy(context, language)
        }

        @TargetApi(Build.VERSION_CODES.N)
        private fun updateResources(context: Context, language: String): Context {
            val locale = Locale(language)
            Locale.setDefault(locale)

            val config: Configuration = context.resources.configuration
            config.setLocale(locale)
            config.setLayoutDirection(locale)

            return context.createConfigurationContext(config)
        }

        @Suppress("DEPRECATION")
        private fun updateResourcesLegacy(context: Context, language: String): Context {
            val locale = Locale(language)
            Locale.setDefault(locale)

            val resources: Resources = context.resources

            val config: Configuration = resources.configuration
            config.locale = locale

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                config.setLayoutDirection(locale)
            }

            resources.updateConfiguration(config, resources.displayMetrics)

            return context
        }

        private fun setPrefLanguage(context: Context, language: String) {
            val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor: SharedPreferences.Editor = preferences.edit()

            editor.putString(SELECTED_LANGUAGE, language)
            editor.apply()
        }

        private fun getPrefLanguage(context: Context, language: String): String {
            val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getString(SELECTED_LANGUAGE, language)
        }

    }

}