package com.anthonychaufrias.people.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthonychaufrias.people.core.Values
import com.anthonychaufrias.people.data.PersonRepository
import com.anthonychaufrias.people.data.model.*
import com.anthonychaufrias.people.domain.SetPersonUseCase
import com.anthonychaufrias.people.domain.UpdatePersonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val repository: PersonRepository,
    val setPersonUseCase: SetPersonUseCase,
    val updatePersonUseCase: UpdatePersonUseCase
) : ViewModel(){
    val liveDataPeopleList = MutableLiveData<MutableList<Person>>()
    val peopleList = mutableListOf<Person>()
    val liveDataPeopleSave = MutableLiveData<PersonSaveResult>()

    fun loadPeopleList(filter: String){
        try{
            viewModelScope.launch {
                val list: MutableList<Person> = repository.getPeopleList(filter)
                peopleList.addAll(list)
                liveDataPeopleList.postValue(list)
            }
        }
        catch(e: Exception){
            print(e.message)
        }
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
        viewModelScope.launch {
            val result: PersonSaveResult = setPersonUseCase(person)
            liveDataPeopleSave.value = result
        }
    }

    private fun updatePerson(person: Person){
        viewModelScope.launch {
            val result: PersonSaveResult = updatePersonUseCase(person)
            liveDataPeopleSave.value = result
        }
    }

    fun deletePerson(person: Person){
        try{
            viewModelScope.launch {
                val deletedPerson: PersonSaveResponse? = repository.deletePerson(person)
                removePersonFromList(person)
                liveDataPeopleList.value = peopleList
            }
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