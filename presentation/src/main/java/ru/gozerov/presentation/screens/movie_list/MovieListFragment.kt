package ru.gozerov.presentation.screens.movie_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import ru.gozerov.presentation.R
import ru.gozerov.presentation.activity.toolbar.ToolbarState
import ru.gozerov.presentation.databinding.FragmentMovieListBinding
import ru.gozerov.presentation.di.appComponent
import ru.gozerov.presentation.navigation.Screens
import ru.gozerov.presentation.navigation.findNavigationProvider
import ru.gozerov.presentation.screens.movie_list.adapters.MoviePagerAdapter
import ru.gozerov.presentation.utils.MultiViewModelFactory
import ru.gozerov.presentation.utils.changeToolbar
import javax.inject.Inject

class MovieListFragment : Fragment() {

    private lateinit var binding: FragmentMovieListBinding

    private val pagerAdapter = MoviePagerAdapter(
        onMovieClick = {
            findNavigationProvider().getRouter().navigateTo(Screens.movieDetails(it))
        },
        onMovieLongClick = {
            viewModel.handleIntent(MovieListIntent.UpdateMovieByFavorite(it))
        },
        onTryAgainClick = {
            viewModel.handleIntent(MovieListIntent.LoadMovies)
        }
    )

    @Inject
    lateinit var factory: MultiViewModelFactory

    private val viewModel: MovieListViewModel by viewModels { factory }

    private var onPageChangeCallback: ViewPager2.OnPageChangeCallback? = null

    override fun onAttach(context: Context) {
        appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        changeStatusBar()
        observeViewState()
        return binding.root
    }

    private fun changeStatusBar() {
        requireActivity().window?.run {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = requireContext().getColor(R.color.white)
        }
    }

    private fun observeViewState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { state ->
                    when (state) {
                        is MovieListState.Empty -> {
                            renderVisibility(isLoading = true)
                            viewModel.handleIntent(MovieListIntent.LoadMovies)
                        }

                        is MovieListState.SuccessMovies -> {
                            renderVisibility()
                            val pagerData = state.movieListData
                            pagerAdapter.data = pagerData
                            binding.moviesViewPager.adapter = pagerAdapter
                            binding.moviesViewPager.currentItem = viewModel.currentTabType.ordinal
                            val titles = state.movieListData.map { it.tabName }
                            configureTabsMediator()
                            setPageListener(titles)
                        }

                        is MovieListState.SuccessUpdatedMovies -> {
                            renderVisibility()
                            val pagerData = state.movieListData
                            val titles = state.movieListData.map { it.tabName }
                            pagerAdapter.data = pagerData
                            if (binding.moviesViewPager.adapter == null) {
                                binding.moviesViewPager.adapter = pagerAdapter
                                configureTabsMediator()
                                setPageListener(titles)
                            } else
                                updateTabs(viewModel.currentTabType.ordinal, titles)
                        }

                        is MovieListState.Error -> {
                            //this state was handled by adapter
                        }
                    }
                }
            }
        }
    }

    private fun configureTabsMediator() {
        TabLayoutMediator(binding.categoryTabs, binding.moviesViewPager) { t, _ ->
            t.setCustomView(R.layout.item_tab)
        }.attach()
    }

    private fun setPageListener(titles: List<String>) {
        onPageChangeCallback = object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateTabs(position, titles)
            }
        }
        binding.moviesViewPager.registerOnPageChangeCallback(onPageChangeCallback!!)
    }

    private fun updateTabs(position: Int, titles: List<String>) {
        val selectedTab = binding.categoryTabs.getTabAt(position)?.customView as TextView
        val unselectedTabIndex = if (position == 0) 1 else 0
        val unselectedTab =
            binding.categoryTabs.getTabAt(unselectedTabIndex)?.customView as? TextView

        viewModel.currentTabType = if (position == 0) TabType.TOP else TabType.FAVORITE
        selectedTab.setTextColor(binding.root.context.getColor(R.color.blue_inactive))
        selectedTab.text = titles[position]
        unselectedTab?.setTextColor(binding.root.context.getColor(R.color.blue_active))
        unselectedTab?.text = titles[unselectedTabIndex]
        changeToolbar(
            ToolbarState(
                title = selectedTab.text.toString(),
                isSearchVisible = true,
                currentTabType = viewModel.currentTabType
            )
        )
    }

    private fun renderVisibility(isLoading: Boolean = false) {
        val mainContainerVisibility = if (isLoading) View.GONE else View.VISIBLE
        val loadingIndicatorVisibility = if (isLoading) View.VISIBLE else View.GONE
        binding.moviesViewPager.visibility = mainContainerVisibility
        binding.categoryTabs.visibility = mainContainerVisibility
        binding.loadingIndicator.visibility = loadingIndicatorVisibility
    }

    override fun onDestroyView() {
        onPageChangeCallback?.let {
            binding.moviesViewPager.unregisterOnPageChangeCallback(it)
        }
        super.onDestroyView()
    }


}