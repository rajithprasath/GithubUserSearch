package com.rajith.payconiq.home.search.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rajith.payconiq.common.util.Constants.Companion.SEARCH_USER_TIME_DELAY
import com.rajith.payconiq.common.util.Resource
import com.rajith.payconiq.databinding.FragmentSearchUserBinding
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
                            searchUserViewModel.searchUser(editable.toString()).collectLatest {userResponse ->
                                when (userResponse) {
                                    is Resource.Success -> {
                                        progressBar.visibility =  View.GONE
                                        userSearchAdapter.differ.submitList(userResponse.data?.items)
                                    }
                                    is Resource.Error -> {
                                        progressBar.visibility =  View.GONE
                                        userResponse.message?.let { message ->

                                        }
                                    }
                                    is Resource.Loading -> {
                                    }
                                }
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
            adapter = userSearchAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


}