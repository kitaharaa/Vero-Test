package com.kitaharaa.digitalapp.data.remote.entity.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class Oauth(
    @SerialName("access_token")
    val accessToken: String? = null, // 193186c163a73ac1b3ab9bf9f8329d937ffce114
    @SerialName("expires_in")
    val expiresIn: Int? = null, // 1200
    @SerialName("refresh_token")
    val refreshToken: String? = null, // 734ee26789da7747d9f93d28a537bfca01a36595
    @SerialName("scope")
    val scope: String? = null, // null
    @SerialName("token_type")
    val tokenType: String? = null // Bearer
)