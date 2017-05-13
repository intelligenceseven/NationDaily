package com.sikeandroid.nationdaily.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.media.FaceDetector;
import android.net.Uri;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.ImageView;
import com.sikeandroid.nationdaily.cosplay.ARCamera;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.sikeandroid.nationdaily.cosplay.ARCamera.faceTrace;
import static com.sikeandroid.nationdaily.cosplay.ARCamera.matrix;

public class TakePhoto extends CameraParam implements Camera.PreviewCallback {

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
    //mCamera.setPreviewCallback( this );
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

      //float scaleRatio = SettingsCamera.getScaleRatio();
      //Matrix screenScale = new Matrix();
      //float[] matrixFloat = new float[9];
      //matrix.getValues( matrixFloat );
      //screenScale.setScale( scaleRatio, scaleRatio );
      //markBitmap =
      //    Bitmap.createBitmap( markBitmap, 0, 0, markBitmap.getWidth(), markBitmap.getHeight(),
      //        screenScale, true );
      //matrixFloat[0] *= scaleRatio;
      //matrixFloat[2] *= scaleRatio;
      //matrixFloat[4] *= scaleRatio;
      //matrixFloat[5] *= scaleRatio;
      //matrixFloat[6] *= scaleRatio;
      //matrixFloat[7] *= scaleRatio;
      //Matrix matrix = new Matrix();
      //matrix.setValues( matrixFloat );

      //float bitmapX = ARCamera.clothesX;
      //float bitmapY = ARCamera.clothesY;

