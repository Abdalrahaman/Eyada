package com.omranic.eyada.di

import com.omranic.eyada.network.EyadaApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val BASE_URL = "https://toric-reenlistment.000webhostapp.com/"

    @Singleton
    @Provides
    fun provideEyadaApiService(): EyadaApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EyadaApiService::class.java)
    }
}