package com.sikeandroid.nationdaily.cosplay;

import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.sikeandroid.nationdaily.R;
import java.io.File;

import static com.sikeandroid.nationdaily.textscan.TextScan.FLAG_CLOSE;
import static com.sikeandroid.nationdaily.textscan.TextScan.FLAG_OPEN;

public class ARCamera extends AppCompatActivity {

  private ImageButton takePhoto;
  private ImageButton changeCamera;
  private CameraPreview mPreview;
  private ImageView mediaPreview;

  public static ImageView clothes;
  public static FrameLayout mainInterface;

  //民族服装相关信息
  public static int nationClothesId;
  public static String nationName;
  public static float clothesX;
  public static float clothesY;

  //public DrawerLayout drawClothes;
  private ImageButton flash;
  private int flashFlag = FLAG_CLOSE;

  @Override protected void onCreate(Bundle savedInstanceState) {
    //满屏显示
    this.getWindow()
        .setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN );
    super.onCreate( savedInstanceState );
    setContentView( R.layout.activity_ar_camera );

    takePhoto = (ImageButton) findViewById( R.id.take_photo );
    mediaPreview = (ImageView) findViewById( R.id.preview );
    changeCamera = (ImageButton) findViewById( R.id.change_camera );
    mainInterface = (FrameLayout) findViewById( R.id.main_interface );

    flash = (ImageButton) findViewById( R.id.flash );
    if (CameraPreview.cameraFlag == CameraPreview.BACK_CAMERA) {

      flash.setVisibility( View.VISIBLE );
      flash.setOnClickListener( new View.OnClickListener() {
        @Override public void onClick(View v) {
          Camera.Parameters parameters = mPreview.getCameraInstance().getParameters();
          if (flashFlag == FLAG_CLOSE) {
            parameters.setFlashMode( Camera.Parameters.FLASH_MODE_TORCH );
            flashFlag = FLAG_OPEN;
            flash.setBackgroundResource( R.drawable.flash_on );
          } else {
            parameters.setFlashMode( Camera.Parameters.FLASH_MODE_OFF );
            flashFlag = FLAG_CLOSE;
            flash.setBackgroundResource( R.drawable.flash_off );
          }
          mPreview.getCameraInstance().setParameters( parameters );
        }
      } );
    } else {
      flash.setVisibility( View.GONE );
    }

    //传入衣服
    passClothes();

    takePhoto.setOnClickListener( new View.OnClickListener() {
      @Override public void onClick(View v) {
        //new Thread() {
        //  @Override public void run() {
        //    super.run();
        mPreview.takePicture( mediaPreview );
        //}
        //}.start();
      }
    } );

    //默认预览是上次拍的最后一张图片
    if (getMediaImageUri() != null) {
      mediaPreview.setImageURI( getMediaImageUri() );
    }
    //全屏预览图片
    mediaPreview.setOnClickListener( new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent( ARCamera.this, ShowPhoto.class );
        if (mPreview.getOutputMediaFileType() == null) {
          if (getMediaImageUri() == null) {
            return;
          }
          intent.setDataAndType( getMediaImageUri(), "image/*" );
          //return;
        } else {
          intent.setDataAndType( mPreview.getOutputMediaFileUri(),
              mPreview.getOutputMediaFileType() );
        }
        startActivityForResult( intent, 0 );
      }
    } );

    //翻转摄像机
    changeCamera.setOnClickListener( new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent( ARCamera.this, ARCamera.class );

        intent.putExtra( Cloth.NATION_NAME, nationName );
        intent.putExtra( Cloth.NATION_CLOTH_ID, nationClothesId );

        if (CameraPreview.cameraFlag == CameraPreview.BACK_CAMERA) {
          CameraPreview.cameraFlag = CameraPreview.FRONT_CAMERA;
        } else {
          CameraPreview.cameraFlag = CameraPreview.BACK_CAMERA;
        }
        startActivity( intent );
        finish();
      }
    } );

    //侧滑功能
    ClothesFragment clothesFragment = new ClothesFragment();
    getFragmentManager().beginTransaction().replace( R.id.pref_set, clothesFragment ).commit();
    //drawClothes = (DrawerLayout) findViewById( R.id.draw_clothes );
    //gestureDetector = new GestureDetector( this, new GestureDetector.SimpleOnGestureListener() {
    //  @Override
    //  public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
    //    //判断是否是右滑
    //    float offsetX = e2.getX() - e1.getX();
    //    float offsetY = e2.getY() - e1.getY();
    //    if ((offsetX > 0 && offsetX > Math.abs( offsetY )) || (velocityX > 0
    //        && velocityX > Math.abs( velocityY ))) {
    //      return true;//返回true表示我们在dispatchTouchEvent中，就不把事件传递到子控件中了
    //    }
    //    return false;
    //  }
    //} );
  }

  //private GestureDetector gestureDetector;

  //@Override public boolean dispatchTouchEvent(MotionEvent event) {
  //  //当向右滑动的时候，拦截事件，不传下去,通过GestureDetector辅助事件的判断
  //  if (gestureDetector.onTouchEvent( event )) {
  //    //打开侧边栏
  //    drawClothes.openDrawer( GravityCompat.START );
  //    return true;
  //  }
  //  return super.dispatchTouchEvent( event );
  //}

  private void passClothes() {
    clothes = new ImageView( this );
    //获取民族相关的信息
    Intent intent = getIntent();
    nationClothesId = intent.getIntExtra( Cloth.NATION_CLOTH_ID, R.drawable.bai_clothes );
    nationName = intent.getStringExtra( Cloth.NATION_NAME );
    setPosition();
  }

  public static void setPosition() {
    clothes.setImageResource( nationClothesId );
    //定义布局
    FrameLayout.LayoutParams params =
        new FrameLayout.LayoutParams( FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT );
    if (nationName.endsWith( "衣服" )) {
      clothes.setX( 20 );
      clothes.setY( 300 );
    } else if (nationName.endsWith( "帽子" )) {
      clothes.setX( 300 );
      clothes.setY( 100 );
    }
    mainInterface.addView( clothes, params );
    Log.d( "ARCamera", clothes.getX() + "," + clothes.getY() );
    clothesX = clothes.getX();
    clothesY = clothes.getY();
  }

  private void initCamera() {

    mPreview = new CameraPreview( this );
    FrameLayout preview = (FrameLayout) findViewById( R.id.camera_preview );
    preview.addView( mPreview );
    SettingsCamera.passCamera( mPreview.getCameraInstance() );
    SettingsCamera.init();
  }

  private Uri getMediaImageUri() {
    File file = new File( "/sdcard/com.sike.android" );
    Uri uri;
    if (!file.exists()) {
      return null;
    }
    File[] files = file.listFiles();
    if (files.length == 0) {
      return null;
    } else {
      uri = Uri.fromFile( files[files.length - 1] );
    }
    return uri;
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
}
