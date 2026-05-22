package com.example.cntrgssr.di

import com.example.cntrgssr.core.data.api.CountryApi
import com.example.cntrgssr.core.util.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10000, TimeUnit.SECONDS)
            .readTimeout(10000, TimeUnit.SECONDS)
            .writeTimeout(10000, TimeUnit.SECONDS)
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(
                HttpLoggingInterceptor(logger = { Timber.d(it)} ).apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()

    @Provides
    @Singleton
    fun provideCountryInstance(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.api-ninjas.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideCountryApi(retrofit: Retrofit): CountryApi =
        retrofit.create(CountryApi::class.java)
}