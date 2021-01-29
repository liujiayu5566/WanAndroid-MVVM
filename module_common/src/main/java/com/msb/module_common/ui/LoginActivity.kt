package com.msb.module_common.ui

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.castiel.common.NavigationConstants
import com.castio.common.Constants
import com.castio.common.base.BaseActivity
import com.castio.common.utils.MmkvWrap
import com.msb.module_common.R
import com.msb.module_common.databinding.ActivityLoginBinding
import com.msb.module_common.viewmodel.LoginVideModel
import kotlinx.android.synthetic.main.activity_login.*

@Route(path = NavigationConstants.NAVIGATION_LOGINACTIVITY)
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginVideModel>(), View.OnClickListener {
    private val REQUEST_REGISTER_CODE: Int = 1

    override fun initViewModel(): Class<LoginVideModel> {
        return LoginVideModel::class.java
    }

    override fun initViewModelId(): Int? {
        return null
    }

    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        dataBinding.onClickListener = this
        //防连点
        ClickUtils.applyGlobalDebouncing(btn_login, this)
        ClickUtils.applyGlobalDebouncing(tv_register, this)
        ClickUtils.applyGlobalDebouncing(tv_forget, this)
    }

    override fun initData() {

    }

    override fun initObserver() {
        viewModel.loginResult.observe(this, Observer {
            MmkvWrap.instance.encode(Constants.MMKV_LOGIN_RESULT, it)
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
            R.id.btn_login -> {
                KeyboardUtils.hideSoftInput(this)
                closeFocus()
                viewModel.netLogin(
                    dataBinding.username.toString(), dataBinding.password.toString()
                )
            }
            R.id.tv_register -> {
                startActivityForResult(
                    Intent(this@LoginActivity, RegisterActivity::class.java),
                    REQUEST_REGISTER_CODE
                )
            }
            R.id.tv_forget -> {
                viewModel.toast.postValue("暂无找回密码功能(无相关接口)")
            }
        }
    }

    private fun closeFocus() {
        et_admin.clearFocus()
        et_password.clearFocus()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_REGISTER_CODE -> {
                    finish()
                }
                else -> {
                }
            }
        }
    }
}