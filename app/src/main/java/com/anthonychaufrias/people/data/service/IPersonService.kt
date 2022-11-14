package com.anthonychaufrias.people.data.service

import com.anthonychaufrias.people.data.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface IPersonService {

    @GET("personas")
    fun getPeopleList(@Query("busqueda") filter: String): Response<PersonListResponse>

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("insertar-persona")
    fun addPerson(
        @Body person: Person
    ): Response<PersonSaveResponse>

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("modificar-persona")
    fun updatePerson(
        @Body person: Person
    ): Response<PersonSaveResponse>

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("eliminar-persona")
    fun deletePerson(
        @Body person: Person
    ): Response<PersonSaveResponse>

}