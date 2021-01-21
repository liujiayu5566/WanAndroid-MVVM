package com.castiel.common.utils

import android.text.TextUtils
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.castiel.common.base.BaseAdapter

object DataBindingAdapter {

    @JvmStatic
    @BindingAdapter("url")
    fun imageViewUrl(
        imageView: AppCompatImageView,
        path: String,
    ) {
        if (TextUtils.isEmpty(path)) return
        Glide.with(imageView)
            .load(path)
            .into(imageView)
    }
}
