package com.anthonychaufrias.people.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PersonListResponse(
    @Expose @SerializedName("status") val status: String,
    @Expose @SerializedName("results") val results: MutableList<Person>
)

data class PersonSaveResponse(
    @Expose @SerializedName("status") val status: String,
    @Expose @SerializedName("result") val person: Person,
    @Expose @SerializedName("message") val message: String
)

sealed class PersonSaveResult {
    class OK(val person: Person?): PersonSaveResult()
    class InvalidInputs(val errors: List<ValidationResult>): PersonSaveResult()
    class OperationFailed(val message: String, val type: ValidationResult) : PersonSaveResult()
}
enum class ValidationResult{
    OK, INVALID_NAME, INVALID_DOCUMENT_ID, FAILURE
}