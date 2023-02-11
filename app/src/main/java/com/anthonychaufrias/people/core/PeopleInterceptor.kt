package com.anthonychaufrias.people.core

import okhttp3.Interceptor
import okhttp3.Response

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