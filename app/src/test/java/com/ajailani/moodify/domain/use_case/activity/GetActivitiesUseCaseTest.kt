package com.ajailani.moodify.domain.use_case.activity

import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.Activity
import com.ajailani.moodify.domain.repository.ActivityRepositoryFake
import com.ajailani.moodify.util.ResourceType
import com.ajailani.moodify.util.activities
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetActivitiesUseCaseTest {
    private lateinit var activityRepositoryFake: ActivityRepositoryFake
    private lateinit var getActivitiesUseCase: GetActivitiesUseCase

    @Before
    fun setUp() {
        activityRepositoryFake = ActivityRepositoryFake()
        getActivitiesUseCase = GetActivitiesUseCase(activityRepositoryFake)
    }

    @Test
    fun `Get activities should return success resource`() =
        runTest(UnconfinedTestDispatcher()) {
            activityRepositoryFake.setResourceType(ResourceType.Success)

            val actualResource = getActivitiesUseCase(true).first()

            assertEquals(
                "Resource should be success",
                Resource.Success(activities),
                actualResource
            )
        }

    @Test
    fun `Get activities should return error resource`() =
        runTest(UnconfinedTestDispatcher()) {
            activityRepositoryFake.setResourceType(ResourceType.Error)

            val actualResource = getActivitiesUseCase(true).first()

            assertEquals(
                "Resource should be error",
                Resource.Error<List<Activity>>(),
                actualResource
            )
        }
}