package com.castiel.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.castiel.home.bean.HomeListData
import com.castiel.home.bean.HomeResponse
import com.castiel.home.databinding.ItemHomeListBinding

class HomeListAdapter : RecyclerView.Adapter<HomeListAdapter.ViewHolder>() {
    private val list: ArrayList<HomeListData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHomeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.model = list[position]
    }


    fun setDate(list: List<HomeListData>) {
        this.list.clear()
        this.list.addAll(list)
        notifyItemChanged(itemCount, list.size)
    }

    fun addDate(list: List<HomeListData>) {
        this.list.addAll(list)
        notifyItemChanged(itemCount, list.size)
    }

    inner class ViewHolder(var binding: ItemHomeListBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}