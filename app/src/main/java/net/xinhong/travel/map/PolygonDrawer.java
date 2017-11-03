package net.xinhong.travel.map;

import android.content.Context;
import android.graphics.Color;
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
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polygon;
import com.amap.api.maps.model.PolygonOptions;

import net.xinhong.travel.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2017/3/3.
 */
public class PolygonDrawer extends LayerDrawer {

    private List<LatLng> latlngs;
    private Projection projection;
    private Polygon polygon;


    public PolygonDrawer(AMap aMap, Context context) {
        super(aMap, context);

        projection = aMap.getProjection();
        latlngs = new ArrayList<>();
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
                Toast.makeText(mContext, "confirm" + latlngs.size(), Toast.LENGTH_SHORT).show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "cancel", Toast.LENGTH_SHORT).show();

                if (polygon != null) {
                    polygon.remove();

                }
            }
        });

        btnReverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (polygon != null) {
//                    polygon.setCustomTexture(BitmapDescriptorFactory.fromResource(R.drawable.cold_front_reverse));
//
//                }
            }
        });
    }


    @Override
    public void add(Point point) {
        LatLng latLng = projection.fromScreenLocation(point);
        latlngs.add(latLng);

        polygon = aMap.addPolygon(new PolygonOptions().add(latLng)
                .fillColor(Color.LTGRAY).strokeColor(Color.RED).strokeWidth(1).zIndex(4));

    }

    @Override
    public void change(Point point) {
        LatLng latLng = projection.fromScreenLocation(new Point(point));
        latlngs.add(latLng);

        polygon.setPoints(latlngs);
    }

    @Override
    public void delete(Point point) {
        super.delete(point);
    }


}
