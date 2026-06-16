package com.bosssim.myfirstapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

data class User (
    val _id: String,
    val name: String,
    val city: String
)

interface ApiService {
    @GET("users")
    suspend fun getUsers(@Header("authorization") token: String): List<User>
}

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.109:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: ApiService = retrofit.create(ApiService::class.java)
}