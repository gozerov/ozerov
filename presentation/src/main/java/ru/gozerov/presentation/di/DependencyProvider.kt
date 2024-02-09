package ru.gozerov.presentation.di

interface DependencyProvider {

    fun get(): DependencyContainer

}