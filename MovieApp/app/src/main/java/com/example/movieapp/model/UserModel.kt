package com.example.movieapp.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val nombre: String,
    val email: String,
    val password: String
)

data class UserResponse(
    val id: Long,
    val nombre: String,
    val email: String
)