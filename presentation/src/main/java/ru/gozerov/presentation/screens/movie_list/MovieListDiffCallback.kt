package ru.gozerov.presentation.screens.movie_list

import androidx.recyclerview.widget.DiffUtil
import ru.gozerov.domain.models.MovieCard

class MovieListDiffCallback(
    private val oldMovies: List<MovieCard>,
    private val newMovies: List<MovieCard>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldMovies.size

    override fun getNewListSize(): Int = newMovies.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMovies[oldItemPosition].id == newMovies[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMovies[oldItemPosition] == newMovies[newItemPosition]
    }
}