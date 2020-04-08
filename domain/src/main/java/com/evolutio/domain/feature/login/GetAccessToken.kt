package com.evolutio.domain.feature.login

import com.evolutio.domain.repository.IGithubRepository
import com.evolutio.domain.shared.ResultWrapper
import com.evolutio.domain.shared.UseCaseWithParams
import javax.inject.Inject

class GetAccessToken @Inject constructor(
    private val githubRepository: IGithubRepository
) : UseCaseWithParams<String, ResultWrapper<Exception, Unit>>() {
    override suspend fun buildUseCase(params: String): ResultWrapper<Exception, Unit> =
        githubRepository.getAccessToken(
            code = params
        )

}

