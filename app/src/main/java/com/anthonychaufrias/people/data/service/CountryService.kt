package com.anthonychaufrias.people.data.service

import com.anthonychaufrias.people.data.model.Country
import com.anthonychaufrias.people.data.model.CountryListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class CountryService @Inject constructor(private val service: ICountryService){

    suspend fun getCountryList(): List<Country> {
        return withContext(Dispatchers.IO){
            val response: Response<CountryListResponse> = service.getCountryList()
            response.body()?.results ?: emptyList()
        }
    }
}