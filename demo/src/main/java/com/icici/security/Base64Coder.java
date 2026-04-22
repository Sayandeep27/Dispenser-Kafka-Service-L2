package com.icici.security;
 
public final class Base64Coder {
    
    private static final String systemLineSeparator = System
            .getProperty("line.separator");
    private static char map1[];
    private static byte map2[];
 
    public static String encodeString(String s) {
        return new String(encode(s.getBytes()));
    }
 
    public static String encodeLines(byte abyte0[]) {
        return encodeLines(abyte0, 0, abyte0.length, 76, systemLineSeparator);
    }
 
    public static String encodeLines(byte abyte0[], int i, int j, int k,
            String s) {
        int l = (k * 3) / 4;
        if (l <= 0)
            throw new IllegalArgumentException();
        int i1 = ((j + l) - 1) / l;
        int j1 = ((j + 2) / 3) * 4 + i1 * s.length();
        StringBuilder stringbuilder = new StringBuilder(j1);
        int l1;
        for (int k1 = 0; k1 < j; k1 += l1) {
            l1 = Math.min(j - k1, l);
            stringbuilder.append(encode(abyte0, i + k1, l1));
            stringbuilder.append(s);
        }
 
        return stringbuilder.toString();
    }
 
    public static char[] encode(byte abyte0[]) {
        return encode(abyte0, 0, abyte0.length);
    }
 
    public static char[] encode(byte abyte0[], int i) {
        return encode(abyte0, 0, i);
    }
 
    public static char[] encode(byte abyte0[], int i, int j) {
        int k = (j * 4 + 2) / 3;
        int l = ((j + 2) / 3) * 4;
        char ac[] = new char[l];
        int i1 = i;
        int j1 = i + j;
        for (int k1 = 0; i1 < j1; k1++) {
            int l1 = abyte0[i1++] & 0xff;
            int i2 = i1 >= j1 ? 0 : abyte0[i1++] & 0xff;
            int j2 = i1 >= j1 ? 0 : abyte0[i1++] & 0xff;
            int k2 = l1 >>> 2;
            int l2 = (l1 & 3) << 4 | i2 >>> 4;
            int i3 = (i2 & 0xf) << 2 | j2 >>> 6;
            int j3 = j2 & 0x3f;
            ac[k1++] = map1[k2];
            ac[k1++] = map1[l2];
            ac[k1] = k1 >= k ? '=' : map1[i3];
            k1++;
            ac[k1] = k1 >= k ? '=' : map1[j3];
        }
 
        return ac;
    }
 
    public static String decodeString(String s) {
        return new String(decode(s));
    }
 
    public static byte[] decodeLines(String s) {
        char ac[] = new char[s.length()];
        int i = 0;
        for (int j = 0; j < s.length(); j++) {
            char c = s.charAt(j);
            if (c != ' ' && c != '\r' && c != '\n' && c != '\t')
                ac[i++] = c;
        }
 
        return decode(ac, 0, i);
    }
 
    public static byte[] decode(String s) {
        return decode(s.toCharArray());
    }
 
    public static byte[] decode(char ac[]) {
        return decode(ac, 0, ac.length);
    }
 
    public static byte[] decode(char ac[], int i, int j) {
        if (j % 4 != 0)
            throw new IllegalArgumentException(
                    "Length of Base64 encoded input string is not a multiple of 4.");
        for (; j > 0 && ac[(i + j) - 1] == '='; j--)
            ;
        int k = (j * 3) / 4;
        byte abyte0[] = new byte[k];
        int l = i;
        int i1 = i + j;
        int j1 = 0;
        do {
            if (l >= i1)
                break;
            char c = ac[l++];
            char c1 = ac[l++];
            char c2 = l >= i1 ? 'A' : ac[l++];
            char c3 = l >= i1 ? 'A' : ac[l++];
            if (c > '\177' || c1 > '\177' || c2 > '\177' || c3 > '\177')
                throw new IllegalArgumentException(
                        "Illegal character in Base64 encoded data.");
            byte byte0 = map2[c];
            byte byte1 = map2[c1];
            byte byte2 = map2[c2];
            byte byte3 = map2[c3];
            if (byte0 < 0 || byte1 < 0 || byte2 < 0 || byte3 < 0)
                throw new IllegalArgumentException(
                        "Illegal character in Base64 encoded data.");
            int k1 = byte0 << 2 | byte1 >>> 4;
            int l1 = (byte1 & 0xf) << 4 | byte2 >>> 2;
            int i2 = (byte2 & 3) << 6 | byte3;
            abyte0[j1++] = (byte) k1;
            if (j1 < k)
                abyte0[j1++] = (byte) l1;
            if (j1 < k)
                abyte0[j1++] = (byte) i2;
        } while (true);
        return abyte0;
    }
 
    private Base64Coder() {
    }
 
    static {
        map1 = new char[64];
        int i = 0;
        for (char c = 'A'; c <= 'Z'; c++)
            map1[i++] = c;
 
        for (char c1 = 'a'; c1 <= 'z'; c1++)
            map1[i++] = c1;
 
        for (char c2 = '0'; c2 <= '9'; c2++)
            map1[i++] = c2;
 
        map1[i++] = '+';
        map1[i++] = '/';
        map2 = new byte[128];
        for (int j = 0; j < map2.length; j++)
            map2[j] = -1;
 
        for (int k = 0; k < 64; k++)
            map2[map1[k]] = (byte) k;
 
    }
    /*
     * private Base64Coder() { }
     */
 
    /*
     * public static void main(String args[]) { Base64Coder bc = new
     * Base64Coder(); }
     */
}
 
 