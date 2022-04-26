/*
 * *
 *  * Created by Rafsan Ahmad on 10/11/21, 6:51 PM
 *  * Copyright (c) 2021 . All rights reserved.
 *
 */

package com.rajith.payconiq.home.search.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rajith.payconiq.home.search.domain.model.User
import com.rajith.payconiq.home.search.util.Constants.Companion.GITHUB_STARTING_PAGE_INDEX
import com.rajith.payconiq.home.search.util.Constants.Companion.QUERY_PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException

class GithubPagingSource(
    private val service: SearchUserService,
    private val query: String
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        val apiQuery = query
        return try {
            val response = service.searchUser(apiQuery, position, params.loadSize)
            val repos = response.items
            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / QUERY_PAGE_SIZE)
            }
            LoadResult.Page(
                data = repos,
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}
