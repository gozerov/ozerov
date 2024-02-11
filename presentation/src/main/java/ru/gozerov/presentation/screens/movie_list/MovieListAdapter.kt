package ru.gozerov.presentation.screens.movie_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import ru.gozerov.domain.models.ErrorMovie
import ru.gozerov.domain.models.MovieCard
import ru.gozerov.domain.models.MovieListData
import ru.gozerov.presentation.databinding.ItemMovieCardBinding
import ru.gozerov.presentation.databinding.LayoutErrorBinding

class MovieListAdapter(
    private val onClick: (id: Int) -> Unit,
    private val onLongClick: (id: Int) -> Unit
) : RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>(), View.OnClickListener,
    View.OnLongClickListener {

    abstract class MovieListViewHolder(view: View) : RecyclerView.ViewHolder(view)

    inner class MovieCardViewHolder(private val binding: ItemMovieCardBinding) :
        MovieListViewHolder(binding.root) {
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

    inner class ErrorViewHolder(private val binding: LayoutErrorBinding) :
        MovieListViewHolder(binding.root) {
        fun bind() {
            binding.tryAgainGroup.visibility = View.VISIBLE
        }

    }

    var data: List<MovieListData> = emptyList()
        set(value) {
            if (value.isNotEmpty() && value.first() is MovieCard) {
                val diffCallback =
                    MovieListDiffCallback(field as List<MovieCard>, value as List<MovieCard>)
                val diffResult = DiffUtil.calculateDiff(diffCallback)
                field = value
                diffResult.dispatchUpdatesTo(this)
            }
            else if (value.isNotEmpty() && value.first() is ErrorMovie) {
                field = value
                notifyDataSetChanged()
            }
        }

    override fun getItemViewType(position: Int): Int {
        return when(data[position]) {
            is MovieCard -> 0
            is ErrorMovie -> 1
            else -> throw (ClassNotFoundException("Illegal viewType!"))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == 0) {
            val binding = ItemMovieCardBinding.inflate(inflater, parent, false)
            binding.root.setOnClickListener(this)
            binding.root.setOnLongClickListener(this)
            return MovieCardViewHolder(binding)
        }
        else {
            val binding = LayoutErrorBinding.inflate(inflater, parent, false)
            return ErrorViewHolder(binding)
        }

    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val item = data[position]
        if (holder is MovieCardViewHolder) {
            holder.bind(item as MovieCard)
        }
        else if (holder is ErrorViewHolder)
            holder.bind()
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