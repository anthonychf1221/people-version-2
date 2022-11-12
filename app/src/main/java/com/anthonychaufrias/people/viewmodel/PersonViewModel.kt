package com.anthonychaufrias.people.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anthonychaufrias.people.core.RetrofitHelper
import com.anthonychaufrias.people.core.Values
import com.anthonychaufrias.people.model.*
import com.anthonychaufrias.people.service.IPersonService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonViewModel : ViewModel(){
    private val retrofit = RetrofitHelper.getRetrofit()
    private val service: IPersonService = retrofit.create(IPersonService::class.java)

    val liveDataPeopleList = MutableLiveData<MutableList<Person>>()
    val peopleList = mutableListOf<Person>()
    val liveDataPeopleSave = MutableLiveData<PersonSaveResult>()

    fun loadPeopleList(filter: String){
        val call = service.getPeopleList(filter)
        call.enqueue(object : Callback<PersonListResponse>{
            override fun onResponse(call: Call<PersonListResponse>, response: Response<PersonListResponse>) {
                if( response.body() == null ){
                    return
                }
                if( !response.body()?.status.equals("Ok") ){
                    return
                }
                response.body()?.results?.let { list ->
                    peopleList.addAll(list)
                    liveDataPeopleList.postValue(list)
                }
            }
            override fun onFailure(call: Call<PersonListResponse>, t: Throwable) {
                call.cancel()
            }
        })
    }

    private fun getFormValidation(name: String, docID: String):MutableList<ValidationResult> {
        val validations = mutableListOf<ValidationResult>()
        if( name.isEmpty() ){
            validations.add(ValidationResult.INVALID_NAME)
        }
        if( docID.length != Values.PERSON_DOCUMENT_LENGTH ){
            validations.add(ValidationResult.INVALID_DOCUMENT_ID)
        }
        if( validations.size == 0 ){
            validations.add(ValidationResult.OK)
        }
        return validations
    }

    fun savePerson(person: Person, action: Int){
        try{
            if( action == Values.INSERT ){
                addPerson(person)
            }
            else{
                updatePerson(person)
            }
        }
        catch(e: Exception){
            print(e.message)
        }
    }

    private fun addPerson(person: Person){
        val validations = getFormValidation(person.fullName, person.documentID)
        if( validations[0] != ValidationResult.OK ){
            liveDataPeopleSave.postValue(PersonSaveResult.InvalidInputs(validations))
            return
        }

        val call = service.addPerson(person)
        call.enqueue(object : Callback<PersonSaveResponse>{
            override fun onResponse(call: Call<PersonSaveResponse>,response: Response<PersonSaveResponse>) {
                if( response.body() == null ){
                    liveDataPeopleSave.postValue(PersonSaveResult.OperationFailed("", ValidationResult.FAILURE))
                    return
                }
                if( !response.body()?.status.equals("Ok") ){
                    liveDataPeopleSave.postValue(PersonSaveResult.OperationFailed(response.body()?.message ?: "", ValidationResult.INVALID_DOCUMENT_ID))
                    return
                }
                response.body()?.person.let { person ->
                    liveDataPeopleSave.postValue(PersonSaveResult.OK(person))
                }
            }
            override fun onFailure(call: Call<PersonSaveResponse>, t: Throwable) {
                call.cancel()
                liveDataPeopleSave.postValue(PersonSaveResult.OperationFailed(t.message ?: "", ValidationResult.FAILURE))
            }
        })
    }

    private fun updatePerson(person: Person){
        val validations = getFormValidation(person.fullName, person.documentID)
        if( validations[0] != ValidationResult.OK ){
            liveDataPeopleSave.postValue(PersonSaveResult.InvalidInputs(validations))
            return
        }
        val call = service.updatePerson(person)
        call.enqueue(object : Callback<PersonSaveResponse>{
            override fun onResponse(call: Call<PersonSaveResponse>,response: Response<PersonSaveResponse>) {
                if( response.body() == null ){
                    liveDataPeopleSave.postValue(PersonSaveResult.OperationFailed("", ValidationResult.FAILURE))
                    return
                }
                if( !response.body()?.status.equals("Ok") ){
                    liveDataPeopleSave.postValue(PersonSaveResult.OperationFailed(response.body()?.message ?: "", ValidationResult.INVALID_DOCUMENT_ID))
                    return
                }
                response.body()?.person.let { person ->
                    liveDataPeopleSave.postValue(PersonSaveResult.OK(person))
                }
            }
            override fun onFailure(call: Call<PersonSaveResponse>, t: Throwable) {
                call.cancel()
                liveDataPeopleSave.postValue(PersonSaveResult.OperationFailed(t.message ?: "", ValidationResult.FAILURE))
            }
        })
    }

    fun deletePerson(person: Person){
        try{
            val call = service.deletePerson(person)
            call.enqueue(object : Callback<PersonSaveResponse>{
                override fun onResponse(call: Call<PersonSaveResponse>,response: Response<PersonSaveResponse>) {
                    if( response.body() == null ){
                        return
                    }
                    if( !response.body()?.status.equals("Ok") ){
                        return
                    }
                    response.body()?.person.let {
                        removePersonFromList(person)
                    }
                }
                override fun onFailure(call: Call<PersonSaveResponse>, t: Throwable) {
                    call.cancel()
                }
            })
        }
        catch(e: Exception){
            print(e.message)
        }
    }

    fun refreshList(person: Person, action: Int){
        if( action == Values.INSERT ){
            addPersonToList(person)
        }
        else{
            updatePersonFromList(person)
        }
    }

    private fun addPersonToList(person: Person){
        peopleList.add(person)
        liveDataPeopleList.value = peopleList
    }
    private fun updatePersonFromList(person: Person){
        for (item in peopleList.indices) {
            if( peopleList[item].personID == person.personID ){
                peopleList[item] = person
            }
        }
        liveDataPeopleList.value = peopleList
    }
    private fun removePersonFromList(person: Person){
        for (item in peopleList.indices) {
            if( peopleList[item].personID == person.personID ){
                peopleList.removeAt(item)
            }
        }
        liveDataPeopleList.value = peopleList
    }
}