package com.example.movieapp

import com.example.movieapp.data.auth.AuthPrefsRepo
import com.example.movieapp.model.UserResponse
import com.example.movieapp.network.BackendApi
import com.example.movieapp.network.BackendClient
import com.example.movieapp.viewmodel.auth.LoginState
import com.example.movieapp.viewmodel.auth.LoginViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private val authRepo: AuthPrefsRepo = mockk(relaxed = true)
    private val api: BackendApi = mockk()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        mockkObject(BackendClient)
        coEvery { BackendClient.api } returns api

        viewModel = LoginViewModel(authRepo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `login exitoso cambia el estado a Success y guarda sesion`() = runTest {
        val email = "test@duoc.cl"
        val password = "123"
        val fakeUser = UserResponse(1L, "Test User", email)

        coEvery { api.login(any()) } returns Response.success(fakeUser)

        viewModel.onEmailChange(email)
        viewModel.onPasswordChange(password)
        viewModel.login()

        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.loginState.value is LoginState.Success)

        coVerify { authRepo.saveUserSession("Test User", email, "1") }
    }

    @Test
    fun `login fallido cambia el estado a Error`() = runTest {
        coEvery { api.login(any()) } returns Response.error(401, okhttp3.ResponseBody.create(null, "Error"))

        viewModel.onEmailChange("error@duoc.cl")
        viewModel.onPasswordChange("wrongpass")
        viewModel.login()

        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.loginState.value is LoginState.Error)
    }
}