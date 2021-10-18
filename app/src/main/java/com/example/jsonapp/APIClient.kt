package com.example.jsonapp

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class APIClient {
    companion object{
        var retrofit : Retrofit? = null
        var baseUrl = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/"

        fun getClient(): Retrofit? {
            val interceptor= HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(
                GsonConverterFactory.create()).client(client).build()
            return retrofit
        }
    }



}