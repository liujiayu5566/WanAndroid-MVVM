package com.msb.search.ui

import android.content.Intent
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.castiel.common.Constants
import com.castiel.common.base.BaseActivity
import com.castiel.common.base.BaseAdapter
import com.castiel.common.decoration.RecyclerItemDecoration
import com.castiel.common.utils.MmkvWrap
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.msb.search.R
import com.msb.search.adapter.HistoryAdapter
import com.msb.search.databinding.ActivitySearchBinding
import com.msb.search.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*
import java.lang.reflect.Type


@Route(path = "/activity/searchActivity")
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

        viewModel.searchHotResult.observe(this, Observer {

        })
    }

    override fun initData() {
        viewModel.netSearchHot()
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
//        finish()
    }
}