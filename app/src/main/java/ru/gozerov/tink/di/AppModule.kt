package ru.gozerov.tink.di

import dagger.Module

@Module(
    includes = [
        AppBindModule::class,
        RetrofitModule::class,
        RoomModule::class,
        ViewModelBindModule::class
    ]
)
class AppModule