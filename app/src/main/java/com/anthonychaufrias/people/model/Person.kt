package com.anthonychaufrias.people.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Person(
    @Expose @SerializedName("idPersona") var personID: Int,
    @Expose @SerializedName("nombres") var fullName: String,
    @Expose @SerializedName("documento") var documentID: String,
    @Expose @SerializedName("idPais") var countryID: Int,
    @Expose @SerializedName("pais") var countryName: String): Parcelable
