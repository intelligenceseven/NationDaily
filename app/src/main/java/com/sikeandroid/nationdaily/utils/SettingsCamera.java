package com.sikeandroid.nationdaily.utils;

import android.hardware.Camera;

public class SettingsCamera {
  static Camera mCamera;
  static Camera.Parameters mParameters;

  public static void passCamera(Camera camera) {
    mCamera = camera;
    mParameters = camera.getParameters();
  }

  public static void initTakePhoto() {
    mParameters.setPreviewSize( 1920, 1080 );
    mParameters.setPictureSize( 1920, 1080 );
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
    mParameters.setPreviewSize( 1920, 1080 );
    mParameters.setFocusMode( "continuous-picture" );
    mParameters.setFlashMode( "off" );
    mCamera.stopPreview();
    mCamera.setParameters( mParameters );
    mCamera.startPreview();
  }
}