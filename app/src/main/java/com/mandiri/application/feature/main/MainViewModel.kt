package com.mandiri.application.feature.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mandiri.application.base.wrapper.ViewResource
import com.mandiri.application.data.model.response.Genre
import com.mandiri.application.domain.GetGenresOfMovieUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
@HiltViewModel
class MainViewModel
@Inject constructor(private val getGenresOfMovieUsecase: GetGenresOfMovieUsecase) : ViewModel() {

    val genresOfMovieResult = MutableLiveData<ViewResource<List<Genre>>>()

    fun getGenresOfMovie() {
        viewModelScope.launch {
            getGenresOfMovieUsecase().collect {
                genresOfMovieResult.value = it
            }
        }
    }
}