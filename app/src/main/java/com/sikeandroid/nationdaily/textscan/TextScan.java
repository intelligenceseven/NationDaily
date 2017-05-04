package com.sikeandroid.nationdaily.textscan;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import com.sikeandroid.nationdaily.R;
import com.sikeandroid.nationdaily.cosplay.CameraPreview;
import com.sikeandroid.nationdaily.cosplay.SettingsCamera;

public class TextScan extends AppCompatActivity {
  private ScanView scanView;
  private CameraPreview mPreview;
  //private Button flashSwitch;
  public static final int FLAG_OPEN = 1;
  public static final int FLAG_CLOSE = 0;
  private int flashFlag = FLAG_CLOSE;
  private Button scanText;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.activity_text_scan );
    //flashSwitch = (Button) findViewById( R.id.flash_switch );

    Toolbar toolbar = (Toolbar) findViewById( R.id.scan_toolbar );
    setSupportActionBar( toolbar );
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled( true );
    }

    scanView = (ScanView) findViewById( R.id.scanview );
    scanView.startScanAnim();
  /*  flashSwitch.setOnClickListener( new View.OnClickListener() {
      @Override public void onClick(View v) {
        Camera.Parameters parameters = mPreview.getCameraInstance().getParameters();
        if (flashFlag == FLAG_CLOSE) {
          parameters.setFlashMode( Camera.Parameters.FLASH_MODE_TORCH );
          flashFlag = FLAG_OPEN;
        } else {
          parameters.setFlashMode( Camera.Parameters.FLASH_MODE_OFF );
          flashFlag = FLAG_CLOSE;
        }
        mPreview.getCameraInstance().setParameters( parameters );
      }
    } );*/
    scanText = (Button) findViewById( R.id.scan_text );
    scanText.setOnClickListener( new View.OnClickListener() {
      @Override public void onClick(View v) {
        scanThread();
      }
    } );
  }

  private void scanThread() {

    new Thread( new Runnable() {
      @Override public void run() {
        try {
          mPreview.scanText();
          Thread.sleep( 1000 );
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    } ).start();
  }



  private void initCamera() {

    CameraPreview.cameraFlag = CameraPreview.BACK_CAMERA;
    mPreview = new CameraPreview( this );
    FrameLayout preview = (FrameLayout) findViewById( R.id.scan_camera );
    preview.addView( mPreview );
    SettingsCamera.passCamera( mPreview.getCameraInstance() );
    SettingsCamera.init();
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
        Camera.Parameters parameters = mPreview.getCameraInstance().getParameters();
        if (flashFlag == FLAG_CLOSE) {
          parameters.setFlashMode( Camera.Parameters.FLASH_MODE_TORCH );
          flashFlag = FLAG_OPEN;
          item.setIcon( R.drawable.ic_flash_on_black_24dp );
        } else {
          parameters.setFlashMode( Camera.Parameters.FLASH_MODE_OFF );
          flashFlag = FLAG_CLOSE;
          item.setIcon( R.drawable.ic_flash_off_black_24dp );
        }
        mPreview.getCameraInstance().setParameters( parameters );
        break;
      case android.R.id.home:
        finish();
        break;
    }
    return true;
  }
}
