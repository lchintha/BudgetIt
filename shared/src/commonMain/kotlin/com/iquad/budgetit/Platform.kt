package com.iquad.budgetit

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform