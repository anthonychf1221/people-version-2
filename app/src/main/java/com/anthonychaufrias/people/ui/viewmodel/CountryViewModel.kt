package com.anthonychaufrias.people.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthonychaufrias.people.data.CountryRepository
import com.anthonychaufrias.people.data.model.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val repository: CountryRepository
) : ViewModel(){
    val liveDataCountryList = MutableLiveData<List<Country>>()
    val countryList  = mutableListOf<Country>()
    val countryNamesList = mutableListOf<String>()
    var selectedIndex: Int = 0

    fun loadCountryList(selectedId:Int){
        viewModelScope.launch {
            val list: List<Country> = repository.getCountryList()
            fillListOfCountries(list, selectedId);
            liveDataCountryList.postValue(list)
        }
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