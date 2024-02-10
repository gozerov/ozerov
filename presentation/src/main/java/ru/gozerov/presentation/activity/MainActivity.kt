package ru.gozerov.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.gozerov.domain.repositories.MoviesRepository
import ru.gozerov.presentation.R
import ru.gozerov.presentation.activity.toolbar.ToolbarHolder
import ru.gozerov.presentation.activity.toolbar.ToolbarState
import ru.gozerov.presentation.databinding.ActivityMainBinding
import ru.gozerov.presentation.navigation.Screens
import ru.gozerov.presentation.navigation.findNavigationProvider
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ToolbarHolder {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            findNavigationProvider().getRouter().newRootScreen(Screens.movieList())
        }
        binding.searchButton.setOnClickListener {
            findNavigationProvider().getRouter().navigateTo(Screens.searchMovie())
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
        binding.toolbarGroup.visibility = if (toolbarState.isContainerVisible) View.VISIBLE else View.GONE
        binding.navigateUp.visibility = if (toolbarState.isNavUpVisible) View.VISIBLE else View.GONE
        binding.searchButton.visibility = if (toolbarState.isSearchVisible) View.VISIBLE else View.GONE
        binding.txtToolbarTitle.text = toolbarState.title
    }

}