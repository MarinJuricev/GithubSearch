package com.evolutio.data.model.token


import com.google.gson.annotations.SerializedName

data class RemoteAccessToken(
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("scope")
    val scope: String?,
    @SerializedName("token_type")
    val tokenType: String?
)