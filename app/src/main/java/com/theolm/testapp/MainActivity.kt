package com.theolm.testapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.theolm.mrecyclerview.MRecyclerView

class MainActivity : AppCompatActivity() {
    private val recyclerView : MRecyclerView by lazy { findViewById(R.id.recyclerview) }
    private val adapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        recyclerView.setAdapter(adapter)

        recyclerView.setOnRefreshListener {
            Log.d("ehsuahe", "aseasse")
        }

        recyclerView.setLoadMoreListener {
            recyclerView.isLoadingMore = true
            loadItems()
            recyclerView.isLoadingMore = false
        }

        loadItems()
    }

    private fun loadItems() {
        adapter.addItems(generateItems())
    }

    private fun generateItems() : List<String> {
        val out = arrayListOf<String>()
        repeat(20) {
            out.add("Line")
        }
        return out
    }
}