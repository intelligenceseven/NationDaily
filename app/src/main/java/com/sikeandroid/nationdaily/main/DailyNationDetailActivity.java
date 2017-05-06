package com.sikeandroid.nationdaily.main;

import android.Manifest;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.sikeandroid.nationdaily.R;
import com.sikeandroid.nationdaily.cosplay.ARCosplay;
import com.sikeandroid.nationdaily.guide.PrefManager;
import com.sikeandroid.nationdaily.main.data.DailyNation;
import com.sikeandroid.nationdaily.main.data.DailyNationLab;
import com.sikeandroid.nationdaily.main.menu.DrawerAdapter;
import com.sikeandroid.nationdaily.main.menu.DrawerItem;
import com.sikeandroid.nationdaily.main.menu.SimpleItem;
import com.sikeandroid.nationdaily.main.menu.SpaceItem;
import com.sikeandroid.nationdaily.textscan.TextScan;
import com.sikeandroid.nationdaily.util.BaseAppCompatActivity;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

public class DailyNationDetailActivity extends BaseAppCompatActivity
    implements DrawerAdapter.OnItemSelectedListener {

  private Button guideAgain;
  private int SPLASH_DISPLAY_LENGHT; // 延迟六秒
  private int mFileExist;

  public static final String mstrFilePathForDatSave =
      Environment.getExternalStorageDirectory().toString() + "/NationDaily/TianruiWorkroomOCR.dat";
  public static final String mstrFilePathForDat =
      Environment.getExternalStorageDirectory().toString() + "/NationDaily";

  private static final int POS_HANZI = 0;
  private static final int POS_MINZU = 1;
  private static final int POS_AR = 2;
  private static final int POS_ABOUT = 3;

  private SlidingRootNav mSlidingRootNav;

  private String[] screenTitles;
  private Drawable[] screenIcons;

  private ViewPager mViewPager;
  private List<DailyNation> mDailyNations;

  private static final String EXTRA_DAILYNATION_DATE =
      "com.sikeandroid.nationdaily.dailynation_DATE";

  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.activity_nation_daily_pager );

    Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
    toolbar.inflateMenu( R.menu.activity_main );
    toolbar.setTitle( "NationDaily" );
    toolbar.setOnMenuItemClickListener( new Toolbar.OnMenuItemClickListener() {

      @Override public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
          case R.id.scan_menu:
            performCodeWithPermission( "请求访问相机权限", new BaseAppCompatActivity.PermissionCallback() {
              @Override public void hasPermission() {
                //执行打开相机相关代码
                Intent intent = new Intent( DailyNationDetailActivity.this, TextScan.class );
                startActivity( intent );
              }

              @Override public void noPermission() {
              }
            }, Manifest.permission.CAMERA );

            break;
        }
        return true;
      }
    } );

    String date = (String) getIntent().getSerializableExtra( EXTRA_DAILYNATION_DATE );

    mViewPager = (ViewPager) findViewById( R.id.activity_nation_daily_pager );

    mDailyNations = DailyNationLab.get( this ).getDailyNations();
    FragmentManager fragmentManager = getSupportFragmentManager();
    mViewPager.setAdapter( new FragmentPagerAdapter( fragmentManager ) {
      @Override public android.support.v4.app.Fragment getItem(int position) {
        DailyNation dailyNation = mDailyNations.get( position );
        return DailyNationFragment.newInstance( dailyNation.getDate() );
      }

      @Override public int getCount() {
        return mDailyNations.size();
      }
    } );

    for (int i = 0; i < mDailyNations.size(); i++) {
      if (mDailyNations.get( i ).getDate().equals( date )) {
        mViewPager.setCurrentItem( i );
        break;
      }
    }
    //toolbar.setOnMenuItemClickListener(OnMenuItemClickListener);

    mSlidingRootNav = new SlidingRootNavBuilder( this ).withToolbarMenuToggle( toolbar )
        .withMenuOpened( false ) // 启动时菜单是否打开
        .withSavedState( savedInstanceState )
        .withMenuLayout( R.layout.menu_left_drawer )
        .inject();

    screenIcons = loadScreenIcons();
    screenTitles = loadScreenTitles();

    DrawerAdapter adapter = new DrawerAdapter(
        Arrays.asList( createItemFor( POS_MINZU ).setChecked( true ), createItemFor( POS_HANZI ),
            createItemFor( POS_AR ), createItemFor( POS_ABOUT ), new SpaceItem( 48 ) ) );
    adapter.setListener( this );

    RecyclerView list = (RecyclerView) findViewById( R.id.list );
    list.setNestedScrollingEnabled( true );
    list.setLayoutManager( new LinearLayoutManager( this ) );
    list.setAdapter( adapter );

    adapter.setSelected( POS_MINZU );

    SPLASH_DISPLAY_LENGHT = 1000;
    mFileExist = 0;
    boolean bf1 = fileIsExists( mstrFilePathForDatSave );

    if (bf1 == false) {
      mFileExist = 0;
    } else {
      mFileExist = 1;
      SPLASH_DISPLAY_LENGHT = 150;
    }

    new Handler().postDelayed( new Runnable() {
      public void run() {

        if (mFileExist == 0) {
          try {
            Thread trimmingThread = new Thread( new Runnable() {
              public void run() {
                CopyAssets( "", mstrFilePathForDat );
              }
            } );
            trimmingThread.setName( "savingThread" );
            trimmingThread.start();

            Thread.sleep( SPLASH_DISPLAY_LENGHT );
          } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
      }
    }, SPLASH_DISPLAY_LENGHT );

    //再次启动引导的界面

    guideAgain = (Button) findViewById( R.id.guide_again );
    guideAgain.setOnClickListener( new View.OnClickListener() {
      @Override public void onClick(View v) {

        new PrefManager( DailyNationDetailActivity.this ).setFirstTimeLaunch( true );
        finish();
      }
    } );
  }

  public boolean fileIsExists(String filePath) {
    long fLength = 0;
    try {
      File f = new File( filePath );

      if (!f.exists()) {
        return false;
      }

      fLength = f.length();
    } catch (Exception e) {
      // TODO: handle exception
      return false;
    }

    if (fLength != 11140123) {
      return false;
    }

    return true;
  }

  private void CopyAssets(String assetDir, String dir) {
    String[] files;
    try {
      files = this.getResources().getAssets().list( assetDir );
    } catch (IOException e1) {
      return;
    }
    File mWorkingPath = new File( dir );
    // if this directory does not exists, make one.
    if (!mWorkingPath.exists()) {
      if (!mWorkingPath.mkdirs()) {

      }
    }
    for (int i = 0; i < files.length; i++) {
      try {
        String fileName = files[i];
        // we make sure file name not contains '.' to be a folder.
        if (!fileName.contains( "." )) {
          if (0 == assetDir.length()) {
            CopyAssets( fileName, dir + fileName + "/" );
          } else {
            CopyAssets( assetDir + "/" + fileName, dir + fileName + "/" );
          }
          continue;
        }
        File outFile = new File( mWorkingPath, fileName );
        if (outFile.exists()) outFile.delete();
        InputStream in = null;
        if (0 != assetDir.length()) {
          in = getAssets().open( assetDir + "/" + fileName );
        } else {
          in = getAssets().open( fileName );
        }
        OutputStream out = new FileOutputStream( outFile );

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read( buf )) > 0) {
          out.write( buf, 0, len );
        }
        out.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override public void onItemSelected(int position) {
    Intent intent;
    switch (position) {
      case POS_HANZI: // 汉字Acticity启动

        break;
      case POS_MINZU:
        mSlidingRootNav.closeMenu();
        break;
      case POS_AR:
        // AR换衣Activity启动
        performCodeWithPermission( "请求访问相机权限", new BaseAppCompatActivity.PermissionCallback() {
              @Override public void hasPermission() {
                Intent cosplay = new Intent( DailyNationDetailActivity.this, ARCosplay.class );
                startActivity( cosplay );
              }

              @Override public void noPermission() {
              }
            }, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE );
        break;
      case POS_ABOUT: // 关于界面启动

        break;
    }
    //mSlidingRootNav.closeMenu();
  }

  private DrawerItem createItemFor(int position) {
    return new SimpleItem( screenIcons[position], screenTitles[position] ).withIconTint(
        color( R.color.textColorPrimary ) )
        .withTextTint( color( R.color.textColorPrimary ) )
        .withSelectedIconTint( color( R.color.colorAccent ) )
        .withSelectedTextTint( color( R.color.colorAccent ) );
  }

  private String[] loadScreenTitles() {
    return getResources().getStringArray( R.array.ld_activityScreenTitles );
  }

  private Drawable[] loadScreenIcons() {
    TypedArray ta = getResources().obtainTypedArray( R.array.ld_activityScreenIcons );
    Drawable[] icons = new Drawable[ta.length()];
    for (int i = 0; i < ta.length(); i++) {
      int id = ta.getResourceId( i, 0 );
      if (id != 0) {
        icons[i] = ContextCompat.getDrawable( this, id );
      }
    }
    ta.recycle();
    return icons;
  }

  @ColorInt private int color(@ColorRes int res) {
    return ContextCompat.getColor( this, res );
  }
}
