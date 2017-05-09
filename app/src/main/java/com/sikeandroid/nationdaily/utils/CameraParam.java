package com.sikeandroid.nationdaily.utils;

import android.content.Context;
import android.hardware.Camera;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public abstract class CameraParam extends SurfaceView implements SurfaceHolder.Callback {

  protected static final String TAG = "CameraParam";
  protected SurfaceHolder mHolder;
  protected Camera mCamera;
  public static final int FLASh_OPEN = 1;
  public static final int FLASH_CLOSE = 0;
  public static int flashFlag = FLASH_CLOSE;
  protected boolean safeToTakePicture = false;

  public CameraParam(Context context) {
    super( context );
    mHolder = getHolder();
    mHolder.addCallback( this );
  }

  @Override public void surfaceDestroyed(SurfaceHolder holder) {
    mHolder.removeCallback( this );
    mCamera.setPreviewCallback( null );
    mCamera.stopPreview();
    mCamera.release();
    mCamera = null;
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

  public boolean isFlashLightOn() {
    Camera.Parameters parameters = mCamera.getParameters();
    String flashMode = parameters.getFlashMode();
    if (flashMode.equals( Camera.Parameters.FLASH_MODE_TORCH )) {
      return true;
    } else {
      return false;
    }
  }

  public void flashLightUtils() {
    if (isFlashLightOn()) {
      closeFlashLight();
      flashFlag = FLASH_CLOSE;
    } else {
      openFlashLight();
      flashFlag = FLASh_OPEN;
    }
  }

  public void openFlashLight() {
    Camera.Parameters parameters = mCamera.getParameters();
    parameters.setFlashMode( Camera.Parameters.FLASH_MODE_TORCH );
    mCamera.setParameters( parameters );
  }

  public void closeFlashLight() {
    Camera.Parameters parameters = mCamera.getParameters();
    parameters.setFlashMode( Camera.Parameters.FLASH_MODE_OFF );
    mCamera.setParameters( parameters );
  }
}