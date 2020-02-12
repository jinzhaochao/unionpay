package Test;

import java.text.DecimalFormat;

public class demo {
    public static void main(String[] args) {
        DecimalFormat decimalFormat=new DecimalFormat("00000000");
        String code="ZC00000000";
        String codenew=code.substring(2, code.length());
        int i=Integer.parseInt(codenew)+1;
        String k=decimalFormat.format(i);
        System.out.println(k);


    }
}
