package ru.gozerov.presentation.screens.movie_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import ru.gozerov.domain.models.MovieCard
import ru.gozerov.presentation.databinding.ItemMovieListBinding
import ru.gozerov.presentation.utils.VerticalMarginItemDecoration

class MoviePagerAdapter(
    private val onMovieClicked: (id: Int) -> Unit
) : RecyclerView.Adapter<MoviePagerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root)

    var data: List<List<MovieCard>> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemMovieListBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val listAdapter = MovieListAdapter(onMovieClicked)
        listAdapter.data = item
        with(holder.binding.root) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(VerticalMarginItemDecoration())
            adapter = listAdapter
            tag = item
        }

    }


}