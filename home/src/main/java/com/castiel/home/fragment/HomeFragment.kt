package com.castiel.home.fragment

import android.app.Activity
import androidx.lifecycle.Observer
import com.castiel.common.base.BaseFragment
import com.castiel.common.utils.StatusBarUtil
import com.castiel.home.BR
import com.castiel.home.R
import com.castiel.home.adapter.ImageAdapter
import com.castiel.home.databinding.MainDataBinding
import com.castiel.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<MainDataBinding, HomeViewModel>() {
    private var imageAdapter: ImageAdapter? = null
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun initViewModelId(): Int {
        return BR.viewModel
    }

    override fun initView() {
        viewModel.bannerResponse.observe(
            this, Observer {
                if (imageAdapter == null) {
                    imageAdapter = ImageAdapter(it)
                    banner.adapter = imageAdapter
                } else imageAdapter?.setDatas(it)
                banner.start()
            }
        )
    }

    override fun initData() {
        viewModel.loading.postValue(true)
        viewModel.getBanner()
    }

    override fun setStatusBar() {
        StatusBarUtil.setTransparentForImageViewInFragment(
            context as Activity?,
            dataBinding.toolbar
        )
    }
}