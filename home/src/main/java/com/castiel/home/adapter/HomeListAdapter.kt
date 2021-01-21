package com.castiel.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.castiel.common.base.BaseAdapter
import com.castiel.home.bean.HomeListData
import com.castiel.home.databinding.ItemHomeListBinding

class HomeListAdapter : BaseAdapter<HomeListData, HomeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHomeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.model = getItem(position)
    }

    inner class ViewHolder(var binding: ItemHomeListBinding) : RecyclerView.ViewHolder(binding.root)
}