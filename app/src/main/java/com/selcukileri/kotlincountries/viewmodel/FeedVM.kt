package com.selcukileri.kotlincountries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.selcukileri.kotlincountries.model.Country

class FeedVM : ViewModel(){
    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData() {
        val country = Country("Turkey","Ankara","Asia","TRY","Turkish","www.ss.com")
        val country2 = Country("France","Paris","Europe","EUR","French","www.ss.com")
        val country3 = Country("Germany","Berlin","Europe","EUR","German","www.ss.com")
        val countryList = arrayListOf<Country>(country,country2,country3)
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }
}