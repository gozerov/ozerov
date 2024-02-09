package ru.gozerov.presentation.activity.toolbar

data class ToolbarState(
    val isNavUpVisible: Boolean = false,
    val title: String,
    val isSearchVisible: Boolean = false,
    val isSearchFieldVisible: Boolean = false
)
