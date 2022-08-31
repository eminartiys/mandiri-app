package com.mandiri.application.ui.viewhelper

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
abstract class LoadMoreOnScrollListener(private val pageMultiplier: Int?)
    : RecyclerView.OnScrollListener() {
    private var visibleItemsInOnePage = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        // scroll down
        if (dy > 0) {
            if (visibleItemsInOnePage == 0) {
                visibleItemsInOnePage = recyclerView.childCount
            }

            if (shouldLoadMore(recyclerView.layoutManager as LinearLayoutManager)) {
                loadMore()
            }
        }
    }

    private fun shouldLoadMore(layoutManager: LinearLayoutManager): Boolean {
        val multiplier = pageMultiplier ?: PAGE_MULTIPLIER
        val pageThreshold = visibleItemsInOnePage * multiplier
        val positionThreshold = layoutManager.itemCount - pageThreshold
        return layoutManager.findLastVisibleItemPosition() > Math.max(0, positionThreshold)
    }

    abstract fun loadMore()

    companion object {
        const val PAGE_MULTIPLIER = 2
    }

}