package com.movies.bten.commons.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Progen
 * Date: 12/9/13
 * Time: 3:47 PM
 * To change this template use File | Settings | File Templates.
 */

public class StringUtil {
    public static String EOL = "\r\n";
    private static final String SINGLE_QUOTE = "'";
    private static final String EMPTY = "";
    public static String SYSTEM_EOL = System.getProperty("line.separator");
    public static final char[] DIGITS = "0123456789".toCharArray();
    public static final char[] LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public static final char[] LETTERS_AND_DIGITS = ((new String(DIGITS)) + (new String(LETTERS))).toCharArray();

    public static final String HEXES = "0123456789ABCDEF";
    public static final String DEFAULT_ENCODING = "utf-8";

    // according to Dennis's email on Tuesday, February 24, 2009 4:05 PM 
    // "Valid Email format definition in reQall server"
    // [A-z, 0-9, +,-,.,%] @ [A-z, 0-9,-,.].[A-z]    
    private static String VALID_ADDR_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+-.%";
    private static String VALID_DOMAIN_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-.";

    private static HashMap<Character, String> jsEscapesTable = new HashMap<Character, String>();

    static {
        jsEscapesTable.put('\r', "\\r");
        jsEscapesTable.put('\n', "\\n");
        jsEscapesTable.put('\t', "\\t");
        jsEscapesTable.put('\"', "\\\"");
        jsEscapesTable.put('\'', "\\\'");
        jsEscapesTable.put('È', "&#233;");
        jsEscapesTable.put('&', "&#38;");
        jsEscapesTable.put('Æ', "&#174;");
        jsEscapesTable.put('©', "&#169;");
        jsEscapesTable.put('ô', "&#153;");
        jsEscapesTable.put('ì', "&#8220;");
        jsEscapesTable.put('î', "&#8221;");
        jsEscapesTable.put('ë', "&#8216;");
        jsEscapesTable.put('í', "&#8217;");
    }


    public static boolean isValidAddressChar(char c) {
        return VALID_ADDR_CHARS.indexOf(c) > -1;
    }


    public static boolean isValidDomainChar(char c) {
        return VALID_DOMAIN_CHARS.indexOf(c) > -1;
    }


    public static boolean isValidURLChar(char c) {
        int x = c;
        return (0x00 <= x && x <= 0xFF) && c != ' ' && c != '\n' && c != '\r' && c != '\t';
    }


    /**
     * <description> taken from http://www.w3.org/TR/REC-xml/#charsets
     *
     * @param codePoint <description>
     * @return <description>
     */
    public static boolean isValidXMLChar(int codePoint) {
        int x = codePoint;


        return (x == 0x9 || x == 0xA || x == 0xD ||
                (0x20 <= x && x <= 0xD7FF) ||
                (0xE000 <= x && x <= 0xFFFD) ||
                (0x10000 <= x && x <= 0x10FFFF));
        /*
        Document authors are encouraged to avoid "compatibility characters", as
         defined in section 2.3 of [Unicode]. The characters defined in the 
         following ranges are also discouraged. They are either control characters 
         or permanently undefined Unicode characters:
         
         
        ((0x7F <= x && x <= 0x84) || (0x86 <= x && x <= 0x9F) || (0xFDD0 <= x && x <= 0xFDEF) ||
        (0x1FFFE <= x && x <= 0x1FFFF) || (0x2FFFE <= x && x <= 0x2FFFF) || (0x3FFFE <= x && x <= 0x3FFFF) ||
        (0x4FFFE <= x && x <= 0x4FFFF) || (0x5FFFE <= x && x <= 0x5FFFF) || (0x6FFFE <= x && x <= 0x6FFFF) ||
        (0x7FFFE <= x && x <= 0x7FFFF) || (0x8FFFE <= x && x <= 0x8FFFF) || (0x9FFFE <= x && x <= 0x9FFFF) ||
        (0xAFFFE <= x && x <= 0xAFFFF) || (0xBFFFE <= x && x <= 0xBFFFF) || (0xCFFFE <= x && x <= 0xCFFFF) ||
        (0xDFFFE <= x && x <= 0xDFFFF) || (0xEFFFE <= x && x <= 0xEFFFF) || (0xFFFFE <= x && x <= 0xFFFFF) ||
        (0x10FFFE <= x && x <= 0x10FFFF));*/
    }


