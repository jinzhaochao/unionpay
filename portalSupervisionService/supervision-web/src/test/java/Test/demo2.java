package Test;

/**
 * 判断两个字符串是否相等
 */
public class demo2 {
    public static void main(String[] args) {
        String a = "1,2,3,4";
        String b = "4,2,6";
        StringBuilder sb = new StringBuilder();
        String[] strings = a.split(",");
        for (String s : strings) {
            if (!b.contains(s)) {
                sb.append(s + ",");
            }
        }
        System.out.println(sb);

        String c = sb.substring(0, sb.length() - 1).toString();
        System.out.println(c);


//        String[] split = b.split(",");
//        for (String s : split) {
//            if (!a.contains(s)){
//                sb.append(s+",");
//            }
//        }
//        System.out.println(sb);
//       String  c1 = sb.substring(0, sb.length() - 1).toString();
//        System.out.println(c1);

        String[] strings2 = a.split(",");
        String[] strings3 = b.split(",");
        String str = "";
        for (int i = 0; i < strings2.length; i++) {
            for (int j = 0; j < strings3.length; j++) {
                if (strings2[i] == strings3[j]){
                    str = str + strings2[i];
                }
            }
        }
        System.out.println(str);
    }
}
