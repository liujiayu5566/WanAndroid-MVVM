package com.castio.login.ui

import android.app.Activity
import android.view.View
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.castio.common.Constants
import com.castio.common.base.BaseActivity
import com.castio.common.utils.MmkvWrap
import com.castio.login.R
import com.castio.login.databinding.ActivityRegisterBinding
import com.castio.login.viewmodel.RegisterVideModel
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.et_admin
import kotlinx.android.synthetic.main.activity_register.et_password

class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterVideModel>(),
    View.OnClickListener {

    override fun initViewModel(): Class<RegisterVideModel> {
        return RegisterVideModel::class.java
    }

    override fun initViewModelId(): Int? {
        return null
    }

    override fun getLayout(): Int {
        return R.layout.activity_register
    }

    override fun initView() {
        dataBinding.onClickListener = this
        ClickUtils.applyGlobalDebouncing(btn_register, this)
    }

    override fun initData() {

    }

    override fun initObserver() {
        viewModel.loginResult.observe(this, Observer {
            MmkvWrap.instance.encode(Constants.MMKV_LOGIN_RESULT, it)
            setResult(Activity.RESULT_OK)
            finish()
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.layout_main -> {
                KeyboardUtils.hideSoftInput(this)
                closeFocus()
            }
            R.id.btn_register -> {
                KeyboardUtils.hideSoftInput(this)
                closeFocus()
                viewModel.netRsgister(
                    dataBinding.username.toString(),
                    dataBinding.password.toString()
                )
            }
        }
    }

    private fun closeFocus() {
        et_admin.clearFocus()
        et_password.clearFocus()
    }
}