package dev.hossam.expensetracker.core.data.local.sharedpref

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

object SharedPrefUtil {

    private lateinit var appContext: Application
    private const val PREF_NAME = "MyPrefs"
    private const val APP_THEME = "theme"
    private lateinit var sharedPreferences: SharedPreferences


    fun init(appContext: Application) {
        this.appContext = appContext
        sharedPreferences = appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setAppTheme(isLight: Boolean) {
        val editor = sharedPreferences.edit()
        editor?.putBoolean(APP_THEME, isLight)?.apply()
    }

    fun getAppTheme(): Boolean = sharedPreferences.getBoolean(APP_THEME, true)

}