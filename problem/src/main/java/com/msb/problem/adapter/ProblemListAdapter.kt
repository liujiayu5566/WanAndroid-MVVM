package com.msb.problem.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.castio.common.base.BaseListAdapter
import com.msb.problem.bean.ProblemListData
import com.msb.problem.databinding.ItemProblemListBinding

class ProblemListAdapter :
    BaseListAdapter<ProblemListData, ProblemListAdapter.ViewHolder>() {

    override fun onBindViewHolderModel(holder: ProblemListAdapter.ViewHolder, position: Int) {
        holder.binding.model = getItem(position)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProblemListAdapter.ViewHolder {
        val binding =
            ItemProblemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemProblemListBinding) :
        RecyclerView.ViewHolder(binding.root)
}