package com.evolutio.data.remote

import com.evolutio.domain.shared.CLIENT_ID
import com.evolutio.domain.shared.REDIRECT_URI
import com.evolutio.domain.shared.SCOPE

const val BASE_URL = "https://api.github.com/"

const val SEARCH_REPOSITORIES_ENDPOINT = "/search/repositories"
const val USERS_ENDPOINT = "/users/{userName}"
const val PRIVATE_USER_ENDPOINT = "/user"
const val ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token"

const val AUTHORIZE = "https://github.com/login/oauth/authorize"

const val FULL_AUTHORIZE_ENDPOINT =
    "$AUTHORIZE?client_id=$CLIENT_ID&scope=$SCOPE&redirect_uri=$REDIRECT_URI"
