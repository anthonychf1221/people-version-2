package com.anthonychaufrias.people.domain

import com.anthonychaufrias.people.data.model.ValidationResult
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidatePersonUseCaseTest{

    @Test
    fun validateOnlyEmptyName(){
        // given
        val name: String = ""
        val docID: String  = "12345678"

        // when
        val result = ValidatePersonUseCase.getFormValidation(name, docID)
        val firstIndex = 0

        // then
        assertTrue(result[firstIndex] == ValidationResult.INVALID_NAME)
    }

    @Test
    fun validateOnlyEmptyDocumentID(){
        // given
        val name: String = "Anthony"
        val docID: String  = ""

        // when
        val result = ValidatePersonUseCase.getFormValidation(name, docID)
        val firstIndex = 0

        // then
        assertTrue(result[firstIndex] == ValidationResult.INVALID_DOCUMENT_ID)
    }

    @Test
    fun validateOnlyLengthDocumentID(){
        // given
        val name: String = "Anthony"
        val docID: String  = "123"

        // when
        val result = ValidatePersonUseCase.getFormValidation(name, docID)
        val firstIndex = 0

        // then
        assertTrue(result[firstIndex] == ValidationResult.INVALID_DOCUMENT_ID)
    }

    @Test
    fun `validate if the name and DocumentID are empty`(){
        // given
        val name: String = ""
        val docID: String  = ""

        // when
        val result = ValidatePersonUseCase.getFormValidation(name, docID)
        val firstIndex = 0
        val secondIndex = 1

        // then
        assertTrue(result[firstIndex] == ValidationResult.INVALID_NAME && result[secondIndex] == ValidationResult.INVALID_DOCUMENT_ID)
    }

    @Test
    fun `validate if the name is empty and the length of DocumentID is not valid`(){
        // given
        val name: String = ""
        val docID: String  = "123"

        // when
        val result = ValidatePersonUseCase.getFormValidation(name, docID)
        val firstIndex = 0
        val secondIndex = 1

        // then
        assertTrue(result[firstIndex] == ValidationResult.INVALID_NAME && result[secondIndex] == ValidationResult.INVALID_DOCUMENT_ID)
    }

    @Test
    fun `validate if everything is OK`(){
        // given
        val name: String = "Anthony"
        val docID: String  = "12345678"

        // when
        val result = ValidatePersonUseCase.getFormValidation(name, docID)
        val firstIndex = 0

        // then
        assertTrue(result[firstIndex] == ValidationResult.OK)
    }

}