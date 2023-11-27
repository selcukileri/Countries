package com.selcukileri.kotlincountries.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.selcukileri.kotlincountries.model.Country
import com.selcukileri.kotlincountries.service.CountryAPIService
import com.selcukileri.kotlincountries.service.CountryDatabase
import com.selcukileri.kotlincountries.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedVM(application: Application) : BaseVM(application) {
    private val countryAPIService = CountryAPIService()
    private val disposable = CompositeDisposable()
    private var customPreferences = CustomSharedPreferences(getApplication())
    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    fun refreshData() {
        countryLoading.value = true
        val updateTime = customPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSQLite()
        } else {
            getDataFromAPI()
        }
    }
    fun refreshFromAPI(){
        getDataFromAPI()
    }

    private fun getDataFromSQLite() {
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "countries from SQLite", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDataFromAPI() {
        countryLoading.value = true
        disposable.add(
            countryAPIService.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(t: List<Country>) {
                        storeInSQLite(t)
                        Toast.makeText(getApplication(), "countries from API", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        println("countryLoading {(countryLoading)}")
                        countryError.value = true
                        e.printStackTrace()

                    }

                })
        )
    }

    private fun showCountries(countryList: List<Country>) {
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInSQLite(list: List<Country>) {
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].uuid = listLong[i].toInt()
                i++
            }
            showCountries(list)
        }
        customPreferences.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}