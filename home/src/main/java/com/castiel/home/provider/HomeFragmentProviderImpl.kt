package com.castiel.home.provider

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.template.IProvider
import com.castiel.common.base.IFragmentProvider
import com.castiel.home.fragment.HomeFragment

@Route(path = "/home/HomeFragmentProviderImpl")
class HomeFragmentProviderImpl : IFragmentProvider {
    override fun init(context: Context?) {

    }

    override fun getFragment(): Fragment {
        return HomeFragment()
    }
}