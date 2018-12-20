package com.example.stack.slid_up_down;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

/**
 * author:  ycl
 * date:  2018/12/20 10:28
 * desc:
 */
public class SlidingLayoutManager extends RecyclerView.LayoutManager {
    private static final String TAG = "SlidingLayoutManager";

    private int mItemViewWidth;
    private int mItemViewHeight;
    private int mItemCount;
    private int mScrollOffset = 0;


    // 指定滚动到具体位置
    private int currentPos = 0;
    private boolean isScrollToPosition = false;
    // 判断是否snackHelper
    private boolean mHasChild = false;
    // 提供对外的具体位置监听
    private OnScrollListener mListener;
    //按照那种顺序执行
    private TYPE mType = TYPE.RESERVE_UP;


    public void setType(TYPE type) {
        mType = type;
    }

    @Override
    public void scrollToPosition(int position) {
        super.scrollToPosition(position);
        currentPos = position;
        isScrollToPosition = true;
    }

    public int findCurrentVisibleItemPosition() {
        return currentPos;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.getItemCount() == 0 || state.isPreLayout()) return;
        removeAndRecycleAllViews(recycler);

        if (!mHasChild) {
            mItemViewWidth = getHorizontalSpace();//item的宽
            mItemViewHeight = getVerticalSpace();//item的高
            mHasChild = true;
        }

        mItemCount = getItemCount();

        // 初始化一次
        if (isScrollToPosition) {
            isScrollToPosition = false;
            mScrollOffset = (currentPos + 1) * mItemViewHeight;
        } else {
            switch (mType) {
                case RESERVE_UP:
                    mScrollOffset = Integer.MAX_VALUE;
                    break;
                case POSITIVE_UP:
                    mScrollOffset = Integer.MAX_VALUE;
                    break;
                case POSITIVE_DOWN:
                    mScrollOffset = 0;
                    break;
            }
        }

        // 不断校验，计算，一定需要 mScrollOffset 为能够滑动的总高度
        mScrollOffset = Math.min(Math.max(mItemViewHeight, mScrollOffset), mItemCount * mItemViewHeight);
        layoutChildByType(recycler);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int pendingScrollOffset = mScrollOffset + dy; // 滑动真实的高度
        mScrollOffset = Math.min(Math.max(mItemViewHeight, pendingScrollOffset), mItemCount * mItemViewHeight);
//        Log.d(TAG, "scrollVerticallyBy: mScrollOffset: " + mScrollOffset);
        layoutChildByType(recycler);
        return mScrollOffset - pendingScrollOffset + dy;
    }

    private void layoutChildByType(RecyclerView.Recycler recycler) {
        switch (mType) {
            case RESERVE_UP:
                layoutChild(recycler);
                break;
            case POSITIVE_UP:
                layoutChild2(recycler);
                break;
            case POSITIVE_DOWN:
                layoutChild3(recycler);
                break;
        }
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }


    private void layoutChild3(RecyclerView.Recycler recycler) {
        if (getItemCount() == 0) return;
//        Log.d(TAG, "layoutChild3: mScrollOffset: " + mScrollOffset);
        int topItemPosition = (int) Math.floor(mScrollOffset / mItemViewHeight);// 能够显示的总的个数   滑动的个数  1-8
        int topVisibleHeight = -mScrollOffset % mItemViewHeight; // 底面显示的能够看到的高度 0-height


        final int startPos = topItemPosition - 1;
        final int endPos = topItemPosition < mItemCount ? topItemPosition : topItemPosition - 1; // 0-7


        int layoutCount;
        if (endPos > startPos) {
            layoutCount = 2;
        } else {
            layoutCount = 1;
            topVisibleHeight = 0;
        }
        final int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            View childView = getChildAt(i);
            int pos = getPosition(childView);
            if (pos > endPos || pos < startPos) {
                removeAndRecycleView(childView, recycler);
            }
        }
        detachAndScrapAttachedViews(recycler);

        for (int i = layoutCount - 1; i >= 0; i--) {
            View view = recycler.getViewForPosition(startPos + i);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            if (i == 1) {
                layoutDecoratedWithMargins(view, 0, 0, mItemViewWidth, mItemViewHeight);
            } else {
                layoutDecoratedWithMargins(view, 0, topVisibleHeight, mItemViewWidth, topVisibleHeight + mItemViewHeight);
            }
        }

        currentPos = startPos;

        if (mListener != null) {
            if (topVisibleHeight == 0) {
                if (currentPos == RecyclerView.NO_POSITION) {
                    return;
                }
                mListener.stopScroll(currentPos);
            } else {
                mListener.startScroll();
            }
        }
    }

    private void layoutChild2(RecyclerView.Recycler recycler) {
        if (getItemCount() == 0) return;
        int topItemPosition = mItemCount - (int) Math.floor(mScrollOffset / mItemViewHeight);// 能够显示的总的个数   滑动的个数  8-1
        int topVisibleHeight = getVerticalSpace() - mScrollOffset % mItemViewHeight; // 底面显示的能够看到的高度 0-height


        final int startPos = topItemPosition - 1 <= 0 ? 0 : topItemPosition - 1;
        final int endPos = topItemPosition; // 0-7


        int layoutCount;
        if (endPos > startPos) {
            layoutCount = 2;
        } else {
            layoutCount = 1;
            topVisibleHeight = 0;
        }
        final int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            View childView = getChildAt(i);
            int pos = getPosition(childView);
            if (pos > endPos || pos < startPos) {
                removeAndRecycleView(childView, recycler);
            }
        }
        detachAndScrapAttachedViews(recycler);

        if (endPos == mItemCount - 1 && topVisibleHeight == getVerticalSpace()) {
            currentPos = endPos;
        } else {
            currentPos = startPos;
        }

