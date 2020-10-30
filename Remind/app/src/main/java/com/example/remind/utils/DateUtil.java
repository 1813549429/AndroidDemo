package com.example.remind.utils;

import android.content.Context;
import android.text.TextUtils;

import com.example.remind.R;
import com.example.remind.app.AppContext;
import com.example.remind.db.entity.Remind;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {


    /**
     * @param currentTime 时间戳
     *
     * @return 一个int数组，数组第一个为年 yyyy
     *                        第二个为月 MM
     *                        第三个为日 dd
     *                        第四个为时 HH
     *                        第五个为分 mm
     *                        第六个为周 0-6分别代表周日到周六
     */
    public static int[] getDay(long currentTime) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        int[] dates = new int[6];
        String time = null;
        Date date = new Date(currentTime);
        String dateString = sdr.format(date);
        String[] ss = dateString.split("-");
        for (int i = 0; i < 5; i++) {
            dates[i] = Integer.parseInt(ss[i]);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        dates[5] = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        return dates;
    }

    /**
     * @param iWeek 0-6
     * @return  周日  -》 周六
     */
    public static String numberToWeek(int iWeek) {
        switch (iWeek) {
            case 0:
                return "Sunday";
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            default:
                return "Saturday";
        }
    }

    /**
     * @param str 需要转化成long类型的字符串
     * @param format    转化的格式   类似(yyyy-MM-dd HH:mm)这种
     * @return  long类型的时间戳
     */
    public static Long strToLong(String str, String format){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(str);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new NullPointerException("传入的数据有问题，请重新检查转化格式等是否正确!");
    }

    /**
     * @param iMonth 1-12
     * @return  月份
     */
    public static String numberToMonth(int iMonth) {
        switch (iMonth) {
            case 1:
                return AppContext.getContext().getString(R.string.january);
            case 2:
                return AppContext.getContext().getString(R.string.february);
            case 3:
                return AppContext.getContext().getString(R.string.march);
            case 4:
                return AppContext.getContext().getString(R.string.april);
            case 5:
                return AppContext.getContext().getString(R.string.may);
            case 6:
                return AppContext.getContext().getString(R.string.june);
            case 7:
                return AppContext.getContext().getString(R.string.july);
            case 8:
                return AppContext.getContext().getString(R.string.august);
            case 9:
                return AppContext.getContext().getString(R.string.september);
            case 10:
                return AppContext.getContext().getString(R.string.october);
            case 11:
                return AppContext.getContext().getString(R.string.november);
            default:
                return AppContext.getContext().getString(R.string.december);
        }
    }

    public static long intArrayToLong(int[] date) {
        String dateStr = date[0] + "-" + date[1] + "-" +
                date[2] + "-" + date[3] + "-" + date[4];
        return strToLong(dateStr, "yyyy-MM-dd-HH-mm");
    }

    public static int getYearMonthDay(long currentTime) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date(currentTime);
        return Integer.parseInt(sdr.format(date));
    }

    public static String longToStr(long time) {
        int date = getYearMonthDay(time);
        int currentDate = getYearMonthDay(System.currentTimeMillis());
        if(date - currentDate == 0) {
            return AppContext.getContext().getString(R.string.today);
        }else if(date - currentDate == 1) {
            return AppContext.getContext().getString(R.string.tomorrow);
        } else if(date - currentDate == -1) {
            return AppContext.getContext().getString(R.string.yesterday);
        } else {
            int[] allTime = getDay(time);
            String weekDay = numberToWeek(allTime[5]).substring(0,3);
            String monthDay = numberToMonth(allTime[1]).substring(0,3);
            int weekCount = 1;
            while (allTime[2] >= 7) {
                weekCount++;
                allTime[2] -= 7;
            }
            return weekDay+","+monthDay+weekCount;
        }
    }

    /**
     * 将Remind对象转化成可供显示在屏幕的字符串数组
     * @param remind
     * @return  数组长度为5，从0-4分别代表标题、设置的时间、提前提醒的时间、重复的时间、备注
     */
    public static String[] remindToStr(Remind remind) {
        Context context = AppContext.getContext();
        String[] strings = new String[5];
        strings[0] = remind.getTitle();
        strings[4] = remind.getRemark();
        //将时间戳显示成需要显示的样式
        int[] days = getDay(remind.getTime());
        if(days[3] >= 12) {
            strings[1] = days[3]-12 + ":" + days[4] + " PM";
        }else {
            strings[1] = days[3] + ":" + days[4] + " AM";
        }
        if(remind.getAdvance() != null) {
            List<String> advanceList = JsonUtil.jsonToStrList(remind.getAdvance());
            StringBuilder builder = new StringBuilder();
            for (String advanceTime : advanceList) {
                int advanceDate = Integer.parseInt(advanceTime);
                if (advanceDate == -1) {
                    builder.append(context.getString(R.string.none) + ",");
                }else if (advanceDate == 0) {
                    builder.append(context.getString(R.string.one_time) + ",");
                }else if (advanceDate == 5*60*1000) {
                    builder.append(context.getString(R.string.five_mins_early) + ",");
                }else if (advanceDate == 30*60*1000) {
                    builder.append(context.getString(R.string.thirty_mins_early) + ",");
                } else if (advanceDate == 60*60*1000) {
                    builder.append(context.getString(R.string.one_hour_early) + ",");
                }else if (advanceDate == 24*60*60*1000) {
                    builder.append(context.getString(R.string.one_day_early) + ",");
                }else if (advanceDate == 2*24*60*60*1000) {
                    builder.append(context.getString(R.string.two_day_early) + ",");
                }else if (advanceDate == 3*24*60*60*1000) {
                    builder.append(context.getString(R.string.three_day_early) + ",");
                }else if (advanceDate == 7*24*60*60*1000) {
                    builder.append(context.getString(R.string.one_week_early));
                }
            }
            builder.deleteCharAt(builder.length()-1);
            strings[2] = builder.toString();
        }

        int repeatType = remind.getRepeatType();
        int repeatInterval = remind.getRepeatInterval();
        String repeatValue = remind.getRepeatValue();
        List<String> repeatValueList = null;
        if(!TextUtils.isEmpty(repeatValue)) {
            repeatValueList = JsonUtil.jsonToStrList(repeatValue);
        }
        if (repeatType == 0) {
            strings[3] = context.getString(R.string.none);
        }else if (repeatType == 1) {
            strings[3] = context.getString(R.string.yearly);
        }else if (repeatType == 2) {
            strings[3] = context.getString(R.string.monthly);
        }else if (repeatValueList != null && repeatType == 3 && repeatValueList.size()==1) {
            strings[3] = context.getString(R.string.weekly);
        }else if(repeatValueList != null && repeatType == 3 && repeatValueList.size()==5) {
            strings[3] = context.getString(R.string.every_weekday);
        } else if (repeatType == 4) {
            strings[3] = context.getString(R.string.daily);
        }
        return strings;
    }

}
