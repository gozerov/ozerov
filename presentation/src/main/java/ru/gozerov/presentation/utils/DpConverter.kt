package ru.gozerov.presentation.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

fun Context.convertDpToPx(dp: Float): Int {
    val r: Resources = resources
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        r.displayMetrics
    ).toInt()
}
