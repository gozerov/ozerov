package ru.gozerov.presentation.screens.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.gozerov.domain.models.MovieCard
import ru.gozerov.presentation.activity.toolbar.ToolbarState
import ru.gozerov.presentation.databinding.FragmentSearchMovieBinding
import ru.gozerov.presentation.screens.movie_list.MovieListAdapter
import ru.gozerov.presentation.utils.VerticalMarginItemDecoration
import ru.gozerov.presentation.utils.changeToolbar

class SearchMovieFragment : Fragment() {

    private lateinit var binding: FragmentSearchMovieBinding

    private val movieListAdapter = MovieListAdapter()

    val model = MovieCard(
        1115471,
        "Мастер и Маргарита",
        "2023",
        listOf("драма", "фэнтези"),
        "https://kinopoiskapiunofficial.tech/images/posters/kp_small/1115471.jpg",
        false
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchMovieBinding.inflate(inflater, container, false)
        changeToolbar(ToolbarState(isContainerVisible = false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        movieListAdapter.data = (0..9).map { model }
        binding.moviesRecyclerView.addItemDecoration(VerticalMarginItemDecoration())
        binding.moviesRecyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        binding.moviesRecyclerView.adapter = movieListAdapter
    }

}