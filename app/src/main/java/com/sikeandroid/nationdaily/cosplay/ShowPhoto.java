package com.sikeandroid.nationdaily.cosplay;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ShowPhoto extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) { // Hide the window title.
    //requestWindowFeature( Window.FEATURE_NO_TITLE);

    this.getWindow()
        .setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN );
    super.onCreate( savedInstanceState );
    //setContentView( R.layout.activity_show_photo );
    RelativeLayout relativeLayout = new RelativeLayout( this );
    RelativeLayout.LayoutParams layoutParams =
        new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT );
    layoutParams.addRule( RelativeLayout.CENTER_IN_PARENT );
    Uri uri = getIntent().getData();
    if (getIntent().getType().equals( "image/*" )) {
      ImageView view = new ImageView( this );
      view.setImageURI( uri );
      view.setLayoutParams( layoutParams );
      relativeLayout.addView( view );
    }
    setContentView( relativeLayout, layoutParams );
  }
}
