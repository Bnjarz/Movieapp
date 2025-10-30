package com.example.movieapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.ui.theme.AlegreyaSansFontFamily

@Composable
fun CButton (
    onClick: () -> Unit = {},
    text: String,
    enabled: Boolean = true
){
    Button(
        onClick = onClick,
        enabled = enabled,

        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF310062),

            disabledContainerColor = Color(0xAA7C9A92)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {

        Text(
            text = text,
            style = TextStyle(
                fontSize = 22.sp,
                fontFamily = AlegreyaSansFontFamily,
                fontWeight = FontWeight(500),
                color = if (enabled) Color.White else Color.LightGray
            )
        )
    }
}