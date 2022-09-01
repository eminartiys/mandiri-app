package com.mandiri.application.feature.moviesbygenre

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mandiri.application.base.wrapper.ViewResource
import com.mandiri.application.data.model.response.Movie
import com.mandiri.application.domain.DiscoverMovieByUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
@HiltViewModel
class MoviesByGenreViewModel
@Inject constructor(private val discoverMovieByUsecase: DiscoverMovieByUsecase) : ViewModel() {

    val discoverMovieByResult = MutableLiveData<ViewResource<List<Movie>>>()

    fun discoverMovieBy(genreId: String, page: Int) {
        viewModelScope.launch {
            discoverMovieByUsecase(Pair(genreId, page)).collect {
                discoverMovieByResult.value = it
            }
        }
    }

}