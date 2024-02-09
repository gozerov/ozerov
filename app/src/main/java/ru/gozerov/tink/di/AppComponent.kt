package ru.gozerov.tink.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.gozerov.presentation.activity.MainActivity
import ru.gozerov.presentation.di.DependencyContainer
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent: DependencyContainer {

    //override val viewModelFactory: MultiViewModelFactory
    override fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder {

        fun build() : AppComponent

        @BindsInstance
        fun context(context: Context) : Builder

    }

}