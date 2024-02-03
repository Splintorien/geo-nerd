package com.geonerd.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import com.caverock.androidsvg.SVG
import com.geonerd.app.model.Country
import com.geonerd.app.model.CountryFlag
import com.geonerd.app.repository.CountryRepository
import com.geonerd.app.ui.theme.GeoNerdTheme

class DisplayFlagActivity : ComponentActivity() {
    private val countryRepository = CountryRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GeoNerdTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box {
                        CountryForm()
                    }
                }
            }
        }
    }

    @Composable
    fun CountryForm() {
        val countryRepository =
            remember {
                CountryRepository()
            }
        val countries =
            remember {
                mutableStateOf(listOf<Country>())
            }

        val randomCountry =
            remember {
                mutableStateOf<Country?>(null)
            }

        val flagSvg =
            remember {
                mutableStateOf<String?>(null)
            }

        val refreshTrigger = remember { mutableIntStateOf(0) }

        LaunchedEffect(countryRepository) {
            countries.value = countryRepository.getAllCountries()
            refreshTrigger.intValue++
        }

        LaunchedEffect(refreshTrigger.intValue) {
            if (countries.value.isNotEmpty()) {
                randomCountry.value = countries.value.random()
                flagSvg.value = countryRepository.getFlagSvg(randomCountry.value!!.flags.svg)
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Guess the country!")
            randomCountry.value?.let { country ->
                flagSvg.value?.let { svg ->
                    Flag(country.flags, svg)
                    GuessFields(country)
                }
            }
            Button(onClick = {
                refreshTrigger.intValue++
            }) {
                Text("Random")
            }
        }
    }

    @Composable
    fun Flag(countryFlag: CountryFlag, flagSvg: String) {
        val context = LocalContext.current
        val svgPainter = remember(flagSvg) {
            val svg = SVG.getFromString(flagSvg)
            val picture = svg.renderToPicture()
            object : Painter() {
                override val intrinsicSize: Size
                    get() = Size(picture.width.toFloat(), picture.height.toFloat())

                override fun DrawScope.onDraw() {
                    drawIntoCanvas { canvas ->
                        picture.draw(canvas.nativeCanvas)
                    }
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = svgPainter,
                contentDescription = countryFlag.alt,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

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
}
