package com.example.stack

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.example.stack.base.BaseActivity
import com.example.stack.slid_up_down.SlidingLayoutManager
import com.example.stack.slid_up_down.SlidingSnapHelper
import com.example.stack.stack.StackAdapter
import com.example.stack.stack.StackCallBack
import com.example.stack.stack.StackConfig
import com.example.stack.stack.StackManager

/**
 * Created by yanchunlan on 2017/9/5.
 */

inline fun BaseActivity.initStackRecycleView() {
    StackConfig.initConfig(this)

    RecyclerView(this).run {
        setContentView(this)

        layoutManager = StackManager()
        val mAdapter = StackAdapter().apply { halfHeight=true }
        adapter = mAdapter

        /*
         val callback = StackCallBack(mAdapter, mAdapter.list)
         val itemTouchHelper = ItemTouchHelper(callback)
         itemTouchHelper.attachToRecyclerView(this)
         */
        ItemTouchHelper(StackCallBack(mAdapter, mAdapter.list)).attachToRecyclerView(this)
    }
}

inline fun BaseActivity.initSlidingUpReverseRecycleView() {
    RecyclerView(this).run {
        setContentView(this)
        layoutManager = SlidingLayoutManager()
                .apply {
                    setType(SlidingLayoutManager.TYPE.RESERVE_UP)
                }
        val mAdapter = StackAdapter()
        adapter = mAdapter
        SlidingSnapHelper().attachToRecyclerView(this)
    }
}

inline fun BaseActivity.initSlidingUpPositiveRecycleView() {
    RecyclerView(this).run {
        setContentView(this)
        layoutManager = SlidingLayoutManager()
                .apply {
                    setType(SlidingLayoutManager.TYPE.POSITIVE_UP)
                }
        val mAdapter = StackAdapter()
        adapter = mAdapter
        SlidingSnapHelper().attachToRecyclerView(this)
    }
}

inline fun BaseActivity.initSlidingDownPositiveRecycleView() {
    RecyclerView(this).run {
        setContentView(this)
        layoutManager = SlidingLayoutManager()
                .apply {
                    setType(SlidingLayoutManager.TYPE.POSITIVE_DOWN)
                }
        val mAdapter = StackAdapter()
        adapter = mAdapter
        SlidingSnapHelper().attachToRecyclerView(this)
    }
}