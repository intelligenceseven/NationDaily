package com.sikeandroid.nationdaily.cosplay;

/**
 * Created by Administrator on 2017/4/25.
 */

public class Cloth {
  public static String NATION_CLOTH_ID = "nation_cloth_id";
  public static String NATION_NAME = "nation_name";
  private String name;
  private int clothId;

  public Cloth(String name, int clothId) {
    this.name = name;
    this.clothId = clothId;
  }

  public String getName() {
    return name;
  }

  public int getClothId() {
    return clothId;
  }
}
