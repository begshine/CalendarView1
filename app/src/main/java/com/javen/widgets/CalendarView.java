package com.javen.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by javen on 14-4-3.
 */
public class CalendarView extends View {

    private View mItemView = null;


    public CalendarView(Context context) {
        super(context);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mItemView = LayoutInflater.from(context).inflate(R.layout.item_calendar, null);
        setFocusableInTouchMode(true);
        setClickable(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightSpec = MeasureSpec.getMode(heightMeasureSpec);
        if (heightSpec == MeasureSpec.EXACTLY) {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
        } else {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth() / 7 * 6 + 5);
        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int width = right - left;
        mItemView.measure(MeasureSpec.makeMeasureSpec(width / 7, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(width / 7, MeasureSpec.EXACTLY));
        mItemView.layout(0, 0, mItemView.getMeasuredWidth(), mItemView.getMeasuredHeight());
    }

    Random random = new Random();

    @Override
    protected void onDraw(Canvas canvas) {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                canvas.save();
                canvas.translate(col * mItemView.getWidth() + col, row + row * mItemView.getHeight());
                if (mSelected != null && mSelected.equals(row, col)) {
                    mItemView.setBackgroundColor(Color.RED);
                } else {
                    mItemView.setBackgroundColor(Color.GRAY);
                }
                TextView tv = (TextView) mItemView.findViewById(R.id.itemSolar);
                tv.setText((row * 7 + col) + "");
                tv = (TextView) mItemView.findViewById(R.id.itemLunar);
                tv.setText("初三");
                mItemView.findViewById(R.id.itemLady).setBackgroundColor(System.currentTimeMillis() % 2 == 0 ? Color.BLUE : Color.RED);
                mItemView.measure(MeasureSpec.makeMeasureSpec(getWidth() / 7, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(getWidth() / 7, MeasureSpec.EXACTLY));
                mItemView.layout(0, 0, mItemView.getWidth(), mItemView.getHeight());
                mItemView.draw(canvas);
                canvas.restore();
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                float lastTouchX = event.getX();
                float lastTouchY = event.getY();
                processTouchPoint(lastTouchX, lastTouchY);
                break;
            case MotionEvent.ACTION_UP:
                mSelected = null;
                postInvalidate();
                break;
        }
        return true;
    }

    private Point mSelected = null;

    /**
     * @param lastTouchX
     * @param lastTouchY
     */
    private void processTouchPoint(float lastTouchX, float lastTouchY) {
        int col = (int) (lastTouchX / (mItemView.getWidth() + 1));
        int row = (int) (lastTouchY / (mItemView.getHeight() + 1));
        if (mSelected == null) {
            mSelected = new Point(row, col);
        } else {
            mSelected.set(row, col);
        }
        postInvalidate();
    }


}
