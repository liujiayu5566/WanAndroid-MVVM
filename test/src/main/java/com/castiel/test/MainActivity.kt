package com.castiel.test

import android.annotation.TargetApi
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.castiel.common.base.BaseActivity
import com.castiel.test.databinding.MainDataBinding

class MainActivity : BaseActivity<MainViewModel, MainDataBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel?.getBanner()
    }

    override fun initViewModel(): Class<MainViewModel>? {
        return MainViewModel::class.java
    }

    override fun initViewDataBinding(): MainDataBinding? {
        return DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
    }
}