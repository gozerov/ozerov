package ru.gozerov.presentation.screens.movie_list.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import ru.gozerov.domain.models.MovieCard
import ru.gozerov.presentation.databinding.ItemMovieCardBinding
import ru.gozerov.presentation.screens.movie_list.MovieListDiffCallback

class MovieListAdapter(
    private val onClick: (id: Int) -> Unit,
    private val onLongClick: (id: Int) -> Unit
) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>(), View.OnClickListener,
    View.OnLongClickListener {

    inner class ViewHolder(private val binding: ItemMovieCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieCard) {
            with(binding) {
                root.tag = item
                txtName.text = item.name
                txtGenreAndYear.text =
                    "${item.genres.joinToString { it.replaceFirstChar { c -> c.uppercaseChar() } }} (${item.year})"
                poster.load(item.imageUrl) {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(16f))
                }
                imgIsFavorite.visibility =
                    if (item.isFavorite) View.VISIBLE else View.GONE
            }
        }
    }

    var data: List<MovieCard> = emptyList()
        set(value) {
            val diffCallback =
                MovieListDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieCardBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun onClick(v: View?) {
        (v?.tag as? MovieCard)?.let {
            onClick.invoke(it.id)
        }
    }

    override fun onLongClick(v: View?): Boolean {
        (v?.tag as? MovieCard)?.let {
            onLongClick.invoke(it.id)
            return true
        } ?: return false
    }

}