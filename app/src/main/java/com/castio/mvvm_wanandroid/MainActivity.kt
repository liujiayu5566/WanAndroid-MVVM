package com.castio.mvvm_wanandroid

import android.view.View
import android.widget.LinearLayout
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.castiel.common.NavigationConstants
import com.castio.common.base.BaseActivity
import com.castio.common.base.BaseViewModel
import com.castio.common.base.IFragmentProvider
import com.castio.common.utils.StatusBarUtil
import com.castio.mvvm_wanandroid.adapter.FragmentAdapter
import com.castio.mvvm_wanandroid.databinding.LayoutNavigationButtonBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<ViewDataBinding, BaseViewModel>(), View.OnClickListener {
    private val fragments: ArrayList<Fragment> = ArrayList()

    @JvmField
    @Autowired(name = NavigationConstants.NAVIGATION_HOMEFRAGMENTPROVIDER)
    var homeFragmentProviderImpl: IFragmentProvider? = null

    @JvmField
    @Autowired(name = NavigationConstants.NAVIGATION_PROBLEMFRAGMENTPROVIDERIMPL)
    var problemFragmentProviderImpl: IFragmentProvider? = null


    @JvmField
    @Autowired(name = NavigationConstants.NAVIGATION_USERFRAGMENTPROVIDER)
    var meFragmentProviderImpl: IFragmentProvider? = null

    override fun initView() {
        //首页
        homeFragmentProviderImpl?.run {
            addFragment(
                R.drawable.navigation_home_icon,
                R.string.navigation_home,
                R.id.navigation_home
            )
        }
        //问答
        problemFragmentProviderImpl?.run {
            addFragment(
                R.drawable.navigation_problem_icon,
                R.string.navigation_problem,
                R.id.navigation_problem
            )
        }
        //我的
        meFragmentProviderImpl?.run {
            addFragment(
                R.drawable.navigation_user_icon,
                R.string.navigation_me,
                R.id.navigation_me
            )
        }

        val childView: View = vp.getChildAt(0)
        (childView as? RecyclerView)?.overScrollMode = View.OVER_SCROLL_NEVER
        //禁止viewpager滑动
        vp.isUserInputEnabled = false
        vp.offscreenPageLimit = 1
        vp.adapter = FragmentAdapter(this, fragments)
    }

    /**
     * addFragment
     */
    private fun IFragmentProvider.addFragment(image: Int, text: Int, id: Int): Boolean {
        val binding = LayoutNavigationButtonBinding.inflate(layoutInflater)
        binding.ivNavigation.setImageResource(image)
        binding.tvNavigation.text = getString(text)
        binding.root.id = id

        addNavigationButton(binding.root)

        return fragments.add(this.getFragment())
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


    override fun initObserver() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.navigation_home, R.id.navigation_problem, R.id.navigation_me -> {//navigation
                val index = v.tag as Int
                changeNavigationButton(v, index)
            }
            else -> {

            }
        }
    }

    private fun changeNavigationButton(v: View, index: Int) {
        val childCount = bottom_navigation.childCount
        for (i in 0 until childCount) {
            val view = bottom_navigation.getChildAt(i)
            view.isSelected = false
        }
        v.isSelected = true
        vp.setCurrentItem(index, false)
    }

    override fun setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, null)
    }


}