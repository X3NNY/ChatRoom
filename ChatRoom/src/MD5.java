import java.security.MessageDigest;

public class MD5 {
    private static final String slat = "&%5123***&&%%$$#@";
    public static String encode(String str) {
        try {
            str = str + slat;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(str.getBytes("UTF8"));
            byte[] s = m.digest();
            String res = "";
            for (int i = 0; i < s.length; i++) {
                res += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
