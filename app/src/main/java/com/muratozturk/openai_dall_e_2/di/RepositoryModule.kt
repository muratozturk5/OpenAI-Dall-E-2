package com.muratozturk.openai_dall_e_2.di

import com.muratozturk.openai_dall_e_2.data.repository.DallERepositoryImpl
import com.muratozturk.openai_dall_e_2.domain.repository.DallERepository
import com.muratozturk.openai_dall_e_2.domain.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDallERepository(
        remoteDataSource: RemoteDataSource
    ): DallERepository =
        DallERepositoryImpl(remoteDataSource)
}