package com.geonerd.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.DialogProperties
import com.geonerd.app.model.Country

@Composable
fun HelpIcon(country: Country) {
    val openDialog = remember { mutableStateOf(false) }

    IconButton(onClick = { openDialog.value = true }) {
        Icon(
            Icons.Filled.Info,
            contentDescription = "Help"
        )
    }

    when {
        openDialog.value -> {
            CustomDialog(openDialog, country)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDialog(openDialog: MutableState<Boolean>, country: Country) {
    AlertDialog(
        onDismissRequest = { openDialog.value = false },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface
        ) {
            CountryInfo(country = country)
        }
    }
}
