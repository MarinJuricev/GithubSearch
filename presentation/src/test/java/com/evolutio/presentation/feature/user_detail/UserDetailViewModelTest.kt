package com.evolutio.presentation.feature.user_detail

import com.evolutio.domain.feature.user_detail.GetUserData
import com.evolutio.domain.feature.user_detail.PrepareUserData
import com.evolutio.domain.shared.ResultWrapper
import com.evolutio.presentation.*
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
internal class UserDetailViewModelTest {

    private val getUserData: GetUserData = mockk()
    private val prepareUserData: PrepareUserData = mockk()

    private lateinit var userDetailViewModel: UserDetailViewModel

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)

        userDetailViewModel = UserDetailViewModel(getUserData, prepareUserData)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `should trigger getUserData and update bioData and userData when the use-case result is a success`() =
        runBlockingTest {
            coEvery {
                getUserData.execute(TEST_URL)
            } coAnswers { ResultWrapper.build { PRESENTATION_TEST_USER } }

            coEvery {
                prepareUserData.execute(PRESENTATION_TEST_USER)
            } coAnswers { PRESENTATION_TEST_USER_ITEM }

            userDetailViewModel.handleEvent(UserDetailEvent.OnGetUserData(TEST_URL))

            coVerify { getUserData.execute(TEST_URL) }


            assert(userDetailViewModel.bioData.getOrAwaitValue() == PRESENTATION_TEST_USER.bio)
            assert(userDetailViewModel.userData.getOrAwaitValue() == PRESENTATION_TEST_USER_ITEM)
            assert(userDetailViewModel.loading.getOrAwaitValue() == false)
        }

    @Test
    fun `should trigger getUserData and errorState when the use-case result is a failure`() =
        runBlockingTest {
            coEvery {
                getUserData.execute(TEST_URL)
            } coAnswers { ResultWrapper.build { throw Exception("Error occurred") } }

            userDetailViewModel.handleEvent(UserDetailEvent.OnGetUserData(TEST_URL))

            coVerify { getUserData.execute(TEST_URL) }

            assert(userDetailViewModel.error.getOrAwaitValue() == "Error occurred")
            assert(userDetailViewModel.loading.getOrAwaitValue() == false)
        }

}