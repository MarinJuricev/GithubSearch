package com.evolutio.domain.feature.search

import com.evolutio.domain.model.search.RepositoryRequest
import com.evolutio.domain.model.search.SearchResponse
import com.evolutio.domain.repository.IGithubRepository
import com.evolutio.domain.shared.ResultWrapper
import com.evolutio.domain.shared.UseCaseWithParams
import javax.inject.Inject

class GetRepositories @Inject constructor(
    private val githubRepository: IGithubRepository
) : UseCaseWithParams<RepositoryRequest, ResultWrapper<Exception, SearchResponse>>() {

    override suspend fun buildUseCase(params: RepositoryRequest): ResultWrapper<Exception, SearchResponse> =
        githubRepository.getRepositories(
            query = params.query,
            sort = params.sort,
            page = params.page
        )
}