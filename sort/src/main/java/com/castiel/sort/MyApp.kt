package com.castiel.sort

import android.app.Application
import com.castiel.common.AppManager

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppManager.instance.init(this)
    }
}