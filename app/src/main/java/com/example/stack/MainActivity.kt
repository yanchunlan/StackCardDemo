package com.example.stack

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RecyclerView(this).run {
            setContentView(this)

            layoutManager = LinearLayoutManager(context)
            val mAdapter = StackAdapter()
            adapter = mAdapter

            ItemTouchHelper(StackCallBack(this,mAdapter,mAdapter.list)).attachToRecyclerView(this)
        }

    }
}
