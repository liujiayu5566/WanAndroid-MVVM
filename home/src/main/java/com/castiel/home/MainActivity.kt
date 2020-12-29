package com.castiel.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.castiel.home.fragment.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.add(R.id.c_lyout, HomeFragment()).commit()
    }
}