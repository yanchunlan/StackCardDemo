package com.example.stack

import android.support.v7.widget.RecyclerView

/**
 * Created by yanchunlan on 2017/9/4.
 */
class StackManager : RecyclerView.LayoutManager() {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams
            = RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        // 缓存核心类： RecyclerViewPool , Recycler 主要是缓存ViewHolder
        //在布局之前，讲所有的子View先detach掉，放入到Scrap缓存中
        detachAndScrapAttachedViews(recycler)

        val childCount = itemCount
        if (childCount < 1) {
            return
        }

        val bottomPosition: Int = if (childCount < StackConfig.MAX_SHOW_COUNT) {
                                    0
                                } else {
                                    childCount - StackConfig.MAX_SHOW_COUNT
                                }


        for (position in bottomPosition..childCount - 1) {

            //从4级缓存里面取出一个item, （mChangedScrap 、mAttachedScrap） 、
            // mViewCacheExtension、mCachedViews 、mRecyclerPool、createViewHolder
            val view = recycler.getViewForPosition(position)

            //将View加入到RecyclerView中
            addView(view)
            measureChildWithMargins(view, 0, 0)

            //空白区域   因为每一个条目的外面会有一个装饰分割线，所以我们需要把装饰分割线的距离计算出来并减掉
            val widthSpace = width - getDecoratedMeasuredWidth(view)//屏幕的宽度减去使用的宽度就是横向所有空白区的宽度
            val heightSpace = height - getDecoratedMeasuredHeight(view)//屏幕的高度减去使用的高度就是纵向所有空白区的高度
            layoutDecorated(view, widthSpace / 2, //左
                    heightSpace / 2, //上
                    widthSpace / 2 + getDecoratedMeasuredWidth(view), //右
                    heightSpace / 2 + getDecoratedMeasuredHeight(view))  //下

            //  lever == 0 不需要缩放  ， lever == StackConfig.SCALR_GAP 不需要平移缩放y
            val level = childCount - 1 - position
            if (level > 0) {
                view.scaleX = 1 - StackConfig.SCALR_GAP * level
                if (level < StackConfig.MAX_SHOW_COUNT - 1) {
                    //第二个和第三个图层 需要缩放
                    view.translationY = StackConfig.TRANS_Y_GAP * level.toFloat()
                    view.scaleY = 1 - StackConfig.SCALR_GAP * level
                } else {
                    //从顶层往下第四个 重叠处理  跟前一个位置一样 so level-1
                    view.translationY = StackConfig.TRANS_Y_GAP * (level - 1).toFloat()
                    view.scaleY = 1 - StackConfig.SCALR_GAP * (level - 1)
                }
            }
        }
    }
}