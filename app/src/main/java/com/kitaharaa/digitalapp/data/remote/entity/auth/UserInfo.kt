package com.kitaharaa.digitalapp.data.remote.entity.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class UserInfo(
    @SerialName("active")
    val active: Boolean? = null, // true
    @SerialName("businessUnit")
    val businessUnit: String? = null, // IT
    @SerialName("displayName")
    val displayName: String? = null, // Schäfers Christian
    @SerialName("firstName")
    val firstName: String? = null, // Christian
    @SerialName("lastName")
    val lastName: String? = null, // Schäfers
    @SerialName("personalNo")
    val personalNo: Int? = null // 365
)