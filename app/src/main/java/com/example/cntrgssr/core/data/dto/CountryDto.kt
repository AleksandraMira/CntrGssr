package com.example.cntrgssr.core.data.dto

import com.example.cntrgssr.core.data.enums.Continent

data class CountryDto(
    val name: String,
    val capital: String,
    val continent: Continent,
)