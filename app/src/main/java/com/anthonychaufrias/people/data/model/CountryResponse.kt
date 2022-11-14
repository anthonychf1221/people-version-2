package com.anthonychaufrias.people.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CountryListResponse(
    @Expose @SerializedName("status") val status: String,
    @Expose @SerializedName("results") val results: List<Country>)