    /**
     * @param template (if template is "My name is %0 %1")
     * @param tokens   (if tokens is {"pan", "ng"})
     * @return (then it will return "My name is pan ng")
     */
    //public static String sprintf(String template, String[] tokens) {
    public static String sprintf(String template, String... tokens) {
        if (isNullOrEmpty(template) || tokens == null)
            return template;
        StringBuilder sb = new StringBuilder(template);
        for (int i = 0; i < tokens.length; i++) {
            String key = "%" + i;
            int start = sb.indexOf(key);
            if (start > -1)
                sb = sb.replace(start, start + key.length(), tokens[i]);
        }
        return sb.toString();
    }

    public static String concat(String... parts) {
        if (parts == null || parts.length == 0)
            return null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++)
            if (parts[i] != null)
                sb.append(parts[i]);
        return sb.toString();
    }

    /**
     * @param str
     * @return returns true if string is empty or null
     */
    public static boolean isNullOrEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    /**
     * @param str
     * @return return true if the string is just spaces or tabs or empty or null
     */
    public static boolean isNullOrEmptyOrBlank(String str) {
        return isNullOrEmpty(str) || str.trim().length() == 0;
    }

    public static String toString(Object[] array) {
        return StringUtil.toString(array, ",");
    }

    public static String toString(Object[] array, String delim) {
        return StringUtil.toString(array, delim, false);
    }


    /**
     * @param array
     * @param delim
     * @param skipNull
     * @return
     */
    public static String toString(Object[] array, String delim, boolean skipNull) {

        StringBuilder sb = new StringBuilder();
        try {
            int n = 0;
            if (array == null)
                throw new Exception("Null array");

            for (Object obj : array) {
                if (obj == null && skipNull)
                    continue;
                if (n != 0) {
                    sb.append(delim);
                }
                n++;
                if (obj != null) {
                    sb.append(obj.toString());
                }


            }
        } catch (Exception e) {
            return null;
        }
        return sb.toString();
    }


    public static int toPositiveInt(String intStr) {
        return toInt(intStr, -1);
    }

    public static long toPositiveLong(String longStr) {
        return toLong(longStr, -1);
    }

    public static double toDouble(String str) {
        return toDouble(str, Double.NaN);
    }


    public static float toFloat(String str) {
        return toFloat(str, Float.NaN);
    }


    public static int toInt(String str, int invalidValue) {
        int result = invalidValue;
        try {
            result = Integer.parseInt(str);
        } catch (Exception e) {
        }
        return result;
    }

    public static long toLong(String str, long invalidValue) {
        long result = invalidValue;
        try {
            result = Long.parseLong(str);
        } catch (Exception e) {
        }
        return result;
    }

    public static float toFloat(String str, float invalidValue) {
        float result = invalidValue;
        try {
            result = Float.parseFloat(str);
        } catch (Exception e) {
        }
        return result;
    }

    public static double toDouble(String str, double invalidValue) {
        double result = invalidValue;
        try {
            result = Double.parseDouble(str);
        } catch (Exception e) {
        }
        return result;
    }


    public static String urlDecode(String str) {
        try {
            return URLDecoder.decode(str, StringUtil.DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
        }
        return str;
    }


    public static String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, StringUtil.DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
        }
        return str;
    }

    public static int indexOf(int value, int[] array) {
        return indexOf(value, 0, array);
    }

    public static int indexOf(int value, int offset, int[] array) {
        if (array == null || offset >= array.length)
            return -1;

        for (int i = offset; i < array.length; i++) {
            if (array[i] == value)
                return i;
        }

        return -1;
    }


    public static int partialIndexOf(String str, String[] array, boolean ignoreCase) {
        if (str == null || array == null)
            return -1;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == null)
                continue;
            else if (ignoreCase && str.toLowerCase().indexOf(array[i].toLowerCase()) > -1)
                return i;
            else if (!ignoreCase && str.indexOf(array[i]) > -1)
                return i;
        }

        return -1;
    }

    /**
     * return array index pos of the (String str) inside (String[] array) match by String.equals() or String.equalsIgnoreCase() method
     */
    public static int indexOf(String str, String[] array, boolean ignoreCase) {
        if (str == null || array == null)
            return -1;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == null)
                continue;
            else if (ignoreCase && array[i].equalsIgnoreCase(str))
                return i;
            else if (!ignoreCase && array[i].equals(str))
                return i;
        }

        return -1;
    }

    /**
     * return array index pos of the (String str) that ends with any String inside (String[] array) match by String.endsWith()
     */
    public static int endsWith(String str, String[] patterns, boolean ignoreCase) {
        if (str == null || patterns == null)
            return -1;

        for (int i = 0; i < patterns.length; i++) {
            if (patterns[i] == null)
                continue;
            else if (ignoreCase && str.toLowerCase().endsWith(patterns[i].toLowerCase()))
                return i;
            else if (!ignoreCase && str.endsWith(patterns[i]))
                return i;
        }

        return -1;
    }

    /**
     * return array index pos of the (String str) that starts with any String inside (String[] array) match by String.startsWith()
     */
    public static int startsWith(String str, String[] patterns, boolean ignoreCase) {
        if (str == null || patterns == null)
            return -1;

        for (int i = 0; i < patterns.length; i++) {
            if (patterns[i] == null)
                continue;
            else if (ignoreCase && str.toLowerCase().startsWith(patterns[i].toLowerCase()))
                return i;
            else if (!ignoreCase && str.startsWith(patterns[i]))
                return i;
        }

        return -1;
    }

    public static String replace(String str, String pattern, String replace) {
        if (str == null || pattern == null || replace == null)
            return str;

        int s = 0;
        int e = 0;
        StringBuilder result = new StringBuilder();

        while ((e = str.indexOf(pattern, s)) >= 0) {
            //Console.DEBUG.println("e = "+e +" replace ="+replace);
            result.append(str.substring(s, e));
            result.append(replace);
            s = e + pattern.length();
        }
        result.append(str.substring(s));
        return result.toString();
    }

    public static String[] split(String str, String delim, boolean returnEmpty) {
        if (str == null)
            return null;


        if (delim == null)
            delim = ",";
        else if (delim.equals(".") || delim.equals("|"))
            delim = "\\" + delim;

        String regex = delim;
        String[] temp = str.split(regex);
        if (temp == null || returnEmpty || temp.length == 0)
            return temp;


        String[] result = new String[temp.length];
        int n = 0;
        for (int i = 0; i < temp.length; i++) {
            if (!StringUtil.isNullOrEmpty(temp[i])) {
                result[n] = temp[i];
                n++;
            }
        }
        temp = new String[n];
        System.arraycopy(result, 0, temp, 0, temp.length);
        return temp;
    }

    public static String quoteWith(String quote, String middle) {
        return quoteWith(quote, middle, quote, true);
    }

    public static String quoteWith(String prefix, String middle, String suffix, boolean smart) {
        if (middle == null)
            return null;
        StringBuilder sb = new StringBuilder();
        if ((smart && !middle.startsWith(prefix)) || !smart) {
            sb.append(prefix);
        }
        sb.append(middle);
        if ((smart && !middle.endsWith(suffix)) || !smart) {
            sb.append(suffix);
        }
        return sb.toString();
    }


    public static StringBuilder quoteWith(String prefix, StringBuilder middle, String suffix) {
        if (middle == null)
            return null;

        middle.insert(0, prefix);
        middle.append(suffix);

        return middle;
    }


    public static boolean equals(byte[] array1, byte[] array2) {
        if (array1 == null || array2 == null || array1.length != array2.length)
            return false;
        int k = array1.length;
        for (int i = 0; i < k; i++)
            if (array1[i] != array2[i])
                return false;
        return true;
    }


    public static byte[] toMD5(byte[] plainBytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainBytes);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static String getHex(byte[] raw) {
        if (raw == null) {
            return null;
        }
        final StringBuilder hex = new StringBuilder(2 * raw.length);
        for (final byte b : raw) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }

    public static boolean same(String a, String b) {
        return (a == null && b == null) || (a != null && b != null && a.equals(b));
    }

    public static boolean sameIgnoreCase(String a, String b) {
        return (a == null && b == null) || (a != null && b != null && a.equalsIgnoreCase(b));
    }

    public static String retainValidChars(String str, char[] validChars) {
        if (StringUtil.isNullOrEmpty(str) || validChars == null)
            return null;
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            for (char b : validChars) {
                if (b == c) {
                    sb.append(c);
                    break;
                }
            }
        }
        return sb.toString();
    }

    public static String removeInvalidChars(String str, char[] invalidChars) {
        if (StringUtil.isNullOrEmpty(str) || invalidChars == null)
            return null;
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            boolean isInvalid = false;
            for (char b : invalidChars) {
                if (b == c) {
                    isInvalid = true;
                    break;
                }
            }

            if (!isInvalid)
                sb.append(c);
        }
        return sb.toString();
    }


    public static String toUpperCase(String temp, int start, int length) {
        if (temp == null || (length + start) > temp.length())
            return temp;

        return temp.substring(start, length - start).toUpperCase() + temp.substring(start + length);

    }

    /**
     * For efficiency sake, we loop here.
     */
    public static boolean startWith(String str, String prefix, boolean ignoreCase) {
        if (str == null || prefix == null || prefix.length() > str.length())
            return false;
        char[] strArray = str.toCharArray();
        char[] prefixArray = prefix.toCharArray();
        for (int i = 0; i < prefixArray.length; i++) {

            char a = ignoreCase ? Character.toLowerCase(strArray[i]) : strArray[i];
            char b = ignoreCase ? Character.toLowerCase(prefixArray[i]) : prefixArray[i];
            if (a != b)
                return false;

        }
        return true;
        /*
          if(ignoreCase){
              str = str.toLowerCase();
              prefix = prefix.toLowerCase();
          }
          return str.startsWith(prefix);
          */
    }


    public static String jsEscape(String unescapedStringToBeDisplayedInAlert) {
        if (unescapedStringToBeDisplayedInAlert == null)
            return null;
        StringBuilder sb = new StringBuilder();
        char[] chars = unescapedStringToBeDisplayedInAlert.toCharArray();
        for (char c : chars) {
            String escape = jsEscapesTable.get(new Character(c));
            if (escape == null)
                sb.append(c);
            else
                sb.append(escape);
        }
        return sb.toString();
    }


    public static String deqouted(String str) {
        return (StringUtil.isNullOrEmptyOrBlank(str)) ? StringUtil.EMPTY : str.replace(StringUtil.SINGLE_QUOTE, StringUtil.EMPTY);
    }


    public static String getTime(String time, String format) {
        return time.substring(time.indexOf("T") + 1, time.indexOf("T") + 6);
    }

    public static String getDate(String time, String format) {
        return time.substring(0, time.indexOf("T"));
    }


    /**
     * - All ordinal numbers carry a suffix: -nd, -rd, -st, or -th.
     * first (1st)
     * second (2nd)
     * third (3rd)
     * fourth (4th)
     * fifth (5th)
     * sixth (6th)
     * seventh (7th)
     * eighth (8th)
     * ninth (9th)
     * tenth (10th)
     * eleventh (11th)
     * twelfth (12th)
     * twentieth (20th)
     * twenty-first (21st)
     * twenty-second (22nd)
     * twenty-third (23rd)
     * twenty-fourth (24th)
     * thirtieth (30th)
     * one hundredth (100th)
     * one thousandth (1,000th)
     * one millionth (1,000,000th)
     * one billionth (1,000,000,000th)
     * <p/>
     * - "Do not use the ordinal (th, st, rd, nd) form of numbers when writing the complete date: January 15 is the date for the examination. However, you may use the ordinal suffixes if you use only the day: The 15th is the date for the examination. . . .
     * <p/>
     * - "Write out ordinal numbers when they contain just one word: third prize, tenth in line, sixtieth anniversary, fifteenth birthday. Use numerals for the others: the 52nd state, the 21st Amendment."
     * (Val Dumond, Grammar for Grownups. HarperCollins, 1993)
     * <p/>
     * - "When a cardinal number and an ordinal number modify the same noun, the ordinal number always precedes the cardinal number:
     * The first two operations were the most difficult to watch.
     * The second three innings were quite dull.
     * <p/>
     * In the first example, the ordinal number first precedes the cardinal number two. Both first and two are determiners. In the second example, the ordinal number second precedes the cardinal number three. Both second and three are determiners. Try reading the sentences with the ordinal and cardinal numbers reversed. They simply sound wrong."
     * (Michael Strumpf and Auriel Douglas, The Grammar Bible. Owl Books, 2004)
     */
    public static String toOrdinalNumber(int number) {
        String ordinalNumber;
        int lastDigit = number % 10;
        //ordinal Exception
        if (number == 11) {
            ordinalNumber = number + "th";
            return ordinalNumber;
        } else if (number == 12) {
            ordinalNumber = number + "th";
            return ordinalNumber;
        } else if (number == 13) {
            ordinalNumber = number + "th";
            return ordinalNumber;
        }

        switch (lastDigit) {
            case 1: {
                ordinalNumber = number + "st";
            }
            break;

            case 2: {
                ordinalNumber = number + "nd";
            }
            break;

            case 3: {
                ordinalNumber = number + "rd";
            }
            break;

            default: {
                ordinalNumber = number + "th";
            }
        }

        return ordinalNumber;
    }
}