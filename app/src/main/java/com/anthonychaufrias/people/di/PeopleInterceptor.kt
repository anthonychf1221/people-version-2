package com.anthonychaufrias.people.di

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

object PeopleHttpClient{
    fun getClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(PeopleInterceptor())
            .build()
}

class PeopleInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                "Accept", "application/json"
            )
            .addHeader(
                "Content-Type", "application/json"
            )
            .build()

        return chain.proceed(request)
    }
}