package com.ajailani.moodify.ui.viewmodel

import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.FrequentActivity
import com.ajailani.moodify.domain.model.MoodPercentage
import com.ajailani.moodify.domain.model.UserProfile
import com.ajailani.moodify.domain.use_case.user_credential.DeleteAccessTokenUseCase
import com.ajailani.moodify.domain.use_case.user_profile.GetUserProfileUseCase
import com.ajailani.moodify.ui.common.UIState
import com.ajailani.moodify.ui.feature.profile.ProfileEvent
import com.ajailani.moodify.ui.feature.profile.ProfileViewModel
import com.ajailani.moodify.ui.feature.statistic.StatisticEvent
import com.ajailani.moodify.ui.feature.statistic.StatisticViewModel
import com.ajailani.moodify.util.TestCoroutineRule
import com.ajailani.moodify.util.frequentActivities
import com.ajailani.moodify.util.moodPercentage
import com.ajailani.moodify.util.userProfile
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProfileViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getUserProfileUseCase: GetUserProfileUseCase

    @Mock
    private lateinit var deleteAccessTokenUseCase: DeleteAccessTokenUseCase

    private lateinit var profileViewModel: ProfileViewModel

    private lateinit var onEvent: (ProfileEvent) -> Unit

    @Before
    fun setUp() {
        profileViewModel = ProfileViewModel(
            getUserProfileUseCase,
            deleteAccessTokenUseCase
        )
        onEvent = profileViewModel::onEvent
    }

    @Test
    fun `Get user profile should be success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(userProfile))

            doReturn(resource).`when`(getUserProfileUseCase)()

            onEvent(ProfileEvent.GetUserProfile)

            val userProfile =
                when (val userProfileState = profileViewModel.userProfileState) {
                    is UIState.Success -> userProfileState.data

                    else -> null
                }

            assertNotNull(userProfile)
            assertEquals(
                "Username should be 'george'",
                "george",
                userProfile?.username
            )
        }
    }

    @Test
    fun `Get user profile should be fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<UserProfile>())

            doReturn(resource).`when`(getUserProfileUseCase)()

            onEvent(ProfileEvent.GetUserProfile)

            val isSuccess = when (profileViewModel.userProfileState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }
}