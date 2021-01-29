package com.castio.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.castio.common.base.BaseListAdapter
import com.castio.home.bean.HomeListData
import com.castio.home.databinding.ItemHomeListBinding

class HomeListListAdapter : BaseListAdapter<HomeListData, HomeListListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHomeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolderModel(holder: ViewHolder, position: Int) {
        holder.binding.model = getItem(position)
    }

    inner class ViewHolder(var binding: ItemHomeListBinding) : RecyclerView.ViewHolder(binding.root)
}