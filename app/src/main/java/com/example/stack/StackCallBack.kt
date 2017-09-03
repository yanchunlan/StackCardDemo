package com.example.stack

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import org.jetbrains.anko.toast

/**
 * Created by yanchunlan on 2017/9/3.
 *
 *  相对于 callback 少实现  getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder)  方法
 *  int dragDirs, int swipeDirs
 *  不支持拖拽 ，支持滑动
 *
 */
class StackCallBack(val mRecyclerView: RecyclerView,
                    val mAdapter: StackAdapter,
                    val mData: ArrayList<StackEntity>) :
        ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.DOWN or ItemTouchHelper.UP or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    /**
     * 拖动置位  true 置位   false 不置位
     */
    override fun onMove(recyclerView: RecyclerView?,
                        viewHolder: RecyclerView.ViewHolder?,
                        target: RecyclerView.ViewHolder?): Boolean = false


    /**
     *滑动删除
     */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        viewHolder.run {
            val item = mData.removeAt(layoutPosition)
            when (direction) {
                ItemTouchHelper.DOWN -> itemView.context.toast("方向：下，滑掉的第${item.description}个美女")
                ItemTouchHelper.UP -> itemView.context.toast("方向：上，滑掉的第${item.description}个美女")
                ItemTouchHelper.LEFT -> itemView.context.toast("方向：左，滑掉的第${item.description}个美女")
                ItemTouchHelper.RIGHT -> itemView.context.toast("方向：右，滑掉的第${item.description}个美女")
            }
            // 加到顶部，数据重复利用
            mData.add(0, item)
            mAdapter.notifyDataSetChanged()
        }
    }






}


