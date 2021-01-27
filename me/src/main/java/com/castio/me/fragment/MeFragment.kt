package com.castio.me.fragment

import android.app.Activity
import androidx.recyclerview.widget.LinearLayoutManager
import com.castio.common.base.BaseFragment
import com.castio.common.base.BaseViewModel
import com.castio.common.utils.StatusBarUtil
import com.castio.me.R
import com.castio.me.adapter.MeHomeAdapter
import com.castio.me.bean.MeItemModel
import com.castio.me.databinding.FragmentMeBinding
import kotlinx.android.synthetic.main.fragment_me.*

class MeFragment : BaseFragment<FragmentMeBinding, BaseViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_me
    }

    override fun initViewModel(): Class<BaseViewModel> {
        return BaseViewModel::class.java
    }

    override fun initViewModelId(): Int? {
        return null
    }

    override fun initView() {
        recyclerview.layoutManager = LinearLayoutManager(context)
        val meHomeAdapter = MeHomeAdapter()
        recyclerview.adapter = meHomeAdapter
        val itemList = arrayListOf(
            MeItemModel(R.drawable.ic_launcher_foreground, "设置"),
            MeItemModel(R.drawable.ic_launcher_foreground, "设置"),
            MeItemModel(R.drawable.ic_launcher_foreground, "设置"),
            MeItemModel(R.drawable.ic_launcher_foreground, "设置"),
            MeItemModel(R.drawable.ic_launcher_foreground, "设置"),
            MeItemModel(R.drawable.ic_launcher_foreground, "设置"),
            MeItemModel(R.drawable.ic_launcher_foreground, "设置"),
            MeItemModel(R.drawable.ic_launcher_foreground, "设置"),
            MeItemModel(R.drawable.ic_launcher_foreground, "设置"),
            MeItemModel(R.drawable.ic_launcher_foreground, "设置"),
            MeItemModel(R.drawable.ic_launcher_foreground, "设置"),
            MeItemModel(R.drawable.ic_launcher_foreground, "设置"),
            MeItemModel(R.drawable.ic_launcher_foreground, "设置"),
        )
        meHomeAdapter.submitList(itemList)
    }


    override fun initData() {
        
    }

    override fun setStatusBar() {
        StatusBarUtil.setTransparentForImageViewInFragment(
            context as Activity?,
            toolbar
        )
    }

}