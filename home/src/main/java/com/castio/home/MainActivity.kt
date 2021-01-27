package com.castio.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.castio.home.fragment.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_main)
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.add(R.id.c_lyout, HomeFragment()).commit()
    }
}