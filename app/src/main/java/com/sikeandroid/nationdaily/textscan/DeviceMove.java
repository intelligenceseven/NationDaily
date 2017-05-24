package com.sikeandroid.nationdaily.textscan;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/********************************************************************************
 * using for:
 * 丁酉鸡年四月 2017/05/05 17:46
 * @author 西唐王, xtwyzh@gmail.com,blog.xtwroot.com
 * xtwroot Copyrights (c) 2017. All rights reserved.
 ********************************************************************************/
public class DeviceMove extends Activity {

    //摇晃速度临界值
    private static final int SPEED_SHRESHOLD = 50;
    //两次检测的时间间隔
    private static final int UPTATE_INTERVAL_TIME = 200;
    //上次检测时间
    private long lastUpdateTime;

    private SensorManager sensorMag;
    private Sensor gravitySensor;
    //保存上一次记录
    float lastX = 0;
    float lastY = 0;
    float lastZ = 0;

    private boolean moving = true;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sensorMag.registerListener(sensorLis, gravitySensor, SensorManager.SENSOR_DELAY_UI);
        initGravitySensor();
        sensorLis.notify();
    }
    /**
     * 初始化传感器
     */
    private void initGravitySensor(){

        sensorMag=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        gravitySensor = sensorMag.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    @Override
    protected void onPause() {
        sensorMag.unregisterListener(sensorLis);
        super.onPause();
    }

    @Override
    protected void onResume() {
        sensorMag.registerListener(sensorLis, gravitySensor, SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }

    float tMax=0.1f;
    private SensorEventListener sensorLis = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {

            if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
                return;
            }
            //现在检测时间
            long currentUpdateTime = System.currentTimeMillis();
            //两次检测的时间间隔
            long timeInterval = currentUpdateTime - lastUpdateTime;
            //判断是否达到了检测时间间隔
            if(timeInterval < UPTATE_INTERVAL_TIME)
                return;
            //现在的时间变成last时间
            lastUpdateTime = currentUpdateTime;
            //获取加速度数值，以下三个值为重力分量在设备坐标的分量大小
            float x = event.values[SensorManager.DATA_X];

            float y = event.values[SensorManager.DATA_Y];

            float z = event.values[SensorManager.DATA_Z];


            //   Log.e("msg", "x= "+x+" y= "+y);
            //    Log.e("msg", "x= "+x+" y= "+y+" z= "+z);

            float absx = Math.abs(x);
            float absy = Math.abs(y);
            float absz = Math.abs(z);

            if (absx > absy && absx > absz) {

                if (x > tMax) {

                    Log.e("origen", "turn left");
                } else if(x<-tMax){

                    Log.e("origen", "turn right");
                }

            }
            else if (absy > absx && absy > absz) {

                if (y > tMax) {

                    Log.e("origen", "turn up");
                } else if(y<-tMax){

                    Log.e("origen", "turn down");
                }
            }

            else if (absz > absx && absz > absy) {
                if (z > 0) {
                    Log.e("origen", "screen up");
                } else {
                    Log.e("origen", "screen down");
                }
            }
            else {

                Log.e("origen", "unknow action");
            }

            //获得x,y,z的变化值
            float deltaX = x - lastX;
            float deltaY = y - lastY;
            float deltaZ = z - lastZ;
            //备份本次坐标
            lastX = x;
            lastY = y;
            lastZ = z;
            //计算移动速度
            double speed = Math.sqrt(deltaX*deltaX + deltaY*deltaY + deltaZ*deltaZ)/timeInterval * 10000;
            // Log.e("msg", "speed= "+speed);

            if(speed >= SPEED_SHRESHOLD)
            {
                Toast.makeText(DeviceMove.this, "onshake", Toast.LENGTH_SHORT).show();
                moving = true;
            }
            else
                moving = false;


        }
    };

    public boolean isDeviceMoving()
    {
        return moving;
    }

}
