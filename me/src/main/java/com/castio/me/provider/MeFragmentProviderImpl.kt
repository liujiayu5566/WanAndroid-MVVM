package com.castio.me.provider

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.castiel.common.NavigationConstants
import com.castio.common.base.IFragmentProvider
import com.castio.me.fragment.MeFragment

@Route(path = NavigationConstants.NAVIGATION_USERFRAGMENTPROVIDER)
class MeFragmentProviderImpl : IFragmentProvider {
    override fun init(context: Context?) {

    }

    override fun getFragment(): Fragment {
        return MeFragment()
    }
}