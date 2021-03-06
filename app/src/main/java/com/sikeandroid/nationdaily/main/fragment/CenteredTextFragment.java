package com.sikeandroid.nationdaily.main.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sikeandroid.nationdaily.R;


public class CenteredTextFragment extends Fragment {

    private static final String EXTRA_TEXT = "text";

    public static CenteredTextFragment createFor(String text) {
        CenteredTextFragment fragment = new CenteredTextFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        fragment.setArguments(args);
        fragment.setMenuVisibility(true);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_daily_nation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        String text = getArguments().getString(EXTRA_TEXT);
        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText(text);
    }

}
