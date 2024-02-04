package com.geonerd.app.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.geonerd.app.model.Country

@Composable
fun GuessFields(country: Country) {
    var guessedCountry by remember(country) { mutableStateOf("") }
    var guessedCapital by remember(country) { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    val context = LocalContext.current

    OutlinedTextField(
        value = guessedCountry,
        onValueChange = { guessedCountry = it },
        label = { Text("Country") },
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        value = guessedCapital,
        onValueChange = { guessedCapital = it },
        label = { Text("Capital") },
        modifier = Modifier.fillMaxWidth()
    )

    Button(onClick = {
        result = if (
            guessedCountry.equals(country.name.common, ignoreCase = true) &&
            guessedCapital.equals(country.capital.first(), ignoreCase = true)
        ) {
            "Correct!"
        } else {
            "Incorrect!"
        }
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
    }) {
        Text("Check")
    }
}
