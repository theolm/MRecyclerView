package com.theolm.mrecyclerview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


@Suppress("MemberVisibilityCanBePrivate")
class MRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.internalMRecyclerView) }
    private val swipeRefreshLayout by lazy { findViewById<SwipeRefreshLayout>(R.id.internalSwipeRefresh) }
    private var refreshListener : (() -> Unit)? = null
    private var loadMoreListener: (() -> Unit)? = null
    private var isLastPage = false
    private var isLoadingMore = false

    init {
        inflate(context, R.layout.mrecyclerview, this)

        val customAttrs = context.obtainStyledAttributes(attrs, R.styleable.MRecyclerView, 0, 0)

        try {
            //Load More
            val loadMore = customAttrs.getBoolean(R.styleable.MRecyclerView_loadMore, false)
            enableLoadMore(loadMore)

            //Refresh Layout
            setSwipeRefreshColors(intArrayOf(customAttrs.getColor(R.styleable.MRecyclerView_pullToRefreshColor, Color.BLACK)))
            val pullToRefresh = customAttrs.getBoolean(
                R.styleable.MRecyclerView_pullToRefresh,
                false
            )
            swipeRefreshLayout.setOnRefreshListener {
                isLastPage = false
                refreshListener?.invoke()
            }
            enableRefresh(pullToRefresh)

        } finally {
            customAttrs.recycle()
        }
    }

    fun enableRefresh(b: Boolean) {
        swipeRefreshLayout.isEnabled = b
    }

    fun enableLoadMore(loadMore: Boolean) {
        if (loadMore) {
            recyclerView.addOnScrollListener(object : EndlessRecyclerViewScrollListener(recyclerView) {
                override fun loadMoreItems() {
                    isLoadingMore = true
                    loadMoreListener?.invoke()
                }

                override val isLastPage: Boolean
                    get() = this@MRecyclerView.isLastPage

                override val isLoading: Boolean
                    get() = isLoadingMore

            })
        } else {
            recyclerView.clearOnScrollListeners()
        }
    }

    fun loadMoreComplete() {
        isLoadingMore = false
    }

    fun loadMoreLastPage(isLastPage: Boolean) {
        this.isLastPage = isLastPage
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

    fun isRefreshing(b: Boolean) {
        swipeRefreshLayout.isRefreshing = b
    }

    fun refreshComplete() {
        swipeRefreshLayout.isRefreshing = false
    }

    fun setOnRefreshListener(listener: (() -> Unit)) {
        refreshListener = listener
    }

    fun setSwipeRefreshColors(@ColorInt colors: IntArray) {
        swipeRefreshLayout.setColorSchemeColors(*colors)
    }

    fun setLoadMoreListener(l: () -> Unit) {
        loadMoreListener = l
    }

}