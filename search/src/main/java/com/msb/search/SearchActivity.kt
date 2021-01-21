package com.msb.search

import android.view.View
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.castiel.common.base.BaseActivity
import com.msb.search.databinding.ActivitySearchBinding
import com.msb.search.viewmodel.SearchViewModel

@Route(path = "/activity/searchActivity")
class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>(),
    View.OnClickListener {

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
        viewModel.searchHotResponse.observe(this, Observer {

        })
        dataBinding.onClickListener = this
    }

    override fun initData() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.tv_search -> {
            }
        }
    }
}