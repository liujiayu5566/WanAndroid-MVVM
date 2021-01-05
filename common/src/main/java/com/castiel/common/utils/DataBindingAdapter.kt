package com.castiel.common.utils

import android.text.TextUtils
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

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
