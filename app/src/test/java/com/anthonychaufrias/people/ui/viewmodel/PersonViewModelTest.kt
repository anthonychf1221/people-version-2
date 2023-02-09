package com.anthonychaufrias.people.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anthonychaufrias.people.data.PersonRepository
import com.anthonychaufrias.people.data.service.PersonService
import com.anthonychaufrias.people.domain.SetPersonUseCase
import com.anthonychaufrias.people.domain.UpdatePersonUseCase
import com.anthonychaufrias.people.getOrAwaitValue
import com.anthonychaufrias.people.mock.PersonMockService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PersonViewModelTest {
    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var personaViewModel: PersonViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)

        val service = PersonMockService()
        val api = PersonService(service)
        val repository = PersonRepository(api)

        val setPersonUseCase = SetPersonUseCase(repository)
        val updatePersonUseCase = UpdatePersonUseCase(repository)

        personaViewModel = PersonViewModel(repository, setPersonUseCase, updatePersonUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `validate the size of the entire people list`() {
        // given
        val search: String = ""
        personaViewModel.peopleList.clear()

        // when
        personaViewModel.loadPeopleList(search)

        // then
        val result = personaViewModel.liveDataPeopleList.getOrAwaitValue()
        assertTrue(result.size == 6)
        assertTrue(personaViewModel.peopleList.size == 6)
    }
}