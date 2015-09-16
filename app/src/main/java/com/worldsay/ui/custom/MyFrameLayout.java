package com.worldsay.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by gekson on 2015/8/29.
 */
public class MyFrameLayout extends FrameLayout{
    private boolean isIntercept = false;

    public MyFrameLayout(Context context) {
        super(context);
    }

    public MyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isIntercept;
    }

    public boolean isIntercept() {
        return isIntercept;
    }

    public void setIsIntercept(boolean isIntercept) {
        this.isIntercept = isIntercept;
    }
}
