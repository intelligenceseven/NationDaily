package com.sikeandroid.nationdaily.main.data;

import java.util.UUID;

public class DailyNation {

    private UUID mId;
    private String mName;
    private String mDate;
    private int mImage;
    private int mSuspect;
    private String mUrl;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public DailyNation(int image, String date, String name, int suspect,String url)
    {
        mImage = image;
        mName = name;
        mSuspect = suspect;
        mDate = date;
        mUrl = url;
    }

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getnName() {
        return mName;
    }

    public void setnName(String nName) {
        this.mName = nName;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        mImage = image;
    }

    public int getSuspect() {
        return mSuspect;
    }

    public void setSuspect(int suspect) {
        mSuspect = suspect;
    }
}
