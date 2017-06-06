package com.sikeandroid.nationdaily.culture.data;

/********************************************************************************
 * using for:
 * 丁酉鸡年四月 2017/05/23 21:48
 * @author 西唐王, xtwyzh@gmail.com,blog.xtwroot.com
 * xtwroot Copyrights (c) 2017. All rights reserved.
 ********************************************************************************/
public class Char
{
    private String mName;
    private int mImage;
    private String mSummary;
    private String mImageUrl;

    public Char(String name, int image, String summary, String imageUrl) {
        mName = name;
        mImage = image;
        mSummary = summary;
        mImageUrl = imageUrl;
    }

    public String getImageUrl() {

        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        mImage = image;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }
}
