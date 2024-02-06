package com.geonerd.app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.geonerd.app.model.Country
import com.geonerd.app.repository.CountryRepository
import com.geonerd.app.ui.components.FlagSVG
import com.geonerd.app.ui.components.GuessFields
import com.geonerd.app.viewmodels.CountryViewModel
import com.geonerd.app.viewmodels.CountryViewModelFactory

@Composable
fun GuessFromFlagScreen() {
    val viewModel: CountryViewModel =
        viewModel(factory = CountryViewModelFactory(CountryRepository()))
    val country by viewModel.currentCountry.observeAsState(initial = null)
    val flagSvg by viewModel.currentFlagSvg.observeAsState(initial = null)
    val countries by viewModel.countries.observeAsState(initial = emptyList())
    val countriesNames = remember { mutableListOf<String>() }
    val countriesCapitals = remember { mutableListOf<String>() }

    LaunchedEffect(countries) {
        viewModel.getRandomCountry()
        countriesNames.addAll(viewModel.getAllCountriesNames())
        countriesCapitals.addAll(viewModel.getAllCountriesCapitals())
    }

    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            val localCountry = country
            val localFlagSvg = flagSvg

            Text(text = "Guess the country and capital!")

            if (localCountry is Country && localFlagSvg is String) {
                FlagSVG(localCountry.flags, localFlagSvg)
                GuessFields(
                    localCountry,
                    countriesNames,
                    countriesCapitals,
                    onCorrectAnswer = {
                        viewModel.getRandomCountry()
                    }
                )
                Button(onClick = {
                    viewModel.getRandomCountry()
                }) {
                    Text("Random")
                }
            }
        }
    }
}
