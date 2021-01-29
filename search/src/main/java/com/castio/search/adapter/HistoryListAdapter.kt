package com.castio.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.castio.common.base.BaseListAdapter
import com.castio.search.databinding.ItemHistoryTagBinding

class HistoryListAdapter : BaseListAdapter<String, HistoryListAdapter.ViewHolder>() {

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