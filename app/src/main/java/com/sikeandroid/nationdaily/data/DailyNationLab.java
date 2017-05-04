package com.sikeandroid.nationdaily.data;

import android.content.Context;
import com.sikeandroid.nationdaily.R;
import java.util.ArrayList;
import java.util.List;

/********************************************************************************
 * using for:
 * 丁酉鸡年三月 2017/04/02 20:08
 * @author 西唐王, xtwyzh@gmail.com,xtwroot.com
 * xtwroot Copyrights (c) 2017. All rights reserved.
 ********************************************************************************/
public class DailyNationLab {

  private static DailyNationLab sDailyNationLab;

  private List<DailyNation> mDailyNations;

  public static DailyNationLab get(Context context) {
    if (sDailyNationLab == null) {
      sDailyNationLab = new DailyNationLab( context );
    }
    return sDailyNationLab;
  }

  private DailyNationLab(Context context) {
    DailyCalendarCount dailyCalendarCount = new DailyCalendarCount();
    List<String> dates;
    dates = dailyCalendarCount.getFiftySixDate();
    //Toast.makeText(context,dates.get(0),Toast.LENGTH_SHORT).show();
    //Toast.makeText(context,dates.get(10),Toast.LENGTH_SHORT).show();
    //Toast.makeText(context,dates.get(20),Toast.LENGTH_SHORT).show();
    //Toast.makeText(context,dates.get(30),Toast.LENGTH_SHORT).show();
    //Toast.makeText(context,dates.get(40),Toast.LENGTH_SHORT).show();
    //Toast.makeText(context,dates.get(50),Toast.LENGTH_SHORT).show();

    mDailyNations = new ArrayList<>();
    DailyNation dailyNation1 =
        new DailyNation( R.drawable.mz_1achang, dates.get( 0 ), "阿昌族", R.string.jj_1 );
    mDailyNations.add( dailyNation1 );
    DailyNation dailyNation2 =
        new DailyNation( R.drawable.mz_2bai, dates.get( 1 ), "白族", R.string.jj_2 );
    mDailyNations.add( dailyNation2 );
    DailyNation dailyNation3 =
        new DailyNation( R.drawable.mz_3baoan, dates.get( 2 ), "保安族", R.string.jj_3 );
    mDailyNations.add( dailyNation3 );
    DailyNation dailyNation4 =
        new DailyNation( R.drawable.mz_4bulang, dates.get( 3 ), "布朗族", R.string.jj_4 );
    mDailyNations.add( dailyNation4 );
    DailyNation dailyNation5 =
        new DailyNation( R.drawable.mz_5buyi, dates.get( 4 ), "布依族", R.string.jj_5 );
    mDailyNations.add( dailyNation5 );
    DailyNation dailyNation6 =
        new DailyNation( R.drawable.mz_6zang, dates.get( 5 ), "藏族", R.string.jj_6 );
    mDailyNations.add( dailyNation6 );
    DailyNation dailyNation7 =
        new DailyNation( R.drawable.mz_7chaoxian, dates.get( 6 ), "朝鲜族", R.string.jj_7 );
    mDailyNations.add( dailyNation7 );
    DailyNation dailyNation8 =
        new DailyNation( R.drawable.mz_8daguaner, dates.get( 7 ), "达斡尔族", R.string.jj_8 );
    mDailyNations.add( dailyNation8 );
    DailyNation dailyNation9 =
        new DailyNation( R.drawable.mz_9dai, dates.get( 8 ), "傣族", R.string.jj_9 );
    mDailyNations.add( dailyNation9 );
    DailyNation dailyNation10 =
        new DailyNation( R.drawable.mz_10deang, dates.get( 9 ), "德昂族", R.string.jj_10 );
    mDailyNations.add( dailyNation10 );
    DailyNation dailyNation11 =
        new DailyNation( R.drawable.mz_11dongxiang, dates.get( 10 ), "东乡族", R.string.jj_11 );
    mDailyNations.add( dailyNation11 );
    DailyNation dailyNation12 =
        new DailyNation( R.drawable.mz_12dong, dates.get( 11 ), "侗族", R.string.jj_12 );
    mDailyNations.add( dailyNation12 );
    DailyNation dailyNation13 =
        new DailyNation( R.drawable.mz_13dulong, dates.get( 12 ), "独龙族", R.string.jj_13 );
    mDailyNations.add( dailyNation13 );
    DailyNation dailyNation14 =
        new DailyNation( R.drawable.mz_14eluosi, dates.get( 13 ), "俄罗斯族", R.string.jj_14 );
    mDailyNations.add( dailyNation14 );
    DailyNation dailyNation15 =
        new DailyNation( R.drawable.mz_15elunchun, dates.get( 14 ), "鄂伦春族", R.string.jj_15 );
    mDailyNations.add( dailyNation15 );
    DailyNation dailyNation16 =
        new DailyNation( R.drawable.mz_16ewenke, dates.get( 15 ), "鄂温克族", R.string.jj_16 );
    mDailyNations.add( dailyNation16 );
    DailyNation dailyNation17 =
        new DailyNation( R.drawable.mz_17gaoshan, dates.get( 16 ), "高山族", R.string.jj_17 );
    mDailyNations.add( dailyNation17 );
    DailyNation dailyNation18 =
        new DailyNation( R.drawable.mz_18hani, dates.get( 17 ), "哈尼族", R.string.jj_18 );
    mDailyNations.add( dailyNation18 );
    DailyNation dailyNation19 =
        new DailyNation( R.drawable.mz_19hasake, dates.get( 18 ), "哈萨克族", R.string.jj_19 );
    mDailyNations.add( dailyNation19 );
    DailyNation dailyNation20 =
        new DailyNation( R.drawable.mz_20han, dates.get( 19 ), "汉族", R.string.jj_20 );
    mDailyNations.add( dailyNation20 );
    DailyNation dailyNation21 =
        new DailyNation( R.drawable.mz_21hezhe, dates.get( 20 ), "赫哲族", R.string.jj_21 );
    mDailyNations.add( dailyNation21 );
    DailyNation dailyNation22 =
        new DailyNation( R.drawable.mz_22hui, dates.get( 21 ), "回族", R.string.jj_22 );
    mDailyNations.add( dailyNation22 );
    DailyNation dailyNation23 =
        new DailyNation( R.drawable.mz_23jinuo, dates.get( 22 ), "基诺族", R.string.jj_23 );
    mDailyNations.add( dailyNation23 );
    DailyNation dailyNation24 =
        new DailyNation( R.drawable.mz_24jing, dates.get( 23 ), "京族", R.string.jj_24 );
    mDailyNations.add( dailyNation24 );
    DailyNation dailyNation25 =
        new DailyNation( R.drawable.mz_25jingpo, dates.get( 24 ), "景颇族", R.string.jj_25 );
    mDailyNations.add( dailyNation25 );
    DailyNation dailyNation26 =
        new DailyNation( R.drawable.mz_26keerkezi, dates.get( 25 ), "柯尔克孜族", R.string.jj_26 );
    mDailyNations.add( dailyNation26 );
    DailyNation dailyNation27 =
        new DailyNation( R.drawable.mz_27lagu, dates.get( 26 ), "拉祜族", R.string.jj_27 );
    mDailyNations.add( dailyNation27 );
    DailyNation dailyNation28 =
        new DailyNation( R.drawable.mz_28li, dates.get( 27 ), "黎族", R.string.jj_28 );
    mDailyNations.add( dailyNation28 );
    DailyNation dailyNation29 =
        new DailyNation( R.drawable.mz_29lili, dates.get( 28 ), "傈傈族", R.string.jj_29 );
    mDailyNations.add( dailyNation29 );
    DailyNation dailyNation30 =
        new DailyNation( R.drawable.mz_30geba, dates.get( 29 ), "戈巴族", R.string.jj_30 );
    mDailyNations.add( dailyNation30 );
    DailyNation dailyNation31 =
        new DailyNation( R.drawable.mz_31mang, dates.get( 30 ), "满族", R.string.jj_31 );
    mDailyNations.add( dailyNation31 );
    DailyNation dailyNation32 =
        new DailyNation( R.drawable.mz_32maonan, dates.get( 31 ), "毛南族", R.string.jj_32 );
    mDailyNations.add( dailyNation32 );
    DailyNation dailyNation33 =
        new DailyNation( R.drawable.mz_33menba, dates.get( 32 ), "门巴族", R.string.jj_33 );
    mDailyNations.add( dailyNation33 );
    DailyNation dailyNation34 =
        new DailyNation( R.drawable.mz_34menggujpg, dates.get( 33 ), "蒙古族", R.string.jj_34 );
    mDailyNations.add( dailyNation34 );
    DailyNation dailyNation35 =
        new DailyNation( R.drawable.mz_35miao, dates.get( 34 ), "苗族", R.string.jj_35 );
    mDailyNations.add( dailyNation35 );
    DailyNation dailyNation36 =
        new DailyNation( R.drawable.mz_36mulao, dates.get( 35 ), "仫佬族", R.string.jj_36 );
    mDailyNations.add( dailyNation36 );
    DailyNation dailyNation37 =
        new DailyNation( R.drawable.mz_37naxi, dates.get( 36 ), "纳西族", R.string.jj_37 );
    mDailyNations.add( dailyNation37 );
    DailyNation dailyNation38 =
        new DailyNation( R.drawable.mz_38nu, dates.get( 37 ), "怒族", R.string.jj_38 );
    mDailyNations.add( dailyNation38 );
    DailyNation dailyNation39 =
        new DailyNation( R.drawable.mz_39pumi, dates.get( 38 ), "普米族", R.string.jj_39 );
    mDailyNations.add( dailyNation39 );
    DailyNation dailyNation40 =
        new DailyNation( R.drawable.mz_40qiang, dates.get( 39 ), "羌族", R.string.jj_40 );
    mDailyNations.add( dailyNation40 );
    DailyNation dailyNation41 =
        new DailyNation( R.drawable.mz_41sala, dates.get( 40 ), "撒拉族", R.string.jj_41 );
    mDailyNations.add( dailyNation41 );
    DailyNation dailyNation42 =
        new DailyNation( R.drawable.mz_42she, dates.get( 41 ), "畲族", R.string.jj_42 );
    mDailyNations.add( dailyNation42 );
    DailyNation dailyNation43 =
        new DailyNation( R.drawable.mz_43shui, dates.get( 42 ), "水族", R.string.jj_43 );
    mDailyNations.add( dailyNation43 );
    DailyNation dailyNation44 =
        new DailyNation( R.drawable.mz_44tajike, dates.get( 43 ), "塔吉克族", R.string.jj_44 );
    mDailyNations.add( dailyNation44 );
    DailyNation dailyNation45 =
        new DailyNation( R.drawable.mz_45tataer, dates.get( 44 ), "塔塔尔族", R.string.jj_45 );
    mDailyNations.add( dailyNation45 );
    DailyNation dailyNation46 =
        new DailyNation( R.drawable.mz_46tujia, dates.get( 45 ), "土家族", R.string.jj_46 );
    mDailyNations.add( dailyNation46 );
    DailyNation dailyNation47 =
        new DailyNation( R.drawable.mz_47tu, dates.get( 46 ), "土族", R.string.jj_47 );
    mDailyNations.add( dailyNation47 );
    DailyNation dailyNation48 =
        new DailyNation( R.drawable.mz_48wa, dates.get( 47 ), "佤族", R.string.jj_48 );
    mDailyNations.add( dailyNation48 );
    DailyNation dailyNation49 =
        new DailyNation( R.drawable.mz_49weiwuer, dates.get( 48 ), "维吾尔族", R.string.jj_49 );
    mDailyNations.add( dailyNation49 );
    DailyNation dailyNation50 =
        new DailyNation( R.drawable.mz_50wuzibieke, dates.get( 49 ), "乌孜别克族", R.string.jj_50 );
    mDailyNations.add( dailyNation50 );
    DailyNation dailyNation51 =
        new DailyNation( R.drawable.mz_51xibo, dates.get( 50 ), "锡伯族", R.string.jj_51 );
    mDailyNations.add( dailyNation51 );
    DailyNation dailyNation52 =
        new DailyNation( R.drawable.mz_52yao, dates.get( 51 ), "瑶族", R.string.jj_52 );
    mDailyNations.add( dailyNation52 );
    DailyNation dailyNation53 =
        new DailyNation( R.drawable.mz_53yi, dates.get( 52 ), "彝族", R.string.jj_53 );
    mDailyNations.add( dailyNation53 );
    DailyNation dailyNation54 =
        new DailyNation( R.drawable.mz_54yilao, dates.get( 53 ), "仡佬族", R.string.jj_54 );
    mDailyNations.add( dailyNation54 );
    DailyNation dailyNation55 =
        new DailyNation( R.drawable.mz_55yugu, dates.get( 54 ), "裕固族", R.string.jj_55 );
    mDailyNations.add( dailyNation55 );
    DailyNation dailyNation56 =
        new DailyNation( R.drawable.mz_56zhuang, dates.get( 55 ), "壮族", R.string.jj_56 );
    mDailyNations.add( dailyNation56 );
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
