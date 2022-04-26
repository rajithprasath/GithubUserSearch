package com.rajith.core.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.rajith.core.R
import java.util.*

object DataBindingAdapter {

    @JvmStatic
    @BindingAdapter("setImage")
    fun ImageView.setImage(relativeImageUrl: String?) {
        val requestOptions = RequestOptions().priority(Priority.IMMEDIATE)
            .placeholder(R.drawable.ic_place).circleCrop()

        Glide.with(context).load(relativeImageUrl)
            .apply(requestOptions)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("setUsername")
    fun TextView.setUsername(username: String?) {
        text = username?.replaceFirstChar { it.titlecase(Locale.getDefault()) }
    }
}