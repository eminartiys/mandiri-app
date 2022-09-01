package com.mandiri.application.di

import com.mandiri.application.domain.*
import com.mandiri.application.feature.main.MainViewModel
import com.mandiri.application.feature.movie.MovieDetailViewModel
import com.mandiri.application.feature.moviesbygenre.MoviesByGenreViewModel
import com.mandiri.application.feature.reviewsbymovie.ReviewsByMovieViewModel
import com.mandiri.news.app.base.arch.ViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
@Module
@InstallIn(ActivityComponent::class)
object ViewModelModule {

    @Provides
    @ActivityScoped
    fun provideMainViewModel(getGenresOfMovieUsecase: GetGenresOfMovieUsecase): MainViewModel {
        return ViewModelFactory(MainViewModel(getGenresOfMovieUsecase)).create(MainViewModel::class.java)
    }

    @Provides
    @ActivityScoped
    fun provideMoviesByGenreViewModel(discoverMovieByUsecase: DiscoverMovieByUsecase): MoviesByGenreViewModel {
        return ViewModelFactory(MoviesByGenreViewModel(discoverMovieByUsecase)).create(
            MoviesByGenreViewModel::class.java
        )
    }

    @Provides
    @ActivityScoped
    fun provideMovieDetailViewModel(
        getDetailMovieUsecase: GetDetailMovieUsecase,
        getMovieReviewsUsecase: GetMovieReviewsUsecase, getMovieVideoUsecase: GetMovieVideoUsecase
    ): MovieDetailViewModel {
        return ViewModelFactory(
            MovieDetailViewModel(
                getDetailMovieUsecase,
                getMovieReviewsUsecase,
                getMovieVideoUsecase
            )
        ).create(MovieDetailViewModel::class.java)
    }

    @Provides
    @ActivityScoped
    fun provideReviewsByMovieViewModel(getMovieReviewsUsecase: GetMovieReviewsUsecase): ReviewsByMovieViewModel {
        return ViewModelFactory(ReviewsByMovieViewModel(getMovieReviewsUsecase)).create(
            ReviewsByMovieViewModel::class.java
        )
    }
}