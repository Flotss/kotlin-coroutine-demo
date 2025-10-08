package com.isep.coroutineDemo.network

import com.isep.coroutineDemo.data.UserResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/")
    suspend fun getRandomUsers(
        @Query("results") results: Int = 5
    ): UserResponse

    companion object {
        private const val BASE_URL = "https://randomuser.me/"

        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}

