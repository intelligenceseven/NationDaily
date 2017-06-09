package com.sikeandroid.nationdaily.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gjiazhe.panoramaimageview.GyroscopeObserver;
import com.sikeandroid.nationdaily.R;
import com.sikeandroid.nationdaily.main.data.DailyNation;
import com.sikeandroid.nationdaily.main.data.DailyNationLab;
public class DailyNationFragment extends Fragment {

    public static final String NATION_HISTORY_URL = "NationHistoryUrl";
    private DailyNation mDailyNation;
    private ImageView mImageView;
    private TextView mNameTextView;
    private TextView mSuspectTextView;
    private TextView mTimeTextView;
    private GyroscopeObserver gyroscopeObserver;

    private static final String ARG_DAILYNATION_DATE = "dailynation_date";
    private ImageView mImageTime;
    private TextView mGreetingTextView;

    public static DailyNationFragment newInstance(String date)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DAILYNATION_DATE,date);

        DailyNationFragment fragment = new DailyNationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String date = "2017-4-6";
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            date = (String)bundle.getSerializable(ARG_DAILYNATION_DATE);
        }
        mDailyNation = DailyNationLab.get(getActivity()).getDailyNation(date);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate( R.layout.fragment_daily_nation,container,false);

        gyroscopeObserver = new GyroscopeObserver();
        // Set the maximum radian the device should rotate to show image's bounds.
        // It should be set between 0 and π/2.
        // The default value is π/9.
        gyroscopeObserver.setMaxRotateRadian(Math.PI/9);

        mImageView = (ImageView)v.findViewById(R.id.image_minzu);
        // Set GyroscopeObserver for PanoramaImageView.

        //mImageView = (ImageView)v.findViewById(R.id.image_minzu);
        Glide.with( this ).load( mDailyNation.getImageUrl() ).into( mImageView );
        //mImageView.setImageDrawable(getResources().getDrawable(mImage));
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),NationHistoryActivity.class);
                intent.putExtra(NATION_HISTORY_URL,mDailyNation.getUrl());
                startActivity(intent);
            }
        });

        mNameTextView = (TextView)v.findViewById(R.id.text_minzu);
        mNameTextView.setText(mDailyNation.getName());
        mSuspectTextView = (TextView)v.findViewById(R.id.text_minzumiaoshu);
        mSuspectTextView.setText(mDailyNation.getDescribe());
        mTimeTextView = (TextView)v.findViewById(R.id.text_time);
        String time = mDailyNation.getDate();
        time = time.replaceFirst("-","年\n");time = time.replaceFirst("-","月");time +="日";
        mTimeTextView.setText(time);

        mImageTime = (ImageView)v.findViewById(R.id.image_time);
        mImageTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),NationHistoryActivity.class);
                intent.putExtra(NATION_HISTORY_URL,"http://blog.xtwroot.com/categories/%E6%AF%8F%E6%97%A5%E6%B0%91%E6%97%8F/");
                startActivity(intent);
            }
        });

        mGreetingTextView = (TextView) v.findViewById(R.id.text_greeting);
        mGreetingTextView.setText(mDailyNation.getGreeting());

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
