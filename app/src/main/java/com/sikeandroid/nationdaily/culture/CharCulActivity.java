package com.sikeandroid.nationdaily.culture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sikeandroid.nationdaily.R;
import com.sikeandroid.nationdaily.textscan.CharDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import static com.sikeandroid.nationdaily.main.DailyNationFragment.NATION_HISTORY_URL;

public class CharCulActivity extends AppCompatActivity {
    public static final String TOPIC_NAME="topic_name";
    public static final String TOPIC_IMAGE_ID="topic_image_id";
    public static final String TOPIC_ID="topic_id";

    private List<View> charViewList;
    private ViewPager charViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_cul);
        charViewPager= (ViewPager) findViewById(R.id.activity_char_view_pager);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();

        if(actionBar!=null){
            //让导航按钮显示出来
            actionBar.setDisplayHomeAsUpEnabled(true);

        }


        Intent intent=getIntent();
        String topicName=intent.getStringExtra(TOPIC_NAME);
        int topicImageId=intent.getIntExtra(TOPIC_IMAGE_ID,0);
        int topicId=intent.getIntExtra(TOPIC_ID,0);
        charViewList=new ArrayList<View>();
        if(topicId==1){
            View view1=View.inflate(this,R.layout.activity_char_cul_content,null);
            ImageView charImage1= (ImageView) view1.findViewById(R.id.char_image);
            Glide.with(this).load(R.drawable.jin).into(charImage1);
            View view2=View.inflate(this,R.layout.activity_char_cul_content,null);
            ImageView charImage2= (ImageView) view2.findViewById(R.id.char_image);
            Glide.with(this).load(R.drawable.mu).into(charImage2);
            View view3=View.inflate(this,R.layout.activity_char_cul_content,null);
            ImageView charImage3= (ImageView) view3.findViewById(R.id.char_image);
            Glide.with(this).load(R.drawable.shui).into(charImage3);
            View view4=View.inflate(this,R.layout.activity_char_cul_content,null);
            ImageView charImage4= (ImageView) view4.findViewById(R.id.char_image);
            Glide.with(this).load(R.drawable.huo).into(charImage4);
            View view5=View.inflate(this,R.layout.activity_char_cul_content,null);
            ImageView charImage5= (ImageView) view5.findViewById(R.id.char_image);
            Glide.with(this).load(R.drawable.tu).into(charImage5);
            charViewList.add(view1);
            charViewList.add(view2);
            charViewList.add(view3);
            charViewList.add(view4);
            charViewList.add(view5);
        }else if(topicId==2){

            View view1=View.inflate(this,R.layout.activity_char_cul_content,null);
            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CharCulActivity.this,CharDetailsActivity.class);
                    intent.putExtra(NATION_HISTORY_URL,"http://www.xtwroot.top/zhen_details.jpg");
                    startActivity(intent);
                }
            });
            ImageView charImage1= (ImageView) view1.findViewById(R.id.char_image);
            Glide.with(this).load(R.drawable.zhen).into(charImage1);
            View view2=View.inflate(this,R.layout.activity_char_cul_content,null);
            view2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CharCulActivity.this,CharDetailsActivity.class);
                    intent.putExtra(NATION_HISTORY_URL,"http://www.xtwroot.top/shan_details.jpg");
                    startActivity(intent);
                }
            });
            ImageView charImage2= (ImageView) view2.findViewById(R.id.char_image);
            Glide.with(this).load(R.drawable.shan).into(charImage2);
            View view3=View.inflate(this,R.layout.activity_char_cul_content,null);
            view3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CharCulActivity.this,CharDetailsActivity.class);
                    intent.putExtra(NATION_HISTORY_URL,"http://www.xtwroot.top/mei_details.jpg");
                    startActivity(intent);
                }
            });
            ImageView charImage3= (ImageView) view3.findViewById(R.id.char_image);
            Glide.with(this).load(R.drawable.mei).into(charImage3);
            charViewList.add(view1);
            charViewList.add(view2);
            charViewList.add(view3);

        }else if(topicId==3){
            View view1=View.inflate(this,R.layout.activity_char_cul_content,null);
            ImageView charImage1= (ImageView) view1.findViewById(R.id.char_image);
            Glide.with(this).load(R.drawable.jia).into(charImage1);
            View view2=View.inflate(this,R.layout.activity_char_cul_content,null);
            ImageView charImage2= (ImageView) view2.findViewById(R.id.char_image);
            Glide.with(this).load(R.drawable.ren).into(charImage2);
            View view3=View.inflate(this,R.layout.activity_char_cul_content,null);
            ImageView charImage3= (ImageView) view3.findViewById(R.id.char_image);
            Glide.with(this).load(R.drawable.zai).into(charImage3);
            View view4=View.inflate(this,R.layout.activity_char_cul_content,null);
            ImageView charImage4= (ImageView) view4.findViewById(R.id.char_image);
            Glide.with(this).load(R.drawable.zai).into(charImage4);
            charViewList.add(view1);
            charViewList.add(view2);
            charViewList.add(view3);
            charViewList.add(view4);
        }
        CharCulAdapter adapter=new CharCulAdapter(charViewList);
        charViewPager.setAdapter(adapter);





    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();

        }
        return true;
    }
    @Override public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
