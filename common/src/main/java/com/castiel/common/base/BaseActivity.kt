package com.castiel.common.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.castiel.common.R
import com.castiel.common.dialog.LoadingDialog
import com.castiel.common.utils.StatusBarUtil
import com.castiel.common.utils.ToastUtils
import com.castiel.common.widget.MultiStateView


abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {
    protected lateinit var dataBinding: V
    protected val viewModel: VM by lazy { ViewModelProvider(this)[this.initViewModel()] }

    protected abstract fun initViewModel(): Class<VM>
    protected abstract fun initViewModelId(): Int?
    protected abstract fun getLayout(): Int
    protected abstract fun initView()
    protected abstract fun initData()
    private var loading: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;// 改为竖屏
        dataBinding = DataBindingUtil.setContentView(this, getLayout())
        dataBinding.lifecycleOwner = this
        ARouter.getInstance().inject(this)
        val initViewModelId = initViewModelId()
        initViewModelId?.let { dataBinding.setVariable(initViewModelId, viewModel) }
        loading = LoadingDialog(this)
        addObserver()
        initView()
        initData()
        setStatusBar()
    }

    protected open fun setStatusBar() {
    }

    private fun addObserver() {
        val stateView: MultiStateView? =
            dataBinding.root.findViewById(R.id.state_view)
        stateView?.let {
            stateView.getView(MultiStateView.ViewState.ERROR)?.findViewById<TextView>(R.id.retry)
                ?.setOnClickListener {
                    viewModel.toast.postValue("重试")
                    initData()
                }
            viewModel.state.observe(this, Observer {
                stateView.viewState = it
            })
        }
        viewModel.toast.observe(this, Observer {
            showToast(it)
        })
        viewModel.loading.observe(this, Observer {
            if (it) {
                loading?.show()
            } else {
                loading?.dismiss()
            }
        })
    }

    private fun showToast(msg: String?) {
        msg?.let { ToastUtils.showToast(this, msg) }
    }

    override fun onPause() {
        super.onPause()
        loading?.dismiss()
    }

}