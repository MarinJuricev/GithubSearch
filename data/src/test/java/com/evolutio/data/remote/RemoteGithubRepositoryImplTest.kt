package com.evolutio.data.remote

import com.evolutio.data.TEST_REMOTE_USER
import com.evolutio.data.TEST_USER_NAME
import com.evolutio.data.model.user.toUser
import com.evolutio.domain.service.ILoginService
import com.evolutio.domain.shared.DispatcherProvider
import com.evolutio.domain.shared.ResultWrapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

internal class RemoteGithubRepositoryImplTest {

    private val dispatcherProvider: DispatcherProvider = mockk()
    private val restApiInterface: RestApiInterface = mockk()
    private val loginService: ILoginService = mockk()

    private lateinit var repository: RemoteGithubRepositoryImpl

    @BeforeEach
    fun setUp() {
        repository = RemoteGithubRepositoryImpl(dispatcherProvider, restApiInterface, loginService)
    }

    @Test
    fun `should return ResultWrapperValue when the call to restApiInterface is a success`() =
        runBlocking {

            coEvery { dispatcherProvider.provideIOContext() } returns Dispatchers.Unconfined

            coEvery {
                restApiInterface.getUserData(TEST_USER_NAME)
            } coAnswers {
                Response.success(TEST_REMOTE_USER)
            }

            val actualResult = repository.getUserData(TEST_USER_NAME)
            val expectedResult = ResultWrapper.build { TEST_REMOTE_USER.toUser() }

            coVerify { restApiInterface.getUserData(TEST_USER_NAME) }
            assert(expectedResult == actualResult)
        }

    @Test
    fun `should return ResultWrapperError when the call to restApiInterface is a failure`() =
        runBlocking {
            coEvery { dispatcherProvider.provideIOContext() } returns Dispatchers.Unconfined

            coEvery {
                restApiInterface.getUserData(TEST_USER_NAME)
            } coAnswers {
                throw Exception("Error occurred")
            }

            val actualResultWrapper = repository.getUserData(TEST_USER_NAME)
            val expectedResultWrapper = ResultWrapper.build { throw Exception("Error occurred") }

            coVerify { restApiInterface.getUserData(TEST_USER_NAME) }

            val expectedResult = expectedResultWrapper as ResultWrapper.Error
            val actualResult = actualResultWrapper as ResultWrapper.Error

            assert(expectedResult.error.message == actualResult.error.message)
        }
}