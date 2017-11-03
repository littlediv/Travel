package net.xinhong.travel.map;

import android.content.Context;

import com.amap.api.maps.AMap;

/**
 * Created by mac on 2017/3/3.
 */
public class OverlayFactory {
    public static final int OVERLAY_POLYLINE = 1;
    public static final int OVERLAY_POLYGON = 2;
    public static final int OVERLAY_GROUND = 3;
    public static final int OVERLAY_CIRCLE = 4;
    public static final int OVERLAY_MARKER = 5;


    private OverlayFactory() {

    }

    public static LayerDrawer getLayerDrawerInstance(int type, AMap aMap, Context context) {
        LayerDrawer layerDrawer = null;
        switch (type) {
            case OVERLAY_POLYLINE:
                layerDrawer = new PolylineDrawer(aMap, context);
                break;

            case OVERLAY_POLYGON:
                layerDrawer = new PolygonDrawer(aMap, context);
                break;

            case OVERLAY_CIRCLE:
                layerDrawer = new CircleDrawer(aMap, context);
                break;
        }

        return layerDrawer;
    }
}
