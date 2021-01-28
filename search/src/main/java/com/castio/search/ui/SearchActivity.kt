package com.castio.search.ui

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.blankj.utilcode.util.ClickUtils
import com.castiel.common.NavigationConstants
import com.castio.common.Constants
import com.castio.common.base.BaseActivity
import com.castio.common.base.BaseAdapter
import com.castio.common.decoration.RecyclerItemDecoration
import com.castio.common.utils.MmkvWrap
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.castio.search.R
import com.castio.search.adapter.HistoryAdapter
import com.castio.search.databinding.ActivitySearchBinding
import com.castio.search.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.layout_search_input.*
import java.lang.reflect.Type


@Route(path = NavigationConstants.NAVIGATION_SEARCHACTIVITY)
class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>(),
    View.OnClickListener {

    private var historyAdapter: HistoryAdapter? = null

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
        historyAdapter = HistoryAdapter()
        val recyclerItemDecoration = RecyclerItemDecoration(this, 10f)
        val chipsLayoutManager = ChipsLayoutManager.newBuilder(this)
            .build()
        chips_recycler.itemAnimator = null
        chips_recycler.layoutManager = chipsLayoutManager
        chips_recycler.adapter = historyAdapter
        chips_recycler.addItemDecoration(recyclerItemDecoration)
        historyAdapter?.submitList(historyList)
        historyAdapter?.clickListener = object : BaseAdapter.OnItemClickListener<String> {
            override fun onItemClick(view: View?, t: String, position: Int) {
                goResultActivity(t)
            }
        }


    }

    override fun initData() {
        viewModel.netSearchHot()
    }

    override fun initObserver() {
        viewModel.searchHotResult.observe(this, Observer {

        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.tv_search -> {
                dataBinding.searchText?.run {
                    goResultActivity(this)

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

    //跳转结果页
    private fun goResultActivity(search: String) {
        val intent = Intent(this@SearchActivity, SearchResultActivity::class.java)
        intent.putExtra("search", search)
        startActivity(intent)
        finish()
    }
}