package com.sikeandroid.nationdaily.culture;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sikeandroid.nationdaily.R;

import java.util.ArrayList;
import java.util.List;

public class CharTopicActivity extends AppCompatActivity {

    private CharTopicAdapter adapter;
    private List<CharCulture> charCultureList=new ArrayList<>();
    private CharCulture[] charCultures={
            new CharCulture(1,"聆听--藏在云朵中的故事",R.drawable.topic_1),
            new CharCulture(2,"三月，送世界一份声音的礼物",R.drawable.topic_2),
            new CharCulture(3,"从你的全世界路过",R.drawable.topic_3),
            new CharCulture(4,"生活有度，人生添寿",R.drawable.topic_4),
            new CharCulture(5,"人生贵知心，定交无暮早",R.drawable.topic_5),
            new CharCulture(6,"两世容华,三生烟火,怎及她一醉倾城",R.drawable.topic_6),
            new CharCulture(7,"埋下一座城、关了所有灯",R.drawable.topic_7),
            new CharCulture(8,"泅渡一个世界、共一场生死",R.drawable.topic_8),
            new CharCulture(9,"路过的风景、有没有人为你好好收藏",R.drawable.topic_9),
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_topic);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();

        if(actionBar!=null){
            //让导航按钮显示出来
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        initCharTopics();
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recycle_view);
        GridLayoutManager layoutManager=new GridLayoutManager(CharTopicActivity.this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new CharTopicAdapter(charCultureList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return true;
    };

    @Override public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    private void initCharTopics(){
        charCultureList.clear();
        for(CharCulture i: charCultures)
        {
            charCultureList.add(i);
        }

    }
}
