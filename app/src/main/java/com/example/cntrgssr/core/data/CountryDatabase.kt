package com.example.cntrgssr.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cntrgssr.core.data.dao.CountryDao
import com.example.cntrgssr.core.data.entity.CountryEntity

@Database(
    entities = [CountryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CountryDatabase: RoomDatabase() {
    abstract val countryDao: CountryDao
}