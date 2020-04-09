package com.evolutio.domain.model.user.public_user

import com.evolutio.domain.model.user.IBaseUser

data class User(
    override val name: String?,
    override val company: String?,
    override val blog: String,
    override val location: String?,
    override val email: String?,
    override val hireable: String?,
    override val bio: String?,
    override val publicRepos: Int,
    override val publicGists: Int,
    override val followers: Int,
    override val following: Int,
    override val createdAt: String,
    override val updatedAt: String
): IBaseUser