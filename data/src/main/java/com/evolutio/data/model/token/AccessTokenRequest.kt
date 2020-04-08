package com.evolutio.data.model.token

import com.google.gson.annotations.SerializedName

data class AccessTokenRequest(
    @SerializedName("client_id")
    val clientId: String,
    @SerializedName("client_secret")
    val clientSecret: String,
    val code: String
)