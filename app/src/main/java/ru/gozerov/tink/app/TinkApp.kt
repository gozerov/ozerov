package ru.gozerov.tink.app

import android.app.Application
import ru.gozerov.presentation.di.DependencyContainer
import ru.gozerov.presentation.di.DependencyProvider
import ru.gozerov.tink.di.AppComponent
import ru.gozerov.tink.di.DaggerAppComponent

class TinkApp : Application(), DependencyProvider {

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .context(this)
            .build()
    }

    override fun get(): DependencyContainer = appComponent

}