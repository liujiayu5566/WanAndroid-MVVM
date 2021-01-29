package com.msb.problem.fragment

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.castio.common.base.BaseFragment
import com.castio.common.base.BaseListAdapter
import com.castio.common.ui.WebActivity
import com.castio.common.utils.StatusBarUtil
import com.castio.common.widget.MultiStateView
import com.msb.problem.R
import com.msb.problem.adapter.ProblemListAdapter
import com.msb.problem.bean.ProblemListData
import com.msb.problem.databinding.FragmentProblemBinding
import com.msb.problem.viewmodel.ProblemViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_problem.*


class ProblemFragment : BaseFragment<FragmentProblemBinding, ProblemViewModel>(),
    View.OnClickListener {
    private var problemAdapter: ProblemListAdapter? = null
    private var index: Int = 0

    override fun getLayoutId(): Int {
        return R.layout.fragment_problem
    }

    override fun initViewModel(): Class<ProblemViewModel> {
        return ProblemViewModel::class.java
    }

    override fun initViewModelId(): Int? {
        return null
    }

    override fun initView() {
        recycler.itemAnimator = null
        recycler.layoutManager = LinearLayoutManager(context)
        problemAdapter = ProblemListAdapter()
        recycler.adapter = problemAdapter
        problemAdapter?.clickListener =
            object : BaseListAdapter.OnItemClickListener<ProblemListData> {
                override fun onItemClick(
                    view: View?,
                    t: ProblemListData,
                    position: Int
                ) {
                    t.run {
                        val intent = Intent(context, WebActivity::class.java)
                        intent.putExtra("url", link)
                        startActivity(intent)
                    }

                }
            }

        refreshlayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                index++
                viewModel.netProblemList(index)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                index = 0
                viewModel.netProblemList(index)
            }
        })
    }

    override fun initData() {
        viewModel.loading.postValue(true)
        index = 0
        viewModel.netProblemList(index)
    }

    override fun initObserver() {
        viewModel.problemReslutList.observe(this, Observer {
            problemAdapter?.submitList(it)
            if (it.isEmpty()) {
                viewModel.state.postValue(MultiStateView.ViewState.EMPTY)
            }
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
        }
    }
}