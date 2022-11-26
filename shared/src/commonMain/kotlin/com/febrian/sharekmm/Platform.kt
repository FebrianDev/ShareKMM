package com.febrian.sharekmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform