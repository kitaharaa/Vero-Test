package com.kitaharaa.digitalapp.data.remote.entity.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class AuthorizationResponse(
    @SerialName("apiVersion")
    val apiVersion: String? = null, // f612a10 2024-04-03
    @SerialName("oauth")
    val oauth: Oauth? = null,
    @SerialName("permissions")
    val permissions: List<String?>? = null,
    @SerialName("showPasswordPrompt")
    val showPasswordPrompt: Boolean? = null, // false
    @SerialName("userInfo")
    val userInfo: UserInfo? = null
)