package com.castio.search.ui

import android.content.Intent
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.blankj.utilcode.util.ClickUtils
import com.castiel.common.NavigationConstants
import com.castio.common.Constants
import com.castio.common.base.BaseActivity
import com.castio.common.base.BaseListAdapter
import com.castio.common.decoration.RecyclerItemDecoration
import com.castio.common.utils.MmkvWrap
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.castio.search.R
import com.castio.search.adapter.HistoryListAdapter
import com.castio.search.adapter.SearchResultListAdapter
import com.castio.search.bean.SearchResultListData
import com.castio.search.databinding.ActivitySearchBinding
import com.castio.search.viewmodel.SearchViewModel
import com.msb.module_common.ui.WebActivity
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.recyclerview
import kotlinx.android.synthetic.main.activity_search.refreshlayout
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.layout_search_input.*
import java.lang.reflect.Type


@Route(path = NavigationConstants.NAVIGATION_SEARCHACTIVITY)
class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>(),
    View.OnClickListener {
    private var index: Int = 0
    private val adapter: SearchResultListAdapter
        get() {
            return SearchResultListAdapter()
        }

    private var historyAdapter: HistoryListAdapter? = null

    override fun initViewModel(): Class<SearchViewModel> {
        return SearchViewModel::class.java
    }

    override fun initViewModelId(): Int? {
        return null
    }

    override fun getLayout(): Int {
        return R.layout.activity_search
    }

    override fun initView() {
        dataBinding.searchText = ""
        dataBinding.onClickListener = this
        ClickUtils.applyGlobalDebouncing(tv_search, this)
        //获取搜索历史
        val history = MmkvWrap.instance.decodeString(Constants.MMKV_SEARCH_HISTROY, "")
        val historyList: ArrayList<String> = if (TextUtils.isEmpty(history)) {
            arrayListOf()
        } else {
            val type: Type = object : TypeToken<ArrayList<String>?>() {}.type
            Gson().fromJson(history, type)
        }
        historyAdapter = HistoryListAdapter()
        val recyclerItemDecoration = RecyclerItemDecoration(this, 10f)
        val chipsLayoutManager = ChipsLayoutManager.newBuilder(this)
            .build()
        chips_recycler.itemAnimator = null
        chips_recycler.layoutManager = chipsLayoutManager
        chips_recycler.adapter = historyAdapter
        chips_recycler.addItemDecoration(recyclerItemDecoration)
        historyAdapter?.submitList(historyList)
        historyAdapter?.clickListener = object : BaseListAdapter.OnItemClickListener<String> {
            override fun onItemClick(view: View?, t: String, position: Int) {
                dataBinding.searchText = t
                serchData()
            }
        }

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
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter
        adapter.clickListener =
            object : BaseListAdapter.OnItemClickListener<SearchResultListData> {
                override fun onItemClick(
                    view: View?,
                    t: SearchResultListData,
                    position: Int
                ) {
                    t.run {
                        val intent =
                            Intent(this@SearchActivity, WebActivity::class.java)
                        intent.putExtra("url", link)
                        intent.putExtra("isShowLike", true)
                        startActivity(intent)
                    }

                }
            }


    }

    override fun initData() {
        viewModel.netSearchHot()
    }

    override fun initObserver() {
        viewModel.searchHotResult.observe(this, Observer {

        })

        viewModel.searchResult.observe(this, {
            adapter.submitList(it)
        })

        viewModel.loading.observe(this, {
            refreshlayout.finishRefresh()
            refreshlayout.finishLoadMore()
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.tv_search -> {
                serchData()
            }
        }
    }

    /**
     * 发送搜索请求
     */
    private fun serchData() {
        dataBinding.searchText?.run {
            if (TextUtils.isEmpty(this)) {
                dataBinding.refreshlayout.visibility = View.GONE
                return
            }
            viewModel.loading.value = true
            dataBinding.refreshlayout.visibility = View.VISIBLE
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
                historyAdapter?.submitList(historyList)
            }
        } ?: let {
            dataBinding.refreshlayout.visibility = View.GONE
        }
    }
}