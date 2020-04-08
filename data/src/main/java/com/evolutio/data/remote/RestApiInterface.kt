package com.evolutio.data.remote

import com.evolutio.data.model.repository.RemoteSearchResponse
import com.evolutio.data.model.token.AccessTokenRequest
import com.evolutio.data.model.token.RemoteAccessToken
import com.evolutio.data.model.user.RemoteUser
import com.evolutio.domain.shared.INITIAL_PAGE
import com.evolutio.domain.shared.PER_PAGE_SIZE
import com.evolutio.domain.shared.STAR_SORT
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


interface RestApiInterface {

    @GET(SEARCH_REPOSITORIES_ENDPOINT)
    suspend fun getRepositories(
        @Query("q") queryValue: String,
        @Query("sort") sort: String = STAR_SORT,
        @Query("page") page: Int = INITIAL_PAGE,
        @Query("per_page") perPage: Int = PER_PAGE_SIZE
        // For our usecase we'll leave the DEFAULT_PER_PAGE for now,
        // if we wanted to change the perPage at runTime we'd need to extend our usecase
        // to accept a per_page argument and pass it down to the repository.
    ): Response<RemoteSearchResponse>

    @GET(USERS_ENDPOINT)
    suspend fun getUserData(
        @Path("userName") userName: String
    ): Response<RemoteUser>

    @POST
    suspend fun getAccessToken(
        @Url authUrl: String,
        @Body accessTokenBody: AccessTokenRequest
    ): Response<RemoteAccessToken>

    @GET(PRIVATE_USER_ENDPOINT)
    suspend fun getPrivateUserData(): Response<ResponseBody>
}