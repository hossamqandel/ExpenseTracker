package dev.hossam.expensetracker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import dev.hossam.expensetracker.core.data.sharedpref.SharedPrefUtil

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPrefUtil.init(this)
        setAppTheme()
    }

    private fun setAppTheme(){
        val isLight = SharedPrefUtil.getAppTheme()
        when(isLight){
            true -> AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            false -> AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        }
    }

}