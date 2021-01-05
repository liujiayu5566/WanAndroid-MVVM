package com.castiel.me

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.castiel.me.fragment.MeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module_main)
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.add(R.id.c_lyout, MeFragment()).commit()
    }
}