package com.castio.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.castio.common.base.BaseListAdapter
import com.castio.search.bean.SearchResultListData
import com.castio.search.databinding.ItemSearchResultListBinding

class SearchResultListAdapter : BaseListAdapter<SearchResultListData, SearchResultListAdapter.ViewHolder>() {

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