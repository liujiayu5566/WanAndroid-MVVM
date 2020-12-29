package com.castiel.home

import android.app.Application
import com.castiel.common.AppManager

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppManager.instance.init(this)
    }
}