package com.sebascamayo.auth.data

import com.sebascamayo.auth.domain.AuthRepository
import com.sebascamayo.core.data.networking.HttpClientFactory
import com.sebascamayo.core.data.networking.post
import com.sebascamayo.core.domain.AuthInfo
import com.sebascamayo.core.domain.SessionStorage
import com.sebascamayo.core.domain.util.DataError
import com.sebascamayo.core.domain.util.EmptyResult
import com.sebascamayo.core.domain.util.Result
import com.sebascamayo.core.domain.util.asEmptyDataResult
import io.ktor.client.HttpClient
import org.koin.core.component.KoinComponent

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage
): AuthRepository, KoinComponent {

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

            //Se sobreescribe la instancia HttpClient
            //Asi obtenemos el token actualizado en la nueva instancia al inicio
            getKoin().declare(HttpClientFactory(sessionStorage).build(), allowOverride = true)
            println("Access token in cache/ implem: ${sessionStorage.get()?.accessToken}")
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