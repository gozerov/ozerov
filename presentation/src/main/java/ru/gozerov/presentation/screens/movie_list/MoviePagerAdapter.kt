package ru.gozerov.presentation.screens.movie_list

import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.gozerov.domain.models.MovieCard
import ru.gozerov.presentation.databinding.ItemMovieListBinding
import ru.gozerov.presentation.utils.VerticalMarginItemDecoration

class MoviePagerAdapter(
    private val onMovieClick: (id: Int) -> Unit,
    private val onMovieLongClick: (id: Int) -> Unit
) : RecyclerView.Adapter<MoviePagerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root)

    var data: List<List<MovieCard>> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var recyclerState: Parcelable? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieListBinding.inflate(inflater, parent, false)
        with(binding.moviesRecyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(VerticalMarginItemDecoration())
        }
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val listAdapter = MovieListAdapter(onMovieClick, onMovieLongClick.apply {
            recyclerState = holder.binding.moviesRecyclerView.layoutManager?.onSaveInstanceState()
        })
        listAdapter.data = item
        holder.binding.moviesRecyclerView.adapter = listAdapter
        holder.binding.root.tag = item
        recyclerState?.let {
            holder.binding.moviesRecyclerView.layoutManager?.onRestoreInstanceState(recyclerState)
        }
    }

}