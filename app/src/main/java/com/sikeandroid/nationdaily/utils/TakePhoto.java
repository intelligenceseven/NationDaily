package com.sikeandroid.nationdaily.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.ImageView;
import com.sikeandroid.nationdaily.cosplay.ARCamera;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakePhoto extends CameraParam {

  private static final String TAG = "CameraPreview";
  public static final int FRONT_CAMERA = 1;
  public static final int BACK_CAMERA = 0;
  public static int cameraFlag = BACK_CAMERA;

  public TakePhoto(Context context) {
    super( context );
  }

  public Camera getCameraInstance() {
    if (mCamera == null) {
      //Log.d( TAG, "Camera number: " + Camera.getNumberOfCameras() );
      try {
        ReleaseCamera();
        mCamera = Camera.open( cameraFlag );
      } catch (Exception e) {
        e.printStackTrace();
        Log.d( TAG, "camera is not available" );
      }
    }
    return mCamera;
  }

  private void ReleaseCamera() {
    if (mCamera != null) {
      mCamera.release();
      mCamera = null;
    }
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

  public void changeCamera() {
    mCamera.stopPreview();
    mCamera.release();
    mCamera = null;
    getCameraInstance();
    try {
      mCamera.setPreviewDisplay( mHolder );
    } catch (IOException e) {
      e.printStackTrace();
    }
    mCamera.startPreview();
  }

  @Override public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    Camera.Parameters parameters = mCamera.getParameters();
    int rotation = getDisplayOrientation();
    if (cameraFlag == FRONT_CAMERA) {
      parameters.setRotation( rotation + 180 );
    } else {
      parameters.setRotation( rotation );
    }
    //设置相机旋转
    mCamera.setDisplayOrientation( rotation );
    //设置预览旋转
    mCamera.setParameters( parameters );
  }

  public void changePreview() {

    Camera.Parameters parameters = mCamera.getParameters();
    int rotation = getDisplayOrientation();
    if (cameraFlag == FRONT_CAMERA) {
      parameters.setRotation( rotation + 180 );
    } else {
      parameters.setRotation( rotation );
    }
    //设置相机旋转
    mCamera.setDisplayOrientation( rotation );
    //设置预览旋转
    mCamera.setParameters( parameters );
  }

  //拍照功能
  private Uri outputMediaFileUri;
  private String outputMediaFileType;

  private File getOutputMediaFile() {
    File mediaStorageDir = new File( "/sdcard/com.sike.android/DCIM" );
    //Log.d( TAG, "getOutputMediaFile: " + mediaStorageDir.toString() );
    if (!mediaStorageDir.exists()) {
      if (!mediaStorageDir.mkdirs()) {
        //Log.d( TAG, "failed to create directory" );
        return null;
      }
    }
    String timeStamp = new SimpleDateFormat( "yyyyMMdd_HHmmss" ).format( new Date() );
    File mediaFile =
        new File( mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg" );
    outputMediaFileType = "image/*";
    outputMediaFileUri = Uri.fromFile( mediaFile );
    return mediaFile;
  }

  public Uri getOutputMediaFileUri() {
    return outputMediaFileUri;
  }

  public String getOutputMediaFileType() {
    return outputMediaFileType;
  }

  public void takePicture(final ImageView view) {
    Camera.PictureCallback picture = new Camera.PictureCallback() {
      @Override public void onPictureTaken(byte[] data, Camera camera) {

        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
          Log.d( TAG, "Error creating media file,check storage permissions" );
          return;
        }
        try {
          if (TakePhoto.cameraFlag == TakePhoto.BACK_CAMERA) {
            if (flashFlag == FLASh_OPEN) {
              Camera.Parameters parameters = mCamera.getParameters();
              parameters.setFlashMode( Camera.Parameters.FLASH_MODE_ON );
              mCamera.setParameters( parameters );
            }
          }
          BitmapFactory.Options options = new BitmapFactory.Options();
          options.inTargetDensity = options.inDensity;
          Bitmap srcBitmap = BitmapFactory.decodeByteArray( data, 0, data.length, options );

          Bitmap destBitmap =
              createWatermark( getContext(), srcBitmap, ARCamera.nationClothesId, options );
          //Log.d( TAG, "onPictureTaken: " + destBitmap.getByteCount() );
          view.setImageBitmap( destBitmap );
          FileOutputStream fos = new FileOutputStream( pictureFile );
          destBitmap.compress( Bitmap.CompressFormat.JPEG, 100, fos );

          fos.flush();
          fos.close();

          //view.setImageURI( outputMediaFileUri );
          camera.startPreview();
          safeToTakePicture = true;
          if (cameraFlag == BACK_CAMERA) {
            if (flashFlag == FLASh_OPEN) {
              openFlashLight();
            }
          }
        } catch (FileNotFoundException e) {
          Log.d( TAG, "File not found:" + e.getMessage() );
        } catch (IOException e) {
          Log.d( TAG, "Error accessing file:" + e.getMessage() );
        }
      }
    };
    if (safeToTakePicture) {
      mCamera.takePicture( null, null, picture );
      safeToTakePicture = false;
    }
  }

  public static Bitmap createWatermark(Context context, Bitmap bitmap, int markBitmapId,
      BitmapFactory.Options options) {

    if (cameraFlag == FRONT_CAMERA) {
      Matrix matrix = new Matrix();
      matrix.setScale( -1, 1 );
      matrix.postTranslate( bitmap.getWidth(), 0 );
      bitmap =
          Bitmap.createBitmap( bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true );
    }

    // 获取图片的宽高
    int bitmapWidth = bitmap.getWidth();
    int bitmapHeight = bitmap.getHeight();

    // 创建一个和图片一样大的背景图
    Bitmap bmp = Bitmap.createBitmap( bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888 );
    Canvas canvas = new Canvas( bmp );
    // 画背景图
    canvas.drawBitmap( bitmap, 0, 0, null );

    if (markBitmapId != 0) {
      // 载入水印图片

      Bitmap markBitmap =
          BitmapFactory.decodeResource( context.getResources(), markBitmapId, options );
      Log.d( TAG, "bitmap:width=" + bitmapWidth + ",height=" + bitmapHeight );

      int markBitmapWidth = markBitmap.getWidth();
      int markBitmapHeight = markBitmap.getHeight();
      Log.d( TAG, "markBitmap:width=" + markBitmapWidth + ",height=" + markBitmapHeight );

      //float bitmapX = ARCamera.clothesX;
      //float bitmapY = ARCamera.clothesY;

      // 画图
      //canvas.drawBitmap( markBitmap, bitmapX, bitmapY, null );
      canvas.drawBitmap( markBitmap, ARCamera.matrix, null );
    }

    //保存所有元素
    canvas.save( Canvas.ALL_SAVE_FLAG );
    canvas.restore();

    return bmp;
  }
}