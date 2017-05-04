package com.sikeandroid.nationdaily.cosplay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Camera;
import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.ImageView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

  private static final String TAG = "CameraPreview";
  private SurfaceHolder mHolder;
  private Camera mCamera;
  public static final int FRONT_CAMERA = 1;
  public static final int BACK_CAMERA = 0;
  public static int cameraFlag = FRONT_CAMERA;
  private boolean safeToTakePicture = false;

  public CameraPreview(Context context) {
    super( context );
    mHolder = getHolder();
    mHolder.addCallback( this );
  }

  public Camera getCameraInstance() {
    if (mCamera == null) {
      //Log.d( TAG, "Camera number: " + Camera.getNumberOfCameras() );
      try {
        mCamera = Camera.open( cameraFlag );
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

  @Override public void surfaceDestroyed(SurfaceHolder holder) {
    mHolder.removeCallback( this );
    mCamera.setPreviewCallback( null );
    mCamera.stopPreview();
    mCamera.release();
    mCamera = null;
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
    Camera.PictureCallback mPicture = new Camera.PictureCallback() {
      @Override public void onPictureTaken(byte[] data, Camera camera) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
          Log.d( TAG, "Error creating media file,check storage permissions" );
          return;
        }
        try {
          FileOutputStream fos = new FileOutputStream( pictureFile );
          fos.write( data );
          fos.close();
          //Bitmap srcBitmap = BitmapFactory.decodeFile( pictureFile.getAbsolutePath() );
          //view.setImageBitmap( srcBitmap );

          Bitmap destBitmap =
              createWatermark( getContext(), pictureFile, ARCamera.nationClothesId );
          //Log.d( TAG, "onPictureTaken: " + destBitmap.getByteCount() );
          view.setImageBitmap( destBitmap );

          fos = new FileOutputStream( pictureFile );
          destBitmap.compress( Bitmap.CompressFormat.JPEG, 100, fos );

          fos.flush();
          fos.close();

          //view.setImageURI( outputMediaFileUri );
          camera.startPreview();
          safeToTakePicture = true;
        } catch (FileNotFoundException e) {
          Log.d( TAG, "File not found:" + e.getMessage() );
        } catch (IOException e) {
          Log.d( TAG, "Error accessing file:" + e.getMessage() );
        }
      }
    };
    if (safeToTakePicture) {

      mCamera.takePicture( null, null, mPicture );
      safeToTakePicture = false;
    }
  }

  public void scanText() {
    final File mediaStorageDir = new File( "/sdcard/com.sike.android" );
    //Log.d( TAG, "getOutputMediaFile: " + mediaStorageDir.toString() );
    if (!mediaStorageDir.exists()) {
      if (!mediaStorageDir.mkdirs()) {
        Log.d( TAG, "failed to create directory" );
        return;
      }
    }
    mCamera.takePicture( null, null, new Camera.PictureCallback() {
      @Override public void onPictureTaken(byte[] data, Camera camera) {
        //File textPhoto = new File( "/storage/emulated/0/DCIM/Camera", "text.jpg" );
        File textPhoto = new File( "/sdcard/com.sike.android", "text.jpg" );
        if (textPhoto.exists()) {
          textPhoto.delete();
          Log.d( TAG, "删除成功" );
        }
        try {
          FileOutputStream fos = new FileOutputStream( textPhoto );
          fos.write( data );
          fos.close();
          Bitmap srcBitmap = BitmapFactory.decodeFile( textPhoto.getAbsolutePath() );
          Matrix matrix = new Matrix();
          matrix.setScale( 0.25f, 0.25f );
          Bitmap midBitmap = Bitmap.createBitmap( srcBitmap, 340, 726, 380, 486, matrix, false );
          //midBitmap.compress( Bitmap.CompressFormat.PNG, 100, fos );

          //Log.d( TAG, "" );
          Bitmap destBitmap = Bitmap.createBitmap( 700, 800, Bitmap.Config.ARGB_8888 );
          Canvas canvas = new Canvas( destBitmap );
          Paint paint = new Paint();
          paint.setColor( Color.WHITE );
          paint.setStyle( Paint.Style.FILL );
          canvas.drawRect( 0, 0, 700, 800, paint );
          canvas.drawBitmap( midBitmap, 302, 340, null );
          fos = new FileOutputStream( textPhoto );

          fos = new FileOutputStream( textPhoto );
          destBitmap.compress( Bitmap.CompressFormat.JPEG, 100, fos );
          fos.flush();

          fos.close();

          camera.startPreview();
          Log.d( TAG, "保存成功" );
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } );
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

  /**
   * 添加水印
   *
   * @param context 上下文
   * @param markBitmapId 水印图片
   * @return bitmap      打了水印的图
   */
  public static Bitmap createWatermark(Context context, File srcFile, int markBitmapId) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inTargetDensity = options.inDensity;
    Bitmap bitmap = BitmapFactory.decodeFile( srcFile.getAbsolutePath(), options );
    if (cameraFlag == FRONT_CAMERA) {
      Matrix matrix = new Matrix();
      matrix.setScale( -1, 1 );
      matrix.postTranslate( bitmap.getWidth(), 0 );
      bitmap =
          Bitmap.createBitmap( bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true );
    }

    // 当水印图片没有的时候，返回原图
    if (markBitmapId == 0) {
      return bitmap;
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

      // 图片开始的坐标
      //float bitmapX = (float) (bitmapWidth / 2 - markBitmapWidth / 2);//这里的-10和下面的-20都是微调的结果
      //float bitmapY;
      //if (cameraFlag == FRONT_CAMERA) {
      //  bitmapY = (float) 100;
      //} else {
      //  bitmapY = (float) (bitmapHeight / 2 - markBitmapHeight / 2);
      //}
      float bitmapX = ARCamera.clothesX;
      float bitmapY = ARCamera.clothesY;

      // 画图
      canvas.drawBitmap( markBitmap, bitmapX, bitmapY, null );
    }

    //保存所有元素
    canvas.save( Canvas.ALL_SAVE_FLAG );
    canvas.restore();

    return bmp;
  }
}
