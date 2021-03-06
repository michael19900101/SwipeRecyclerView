package com.michael.swiperecyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;


public class MyRecyclerView extends RecyclerView {


    private int maxLength;
    private int mStartX = 0;
    private LinearLayout itemLayout;
    private int pos;
    private Rect mTouchFrame;
    private int xDown, xMove, yDown, yMove, mTouchSlop, xUp, yUp;
    private Scroller mScroller;
    private TextView textView;
    private onGetListener listener;

    public interface onGetListener {
        void getPosition(int position);
    }

    public void setListener(onGetListener listener) {
        this.listener = listener;
    }

    public MyRecyclerView(Context context) {
        this(context, null);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        //滑动到最小距离
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        //滑动的最大距离
        maxLength = dipToPx(context,180);
        //初始化Scroller
        mScroller = new Scroller(context, new LinearInterpolator(context, null));
    }


    private int dipToPx(Context context, int dip) {
        return (int) (dip * context.getResources().getDisplayMetrics().density + 0.5f);
    }


    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                xDown = x;
                yDown = y;
                //通过点击的坐标计算当前的position
                int mFirstPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
                Rect frame = mTouchFrame;
                if (frame == null) {
                    mTouchFrame = new Rect();
                    frame = mTouchFrame;
                }
                int count = getChildCount();
                for (int i = count - 1; i >= 0; i--) {
                    final View child = getChildAt(i);
                    if (child.getVisibility() == View.VISIBLE) {
                        child.getHitRect(frame);
                        if (frame.contains(x, y)) {
                            pos = mFirstPosition + i;
                        }
                    }
                }
                //通过position得到item的viewHolder
                View view = getChildAt(pos - mFirstPosition);
                MyViewHolder viewHolder = (MyViewHolder) getChildViewHolder(view);
                itemLayout = viewHolder.layout;
                textView = (TextView) itemLayout.findViewById(R.id.item_delete_txt);
            }
            break;

            case MotionEvent.ACTION_MOVE: {
                xMove = x;
                yMove = y;
                int dx = xMove - xDown;
                int dy = yMove - yDown;

                if (Math.abs(dy) < mTouchSlop * 2 && Math.abs(dx) > mTouchSlop) {
                    int scrollX = itemLayout.getScrollX();
                    int newScrollX = mStartX - x;
                    if (newScrollX < 0 && scrollX <= 0) {
                        newScrollX = 0;
                    } else if (newScrollX > 0 && scrollX >= maxLength) {
                        newScrollX = 0;
                    }
                    itemLayout.scrollBy(newScrollX, 0);
                }
            }
            break;
            case MotionEvent.ACTION_UP: {
                xUp = x;
                yUp = y;
                int dx = xUp - xDown;
                int dy = yUp - yDown;
                if (Math.abs(dy) < mTouchSlop && Math.abs(dx) < mTouchSlop) {
                    listener.getPosition(pos);
                } else {
                    int scrollX = itemLayout.getScrollX();
                    if (scrollX > maxLength) {
                        ((RecyclerAdapter) getAdapter()).removeRecycle(pos);
                    } else {
                        mScroller.startScroll(scrollX, 0, -scrollX, 0);
                        invalidate();
                    }
                }
            }


            break;
        }
        mStartX = x;
        return super.onTouchEvent(event);
    }

    // 调用invalidate()是使view进行重绘，
    // 在view的onDraw()方法中又会去调用computeScroll()方法，view才能实现弹性滑动
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            itemLayout.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

}
