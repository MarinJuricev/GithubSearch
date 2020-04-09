package com.evolutio.domain.feature.login

import com.evolutio.domain.service.ILoginService
import com.evolutio.domain.shared.UseCase
import javax.inject.Inject

class IsAccessTokenAvailable @Inject constructor(
    private val loginService: ILoginService
) : UseCase<String>() {
    override suspend fun buildUseCase(): String =
        loginService.getAccessToken()

}