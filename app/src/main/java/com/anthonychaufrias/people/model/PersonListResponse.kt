package com.anthonychaufrias.people.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PersonListResponse(
    @Expose @SerializedName("status") val status: String,
    @Expose @SerializedName("results") val results: MutableList<Person>
)
