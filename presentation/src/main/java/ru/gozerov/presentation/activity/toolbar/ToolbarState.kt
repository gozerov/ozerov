package ru.gozerov.presentation.activity.toolbar

data class ToolbarState(
    val isContainerVisible: Boolean = true,
    val isNavUpVisible: Boolean = false,
    val title: String = "",
    val isSearchVisible: Boolean = false,
    val isSearchFieldVisible: Boolean = false
)
