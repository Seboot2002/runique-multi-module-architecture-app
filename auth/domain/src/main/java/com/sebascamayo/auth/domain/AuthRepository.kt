package com.sebascamayo.auth.domain

import com.sebascamayo.core.domain.util.DataError
import com.sebascamayo.core.domain.util.EmptyResult

interface AuthRepository {

    suspend fun login(email: String, password: String): EmptyResult<DataError.Network>
    suspend fun register(email: String, password: String): EmptyResult<DataError.Network>
}