
package com.eagle.hacks.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.eagle.hacks.R;

public class CascadeLayout extends ViewGroup {

    private int mVerticalSpacing;
    private int mHorizontalSpacing;

    public CascadeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CascadeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context
                .obtainStyledAttributes(attrs, R.styleable.CascadeLayout, defStyle, 0);
        mVerticalSpacing = a.getDimensionPixelSize(R.styleable.CascadeLayout_verticalSpacing, 0);
        mHorizontalSpacing = a
                .getDimensionPixelSize(R.styleable.CascadeLayout_horizontalSpacing, 0);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        int height = getPaddingTop();
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.VISIBLE) {
                int verticalSpacing = mVerticalSpacing;
                int horizontalSpacing = mHorizontalSpacing;
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.horizontalSpacing > 0) {
                    horizontalSpacing = lp.horizontalSpacing;
                }
                width = getPaddingLeft() + i * horizontalSpacing;
                if (lp.verticalSpacing > 0) {
                    verticalSpacing = lp.verticalSpacing;
                }
                lp.x = width;
                lp.y = height;
                width += child.getMeasuredWidth();
                height += verticalSpacing;
            }
        }
        width += getPaddingRight();
        height += getChildAt(getChildCount() - 1).getMeasuredHeight()
                + getPaddingBottom();
        setMeasuredDimension(resolveSize(width, widthMeasureSpec),
                resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            child.layout(lp.x, lp.y, lp.x + child.getMeasuredWidth(),
                    lp.y + child.getMeasuredHeight());
        }
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof CascadeLayout.LayoutParams;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    public class LayoutParams extends ViewGroup.LayoutParams {

        int x;
        int y;
        int verticalSpacing;
        int horizontalSpacing;

        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a =
                    context.obtainStyledAttributes(attrs, R.styleable.CascadeLayout_Layout);

            verticalSpacing = a.getDimensionPixelSize(
                    R.styleable.CascadeLayout_Layout_layoutHorizontalSpacing, 0);
            horizontalSpacing = a.getDimensionPixelSize(
                    R.styleable.CascadeLayout_Layout_layoutHorizontalSpacing, 0);

            a.recycle();
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }

        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
        }

    }
}
