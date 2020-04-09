package com.evolutio.domain.feature.login

import com.evolutio.domain.service.ILoginService
import com.evolutio.domain.shared.UseCase
import javax.inject.Inject

class ClearAccessToken @Inject constructor(
    private val loginService: ILoginService
) : UseCase<Unit>() {
    override suspend fun buildUseCase(): Unit =
        loginService.clearAccessToken()

}