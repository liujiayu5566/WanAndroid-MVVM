package com.msb.search

import com.castiel.common.AppManager
import com.castiel.common.BaseApp

class MyApp : BaseApp() {

    override fun onCreate() {
        super.onCreate()
        AppManager.instance.init(this)
    }
}