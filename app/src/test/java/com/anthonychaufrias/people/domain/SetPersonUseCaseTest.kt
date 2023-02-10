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

        val service = PersonMockService()
        val api = PersonService(service)
        val repository = PersonRepository(api)
        setPersonUseCase = SetPersonUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `validate result after inserting a person to produce an OK as a result`() = runTest {
        // given
        val personID = 0
        val differentDocumentID = "11111111"
        val person = Person(
            personID,
            PersonMockService.ANTHONY_CHAU_FRIAS.fullName,
            differentDocumentID,
            PersonMockService.ANTHONY_CHAU_FRIAS.countryID,
            PersonMockService.ANTHONY_CHAU_FRIAS.countryName
        )

        // when
        val result: PersonSaveResult = setPersonUseCase(person)

        // then
        assertTrue(result is PersonSaveResult.OK)
    }

    @Test
    fun `validate result after sending a person to insert to produce an OperationFailed as a result`() = runTest {
        // given
        val personID = 0
        val person = Person(
            personID,
            PersonMockService.ANTHONY_CHAU_FRIAS.fullName,
            PersonMockService.ANTHONY_CHAU_FRIAS.documentID,
            PersonMockService.ANTHONY_CHAU_FRIAS.countryID,
            PersonMockService.ANTHONY_CHAU_FRIAS.countryName
        )
        // when
        val result: PersonSaveResult = setPersonUseCase(person)

        // then
        assertTrue(result is PersonSaveResult.OperationFailed)
    }
}