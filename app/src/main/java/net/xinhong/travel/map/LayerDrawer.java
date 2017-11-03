package net.xinhong.travel.map;

import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;

import com.amap.api.maps.AMap;

/**
 * Created by mac on 2017/3/2.
 */
public class LayerDrawer implements ITouchEvent{


    protected Point point;
    protected AMap aMap;
    protected Context mContext;

    public LayerDrawer(AMap aMap, Context context) {
        point = new Point();
        this.aMap = aMap;
        this.mContext = context;
    }

    @Override
    public void onTouchDown(MotionEvent event) {

        point.x = (int) event.getX();
        point.y = (int) event.getY();
        add(point);
    }

    @Override
    public void onTouchUp(MotionEvent event) {
        point.x = (int) event.getX();
        point.y = (int) event.getY();
        change(point);

        showPopWindow();
    }



    public void showPopWindow() {
    }

    @Override
    public void onTouchMove(MotionEvent event) {
        point.x = (int) event.getX();
        point.y = (int) event.getY();
        change(point);

    }

    public void add(Point point) {

    }

    public void change(Point point) {

    }

    public void delete(Point point) {

    }
}
