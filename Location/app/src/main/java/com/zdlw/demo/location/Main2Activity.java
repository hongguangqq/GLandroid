package com.zdlw.demo.location;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class Main2Activity extends AppCompatActivity {
    public LocationClient mLocationClient=null;
    public MyLocationListener mMyLocationListener=null;
    public Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.baidu.location.f");
                startService(intent);
                Log.i("1", "1");
                mMyLocationListener = new MyLocationListener();
                location(mMyLocationListener);
            }
        });
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // Receive Location
//            appSession.setBdLocation(location);
            Log.i("baidu", "baidu");
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
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\ndirection : ");
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append(location.getDirection());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\noperationers:");
                sb.append(location.getOperators());
            }
            double lat= location.getLatitude();
            double lon=location.getLongitude();
            Log.i("province",location.getProvince()+"");
            Log.i("city",location.getCity()+"");
            Log.i("lat", "" + lat);
            Log.i("lon",""+lon);
            Log.i("BaiduLocationApiDem", sb.toString());
        }

    }
    public void location(BDLocationListener listener) {
        mLocationClient = new LocationClient(getApplicationContext());
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);// 中文地址
        option.setCoorType("bd09ll");// gcj02 国测局经纬度坐标系 ；bd09 百度墨卡托坐标系；bd09ll
        // 百度经纬度坐标系
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);// 设置定位模式
        option.setScanSpan(5*60000);//检查周期 小于1秒的按1秒
        mLocationClient.setLocOption(option);
        Log.i("2", "2");
        mLocationClient.registerLocationListener(listener);
        mLocationClient.start();
    }
    /**
     * 用这个方法的时候要注意ct是 调用这个函数类的context，方法的参数应该是this.getApplication传到这个方法里面去，而不是这个类的context，一定要注意。
     * @param ct
     */
    public void location(Context ct) {
        mLocationClient = new LocationClient(ct);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);// 中文地址
        option.setCoorType("bd09ll");// gcj02 国测局经纬度坐标系 ；bd09 百度墨卡托坐标系；bd09ll
        // 百度经纬度坐标系
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);// 设置定位模式
        option.setScanSpan(5 * 60000);//检查周期 小于1秒的按1秒
        mLocationClient.setLocOption(option);
        Log.i("2", "2");
        mLocationClient.registerLocationListener(new MyLocationListener());
        mLocationClient.start();
    }


}
