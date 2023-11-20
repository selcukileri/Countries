package com.selcukileri.kotlincountries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.selcukileri.kotlincountries.model.Country

class CountryVM : ViewModel() {
    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(){
        val country = Country("Turkey","Ankara","Asia","TRY","Turkish","www.ss.com")
        countryLiveData.value = country
    }
}