package com.castiel.sort.provider

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.template.IProvider
import com.castiel.common.base.IFragmentProvider
import com.castiel.sort.fragment.SortFragment

@Route(path = "/sort/SortFragmentProviderImpl")
class SortFragmentProviderImpl : IFragmentProvider {
    override fun init(context: Context?) {

    }

    override fun getFragment(): Fragment {
        return SortFragment()
    }
}