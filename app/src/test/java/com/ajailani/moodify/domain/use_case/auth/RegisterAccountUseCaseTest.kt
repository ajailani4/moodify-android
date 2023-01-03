package com.ajailani.moodify.domain.use_case.auth

import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.UserCredential
import com.ajailani.moodify.domain.repository.AuthRepositoryFake
import com.ajailani.moodify.util.ResourceType
import com.ajailani.moodify.util.userCredential
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RegisterAccountUseCaseTest {
    private lateinit var authRepositoryFake: AuthRepositoryFake
    private lateinit var registerAccountUseCase: RegisterAccountUseCase

    @Before
    fun setUp() {
        authRepositoryFake = AuthRepositoryFake()
        registerAccountUseCase = RegisterAccountUseCase(authRepositoryFake)
    }

    @Test
    fun `Register should return success resource`() =
        runTest(UnconfinedTestDispatcher()) {
            authRepositoryFake.setResourceType(ResourceType.Success)

            val actualResource = registerAccountUseCase(
                name = "George",
                email = "george@email.com",
                username = "george",
                password = "123"
            ).first()

            assertEquals(
                "Resource should be success",
                Resource.Success(userCredential),
                actualResource
            )
        }

    @Test
    fun `Register should return error resource`() =
        runTest(UnconfinedTestDispatcher()) {
            authRepositoryFake.setResourceType(ResourceType.Error)

            val actualResource = registerAccountUseCase(
                name = "George",
                email = "george@email.com",
                username = "george",
                password = "123"
            ).first()

            assertEquals(
                "Resource should be error",
                Resource.Error<UserCredential>(),
                actualResource
            )
        }
}