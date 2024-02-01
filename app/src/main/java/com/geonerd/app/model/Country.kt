package com.geonerd.app.model

data class Country (
    val flags: CountryFlag,
    val name: CountryName,
    val capital: List<String>
)

data class CountryFlag (
    val png: String,
    val svg: String,
    val alt: String
)

data class CountryName (
    val common: String,
    val official: String,
)
