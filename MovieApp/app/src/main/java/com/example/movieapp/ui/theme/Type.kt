package com.example.movieapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.movieapp.R

val AlegreyaSansFontFamily = FontFamily(
    Font(R.font.alegreyasans_regular, FontWeight.Normal),
    Font(R.font.alegreyasans_bold, FontWeight.Bold)
)

val AlegreyaFontFamily = FontFamily(
    Font(R.font.alegreya_regular, FontWeight.Normal),
    Font(R.font.alegreya_bold, FontWeight.Bold)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = AlegreyaSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = AlegreyaFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    )
)
