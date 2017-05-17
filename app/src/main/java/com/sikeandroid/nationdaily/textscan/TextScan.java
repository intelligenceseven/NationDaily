package com.sikeandroid.nationdaily.textscan;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.sikeandroid.nationdaily.R;
import com.sikeandroid.nationdaily.utils.OCRScan;
import com.sikeandroid.nationdaily.utils.SettingsCamera;
import com.sikeandroid.nationdaily.utils.TakePhoto;
import com.tianruiworkroomocr.Native;

import static com.sikeandroid.nationdaily.utils.CameraParam.FLASH_CLOSE;

public class TextScan extends AppCompatActivity {

  private ScanView scanView;
  private OCRScan mPreview;
  private Button scanText;

    /******************************************************************/
    //摇晃速度临界值
    private static final int SPEED_SHRESHOLD = 10;
    //两次检测的时间间隔
    private static final int UPTATE_INTERVAL_TIME = 100;
    //上次检测时间
    private long lastUpdateTime;

    private SensorManager sensorMag;
    private Sensor gravitySensor;
    //保存上一次记录
    float lastX = 0;
    float lastY = 0;
    float lastZ = 0;

    private boolean moving = false;


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
                //Toast.makeText(TextScan.this, "onshake", Toast.LENGTH_SHORT).show();
                moving = true;
            }
            else
                moving = false;


        }
    };

    /**
     * 初始化传感器
     */
    private void initGravitySensor(){

        sensorMag=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        gravitySensor = sensorMag.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    public boolean isDeviceMoving()
    {
        return moving;
    }

     /******************************************************************/


    //private Button flashSwitch;
    public static final int FLAG_OPEN = 1;
    public static final int FLAG_CLOSE = 0;
    private int flashFlag = FLAG_CLOSE;
    private int mOpenSetLangFlg;

  public static final String mstrFilePathForDat =
      Environment.getExternalStorageDirectory().toString() + "/NationDaily";
  private static final String TAG = "TextScan";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.activity_text_scan );
    scanView = (ScanView) findViewById( R.id.scanview );
    scanView.startScanAnim();

    Native.openOcrEngine( mstrFilePathForDat );
    int rlt = Native.setOcrLanguage( Native.TIANRUI_LANGUAGE_CHINESE_MIXED );

    if (rlt != 1) {
      Toast.makeText( this, "加载库失败！", Toast.LENGTH_SHORT ).show();
    } else {
      mOpenSetLangFlg = 1;
    }

    Toolbar toolbar = (Toolbar) findViewById( R.id.scan_toolbar );
    setSupportActionBar( toolbar );
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled( true );
    }

    scanText = (Button) findViewById( R.id.scan_text );
    scanText.setOnClickListener( new View.OnClickListener() {
      @Override public void onClick(View v) {
        scanView.startScanMatchingAnim();
      }
    } );
  }

  private void initCamera() {

    TakePhoto.cameraFlag = TakePhoto.BACK_CAMERA;
    mPreview = new OCRScan( this );
    FrameLayout preview = (FrameLayout) findViewById( R.id.scan_camera );
    preview.addView( mPreview );
    SettingsCamera.passCamera( mPreview.getCameraInstance() );
    SettingsCamera.initOCRScan();
  }

  @Override protected void onPause() {
    super.onPause();
    mPreview = null;
  }

  @Override protected void onResume() {
    super.onResume();
    if (mPreview == null) {
      initCamera();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate( R.menu.menu_flash, menu );
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.flash_switch:
        mPreview.flashLightUtils();
        if (flashFlag == FLASH_CLOSE) {
          item.setIcon( R.drawable.ic_flash_off_black_24dp );
        } else {
          item.setIcon( R.drawable.ic_flash_on_black_24dp );
        }
        break;
      case android.R.id.home:
        finish();
        break;
    }
    return true;
  }
}
