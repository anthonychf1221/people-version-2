package com.anthonychaufrias.people.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anthonychaufrias.people.core.RetrofitHelper
import com.anthonychaufrias.people.data.model.Country
import com.anthonychaufrias.people.data.model.CountryListResponse
import com.anthonychaufrias.people.data.service.ICountryService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountryViewModel : ViewModel(){
    private val retrofit = RetrofitHelper.getRetrofit()
    private val service: ICountryService = retrofit.create(ICountryService::class.java)

    val liveDataCountryList = MutableLiveData<List<Country>>()
    val countryList  = mutableListOf<Country>()
    val countryNamesList = mutableListOf<String>()
    var selectedIndex: Int = 0

    fun loadCountryList(selectedId:Int){
        val call = service.getCountryList()
        call.enqueue(object : Callback<CountryListResponse> {
            override fun onResponse(call: Call<CountryListResponse>, response: Response<CountryListResponse>) {
                if( response.body() == null ){
                    return
                }
                if( !response.body()?.status.equals("Ok") ){
                    return
                }
                response.body()?.results?.let { list ->
                    fillListOfCountries(list, selectedId)
                }
            }
            override fun onFailure(call: Call<CountryListResponse>, t: Throwable) {
                call.cancel()
            }
        })
    }

    private fun fillListOfCountries(list: List<Country>, selectedId:Int){
        countryList.clear()
        countryList.addAll(list)
        for (item in countryList.indices) {
            countryNamesList.add(countryList[item].countryName)
            if( countryList[item].countryID == selectedId ){
                selectedIndex = item
            }
        }
        liveDataCountryList.postValue(list)
    }

}