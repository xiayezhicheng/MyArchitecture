package com.wanghao.myarchitecture.utils;

import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wanghao on 2016/3/23.
 */
public class StringUtils {
    public static String getStr(String str) {
        return isEmpty(str) ? "" : str;
    }

    public static String getEditTextContent(EditText edt) {
        return edt.getText().toString().trim();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.equals("null") || str.equals("");
    }

    public static boolean isEditTextEmpty(EditText edt) {
        return isEmpty(getEditTextContent(edt));
    }

    /**
     * 必须要是非空且11位数
     * @author wanghao
     * @since 2015-1-27 下午5:20:40
     * @param edt 输入的电话号码
     */
    public static boolean isPhoneEditTextEmpty(EditText edt) {
        return isEmpty(getEditTextContent(edt)) || getEditTextContent(edt).length() != 11;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String getTextViewContent(TextView tv) {
        return tv.getText().toString().trim();
    }

    /**
     * 首字母转大写
     * @param s
     * @return
     */
    public static String toUpperFirst(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder())
                    .append(Character.toUpperCase(s.charAt(0)))
                    .append(s.substring(1)).toString();
    }

    /**
     * 验证是否为数字
     */
    public static boolean isNumber(String str) {
        if (isEmpty(str)) {
            return false;
        }
        java.util.regex.Pattern pattern = java.util.regex.Pattern
                .compile("[0-9]*");
        java.util.regex.Matcher match = pattern.matcher(str);
        return match.matches() != false;
    }

    /**
     * utf-8编码
     *
     * StringUtils.utf8Encode(null) = null StringUtils.utf8Encode("") = "";
     * StringUtils.utf8Encode("aa") = "aa"; StringUtils.utf8Encode("啊啊啊啊") =
     * "%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A";
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     *             if an error occurs
     */
    public static String utf8Encode(String str) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(
                        "UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }

    /**
     * utf-8编码, 如果出意外，返回默认值
     *
     * @param str
     * @param defultReturn
     * @return
     */
    public static String utf8Encode(String str, String defultReturn) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return defultReturn;
            }
        }
        return str;
    }


    /*
     * 把中文字符串转换为十六进制Unicode编码字符串
     */
    public static String stringToUnicode(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            if (ch > 255)
                str += "\\u" + Integer.toHexString(ch);
            else
                str += "\\" + Integer.toHexString(ch);
        }
        return str;
    }

    /*
     * 把十六进制Unicode编码字符串转换为中文字符串
     */
    public static String unicodeToString(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }
}
