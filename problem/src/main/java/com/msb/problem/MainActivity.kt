package com.msb.problem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.msb.problem.fragment.ProblemFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.add(R.id.c_lyout, ProblemFragment()).commit()
    }
}