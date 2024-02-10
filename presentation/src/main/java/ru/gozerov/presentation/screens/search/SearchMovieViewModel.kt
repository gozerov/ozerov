package ru.gozerov.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.models.NameWithCategory
import ru.gozerov.domain.usecases.SearchMovie
import ru.gozerov.domain.usecases.SetMovieFavorite
import javax.inject.Inject

class SearchMovieViewModel @Inject constructor(
    private val searchMovieByName: SearchMovie,
    private val setMovieFavorite: SetMovieFavorite
): ViewModel() {

    private val _viewState = MutableStateFlow<SearchMovieState>(SearchMovieState.Empty)
    val viewState = _viewState.asStateFlow()

    fun handleIntent(intent: SearchMovieIntent) {
        viewModelScope.launch {
            when(intent) {
                is SearchMovieIntent.SearchByName -> {
                    searchMovieByName.execute(
                        arg = NameWithCategory(intent.name, intent.tabType.ordinal),
                        onSuccess = {
                            _viewState.emit(SearchMovieState.SearchedMovies(it))
                        }
                    )
                }
                is SearchMovieIntent.UpdateMovieByFavorite -> {
                    setMovieFavorite.execute(
                        arg = intent.id,
                        onSuccess = {
                        }
                    )
                }
            }
        }
    }

}