package com.sebascamayo.auth.data

import com.sebascamayo.auth.domain.AuthRepository
import com.sebascamayo.core.data.networking.post
import com.sebascamayo.core.domain.AuthInfo
import com.sebascamayo.core.domain.SessionStorage
import com.sebascamayo.core.domain.util.DataError
import com.sebascamayo.core.domain.util.EmptyResult
import com.sebascamayo.core.domain.util.Result
import com.sebascamayo.core.domain.util.asEmptyDataResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage
): AuthRepository {

    override suspend fun login(email: String, password: String): EmptyResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, LoginResponse>(
            route = "/login",
            body = LoginRequest(
                email = email,
                password = password
            )
        )
        if(result is Result.Success) {
            sessionStorage.set(
                AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    userId = result.data.userId
                )
            )
        }
        return result.asEmptyDataResult()
    }

    override suspend fun register(
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(
                email = email,
                password = password
            )
        )
    }
}