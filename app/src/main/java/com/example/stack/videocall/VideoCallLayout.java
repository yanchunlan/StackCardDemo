package com.example.stack.videocall;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * author:  ycl
 * date:  2019/5/15 16:38
 * desc:
 */
public class VideoCallLayout extends ViewGroup {
    private final int margin = 10;

    public VideoCallLayout(Context context) {
        this(context, null);
    }

    public VideoCallLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoCallLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);

        int childrenCount = getChildCount();
        if (childrenCount <= 0) return;
        if (childrenCount <= 2) { // <=2
            for (int i = 0; i < childrenCount; i++) {
                View child = getChildAt(i);
                if (i == 0) {
                    child.measure(widthMeasureSpec, heightMeasureSpec);
                } else {
                    child.measure(MeasureSpec.makeMeasureSpec(w / 3, MeasureSpec.EXACTLY),
                            MeasureSpec.makeMeasureSpec(h / 3, MeasureSpec.EXACTLY));
                }
            }
        } else if (childrenCount <= 4) { // <=4
            int cw = (w - margin) / 2;
            int ch = (h - margin) / 2;
            for (int i = 0; i < childrenCount; i++) {
                View child = getChildAt(i);
                child.measure(MeasureSpec.makeMeasureSpec(cw, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(ch, MeasureSpec.EXACTLY));
            }
        } else { // <=6
            int cw = (w - margin) / 2;
            int ch = (h - 2 * margin) / 3;
            for (int i = 0; i < childrenCount; i++) {
                if (i > 6) {
                    break;
                }
                View child = getChildAt(i);
                child.measure(MeasureSpec.makeMeasureSpec(cw, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(ch, MeasureSpec.EXACTLY));
            }
        }

        setMeasuredDimension(w, h);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childrenCount = getChildCount();
        if (childrenCount <= 0) return;
        if (childrenCount <= 2) { // <=2
            for (int i = 0; i < childrenCount; i++) {
                View child = getChildAt(i);
                setChildDoubleClickListener(l, t, r, b, child);
                if (i == 0) {
                    child.layout(l, t, r, b);
                } else {
                    child.layout(r * 2 / 3, t, r, b / 3);
                }
            }
        } else if (childrenCount <= 4) { // <=4
            int cw = (r - margin) / 2;
            int ch = (b - margin) / 2;
            for (int i = 0; i < childrenCount; i++) {
                View child = getChildAt(i);
                setChildDoubleClickListener(l, t, r, b, child);
                if (i == 0) {
                    child.layout(l, t, cw, ch);
                } else if (i == 1) {
                    child.layout(cw + margin, t, r, ch);
                } else if (i == 2) {
                    child.layout(l, ch + margin, cw, b);
                } else {
                    child.layout(cw + margin, ch + margin, r, b);
                }
            }
        } else { // <=6
            int cw = (r - margin) / 2;
            int ch = (b - margin * 2) / 3;
            for (int i = 0; i < childrenCount; i++) {
                View child = getChildAt(i);
                setChildDoubleClickListener(l, t, r, b, child);
                if (i == 0) {
                    child.layout(l, t, cw, ch);
                } else if (i == 1) {
                    child.layout(cw + margin, t, r, ch);
                } else if (i == 2) {
                    child.layout(l, ch + margin, cw, 2 * ch + margin);
                } else if (i == 3) {
                    child.layout(cw + margin, ch + margin, r, 2 * ch + margin);
                } else if (i == 4) {
                    child.layout(l, ch * 2 + margin * 2, cw, b);
                } else if (i == 5) {
                    child.layout(cw + margin, ch * 2 + margin * 2, r, b);
                } else {
                    break;
                }
            }
        }
    }

    private void setChildDoubleClickListener(final int l, final int t, final int r, final int b, View child) {
        child.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (v.isSelected()) { // min
                    v.setSelected(false);
                    if (v.getTag() != null && v.getTag() instanceof Rect) {
                        Rect rect = (Rect) v.getTag();
                        v.layout(rect.left, rect.top, rect.right, rect.bottom);
                        setVisibleOutside(v, View.VISIBLE);
                    }
                } else { // max
                    v.setSelected(true);
                    if (v.getTag() != null && v.getTag() instanceof Rect) {
                        Rect rect = (Rect) v.getTag();
                        rect.set(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                        v.setTag(rect);
                    } else {
                        v.setTag(new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom()));
                    }
                    v.layout(l, t, r, b);

                    setVisibleOutside(v, View.INVISIBLE);
                }
                return false;
            }
        });
    }

    private void setVisibleOutside(View v, int visible) {
        int childrenCount = getChildCount();
        for (int i = 0; i < childrenCount; i++) {
            View child = getChildAt(i);
            if (child != v) {
                child.setVisibility(visible);
            }
        }
    }
}
