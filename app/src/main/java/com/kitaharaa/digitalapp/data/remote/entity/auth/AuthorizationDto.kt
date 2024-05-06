package com.kitaharaa.digitalapp.data.remote.entity.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep
import com.kitaharaa.digitalapp.data.local.entity.AuthorizationEntity
import java.util.Calendar
import java.util.Date

@Keep
@Serializable
data class AuthorizationDto(
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
) {
    fun toAuthEntity(): AuthorizationEntity {
        val calendar: Calendar = Calendar.getInstance().apply {
            setTime(Date())
            add(Calendar.SECOND, oauth?.expiresIn ?: 0)
        }

        return AuthorizationEntity(
            id = 1,
            token = this.oauth?.accessToken.toString(),
            updateDate = calendar.time,
            validIn = this.oauth?.expiresIn ?: 1200
        )
    }
}