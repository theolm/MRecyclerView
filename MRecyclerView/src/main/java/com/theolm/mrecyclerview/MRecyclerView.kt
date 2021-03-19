package com.theolm.mrecyclerview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener


@Suppress("MemberVisibilityCanBePrivate")
class MRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val swipeRefreshLayout by lazy { findViewById<SwipeRefreshLayout>(R.id.swipeRefresh) }
    private var loadMoreListener: (() -> Unit)? = null
    var isLastPage = false
    var isLoadingMore = false

    init {
        inflate(context, R.layout.mrecyclerview, this)

        val customAttrs = context.obtainStyledAttributes(attrs, R.styleable.MRecyclerView, 0, 0)

        try {

            //Load More
            val loadMore = customAttrs.getBoolean(R.styleable.MRecyclerView_loadMore, false)
            configureLoadMore(loadMore)


            //Refresh Layout
            setSwipeRefreshColors(intArrayOf(customAttrs.getColor(R.styleable.MRecyclerView_pullToRefreshColor, Color.BLACK)))

            val pullToRefresh = customAttrs.getBoolean(
                R.styleable.MRecyclerView_pullToRefresh,
                false
            )

            swipeRefreshLayout.isEnabled = pullToRefresh
        } finally {
            customAttrs.recycle()
        }

    }

    private fun configureLoadMore(loadMore: Boolean) {
        if (loadMore) {
            recyclerView.addOnScrollListener(object : EndlessRecyclerViewScrollListener(recyclerView) {
                override fun loadMoreItems() {
                    loadMoreListener?.invoke()
                }

                override val isLastPage: Boolean
                    get() = this@MRecyclerView.isLastPage

                override val isLoading: Boolean
                    get() = isLoadingMore

            })
        }
    }

    fun setHasFixedSize(hasFixedSize: Boolean) {
        recyclerView.setHasFixedSize(true)
    }

    fun setLayoutManager(layout: RecyclerView.LayoutManager?) {
        recyclerView.layoutManager = layout
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        recyclerView.adapter = adapter
    }

    fun getAdapter(): RecyclerView.Adapter<*>? = recyclerView.adapter

    fun getRootRecyclerView() : RecyclerView = recyclerView

    fun isRefreshing(isRefreshing: Boolean) {
        swipeRefreshLayout.isRefreshing = isRefreshing
    }

    fun setOnRefreshListener(listener: OnRefreshListener?) {
        swipeRefreshLayout.setOnRefreshListener(listener)
    }

    fun setSwipeRefreshColors(@ColorInt colors: IntArray) {
        swipeRefreshLayout.setColorSchemeColors(*colors)
    }

    fun setLoadMoreListener(l: () -> Unit) {
        loadMoreListener = l
    }

}