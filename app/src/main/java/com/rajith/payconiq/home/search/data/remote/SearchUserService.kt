package com.rajith.payconiq.home.search.data.remote

import com.rajith.payconiq.common.domain.model.UserResponse
import com.rajith.payconiq.common.util.Constants.Companion.QUERY_PAGE_SIZE
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchUserService {

    @GET("search/users")
    suspend fun searchUser(@Query("q") query: String,
                           @Query("page")  pageNumber: Int = 1,
                           @Query("per_page")  pageSize: Int = QUERY_PAGE_SIZE): UserResponse
}