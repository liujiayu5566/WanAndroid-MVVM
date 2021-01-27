package com.castio.home.provider

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.castiel.common.NavigationConstants
import com.castio.common.base.IFragmentProvider
import com.castio.home.fragment.HomeFragment

@Route(path = NavigationConstants.NAVIGATION_HOMEFRAGMENTPROVIDER)
class HomeFragmentProviderImpl : IFragmentProvider {
    override fun init(context: Context?) {

    }

    override fun getFragment(): Fragment {
        return HomeFragment()
    }
}