package com.ajailani.moodify.domain.use_case.user_profile

import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.MoodPercentage
import com.ajailani.moodify.domain.model.UserProfile
import com.ajailani.moodify.domain.repository.StatisticRepositoryFake
import com.ajailani.moodify.domain.repository.UserProfileRepositoryFake
import com.ajailani.moodify.util.ResourceType
import com.ajailani.moodify.util.moodPercentage
import com.ajailani.moodify.util.userProfile
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetUserProfileUseCaseTest {
    private lateinit var userProfileRepositoryFake: UserProfileRepositoryFake
    private lateinit var getUserProfileUseCase: GetUserProfileUseCase

    @Before
    fun setUp() {
        userProfileRepositoryFake = UserProfileRepositoryFake()
        getUserProfileUseCase = GetUserProfileUseCase(userProfileRepositoryFake)
    }

    @Test
    fun `Get user profile should return success resource`() =
        runTest(UnconfinedTestDispatcher()) {
            userProfileRepositoryFake.setResourceType(ResourceType.Success)

            val actualResource = getUserProfileUseCase().first()

            assertEquals(
                "Resource should be success",
                Resource.Success(userProfile),
                actualResource
            )
        }

    @Test
    fun `Get user profile should return error resource`() =
        runTest(UnconfinedTestDispatcher()) {
            userProfileRepositoryFake.setResourceType(ResourceType.Error)

            val actualResource = getUserProfileUseCase().first()

            assertEquals(
                "Resource should be error",
                Resource.Error<UserProfile>(),
                actualResource
            )
        }
}