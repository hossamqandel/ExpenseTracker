package dev.hossam.expensetracker

import android.app.Application
import dev.hossam.expensetracker.core.data.sharedpref.SharedPrefUtil

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPrefUtil.init(this)
    }


}