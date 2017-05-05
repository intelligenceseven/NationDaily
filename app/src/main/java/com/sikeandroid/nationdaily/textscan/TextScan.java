package com.sikeandroid.nationdaily.textscan;

import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import com.sikeandroid.nationdaily.R;
import com.sikeandroid.nationdaily.util.CameraPreview;
import com.sikeandroid.nationdaily.util.OCRScan;
import com.sikeandroid.nationdaily.util.SettingsCamera;
import com.tianruiworkroomocr.Native;
import java.util.Timer;
import java.util.TimerTask;

public class TextScan extends AppCompatActivity {

  private ScanView scanView;
  private OCRScan mPreview;
  private ImageView scanImage;
  public static final int FLAG_OPEN = 1;
  public static final int FLAG_CLOSE = 0;
  private int flashFlag = FLAG_CLOSE;
  private Button scanText;

  private int mOpenSetLangFlg;
  private Bitmap mBitmap;

  private final Timer timer = new Timer();
  private TimerTask task = new TimerTask() {
    @Override public void run() {
      Message message = new Message();
      message.what = 1;
      handler.sendMessage( message );
    }
  };

  Handler handler = new Handler() {
    @Override public void handleMessage(Message msg) {
      mPreview.scanText( scanImage );
      super.handleMessage( msg );
    }
  };

  private static String[] mwholeWord;
  public static final String mstrFilePathForDat =
      Environment.getExternalStorageDirectory().toString() + "/NationDaily";
  private static final String TAG = "TextScan";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.activity_text_scan );

    int rlt = Native.openOcrEngine( mstrFilePathForDat );
    rlt = Native.setOcrLanguage( Native.TIANRUI_LANGUAGE_CHINESE_MIXED );

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
        //scanThread();
        scanView.startScanMatchingAnim();
      }
    } );
    timer.schedule( task, 4000, 2000 );
    scanImage = (ImageView) findViewById( R.id.scan_image );
  }

  private void OcrThread() {

        /*mPreview.scanText();

          try {
              Thread.sleep( 1000 );
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
        mBitmap = BitmapFactory.decodeFile(mImgFilePath);
        int picw = mBitmap.getWidth();
        int pich = mBitmap.getHeight();
        int[] pix = new int[pich * picw];
        mBitmap.getPixels(pix,0,picw,0,0,picw,pich);
        int rlt = Native.recognizeImage(pix,picw,pich);

        if(rlt == 1)
        {
            mwholeWord = Native.getWholeWordResult();
            Log.e(TAG,mwholeWord[0]);
        }
        else
        {
            Log.e(TAG, "OcrThread: 无法识别");
        }*/

  }

  private void initCamera() {

    CameraPreview.cameraFlag = CameraPreview.BACK_CAMERA;
    mPreview = new OCRScan( this );
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
