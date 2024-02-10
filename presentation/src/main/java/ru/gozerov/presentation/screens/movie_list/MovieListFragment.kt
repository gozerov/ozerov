package ru.gozerov.presentation.screens.movie_list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import ru.gozerov.domain.models.MovieCard
import ru.gozerov.presentation.R
import ru.gozerov.presentation.activity.toolbar.ToolbarState
import ru.gozerov.presentation.databinding.FragmentMovieListBinding
import ru.gozerov.presentation.utils.changeToolbar

class MovieListFragment : Fragment() {

    private lateinit var binding: FragmentMovieListBinding

    private val pagerAdapter = MoviePagerAdapter()

    val model = MovieCard(
        1115471,
        "Мастер и Маргарита",
        "2023",
        listOf("драма", "фэнтези"),
        "https://kinopoiskapiunofficial.tech/images/posters/kp_small/1115471.jpg",
        false
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        pagerAdapter.data = listOf((0..5).map { model } , (0..9).map { model })
        binding.moviesViewPager.adapter = pagerAdapter

        configureTabsMediator()
        setTabsListener()
    }

    private fun configureTabsMediator() {
        TabLayoutMediator(binding.categoryTabs, binding.moviesViewPager) { t, position ->
            t.setCustomView(R.layout.item_tab)
            val tab = (t.customView as TextView)
            if (position == 0) {
                tab.text = "Популярные"
                tab.setTextColor(binding.root.context.getColor(R.color.blue_inactive))
                changeToolbar(ToolbarState(title = tab.text.toString(), isSearchVisible = true))
            } else {
                tab.text = "Избранное"
                tab.setTextColor(binding.root.context.getColor(R.color.blue_active))
            }
        }.attach()
    }

    private fun setTabsListener() {
        binding.categoryTabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                changeToolbar(ToolbarState(title = (tab?.customView as TextView).text.toString(), isSearchVisible = true))
                (tab.customView as TextView).setTextColor(binding.root.context.getColor(R.color.blue_inactive))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                (tab?.customView as TextView).setTextColor(binding.root.context.getColor(R.color.blue_active))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }


}