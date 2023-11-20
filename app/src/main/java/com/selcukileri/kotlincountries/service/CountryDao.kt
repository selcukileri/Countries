package com.selcukileri.kotlincountries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.selcukileri.kotlincountries.model.Country
@Dao
interface CountryDao {

    @Insert
    suspend fun insertAll(vararg countries: Country) : List<Long>
    //vararg -> Multiple country object
    //List<Long> -> primary keys

    @Query("SELECT * FROM country")
    suspend fun getAllCountries() : List<Country>

    @Query("SELECT * FROM country WHERE uuid = :countryId")
    suspend fun getCountry(countryId: Int) : Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()

}