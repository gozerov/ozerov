package ru.gozerov.presentation.screens.movie_details

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import androidx.core.view.WindowInsetsAnimationControlListenerCompat
import androidx.core.view.WindowInsetsAnimationControllerCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.size.Scale
import kotlinx.coroutines.launch
import ru.gozerov.domain.repositories.MoviesRepository
import ru.gozerov.presentation.R
import ru.gozerov.presentation.activity.toolbar.ToolbarState
import ru.gozerov.presentation.databinding.FragmentMovieDetailsBinding
import ru.gozerov.presentation.di.appComponent
import ru.gozerov.presentation.utils.changeToolbar
import javax.inject.Inject


class MovieDetailsFragment: Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding

    @Inject
    lateinit var moviesRepository: MoviesRepository

    override fun onAttach(context: Context) {
        appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        changeToolbar(ToolbarState(isContainerVisible = false, isNavUpVisible = true))
        requireActivity().window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        requireActivity().window.statusBarColor = Color.TRANSPARENT
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        lifecycleScope.launch {
            val movie = moviesRepository.getMovieById(1115471)
            binding.poster.load(movie.imageUrl) {
                crossfade(true)
                scale(Scale.FIT)
            }
            val imageLoader = ImageLoader(requireContext())
            val request = ImageRequest.Builder(requireContext())
                .data(movie.imageUrl)
                .build()
            val drawable = imageLoader.enqueue(request).job.await()
            binding.poster.setImageDrawable(drawable.drawable)
            binding.txtName.text = movie.name
            binding.txtDescription.text = movie.description
            binding.txtGenres.text = movie.genres.joinToString()
            binding.txtCountries.text = movie.countries.joinToString()
        }

        return binding.root
    }

}