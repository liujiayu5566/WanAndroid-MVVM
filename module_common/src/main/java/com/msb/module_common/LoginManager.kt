package com.msb.module_common

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.castiel.common.NavigationConstants
import com.castio.common.Constants
import com.castio.common.base.ILoginManagerProvider
import com.castio.common.utils.MmkvWrap
import com.msb.module_common.bean.LoginResult

@Route(path = NavigationConstants.NAVIGATION_LOGINMANAGER)
class LoginManager : ILoginManagerProvider {
    override fun init(context: Context?) {

    }

    override fun isLogin(): Boolean {
        val loginResult = MmkvWrap.instance.decodeParcelable(
            Constants.MMKV_LOGIN_RESULT,
            LoginResult::class.java,
            null
        )
        return loginResult != null
    }

    override fun goLogin() {
        ARouter.getInstance().build(NavigationConstants.NAVIGATION_LOGINACTIVITY).navigation()
    }


    override fun logout() {
        MmkvWrap.instance.remove(Constants.MMKV_LOGIN_RESULT)
    }
}