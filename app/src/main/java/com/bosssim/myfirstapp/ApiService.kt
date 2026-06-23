package com.bosssim.myfirstapp

import android.os.Message
import android.provider.ContactsContract
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

data class User (
    val _id: String,
    val name: String,
    val city: String
)

data class NewUser (
    val name: String,
    val email: String,
    val password: String,
    val city: String
)

data class RegisterResponse (
    val message: String
)

interface ApiService {
    @GET("users")
    suspend fun getUsers(@Header("authorization") token: String): List<User>

    @POST("auth/register")
    suspend fun registerUser(@Body user: NewUser): RegisterResponse
}

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.108:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: ApiService = retrofit.create(ApiService::class.java)
}