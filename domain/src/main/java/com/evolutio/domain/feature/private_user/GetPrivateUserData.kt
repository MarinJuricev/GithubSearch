package com.evolutio.domain.feature.private_user

import com.evolutio.domain.model.user.private_user.PrivateUser
import com.evolutio.domain.repository.IGithubRepository
import com.evolutio.domain.shared.ResultWrapper
import com.evolutio.domain.shared.UseCase
import javax.inject.Inject

class GetPrivateUserData @Inject constructor(
    private val githubRepository: IGithubRepository
) : UseCase<ResultWrapper<Exception, PrivateUser>>() {
    override suspend fun buildUseCase(): ResultWrapper<Exception, PrivateUser> =
        githubRepository.getPrivateUserData()


}