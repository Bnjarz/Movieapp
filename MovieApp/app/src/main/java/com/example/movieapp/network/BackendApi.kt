package com.example.movieapp.network

import com.example.movieapp.model.LoginRequest
import com.example.movieapp.model.RegisterRequest
import com.example.movieapp.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface BackendApi {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<UserResponse>

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<UserResponse>
}