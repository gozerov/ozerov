package ru.gozerov.presentation.screens.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.usecases.GetAllMovies
import ru.gozerov.domain.usecases.SetMovieFavorite
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val getAllMovies: GetAllMovies,
    private val setMovieFavorite: SetMovieFavorite
) : ViewModel() {

    private val _viewState = MutableStateFlow<MovieListState>(MovieListState.Empty())
    val viewState = _viewState.asStateFlow()

    var currentTabType = TabType.TOP

    fun handleIntent(intent: MovieListIntent) {
        viewModelScope.launch {
            when (intent) {
                is MovieListIntent.LoadMovies -> {
                    getAllMovies.execute(
                        arg = Unit,
                        onSuccess = {
                            _viewState.emit(MovieListState.SuccessMovies(it))
                        }
                    )
                }
                is MovieListIntent.UpdateMovieByFavorite -> {
                    setMovieFavorite.execute(
                        arg = intent.id,
                        onSuccess = {
                            _viewState.emit(MovieListState.SuccessUpdatedMovies(it))
                        }
                    )
                }
            }
        }
    }

}