package com.castio.me

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.castiel.common.NavigationConstants
import com.castio.common.base.ILoginManagerProvider
import com.castio.me.fragment.MeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module_main)
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.add(R.id.c_lyout, MeFragment()).commit()

        ARouter.getInstance().navigation(ILoginManagerProvider::class.java)?.run {
            if (!isLogin())
                goLogin()
        }
    }
}