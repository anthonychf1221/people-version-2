package com.anthonychaufrias.people.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Country(
    @Expose @SerializedName("idPais") val countryID: Int,
    @Expose @SerializedName("nombre") val countryName: String)
