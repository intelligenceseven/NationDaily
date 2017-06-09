package com.sikeandroid.nationdaily.main.data;

import android.content.Context;

import com.sikeandroid.nationdaily.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DailyNationLab {

  private static DailyNationLab sDailyNationLab;

  private List<DailyNation> mDailyNations;
  private String mJsonString = "";

  public static DailyNationLab get(Context context) {
    //if (sDailyNationLab == null) {
      sDailyNationLab = new DailyNationLab( context );
    //}
    return sDailyNationLab;
  }

  private DailyNationLab(Context context) {

    mDailyNations = new ArrayList<>();

    try {
      URL url=new URL("http://image.xtwroot.top/json/minzu.json");
      HttpURLConnection conn=(HttpURLConnection)url.openConnection();
      //取得inputStream，并进行读取
      InputStream input=conn.getInputStream();
      BufferedReader in=new BufferedReader(new InputStreamReader(input));
      String line=null;
      StringBuffer sb=new StringBuffer();
      while((line=in.readLine())!=null)
      {
        sb.append(line);
      }
      System.out.println(sb.toString());

      mJsonString = sb.toString();

    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      JSONObject jsonObject = new JSONObject(mJsonString);
      JsonUtils.parseNationLabs(mDailyNations,jsonObject );
    } catch (JSONException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public List<DailyNation> getDailyNations() {
    return mDailyNations;
  }

  public DailyNation getDailyNation(String date) {
    for (DailyNation dailyNation : mDailyNations) {
      if (dailyNation.getDate().equals( date )) return dailyNation;
    }
    return null;
  }
}
