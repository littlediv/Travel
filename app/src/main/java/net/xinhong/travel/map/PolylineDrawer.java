package net.xinhong.travel.map;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;

import net.xinhong.travel.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2017/3/2.
 */
public class PolylineDrawer extends LayerDrawer{

    private List<LatLng> latlngs;
    private Projection projection;
    private Polyline polyline;


    public PolylineDrawer(AMap aMap,Context context) {
        super(aMap,context);
        projection = aMap.getProjection();
        latlngs = new ArrayList<>();

    }

    @Override
    public void add(Point point) {
        LatLng latLng = projection.fromScreenLocation(point);
        latlngs.add(latLng);

        polyline = aMap.addPolyline(new PolylineOptions().add(latLng)
                .color(0xffff0000).width(20).zIndex(4)
                .setCustomTexture(BitmapDescriptorFactory.fromResource(R.drawable.cold_front)));

    }

    @Override
    public void change(Point point) {
        LatLng latLng = projection.fromScreenLocation(new Point(point));
        latlngs.add(latLng);

        polyline.setPoints(latlngs);
    }

    @Override
    public void delete(Point point) {
        if (polyline != null) {
            polyline.remove();

        }
    }

    @Override
    public void showPopWindow() {
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

                if (polyline != null) {
                    polyline.remove();

                }
            }
        });

        btnReverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (polyline != null) {
                    polyline.setCustomTexture(BitmapDescriptorFactory.fromResource(R.drawable.cold_front_reverse));

                }
            }
        });
    }
}
