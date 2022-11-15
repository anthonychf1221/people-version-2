package com.anthonychaufrias.people.data.service

import com.anthonychaufrias.people.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface IPersonService {

    @GET("personas")
    suspend fun getPeopleList(@Query("busqueda") filter: String): Response<PersonListResponse>

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("insertar-persona")
    suspend fun addPerson(
        @Body person: Person
    ): Response<PersonSaveResponse>

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("modificar-persona")
    suspend fun updatePerson(
        @Body person: Person
    ): Response<PersonSaveResponse>

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("eliminar-persona")
    suspend fun deletePerson(
        @Body person: Person
    ): Response<PersonSaveResponse>

}