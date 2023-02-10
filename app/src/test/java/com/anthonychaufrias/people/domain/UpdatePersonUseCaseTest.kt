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

class UpdatePersonUseCaseTest{
    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var updatePersonUseCase: UpdatePersonUseCase
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)

        val service = PersonMockService()
        val api = PersonService(service)
        val repository = PersonRepository(api)
        updatePersonUseCase = UpdatePersonUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `validate result after updating a person to produce an OK as a result`() = runTest {
        // given
        val person = PersonMockService.ANTHONY_CHAU_FRIAS

        // when
        val result: PersonSaveResult = updatePersonUseCase(person)

        // then
        assertTrue(result is PersonSaveResult.OK)
    }

    @Test
    fun `validate result after sending a person to update to produce an OperationFailed as a result`() = runTest {
        // given
        val person = Person(
            PersonMockService.ANTHONY_CHAU_FRIAS.personID,
            PersonMockService.ANTHONY_CHAU_FRIAS.fullName,
            PersonMockService.GUILLERMINA_LUBARTI.documentID,
            PersonMockService.ANTHONY_CHAU_FRIAS.countryID,
            PersonMockService.ANTHONY_CHAU_FRIAS.countryName
        )

        // when
        val result: PersonSaveResult = updatePersonUseCase(person)

        // then
        assertTrue(result is PersonSaveResult.OperationFailed)
    }

}