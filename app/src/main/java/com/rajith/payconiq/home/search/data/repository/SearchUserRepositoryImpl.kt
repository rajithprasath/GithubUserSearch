package com.rajith.payconiq.home.search.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rajith.payconiq.home.search.data.remote.GithubPagingSource
import com.rajith.payconiq.home.search.data.remote.SearchUserService
import com.rajith.payconiq.home.search.domain.model.User
import com.rajith.payconiq.home.search.domain.repository.SearchUserRepository
import com.rajith.payconiq.home.search.util.Constants.Companion.QUERY_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUserRepositoryImpl @Inject constructor(
    private val api: SearchUserService
) : SearchUserRepository {

    override fun searchUser(searchQuery: String): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = QUERY_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GithubPagingSource(api, searchQuery) }
        ).flow
    }

}