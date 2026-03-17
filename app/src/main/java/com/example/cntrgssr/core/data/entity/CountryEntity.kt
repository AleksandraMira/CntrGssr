package com.example.cntrgssr.core.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.cntrgssr.core.data.enums.Continent

@Entity(
    tableName = "country",
    indices = [Index(value = ["name"], unique = true)],
)
data class CountryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val continent: Continent,
)
