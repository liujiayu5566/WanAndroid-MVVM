package com.castiel.home.fragment

import android.app.Activity
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.castiel.common.base.BaseFragment
import com.castiel.common.utils.StatusBarUtil
import com.castiel.home.BR
import com.castiel.home.R
import com.castiel.home.adapter.BannerImageAdapter
import com.castiel.home.adapter.HomeListAdapter
import com.castiel.home.databinding.MainDataBinding
import com.castiel.home.viewmodel.HomeViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<MainDataBinding, HomeViewModel>() {
    private var imageAdapter: BannerImageAdapter? = null
    private var homeListAdapter: HomeListAdapter? = null
    private var index = 0

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
        dataBinding.refreshlayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                index++
                viewModel.netHomeList(index)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                index = 0
                viewModel.netHomeList(index)
            }

        })


        viewModel.bannerResponse.observe(
            this, Observer {
                if (imageAdapter == null) {
                    imageAdapter = BannerImageAdapter(it)
                    banner.adapter = imageAdapter
                } else imageAdapter?.setDatas(it)
                banner.start()
            }
        )

        viewModel.homeResponse.observe(
            this, Observer {
                if (homeListAdapter == null) {
                    homeListAdapter = HomeListAdapter()
                    dataBinding.recyclerview.layoutManager = LinearLayoutManager(context)
                    dataBinding.recyclerview.adapter = homeListAdapter
                }
                if (index == 0)
                    homeListAdapter?.setDate(it)
                else homeListAdapter?.addDate(it)
            }
        )

        viewModel.loading.observe(this, Observer {
            dataBinding.refreshlayout.finishRefresh()
            dataBinding.refreshlayout.finishLoadMore()
        })
    }

    override fun initData() {
        viewModel.loading.postValue(true)
        viewModel.netBanner()
        viewModel.netHomeList(index)
    }

    override fun setStatusBar() {
        StatusBarUtil.setTransparentForImageViewInFragment(
            context as Activity?,
            dataBinding.toolbar
        )
    }
}