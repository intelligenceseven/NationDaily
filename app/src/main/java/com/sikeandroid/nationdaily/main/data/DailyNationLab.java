package com.sikeandroid.nationdaily.main.data;

import android.content.Context;
import com.sikeandroid.nationdaily.R;
import java.util.ArrayList;
import java.util.List;

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

    mDailyNations = new ArrayList<>();
    mDailyNations.add(
        new DailyNation( R.drawable.mz_1achang, dates.get( 0 ), "阿昌族", R.string.jj_1,"http://blog.xtwroot.com/2017/06/07/%E6%AF%8F%E6%97%A5%E6%B0%91%E6%97%8F/%E9%98%BF%E6%98%8C%E6%97%8F/" ) );

    mDailyNations.add( new DailyNation( R.drawable.mz_2bai, dates.get( 1 ), "白族", R.string.jj_2 ,"http://blog.xtwroot.com/2017/06/06/%E6%AF%8F%E6%97%A5%E6%B0%91%E6%97%8F/%E7%99%BD%E6%97%8F/") );

    mDailyNations.add(
        new DailyNation( R.drawable.mz_3baoan, dates.get( 2 ), "保安族", R.string.jj_3 ,"http://blog.xtwroot.com/2017/06/05/%E6%AF%8F%E6%97%A5%E6%B0%91%E6%97%8F/%E4%BF%9D%E5%AE%89%E6%97%8F/") );

    mDailyNations.add(
        new DailyNation( R.drawable.mz_4bulang, dates.get( 3 ), "布朗族", R.string.jj_4 ,"http://blog.xtwroot.com/2017/06/04/%E6%AF%8F%E6%97%A5%E6%B0%91%E6%97%8F/%E5%B8%83%E6%9C%97%E6%97%8F/") );

    mDailyNations.add(
        new DailyNation( R.drawable.mz_5buyi, dates.get( 4 ), "布依族", R.string.jj_5 ,"http://blog.xtwroot.com/2017/06/03/%E6%AF%8F%E6%97%A5%E6%B0%91%E6%97%8F/%E5%B8%83%E4%BE%9D%E6%97%8F/") );

    mDailyNations.add(
        new DailyNation( R.drawable.mz_6zang, dates.get( 5 ), "藏族", R.string.jj_6 ,"http://blog.xtwroot.com/2017/06/02/%E6%AF%8F%E6%97%A5%E6%B0%91%E6%97%8F/%E8%97%8F%E6%97%8F/") );

    mDailyNations.add(
        new DailyNation( R.drawable.mz_7chaoxian, dates.get( 6 ), "朝鲜族", R.string.jj_7 ,"http://blog.xtwroot.com/2017/06/01/%E6%AF%8F%E6%97%A5%E6%B0%91%E6%97%8F/%E6%9C%9D%E9%B2%9C%E6%97%8F/") );

    mDailyNations.add(
        new DailyNation( R.drawable.mz_8daguaner, dates.get( 7 ), "达斡尔族", R.string.jj_8 ,"http://blog.xtwroot.com/2017/05/31/%E6%AF%8F%E6%97%A5%E6%B0%91%E6%97%8F/%E8%BE%BE%E7%BF%B0%E5%B0%94%E6%97%8F/") );

    mDailyNations.add( new DailyNation( R.drawable.mz_9dai, dates.get( 8 ), "傣族", R.string.jj_9 ,"http://blog.xtwroot.com/2017/05/30/%E6%AF%8F%E6%97%A5%E6%B0%91%E6%97%8F/%E5%82%A3%E6%97%8F/") );

    mDailyNations.add(
        new DailyNation( R.drawable.mz_10deang, dates.get( 9 ), "德昂族", R.string.jj_10 ,"http://blog.xtwroot.com/2017/05/29/%E6%AF%8F%E6%97%A5%E6%B0%91%E6%97%8F/%E5%BE%B3%E6%98%82%E6%97%8F/") );
