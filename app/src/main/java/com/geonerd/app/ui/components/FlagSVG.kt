package com.geonerd.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import com.caverock.androidsvg.SVG
import com.geonerd.app.model.CountryFlag

@Composable
fun FlagSVG(countryFlag: CountryFlag, flagSvg: String) {
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
