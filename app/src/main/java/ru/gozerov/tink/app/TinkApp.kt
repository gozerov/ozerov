package ru.gozerov.tink.app

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import ru.gozerov.presentation.di.DependencyContainer
import ru.gozerov.presentation.di.DependencyProvider
import ru.gozerov.presentation.navigation.AppNavigationProvider
import ru.gozerov.tink.di.AppComponent
import ru.gozerov.tink.di.DaggerAppComponent

class TinkApp : Application(), DependencyProvider, AppNavigationProvider {

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .context(this)
            .build()
    }

    override fun get(): DependencyContainer = appComponent

    override val cicerone: Cicerone<Router> by lazy { Cicerone.create() }

}