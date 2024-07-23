package com.geonerd.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
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
import com.geonerd.app.ui.components.HelpIcon
import com.geonerd.app.viewmodels.CountryViewModel
import com.geonerd.app.viewmodels.CountryViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
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
    val localCountry = country
    val localFlagSvg = flagSvg

    if (localCountry is Country && localFlagSvg is String) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Geo Nerd")
                    },
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    ),
                    actions = {
                        HelpIcon(localCountry)
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        viewModel.getRandomCountry()
                    }
                ) {
                    Icon(Icons.Filled.Refresh, contentDescription = "Random")
                }
            }
        ) { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Text(text = "Guess the country and capital!")

                FlagSVG(localCountry.flags, localFlagSvg)
                GuessFields(
                    localCountry,
                    countriesNames,
                    countriesCapitals,
                    onCorrectAnswer = {
                        viewModel.getRandomCountry()
                    }
                )
            }
        }
    }
}
