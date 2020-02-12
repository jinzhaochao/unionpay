package Test;

import java.util.UUID;

/**
 * 字符串分割   a,b,c,d,e
 */
public class demo1 {
    public static void main(String[] args) {
        String str = "a,b,c";
        String[] strings = str.split(",");
        for (int i = 0; i < strings.length; i++) {
            System.out.println(strings[i]);
        }
        System.out.println(UUID.randomUUID());
    }
}
