package com.mumtazfayyadh0102.iformula.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Light theme generator
private fun getLightColor(color: Color) = lightColorScheme(
    primary = color,
    primaryContainer = color,
    onPrimary = Color.White,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black
)

// Dark theme generator
private fun getDarkColor(color: Color) = darkColorScheme(
    primary = color,
    primaryContainer = color,
    onPrimary = Color.White,
    background = Color.Black,
    onBackground = Color.White,
    surface = Color.Black,
    onSurface = Color.White
)

@Composable
fun IFormulaTheme(
    selectedColor: String,
    content: @Composable () -> Unit
) {
    val primaryColor = when (selectedColor) {
        "red" -> ThemeRed
        "blue" -> ThemeBlue
        "green" -> ThemeGreen
        "yellow" -> ThemeYellow
        "black" -> ThemeBlack
        else -> ThemeRed
    }

    val colorScheme = if (selectedColor == "black") {
        getDarkColor(primaryColor)
    } else {
        getLightColor(primaryColor)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
