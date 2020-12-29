package com.castiel.mvvm_wanandroid

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.castiel.common.base.BaseActivity
import com.castiel.common.base.BaseViewModel
import com.castiel.common.base.IFragmentProvider
import com.castiel.mvvm_wanandroid.adapter.FragmentAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ViewDataBinding, BaseViewModel>() {
    @JvmField
    @Autowired(name = "/home/HomeFragmentProviderImpl")
    var homeFragmentProviderImpl: IFragmentProvider? = null

    @JvmField
    @Autowired(name = "/sort/SortFragmentProviderImpl")
    var sortFragmentProviderImpl: IFragmentProvider? = null

    @JvmField
    @Autowired(name = "/me/MeFragmentProviderImpl")
    var meFragmentProviderImpl: IFragmentProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun initView() {
        val fragments = arrayOf(
            homeFragmentProviderImpl?.getFragment() ?: Fragment(),
            sortFragmentProviderImpl?.getFragment() ?: Fragment(),
            meFragmentProviderImpl?.getFragment() ?: Fragment()
        )

        vp.adapter = FragmentAdapter(this, fragments)
        vp.offscreenPageLimit = 1
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.page_home -> {
                    vp.currentItem = 0
                }
                R.id.page_list -> {
                    vp.currentItem = 1
                }
                R.id.page_user -> {
                    vp.currentItem = 2
                }
            }
            true
        }

    }


    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initViewModel(): Class<BaseViewModel> {
        return BaseViewModel::class.java
    }

    override fun initViewModelId(): Int? {
        return null
    }

    override fun initData() {
    }


}