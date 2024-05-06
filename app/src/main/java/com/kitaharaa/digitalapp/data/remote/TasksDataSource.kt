package com.kitaharaa.digitalapp.data.remote

import com.kitaharaa.digitalapp.data.remote.entity.task.TaskInfoResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TasksDataSource @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun getTaskList(token: String): List<TaskInfoResponse> = withContext(IO) {
        httpClient.get {
            url {
                protocol = URLProtocol.HTTPS
                host = ServerData.HOST
                header("Authorization", "Bearer $token")
                path("dev/index.php/v1/tasks/select")
            }
        }.body<List<TaskInfoResponse>>()
    }
}