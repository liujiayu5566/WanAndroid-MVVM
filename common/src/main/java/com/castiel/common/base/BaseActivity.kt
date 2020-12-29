package com.castiel.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.castiel.common.R
import com.castiel.common.utils.StatusBarUtil
import com.castiel.common.utils.ToastUtils


abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel> :
    AppCompatActivity() {
    protected lateinit var dataBinding: V
    protected val viewModel: VM by lazy { ViewModelProvider(this)[this.initViewModel()] }


    protected abstract fun initViewModel(): Class<VM>
    protected abstract fun initViewModelId(): Int?
    protected abstract fun getLayout(): Int
    protected abstract fun initData()
    protected abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, getLayout())
        dataBinding.lifecycleOwner = this
        ARouter.getInstance().inject(this)
        val initViewModelId = initViewModelId()
        initViewModelId?.let { dataBinding.setVariable(initViewModelId, viewModel) }
        addObserver()
        initView()
        initData()
        setStatusBar()
    }

    protected open fun setStatusBar() {

    }

    private fun addObserver() {
        viewModel.toast.observe(this, Observer {
            showToast(it)
        })
        viewModel.loading.observe(this, Observer {
            if (it) {

            } else {

            }
        })
    }

    private fun showToast(msg: String?) {
        msg?.let { ToastUtils.showToast(this, msg) }
    }

}