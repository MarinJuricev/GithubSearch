package com.evolutio.domain.repository

import com.evolutio.domain.model.user.private_user.PrivateUser
import com.evolutio.domain.model.search.SearchResponse
import com.evolutio.domain.model.user.public_user.User
import com.evolutio.domain.shared.ResultWrapper

interface IGithubRepository {

    suspend fun getRepositories(
        query: String,
        sort: String,
        page: Int
    ): ResultWrapper<Exception, SearchResponse>

    suspend fun getUserData(
        query: String
    ): ResultWrapper<Exception, User>

    suspend fun getAccessToken(
        code: String
    ): ResultWrapper<Exception, Unit>

    suspend fun getPrivateUserData(): ResultWrapper<Exception, PrivateUser>
}