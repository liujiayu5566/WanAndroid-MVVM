package com.castiel.home

import android.app.Application
import android.content.Context
import com.castiel.common.AppManager
import com.castiel.common.BaseApp
import com.castiel.common.widget.MsbRefreshHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator


class MyApp : BaseApp() {

    override fun onCreate() {
        super.onCreate()
        AppManager.instance.init(this)
    }
}