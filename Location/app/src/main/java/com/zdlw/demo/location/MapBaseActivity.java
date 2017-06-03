package com.zdlw.demo.location;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;


/**
 * Created by Administrator on 2017/4/9.
 */
public abstract class MapBaseActivity extends AppCompatActivity{

    protected MapView mMapView;
    protected BaiduMap baiduMap;
    protected LatLng hmPos = new LatLng(40.050513, 116.30361);
    protected LatLng latLngXH=new LatLng(23.064248,113.586823);//纬度在前，经度在后
    protected LatLng czPos = new LatLng(40.065817,116.349902);
    protected LatLng latLngMOVE=new LatLng(50.064248,200.586823);
    protected UiSettings uiSettings;



    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMapView = (MapView) findViewById(R.id.map_main_view);
        if (mMapView!=null){
            Log.e("ZDLW","SS");
        }
        baiduMap = mMapView.getMap();

        //1是否开启缩放按钮
        mMapView.showZoomControls(true);
        //2是否开启比例尺
        mMapView.showScaleControl(true);
        //3设置最大放大与最小缩放级别，20为最大，3为最小，极限
//        baiduMap.setMaxAndMinZoomLevel(20f,3f);
        //4获取最大最小缩放级别
        float MaxZoomLevel=baiduMap.getMaxZoomLevel();
        float MinZoomLevel=baiduMap.getMinZoomLevel();
        //5设置地图中心点
//        MapStatusUpdate statusUpdate= MapStatusUpdateFactory.newLatLng(hmPos);//MapStatusUpdateFactory工厂类，newLatLng设置中心点坐标
//        baiduMap.setMapStatus(statusUpdate);//改变地图状态。给予地图要改变的状态，如变大，移动等，之后就会根据数值变动当前显示的状态
        //6设置当前缩放级别为15
//        statusUpdate=MapStatusUpdateFactory.zoomTo(15);//zoomTo缩放级别
//        baiduMap.setMapStatus(statusUpdate);
        //7设置缩放
//        statusUpdate=MapStatusUpdateFactory.zoomOut();//缩小
//        statusUpdate=MapStatusUpdateFactory.zoomIn();//放大
        //设置角度
//        float rotate=baiduMap.getMapStatus().rotate+23;//获取当前的角度并加上23度
//        MapStatus mapStatus=new MapStatus.Builder().rotate(rotate).build();//旋转23度
//        float overlook=baiduMap.getMapStatus().overlook+-5;//获取当前的俯仰角度并加上-5度,注意：只支持负数
//        mapStatus=new MapStatus.Builder().overlook(overlook).build();//俯仰-5度
//        statusUpdate=MapStatusUpdateFactory.newMapStatus(mapStatus);//更新
//        baiduMap.setMapStatus(statusUpdate);
        //设置移动
//        statusUpdate=MapStatusUpdateFactory.newLatLng(latLngMOVE);
//        baiduMap.animateMapStatus(statusUpdate,3000);//3秒完成移动
        //8获取地图Ui控制器，指南针
//        uiSettings = baiduMap.getUiSettings();
//        uiSettings.setCompassEnabled(true);//是否显示指南针
        initdata();
    }

    protected abstract void initdata();

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
}
