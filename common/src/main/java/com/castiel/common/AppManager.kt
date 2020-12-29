package com.castiel.common

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

class AppManager {
    private var context: Application? = null

    companion object {
        val instance: AppManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AppManager()
        }
    }

    fun init(context: Application?) {
        if (context == null)
            throw NullPointerException("Application isn't null")
        if (this.context != null) {
            throw Exception("AppManager Already initialized")
        }
        this.context = context
        if (true) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(context)
    }
}