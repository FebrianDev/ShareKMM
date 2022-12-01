package com.febrian.sharekmm.response

import com.febrian.sharekmm.auth.data.AuthResponse

sealed class AuthState {
    data class Success(val data: ApiResponse<AuthResponse>) : AuthState()
    data class Error(val message: String) : AuthState()
    object Loading : AuthState()
}