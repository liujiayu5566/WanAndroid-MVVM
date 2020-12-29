package com.castiel.me.provider

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.castiel.common.base.IFragmentProvider
import com.castiel.me.fragment.MeFragment

@Route(path = "/me/MeFragmentProviderImpl")
class MeFragmentProviderImpl : IFragmentProvider {
    override fun init(context: Context?) {

    }

    override fun getFragment(): Fragment {
        return MeFragment()
    }
}