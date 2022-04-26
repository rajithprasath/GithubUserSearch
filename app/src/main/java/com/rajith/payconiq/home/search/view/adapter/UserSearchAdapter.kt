package com.rajith.payconiq.home.search.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import com.rajith.core.ui.BaseViewHolder
import com.rajith.payconiq.databinding.ItemSearchUserBinding
import com.rajith.payconiq.home.search.domain.model.User
import com.rajith.payconiq.home.search.view.fragment.SearchUserFragmentDirections

class UserSearchAdapter :
    PagingDataAdapter<User, UserSearchAdapter.ViewHolder>(
        UsersDiffUtil
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindItem(it as User)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSearchUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }


    class ViewHolder(val binding: ItemSearchUserBinding) :
        BaseViewHolder<User>(binding.root) {
        override fun bindItem(item: User?) {
            binding.user = item
            binding.viewHolder = this
            onItemClick(item)
            binding.executePendingBindings()
        }

        private fun onItemClick(item: User?) {
            binding.clickListener = View.OnClickListener {
                item?.let { user ->
                    val destination =
                        SearchUserFragmentDirections
                            .navToUserDetailFragment(
                                item.login
                            )
                    it.findNavController().navigate(destination)
                }
            }
        }

    }

}