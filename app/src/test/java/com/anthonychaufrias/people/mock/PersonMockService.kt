package com.anthonychaufrias.people.mock

import com.anthonychaufrias.people.data.model.Person
import com.anthonychaufrias.people.data.model.PersonListResponse
import com.anthonychaufrias.people.data.model.PersonSaveResponse
import com.anthonychaufrias.people.data.service.IPersonService
import retrofit2.Response

class PersonMockService: IPersonService {
    private val results = mutableListOf<Person>()
    init {
        loadFakeData()
    }

    companion object{
        val ANTHONY_CHAU_FRIAS = Person(1, "Anthony Chau", "12345678", 1, "Perú")
        val GUILLERMINA_LUBARTI = Person(5, "Guillermina Lubarti", "55555555", 3, "Argentina")
    }

    override suspend fun getPeopleList(search: String): Response<PersonListResponse> {
        var listFiltered = mutableListOf<Person>()
        val filter = search.lowercase()

        listFiltered = if (search.isEmpty())
            results
        else {
            for (item in results.indices) {
                if( results[item].fullName.lowercase().contains(filter) ){
                    listFiltered.add(results[item])
                }
            }
            listFiltered
        }

        val listResponse = PersonListResponse("Ok", listFiltered)
        return Response.success(listResponse)
    }

    override suspend fun addPerson(person: Person): Response<PersonSaveResponse> {
        var status = ""
        var message = ""
        if(findPersonBy(person.documentID, person.personID) == null ){
            person.personID = results.size
            status = "Ok"
        }
        else{
            status = "Failed"
            message = "Documento duplicado. Por favor ingrese otro"
        }

        val saveResponse = PersonSaveResponse(status, person, message)
        return Response.success(saveResponse)
    }

    override suspend fun updatePerson(person: Person): Response<PersonSaveResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun deletePerson(person: Person): Response<PersonSaveResponse> {
        TODO("Not yet implemented")
    }

    private fun findPersonBy(documentID: String, personID: Int): Person?{
        return results.find { person ->
            person.documentID == documentID &&
            person.personID != personID
        }
    }

    private fun loadFakeData(){
        results.add(ANTHONY_CHAU_FRIAS)
        results.add(Person(2, "Juana Miranda", "22222222", 2, "Colombia"))
        results.add(Person(3, "Francisco Tirado", "33333333", 2, "Colombia"))
        results.add(Person(4, "Juanita Gutierrez", "44444444", 1, "Perú"))
        results.add(GUILLERMINA_LUBARTI)
        results.add(Person(6, "Juan Perez", "55558888", 3, "Argentina"))
    }

}