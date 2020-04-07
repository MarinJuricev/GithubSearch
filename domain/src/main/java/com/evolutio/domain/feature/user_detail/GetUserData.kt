package com.evolutio.domain.feature.user_detail

import com.evolutio.domain.model.user.User
import com.evolutio.domain.repository.IGithubRepository
import com.evolutio.domain.shared.ResultWrapper
import com.evolutio.domain.shared.UseCaseWithParams
import javax.inject.Inject

class GetUserData @Inject constructor(
    private val githubRepository: IGithubRepository
) : UseCaseWithParams<String, ResultWrapper<Exception, User>>() {
    override suspend fun buildUseCase(params: String): ResultWrapper<Exception, User> =
        githubRepository.getUserData(params)
}