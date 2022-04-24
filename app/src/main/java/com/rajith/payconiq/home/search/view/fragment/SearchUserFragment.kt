package com.rajith.payconiq.home.search.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.rajith.payconiq.databinding.FragmentSearchUserBinding
import com.rajith.payconiq.home.search.viewmodel.SearchUserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchUserFragment : Fragment() {

    private val searchUserViewModel: SearchUserViewModel by viewModels()
    private lateinit var binding: FragmentSearchUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchUserBinding.inflate(inflater, container, false)
        searchUser()
        return binding.root
    }

    private fun searchUser() {
        lifecycleScope.launch {
            searchUserViewModel.searchUser().collectLatest {
            }
        }
    }

}