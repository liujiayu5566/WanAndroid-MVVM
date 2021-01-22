package com.msb.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.castiel.common.base.BaseAdapter
import com.msb.search.databinding.ItemHistoryTagBinding

class HistoryAdapter : BaseAdapter<String, HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHistoryTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolderModel(holder: ViewHolder, position: Int) {
        holder.binding.tag = getItem(position)
    }

    inner class ViewHolder(var binding: ItemHistoryTagBinding) :
        RecyclerView.ViewHolder(binding.root)
}