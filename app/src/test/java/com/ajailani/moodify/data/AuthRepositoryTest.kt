package com.ajailani.moodify.data

import android.content.Context
import com.ajailani.moodify.data.remote.data_source.AuthRemoteDataSource
import com.ajailani.moodify.data.remote.dto.UserCredentialDto
import com.ajailani.moodify.data.remote.dto.response.BaseResponse
import com.ajailani.moodify.data.repository.AuthRepositoryImpl
import com.ajailani.moodify.domain.model.UserCredential
import com.ajailani.moodify.domain.repository.AuthRepository
import com.ajailani.moodify.util.userCredential
import com.ajailani.moodify.util.userCredentialDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AuthRepositoryTest {

    @Mock
    private lateinit var authRemoteDataSource: AuthRemoteDataSource

    @Mock
    private lateinit var context: Context

    private lateinit var authRepository: AuthRepository

    @Before
    fun setUp() {
        authRepository = AuthRepositoryImpl(
            context,
            authRemoteDataSource
        )
    }

    @Test
    fun `Register should return success resource`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.success(
                201,
                BaseResponse(
                    code = 201,
                    status = "Created",
                    data = userCredentialDto
                )
            )

            doReturn(response).`when`(authRemoteDataSource).register(any())

            val actualResource = authRepository.register(
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
            val response = Response.error<UserCredentialDto>(
                409,
                "".toResponseBody()
            )

            doReturn(response).`when`(authRemoteDataSource).register(any())

            val actualResource = authRepository.register(
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

    @Test
    fun `Login should return success resource`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.success(
                200,
                BaseResponse(
                    code = 200,
                    status = "OK",
                    data = userCredentialDto
                )
            )

            doReturn(response).`when`(authRemoteDataSource).login(any())

            val actualResource = authRepository.login(
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
    fun `Login should return error resource`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.error<UserCredentialDto>(
                400,
                "".toResponseBody()
            )

            doReturn(response).`when`(authRemoteDataSource).login(any())

            val actualResource = authRepository.login(
                username = "george",
                password = "123"
            ).first()

            assertEquals(
                "Resource should be success",
                Resource.Error<UserCredential>(),
                actualResource
            )
        }
}