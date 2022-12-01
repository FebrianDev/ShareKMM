package com.febrian.sharekmm.response

import com.febrian.sharekmm.auth.data.AuthResponse
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    var code: Int? = null,
    var status: String? = null,
    var data: T? = null
)

@Serializable
data class ApiErrorResponse(
    var status: String? = null,
    var code: Int? = null,
    var message: String? = null
)