package ru.gozerov.presentation.di

import android.content.Context
import ru.gozerov.presentation.activity.MainActivity

interface DependencyContainer {

    fun inject(activity: MainActivity)

}

val Context.appComponent : DependencyContainer
    get() = (this.applicationContext as DependencyProvider).get()
