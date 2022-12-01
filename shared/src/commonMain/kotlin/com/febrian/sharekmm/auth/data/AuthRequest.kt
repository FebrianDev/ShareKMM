package com.febrian.sharekmm.auth.data

@kotlinx.serialization.Serializable
data class AuthRequest(
    var email: String?,
    var password: String?
)