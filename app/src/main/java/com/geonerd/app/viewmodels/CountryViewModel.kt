package com.geonerd.app.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.geonerd.app.model.Country
import com.geonerd.app.repository.CountryRepository
import kotlinx.coroutines.launch

class CountryViewModel(private val countryRepository: CountryRepository) : ViewModel() {
    val countries = liveData {
        emit(countryRepository.getAllCountries())
    }
    val currentCountry = MutableLiveData<Country>()
    val currentFlagSvg = MutableLiveData<String>()

    fun getRandomCountry() {
        viewModelScope.launch {
            currentCountry.value = countries.value?.random()
            val localCountry = currentCountry.value
            if (localCountry is Country) {
                currentFlagSvg.value = countryRepository.getFlagSvg(localCountry.flags.svg)
            }
        }
    }

    fun getAllCountriesNames(): List<String> {
        return countries.value?.map { it.name.common } ?: emptyList()
    }

    fun getAllCountriesCapitals(): List<String> {
        return countries.value?.flatMap { it.capital } ?: emptyList()
    }
}

class CountryViewModelFactory(
    private val repository: CountryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CountryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
