package ru.gozerov.presentation.navigation

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.gozerov.presentation.R

interface AppNavigationProvider {

    val cicerone: Cicerone<Router>

    fun setNavigator(activity: FragmentActivity, containerId: Int) {
        val navigator = object : AppNavigator(activity, containerId) {
            override fun setupFragmentTransaction(
                screen: FragmentScreen,
                fragmentTransaction: FragmentTransaction,
                currentFragment: Fragment?,
                nextFragment: Fragment
            ) {
                fragmentTransaction
                    .setCustomAnimations(
                        R.anim.enter,
                        R.anim.exit,
                        R.anim.pop_enter,
                        R.anim.pop_exit
                    )
            }
        }
        cicerone.getNavigatorHolder().setNavigator(navigator)
    }

    fun removeNavigator() {
        cicerone.getNavigatorHolder().removeNavigator()
    }

    fun getRouter(): Router = cicerone.router

}

fun Context.findNavigationProvider(): AppNavigationProvider {
    return this.applicationContext as AppNavigationProvider
}

fun Fragment.findNavigationProvider(): AppNavigationProvider {
    return this.requireContext().findNavigationProvider()
}