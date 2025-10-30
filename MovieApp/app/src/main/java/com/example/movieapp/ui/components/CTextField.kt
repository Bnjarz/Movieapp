package com.example.movieapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.ui.theme.AlegreyaSansFontFamily

@Composable
fun CTextField(
    onValueChange: (String) -> Unit,
    hint: String,
    value: String,

    isError: Boolean = false,
    errorText: String? = null,
    isPassword: Boolean = false
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = hint,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = AlegreyaSansFontFamily,
                        color = Color(0xFFBEC2C2)
                    )
                )
            },

            isError = isError,
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text
            ),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,

            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 0.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,

                focusedIndicatorColor = if (isError) Color.Red else Color(0xFFBEC2C2),
                unfocusedIndicatorColor = if (isError) Color.Red else Color(0xFFBEC2C2),

                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White,
                errorIndicatorColor = Color.Red,
                errorCursorColor = Color.Red
            )
        )

        if (isError && errorText != null) {
            Text(
                text = errorText,
                color = Color.Red,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = AlegreyaSansFontFamily
                ),
                modifier = Modifier.padding(start = 16.dp, top = 4.dp, bottom = 8.dp)
            )
        } else {
            Spacer(modifier = Modifier.padding(bottom = 8.dp))
        }
    }
}