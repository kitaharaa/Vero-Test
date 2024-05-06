package com.kitaharaa.digitalapp.data.remote

import com.kitaharaa.digitalapp.data.remote.ServerData.AUTH_HEADER
import com.kitaharaa.digitalapp.data.remote.ServerData.HOST
import com.kitaharaa.digitalapp.data.remote.entity.auth.AuthorizationDto
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
    suspend fun getAuthData(): AuthorizationDto? = withContext(IO) {
        val body = JSONObject().apply {
            put(USER_NAME, USER_NAME_VALUE)
            put(PASSWORD_NAME, PASSWORD_VALUE)
        }

        httpClient.post {
            url {
                protocol = URLProtocol.HTTPS
                host = HOST

                path(AUTH_PATH)

                header(AUTH_HEADER, AUTH_TOKEN)
                setBody(body.toString())
            }
        }.body()
    }

    companion object {
        //Body
        private const val USER_NAME = "username"
        private const val USER_NAME_VALUE = "365"

        private const val PASSWORD_NAME = "password"
        private const val PASSWORD_VALUE = "1"

        //Authorization client info
        private const val AUTH_PATH = "index.php/login"
        private const val AUTH_TOKEN = "Basic QVBJX0V4cGxvcmVyOjEyMzQ1NmlzQUxhbWVQYXNz"
    }
}