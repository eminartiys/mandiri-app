package com.mandiri.application.di

import com.mandiri.application.data.repository.genre.GenreRepository
import com.mandiri.application.data.repository.movie.MovieRepository
import com.mandiri.application.domain.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
@Module
@InstallIn(SingletonComponent::class)
object UsecaseModule {

    @Singleton
    @Provides
    fun provideGetGenresOfMovieUsecase(repository: GenreRepository): GetGenresOfMovieUsecase {
        return GetGenresOfMovieUsecase(repository, Dispatchers.IO)
    }

    @Singleton
    @Provides
    fun provideDiscoverMovieByUsecase(repository: MovieRepository): DiscoverMovieByUsecase {
        return DiscoverMovieByUsecase(repository, Dispatchers.IO)
    }

    @Singleton
    @Provides
    fun provideGetDetailMovieUsecase(repository: MovieRepository): GetDetailMovieUsecase {
        return GetDetailMovieUsecase(repository, Dispatchers.IO)
    }

    @Singleton
    @Provides
    fun provideGetMovieReviewsUsecase(repository: MovieRepository): GetMovieReviewsUsecase {
        return GetMovieReviewsUsecase(repository, Dispatchers.IO)
    }

    @Singleton
    @Provides
    fun provideGetMovieVideoUsecase(repository: MovieRepository): GetMovieVideoUsecase {
        return GetMovieVideoUsecase(repository, Dispatchers.IO)
    }
}