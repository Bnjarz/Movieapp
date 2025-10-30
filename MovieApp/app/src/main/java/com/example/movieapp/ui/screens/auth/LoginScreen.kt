package com.example.movieapp.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.movieapp.ui.navigation.Routes
import com.example.movieapp.R
import com.example.movieapp.ui.components.CButton
import com.example.movieapp.ui.theme.AlegreyaFontFamily
import com.example.movieapp.ui.theme.AlegreyaSansFontFamily
import com.example.movieapp.ui.components.CTextField
import com.example.movieapp.ui.components.DontHaveAccount
import com.example.movieapp.viewmodel.auth.LoginViewModel
import com.example.movieapp.viewmodel.auth.LoginViewModelFactory

@Composable
fun LoginScreen(navController: NavHostController){

    val context = LocalContext.current
    val viewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(context))

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
                modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)
            ){Image(painter = painterResource(id = R.drawable.logo ),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 60.dp)
                    .height(80.dp)
                    .align(Alignment.Start)
                    .offset(x=(-20).dp)
                    .padding(bottom = 24.dp)
            )
                Text(text="Iniciar Sesion",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontFamily = AlegreyaFontFamily,
                        fontWeight = FontWeight(500),
                        color = Color.White
                    ),
                    modifier = Modifier.align(Alignment.Start)
                )
                Text("Inicia Sesion para acceder al catálogo",
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
                    isPassword = true,
                    isError = errors.contrasenaError != null,
                    errorText = errors.contrasenaError
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (errors.globalError != null) {
                    Text(
                        text = errors.globalError!!,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = AlegreyaSansFontFamily,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
                    )
                }

                CButton(
                    text = if (state.isLoading) "Iniciando..." else "Iniciar Sesión",
                    enabled = !state.isLoading,
                    onClick = {
                        viewModel.onLoginClick {
                            navController.navigate(Routes.APP_FLOW) {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                )

                DontHaveAccount(onSignupTap = {navController.navigate(Routes.SIGNUP)})
            }
        }
    }
}