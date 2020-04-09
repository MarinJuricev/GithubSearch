package com.evolutio.domain.model.user.private_user

import com.evolutio.domain.model.user.IBaseUser

data class PrivateUser(
    val avatarUrl: String,
    override val bio: String,
    override val blog: String,
    override val company: String,
    override val createdAt: String,
    override val email: String,
    val eventsUrl: String,
    override val followers: Int,
    val followersUrl: String,
    override val following: Int,
    val followingUrl: String,
    val gistsUrl: String,
    val gravatarId: String,
    override val hireable: String,
    val htmlUrl: String,
    val id: Int,
    override val location: String,
    val login: String,
    override val name: String,
    val nodeId: String,
    val organizationsUrl: String,
    override val publicGists: Int,
    override val publicRepos: Int,
    val receivedEventsUrl: String,
    val reposUrl: String,
    val siteAdmin: Boolean,
    val starredUrl: String,
    val subscriptionsUrl: String,
    val type: String,
    override val updatedAt: String,
    val url: String
) : IBaseUser