package com.anthonychaufrias.people.service

import com.anthonychaufrias.people.model.*
import retrofit2.Call
import retrofit2.http.*

interface IPersonService {

    @GET("personas")
    fun getPeopleList(@Query("busqueda") filter: String): Call<PersonListResponse>

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("insertar-persona")
    fun addPerson(
        @Body person: Person
    ): Call<PersonSaveResponse>

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("modificar-persona")
    fun updatePerson(
        @Body person: Person
    ): Call<PersonSaveResponse>


}