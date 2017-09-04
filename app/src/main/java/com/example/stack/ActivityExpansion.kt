package com.example.stack

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

/**
 * Created by yanchunlan on 2017/9/5.
 */

inline fun AppCompatActivity.initStackRecycleView() {
    StackConfig.initConfig(this)

    RecyclerView(this).run {
        setContentView(this)

        layoutManager = StackManager()
        val mAdapter = StackAdapter()
        adapter = mAdapter

        /*
         val callback = StackCallBack(mAdapter, mAdapter.list)
         val itemTouchHelper = ItemTouchHelper(callback)
         itemTouchHelper.attachToRecyclerView(this)
         */
        ItemTouchHelper(StackCallBack(mAdapter, mAdapter.list)).attachToRecyclerView(this)
    }
}