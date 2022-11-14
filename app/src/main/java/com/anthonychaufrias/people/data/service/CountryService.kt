package com.anthonychaufrias.people.data.service

import com.anthonychaufrias.people.core.RetrofitHelper
import com.anthonychaufrias.people.data.model.Country
import com.anthonychaufrias.people.data.model.CountryListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class CountryService {
    private val retrofit = RetrofitHelper.getRetrofit()
    private val service = retrofit.create(ICountryService::class.java)

    suspend fun getCountryList(): List<Country> {
        return withContext(Dispatchers.IO){
            val response: Response<CountryListResponse> = service.getCountryList()
            response.body()?.results ?: emptyList()
        }
    }
}