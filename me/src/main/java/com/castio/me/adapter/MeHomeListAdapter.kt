package com.castio.me.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.castio.common.base.BaseListAdapter
import com.castio.me.bean.MeItemModel
import com.castio.me.databinding.ItemMeListBinding

class MeHomeListAdapter : BaseListAdapter<MeItemModel, MeHomeListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMeListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolderModel(holder: ViewHolder, position: Int) {
        holder.binding.model = getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
}