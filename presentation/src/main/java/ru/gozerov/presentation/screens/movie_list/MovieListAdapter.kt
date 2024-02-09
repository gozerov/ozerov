package ru.gozerov.presentation.screens.movie_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import ru.gozerov.domain.models.MovieCard
import ru.gozerov.presentation.databinding.ItemMovieCardBinding
import ru.gozerov.presentation.databinding.ItemMovieListBinding

class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ItemMovieCardBinding) : RecyclerView.ViewHolder(binding.root)

    var data: List<MovieCard> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemMovieCardBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        with(holder.binding) {
            root.tag = item
            txtName.text = item.name
            txtGenreAndYear.text = "${item.genres.joinToString()} (${item.year})"
            poster.load(item.imageUrl) {
                crossfade(true)
                transformations(RoundedCornersTransformation(16f))
            }
            imgIsFavorite.visibility = if (item.isFavorite) View.VISIBLE else View.GONE
        }
    }


}