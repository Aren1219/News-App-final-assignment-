package com.example.newsappfinalassignment.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsappfinalassignment.getOrAwaitValue
import com.example.newsappfinalassignment.model.Data
import com.example.newsappfinalassignment.repo.FakeRepository
import com.example.newsappfinalassignment.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MainViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantTaskExecutionRule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        val fakeRepository = FakeRepository()
        runBlocking {
            viewModel = MainViewModel(fakeRepository)
            viewModel.newsList.getOrAwaitValue()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun cleanUp(){
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `initial api list` () = runBlocking {
        assertEquals(Util.previewNewsDataList(), viewModel.newsList.value!!.data)
    }

    @Test
    fun `load two pages from api` () = runBlocking {
        viewModel.getNewsList()
        Thread.sleep(1)
        assertEquals(
            Util.previewNewsDataList() + Util.previewNewsDataList(),
            viewModel.newsList.getOrAwaitValue().data
        )
    }

    @Test
    fun `save news`() = runBlocking {
        viewModel.saveNews(Util.previewNewsData())
        Thread.sleep(1)
        assertEquals(listOf(Util.previewNewsData()), viewModel.savedList.getOrAwaitValue())
    }

    @Test
    fun `delete news`() = runBlocking {
        viewModel.saveNews(Util.previewNewsData())
        viewModel.savedList.getOrAwaitValue()
        viewModel.deleteNews(Util.previewNewsData())
        Thread.sleep(1)
        assertEquals(listOf<Data>(), viewModel.savedList.getOrAwaitValue())
    }

    @Test
    fun `find news from news list by uuid`() {
        assertEquals(Util.previewNewsData("3"), viewModel.getNewsUUID("3"))
    }

    @Test
    fun `find news from favourite list by uuid`() = runBlocking{
        val dummy = Util.previewNewsData("a saved news")
        viewModel.saveNews(dummy)
        viewModel.savedList.getOrAwaitValue()
        Thread.sleep(1)
        assertEquals(dummy ,viewModel.getNewsUUID("a saved news"))
    }

    @Test
    fun `no news found by uuid`(){
        Thread.sleep(1)
        assertNull(viewModel.getNewsUUID("a news that don't exist"))
    }
}