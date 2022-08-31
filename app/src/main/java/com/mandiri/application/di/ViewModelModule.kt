package com.mandiri.application.di

import com.mandiri.application.domain.DiscoverMovieByUsecase
import com.mandiri.application.domain.GetGenresOfMovieUsecase
import com.mandiri.application.feature.main.MainViewModel
import com.mandiri.application.feature.moviebygenre.MoviesByGenreViewModel
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
        return ViewModelFactory(MoviesByGenreViewModel(discoverMovieByUsecase)).create(MoviesByGenreViewModel::class.java)
    }
}