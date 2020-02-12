package Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class demo4 {
    public static void main(String[] args) {
        long time = 0; // 得到指定日期的毫秒数
        try {
            time = new Date().getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long day2 =14;
        long day1 = day2*24*60*60*1000; // 要加上的天数转换成毫秒数
        time-=day1; // 相加得到新的毫秒数
        System.out.println( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time)).substring(0,10)); // 将毫秒数转换成日期
    }
}
