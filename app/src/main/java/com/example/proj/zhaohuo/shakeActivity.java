package com.example.proj.zhaohuo;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class shakeActivity extends AppCompatActivity{
    private SensorManager mSensorManager;
    private Sensor mMagneticSensor;
    private Sensor mAccelerometerSensor;
    private TextView angle,shake_count;
    private LocationManager locationManager;
    private Location mCurrentLocation = null;
    private TextView longitude,latitude,Address;
    private ArrayList urllist;
    private int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);

        //获取手机传感器的管理器
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //通过管理器获取地磁管理器和加速度管理器
        mMagneticSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        urllist = bundle.getStringArrayList("URLLIST");
    }

    private SensorEventListener mSensorEventListener  = new SensorEventListener() {
        float[] accValues = null;
        float[] magValues = null;
        long lastShakeTime = 0;

        @Override
        public void onSensorChanged(SensorEvent event) {
            switch (event.sensor.getType()){
                case Sensor.TYPE_ACCELEROMETER:
                    accValues = event.values.clone();
                    //摇一摇
                    float x = accValues[0];
                    float y = accValues[1];
                    float z = accValues[2];
                    int value = 15;//摇一摇阀值

                    long curTime = System.currentTimeMillis();
                    if((curTime-lastShakeTime)>=1000){
                        lastShakeTime = curTime;
                        if(x >= value || x <= -value || y >= value || y <= -value || z >= value || z <= -value){
                            Log.i("嗯", "检测到摇动");
                            //更新摇一摇 次数
                            Random ra = new Random();
                            int list_number = ra.nextInt(5)+1;
                            Bundle bundle = new Bundle();
                            bundle.putString("url",urllist.get(list_number).toString());
                            Intent intent = new Intent(shakeActivity.this,actDetailActivity.class);
                            intent.putExtras(bundle);
                            startActivityForResult(intent,0);
                        }
                    }
                    else{
                        Toast.makeText(shakeActivity.this,"请过1s再摇一摇",Toast.LENGTH_SHORT);
                    }
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    magValues = event.values.clone();
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        //注册传感器
        mSensorManager.registerListener(mSensorEventListener,mMagneticSensor,SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(mSensorEventListener,mAccelerometerSensor,SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注销
        mSensorManager.unregisterListener(mSensorEventListener);
    }




}
