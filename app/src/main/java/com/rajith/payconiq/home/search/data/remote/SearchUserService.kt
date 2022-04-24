package com.rajith.payconiq.home.search.data.remote

import com.rajith.payconiq.common.domain.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchUserService {

    @GET("search/users")
    suspend fun searchUser(@Query("q") query: String,
                           @Query("page")  pageNumber: String): UserResponse
}