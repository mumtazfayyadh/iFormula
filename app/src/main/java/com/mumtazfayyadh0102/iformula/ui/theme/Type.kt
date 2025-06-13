package com.mumtazfayyadh0102.iformula.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mumtazfayyadh0102.iformula.R

val Titillium = FontFamily(
    Font(R.font.titilliumweb_regular)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Titillium,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Titillium,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Titillium,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Titillium,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Titillium,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Titillium,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Titillium,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
)
