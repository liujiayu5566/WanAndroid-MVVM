package com.castiel.mvvm_wanandroid

import android.view.View
import android.widget.LinearLayout
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.castiel.common.base.BaseActivity
import com.castiel.common.base.BaseViewModel
import com.castiel.common.base.IFragmentProvider
import com.castiel.mvvm_wanandroid.adapter.FragmentAdapter
import com.castiel.mvvm_wanandroid.databinding.LayoutNavigationButtonBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<ViewDataBinding, BaseViewModel>(), View.OnClickListener {
    @JvmField
    @Autowired(name = "/home/HomeFragmentProviderImpl")
    var homeFragmentProviderImpl: IFragmentProvider? = null

    @JvmField
    @Autowired(name = "/me/MeFragmentProviderImpl")
    var meFragmentProviderImpl: IFragmentProvider? = null

    override fun initView() {
        val fragments: ArrayList<Fragment> = ArrayList()
        //首页
        homeFragmentProviderImpl?.let {
            val binding = LayoutNavigationButtonBinding.inflate(layoutInflater)
            binding.ivNavigation.setImageResource(R.drawable.navigation_home_icon)
            binding.tvNavigation.text = getString(R.string.navigation_home)
            binding.root.id = R.id.navigation_home

            addNavigationButton(binding.root)

            fragments.add(it.getFragment())
        }
        //我的
        meFragmentProviderImpl?.let {
            val binding = LayoutNavigationButtonBinding.inflate(layoutInflater)
            binding.ivNavigation.setImageResource(R.drawable.navigation_user_icon)
            binding.tvNavigation.text = getString(R.string.navigation_me)
            binding.root.id = R.id.navigation_me

            addNavigationButton(binding.root)

            fragments.add(it.getFragment())
        }

        val childView: View = vp.getChildAt(0)
        (childView as? RecyclerView)?.overScrollMode = View.OVER_SCROLL_NEVER
        //禁止viewpager滑动
        vp.isUserInputEnabled = false
        vp.offscreenPageLimit = 1
        vp.adapter = FragmentAdapter(this, fragments)

    }

    /**
     * navigationView添加
     */
    private fun addNavigationButton(view: View) {
        view.setOnClickListener(this)
        if (bottom_navigation.childCount == 0) {
            view.isSelected = true
        }
        view.tag = bottom_navigation.childCount
        bottom_navigation.addView(view)
        val layoutParams = view.layoutParams as LinearLayout.LayoutParams
        layoutParams.weight = 1F
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.navigation_home, R.id.navigation_me -> {//navigation
                val childCount = bottom_navigation.childCount
                for (i in 0 until childCount) {
                    val view = bottom_navigation.getChildAt(i)
                    view.isSelected = false
                }
                v.isSelected = true
                vp.currentItem = v.tag as Int
            }
            else -> {

            }
        }
    }


}