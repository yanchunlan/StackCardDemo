package com.example.stack

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import org.jetbrains.anko.toast

/**
 * Created by yanchunlan on 2017/9/3.
 *
 *  相对于 callback 少实现  getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder)  方法
 *  int dragDirs, int swipeDirs
 *  不支持拖拽 ，支持滑动
 */
class StackCallBack(val mAdapter: StackAdapter,
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

    /**
     *  滑动时的动画
     */
    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                             dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        //零界点
        val width = recyclerView.width * 0.5f

        //移动的距离
        val distance = Math.sqrt((dX * dX + dY * dY).toDouble())

        // 动画执行的百分比
        var fraction = distance / width

        if (fraction > 1) {
            fraction = 1.0
        }
        val childCount = recyclerView.childCount
        for (i in 0..childCount - 1) {
            val child = recyclerView.getChildAt(i)
            val level = childCount - i - 1
            if (level > 0) {    // 最多新增一个level 即 1-StackConfig.SCALR_GAP * （level-1）
                child.scaleX = (1 - StackConfig.SCALR_GAP * level + fraction * StackConfig.SCALR_GAP).toFloat()
                if (level < StackConfig.MAX_SHOW_COUNT - 1) { // 1 2 3
                    //顶层的3个图层
                    child.translationY = (StackConfig.TRANS_Y_GAP * level - fraction * StackConfig.TRANS_Y_GAP).toFloat()
                    child.scaleY = (1 - StackConfig.SCALR_GAP * level + fraction * StackConfig.SCALR_GAP).toFloat()
                }
            }
        }
    }
}


