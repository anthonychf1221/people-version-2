package com.anthonychaufrias.people.data

import com.anthonychaufrias.people.data.model.Person
import com.anthonychaufrias.people.data.model.PersonSaveResponse
import com.anthonychaufrias.people.data.service.PersonService

class PersonRepository(private val api: PersonService) {

    suspend fun getPeopleList(filter: String): MutableList<Person> {
        return api.getPeopleList(filter)
    }
    suspend fun setPerson(person: Person): PersonSaveResponse? {
        return api.setPerson(person)
    }
    suspend fun updatePerson(person: Person): PersonSaveResponse? {
        return api.updatePerson(person)
    }
    suspend fun deletePerson(person: Person): PersonSaveResponse? {
        return api.deletePerson(person)
    }
}