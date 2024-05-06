package com.kitaharaa.digitalapp.data.remote

import com.kitaharaa.digitalapp.data.remote.ServerData.HOST
import com.kitaharaa.digitalapp.data.remote.entity.auth.AuthorizationResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.URLProtocol
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

class AuthorizationDataSource @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getAuthData(): AuthorizationResponse? = withContext(IO) {
        val body = JSONObject().apply {
            put("username", "365")
            put("password", "1")
        }

        httpClient.post {
            url {
                protocol = URLProtocol.HTTPS
                host = HOST

                path("index.php/login")

                header("Authorization", "Basic QVBJX0V4cGxvcmVyOjEyMzQ1NmlzQUxhbWVQYXNz")
                setBody(body.toString())
            }
        }.body()
    }
}