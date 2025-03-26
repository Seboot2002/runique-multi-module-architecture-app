package com.sebascamayo.auth.data

import kotlinx.serialization.Serializable

//Nos permite transformat clase a formato json

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String
)