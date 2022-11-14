package com.anthonychaufrias.people.data.service

import com.anthonychaufrias.people.core.RetrofitHelper
import com.anthonychaufrias.people.data.model.Person
import com.anthonychaufrias.people.data.model.PersonListResponse
import com.anthonychaufrias.people.data.model.PersonSaveResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class PersonService {
    private val retrofit = RetrofitHelper.getRetrofit()
    private val service = retrofit.create(IPersonService::class.java)

    suspend fun getPeopleList(filter: String): MutableList<Person> {
        return withContext(Dispatchers.IO){
            val response: Response<PersonListResponse> = service.getPeopleList(filter)
            response.body()?.results ?: mutableListOf<Person>()
        }
    }
    suspend fun setPerson(person: Person): PersonSaveResponse? {
        return withContext(Dispatchers.IO){
            val response: Response<PersonSaveResponse> = service.addPerson(person)
            response.body()
        }
    }
    suspend fun updatePerson(person: Person): PersonSaveResponse? {
        return withContext(Dispatchers.IO){
            val response: Response<PersonSaveResponse> = service.updatePerson(person)
            response.body()
        }
    }
    suspend fun deletePerson(person: Person): PersonSaveResponse? {
        return withContext(Dispatchers.IO){
            val response: Response<PersonSaveResponse> = service.deletePerson(person)
            response.body()
        }
    }
}