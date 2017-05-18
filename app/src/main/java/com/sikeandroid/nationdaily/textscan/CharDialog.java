package com.sikeandroid.nationdaily.textscan;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sikeandroid.nationdaily.R;

/********************************************************************************
 * using for:
 * 丁酉鸡年四月 2017/05/17 23:56
 * @author 西唐王, xtwyzh@gmail.com,blog.xtwroot.com
 * xtwroot Copyrights (c) 2017. All rights reserved.
 ********************************************************************************/
public class CharDialog extends Dialog{

    private TextView charTextView;
    //private ImageButton closeButton;

    public CharDialog(@NonNull Context context)
    {
        super(context);
        setCharTextView();
    }

    private void setCharTextView()
    {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dailog_char_select,null);
        charTextView = (TextView) mView.findViewById(R.id.char_text);
        //closeButton = (ImageButton)mView.findViewById(R.id.char_close_button);
        super.setContentView(mView);
    }

    public View getText()
    {
        return charTextView;
    }

    public void setClostListener(View.OnClickListener listener)
    {
        //closeButton.setOnClickListener(listener);
    }
}
