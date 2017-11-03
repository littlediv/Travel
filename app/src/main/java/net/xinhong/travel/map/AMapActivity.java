package net.xinhong.travel.map;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;

import net.xinhong.travel.R;


/**
 * Created by mac on 2017/3/1.
 */
public class AMapActivity extends Activity {

    private MapView mapview;
    private AMap amap;
    private UiSettings uiSettings;
    private LinearLayout llRoot;
    private CheckBox cbDraw;
    private LayerDrawer layerDrawerInstance;

    private int curLayerType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_map);

        llRoot = (LinearLayout) findViewById(R.id.ll_root);
        cbDraw = (CheckBox) findViewById(R.id.cb_draw);

        RadioGroup rgLayerDrawr = (RadioGroup) findViewById(R.id.rg_layer_drawer);
        RadioButton rbCircle = (RadioButton) findViewById(R.id.rb_circle);
        RadioButton rbPolygon = (RadioButton) findViewById(R.id.rb_polygon);
        RadioButton rbPolyline = (RadioButton) findViewById(R.id.rb_polyline);

        rgLayerDrawr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_polyline:
                        curLayerType = OverlayFactory.OVERLAY_POLYLINE;
                        break;

                    case R.id.rb_polygon:
                        curLayerType = OverlayFactory.OVERLAY_POLYGON;
                        break;

                    case R.id.rb_circle:
                        curLayerType = OverlayFactory.OVERLAY_CIRCLE;
                        break;
                }
            }
        });



        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amap != null) {
                    amap.clear();
                }
            }
        });


        mapview = (MapView) findViewById(R.id.mapview);
        mapview.onCreate(savedInstanceState);// 此方法必须重写
        init();

        cbDraw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    uiSettings.setScrollGesturesEnabled(false);
                }else
                {
                    uiSettings.setScrollGesturesEnabled(true);
                }
            }
        });
    }

    private static final String TAG = "AMapActivity";

    private void init() {
        if (amap == null) {
            amap = mapview.getMap();

            uiSettings = amap.getUiSettings();



            amap.setOnMapTouchListener(new AMap.OnMapTouchListener() {

                @Override
                public void onTouch(MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:

                            if (cbDraw.isChecked()) {
                                layerDrawerInstance = OverlayFactory.getLayerDrawerInstance(curLayerType, amap, AMapActivity.this);
                                layerDrawerInstance.onTouchDown(motionEvent);

                            }

                            break;

                        case MotionEvent.ACTION_MOVE:


                            if (cbDraw.isChecked()) {
                                layerDrawerInstance.onTouchMove(motionEvent);
                            }


                            break;

                        case MotionEvent.ACTION_UP:

                            if (cbDraw.isChecked()) {
                                layerDrawerInstance.onTouchUp(motionEvent);
                            }

                            break;
                    }
                }
            });


        }
    }


    /**
     * 方法必须重写
     */
    protected void onResume() {
        super.onResume();
        mapview.onResume();

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapview.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapview.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapview.onDestroy();
    }
}
