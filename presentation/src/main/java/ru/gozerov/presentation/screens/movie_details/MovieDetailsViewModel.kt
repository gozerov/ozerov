package ru.gozerov.presentation.screens.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.usecases.GetMovieById
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val getMovieById: GetMovieById
) : ViewModel() {

    private val _viewState = MutableStateFlow<MovieDetailsState>(MovieDetailsState.Empty)
    val viewState = _viewState.asStateFlow()

    fun handleIntent(intent: MovieDetailsIntent) {
        viewModelScope.launch {
            when(intent) {
                is MovieDetailsIntent.LoadMovieDetails -> {
                    getMovieById.execute(
                        arg = intent.id,
                        onSuccess = {
                            _viewState.emit(MovieDetailsState.SuccessMovie(it))
                        },
                        onError = {
                            _viewState.emit(MovieDetailsState.Error(it.message.toString()))
                        }
                    )
                }
            }
        }
    }

}