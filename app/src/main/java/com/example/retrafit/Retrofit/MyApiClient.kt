package com.example.retrafit.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyApiClient {
    const val BASE_URL = "https://hvax.pythonanywhere.com/"

    fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apicl = getRetrofit().create(MyRetrofitApi::class.java)
}