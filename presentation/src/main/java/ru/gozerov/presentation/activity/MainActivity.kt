package ru.gozerov.presentation.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.gozerov.presentation.R
import ru.gozerov.presentation.activity.toolbar.ToolbarHolder
import ru.gozerov.presentation.activity.toolbar.ToolbarState
import ru.gozerov.presentation.databinding.ActivityMainBinding
import ru.gozerov.presentation.navigation.Screens
import ru.gozerov.presentation.navigation.findNavigationProvider
import ru.gozerov.presentation.screens.movie_details.MovieDetailsFragment
import ru.gozerov.presentation.screens.movie_list.TabType


class MainActivity : AppCompatActivity(), ToolbarHolder {

    private lateinit var binding: ActivityMainBinding

    private var tabType = TabType.TOP

    private val onFragmentLifecycleCallback =
        object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewCreated(
                fm: FragmentManager,
                fragment: Fragment,
                v: View,
                savedInstanceState: Bundle?
            ) {
                if (fragment is MovieDetailsFragment) {
                    binding.navigateUp.updateLayoutParams<MarginLayoutParams> {
                        this.topMargin = resources.getDimension(R.dimen.margin_64).toInt()
                    }
                } else
                    binding.navigateUp.updateLayoutParams<MarginLayoutParams> {
                        this.topMargin = resources.getDimension(R.dimen.margin_16).toInt()
                    }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation(savedInstanceState)
        supportFragmentManager.registerFragmentLifecycleCallbacks(onFragmentLifecycleCallback, false)
    }

    private fun setupNavigation(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            findNavigationProvider().getRouter().newRootScreen(Screens.movieList())
        }
        binding.searchButton.setOnClickListener {
            findNavigationProvider().getRouter().navigateTo(Screens.searchMovie(tabType))
        }
        binding.navigateUp.setOnClickListener {
            findNavigationProvider().getRouter().exit()
        }
    }

    override fun onResume() {
        super.onResume()
        findNavigationProvider().setNavigator(this, R.id.fragmentContainer)
    }

    override fun onPause() {
        super.onPause()
        findNavigationProvider().removeNavigator()
    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(onFragmentLifecycleCallback)
        super.onDestroy()
    }

    override fun onToolbarChange(toolbarState: ToolbarState) {
        toolbarState.currentTabType?.let { tabType = it }
        binding.navigateUp.post {
            binding.navigateUp.visibility =
                if (toolbarState.isNavUpVisible) View.VISIBLE else View.GONE
        }
        binding.toolbarGroup.visibility =
            if (toolbarState.isContainerVisible) View.VISIBLE else View.GONE
        binding.navigateUp.visibility = if (toolbarState.isNavUpVisible) View.VISIBLE else View.GONE
        binding.searchButton.visibility =
            if (toolbarState.isSearchVisible) View.VISIBLE else View.GONE
        binding.txtToolbarTitle.text = toolbarState.title
    }

}