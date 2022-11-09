package com.anthonychaufrias.people.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Person(
    @Expose @SerializedName("idPersona") var personID: Int,
    @Expose @SerializedName("nombres") var fullName: String,
    @Expose @SerializedName("documento") var documentID: String,
    @Expose @SerializedName("idPais") var countryID: Int,
    @Expose @SerializedName("pais") var countryName: String): Serializable
