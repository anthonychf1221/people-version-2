package com.anthonychaufrias.people.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anthonychaufrias.people.data.PersonRepository
import com.anthonychaufrias.people.data.model.Person
import com.anthonychaufrias.people.data.model.PersonSaveResult
import com.anthonychaufrias.people.data.service.PersonService
import com.anthonychaufrias.people.mock.PersonMockService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SetPersonUseCaseTest{
    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var setPersonUseCase: SetPersonUseCase
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `validate result after inserting a person to produce an OK as a result`() = runTest {
        // given
        val service = PersonMockService()
        val api = PersonService(service)
        val repository = PersonRepository(api)
        setPersonUseCase = SetPersonUseCase(repository)
        val person = Person(0, "Anthony Chau Frias", "11111111", 1, "Perú")

        // when
        val result: PersonSaveResult = setPersonUseCase(person)

        // then
        assertTrue(result is PersonSaveResult.OK)
    }

}