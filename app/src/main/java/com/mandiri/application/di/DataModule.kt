package com.mandiri.application.di

import com.mandiri.application.data.repository.genre.GenreRemoteDataSource
import com.mandiri.application.data.repository.genre.GenreRemoteDataSourceImpl
import com.mandiri.application.data.repository.genre.GenreRepository
import com.mandiri.application.data.repository.genre.GenreRepositoryImpl
import com.mandiri.application.data.repository.movie.MovieRemoteDataSource
import com.mandiri.application.data.repository.movie.MovieRemoteDataSourceImpl
import com.mandiri.application.data.repository.movie.MovieRepository
import com.mandiri.application.data.repository.movie.MovieRepositoryImpl
import com.mandiri.application.data.service.GenreService
import com.mandiri.application.data.service.MovieService
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

    @Singleton
    @Provides
    fun provideMovieRemoteDataSource(service: MovieService): MovieRemoteDataSource {
        return MovieRemoteDataSourceImpl(service)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(remoteDataSource: MovieRemoteDataSource): MovieRepository {
        return MovieRepositoryImpl(remoteDataSource)
    }
}