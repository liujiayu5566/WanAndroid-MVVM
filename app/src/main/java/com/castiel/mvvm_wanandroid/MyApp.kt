package com.castiel.mvvm_wanandroid

import com.castiel.common.AppManager
import com.castiel.common.BaseApp

class MyApp : BaseApp() {

    override fun onCreate() {
        super.onCreate()
        AppManager.instance.init(this)
    }
}