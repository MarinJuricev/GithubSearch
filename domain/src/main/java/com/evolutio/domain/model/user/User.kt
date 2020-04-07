package com.evolutio.domain.model.user

data class User(
    val name: String?,
    val company: String?,
    val blog: String,
    val location: String?,
    val email: String?,
    val hireable: String?,
    val bio: String?,
    val publicRepos: Int,
    val publicGists: Int,
    val followers: Int,
    val following: Int,
    val createdAt: String,
    val updatedAt: String
)