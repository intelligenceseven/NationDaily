package com.sikeandroid.nationdaily.cosplay;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.sikeandroid.nationdaily.R;

public class ShowPhoto extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) { // Hide the window title.
    //requestWindowFeature( Window.FEATURE_NO_TITLE);

    this.getWindow()
        .setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN );
    super.onCreate( savedInstanceState );
    //setContentView( R.layout.activity_show_photo );
  /*  RelativeLayout relativeLayout = new RelativeLayout( this );
    RelativeLayout.LayoutParams layoutParams =
        new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT );
    RelativeLayout.LayoutParams shareLayoutParams = new RelativeLayout.LayoutParams( 50, 50 );
    shareLayoutParams.addRule( RelativeLayout.ALIGN_END, relativeLayout.getId() );
    layoutParams.addRule( RelativeLayout.CENTER_IN_PARENT );
    final Uri uri = getIntent().getData();
    if (getIntent().getType().equals( "image/*" )) {
      ImageView view = new ImageView( this );
      //view.setImageURI( uri );
      Glide.with( this ).load( uri ).into( view );
      view.setLayoutParams( layoutParams );
      relativeLayout.addView( view );
      ImageView shareView = new ImageView( this );
      Glide.with( this ).load( R.drawable.share ).into( shareView );
      //shareView.setLayoutParams( layoutParams );
      relativeLayout.addView( shareView );
      relativeLayout.setLayoutParams( shareLayoutParams );
      shareView.setOnClickListener( new View.OnClickListener() {
        @Override public void onClick(View v) {
          Intent sendIntent = new Intent();
          sendIntent.setAction( Intent.ACTION_SEND );
          sendIntent.setType( "image/*" );
          sendIntent.putExtra( Intent.EXTRA_STREAM, uri );
          startActivity( sendIntent );
        }
      } );
    }*/
    setContentView( R.layout.activity_ar_show_photo );
    final Uri uri = getIntent().getData();
    if (getIntent().getType().equals( "image/*" )) {
      ImageView photoImg = (ImageView) findViewById( R.id.photo_img );
      Glide.with( this ).load( uri ).into( photoImg );
      ImageView shareImg = (ImageView) findViewById( R.id.share_img );
      shareImg.setOnClickListener( new View.OnClickListener() {
        @Override public void onClick(View v) {
          Intent sendIntent = new Intent();
          sendIntent.setAction( Intent.ACTION_SEND );
          sendIntent.setType( "image/*" );
          sendIntent.putExtra( Intent.EXTRA_STREAM, uri );
          startActivity( sendIntent );
        }
      } );
    }
  }
}
