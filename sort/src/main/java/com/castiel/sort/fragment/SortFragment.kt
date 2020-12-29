package com.castiel.sort.fragment

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.castiel.common.base.BaseFragment
import com.castiel.common.base.BaseViewModel
import com.castiel.sort.R

class SortFragment : BaseFragment<ViewDataBinding, BaseViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_sort
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
        viewModel.toast.value = "SortFragment"
    }

}