      // 画图
      //canvas.drawBitmap( markBitmap, bitmapX, bitmapY, null );
      //ARCamera.matrix.postScale( SettingsCamera.getScaleRatio(), SettingsCamera.getScaleRatio() );
      canvas.drawBitmap( markBitmap, ARCamera.matrix, null );
    }

    //保存所有元素
    canvas.save( Canvas.ALL_SAVE_FLAG );
    canvas.restore();

    return bmp;
  }

  private int imageWidth, imageHeight;
  private int numberOfFace = 5;
  private FaceDetector myFaceDetect;
  private FaceDetector.Face[] myFace;
  float myEyesDistance;
  int numberOfFaceDetected;

  @Override public void onPreviewFrame(byte[] data, Camera camera) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inTargetDensity = options.inDensity;
    Bitmap srcBitmap = decodeToBitmap( data, camera );
    Bitmap destBitmap;
    //Log.d( TAG, "srcBitmap width:" + srcBitmap.getWidth() + "height:" + srcBitmap.getHeight() );
    Matrix matrix = new Matrix();
    matrix.setRotate( 90 );
    srcBitmap =
        Bitmap.createBitmap( srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(), matrix,
            true );
    imageWidth = srcBitmap.getWidth();
    imageHeight = srcBitmap.getHeight();
    destBitmap = Bitmap.createBitmap( imageWidth, imageHeight, Bitmap.Config.RGB_565 );
    myFace = new FaceDetector.Face[numberOfFace];
    myFaceDetect = new FaceDetector( imageWidth, imageHeight, numberOfFace );

    Canvas canvas = new Canvas( destBitmap );
    canvas.drawBitmap( srcBitmap, 0.0F, 0.0F, new Paint() );
    Paint myPaint = new Paint();
    myPaint.setColor( Color.GREEN );
    myPaint.setStyle( Paint.Style.STROKE );
    myPaint.setStrokeWidth( 3 );          //设置位图上paint操作的参数
    numberOfFaceDetected = myFaceDetect.findFaces( destBitmap, myFace );
    Log.d( TAG, "numberOfFaceDetected" + numberOfFaceDetected );

    for (int i = 0; i < numberOfFaceDetected; i++) {
      FaceDetector.Face face = myFace[i];
      PointF myMidPoint = new PointF();
      face.getMidPoint( myMidPoint );
      myEyesDistance = face.eyesDistance();   //得到人脸中心点和眼间距离参数，并对每个人脸进行画框
      canvas.drawRect(            //矩形框的位置参数
          (int) (myMidPoint.x - myEyesDistance), (int) (myMidPoint.y - myEyesDistance),
          (int) (myMidPoint.x + myEyesDistance), (int) (myMidPoint.y + myEyesDistance), myPaint );
      Log.d( TAG, "canvas draw" );
    }
    faceTrace.setImageBitmap( destBitmap );
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

  /*
  private long mScanBeginTime = 0;   // 扫描开始时间
  private long mScanEndTime = 0;   // 扫描结束时间
  private long mSpecPreviewTime = 0;   // 扫描持续时间
  int numberOfFaceDetected;    //最终识别人脸数目

  @Override public void onPreviewFrame(byte[] data, Camera camera) {
    Log.d( TAG, "onPreviewFrame" );
    //Camera.Size localSize = camera.getParameters().getPreviewSize();  //获得预览分辨率
    YuvImage localYuvImage = new YuvImage( data, 17, 1080, 1920, null );
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    localYuvImage.compressToJpeg( new Rect( 0, 0, 1080, 1920 ), 80,
        localByteArrayOutputStream );    //把摄像头回调数据转成YUV，再按图像尺寸压缩成JPEG，从输出流中转成数组
    byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
    drawFace( arrayOfByte );
  }

  public void drawFace(byte[] paramArrayOfByte) {
    Log.d( TAG, "paramArrayOfByte: " + paramArrayOfByte );
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    Bitmap localBitmap1 =
        BitmapFactory.decodeByteArray( paramArrayOfByte, 0, paramArrayOfByte.length, localOptions );
    int i = localBitmap1.getWidth();
    int j = localBitmap1.getHeight();   //从上步解出的JPEG数组中接出BMP，即RAW->JPEG->BMP
    Log.d( TAG, "drawFace: " + i + "," + j );
    Matrix localMatrix = new Matrix();
    //int k = cameraResOr;
    Bitmap localBitmap2 = null;
    FaceDetector localFaceDetector = null;

    localFaceDetector = new FaceDetector( i, j, 1 );
    localMatrix.postRotate( 90.0F, i / 2, j / 2 );
    localBitmap2 = Bitmap.createBitmap( i, j, Bitmap.Config.RGB_565 );

    FaceDetector.Face[] arrayOfFace = new FaceDetector.Face[5];
    Paint localPaint1 = new Paint();
    Paint localPaint2 = new Paint();
    localPaint1.setDither( true );
    localPaint2.setColor( Color.RED );
    localPaint2.setStyle( Paint.Style.STROKE );
    localPaint2.setStrokeWidth( 2.0F );
    Canvas localCanvas = new Canvas();
    localCanvas.setBitmap( localBitmap2 );
    localCanvas.setMatrix( localMatrix );
    localCanvas.drawBitmap( localBitmap1, 0.0F, 0.0F,
        localPaint1 ); //该处将localBitmap1和localBitmap2关联（可不要？）

    numberOfFaceDetected = localFaceDetector.findFaces( localBitmap2, arrayOfFace ); //返回识脸的结果
    Paint myPaint = new Paint();
    myPaint.setColor( Color.GREEN );
    myPaint.setStyle( Paint.Style.STROKE );
    myPaint.setStrokeWidth( 3 );          //设置位图上paint操作的参数
    Log.d( TAG, "drawFace: " + localBitmap2.getWidth() + "," + localBitmap2.getHeight() );

    for (int k = 0; k < numberOfFaceDetected; k++) {
      //Canvas canvas = mHolder.lockCanvas();
      FaceDetector.Face face = arrayOfFace[k];
      PointF myMidPoint = new PointF();
      face.getMidPoint( myMidPoint );
      float myEyesDistance = face.eyesDistance();   //得到人脸中心点和眼间距离参数，并对每个人脸进行画框
      localCanvas.drawRect(            //矩形框的位置参数
          (int) (myMidPoint.x - myEyesDistance), (int) (myMidPoint.y - myEyesDistance),
          (int) (myMidPoint.x + myEyesDistance), (int) (myMidPoint.y + myEyesDistance), myPaint );
      //mHolder.unlockCanvasAndPost( canvas );
      //mHolder.lockCanvas( new Rect( 0, 0, 0, 0 ) );
      //mHolder.unlockCanvasAndPost( canvas );
    }
    faceTrace.setImageBitmap( localBitmap2 );

    //faceTrace.setImageBitmap( localBitmap2 );
    //localBitmap2.recycle();
    //localBitmap1.recycle();   //释放位图资源

    //FaceDetectDeal( numberOfFaceDetected );
    Log.d( TAG, "numberOfFaceDetected" + numberOfFaceDetected );
  }*/
}