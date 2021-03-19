package com.theolm.mrecyclerview

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener


class MRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val swipeRefreshLayout by lazy { findViewById<SwipeRefreshLayout>(R.id.swipeRefresh) }
    
    init {
        val root = inflate(context, R.layout.mrecyclerview, this)

        val customAttrs = context.obtainStyledAttributes(attrs, R.styleable.MRecyclerView, 0, 0)

        try {
            val loadMore = customAttrs.getBoolean(R.styleable.MRecyclerView_loadMore, false)
            val pullToRefresh = customAttrs.getBoolean(
                R.styleable.MRecyclerView_pullToRefresh,
                false
            )

            swipeRefreshLayout.isEnabled = pullToRefresh

        } finally {
            customAttrs.recycle()
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

    fun getRootRecyclerView() = recyclerView

    fun isRefreshing(isRefreshing: Boolean) {
        swipeRefreshLayout.isRefreshing = isRefreshing
    }

    fun setOnRefreshListener(listener: OnRefreshListener?) {
        swipeRefreshLayout.setOnRefreshListener(listener)
    }

}