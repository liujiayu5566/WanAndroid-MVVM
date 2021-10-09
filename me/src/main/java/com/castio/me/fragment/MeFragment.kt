package com.castio.me.fragment

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ClickUtils
import com.castio.common.AppManager
import com.castio.common.Constants
import com.castio.common.base.BaseListAdapter
import com.castio.common.base.BaseFragment
import com.castio.common.base.BaseViewModel
import com.castio.common.utils.MmkvWrap
import com.castio.common.utils.StatusBarUtil
import com.castio.me.R
import com.castio.me.adapter.MeHomeListAdapter
import com.castio.me.bean.MeItemModel
import com.castio.me.databinding.FragmentMeBinding
import com.google.android.material.appbar.AppBarLayout
import com.msb.module_common.bean.LoginResult
import kotlinx.android.synthetic.main.fragment_me.*
import kotlin.math.abs

class MeFragment : BaseFragment<FragmentMeBinding, BaseViewModel>(), View.OnClickListener {
    private val itemList = arrayListOf<MeItemModel>()
    private val meHomeAdapter = MeHomeListAdapter()
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
        ClickUtils.applyGlobalDebouncing(iv_avatar_big, this)
        //切换暗黑模式
//        itemList.add(MeItemModel(R.drawable.ic_launcher_foreground, "切换暗黑模式"))
        if (AppManager.instance.isLogin()) {
            itemList.add(MeItemModel(R.drawable.ic_launcher_foreground, "退出"))
        }

        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.adapter = meHomeAdapter

        meHomeAdapter.submitList(itemList)
        meHomeAdapter.clickListener = object : BaseListAdapter.OnItemClickListener<MeItemModel> {
            override fun onItemClick(view: View?, t: MeItemModel, position: Int) {
                when (t.title) {
                    "切换暗黑模式" -> {
                        if (context != null)
                            if (isNightMode(context!!)) {
                                // 关闭暗黑模式
                                viewModel.toast.value = "关闭暗黑模式"
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                            } else {
                                // 开启暗黑模式
                                viewModel.toast.value = "开启暗黑模式"
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                            }
                    }
                    "退出" -> {
                        AppManager.instance.logout()
                        changeUserData()
                        viewModel.toast.value = "退出成功"
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
        dataBinding.model = if (AppManager.instance.isLogin()) {
            itemList.map {
                if ("退出" == it.title) {
                    itemList.add(MeItemModel(R.drawable.ic_launcher_foreground, "退出"))
                    meHomeAdapter.submitList(itemList)
                    return
                }
            }
            MmkvWrap.instance.decodeParcelable(
                Constants.MMKV_LOGIN_RESULT,
                LoginResult::class.java,
                null
            )
        } else {
            itemList.map {
                if ("退出" == it.title) {
                    itemList.remove(it)
                    meHomeAdapter.submitList(itemList)
                    return
                }
            }
            null
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
            R.id.tv_username, R.id.iv_avatar_big -> {
                if (AppManager.instance.isLoginAndGoLgin()) {

                }
            }
            else -> {
            }
        }
    }

    override fun initObserver() {

    }


    //判断深色主题是否开启
    fun isNightMode(context: Context): Boolean {
        val currentNightMode: Int =
            context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }
}