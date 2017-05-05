package com.sikeandroid.nationdaily.cosplay;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import com.sikeandroid.nationdaily.R;
import java.util.ArrayList;
import java.util.List;

public class ARCosplay extends AppCompatActivity {
  private Cloth[] cloths = {
      new Cloth( "白族-衣服", R.drawable.bai_clothes ),
      new Cloth( "朝鲜族-衣服", R.drawable.chaoxian_clothes ),
      new Cloth( "土族-衣服", R.drawable.tu_clothes ), new Cloth( "土族-帽子", R.drawable.tu_hat ),
      new Cloth( "白族-帽子", R.drawable.bai_hat ), new Cloth( "裕固族-帽子", R.drawable.yugu_hat ),
      new Cloth( "门巴族-衣服", R.drawable.menba_clothes ),
      new Cloth( "普米族-衣服", R.drawable.pumi_clothes )
  };
  public static List<Cloth> clothList = new ArrayList<>();
  private ClothAdapter adapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.activity_ar_cosplay );
    Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
    setSupportActionBar( toolbar );
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled( true );
    }

    initCloths();
    RecyclerView recyclerView = (RecyclerView) findViewById( R.id.clothes );
    GridLayoutManager layoutManager = new GridLayoutManager( this, 2 );
    recyclerView.setLayoutManager( layoutManager );
    adapter = new ClothAdapter( clothList );
    recyclerView.setAdapter( adapter );
  }

  @Override public void onBackPressed() {
    super.onBackPressed();
    finish();
  }

  private void initCloths() {
    clothList.clear();
    clothList.add( cloths[7] );
    clothList.add( cloths[6] );
    clothList.add( cloths[2] );
    clothList.add( cloths[0] );
    clothList.add( cloths[1] );
    clothList.add( cloths[3] );
    clothList.add( cloths[5] );
    clothList.add( cloths[4] );

    //for (int i = 0; i < 20; i++) {
    //  clothList.add( cloths[(int) (Math.random() * 8)] );
    //}
  }
}
