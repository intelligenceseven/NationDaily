package com.sikeandroid.nationdaily.culture;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sikeandroid.nationdaily.R;
import com.sikeandroid.nationdaily.cosplay.ClothAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CharTopicActivity extends AppCompatActivity {

    private CharTopicAdapter adapter;
    private List<CharCulture> charCultureList=new ArrayList<>();
    private CharCulture[] charCultures={
            new CharCulture(1,"聆听--藏在云朵中的故事",R.drawable.mz_31mang),
            new CharCulture(2,"三月，送世界一份声音的礼物",R.drawable.mz_32maonan),
            new CharCulture(3,"从你的全世界路过",R.drawable.diwen4)
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
        for(int i=0;i<10;i++){
            Random random=new Random();
            int index=random.nextInt(charCultures.length);
            charCultureList.add(charCultures[index]);
        }
    }
}
