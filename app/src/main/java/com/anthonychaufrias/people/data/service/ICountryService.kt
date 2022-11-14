package com.anthonychaufrias.people.data.service

import com.anthonychaufrias.people.data.model.CountryListResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ICountryService {

    @GET("paises")
    fun getCountryList(): Response<CountryListResponse>

}