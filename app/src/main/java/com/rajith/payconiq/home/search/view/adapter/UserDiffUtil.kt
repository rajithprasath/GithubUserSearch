package com.rajith.payconiq.home.search.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.rajith.payconiq.home.search.domain.model.User

object UsersDiffUtil : DiffUtil.ItemCallback<User>() {


    override fun areItemsTheSame(
        oldItem: User,
        newItem: User
    ): Boolean = oldItem == newItem


    override fun areContentsTheSame(
        oldItem: User,
        newItem: User
    ): Boolean = oldItem.login == newItem.login

}