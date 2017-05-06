package com.sikeandroid.nationdaily.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import com.tianruiworkroomocr.Native;
import java.io.IOException;

public class OCRScan extends SurfaceView implements SurfaceHolder.Callback {

  private static final String TAG = "OCRScan";
  private SurfaceHolder mHolder;
  private Camera mCamera;
  private boolean safeToTakePicture = false;

  public OCRScan(Context context) {
    super( context );
    mHolder = getHolder();
    mHolder.addCallback( this );
  }

  public Camera getCameraInstance() {
    if (mCamera == null) {
      //Log.d( TAG, "Camera number: " + Camera.getNumberOfCameras() );
      try {
        mCamera = Camera.open();
      } catch (Exception e) {
        e.printStackTrace();
        Log.d( TAG, "camera is not available" );
      }
    }
    return mCamera;
  }

  @Override public void surfaceCreated(SurfaceHolder holder) {
    getCameraInstance();
    try {
      mCamera.setPreviewDisplay( holder );
      mCamera.startPreview();
      safeToTakePicture = true;
    } catch (IOException e) {
      Log.d( TAG, "Error setting camera preview: " + e.getMessage() );
    }
  }

  @Override public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    Camera.Parameters parameters = mCamera.getParameters();
    int rotation = getDisplayOrientation();
    parameters.setRotation( rotation );
    //设置相机旋转
    mCamera.setDisplayOrientation( rotation );
    //设置预览旋转
    mCamera.setParameters( parameters );
  }

  @Override public void surfaceDestroyed(SurfaceHolder holder) {
    mHolder.removeCallback( this );
    mCamera.setPreviewCallback( null );
    mCamera.stopPreview();
    mCamera.release();
    mCamera = null;
  }

  public void scanText(final ImageView scanImage) {
    Camera.PictureCallback scan = new Camera.PictureCallback() {
      @Override public void onPictureTaken(byte[] data, Camera camera) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inTargetDensity = options.inDensity;
        Bitmap srcBitmap = BitmapFactory.decodeByteArray( data, 0, data.length, options );
        //Log.d( TAG, "srcBitmap width:" + srcBitmap.getWidth() + "height:" + srcBitmap.getHeight() );
        Matrix matrix = new Matrix();
        matrix.setScale( 0.25f, 0.25f );
        Bitmap midBitmap = Bitmap.createBitmap( srcBitmap, 340, 726, 380, 486, matrix, false );
        Bitmap destBitmap = Bitmap.createBitmap( 700, 800, Bitmap.Config.ARGB_8888 );
        Canvas canvas = new Canvas( destBitmap );
        Paint paint = new Paint();
        //Color.argb( 255, 41, 115, 56 )
        paint.setColor( Color.GREEN );
        paint.setStyle( Paint.Style.FILL );
        canvas.drawRect( 0, 0, 700, 800, paint );
        canvas.drawBitmap( midBitmap, 302, 340, null );
        scanImage.setImageBitmap( destBitmap );
        camera.startPreview();
        safeToTakePicture = true;

        int picw = destBitmap.getWidth();
        int pich = destBitmap.getHeight();
        int[] pix = new int[pich * picw];
        destBitmap.getPixels( pix, 0, picw, 0, 0, picw, pich );
        int rlt = Native.recognizeImage( pix, picw, pich );

        if (rlt == 1) {
          String mwholeWord[] = Native.getWholeTextLineResult();
          if (mwholeWord != null) {
            Log.e( TAG, mwholeWord[0] );
            Toast.makeText( getContext(), mwholeWord[0], Toast.LENGTH_SHORT ).show();
          }
        } else {
          Log.e( TAG, "OcrThread: 无法识别" );
        }
      }
    };
    if (safeToTakePicture) {
      mCamera.takePicture( null, null, scan );
      safeToTakePicture = false;
    }
  }

  public int getDisplayOrientation() {
    Display display = ((WindowManager) getContext().getSystemService(
        Context.WINDOW_SERVICE )).getDefaultDisplay();
    int rotation = display.getRotation();
    int degrees = 0;
    switch (rotation) {
      case Surface.ROTATION_0:
        degrees = 0;
        break;
      case Surface.ROTATION_90:
        degrees = 90;
        break;
      case Surface.ROTATION_180:
        degrees = 180;
        break;
      case Surface.ROTATION_270:
        degrees = 270;
        break;
    }
    Camera.CameraInfo camInfo = new Camera.CameraInfo();
    Camera.getCameraInfo( Camera.CameraInfo.CAMERA_FACING_BACK, camInfo );
    int result = (camInfo.orientation - degrees + 360) % 360;
    return result;
  }
}