package com.ajailani.moodify.ui.viewmodel

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import com.ajailani.moodify.domain.use_case.mood.GetPagingMoodsUseCase
import com.ajailani.moodify.ui.feature.mood_list.MoodListEvent
import com.ajailani.moodify.ui.feature.mood_list.MoodListViewModel
import com.ajailani.moodify.util.ListCallback
import com.ajailani.moodify.util.TestCoroutineRule
import com.ajailani.moodify.util.moods
import com.ajailani.moodify.util.DiffCallback
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.doReturn

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoodListViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getPagingMoodsUseCase: GetPagingMoodsUseCase

    private lateinit var moodListViewModel: MoodListViewModel

    private lateinit var onEvent: (MoodListEvent) -> Unit

    @Before
    fun setUp() {
        moodListViewModel = MoodListViewModel(getPagingMoodsUseCase)
        onEvent = moodListViewModel::onEvent
    }

    @Test
    fun `Get paging projects should be success`() {
        testCoroutineRule.runTest {
            val pagingData = flowOf(PagingData.from(moods))

            doReturn(pagingData).`when`(getPagingMoodsUseCase)(
                month = anyOrNull(),
                year = anyOrNull()
            )

            onEvent(MoodListEvent.GetPagingMoods)

            val pagingMoods = moodListViewModel.pagingMoods.value
            val differ = AsyncPagingDataDiffer(
                diffCallback = DiffCallback(),
                updateCallback = ListCallback()
            )

            differ.submitData(pagingMoods)

            assertEquals(
                "Should be success",
                moods,
                differ.snapshot().items
            )
        }
    }
}