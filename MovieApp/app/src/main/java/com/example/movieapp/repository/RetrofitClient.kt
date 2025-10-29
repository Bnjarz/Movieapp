// En: repository/RetrofitClient.kt
package com.example.movieapp.repository

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // --- ¡¡¡PEGA TU API KEY AQUÍ!!! ---
    private const val API_KEY = "6764017497983147e9e1f5b87d5932f7"

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    private val authInterceptor = Interceptor { chain ->
        val original = chain.request()
        val httpUrl = original.url.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .addQueryParameter("language", "es-ES") // ¡Resultados en español!
            .build()

        val requestBuilder = original.newBuilder().url(httpUrl)
        val request = requestBuilder.build()
        chain.proceed(request)
    }

    // Creamos el cliente de OkHttp (el "mesero") con el interceptor
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    // Creamos la instancia de Retrofit (la "cocina")
    val instance: TmdbApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Usamos nuestro "mesero" personalizado
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(TmdbApi::class.java)
    }
}