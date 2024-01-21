package com.dsa.practicekotlin2.data.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideHoroscopeApiService(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://newastro.vercel.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}