package com.zdlw.demo.location;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.BDNotifyListener;//假如用到位置提醒功能，需要import该类
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    protected LatLng hmPos = new LatLng(40.050513, 116.30361);
    protected LatLng latLngXH=new LatLng(23.064248,113.586823);//纬度在前，经度在后
    protected LatLng czPos = new LatLng(40.065817,116.349902);
    protected LatLng latLngMOVE=new LatLng(50.064248,200.586823);
    private BaiduMap baiduMap;
    public LocationClient mLocationClient=null;
    public BDLocationListener myListener=new MyLocationListener();
    private MapView mapView;

    public class MyLocationListener implements BDLocationListener {

        // 在这个方法里面接收定位结果
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location != null) {
                MyLocationData.Builder builder = new MyLocationData.Builder();
                builder.accuracy(location.getRadius());		// 设置精度
                builder.direction(location.getDirection());	// 设置方向
                builder.latitude(location.getLatitude());	// 设置纬度
                builder.longitude(location.getLongitude());	// 设置经度
                MyLocationData locationData = builder.build();
                baiduMap.setMyLocationData(locationData);	// 把定位数据显示到地图上
            }

            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i("BaiduLocationApiDem", sb.toString());
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mapView = (MapView) findViewById(R.id.map_main_view);
//        baiduMap= mapView.getMap();
//
//        //1是否开启缩放按钮
//        mapView.showZoomControls(true);
//        //2是否开启比例尺
//        mapView.showScaleControl(true);
//        //3设置最大放大与最小缩放级别，20为最大，3为最小，极限
//        baiduMap.setMaxAndMinZoomLevel(20f,3f);
//        //4获取最大最小缩放级别
//        float MaxZoomLevel=baiduMap.getMaxZoomLevel();
//        float MinZoomLevel=baiduMap.getMinZoomLevel();
//        //5设置地图中心点
//        MapStatusUpdate statusUpdate= MapStatusUpdateFactory.newLatLng(latLngXH);//MapStatusUpdateFactory工厂类，newLatLng设置中心点坐标
//        baiduMap.setMapStatus(statusUpdate);//改变地图状态。给予地图要改变的状态，如变大，移动等，之后就会根据数值变动当前显示的状态
//        //6设置当前缩放级别为15
//        statusUpdate=MapStatusUpdateFactory.zoomTo(15);//zoomTo缩放级别
//        baiduMap.setMapStatus(statusUpdate);
//        //7设置缩放
//        statusUpdate=MapStatusUpdateFactory.zoomOut();//缩小
//        statusUpdate=MapStatusUpdateFactory.zoomIn();//放大
//        //设置角度
//        float rotate=baiduMap.getMapStatus().rotate+23;//获取当前的角度并加上23度
//        MapStatus mapStatus=new MapStatus.Builder().rotate(rotate).build();//旋转23度
//        float overlook=baiduMap.getMapStatus().overlook+-5;//获取当前的俯仰角度并加上-5度,注意：只支持负数
//        mapStatus=new MapStatus.Builder().overlook(overlook).build();//俯仰-5度
//        statusUpdate=MapStatusUpdateFactory.newMapStatus(mapStatus);//更新
//        baiduMap.setMapStatus(statusUpdate);
//        //设置移动
//        statusUpdate=MapStatusUpdateFactory.newLatLng(latLngMOVE);
//        baiduMap.animateMapStatus(statusUpdate,3000);//3秒完成移动


        mLocationClient =new LocationClient(getApplicationContext());//声明LocationClient类
        mLocationClient.registerLocationListener(myListener); //注册监听函数
        initLocation();
        mLocationClient.start();//开始定位

    }
    /**
     * 行为属性的设置
     */
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span= 5000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }



}
