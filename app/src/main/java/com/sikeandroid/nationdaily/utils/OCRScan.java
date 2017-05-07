package com.sikeandroid.nationdaily.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.ImageView;
import android.widget.Toast;
import com.tianruiworkroomocr.Native;

public class OCRScan extends CameraParam {

  private static final String TAG = "OCRScan";

  public OCRScan(Context context) {
    super( context );
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
}