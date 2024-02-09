package ru.gozerov.presentation.navigation

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator

interface AppNavigationProvider {

    val cicerone: Cicerone<Router>

    fun setNavigator(activity: FragmentActivity, containerId: Int) {
        cicerone.getNavigatorHolder().setNavigator(AppNavigator(activity, containerId))
    }

    fun removeNavigator() {
        cicerone.getNavigatorHolder().removeNavigator()
    }

    fun getRouter() : Router = cicerone.router

}

fun Context.findNavigationProvider() : AppNavigationProvider {
    return this.applicationContext as AppNavigationProvider
}

fun Fragment.findNavigationProvider() : AppNavigationProvider {
    return this.requireContext().findNavigationProvider()
}