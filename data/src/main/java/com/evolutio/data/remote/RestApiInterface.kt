package com.evolutio.data.remote

import com.evolutio.data.model.repository.RemoteSearchResponse
import com.evolutio.data.model.user.RemoteUser
import com.evolutio.domain.shared.INITIAL_PAGE
import com.evolutio.domain.shared.PER_PAGE_SIZE
import com.evolutio.domain.shared.STAR_SORT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RestApiInterface {

    @GET(SEARCH_REPOSITORIES)
    suspend fun getRepositories(
        @Query("q") queryValue: String,
        @Query("sort") sort: String = STAR_SORT,
        @Query("page") page: Int = INITIAL_PAGE,
        @Query("per_page") perPage: Int = PER_PAGE_SIZE
        // For our usecase we'll leave the DEFAULT_PER_PAGE for now,
        // if we wanted to change the perPage at runTime we'd need to extend our usecase
        // to accept a per_page argument and pass it down to the repository.
    ): Response<RemoteSearchResponse>

    @GET(USERS)
    suspend fun getUserData(
        @Path("userName") userName: String
    ): Response<RemoteUser>
}