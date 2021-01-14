package com.castiel.me.fragment

import androidx.databinding.ViewDataBinding
import com.castiel.common.base.BaseFragment
import com.castiel.common.base.BaseViewModel
import com.castiel.me.R
import com.castiel.me.databinding.FragmentMeBinding

class MeFragment : BaseFragment<FragmentMeBinding, BaseViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_me
    }

    override fun initViewModel(): Class<BaseViewModel> {
        return BaseViewModel::class.java
    }

    override fun initViewModelId(): Int? {
        return null
    }

    override fun initView() {
    }


    override fun initData() {
        viewModel.toast.value = "MeFragment"
    }

}