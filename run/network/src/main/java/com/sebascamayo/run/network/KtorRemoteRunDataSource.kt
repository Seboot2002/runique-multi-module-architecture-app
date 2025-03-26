package com.sebascamayo.run.network

import com.sebascamayo.core.data.networking.constructRoute
import com.sebascamayo.core.data.networking.delete
import com.sebascamayo.core.data.networking.get
import com.sebascamayo.core.data.networking.safeCall
import com.sebascamayo.core.domain.run.RemoteRunDataSource
import com.sebascamayo.core.domain.run.Run
import com.sebascamayo.core.domain.util.DataError
import com.sebascamayo.core.domain.util.EmptyResult
import com.sebascamayo.core.domain.util.Result
import com.sebascamayo.core.domain.util.map
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class KtorRemoteRunDataSource(
    private val httpClient: HttpClient
): RemoteRunDataSource {

    override suspend fun getRuns(): Result<List<Run>, DataError.Network> {

        return httpClient.get<List<RunDto>>(
            route = "/run",
        ).map { runDtos ->
            runDtos.map {
                it.toRun()
            }
        }
    }

    override suspend fun postRun(run: Run, mapPicture: ByteArray): Result<Run, DataError.Network> {
        val createRunRequestJson = Json.encodeToString(run.toCreateRunRequest())

        // Necesitaremos obtener data binaria para la imagen
        // Se hace una peticion http

        val result = safeCall<RunDto> {

            httpClient.submitFormWithBinaryData(
                url = constructRoute("/run"),
                formData = formData {
                    append("MAP_PICTURE", mapPicture, Headers.build {
                        append(HttpHeaders.ContentType, "image/jpeg")
                        append(HttpHeaders.ContentType, "filename=mappicture.jpg")
                    })
                    append("RUN_DATA", createRunRequestJson, Headers.build {
                        append(HttpHeaders.ContentType, "text/plain")
                        append(HttpHeaders.ContentType, "form-data; name=\"RUN_DATA\"")
                    })
                }
            ) {
                method = HttpMethod.Post
            }
        }
        return result.map {
            it.toRun()
        }
    }

    override suspend fun deleteRun(id: String): EmptyResult<DataError.Network> {
        return httpClient.delete(
            route = "/run",
            queryParameters = mapOf(
                "id" to id
            )
        )
    }

}