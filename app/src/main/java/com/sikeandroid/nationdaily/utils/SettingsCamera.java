package com.sikeandroid.nationdaily.utils;

import android.hardware.Camera;
import android.util.Log;

public class SettingsCamera {
  private static final String TAG = "SettingsCamera";
  static Camera mCamera;
  static Camera.Parameters mParameters;
  private static int defaultWidth;
  private static int defaultHeight;
  private static float scaleRatio;

  //private static DisplayMetrics dm;

  public static void passCamera(Camera camera) {
    //SettingsCamera.dm = dm;
    mCamera = camera;
    mParameters = camera.getParameters();
  }

  public static void initTakePhoto() {
    getDefault();
    //mParameters.setPreviewSize( 1920, 1080 );
    //mParameters.setPictureSize( 1920, 1080 );
    mParameters.setPreviewSize( defaultWidth, defaultHeight );
    mParameters.setPictureSize( defaultWidth, defaultHeight );
    Log.d( TAG, "initTakePhoto: " + defaultWidth + "," + defaultHeight );
    mParameters.setJpegQuality( 100 );
    mParameters.setFocusMode( "continuous-picture" );
    if (TakePhoto.cameraFlag == TakePhoto.BACK_CAMERA) {
      mParameters.setFlashMode( "off" );
    }
    mCamera.stopPreview();
    mCamera.setParameters( mParameters );
    mCamera.startPreview();
  }

  public static void initOCRScan() {
    getDefault();
    mParameters.setPreviewSize( defaultWidth, defaultHeight );
    mParameters.setFocusMode( "continuous-picture" );
    mParameters.setFlashMode( "off" );
    mCamera.stopPreview();
    mCamera.setParameters( mParameters );
    mCamera.startPreview();
  }

  public static void getDefault() {

    defaultHeight = 1080;
    defaultWidth = 1920;
    /*WindowManager wm =
        (WindowManager) MyApplication.getContext().getSystemService( Context.WINDOW_SERVICE );
    int screenWidth = wm.getDefaultDisplay().getWidth();
    int screenHeight = wm.getDefaultDisplay().getHeight();
    List<Camera.Size> previewSizes = mParameters.getSupportedPreviewSizes();
    List<Camera.Size> pictureSizes = mParameters.getSupportedPreviewSizes();
    for (Camera.Size previewSize : previewSizes) {
      for (Camera.Size pictureSize : pictureSizes) {
        if (previewSize.equals( pictureSize )) {
          //if (previewSize.width / screenHeight == previewSize.height / screenWidth) {
          //ARCamera.matrix.setScale( previewSize.width/screenWidth,previewSize.width/screenWidth, );
          defaultWidth = 1280;
          defaultHeight = 720;
          //}
          //scaleRatio = (float) defaultWidth / screenHeight;
          return;
        }
      }
    }*/
  }

  public static float getScaleRatio() {
    return scaleRatio;
  }
}