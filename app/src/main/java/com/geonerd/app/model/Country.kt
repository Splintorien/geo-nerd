package com.geonerd.app.model

data class Country(
    val flags: CountryFlag,
    val name: CountryName,
    val capital: List<String>
) {
    override fun toString(): String {
        val capitalsString = if (capital.isNotEmpty()) capital.joinToString(separator = ", ") else "Unknown"
        return """
            Country: ${name.common}, Official Name: ${name.official}
            Capitals: $capitalsString
        """.trimIndent()
    }
}

data class CountryFull(
    val flags: CountryFlag,
    val name: CountryName,
    val capitals: List<String>,
    val region: String,
    val subregion: String,
    val population: Int,
    val landlocked: Boolean,
    val currencies: List<CountryCurrency>,
    val languages: List<String>,
    val timezones: List<String>,
    val continents: List<String>,
    val coatOfArms: CountryCoatOfArms,
    val location: CountryLocation,
    val unMember: Boolean,
) {
    override fun toString(): String {
        val capitalsString = if (capitals.isNotEmpty()) capitals.joinToString(separator = ", ") else "Unknown"
        val currenciesString = currencies.joinToString(separator = ", ") { "${it.name} (${it.symbol})" }
        val languagesString = languages.joinToString(separator = ", ")
        return """
            Country: ${name.common}, Official Name: ${name.official}
            Capitals: $capitalsString, Region: $region, Subregion: $subregion
            Population: $population, Landlocked: $landlocked
            Currencies: $currenciesString, Languages: $languagesString
            Timezones: ${timezones.joinToString(", ")}, Continents: ${continents.joinToString(", ")}
            UN Member: $unMember
        """.trimIndent()
    }
}

data class CountryFlag(
    val png: String,
    val svg: String,
    val alt: String
)

data class CountryName(
    val common: String,
    val official: String
)

data class CountryCurrency(
    val name: String,
    val symbol: String,
)

data class CountryCoatOfArms(
    val png: String,
    val svg: String
)

data class CountryLocation(
    val lat: Double,
    val long: Double
)

fun Country.formatForHelp(): String {
    val capitals = if (capital.isNotEmpty()) capital.joinToString(separator = ", ") else "Unknown";

    return "Country: ${name.common}\nCapital(s): $capitals";
}
