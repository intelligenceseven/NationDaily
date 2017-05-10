package com.sikeandroid.nationdaily.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.SurfaceHolder;
import com.tianruiworkroomocr.Native;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class OCRScan extends CameraParam implements Camera.PreviewCallback {

  private static final String TAG = "OCRScan";

  public OCRScan(Context context) {
    super( context );
  }

  @Override public void surfaceCreated(SurfaceHolder holder) {
    getCameraInstance();
    try {
      mCamera.setPreviewDisplay( holder );
      mCamera.startPreview();
    } catch (IOException e) {
      Log.d( TAG, "Error setting camera preview: " + e.getMessage() );
    }

    mCamera.setPreviewCallback( this );
  }

  public Camera getCameraInstance() {
    if (mCamera == null) {
      CameraHandlerThread mThread = new CameraHandlerThread( "camera thread" );
      synchronized (mThread) {
        mThread.openCamera();
      }
    }
    return mCamera;
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

  public Bitmap decodeToBitmap(byte[] data, Camera camera) {
    if (data == null || camera == null) {
      return null;
    }
    Camera.Size size = camera.getParameters().getPreviewSize();
    try {
      YuvImage image = new YuvImage( data, ImageFormat.NV21, size.width, size.height, null );
      if (image != null) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compressToJpeg( new Rect( 0, 0, size.width, size.height ), 80, stream );
        Bitmap bmp = BitmapFactory.decodeByteArray( stream.toByteArray(), 0, stream.size() );

        stream.close();
        return bmp;
      }
    } catch (Exception ex) {

    }
    return null;
  }

  @Override public void onPreviewFrame(byte[] data, Camera camera) {

    //try {
    //  Thread.sleep( 1000 );
    //} catch (InterruptedException e) {
    //  e.printStackTrace();
    //}
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inTargetDensity = options.inDensity;
    Bitmap srcBitmap = decodeToBitmap( data, camera );
    //Log.d( TAG, "srcBitmap width:" + srcBitmap.getWidth() + "height:" + srcBitmap.getHeight() );
    Matrix matrix = new Matrix();
    matrix.setRotate( 90 );
    srcBitmap =
        Bitmap.createBitmap( srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(), matrix,
            true );
    matrix.reset();
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

    int picw = destBitmap.getWidth();
    int pich = destBitmap.getHeight();
    int[] pix = new int[pich * picw];
    destBitmap.getPixels( pix, 0, picw, 0, 0, picw, pich );
    int rlt = Native.recognizeImage( pix, picw, pich );

    if (rlt == 1) {
      String mwholeWord[] = Native.getWholeTextLineResult();
      if (mwholeWord != null) {
        Log.e( TAG, mwholeWord[0] );
        //Toast.makeText( getContext(), mwholeWord[0], Toast.LENGTH_SHORT ).show();
      }
    } else {
      Log.e( TAG, "OcrThread: 无法识别" );
    }
  }

  private class CameraHandlerThread extends HandlerThread {

    Handler mHandler;

    public CameraHandlerThread(String name) {
      super( name );
      start();
      mHandler = new Handler( getLooper() );
    }

    synchronized void notifyCameraOpened() {
      notify();
    }

    void openCamera() {
      mHandler.post( new Runnable() {
        @Override public void run() {
          openCameraOriginal();
          notifyCameraOpened();
        }
      } );
      try {
        wait();
      } catch (InterruptedException e) {
        Log.w( TAG, "wait was interrupted" );
      }
    }
  }

  private void openCameraOriginal() {
    try {
      mCamera = Camera.open();
    } catch (Exception e) {
      Log.d( TAG, "camera is not available" );
    }
  }
}