//        Log.d(TAG, "layoutChild2: " + topItemPosition + " top " + topVisibleHeight + " startPos　" + startPos + " endPos " + endPos + " currentPos " + currentPos);

        for (int i = layoutCount - 1; i >= 0; i--) {
//            Log.d(TAG, "layoutChild2: i " + i);
            View view = recycler.getViewForPosition(startPos + i);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            if (i == 1) {
                layoutDecoratedWithMargins(view, 0, 0, mItemViewWidth, mItemViewHeight);
            } else {
                layoutDecoratedWithMargins(view, 0, topVisibleHeight, mItemViewWidth, topVisibleHeight + mItemViewHeight);
            }
        }
    }


    // 下滑 最大值-0，   上滑最大值
    private void layoutChild(RecyclerView.Recycler recycler) {
        if (getItemCount() == 0) return;
        int bottomItemPosition = (int) Math.floor(mScrollOffset / mItemViewHeight);// 能够显示的总的个数   滑动的个数  0-20
//        int remainSpace = getVerticalSpace() - mItemViewHeight; // 失误的高度，一般正常就是0
//        bottomItemPosition = mItemCount - bottomItemPosition;
        int bottomItemVisibleHeight = mScrollOffset % mItemViewHeight; // 底面显示的能够看到的高度 0-height  从大到小
//        final float offsetPercentRelativeToItemView = bottomItemVisibleHeight * 1.0f / mItemViewHeigh t;// 底面显示的占据屏幕的百分比  0-1
//        Log.d(TAG, "layoutChild: bottomItemPosition: " + bottomItemPosition + " bottomItemVisibleHeight: " + bottomItemVisibleHeight);
//        Log.d(TAG, "layoutChild: bottomItemPosition:"+bottomItemPosition+" remainSpace:"+remainSpace+
//                " bottomItemVisibleHeight:"+bottomItemVisibleHeight+" offsetPercentRelativeToItemView"+offsetPercentRelativeToItemView);

        // 8-1
        if (bottomItemPosition == 1 && bottomItemVisibleHeight == 0) {
            currentPos = 0;
        } else if (bottomItemPosition == mItemCount) {
            currentPos = bottomItemPosition - 1;
        } else {
            currentPos = bottomItemPosition;
        }
//        Log.d(TAG, "layoutChild: " + currentPos);

        // 遍历所有的个数,目的是设置所有数据的高度
        ArrayList<Integer> layoutInfos = new ArrayList<>();
//        ArrayList<ItemViewInfo> layoutInfos = new ArrayList<>();
//        for (int i = bottomItemPosition - 1, j = 1; i >= 0; i--, j++) {
//            double maxOffset = (getVerticalSpace() - mItemViewHeight) / 2 * Math.pow(0.8, j);// 几次方
//            int start = (int) (remainSpace - offsetPercentRelativeToItemView * maxOffset); // maxOffset 最大允许失误的尺寸
//
//            ItemViewInfo info = new ItemViewInfo(start);
//            layoutInfos.add(0, info);
//
//
//            remainSpace = (int) (remainSpace - maxOffset);
//            Log.d(TAG, "layoutChild: j:" + j + " start:" + start + " remainSpace: " + remainSpace); // 必定全部是负数
//
//            if (remainSpace <= 0) { // 不允许负数
//                info.setTop((int) (remainSpace + maxOffset));
//                break;
//            }
//        }
        layoutInfos.add(0);

        if (bottomItemPosition < mItemCount) { // 不是最后一个，就是当前显示的一个item加入高度
            final int start = getVerticalSpace() - bottomItemVisibleHeight;
            layoutInfos.add(start);
        } else {
            bottomItemPosition = bottomItemPosition - 1;// 最后一个就是前一个
        }


//        Log.d(TAG, "layoutChild: size2： " + layoutInfos.size()); // 一直是1


        // 控制最后一个就不设置了，其余的就设置最后一个跟其前一个
        int layoutCount = layoutInfos.size();
        final int startPos = bottomItemPosition - (layoutCount - 1); // 不知为何？
        final int endPos = bottomItemPosition;

//        Log.d(TAG, "layoutChild: startPos: " + startPos + " endPos: " + endPos);

        // 把不在界面显示的都删除了，让其存到缓存去
        final int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            View childView = getChildAt(i);
            int pos = getPosition(childView);
            if (pos > endPos || pos < startPos) {
                removeAndRecycleView(childView, recycler);
            }
        }

        detachAndScrapAttachedViews(recycler);

        // 一直只遍历2个
        // 0-1
        for (int i = 0; i < layoutCount; i++) {  //7_>   6 7
            int top = layoutInfos.get(i);
            View view = recycler.getViewForPosition(startPos + i);
            addView(view);
//            measureChildWithExactlySize(view);
            measureChildWithMargins(view, 0, 0);
//            Log.d(TAG, "layoutChild: top " + top + " b " + (top + mItemViewHeight));
            layoutDecoratedWithMargins(view, 0, top, mItemViewWidth, top + mItemViewHeight);
        }
    }

    /**
     * 测量itemview的确切大小
     */
    private void measureChildWithExactlySize(View child) {
        final int widthSpec = View.MeasureSpec.makeMeasureSpec(mItemViewWidth, View.MeasureSpec.EXACTLY);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(mItemViewHeight, View.MeasureSpec.EXACTLY);
        child.measure(widthSpec, heightSpec);
    }

    /**
     * 获取RecyclerView的显示高度
     */
    public int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    /**
     * 获取RecyclerView的显示宽度
     */
    public int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }


    // --------------------------------  snackHelper start ------------------------------

    public int calculateDistanceToPosition(int targetPos) {
        int pendingScrollOffset = mItemViewHeight * (convert2LayoutPosition(targetPos) + 1);
        return pendingScrollOffset - mScrollOffset;
    }

    public int convert2LayoutPosition(int adapterPostion) {
        switch (mType) {
            case RESERVE_UP:
                return adapterPostion;
            case POSITIVE_UP:
                return mItemCount - 1 - adapterPostion;
            case POSITIVE_DOWN:
                return adapterPostion;
        }
        return mItemCount - 1 - adapterPostion;
    }


    public int getFixedScrollPosition(int direction, float fixValue) {
        if (mHasChild) {
            if (mScrollOffset % mItemViewHeight == 0) {
                return RecyclerView.NO_POSITION;
            }
            float position = mScrollOffset * 1.0f / mItemViewHeight;
            return convert2AdapterPosition((int) (direction > 0 ? position + fixValue : position + (1 - fixValue)) - 1);
        }
        return RecyclerView.NO_POSITION;
    }

    public int convert2AdapterPosition(int layoutPosition) {
        switch (mType) {
            case RESERVE_UP:
                return layoutPosition;
            case POSITIVE_UP:
                return mItemCount - 1 - layoutPosition;
            case POSITIVE_DOWN:
                return layoutPosition;
        }
        return mItemCount - 1 - layoutPosition;
    }
    // --------------------------------  snackHelper end ------------------------------


    public void setListener(OnScrollListener listener) {
        mListener = listener;
    }

    public interface OnScrollListener {
        void stopScroll(int position); // 滚动到具体一点就调用

        void startScroll(); // 开始滚动就调用
    }

    public enum TYPE {
        RESERVE_UP,
        POSITIVE_UP,
        POSITIVE_DOWN
    }
}
