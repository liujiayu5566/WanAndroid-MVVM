package com.castiel.sort

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.castiel.sort.fragment.SortFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.add(R.id.c_lyout, SortFragment()).commit()
    }
}