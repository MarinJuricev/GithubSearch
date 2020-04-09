package com.evolutio.domain.feature.user_detail

import DOMAIN_TEST_USER
import com.evolutio.domain.model.user.UserItem
import com.evolutio.domain.shared.DispatcherProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PrepareUserDataTest {

    private val dispatcherProvider: DispatcherProvider = mockk()

    private lateinit var prepareUserData: PrepareUserData

    @BeforeEach
    fun setUp() {
        prepareUserData = PrepareUserData(dispatcherProvider)
    }

    @Test
    fun `should return the expectedResult when TEST_USER is provided to the use-case`() =
        runBlocking {
            coEvery { dispatcherProvider.provideComputationContext() } returns Dispatchers.Unconfined

            val actualResult = prepareUserData.execute(DOMAIN_TEST_USER)

            val expectedResult = listOf(
                UserItem("username", "name"),
                UserItem("company", "company"),
                UserItem("blog", "blog"),
                UserItem("location", "location"),
                UserItem("email", "email"),
                UserItem("hireable", "hireable"),
                UserItem("public_repos", "10"),
                UserItem("public_gists", "25"),
                UserItem("followers", "5"),
                UserItem("following", "5"),
                UserItem("created_at", "createdAt"),
                UserItem("updated_at", "updatedAt")
            )

            assert(expectedResult == actualResult)
        }

}