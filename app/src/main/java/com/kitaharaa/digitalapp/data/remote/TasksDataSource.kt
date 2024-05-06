package com.kitaharaa.digitalapp.data.remote

import com.kitaharaa.digitalapp.data.remote.ServerData.AUTH_HEADER
import com.kitaharaa.digitalapp.data.remote.entity.task.TaskInfoDto
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

    suspend fun getTaskList(token: String): List<TaskInfoDto> = withContext(IO) {
        httpClient.get {
            url {
                protocol = URLProtocol.HTTPS
                host = ServerData.HOST
                header(AUTH_HEADER, TASK_TOKEN_LABEL + token)
                path(TASK_PATH)
            }
        }.body<List<TaskInfoDto>>()
    }

    companion object {
        private const val TASK_TOKEN_LABEL = "Bearer "
        private const val TASK_PATH = "dev/index.php/v1/tasks/select"
    }
}