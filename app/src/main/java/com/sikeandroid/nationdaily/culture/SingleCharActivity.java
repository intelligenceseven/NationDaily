package com.sikeandroid.nationdaily.culture;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sikeandroid.nationdaily.R;
import com.sikeandroid.nationdaily.culture.data.CharLab;
import com.sikeandroid.nationdaily.textscan.CharDetailsActivity;

import static com.sikeandroid.nationdaily.main.DailyNationFragment.NATION_HISTORY_URL;

/********************************************************************************
 * using for:
 * 丁酉鸡年四月 2017/05/23 21:47
 * @author 西唐王, xtwyzh@gmail.com,blog.xtwroot.com
 * xtwroot Copyrights (c) 2017. All rights reserved.
 ********************************************************************************/
public class SingleCharActivity extends AppCompatActivity{


    String mChar;
    TextView mSumTextView;
    ImageView mCharImageView;

    public static final String CHARKAY = "SIKEANDROID_CHAR";

    public static Intent newIntent(Context context,String s)
    {
        Intent i = new Intent(context,SingleCharActivity.class);
        i.putExtra(CHARKAY,s);
        return i;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_char_avtivity);

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled( true );
        }

        mChar = getIntent().getStringExtra(CHARKAY);
        final CharLab charLab = CharLab.get(this);
        if(CharLab.hasChar(mChar))
        {
            mCharImageView = (ImageView) findViewById(R.id.char_image);
            Glide.with(this).load(charLab.getChar(mChar).getImage()).into(mCharImageView);
            mCharImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SingleCharActivity.this,CharDetailsActivity.class);
                    intent.putExtra(NATION_HISTORY_URL,charLab.getChar(mChar).getImageUrl());
                    startActivity(intent);
                }
            });
        }


    }



}
