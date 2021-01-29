package com.castio.me.fragment

import android.app.Activity
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ClickUtils
import com.castio.common.AppManager
import com.castio.common.Constants
import com.castio.common.base.BaseListAdapter
import com.castio.common.base.BaseFragment
import com.castio.common.base.BaseViewModel
import com.castio.common.utils.MmkvWrap
import com.castio.common.utils.StatusBarUtil
import com.castio.login.bean.LoginResult
import com.castio.me.R
import com.castio.me.adapter.MeHomeListAdapter
import com.castio.me.bean.MeItemModel
import com.castio.me.databinding.FragmentMeBinding
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_me.*
import kotlin.math.abs

class MeFragment : BaseFragment<FragmentMeBinding, BaseViewModel>(), View.OnClickListener {

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
        ClickUtils.applyGlobalDebouncing(tv_username, this)
        ClickUtils.applyGlobalDebouncing(cardview_big, this)

        recyclerview.layoutManager = LinearLayoutManager(context)
        val meHomeAdapter = MeHomeListAdapter()
        recyclerview.adapter = meHomeAdapter
        val itemList = arrayListOf(
            MeItemModel(R.drawable.ic_launcher_foreground, "设置"),
            MeItemModel(R.drawable.ic_launcher_foreground, "退出"),
        )
        meHomeAdapter.submitList(itemList)
        meHomeAdapter.clickListener = object : BaseListAdapter.OnItemClickListener<MeItemModel> {
            override fun onItemClick(view: View?, t: MeItemModel, position: Int) {
                when (t.title) {
                    "设置" -> {
                        viewModel.toast.postValue("设置")
                    }
                    "退出" -> {
                        AppManager.instance.logout()
                        changeUserData()
                    }
                }
            }
        }
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            toolbar.alpha = abs(verticalOffset * 0.5f) / appBarLayout?.totalScrollRange!!
        })

    }

    override fun onResume() {
        super.onResume()
        changeUserData()
    }

    private fun changeUserData() {
        if (AppManager.instance.isLogin()) {
            dataBinding.model = MmkvWrap.instance.decodeParcelable(
                Constants.MMKV_LOGIN_RESULT,
                LoginResult::class.java,
                null
            )
        }
    }


    override fun initData() {

    }

    override fun setStatusBar() {
        StatusBarUtil.setTransparentForImageViewInFragment(
            context as Activity?,
            toolbar
        )
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_username, R.id.cardview_big -> {
                if (AppManager.instance.isLoginAndGoLgin()) {

                }
            }
            else -> {
            }
        }
    }

    override fun initObserver() {

    }

}