package com.sikeandroid.nationdaily.culture.data;

import android.content.Context;

import com.sikeandroid.nationdaily.R;

import java.util.ArrayList;
import java.util.List;

/********************************************************************************
 * using for:
 * 丁酉鸡年四月 2017/05/23 21:52
 * @author 西唐王, xtwyzh@gmail.com,blog.xtwroot.com
 * xtwroot Copyrights (c) 2017. All rights reserved.
 ********************************************************************************/
public class CharLab
{
    private static CharLab sCharLab;

    private List<Char> mChars;

    public static CharLab get(Context context)
    {
        if(sCharLab == null)
            sCharLab = new CharLab(context);
        return sCharLab;
    }

    CharLab(Context context)
    {
        mChars = new ArrayList<>();

        mChars.add(new Char("金", R.drawable.jin,"","http://www.xtwroot.top/404.png"));
        mChars.add(new Char("木", R.drawable.mu,"","http://www.xtwroot.top/404.png"));
        mChars.add(new Char("水", R.drawable.shui,"","http://www.xtwroot.top/404.png"));
        mChars.add(new Char("火", R.drawable.huo,"","http://www.xtwroot.top/404.png"));
        mChars.add(new Char("土", R.drawable.tu,"","http://www.xtwroot.top/404.png"));

        mChars.add(new Char("真", R.drawable.zhen,"","http://www.xtwroot.top/zhen_details.jpg"));
        mChars.add(new Char("善", R.drawable.shan,"","http://www.xtwroot.top/shan_details.jpg"));
        mChars.add(new Char("美", R.drawable.mei,"","http://www.xtwroot.top/mei_details.jpg"));

    }

    public List<Char> getChars()
    {
        return mChars;
    }

    public Char getChar(String s)
    {
        for(Char c : mChars)
            if(c.getName().equals(s))
                return c;
        return null;
    }

    public static boolean hasChar(String s)
    {
        for (Char c :
                sCharLab.mChars) {
            if(c.getName().equals(s))
                return true;
        }
        return false;
    }

}
