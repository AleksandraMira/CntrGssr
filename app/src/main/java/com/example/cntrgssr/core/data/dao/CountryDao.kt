package com.example.cntrgssr.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cntrgssr.core.data.entity.CountryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {
    @Insert
    suspend fun insertCountries(countries: List<CountryEntity>)

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()

    @Query("SELECT id FROM country ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomCountryId(): Long?

    @Query("SELECT * FROM country WHERE id = :id")
    suspend fun getCountryById(id: Long): CountryEntity?

    @Query("SELECT name FROM country WHERE id = :id")
    suspend fun getCountryNameById(id: Long): String
}