package com.castiel.mvvm_wanandroid.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(
    activity: AppCompatActivity,
    fragments: ArrayList<Fragment>
) : FragmentStateAdapter(activity) {
    private val mFragments = fragments

    override fun getItemCount(): Int {
        return mFragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragments[position]
    }

}