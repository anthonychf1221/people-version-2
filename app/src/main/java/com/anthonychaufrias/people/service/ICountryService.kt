package com.anthonychaufrias.people.service

import com.anthonychaufrias.people.model.CountryListResponse
import retrofit2.Call
import retrofit2.http.GET

interface ICountryService {

    @GET("paises")
    fun getCountryList(): Call<CountryListResponse>

}