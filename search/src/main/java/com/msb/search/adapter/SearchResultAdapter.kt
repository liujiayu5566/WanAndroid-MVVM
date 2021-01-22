package com.msb.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.castiel.common.base.BaseAdapter
import com.msb.search.bean.SearchResultListData
import com.msb.search.databinding.ItemSearchResultListBinding

class SearchResultAdapter : BaseAdapter<SearchResultListData, SearchResultAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSearchResultListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolderModel(holder: ViewHolder, position: Int) {
        holder.binding.model = getItem(position)
    }

    inner class ViewHolder(val binding: ItemSearchResultListBinding) :
        RecyclerView.ViewHolder(binding.root)
}