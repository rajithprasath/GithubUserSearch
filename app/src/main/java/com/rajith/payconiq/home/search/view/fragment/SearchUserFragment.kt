package com.rajith.payconiq.home.search.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.rajith.core.ui.loading.LoadingStateAdapter
import com.rajith.core.utils.NetworkUtils
import com.rajith.payconiq.R
import com.rajith.payconiq.databinding.FragmentSearchUserBinding
import com.rajith.payconiq.home.MainActivity
import com.rajith.payconiq.home.search.util.Constants.Companion.MAX_SEARCH_LIMIT_REACHED_ERROR_CODE
import com.rajith.payconiq.home.search.util.Constants.Companion.SEARCH_USER_TIME_DELAY
import com.rajith.payconiq.home.search.view.adapter.UserSearchAdapter
import com.rajith.payconiq.home.search.viewmodel.SearchUserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_user.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchUserFragment : Fragment() {

    private val searchUserViewModel: SearchUserViewModel by viewModels()
    private lateinit var userSearchAdapter: UserSearchAdapter
    private lateinit var binding: FragmentSearchUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        configureStateListener()
        searchUser()
    }


    private fun searchUser() {

        var job: Job? = null
        etSearch?.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_USER_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        lifecycleScope.launch {
                            if (NetworkUtils.isNetworkConnected(activity as MainActivity)) {
                                searchUserViewModel.searchUser(editable.toString())
                                    .collectLatest { userResponse ->
                                        binding.tvPlaceholder.isVisible = false
                                        userSearchAdapter.submitData(userResponse)
                                    }
                            } else {
                                showError(getString(R.string.text_no_internet))
                            }
                        }
                    }
                }
            }
        }

    }


    private fun setupRecyclerView() {
        userSearchAdapter = UserSearchAdapter()
        rvUserSearch?.apply {
            adapter = userSearchAdapter.withLoadStateHeaderAndFooter(
                header = LoadingStateAdapter { userSearchAdapter.retry() },
                footer = LoadingStateAdapter { userSearchAdapter.retry() }
            )
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun configureStateListener() {
        userSearchAdapter.addLoadStateListener { loadState ->
            configureViews(loadState)

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                if (errorState.error.localizedMessage.contains(MAX_SEARCH_LIMIT_REACHED_ERROR_CODE)) {
                    showError(getString(R.string.text_max_limit_reached))
                } else {
                    showError(errorState.error.localizedMessage)
                    Toast.makeText(activity, errorState.error.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }
    }

    private fun configureViews(loadState: CombinedLoadStates) {
        when (loadState.source.refresh) {
            is LoadState.NotLoading -> {
                binding.rvUserSearch.isVisible = true
                binding.progressBar.isVisible = false
                if (userSearchAdapter.itemCount < 1) {
                    binding.retryButton.isVisible = true
                }
            }
            is LoadState.Loading -> {
                binding.progressBar.isVisible = true
                binding.retryButton.isVisible = false
                binding.tvPlaceholder.isVisible = false
            }
            is LoadState.Error -> {
                binding.rvUserSearch.isVisible = false
                binding.progressBar.isVisible = false
                binding.retryButton.isVisible = true
                binding.tvPlaceholder.isVisible = false
                showError(getString(R.string.text_max_limit_reached))
            }
        }
    }

    private fun showError(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_SHORT)
            .show()
    }


}