package com.example.movieapp.ui.screens.auth


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import com.example.movieapp.viewmodel.auth.SignupViewModelFactory

import androidx.navigation.NavHostController

import com.example.movieapp.R
import com.example.movieapp.ui.components.CButton
import com.example.movieapp.ui.components.CTextField

import com.example.movieapp.ui.theme.AlegreyaFontFamily
import com.example.movieapp.ui.theme.AlegreyaSansFontFamily
import com.example.movieapp.viewmodel.auth.SignupViewModel

@Composable
fun SignupScreen(
    navController: NavHostController,
){
    val context = LocalContext.current
    val viewModel: SignupViewModel = viewModel(factory = SignupViewModelFactory(context))
    val state = viewModel.state.collectAsState().value
    val errors = viewModel.errors.collectAsState().value

    Surface (
        modifier = Modifier.fillMaxSize()
    ){
        Box(modifier = Modifier.fillMaxSize()){
            Image(painter = painterResource(id = R.drawable.fondo2),
                null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ){
                Image(painter = painterResource(id = R.drawable.logo ),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 60.dp)
                        .height(80.dp)
                        .align(Alignment.Start)
                        .offset(x=(-20).dp)
                        .padding(bottom = 24.dp)
                )
                Text(text="Registrarse",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontFamily = AlegreyaFontFamily,
                        fontWeight = FontWeight(500),
                        color = Color.White
                    ),
                    modifier = Modifier.align(Alignment.Start)
                )
                Text("Registrate ahora para poder acceder al catálogo.",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = AlegreyaSansFontFamily,
                        color = Color(0xB2FFFFFF)
                    ),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 16.dp)
                        .padding(bottom = 24.dp)
                )

                CTextField(
                    hint = "Nombre Completo",
                    value = state.nombreCompleto,
                    onValueChange = viewModel::onNombreCompletoChange,
                    isError = errors.nombreCompletoError != null,
                    errorText = errors.nombreCompletoError
                )

                Spacer(modifier = Modifier.height(12.dp))

                CTextField(
                    hint = "Gmail",
                    value = state.email,
                    onValueChange = viewModel::onEmailChange,
                    isError = errors.emailError != null,
                    errorText = errors.emailError
                )

                Spacer(modifier = Modifier.height(12.dp))

                CTextField(
                    hint = "Contraseña",
                    value = state.contrasena,
                    onValueChange = viewModel::onContrasenaChange,
                    isError = errors.contrasenaError != null,
                    errorText = errors.contrasenaError,
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                CButton(
                    text = if (state.isLoading) "Registrando..." else "Registrarse",
                    enabled = !state.isLoading,
                    onClick = {
                        viewModel.onSignupClick {
                            navController.navigate("login") {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                )
                if (errors.globalError != null) {
                    Text(
                        text = errors.globalError,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = AlegreyaSansFontFamily,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }

                Row (
                    modifier = Modifier.padding(top=12.dp,bottom= 52.dp)
                ){
                    Text("Ya tienes una cuenta? ",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = AlegreyaSansFontFamily,
                            color = Color.White
                        )
                    )

                    Text("Inicia Sesion",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = AlegreyaSansFontFamily,
                            fontWeight = FontWeight(800),
                            color = Color.White
                        ),
                        modifier = Modifier.clickable{
                            navController.navigate("login")
                        }
                    )
                }


            }

        }
    }

}