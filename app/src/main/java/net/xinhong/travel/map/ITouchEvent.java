package net.xinhong.travel.map;

import android.view.MotionEvent;

/**
 * Created by mac on 2017/3/2.
 */
public interface ITouchEvent {

    void onTouchDown(MotionEvent event);

    void onTouchUp(MotionEvent event);

    void onTouchMove(MotionEvent event);
}
