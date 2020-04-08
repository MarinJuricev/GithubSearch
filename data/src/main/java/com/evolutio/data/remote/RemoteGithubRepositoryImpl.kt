package com.evolutio.data.remote

import com.evolutio.data.ext.safeApiCall
import com.evolutio.data.model.repository.toSearchResponse
import com.evolutio.data.model.token.AccessTokenRequest
import com.evolutio.data.model.user.toUser
import com.evolutio.domain.model.search.SearchResponse
import com.evolutio.domain.model.user.User
import com.evolutio.domain.repository.IGithubRepository
import com.evolutio.domain.service.ILoginService
import com.evolutio.domain.shared.CLIENT_ID
import com.evolutio.domain.shared.CLIENT_SECRET
import com.evolutio.domain.shared.DispatcherProvider
import com.evolutio.domain.shared.ResultWrapper
import kotlinx.coroutines.withContext

class RemoteGithubRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val restApiInterface: RestApiInterface,
    private val loginService: ILoginService
) : IGithubRepository {
    override suspend fun getRepositories(
        query: String,
        sort: String,
        page: Int
    ): ResultWrapper<Exception, SearchResponse> =
        withContext(dispatcherProvider.provideIOContext()) {
            val result = safeApiCall {
                restApiInterface.getRepositories(
                    queryValue = query,
                    sort = sort,
                    page = page
                )
            }
            when (result) {
                is ResultWrapper.Value -> ResultWrapper.build { result.value.toSearchResponse() }
                is ResultWrapper.Error -> result
            }
        }

    override suspend fun getUserData(query: String): ResultWrapper<Exception, User> =
        withContext(dispatcherProvider.provideIOContext()) {
            when (val result = safeApiCall { restApiInterface.getUserData(userName = query) }) {
                is ResultWrapper.Value -> ResultWrapper.build { result.value.toUser() }
                is ResultWrapper.Error -> result
            }
        }

    override suspend fun getAccessToken(
        code: String
    ): ResultWrapper<Exception, Unit> =
        withContext(dispatcherProvider.provideIOContext()) {
            when (val result = safeApiCall {
                restApiInterface.getAccessToken(
                    authUrl = ACCESS_TOKEN_URL,
                    accessTokenBody = AccessTokenRequest(CLIENT_ID, CLIENT_SECRET, code)
                )
            }) {
                is ResultWrapper.Value -> {
                    loginService.saveAccessToken(result.value.accessToken ?: "")
                    ResultWrapper.build { Unit }
                }
                is ResultWrapper.Error -> result
            }
        }


}