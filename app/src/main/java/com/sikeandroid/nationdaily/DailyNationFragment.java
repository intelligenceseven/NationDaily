package com.sikeandroid.nationdaily;

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
import com.sikeandroid.nationdaily.data.DailyNation;
import com.sikeandroid.nationdaily.data.DailyNationLab;

/********************************************************************************
 * using for:
 * 丁酉鸡年二月 2017/04/03 11:03
 * @author 西唐王, xtwyzh@gmail.com,xtwroot.com
 * xtwroot Copyrights (c) 2017. All rights reserved.
 ********************************************************************************/
public class DailyNationFragment extends Fragment {

    private DailyNation mDailyNation;
    private int mImage;
    private ImageView mImageView;
    private TextView mNameTextView;
    private TextView mSuspectTextView;

    private static final String ARG_DAILYNATION_DATE = "dailynation_date";

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
        mImage = mDailyNation.getImage();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_daily_nation,container,false);

        mImageView = (ImageView)v.findViewById(R.id.image_minzu);
        Glide.with( this ).load( mImage ).into( mImageView );
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),NationHistoryActivity.class);
                startActivity(intent);
            }
        });

        mNameTextView = (TextView)v.findViewById(R.id.text_minzu);
        mNameTextView.setText(mDailyNation.getName());
        mSuspectTextView = (TextView)v.findViewById(R.id.text_minzumiaoshu);
        mSuspectTextView.setText(getResources().getText(mDailyNation.getSuspect()));
        return v;
    }
}
