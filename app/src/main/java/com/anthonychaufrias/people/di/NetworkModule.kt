package com.anthonychaufrias.people.di

import com.anthonychaufrias.people.data.service.ICountryService
import com.anthonychaufrias.people.data.service.IPersonService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://ticsolu.com/mvvm/api/v.1.2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(PeopleHttpClient.getClient())
            .build()
    }

    @Singleton
    @Provides
    fun providePersonAPI(retrofit: Retrofit): IPersonService {
        return retrofit.create(IPersonService::class.java)
    }

    @Singleton
    @Provides
    fun provideCountryAPI(retrofit: Retrofit): ICountryService {
        return retrofit.create(ICountryService::class.java)
    }
}