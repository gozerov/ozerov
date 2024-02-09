package ru.gozerov.presentation.utils

import androidx.fragment.app.Fragment
import ru.gozerov.presentation.activity.toolbar.ToolbarHolder
import ru.gozerov.presentation.activity.toolbar.ToolbarState

fun Fragment.changeToolbar(toolbarState: ToolbarState) {
    (requireActivity() as ToolbarHolder).onToolbarChange(toolbarState)
}