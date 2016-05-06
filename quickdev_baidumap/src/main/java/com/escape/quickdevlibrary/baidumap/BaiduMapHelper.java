package com.escape.quickdevlibrary.baidumap;

import android.location.Location;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.DistanceUtil;

/**
 * Created by 洋 on 2016/5/6.
 */
public class BaiduMapHelper {
    /**
     * 显示当前位置
     *
     * @param mMapView
     * @param lat
     * @param lng
     * @param myLocationRes
     * @return
     */
    public static BaiduMap showMyLocation(TextureMapView mMapView, double lat, double lng, int
            myLocationRes) {

        return showMyLocation(mMapView, new LatLng(lat, lng), myLocationRes);
    }


    /**
     * 显示当前位置
     *
     * @param mMapView
     * @param latLng
     * @param myLocationRes
     * @return
     */
    public static BaiduMap showMyLocation(TextureMapView mMapView, LatLng latLng, int
            myLocationRes) {
        return showMyLocation(mMapView, latLng, myLocationRes, true);
    }


    /**
     * 显示当前位置
     *
     * @param mapView
     * @param latLng
     * @param myLocationRes
     * @param animateTo
     * @return
     */
    public static BaiduMap showMyLocation(TextureMapView mapView, LatLng latLng, int
            myLocationRes, boolean animateTo) {
        BaiduMap mMap = mapView.getMap();
        LatLng point = latLng;
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(point, 12);
        mMap.animateMapStatus(mapStatusUpdate);
        MyLocationData myLocationData = new MyLocationData.Builder().latitude(latLng.latitude).longitude(
                latLng.longitude).build();
        mMap.setMyLocationEnabled(true);
        mMap.setMyLocationData(myLocationData);
        BitmapDescriptor bitmapDescriptor = null;
        if (myLocationRes != 0) {
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(myLocationRes);
        }
        MyLocationConfiguration myLocationConfiguration = new MyLocationConfiguration
                (MyLocationConfiguration.LocationMode.NORMAL, false, bitmapDescriptor);
        mMap.setMyLocationConfigeration(myLocationConfiguration);
        if (animateTo) {
            animateToPosition(mapView, latLng, mMap.getMapStatus().zoom);
        }
        return mMap;
    }

    public static InfoWindow showInfoWindow(TextureMapView mapView, double lat, double lng, View v) {
        //定义用于显示该InfoWindow的坐标点
        LatLng pt = new LatLng(lat, lng);
//创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
        InfoWindow mInfoWindow = new InfoWindow(v, pt, -47);
//显示InfoWindow
        mapView.getMap().showInfoWindow(mInfoWindow);
        return mInfoWindow;
    }

    public static void hideInfoWindow(TextureMapView mapView) {
        mapView.getMap().hideInfoWindow();
    }

    public static LatLng convertCoordinate(LatLng source) {
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
// sourceLatLng待转换坐标
        converter.coord(source);
        return converter.convert();
    }

    public static LatLng convertCoordinate(Location location) {
        return convertCoordinate(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    public static void animateToPosition(TextureMapView view, double lat, double lng, float zoom) {
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(new LatLng(lat,
                lng), zoom);
        view.getMap().animateMapStatus(mapStatusUpdate);
    }

    public static void animateToPosition(TextureMapView mapView, LatLng latLng, float zoom) {
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(latLng, zoom);
        mapView.getMap().animateMapStatus(mapStatusUpdate);
    }

    public static String getReadFriendlyDistance(LatLng latLng, LatLng other) {
        double distance = DistanceUtil.getDistance(latLng, other);
        if (distance > 1000) {
            return String.format("%.2f千米", distance / 1000.0);
        } else {
            return (int) distance + "米";
        }
    }
}
