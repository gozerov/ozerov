package ru.gozerov.presentation.screens.movie_list

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.gozerov.domain.models.MovieListData
import ru.gozerov.presentation.databinding.ItemMovieListBinding
import ru.gozerov.presentation.databinding.LayoutErrorBinding
import ru.gozerov.presentation.utils.VerticalMarginItemDecoration

class MoviePagerAdapter(
    private val onMovieClick: (id: Int) -> Unit,
    private val onMovieLongClick: (id: Int) -> Unit,
    private val onTryAgainClick: () -> Unit
) : RecyclerView.Adapter<MoviePagerAdapter.MoviePagerViewHolder>() {

    abstract inner class MoviePagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: MovieListData)
    }

    private inner class MovieViewHolder(private val binding: ItemMovieListBinding) :
        MoviePagerViewHolder(binding.root) {
        override fun bind(item: MovieListData) {
            val movies = (item as MovieListData.MovieList).movies
            val listAdapter = MovieListAdapter(onMovieClick, onMovieLongClick.apply {
                recyclerState = binding.moviesRecyclerView.layoutManager?.onSaveInstanceState()
            })
            listAdapter.data = movies
            binding.moviesRecyclerView.adapter = listAdapter
            binding.root.tag = movies
            recyclerState?.let {
                binding.moviesRecyclerView.layoutManager?.onRestoreInstanceState(recyclerState)
            }
        }
    }

    private inner class ErrorViewHolder(private val binding: LayoutErrorBinding) :
        MoviePagerViewHolder(binding.root) {
        override fun bind(item: MovieListData) {
            binding.tryAgainButton.setOnClickListener {
                onTryAgainClick.invoke()
            }
            binding.tryAgainGroup.visibility = View.VISIBLE
        }

    }

    var data: List<MovieListData> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var recyclerState: Parcelable? = null

    override fun getItemViewType(position: Int): Int {
        return when(data[position]) {
            is MovieListData.MovieList -> TYPE_LIST
            is MovieListData.ErrorMovie -> TYPE_ERROR
            else -> throw (ClassNotFoundException("Illegal viewType!"))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePagerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_LIST) {
            val binding = ItemMovieListBinding.inflate(inflater, parent, false)
            with(binding.moviesRecyclerView) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                addItemDecoration(VerticalMarginItemDecoration())
            }
            MovieViewHolder(binding)
        } else {
            val binding = LayoutErrorBinding.inflate(inflater, parent, false)
            ErrorViewHolder(binding)
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MoviePagerViewHolder, position: Int) {
        holder.bind(data[position])
    }

    companion object {

        private const val TYPE_LIST = 0
        private const val TYPE_ERROR = 1

    }

}