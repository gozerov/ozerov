package ru.gozerov.presentation.screens.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.gozerov.presentation.R
import ru.gozerov.presentation.activity.toolbar.ToolbarState
import ru.gozerov.presentation.databinding.FragmentSearchMovieBinding
import ru.gozerov.presentation.di.appComponent
import ru.gozerov.presentation.navigation.Screens
import ru.gozerov.presentation.navigation.findNavigationProvider
import ru.gozerov.presentation.screens.movie_list.MovieListAdapter
import ru.gozerov.presentation.screens.movie_list.TabType
import ru.gozerov.presentation.utils.MultiViewModelFactory
import ru.gozerov.presentation.utils.VerticalMarginItemDecoration
import ru.gozerov.presentation.utils.changeToolbar
import javax.inject.Inject

class SearchMovieFragment : Fragment() {

    private lateinit var binding: FragmentSearchMovieBinding

    @Inject
    lateinit var factory: MultiViewModelFactory

    private val viewModel: SearchMovieViewModel by viewModels { factory }

    private val movieListAdapter = MovieListAdapter(
        onClick = {
            findNavigationProvider().getRouter().navigateTo(Screens.movieDetails(it))
        },
        onLongClick = { }
    )

    override fun onAttach(context: Context) {
        appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchMovieBinding.inflate(inflater, container, false)
        changeToolbar(ToolbarState(isContainerVisible = false))
        setupNavUp()
        changeStatusBar()
        observeState()
        observeSearchField()
        setupTryAgainGroup()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.moviesRecyclerView.addItemDecoration(VerticalMarginItemDecoration())
        binding.moviesRecyclerView.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        binding.moviesRecyclerView.adapter = movieListAdapter
    }

    private fun setupNavUp() {
        binding.navigateUp.setOnClickListener {
            findNavigationProvider().getRouter().exit()
        }
    }

    private fun changeStatusBar() {
        requireActivity().window?.run {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = requireContext().getColor(R.color.white)
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { state ->
                    when (state) {
                        is SearchMovieState.Empty -> {
                            binding.txtNotFound.visibility = View.VISIBLE
                        }
                        is SearchMovieState.SearchedMovies -> {
                            renderVisibility(isError = false, state.movies.isEmpty())
                            movieListAdapter.data = state.movies
                        }

                        is SearchMovieState.Error -> {
                            renderVisibility(isError = true, isFieldEmpty = false)
                        }
                    }
                }
            }
        }
    }

    private fun observeSearchField() {
        val tabType = TabType.values()[arguments?.getInt(ARG_TAB_TYPE) ?: 0]
        binding.searchField.doOnTextChanged { text, _, _, _ ->
            viewModel.handleIntent(SearchMovieIntent.SearchByName(text.toString(), tabType))
        }
    }

    private fun setupTryAgainGroup() {
        val tabType = TabType.values()[arguments?.getInt(ARG_TAB_TYPE) ?: 0]
        binding.tryAgainButton.setOnClickListener {
            viewModel.handleIntent(SearchMovieIntent.SearchByName("", tabType))
        }
    }
    private fun renderVisibility(isError: Boolean, isFieldEmpty: Boolean) {
        val recyclerVisibility = if (isError || isFieldEmpty) View.GONE else View.VISIBLE
        val errorContainerVisibility = if (isError) View.VISIBLE else View.GONE
        val isStubOnEmptyVisibility = if (isFieldEmpty && !isError) View.VISIBLE else View.GONE
        binding.moviesRecyclerView.visibility = recyclerVisibility
        binding.txtNotFound.visibility = isStubOnEmptyVisibility
        binding.tryAgainGroup.visibility = errorContainerVisibility
    }

    companion object {

        private const val ARG_TAB_TYPE = "tab_type"

        fun newInstance(tabType: TabType): SearchMovieFragment {
            val fragment = SearchMovieFragment()
            fragment.arguments = bundleOf(ARG_TAB_TYPE to tabType.ordinal)
            return fragment
        }

    }

}