package com.castiel.common.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ClickUtils

abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder> :
    ListAdapter<T, VH>(DiffCallback<T>()) {

    var clickListener: OnItemClickListener<T>? = null
    var clickLongListener: OnItemLongClickListener<T>? = null

    companion object {
        class DiffCallback<T> : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        clickListener?.run {
            ClickUtils.applyGlobalDebouncing(holder.itemView) {
                onItemClick(it, getItem(position), position)
            }
        }
        clickLongListener?.run {
            ClickUtils.applyGlobalDebouncing(holder.itemView) {
                onItemLongClick(it, getItem(position), position)
            }
        }
    }


    interface OnItemClickListener<T> {
        fun onItemClick(view: View?, t: T, position: Int)
    }

    interface OnItemLongClickListener<T> {
        fun onItemLongClick(view: View?, t: T, position: Int)
    }
}