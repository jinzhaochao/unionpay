package Test;

import com.unionpay.common.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class demo3 {
    public static void main(String[] args) throws ParseException {
        String currentData = DateUtil.format(DateUtil.getCurrentDateStart(), "yyyy-MM-dd");
        System.out.println(currentData);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse("2019-10-12 12:12:12"));
        cal.add(Calendar.MONTH, -9);
        String time = sdf.format(cal.getTime());
        System.out.println(time);
        if(sdf.parse(time).after(sdf.parse("2018-06-29 15:58:37"))){
            System.out.println(1);
        }

    }
}
