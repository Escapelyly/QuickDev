package com.escape.quickdevlibrary.widget;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.List;

public class RollViewPager extends ViewPager {

    // 描述文字的列表
    private List<String> topNewsDescList;
    // 保存存 图片的地址列表
    private List<String> topNewsImageUrlList;


    public RollViewPager(Context context) {
        super(context);

    }

    private int lastDotsPosition = 0;
    private TextView top_news_title;

    public void setDescList(List<String> topNewsDescList,
                            TextView top_news_title) {

        // 设置初始文字
        if (topNewsDescList != null && topNewsDescList.size() > 0 && top_news_title != null) {
            top_news_title.setText(topNewsDescList.get(0));
        }

        this.topNewsDescList = topNewsDescList;
        this.top_news_title = top_news_title;


    }

    public void setImageUrls(List<String> topNewsImageUrlList) {
        this.topNewsImageUrlList = topNewsImageUrlList;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isRunning = true;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isRunning = false;
    }

    private boolean isRunning = false;

    public void startRoll() {

        if (getAdapter() == null) {
            return;
        }
        isRunning = true;
        // TODO 开始滚动
        // 清空历史消息
        handler.removeMessages(ROLL);
        // 发送新的信息
        handler.sendEmptyMessageDelayed(ROLL, 3000);
    }

    private final int ROLL = 100;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            if (isRunning) {
                // 切换至下一页
                setCurrentItem((getCurrentItem() + 1) % getAdapter().getCount());
                handler.sendEmptyMessageDelayed(ROLL, 3000);
            }
        }

        ;
    };

    private int downX;
    private int downY;

    public boolean dispatchTouchEvent(MotionEvent ev) {

        //System.out.println("RollViewPager.java :"+ev.getAction()); // down 0  up 1  move 2   cancel 3

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                requestDisallowInterceptTouchEvent(true); // 向父view们。请求，别中断事件，啊，把事件给我。
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:


                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();

                int disX = Math.abs(moveX - downX);
                int disY = Math.abs(moveY - downY);

                // 只有在水平滑动，大于，竖起滑动时，轮播图，才允许发出请求，获得事件
                if (disX > disY && disX > 10) { // disX >10  防止手指在屏幕上抖动

                    // 当是第一个页面，手指向右滑，轮播图不需要事件
                    if (getCurrentItem() == 0 && moveX > downX) {
                        requestDisallowInterceptTouchEvent(false);
                    } else if (getCurrentItem() == getAdapter().getCount() - 1 && downX > moveX) {
                        // 在最后一个页面，手指向左滑，轮播图不需要事件
                        requestDisallowInterceptTouchEvent(false);
                    } else {
                        // 其他情况下，轮播图，处理事件
                        requestDisallowInterceptTouchEvent(true);
                    }

                } else {

                    requestDisallowInterceptTouchEvent(false);
                }
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    ;


    int touchDownX;
    int touchDownY;
    long startDownTime;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("onTouchEvent ; MotionEvent.ACTION_DOWN");
                isRunning = false;

                // 取消刚才发送的handler 消息
                handler.removeMessages(ROLL);

                // 判断点击的动作
                touchDownX = (int) ev.getX();
                touchDownY = (int) ev.getY();
                startDownTime = SystemClock.uptimeMillis(); // 获得手机，从开机，到现在的时间
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("onTouchEvent ; MotionEvent.ACTION_MOVE");

                break;
            case MotionEvent.ACTION_UP:
                System.out.println("onTouchEvent ; MotionEvent.ACTION_UP");
                startRoll();

                // 判断 点击的动作
                int touchUpX = (int) ev.getX();
                int touchUpY = (int) ev.getY();
                long upTime = SystemClock.uptimeMillis();

                int disX = Math.abs(touchUpX - touchDownX); // X轴的距离
                int disY = Math.abs(touchUpY - touchDownY); // Y轴的距离

                int disPoint = (int) Math.sqrt(disX * disX + disY * disY); // 勾股定理，求得二个点的距离

                if (disPoint < 20 && (upTime - startDownTime) < 500) {
                    //点击的动作 ,触发，点击的监听
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(getCurrentItem());
                    }
                }

			/*
             *  当我们收到down事件，和前几个move事件，然后，父view将事件中断后，
			 *  我们就收不到UP事件，此时，父view为给我们一个cancel事件
			 *  * up事件，和cancel 只能发生一个，不会二个都发生。
			 */
                break;
            case MotionEvent.ACTION_CANCEL:
                System.out.println("onTouchEvent ; MotionEvent.ACTION_CANCEL");
                startRoll();
                break;
        }
        return super.onTouchEvent(ev);
    }


    public void setOnItemClickListener(IOnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private IOnItemClickListener itemClickListener;

    public interface IOnItemClickListener {

        void onItemClick(int position);
    }

}
