package com.anthonychaufrias.people.domain

import com.anthonychaufrias.people.core.Values
import com.anthonychaufrias.people.data.PersonRepository
import com.anthonychaufrias.people.data.model.Person
import com.anthonychaufrias.people.data.model.PersonSaveResult
import com.anthonychaufrias.people.data.model.ValidationResult
import javax.inject.Inject

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

class SetPersonUseCase @Inject constructor(private val repository: PersonRepository) {

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

class UpdatePersonUseCase @Inject constructor(private val repository: PersonRepository) {

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