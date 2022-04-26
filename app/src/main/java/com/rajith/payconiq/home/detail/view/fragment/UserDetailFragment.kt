package com.rajith.payconiq.home.detail.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.rajith.payconiq.R
import com.rajith.payconiq.common.util.Resource
import com.rajith.payconiq.databinding.FragmentUserDetailBinding
import com.rajith.payconiq.home.detail.domain.model.UserInfo
import com.rajith.payconiq.home.detail.viewmodel.UserDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user_detail.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailFragment : Fragment() {

    private val userDetailViewModel: UserDetailViewModel by viewModels()
    private lateinit var binding: FragmentUserDetailBinding
    private val args: UserDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = args.login
        fetchUserDetails(username)
        detailToolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        ibFav.setOnClickListener {
            ibFav.setImageResource(R.drawable.ic_favorite)
        }
    }

    private fun fetchUserDetails(username: String) {
        lifecycleScope.launch {
            userDetailViewModel.getUserDetail(username).collectLatest { userInfoResponse ->
                when (userInfoResponse) {
                    is Resource.Success -> {
                        progressBar.visibility = View.GONE
                        userInfoResponse.data?.let { showUserDetails(it) }
                    }
                    is Resource.Error -> {
                        progressBar.visibility = View.GONE
                        userInfoResponse.message?.let { message ->

                        }
                    }
                    is Resource.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun showUserDetails(userInfo: UserInfo) {
        Glide.with(this)
            .load(userInfo.avatar_url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(RequestOptions.circleCropTransform())
            .into(ivAvatar)
        tbName.title = userInfo.name ?: userInfo.login

        if(userInfo.bio == null){
            lblBio.visibility = View.GONE
            tvBio.visibility = View.GONE
        }else{
            tvBio.text = userInfo.bio
        }

        tvUrl.text = userInfo.url
        val followerCount = getString(
            R.string.text_followers_and_followings,
            userInfo.followers?.toString(),
            userInfo.following?.toString()
        );
        tvFollowersAndFollowing.text = followerCount

        val reposCount = getString(
            R.string.text_repos_and_gists,
            userInfo.public_repos?.toString(),
            userInfo.public_gists?.toString()
        );
        tvReposAndGists.text = reposCount

        tvLocation.text = userInfo.location ?: getString(R.string.text_not_valid)
        tvEmail.text = userInfo.email ?: getString(R.string.text_not_valid)

        if(userInfo.twitter_username == null){
            ivTwitter.visibility = View.GONE
            tvTwiiter.visibility = View.GONE
        }else{
            tvTwiiter.text = userInfo.twitter_username
        }
    }


}