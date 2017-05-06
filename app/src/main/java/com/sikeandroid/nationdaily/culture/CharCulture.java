package com.sikeandroid.nationdaily.culture;

/**
 * Created by KingChaos on 2017/5/6.
 */

public class CharCulture {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String topicName;
    private int topicImageId;

    public CharCulture(int id,String topicName, int topicImageId) {
        this.id=id;
        this.topicName = topicName;
        this.topicImageId = topicImageId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getTopicImageId() {
        return topicImageId;
    }

    public void setTopicImageId(int topicImageId) {
        this.topicImageId = topicImageId;
    }
}