/**
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_11dongxiang, dates.get( 10 ), "东乡族", R.string.jj_11 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_12dong, dates.get( 11 ), "侗族", R.string.jj_12 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_13dulong, dates.get( 12 ), "独龙族", R.string.jj_13 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_14eluosi, dates.get( 13 ), "俄罗斯族", R.string.jj_14 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_15elunchun, dates.get( 14 ), "鄂伦春族", R.string.jj_15 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_16ewenke, dates.get( 15 ), "鄂温克族", R.string.jj_16 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_17gaoshan, dates.get( 16 ), "高山族", R.string.jj_17 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_18hani, dates.get( 17 ), "哈尼族", R.string.jj_18 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_19hasake, dates.get( 18 ), "哈萨克族", R.string.jj_19 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_20han, dates.get( 19 ), "汉族", R.string.jj_20 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_21hezhe, dates.get( 20 ), "赫哲族", R.string.jj_21 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_22hui, dates.get( 21 ), "回族", R.string.jj_22 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_23jinuo, dates.get( 22 ), "基诺族", R.string.jj_23 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_24jing, dates.get( 23 ), "京族", R.string.jj_24 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_25jingpo, dates.get( 24 ), "景颇族", R.string.jj_25 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_26keerkezi, dates.get( 25 ), "柯尔克孜族", R.string.jj_26 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_27lagu, dates.get( 26 ), "拉祜族", R.string.jj_27 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_28li, dates.get( 27 ), "黎族", R.string.jj_28 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_29lili, dates.get( 28 ), "傈傈族", R.string.jj_29 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_30geba, dates.get( 29 ), "戈巴族", R.string.jj_30 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_31mang, dates.get( 30 ), "满族", R.string.jj_31 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_32maonan, dates.get( 31 ), "毛南族", R.string.jj_32 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_33menba, dates.get( 32 ), "门巴族", R.string.jj_33 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_34menggujpg, dates.get( 33 ), "蒙古族", R.string.jj_34 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_35miao, dates.get( 34 ), "苗族", R.string.jj_35 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_36mulao, dates.get( 35 ), "仫佬族", R.string.jj_36 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_37naxi, dates.get( 36 ), "纳西族", R.string.jj_37 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_38nu, dates.get( 37 ), "怒族", R.string.jj_38 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_39pumi, dates.get( 38 ), "普米族", R.string.jj_39 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_40qiang, dates.get( 39 ), "羌族", R.string.jj_40 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_41sala, dates.get( 40 ), "撒拉族", R.string.jj_41 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_42she, dates.get( 41 ), "畲族", R.string.jj_42 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_43shui, dates.get( 42 ), "水族", R.string.jj_43 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_44tajike, dates.get( 43 ), "塔吉克族", R.string.jj_44 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_45tataer, dates.get( 44 ), "塔塔尔族", R.string.jj_45 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_46tujia, dates.get( 45 ), "土家族", R.string.jj_46 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_47tu, dates.get( 46 ), "土族", R.string.jj_47 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_48wa, dates.get( 47 ), "佤族", R.string.jj_48 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_49weiwuer, dates.get( 48 ), "维吾尔族", R.string.jj_49 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_50wuzibieke, dates.get( 49 ), "乌孜别克族", R.string.jj_50 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_51xibo, dates.get( 50 ), "锡伯族", R.string.jj_51 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_52yao, dates.get( 51 ), "瑶族", R.string.jj_52 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_53yi, dates.get( 52 ), "彝族", R.string.jj_53 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_54yilao, dates.get( 53 ), "仡佬族", R.string.jj_54 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_55yugu, dates.get( 54 ), "裕固族", R.string.jj_55 ) );
//
//    mDailyNations.add(
//        new DailyNation( R.drawable.mz_56zhuang, dates.get( 55 ), "壮族", R.string.jj_56 ) );
//
    */
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
