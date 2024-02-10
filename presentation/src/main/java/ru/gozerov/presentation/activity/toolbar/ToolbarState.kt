package ru.gozerov.presentation.activity.toolbar

import ru.gozerov.presentation.screens.movie_list.TabType

data class ToolbarState(
    val isContainerVisible: Boolean = true,
    val isNavUpVisible: Boolean = false,
    val title: String = "",
    val isSearchVisible: Boolean = false,
    val isSearchFieldVisible: Boolean = false,
    val currentTabType: TabType? = null
)
