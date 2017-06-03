package com.sikeandroid.nationdaily.cosplay;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.sikeandroid.nationdaily.R;
import com.sikeandroid.nationdaily.utils.SettingsCamera;
import com.sikeandroid.nationdaily.utils.TakePhoto;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.sikeandroid.nationdaily.utils.CameraParam.FLASH_CLOSE;
import static com.sikeandroid.nationdaily.utils.CameraParam.FLASh_OPEN;
import static com.sikeandroid.nationdaily.utils.CameraParam.flashFlag;

public class ARCamera extends AppCompatActivity
    implements View.OnClickListener, View.OnTouchListener {

  private static final String TAG = "ARCamera";
  private ImageButton takePhoto;
  private ImageButton changeCamera;
  private TakePhoto mPreview;
  private ImageView mediaPreview;

  public static ImageView clothes;
  public static FrameLayout mainInterface;

  //民族服装相关信息
  public static int nationClothesId;
  public static String nationName;

  //public DrawerLayout drawClothes;
  private ImageButton flash;
  public static ImageView faceTrace;
  private FrameLayout previewLayout;

  @Override protected void onCreate(Bundle savedInstanceState) {
    //满屏显示
    //getWindowManager().getDefaultDisplay().getMetrics( dm );
    this.getWindow()
        .setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN );
    super.onCreate( savedInstanceState );
    setContentView( R.layout.activity_ar_camera );
    previewLayout = (FrameLayout) findViewById( R.id.camera_preview );

    takePhoto = (ImageButton) findViewById( R.id.take_photo );
    mediaPreview = (ImageView) findViewById( R.id.preview );
    mainInterface = (FrameLayout) findViewById( R.id.main_interface );

    changeCamera = (ImageButton) findViewById( R.id.change_camera );
    if (Camera.getNumberOfCameras() == 1) {
      TakePhoto.cameraFlag = TakePhoto.BACK_CAMERA;
      //兼容只有一个摄像头
      changeCamera.setVisibility( View.GONE );
    }

    flash = (ImageButton) findViewById( R.id.flash );
    flashState();

    //传入衣服
    passClothes();

    takePhoto.setOnClickListener( this );

    //默认预览是上次拍的最后一张图片
    if (getMediaImageUri() != null) {
      mediaPreview.setImageURI( getMediaImageUri() );
    }
    //全屏预览图片
    mediaPreview.setOnClickListener( this );

    //翻转摄像机
    changeCamera.setOnClickListener( this );

    //侧滑功能
    final ClothesFragment clothesFragment = new ClothesFragment();
    getFragmentManager().beginTransaction().replace( R.id.pref_set, clothesFragment ).commit();

    clothes.setOnTouchListener( this );

    m_list_coords = new ArrayList<>();
    matrix = new Matrix();
    faceTrace = (ImageView) findViewById( R.id.face_track );
  }

  private void changeCamera() {
    if (TakePhoto.cameraFlag == TakePhoto.BACK_CAMERA) {
      if (TakePhoto.flashFlag == TakePhoto.FLASh_OPEN) {
        TakePhoto.flashFlag = TakePhoto.FLASH_CLOSE;
        mPreview.closeFlashLight();
      }
      TakePhoto.cameraFlag = TakePhoto.FRONT_CAMERA;
      mPreview.changeCamera();
      SettingsCamera.passCamera( mPreview.getCameraInstance() );
      SettingsCamera.initTakePhoto();
      mPreview.changePreview();
    } else {
      TakePhoto.cameraFlag = TakePhoto.BACK_CAMERA;
      mPreview.changeCamera();
      SettingsCamera.passCamera( mPreview.getCameraInstance() );
      SettingsCamera.initTakePhoto();
      mPreview.changePreview();
      flash.setBackgroundResource( R.drawable.flash_off );
    }
    flashState();
  }

  private void mediaPreview() {
    if (TakePhoto.cameraFlag == TakePhoto.BACK_CAMERA) {
      if (flashFlag == FLASh_OPEN) {
        flashFlag = FLASH_CLOSE;
        flash.setBackgroundResource( R.drawable.flash_off );
      }
    }
    Intent intent = new Intent( ARCamera.this, ShowPhoto.class );
    if (mPreview.getOutputMediaFileType() == null) {
      if (getMediaImageUri() == null) {
        return;
      }
      intent.setDataAndType( getMediaImageUri(), "image/*" );
      //return;
    } else {
      intent.setDataAndType( mPreview.getOutputMediaFileUri(), mPreview.getOutputMediaFileType() );
    }
    startActivityForResult( intent, 0 );
  }

  private void flashState() {
    if (TakePhoto.cameraFlag == TakePhoto.BACK_CAMERA) {
      flash.setVisibility( View.VISIBLE );
      flash.setOnClickListener( new View.OnClickListener() {
        @Override public void onClick(View v) {
          mPreview.flashLightUtils();
          if (flashFlag == FLASh_OPEN) {
            flash.setBackgroundResource( R.drawable.flash_on );
          } else {
            flash.setBackgroundResource( R.drawable.flash_off );
          }
        }
      } );
    } else {
      flash.setVisibility( View.GONE );
    }
  }

  private void passClothes() {
    clothes = new ImageView( this );
    clothes.setScaleType( ImageView.ScaleType.MATRIX );
    //获取民族相关的信息
    Intent intent = getIntent();
    nationClothesId = intent.getIntExtra( Cloth.NATION_CLOTH_ID, R.drawable.bai_clothes );
    nationName = intent.getStringExtra( Cloth.NATION_NAME );
    setPosition();
  }

  public static void setPosition() {
    clothes.setImageResource( nationClothesId );
    //Glide.with( MyApplication.getContext() ).load( nationClothesId ).into( clothes );
    //定义布局
    FrameLayout.LayoutParams params =
        new FrameLayout.LayoutParams( FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT );
    mainInterface.addView( clothes, 2, params );
    //if (nationName.endsWith( "衣服" )) {
    //  //clothes.layout( 20, 300, 20 + clothes.getWidth(), 300 + clothes.getWidth() );
    //} else if (nationName.endsWith( "帽子" )) {
    //  //clothes.layout( 300, 100, 300 + clothes.getWidth(), 100 + clothes.getWidth() );
    //}
    clothes.setX( 0 );
    clothes.setY( 0 );
    Log.d( "ARCamera", clothes.getX() + "," + clothes.getY() );

    //clothesX = (int) clothes.getX();
    //clothesY = (int) clothes.getY();
  }

  private void initCamera() {

    mPreview = new TakePhoto( this );

    previewLayout.addView( mPreview );
    SettingsCamera.passCamera( mPreview.getCameraInstance() );
    SettingsCamera.initTakePhoto();
  }

  private Uri getMediaImageUri() {
    File file = new File( "/sdcard/com.sike.android/DCIM" );
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
    mPreview.cameraRelease();
    previewLayout.removeView( mPreview );
    mPreview = null;
  }

  @Override protected void onResume() {
    super.onResume();
    if (mPreview == null) {
      initCamera();
      if (TakePhoto.cameraFlag == TakePhoto.BACK_CAMERA) {
        if (TakePhoto.flashFlag == TakePhoto.FLASh_OPEN) {
          mPreview.closeFlashLight();
          TakePhoto.flashFlag = TakePhoto.FLASH_CLOSE;
        }
      }
    }
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.take_photo:
        //Log.d( TAG, "clothes:X=" + clothes.getX() + ",Y=" + clothes.getY() );
        //Log.d( TAG, "clothesX=" + clothesX + ",clothesY=" + clothesY );
        //clothesX = (int) clothes.getX();
        //clothesY = (int) clothes.getY();
        mPreview.takePicture( mediaPreview );
        break;
      case R.id.preview:
        mediaPreview();
        break;
      case R.id.change_camera:
        changeCamera();
        break;
    }
  }

  /**
   * 旋转角度：相对于默认状态
   */
  private double angle = 0;

  /**
   * 缩放比例:相对于默认状态
   */
  private double scale = 1.0;

  /**
   * 移动位置:相对于默认状态
   */
  private PointF position = new PointF( 0, 0 );

  /**
   * 存有操作过程中的手指坐标信息
   */
  List<PointF> m_list_coords;

  /**
   * 用来显示各效果的矩阵类
   */
  public static Matrix matrix;

  /**
   * 显示手势计算后的效果，即更新textView和imageView
   */
  private void showResult() {
    clothes.setImageMatrix( matrix );
    Log.d( TAG, "angle:" + angle + ";scale" + scale + "trans:" + position.x + ";" + position.y );
  }

  /**
   * 计算移动
   */
  void calcMove() {
    int listSize = m_list_coords.size();
    if (listSize < 2) {
      return;
    }
    PointF spt = m_list_coords.get( listSize - 2 );
    PointF ept = m_list_coords.get( listSize - 1 );
    PointF v1 = new PointF( ept.x - spt.x, ept.y - spt.y );

    position.x += v1.x;
    position.y += v1.y;

    matrix.postTranslate( v1.x, v1.y );
    showResult();
  }

  /**
   * 计算双指缩放和旋转
   */
  void calcAngleScale() {
    int listSize = m_list_coords.size();
    if (listSize < 4) {
      return;
    }

    //根据移动前和移动后的位置构建出 第一条线和第二条线段。
    PointF spt1 = m_list_coords.get( listSize - 4 );
    PointF ept1 = m_list_coords.get( listSize - 3 );
    PointF spt2 = m_list_coords.get( listSize - 2 );
    PointF ept2 = m_list_coords.get( listSize - 1 );

    //两个线段之间的运动幅度过小，删除。
    //此处要注意，为什么幅度过小要删除呢，因为在某些手机屏幕，在按压时灵敏度过高，
    //或者硬件计算手指位置算法不够好，造成用户感觉手指没动而显示出来的坐标一直在抖动

    if (getLineLength( spt1, spt2 ) + getLineLength( ept1, ept2 ) < 5) {
      m_list_coords.remove( listSize - 1 );
      m_list_coords.remove( listSize - 2 );
      return;
    }

    //旋转和缩放中点位置，认为是第二线段的中点.
    PointF centerPoint = new PointF( (spt2.x + ept2.x) / 2, (spt2.y + ept2.y) / 2 );

    //根据触点 构建两个向量，计算两个向量角度.
    PointF v1 = new PointF( ept1.x - spt1.x, ept1.y - spt1.y );
    PointF v2 = new PointF( ept2.x - spt2.x, ept2.y - spt2.y );

    //计算两个向量的夹角.
    double v1Len = getLineLength( v1 );
    double v2Len = getLineLength( v2 );
    double cosAlpha = (v1.x * v2.x + v1.y * v2.y) / (v1Len * v2Len);

    //由于计算误差，可能会带来略大于1的cos，例如
    if (cosAlpha > 1.0f) {
      cosAlpha = 1.0f;
    }
    //本次的角度已经计算出来。
    double dAngle = Math.acos( cosAlpha ) * 180.0 / 3.14;

    System.out.println( "" + dAngle );
    //判断顺时针和逆时针.
    //判断方法其实很简单，这里的v1v2其实相差角度很小的。
    //v1v2先Normalize，
    v1.x /= v1Len;
    v1.y /= v1Len;
    v2.x /= v2Len;
    v2.y /= v2Len;
    //作v2的逆时针垂直向量。
    PointF v2Vec = new PointF( v2.y, -v2.x );

    //判断这个垂直向量和v1的点积，点积>0表示俩向量夹角锐角。=0表示垂直，<0表示钝角
    float vDot = v1.x * v2Vec.x + v1.y * v2Vec.y;
    if (vDot > 0) {
      //v2的逆时针垂直向量和v1是锐角关系，说明v1在v2的逆时针方向。
    } else {
      dAngle = -dAngle;
    }

    angle += dAngle;

    //角度你懂的。
    if (angle >= 360) {

      angle -= 360;
    }
    if (angle < 0) {
      angle += 360;
    }
    matrix.postRotate( (float) dAngle, centerPoint.x, centerPoint.y );

    //判断缩放.
    double lineLen1 = getLineLength( spt1, ept1 );
    double lineLen2 = getLineLength( spt2, ept2 );

    //当两指距离过近，不要再进行缩放了。
    if (lineLen1 >= 5 && lineLen2 >= 5) {
      double dScale = lineLen2 / lineLen1;
      scale *= dScale;
      matrix.postScale( (float) dScale, (float) dScale, centerPoint.x, centerPoint.y );
    }

    showResult();
    //textView.setText(String.valueOf(angle));
  }

  /**
   * 获取p1到p2的线段的长度
   */
  double getLineLength(PointF p1, PointF p2) {
    return Math.sqrt( (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y) );
  }

  /**
   * 获取原点到p的线段的长度
   */
  double getLineLength(PointF p) {
    return Math.sqrt( p.x * p.x + p.y * p.y );
  }

  @Override public boolean onTouch(View view, MotionEvent event) {
    Log.d( TAG, "onTouch" );

    //此处只处理imageView的touch事件
    if (view == clothes) {
      int touchSize = event.getPointerCount();

      //只处理单指和双指消息，超过2个手指不处理，此处也可以放开，
      //超过2个手指只处理前面两个手指的消息
      if (touchSize >= 3) {
        return false;
      }
      switch (event.getAction() & event.getActionMasked()) {
        case MotionEvent.ACTION_DOWN:

          //一个手指按下，记录按下的位置

          m_list_coords.add( new PointF( event.getX( 0 ), event.getY( 0 ) ) );

          //System.out.println("down"+event.getX(0)+ ":"+event.getY(0));
          break;
        case MotionEvent.ACTION_POINTER_DOWN:
          //第二个手指按下，记录按下的位置
          if (m_list_coords.isEmpty()) {
            //数据有误或者第二个手指拿起又放下
            //do nothing
          } else {
            m_list_coords.add( new PointF( event.getX( 1 ), event.getY( 1 ) ) );
          }
          break;
        case MotionEvent.ACTION_MOVE:
          if (touchSize > 1) {
            m_list_coords.add( new PointF( event.getX( 0 ), event.getY( 0 ) ) );
            m_list_coords.add( new PointF( event.getX( 1 ), event.getY( 1 ) ) );
            calcAngleScale();
          } else {
            m_list_coords.add( new PointF( event.getX( 0 ), event.getY( 0 ) ) );
            calcMove();
          }
          break;

        case MotionEvent.ACTION_UP:
          //UP中暂不处理消息，如果想做松手后的惯性滑动/缩放/旋转等，需要在此处理
          //并且在event事件中需要记录时间，才能判断松手时的速度。
          //本博客仅用来说明如何计算缩放旋转，因此不讲解这个处理。
          m_list_coords.clear();
          break;
        case MotionEvent.ACTION_POINTER_UP:
          //认为2指操作结束
          m_list_coords.clear();
          break;
        default:
          break;
      }
      return true;
    }
    return false;
  }
}
