package com.anthonychaufrias.people.domain

import com.anthonychaufrias.people.core.Values
import com.anthonychaufrias.people.data.PersonRepository
import com.anthonychaufrias.people.data.model.Person
import com.anthonychaufrias.people.data.model.PersonSaveResult
import com.anthonychaufrias.people.data.model.ValidationResult

object ValidatePersonUseCase {
    fun getFormValidation(name: String, docID: String): MutableList<ValidationResult> {
        val validations = mutableListOf<ValidationResult>()
        if (name.isEmpty()) {
            validations.add(ValidationResult.INVALID_NAME)
        }
        if (docID.length != Values.PERSON_DOCUMENT_LENGTH) {
            validations.add(ValidationResult.INVALID_DOCUMENT_ID)
        }
        if (validations.size == 0) {
            validations.add(ValidationResult.OK)
        }
        return validations
    }
}

class SetPersonUseCase(private val repository: PersonRepository) {

    suspend operator fun invoke(person: Person): PersonSaveResult {
        val validations = ValidatePersonUseCase.getFormValidation(person.fullName, person.documentID)
        if( validations[0] == ValidationResult.OK ){
            val response = repository.setPerson(person)
            if( response?.status.equals("Ok") ){
                return PersonSaveResult.OK(response?.person)
            }
            else{
                return PersonSaveResult.OperationFailed(response?.message ?: "", ValidationResult.INVALID_DOCUMENT_ID)
            }
        }
        else{
            return PersonSaveResult.InvalidInputs(validations)
        }
    }
}

class UpdatePersonUseCase(private val repository: PersonRepository) {

    suspend operator fun invoke(person: Person): PersonSaveResult {
        val validations = ValidatePersonUseCase.getFormValidation(person.fullName, person.documentID)
        if( validations[0] == ValidationResult.OK ){
            val response = repository.updatePerson(person)
            if( response?.status.equals("Ok") ){
                return PersonSaveResult.OK(response?.person)
            }
            else{
                return PersonSaveResult.OperationFailed(response?.message ?: "", ValidationResult.INVALID_DOCUMENT_ID)
            }
        }
        else{
            return PersonSaveResult.InvalidInputs(validations)
        }
    }
}