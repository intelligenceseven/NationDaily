package com.sikeandroid.nationdaily.about;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import com.sikeandroid.nationdaily.R;
import com.sikeandroid.nationdaily.guide.PrefManager;
import com.sikeandroid.nationdaily.guide.WelcomeActivity;

public class AboutActivity extends AppCompatActivity {

  private Button funcIntroduce;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.activity_about );
    Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
    setSupportActionBar( toolbar );
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled( true );
    }
    funcIntroduce = (Button) findViewById( R.id.func_introduce );
    funcIntroduce.setOnClickListener( new View.OnClickListener() {
      @Override public void onClick(View v) {
        new PrefManager( AboutActivity.this ).setFirstTimeLaunch( true );
        startActivity( new Intent( AboutActivity.this, WelcomeActivity.class ) );
        finish();
      }
    } );
  }
}
