package ru.gozerov.presentation.utils

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import ru.gozerov.presentation.R

class VerticalMarginItemDecoration(
    @DimenRes private val innerMarginDimenId: Int = R.dimen.margin_8,
    @DimenRes private val outerMarginDimenId: Int = R.dimen.margin_24
) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val adapter = parent.adapter ?: return
        val currentPosition = parent.getChildAdapterPosition(view).takeIf { it != RecyclerView.NO_POSITION } ?: return

        val isPrevTargetView = adapter.isPrevTargetView(currentPosition)
        val isNextTargetView = adapter.isNextTargetView(currentPosition)

        val innerMargin = view.resources.getDimensionPixelSize(innerMarginDimenId)
        val outerMargin = view.resources.getDimensionPixelSize(outerMarginDimenId)

        with(outRect) {
            top = if (isPrevTargetView) innerMargin else outerMargin
            bottom = if (isNextTargetView) innerMargin else outerMargin
        }
    }

    private fun RecyclerView.Adapter<*>.isPrevTargetView(
        currentPosition: Int
    ) = currentPosition != 0

    private fun RecyclerView.Adapter<*>.isNextTargetView(
        currentPosition: Int
    ) : Boolean {
        val lastIndex = itemCount - 1
        return currentPosition != lastIndex
    }

}