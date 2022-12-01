package com.febrian.sharekmm.auth

import com.febrian.sharekmm.auth.network.AuthApi
import com.febrian.sharekmm.response.ApiResponse
import com.febrian.sharekmm.response.AuthState

class AuthSdk {
    private val api = AuthApi()

    @Throws(Exception::class)
    suspend fun register(email: String, password: String): AuthState {
        return api.register(email, password)
    }

    suspend fun login(email: String, password: String): AuthState {
        return api.login(email, password)
    }
}