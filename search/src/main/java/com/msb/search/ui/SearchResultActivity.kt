package com.msb.search.ui

import android.content.Intent
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ClickUtils
import com.castiel.common.Constants
import com.castiel.common.base.BaseActivity
import com.castiel.common.base.BaseAdapter
import com.castiel.common.ui.WebActivity
import com.castiel.common.utils.MmkvWrap
import com.castiel.common.widget.MultiStateView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.msb.search.R
import com.msb.search.adapter.SearchResultAdapter
import com.msb.search.bean.SearchResultListData
import com.msb.search.databinding.ActivitySearchResultBinding
import com.msb.search.viewmodel.SearchResultModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.layout_search_input.*
import java.lang.reflect.Type

class SearchResultActivity : BaseActivity<ActivitySearchResultBinding, SearchResultModel>(),
    View.OnClickListener {
    private var adapter: SearchResultAdapter? = null
    private var index: Int = 0


    override fun initViewModel(): Class<SearchResultModel> {
        return SearchResultModel::class.java
    }

    override fun initViewModelId(): Int? {
        return null
    }

    override fun getLayout(): Int {
        return R.layout.activity_search_result
    }

    override fun initView() {
        val search = intent.getStringExtra("search") ?: ""
        dataBinding.onClickListener = this
        dataBinding.searchText = search
        //防连点
        ClickUtils.applyGlobalDebouncing(tv_search, dataBinding.onClickListener)

        viewModel.loading.observe(this, Observer {
            refreshlayout.finishRefresh()
            refreshlayout.finishLoadMore()
        })

        refreshlayout.setEnableOverScrollBounce(true)//是否启用越界回弹
        refreshlayout.setEnableAutoLoadMore(true)//是否启用列表惯性滑动到底部时自动加载更多
        refreshlayout.setEnableLoadMoreWhenContentNotFull(true)//是否在列表不满一页时候开启上拉加载功能
        refreshlayout.setEnableOverScrollDrag(true)//是否启用越界拖动（仿苹果效果）1.0.4
        refreshlayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                index++
                viewModel.netSearch(index, dataBinding.searchText.toString())
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                index = 0
                viewModel.netSearch(index, dataBinding.searchText.toString())
            }

        })

        recyclerview.itemAnimator = null
        viewModel.searchResult.observe(this, Observer {
            if (adapter == null) {
                adapter = SearchResultAdapter()
                recyclerview.layoutManager = LinearLayoutManager(this)
                recyclerview.adapter = adapter
                adapter?.clickListener =
                    object : BaseAdapter.OnItemClickListener<SearchResultListData> {
                        override fun onItemClick(
                            view: View?,
                            t: SearchResultListData,
                            position: Int
                        ) {
                            t.run {
                                val intent =
                                    Intent(this@SearchResultActivity, WebActivity::class.java)
                                intent.putExtra("url", link)
                                startActivity(intent)
                            }

                        }
                    }
            }
            adapter?.submitList(it)
            if (it.isEmpty()) {
                viewModel.state.postValue(MultiStateView.ViewState.EMPTY)
            }
        })
    }

    override fun initData() {
        viewModel.netSearch(0, dataBinding.searchText!!)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.tv_search -> {
                dataBinding.searchText?.run {
                    viewModel.netSearch(0, this)
                    //存储历史记录
                    val history = MmkvWrap.instance.decodeString(Constants.MMKV_SEARCH_HISTROY, "")
                    val historyList: ArrayList<String> = if (TextUtils.isEmpty(history)) {
                        arrayListOf()
                    } else {
                        val type: Type = object : TypeToken<ArrayList<String>?>() {}.type
                        Gson().fromJson(history, type)
                    }
                    if (!historyList.contains(this)) {
                        historyList.add(0, this)
                        if (historyList.size > 10) historyList.removeAt(historyList.size - 1)
                        MmkvWrap.instance.encode(
                            Constants.MMKV_SEARCH_HISTROY,
                            Gson().toJson(historyList)
                        )
                    }
                }
            }
        }
    }
}