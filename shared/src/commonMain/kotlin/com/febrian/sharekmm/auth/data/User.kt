package com.febrian.sharekmm.auth.data

@kotlinx.serialization.Serializable
data class User(
    var email : String?,
    var password:String?
)
