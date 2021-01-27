package com.castio.home.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.castio.home.bean.BannerResult
import com.youth.banner.adapter.BannerAdapter


class BannerImageAdapter(list: List<BannerResult>) :
    BannerAdapter<BannerResult, BannerImageAdapter.BannerViewHolder>(list) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder? {
        val imageView = ImageView(parent.context)
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    override fun onBindView(
        holder: BannerViewHolder,
        data: BannerResult,
        position: Int,
        size: Int
    ) {
        Glide
            .with(holder.imageView)
            .load(data.imagePath)
            .centerCrop()
            .into(holder.imageView)
    }

    inner class BannerViewHolder(view: ImageView) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view
    }
}