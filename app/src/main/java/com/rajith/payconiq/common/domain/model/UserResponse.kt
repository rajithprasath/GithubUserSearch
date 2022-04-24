package com.rajith.payconiq.common.domain.model

data class UserResponse(
    val incomplete_results: Boolean,
    val items: List<User>,
    val total_count: Int
)