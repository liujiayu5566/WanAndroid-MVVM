package com.castio.home.fragment

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.castiel.common.NavigationConstants
import com.castio.common.base.BaseListAdapter
import com.castio.common.base.BaseFragment
import com.castio.common.utils.StatusBarUtil
import com.castio.common.widget.MultiStateView
import com.castio.home.R
import com.castio.home.adapter.BannerImageAdapter
import com.castio.home.adapter.HomeListListAdapter
import com.castio.home.bean.BannerResult
import com.castio.home.bean.HomeListData
import com.castio.home.databinding.FragmentHomeBinding
import com.castio.home.viewmodel.HomeViewModel
import com.msb.module_common.ui.WebActivity
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.youth.banner.indicator.RectangleIndicator
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), View.OnClickListener {
    private var imageAdapter: BannerImageAdapter? = null
    private var homeListAdapter: HomeListListAdapter? = null
    private var index = 0

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun initViewModelId(): Int? {
        return null
    }

    override fun initView() {
        dataBinding.onClickListener = this
        refreshlayout.setEnableOverScrollDrag(true)//是否启用越界拖动（仿苹果效果）1.0.4
        refreshlayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                index++
                viewModel.netHomeList(index)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                index = 0
                viewModel.netBanner()
                viewModel.netPlacedAndHomeList()
            }

        })

        recyclerview.itemAnimator = null
        homeListAdapter = HomeListListAdapter()
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.adapter = homeListAdapter
        homeListAdapter?.clickListener =
            object : BaseListAdapter.OnItemClickListener<HomeListData> {
                override fun onItemClick(
                    view: View?,
                    t: HomeListData,
                    position: Int
                ) {
                    t.run {
                        val intent = Intent(context, WebActivity::class.java)
                        intent.putExtra("url", link)
                        intent.putExtra("isShowLike", true)
                        startActivity(intent)
                    }

                }
            }
    }

    override fun initData() {
        viewModel.loading.value = true
        index = 0
        viewModel.netBanner()
        viewModel.netPlacedAndHomeList()
    }


    override fun initObserver() {
        //banner
        viewModel.bannerResult.observe(
            this, Observer {
                if (imageAdapter == null) {
                    imageAdapter = BannerImageAdapter(it)
                    imageAdapter?.setOnBannerListener(object : OnBannerListener<BannerResult> {
                        override fun OnBannerClick(data: BannerResult?, position: Int) {
                            val url = data?.url
                            val intent = Intent(context, WebActivity::class.java)
                            intent.putExtra("url", url)
                            startActivity(intent)
                        }
                    })
                    banner.addBannerLifecycleObserver(this)//添加生命周期观察者
                        .setAdapter(imageAdapter).indicator = RectangleIndicator(context)
                } else imageAdapter?.setDatas(it)
                banner.start()
            }
        )
        viewModel.homeResponse.observe(
            this, Observer {
                homeListAdapter?.submitList(it)
            })

        viewModel.loading.observe(this, Observer {
            refreshlayout.finishRefresh()
            refreshlayout.finishLoadMore()
        })
    }

    override fun setStatusBar() {
        context?.let {
            StatusBarUtil.setTransparentForImageViewInFragment(
                it as Activity,
                toolbar
            )
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fragment_search_ll -> {//搜索
                ARouter.getInstance().build(NavigationConstants.NAVIGATION_SEARCHACTIVITY)
                    .navigation()
            }
            else -> {
            }
        }
    }
}