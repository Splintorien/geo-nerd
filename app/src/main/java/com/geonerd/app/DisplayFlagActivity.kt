package com.geonerd.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import coil.compose.rememberImagePainter
import com.caverock.androidsvg.SVG
import com.geonerd.app.model.Country
import com.geonerd.app.model.CountryFlag
import com.geonerd.app.repository.CountryRepository
import com.geonerd.app.ui.theme.GeoNerdTheme
import kotlinx.coroutines.launch

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
        val countryRepository = remember {
            CountryRepository()
        }
        val countries = remember {
            mutableStateOf(listOf<Country>())
        }

        val randomCountry = remember {
            mutableStateOf<Country?>(null)
        }

        val flagSvg = remember {
            mutableStateOf<String?>(null)
        }

        LaunchedEffect(countryRepository) {
            countries.value = countryRepository.getAllCountries()
            randomCountry.value = countries.value.random()
            flagSvg.value = countryRepository.getFlagSvg(randomCountry.value!!.flags.svg)
        }

        randomCountry.value?.let { country ->
            flagSvg.value?.let { svg ->
                Flag(country.flags, svg)
            }
        }
    }

    @Composable
    fun Flag (countryFlag: CountryFlag, flagSvg: String) {
        val context = LocalContext.current
        val svgImage = remember(flagSvg) {
            val svg = SVG.getFromString(flagSvg)
            svg.renderToPicture()
        }

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Guess the country!")
            Box {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawIntoCanvas { canvas ->
                        svgImage.draw(canvas.nativeCanvas)
                    }
                }
            }
        }
    }
}
