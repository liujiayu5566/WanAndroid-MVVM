package com.msb.problem.provider

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.castiel.common.NavigationConstants
import com.castio.common.base.IFragmentProvider
import com.msb.problem.fragment.ProblemFragment

@Route(path = NavigationConstants.NAVIGATION_PROBLEMFRAGMENTPROVIDERIMPL)
class ProblemFragmentProviderImpl : IFragmentProvider {
    override fun init(context: Context?) {

    }

    override fun getFragment(): Fragment {
        return ProblemFragment()
    }
}