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
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import ru.gozerov.presentation.R
import ru.gozerov.presentation.activity.toolbar.ToolbarState
import ru.gozerov.presentation.databinding.FragmentMovieListBinding
import ru.gozerov.presentation.di.appComponent
import ru.gozerov.presentation.navigation.Screens
import ru.gozerov.presentation.navigation.findNavigationProvider
import ru.gozerov.presentation.utils.MultiViewModelFactory
import ru.gozerov.presentation.utils.changeToolbar
import javax.inject.Inject

class MovieListFragment : Fragment() {

    private lateinit var binding: FragmentMovieListBinding

    private val pagerAdapter = MoviePagerAdapter(
        onMovieClick = {
            findNavigationProvider().getRouter().navigateTo(Screens.movieDetails())
        },
        onMovieLongClick = {
            viewModel.handleIntent(MovieListIntent.UpdateMovieByFavorite(it))
        }
    )

    @Inject
    lateinit var factory: MultiViewModelFactory

    private val viewModel: MovieListViewModel by viewModels { factory }

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
        requireActivity().window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private fun observeViewState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { state ->
                    when (state) {
                        is MovieListState.Empty -> {
                            viewModel.handleIntent(MovieListIntent.LoadFilms)
                        }
                        is MovieListState.SuccessMovies -> {
                            val pagerData = state.categoryWithMovies.map { it.second }
                            pagerAdapter.data = pagerData
                            binding.moviesViewPager.adapter = pagerAdapter
                            configureTabsMediator(state.categoryWithMovies.map { it.first })
                            setTabsListener()
                        }
                        is MovieListState.SuccessUpdatedMovies -> {
                            val pagerData = state.categoryWithMovies.map { it.second }
                            pagerAdapter.data = pagerData
                        }

                        is MovieListState.Error -> {
                        }
                    }
                }
            }
        }
    }

    private fun configureTabsMediator(titles: List<String>) {
        TabLayoutMediator(binding.categoryTabs, binding.moviesViewPager) { t, position ->
            t.setCustomView(R.layout.item_tab)
            val tab = (t.customView as TextView)
            val currentItem = binding.moviesViewPager.currentItem
            tab.text = titles[position]
            if (position == currentItem) {
                tab.setTextColor(binding.root.context.getColor(R.color.blue_inactive))
                changeToolbar(ToolbarState(title = tab.text.toString(), isSearchVisible = true))
            } else
                tab.setTextColor(binding.root.context.getColor(R.color.blue_active))
        }.attach()
    }

    private fun setTabsListener() {
        binding.categoryTabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                changeToolbar(
                    ToolbarState(
                        title = (tab?.customView as TextView).text.toString(),
                        isSearchVisible = true
                    )
                )
                (tab.customView as TextView).setTextColor(binding.root.context.getColor(R.color.blue_inactive))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                (tab?.customView as TextView).setTextColor(binding.root.context.getColor(R.color.blue_active))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }


}