package com.anthonychaufrias.people.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anthonychaufrias.people.core.RetrofitHelper
import com.anthonychaufrias.people.model.Person
import com.anthonychaufrias.people.model.PersonListResponse
import com.anthonychaufrias.people.service.IPersonService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonViewModel : ViewModel(){
    private val retrofit = RetrofitHelper.getRetrofit()
    private val service: IPersonService = retrofit.create(IPersonService::class.java)

    val liveDataPeopleList = MutableLiveData<MutableList<Person>>()
    val peopleList = mutableListOf<Person>()

    fun loadPeopleList(filter: String){
        val call = service.getPeopleList(filter)
        call.enqueue(object : Callback<PersonListResponse>{
            override fun onResponse(call: Call<PersonListResponse>,response: Response<PersonListResponse>) {
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

}