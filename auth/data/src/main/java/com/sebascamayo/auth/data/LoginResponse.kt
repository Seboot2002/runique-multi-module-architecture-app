package com.sebascamayo.auth.data

import kotlinx.serialization.Serializable

@Serializable
class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpirationTimestamp: Long,
    val userId: String
)