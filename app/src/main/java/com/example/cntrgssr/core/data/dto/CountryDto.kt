package com.example.cntrgssr.core.data.dto

import com.example.cntrgssr.core.data.entity.CountryEntity

data class CountryDto(
    val name: String,
) {
    fun toEntity() = CountryEntity(
        id = 0L,
        name = name,
    )
}