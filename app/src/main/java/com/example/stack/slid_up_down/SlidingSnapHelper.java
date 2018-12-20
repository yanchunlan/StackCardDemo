package com.example.stack.slid_up_down;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;

/**
 * author:  ycl
 * date:  2018/12/19 15:23
 * desc:  如果要实现 PagerSnapHelper 的效果， layoutManager 必须实现ScrollVectorProvider 接口
 */
public class SlidingSnapHelper extends PagerSnapHelper {
    private int mDirection; // 记录速度

    // 滚动停止后  计算离下一个的距离
    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        if (layoutManager instanceof SlidingLayoutManager) {
            int[] out = new int[2];
            if (layoutManager.canScrollHorizontally()) {
                out[0] = ((SlidingLayoutManager) layoutManager).calculateDistanceToPosition(
                        layoutManager.getPosition(targetView));
                out[1] = 0;
            } else {
                out[0] = 0;
                out[1] = ((SlidingLayoutManager) layoutManager).calculateDistanceToPosition(
                        layoutManager.getPosition(targetView));
            }
            return out;
        }
        return null;
    }

    // 滚动停止后  自动滚到下一个View
    @Nullable
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof SlidingLayoutManager) {
            int pos = ((SlidingLayoutManager) layoutManager).getFixedScrollPosition(
                    mDirection, mDirection != 0 ? 0.8f : 0.5f);
            mDirection = 0;
            if (pos != RecyclerView.NO_POSITION) {
                return layoutManager.findViewByPosition(pos);
            }
        }

        return null;
    }

    // fling 滚到下一个pos
    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        if (layoutManager.canScrollHorizontally()) {
            mDirection = velocityX;
        } else {
            mDirection = velocityY;
        }


        if (layoutManager instanceof SlidingLayoutManager) {
            final boolean forwardDirection;
            if (layoutManager.canScrollHorizontally()) {
                forwardDirection = velocityX > 0;
            } else {
                forwardDirection = velocityY > 0;
            }
            if (forwardDirection) {
                return ((SlidingLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else {
                return ((SlidingLayoutManager) layoutManager).findFirstVisibleItemPosition();
            }
        }
        return RecyclerView.NO_POSITION;
    }
}
