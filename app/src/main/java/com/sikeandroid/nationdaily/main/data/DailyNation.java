package com.sikeandroid.nationdaily.main.data;

import java.util.UUID;

public class DailyNation {

    private UUID mId;
    private String mName;
    private String mDate;
    private String mGreeting;
    private String mUrl;
    private String mDescribe;
    private String mImageUrl;

    public String getGreeting() {
        return mGreeting;
    }

    public void setGreeting(String greeting) {
        mGreeting = greeting;
    }

    public String getDescribe() {
        return mDescribe;
    }

    public void setDescribe(String describe) {
        mDescribe = describe;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public DailyNation(int image, String date, String name, int suspect,String url)
    {
        mName = name;
        mDate = date;
        mUrl = url;
    }

    public DailyNation(){}

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

}
