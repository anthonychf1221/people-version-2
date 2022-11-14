package com.anthonychaufrias.people.data.service

import com.anthonychaufrias.people.data.model.*
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

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("eliminar-persona")
    fun deletePerson(
        @Body person: Person
    ): Call<PersonSaveResponse>

}