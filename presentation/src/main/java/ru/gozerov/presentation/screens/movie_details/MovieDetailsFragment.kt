package ru.gozerov.presentation.screens.movie_details

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import coil.size.Scale
import kotlinx.coroutines.launch
import ru.gozerov.presentation.R
import ru.gozerov.presentation.activity.toolbar.ToolbarState
import ru.gozerov.presentation.databinding.FragmentMovieDetailsBinding
import ru.gozerov.presentation.di.appComponent
import ru.gozerov.presentation.utils.MultiViewModelFactory
import ru.gozerov.presentation.utils.changeToolbar
import javax.inject.Inject

class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding

    @Inject
    lateinit var factory: MultiViewModelFactory

    private val viewModel: MovieDetailsViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        changeToolbar(ToolbarState(isContainerVisible = false, isNavUpVisible = true))
        updateStatusBar()
        observeState()
        setupTryAgainGroup()
        return binding.root
    }

    private fun updateStatusBar() {
        requireActivity().window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        requireActivity().window.statusBarColor = Color.TRANSPARENT
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { state ->
                    when (state) {
                        is MovieDetailsState.Empty -> {
                            renderVisibility(isLoading = true, isError = false)
                            sendLoadIntent()
                        }

                        is MovieDetailsState.SuccessMovie -> {
                            renderVisibility(isError = false)
                            val movie = state.movie
                            binding.poster.load(movie.imageUrl) {
                                scale(Scale.FIT)
                                crossfade(200)
                            }
                            binding.txtName.text = movie.name
                            binding.txtDescription.text = movie.description
                            setGenres(movie.genres.joinToString { it.replaceFirstChar { c -> c.uppercaseChar() } })
                            setCountries(movie.countries.joinToString { it.replaceFirstChar { c -> c.uppercaseChar() } })
                        }

                        is MovieDetailsState.Error -> {
                            renderVisibility(isError = true)
                        }
                    }
                }
            }

        }
    }

    private fun renderVisibility(isLoading: Boolean = false, isError: Boolean) {
        val mainContainerVisibility = if (isError || isLoading) View.GONE else View.VISIBLE
        val errorContainerVisibility = if (isError && !isLoading) View.VISIBLE else View.GONE
        val loadingIndicatorVisibility = if (isLoading) View.VISIBLE else View.GONE
        binding.txtName.visibility = mainContainerVisibility
        binding.txtGenres.visibility = mainContainerVisibility
        binding.poster.visibility = mainContainerVisibility
        binding.txtCountries.visibility = mainContainerVisibility
        binding.txtDescription.visibility = mainContainerVisibility
        binding.errorContainer.tryAgainGroup.visibility = errorContainerVisibility
        binding.loadingContainer.loadingContainer.visibility = loadingIndicatorVisibility
    }

    private fun setGenres(genres: String) {
        val fullText = getString(R.string.genres, genres)
        val genre = getString(R.string.genre_c)
        val spannableString = SpannableString(fullText)
        spannableString.setSpan(
            StyleSpan(Typeface.BOLD),
            fullText.indexOf(genre),
            fullText.indexOf(genre) + genre.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.txtGenres.text = spannableString
    }

    private fun setCountries(countries: String) {
        val fullText = getString(R.string.countries, countries)
        val country = getString(R.string.country_c)
        val spannableString = SpannableString(fullText)
        spannableString.setSpan(
            StyleSpan(Typeface.BOLD),
            fullText.indexOf(country),
            fullText.indexOf(country) + country.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.txtCountries.text = spannableString
    }

    private fun setupTryAgainGroup() {
        binding.errorContainer.tryAgainButton.setOnClickListener {
            sendLoadIntent()
        }
    }

    private fun sendLoadIntent() {
        arguments?.getInt(ARG_ID)?.let { id ->
            viewModel.handleIntent(MovieDetailsIntent.LoadMovieDetails(id))
        }
    }

    companion object {

        private const val ARG_ID = "arg_id"

        fun newInstance(id: Int): MovieDetailsFragment {
            val fragment = MovieDetailsFragment()
            fragment.arguments = bundleOf(ARG_ID to id)
            return fragment
        }

    }

}