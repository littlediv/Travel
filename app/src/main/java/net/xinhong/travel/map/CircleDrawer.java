package net.xinhong.travel.map;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;

import net.xinhong.travel.R;

/**
 * Created by mac on 2017/3/3.
 */
public class CircleDrawer extends LayerDrawer {

    private Projection projection;
    private Circle circle;
    private LatLng startLatlng;

    private static final String TAG = "CircleDrawer";

    public CircleDrawer(AMap aMap, Context context) {
        super(aMap, context);

        projection = aMap.getProjection();

        Log.e(TAG, "CircleDrawer: " );

    }

    @Override
    public void showPopWindow() {
        Log.e(TAG, "showPopWindow: " );
        View view = View.inflate(mContext, R.layout.choice_window, null);

        Button btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        Button btnReverse = (Button) view.findViewById(R.id.btn_reverse);

        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(btnConfirm, Gravity.LEFT | Gravity.TOP, point.x, point.y);


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "confirm", Toast.LENGTH_SHORT).show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "cancel", Toast.LENGTH_SHORT).show();

                if (circle != null) {
                    circle.remove();

                }
            }
        });

        btnReverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void add(Point point) {
        Log.e(TAG, "add: ");
        startLatlng = projection.fromScreenLocation(point);

        circle = aMap.addCircle(new CircleOptions().center(startLatlng)
                .radius(4000).strokeColor(Color.argb(50, 1, 1, 1))
                .fillColor(Color.argb(50, 1, 1, 1)).strokeWidth(25));
    }

    @Override
    public void change(Point point) {

        LatLng latLng = projection.fromScreenLocation(point);
        float distance = AMapUtils.calculateLineDistance(startLatlng, latLng);
        Log.e(TAG, "change: " + distance);
        circle.setRadius(distance);
    }

    @Override
    public void delete(Point point) {
        super.delete(point);
    }
}
