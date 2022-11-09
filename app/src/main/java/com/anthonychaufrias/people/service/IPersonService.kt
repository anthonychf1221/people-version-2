package com.anthonychaufrias.people.service

import com.anthonychaufrias.people.model.*
import retrofit2.Call
import retrofit2.http.*

interface IPersonService {

    @GET("personas")
    fun getPeopleList(@Query("busqueda") filter: String): Call<PersonListResponse>

}