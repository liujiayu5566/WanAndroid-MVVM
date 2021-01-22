package com.castiel.home.fragment

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.castiel.common.base.BaseAdapter
import com.castiel.common.base.BaseFragment
import com.castiel.common.ui.WebActivity
import com.castiel.common.utils.StatusBarUtil
import com.castiel.common.widget.MultiStateView
import com.castiel.home.R
import com.castiel.home.adapter.BannerImageAdapter
import com.castiel.home.adapter.HomeListAdapter
import com.castiel.home.bean.BannerResult
import com.castiel.home.bean.HomeListData
import com.castiel.home.databinding.FragmentHomeBinding
import com.castiel.home.viewmodel.HomeViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.youth.banner.indicator.RectangleIndicator
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), View.OnClickListener {
    private var imageAdapter: BannerImageAdapter? = null
    private var homeListAdapter: HomeListAdapter? = null
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
        refreshlayout.setEnableOverScrollBounce(true)//是否启用越界回弹
        refreshlayout.setEnableAutoLoadMore(true)//是否启用列表惯性滑动到底部时自动加载更多
        refreshlayout.setEnableLoadMoreWhenContentNotFull(true)//是否在列表不满一页时候开启上拉加载功能
        refreshlayout.setEnableOverScrollDrag(true)//是否启用越界拖动（仿苹果效果）1.0.4
        refreshlayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                index++
                viewModel.netHomeList(index)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                index = 0
                viewModel.netHomeList(index)
                viewModel.netBanner()
            }

        })
        //banner
        viewModel.bannerResult.observe(
            this, Observer {
                if (imageAdapter == null) {
                    imageAdapter = BannerImageAdapter(it)
                    imageAdapter?.setOnBannerListener(OnBannerListener<BannerResult> { data, position ->
                        val url = data?.url
                        val intent = Intent(context, WebActivity::class.java)
                        intent.putExtra("url", url)
                        startActivity(intent)
                    })
                    banner.addBannerLifecycleObserver(this)//添加生命周期观察者
                        .setAdapter(imageAdapter).indicator = RectangleIndicator(context)
                } else imageAdapter?.setDatas(it)
                banner.start()
            }
        )

        recyclerview.itemAnimator = null
        viewModel.homeResponse.observe(
            this, Observer {
                if (homeListAdapter == null) {
                    homeListAdapter = HomeListAdapter()
                    recyclerview.layoutManager = LinearLayoutManager(context)
                    recyclerview.adapter = homeListAdapter
                    homeListAdapter?.clickListener =
                        object : BaseAdapter.OnItemClickListener<HomeListData> {
                            override fun onItemClick(
                                view: View?,
                                t: HomeListData,
                                position: Int
                            ) {
                                t.run {
                                    val intent = Intent(context, WebActivity::class.java)
                                    intent.putExtra("url", link)
                                    startActivity(intent)
                                }

                            }
                        }
                }

                homeListAdapter?.run {
                    submitList(it)
                }
                if (it.isEmpty()) viewModel.state.postValue(MultiStateView.ViewState.EMPTY)
            })

        viewModel.loading.observe(this, Observer {
            refreshlayout.finishRefresh()
            refreshlayout.finishLoadMore()
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
            toolbar
        )
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fragment_search_ll -> {//搜索
                ARouter.getInstance().build("/activity/searchActivity").navigation()
            }
            else -> {
            }
        }
    }
}