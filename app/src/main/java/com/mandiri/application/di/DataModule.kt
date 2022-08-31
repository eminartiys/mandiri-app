package com.mandiri.application.di

import com.mandiri.application.data.repository.genre.GenreRemoteDataSource
import com.mandiri.application.data.repository.genre.GenreRemoteDataSourceImpl
import com.mandiri.application.data.repository.genre.GenreRepository
import com.mandiri.application.data.repository.genre.GenreRepositoryImpl
import com.mandiri.application.data.service.GenreService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideGenreRemoteDataSource(service: GenreService): GenreRemoteDataSource {
        return GenreRemoteDataSourceImpl(service)
    }

    @Singleton
    @Provides
    fun provideGenreRepository(remoteDataSource: GenreRemoteDataSource): GenreRepository {
        return GenreRepositoryImpl(remoteDataSource)
    }

}