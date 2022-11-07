package com.muratozturk.openai_dall_e_2.di

import com.muratozturk.openai_dall_e_2.common.Constants.BASE_URL
import com.muratozturk.openai_dall_e_2.common.Constants.TOKEN
import com.muratozturk.openai_dall_e_2.data.source.DallEService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private var client: OkHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val newRequest: Request =
            chain.request().newBuilder().addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer $TOKEN").build()
        chain.proceed(newRequest)
    }.build()

    @Provides
    @Singleton
    fun provideDallEService(): DallEService = Retrofit.Builder().client(client).baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build().create(DallEService::class.java)
}