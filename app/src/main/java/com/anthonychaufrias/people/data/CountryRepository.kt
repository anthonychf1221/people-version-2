package com.anthonychaufrias.people.data

import com.anthonychaufrias.people.data.model.Country
import com.anthonychaufrias.people.data.service.CountryService
import javax.inject.Inject

class CountryRepository @Inject constructor(private val api: CountryService) {

    suspend fun getCountryList(): List<Country> {
        return api.getCountryList()
    }
}