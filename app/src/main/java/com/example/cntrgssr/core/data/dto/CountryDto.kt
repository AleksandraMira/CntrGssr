package com.example.cntrgssr.core.data.dto

import com.example.cntrgssr.core.data.entity.CountryEntity
import com.example.cntrgssr.core.data.enums.Continent

data class CountryDto(
    val name: String,
    val capitol: String,
    val continent: Continent,
) {
    fun toEntity() = CountryEntity(
        id = 0L,
        name = name,
        capitol = capitol,
        continent = continent,
    )
}