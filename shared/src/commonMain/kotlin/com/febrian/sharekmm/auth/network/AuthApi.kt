package com.febrian.sharekmm.auth.network

import com.febrian.sharekmm.auth.data.AuthRequest
import com.febrian.sharekmm.auth.data.User
import com.febrian.sharekmm.response.ApiErrorResponse
import com.febrian.sharekmm.response.ApiResponse
import com.febrian.sharekmm.response.AuthState
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.kotlinx.serializer.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.*
import kotlinx.serialization.json.Json

class AuthApi {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    suspend fun register(email: String, password: String): AuthState {
        val data = client.post("http://192.168.1.5:3030/api/register") {
            contentType(ContentType.Application.Json)
            setBody(AuthRequest(email = email, password = password))
        }
        var authState: AuthState = AuthState.Loading
        when (data.status.value) {
            201 -> {
                authState = AuthState.Success(data.body())
            }
            404 -> {
                authState = AuthState.Error((data.body() as ApiErrorResponse).message.toString())
            }
        }

        return authState
    }

    suspend fun login(email: String, password: String): AuthState {
       val data = client.post("http://192.168.1.5:3030/api/login") {
           contentType(ContentType.Application.Json)
           setBody(AuthRequest(email = email, password = password))
        }

        var authState: AuthState = AuthState.Loading
        when (data.status.value) {
            200 -> {
                authState = AuthState.Success(data.body())
            }
            in 400..404 -> {
                authState = AuthState.Error((data.body() as ApiErrorResponse).message.toString())
            }
        }

        return authState
    }
}