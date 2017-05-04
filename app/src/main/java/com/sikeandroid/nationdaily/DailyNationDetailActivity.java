package com.sikeandroid.nationdaily;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.sikeandroid.nationdaily.cosplay.ARCosplay;
import com.sikeandroid.nationdaily.data.DailyNation;
import com.sikeandroid.nationdaily.data.DailyNationLab;
import com.sikeandroid.nationdaily.menu.DrawerAdapter;
import com.sikeandroid.nationdaily.menu.DrawerItem;
import com.sikeandroid.nationdaily.menu.SimpleItem;
import com.sikeandroid.nationdaily.menu.SpaceItem;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import java.util.Arrays;
import java.util.List;

public class DailyNationDetailActivity extends AppCompatActivity
    implements DrawerAdapter.OnItemSelectedListener {

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
            Intent intent = new Intent( "android.media.action.IMAGE_CAPTURE" );
            //Intent intent = new Intent( DailyNationDetailActivity.this, TextScan.class );
            startActivity( intent );

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
  }

  @Override public void onItemSelected(int position) {
    Intent intent;
    switch (position) {
      case POS_HANZI: // 汉字Acticity启动

        break;
      case POS_MINZU: //

        mSlidingRootNav.closeMenu();
        break;
      case POS_AR: // AR换衣Activity启动
        Intent cosplay = new Intent( DailyNationDetailActivity.this, ARCosplay.class );
        startActivity( cosplay );
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
