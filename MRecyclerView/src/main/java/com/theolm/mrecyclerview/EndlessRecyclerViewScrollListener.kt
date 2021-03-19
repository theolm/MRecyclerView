package com.theolm.mrecyclerview

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager


@Suppress("unused")
abstract class EndlessRecyclerViewScrollListener constructor(private val recyclerView: RecyclerView) :
    RecyclerView.OnScrollListener() {
    private val layoutManager: RecyclerView.LayoutManager? get() = recyclerView.layoutManager

//    constructor(layoutManager: GridLayoutManager) {
//        this.layoutManager = layoutManager
//    }
//
//    constructor(layoutManager: StaggeredGridLayoutManager) {
//        this.layoutManager = layoutManager
//    }
//
//    constructor(layoutManager: LinearLayoutManager) {
//        this.layoutManager = layoutManager
//    }


    /*
     Method gets callback when user scroll the search list
     */
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (layoutManager == null) return

        val visibleItemCount = layoutManager!!.childCount
        val totalItemCount = layoutManager!!.itemCount
        var firstVisibleItemPosition = 0

        when (layoutManager) {
            is StaggeredGridLayoutManager -> {
                val firstVisibleItemPositions =
                    (layoutManager as StaggeredGridLayoutManager).findFirstVisibleItemPositions(
                        null
                    )
                // get maximum element within the list
                firstVisibleItemPosition = firstVisibleItemPositions[0]
            }
            is GridLayoutManager -> {
                firstVisibleItemPosition =
                    (layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
            }
            is LinearLayoutManager -> {
                firstVisibleItemPosition =
                    (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            }
        }
        if (!isLoading && !isLastPage) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
            ) {
                Log.i(TAG, "Loading more items")
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()

    abstract val isLastPage: Boolean
    abstract val isLoading: Boolean

    companion object {
        private val TAG = EndlessRecyclerViewScrollListener::class.java.simpleName
    }
}