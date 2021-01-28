package com.msb.problem.fragment

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.castio.common.base.BaseFragment
import com.castio.common.base.BaseViewModel
import com.msb.problem.R
import com.msb.problem.adapter.ProblemAdapter
import com.msb.problem.databinding.FragmentProblemBinding
import com.msb.problem.viewmodel.ProblemViewModel
import kotlinx.android.synthetic.main.fragment_problem.*


class ProblemFragment : BaseFragment<FragmentProblemBinding, ProblemViewModel>(),
    View.OnClickListener {
    private var problemAdapter: ProblemAdapter? = null

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
        recycler.layoutManager = LinearLayoutManager(context)
        problemAdapter = ProblemAdapter()
        recycler.adapter = problemAdapter
    }

    override fun initData() {

    }

    override fun initObserver() {
        viewModel.problemReslutList.observe(this, Observer {
            problemAdapter?.submitList(it)
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
        }
    }
}