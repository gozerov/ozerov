package ru.gozerov.presentation.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout.Tab
import ru.gozerov.presentation.R
import ru.gozerov.presentation.activity.toolbar.ToolbarHolder
import ru.gozerov.presentation.activity.toolbar.ToolbarState
import ru.gozerov.presentation.databinding.ActivityMainBinding
import ru.gozerov.presentation.navigation.Screens
import ru.gozerov.presentation.navigation.findNavigationProvider
import ru.gozerov.presentation.screens.movie_list.TabType

class MainActivity : AppCompatActivity(), ToolbarHolder {

    private lateinit var binding: ActivityMainBinding

    private var tabType = TabType.TOP

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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