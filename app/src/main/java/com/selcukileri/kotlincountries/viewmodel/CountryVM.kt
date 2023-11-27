package com.selcukileri.kotlincountries.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.selcukileri.kotlincountries.model.Country
import com.selcukileri.kotlincountries.service.CountryDatabase
import kotlinx.coroutines.launch
import java.util.UUID

class CountryVM(application: Application) : BaseVM(application) {
    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(uuid: Int){
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            val country = dao.getCountry(uuid)
            countryLiveData.value = country
        }
    }
}