package com.sikeandroid.nationdaily.main.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;


public class DailyCalendarCount {
    private int year;
    private int month;
    private int day;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public List<String> getFiftySixDate() {
        List<String> dates = new ArrayList<String>();
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));


        for (int i = 0; i < 56; i++) {
            year=c.get(Calendar.YEAR);
            month=c.get(Calendar.MONTH)+1;
            day=c.get(Calendar.DAY_OF_MONTH)-i;
            if(month!=0){
                if(day<=0){
                    day+=MaxDayFromDay_OF_MONTH(year,month);
                    month--;
                    if(day<=0){
                        day+=MaxDayFromDay_OF_MONTH(year,month);
                        month--;
                    }
                }
            }else{
                month+=12;
                year--;
            }


            String date = year+"-"+month + "-" + day ;
            dates.add(date);
        }
        return dates;
    }
    public static int MaxDayFromDay_OF_MONTH(int year,int month){
        Calendar time=Calendar.getInstance();
        time.clear();

        time.set(Calendar.YEAR,year);
        time.set(Calendar.MONTH,month-1);//注意,Calendar对象默认一月为0
        int day=time.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数
        return day;
    }

}
