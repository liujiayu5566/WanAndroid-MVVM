package com.castio.me

import com.castio.common.AppManager
import com.castio.common.BaseApp

class MyApp : BaseApp() {

    override fun onCreate() {
        super.onCreate()
        AppManager.instance.init(this)
    }
}