package com.evolutio.presentation

import com.evolutio.domain.model.user.UserItem
import com.evolutio.domain.model.user.public_user.User

const val TEST_URL = "https://totally.valid.url.com/"

val PRESENTATION_TEST_USER = User(
    name = "name",
    company = "company",
    blog = "blog",
    location = "location",
    email = "email",
    hireable = "hireable",
    bio = "bio",
    publicRepos = 10,
    publicGists = 25,
    followers = 5,
    following = 5,
    createdAt = "createdAt",
    updatedAt = "updatedAt"
)

val PRESENTATION_TEST_USER_ITEM = listOf(
    UserItem("name", "name"),
    UserItem("company", "company"),
    UserItem("blog", "blog"),
    UserItem("location", "location"),
    UserItem("email", "email"),
    UserItem("hireable", "hireable"),
    UserItem("bio", "bio"),
    UserItem("publicRepos", "publicRepos"),
    UserItem("publicGists", "publicRepos"),
    UserItem("followers", "followers"),
    UserItem("following", "following"),
    UserItem("createdAt", "createdAt"),
    UserItem("updatedAt", "updatedAt")
)