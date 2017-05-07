package com.sikeandroid.nationdaily.textscan;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import static com.sikeandroid.nationdaily.utils.CameraParam.flashFlag;

public class TextScan extends AppCompatActivity {

  private ScanView scanView;
  private OCRScan mPreview;
  private Button scanText;

  private int mOpenSetLangFlg;

  public static final String mstrFilePathForDat =
      Environment.getExternalStorageDirectory().toString() + "/NationDaily";
  private static final String TAG = "TextScan";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.activity_text_scan );

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

    scanView = (ScanView) findViewById( R.id.scanview );
    scanView.startScanAnim();
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
    SettingsCamera.initTakePhoto();
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
