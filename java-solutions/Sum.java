public class Sum {
    public static void main(String[] args) {
        int ans = 0;
        for (String s : args) {
            int k = 0, len = 0;
            for (int i = 0; i < s.length(); ++i) {
                char a = s.charAt(i);
                if (!Character.isWhitespace(a) && i < s.length() - 1) {
                    len++;
                    continue;
                }
                if (len > 0 || (i == s.length() - 1 && !Character.isWhitespace(a))) {
                    if (!Character.isWhitespace(a)) {
                        len++;
                    }
                    String str = s.substring(k, k + len);
                    ans += Integer.parseInt(str);
                }
                len = 0;
                k = i + 1;
            }
        }
        System.out.println(ans);
    }
}