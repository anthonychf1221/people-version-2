package com.anthonychaufrias.people.core

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://ticsolu.com/mvvm/api/v.1.2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()
    }

    private fun getClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(PeopleInterceptor())
            .build()

}