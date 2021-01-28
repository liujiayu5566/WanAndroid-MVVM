package com.msb.problem.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.castio.common.base.BaseAdapter
import com.msb.problem.bean.ProblemData
import com.msb.problem.databinding.ItemProblemListBinding

class ProblemAdapter : BaseAdapter<ProblemData, ProblemAdapter.ViewHolder>() {
    override fun onBindViewHolderModel(holder: ProblemAdapter.ViewHolder, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProblemAdapter.ViewHolder {
        val binding =
            ItemProblemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemProblemListBinding) :
        RecyclerView.ViewHolder(binding.root)
}