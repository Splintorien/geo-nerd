package com.geonerd.app.ui.components

import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import com.geonerd.app.model.Country

@Composable
fun GuessFields(
    country: Country,
    countriesNames: MutableList<String>,
    countriesCapitals: MutableList<String>,
    onCorrectAnswer: () -> Unit
) {
    var guessedCountry by remember(country) { mutableStateOf(TextFieldValue("")) }
    var guessedCapital by remember(country) { mutableStateOf(TextFieldValue("")) }

    var namesSuggestions by remember { mutableStateOf(emptyList<String>()) }
    var capitalsSuggestions by remember { mutableStateOf(emptyList<String>()) }

    var namesExpanded by remember { mutableStateOf(false) }
    var capitalsExpanded by remember { mutableStateOf(false) }

    var result by remember { mutableStateOf("") }
    val context = LocalContext.current

    AutocompleteTextField(
        label = "Country",
        value = guessedCountry,
        setValue = {
            guessedCountry = it
            namesExpanded = true
            namesSuggestions = countriesNames.filter {
                    name ->
                name.startsWith(guessedCountry.text, ignoreCase = true)
            }.take(3)
        },
        onDismissRequest = {
            namesExpanded = false
        },
        dropDownExpanded = namesExpanded,
        list = namesSuggestions
    )

    AutocompleteTextField(
        label = "Capital",
        value = guessedCapital,
        setValue = {
            guessedCapital = it
            capitalsExpanded = true
            capitalsSuggestions = countriesCapitals.filter {
                    capital ->
                capital.startsWith(guessedCapital.text, ignoreCase = true)
            }.take(3)
        },
        onDismissRequest = {
            capitalsExpanded = false
        },
        dropDownExpanded = capitalsExpanded,
        list = capitalsSuggestions
    )

    Button(onClick = {
        result = if (
            guessedCountry.text.equals(country.name.common, ignoreCase = true) &&
            guessedCapital.text.equals(country.capital.first(), ignoreCase = true)
        ) {
            onCorrectAnswer()
            "Correct!"
        } else {
            "Incorrect!"
        }
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
    }) {
        Text("Check")
    }
}
